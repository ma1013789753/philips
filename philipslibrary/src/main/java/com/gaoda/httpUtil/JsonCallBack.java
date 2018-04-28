package com.gaoda.httpUtil;


import com.lzy.okgo.model.Response;

import org.json.JSONObject;


public  interface JsonCallBack {
    public void onSuccess(Response<JSONObject> response);
    public void onFail(Response<JSONObject> response);
}
