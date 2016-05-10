package com.netease.nrtc.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nrtc.sdk.NRtc;
import com.netease.nrtc.sdk.NRtcCallback;
import com.netease.nrtc.sdk.NRtcConstants;
import com.netease.nrtc.sdk.NRtcOptional;
import com.netease.nrtc.sdk.SessionStats;
import com.netease.nrtc.sdk.toolbox.ScreenLocker;
import java.util.HashSet;
import java.util.Set;

public class MultiChatActivity extends AppCompatActivity implements NRtcCallback, View.OnClickListener {

    private static final String TAG = "MultiChat";

    public static void launch(Context context, long uid,
                              String channel, String token,
                              boolean videoEnabled, boolean multi, boolean audience) {

        Intent intent = new Intent();
        intent.putExtra("uid", uid);
        intent.putExtra("channel", channel);
        intent.putExtra("videoEnabled", videoEnabled);
        intent.putExtra("token", token);
        intent.putExtra("multi", multi);
        intent.putExtra("audience", audience);
        intent.setClass(context, MultiChatActivity.class);
        context.startActivity(intent);

    }

    //离开房间
    private final static int MENU_QUIT = 1;
    //切换摄像头
    private final static int MENU_SWITCH_CAMERA = 2;
    //选中用户
    private final static int MENU_SELECTED = 3;
    //交换布局文件
    private final static int MENU_SWITCH_RENDER = 5;

    private final static int MENU_AUDIO_MUTED = 10;
    private final static int MENU_VIDEO_MUTED = 11;

    private final static int MENU_SNAPSHOT = 20;

    private final static int MENU_ROLE = 40;


    private final static int MENU_RECORD = 30;

    private long selectedRenderUid = 0;

    private User actionUser = null;



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        User user = (User) v.getTag();
        actionUser = user;

