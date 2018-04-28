package com.gaoda.philips.add;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.gaoda.base.BaseActivity;
import com.gaoda.philips.R;
import com.gaoda.rxbus.RxBus;
import com.gaoda.rxbus.RxBusBaseMessage;
import com.gaoda.rxbus.RxCodeConstants;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

/**
 * @Created: Suqi
 * @Date: 2018/1/4
 * @Description: 选择设备型号
 */

public class SelectModelActivity extends BaseActivity {

    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private ImageView image_back;
    private TextView tv_model;
//    private RecyclerView recyclerView;
//    private DeviceModelAdapter adapter;
//    private ArrayList<DevicesModelBean> list;
    private Disposable subscription;

//    private Handler myHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            String data = JSON.parseObject(msg.obj.toString()).getString("data");
//            list = new ArrayList<>(JSON.parseArray(data, DevicesModelBean.class));
//            if (list.size() > 0) {
//                adapter = new DeviceModelAdapter(SelectModelActivity.this, list);
//                adapter.setListener(SelectModelActivity.this);
//                recyclerView.setLayoutManager(new LinearLayoutManager(SelectModelActivity.this));
//                recyclerView.setAdapter(adapter);
//            }
//        }
//    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_model);
        registerRxBus();
        initView();
//        initData();
        initEvent();
    }

    private void initView() {
        actionbar_back = (LinearLayout) findViewById(R.id.actionbar_back);
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        actionbar_title.setText(getString(R.string.SelectModel_Header_Title));
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        tv_model = findViewById(R.id.tv_model);
//        recyclerView = (RecyclerView) findViewById(R.id.model_list);
    }

//    private void initData() {
//        String type_id = PublicApplication.getSpInstance().getString(Constant.TYPE_ID);
//        if (!BaseUtils.isNullString(type_id))
//        PublicApplication.getFog().getProductList(Fog.APP_ID(), type_id, new FogCallBack() {
//            @Override
//            public void onSuccess(String message) {
//                LogUtil.d("suqi", message);
//                if (BaseUtils.getCode(message) == 0) {
//                    BaseUtils.sendMessage(myHandler, 0, message);
//                }
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
//                initData();
//            }
//        }, PublicApplication.getToken());
//    }

    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
        RxView.clicks(tv_model).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Intent intent = new Intent(this, DeviceCanLinkActivity.class);
                    startActivity(intent);
                });
    }

//    @Override
//    public void onItemClick(int position) {
//        PublicApplication.getSpInstance().setString(Constant.PRODUCT_ID, list.get(position).product_id);
//        Intent intent = new Intent(this, DeviceCanLinkActivity.class);
//        intent.putExtra("name", list.get(position).name);
//        startActivity(intent);
//    }

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
//        myHandler.removeCallbacksAndMessages(null);
    }
}
