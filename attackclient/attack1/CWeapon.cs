using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace attack1
{
    public partial class CWeapon : Form
    {
        public CWeapon()
        {
            InitializeComponent();
            string apppath = Path.GetDirectoryName(Application.ExecutablePath);
            CWeapondata.S.init(apppath + "\\weapon1.mdb");  //初始化数据库
            string sql = "select * from weaponC";
            DataTable dt = CWeapondata.S.Select(sql);

            foreach (DataRow dr in dt.Rows)
            {
             //   for (int i = 0; i < strlist.Length; i++)
             //   {
                    ListViewItem li = new ListViewItem(dr["名称"].ToString());
                    li.SubItems.Add(dr["负载重量（公斤）"].ToString());
                    li.SubItems.Add(dr["长度（米）"].ToString());
                    li.SubItems.Add(dr["直径（米）"].ToString());
                    li.SubItems.Add(dr["速度（马赫）"].ToString());
                    li.SubItems.Add(dr["射程（公里）"].ToString());
                    listView2.Items.Add(li);
            //    }
            }
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void CWeapon_Load(object sender, EventArgs e)
        {

        }

        private void listView2_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void label1_Click_1(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            Weapon A = new Weapon();
            A.Show();
        }
    }
}
