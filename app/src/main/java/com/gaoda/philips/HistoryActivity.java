package com.gaoda.philips;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoda.base.BarChart3s;
import com.gaoda.base.LineChartManager;
import com.gaoda.bean.DataBean;
import com.gaoda.bean.DeviceBean;
import com.gaoda.httpUtil.HttpModleUtil;
import com.gaoda.httpUtil.JsonCallBack;
import com.gaoda.rxbus.RxBus;
import com.gaoda.util.ReTurnUtil;
import com.gaoda.util.StatusBarUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.lzy.okgo.model.Response;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HistoryActivity extends AutoLayoutActivity {

    @BindView(R.id.backbar)
    ImageView backbar;
    @BindView(R.id.titlebar)
    TextView titlebar;
    @BindView(R.id.menubar)
    ImageView menubar;
    @BindView(R.id.dayline)
    LineChart dayline;
    @BindView(R.id.monthline)
    BarChart monthline;
    @BindView(R.id.sevenline)
    BarChart sevenline;
    private String deviceId;
    private DeviceBean beans ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        StatusBarUtils.setWindowStatusBarColor(this, R.color.white);
        deviceId = getIntent().getStringExtra("deviceId");
        beans = (DeviceBean) getIntent().getSerializableExtra("bean");
        initTitle();
        initLine();
        initLine2();
        initLine3();
        initData();
    }

    private void initData() {
        HttpModleUtil.history(this, deviceId, new JsonCallBack() {
            @Override
            public void onSuccess(Response<JSONObject> response) {
                if(!ReTurnUtil.isSuccess(response,getBaseContext())){
                    return;
                }
            }

            @Override
            public void onFail(Response<JSONObject> response) {

            }
        });
    }

    private void initLine2() {
        BarChart3s mBarChart3s = new BarChart3s(sevenline);
        BarData data = new BarData(mBarChart3s.getDataSet1());

        // 设置数据
        sevenline.setData(data);
    }
    public void registMessage(){
        RxBus.getDefault().toObservable(DeviceDetailActivity.UNBIND_CODE,String.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((str)->{
                    finish();
                });
    }

    private void initLine3() {
        BarChart3s mBarChart3s = new BarChart3s(monthline);
        BarData data = new BarData(mBarChart3s.getDataSet2());

        // 设置数据
        monthline.setData(data);
    }

    private void initLine() {
        ArrayList<DataBean> beans = new ArrayList<>();
        Map<Float, String> map = new HashMap<>();

        for (int i = 0; i < 7; i++) {
            DataBean bean = new DataBean();
            bean.setIndex("s" + "_" + i);
            bean.setIndex("d" + i);
            beans.add(bean);
            map.put((float) i, bean.getIndex());
        }


        LineChartManager lineChartManager1 = new LineChartManager(dayline, this);
        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            xValues.add((float) i);

        }

        //设置y轴的数据()
        List<Float> yValues = new ArrayList<>();
        for (int i = 0; i < beans.size(); i++) {
            yValues.add((float) (Math.random() * 80));
        }
        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(Color.GREEN);
        colours.add(Color.BLUE);
        colours.add(Color.RED);
        colours.add(Color.CYAN);
        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");
        names.add("折线二");
        names.add("折线三");
        names.add("折线四");

        //创建多条折线的图表
        lineChartManager1.showLineChart(xValues, yValues, names.get(1), colours.get(3), map);
//        lineChartManager1.setDescription("温度");
        lineChartManager1.setYAxis(100, 0, 4);

    }

    private void initTitle() {
        backbar.setOnClickListener(v -> finish());
        titlebar.setText("History");
        menubar.setOnClickListener(v -> {
            Intent intent = new Intent(this, DeviceDetailActivity.class);
            intent.putExtra("deviceId", deviceId);
            intent.putExtra("bean", beans);
            startActivity(intent);
        });
    }


}
