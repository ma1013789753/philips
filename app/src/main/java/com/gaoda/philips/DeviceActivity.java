package com.gaoda.philips;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.gaoda.base.BaseActivity;
import com.gaoda.base.MyApplication;
import com.gaoda.fragent.DeviceFragment;
import com.gaoda.fragent.MineFragment;
import com.gaoda.util.StatusBarUtils;
import com.gaoda.util.StringUtils;
import com.pgyersdk.update.PgyUpdateManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceActivity extends BaseActivity {

    @BindView(R.id.root)
    FrameLayout root;
    @BindView(R.id.group1)
    RadioButton group1;
    @BindView(R.id.group2)
    RadioButton group2;
    @BindView(R.id.rootgroup)
    RadioGroup rootgroup;

    //当前的fragment
    private Fragment instance;
    DeviceFragment deviceActivity = new DeviceFragment();
    MineFragment mineFragment = new MineFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.loginblue);
        initVersion();
        initView();
    }

    private void initVersion() {
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        instance = deviceActivity;
        //初始化第一个fragment
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.root,instance);
        transaction.commit();
    }

    @OnClick({R.id.group1, R.id.group2})
    public void onViewClicked(View view) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()) {
            case R.id.group1:

                instance = deviceActivity;
                transaction.replace(R.id.root,instance);
                transaction.commit();
                group1.setTextColor(getResources().getColor(R.color.blue));
                group2.setTextColor(getResources().getColor(R.color.c999));
                break;
            case R.id.group2:
                instance = mineFragment;
                transaction.replace(R.id.root,instance);
                transaction.commit();
                group1.setTextColor(getResources().getColor(R.color.c999));
                group2.setTextColor(getResources().getColor(R.color.blue));
                break;
        }
    }
}
