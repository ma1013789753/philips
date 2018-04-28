package com.gaoda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.gaoda.log.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义消息接收器
 * <p>
 *     如果不定义这个 Receiver，则：
 *     1) 默认用户会打开主界面
 *     2) 接收不到自定义消息
 * </p>
 *
 */
public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtil.w(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                //SDK 向 JPush Server 注册所得到的注册 ID
                LogUtil.w(TAG, "接收RegistrationId");
//                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                //收到了自定义消息 Push
                LogUtil.w(TAG, "接收到推送下来的自定义消息");
//                //1.消息标题
//                String title = bundle.getString(JPushInterface.EXTRA_TITLE);
//                //2.消息内容
//                String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//                //3.消息附加字段
//                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                //4.消息唯一标识ID
//                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                //收到了通知 Push
                LogUtil.w(TAG, "接收到推送下来的通知");
//                //1.消息标题
//                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//                //2.消息内容
//                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//                //3.消息附加字段
//                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                //4.通知栏的Notification ID，可以用于清除Notification
//                String notificationId = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ID);
//                //5.富媒体通知推送下载的HTML的文件路径,用于展现WebView。
//                String fileHtml = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_PATH);
//                //6.富媒体通知推送下载的图片资源的文件名
//                String fileStr = bundle.getString(JPushInterface.EXTRA_RICHPUSH_HTML_RES);
//                String[] fileNames = fileStr.split(",");
//                //7.唯一标识通知消息的 ID, 可用于上报统计等。
//                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
//                //8.大文本通知样式中大文本的内容。
//                String bigText = bundle.getString(JPushInterface.EXTRA_BIG_TEXT);
//                //9.大图片通知样式中大图片的路径/地址
//                String bigPicPath = bundle.getString(JPushInterface.EXTRA_BIG_PIC_PATH);
//                //10.收件箱通知样式中收件箱的内容
//                String inboxJson = bundle.getString(JPushInterface.EXTRA_INBOX);
//                //11.通知的优先级。默认为0，范围为 -2～2 ，其他值将会被忽略而采用默认。
//                String priority = bundle.getString(JPushInterface.EXTRA_NOTI_PRIORITY);
//                //12.通知分类。
//                String category = bundle.getString(JPushInterface.EXTRA_NOTI_CATEGORY);
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                //(1)用户点击了通知。 一般情况下，用户不需要配置此 receiver action。
                //(2)如果开发者在 AndroidManifest.xml 里未配置此 receiver action，那么，SDK 会默认打开应用程序的主 Activity，相当于用户点击桌面图标的效果。
                //(3)如果开发者在 AndroidManifest.xml 里配置了此 receiver action，那么，当用户点击通知时，SDK 不会做动作。开发者应该在自己写的 BroadcastReceiver 类里处理，比如打开某 Activity 。
                LogUtil.w(TAG, "[用户点击打开了通知");
                //1.消息标题
//                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//                //2.消息内容
//                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
//                //3.消息附加字段
//                String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
//                //4.知栏的Notification ID，可以用于清除Notification
//                int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//                //5.唯一标识调整消息的 ID, 可用于上报统计等。
//                String msgId = bundle.getString(JPushInterface.EXTRA_MSG_ID);
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //(1)用户点击了通知栏中自定义的按钮。(SDK 3.0.0 以上版本支持)
                //(2)使用普通通知的开发者不需要配置此 receiver action。只有开发者使用了 MultiActionsNotificationBuilder 构建携带按钮的通知栏的通知时，可通过该 action 捕获到用户点击通知栏按钮的操作，并自行处理。
                LogUtil.w(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                /**
                 * <p>
                 *     private void setAddActionsStyle() {
                 *        MultiActionsNotificationBuilder builder = new MultiActionsNotificationBuilder(PushSetActivity.this);
                 *        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "first", "my_extra1");
                 *        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "second", "my_extra2");
                 *        builder.addJPushAction(R.drawable.jpush_ic_richpush_actionbar_back, "third", "my_extra3");
                 *        JPushInterface.setPushNotificationBuilder(10, builder);
                 *        Toast.makeText(PushSetActivity.this, "AddActions Builder - 10", Toast.LENGTH_SHORT).show();
                 }
                 * </p>
                 */
                /**
                 * <p>
                 *      Log.d(TAG, "[MyReceiver] 用户点击了通知栏按钮");
                 String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);
                 //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
                 if(nActionExtra==null){
                 Log.d(TAG,"ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
                 return;
                 }
                 if (nActionExtra.equals("my_extra1")) {
                 Log.d(TAG, "[MyReceiver] 用户点击通知栏按钮一");
                 } else if (nActionExtra.equals("my_extra2")) {
                 Log.d(TAG, "[MyReceiver] 用户点击通知栏按钮二");
                 } else if (nActionExtra.equals("my_extra3")) {
                 Log.d(TAG, "[MyReceiver] 用户点击通知栏按钮三");
                 } else {
                 Log.d(TAG, "[MyReceiver] 用户点击通知栏按钮未定义");
                 }
                 * </p>
                 */

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                //JPush 服务的连接状态发生变化。（注：不是指 Android 系统的网络连接状态。）
                LogUtil.w(TAG, "JPush服务连接状态发生改变");
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            } else {
                LogUtil.w(TAG, "未处理的Intent");
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtil.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }
}