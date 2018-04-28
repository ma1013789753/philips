package com.gaoda.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gaoda.listener.WiFIStateChangeListener;


/**
 * @Created: Suqi
 * @Date: 2018/1/15
 * @Description:
 */

public class WiFiConnectChangedReceiver extends BroadcastReceiver {

    private WiFIStateChangeListener listener;

    public void setListener(WiFIStateChangeListener listener){
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        listener.connectedWifi();
                        Log.e("suqi", "wifi连接");
                    }
                } else {
                    listener.connectedWifi();
                    Log.e("suqi", "wifi断开");
                }
            }
        }
    }
}
