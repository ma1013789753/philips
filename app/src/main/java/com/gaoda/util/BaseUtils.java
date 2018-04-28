package com.gaoda.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Created: Suqi
 * @Date: 2018/1/9
 * @Description: 工具类
 */

public class BaseUtils {
    /**
     * 判断 Object 是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        if (null == obj) {
            return true;
        }
        return false;
    }

    /**
     * 判断 Object 是否为空
     *
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj) {
        if (null == obj) {
            return false;
        }
        return true;
    }

    /**
     * 判断 String 是否为空或者为“”
     *
     * @param obj
     * @return
     */
    public static boolean isNullString(String obj) {
        if (null == obj) {
            return true;
        } else {
            if (obj.length() <= 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * handler 发送 message
     *
     * @param handler
     * @param what
     * @param obj
     */
    public static void sendMessage(Handler handler, int what, String obj) {
        Message messageHandle = new Message();
        messageHandle.what = what;
        messageHandle.obj = obj;
        handler.sendMessage(messageHandle);
    }


    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    private static Toast toast;

    public static void showShortToast(Context context, CharSequence message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        String version = "";
        try {

            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            version = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断密码是否格式正确
     *
     * @param
     * @return
     */
    public static boolean isPwdFormat(String pwd) {
        String str = "^\\w{6,30}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(pwd);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证手机号是否正确
     *
     * @param phone 手机号码
     * @return boolean
     */
    public static boolean isPhone(String phone) {
        String str = "^(13|14|15|16|17|18|19)\\d{9}$";
        Pattern p = Pattern.compile(str);
        return p.matcher(phone).matches();
    }

    /**
     * 用****替换手机号中间四位
     *
     * @param phoneNum
     */
    public static String hidePhoneNum(String phoneNum) {
        if (isNullString(phoneNum)) {
            return "";
        }
        String phoneNum_new = phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        return phoneNum_new;
    }

    //判断是否是表情
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff" +
                "]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * 接口返回code
     *
     * @param message
     * @return
     */
    public static int getCode(String message) {
        String meta = JSON.parseObject(message).getString("meta");
        return JSON.parseObject(meta).getInteger("code");
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
