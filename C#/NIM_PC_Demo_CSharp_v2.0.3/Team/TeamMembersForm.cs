using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace NIMDemo.Team
{
    public partial class TeamMembersForm : Form
    {
        public TeamMembersForm()
        {
            InitializeComponent();
            
        }

        private HashSet<string> _memberCollection = new HashSet<string>();

        private Dictionary<string, NIM.Team.NIMTeamMemberInfo> _teamMmebers = new Dictionary<string, NIM.Team.NIMTeamMemberInfo>();
        private string _teamId;
        public TeamMembersForm(string tid)
            : this()
        {
            _teamId = tid;
            this.Load += TeamMembersForm_Load;
        }

        private void TeamMembersForm_Load(object sender, EventArgs e)
        {
            treeView1.MouseUp += TreeView1_MouseUp;
            NIM.Team.TeamAPI.QueryTeamMembersInfo(_teamId, (info) =>
            {
                foreach (var i in info)
                {
                    _memberCollection.Add(i.AccountId);
                    _teamMmebers[i.AccountId] = i;
                }
                UpdateTreeView();
            });
        }

        private void TreeView1_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                var node = treeView1.SelectedNode;
                if (node != null && node.Level > 0)
                {
                    NIM.Team.TeamAPI.QuerySingleMemberInfo(_teamId, node.Name, (info) =>
                    {
                        Action action = () =>
                        {
                            this.flowLayoutPanel1.Controls.Clear();
                            ObjectPropertyInfoForm.CreateFormContent(info, this.flowLayoutPanel1);
                        };
                        this.Invoke(action);
                    });
                }
            }
            if (e.Button == MouseButtons.Right)
            {
                var node = treeView1.SelectedNode;
                if (node != null && node.Level > 0)
                {
                    MenuItem menu = new MenuItem("移出群", (s, args) =>
                    {
                        NIM.Team.TeamAPI.KickMemberOutFromTeam(_teamId, new string[] {node.Name}, (a) =>
                        {
                            if (a.TeamEvent.ResponseCode == NIM.ResponseCode.kNIMResSuccess)
                            {
                                foreach (var id in a.TeamEvent.IdCollection)
                                {
                                    _memberCollection.Remove(id);
                                    _teamMmebers.Remove(id);
                                }
                                UpdateTreeView();
                            }
                            else
                            {
                                MessageBox.Show("操作失败:" + a.TeamEvent.ResponseCode.ToString());
                            }
                        });
                    });
                    ContextMenu m = new ContextMenu();
                    string uid = node.Name;
                    if (_teamMmebers.ContainsKey(uid))
                    {
                        bool normalMember = _teamMmebers[uid].Type == NIM.Team.NIMTeamUserType.kNIMTeamUserTypeNomal;
                        string txt = normalMember ? "设为管理员" : "取消管理员";
                        MenuItem item = new MenuItem(txt, (s, args) =>
                        {
                            if (normalMember)
                                NIM.Team.TeamAPI.AddTeamManagers(_teamId, new string[] { uid }, (ret) =>
                                {

                                });
                            else
                                NIM.Team.TeamAPI.RemoveTeamManagers(_teamId, new string[] { uid }, (ret) =>
                                {

                                });
                        });
                        m.MenuItems.Add(item);
                    }
                    m.MenuItems.Add(menu);
                    m.Show(treeView1, e.Location);
                }
                else
                {
                    MenuItem menu = new MenuItem("邀请入群", (s, args) =>
                    {
                        CreateTeamForm form = new CreateTeamForm(FormType.InviteMemeber);
                        form.MembersIDSelected = (n, i) =>
                        {
                            NIM.Team.TeamAPI.Invite(_teamId, i, n, (r) =>
                            {
                                if (r.TeamEvent.ResponseCode == NIM.ResponseCode.kNIMResSuccess)
                                {
                                    foreach (var id in r.TeamEvent.IdCollection)
                                    {
                                        _memberCollection.Add(id);
                                        NIM.Team.TeamAPI.QuerySingleMemberInfo(_teamId, id, (ret) =>
                                        {
                                            _teamMmebers[id] = ret;
                                        });
                                    }
                                    UpdateTreeView();
                                }
                                else
                                {
                                    MessageBox.Show("操作失败:" + r.TeamEvent.ResponseCode.ToString());
                                }
                            });
                        };
                        form.Show();
                    });
                    ContextMenu m = new ContextMenu();
                    
                    m.MenuItems.Add(menu);
                    m.Show(treeView1, e.Location);
                }
            }
        }

        private TreeNode _rootNode = null;
        void UpdateTreeView()
        {
            Action action = () =>
            {
                treeView1.BeginUpdate();
                if (_rootNode == null)
                {
                    _rootNode = new TreeNode(_teamId);
                    treeView1.Nodes.Add(_rootNode);
                }
                foreach (var i in _memberCollection)
                {
                    if (!_rootNode.Nodes.Find(i, true).Any())
                    {
                        TreeNode node = new TreeNode(i);
                        node.Name = i;
                        _rootNode.Nodes.Add(node);
                    }
                }

                for(int i = 0;i< _rootNode.Nodes.Count;i++)
                {
                    TreeNode tn = _rootNode.Nodes[i];
                    if (!_memberCollection.Contains(tn.Name))
                    {
                        _rootNode.Nodes.RemoveByKey(tn.Name);
                    }
                }
                treeView1.EndUpdate();
            };
            this.Invoke(action);
        }
    }
}
