package com.bentudou.westwinglife.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BTDApplication;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.dbcache.GLCacheProxy;
import com.bentudou.westwinglife.dbcache.ICache;
import com.bentudou.westwinglife.dbcache.impl.ICacheImpl;
import com.bentudou.westwinglife.fragment.FourFragment;
import com.bentudou.westwinglife.fragment.NewTwoFragment;
import com.bentudou.westwinglife.fragment.OneFragment;
import com.bentudou.westwinglife.fragment.ThreeFragment;
import com.bentudou.westwinglife.fragment.TwoFragment;
import com.bentudou.westwinglife.json.UserSign;
import com.bentudou.westwinglife.json.VersionInfo;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/2/29.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener{
    private final String TAG = getClass().getSimpleName();
    private FrameLayout flayout;
    private RelativeLayout lview1, lview2, lview3,lview4;
    private FragmentManager fmanager;
    private Fragment mContent;
    private FragmentTransaction transaction;
    private ArrayList<RelativeLayout> view;
    private ArrayList<Fragment> fragmentList;
    ICache cache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Constant.activity = MainActivity.this;
        Log.d(TAG, "-----onCreate:mainactivity开始 ");
        initversionName();
        initView();
        initFragmentView();
    }
    private void initFragmentView() {
        flayout = (FrameLayout) findViewById(R.id.main_content);
        Fragment oneFragment = new OneFragment();
//        Fragment twoFragment = new TwoFragment();
        Fragment twoFragment = new NewTwoFragment();
        Fragment threeFragment = new ThreeFragment();
        Fragment fourFragment = new FourFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(oneFragment);
        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(fourFragment);
        fmanager = getSupportFragmentManager();
        transaction = fmanager.beginTransaction();
//        transaction.replace(R.id.main_content, oneFragment);
        transaction.add(R.id.main_content, oneFragment);
        mContent = oneFragment;
        transaction.commit();
        lview1.setSelected(true);
        lview1.requestFocus();
        lview1.setOnClickListener(this);
        lview2.setOnClickListener(this);
        lview3.setOnClickListener(this);
        lview4.setOnClickListener(this);
    }

    private void initView() {
        lview1 = (RelativeLayout) findViewById(R.id.la_guid1);
        lview2 = (RelativeLayout) findViewById(R.id.la_guid2);
        lview3 = (RelativeLayout) findViewById(R.id.la_guid3);
        lview4 = (RelativeLayout) findViewById(R.id.la_guid4);
        view = new ArrayList<>();
        view.add(lview1);
        view.add(lview2);
        view.add(lview3);
        view.add(lview4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.la_guid1:
                setChioceItem(0);
                break;
            case R.id.la_guid2:
                setChioceItem(1);
                break;
            case R.id.la_guid3:
                setChioceItem(2);
                break;
            case R.id.la_guid4:
                setChioceItem(3);
                break;
        }
    }

    public void setChioceItem(int index){
        fmanager = getSupportFragmentManager();
        transaction = fmanager.beginTransaction();
        if (mContent!=fragmentList.get(index)){
            switch (index){
                case 0:
                    if (!fragmentList.get(index).isAdded()){
                        transaction.hide(mContent).add(R.id.main_content, fragmentList.get(index)).commitAllowingStateLoss();
                    }else {
                        transaction.hide(mContent).show(fragmentList.get(index)).commitAllowingStateLoss();
                    }
                    mContent=fragmentList.get(index);
                    Constant.push_value = index+1;
                    for(int i = 0;i<view.size();i++) {
                        if (i == index) {
                            view.get(i).setSelected(true);
                            view.get(i).requestFocus();
                        } else {
                            view.get(i).setSelected(false);
                            view.get(i).requestFocus();
                        }
                    }
                    break;
                case 1:
                    if (!fragmentList.get(index).isAdded()){
                        transaction.hide(mContent).add(R.id.main_content, fragmentList.get(index)).commitAllowingStateLoss();
                    }else {
                        transaction.hide(mContent).show(fragmentList.get(index)).commitAllowingStateLoss();
                    }
                    mContent=fragmentList.get(index);
                    Constant.push_value = index+1;
                    for(int i = 0;i<view.size();i++) {
                        if (i == index) {
                            view.get(i).setSelected(true);
                            view.get(i).requestFocus();
                        } else {
                            view.get(i).setSelected(false);
                            view.get(i).requestFocus();
                        }
                    }
                    break;
                case 2:
                    if (SharePreferencesUtils.getBtdToken(this).equals("")){
                        startActivity(new Intent(this,LoginActivity.class).putExtra("index",3));
                    }else {
                        if (!fragmentList.get(index).isAdded()){
                            transaction.hide(mContent).add(R.id.main_content, fragmentList.get(index)).commitAllowingStateLoss();
                        }else {
                            transaction.hide(mContent).show(fragmentList.get(index)).commitAllowingStateLoss();
                        }
                        mContent=fragmentList.get(index);
                        Constant.push_value = index+1;
                        for(int i = 0;i<view.size();i++) {
                            if (i == index) {
                                view.get(i).setSelected(true);
                                view.get(i).requestFocus();
                            } else {
                                view.get(i).setSelected(false);
                                view.get(i).requestFocus();
                            }
                        }
                    }
                    break;
                case 3:
                    if (SharePreferencesUtils.getBtdToken(this).equals("")){
                        startActivity(new Intent(this,LoginActivity.class).putExtra("index",4));
                    }else {
                        if (!fragmentList.get(index).isAdded()){
                            transaction.hide(mContent).add(R.id.main_content, fragmentList.get(index)).commitAllowingStateLoss();
                        }else {
                            transaction.hide(mContent).show(fragmentList.get(index)).commitAllowingStateLoss();
                        }
                        mContent=fragmentList.get(index);
                        Constant.push_value = index+1;
                        for(int i = 0;i<view.size();i++) {
                            if (i == index) {
                                view.get(i).setSelected(true);
                                view.get(i).requestFocus();
                            } else {
                                view.get(i).setSelected(false);
                                view.get(i).requestFocus();
                            }
                        }
                    }
                    break;
            }
        }

//        transaction.replace(R.id.main_content, fragmentList.get(index));
//        transaction.commitAllowingStateLoss();
//        for(int i = 0;i<view.size();i++) {
//            if (i == index) {
//                view.get(i).setSelected(true);
//                view.get(i).requestFocus();
//            } else {
//                view.get(i).setSelected(false);
//                view.get(i).requestFocus();
//            }
//        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        switch (Constant.push_value){
            case 1:
                lview1.performClick();
                break;
            case 2:
                lview2.performClick();
                break;
            case 3:
                lview3.performClick();
                break;
            case 4:
                lview4.performClick();
                break;
        }
