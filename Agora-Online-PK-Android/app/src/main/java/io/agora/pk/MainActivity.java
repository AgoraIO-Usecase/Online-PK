package io.agora.pk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import io.agora.pk.utils.PKConstants;
import io.agora.pk.utils.StringUtils;

public class MainActivity extends AppCompatActivity {
    private EditText mEtUserAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtUserAccount = findViewById(R.id.et_main_account_name);
        mEtUserAccount.setText(StringUtils.random(10));
    }

    public void onLoginClicked(View v){
        if (!StringUtils.validate(mEtUserAccount.getText().toString())) {
            Toast.makeText(this, "Please input a account!", Toast.LENGTH_SHORT).show();
            return;
        }

        ((PKApplication)getApplication()).getPkConfig().setBroadcasterAccount(mEtUserAccount.getText().toString());
        Intent intent = new Intent(MainActivity.this, ChatRoomListActivity.class);
        intent.putExtra(PKConstants.BUNDLE_ACCOUNT_NAME, mEtUserAccount.getText().toString());
        startActivity(intent);
    }

}
