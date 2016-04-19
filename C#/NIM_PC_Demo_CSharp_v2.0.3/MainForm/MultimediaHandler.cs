using System;
using System.Collections.Generic;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Windows.Forms;

namespace NIMDemo
{
    public class MultimediaHandler
    {
        private NIM.NIMVChatSessionStatus _vchatHandlers;
        private readonly Form _ownerForm = null;
        public MultimediaHandler(Form owner)
        {
            _ownerForm = owner;
        }

        public void InitVChatInfo()
        {
            _vchatHandlers.onSessionStartRes = (channel_id, code) =>
            {
                if (code != 200)
                {
                    MessageBox.Show("发起音视频聊天失败");
                }
            };
            _vchatHandlers.onSessionInviteNotify = (channel_id, uid, mode, time) =>
            {
                Action a = () => {
                    string test = uid.ToString();
                    if (mode == (int)NIM.NIMVideoChatMode.kNIMVideoChatModeAudio)
                    {
                        test += "向你发起实时语音";
                    }
                    else
                    {
                        test += "向你发起视频聊天";
                    }
                    DialogResult ret = MessageBox.Show(test, "音视频邀请", MessageBoxButtons.YesNo);
                    NIM.NIMVChatInfo info = new NIM.NIMVChatInfo();
                    NIM.VChatAPI.CalleeAck(channel_id, ret == DialogResult.Yes, info);
                };
                _ownerForm.Invoke(a);
            };
            _vchatHandlers.onSessionCalleeAckRes = (channel_id, code) =>
            {
            };
            _vchatHandlers.onSessionCalleeAckNotify = (channel_id, code, mode, accept) =>
            {
                if (accept)
                {
                    DemoTrace.WriteLine("对方接听");
                }
                else
                {
                    Action a = () => { MessageBox.Show("对方拒绝接听"); };
                    _ownerForm.Invoke(a);
                }
            };
            _vchatHandlers.onSessionControlRes = (channel_id, code, type) =>
            {
            };
            _vchatHandlers.onSessionControlNotify = (channel_id, uid, type) =>
            {
            };
            _vchatHandlers.onSessionConnectNotify = (channel_id, code, record_addr, record_file) =>
            {
                if (code == 200)
                {
                    Action a = () =>
                    {
                        MainForm.VideoChatForm vform = new MainForm.VideoChatForm(this);
                        vform.Show();
                    };
                    _ownerForm.Invoke(a);
                    StartDevices();

                }
                else
                {
                    NIM.VChatAPI.End();
                }
            };
            _vchatHandlers.onSessionPeopleStatus = (channel_id, uid, status) =>
            {
            };
            _vchatHandlers.onSessionNetStatus = (channel_id, status) =>
            {
            };
            _vchatHandlers.onSessionHangupRes = (channel_id, code) =>
            {
                EndDevices();
            };
            _vchatHandlers.onSessionHangupNotify = (channel_id, code) =>
            {
                EndDevices();
                if (code == 200)
                    MessageBox.Show("已挂断");
            };
            _vchatHandlers.onSessionSyncAckNotify = null;
            //注册音视频会话交互回调
            NIM.VChatAPI.SetSessionStatusCb(_vchatHandlers);
            //注册音频接收数据回调
            NIM.DeviceAPI.SetAudioReceiveDataCb(AudioDataRecHandler);
            //注册视频接收数据回调
            NIM.DeviceAPI.SetVideoReceiveDataCb(VideoDataRecHandler);
            //注册视频采集数据回调
            NIM.DeviceAPI.SetVideoCaptureDataCb(VideoDataCaptureHandler);
        }
        void AudioDataRecHandler(UInt64 time, IntPtr data, UInt32 size, Int32 rate)
        {

        }

        public EventHandler<MainForm.VideoEventAgrs> ReceiveVideoFrameHandler;
        public EventHandler<MainForm.VideoEventAgrs> CapturedVideoFrameHandler;
        //捕获视频帧回调函数
        void VideoDataCaptureHandler(UInt64 time, IntPtr data, UInt32 size, UInt32 width, UInt32 height)
        {
            if (CapturedVideoFrameHandler != null)
            {
                MainForm.VideoFrame frame = new MainForm.VideoFrame(data, (int)width, (int)height, (int)size, (long)time);
                CapturedVideoFrameHandler(this, new MainForm.VideoEventAgrs(frame));
            }
        }

        //收到视频帧回调函数
        void VideoDataRecHandler(UInt64 time, IntPtr data, UInt32 size, UInt32 width, UInt32 height)
        {
            if (ReceiveVideoFrameHandler != null)
            {
                MainForm.VideoFrame frame = new MainForm.VideoFrame(data, (int) width, (int) height, (int) size, (long)time);
                ReceiveVideoFrameHandler(this, new MainForm.VideoEventAgrs(frame));
            }
        }

        Stream ParseVedioData(IntPtr data, uint size, uint width, uint height)
        {
            byte[] buffer = new byte[size];
            int offset = 0;
            while (offset < size)
            {
                var b = Marshal.ReadByte(data, offset);
                buffer[offset++] = b;
            }
            using (Bitmap resultBitmap = new Bitmap((int) width, (int) height, PixelFormat.Format32bppArgb))
            {
                MemoryStream curImageStream = new MemoryStream();

                resultBitmap.Save(curImageStream, System.Drawing.Imaging.ImageFormat.Bmp);

                byte[] tempData = new byte[4];

                //bmp format: https://en.wikipedia.org/wiki/BMP_file_format
                //读取数据开始位置，写入字节流
                curImageStream.Position = 10;

                curImageStream.Read(tempData, 0, 4);

                var dataOffset = BitConverter.ToInt32(tempData, 0);
                curImageStream.Position = dataOffset;
                curImageStream.Write(buffer, 0, (int)size);
                curImageStream.Flush();
                return curImageStream;
            }
        }

        void StartDevices()
        {
            NIM.DeviceAPI.StartDeviceResultHandler handle = (type, ret) =>
            {

            };
            NIM.DeviceAPI.StartDevice(NIM.NIMDeviceType.kNIMDeviceTypeAudioIn, "", 0, handle);//开启麦克风
            NIM.DeviceAPI.StartDevice(NIM.NIMDeviceType.kNIMDeviceTypeAudioOutChat, "", 0, handle);//开启扬声器播放对方语音
            NIM.DeviceAPI.StartDevice(NIM.NIMDeviceType.kNIMDeviceTypeVideo, "", 0, handle);//开启摄像头
        }
        public void EndDevices()
        {
            NIM.DeviceAPI.EndDevice(NIM.NIMDeviceType.kNIMDeviceTypeAudioIn);
            NIM.DeviceAPI.EndDevice(NIM.NIMDeviceType.kNIMDeviceTypeAudioOutChat);
            NIM.DeviceAPI.EndDevice(NIM.NIMDeviceType.kNIMDeviceTypeVideo);
        }
    }
}
