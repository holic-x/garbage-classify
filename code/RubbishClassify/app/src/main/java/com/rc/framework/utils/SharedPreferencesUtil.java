package com.rc.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


public class SharedPreferencesUtil {

    public static final String TAG = "SharedPreferencesUtil";
    private static final String USER_TOKEN = "USER_TOKEN";
    private static final String USER_ID = "USER_ID";
    private static final String USER_NAME = "USER_NAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String Description = "Description";


    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;
    private final Context context;

    public SharedPreferencesUtil(Context context) {

        this.context = context.getApplicationContext();
        mPreferences =   this.context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil ==null){
            mSharedPreferencesUtil =new SharedPreferencesUtil(context);
        }
        return  mSharedPreferencesUtil;
    }

    public void put(String key, String value) {
        mEditor.putString(key,value);
        mEditor.commit();
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key,value);
        mEditor.commit();
    }

    public String get(String key) {
        return mPreferences.getString(key,"");
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key,defaultValue);
    }

    public void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    // 自用方法
    public  void setToken(String token) {
        put(USER_TOKEN,token);
    }
    public String getToken() {
        return get(USER_TOKEN);
    }

    public void setUserId(String userId) {
        put(USER_ID,userId);
    }
    public String getUserId() {
        return get(USER_ID);
    }

    public  void setUserName(String userName) {
        put(USER_NAME,userName);
    }
    public String getUserName() {
        return get(USER_NAME);
    }

    public  void setPassword(String password) {
        put(PASSWORD,password);
    }
    public String getPassword() {
        return get(PASSWORD);
    }

    public String getDescription() {return get(Description);}
    public void setDescription(String description) {put(Description,description);}


    public boolean isLogin() {

        return !TextUtils.isEmpty(get(USER_TOKEN))&&!TextUtils.isEmpty(get(USER_NAME))
                &&!TextUtils.isEmpty(get(PASSWORD));
    }

    public void logout() {
        put(USER_TOKEN,"");
        put(USER_ID,"");
        put(USER_NAME,"");
        put(PASSWORD,"");
        put(Description,"");
    }


}
