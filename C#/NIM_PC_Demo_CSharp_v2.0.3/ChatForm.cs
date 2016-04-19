using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using NIM;

namespace NIMDemo
{
    public partial class ChatForm : Form
    {
        private string _peerID = null;
        private NIM.Session.NIMSessionType _sessionType;
        private bool _fileDroped = false;

        private ChatForm()
        {
            InitializeComponent();
            NIM.TalkAPI.OnReceiveMessageHandler += ReceiveMessageHandler;
            NIM.TalkAPI.OnSendMessageCompleted += SendMessageResultHandler;
            this.FormClosed += new FormClosedEventHandler(ChatForm_FormClosed);
            this.textBox1.DragEnter += TextBox1_DragEnter;
            this.textBox1.DragDrop += TextBox1_DragDrop;
        }

        private void TextBox1_DragDrop(object sender, DragEventArgs e)
        {
            string path = ((System.Array) e.Data.GetData(DataFormats.FileDrop)).GetValue(0).ToString();
            textBox1.Text = path;
            _fileDroped = true;
        }

        private void TextBox1_DragEnter(object sender, DragEventArgs e)
        {
            if (e.Data.GetDataPresent(DataFormats.FileDrop))
                e.Effect = DragDropEffects.Link;
            else
                e.Effect = DragDropEffects.None;
        }

        public ChatForm(string id,NIM.Session.NIMSessionType st = NIM.Session.NIMSessionType.kNIMSessionTypeP2P)
            : this()
        {
            _peerID = id;
            _sessionType = st;
            base.Text = string.Format("与 {0} 聊天中", _peerID);
            if (st != NIM.Session.NIMSessionType.kNIMSessionTypeP2P)
            {
                testMediaBtn.Visible = false;
                testRtsBtn.Visible = false;
            }
        }

        void ChatForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            NIM.TalkAPI.OnReceiveMessageHandler -= ReceiveMessageHandler;
            NIM.TalkAPI.OnSendMessageCompleted -= SendMessageResultHandler;
        }

        private void SendMessageToFriend(object sender, EventArgs e)
        {
            string message = this.textBox1.Text;
            if (_fileDroped)
            {
                _fileDroped = false;
                SendFile(message);
            }
            else
            {
                NIM.NIMTextMessage textMsg = new NIM.NIMTextMessage();
                textMsg.ReceiverID = _peerID;
                textMsg.SessionType = _sessionType;
                textMsg.TextContent = message;
                NIM.TalkAPI.SendMessage(textMsg);
            }
            this.listBox1.Items.Add(message);
            this.textBox1.Text = string.Empty;
        }

        void SendFile(string path)
        {
            string fileName = System.IO.Path.GetFileNameWithoutExtension(path);
            string extension = System.IO.Path.GetExtension(path);

            if (IsImageFile(path))
            {
                NIM.NIMImageMessage imageMsg = new NIMImageMessage();
                imageMsg.LocalFilePath = path;
                imageMsg.ImageAttachment = new NIMImageAttachment();
                imageMsg.ImageAttachment.DisplayName = fileName;
                imageMsg.ImageAttachment.FileExtension = extension;
                imageMsg.ReceiverID = _peerID;
                imageMsg.SessionType = _sessionType;
                using (var i = Image.FromFile(path))
                {
                    imageMsg.ImageAttachment.Height = i.Height;
                    imageMsg.ImageAttachment.Width = i.Width;
                }
                TalkAPI.SendMessage(imageMsg);
            }
            else
            {
                NIM.NIMFileMessage fileMsg = new NIMFileMessage();
                fileMsg.FileAttachment = new NIMMessageAttachment();
                fileMsg.LocalFilePath = path;
                fileMsg.FileAttachment = new NIMMessageAttachment();
                fileMsg.FileAttachment.DisplayName = fileName;
                fileMsg.FileAttachment.FileExtension = extension;
                fileMsg.ReceiverID = _peerID;
                fileMsg.SessionType = _sessionType;
                NIM.TalkAPI.SendMessage(fileMsg);
            }
        }

