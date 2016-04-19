using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NIMDemo
{
    public partial class MsgInfoForm : Form
    {
        private MsgInfoForm()
        {
            InitializeComponent();
            viewAttachmentBtn.Enabled = false;
            downloadBtn.Enabled = false;
            this.Load += MsgInfoForm_Load;
        }

        private void MsgInfoForm_Load(object sender, EventArgs e)
        {
            var activeForm = Form.ActiveForm;
            if (activeForm == null)
            {
                this.StartPosition = FormStartPosition.CenterScreen;
                return;
            }
            this.StartPosition = FormStartPosition.Manual;
            this.Location = new Point(activeForm.Location.X + activeForm.Size.Width + 20, activeForm.Location.Y + 100);
        }

        private readonly NIM.NIMIMMessage _message = null;
        private NIM.NIMMessageAttachment _attachment = null;

        public MsgInfoForm(NIM.NIMIMMessage msg)
            :this()
        {
            _message = msg;
            InitFormContent();
        }

        void InitFormContent()
        {
            var imageMsg = _message as NIM.NIMImageMessage;
            if (imageMsg != null)
            {
                _attachment = imageMsg.ImageAttachment;
            }
            var fileMsg = _message as NIM.NIMFileMessage;
            if (fileMsg != null)
            {
                _attachment = fileMsg.FileAttachment;
            }
            richTextBox1.Text = _message.Dump();
            if (_attachment != null)
            {
                downloadBtn.Enabled = true;
            }
        }

        private void downloadBtn_Click(object sender, EventArgs e)
        {
            if (_attachment == null) return;
            viewAttachmentBtn.Enabled = false;
            NIM.Nos.NosAPI.DownloadMedia(_message, (a, b, c, d) =>
            {
                if (a == 200)
                {
                    Action<string> action = OnDownloadCompleted;
                    _message.LocalFilePath = b;
                    this.Invoke(action, b);
                }
            },
            (a, b) =>
            {
                
            });
        }

        void OnDownloadCompleted(string path)
        {
            if (_attachment is NIM.NIMImageAttachment)
                pictureBox1.Image = Image.FromFile(path);
            else
            {
                viewAttachmentBtn.Enabled = true;
            }
        }

        private void viewAttachmentBtn_Click(object sender, EventArgs e)
        {
            OpenFileDialog dialog = new OpenFileDialog();
            var path = _message.LocalFilePath;
            dialog.InitialDirectory = System.IO.Path.GetDirectoryName(path);
            dialog.FileName = path;
            dialog.ShowDialog();
        }
    }
}
