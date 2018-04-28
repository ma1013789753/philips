package com.gaoda.philips.add;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.gaoda.base.BaseActivity;
import com.gaoda.philips.R;
import com.gaoda.rxbus.RxBus;
import com.gaoda.rxbus.RxBusBaseMessage;
import com.gaoda.rxbus.RxCodeConstants;
import com.gaoda.util.StatusBarUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

/**
 * @Created: Suqi
 * @Date: 2018/1/4
 * @Description: 激活配网
 */

public class DeviceCanLinkActivity extends BaseActivity {

    private Disposable subscription;
    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private TextView tv_socket_bg;
    private ImageView image_back;
    private ImageView device_img;
    private TextView tv_next_step;
    private TextView tv_hint_info;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_can_link);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        registerRxBus();
        initView();
        initEvent();
    }

    private void initView() {
        actionbar_back = findViewById(R.id.actionbar_back);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText(getString(R.string.ActivateNetworking_Header_Title));
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        image_back = findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        device_img = findViewById(R.id.device_img);
        tv_socket_bg = findViewById(R.id.tv_socket_bg);
        tv_next_step = findViewById(R.id.tv_next_step);
        tv_hint_info = findViewById(R.id.tv_hint_info);
//        switch (PublicApplication.getSpInstance().getString(Constant.TYPE_ID)) {
//            case "1102":
//                device_img.setImageResource(R.mipmap.lamp_start_link);
//                tv_hint_info.setText(getString(R.string.ActivateNetworking_Prompt_PowerPlug));
//                break;
//            case "1101":
//                device_img.setImageResource(R.mipmap.socket_start_link);
//                tv_socket_bg.setBackgroundColor(Color.RED);
//                tv_socket_bg.setVisibility(View.VISIBLE);
//                ObjectAnimator animator = ObjectAnimator.ofFloat(tv_socket_bg, "alpha", 1f, 0f);
//                animator.setRepeatCount(ValueAnimator.INFINITE);
//                animator.setRepeatMode(ValueAnimator.RESTART);
//                animator.setDuration(1000);
//                animator.start();
//                tv_hint_info.setText(getString(R.string.ActivateNetworking_Prompt_Bulb));
//                break;
//        }
    }

    @SuppressLint("CheckResult")
    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());

        RxView.clicks(tv_next_step).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> startActivity(new Intent(this, DeviceLinkInfoActivity.class)));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isDisposed())
            subscription.dispose();
    }

}
