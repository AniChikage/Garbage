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
    public partial class Weapon : Form
    {
        public Weapon()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            CWeapon C1 = new CWeapon();
            C1.Show();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            JWeapon J1 = new JWeapon();
            J1.Show();
        }

        private void Weapon_Load(object sender, EventArgs e)
        {

        }

        private void button3_Click(object sender, EventArgs e)
        {
            main k = new main();
            k.Show();
        }
    }
}
