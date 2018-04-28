//package com.gaoda.manager;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.content.LocalBroadcastManager;
//import android.util.Log;
//
//
//import com.alibaba.fastjson.JSON;
//import com.gaoda.base.MyApplication;
//import com.gaoda.rxbus.RxBus;
//import com.gaoda.rxbus.RxBusBaseMessage;
//import com.gaoda.rxbus.RxCodeConstants;
//import com.gaoda.util.StringUtils;
//
//
//import java.util.ArrayList;
//
//import io.fogcloud.sdk.mqtt.api.MQTT;
//import io.fogcloud.sdk.mqtt.helper.ListenDeviceCallBack;
//import io.fogcloud.sdk.mqtt.helper.ListenDeviceParams;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//
///**
// * @Created: Suqi
// * @Date: 2017/12/28
// * @Description:
// */
//
//public class MqttManager {
//
//    private ArrayList<String> topicList = new ArrayList<>();
//    private ArrayList<String> newList = new ArrayList<>();
//    private MQTT mqtt;
//    private static MqttManager manager;
//    private String currentDeviceId = "";
//    private Disposable subscriptions;
//    private boolean isStart = false;//mqtt是否启动
//    private String topic;
//    private Context context;
//    private ListenDeviceParams listenDeviceParams;
//
//    private MqttManager() {
//
//    }
//
//    public void init(Context context) {
//        mqtt = new MQTT(context);
//        this.context=context;
//        registerRxBus();
//    }
//
//    public static MqttManager getManager() {
//        if (null == manager) {
//            manager = new MqttManager();
//        }
//        return manager;
//    }
//
//    /**
//     * connect the mqtt server
//     * <p>
//     * //     * @param listendevparams the paraments of listener
//     */
//    public void startMqtt(ListenDeviceParams params) {
//        listenDeviceParams = params;
//        mqtt.startMqtt(params, new ListenDeviceCallBack() {
//            @Override
//            public void onSuccess(int code, String message) {
//                super.onSuccess(code, message);
//                Log.d("suqi", "su" + message + code);
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                super.onFailure(code, message);
//                Log.d("suqi", "fail" + message + code);
//            }
//
//            @Override
//            public void onDeviceStatusReceived(int code, String messages) {
//                super.onDeviceStatusReceived(code, messages);
//                Log.d("suqi", "rece" + messages + code);
//                //mqtt断开连接
//                if (code == 4216 || code == 4217)
//                    isStart = false;
//
//                //通知设备列表界面更新在线状态
//                if (code == 4200 && !StringUtils.isEmpty(messages) && messages.contains("online")) {
//                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
//                            new RxBusBaseMessage(RxCodeConstants.TYPE_UPDATE_DEVICE_ONLINE_STATE, messages));
//                }
//
//                //通知控制界面刷新界面
//                if (code == 4200 && !StringUtils.isEmpty(currentDeviceId) &&
//                        !StringUtils.isEmpty(messages) && messages.contains(currentDeviceId)) {
////                    BaseUtils.sendMessage(myHandler, 500, messages);
//                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
//                            new RxBusBaseMessage(RxCodeConstants.TYPE_DEVICE_STATUS_CHANGE, messages));
//                }
//                //ota进度
//                if (code == 4200 && !StringUtils.isEmpty(messages) &&
//                        (messages.contains("ota_progress") || messages.contains("ota_result"))){
////                    BaseUtils.sendMessage(myHandler, 700, messages);
//
//                    Intent intent=new Intent(Constant.UPDATE_OTA_PROGRESS);
//                    intent.putExtra("progress",messages);
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
////                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
////                            new RxBusBaseMessage(RxCodeConstants.TYPE_OTA_PROGRESS, messages));
//                }
//
//                //mqtt启动成功后开始循环订阅
//                if (code == 4210) {
//                    isStart = true;
////                    BaseUtils.sendMessage(myHandler, 100, messages);
//                    toSubscribe();
////                    if (BaseUtils.isNotNull(topicList) && topicList.size() > 0) {
////                        for (String string : topicList){
////                            subscribe(string, 1);
////                        }
////                    } else {
////                        toSubscribe();
////                    }
//                }
//
//                //mqtt重连通知重新获取设备状态
//                if (code == 4214) {
////                    BaseUtils.sendMessage(myHandler, 600, "");
//                    RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
//                            new RxBusBaseMessage(RxCodeConstants.TYPE_REGET_DEVICE_STATE, new Object()));
//                }
//
//                if (messages.contains("topic") && !messages.endsWith("\"payload\":}")) {
//                    String topic = JSON.parseObject(messages).getString("topic");
//                    if (topic.equals(getSoftApTopic(MyApplication.getInstance().getString(Constant.ENDUSER_ID)))) {
//                        String json = JSON.parseObject(messages).getString("payload");
//                        if (!StringUtils.isEmpty(json)) {
//                            String data = JSON.parseObject(json).getString("data");
//                            String result = JSON.parseObject(data).getString("device_id");
//                            //通知去绑定设备
//                            Log.d("suqi", "收到retain消息");
////                            BaseUtils.sendMessage(myHandler, 200, result);
//                            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
//                                    new RxBusBaseMessage(RxCodeConstants.TYPE_TO_BINDDEVICE, result));
//                            //清空
//                            publish(getSoftApTopic(MyApplication.getInstance().getString(Constant.ENDUSER_ID)), null, 0, true);
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * send command
//     *
//     * @param topic    topics
//     * @param command  the command to the Home_Tabbar_Title
//     * @param qos      012
//     * @param retained 0
//     */
//    public void publish(String topic,
//                        String command,
//                        int qos,
//                        boolean retained) {
//        Log.d("suqi", "下发指令:" + (StringUtils.isEmpty(command) ? "" : command));
//        mqtt.publish(topic, command, qos, retained, new ListenDeviceCallBack() {
//            @Override
//            public void onSuccess(int code, String message) {
//                super.onSuccess(code, message);
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                super.onFailure(code, message);
//            }
//
//            @Override
//            public void onDeviceStatusReceived(int code, String messages) {
//                super.onDeviceStatusReceived(code, messages);
//            }
//        });
//    }
//
//    /**
//     * add topic
//     *
//     * @param topic topics
//     */
//
//    public void subscribe(String topic, int qos) {
//        mqtt.subscribe(topic, qos, new ListenDeviceCallBack() {
//            @Override
//            public void onSuccess(int code, String message) {
//                super.onSuccess(code, message);
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                super.onFailure(code, message);
//            }
//
//            @Override
//            public void onDeviceStatusReceived(int code, String messages) {
//                super.onDeviceStatusReceived(code, messages);
//            }
//        });
//    }
//
//    /**
//     * remove the topic
//     *
//     * @param topic     topics
//     */
//    public void unsubscribe(String topic) {
//
//        mqtt.unsubscribe(topic, new ListenDeviceCallBack() {
//            @Override
//            public void onSuccess(int code, String message) {
//                super.onSuccess(code, message);
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                super.onFailure(code, message);
//            }
//
//            @Override
//            public void onDeviceStatusReceived(int code, String messages) {
//                super.onDeviceStatusReceived(code, messages);
//                Log.e("suqi", "unsubscribe:" + messages + code);
//            }
//        });
//    }
//
//    /**
//     * close the connect
//     */
//    public void stopMqtt() {
//
//        mqtt.stopMqtt(new ListenDeviceCallBack() {
//            @Override
//            public void onSuccess(int code, String message) {
//                super.onSuccess(code, message);
//            }
//
//            @Override
//            public void onFailure(int code, String message) {
//                super.onFailure(code, message);
//            }
//
//            @Override
//            public void onDeviceStatusReceived(int code, String messages) {
//                super.onDeviceStatusReceived(code, messages);
//            }
//        });
//    }
//
//    public void pauseMqtt() {
//        mqtt.onPause();
//    }
//
//    public void reConnect() {
//        mqtt.reConnect();
//    }
//
//    public void putDeviceId(String deviceId) {
//        currentDeviceId = deviceId;
//    }
//
//    public void removeDeviceId() {
//        currentDeviceId = "";
//    }
//
//    public void setTopicList(ArrayList<String> list) {
//        if ((list)!=null && list.size() > 0) {
//            newList.clear();
//            newList.addAll(list);
////            BaseUtils.sendMessage(myHandler, 100, "");
//            toSubscribe();
//        }
//    }
//
//    private void toSubscribe(){
//        //程序打开无网络再次去请求mqttinfo
//        if ((listenDeviceParams)==null)
//            RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE,
//                    new RxBusBaseMessage(RxCodeConstants.TYPE_TO_GET_MQTTINFO, new Object()));
//        if (isStart) {
//            if (newList!=null && newList.size() > 0) {
//                if (topicList.size() > 0) {
//                    for (String nl : newList) {
//                        if (!topicList.contains(nl)) {
//                            subscribe(nl, 1);
//                        } else {
//                            topicList.remove(nl);
//                        }
//                    }
//                    for (int j = 0; j < topicList.size(); j++) {
//                        if (StringUtils.isEmpty(topic) || !topicList.get(j).equals(topic)) {
//                            unsubscribe(topicList.get(j));
//                        }
//                    }
//                    topicList.clear();
//                } else {
//                    for (int i = 0; i < newList.size(); i++) {
//                        subscribe(newList.get(i), 1);
//                    }
//                }
//                topicList.addAll(newList);
//            }
//        }
//    }
//    public void clearTopic() {
//        if ((topicList)!=null && topicList.size() > 0) {
//            topicList.clear();
//        }
//    }
//
//    //接收通知
//    private void registerRxBus() {
//        subscriptions = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, RxBusBaseMessage.class).subscribe(new Consumer<RxBusBaseMessage>() {
//            @Override
//            public void accept(RxBusBaseMessage rxBusBaseMessage) throws Exception {
//                switch (rxBusBaseMessage.getCode()) {
//                    case RxCodeConstants.TYPE_TO_SUBSCRIBE:
////                    BaseUtils.sendMessage(myHandler, 300, "");
//                        //设备绑定时获取device_id通道
//                        if (!StringUtils.isEmpty(MyApplication.getInstance().getString(Constant.ENDUSER_ID))
//                                && !StringUtils.isEmpty(MyApplication.getInstance().getString(Constant.END_POINT))) {
//                            topic = getSoftApTopic(MyApplication.getInstance().getString(Constant.ENDUSER_ID));
//                            if (!topicList.contains(topic)) {
//                                topicList.add(topic);
//                            }
//                            subscribe(topic, 1);
//                        }
//                        break;
//                    case RxCodeConstants.TYPE_TO_UNSUBSCRIBE:
////                    BaseUtils.sendMessage(myHandler, 400, "");
//                        unsubscribe(topic);
//                        if (topicList.contains(topic)) {
//                            topicList.remove(topic);
//                        }
//                        break;
//                }
//            }
//        });
//    }
//
//    public void detach() {
//        if (!subscriptions.isDisposed()) {
//            subscriptions.dispose();
//        }
//    }
//
//    /**
//     * softAp配网时订阅
//     *
//     *
//     * @param enduser_id
//     * @return
//     */
//    public static String getSoftApTopic(String enduser_id) {
//
//       return "$baidu/iot/general/" + enduser_id + "/m2m/json";
//
//    }
//}
