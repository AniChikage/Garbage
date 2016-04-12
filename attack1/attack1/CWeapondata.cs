using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
 using System.Threading.Tasks;
using System.Data.OleDb;
using System.Data;

namespace attack1
{
    class CWeapondata
    {
        string dbfn;
        private OleDbConnection conn;
        private static CWeapondata s;
        public static CWeapondata S
        {
            get
            {
                if (s == null)
                    s = new CWeapondata();
                return s;
            }
        }
        public void init(string dbfn)    //初始化数据库
        {
            this.dbfn = dbfn;
            string connstr = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + dbfn;
            conn = new OleDbConnection(connstr);
        }
        public DataTable Select (string sql)      //返回数据
        {
            DataTable dt = new DataTable();
            if (conn.State == ConnectionState.Closed)
                conn.Open();
            System.Data.OleDb.OleDbDataAdapter myAd = new OleDbDataAdapter(sql, conn);
            myAd.Fill(dt);
            return dt;
        }
        public bool Execute(string sql)        //执行数据库语句
        {
            bool f = true;
            if (conn.State == ConnectionState.Closed)
                conn.Open();
            OleDbCommand sc = new OleDbCommand(sql, conn);
            sc.ExecuteNonQuery();
            return f;
        }

    }
}
