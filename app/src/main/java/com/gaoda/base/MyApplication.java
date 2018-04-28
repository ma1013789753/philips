package com.gaoda.base;

import android.app.Application;

import com.gaoda.util.SharedPreferencesUtil;
import com.pgyersdk.crash.PgyCrashManager;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private String token;
    private String endUserId;
    public String getUSername() {
        return USername;
    }

    private String USername;

    private static MyApplication instance;




    @Override
    public void onCreate() {
        super.onCreate();
        initJpush();
        PgyCrashManager.register(this);
    }

    private void initJpush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }

    public static MyApplication getInstance(){
        if (instance == null)instance = new MyApplication();
        return  instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getString(String str){
        return (String)SharedPreferencesUtil.getData(this,str,"String");
    }
    public void setString(String str){
        SharedPreferencesUtil.saveData(this,str,"String");
    }

    public void setUSername(String USername) {
        this.USername = USername;
    }

    public String getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(String endUserId) {
        this.endUserId = endUserId;
    }
}
