using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NIMDemo
{
    public partial class ChatRoomListForm : Form
    {
        private ChatRoomListForm()
        {
            InitializeComponent();
            this.Load += ChatRoomListForm_Load;
            treeView1.NodeMouseDoubleClick += TreeView1_NodeMouseDoubleClick;
            treeView1.NodeMouseClick += TreeView1_NodeMouseClick;
            NIMChatRoom.ChatRoomApi.Init();
            this.FormClosed += ChatRoomListForm_FormClosed;
        }

        private readonly HashSet<long> _joinedRoomIdSet = new HashSet<long>();

        private void TreeView1_NodeMouseClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            if (e.Button != MouseButtons.Right)
                return;
            var roomId = long.Parse(e.Node.Name);
            ContextMenu menu = new ContextMenu();
            MenuItem item1 = null;
            if (!_joinedRoomIdSet.Contains(roomId))
            {
                item1 = new MenuItem("进入聊天室", (s, arg) =>
                {
                    NIM.Plugin.ChatRoom.RequestLoginInfo(roomId, (response, authResult) =>
                    {
                        if (response == NIM.ResponseCode.kNIMResSuccess)
                        {
                            NIMChatRoom.ChatRoomApi.Login(roomId, authResult);
                        }
                    });
                });
            }
            else
            {
                item1 = new MenuItem("离开聊天室", (s, arg) =>
                {
                    NIMChatRoom.ChatRoomApi.Exit(roomId);
                });

                MenuItem item2 = new MenuItem("聊天室信息", (s, arg) =>
                {
                    NIMChatRoom.ChatRoomApi.GetRoomInfo(roomId, (a, b, c) =>
                    {
                        Action action = () =>
                        {
                            ObjectPropertyInfoForm form = new ObjectPropertyInfoForm();
                            form.TargetObject = c;
                            form.Show();
                        };
                        this.Invoke(action);
                    });
                });

                MenuItem item3 = new MenuItem("聊天室成员", (s, arg) =>
                {
                    NIMChatRoom.ChatRoomApi.QueryMembersOnline(roomId, NIMChatRoom.NIMChatRoomGetMemberType.kNIMChatRoomGetMemberTypeSolid, 0, 10, (a, b, c) =>
                    {
                        if (b == NIM.ResponseCode.kNIMResSuccess)
                            OutputForm.SetText(c.Dump());
                    });

                    NIMChatRoom.ChatRoomApi.QueryMembersOnline(roomId, NIMChatRoom.NIMChatRoomGetMemberType.kNIMChatRoomGetMemberTypeTemp, 0, 10, (a, b, c) =>
                    {
                        if (b == NIM.ResponseCode.kNIMResSuccess)
                            OutputForm.SetText(c.Dump());
                    });
                });

                MenuItem item4 = new MenuItem("消息历史", (s, arg) =>
                {
                    NIMChatRoom.ChatRoomApi.QueryMessageHistoryOnline(roomId, 0, 50, (a, b, c) =>
                    {
                        if (b == NIM.ResponseCode.kNIMResSuccess)
                            OutputForm.SetText(c.Dump());
                    });

                });

                MenuItem item5 = new MenuItem("发送测试消息", (s, arg) =>
                {
                    NIMChatRoom.Message msg = new NIMChatRoom.Message();
                    msg.MessageType = NIMChatRoom.NIMChatRoomMsgType.kNIMChatRoomMsgTypeText;
                    msg.MessageAttachment = "这是一条测试消息 " + DateTime.Now.ToString() + " " + new Random().NextDouble().ToString();
                    NIMChatRoom.ChatRoomApi.SendMessage(roomId, msg);
                });
                menu.MenuItems.Add(item2);
                menu.MenuItems.Add(item3);
                menu.MenuItems.Add(item4);
                menu.MenuItems.Add(item5);
            }

            menu.MenuItems.Add(item1);
            menu.Show(treeView1, e.Location);
        }

        void SendTextMsg(long roomId,string text)
        {
            NIMChatRoom.Message msg = new NIMChatRoom.Message();
            msg.MessageType = NIMChatRoom.NIMChatRoomMsgType.kNIMChatRoomMsgTypeText;
            msg.RoomId = roomId;
            msg.MessageAttachment = text;
            NIMChatRoom.ChatRoomApi.SendMessage(roomId, msg);
        }

        private void TreeView1_NodeMouseDoubleClick(object sender, TreeNodeMouseClickEventArgs e)
        {
            var roomId = long.Parse(e.Node.Name);
            
            NIMChatRoom.ChatRoomApi.QueryMembersOnline(roomId, NIMChatRoom.NIMChatRoomGetMemberType.kNIMChatRoomGetMemberTypeSolid, 0, 10, (a, b, c) =>
            {
                
            });
        }

        public ChatRoomListForm(List<NIMChatRoom.ChatRoomInfo> list)
            : this()
        {
            _chatrommList = list;
        }

        private void ChatRoomListForm_Load(object sender, EventArgs e)
        {
            InitChatRoomlistTreeView();
            NIMChatRoom.ChatRoomApi.LoginHandler += ChatRoomApi_LoginHandler;
            NIMChatRoom.ChatRoomApi.ExitHandler += ChatRoomApi_ExitHandler;
            NIMChatRoom.ChatRoomApi.SendMessageHandler += ChatRoomApi_SendMessageHandler;
            NIMChatRoom.ChatRoomApi.ReceiveNotificationHandler += ChatRoomApi_ReceiveNotificationHandler;
            NIMChatRoom.ChatRoomApi.ReceiveMessageHandler += ChatRoomApi_ReceiveMessageHandler;
        }

        private void ChatRoomListForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            NIMChatRoom.ChatRoomApi.LoginHandler -= ChatRoomApi_LoginHandler;
            NIMChatRoom.ChatRoomApi.ExitHandler -= ChatRoomApi_ExitHandler;
            NIMChatRoom.ChatRoomApi.SendMessageHandler -= ChatRoomApi_SendMessageHandler;
            NIMChatRoom.ChatRoomApi.ReceiveNotificationHandler -= ChatRoomApi_ReceiveNotificationHandler;
            NIMChatRoom.ChatRoomApi.ReceiveMessageHandler -= ChatRoomApi_ReceiveMessageHandler;
            NIMChatRoom.ChatRoomApi.Cleanup();
        }
        private void ChatRoomApi_ReceiveMessageHandler(long roomId, NIMChatRoom.Message message)
        {
            string item = string.Format("{0}:\r\n{1}\r\n", message.SenderNickName, message.MessageAttachment);
            Action action = () =>
            {
                this.receivedmsgListbox.Items.Add(item);
            };
            this.Invoke(action);
        }
        private void ChatRoomApi_ReceiveNotificationHandler(long roomId, NIMChatRoom.Notification notification)
        {
            MessageBox.Show(notification.Dump(), "聊天室通知:" + roomId);
        }

        private void ChatRoomApi_SendMessageHandler(long roomId, NIM.ResponseCode code, NIMChatRoom.Message message)
        {
            if (code != NIM.ResponseCode.kNIMResSuccess)
            {
                MessageBox.Show("聊天室消息发送失败");
            } 
        }

        private void ChatRoomApi_ExitHandler(long roomId, NIM.ResponseCode errorCode, NIMChatRoom.NIMChatRoomExitReason reason)
        {
            if (errorCode == NIM.ResponseCode.kNIMResSuccess)
            {
                _joinedRoomIdSet.Remove(roomId);
            }
        }

        private void ChatRoomApi_LoginHandler(NIMChatRoom.NIMChatRoomLoginStep loginStep, NIM.ResponseCode errorCode, NIMChatRoom.ChatRoomInfo roomInfo, NIMChatRoom.MemberInfo memberInfo)
        {
            if (loginStep == NIMChatRoom.NIMChatRoomLoginStep.kNIMChatRoomLoginStepRoomAuthOver && errorCode == NIM.ResponseCode.kNIMResSuccess)
            {
                _joinedRoomIdSet.Add(roomInfo.RoomId);
            }
            if (errorCode != NIM.ResponseCode.kNIMResSuccess)
            {
                MessageBox.Show(loginStep.ToString() + " " + errorCode.ToString(), "进入聊天室出错");
            }
        }

        private readonly List<NIMChatRoom.ChatRoomInfo> _chatrommList;

        void InitChatRoomlistTreeView()
        {
            TreeNode root = new TreeNode("聊天室");
            if (_chatrommList != null)
            {
                foreach (var info in _chatrommList)
                {
                    TreeNode node = new TreeNode(info.RoomName + " - " + info.RoomId);
                    node.Name = info.RoomId.ToString();
                    root.Nodes.Add(node);
                }
            }
            treeView1.Nodes.Add(root);
        }
    }
}
