package com.gaoda.base;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class BarChart3s {

    public BarChart3s(BarChart chart) {
        // 数据描述
        Description description = new Description();
        description.setText("");
        chart.setDescription(description);
        chart.invalidate();
        //背景
        chart.setBackgroundColor(0xffffffff);
        //定义数据描述得位置
        //chart.setDescriptionPosition(2,100);
        // 设置描述文字的颜色
        // chart.setDescriptionColor(0xffededed);
        // 动画
        chart.animateY(1000);
        //设置无边框
        chart.setDrawBorders(false);
        // 设置是否可以触摸
        chart.setTouchEnabled(true);
        // 是否可以拖拽
        chart.setDragEnabled(true);
        // 是否可以缩放
        chart.setScaleEnabled(true);
        //设置网格背景
        chart.setGridBackgroundColor(0xffffffff);
        //设置边线宽度
        chart.setBorderWidth(0);
        //设置边线颜色
        chart.setBorderColor(0xffffffff);
        // 集双指缩放
        chart.setPinchZoom(false);
        // 隐藏右边的坐标轴
        chart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴
        chart.getAxisLeft().setEnabled(true);

        Legend mLegend = chart.getLegend(); // 设置比例图标示
        // 设置窗体样式
        mLegend.setForm(Legend.LegendForm.SQUARE);
        //设置图标位置
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        // 字体
        mLegend.setFormSize(4f);
        //是否显示注释
        mLegend.setEnabled(false);
        // 字体颜色
//        mLegend.setTextColor(Color.parseColor("#7e7e7e"));

        //设置X轴位置
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        //设置轴线得颜色
        xAxis.setAxisLineColor(0xffffffff);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
//        xAxis.setSpaceBetweenLabels(2);
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setEnabled(false);
        //设置Y轴
        YAxis axisRight = chart.getAxisRight();
        axisLeft.setEnabled(true);
        //Y轴颜色
        axisRight.setAxisLineColor(0xffffffff);
        //Y轴参照线颜色
        axisRight.setGridColor(0xfff3f3f3);
        //参照线长度
        axisRight.setAxisLineWidth(5f);
        // 顶部居最大值站距离占比
        axisRight.setSpaceTop(20f);
        chart.setDrawGridBackground(false);
        xAxis.enableGridDashedLine(10f, 5f, 0f);
        axisRight.enableGridDashedLine(10f, 5f, 0f);
        axisRight.enableGridDashedLine(10f, 5f, 0f);
        //显示边界
        chart.setDrawBorders(false);
        chart.invalidate();
    }

    public List<IBarDataSet> getDataSet2() {
        ArrayList<IBarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        for (int i = 0; i < 30; i++) {
            float value = (float) (Math.random() * 100/*100以内的随机数*/) + 3;
            valueSet1.add(new BarEntry(i,value));
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "目标");
        barDataSet1.setColor(Color.parseColor("#45a2ff"));

        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));
        dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
        return dataSets;
    }
    public List<IBarDataSet> getDataSet1() {
        ArrayList<IBarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        for (int i = 0; i < 7; i++) {
            float value = (float) (Math.random() * 100/*100以内的随机数*/) + 3;
            valueSet1.add(new BarEntry(i,value));
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "目标");
        barDataSet1.setColor(Color.parseColor("#45a2ff"));

        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));
        dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet1);
//        dataSets.add(barDataSet2);
        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<String>();
        for (int j = 0; j < 31; j++){
            xAxis.add("8-"+(j+1));
        }
        return xAxis;
    }

    public void setDescription(String str) {

    }
}