package com.gaoda.philips.add;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaoda.base.BaseActivity;
import com.gaoda.bean.QrBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.log.LogUtil;
import com.gaoda.philips.R;
import com.gaoda.rxbus.RxBus;
import com.gaoda.rxbus.RxBusBaseMessage;
import com.gaoda.rxbus.RxCodeConstants;
import com.gaoda.util.BaseUtils;
import com.gaoda.util.StatusBarUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.rxbinding2.view.RxView;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.disposables.Disposable;


/**
 * @author: SuQi
 * @date: 2017/6/8
 * @project: 二维码扫描
 * @detail:
 */
public class ScanQrCodeActivity extends BaseActivity {
    private static final int CAMERA_REQUEST_CODE = 303;
    private static final int ANALYSIS_FAIL = 200;
    private static final int BIND_SUCCESS = 300;
    private static final int BIND_FAIL = 400;
    private static final int GET_PRODUCT_TYPE_SUCCESS = 500;

    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private ImageView image_back;
    private TextView tv_to_next;
    private Disposable subscription;
    private String vercode;
    private String deviceid;
    CaptureFragment captureFragment;

    private Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case ANALYSIS_FAIL:
                reScan();
                BaseUtils.showShortToast(ScanQrCodeActivity.this, msg.obj.toString());
                break;
            case BIND_SUCCESS:
//                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
//                            new RxBusBaseMessage(RxCodeConstants.TYPE_BIND_SUCCESS_WITH_SQRCODE, new Object()));
                BaseUtils.showShortToast(ScanQrCodeActivity.this, msg.obj.toString());
                finish();
                break;
            case BIND_FAIL:
                reScan();
                BaseUtils.showShortToast(ScanQrCodeActivity.this, msg.obj.toString());
                break;
            case GET_PRODUCT_TYPE_SUCCESS:
                String product_id = JSON.parseObject(msg.obj.toString()).getString("product_id");
                String type_id = JSON.parseObject(msg.obj.toString()).getString("type_id");
//                PublicApplication.getSpInstance().setString(Constant.PRODUCT_ID, product_id);
//                PublicApplication.getSpInstance().setString(Constant.TYPE_ID, type_id);
                startActivity(new Intent(ScanQrCodeActivity.this, DeviceCanLinkActivity.class));
                finish();
                break;
        }
        return true;
    });

//    private void bindDevice(String vercode, String deviceid) {
//        PublicApplication.getFog().addDeviceByVerCode(deviceid, vercode, "", new FogCallBack() {
//            @Override
//            public void onSuccess(String message) {
//                if (BaseUtils.getCode(message) == 0) {
//                    BaseUtils.sendMessage(handler, BIND_SUCCESS, getString(R.string.add_bind_success));
//                } else if (BaseUtils.getCode(message) == 10400){
//                    BaseUtils.sendMessage(handler, BIND_FAIL, getString(R.string.add_qrcode_overtime));
//                } else if (BaseUtils.getCode(message) == 10050){
//                    BaseUtils.sendMessage(handler, BIND_FAIL, getString(R.string.add_bind_over_people));
//                } else {
//                    BaseUtils.sendMessage(handler, BIND_FAIL, getString(R.string.add_bind_fail));
//                }
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                BaseUtils.sendMessage(handler, BIND_FAIL, getString(R.string.add_bind_fail));
//            }
//
//            @Override
//            public void onLinkException(Exception e) {
//                BaseUtils.sendMessage(handler, BIND_FAIL, getString(R.string.add_bind_fail));
//            }
//
//            @Override
//            public void onReRequest() {
//                bindDevice(vercode, deviceid);
//            }
//
//        }, PublicApplication.getToken());
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);
        //执行扫描Fragment的初始化操作
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera_view);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        //替换扫描控件
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_view, captureFragment).commit();
        initView();
        initEvent();
        registerRxBus();
    }



    private void initView() {
        actionbar_back = findViewById(R.id.actionbar_back);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText(getString(R.string.AddDevice_Header_Title));
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        image_back = findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        tv_to_next = findViewById(R.id.tv_to_next);
    }

    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
        RxView.clicks(tv_to_next).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> startActivity(new Intent(ScanQrCodeActivity.this, DeviceLinkInfoActivity.class)));

    }


    //二维码解析回调函数
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            //            BaseUtils.sendMessage(handler, ANALYSIS_SUCCESS, result);
            LogUtil.w(result+"");
            try {
                QrBean bean = new Gson().fromJson(result,QrBean.class);
                bindDevice(bean);

            }catch (JsonSyntaxException e){
                BaseUtils.showShortToast(ScanQrCodeActivity.this, getString(R.string.AddDevice_Toast_NotSpecifyCode)
                .replace("******", getString(R.string.app_name)));
                reScan();
            }

