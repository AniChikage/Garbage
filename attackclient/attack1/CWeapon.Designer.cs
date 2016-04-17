namespace attack1
{
    partial class CWeapon
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CWeapon));
            this.listView2 = new System.Windows.Forms.ListView();
            this.q = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.w = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.e = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.r = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.t = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.y = ((System.Windows.Forms.ColumnHeader)(new System.Windows.Forms.ColumnHeader()));
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // listView2
            // 
            this.listView2.BackColor = System.Drawing.Color.DarkOliveGreen;
            this.listView2.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.q,
            this.w,
            this.e,
            this.r,
            this.t,
            this.y});
            this.listView2.Font = new System.Drawing.Font("微软雅黑", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.listView2.ForeColor = System.Drawing.Color.Snow;
            this.listView2.Location = new System.Drawing.Point(375, 151);
            this.listView2.Name = "listView2";
            this.listView2.Size = new System.Drawing.Size(514, 327);
            this.listView2.TabIndex = 1;
            this.listView2.UseCompatibleStateImageBehavior = false;
            this.listView2.View = System.Windows.Forms.View.Details;
            this.listView2.SelectedIndexChanged += new System.EventHandler(this.listView2_SelectedIndexChanged);
            // 
            // q
            // 
            this.q.Text = "名称";
            this.q.Width = 98;
            // 
            // w
            // 
            this.w.Text = "负载重量";
            this.w.Width = 91;
            // 
            // e
            // 
            this.e.Text = "长度";
            this.e.Width = 79;
            // 
            // r
            // 
            this.r.Text = "直径";
            this.r.Width = 79;
            // 
            // t
            // 
            this.t.Text = "速度";
            this.t.Width = 69;
            // 
            // y
            // 
            this.y.Text = "射程";
            this.y.Width = 93;
            // 
            // label1
            // 
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("微软雅黑", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label1.ForeColor = System.Drawing.Color.DarkRed;
            this.label1.Location = new System.Drawing.Point(47, 66);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(260, 400);
            this.label1.TabIndex = 2;
            this.label1.Text = resources.GetString("label1.Text");
            this.label1.Click += new System.EventHandler(this.label1_Click_1);
            // 
            // label2
            // 
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Font = new System.Drawing.Font("微软雅黑", 15F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.label2.ForeColor = System.Drawing.Color.Goldenrod;
            this.label2.Location = new System.Drawing.Point(670, 540);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(219, 27);
            this.label2.TabIndex = 3;
            this.label2.Text = "总装科技信息研究中心";
            // 
            // button1
            // 
            this.button1.BackColor = System.Drawing.Color.DarkOliveGreen;
            this.button1.Font = new System.Drawing.Font("微软雅黑", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(134)));
            this.button1.ForeColor = System.Drawing.Color.DarkRed;
            this.button1.Location = new System.Drawing.Point(98, 508);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(87, 42);
            this.button1.TabIndex = 4;
            this.button1.Text = "返回";
            this.button1.UseVisualStyleBackColor = false;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // CWeapon
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
            this.ClientSize = new System.Drawing.Size(947, 605);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.listView2);
            this.Location = new System.Drawing.Point(100, 100);
            this.Name = "CWeapon";
            this.Text = "CWeapon";
            this.Load += new System.EventHandler(this.CWeapon_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView listView2;
        private System.Windows.Forms.ColumnHeader q;
        private System.Windows.Forms.ColumnHeader w;
        private System.Windows.Forms.ColumnHeader e;
        private System.Windows.Forms.ColumnHeader r;
        private System.Windows.Forms.ColumnHeader t;
        private System.Windows.Forms.ColumnHeader y;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button button1;
    }
}