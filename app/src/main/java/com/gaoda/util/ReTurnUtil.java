package com.gaoda.util;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class ReTurnUtil {

    public static boolean isSuccess(Response<JSONObject> response, Context context){
        JSONObject json = response.body();
        if(json!=null&&json.has("meta")){
            try {
                JSONObject meta = json.getJSONObject("meta");
                if (meta.has("code")&&meta.getInt("code")==0){
                    return true;
                }else{
                    Toast.makeText(context,meta.getString("message"),Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
