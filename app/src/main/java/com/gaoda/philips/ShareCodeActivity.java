package com.gaoda.philips;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.base.MyApplication;
import com.gaoda.bean.QrBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareCodeActivity extends BaseActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.menubar)
    ImageView menubar;
    @BindView(R.id.qr_image)
    ImageView qrImage;
    String deviceId ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ercode_dialog);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        deviceId = getIntent().getStringExtra("deviceId");
        initData();
//        initView();
    }

    private void initData() {
        HttpModleUtil.shareCode(this, deviceId, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                    return;
                }
                JSONObject json = response.body();
                try {
                    if(0 == json.getJSONObject("meta").getInt("code")){
                        String shareCode = json.getJSONObject("data").getString("vercode");
                        initView(shareCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }

    private void initView(String shareCode) {
        QrBean bean = new QrBean();
        bean.setUserId(MyApplication.getInstance().getUSername());
        bean.setDeviceId(deviceId);
        bean.setVercode(shareCode);
        bean.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Bitmap image1 = CodeUtils.createImage(new Gson().toJson(bean), 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        qrImage.setImageBitmap(image1);


    }

    @OnClick({R.id.backbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backbar:
                finish();
                break;
        }
    }
}
