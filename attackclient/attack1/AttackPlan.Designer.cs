namespace attack1
{
    partial class AttackPlan
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AttackPlan));
            this.hitbound = new System.Windows.Forms.TextBox();
            this.result = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.tohit = new System.Windows.Forms.TextBox();
            this.countb = new System.Windows.Forms.TextBox();
            this.counta = new System.Windows.Forms.TextBox();
            this.missilenum = new System.Windows.Forms.TextBox();
            this.countd = new System.Windows.Forms.TextBox();
            this.countc = new System.Windows.Forms.TextBox();
            this.button1 = new System.Windows.Forms.Button();
            this.RdShow = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.button2 = new System.Windows.Forms.Button();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.button3 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // hitbound
            // 
            this.hitbound.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.hitbound.ForeColor = System.Drawing.Color.Thistle;
            this.hitbound.Location = new System.Drawing.Point(207, 278);
            this.hitbound.Name = "hitbound";
            this.hitbound.Size = new System.Drawing.Size(100, 33);
            this.hitbound.TabIndex = 0;
            this.hitbound.Text = "0.3";
            // 
            // result
            // 
            this.result.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.result.ForeColor = System.Drawing.Color.OliveDrab;
            this.result.Location = new System.Drawing.Point(617, 369);
            this.result.Multiline = true;
            this.result.Name = "result";
            this.result.Size = new System.Drawing.Size(226, 170);
            this.result.TabIndex = 7;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.ForeColor = System.Drawing.Color.Bisque;
            this.label1.Location = new System.Drawing.Point(67, 158);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(93, 26);
            this.label1.TabIndex = 8;
            this.label1.Text = "导弹数量:";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.BackColor = System.Drawing.Color.Transparent;
            this.label8.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label8.ForeColor = System.Drawing.Color.Bisque;
            this.label8.Location = new System.Drawing.Point(67, 220);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(93, 26);
            this.label8.TabIndex = 15;
            this.label8.Text = "命中概率:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.ForeColor = System.Drawing.Color.Bisque;
            this.label2.Location = new System.Drawing.Point(83, 281);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(74, 26);
            this.label2.TabIndex = 16;
            this.label2.Text = "损伤率:";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.BackColor = System.Drawing.Color.Transparent;
            this.label3.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label3.ForeColor = System.Drawing.Color.Bisque;
            this.label3.Location = new System.Drawing.Point(67, 338);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(107, 26);
            this.label3.TabIndex = 17;
            this.label3.Text = "A损伤占比:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.BackColor = System.Drawing.Color.Transparent;
            this.label4.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label4.ForeColor = System.Drawing.Color.Bisque;
            this.label4.Location = new System.Drawing.Point(67, 399);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(106, 26);
            this.label4.TabIndex = 18;
            this.label4.Text = "B损伤占比:";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.BackColor = System.Drawing.Color.Transparent;
            this.label5.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label5.ForeColor = System.Drawing.Color.Bisque;
            this.label5.Location = new System.Drawing.Point(65, 509);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(108, 26);
            this.label5.TabIndex = 19;
            this.label5.Text = "D损伤占比:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.BackColor = System.Drawing.Color.Transparent;
            this.label6.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label6.ForeColor = System.Drawing.Color.Bisque;
            this.label6.Location = new System.Drawing.Point(67, 454);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(106, 26);
            this.label6.TabIndex = 20;
            this.label6.Text = "C损伤占比:";
            // 
            // tohit
            // 
            this.tohit.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.tohit.ForeColor = System.Drawing.Color.Thistle;
            this.tohit.Location = new System.Drawing.Point(207, 217);
            this.tohit.Name = "tohit";
            this.tohit.Size = new System.Drawing.Size(100, 33);
            this.tohit.TabIndex = 21;
            this.tohit.Text = "0.5";
            // 
            // countb
            // 
            this.countb.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.countb.ForeColor = System.Drawing.Color.Thistle;
            this.countb.Location = new System.Drawing.Point(207, 392);
            this.countb.Name = "countb";
            this.countb.Size = new System.Drawing.Size(100, 33);
            this.countb.TabIndex = 22;
            this.countb.Text = "0.8";
            // 
            // counta
            // 
            this.counta.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.counta.ForeColor = System.Drawing.Color.Thistle;
            this.counta.Location = new System.Drawing.Point(207, 335);
            this.counta.Name = "counta";
            this.counta.Size = new System.Drawing.Size(100, 33);
            this.counta.TabIndex = 23;
            this.counta.Text = "0.8";
            // 
            // missilenum
            // 
            this.missilenum.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.missilenum.ForeColor = System.Drawing.Color.Thistle;
            this.missilenum.Location = new System.Drawing.Point(207, 155);
            this.missilenum.Name = "missilenum";
            this.missilenum.Size = new System.Drawing.Size(100, 33);
            this.missilenum.TabIndex = 24;
            this.missilenum.Text = "10";
            // 
            // countd
            // 
            this.countd.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.countd.ForeColor = System.Drawing.Color.Thistle;
            this.countd.Location = new System.Drawing.Point(207, 506);
            this.countd.Name = "countd";
            this.countd.Size = new System.Drawing.Size(100, 33);
            this.countd.TabIndex = 25;
            this.countd.Text = "0.2";
            // 
            // countc
            // 
            this.countc.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.countc.ForeColor = System.Drawing.Color.Thistle;
            this.countc.Location = new System.Drawing.Point(207, 451);
            this.countc.Name = "countc";
            this.countc.Size = new System.Drawing.Size(100, 33);
            this.countc.TabIndex = 26;
            this.countc.Text = "0.4";
            // 
            // button1
            // 
            this.button1.Font = new System.Drawing.Font("微软雅黑", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.button1.ForeColor = System.Drawing.Color.OliveDrab;
            this.button1.Location = new System.Drawing.Point(431, 220);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 54);
            this.button1.TabIndex = 27;
            this.button1.Text = "攻打";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // RdShow
            // 
            this.RdShow.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.RdShow.ForeColor = System.Drawing.Color.OliveDrab;
            this.RdShow.Location = new System.Drawing.Point(617, 195);
            this.RdShow.Multiline = true;
            this.RdShow.Name = "RdShow";
            this.RdShow.Size = new System.Drawing.Size(226, 157);
            this.RdShow.TabIndex = 28;
            // 
            // label7
            // 
            this.label7.BackColor = System.Drawing.Color.Transparent;
            this.label7.Font = new System.Drawing.Font("微软雅黑", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label7.ForeColor = System.Drawing.Color.Goldenrod;
            this.label7.Location = new System.Drawing.Point(624, 542);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(219, 33);
            this.label7.TabIndex = 29;
            this.label7.Text = "总装科技信息研究中心";
            // 
            // button2
            // 
            this.button2.Font = new System.Drawing.Font("微软雅黑", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.button2.ForeColor = System.Drawing.Color.OliveDrab;
            this.button2.Location = new System.Drawing.Point(431, 369);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 54);
            this.button2.TabIndex = 30;
            this.button2.Text = "显示";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // timer1
            // 
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(57, 41);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(100, 50);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pictureBox1.TabIndex = 31;
            this.pictureBox1.TabStop = false;
            // 
            // button3
            // 
            this.button3.Font = new System.Drawing.Font("微软雅黑", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.button3.ForeColor = System.Drawing.Color.OliveDrab;
            this.button3.Location = new System.Drawing.Point(431, 494);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 54);
            this.button3.TabIndex = 32;
            this.button3.Text = "返回";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // AttackPlan
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
            this.ClientSize = new System.Drawing.Size(901, 605);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.RdShow);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.countc);
            this.Controls.Add(this.countd);
            this.Controls.Add(this.missilenum);
            this.Controls.Add(this.counta);
            this.Controls.Add(this.countb);
            this.Controls.Add(this.tohit);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.result);
            this.Controls.Add(this.hitbound);
            this.Name = "AttackPlan";
            this.Text = ".";
            this.Load += new System.EventHandler(this.AttackPlan_Load);
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox hitbound;
        private System.Windows.Forms.TextBox result;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox tohit;
        private System.Windows.Forms.TextBox countb;
        private System.Windows.Forms.TextBox counta;
        private System.Windows.Forms.TextBox missilenum;
        private System.Windows.Forms.TextBox countd;
        private System.Windows.Forms.TextBox countc;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.TextBox RdShow;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.PictureBox pictureBox1;
        private System.Windows.Forms.Button button3;
    }
}