package com.xyd.project.android.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xyd.project.R;
import com.xyd.project.android.BaseActivity;

import java.lang.ref.WeakReference;

import xydproject.pluginutil.net.HttpRequest;
import xydproject.pluginutil.net.ObjectCallback;
import xydproject.pluginutil.net.StringCallback;
import xydproject.pluginutil.widget.BaseDialog;

/**
 * Created by Administrator on 2017/5/24.
 */

public class NetTestActivity extends BaseActivity {

    private Button netGetBtn, netPostBtn;
    private TextView contentTv;
    private MyHandler myHandler;

    private final static int NET_OK_MSG = 1001;
    private final static int NET_ERROR_MSG = 1002;

    @Override
    public void initView() {
        netGetBtn = (Button) findViewById(R.id.net_get_btn);
        netPostBtn = (Button) findViewById(R.id.net_post_btn);
        contentTv = (TextView) findViewById(R.id.content_tv);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_net;
    }

    @Override
    public void initData() {
        myHandler = new MyHandler(this);
        setTitleStr("网络模拟");
    }

    BaseDialog dialog;

    private void showDialog() {
        dialog = new BaseDialog.Builder(this).setTitle("标题").setMessage("内容")
                .setPositiveButton("哈哈", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(NetTestActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }
                }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(NetTestActivity.this, "dismiss", Toast.LENGTH_SHORT).show();
                    }
                }).create();
        dialog.show();
    }


    @Override
    protected void initEvent() {
        netGetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();
                String url = "https://www.baidu.com/";
                HttpRequest httpRequest = new HttpRequest(url, null);
                httpRequest.setCallbackT(new ObjectCallback<User>() {
                    @Override
                    public void onResponse(int status, String message, User result) {
                        sendHandler(myHandler, NET_OK_MSG, result);
                    }
                });
                httpRequest.setRequestMode(HttpRequest.HTTP_TYPE.GET);
                httpRequest.execute();
            }
        });
        netPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private static class MyHandler extends Handler {

        WeakReference<NetTestActivity> act;

        MyHandler(NetTestActivity activity) {
            act = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final NetTestActivity activity = act.get();
            if (activity == null) {
                return;
            }
            activity.dismissLoadingDialog();
            switch (msg.what) {
                case NET_OK_MSG:
                    activity.contentTv.setText("请求结果：" + msg.obj);
                    break;
                case NET_ERROR_MSG:

                    break;
            }
        }
    }
}
