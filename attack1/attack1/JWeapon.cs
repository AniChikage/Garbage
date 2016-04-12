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
    public partial class JWeapon : Form
    {
        public JWeapon()
        {
            InitializeComponent();
            string apppath = Path.GetDirectoryName(Application.ExecutablePath);
            JWeapondata.A.init(apppath + "\\weapon1.mdb");  //初始化数据库
            string sql = "select * from weaponJ";
            DataTable dtj = JWeapondata.A.Select(sql);

            foreach (DataRow drj in dtj.Rows)
            {
                //   for (int i = 0; i < strlist.Length; i++)
                //   {
                ListViewItem lij = new ListViewItem(drj["名称"].ToString());
                lij.SubItems.Add(drj["空重（千克）"].ToString());
                lij.SubItems.Add(drj["起飞滑跑距离（米）"].ToString());
                lij.SubItems.Add(drj["平飞速度（千米/时）"].ToString());
                lij.SubItems.Add(drj["长度（米）"].ToString());
                lij.SubItems.Add(drj["射程（千米）"].ToString());
                listView1.Items.Add(lij);
                //    }
            }
        }

        private void JWeapon_Load(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Weapon b = new Weapon();
            b.Show();
        }
    }
}
