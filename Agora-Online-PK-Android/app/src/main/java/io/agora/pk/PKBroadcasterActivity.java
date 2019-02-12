package io.agora.pk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import io.agora.pk.model.AGEventHandler;
import io.agora.pk.utils.PKConstants;
import io.agora.pk.utils.StringUtils;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

public class PKBroadcasterActivity extends BaseActivity implements AGEventHandler {

    private static final String TAG = "PKBroadcaster";

    private int mClientRole;

    private FrameLayout mFLSingleView;

    private FrameLayout mFLPKViewLeft;
    private FrameLayout mFLPKViewRight;

    private FrameLayout mFLPKMidBoard;

    private Button mBtnExitPk;

    private boolean isPKnow = false;
    private boolean isBroadcaster = false;

    private int mLocalUid = 0;
    private int mRemoteUid = 0;
    private HashMap<Integer, Integer> mUserList = new HashMap<>();

    private SurfaceView localView;
    private SurfaceView remoteView;

    private TextView mTvStartPk;

    private Button mBtnVCopyRtmpPullUrl;
    private TextView mTvRtmpPullUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_broadcaster);

        mClientRole = getIntent().getIntExtra(PKConstants.USER_CLIENT_ROLE, Constants.CLIENT_ROLE_AUDIENCE);
    }

    @Override
    protected void initUIandEvent() {
        mFLSingleView = findViewById(R.id.fl_chat_room_main_video_view);
        mFLPKViewLeft = findViewById(R.id.fl_chat_room_main_pk_board_left);
        mFLPKViewRight = findViewById(R.id.fl_chat_room_main_pk_board_right);
        mFLPKMidBoard = findViewById(R.id.fl_chat_room_main_pk_board);
        mTvStartPk = findViewById(R.id.et_chat_room_main_start_pk);
        mBtnExitPk = findViewById(R.id.btn_main_pk_exit_pk);

        mBtnVCopyRtmpPullUrl = findViewById(R.id.btn_copy_rtmp_pull_url);
        mBtnVCopyRtmpPullUrl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                copyRtmpPullUrl();
            }
        });
        mTvRtmpPullUrl = findViewById(R.id.tv_rtmp_pull_url);

        initEngine();
    }

    public void initEngine() {
        event().addEventHandler(this);

        workThread().configEngine(mClientRole);
        if (mClientRole == Constants.CLIENT_ROLE_BROADCASTER) {
            isBroadcaster = true;
            workThread().joinChannel(((PKApplication) getApplication()).getPkConfig().getBroadcasterAccount(), 0);
        } else if (mClientRole == Constants.CLIENT_ROLE_AUDIENCE) {
            isBroadcaster = false;
        }
        changeViewToSingle();
        localView = RtcEngine.CreateRendererView(this);
        remoteView = RtcEngine.CreateRendererView(this);
    }

    @Override
    protected void deInitUIandEvent() {

    }

    public void onBackClicked(View v) {
        if (isBroadcaster) {
            removePublishUrl();
            workThread().leaveChannel();
        }

        mUserList.clear();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackClicked(null);
    }

    // exit pk
    public void onExitPKClicked(View v) {
        isPKnow = false;

        if (remoteView.getParent() != null)
            ((ViewGroup) (remoteView.getParent())).removeAllViews();

        ((PKApplication) getApplication()).getPkConfig().setPkMediaAccount("");

        mUserList.clear();
        removePublishUrl();
        workThread().leaveChannel();
        workThread().joinChannel(((PKApplication) getApplication()).getPkConfig().getBroadcasterAccount(), 0);
        changeViewToSingle();
    }

    // start pk, input a room channel to start pk
    public void onStartPKClicked(View v) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_view_pk, null);
        alertDialog.setView(rootView);
        final AlertDialog dialog = alertDialog.create();
        if (null != dialog.getWindow())
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        Button btn = rootView.findViewById(R.id.btn_start_pk);
        final EditText et = rootView.findViewById(R.id.et_pk_channel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.validate(et.getText().toString())) {
                    Toast.makeText(PKBroadcasterActivity.this, "please input a channel account", Toast.LENGTH_SHORT).show();
                    return;
                }

                isPKnow = true;
                ((PKApplication) getApplication()).getPkConfig().setPkMediaAccount(et.getText().toString());
                removePublishUrl();
                workThread().leaveChannel();
                mUserList.clear();
                workThread().joinChannel(((PKApplication) getApplication()).getPkConfig().getPkMediaAccount(), 0);
                dialog.dismiss();
            }
        });
    }

    public void changeViewToSingle() {
        mFLPKMidBoard.setVisibility(View.INVISIBLE);
        mFLSingleView.setVisibility(View.VISIBLE);

        mFLSingleView.setBackgroundColor(Color.BLACK);
        if (isBroadcaster)
            mTvStartPk.setVisibility(View.VISIBLE);
        else {
            mTvStartPk.setVisibility(View.INVISIBLE);
        }
    }

    public void changeViewToPkBroadcaster() {
        mFLSingleView.setVisibility(View.INVISIBLE);
        mFLPKMidBoard.setVisibility(View.VISIBLE);
        mTvStartPk.setVisibility(View.VISIBLE);

        mFLPKViewRight.setVisibility(View.VISIBLE);
        mFLPKViewLeft.setVisibility(View.VISIBLE);
        mBtnExitPk.setVisibility(View.VISIBLE);
    }

    public void setLocalPreviewView(int uid) {
        workThread().preview(true, localView, uid);

        if (mFLSingleView.getChildCount() > 0) {
            mFLSingleView.removeAllViews();
        }

        if (localView.getParent() != null)
            ((ViewGroup) (localView.getParent())).removeAllViews();

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        localView.setZOrderOnTop(false);
        localView.setZOrderMediaOverlay(false);
        localView.setLayoutParams(lp);
        mFLSingleView.addView(localView);
    }

    public void setLocalPkLeftView(int uid) {
        workThread().preview(true, localView, uid);

        if (mFLPKViewLeft.getChildCount() > 0)
            mFLPKViewLeft.removeAllViews();

        if (localView.getParent() != null)
            ((ViewGroup) (localView.getParent())).removeAllViews();

        mFLPKViewLeft.addView(localView);
    }

    public void setRemotePkRightView(int uid) {
        if (mFLPKViewRight.getChildCount() > 0)
            mFLPKViewRight.removeAllViews();

        if (remoteView.getParent() != null)
            ((ViewGroup) (remoteView.getParent())).removeAllViews();

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        remoteView.setZOrderOnTop(false);
        remoteView.setZOrderMediaOverlay(false);
        remoteView.setLayoutParams(lp);

        rtcEngine().setupRemoteVideo(new VideoCanvas(remoteView, Constants.RENDER_MODE_HIDDEN, uid));
        mFLPKViewRight.addView(remoteView);
    }

    @Override
    public void onJoinChannelSuccess(final String channel, final int uid, int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onJoinChannelSuccess channel = " + channel + " uid = " + (uid & 0XFFFFFFFFL));

                mLocalUid = uid;
                mUserList.put(mLocalUid, mLocalUid);
                if (isPKnow) {
                    changeViewToPkBroadcaster();
                    setLocalPkLeftView(uid);
                } else {
                    changeViewToSingle();
                    setLocalPreviewView(uid);
                }

                // start CDN Streaming
                setLiveTranscoding();
                publishUrl();
            }
        });
    }

    @Override
    public void onUserJoined(final int uid, int elapsed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mUserList.size() < PKConstants.MAX_PK_COUNT) {
                    mRemoteUid = uid;
                    mUserList.put(uid, uid);
                    setLiveTranscoding();
                    setRemotePkRightView(uid);
                }
            }
        });

    }

    @Override
    public void onStreamPublished(String url, int error) {
    }

    @Override
    public void onStreamUnpublished(String url) {
    }

    @Override
    public void onError(int err) {
    }

    @Override
    public void onUserOffline(final int uid, int reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mUserList.keySet().contains(uid)) {
                    mUserList.remove(uid);
                    onExitPKClicked(null);
                    setLiveTranscoding();
                }
            }
        });

    }

    @Override
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
    }

    private String rtmpPullUrl() {
        return PKConstants.PUBLISH_PULL_URL + ((PKApplication) getApplication()).getPkConfig().getBroadcasterAccount();
    }

    private void copyRtmpPullUrl() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", rtmpPullUrl());
        cm.setPrimaryClip(mClipData);

        Toast.makeText(this, R.string.already_copied, Toast.LENGTH_LONG).show();
    }

    public void publishUrl() {
        rtcEngine().addPublishStreamUrl(PKConstants.PUBLISH_URL + ((PKApplication) getApplication()).getPkConfig().getBroadcasterAccount(), true);

        mTvRtmpPullUrl.setText(rtmpPullUrl());
    }

    public void removePublishUrl() {
        rtcEngine().removePublishStreamUrl(PKConstants.PUBLISH_URL + ((PKApplication) getApplication()).getPkConfig().getBroadcasterAccount());
    }

    public void setLiveTranscoding() {
        rtcEngine().setLiveTranscoding(updateLiveTranscoding(mLocalUid, mRemoteUid, mUserList));
    }

}
