using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.OleDb;
using System.Data;

namespace attack1
{
    class JWeapondata
    {
        string dbfn;
        private OleDbConnection con;
        private static JWeapondata a;
        public static JWeapondata A
        {
            get
            {
                if (a == null)
                    a = new JWeapondata();
                return a;
            }
        }
        public void init(string dbfn)    //初始化数据库
        {
            this.dbfn = dbfn;
            string connstr = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + dbfn;
            con = new OleDbConnection(connstr);
        }
        public DataTable Select (string sql)      //返回数据
        {
            DataTable dtj = new DataTable();
            if (con.State == ConnectionState.Closed)
                con.Open();
            System.Data.OleDb.OleDbDataAdapter myAdj = new OleDbDataAdapter(sql, con);
            myAdj.Fill(dtj);
            return dtj;
        }
        public bool Execute(string sql)        //执行数据库语句
        {
            bool fj = true;
            if (con.State == ConnectionState.Closed)
                con.Open();
            OleDbCommand scj = new OleDbCommand(sql, con);
            scj.ExecuteNonQuery();
            return fj;
        }

    }
    }

