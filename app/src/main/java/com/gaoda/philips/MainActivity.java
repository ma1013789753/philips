package com.gaoda.philips;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.gaoda.bean.DeviceBean;
import com.gaoda.bean.HostBean;
import com.gaoda.bean.PushUpBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.log.LogUtil;
import com.gaoda.mqtt.MqttClientUtil;
import com.gaoda.util.BaseUtils;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.gaoda.view.StorageView;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.zhy.autolayout.AutoLayoutActivity;
import com.zhy.autolayout.AutoLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AutoLayoutActivity {


    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.menubar)
    ImageView menubar;
    @BindView(R.id.m_num)
    TextView mNum;
    @BindView(R.id.bolang)
    ImageView bolang;
    @BindView(R.id.fouth_button)
    LinearLayout fouthButton;
    @BindView(R.id.switch_button)
    LinearLayout switchButton;
    @BindView(R.id.history)
    LinearLayout history;
    @BindView(R.id.lvxin)
    LinearLayout lvxin;
    @BindView(R.id.container)
    AutoLinearLayout container;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final int MESSAGE_RECEIVE = 302;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.stagebutton)
    StorageView stagebutton;
    @BindView(R.id.text)
    EditText text1;
    @BindView(R.id.value)
    EditText value;
    @BindView(R.id.denghuan)
    Spinner denghuan;
    @BindView(R.id.lock)
    Spinner lock;
    @BindView(R.id.mianban)
    Spinner mianban;
    @BindView(R.id.tongsuo)
    Switch tongsuo;
    @BindView(R.id.tongsuot)
    TextView tongsuot;
    @BindView(R.id.mianbant)
    TextView mianbant;
    @BindView(R.id.denghuant)
    TextView denghuant;
    @BindView(R.id.lockt)
    TextView lockt;

    String deviceId = "";
    @BindView(R.id.pm_mes)
    TextView pmMes;
    @BindView(R.id.send_message)
    Button sendMessage;
    private MqttClientUtil client;
    private PushUpBean upBean;
    private DeviceBean beans;
    private HostBean bean;
    private Handler handler = new Handler(msg -> {
        switch (msg.what) {
            case MESSAGE_RECEIVE:
                Gson gson = new Gson();
                upBean = gson.fromJson(msg.obj.toString(), PushUpBean.class);
                Log.w("xxxxj", msg.obj.toString());
                refreshUi(upBean);
                break;
        }
        return true;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.s_green);
        deviceId = getIntent().getStringExtra("deviceId");
        beans = (DeviceBean) getIntent().getSerializableExtra("bean");
        initOthers();
        initTitle();
        initFouth();
        initData();
        initView();
    }

    private void initOthers() {
        tongsuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (client == null) {
                        return;
                    }
                    client.sendMessege("{\"childLock\":\"" + "1" + "\"}");
                } else {
                    if (client == null) {
                        return;
                    }
                    client.sendMessege("{\"childLock\":\"" + "0" + "\"}");
                }
            }
        });
        mianban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (client == null) {
                    return;
                }
                client.sendMessege("{\"UILight\":\"" + position + "\"}");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        denghuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (client == null) {
                    return;
                }
                client.sendMessege("{\"AQILight\":\"" + position + "\"}");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (client == null) {
                    return;
                }
                position = position + 1;
                client.sendMessege("{\"Countdown\":\"" + position + "\"}");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initView() {

        stagebutton.setListener(new StorageView.OnSwitchListener() {
            @Override
            public void onChange(int index) {
                if (client == null) {
                    return;
                }
                client.sendMessege("{\"WindSpeed\":\"" + index + "\"}");
            }
        });

    }


    //设置背景颜色
    public void setColor(int i) {
        mNum.setText(i + "");
        if (i <= 35) {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.s_green);
            root.setBackgroundColor(getResources().getColor(R.color.s_green));
            pmMes.setText("Indoor air：Excellent");
        } else if (i <= 75) {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.s_yellow);
            root.setBackgroundColor(getResources().getColor(R.color.s_yellow));
            pmMes.setText("Indoor air：Good");
        } else if (i < 115) {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.s_orange);
            root.setBackgroundColor(getResources().getColor(R.color.s_orange));
            pmMes.setText("Indoor air：Mild pollution");
        } else {
            StatusBarUtils.setWindowStatusBarColor(this, R.color.s_red);
            root.setBackgroundColor(getResources().getColor(R.color.s_red));
            pmMes.setText("Indoor air：Moderate pollution");
        }
    }

    private void initData() {
        HttpModleUtil.getHost(this, deviceId, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                try {
                    if (!ReTurnUtil.isSuccess(response, getBaseContext())) {
                        return;
                    }
                    bean = new Gson().fromJson(response.body().getString("data"), HostBean.class);
                    if (bean == null || bean.getDevice_list() == null) {
                        return;
                    }
                    new Thread(() -> {
                        startMqtt(bean.getHost(), bean.getDevice_list().get(0), bean.getClient_id());
                    }).start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });

    }

    //startLink
    public void startMqtt(String Host, String deviceId, String clientId) {
        client = new MqttClientUtil(Host, deviceId, clientId);
        client.startConnet(((topic, message) -> {
            BaseUtils.sendMessage(handler, MESSAGE_RECEIVE, message.toString());
        }));

    }

    //实时刷新界面
    private void refreshUi(PushUpBean bean) {
        setColor(Integer.parseInt(bean.getPM25()));
        int mode = Integer.parseInt(bean.getWorkMode());
        setFourth(mode);
        stagebutton.setFlag(Integer.parseInt(bean.getWindSpeed()));
        //设置开关
        final CheckBox box = (CheckBox) switchButton.getChildAt(0);
        final TextView text = (TextView) switchButton.getChildAt(1);
        if ("0".equals(bean.getSwitch())) {
            box.setChecked(false);
            text.setTextColor(0xff999999);
        } else {
            box.setChecked(true);
            text.setTextColor(0xff4A90E2);
        }

        //
        denghuant.setText(bean.getAQILight());
        mianbant.setText(bean.getUILigth());
        lockt.setText(bean.getCountdown());
        tongsuot.setText(bean.getChildLock());
    }

    private void setFourth(int mode) {
        if (mode >= 4) {
            return;
        }
        if (mode == 0) {
            stagebutton.setEnable(false);
        } else {
            stagebutton.setEnable(true);
        }
        clearOthers();
        LinearLayout linearLayout = (LinearLayout) fouthButton.getChildAt(mode);
        final CheckBox box = (CheckBox) linearLayout.getChildAt(0);
        final TextView text = (TextView) linearLayout.getChildAt(1);
        box.setChecked(true);
        text.setTextColor(0xff4A90E2);
    }


    //4个按钮的点击事件
    private void initFouth() {
        for (int i = 0; i < fouthButton.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) fouthButton.getChildAt(i);
            final CheckBox box = (CheckBox) linearLayout.getChildAt(0);
            final TextView text = (TextView) linearLayout.getChildAt(1);
            final int a = i;
            linearLayout.setOnClickListener(v -> {
                if (box.isChecked()) {
                    return;
                }
                if (client == null) {
                    return;
                }
                client.sendMessege("{\"WorkMode\":\"" + a + "\"}");
                if (a == 0) {
                    stagebutton.setEnable(false);
                } else {
                    stagebutton.setEnable(true);
                }
                clearOthers();
                box.setChecked(true);
                text.setTextColor(0xff4A90E2);
            });
        }

    }

    //清楚其他
    private void clearOthers() {
        for (int i = 0; i < fouthButton.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) fouthButton.getChildAt(i);
            final CheckBox box = (CheckBox) linearLayout.getChildAt(0);
            final TextView text = (TextView) linearLayout.getChildAt(1);
            if (box.isChecked()) {
                box.setChecked(false);
                text.setTextColor(0xff999999);
            }
        }
    }


    private void initTitle() {
        backbar.setOnClickListener(v -> finish());
        titlebar.setText("Air cleaner");
        menubar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DeviceDetailActivity.class);
            intent.putExtra("deviceId", deviceId);
            intent.putExtra("bean", beans);
            startActivity(intent);
        });
    }

    @OnClick({R.id.switch_button, R.id.history, R.id.lvxin,R.id.send_message})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.switch_button:
                final CheckBox box = (CheckBox) switchButton.getChildAt(0);
                final TextView text = (TextView) switchButton.getChildAt(1);
                if (box.isChecked()) {
                    if (client == null) {
                        return;
                    }
                    client.sendMessege("{\"Switch\":\"" + 0 + "\"}");
                    box.setChecked(false);
                    text.setTextColor(0xff999999);
                } else {
                    if (client == null) {
                        return;
                    }
                    client.sendMessege("{\"Switch\":\"" + 1 + "\"}");
                    box.setChecked(true);
                    text.setTextColor(0xff4A90E2);
                }
                break;
            case R.id.history:
                intent = new Intent(this, HistoryActivity.class);
                intent.putExtra("deviceId", deviceId);
                intent.putExtra("bean", beans);
                startActivity(intent);
                break;
            case R.id.lvxin:
                intent = new Intent(this, LvXinActivity.class);
                intent.putExtra("beans", upBean);
                intent.putExtra("deviceId", deviceId);
                intent.putExtra("bean", beans);
                startActivity(intent);
                break;
            case R.id.send_message:
                sendMes();
                break;
        }
    }

    private void sendMes() {
        HttpModleUtil.sendMsg(this, deviceId, "Filter cartridge can be replaced, please deal with it as soon as possible", new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {

            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });

    }


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);

            } else {
                Log.e("xxxx", "有权限");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                    } else
                        finish();
                } else {
                    makeText(this, "权限获取成功", LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.ondestory();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (client != null && !client.isConnect()) {
            if (bean == null) {
                return;
            }
            new Thread(() -> {
                startMqtt(bean.getHost(), bean.getDevice_list().get(0), bean.getClient_id());
            }).start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.w("pause");
        if (client != null) {
            client.ondestory();
        }

    }
}
