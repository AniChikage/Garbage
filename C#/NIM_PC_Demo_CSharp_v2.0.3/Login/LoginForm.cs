using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using NimUtility;

namespace NIMDemo
{
    public partial class LoginForm : Form
    {
        private AccountCollection _accounts;
        private string _userName;
        private string _password;

        public LoginForm()
        {
            InitializeComponent();
            InitSdk();
            this.Load += (s, e) =>
            {
                OutputForm.Instance.Show();
                InitLoginAccount();
            };
        }

        void InitSdk()
        {
            var config = ConfigReader.GetServerConfig();
            if (!NIM.ClientAPI.Init("NIMCSharpDemo", null, config))
            {
                MessageBox.Show("NIM init failed!");
            }
            if (!NIM.VChatAPI.Init())
            {
                MessageBox.Show("NIM VChatAPI init failed!");
            }
            //sdk init 完成后注册全局回调函数
            GlobalCallbacks callback = new GlobalCallbacks();
            callback.Register();
        }

        private void OnLoginButtonClicked(object sender, EventArgs e)
        {
            _userName = UserNameComboBox.Text;
            _password = textBox2.Text;
            var password = NIM.ToolsAPI.GetMd5(_password);
            if (!string.IsNullOrEmpty(_userName) && !string.IsNullOrEmpty(password))
            {
                toolStripProgressBar1.Value = 0;
                label3.Text = "";
                string appkey = ConfigReader.GetAppKey();
                if (string.IsNullOrEmpty(appkey))
                {
                    MessageBox.Show(@"appkey can't be null,please set it in config.json file");
                    return;
                }
                NIM.ClientAPI.Login(appkey, _userName, password, HandleLoginResult);
            }
        }

        void HandleLogoutResult(NIM.NIMLogoutResult result)
        {
        }

        void HandleLoginResult(NIM.NIMLoginResult result)
        {
            DemoTrace.WriteLine(result.LoginStep.ToString());
            Action action = () =>
            {
                toolStripProgressBar1.PerformStep();

                this.label3.Text = string.Format("{0}  {1}", result.LoginStep, result.Code);
                if (result.LoginStep == NIM.NIMLoginStep.kNIMLoginStepLogin)
                {
                    toolStripProgressBar1.Value = 100;
                    if (result.Code == NIM.ResponseCode.kNIMResSuccess)
                    {
                        this.Hide();
                        new FriendsListForm(_userName).Show();
                        System.Threading.ThreadPool.QueueUserWorkItem((s) =>
                        {
                            SaveLoginAccount();
                        });
                    }
                    else
                    {
                        NIM.ClientAPI.Logout(NIM.NIMLogoutType.kNIMLogoutChangeAccout, HandleLogoutResult);
                    }
                }
            };
            this.Invoke(action);
        }

        void SaveLoginAccount()
        {
            if (_accounts == null)
                _accounts = new AccountCollection();
            var index = _accounts.IndexOf(_userName);
            if (index != -1)
            {
                _accounts.List[index].Password = _password;
                _accounts.LastIndex = index;
            }
            else
            {
                Account account = new Account();
                account.Name = _userName;
                account.Password = _password;
                _accounts.List.Insert(0, account);
                _accounts.LastIndex = 0;
            }
            AccountManager.SaveLoginAccounts(_accounts);
        }

        void InitLoginAccount()
        {
            _accounts = AccountManager.GetAccountList();
            if (_accounts != null)
            {
                foreach (var item in _accounts.List)
                    UserNameComboBox.Items.Add(item.Name);
                UserNameComboBox.Text = _accounts.List[_accounts.LastIndex].Name;
                textBox2.Text = _accounts.List[_accounts.LastIndex].Password;
            }
        }

        private void UserNameComboBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (_accounts == null)
                return;
            var text = UserNameComboBox.Text;
            var account = _accounts.Find(text);
            if (account != null)
                textBox2.Text = account.Password;
        }
    }
}
