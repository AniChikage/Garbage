package com.netease.nrtc.demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.netease.nrtc.sdk.NRtc;
import com.netease.nrtc.sdk.NRtcCallback;
import com.netease.nrtc.sdk.NRtcConstants;
import com.netease.nrtc.sdk.NRtcOptional;
import com.netease.nrtc.sdk.SessionStats;
import com.netease.nrtc.sdk.toolbox.ScreenLocker;

/**
 * Created by liuqijun on 3/30/16.
 */
public class ChatActivity extends AppCompatActivity implements NRtcCallback, View.OnClickListener  {

    private static final String TAG = "Chat";

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
        intent.setClass(context, ChatActivity.class);
        context.startActivity(intent);

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


    //layout
    private LinearLayout smallPreview;
    private LinearLayout largePreview;

    private ProgressView netState;
    private ScreenLocker locker;

    private Button leave;
    private Button muteAudio;
    private Button muteVideo;
    private Button switchCamera;
    private Button switchRender;
    private Button switchMode;
    private Button localRecord;
    private Button speaker;


    private NRtc nrtc;



    private long remoteId;
    private SurfaceView remoteRender;


    private SurfaceView selfRender;


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenOnFlag();
        setContentView(R.layout.chat_layout);

        configFromIntent(getIntent());
        configFromPreference(PreferenceManager.getDefaultSharedPreferences(this));


        componentFromLayout();

        leave.setEnabled(true);
        muteAudio.setEnabled(true);
        if(videoEnabled)
            muteVideo.setEnabled(true);
        else
            muteVideo.setEnabled(false);
        switchCamera.setEnabled(false);
        switchRender.setEnabled(false);
        switchMode.setEnabled(false);
        localRecord.setEnabled(false);
        if(videoEnabled)
            speaker.setEnabled(false);
        else
            speaker.setEnabled(true);


        NRtcOptional optional = new NRtcOptional();
        optional.setVideoQuality(videoQuality).setDefaultFrontCamera(defaultFrontCamera)
                .setScreenLocker(locker).enableCallProximity(autoCallProximity)
                .enableServerRecordAudio(serverRecordAudio).enableServerRecordVideo(serverRecordVideo)
                .enableVideoCrop(videoAutoCrop)
                .enableVideoRotate(videoAutoRotate);

        nrtc = NRtc.create(this, this, optional);



