package com.qiu.neteasemusic.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Config;

import com.framework.io.Constants;
import com.qiu.neteasemusic.Bean.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Colin on 2017/11/20.
 * 用来保存用户名和密码
 */

public class SharedpreferencesUserUtils {
    private Context context;
    private SharedPreferences sharedPreferences;
    private static final String user_id = "user_id";
    private static final String user_pwd = "user_pwd";
    private static final String key_shared = "xiaomi_qiu_ceshi_12138";
    public SharedpreferencesUserUtils(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(key_shared,Context.MODE_PRIVATE);
    }
    public void putUser(JSONObject jsonObject){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putString(user_id,jsonObject.getString(user_id));
            editor.putString(user_pwd,jsonObject.getString(user_pwd));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.commit();
    }
    public User getUser(){
        User user = new User(sharedPreferences.getString(user_id , com.qiu.neteasemusic.Config.Config.NULL),
                sharedPreferences.getString(user_pwd , com.qiu.neteasemusic.Config.Config.NULL));
        return user;
    }
}
