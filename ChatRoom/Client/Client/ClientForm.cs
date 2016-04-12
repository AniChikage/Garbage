using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace Client
{
    public partial class ClientForm : Form
    {
        ChatClient chatClient;
        public ClientForm()
        {
            InitializeComponent();
            chatClient = new ChatClient();
        }

        //����
        private void button1_Click(object sender, EventArgs e)
        {
            if (button2.Enabled)
            {
                MessageBox.Show("��û��¼�����ܷ��ͣ�");
                return;
            }
            chatClient.SendMessage(textBox1.Text);
        }

        //���ӷ�����
        private void button2_Click(object sender, EventArgs e)
        {
            chatClient.User = textBox2.Text;
            chatClient.Connect();
            chatClient.listbox = this.listBox1;
            button2.Enabled = false;
        }

        //�뿪
        private void ClientForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            if (chatClient.IsLogin)
            {
                chatClient.disConnect();
                chatClient = null;
            }
        }
    }
}