        bool IsImageFile(string path)
        {
            int[] imageBytes = {0x8950, 0xFFD8, 0x4D42};
            using (FileStream fs = new FileStream(path, FileMode.Open))
            {
                var b1 = fs.ReadByte();
                var b2 = fs.ReadByte();
                var r = (b1 << 8) + b2;
                return imageBytes.Contains(r);
            }
        }

        void SendMessageResultHandler(object sender, MessageArcEventArgs args)
        {
            if (args.ArcInfo.Response == ResponseCode.kNIMResSuccess)
                return;
            Action action = () =>
            {
                MessageBox.Show(args.Dump(),"发送失败");
            };
            this.Invoke(action);
        }

        void ReceiveMessageHandler(object sender, NIM.NIMReceiveMessageEventArgs args)
        {
            if (args.Message != null && args.Message.MessageContent.MessageType == NIM.NIMMessageType.kNIMMessageTypeText)
            {
                Action action = () =>
                {
                    var m = args.Message.MessageContent as NIM.NIMTextMessage;
                    this.listBox1.Items.Add(new string(' ', 50) + m.TextContent);
                };
                this.Invoke(action);
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            System.Threading.ThreadPool.QueueUserWorkItem((obj) =>
            {
                VideoTest();
            });
        }

        public void VideoTest()
        {
            NIMVChatInfo info = new NIMVChatInfo();
            info.Uids = new System.Collections.Generic.List<string>();
            info.Uids.Add(_peerID);
            VChatAPI.Start(NIMVideoChatMode.kNIMVideoChatModeVideo, info);//邀请test_id进行语音通话
        }

        private void QueryMsglogBtn_Click(object sender, EventArgs e)
        {
            //var f = new InfomationForm();
            //f.Load += (s, ex) =>
            //{
            //    NIM.Messagelog.MessagelogAPI.QueryMsglogOnline(_peerID, 
            //        _sessionType, 100, 0, 0, 0, false, false,
            //        (a, b, c, d) =>
            //        {
            //            var x = d.Dump();
            //            f.ShowMessage(x);
            //        });
            //};
            //f.Show();
            MessageLogForm form = new MessageLogForm();
            form.Show();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            System.Threading.ThreadPool.QueueUserWorkItem((obj) =>
            {
                RtsTest();
            });
        }
        public void RtsTest()
        {
            NIMRts.RtsStartInfo info = new NIMRts.RtsStartInfo();
            info.ApnsText = "123";
            info.CustomInfo = "456";
            RtsAPI.Start((int)(NIMRts.NIMRtsChannelType.kNIMRtsChannelTypeTcp | NIMRts.NIMRtsChannelType.kNIMRtsChannelTypeVchat), _peerID, info,
                (code, session_id, channel_type, uid) =>
                {
                    if (code == 200)
                    {

                    }
                });
        }

        private void SysMsgBtn_Click(object sender, EventArgs e)
        {
            NIM.SysMessage.NIMSysMessageContent content = new NIM.SysMessage.NIMSysMessageContent();
            content.ClientMsgId = Guid.NewGuid().ToString();
            content.ReceiverId = _peerID;
            content.MsgType = NIM.SysMessage.NIMSysMsgType.kNIMSysMsgTypeCustomP2PMsg;
            content.Attachment = textBox1.Text;
            NIM.SysMessage.SysMsgAPI.SendCustomMessage(content);
        }

        private void SysmsgLogBtn_Click(object sender, EventArgs e)
        {
            NIM.SysMessage.SysMsgAPI.QueryMessage(100, 0, (r) =>
            {
                Action action = () =>
                {
                    InfomationForm f = new InfomationForm();
                    f.Load += (s, ex) =>
                    {
                        var x = r.Dump();
                        f.ShowMessage(x);
                    };
                    f.Show();
                };
                this.Invoke(action);
            });
        }
    }
}
