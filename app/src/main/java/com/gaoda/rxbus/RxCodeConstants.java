package com.gaoda.rxbus;


/**
 * @Created: Suqi
 * @Date: 2018/1/23
 * @Description:
 */

public interface RxCodeConstants {
    int JUMP_TYPE = 0;//code多类型接收

    int TYPE_USER_LOGIN = 1;//登录
    int TYPE_USER_LOGOUT = 2;//登出
    int TYPE_USERINFO_CHANGED = 3;//用户信息改变
    int TYPE_DEVICE_BIND_FAIL = 4;//绑定失败，重试
    int TYPE_DEVICE_STATUS_CHANGE = 5;//设备状态改变
    int TYPE_REGET_DEVICE_STATE = 6;//mqtt重连后重新获取设备状态
    int TYPE_TO_UNSUBSCRIBE = 7;//不配网后关闭订阅获取device_id通道
    int TYPE_TO_SUBSCRIBE = 8; //softap配网时通知订阅获取device_id通道
    int TYPE_TO_BINDDEVICE = 9; //通知配网界面去绑定设备
    int TYPE_BIND_SUCCESS_FINISH = 10; //配网成功关闭界面
    int TYPE_UNBIND_DEVICE_SUCCESS = 11; //设备解绑成功
    int TYPE_BIND_SUCCESS_WITH_SQRCODE = 12; //扫二维码绑定成功
    int TYPE_RESET_DEVICE_NAME_SUCCESS = 13; //配网后改名成功
    int TYPE_FIRMWARE_UPDATE_RESULT = 14; //固件升级结果
    int TYPE_OTA_PROGRESS = 15;//ota进度
    int TYPE_START_FIRMWARE = 19;// 开始ota
    int TYPE_INVITATION_STATE = 16;//ota进度
    int TYPE_INVITATION_ACCEMPT = 17;//ota进度
    int TYPE_HEAD_IMAGE_CHANGE = 18;//ota进度
    int TYPE_UPDATE_DEVICE_ONLINE_STATE = 19;//刷新设备列表界面在线状态
    int TYPE_TO_GET_MQTTINFO = 20;//获取mqtt信息启动mqtt
}
