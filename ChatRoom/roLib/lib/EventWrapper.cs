using System;
using System.Collections.Generic;
using System.Text;


using System;
using System.Runtime.Remoting.Messaging;

namespace lib
{
	/// <summary>
	/// EventClass ��ժҪ˵����
	/// </summary>
	public class EventWrapper:MarshalByRefObject
	{
        public event ChatRoom.ChatRoomEventHandler MessageReceive; //��Ϣ�����¼�
        public event ChatRoom.ChatRoomEventHandler Login; //��¼�¼�
        public event ChatRoom.ChatRoomEventHandler Logoff; //�˳��¼�

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
