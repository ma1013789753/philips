package com.gaoda.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gaoda.philips.R;

public class ColorData extends View {
    private int mHeight = 0;
    private int mWidth = 0;
    private Paint mPint = new Paint();



    public ColorData(Context context) {
        super(context,null);

    }

    public ColorData(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public ColorData(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


    //测量尺寸
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /*
        UNSPECIFIED	父容器没有对当前View有任何限制，当前View可以任意取尺寸
        EXACTLY	当前的尺寸就是当前View应该取的尺寸match_parent固定尺寸（如100dp）
        AT_MOST	当前尺寸是当前View能取的最大尺寸 wrap_content
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mWidth = 500;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mWidth = widthSize;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mWidth = widthSize;
                break;
            }
        }

        switch (heightMode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mWidth = 500;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mHeight = heightSize;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mHeight = heightSize;
                break;
            }
        }
    }


//    int max = 0xffffff;
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int dexw = max/mWidth;
//        int dexh = max/mHeight;
//        for (int i = 0; i < mWidth; i++) {
//            for (int j = 0; j < mHeight; j++) {
//                canvas.drawColor(max/mWidth*i);
//            }
//        }
//
//
//
//    }

}
