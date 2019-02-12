package io.agora.pk;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.agora.pk.utils.PKConstants;
import io.agora.rtc.Constants;

public class MainActivity extends AppCompatActivity {

    private EditText mEtChannel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtChannel = findViewById(R.id.et_channel);
    }

    public void onBroadcastClicked(View v) {
        String channel = mEtChannel.getText().toString();
        if (TextUtils.isEmpty(channel)) {
            Toast.makeText(this, R.string.main_channel_hint, Toast.LENGTH_LONG).show();
            return;
        }

        ((PKApplication) getApplication()).getPkConfig().setBroadcasterAccount(channel);

        if (checkSelfPermissions()) {
            forwardTo(Constants.CLIENT_ROLE_BROADCASTER);
        }
    }

    private void forwardTo(int clientRole) {
        Intent intent = new Intent(MainActivity.this, PKBroadcasterActivity.class);
        intent.putExtra(PKConstants.USER_CLIENT_ROLE, clientRole);
        startActivity(intent);
    }

    private static final int PERMISSION_REQ_ID = 1024;

    @TargetApi(Build.VERSION_CODES.M)
    private void askPermission() {
        requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_REQ_ID);
    }

    private boolean checkSelfPermissions() {
        return checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID) &&
                checkSelfPermission(Manifest.permission.CAMERA, PERMISSION_REQ_ID) &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQ_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_ID) {
            for (int g : grantResults) {
                if (g != PermissionChecker.PERMISSION_GRANTED) {
                    return;
                }
            }
        }
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            askPermission();
            return false;
        }
        return true;
    }
}
