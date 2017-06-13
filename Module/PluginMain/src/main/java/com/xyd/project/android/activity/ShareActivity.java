package com.xyd.project.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.xyd.project.R;
import com.xyd.project.android.BaseActivity;
import com.xyd.project.android.adapter.ShareAdapter;

import java.util.ArrayList;

/**
 * Created by xiaojun on 2017/6/6.
 * 分享界面
 */

public class ShareActivity extends BaseActivity {
    private ShareAdapter shareAdapter;
    private ListView shareList;
    public ArrayList<SnsPlatform> platforms = new ArrayList<>();

    @Override
    public void initView() {
        shareList = (ListView) findViewById(R.id.share_list);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_share;
    }

    @Override
    public void initData() {
        initPlatforms();
        shareAdapter = new ShareAdapter(this, platforms);
        shareList.setAdapter(shareAdapter);
        shareList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShareActivity.this, ShareDetailActivity.class);
                intent.putExtra("platform", platforms.get(position).mPlatform);
                ShareActivity.this.startActivity(intent);
            }
        });
        setTitleStr("微信分享");
    }

    private void initPlatforms() {
        platforms.clear();
        platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());
    }
}
