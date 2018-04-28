package com.gaoda.httpUtil;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.gaoda.util.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 网络连接以及相关方法
 */
public class OkGoUtil {
    private static String token = "";
    private static  Callback<JSONObject> call;
    public static void setToken(String str){
        token = str;
    }

    //使用默认的Https协议
    public static void initHttps(Application app){

        OkGo.getInstance().init(app);

    }

    //发送简单的get请求
    public static void OkGet(final Context context, String Url, Map<String,String> map, final JsonCallBack callBack){
        HttpHeaders headers = new HttpHeaders();
//        headers.put("Content-Type", "application/json;charset:utf-8");
        if(!StringUtils.isEmpty(token)){
            headers.put("Authorization","jwt "+token);
        }
        OkGo.<JSONObject>get(Url)
                .tag(context)
                .params(map)
                .headers(headers)
                .execute(getCall(context,callBack));
    }

    //发送简单的get请求
    public static void OkDelete(final Context context, String Url, Map<String,String> map, final JsonCallBack callBack){
        HttpHeaders headers = new HttpHeaders();
        if(!StringUtils.isEmpty(token)){
            headers.put("Authorization","jwt "+token);
        }
        OkGo.<JSONObject>delete(Url)
                .tag(context)
                .headers(headers)
                .params(map)
                .execute(getCall(context,callBack));
    }

    //发送简单的get请求
    public static void OkPost(final Context context, String Url, JSONObject map, final JsonCallBack callBack){
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", "application/json;charset:utf-8");
        if(!StringUtils.isEmpty(token)){
            headers.put("Authorization","jwt "+token);
        }
        OkGo.<JSONObject>post(Url)
                .upJson(map)
                .tag(context)
                .headers(headers)
                .execute(getCall(context,callBack));
    }

    //发送简单的get请求
    public static void OkPut(final Context context, String Url, JSONObject map,final JsonCallBack callBack){
        HttpHeaders headers = new HttpHeaders();
        headers.put("Content-Type", "application/json;charset:utf-8");
        if(!StringUtils.isEmpty(token)){
            headers.put("Authorization","jwt "+token);
        }
        OkGo.<JSONObject>put(Url)
                .tag(context)
                .upJson(map)
                .headers(headers)
                .execute(getCall(context,callBack));
    }


    public static Callback<JSONObject> getCall(final Context context,final JsonCallBack callBack) {

        Callback<JSONObject> call = new Callback<JSONObject>() {
            @Override
            public void onStart(Request<JSONObject, ? extends Request> request) {

            }

            @Override
            public void onSuccess(Response<JSONObject> response) {
                JSONObject jsonObject = response.body();
                    try {
                        if (0 == jsonObject.getJSONObject("meta").getInt("code")) {
                            callBack.onSuccess(response);
                        } else {
                            callBack.onSuccess(response);
                            Toast.makeText(context, jsonObject.getJSONObject("meta").getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onCacheSuccess(Response<JSONObject> response) {

            }

            @Override
            public void onError(Response<JSONObject> response) {
                callBack.onFail(response);
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void uploadProgress(Progress progress) {

            }

            @Override
            public void downloadProgress(Progress progress) {

            }

            @Override
            public JSONObject convertResponse(okhttp3.Response response) throws Throwable {
                String s = new StringConvert().convertResponse(response);
                return new JSONObject(s);
            }
        };
        return call;
    }
}
