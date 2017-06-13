package com.xyd.project.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xyd.project.R;
import com.xyd.project.android.BaseActivity;

/**
 * Created by xiaojun on 2017/6/9.
 * 登录界面
 */

public class LoginActivity extends BaseActivity {
    private EditText loginAccountEt, loginPassEt;
    private Button loginBtn;

    @Override
    public void initView() {
        loginAccountEt = (EditText) findViewById(R.id.login_account_et);
        loginPassEt = (EditText) findViewById(R.id.login_pass_et);
        loginBtn = (Button) findViewById(R.id.login_btn);
    }

    @Override

    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initEvent() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }
}
