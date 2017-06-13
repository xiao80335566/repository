package com.xyd.project.android.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xyd.project.R;
import com.xyd.project.android.BaseActivity;

/**
 * Created by Administrator on 2017/6/5.
 */

public class ImageActivity extends BaseActivity {
    private final static String TAG = "ImageActivity";
    private String imageUrl = "http://www.kekegold.com/d/file/yule/star/2016-12-03/6968613360f003da4a228648f737a0fa.jpg";
    private ImageLoader loader;
    private ImageView imageView;

    @Override
    public void initView() {
        imageView = (ImageView) findViewById(R.id.image);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    public void initData() {
        imageloaderDisImg();
    }

    //采用Universal-ImageLoader加载图片
    private void imageloaderDisImg() {
        //实例化我们的ImageLoader
        loader = ImageLoader.getInstance();
        //加载本地图片
//        String uri = "file:///" + Environment.getExternalStorageDirectory()
//                + File.separator+"1483690114524.jpg";
//        loader.displayImage(uri,imageView);
        //加载网络图片
//        loader.displayImage(imageUrl,imageView);
        //加载带有监听事件的网络图片
        loader.displayImage(imageUrl, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                Log.e(TAG, "onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                Log.e(TAG, "onLoadingFailed");
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                Log.e(TAG, "onLoadingComplete");
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                Log.e(TAG, "onLoadingCancelled");
            }
        });
    }
}
