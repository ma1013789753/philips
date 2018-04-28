package com.gaoda.philips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.adapter.UserAdapter;
import com.gaoda.base.BaseActivity;
import com.gaoda.bean.UserBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.util.ReTurnUtil;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserListActivity extends BaseActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.menubar)
    ImageView menubar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    private String deviceId;
    private UserAdapter adapter;
    private List<UserBean> datas;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        ButterKnife.bind(this);
        deviceId = getIntent().getStringExtra("deviceId");
        initView();
        initData();
    }

    private void initView() {
        datas = new ArrayList<>();
        adapter = new UserAdapter(this,datas);
        adapter.setClick(new UserAdapter.ItemClick() {
            @Override
            public void onClick() {

            }

            @Override
            public void onDelelete(int position) {
                deleteUser(datas.get(position));
            }

            @Override
            public void onAdmin(int position) {
                setAdmin(datas.get(position));
            }
        });
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
    }

    private void initData() {
        datas.clear();
        HttpModleUtil.userList(this, deviceId, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                    return;
                }
                try {
                    JSONObject json = response.body();
                    Gson gson = new Gson();
                    JSONArray array = json.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        datas.add(gson.fromJson(array.getString(i),UserBean.class));
                    }
                    Log.w("xxxx",datas.size()+"__");
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }
    //删除用户
    private void deleteUser(UserBean bean){
        HttpModleUtil.deleteUser(this, deviceId, bean.getEnduser_id(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                initData();
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }
    //设置admin
    private void setAdmin(UserBean bean){
        HttpModleUtil.setAdmin(this, deviceId, bean.getEnduser_id(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                initData();
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }

    @OnClick(R.id.backbar)
    public void onViewClicked() {
        finish();
    }
}
