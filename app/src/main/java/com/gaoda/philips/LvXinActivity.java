package com.gaoda.philips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.bean.DeviceBean;
import com.gaoda.bean.PushUpBean;
import com.gaoda.rxbus.RxBus;
import com.gaoda.util.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wangxd-fog on 2018/3/29.
 */

public class LvXinActivity extends AutoLayoutActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.menubar)
    ImageView menubar;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.timelist)
    TextView timelist;
    @BindView(R.id.tip)
    TextView tip;
    private PushUpBean bean;
    private String device_id;
    private DeviceBean beans ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvxin);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        bean = (PushUpBean) getIntent().getSerializableExtra("beans");
        device_id = getIntent().getStringExtra("deviceId");
        beans = (DeviceBean) getIntent().getSerializableExtra("bean");
        if (bean != null) {
            initView();
        }
        initTitle();
    }

    private void initView() {

        timelist.setText(bean.getFilterLife1());
        int i = Integer.parseInt(bean.getFilterLife1());
        if (i <= 0) {
            image.setBackgroundResource(R.drawable.lvx_0);
            tip.setVisibility(View.VISIBLE);
        } else if (i <= 1700) {
            image.setBackgroundResource(R.drawable.lvx_1);
            tip.setVisibility(View.VISIBLE);
        } else if (i <= 1800) {
            image.setBackgroundResource(R.drawable.lvx_2);

        } else if (i <= 3500) {
            image.setBackgroundResource(R.drawable.lvx_4);

        } else if (i <= 6000) {
            image.setBackgroundResource(R.drawable.lvx_5);

        } else if (i <= 7888) {
            image.setBackgroundResource(R.drawable.lvx_6);

        } else {
            image.setBackgroundResource(R.drawable.lvx_7);

        }
    }
    public void registMessage(){
        RxBus.getDefault().toObservable(DeviceDetailActivity.UNBIND_CODE,String.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((str)->{
                    finish();
                });
    }
    private void initTitle() {
        backbar.setOnClickListener((view)-> finish());
        titlebar.setText("Filter");
        menubar.setOnClickListener(v -> {
            Intent intent = new Intent(this, DeviceDetailActivity.class);
            intent.putExtra("deviceId", device_id);
            intent.putExtra("bean", beans);
            startActivity(intent);
        });
    }
}
