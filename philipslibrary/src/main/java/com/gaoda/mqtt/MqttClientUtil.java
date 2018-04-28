package com.gaoda.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static okhttp3.internal.Internal.instance;

public class MqttClientUtil{

    private MqttClient instance;

    private String host;
    private String deviceId;
    private String clientId;

    private String top ;
    private String top2;

    public MqttClientUtil(String host, String deviceId, String clientId) {
        this.host = host;
        this.deviceId = deviceId;
        this.clientId = clientId;
        this.top =  "Philips/"+deviceId+"/status";
        this.top2 =  "Philips/"+deviceId+"/command";
        Log.w("top2",this.top2);
        try {
            instance = new MqttClient(host,clientId,new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void startConnet(IMqttMessageListener listener){

        try {

            instance.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    //连接丢失
                    Log.w("throwable",throwable.toString());
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    //subscribe后得到的消息会执行到这里面
                    Log.w("messageArrived",mqttMessage.getPayload().toString()+"");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    //当publish后会执行到这里
                    Log.w("deliveryComplete","deliveryComplete.............");
                }


            });
            IMqttToken tok = instance.connectWithResult(new MqttConnectOptions());
            Log.w("qqqqq",tok.isComplete()+"");
            tok.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("xxxxx","onsusess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("xxxxx","onFailure");
                }
            });
            instance.subscribe(top, listener);

        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    /**
     * 发布消息
     * @param content 消息的文本
     *
     */
    public void sendMessege(final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(instance==null){
                    return;
                }
                MqttTopic mqttTopic= instance.getTopic(top2);
                MqttMessage message=new MqttMessage();
                message.setQos(1);
                message.setRetained(false);
                message.setPayload(content.getBytes());
                try {
                    //发布MqttMessege消息
                    Log.w("xxx",message.toString());
                    mqttTopic.publish(message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void onResume(){
        if(instance!=null&&!instance.isConnected()){
            try {
                instance.connect();
                Log.w("MQTT","重连");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnect(){

        return instance.isConnected();
    }
    public void onPause(){

        if(instance!=null&&instance.isConnected()){
            try {
                instance.close();
                Log.w("MQTT","重连");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void ondestory(){
        if(instance!=null&&instance.isConnected()){
            try {
                instance.disconnect();
                Log.w("MQTT","断开");
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
}