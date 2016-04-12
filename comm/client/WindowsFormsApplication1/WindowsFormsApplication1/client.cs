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

namespace WindowsFormsApplication1
{
     
    public partial class client : Form
    { 
        private static byte[] result = new byte[1024];
        public client()
        {
            InitializeComponent();
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            IPAddress ip = IPAddress.Parse("127.0.0.1");
            Socket clientSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            try
            {
                clientSocket.Connect(new IPEndPoint(ip, 8885)); //配置服务器IP与端口  
                //Console.WriteLine("连接服务器成功");
                textBox1.Text += "连接服务器成功\n";
            }
            catch
            {
                //Console.WriteLine("连接服务器失败，请按回车键退出！");
                textBox1.Text += "连接服务器失败\n";
                return;
            }
            //通过clientSocket接收数据  
            int receiveLength = clientSocket.Receive(result);
            Console.WriteLine("接收服务器消息：{0}", Encoding.ASCII.GetString(result, 0, receiveLength));
            //通过 clientSocket 发送数据  
            for (int i = 0; i < 10; i++)
            {
                try
                {
                    Thread.Sleep(1000);    //等待1秒钟  
                    //string sendMessage = "client send Message Hellp" + DateTime.Now;
                    string sendMessage = textBox2.Text.ToString();
                    clientSocket.Send(Encoding.ASCII.GetBytes(sendMessage));
                    Console.WriteLine("向服务器发送消息：{0}" + sendMessage);
                }
                catch
                {
                    clientSocket.Shutdown(SocketShutdown.Both);
                    clientSocket.Close();
                    break;
                }
            }
            Console.WriteLine("发送完毕，按回车键退出");
            Console.ReadLine();  
        }
    }
}
