using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NIMDemo
{
    class GlobalCallbacks
    {
        public void Register()
        {
            NIM.ClientAPI.RegAutoReloginCb(_autoReloginCallback);
            NIM.ClientAPI.RegDisconnectedCb(_dissconnectCallback);
            NIM.ClientAPI.RegKickoutCb(_bekickedcallback);
            NIM.ClientAPI.RegKickOtherClientCb(_kickOtherClientCallback);
            NIM.ClientAPI.RegMultiSpotLoginNotifyCb(_multiLoginCallback);

            NIM.Nos.NosAPI.RegDownloadCb(_downloadCallback);

            RegisterRtsCallback();

        }

        void RegisterRtsCallback()
        {
            _rtsNotifyHandlers = new NIM.RtsAPI.RtsNotifyHandlerInfo();
            _rtsNotifyHandlers.onAckNotify = (a, b, c, d) =>
            {
                DemoTrace.WriteLine(a, b, c, d);
            };
            _rtsNotifyHandlers.onConnectNotify = (a, b, c) => { };
            _rtsNotifyHandlers.onControlNotify = (a, b, c) => { };
            _rtsNotifyHandlers.onHangupNotify = (a, b) => { };
            _rtsNotifyHandlers.onMemberNotify = (a, b, c, d) => { };
            _rtsNotifyHandlers.onRecData = (a, b, c, d, e) => { };
            _rtsNotifyHandlers.onStartNotify = (a, b, c, d) => { };
            _rtsNotifyHandlers.onSyncAckNotify = (a, b, c, d) => { };
            NIM.RtsAPI.SetNotifyHandler(_rtsNotifyHandlers);
        }

        void RegisterVChatCallback()
        {
            //NIM.NIMVChatSessionStatus status = new NIM.NIMVChatSessionStatus();
        }

        readonly NIM.ClientAPI.LoginResultDelegate _autoReloginCallback = (result) =>
        {
            
        };

        readonly Action _dissconnectCallback = () => { };

        readonly NIM.ClientAPI.KickoutResultHandler _bekickedcallback = (result) => { DemoTrace.WriteLine(result.Dump()); };
        readonly NIM.ClientAPI.KickOtherClientResultHandler _kickOtherClientCallback = (result) => { DemoTrace.WriteLine(result.Dump()); };
        readonly NIM.ClientAPI.MultiSpotLoginNotifyResultHandler _multiLoginCallback = (result) => { };

        readonly NIM.Nos.DownloadResultHandler _downloadCallback = (response,filePath,cid,resId) => { };

        NIM.RtsAPI.RtsNotifyHandlerInfo _rtsNotifyHandlers;
    }
}
