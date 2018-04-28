package com.gaoda.philips;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gaoda.util.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.ButterKnife;

public class ForgetPwdActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.loginblue);

    }
}
