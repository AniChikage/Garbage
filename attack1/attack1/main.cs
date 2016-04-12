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
    public partial class main : Form
    {
        public main()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Weapon c = new Weapon();
            c.Show();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            AttackPlan m = new AttackPlan();
            m.Show();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            BestOne n = new BestOne();
            n.Show();
        }
    }
}
