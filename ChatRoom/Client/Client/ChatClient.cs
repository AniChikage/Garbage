using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Text;
using System.Data;
using System.Collections;
using System.Runtime.Serialization.Formatters;
using System.Runtime.Remoting;
using System.Runtime.Remoting.Channels;
using System.Runtime.Remoting.Channels.Http;
using System.Windows.Forms;

using lib;

namespace Client
{
    class ChatClient
    {
        private bool isLogin;
        private ChatRoom x;
        private EventWrapper wrapper;
        public string User;
        public  ListBox listbox;

        public ChatClient()
        {
            x = null;
            wrapper = null;
            listbox = null;
            isLogin = false;
            User = "";
        }

        public   bool   IsLogin   
        {   
           get   
            {
                return isLogin;   
            }   
           set   
           {
               isLogin = value;   
            }   
         }   
                 
        //ֱ����System.EventHandler,û�б�Ҫ�Զ���ί��
        private void UpdateUI(object o, System.EventArgs e)
        {
           //UI�߳�����label1����
            listbox.Items.Add(o.ToString());
        }

        //�յ���Ϣ�Ĵ�����
        public  void OnMessageReceive(string Message)
        {
            Debug.WriteLine("OnMessageReceive...");
            listbox.BeginInvoke(new System.EventHandler(UpdateUI), Message);
            //listbox.Items.Add(Message);
        }
        public  void OnLogin(string Message)
        {
            Debug.WriteLine("OnLogin...");
        }
        public  void OnLogoff(string Message)
        {
            Debug.WriteLine("OnLogoff...");
        }

        public  void Connect()
        {
            Debug.WriteLine("Init...");
            //�Ա�̷�ʽ��ȡԶ�̷������ʵ��
            BinaryServerFormatterSinkProvider serverProvider = new BinaryServerFormatterSinkProvider();
            BinaryClientFormatterSinkProvider clientProvider = new BinaryClientFormatterSinkProvider();
            serverProvider.TypeFilterLevel = TypeFilterLevel.Full;

            IDictionary props = new Hashtable();
            props["port"] = 0;
            HttpChannel channel = new HttpChannel(props, clientProvider, serverProvider);
            ChannelServices.RegisterChannel(channel);

            x = (ChatRoom)System.Activator.GetObject(typeof(ChatRoom), "http://127.0.0.1:8080/ChatRoomURL");
            
            //ע���¼�
            wrapper = new EventWrapper();
            wrapper.MessageReceive += new ChatRoom.ChatRoomEventHandler(OnMessageReceive);
            x.MessageReceive += new ChatRoom.ChatRoomEventHandler(wrapper.OnMessageReceive);

            wrapper.Login += new ChatRoom.ChatRoomEventHandler(OnLogin);
            x.Login += new ChatRoom.ChatRoomEventHandler(wrapper.OnLogin);

            wrapper.Logoff += new ChatRoom.ChatRoomEventHandler(OnLogoff);
            x.Logoff += new ChatRoom.ChatRoomEventHandler(wrapper.OnLogoff);		


            //�ȵ�¼
            Debug.WriteLine("Login:");

            //���ô�Զ�̷���,֪ͨ���������� Receiver �ͻ��� Login �¼�,�㲥 "��¼" ��Ϣ
            x.OnLogin(User);
            isLogin = true;
        }

        public  void SendMessage(string message)
        {
            Debug.WriteLine("Send  Message here:");

            x.OnMessageReceive(User+"˵��"+message);
         }

        public  void disConnect()
        {
           //���ô�Զ�̷���,֪ͨ���������� Receiver �ͻ��� Logoff �¼�,�㲥 "�˳�" ��Ϣ
            x.OnLogoff(User);

            x.MessageReceive -= new ChatRoom.ChatRoomEventHandler(wrapper.OnMessageReceive);
            x.Login -= new ChatRoom.ChatRoomEventHandler(wrapper.OnLogin);
            x.Logoff -= new ChatRoom.ChatRoomEventHandler(wrapper.OnLogoff);

            Debug.WriteLine("bye bye " + User);
        }
    }
}
