using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace attack1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            
            
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
         //   if(textBox1.Text =="123" &&  textBox2.Text =="123")
         //   {
                MessageBox.Show("登录成功");
                this.Hide();
                main W1 = new main();
                W1.Show();

        //    }
        }
    }
}
