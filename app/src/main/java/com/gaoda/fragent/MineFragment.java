package com.gaoda.fragent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaoda.base.BaseFragment;
import com.gaoda.base.MyApplication;
import com.gaoda.philips.LoginActivity;
import com.gaoda.philips.MineActivity;
import com.gaoda.philips.MyDeviceActivity;
import com.gaoda.philips.R;
import com.gaoda.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment {
    @BindView(R.id.userlogin)
    LinearLayout userlogin;
    @BindView(R.id.userinfo)
    LinearLayout userinfo;
    Unbinder unbinder;
    @BindView(R.id.myaccount)
    LinearLayout myaccount;
    @BindView(R.id.mydevice)
    LinearLayout mydevice;
    @BindView(R.id.username)
    TextView username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, null);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();

    }

    private void initView() {
        if (StringUtils.isEmpty(MyApplication.getInstance().getToken())) {
            userinfo.setVisibility(View.GONE);
            userlogin.setVisibility(View.VISIBLE);
        } else {
            userlogin.setVisibility(View.GONE);
            userinfo.setVisibility(View.VISIBLE);
            username.setText(MyApplication.getInstance().getUSername());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.userlogin, R.id.userinfo, R.id.myaccount, R.id.mydevice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.userlogin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Log.d("c", "dsf");
                break;
            case R.id.userinfo:
                if(StringUtils.isEmpty(MyApplication.getInstance().getToken())){
                    Toast.makeText(getActivity(),"Login Frist！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MineActivity.class));
                break;
            case R.id.myaccount:
                if(StringUtils.isEmpty(MyApplication.getInstance().getToken())){
                    Toast.makeText(getActivity(),"请先登录！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MineActivity.class));
                break;
            case R.id.mydevice:
                if(StringUtils.isEmpty(MyApplication.getInstance().getToken())){
                    Toast.makeText(getActivity(),"请先登录！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyDeviceActivity.class));
                break;
        }
    }
}
