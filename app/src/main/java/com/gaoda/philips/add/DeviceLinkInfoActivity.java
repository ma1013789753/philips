package com.gaoda.philips.add;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gaoda.base.BaseActivity;
import com.gaoda.broadcastreceiver.WiFiConnectChangedReceiver;
import com.gaoda.listener.WiFIStateChangeListener;
import com.gaoda.manager.Constant;
import com.gaoda.philips.R;
import com.gaoda.rxbus.RxBus;
import com.gaoda.rxbus.RxBusBaseMessage;
import com.gaoda.rxbus.RxCodeConstants;
import com.gaoda.util.BaseUtils;
import com.gaoda.util.StatusBarUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

/**
 * @Created: Suqi
 * @Date: 2018/1/4
 * @Description: 设定连接信息
 */

public class DeviceLinkInfoActivity extends BaseActivity implements WiFIStateChangeListener {

    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private ImageView image_back;
    private TextView tv_next_step;
    private TextView tv_change_net;
    private EditText et_wifi_name;
    private EditText et_wifi_psd;
    private ImageView iv_wifi;
    private LinearLayout ll_info;
    private WiFiConnectChangedReceiver receiver;
    private Context mContext;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private Disposable subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_info);
        mContext = this;
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        register();
        registerRxBus();
        initView();
        initEvent();
    }

    private void register() {
        receiver = new WiFiConnectChangedReceiver();
        receiver.setListener(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    private void setWiFiName() {
        String name = getSSID();
        if (name.equals("<unknown ssid>") || name.equals("0x")) {
            et_wifi_name.setHint(getString(R.string.ConnectionInfo_placeholder_NotSetWifi));
            et_wifi_name.setText("");
        } else {
            et_wifi_name.setText(name);
//            String psd = PublicApplication.getSpInstance().getString(name);
//            if (!BaseUtils.isNullString(psd)) {
//                et_wifi_psd.setText(psd);
//            }
        }
    }

    private void initView() {
        actionbar_back = findViewById(R.id.actionbar_back);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText(getString(R.string.ConnectionInfo_Header_Title));
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        image_back = findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        tv_next_step = findViewById(R.id.tv_next_step);
        tv_change_net = findViewById(R.id.tv_change_net);
        et_wifi_name = findViewById(R.id.et_wifi_name);
        et_wifi_psd = findViewById(R.id.et_wifi_psd);
        ll_info = findViewById(R.id.ll_info);
        iv_wifi = findViewById(R.id.iv_wifi);
    }

    @SuppressLint("CheckResult")
    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());

        RxView.clicks(tv_next_step).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    if (!BaseUtils.isNullString(et_wifi_name.getText().toString().trim())) {
//                        PublicApplication.getSpInstance().setString(et_wifi_name.getText().toString().trim(),
//                                et_wifi_psd.getText().toString().trim());
                        if (BaseUtils.isNullString(et_wifi_psd.getText().toString().trim()) ||
                                et_wifi_psd.getText().toString().trim().length() >= 8) {
                            Intent intent = new Intent(this, DeviceLinkWifiActivity.class);
                            intent.putExtra("ssid", et_wifi_name.getText().toString().trim());
                            intent.putExtra("psd", et_wifi_psd.getText().toString().trim());
                            startActivity(intent);
                        }
                    } else {
//                        BaseUtils.showShortToast(this, getString(R.string.add_please_link_wifi));
                    }
                });

        RxView.clicks(tv_change_net).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                });//直接进入手机中的wifi网络设置界面

        ll_info.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                ll_info.getWindowVisibleDisplayFrame(rect);
                // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                int rootInvisibleHeight = ll_info.getRootView()
                        .getHeight() - rect.bottom;
                if (rootInvisibleHeight > 200) {
                    //软键盘弹出来的时候
                    ObjectAnimator animator = ObjectAnimator.ofFloat(ll_info, "translationY", ll_info.getTranslationY(), -BaseUtils.dip2px(DeviceLinkInfoActivity.this, 200));
                    animator.setDuration(200);
                    animator.start();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_wifi, "alpha", 1f, 0f);
                    animator.setDuration(200);
                    animator1.start();

                } else {
                    // 软键盘没有弹出来的时候
                    ObjectAnimator animator = ObjectAnimator.ofFloat(ll_info, "translationY", ll_info.getTranslationY(), 0);
                    animator.setDuration(200);
                    animator.start();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_wifi, "alpha", 0f, 1f);
                    animator.setDuration(200);
                    animator1.start();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BaseUtils.isNotNull(receiver))
            unregisterReceiver(receiver);
        if (!subscription.isDisposed())
            subscription.dispose();
    }

    @Override
    public void connectedWifi() {
        setWiFiName();
    }

    /**
     * get ssid
     *
     * @return the wifi router which you connected
     */
    public String getSSID() {
        if (mContext != null) {
            mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            mWifiInfo = mWifiManager.getConnectionInfo();
            return mWifiInfo.getSSID().replaceAll("\"", "");
        } else {
            return null;
        }
    }

    private void registerRxBus() {
        subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, RxBusBaseMessage.class).subscribe(rxBusBaseMessage -> {
            switch (rxBusBaseMessage.getCode()) {
                case RxCodeConstants.TYPE_BIND_SUCCESS_FINISH:
                    finish();
                    break;
            }
        });
    }

}
