package com.gaoda.base;

import android.content.Context;
import android.widget.TextView;

import com.gaoda.philips.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.Map;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private Map<Float,String> map;
    public MyMarkerView(Context context, int layoutResource,Map<Float,String> map) {
        super(context, layoutResource);
        this.map = map;
        tvContent= (TextView) findViewById(R.id.tvContent);
    }
    
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText(map.get(e.getX()));
        } else {

            tvContent.setText(map.get(e.getX()));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}