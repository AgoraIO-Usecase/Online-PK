package io.agora.pk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.agora.pk.bean.CRLBean;
import io.agora.pk.ui.CRLItemDecor;
import io.agora.pk.ui.CRLRecycleAdapter;
import io.agora.pk.utils.ChatRoomListCreator;
import io.agora.pk.utils.PKConstants;
import io.agora.pk.utils.StringUtils;
import io.agora.rtc.Constants;

public class ChatRoomListActivity extends Activity implements ChatRoomListCreator.CreatorListener {
    private RecyclerView mRvChatRoomList;
    private CRLRecycleAdapter mRVAdapter;

    private boolean hasPermission = false;

    private ChatRoomListCreator crlc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_list);

        crlc = new ChatRoomListCreator(new WeakReference<Context>(this), this);
        if (checkSelfPermissions()) {
            hasPermission = true;
            crlc.execute();
        }
    }

    public void onBroadcastClicked(View v) {
        forwardTo(Constants.CLIENT_ROLE_BROADCASTER);
    }

    public void onWatchClicked(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        View rootView = LayoutInflater.from(this).inflate(R.layout.pop_view_watch, null);
        alertDialog.setView(rootView);
        AlertDialog dialog = alertDialog.create();
        if (null != dialog.getWindow())
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();

        Button btn = rootView.findViewById(R.id.btn_start_watch);
        final EditText et = rootView.findViewById(R.id.et_watch_channel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.validate(et.getText().toString())) {
                    Toast.makeText(ChatRoomListActivity.this, "Please input text", Toast.LENGTH_SHORT).show();
                    return;
                }

                ((PKApplication) getApplication()).getPkConfig().setAudienceSignalAccount(et.getText().toString());
                forwardTo(Constants.CLIENT_ROLE_AUDIENCE);
            }
        });

    }

    private void forwardTo(int clintRole) {
        if (hasPermission) {
            Intent intent = new Intent(ChatRoomListActivity.this, ChatRoomActivity.class);
            intent.putExtra(PKConstants.USER_CLIENT_ROLE, clintRole);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Permission limited", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void creatFinished(List<CRLBean> beans) {
        mRvChatRoomList = findViewById(R.id.rv_chat_room_list_recycler_view);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rv_view_load);
        mRvChatRoomList.setAnimation(animation);
        mRVAdapter = new CRLRecycleAdapter(this, beans);
        mRvChatRoomList.setFocusable(true);
        mRvChatRoomList.setHasFixedSize(true);

        mRvChatRoomList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvChatRoomList.setAdapter(mRVAdapter);
        mRvChatRoomList.setDrawingCacheEnabled(true);
        mRvChatRoomList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        mRvChatRoomList.addItemDecoration(new CRLItemDecor());
    }

    private boolean checkSelfPermissions() {
        return checkSelfPermission(Manifest.permission.RECORD_AUDIO, 200) &&
                checkSelfPermission(Manifest.permission.CAMERA, 201) &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 202);
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, 201);
                } else {
                    finish();
                }
                break;
            }
            case 201: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 202);
                    hasPermission = true;
                    crlc.execute();
                } else {
                    finish();
                }
                break;
            }
            case 202: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                }
                break;
            }
        }
    }
}