        if(user != null) {
            menu.setHeaderTitle(user.uid + "");

            if(user.uid == uid) {
                menu.add(0, MENU_QUIT, Menu.NONE, "离开会话");

                if(nrtc.getRole() == NRtcConstants.UserRole.AUDIENCE) {
                    menu.add(0, MENU_ROLE, Menu.NONE, "切换为普通用户");
                    return;
                } else {
                    menu.add(0, MENU_ROLE, Menu.NONE, "切换为观众用户");
                }

                if(isCallEstablished) {

                    if(videoEnabled) {
                        if(nrtc.hasMultipleCameras()) {
                            menu.add(0, MENU_SWITCH_CAMERA, Menu.NONE, "切换摄像头");
                        }
                        menu.add(0, MENU_VIDEO_MUTED, Menu.NONE, nrtc.videoStreamMuted(uid) ? "打开视频发送" : "关闭视频发送");
                    }
                    menu.add(0, MENU_AUDIO_MUTED, Menu.NONE, nrtc.audioStreamMuted(user.uid) ? "打开语音发送" : "关闭语音发送");
                    menu.add(0, MENU_RECORD, Menu.NONE, nrtc.isLocalRecording() ? "关闭录制" : "打开录制");
                }


            } else {

                if(isCallEstablished) {
                    if(videoEnabled) {
                        menu.add(0, MENU_VIDEO_MUTED, Menu.NONE, nrtc.videoStreamMuted(user.uid) ? "打开视频接收" : "关闭视频接收");
                    }
                    menu.add(0, MENU_AUDIO_MUTED, Menu.NONE, nrtc.audioStreamMuted(user.uid) ? "打开语音接收" : "关闭语音接收");
                }

            }



            if(videoEnabled && isCallEstablished) {

                if(selectedRenderUid == 0) {
                    menu.add(0, MENU_SELECTED, Menu.NONE, "选中用户");
                } else {
                    if(selectedRenderUid == user.uid) {
                        menu.add(0, MENU_SELECTED, Menu.NONE, "取消选中");
                    } else {
                        menu.add(0, MENU_SWITCH_RENDER, Menu.NONE, "交换布局");
                    }
                }

                menu.add(0, MENU_SNAPSHOT, Menu.NONE, "截图");
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final long uu = actionUser.uid;

        switch (item.getItemId()) {
            case MENU_QUIT:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        leave();
                    }
                });
                break;
            case MENU_SWITCH_CAMERA:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        nrtc.switchCamera();
                    }
                });
                break;
            case MENU_SELECTED:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        User user = findUser(uu);

                        if(uu == selectedRenderUid) {
                            //取消选中
                            selectedRenderUid = 0;
                            user.selected = false;
                        } else {
                            //选中
                            selectedRenderUid = uu;
                            user.selected = true;
                        }

                        refreshHolder(user.holder, false);
                    }
                });
                break;
            case MENU_VIDEO_MUTED:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        nrtc.muteVideoStream(uu, !nrtc.videoStreamMuted(uu));
                        User user = findUser(uu);
                        user.videoMuted = nrtc.videoStreamMuted(uu);
                        refreshHolder(user.holder, true);
                    }
                });
                break;
            case MENU_AUDIO_MUTED:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        nrtc.muteAudioStream(uu, !nrtc.audioStreamMuted(uu));
                        User user = findUser(uu);
                        user.audioMuted = nrtc.audioStreamMuted(uu);
                        refreshHolder(user.holder, false);
                    }
                });
                break;
            case MENU_SWITCH_RENDER:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        User user_1 = findUser(selectedRenderUid);
                        User user_2 = findUser(uu);

                        //SurfaceView 已经完成交换
                        nrtc.switchRender(selectedRenderUid, uu);

                        //为了UI看起来不至于混乱，其余所有的状态也交换
                        PreviewHolder holder = user_1.holder;
                        user_1.holder = user_2.holder;
                        user_2.holder = holder;

                        user_1.holder.container.setTag(user_1);
                        user_2.holder.container.setTag(user_2);

                        refreshHolder(user_1.holder, false);
                        refreshHolder(user_2.holder, false);
                    }
                });
                break;
            case MENU_SNAPSHOT:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        nrtc.takeSnapshot(uu);
                    }
                });
                break;
            case MENU_RECORD:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        User user = findUser(uu);

                        if(nrtc.isLocalRecording()) {
                            nrtc.stopLocalRecording();
                        } else {
                            nrtc.startLocalRecording();
                        }
                        user.isRecording = nrtc.isLocalRecording();

                        refreshHolder(user.holder, false);
                    }
                });
                break;
            case MENU_ROLE:
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        User user = findUser(uu);

                        if(nrtc.getRole() == NRtcConstants.UserRole.AUDIENCE) {
                            nrtc.setRole(NRtcConstants.UserRole.NORMAL);
                        } else {
                            nrtc.setRole(NRtcConstants.UserRole.AUDIENCE);
                        }

                        user.role = nrtc.getRole();

                        refreshHolder(user.holder, true);
                    }
                });
            default:
                break;

        }
        actionUser = null;
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leave_channel:
                leave();
                break;
            case R.id.switch_speaker: {
                nrtc.setSpeaker(!nrtc.speakerEnabled());
                speaker.setText(nrtc.speakerEnabled() ? "关闭扬声器" : "打开扬声器");
            }
                break;
        }
    }


    private class User {

        //用户ID
        public long uid;

        //本地操作语音静音
        public boolean audioMuted = false;

        //本地操作视频
        public boolean videoMuted = false;

        //远端操作视频
        public boolean videoRemoteMuted = false;

        //远端操作语音
        public boolean audioRemoteMuted = false;

        //用户当前处于的音视频模式
        public int rtcMode = NRtcConstants.RtcMode.AUDIO;

        //是否允许切换摄像头
        public boolean canSwitchCamera = false;

        //画布
        public SurfaceView render;

        //
        public PreviewHolder holder;

        //是否在录制
        public boolean isRecording = false;

        //视频画面帧率
        public int fps = 0;

        //是否正在说话，暂时不支持
        public boolean isSpeaking = false;

        //选中
        public boolean selected = false;

        //角色类型
        public int role;

    }


    private void setScreenOnFlag() {
        final int keepScreenOnFlag =
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        Window w = getWindow();
        w.getAttributes().flags |= keepScreenOnFlag;
        w.addFlags(keepScreenOnFlag);
    }


    //用户传递参数
    private long uid;
    private String channelName;
    private boolean videoEnabled;
    private String token;
    private boolean multi;
    private boolean audience;


    //全局设置参数
    private boolean videoAutoCrop;
    private boolean videoAutoRotate;
    private int videoQuality;
    private boolean serverRecordAudio;
    private boolean serverRecordVideo;
    private boolean defaultFrontCamera;
    private boolean autoCallProximity;



    private PreviewHolder[] holders = new PreviewHolder[9];
    private Set<User> users = new HashSet<>();


    private ScreenLocker locker;
    private TextView log;
    private Button leave;
    private Button speaker;

    private NRtc nrtc;


    private boolean isCallEstablished = false;


    private Handler uiHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenOnFlag();
        setContentView(R.layout.multi_chat_layout);

        configFromIntent(getIntent());
        configFromPreference(PreferenceManager.getDefaultSharedPreferences(this));

        componentFromLayout();


        NRtcOptional optional = new NRtcOptional();
        optional.setVideoQuality(videoQuality).setDefaultFrontCamera(defaultFrontCamera)
                .setScreenLocker(locker).enableCallProximity(autoCallProximity)
                .enableServerRecordAudio(serverRecordAudio).enableServerRecordVideo(serverRecordVideo)
                .enableVideoCrop(videoAutoCrop)
                .enableVideoRotate(videoAutoRotate);

        nrtc = NRtc.create(this, this, optional);

        nrtc.joinChannel(Config.APP_KEY, token, channelName, uid,
                videoEnabled ? NRtcConstants.RtcMode.VIDEO : NRtcConstants.RtcMode.AUDIO, multi
            ,audience ? NRtcConstants.UserRole.AUDIENCE : NRtcConstants.UserRole.NORMAL);


        User user = new User();
        user.uid = uid;
        user.audioMuted = false;
        user.videoMuted = false;
        user.render = null;
        user.rtcMode = videoEnabled ? NRtcConstants.RtcMode.VIDEO : NRtcConstants.RtcMode.AUDIO;
        user.role = audience ? NRtcConstants.UserRole.AUDIENCE : NRtcConstants.UserRole.NORMAL;

        user.holder = findEmptyHolder();
        user.holder.container.setTag(user);

        users.add(user);

        refreshHolder(user.holder, false);

        if(videoEnabled) {
            speaker.setText("关闭扬声器");
        } else {
            speaker.setText("打开扬声器");
        }

    }

    @Override
    public void onBackPressed() {
        leave();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        nrtc.dispose();
    }

    private void refreshHolder(PreviewHolder holder, boolean updateRender) {

        if(holder == null) return;

        User user = (User) holder.container.getTag();

        if(user == null) {
            holder.uid.setText("");
            holder.render.removeAllViews();
            holder.container.setVisibility(View.INVISIBLE);
            return;
        } else {
            holder.container.setVisibility(View.VISIBLE);
        }

        //处理选中
        String uid = String.valueOf(user.uid);
        if(user.selected) {
            uid  = "✓" + uid;
        }
        if(user.role == NRtcConstants.UserRole.AUDIENCE) {
            uid = "✗" + uid;
        }
        holder.uid.setText(uid);


        //以下将是各种运行时状态
        if(!isCallEstablished) return;

        //帧率设置
        holder.fps.setText(String.valueOf(user.fps));
        //UI上的状态表示是否有语音视频，和mute相反
        //本地audio mute
        holder.audioMute.setText(user.audioMuted ? "✗" : "✓");
        //本地video mute
        holder.videoMute.setText(user.videoMuted ? "✗" : "✓");
        //远端audio mute
        holder.audioRemoteMute.setText(user.audioRemoteMuted ? "✗" : "✓");
        //远端video mute
        holder.videoRemoteMute.setText(user.videoRemoteMuted ? "✗" : "✓");
        //是否录制
        holder.isRecording.setText(user.isRecording ? "✓" : "✗");
        //是否Speaking
        //TODO:

        if(updateRender) {
            if(videoEnabled && user.role == NRtcConstants.UserRole.NORMAL) {
                if(nrtc.videoStreamMuted(user.uid)) {
                    holder.render.removeAllViews();
                } else {
                    user.render = nrtc.getSurfaceRender(user.uid);
                    if(user.render != null) {
                        holder.render.removeAllViews();
                        holder.render.addView(user.render);
                    } else {
                        holder.render.removeAllViews();
                    }
                }

            } else {
                holder.render.removeAllViews();
            }
        }

    }

    private class PreviewHolder {
        public CardView container = null;
        public ResizingTextTextView uid = null;
        public LinearLayout render = null;

        public ResizingTextTextView fps = null;
        public ResizingTextTextView videoMute = null;
        public ResizingTextTextView audioMute = null;
        public ResizingTextTextView audioRemoteMute = null;
        public ResizingTextTextView videoRemoteMute = null;
        public ResizingTextTextView isSpeaking = null;
        public ResizingTextTextView isRecording = null;
    }

    public User findUser(long uid) {
        for(User u : users) {
            if(u.uid == uid) {
                return u;
            }
        }
        return null;
    }

    public PreviewHolder findEmptyHolder() {
        for(PreviewHolder holder : holders) {
            if(holder.container.getTag() == null) {
                return holder;
            }
        }
        return null;
    }


    private void log(int level, String message) {

        log.append("\n");

        SpannableString msg = new SpannableString(message);

        int color = getResources().getColor(R.color.black);

        switch (level) {
            case Log.DEBUG:
                color = getResources().getColor(R.color.lime);
                Log.d(TAG, message);
                break;
            case Log.INFO:
                color = getResources().getColor(R.color.light_green);
                Log.i(TAG, message);
                break;
            case Log.WARN:
                color = getResources().getColor(R.color.orange);
                Log.w(TAG, message);
                break;
            case Log.ERROR:
                color = getResources().getColor(R.color.red);
                Log.e(TAG, message);
                break;
        }

        msg.setSpan(new ForegroundColorSpan(color),
                0, msg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        log.append(msg, 0, msg.length());
    }


    private void leave() {
        nrtc.leaveChannel();
    }

    private PreviewHolder inflateRender(CardView parent) {
        PreviewHolder holder = new PreviewHolder();
        holder.container = parent;
        registerForContextMenu(parent);
        holder.uid = (ResizingTextTextView) holder.container.findViewById(R.id.uid);
        holder.render = (LinearLayout) holder.container.findViewById(R.id.render);

        holder.fps = (ResizingTextTextView) holder.container.findViewById(R.id.fps);
        holder.isRecording = (ResizingTextTextView) holder.container.findViewById(R.id.recording);
        holder.isSpeaking = (ResizingTextTextView) holder.container.findViewById(R.id.audio_speaking);
        holder.audioMute = (ResizingTextTextView) holder.container.findViewById(R.id.audio_local_muted);
        holder.videoMute = (ResizingTextTextView) holder.container.findViewById(R.id.video_local_muted);
        holder.audioRemoteMute = (ResizingTextTextView) holder.container.findViewById(R.id.audio_remote_muted);
        holder.videoRemoteMute = (ResizingTextTextView) holder.container.findViewById(R.id.video_remote_muted);

        return holder;
    }

    private void componentFromLayout() {

        holders[0] = inflateRender((CardView) findViewById(R.id.preview_holder_0));
        holders[1] = inflateRender((CardView) findViewById(R.id.preview_holder_1));
        holders[2] = inflateRender((CardView) findViewById(R.id.preview_holder_2));
        holders[3] = inflateRender((CardView) findViewById(R.id.preview_holder_3));
        holders[4] = inflateRender((CardView) findViewById(R.id.preview_holder_4));
        holders[5] = inflateRender((CardView) findViewById(R.id.preview_holder_5));
        holders[6] = inflateRender((CardView) findViewById(R.id.preview_holder_6));
        holders[7] = inflateRender((CardView) findViewById(R.id.preview_holder_7));
        holders[8] = inflateRender((CardView) findViewById(R.id.preview_holder_8));

        locker = (ScreenLocker) findViewById(R.id.call_locker);
        log = (TextView) findViewById(R.id.log);
        log.setMovementMethod(ScrollingMovementMethod.getInstance());
        leave = (Button) findViewById(R.id.leave_channel);
        speaker = (Button) findViewById(R.id.switch_speaker);
        leave.setOnClickListener(this);
        speaker.setOnClickListener(this);

    }

    private void configFromIntent(Intent intent) {
        uid = intent.getLongExtra("uid", 100);
        channelName = intent.getStringExtra("channel");
        videoEnabled = intent.getBooleanExtra("videoEnabled", false);
        token = intent.getStringExtra("token");
        multi = intent.getBooleanExtra("multi", false);
        audience = intent.getBooleanExtra("audience", true);
    }


    private void configFromPreference(SharedPreferences preferences) {
        videoAutoCrop = preferences.getBoolean(getString(R.string.setting_vie_crop_key), true);
        videoAutoRotate = preferences.getBoolean(getString(R.string.setting_vie_rotation_key), true);
        videoQuality = Integer.parseInt(preferences.getString(getString(R.string.setting_vie_quality_key), 0 + ""));
        serverRecordAudio = preferences.getBoolean(getString(R.string.setting_other_server_record_audio_key), false);
        serverRecordVideo = preferences.getBoolean(getString(R.string.setting_other_server_record_video_key), false);
        defaultFrontCamera = preferences.getBoolean(getString(R.string.setting_vie_default_front_camera_key), true);
        autoCallProximity = preferences.getBoolean(getString(R.string.setting_voe_call_proximity_key), true);
    }



    @Override
    public void onJoinedChannel(long channelId, String serverRecordUrl, String serverRecordName) {
        log(Log.INFO, "onJoinedChannel->" + channelId);
        if(serverRecordAudio || serverRecordVideo) {
            log(Log.INFO, "record-> url:" + serverRecordUrl + ", name:" + serverRecordName);
        }
    }

    @Override
    public void onLeftChannel(SessionStats stats) {
        log(Log.INFO, "onLeaveChannel. " + "RX->" + stats.trafficStatRX + ", TX->" + stats.trafficStatTX);

        finish();
    }

    @Override
    public void onError(int event, int code) {
        log(Log.ERROR, "error->" + event + "#" + code);
        Toast.makeText(this, "error->"+ event + "#" + code, Toast.LENGTH_SHORT).show();
        leave();
    }

    @Override
    public void onDeviceEvent(long channel, int event, String desc) {
        log(Log.WARN, "device->" + channel + "#(" + event + "," + desc + ")");
    }

    @Override
    public void onCallEstablished() {

        log(Log.INFO, "onCallEstablished");

        isCallEstablished = true;

        User user = findUser(uid);


        if (videoEnabled) {

            if (nrtc.hasMultipleCameras()) {
                user.canSwitchCamera = true;
            }

            user.render = nrtc.getSurfaceRender(uid);

        }

        refreshHolder(user.holder, true);
    }

    @Override
    public void onUserJoined(long uid) {

        log(Log.INFO, "onUserJoined ->" + uid);


        if(findUser(uid) != null) {
            return;
        }

        User user = new User();
        user.uid = uid;

        PreviewHolder holder = findEmptyHolder();
        if(holder != null) {
            holder.container.setTag(user);
        }

        user.holder = holder;
        user.render = nrtc.getSurfaceRender(uid);

        users.add(user);

        refreshHolder(user.holder, true);
    }

    @Override
    public void onUserLeft(long uid, int event) {
        log(Log.ERROR, "user left->" + uid + "#" + event);
        User user = findUser(uid);
        if(user != null) {
            PreviewHolder holder = user.holder;
            users.remove(user);
            if(holder != null) {
                holder.container.setTag(null);
            }

            refreshHolder(holder, false);
        }
    }

    @Override
    public void onNetworkQuality(long user, int quality) {
        log(Log.WARN, "net quality->" + user + "#" + quality);
    }

    @Override
    public void onUserMuteAudio(long uid, boolean muted) {
        log(Log.WARN, "audio muted-> " + uid + "#" + muted);
        User user = findUser(uid);

        if(user != null) {
            user.audioRemoteMuted = muted;

            refreshHolder(user.holder, false);
        }
    }

    @Override
    public void onUserMuteVideo(long uid, boolean muted) {
        log(Log.WARN, "video muted->" + uid + "#" + muted);
        User user = findUser(uid);

        if(user != null) {
            user.videoRemoteMuted = muted;

            refreshHolder(user.holder, false);
        }
    }

    @Override
    public void onUserChangeMode(long uid, int mode) {
        log(Log.WARN, "mode change->" + uid + "#" + mode);
    }

    @Override
    public void onConnectionTypeChanged(int current, int old) {
        log(Log.WARN, "connection change : " + old + "->" + current);
    }

    @Override
    public void onUserRecordStatusChange(long uid, boolean on) {
        log(Log.WARN, "record change->" + uid + "->" + on);

        User user = findUser(uid);
        if(user != null) {
            user.isRecording = on;

            refreshHolder(user.holder, false);
        }

    }

    @Override
    public void onRecordEnd(String[] files, int event) {
        log(Log.WARN, "record end ->" + event);
    }

    @Override
    public void onFirstVideoFrameAvailable(long channel) {
        log(Log.WARN, "first video available :" + channel);
    }

    @Override
    public void onVideoFpsReported(long channel, int fps) {
        User user = findUser(channel);
        if(user != null && user.holder != null) {
            user.fps = fps;
            refreshHolder(user.holder, false);
        }
    }

    @Override
    public void onTakeSnapshotResult(long channel, boolean success, String file) {
        log(Log.DEBUG, "snapshot->" + channel + "#" + success);
    }

}
