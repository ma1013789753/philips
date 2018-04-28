package com.gaoda.fragent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gaoda.adapter.DeviceAdapter;
import com.gaoda.bean.DeviceBean;
import com.gaoda.base.BaseFragment;
import com.gaoda.base.MyApplication;
import com.gaoda.bean.QrBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.philips.DeviceActivity;
import com.gaoda.philips.LoginActivity;
import com.gaoda.philips.MainActivity;
import com.gaoda.philips.R;
import com.gaoda.philips.ShareCodeActivity;
import com.gaoda.philips.WifiActivity;
import com.gaoda.philips.add.ScanQrCodeActivity;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

public class DeviceFragment extends BaseFragment {
    private static final int CAMERA_REQUEST_CODE = 303;
    @BindView(R.id.add_device)
    ImageView addDevice;
    @BindView(R.id.no_device)
    LinearLayout noDevice;
    @BindView(R.id.no_login)
    LinearLayout noLogin;
    Unbinder unbinder;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    List<DeviceBean> datas ;
    DeviceAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_device, null);

        unbinder = ButterKnife.bind(this, view);
        initlist();
        return view;

    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
            // 向用户解释为什么需要这个权限

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("申请相机权限")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //申请相机权限
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                //申请相机权限
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        } else {
            Intent intent2 = new Intent(getActivity(), ScanQrCodeActivity.class);
            startActivity(intent2);
        }
    }

    private void initlist() {
        datas = new ArrayList<>();
        adapter = new DeviceAdapter(getActivity(),datas);
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setClick(new DeviceAdapter.ItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("deviceId",datas.get(position).getDevice_info().getDevice_id()+"");
                intent.putExtra("bean",datas.get(position));
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        if (StringUtils.isEmpty(MyApplication.getInstance().getToken())) {
            noDevice.setVisibility(View.GONE);
            noLogin.setVisibility(View.VISIBLE);
            noLogin.setOnClickListener(v -> startActivity(new Intent(getActivity(), LoginActivity.class)));
        } else {
            noLogin.setVisibility(View.GONE);
            initData();
            initJpush();
        }
    }

    private void initJpush() {
        if(StringUtils.isNotBlank(MyApplication.getInstance().getUSername())){
            JPushInterface.setAlias(getActivity(),0,MyApplication.getInstance().getEndUserId());
        }

    }

    //获取设备
    private void initData() {
        Thread t1 = Thread.currentThread();
        Log.d("Thread",t1.getName()+"__"+t1.getId());
        HttpModleUtil.deviceList(getActivity(), new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {

                Thread t = Thread.currentThread();
                Log.d("Thread",t.getName()+"__"+t.getId());
                if(!ReTurnUtil.isSuccess(response,getActivity())){
                    return;
                }
                JSONObject data = response.body();
                Log.w("xxxx",data.toString());
                try {
                    datas.clear();
                    JSONArray arr = data.getJSONArray("data");
                    if (arr.length() == 0) {
                        noDevice.setVisibility(View.VISIBLE);
                    } else {
                        noDevice.setVisibility(View.GONE);
                        Gson gson = new Gson();
                        for (int i = 0; i < arr.length(); i++) {
                            datas.add(gson.fromJson(arr.getString(i),DeviceBean.class));
                        }
                        adapter.notifyDataSetChanged();
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

    //绑定设备
    private void bindData() {
//        HttpModleUtil.bindDevice(getActivity(), "4391c0c0333c11e8ad571008b1d7bcf2", new JsonCallBack() {
//            @Override
//            public void onSuccess(Response<JSONObject> response) {
//                initData();
//            }
//
//            @Override
//            public void onFail(Response<JSONObject> response) {
//
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.add_device, R.id.no_device, R.id.no_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_device:
                if(StringUtils.isEmpty(MyApplication.getInstance().getToken())){
                    Toast.makeText(getActivity(),"Login first please！",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                requestPermission();
                break;
            case R.id.no_device:
                requestPermission();
                break;
            case R.id.no_login:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent2 = new Intent(getActivity(), ScanQrCodeActivity.class);
                startActivity(intent2);
            } else {
                Toast.makeText(getActivity(), "相机权限已被禁止", Toast.LENGTH_SHORT).show();
                //用户勾选了不再询问
                //提示用户手动打开权限
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
//                    Toast.makeText(getActivity(), "相机权限已被禁止", Toast.LENGTH_SHORT).show();
//                }
            }
        }
    }
}
