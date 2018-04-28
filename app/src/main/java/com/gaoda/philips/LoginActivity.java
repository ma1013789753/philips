package com.gaoda.philips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.base.MyApplication;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.httpUtil.OkGoUtil;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.gaoda.util.StringUtils;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.forgetpsw)
    TextView forgetpsw;
    @BindView(R.id.regist)
    TextView regist;
    @BindView(R.id.backbar)
    ImageView backbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.loginblue);

    }

    @OnClick({R.id.login, R.id.forgetpsw, R.id.regist,R.id.backbar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (StringUtils.isEmpty(username.getText().toString().trim())) {
                    showToast(" Please enter phone number");
                } else if (StringUtils.isEmpty(password.getText().toString().trim())) {
                    showToast(" Please enter password");
                } else {
                    login();
                }
                break;
            case R.id.forgetpsw:
//                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.regist:
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.backbar:
                finish();
                break;
        }
    }

    public void login() {

        HttpModleUtil.Login(this, username.getText().toString().trim(), password.getText().toString().trim(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                JSONObject data = response.body();
                try {
                    if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                        return;
                    }
                    JSONObject meta = data.getJSONObject("meta");
                    if ("login successful".equals(meta.getString("message"))) {
                        String token = data.getJSONObject("data").getString("token");
                        String endUserId = data.getJSONObject("data").getString("enduser_id");
                        MyApplication.getInstance().setToken(token);
                        MyApplication.getInstance().setUSername(username.getText().toString().trim());
                        MyApplication.getInstance().setEndUserId(endUserId);
                        OkGoUtil.setToken(token);
                        finish();
                    } else {
                        showToast(meta.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

                    @Override
                    public void onFail(Response<JSONObject> response) {

                    }

                }
        );
    }

    @OnClick(R.id.backbar)
    public void onViewClicked() {
        finish();
    }
}
