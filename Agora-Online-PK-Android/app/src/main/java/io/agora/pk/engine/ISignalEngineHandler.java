package io.agora.pk.engine;

public interface ISignalEngineHandler {
    void onChannelJoined(String channelID);

    void onChannelJoinFailed(String channelID, int ecode);

    void onChannelUserJoined(String account, int uid);

    void onMessageInstantReceive(String account, int uid, String msg);

    void onMessageChannelReceive(String channelID, String account, int uid, String msg);

    void onError(String name, int ecode, String desc);

    void onLogout(int ecode);
}
