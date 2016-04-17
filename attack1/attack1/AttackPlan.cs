using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Net.Sockets;
using System.Net;
using System.Threading;

namespace attack1
{
    public partial class AttackPlan : Form
    {
        private int int_missilenum;
        private int result_a;
        private int result_b;
        private int result_c;
        private int result_d;
        private double double_tohit;
        private double double_hitbound;
        private double double_counta;
        private double double_countb;
        private double double_countc;
        private double double_countd;
        private double double_result;
        private double temp_result;
        private string[] string_result = new string[8];
        private int[,] int_temp = new int[8, 3] { { 0, 0, 0 }, { 0, 0, 1 }, { 0, 1, 0 }, { 0, 1, 1 }, { 1, 0, 0 }, { 1, 0, 1 }, { 1, 1, 0 }, { 1, 1, 1 } };
        

        private int a, b, c, d;
        private int[,] arr = new int[100000, 4];
        private int flag = 0;
        private int i;

        //
        Thread threadWatch = null; //负责监听客户端的线程
        Socket socketWatch = null; //负责监听客户端的套接字


        public AttackPlan()
        {
            InitializeComponent();
            //关闭对文本框的非法线程操作检查
            TextBox.CheckForIllegalCrossThreadCalls = false;
        }

        private void button1_Click(object sender, EventArgs e)
        {
            int_missilenum = Convert.ToInt32(missilenum.Text);
            double_tohit = Convert.ToDouble(tohit.Text);
            double_hitbound = Convert.ToDouble(hitbound.Text);
            double_counta = Convert.ToDouble(counta.Text);
            double_countb = Convert.ToDouble(countb.Text);
            double_countc = Convert.ToDouble(countc.Text);
            double_countd = Convert.ToDouble(countd.Text);

            timer1.Start();
            for (a = 0; a <= int_missilenum; a++)
                for (b = 0; b <= int_missilenum - a; b++)
                    for (c = 0; c <= int_missilenum - a - b; c++)
                    {
                        d = int_missilenum - a - b - c;
                        arr[flag, 0] = a;
                        arr[flag, 1] = b;
                        arr[flag, 2] = c;
                        arr[flag, 3] = d;
                        flag++;
                    }

            /*Random rd2 = new Random(1);
            Random rd3 = new Random(2);
            Random rd4 = new Random(3);

            int b1 = rd2.Next(100);
            int c1 = rd3.Next(100);
            int d1 = rd4.Next(100);

            float f2 = (float)(b1 * 0.01);
            float f3 = (float)(c1 * 0.01);
            float f4 = (float)(d1 * 0.01);*/
            int j;
            for (j = 0; j < 8;j++ )
            {
                temp_result = 0;
                double_result = 0;
                for (i = 0; i < flag; i++)
                {
                    temp_result = hitfunc(Math.Pow(1 - double_tohit, arr[i, 0]), double_hitbound) * double_counta +
                                  hitfunc(Math.Pow(1 - double_tohit, arr[i, 1]), double_hitbound) * fixfunc(int_temp[j, 0]) * double_countb +
                                  hitfunc(Math.Pow(1 - double_tohit, arr[i, 2]), double_hitbound) * fixfunc(int_temp[j, 1]) * double_countc +
                                  hitfunc(Math.Pow(1 - double_tohit, arr[i, 3]), double_hitbound) * fixfunc(int_temp[j, 2]) * double_countd;
                    if (temp_result >= double_result)
                    {
                        double_result = temp_result;
                        result_a = arr[i, 0];
                        result_b = arr[i, 1];
                        result_c = arr[i, 2];
                        result_d = arr[i, 3];
                    }
                }
                result.Text = result.Text+"最大损伤率：" + (double_result * 100).ToString() + "%\r\n"
                            + "打击A的导弹个数：" + result_a + "\r\n"
                            + "打击B的导弹个数：" + result_b + "\r\n"
                            + "打击C的导弹个数：" + result_c + "\r\n"
                            + "打击D的导弹个数：" + result_d + "\r\n--------------------------------------\r\n";


                RdShow.Text =RdShow.Text+ "b地点是否修复:" + int_temp[j, 0] + "\r\n"
                            + "c地点是否修复:" + int_temp[j, 1] + "\r\n"
                            + "d地点是否修复:" + int_temp[j, 2] + "\r\n--------------------------------------\r\n";
            }
            
        }
        static private int hitfunc(double arg1, double arg2)
        {
            return arg1<=arg2?1:0;
        }
        static private double fixfunc(int arg1)
        {
            if (arg1 == 0)
            {
                return 1;
            }
            else
                return 0.5;
            //return arg1 <= arg2 ? 1 : 0.5;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            BestOne bo = new BestOne();
            bo.setArg(result.Text, RdShow.Text);
            bo.Show();
        }

