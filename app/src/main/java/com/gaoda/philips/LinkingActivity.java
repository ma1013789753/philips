package com.gaoda.philips;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.SSLCertificateSocketFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gaoda.base.BaseActivity;
import com.gaoda.util.StringUtils;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fogcloud.sdk.easylink.api.EasylinkP2P;
import io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import io.fogcloud.sdk.easylink.helper.EasyLinkParams;

import static android.widget.Toast.*;

public class LinkingActivity extends BaseActivity {
    private static final int LINK_WIFI_SUCCESS = 100;
    private static final int BIND_DEVICE_SUCCESS = 200;
    private static final int BIND_DEVICE_FAIL = 300;
    private static final int HAS_SUPER_USER = 400;
    private static final int SET_PROGRESS = 500;
    private static final int ON_REQUEST = 600;
    private static final int PRODUCT_ID_ERROR = 700;
    private static final int AWS_LINK_FAIL = 800;
    private static final int TO_BIND_DEVICE = 900; //收到监听端口的device_id去绑定
    @BindView(R.id.text)
    EditText text;
    @BindView(R.id.fasong)
    Button fasong;
    private boolean isToLinkFog = true; //是否去调云端绑定接口
    EasylinkP2P elp2p;
    private String device_id = "";
    String name = "";
    String pwd = "";
    @BindView(R.id.progress)
    ProgressBar progress;



    private Handler myHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            Log.d("xxxxxxxxxx", msg.what + "__" + msg.obj.toString());
            switch (msg.what) {
                case LINK_WIFI_SUCCESS:
                    break;
                case BIND_DEVICE_SUCCESS:
                    break;
                case BIND_DEVICE_FAIL:
                    break;
                case HAS_SUPER_USER:
                    break;
                case SET_PROGRESS:

                    break;
                case ON_REQUEST:
                    break;
                case PRODUCT_ID_ERROR:
                    finish();
                    break;
                case AWS_LINK_FAIL:

                    break;
                case TO_BIND_DEVICE:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linking_activity);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra("name");
        pwd = getIntent().getStringExtra("pwd");
        Log.e("name", name + "___" + pwd);
        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(pwd)) {
//            startEasyLink(name, pwd);
//            startMqtt();
        }

    }





    String broker       = "wss://a3o62fhmrrmmgs.iot.cn-north-1.amazonaws.com.cn/mqtt?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAOHHVY3ZFM47MQOOQ%2F20180409%2Fcn-north-1%2Fiotdevicegateway%2Faws4_request&X-Amz-Date=20180409T023938Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=009f2b5aa6f403eb301b64339cb364bdce09524150791b2780a2501497627517&X-Amz-Security-Token=FQoDYXdzEIP//////////wEaDAynHAqv7FFHzsnYlSLjA9tWd3Rh6wS8yw4/EPgd5MuYvTT5OBYOIQoGkc08nXvI7f8HimGyrc5GgsEsgFjdymuSoLTcT6I/lgpy5FCOKDdqR8MU0HICsWuTx3gTwA6TvMjc5IiGZU%2BxjrpM01/vaivilhvRbOzpSbeinpijNDVV6wZVFR6fM3f%2BCROkPKYW%2B2rgwzymB01kBCAzIeSOLBrM9IX7n6cMTIVfT0DW9xyn1l1KtyJXmiThhIzvffcJO5lZ%2B5P6xlCghKakn7%2B5nQJ694a/DPqt6pEae/QheQWsWfLF426QNfAlKc7Lwn0JU5tlCA87EmMu0JNZNRS0KwH84PnL7Tck5XL7qCDctTR%2BTlfL%2BMclqMhbiqwdJVS3zgnjgo6fnj157wsuR86DlUP62rxhJR3Ri/vlOP/lFA2TK3M/WyIR210s7TtgR1PdKP3Tv4OYab8vwbPK%2BS9Kh3bilB9iHAd%2BgoYRjZxzstWuUXBypbthm6Wu2Fl81QVRtToK0RAefgL8jdD8s4no2PNIExFNjmyYWBZv7GsC4ynqoqc53t3jbxfcarKNNXGZ7MzkSMwQ7mjDRqgvphIUuinPsxS%2B/gJyGHFYPEglh1FqdECUPcSaLrcwjoZXhyXg9AYZCC1lcKDPogZKMp5lv6oy8SjepKvWBQ%3D%3D"
            +"";
    String clientId     = "985a2b5e33f211e889210090a2d108f7";
    MemoryPersistence persistence = new MemoryPersistence();
    MqttClient client;
    public void startMqtt() {
        URI vURI = null;
        try {
            vURI = new URI(broker);
            Log.d("xxxxxx",vURI.getScheme().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            MqttConnectOptions options = new MqttConnectOptions();

//            options.setKeepAliveInterval(60);
//            options.setSSLProperties(pro);
//            options.setCleanSession(true);
            client = new MqttClient(broker, clientId,persistence);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.w("cause",cause.toString());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.w("message",topic); Log.w("xxxx",message.toString());
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.w("token",token.toString());

                }

            });
            client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }


    public void startEasyLink(String ssid, String password) {
        elp2p = new EasylinkP2P(this);
        EasyLinkParams easylinkPara = new EasyLinkParams();
        easylinkPara.ssid = ssid;
        easylinkPara.password = password;
        easylinkPara.runSecond = 60000;
        easylinkPara.sleeptime = 20;
        Log.e("suqi", "开始aws");
        elp2p.startEasyLink(easylinkPara, new EasyLinkCallBack() {
            @Override
            public void onSuccess(int code, String message) {
                Log.e("suqi", Thread.currentThread().getName() + code + message);
                if (code == 0) {
                    String type_id = JSON.parseObject(message).getString("ExtraData");
                    String ip = JSON.parseObject(message).getString("IP");
                    stopEasylink();
                }
            }

            @Override
            public void onFailure(int code, String message) {
                Log.e("suqi", Thread.currentThread().getName() + code + message);
            }
        });
    }



    //停止配网
    public void stopEasylink() {
        if (elp2p != null)
            elp2p.stopEasyLink(new EasyLinkCallBack() {
                @Override
                public void onSuccess(int code, String message) {

                }

                @Override
                public void onFailure(int code, String message) {

                }
            });
    }

    @OnClick(R.id.fasong)
    public void onViewClicked() {
        startMqtt();
    }




}







