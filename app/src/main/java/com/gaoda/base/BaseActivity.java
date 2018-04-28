package com.gaoda.base;

import android.widget.Toast;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.zhy.autolayout.AutoLayoutActivity;

public class BaseActivity extends AutoLayoutActivity {


    @Override
    protected void onStart() {
        super.onStart();
        PgyCrashManager.register(this);
    }

    public void showToast(String str){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyCrashManager.unregister();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
        PgyFeedbackShakeManager.setShakingThreshold(1000);

        // 以对话框的形式弹出，对话框只支持竖屏
        PgyFeedbackShakeManager.register(this);

        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
        // 打开沉浸式,默认为false
        // FeedbackActivity.setBarImmersive(true);
        //PgyFeedbackShakeManager.register(MainActivity.this, true); 相当于使用Dialog的方式；
        PgyFeedbackShakeManager.register(this, false);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        PgyFeedbackShakeManager.unregister();
    }
}
