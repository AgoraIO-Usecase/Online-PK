package io.agora.pk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import io.agora.pk.model.MyEngineEventHandler;
import io.agora.pk.model.WorkerThread;
import io.agora.pk.utils.PKConstants;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.live.LiveTranscoding;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PKApplication) getApplication()).initWorkerThread();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initUIandEvent();
    }

    protected abstract void initUIandEvent();

    protected abstract void deInitUIandEvent();

    protected RtcEngine rtcEngine() {
        return ((PKApplication) getApplication()).getWorkerThread().rtcEngine();
    }

    protected WorkerThread workThread() {
        return ((PKApplication) getApplication()).getWorkerThread();
    }

    protected final MyEngineEventHandler event() {
        return ((PKApplication) getApplication()).getWorkerThread().eventHandler();
    }

    // set LiveTranscoding property for each user
    protected LiveTranscoding updateLiveTranscoding(int localUid, int remoteUid, HashMap<Integer, Integer> members) {

        ArrayList<LiveTranscoding.TranscodingUser> users = new ArrayList<>(members.size());

        LiveTranscoding.TranscodingUser localUser = new LiveTranscoding.TranscodingUser();

        LiveTranscoding liveTranscoding = new LiveTranscoding();

        // LiveTranscoding update, the LiveTranscoding is used to set the CDN stream layout in Agora server
        // more details please refer to the document
        switch (members.size()) {
            case 1:
                // the LiveTranscoding for one person
                localUser.uid = localUid;

                localUser.x = 0;
                localUser.y = 0;
                localUser.width = PKConstants.LIVE_TRANSCODING_WIDTH;
                localUser.height = PKConstants.LIVE_TRANSCODING_HEIGHT;

                localUser.zOrder = 1;
                localUser.audioChannel = 0;

                liveTranscoding.addUser(localUser);

                liveTranscoding.width = PKConstants.LIVE_TRANSCODING_WIDTH;
                liveTranscoding.height = PKConstants.LIVE_TRANSCODING_HEIGHT;

                liveTranscoding.videoBitrate = PKConstants.LIVE_TRANSCODING_BITRATE;
                liveTranscoding.videoFramerate = PKConstants.LIVE_TRANSCODING_FPS;
                liveTranscoding.lowLatency = true;
                break;

            case 2:
                // the LiveTranscoding for two persons in PK mode
                localUser.uid = localUid;

                localUser.x = 0;
                localUser.y = 0;
                localUser.width = PKConstants.LIVE_TRANSCODING_WIDTH;
                localUser.height = PKConstants.LIVE_TRANSCODING_HEIGHT;

                localUser.zOrder = 1;
                localUser.audioChannel = 0;

                users.add(localUser);

                LiveTranscoding.TranscodingUser remoteUser = new LiveTranscoding.TranscodingUser();

                remoteUser.uid = members.get(remoteUid); // REMOTE USER

                remoteUser.x = PKConstants.LIVE_TRANSCODING_WIDTH; // START FROM END OF THE FIRST USER
                remoteUser.y = 0;
                remoteUser.width = PKConstants.LIVE_TRANSCODING_WIDTH;
                remoteUser.height = PKConstants.LIVE_TRANSCODING_HEIGHT;

                remoteUser.zOrder = 1;
                remoteUser.audioChannel = 0;

                users.add(remoteUser);

                liveTranscoding.setUsers(users);

                liveTranscoding.width = PKConstants.LIVE_TRANSCODING_WIDTH * 2;
                liveTranscoding.height = PKConstants.LIVE_TRANSCODING_HEIGHT;

                liveTranscoding.videoBitrate = PKConstants.LIVE_TRANSCODING_BITRATE;
                liveTranscoding.videoFramerate = PKConstants.LIVE_TRANSCODING_FPS;
                liveTranscoding.lowLatency = true;
                break;

            default:
                break;
        }
        return liveTranscoding;
    }
}
