package com.xyd.project.android;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.morgoo.droidplugin.PluginHelper;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.xyd.project.R;

import java.io.File;

import xydproject.pluginutil.util.SPUtils;
import xydproject.pluginutil.util.ToastUtils;


/**
 * Created by xiaojun on 2017/5/19.
 */

public class XydApplication extends Application {
    private Handler handler;
    private static final String TAG = XydApplication.class.getName();
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";

    @Override
    public void onCreate() {
        super.onCreate();
        regiestPush();
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setDebugMode(true);
//        //sdk开启通知声音
//        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);


        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        initImageLoader();
        //这里必须在super.onCreate方法之后，顺序不能变
        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
        SPUtils.init(this);
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
    }

    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
    }

    private void regiestPush() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                ToastUtils.showShort(getApplicationContext(), "deviceToken ==" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                ToastUtils.showShort(getApplicationContext(), "s ==" + s + "---S1 = " + s1);
            }
        });

        /**
         * 自定义行为的回调处理，参考文档：高级功能-通知的展示及提醒-自定义通知打开动作
         * UmengNotificationClickHandler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        //使用自定义的NotificationHandler，来结合友盟统计处理消息通知，参考http://bbs.umeng.com/thread-11112-1-1.html
        //CustomNotificationHandler notificationClickHandler = new CustomNotificationHandler();
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                //max width,max height即保存每个缓存文件的最大长宽
                .memoryCacheExtraOptions(480, 800)
                //设置硬盘缓存
                .diskCacheExtraOptions(480, 800, null)
                //线程池内加载的数量
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                //设置内存缓存的大小
                .memoryCacheSize(2 * 1024 * 1024)
                //硬盘设置的最大缓存数
                .diskCacheSize(50 * 1024 * 1024)
                //将手机sd卡里面的缓存文件名称用MD5加密
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //缓存文件的数量
                .diskCacheFileCount(100)
                //缓存文件的路径
                .diskCache(new UnlimitedDiskCache(new File(
                        Environment.getExternalStorageDirectory()
                                + "xyd/imageCache")))
                //加载图片默认的配置信息
                .defaultDisplayImageOptions(getDisplayOptions())
                //图片加载时的配置,连接时间和加载超时的时间
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                //写入加载时的错误日志
                .writeDebugLogs()
                //构建完成
                .build();
        ImageLoader.getInstance().init(configuration);
    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                //设置图片在下载期间显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                //设置Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                //设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                //设置下载的图片是否缓存在内存中
                .cacheInMemory(true)
                //设置下载的图片是否缓存在sd卡中
                .cacheOnDisk(true)
                //是否考虑JPEG图像EXIF参数(旋转,翻转)
                .considerExifParams(true)
                //设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                //设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                //设置下载前的延迟时间
                //.delayBeforeLoading(int delayMills)
                //图片加入缓存前,对bitmap进行设置
                //.postProcessor(BitmapProcessor processor)
                //设置图片在下载前是否重置,复位
                .resetViewBeforeLoading(true)
                //是否设置为圆角,弧度是多少
                .displayer(new RoundedBitmapDisplayer(20))
                //设置图片加载好后渐入的动画时间
                .displayer(new FadeInBitmapDisplayer(100))
                //构建完成
                .build();
        return options;
    }
}
