package com.gaoda.httpUtil;

public class HttpContent {
    //主机地址
    private static String HOST = "https://philipsapi.fogcloud.io/";
    //注册终端用户
    public static String REGIST = HOST+"enduser/signup/";
    //登录终端用户
    public static String LOGIN = HOST+"enduser/login/";
    //绑定终端设备
    public static String BINDDVICE = HOST+"enduser/bindDevice/";
    //解除终端设备绑定
    public static String UNBINDDVICE = HOST+"enduser/unbindDevice/";
    //终端用户获取绑定设备列表
    public static String GETALLDVICE = HOST+"enduser/deviceList/";
    //超级用户生成分享码（5分钟有效期）
    public static String REATECODE = HOST+"enduser/shareCode/";
    //超级用户通过分享码授权设备权限
    public static String ADMIN = HOST+"enduser/grantDevice/";
    //获取设备socket远程
    public static String GETHOST = HOST + "enduser/mqttInfo/";
    //设备详情
    public static String DEVICEDETAIL = HOST + "enduser/deviceInfo/";
    //修改设备别名
    public static String MODIFYNAME = HOST + "enduser/updateDeviceAlias/";
    //获取设备用户
    public static String USERLIST = HOST + "enduser/enduserList/";
    //转移权限
    public static String SETADMIN = HOST + "enduser/transferAdmin/";
    //删除用户
    public static String DELETEUSER = HOST + "enduser/removeBindRole//";
    //历史数据
    public static String HISTORY = HOST + "enduser/deviceData/";
    //发送通知
    public static String SENDMESSAGE = HOST + "push/";

}