//            Pattern p = Pattern.compile("^https\\:\\/\\/app\\.fogcloud\\.io\\/0b4c4e80f73f11e7804bfa163e431402\\/\\?model\\=(\\w+)$");
//            Matcher m = p.matcher(result);
//            if (m.matches()) {
//                //网络请求
//                String model = result.substring((result.indexOf("model=") + 6), result.length());
////                getProductType(model);
//            } else {
//
//                p = Pattern.compile("^https\\:\\/\\/app\\.fogcloud\\.io\\/0b4c4e80f73f11e7804bfa163e431402\\/\\?deviceid\\=\\w+\\&vercode=[\\w\\-]+$");
//                m = p.matcher(result);
//                if (m.matches()) {
//                    p = Pattern.compile("deviceid\\=(\\w+)\\&");
//                    m = p.matcher(result);
//                    if (m.find()) {
//                        deviceid = m.group(1);
//                    }
//                    vercode = result.substring(result.indexOf("vercode=") + 8, result.length());
////                    bindDevice(vercode, deviceid);
//                } else {
//                    BaseUtils.showShortToast(ScanQrCodeActivity.this, getString(R.string.AddDevice_Toast_NotSpecifyCode)
//                            .replace("******", getString(R.string.app_name)));
//                    reScan();
//                }
//            }
        }
        @Override
        public void onAnalyzeFailed() {
                        BaseUtils.sendMessage(handler, ANALYSIS_FAIL, "解析失败");
        }
    };
    private void bindDevice( QrBean bean){
        HttpModleUtil.adminCode(this, bean.getDeviceId(), bean.getVercode(), JPushInterface.getRegistrationID(this),new JsonCallBack() {
            @Override
            public void onSuccess(Response<org.json.JSONObject> response) {
                org.json.JSONObject json = response.body();
                try {
                    if(0 == json.getJSONObject("meta").getInt("code")){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Response<org.json.JSONObject> response) {

            }
        });
    }
//    private void getProductType(String model) {
//
//        PublicApplication.getFog().getProductType(model, new FogCallBack() {
//            @Override
//            public void onSuccess(String message) {
//                if (BaseUtils.getCode(message) == 0) {
//                    String data = JSON.parseObject(message).getString("data");
//                    BaseUtils.sendMessage(handler, GET_PRODUCT_TYPE_SUCCESS, data);
//                }
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                reScan();
//            }
//
//            @Override
//            public void onLinkException(Exception e) {
//                reScan();
//            }
//
//            @Override
//            public void onReRequest() {
//                getProductType(model);
//            }
//        }, PublicApplication.getToken());
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isDisposed())
            subscription.dispose();
        handler.removeCallbacksAndMessages(null);
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

    //重新扫描
    private void reScan() {
        Message restartMessage = new Message();
        restartMessage.what = com.uuzuche.lib_zxing.R.id.restart_preview;
        captureFragment.getHandler().sendMessageDelayed(restartMessage, 2000);
    }


}
