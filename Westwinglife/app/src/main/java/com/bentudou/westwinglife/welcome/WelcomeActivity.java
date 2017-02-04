package com.bentudou.westwinglife.welcome;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bentudou.westwinglife.adapter.OrderListAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.ImgUrl;
import com.bentudou.westwinglife.json.MyOrder;
import com.bentudou.westwinglife.json.RMBRate;
import com.bentudou.westwinglife.json.StartImgInfo;
import com.bentudou.westwinglife.json.VersionInfo;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.gunlei.app.ui.util.FileManager;
import com.gunlei.app.ui.view.ProgressHUD;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.MainActivity;
import com.bentudou.westwinglife.dbcache.GLCacheProxy;
import com.bentudou.westwinglife.dbcache.ICache;
import com.bentudou.westwinglife.dbcache.impl.ICacheImpl;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by 天池 on 2015/8/4.
 */
public class WelcomeActivity extends Activity {
    public static final int UPDATA_CLIENT = 1;
    public static final int GET_UNDATAINFO_ERROR = 2;
    public static final int DOWN_ERROR = 3;

    public static final String TAG = WelcomeActivity.class.getSimpleName();
    private TextView mTxtName;
    private TextView tv_version;
    private ImageView mImgLogo;
    private ImageView start_img;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    private static final String SHAREDPREFERENCES_FIRST = "first_update";
    boolean isFirstIn = false;
    boolean isUpdate = false;
    ProgressHUD progressHUD = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
//        if(Build.VERSION.SDK_INT>=23){
//            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            ActivityCompat.requestPermissions(this,mPermissionList,123);
//        }
        initViews();

//        MyTimerTask task = new MyTimerTask();
//        Timer timer = new Timer();
//        timer.schedule(task, 3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startAnim();
        MobclickAgent.onResume(this);

    }
    /**
     * 初始化UI各个控件。
     */
    private void initViews() {
        tv_version = (TextView) findViewById(R.id.tv_version);
        start_img = (ImageView) findViewById(R.id.start_img);
        if (FileManager.exists("/sdcard/startimg.png")){
            Log.d("is_start_img","-----去sd卡取图片");
            start_img.setImageBitmap(getDiskBitmap("/sdcard/startimg.png"));
        }
        tv_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change();
            }
        });
        initTime();
        initImg();
        initStartImg();
//        initRate();
    }
    //获取汇率方法
//    private void initRate() {
//        final PotatoService service = RTHttpClient.create(PotatoService.class);
//        service.getBTDouExchangeRate(new Callback<RMBRate>() {
//            @Override
//            public void success(RMBRate rmbRate, Response response) {
//                if (rmbRate.getStatus().equals("1")){
//                    if (rmbRate.getData().toString().equals("0")){
//                        return;
//                    }else {
//                       SharePreferencesUtils.updateRate(WelcomeActivity.this,rmbRate.getData());
//                    }
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//            }
//        });
//    }

    private void initTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num =3;
                while (num>=0){
                    final int finalNum = num;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_version.setText(String.valueOf(finalNum)+" | 跳过");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    num--;
                }
                change();
            }
        }).start();

    }

    private void initStartImg() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getAppNewStartPage(new Callback<StartImgInfo>() {
            @Override
            public void success(StartImgInfo startImgInfo, Response response) {
                if (startImgInfo.getStatus().equals("1")){
                   if (null==startImgInfo.getData()||startImgInfo.getData().getStartPageImg().isEmpty()){
                            FileManager.deleteFile("/sdcard/startimg.png");
                   }else {
                       if (startImgInfo.getData().getStartPageImg().equals(Constant.start_img_name)){
                           return;
                       }else {
                           toGetBitmap(Constant.URL_BASE_IMG+startImgInfo.getData().getStartPageImg());
                           Constant.start_img_name=startImgInfo.getData().getStartPageImg();
                       }
                   }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
    private void toGetBitmap(final String url){
        new Thread(){
            @Override
            public void run() {
                Bitmap bm = returnBitmap(url);
               if (null!=bm)
                   saveImg(bm);
            }
        }.start();
    }
    private Bitmap returnBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    private void initImg() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getImgUrl( new CallbackSupport<ImgUrl>(this) {
            @Override
            public void success(ImgUrl imgUrl, Response response) {
                if (imgUrl.getStatus().equals("1")){
                    SharePreferencesUtils.updateImg_url(WelcomeActivity.this,imgUrl.getData().getImgUrl());
                    Constant.URL_BASE_IMG = SharePreferencesUtils.getImg_url(WelcomeActivity.this);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    /**
     * 开始动画。
     */
    private void startAnim() {
        // 文字渐变
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(2500);

        // Logo移动
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -0.5f,
                Animation.RELATIVE_TO_SELF, 0);

        translateAnimation.setDuration(2500);
        translateAnimation.setInterpolator(new BounceInterpolator());

        // 动画设置到控件中
        mTxtName.startAnimation(alphaAnimation);
        mImgLogo.startAnimation(translateAnimation);
    }


    private void change() {
        isFirstIn = SharePreferencesUtils.getFirstIn(this);
        isUpdate =Integer.valueOf(RTHttpClient.VERSION_CODE)>SharePreferencesUtils.getUpdate(this)?true:false;
        SharePreferencesUtils.saveUpdate(this);
        ICache cache = new GLCacheProxy(new ICacheImpl(WelcomeActivity.this)).getProxy();
        if (isFirstIn||isUpdate) {
            cache.saveData("first_or_update", "true");
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
            WelcomeActivity.this.finish();
        } else{
            Log.d(TAG, "------change:跳转 ");
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            WelcomeActivity.this.finish();
        }
    }


    /**
     * 定时的任务。
     */
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            change();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    //保存网络图片到本地 第一个参数是图片名称 第二个参数为需要保存的bitmap
    public void saveImg( Bitmap bitmap) {
        File file = new File("/sdcard/","startimg.png");
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    start_img.setImageBitmap(getDiskBitmap("/sdcard/startimg.png"));
                }
            });
            Log.i("is_start_img", "-----成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //读取本地图片 参数路径
    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            // File file = new File(pathString);
            // if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(pathString);
            // }
        } catch (Exception e) {
        }

        return bitmap;
    }
}
