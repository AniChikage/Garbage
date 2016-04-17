using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace attack1
{
    public partial class BestOne : Form
    {
        public BestOne()
        {
            InitializeComponent();
        //    AttackPlan q1 =new 
        }

        public void setArg(string arg1, string arg2)
        {
                textBox1.Text = arg1;
                textBox2.Text = arg2;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            AttackPlan at = new AttackPlan();
            at.Show();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Flash fl = new Flash();
            fl.Show();
        }

       
    }
}
