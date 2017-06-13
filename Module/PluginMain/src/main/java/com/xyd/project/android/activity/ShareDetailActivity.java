package com.xyd.project.android.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.umeng.social.tool.UMImageMark;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.SocializeUtils;
import com.xyd.project.R;
import com.xyd.project.android.BaseActivity;
import com.xyd.project.android.adapter.ShareTypeAdapter;
import com.xyd.project.common.util.Defaultcontent;
import com.xyd.project.common.util.StyleUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class ShareDetailActivity extends BaseActivity {
    private ListView listView;
    private ShareTypeAdapter shareAdapter;
    public List<String> styles = new ArrayList<>();
    private SHARE_MEDIA share_media;
    private UMImage imageurl, imagelocal;
    private UMVideo video;
    private UMusic music;
    private UMEmoji emoji;
    private UMWeb web;
    private File file;
    private ProgressDialog dialog;

    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_share_detail;
    }

    @Override
    public void initData() {
        share_media = (SHARE_MEDIA) getIntent().getSerializableExtra("platform");
        initStyles(share_media);
        initMedia();
        dialog = new ProgressDialog(this);
        shareAdapter = new ShareTypeAdapter(this, styles);
        listView.setAdapter(shareAdapter);
    }

    @Override
    protected void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (styles.get(position).equals(StyleUtil.IMAGELOCAL)) {
                    new ShareAction(ShareDetailActivity.this).withMedia(imagelocal)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.IMAGEURL)) {
                    new ShareAction(ShareDetailActivity.this).withMedia(imageurl)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.TEXT)) {
                    new ShareAction(ShareDetailActivity.this).withText(Defaultcontent.text)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.TEXTANDIMAGE)) {
                    new ShareAction(ShareDetailActivity.this).withText(Defaultcontent.text)
                            .withMedia(imagelocal)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.WEB11)
                        || styles.get(position).equals(StyleUtil.WEB00)
                        || styles.get(position).equals(StyleUtil.WEB10)
                        || styles.get(position).equals(StyleUtil.WEB01)) {
                    new ShareAction(ShareDetailActivity.this)
                            .withText(Defaultcontent.text)
                            .withMedia(web)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.MUSIC11)
                        || styles.get(position).equals(StyleUtil.MUSIC00)
                        || styles.get(position).equals(StyleUtil.MUSIC10)
                        || styles.get(position).equals(StyleUtil.MUSIC01)) {
                    new ShareAction(ShareDetailActivity.this).withMedia(music)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.VIDEO11)
                        || styles.get(position).equals(StyleUtil.VIDEO00)
                        || styles.get(position).equals(StyleUtil.VIDEO01)
                        || styles.get(position).equals(StyleUtil.VIDEO10)) {
                    new ShareAction(ShareDetailActivity.this).withMedia(video)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.EMOJI)) {
                    new ShareAction(ShareDetailActivity.this)
                            .withMedia(emoji)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.FILE)) {
                    new ShareAction(ShareDetailActivity.this)
                            .withFile(file)
                            .withText(Defaultcontent.text)
                            .withSubject(Defaultcontent.title)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                } else if (styles.get(position).equals(StyleUtil.MINAPP)) {
                    UMMin umMin = new UMMin(Defaultcontent.url);
                    umMin.setThumb(imagelocal);
                    umMin.setTitle(Defaultcontent.title);
                    umMin.setDescription(Defaultcontent.text);
                    umMin.setPath("pages/page10007/page10007");
                    umMin.setUserName("gh_3ac2059ac66f");
                    new ShareAction(ShareDetailActivity.this)
                            .withMedia(umMin)
                            .setPlatform(share_media)
                            .setCallback(shareListener).share();
                }
            }
        });
    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShareDetailActivity.this, "成功了", Toast.LENGTH_LONG).show();
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(ShareDetailActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(ShareDetailActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    private void initStyles(SHARE_MEDIA share_media) {
        styles.clear();
        if (share_media == SHARE_MEDIA.WEIXIN) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
            styles.add(StyleUtil.EMOJI);
            styles.add(StyleUtil.MINAPP);
        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        } else if (share_media == SHARE_MEDIA.WEIXIN_FAVORITE) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        }
    }

    private void initMedia() {
        UMImageMark umImageMark = new UMImageMark();
        umImageMark.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        umImageMark.setMarkBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.umsocial_defaultwatermark));
        imageurl = new UMImage(this, Defaultcontent.imageurl);
        imageurl.setThumb(new UMImage(this, R.mipmap.thumb));
        // imagelocal = new UMImage(this,R.drawable.logo,umImageMark);
        imagelocal = new UMImage(this, R.mipmap.datu);
        imagelocal.setThumb(new UMImage(this, R.mipmap.thumb));
        music = new UMusic(Defaultcontent.musicurl);
        video = new UMVideo(Defaultcontent.videourl);
        web = new UMWeb(Defaultcontent.url);
        web.setTitle("This is web title");
        web.setThumb(new UMImage(this, R.mipmap.thumb));
        web.setDescription("my description");
        music.setTitle("This is music title");
        music.setThumb(new UMImage(this, R.mipmap.thumb));
        music.setDescription("my description");
        music.setmTargetUrl(Defaultcontent.url);
        video.setThumb(new UMImage(this, R.mipmap.thumb));
        video.setTitle("This is video title");
        video.setDescription("my description");
        emoji = new UMEmoji(this, "http://img5.imgtn.bdimg.com/it/u=2749190246,3857616763&fm=21&gp=0.jpg");
        emoji.setThumb(new UMImage(this, R.mipmap.thumb));
        file = new File(this.getFilesDir() + "test.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (SocializeUtils.File2byte(file).length <= 0) {
            String content = "U-share分享";
            byte[] contentInBytes = content.getBytes();
            try {
                FileOutputStream fop = new FileOutputStream(file);
                fop.write(contentInBytes);
                fop.flush();
                fop.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
