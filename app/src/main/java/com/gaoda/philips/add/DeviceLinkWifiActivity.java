package com.gaoda.philips.add;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaoda.base.BaseActivity;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.log.LogUtil;
import com.gaoda.philips.R;
import com.gaoda.rxbus.RxBus;
import com.gaoda.rxbus.RxBusBaseMessage;
import com.gaoda.rxbus.RxCodeConstants;
import com.gaoda.util.BaseUtils;
import com.gaoda.util.StatusBarUtils;
import com.google.gson.JsonObject;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lzy.okgo.model.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.fogcloud.sdk.easylink.api.EasylinkP2P;
import io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import io.fogcloud.sdk.easylink.helper.EasyLinkParams;
import io.fogcloud.sdk.easylink.helper.UdpSend;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @Created: Suqi
 * @Date: 2018/1/4
 * @Description: 正在配网
 */

public class DeviceLinkWifiActivity extends BaseActivity {

    private static final int LINK_WIFI_SUCCESS = 100;
    private static final int BIND_DEVICE_SUCCESS = 200;
    private static final int BIND_DEVICE_FAIL = 300;
    private static final int HAS_SUPER_USER = 400;
    private static final int SET_PROGRESS = 500;
    private static final int AWS_LINK_FAIL = 800;
    private static final int TO_BIND_DEVICE = 900; //收到监听端口的device_id去绑定

    private PowerManager powerManager = null;
    private PowerManager.WakeLock wakeLock = null;
    private LinearLayout actionbar_back;
    private TextView actionbar_title;
    private TextView tv_progress;
    private ImageView image_back;
    private ImageView iv_linking;
    private String ssid;
    private String psd;

