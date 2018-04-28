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
import com.gaoda.util.BaseUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

/**
 * @Created: Suqi
 * @Date: 2018/1/4
 * @Description: 选择设备品类
 */

public class SelectTypeActivity extends BaseActivity {

    private static final int GET_TYPE_SUCCESS = 100;
    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private TextView tv_socket;
    private ImageView image_back;
    private Disposable subscription;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;

    //    private Handler myHandler = new Handler(new Handler.Callback() {
    //        @Override
    //        public boolean handleMessage(Message msg) {
    //            switch (msg.what) {
    //                case GET_TYPE_SUCCESS:
    //                    String data = JSON.parseObject(msg.obj.toString()).getString("data");
    //                    list = new ArrayList<>(JSON.parseArray(data, ProductTypeBean.class));
    //                    if (list.size() > 0) {
    //                        recyclerView.setLayoutManager(mLayoutManager);
    //                        adapter = new SelectTypeAdapter(SelectTypeActivity.this, list);
    //                        adapter.setListener(SelectTypeActivity.this);
    //                        recyclerView.setAdapter(adapter);
    //                    }
    //                    break;
    //            }
    //            return true;
    //        }
    //    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);
        registerRxBus();
        initView();
        initEvent();
        //        initData();
    }

    //    private void initData() {
    //        PublicApplication.getFog().getProductCategory(Fog.APP_ID(), new FogCallBack() {
    //            @Override
    //            public void onSuccess(String message) {
    //                LogUtil.d("suqi", message);
    //                if (BaseUtils.getCode(message) == 0) {
    //                    BaseUtils.sendMessage(myHandler, GET_TYPE_SUCCESS, message);
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
    //
    //            }
    //        }, PublicApplication.getToken());
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

    private void initView() {
        actionbar_back = (LinearLayout) findViewById(R.id.actionbar_back);
        actionbar_title = (TextView) findViewById(R.id.actionbar_title);
        actionbar_title.setText(getString(R.string.SelectClassify_Header_Title));
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        tv_socket = findViewById(R.id.tv_socket);
        //        recyclerView = (RecyclerView) findViewById(R.id.type_list);
        //        mLayoutManager = new LinearLayoutManager(this);
    }

    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
        RxView.clicks(tv_socket).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    Intent intent = new Intent(this, SelectModelActivity.class);
                    intent.putExtra("type_id", "1101");
                    startActivity(intent);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!subscription.isDisposed())
            subscription.dispose();
        //        myHandler.removeCallbacksAndMessages(null);
    }

    //    @Override
    //    public void onTypeClick(int position) {
    //        PublicApplication.getSpInstance().setString(Constant.TYPE_ID, list.get(position).type_id);
    //        Intent intent = new Intent(this, SelectModelActivity.class);
    //        intent.putExtra("type_id", list.get(position).type_id);
    //        startActivity(intent);
    //    }
}
