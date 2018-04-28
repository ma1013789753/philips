package com.gaoda.manager;

import android.content.Context;

/**
 * @Created: Suqi
 * @Date: 2017/12/1
 * @Description:
 */

public interface Constant {
    String GENERAL_SP_NAME = "sp_public_app";
    int GENERAL_SP_MODE = Context.MODE_PRIVATE;
    String MEDIATYPE_JSON = "application/json";
    String USER_TOKEN = "user_token";
    String ENDUSER_ID = "enduser_id";
    String END_POINT = "endpoint";
    String USER_PHONE_NUMBER = "phone_number";
    String PRODUCT_ID = "product_id";
    String TYPE_ID = "type_id";
    String SP_PLACE = "local";
    String SP_LANGUAGE = "language";
    String SP_CLIENT_ID = "sp_client_id";

    String SP_SERVER_IS_ONLINE ="server_is_online";//环境-是否是生产环境
    String SP_NET_IS_AP ="net_is_ap";//环境-配网方式

    String BAIDU_ACCESS_KEY_ID = "fba8a9c577a443ab80d1f7111ea97ea3";
    String BAIDU_SECRET_ACCESS_KEY = "3181d962dff94ebd91a30035db68736a";
    String BAIDU_ENDPOINT="https://fog-pub-test.gz.bcebos.com";
    String BAIDU_BUCKETNAME="fog-pub-app";
    String UPDATE_OTA_PROGRESS="action.update.ota.progress";
}
