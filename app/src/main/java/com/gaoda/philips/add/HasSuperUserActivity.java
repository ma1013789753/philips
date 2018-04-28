package com.gaoda.philips.add;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.philips.R;
import com.gaoda.util.BaseUtils;
import com.gaoda.util.StatusBarUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * @Created: Suqi
 * @Date: 2018/2/9
 * @Description:
 */

public class HasSuperUserActivity extends BaseActivity {

    private LinearLayout actionbar_back;
    private ImageView image_back;
    private String device_alias;
    private String superuser;
    private TextView tv_super_user;
    private TextView tv_device_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_super_user);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        device_alias = getIntent().getStringExtra("device_alias");
        superuser = getIntent().getStringExtra("superuser");
        initView();
        initEvent();
    }

    private void initView() {
        actionbar_back = (LinearLayout) findViewById(R.id.actionbar_back);
        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        tv_super_user = (TextView) findViewById(R.id.tv_super_user);
        tv_device_name = (TextView) findViewById(R.id.tv_device_name);
        tv_super_user.setText(BaseUtils.isNullString(superuser) ? "" : superuser);
        tv_device_name.setText((BaseUtils.isNullString(device_alias) ? "" : device_alias));
    }

    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> finish());
    }
}
