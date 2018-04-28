package com.gaoda.philips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fogcloud.sdk.easylink.api.EasylinkP2P;

public class WifiActivity extends BaseActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.wifi)
    TextView wifi;
    @BindView(R.id.psw)
    EditText psw;
    @BindView(R.id.next)
    Button next;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wifi_activity);
        ButterKnife.bind(this);
        initWifi();
    }

    private void initWifi() {

        EasylinkP2P elp2p = new EasylinkP2P(this);
        String name = elp2p.getSSID();
        wifi.setText(name);
    }


    @OnClick({R.id.backbar, R.id.next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backbar:
                finish();
                break;
            case R.id.next:
                Intent intent = new Intent(this,LinkingActivity.class);
                intent.putExtra("name",wifi.getText().toString());
                intent.putExtra("pwd",psw.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
