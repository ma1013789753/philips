package com.gaoda.philips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.base.BaseActivity;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.gaoda.util.StringUtils;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.regist)
    Button regist;
    @BindView(R.id.forgetpsw)
    TextView forgetpsw;
    @BindView(R.id.tologin)
    TextView tologin;
    @BindView(R.id.backbar)
    ImageView backbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.loginblue);
    }

    @OnClick({R.id.regist, R.id.forgetpsw, R.id.tologin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regist:
                if (StringUtils.isEmpty(username.getText().toString().trim())) {

                    showToast(" Please enter phone number");
                } else if (StringUtils.isEmpty(password.getText().toString().trim())) {
                    showToast(" Please enter password ");
                } else {
                    regist();
                }
                break;
            case R.id.forgetpsw:
                break;
            case R.id.tologin:
                finish();
                break;
            case R.id.backbar:
                finish();
                break;
        }
    }

    public void regist() {
        HttpModleUtil.REGIST(this, username.getText().toString().trim(), password.getText().toString().trim(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                    return;
                }
                JSONObject data = response.body();
                try {
                    JSONObject meta = data.getJSONObject("meta");
                    if ("enduser signup successful".equals(meta.getString("message"))) {
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
        });
    }
}