    private String device_id;
    private Context mContext;
    private Disposable subscriptionCountTime, subscription;
    private int progress;
    private WifiManager manager;
    private WifiInfo mWifiInfo;
    private EasylinkP2P elp2p;
    private boolean isAWS = true; //是否是aws配网
    private boolean isToLinkFog = true; //是否去调云端绑定接口
    private boolean isNeedLinkHot = true; //是否需要连接设备热点
    private Handler myHandler = new Handler(msg -> {
        switch (msg.what) {
            case LINK_WIFI_SUCCESS:
                httpPostData();
                break;
            case BIND_DEVICE_SUCCESS:
                progress = 100;
                tv_progress.setText("100");
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                        new RxBusBaseMessage(RxCodeConstants.TYPE_BIND_SUCCESS_FINISH, new Object()));
                Intent intent = new Intent(DeviceLinkWifiActivity.this, BindSuccessActivity.class);
                intent.putExtra("device_id", device_id);
                intent.putExtra("result", 1);
                startActivity(intent);
                finish();
                break;
            case BIND_DEVICE_FAIL:
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                        new RxBusBaseMessage(RxCodeConstants.TYPE_DEVICE_BIND_FAIL, new Object()));
                startActivity(new Intent(DeviceLinkWifiActivity.this, BindSuccessActivity.class));
                finish();
                break;
            case HAS_SUPER_USER:
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                        new RxBusBaseMessage(RxCodeConstants.TYPE_DEVICE_BIND_FAIL, new Object()));
                String data = JSON.parseObject(msg.obj.toString()).getString("data");
                String device_alias = JSON.parseObject(data).getString("device_alias");
                String superuser = JSON.parseObject(data).getString("superuser");
                Intent intent1 = new Intent(DeviceLinkWifiActivity.this, HasSuperUserActivity.class);
                intent1.putExtra("device_alias", BaseUtils.isNullString(device_alias) ? "" : device_alias);
                intent1.putExtra("superuser", BaseUtils.isNullString(superuser) ? "" : superuser);
                startActivity(intent1);
                finish();
                break;
            case SET_PROGRESS:
                progress = 60;
                listenerID();
                tv_progress.setText("60");
                WifiConfiguration tempConfig = isExsits(ssid);
                if (null != tempConfig) {
                    manager.enableNetwork(tempConfig.networkId, true);
                } else {
                    createWifiInfo(ssid, psd, BaseUtils.isNullString(psd) ?
                            WifiConfiguration.KeyMgmt.NONE : WifiConfiguration.KeyMgmt.WPA_PSK);
                }
                break;
            case AWS_LINK_FAIL:
                stopEasylink();
                if (!subscriptionCountTime.isDisposed())
                    subscriptionCountTime.dispose();
                Log.d("DeviceLinkWifiActivity", "aws绑定失败");
                isAWS = false;
                LogUtil.w("DeviceLinkWifiActivity", "aws超时开始连接设备热点");
                break;
            case TO_BIND_DEVICE:
                if (!subscriptionCountTime.isDisposed())
                    subscriptionCountTime.dispose();
                progress = 80;
                tv_progress.setText("80");
                bindDevices(device_id);
                break;
        }
        return true;
    });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_wifi);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        initView();
        initEvent();
        iniData();
    }

    private void initView() {
        actionbar_back = findViewById(R.id.actionbar_back);
        actionbar_title = findViewById(R.id.actionbar_title);
        actionbar_title.setText(getString(R.string.NetConnecting_Header_Title));
        actionbar_title.setTextColor(getResources().getColor(R.color.color_333333));
        tv_progress = findViewById(R.id.tv_progress);
        image_back = findViewById(R.id.image_back);
        image_back.setImageResource(R.mipmap.back_black);
        iv_linking = findViewById(R.id.iv_linking);
        RotateAnimation rotate = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        iv_linking.setAnimation(rotate);
        iv_linking.startAnimation(rotate);
    }

    @SuppressLint("CheckResult")
    private void initEvent() {
        RxView.clicks(actionbar_back).throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(aVoid -> {
                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                            new RxBusBaseMessage(RxCodeConstants.TYPE_DEVICE_BIND_FAIL, new Object()));
                    finish();
                });
        Observable.interval(1600, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(70)
                .doOnSubscribe(disposable -> {
                    if (isAWS) {
                        RxTextView.text(tv_progress).accept("0");
                        progress = 1;
                    } else {
                        RxTextView.text(tv_progress).accept("50");
                        progress = 51;
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                subscription = d;
            }

            @Override
            public void onNext(Long aLong) {
                if (!isAWS) {
                    if (isNeedLinkHot && getSSID().contains("Philips")) {
                        myHandler.sendEmptyMessageDelayed(LINK_WIFI_SUCCESS, 1000);
                    } else {
                        if (isNeedLinkHot) {
                            checkWifi();
                        }
                    }
                    if (progress == 59 || progress == 79 || progress == 99) {

                    } else {
                        try {
                            RxTextView.text(tv_progress).accept((String.valueOf(progress++)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (progress <= 50) {
                        try {
                            RxTextView.text(tv_progress).accept((String.valueOf(progress++)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                BaseUtils.sendMessage(myHandler, BIND_DEVICE_FAIL, null);
            }
        });
        subscriptionCountTime = Observable.timer(60, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (isAWS) {
                        BaseUtils.sendMessage(myHandler, AWS_LINK_FAIL, "");
                    } else {
                        BaseUtils.sendMessage(myHandler, BIND_DEVICE_FAIL, "");
                    }
                });


    }

    private void iniData() {
        mContext = this;
        ssid = getIntent().getStringExtra("ssid");
        psd = getIntent().getStringExtra("psd");
        isAWS = getIntent().getBooleanExtra("isAWS", true);
        manager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        powerManager = (PowerManager) this.getSystemService(Service.POWER_SERVICE);
        wakeLock = this.powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");
        wakeLock.setReferenceCounted(false);
        if (isAWS) {
            startEasyLink(ssid, psd);
        }
    }

    public String getSSID() {
        if (mContext != null) {
            mWifiInfo = manager.getConnectionInfo();
            return mWifiInfo.getSSID().replaceAll("\"", "");
        } else {
            return null;
        }
    }

    private void checkWifi() {
        manager.startScan();
        List<ScanResult> scanResults = manager.getScanResults();//搜索到的设备列表
        for (ScanResult scanResult : scanResults) {
            if (scanResult.SSID.contains("Philips")) {
                WifiConfiguration tempConfig = isExsits(scanResult.SSID);
                if (null != tempConfig) {
                    manager.enableNetwork(tempConfig.networkId, true);
                } else {
                    createWifiInfo(scanResult.SSID, "", WifiConfiguration.KeyMgmt.NONE);
                }
                break;
            }
        }
    }

    public void startEasyLink(String ssid, String password) {
        listenerID();
        elp2p = new EasylinkP2P(this);
        EasyLinkParams easylinkPara = new EasyLinkParams();
        easylinkPara.ssid = ssid;
        easylinkPara.password = password;
        easylinkPara.runSecond = 60000;
        easylinkPara.sleeptime = 20;
        Log.e("DeviceLinkWifiActivity", "开始aws");
        elp2p.startEasyLink(easylinkPara, new EasyLinkCallBack() {
            @Override
            public void onSuccess(int code, String message) {

            }

            @Override
            public void onFailure(int code, String message) {

            }
        });

    }

    // 查看以前是否也配置过这个网络
    private WifiConfiguration isExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = manager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    private void createWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        //nopassword
        if (Type == WifiConfiguration.KeyMgmt.NONE) {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        // wpa
        if (Type == WifiConfiguration.KeyMgmt.WPA_PSK) {
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);

            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        int netId = manager.addNetwork(config);
        manager.enableNetwork(netId, true);
    }

    /**
     * udp监听获取device_id
     */
    public void listenerID() {
        new Thread() {
            @Override
            public void run() {
                while (isToLinkFog) {
                    try {
                        DatagramSocket dgSocket = null;
                        int port = 8000;
                        if (dgSocket == null) {
                            dgSocket = new DatagramSocket(null);
                            dgSocket.setReuseAddress(true);
                            dgSocket.bind(new InetSocketAddress(port));
                        }
                        byte[] by = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(by, by.length);
                        dgSocket.receive(packet);
                        String str = new String(packet.getData(), 0, packet.getLength());
                        String status = JSON.parseObject(str).getString("Status");
                        if (isToLinkFog) {
                            LogUtil.w("DeviceLinkWifiActivity", "udp收到绑定" + str);
                            if (status.equals("Device_Discovery")) {
                                isToLinkFog = false;
                                device_id = JSON.parseObject(str).getString("Device_id");
                                BaseUtils.sendMessage(myHandler, TO_BIND_DEVICE, "");
                                String json = "{\"Status\":\"Close_Discovery\",\"Device_id:\"" + device_id + "\"}";
                                byte[] bs = json.getBytes();
                                DatagramPacket dp = new DatagramPacket(bs, bs.length, new InetSocketAddress(port));
                                dgSocket.send(dp);
                            } else if (status.equals("Device_Failed")) {
                                BaseUtils.sendMessage(myHandler, AWS_LINK_FAIL, "");
                            }
                            dgSocket.close();
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isToLinkFog = false;
        isNeedLinkHot = false;
        myHandler.removeCallbacksAndMessages(null);
        stopEasylink();
        if (BaseUtils.isNotNull(subscription) && !subscription.isDisposed())
            subscription.dispose();
        if (!subscriptionCountTime.isDisposed())
            subscriptionCountTime.dispose();
        //        LogcatHelper.getInstance(this).stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
                    new RxBusBaseMessage(RxCodeConstants.TYPE_DEVICE_BIND_FAIL, new Object()));
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void stopEasylink() {
        if (BaseUtils.isNotNull(elp2p))
            elp2p.stopEasyLink(new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {
                    LogUtil.w("DeviceLinkWifiActivity", "stopsuccess" + code + message);
                }

                @Override
                public void onFailure(int code, String message) {
                    LogUtil.w("DeviceLinkWifiActivity", "stopfail" + code + message);
                }
            });
    }

    private void bindDevices(String device_id) {


        HttpModleUtil.bindDevice(this, device_id,JPushInterface.getRegistrationID(this),new JsonCallBack() {
            @Override
            public void onSuccess(Response<org.json.JSONObject> response) {
                LogUtil.w("DeviceLinkWifiActivity", response.body().toString());
                String message = response.body().toString();
                if (BaseUtils.getCode(message) == 0 || BaseUtils.getCode(message) == 10351) {
                    BaseUtils.sendMessage(myHandler, BIND_DEVICE_SUCCESS, null);
                } else if (BaseUtils.getCode(message) == 16005) {
                    BaseUtils.sendMessage(myHandler, HAS_SUPER_USER, message);
                } else {
                    BaseUtils.sendMessage(myHandler, BIND_DEVICE_FAIL, null);
                }
            }

            @Override
            public void onFail(Response<org.json.JSONObject> response) {

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
    }

    @Override
    protected void onResume() {
        wakeLock.acquire();
        super.onResume();
    }

    private void httpPostData() {
        String IPAddress = "10.10.10.1";
        final int port = 30123;
        JSONObject jsonObject = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("ssid", ssid);
        content.put("password", psd);
        jsonObject.put("type", "config");
        jsonObject.put("data", content);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isNeedLinkHot) {
                    try {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress(InetAddress.getByName(IPAddress), port), 2000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        OutputStream os = socket.getOutputStream();
                        os.write(jsonObject.toString().getBytes());
                        socket.shutdownOutput();
                        String line = "";
                        String result = "";
                        LogUtil.w("DeviceLinkWifiActivity", "向设备发送联网信息" + jsonObject.toString());
                        while ((line = in.readLine()) != null) {
                            LogUtil.w("DeviceLinkWifiActivity", "3.getdata" + line + " len=" + line.length());
                            result = result + line;
                        }
                        if (!BaseUtils.isNullString(result)) {
                            if (BaseUtils.getCode(result) == 0) {
                                isNeedLinkHot = false;
                                LogUtil.w("DeviceLinkWifiActivity", "收到设备返回信息，等待连接网络接收retain消息");
                                BaseUtils.sendMessage(myHandler, SET_PROGRESS, "");
                                if (!socket.isClosed()) {
                                    socket.close();
                                }
                            }
                        }
                        os.flush();
                        in.close();
                        Thread.sleep(500);
                    } catch (IOException e) {
                        LogUtil.w("DeviceLinkWifiActivity", e.getMessage());
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
