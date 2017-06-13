package com.xyd.project.android;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xyd.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 记录处于前台的Activity
     */
    private static BaseActivity mForegroundActivity = null;
    /**
     * 记录所有活动的Activity
     */
    private static final List<BaseActivity> mActivities = new LinkedList<>();
    /**
     * 网络加载对话框
     */
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.umeng_blue));
        }
        setContentView(getContentViewId());
        initFindViewById();
        initView();
        initData();
        back();
        initEvent();
    }

    public void setTitleStr(String titleStr) {
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        if (titleTv != null) {
            titleTv.setText(titleStr);
        }
    }

    public void sendHandler(Handler handler, int msgWhat, Object params) {
        Message message = new Message();
        message.obj = params;
        message.what = msgWhat;
        handler.sendMessage(message);
    }

    private void back() {
        ImageView back = (ImageView) findViewById(R.id.back_img);
        if (back != null) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mForegroundActivity != null) {
                        mForegroundActivity.finish();
                    }
                }
            });
        }
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setIndeterminate(false);
            loadingDialog.setCancelable(true);
            loadingDialog.setMessage("正在加载数据");
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * 返回键的隐藏显示
     */
    public void backVisibility(int viewStatus) {
        ImageView back = (ImageView) findViewById(R.id.back_img);
        if (back != null) {
            back.setVisibility(viewStatus);
        }
    }

    @Override
    protected void onResume() {
        MobclickAgent.enableEncrypt(false);
        mForegroundActivity = this;
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        mForegroundActivity = null;
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public abstract void initView();

    public abstract int getContentViewId();

    public abstract void initData();

    /**
     * 关闭所有Activity
     */
    public static void finishAll() {
        List<BaseActivity> copy;
        synchronized (mActivities) {
            copy = new ArrayList<>();
        }

        for (BaseActivity activity : copy) {
            activity.finish();
        }
    }

    /**
     * 是否有启动的Activity
     */
    public static boolean hasActivity() {
        return mActivities.size() > 0;
    }

    /**
     * 获取当前处于前台的activity
     */
    public static BaseActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    protected void initFindViewById() {

    }

    protected void initEvent() {
    }

    /**
     * 退出应用
     */
    public void exitApp() {
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


}

