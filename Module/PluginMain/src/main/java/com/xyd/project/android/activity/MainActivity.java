package com.xyd.project.android.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.xyd.project.R;
import com.xyd.project.android.BaseActivity;
import com.xyd.project.android.adapter.MyRecyclerAdapter;
import com.xyd.project.common.callback.ItemClickListener;
import com.xyd.project.common.callback.ItemLongClickListener;

import xydproject.pluginutil.date.CalendarCard;
import xydproject.pluginutil.date.OnChooseListener;
import xydproject.pluginutil.date.OnTurnPageListener;
import xydproject.pluginutil.util.ToastUtils;

/**
 * Created by xiaojun on 2017/5/19.
 */

public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private String[] tvData = new String[6];
    private int[] ivData = new int[6];
    private MyRecyclerAdapter adapter;

    @Override
    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    protected void initFindViewById() {

    }

    @Override
    protected void initEvent() {
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void itemClick(int position) {
                switch (position) {
                    case 0:
                        MobclickAgent.onEvent(MainActivity.this, "PluginActivity_btn");
                        Intent intent = new Intent(MainActivity.this, PluginActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                    case 1:
                        MobclickAgent.onEvent(MainActivity.this, "NetTestActivity_btn");
                        Intent intent2 = new Intent(MainActivity.this, NetTestActivity.class);
                        MainActivity.this.startActivity(intent2);
                        break;
                    case 2:
                        MobclickAgent.onEvent(MainActivity.this, "AMapActivity_btn");
                        Intent intent3 = new Intent(MainActivity.this, AMapActivity.class);
                        MainActivity.this.startActivity(intent3);
                        break;
                    case 3:
                        MobclickAgent.onEvent(MainActivity.this, "ImageActivity_btn");
                        Intent intent4 = new Intent(MainActivity.this, ImageActivity.class);
                        MainActivity.this.startActivity(intent4);
                        break;
                    case 4:

                        MobclickAgent.onEvent(MainActivity.this, "ShareActivity_btn");
                        Intent intent5 = new Intent(MainActivity.this, ShareActivity.class);
                        MainActivity.this.startActivity(intent5);
                        break;

                    case 5:
                        MobclickAgent.onEvent(MainActivity.this, "DateDemoActivity_btn");
                        Intent intent6 = new Intent(MainActivity.this, DateDemoActivity.class);
                        MainActivity.this.startActivity(intent6);
                        break;
                }
            }
        });
        adapter.setItemLongClickListener(new ItemLongClickListener() {
            @Override
            public void itemLongClick(int position) {
                ToastUtils.showShort(MainActivity.this, "长按==" + position);
            }
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        tvData = new String[]{"跳转到插件", "模拟网络请求", "跳转到定位界面", "图片(ImageLoader)", "分享界面","日历控件"};
        ivData = new int[]{R.mipmap.xiewu, R.mipmap.assets_verify, R.mipmap.xiewu, R.mipmap.assets_verify, R.mipmap.xiewu,R.mipmap.assets_verify};
        setTitleStr("首页");
        adapter = new MyRecyclerAdapter(this, tvData, ivData);
        recyclerView.setAdapter(adapter);
        backVisibility(View.GONE);
    }
}
