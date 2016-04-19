using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Windows.Forms;
using NIM;
using NIM.User;

namespace NIMDemo
{
    public partial class FriendsListForm : Form
    {
        private readonly string _selfId;
        private NIM.User.UserNameCard _selfNameCard;
        private readonly Dictionary<string, NIM.Friend.NIMFriendProfile> _friendsDictionary = new Dictionary<string, NIM.Friend.NIMFriendProfile>();
        private readonly HashSet<string> _blacklistSet = new HashSet<string>();
        private readonly HashSet<string> _mutedlistSet = new HashSet<string>();
        private readonly ListViewGroup[] _groups = new ListViewGroup[]
        {new ListViewGroup("friend", "好友"), new ListViewGroup("blacklist", "黑名单"), new ListViewGroup("mutedlist", "静音")};
        private readonly Team.TeamList _teamList = null;
        private readonly SessionList _sessionList = null;
        private RecentSessionList _recentSessionList = null;
        private MultimediaHandler _multimediaHandler = null;

        private FriendsListForm()
        {
            InitializeComponent();
            this.Load += FriendsListForm_Load;
            this.FormClosing += FriendsListForm_FormClosing;
            this.FormClosed += FriendsListForm_FormClosed;
            RegisterClientCallback();
            listView1.ShowGroups = true;
            listView1.Groups.AddRange(_groups);
            _teamList = new Team.TeamList(TeamListView);
            _sessionList = new SessionList(chatListView);
            NIM.DataSync.DataSyncAPI.RegCompleteCb((a, b, c) =>
            {
                DemoTrace.WriteLine(new {Type = a, Status = b, Json = c});
            });
        }
        public FriendsListForm(string id)
           : this()
        {
            _selfId = id;
        }

        private bool _notifyNetworkDisconnect = true;
        private bool _beKicked = false;
        private void FriendsListForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            _notifyNetworkDisconnect = false;
        }

