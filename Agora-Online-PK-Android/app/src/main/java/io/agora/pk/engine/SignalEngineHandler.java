package io.agora.pk.engine;

import java.util.concurrent.ConcurrentHashMap;

import io.agora.agoramessagetubekit.AbstractMTKCallback;

public class SignalEngineHandler {
    ConcurrentHashMap<Integer, ISignalEngineHandler> handler = new ConcurrentHashMap<>();

    public void addSignalCallback(ISignalEngineHandler callback) {
        handler.put(0, callback);
    }

    public ISignalEngineHandler getSignalCallback() {
        return handler.get(0);
    }

    protected AbstractMTKCallback callback = new AbstractMTKCallback() {
        @Override
        public void onChannelJoined(String channelID) {
            super.onChannelJoined(channelID);
            if (null != handler.get(0))
                handler.get(0).onChannelJoined(channelID);
        }

        @Override
        public void onChannelJoinFailed(String channelID, int ecode) {
            super.onChannelJoinFailed(channelID, ecode);
            if (null != handler.get(0))
                handler.get(0).onChannelJoinFailed(channelID, ecode);
        }

        @Override
        public void onChannelUserJoined(String account, int uid) {
            super.onChannelUserJoined(account, uid);
            if (null != handler.get(0))
                handler.get(0).onChannelUserJoined(account, uid);
        }

        @Override
        public void onMessageInstantReceive(String account, int uid, String msg) {
            super.onMessageInstantReceive(account, uid, msg);
//            if (null != handler.get(0))
//                handler.get(0).onMessageInstantReceive(account, uid, msg);
        }

        public void onMarkedMessageInstantReceive(String account, int uid, String msg) {
            if (null != handler.get(0))
                handler.get(0).onMessageInstantReceive(account, uid, msg);
        }

        @Override
        public void onMessageChannelReceive(String channelID, String account, int uid, String msg) {
            super.onMessageChannelReceive(channelID, account, uid, msg);
        }

        @Override
        public void onMarkedMessageChannelReceive(String channelID, String account, int uid, String msg) {
            super.onMarkedMessageChannelReceive(channelID, account, uid, msg);
            if (null != handler.get(0))
                handler.get(0).onMessageChannelReceive(channelID, account, uid, msg);
        }

        @Override
        public void onError(String name, int ecode, String desc) {
            super.onError(name, ecode, desc);
            if (null != handler.get(0))
                handler.get(0).onError(name, ecode, desc);
        }

        @Override
        public void onLogout(int ecode) {
            super.onLogout(ecode);
            if (null != handler.get(0))
                handler.get(0).onLogout(ecode);
        }
    };
}
