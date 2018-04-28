package com.gaoda.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.gaoda.philips.R;

public class StorageView extends View {
    private boolean flag = false;
    private int mHeight = 0;
    private int mWidth = 0;
    private Paint mPint = new Paint();
    private String[] texts = {"Mute","First","Second","Third","Fourth"};
    private int index = 1;//当前的选中下标
    OnSwitchListener listener;
    private int textColorDefault = 0xff000000;
    private int ColorDisable = 0xffd8d8d8;
    private int ColorDefault = 0xff4A90E2;


    public StorageView(Context context) {
        super(context,null);

    }

    public StorageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.StorageView);
        textsize =(int)arr.getDimension(R.styleable.StorageView_textsize,80);//给他赋值一个红色
        Log.d("xxxxx",textsize+"");
        arr.recycle();
    }

    public StorageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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


    //四短线五个园
    int r = 4;//半径
    int t = 45;//上下左右padding
    int r1 = 40;//大圆的半径
    int textsize = 30;//默认的字体大小
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPint.setAntiAlias(true);//抗锯齿
        //画4条线
        mPint.setColor(ColorDisable);
        mPint.setStrokeWidth(8);
        canvas.drawLine(t,t,mWidth-t,t, mPint);

        //画5个圆心
        for (int i = 0; i < texts.length; i++) {
            mPint.setStrokeWidth(8);
            mPint.setColor(flag?ColorDisable:ColorDefault);
            mPint.setStyle(Paint.Style.FILL);
            canvas.drawCircle((mWidth-2*t)/(texts.length-1)*i+t,t,r,mPint);
        }
        drawCir(canvas);
        drawText(canvas);

    }
    //画大圆
    private void drawCir(Canvas canvas) {
            mPint.setColor(flag?ColorDisable:ColorDefault);
            canvas.drawCircle((mWidth - 2 * t) / (texts.length-1) * index + t, t, r1, mPint);
            mPint.setColor(0xffcccccc);
            mPint.setAlpha(125);
            mPint.setStrokeWidth(10);
            mPint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle((mWidth - 2 * t) / (texts.length-1) * index + t, t, r1, mPint);

    }

    //写字
    private void drawText(Canvas canvas){
        mPint.setStyle(Paint.Style.FILL);
        mPint.setTextSize(textsize);
        for (int i = 0; i < texts.length; i++) {
            String str = texts[i];
            if(i==index) {
                //一个汉字两个字符 所以要除以4
                mPint.setColor(flag?ColorDisable:ColorDefault);
                canvas.drawText(str,(mWidth-2*t)/(texts.length-1)*i+t-str.length()*textsize/2/2,3*t,mPint);
            }else {
                mPint.setColor(flag ? ColorDisable : textColorDefault);
                canvas.drawText(str, (mWidth - 2 * t) / (texts.length - 1) * i + t - str.length()*textsize/2/2, 3 * t, mPint);
            }

        }

    }

    public void setEnable(boolean flag){
        this.flag = flag;
        invalidate();
    }

    public void setFlag(int index){
        this.index = index;
        invalidate();
    }

    public void setListener(OnSwitchListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (flag){
            return true;
        }
        float x = event.getX();
        int index0 = ((int)x)/(mWidth/5);//判断第几个区域
//        return super.onTouchEvent(event);
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                Log.d("xxx","点击"+x+"_"+index0);
                index = index0;
                if(listener!=null){
                    listener.onChange(index);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("xxx","移动");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("xxx","抬起");
                break;
        }
        return true;
    }

    public interface OnSwitchListener{

        void onChange(int index);
    }

}
