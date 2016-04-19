namespace NIMDemo.MainForm
{
    partial class VideoChatForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.peerPicBox = new System.Windows.Forms.PictureBox();
            this.button1 = new System.Windows.Forms.Button();
            this.minePicBox = new System.Windows.Forms.PictureBox();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.peerPicBox)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.minePicBox)).BeginInit();
            this.SuspendLayout();
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 0);
            this.splitContainer1.Name = "splitContainer1";
            this.splitContainer1.Orientation = System.Windows.Forms.Orientation.Horizontal;
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.peerPicBox);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.button1);
            this.splitContainer1.Panel2.Controls.Add(this.minePicBox);
            this.splitContainer1.Size = new System.Drawing.Size(383, 499);
            this.splitContainer1.SplitterDistance = 319;
            this.splitContainer1.TabIndex = 0;
            // 
            // peerPicBox
            // 
            this.peerPicBox.Dock = System.Windows.Forms.DockStyle.Fill;
            this.peerPicBox.Location = new System.Drawing.Point(0, 0);
            this.peerPicBox.Name = "peerPicBox";
            this.peerPicBox.Size = new System.Drawing.Size(383, 319);
            this.peerPicBox.TabIndex = 0;
            this.peerPicBox.TabStop = false;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(12, 120);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 1;
            this.button1.Text = "挂断";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // minePicBox
            // 
            this.minePicBox.Location = new System.Drawing.Point(161, 15);
            this.minePicBox.Name = "minePicBox";
            this.minePicBox.Size = new System.Drawing.Size(201, 135);
            this.minePicBox.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.minePicBox.TabIndex = 0;
            this.minePicBox.TabStop = false;
            // 
            // VideoChatForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(383, 499);
            this.Controls.Add(this.splitContainer1);
            this.Name = "VideoChatForm";
            this.Text = "VideoChatForm";
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.peerPicBox)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.minePicBox)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.PictureBox peerPicBox;
        private System.Windows.Forms.PictureBox minePicBox;
        private System.Windows.Forms.Button button1;
    }
}