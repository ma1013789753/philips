package com.gaoda.philips.add;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.philips.R;
import com.gaoda.rxbus.RxBus;
import com.gaoda.rxbus.RxBusBaseMessage;
import com.gaoda.rxbus.RxCodeConstants;
import com.gaoda.util.BaseUtils;
import com.gaoda.util.StatusBarUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


/**
 * @Created: Suqi
 * @Date: 2018/1/31
 * @Description: 绑定成功
 */

public class BindSuccessActivity extends BaseActivity {

    private static final int RESET_DEVICE_NAME_SUCCESS = 100;
    private static final int RESET_DEVICE_NAME_FAIL = 200;
    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private ImageView image_back;
    private TextView tv_sure;
    private TextView et_device_name;
    private LinearLayout ll_content;
    private ScrollView scrollView_fail;
    private TextView tv_retry;
    private ImageView iv_success;
    private String device_id;
    private int result;

    private Handler myHandler = new Handler(msg -> {
        switch (msg.what) {
            case RESET_DEVICE_NAME_SUCCESS:
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                        new RxBusBaseMessage(RxCodeConstants.TYPE_RESET_DEVICE_NAME_SUCCESS, new Object()));
                finish();
                break;
            case RESET_DEVICE_NAME_FAIL:
                BaseUtils.showShortToast(BindSuccessActivity.this, msg.obj.toString());
                break;
            default:
                break;
        }
        return true;
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_success);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        result = getIntent().getIntExtra("result", 0);
        device_id = getIntent().getStringExtra("device_id");
        initView();
        initEvent();
        //        deviceLocation();
    }

    private void initView() {
        actionbar_back = (LinearLayout) findViewById(R.id.actionbar_back);
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        et_device_name = (TextView) findViewById(R.id.et_device_name);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        iv_success = (ImageView) findViewById(R.id.iv_success);
        scrollView_fail = (ScrollView) findViewById(R.id.scrollView_fail);
        tv_retry = (TextView) findViewById(R.id.tv_retry);
        if (result == 1) {
            ll_content.setVisibility(View.VISIBLE);
            actionbar_title.setText(getString(R.string.AddDeviceSuccess_Header_Title));
        } else {
            scrollView_fail.setVisibility(View.VISIBLE);
            actionbar_title.setText(getString(R.string.AddDeviceFail_Header_Title));
        }
    }

    @SuppressLint("CheckResult")
    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
        RxView.clicks(tv_sure).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> setName());
        RxView.clicks(tv_retry).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
        ll_content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                ll_content.getWindowVisibleDisplayFrame(rect);
                // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                int rootInvisibleHeight = ll_content.getRootView()
                        .getHeight() - rect.bottom;
                if (rootInvisibleHeight > 200) {
                    //软键盘弹出来的时候
                    ObjectAnimator animator = ObjectAnimator.ofFloat(ll_content, "translationY", ll_content.getTranslationY(), -BaseUtils.dip2px(BindSuccessActivity.this, 150));
                    animator.setDuration(200);
                    animator.start();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_success, "alpha", 1f, 0f);
                    animator.setDuration(200);
                    animator1.start();

                } else {
                    // 软键盘没有弹出来的时候
                    ObjectAnimator animator = ObjectAnimator.ofFloat(ll_content, "translationY", ll_content.getTranslationY(), 0);
                    animator.setDuration(200);
                    animator.start();
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_success, "alpha", 0f, 1f);
                    animator.setDuration(200);
                    animator1.start();
                }
            }
        });
    }

    //    public void deviceLocation() {
    //        PublicApplication.getFog().deviceLocation(device_id, new FogCallBack() {
    //            @Override
    //            public void onSuccess(String message) {
    //                LogUtil.d("suqi", message);
    //            }
    //
    //            @Override
    //            public void onFailure(int code, String message) {
    //
    //            }
    //
    //            @Override
    //            public void onLinkException(Exception e) {
    //
    //            }
    //
    //            @Override
    //            public void onReRequest() {
    //
    //            }
    //        }, PublicApplication.getToken());
    //    }
    private void modifyName(String name) {
        HttpModleUtil.modifyName(this, device_id, name, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                        new RxBusBaseMessage(RxCodeConstants.TYPE_RESET_DEVICE_NAME_SUCCESS, new Object()));
                finish();

            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }
    public void setName() {
        if (BaseUtils.isNullString(et_device_name.getText().toString().trim())) {
            finish();
        } else {
            modifyName(et_device_name.getText().toString().trim());

            //            PublicApplication.getFog().updateDeviceAlias(device_id, et_device_name.getText().toString().trim(),
            //                    new FogCallBack() {
            //                        @Override
            //                        public void onSuccess(String message) {
            //                            LogUtil.d("suqi", message);
            //                            if (BaseUtils.getCode(message) == 0) {
            //                                BaseUtils.sendMessage(myHandler, RESET_DEVICE_NAME_SUCCESS, "");
            //                            } else {
            //                                BaseUtils.sendMessage(myHandler, RESET_DEVICE_NAME_FAIL, getString(R.string.add_set_devices_name_fail));
            //                            }
            //                        }
            //
            //                        @Override
            //                        public void onFailure(int code, String message) {
            //                            BaseUtils.sendMessage(myHandler, RESET_DEVICE_NAME_FAIL, getString(R.string.add_set_devices_name_fail));
            //                        }
            //
            //                        @Override
            //                        public void onLinkException(Exception e) {
            //                            BaseUtils.sendMessage(myHandler, RESET_DEVICE_NAME_FAIL, getString(R.string.add_set_devices_name_fail));
            //                        }
            //
            //                        @Override
            //                        public void onReRequest() {
            //                            setName();
            //                        }
            //                    }, PublicApplication.getToken());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacksAndMessages(null);
    }
}
