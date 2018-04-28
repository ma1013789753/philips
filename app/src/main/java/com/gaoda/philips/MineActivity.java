package com.gaoda.philips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.base.MyApplication;
import com.gaoda.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineActivity extends BaseActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.myname)
    LinearLayout myname;
    @BindView(R.id.myphone)
    LinearLayout myphone;
    @BindView(R.id.mypwd)
    LinearLayout mypwd;
    @BindView(R.id.loginout)
    Button loginout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this,R.color.white);
    }

    @OnClick({R.id.backbar, R.id.myname, R.id.myphone, R.id.mypwd,R.id.loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backbar:
                finish();
                break;
            case R.id.myname:
                break;
            case R.id.myphone:
                break;
            case R.id.mypwd:
                break;
            case R.id.loginout:
                MyApplication.getInstance().setToken("");
                finish();
                break;
        }
    }

}
