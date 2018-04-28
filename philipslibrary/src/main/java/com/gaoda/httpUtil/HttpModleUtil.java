package com.gaoda.httpUtil;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpModleUtil  {
    //注册
    /**
     * @param context 上下文
     * @param userName 用户Id
     * @param Appid 设备appid
     * @param callBack 回调函数
     * 16000	The enduser has been registered	400
     */
    public static void REGIST(Context context,String userName,String Appid, JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("app_id",Appid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPost(context,HttpContent.REGIST,jsonObject,callBack);
    }

    //登录

    /**
     * @param context 上下文
     * @param userName 用户Id
     * @param Appid 设备appid
     * @param callBack 回调函数
     *  16001	Invalid enduser	400
     */

    public static void Login(Context context,String userName,String Appid,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",userName);
            jsonObject.put("app_id",Appid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPut(context,HttpContent.LOGIN,jsonObject,callBack);
    }
    //获取设备列表
    /**
     * @param context 上下文
     * @param callBack 回调方法
     *
     */
    public static void deviceList(Context context,JsonCallBack callBack){
        OkGoUtil.OkGet(context,HttpContent.GETALLDVICE,new HashMap<String, String>(),callBack);
    }
    //添加设备
    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param callBack 回调方法
     * @param registration_id 极光registration_id
     *  16003	Invalid device_id	400
        16004	The [product_id] has been bound to the [app_id]	400
        16005	The device has been bound to the superuser	400
     */
    public static void bindDevice(Context context,String deviceId,String registration_id,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
            jsonObject.put("registration_id",registration_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPost(context,HttpContent.BINDDVICE,jsonObject,callBack);
    }
    //获取设备远程
    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param callBack 回调方法
     *
     *   16015	device_id and mac is not matched	400
        16016	mac is not exist	400
        16011	No binding between you and device	400
     */
    public static void getHost(Context context,String deviceId,JsonCallBack callBack){

        Map<String,String> jsonObject = new HashMap<>();

        jsonObject.put("device_id",deviceId);

        OkGoUtil.OkGet(context,HttpContent.GETHOST,jsonObject,callBack);

    }

    //获取设备详情
    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param callBack 回调方法
     *
     *   16015	device_id and mac is not matched	400
        16016	mac is not exist	400
        16011	No binding between you and device	400
     */
    public static void deviceDetail(Context context,String deviceId,JsonCallBack callBack){

        Map<String,String> jsonObject = new HashMap<>();

        jsonObject.put("device_id",deviceId);


        OkGoUtil.OkGet(context,HttpContent.DEVICEDETAIL,jsonObject,callBack);

    }
    //修改设备别名
    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param name 设备新名称
     * @param callBack 回调方法
     *
     * 16011	No binding between you and device	400
     */
    public static void modifyName(Context context,String deviceId,String name,JsonCallBack callBack){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
            jsonObject.put("device_alias",name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGoUtil.OkPut(context,HttpContent.MODIFYNAME,jsonObject,callBack);

    }
    //获取用户列表

    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param callBack 回调方法
     * 16017	The device has no superuser	400
     */
    public static void userList(Context context,String deviceId,JsonCallBack callBack){

        Map<String,String> jsonObject = new HashMap<>();

        jsonObject.put("device_id",deviceId);


        OkGoUtil.OkGet(context,HttpContent.USERLIST,jsonObject,callBack);

    }
    //设置超级权限

    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param enduserId 用户Id
     * @param callBack 回调方法
     *  16003	Invalid device_id.	400
        16001	Invalid enduser	400
        16013	No binding between user with device	400
        16014	User is not superuser. No permission.	400
     */
    public static void setAdmin(Context context,String deviceId,String enduserId,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
            jsonObject.put("enduser_id",enduserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPost(context,HttpContent.SETADMIN,jsonObject,callBack);
    }
    //删除用户
    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param enduserId 用户Id
     * @param callBack 回调方法
     * 16011	No binding between you and device.	400
        16012	No permission to operate.	400
     */
    public static void deleteUser(Context context,String deviceId,String enduserId,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
            jsonObject.put("enduser_id",enduserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPut(context,HttpContent.DELETEUSER,jsonObject,callBack);
    }
    //获取历史数据

    /**
     * @param context 上下文
     * @param deviceId 设备Id
     * @param callBack 回调方法
     *
     *  16013	No binding between user with device	400
        16018	Exorbitant query frequency	400
     */
    public static void history(Context context,String deviceId,JsonCallBack callBack){

        Map<String,String> jsonObject = new HashMap<>();

        jsonObject.put("device_id",deviceId);


        OkGoUtil.OkGet(context,HttpContent.HISTORY,jsonObject,callBack);

    }
    //解除设备

    /**
     *
     * @param context 上下文
     * @param deviceId 设备Id
     * @param callBack 回调方法
     * 16002	Not binding to the device	400
     */
    public static void unBindDevice(Context context,String deviceId,JsonCallBack callBack){

        Map<String,String> jsonObject = new HashMap<>();

        OkGoUtil.OkDelete(context,HttpContent.UNBINDDVICE + "?device_id=" + deviceId,null,callBack);
    }
    //分享设备码
    /**
     *
     * @param context
     * @param deviceId
     * @param callBack
     *
     * 16003	Invalid device_id	400
        16006	No permission: need admin or superuser	400
        16007	role is error:[%d]--[%d]	400
     */
    public static void shareCode(Context context,String deviceId,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPost(context,HttpContent.REATECODE,jsonObject,callBack);
    }

    /**
     * 授权设备
     * @param context
     * @param deviceId
     * @param vercode
     * @param callBack
     * @param registration_id 极光Id
     * 16008	Captcha error.	400
        16009	Captcha is expired	400
     */
    public static void adminCode(Context context,String deviceId,String vercode,String registration_id,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
            jsonObject.put("vercode",vercode);
            jsonObject.put("registration_id",registration_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPost(context,HttpContent.ADMIN,jsonObject,callBack);
    }

    /**
     * 发送极光推送
     * @param context
     * @param deviceId
     * @param message
     * @param callBack
     */
    public static void sendMsg(Context context,String deviceId,String message,JsonCallBack callBack){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device_id",deviceId);
            jsonObject.put("message",message);
            jsonObject.put("notification",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGoUtil.OkPost(context,HttpContent.SENDMESSAGE,jsonObject,callBack);
    }
    /**
     * 16000	The enduser has been registered	400
     16001	Invalid enduser	400
     16002	Not binding to the device	400
     16003	Invalid device_id 400
     16004	The [product_id] has been bound to the [app_id]	400
     16005	The device has been bound to the superuser	400
     16006	No permission: need admin or superuser	400
     16007	role is error	400
     16008	Captcha error	400
     16009	Captcha is expired	400
     16010	App doesn't be granted to product	400
     16010	You have been granted to device	400
     16011	No binding between you and device	400
     16012	No permission to operate	400
     16013	No binding between user with device	400
     16014	User is not superuser. No permission	400
     16015	device_id and mac is not matched	400
     16016	mac is not exist	400
     16017	The device has no superuser	400
     16018	Exorbitant query frequency	400
     */
}