        private void AttackPlan_Load(object sender, EventArgs e)
        {

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            int x = pictureBox1.Left;
            if (x > 600)
            {
                timer1.Stop();
            }
            else
            {
                pictureBox1.Left = pictureBox1.Left + 100;
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            main ma = new main();
            ma.Show();
        }


        
        private void server_listen_Click(object sender, EventArgs e)
        {
            socketWatch = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            IPAddress ipaddress = IPAddress.Parse(txtIP.Text.Trim()); 
            IPEndPoint endpoint = new IPEndPoint(ipaddress, int.Parse(txtPORT.Text.Trim())); 
            socketWatch.Bind(endpoint);
            socketWatch.Listen(20);
            threadWatch = new Thread(WatchConnecting);
            threadWatch.IsBackground = true;
            threadWatch.Start();
            txtMsg.AppendText("开始监听客户端传来的信息!" + "\r\n");
            
        }

        Socket socConnection = null;

        /// <summary>
        /// 监听客户端发来的请求
        /// </summary>
        private void WatchConnecting()
        {
            while (true)  
            {
                socConnection = socketWatch.Accept();
                txtMsg.AppendText("客户端连接成功" + "\r\n");
                ParameterizedThreadStart pts = new ParameterizedThreadStart(ServerRecMsg);
                Thread thr = new Thread(pts);
                thr.IsBackground = true;
                thr.Start(socConnection);
            }
        }

        /// <summary>
        /// 发送信息到客户端的方法
        /// </summary>
        /// <param name="sendMsg">发送的字符串信息</param>
        private void ServerSendMsg(string sendMsg)
        {
            byte[] arrSendMsg = Encoding.UTF8.GetBytes(sendMsg);
            socConnection.Send(arrSendMsg);
            txtMsg.AppendText(GetCurrentTime() + "：" + sendMsg + "\r\n");
        }

        /// <summary>
        /// 接收客户端发来的信息 
        /// </summary>
        /// <param name="socketClientPara">客户端套接字对象</param>
        private void ServerRecMsg(object socketClientPara)
        {
            Socket socketServer = socketClientPara as Socket;
            while (true)
            {
                byte[] arrServerRecMsg = new byte[1024 * 1024];
                int length = socketServer.Receive(arrServerRecMsg);
                string strSRecMsg = Encoding.UTF8.GetString(arrServerRecMsg, 0, length);
                txtMsg.AppendText("client:" + GetCurrentTime() + "：" + strSRecMsg + "\r\n");
            }
        }

        /// <summary>
        /// 获取当前系统时间的方法
        /// </summary>
        /// <returns>当前时间</returns>
        private DateTime GetCurrentTime()
        {
            DateTime currentTime = new DateTime();
            currentTime = DateTime.Now;
            return currentTime;
        }

        private void btnSendMsg_Click(object sender, EventArgs e)
        {
            ServerSendMsg(txtSendMsg.Text.Trim());
        }
         
        }
         
    }

