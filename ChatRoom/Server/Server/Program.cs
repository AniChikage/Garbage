using System;
using System.Collections.Generic;
using System.Text;
using System.Collections;
using System.Runtime.Remoting;
using System.Runtime.Remoting.Channels;
using System.Runtime.Remoting.Channels.Http;
using System.Runtime.Serialization.Formatters;
using lib;

namespace Server
{
    class Program
    {
        public static ChatRoom Obj = null;

        public static void Main(string[] Args)
        {
            //RemotingConfiguration.Configure("Server.config");
            BinaryServerFormatterSinkProvider serverProvider = new BinaryServerFormatterSinkProvider();
            BinaryClientFormatterSinkProvider clientProvider = new BinaryClientFormatterSinkProvider();
            serverProvider.TypeFilterLevel = TypeFilterLevel.Full;

            IDictionary props = new Hashtable();
            props["port"] = 8080;
            HttpChannel channel = new HttpChannel(props, clientProvider, serverProvider);
            ChannelServices.RegisterChannel(channel);

            #region 客户端订阅服务端事件

            Obj = new ChatRoom();
            ObjRef objRef = RemotingServices.Marshal(Obj, "ChatRoomURL");
            #endregion

            Console.WriteLine("Server .... , Press Enter key to exit.");
            Console.ReadLine();
        }
    }

}
