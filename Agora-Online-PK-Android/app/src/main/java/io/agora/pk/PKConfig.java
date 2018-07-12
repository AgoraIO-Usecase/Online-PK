package io.agora.pk;

public class PKConfig {
    // all of the audience join the broadcaster signal channel
    // if the broadcaster want to pk, he should join another media channel, publish with old channel account
    // and keep the signal channel
    private String broadcasterAccount;
    // just for broadcaster pk to join media channel
    private String pkMediaAccount;

    // if the audience want to see the cdn video, he should input the broadcaster account
    private String audienceSignalAccount;

    public String getBroadcasterAccount() {
        return broadcasterAccount;
    }

    public void setBroadcasterAccount(String broadcasterAccount) {
        this.broadcasterAccount = broadcasterAccount;
    }

    public String getPkMediaAccount() {
        return pkMediaAccount;
    }

    public void setPkMediaAccount(String pkMediaAccount) {
        this.pkMediaAccount = pkMediaAccount;
    }

    public String getAudienceSignalAccount() {
        return audienceSignalAccount;
    }

    public void setAudienceSignalAccount(String audienceSignalAccount) {
        this.audienceSignalAccount = audienceSignalAccount;
    }
}