        private void FriendsListForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            //退出前需要结束音视频设备，防止错误的数据上报
            _multimediaHandler.EndDevices();
            System.Threading.Thread.Sleep(500);
            //在释放前需要按步骤清理音视频模块和nim client模块
            NIM.VChatAPI.Cleanup();
            if (!_beKicked)
            {
                System.Threading.Semaphore s = new System.Threading.Semaphore(0, 1);
                NIM.ClientAPI.Logout(NIM.NIMLogoutType.kNIMLogoutAppExit, (r) =>
                {
                    s.Release();
                });
                //需要logout执行完才能退出程序
                s.WaitOne(TimeSpan.FromSeconds(10));
                NIM.ClientAPI.Cleanup();
            }
            if (_restart)
                Process.Start("NIMDemo.exe");
            Application.Exit();
        }

        void GetFriendResult(NIM.Friend.NIMFriends ps)
        {
            Action action = UpdateFriendListView;
            if (ps == null)
            {
                return;
            }
            foreach (var item in ps.ProfileList)
            {
                _friendsDictionary[item.AccountId] = item;
            }
            this.Invoke(action);
        }

        private void FriendsListForm_Load(object sender, EventArgs e)
        {
            NIM.Friend.FriendAPI.GetFriendsList(GetFriendResult);
            NIM.User.UserAPI.GetRelationshipList(GetUserRelationCompleted);
            NIM.User.UserAPI.GetUserNameCard(new List<string>() { _selfId }, (a) =>
            {
                if (a == null || !a.Any())
                    return;
                DisplayMyProfile(a[0]);
            });
            NIM.Friend.FriendAPI.FriendProfileChangedHandler += OnFriendChanged;
            NIM.User.UserAPI.UserRelationshipListSyncHander += OnUserRelationshipSync;
            NIM.User.UserAPI.UserRelationshipChangedHandler += OnUserRelationshipChanged;
            NIM.User.UserAPI.UserNameCardChangedHandler += OnUserNameCardChanged;
            
            NIM.TalkAPI.OnSendMessageCompleted += (s, args) => { DemoTrace.WriteLine(args.Dump()); };
            NIM.TalkAPI.OnReceiveMessageHandler += OnReceiveMessage;
            NIM.SysMessage.SysMsgAPI.SendSysMsgHandler += (s, args) => { DemoTrace.WriteLine("SysMsg:"+args.Dump()); };
            NIM.SysMessage.SysMsgAPI.ReceiveSysMsgHandler += (s, args) => { DemoTrace.WriteLine("receive sysmsg:"+args.Dump()); };
            _teamList.LoadTeams();
            _sessionList.GetRecentSessionList();
            _recentSessionList = new RecentSessionList(recentSessionListbox);
            _recentSessionList.LoadSessionList();
            _multimediaHandler = new MultimediaHandler(this);
            _multimediaHandler.InitVChatInfo();

        }

        void OnReceiveMessage(object sender, NIMReceiveMessageEventArgs args)
        {
            var sid = args.Message.MessageContent.SenderID;
            var msgType = args.Message.MessageContent.MessageType;
            Action action = () =>
            {
                ListViewItem item = new ListViewItem(sid);
                if (msgType != NIMMessageType.kNIMMessageTypeText)
                    item.SubItems.Add(msgType.ToString());
                else
                {
                    var m = args.Message.MessageContent as NIM.NIMTextMessage;
                    item.SubItems.Add(m.TextContent);
                }
                item.Tag = args.Message.MessageContent;
                chatListView.Items.Add(item);
            };
            this.Invoke(action);
            DemoTrace.WriteLine(args.Dump());
        }

        private void OnUserNameCardChanged(object sender, UserNameCardChangedArgs e)
        {
            DemoTrace.WriteLine("用户名片变更:" + e.UserNameCardList.Dump()) ;
            var card = e.UserNameCardList.Find(c => c.AccountId == _selfId);
            DisplayMyProfile(card);
        }

        private void OnUserRelationshipChanged(object sender, UserRelationshipChangedArgs e)
        {
            HashSet<string> tmp = e.ChangedType == NIMUserRelationshipChangeType.AddRemoveBlacklist ? _blacklistSet : _mutedlistSet;
            if (e.IsSetted)
                tmp.Add(e.AccountId);
            else
                tmp.Remove(e.AccountId);
            UpdateRelationshipView();
        }

        private void GetUserRelationCompleted(ResponseCode code, UserSpecialRelationshipItem[] list)
        {
            FillRelationshipSet(list);
        }

        private void OnUserRelationshipSync(object sender, UserRelationshipSyncArgs e)
        {
            FillRelationshipSet(e.Items);
        }

        void FillRelationshipSet(NIM.User.UserSpecialRelationshipItem[] items)
        {
            if (items == null)
                return;
            foreach (var item in items)
            {
                if (item.IsBlacklist)
                    _blacklistSet.Add(item.AccountId);
                if (item.IsMuted)
                    _mutedlistSet.Add(item.AccountId);
            }
            UpdateRelationshipView();
        }

        void OnFriendChanged(object sender, NIM.Friend.NIMFriendProfileChangedArgs args)
        {
            if (args.ChangedInfo == null)
                return;
            Action action = () =>
            {
                if (args.ChangedInfo.ChangedType == NIM.Friend.NIMFriendChangeType.kNIMFriendChangeTypeDel)
                {
                    var info = args.ChangedInfo as NIM.Friend.FriendDeletedInfo;
                    _friendsDictionary.Remove(info.AccountId);
                }
                if (args.ChangedInfo.ChangedType == NIM.Friend.NIMFriendChangeType.kNIMFriendChangeTypeRequest)
                {
                    var info = args.ChangedInfo as NIM.Friend.FriendRequestInfo;
                    if (info.VerifyType == NIM.Friend.NIMVerifyType.kNIMVerifyTypeAdd ||
                        info.VerifyType == NIM.Friend.NIMVerifyType.kNIMVerifyTypeAgree)
                    {
                        _friendsDictionary[info.AccountId] = new NIM.Friend.NIMFriendProfile() {AccountId = info.AccountId};
                    }
                }
                if (args.ChangedInfo.ChangedType == NIM.Friend.NIMFriendChangeType.kNIMFriendChangeTypeSyncList)
                {
                    var info = args.ChangedInfo as NIM.Friend.FriendListSyncInfo;
                    foreach (var i in info.ProfileCollection)
                    {

                        _friendsDictionary[i.AccountId] = i;
                    }
                }
                if (args.ChangedInfo.ChangedType == NIM.Friend.NIMFriendChangeType.kNIMFriendChangeTypeUpdate)
                {
                    var info = args.ChangedInfo as NIM.Friend.FriendUpdatedInfo;
                    _friendsDictionary[info.Profile.AccountId] = info.Profile;
                }
                UpdateFriendListView();
            };
            this.Invoke(action);
        }

        void UpdateFriendListView()
        {
            listView1.BeginUpdate();
            foreach (var pair in _friendsDictionary)
            {
                if (listView1.Items.ContainsKey(pair.Key+"_0"))
                {
                    
                }
                else
                {
                    AddFriendListItem(pair.Value);
                }
            }
            for(int i =0 ;i<listView1.Items.Count;i++)
            {
                var item = listView1.Items[i] as ListViewItem;
                if (item.Group != _groups[0])
                    continue;
                if (!_friendsDictionary.ContainsKey(item.Text))
                    listView1.Items.RemoveByKey(item.Name);
            }
            listView1.EndUpdate();
        }

        void UpdateRelationshipView()
        {
            Action action = () =>
            {
                UpdateRelationshipView(_blacklistSet, "1");
                UpdateRelationshipView(_mutedlistSet, "2");
            };
            this.Invoke(action);
        }

        void UpdateRelationshipView(HashSet<string> set, string group)
        {
            listView1.BeginUpdate();
            foreach (var key in set)
            {
                NIM.Friend.NIMFriendProfile profile = null;
                if (_friendsDictionary.ContainsKey(key))
                    profile = _friendsDictionary[key];
                else
                {
                    profile = new NIM.Friend.NIMFriendProfile();
                    profile.AccountId = key;
                }
                if (listView1.Items.ContainsKey(key + "_" + group))
                {
                    listView1.Items.RemoveByKey(key + "_" + group);
                }
                AddFriendListItem(profile, int.Parse(group));
            }
            for (int i = 0; i < listView1.Items.Count; i++)
            {
                var item = listView1.Items[i] as ListViewItem;
                if (item.Group != _groups[int.Parse(group)])
                    continue;
                if (!set.Contains(item.Text))
                    listView1.Items.RemoveByKey(item.Name);
            }
            listView1.EndUpdate();
        }

        void AddFriendListItem(NIM.Friend.NIMFriendProfile profile,int group = 0)
        {
            ListViewItem item = new ListViewItem();
            item.SubItems.Add(profile.AccountId);
            item.Text = profile.AccountId;
            item.Name = string.Format("{0}_{1}", profile.AccountId, group);
            item.Group = _groups[group];
            listView1.Items.Add(item);
        }

        void RegisterClientCallback()
        {
            NIM.ClientAPI.RegDisconnectedCb(() =>
            {
                if (_notifyNetworkDisconnect)
                    MessageBox.Show("掉线了......");
            });

            NIM.ClientAPI.RegKickoutCb((r) =>
            {
                _beKicked = true;
                DemoTrace.WriteLine(r.Dump());
                MessageBox.Show(r.Dump(), "被踢了");
                Action action = () =>
                {
                    LogoutBtn_Click(null, null);
                };
                this.Invoke(action);
            });

            NIM.ClientAPI.RegAutoReloginCb((r) =>
            {
                if (r.Code == ResponseCode.kNIMResSuccess && r.LoginStep == NIMLoginStep.kNIMLoginStepLogin)
                {
                    MessageBox.Show("重连成功");
                }
            });
        } 

        void DisplayMyProfile(NIM.User.UserNameCard card)
        {
            if (card == null)
                return;
            _selfNameCard = card;
            Action action = () =>
            {
                IDLabel.Text = card.AccountId;
                NameLabel.Text = card.NickName;
                SigLabel.Text = card.Signature;
                if (!string.IsNullOrEmpty(card.IconUrl))
                {
                    var stream = System.Net.WebRequest.Create(card.IconUrl).GetResponse().GetResponseStream();
                    if (stream != null)
                        IconPictureBox.Image = Image.FromStream(stream);
                }
            };
            this.Invoke(action);
        }

        private void listView1_DoubleClick(object sender, EventArgs e)
        {
            var x = listView1.SelectedItems;
            if (x != null && x.Count > 0)
            {
                string id = x[0].Text;
                new ChatForm(id).Show();
            }
        }

        private bool _restart = false;
        private void LogoutBtn_Click(object sender, EventArgs e)
        {
            _restart = true;
            this.Close();
        }

        private void listView1_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                if (listView1.SelectedItems.Count > 0)
                {
                    var id = listView1.SelectedItems[0].Text;
                    ContextMenu cm = new ContextMenu();
                    MenuItem i1 = new MenuItem("查看详情", (o, ex) =>
                    {
                        FriendProfileForm card = new FriendProfileForm(id);
                        card.Show();
                    });
                    MenuItem i2 = new MenuItem("删除", (o, ex) =>
                    {
                        NIM.Friend.FriendAPI.DeleteFriend(id, (a, b, c) =>
                        {
                            if (a != 200)
                            {
                                MessageBox.Show("删除失败");
                            }
                        });
                    });
                    bool isBlacklist = _blacklistSet.Contains(id);
                    string m3 = isBlacklist ? "取消黑名单" : "设置黑名单";
                    MenuItem i3 = new MenuItem(m3, (o, ex) =>
                    {
                        NIM.User.UserAPI.SetBlacklist(id, !isBlacklist, (a, b, c) =>
                        {
                            
                        });
                    });

                    bool muted = _mutedlistSet.Contains(id);
                    string m4 = muted ? "取消静音" : "静音";
                    MenuItem i4 = new MenuItem(m4, (o, ex) =>
                    {
                        NIM.User.UserAPI.SetUserMuted(id, !muted, (a, b, c) =>
                        {

                        });
                    });

                    cm.MenuItems.Add(i1);
                    cm.MenuItems.Add(i2);
                    cm.MenuItems.Add(i3);
                    cm.MenuItems.Add(i4);
                    cm.Show(listView1, e.Location);
                }
                else
                {
                    ContextMenu cm = new ContextMenu();
                    MenuItem item = new MenuItem("添加好友", (s, args) =>
                    {
                        Form form = new Form();
                        form.Size = new Size(300, 200);
                        TextBox box = new TextBox();
                        box.Location = new Point(10, 10);
                        box.Size = new Size(160, 50);
                        Button b = new Button();
                        b.Location = new Point(70, 70);
                        b.Text = "添加";
                        form.Controls.Add(box);
                        form.Controls.Add(b);
                        b.Click += (ss, a) =>
                        {
                            if (!string.IsNullOrEmpty(box.Text))
                            {
                                NIM.Friend.FriendAPI.ProcessFriendRequest(box.Text, NIM.Friend.NIMVerifyType.kNIMVerifyTypeAdd, "加我加我",
                                    (aa, bb, cc) =>
                                    {
                                        if (aa != 200)
                                        {
                                            MessageBox.Show("添加失败:" + aa.ToString());
                                        }
                                    });
                            }
                        };
                        form.StartPosition = FormStartPosition.CenterScreen;
                        form.Show();
                    });
                    cm.MenuItems.Add(item);
                    cm.Show(listView1, e.Location);

                }

            }
            
        }

        private void MyProfileBtn_Click(object sender, EventArgs e)
        {
            ObjectPropertyInfoForm form = new ObjectPropertyInfoForm();
            form.Text = "我的信息";
            form.TargetObject = _selfNameCard;
            form.UpdateObjectAction = (o) =>
            {
                NIM.User.UserAPI.UpdateMyCard(o as UserNameCard, (a) =>
                {
                    if (a == ResponseCode.kNIMResSuccess)
                    {
                        UserAPI.GetUserNameCard(new List<string>() {_selfId}, (ret) =>
                        {
                            if (ret.Any())
                            {
                                _selfNameCard = ret[0];
                                DisplayMyProfile(_selfNameCard);
                            }
                        });
                    }
                });
            };
            form.Show();
        }

        private void chatRoomBtn_Click(object sender, EventArgs e)
        {
            var appkey = NimUtility.ConfigReader.GetAppKey();
            NIMChatRoom.AvailableRooms s = new NIMChatRoom.AvailableRooms(appkey);
            var list = s.Search();
            ChatRoomListForm form = new ChatRoomListForm(list);
            form.Show();
            
        }
    }
}
