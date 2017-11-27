package com.qiu.neteasemusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qiu.neteasemusic.Base.BaseToolbarActivity;
import com.qiu.neteasemusic.Bean.User;
import com.qiu.neteasemusic.Utils.SharedpreferencesUserUtils;
import com.qiu.neteasemusic.Utils.ToastUtil;

import org.json.JSONObject;

/**
 * Created by Colin on 2017/11/20.
 */

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        TextView link_signup = (TextView) findViewById(R.id.link_signup);
        final EditText email = (EditText) findViewById(R.id.input_email);
        final EditText pwd = (EditText) findViewById(R.id.input_password);
        AppCompatButton button = (AppCompatButton) findViewById(R.id.btn_login);
        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class );
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString();
                String pwdStr = pwd.getText().toString();
                JSONObject userJson = null;
                if(emailStr.trim().isEmpty() || pwdStr.trim().isEmpty()){
                    ToastUtil.showToast(LoginActivity.this,R.string.login_empty);
                }else{
                    User user = new User(emailStr,pwdStr);
                    try {
                        userJson = user.toJSON();
                        Log.d("qiu","user_id="+userJson.getString("user_id"));
                        Log.d("qiu","user_pwdu="+userJson.getString("user_pwd"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("qiu","正常="+emailStr+pwdStr);
                    SharedpreferencesUserUtils sharedpreferencesUserUtils = new SharedpreferencesUserUtils(LoginActivity.this);
                    Log.d("qiu","无="+sharedpreferencesUserUtils.getUser().getId());
                    Log.d("qiu","无="+sharedpreferencesUserUtils.getUser().getPwd());
                    sharedpreferencesUserUtils.putUser(userJson);
                    User useruser = sharedpreferencesUserUtils.getUser();

                    Log.d("qiu","shared="+useruser.getId());
                    Log.d("qiu","shared="+useruser.getPwd());
                    ToastUtil.showToast(LoginActivity.this,R.string.login_success);
                }
            }
        });
    }

    private void initData() {
    }

}
