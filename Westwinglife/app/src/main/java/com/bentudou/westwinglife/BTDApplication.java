package com.bentudou.westwinglife;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import common.retrofit.RTHttpClient;

/**
 * 入口Application.
 */
public class BTDApplication extends Application {
    // 用于存放倒计时时间
    public static Map<String, Long> map;
    public static String Imei;
    public static boolean isUpLoad = false;

    @Override
    public void onCreate() {
        super.onCreate();
        isUpLoad = true;
        MobclickAgent.onProfileSignIn(RTHttpClient.mobile);
        Config.isUmengWx = true;
        PlatformConfig.setWeixin("wxbf954c0939ebfb1c", "4717402cbf2e789638b9a0e076a76b10");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3485542683","c7081d46aeb6a725247f1db345c788be");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("1105384335", "9BlvZlOcduiwo3I1");
        // QQ和Qzone appid appkey
        UMShareAPI.get(this);
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        try {
            Imei=tm.getDeviceId();
        }catch(Exception e){
            Imei="";
        }
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        JPushInterface.setAlias(this, SharePreferencesUtils.getMobile(this), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("JPush", "setAlias: "+i);
            }
        });
        //意见反馈
//        FeedbackPush.getInstance(this).init(false);
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(600, 600)
                        // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache()) // 弱缓存，缓存bitmap的总大小没有限制，唯一不足的地方就是不稳定，缓存的图片容易被回收掉
//                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))//建议内存设在5-10M,可以有比较好的表现
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                        // 缓存的文件数量
                .discCache(
                        new UnlimitedDiscCache(new File(Environment
                                .getExternalStorageDirectory()
                                + "/Westwinglife/imgCache")))
                        // 自定义缓存路径
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000))
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);
        RTHttpClient.init(Constant.URL_BASE_TEST, this);

        //CrashHandler
//        CrashHandler handler = CrashHandler.getInstance();
//        handler.init(this);


    }
    public DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.defult_good_img) // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.defult_good_img)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.defult_good_img) // 设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
                .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)// 设置图片以如何的编码方式显示
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
                        // .delayBeforeLoading(int delayInMillis)//int
                        // delayInMillis为你设置的下载前的延迟时间
                        // 设置图片加入缓存前，对bitmap进行设置
                        // .preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100)) // 是否图片加载好后渐入的动画时间
                .build();// 构建完成

        return options;
    }

    /**
     * Activity列表
     */
    private List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 全局唯一实例
     */
    private static BTDApplication instance;

    /**
     * 该类采用单例模式，不能实例化
     */
    public BTDApplication()
    {
    }

    /**
     * 获取类实例对象
     * @return    MyActivityManager
     */
    public static BTDApplication getInstance() {
        if (null == instance) {
            instance = new BTDApplication();
        }
        return instance;
    }

    /**
     * 保存Activity到现有列表中
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    /**
     * 关闭保存的Activity
     */
    public void exit() {
        MobclickAgent.onKillProcess(this);
        if(activityList!=null)
        {
            Activity activity;

            for (int i=0; i<activityList.size(); i++) {
                activity = activityList.get(i);

                if(activity!=null)
                {
                    if(!activity.isFinishing())
                    {
                        activity.finish();
                    }

                    activity = null;
                }

                activityList.remove(i);
            }
        }
    }


}
