package com.gaoda.philips;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.base.MyApplication;
import com.gaoda.bean.DeviceBean;
import com.gaoda.bean.DeviceDetailBean;
import com.gaoda.bean.QrBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.rxbus.RxBus;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceDetailActivity extends BaseActivity

{

    public static final int UNBIND_CODE = 0x1024;
    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.sharelay)
    LinearLayout sharelay;
    @BindView(R.id.usercount)
    TextView usercount;
    @BindView(R.id.userlay)
    LinearLayout userlay;
    @BindView(R.id.devicename)
    TextView devicename;
    @BindView(R.id.devicelay)
    LinearLayout devicelay;
    @BindView(R.id.deviceid)
    TextView deviceid;
    @BindView(R.id.idlay)
    LinearLayout idlay;
    @BindView(R.id.gujian)
    TextView gujian;
    @BindView(R.id.gujianlay)
    LinearLayout gujianlay;
    @BindView(R.id.d_name)
    TextView dName;
    @BindView(R.id.d_time)
    TextView dTime;
    @BindView(R.id.unbind)
    Button unbind;
    private DeviceBean beans ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicedetail);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.deeplilac);
        beans = (DeviceBean) getIntent().getSerializableExtra("bean");
        initData();
    }

    private void initData() {
        HttpModleUtil.deviceDetail(this, beans.getDevice_info().getDevice_id(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                    return;
                }
                JSONObject json = response.body();
                Gson gson = new Gson();
                try {
                    DeviceDetailBean bean = gson.fromJson(json.getString("data"), DeviceDetailBean.class);
                    initView(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }

        });
    }

    private void initView(DeviceDetailBean bean) {
        usercount.setText(bean.getD_count() + "");
        deviceid.setText(bean.getDevice_id());
        dTime.setText("Joind Time：" + bean.getActivate_time());
        dName.setText(beans.getDevice_alias());
        devicename.setText(beans.getDevice_alias());
    }

    AlertDialog dialog = null;

    private void showCusDialog() {

        View view = getLayoutInflater().inflate(R.layout.dialog_modifyname, null);
        final EditText editText = view.findViewById(R.id.name);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView sure = view.findViewById(R.id.sure);
        cancel.setOnClickListener(v -> dialog.dismiss());
        sure.setOnClickListener(v -> {
            dialog.dismiss();
            modifyName(editText.getText().toString());
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void modifyName(String name) {
        HttpModleUtil.modifyName(this, beans.getDevice_info().getDevice_id(), name, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                RxBus.getDefault().post(UNBIND_CODE,"unbind");
                beans.setDevice_alias(name);
                initData();
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }

        });
    }

    @OnClick({R.id.backbar, R.id.sharelay, R.id.userlay, R.id.devicelay, R.id.idlay, R.id.gujianlay,R.id.unbind})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.backbar:
                finish();
                break;
            case R.id.sharelay:
                intent = new Intent(this, ShareCodeActivity.class);
                intent.putExtra("deviceId",beans.getDevice_info().getDevice_id());
                startActivity(intent);
                break;
            case R.id.userlay:
                intent = new Intent(this, UserListActivity.class);
                intent.putExtra("deviceId", beans.getDevice_info().getDevice_id());
                startActivity(intent);
                break;
            case R.id.devicelay:
                showCusDialog();
                break;
            case R.id.idlay:
                break;
            case R.id.gujianlay:
                break;
            case R.id.unbind:
                setUnbind();
                break;
        }
    }

    public void setUnbind(){
        HttpModleUtil.unBindDevice(this, beans.getDevice_info().getDevice_id(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                RxBus.getDefault().post(UNBIND_CODE,"unbind");
                startActivity(new Intent(DeviceDetailActivity.this,DeviceActivity.class));
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }

}
