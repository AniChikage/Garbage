using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Drawing2D;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NIMDemo.MainForm
{
    public partial class VideoChatForm : Form
    {
        private readonly Queue<Stream> _receivedStreamQueue = null;
        private readonly Queue<Stream> _sendedStreamQueue = null;
        private Graphics _peerRegionGraphics;
        private Graphics _mineRegionGraphics;
        private MultimediaHandler _multimediaHandler;
        const int RenderInterval = 60;
        const int MaxFrameCount = 3;
        public VideoChatForm()
        {
            InitializeComponent();
        }

        public VideoChatForm(MultimediaHandler mh)
            : this()
        {
            _multimediaHandler = mh;
            this.Load += VideoChatForm_Load;
            this.FormClosed += VideoChatForm_FormClosed;
        }

        private void VideoChatForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (_multimediaHandler != null)
            {
                _multimediaHandler.ReceiveVideoFrameHandler -= OnReceivedVideoFrame;
                _multimediaHandler.CapturedVideoFrameHandler -= OnCapturedVideoFrame;
            }
            NIM.VChatAPI.End();
            _peerRegionGraphics.Dispose();
            _mineRegionGraphics.Dispose();
            _multimediaHandler = null;
        }

        private void VideoChatForm_Load(object sender, EventArgs e)
        {
            _peerRegionGraphics = peerPicBox.CreateGraphics();
            _mineRegionGraphics = minePicBox.CreateGraphics();
            if (_multimediaHandler != null)
            {
                _multimediaHandler.ReceiveVideoFrameHandler += OnReceivedVideoFrame;
                _multimediaHandler.CapturedVideoFrameHandler += OnCapturedVideoFrame;
            }
        }

        private void OnCapturedVideoFrame(object sender, VideoEventAgrs e)
        {
            ShowVideoFrame(_mineRegionGraphics, minePicBox.Width, minePicBox.Height, e.Frame);
        }

        private void OnReceivedVideoFrame(object sender,VideoEventAgrs args)
        {
            ShowVideoFrame(_peerRegionGraphics, peerPicBox.Width, peerPicBox.Height, args.Frame);
        }

        void ShowVideoFrame(Graphics graphics,int w,int h, VideoFrame frame)
        {
            graphics.CompositingMode = CompositingMode.SourceCopy;
            graphics.CompositingQuality = CompositingQuality.Default;
            var stream = frame.GetBmpStream();
            Bitmap img = new Bitmap(stream);
            //等比例缩放
            float sa = (float)w / frame.Width;
            float sb = (float)h / frame.Height;
            var scale = Math.Min(sa, sb);
            var newWidth = frame.Width*scale;
            var newHeight = frame.Height*scale;
            stream.Dispose();
            graphics.DrawImage(img, new Rectangle((int)(w-newWidth)/2, (int)(h-newHeight)/2, (int)newWidth,(int)newHeight), 
                new Rectangle(0, 0, img.Width, img.Height), GraphicsUnit.Pixel);
            img.Dispose();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            this.Close();
        }

    }
}
