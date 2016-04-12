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
                 
        //直接用System.EventHandler,没有必要自定义委托
        private void UpdateUI(object o, System.EventArgs e)
        {
           //UI线程设置label1属性
            listbox.Items.Add(o.ToString());
        }

        //收到消息的处理函数
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
            //以编程方式获取远程服务对象实例
            BinaryServerFormatterSinkProvider serverProvider = new BinaryServerFormatterSinkProvider();
            BinaryClientFormatterSinkProvider clientProvider = new BinaryClientFormatterSinkProvider();
            serverProvider.TypeFilterLevel = TypeFilterLevel.Full;

            IDictionary props = new Hashtable();
            props["port"] = 0;
            HttpChannel channel = new HttpChannel(props, clientProvider, serverProvider);
            ChannelServices.RegisterChannel(channel);

            x = (ChatRoom)System.Activator.GetObject(typeof(ChatRoom), "http://127.0.0.1:8080/ChatRoomURL");
            
            //注册事件
            wrapper = new EventWrapper();
            wrapper.MessageReceive += new ChatRoom.ChatRoomEventHandler(OnMessageReceive);
            x.MessageReceive += new ChatRoom.ChatRoomEventHandler(wrapper.OnMessageReceive);

            wrapper.Login += new ChatRoom.ChatRoomEventHandler(OnLogin);
            x.Login += new ChatRoom.ChatRoomEventHandler(wrapper.OnLogin);

            wrapper.Logoff += new ChatRoom.ChatRoomEventHandler(OnLogoff);
            x.Logoff += new ChatRoom.ChatRoomEventHandler(wrapper.OnLogoff);		


            //先登录
            Debug.WriteLine("Login:");

            //调用此远程方法,通知服务器触发 Receiver 客户端 Login 事件,广播 "登录" 消息
            x.OnLogin(User);
            isLogin = true;
        }

        public  void SendMessage(string message)
        {
            Debug.WriteLine("Send  Message here:");

            x.OnMessageReceive(User+"说："+message);
         }

        public  void disConnect()
        {
           //调用此远程方法,通知服务器触发 Receiver 客户端 Logoff 事件,广播 "退出" 消息
            x.OnLogoff(User);

            x.MessageReceive -= new ChatRoom.ChatRoomEventHandler(wrapper.OnMessageReceive);
            x.Login -= new ChatRoom.ChatRoomEventHandler(wrapper.OnLogin);
            x.Logoff -= new ChatRoom.ChatRoomEventHandler(wrapper.OnLogoff);

            Debug.WriteLine("bye bye " + User);
        }
    }
}