        nrtc.joinChannel(Config.APP_KEY, token, channelName, uid,
                videoEnabled ? NRtcConstants.RtcMode.VIDEO : NRtcConstants.RtcMode.AUDIO, multi,
                audience ? NRtcConstants.UserRole.AUDIENCE : NRtcConstants.UserRole.NORMAL);

    }


    private void configFromIntent(Intent intent) {
        uid = intent.getLongExtra("uid", 100);
        channelName = intent.getStringExtra("channel");
        videoEnabled = intent.getBooleanExtra("videoEnabled", false);
        token = intent.getStringExtra("token");
        multi = intent.getBooleanExtra("multi", false);
        audience = intent.getBooleanExtra("audience", false);
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


    private void componentFromLayout() {
        smallPreview = (LinearLayout) findViewById(R.id.small_size_preview);
        largePreview = (LinearLayout) findViewById(R.id.large_size_preview);
        netState = (ProgressView) findViewById(R.id.net_state);
        leave = (Button) findViewById(R.id.leave);
        muteAudio = (Button) findViewById(R.id.mute_audio_btn);
        muteVideo = (Button) findViewById(R.id.mute_video_btn);
        switchCamera = (Button) findViewById(R.id.switch_camera_btn);
        switchRender = (Button) findViewById(R.id.switch_render_btn);
        switchMode = (Button) findViewById(R.id.switch_mode_btn);
        speaker = (Button) findViewById(R.id.speaker_btn);
        localRecord = (Button) findViewById(R.id.recorder_btn);

        leave.setOnClickListener(this);
        muteAudio.setOnClickListener(this);
        muteVideo.setOnClickListener(this);
        switchCamera.setOnClickListener(this);
        switchRender.setOnClickListener(this);
        switchMode.setOnClickListener(this);
        speaker.setOnClickListener(this);
        localRecord.setOnClickListener(this);
    }


    private void log(int level, String message) {

        switch (level) {
            case Log.DEBUG:
                Log.d(TAG, message);
                break;
            case Log.INFO:
                Log.i(TAG, message);
                break;
            case Log.WARN:
                Log.w(TAG, message);
                break;
            case Log.ERROR:
                Log.e(TAG, message);
                break;
        }

    }


    private void leave() {
        nrtc.leaveChannel();
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

        Toast.makeText(this, event + "#" + code, Toast.LENGTH_SHORT).show();

        leave();

    }

    @Override
    public void onDeviceEvent(long channel, int event, String desc) {
        log(Log.WARN, "device->" + channel + "#(" + event + "," + desc + ")");
    }

    @Override
    public void onCallEstablished() {
        log(Log.INFO, "onCallEstablished");

        selfRender = nrtc.getSurfaceRender(uid);

        if (videoEnabled) {

            if (nrtc.hasMultipleCameras()) {
                switchCamera.setEnabled(true);
            }

            smallPreview.removeAllViews();
            smallPreview.addView(selfRender);
            selfRender.setZOrderMediaOverlay(true);
            selfRender.setZOrderOnTop(true);
            selfRender.setTag(smallPreview);

        }

        localRecord.setEnabled(true);
        switchMode.setEnabled(true);

    }

    @Override
    public void onUserJoined(long uid) {

        if(remoteId == uid) return;


        remoteRender = nrtc.getSurfaceRender(uid);
        remoteId = uid;

        if(videoEnabled) {
            largePreview.removeAllViews();
            largePreview.addView(remoteRender);
            remoteRender.setZOrderOnTop(false);
            remoteRender.setZOrderMediaOverlay(false);
            remoteRender.setTag(largePreview);

            switchRender.setEnabled(true);
        }
    }

    @Override
    public void onUserLeft(long uid, int event) {
        remoteId = 0;

        switchRender.setEnabled(false);
        LinearLayout view = (LinearLayout) remoteRender.getTag();
        if(view != null)
            view.removeAllViews();

    }

    @Override
    public void onNetworkQuality(long user, int quality) {
        netState.setProgress(5 - quality);
    }

    @Override
    public void onUserMuteAudio(long uid, boolean muted) {
        log(Log.WARN, "audio muted-> " + uid + "#" + muted);
    }

    @Override
    public void onUserMuteVideo(long uid, boolean muted) {
        log(Log.WARN, "video muted->" + uid + "#" + muted);
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
        log(Log.INFO, "fps->" + channel + "#" + fps);
    }

    @Override
    public void onTakeSnapshotResult(long channel, boolean success, String file) {
        log(Log.DEBUG, "snapshot->" + channel + "#" + success);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.leave: {
                leave();
            }
                break;
            case R.id.mute_audio_btn: {
                nrtc.muteAudioStream(uid, !nrtc.audioStreamMuted(uid));

                muteAudio.setText(nrtc.audioStreamMuted(uid) ? "打开语音" : "关闭语音");
            }
                break;
            case R.id.mute_video_btn: {

                nrtc.muteVideoStream(uid, !nrtc.videoStreamMuted(uid));

                muteVideo.setText(nrtc.videoStreamMuted(uid) ? "打开视频" : "关闭视频");

            }
                break;
            case R.id.switch_camera_btn: {

                nrtc.switchCamera();

            }
                break;
            case R.id.switch_render_btn: {
                nrtc.switchRender(uid, remoteId);
            }
                break;
            case R.id.switch_mode_btn: {

                switchMode(nrtc.getChannelMode() == NRtcConstants.RtcMode.VIDEO ?
                        NRtcConstants.RtcMode.AUDIO : NRtcConstants.RtcMode.VIDEO);

            }
                break;
            case R.id.speaker_btn: {

                nrtc.setSpeaker(!nrtc.speakerEnabled());

                speaker.setText(nrtc.speakerEnabled() ? "关闭扬声器" : "打开扬声器");

            }
                break;
            case R.id.recorder_btn: {

                if(!nrtc.isLocalRecording()) {
                    nrtc.startLocalRecording();
                } else {
                    nrtc.stopLocalRecording();
                }

                localRecord.setText(nrtc.isLocalRecording() ? "关闭录制" : "打开录制");

            }
                break;
        }


    }

    private void switchMode(int mode) {

        if(nrtc.getChannelMode() == mode) return;

        nrtc.setChannelMode(mode);

        if(nrtc.getChannelMode() == NRtcConstants.RtcMode.VIDEO) {

            smallPreview.removeAllViews();
            smallPreview.addView(selfRender);
            selfRender.setTag(smallPreview);

            largePreview.removeAllViews();
            largePreview.addView(remoteRender);
            remoteRender.setTag(largePreview);

            muteAudio.setEnabled(true);
            muteVideo.setEnabled(true);
            if(nrtc.hasMultipleCameras()) {
                switchCamera.setEnabled(true);
            }
            switchRender.setEnabled(true);
            switchMode.setEnabled(true);
            localRecord.setEnabled(true);
            speaker.setEnabled(false);

        } else {
            smallPreview.removeAllViews();
            largePreview.removeAllViews();
            selfRender.setTag(null);
            remoteRender.setTag(null);

            muteAudio.setEnabled(true);
            muteVideo.setEnabled(false);
            switchCamera.setEnabled(false);
            switchRender.setEnabled(false);
            switchMode.setEnabled(true);
            localRecord.setEnabled(true);
            speaker.setEnabled(true);

        }
    }

}