//        if ( Constant.push_value==1){
//            Constant.push_value=1;
//            lview1.performClick();
//        }else if (Constant.push_value==2){
//            Constant.push_value=1;
//            lview2.performClick();
//        }else if (Constant.push_value==3){
//            Constant.push_value=1;
//            lview3.performClick();
//        }else {
//            Constant.push_value=1;
//            lview4.performClick();
//
//        }
    }
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            // 判断是否在两秒之内连续点击返回键，是则退出，否则不退出
            if (System.currentTimeMillis() - exitTime > 2000) {
                ToastUtils.showToastCenter(getApplicationContext(), "再按一次退出笨土豆");
                // 将系统当前的时间赋值给exitTime
                exitTime = System.currentTimeMillis();
            } else {
//                BTDApplication.getInstance().exit();
//                if (ImageLoader.getInstance()!=null) {
//                    ImageLoader.getInstance().clearMemoryCache();
//                    ImageLoader.getInstance().clearDiscCache();
//                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    //检查版本号是否是新版
    private void initversionName() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getAppNewVersion(2,new Callback<VersionInfo>() {
            @Override
            public void success(VersionInfo versionInfo, Response response) {
                if (versionInfo.getStatus().equals("1")){
                    if (null!=versionInfo.getData()&&versionInfo.getData().getVersionSn()-SharePreferencesUtils.getVersionCode(MainActivity.this)>0){
                        showVersionDialogs();
                    }else {
                        return;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePreferencesUtils.saveBgImg(MainActivity.this);
    }

    //版本更新提示
    public void showVersionDialogs() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_version_info,
                null);
        TextView tv_store = (TextView) layout.findViewById(R.id.sure_go);
        TextView noSaveInfo = (TextView) layout.findViewById(R.id.cancel_go);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setOnKeyListener(keylistener);
        tv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = DensityUtils.getIntent(MainActivity.this);
//                boolean b = DensityUtils.judge(MainActivity.this, i);
//                if(b==false)
//                {
//                    MainActivity.this.startActivity(i);
//                }
                Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=com.bentudou.westwinglife");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
//                dialog.dismiss();
            }
        });
        noSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                dialog.dismiss();
            }
        });
    }
    //是返回键对dialog失效
    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode== KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    } ;
}
