package com.gaoda.philips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.adapter.DeviceAdapter;
import com.gaoda.base.BaseActivity;
import com.gaoda.bean.DeviceBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.rxbus.RxBus;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyDeviceActivity extends BaseActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    List<DeviceBean> datas ;
    DeviceAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydevice_activity);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        initlist();
        initData();
        registMessage();
    }

    //获取设备
    private void initData() {
        HttpModleUtil.deviceList(this, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                    return;
                }
                datas.clear();
                JSONObject data = response.body();
                try {
                    JSONArray arr = data.getJSONArray("data");
                    Gson gson = new Gson();
                    for (int i = 0; i < arr.length(); i++) {
                        datas.add(gson.fromJson(arr.getString(i),DeviceBean.class));
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }

    private void initlist() {
        datas = new ArrayList<>();
        adapter = new DeviceAdapter(this,datas);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        adapter.setClick((position -> {
            Intent intent = new Intent(MyDeviceActivity.this,DeviceDetailActivity.class);
            intent.putExtra("bean",datas.get(position));
            startActivity(intent);
        }));
    }

    public void registMessage(){
        RxBus.getDefault().toObservable(DeviceDetailActivity.UNBIND_CODE,String.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((str)->{
                    initData();
                });
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
