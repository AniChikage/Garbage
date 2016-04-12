using System;
using System.Collections.Generic;
using System.Text;


using System;
using System.Runtime.Remoting.Messaging;

namespace lib
{
	/// <summary>
	/// EventClass 的摘要说明。
	/// </summary>
	public class EventWrapper:MarshalByRefObject
	{
        public event ChatRoom.ChatRoomEventHandler MessageReceive; //消息接收事件
        public event ChatRoom.ChatRoomEventHandler Login; //登录事件
        public event ChatRoom.ChatRoomEventHandler Logoff; //退出事件

		//[OneWay]
		public void OnMessageReceive(string message)
		{
            if (MessageReceive != null) MessageReceive(message);
		}
        public void OnLogin(string message)
        {
            if (Login != null) Login(message);
        }
        public void OnLogoff(string message)
        {
            if (Logoff != null) Logoff(message);
        }

		public override object InitializeLifetimeService()
		{
			return null;
		}

	}
}
