package com.bentudou.westwinglife;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.umeng.analytics.MobclickAgent;

/**
 * @author jsonwu
 */
public abstract class BaseActivity extends Activity {

    protected Button title_next,title_save;
    protected TextView title_title ;
    protected Button title_back;
    protected RelativeLayout title_layout;
    protected Application application = null;
    /**Context 上下文对象 */
    protected Context context = null;
    /**布局inflater*/
    protected LayoutInflater layoutInflater = null;
    /**输入法管理对象,主要用于弹出和隐藏软键盘*/
    protected InputMethodManager tInputMethodManager = null;
    private FrameLayout mTemplateContainer;
    /** 加载失败界面*/
    protected LinearLayout mTemplateNoData;
    /**加载失败按钮*/
    protected Button noDataBtn;
    private View mBaseActivityContainer;

    /**
     * 弹出对话框提示信息
     *  @param titleID title
     * @param contextID context
     */
    protected void showDialog(int titleID,int contextID) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getString(titleID));
        dialog.setMessage(getString(contextID));
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        context = this;
        setContentView(R.layout.no_connection);
        mTemplateContainer = (FrameLayout) super.findViewById(R.id.view_mainBody);
        mTemplateContainer.setBackgroundColor(Color.WHITE);
        mTemplateNoData = (LinearLayout) super.findViewById(R.id.order_no_wifi);
        noDataBtn = (Button) super.findViewById(R.id.btn_suibian);
        noDataBtn.setOnClickListener(onclick);
        if(!VerifitionUtil.isNetworkAvailable(context)){
            loadError(true);
        }
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.standard_title);
        layoutInflater = LayoutInflater.from(context);
        tInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        findTitleView();
        initTitle();
        setContentView();
        initView();

    }

    View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!VerifitionUtil.isNetworkAvailable(context)){
                loadError(true);
            }else{
                loadError(false);
            }
        }
    };

    /**
     * 数据加载失败
     * @param isError true是显示加载失败,false隐藏加载失败
     */
    public void loadError(boolean isError){
        if(isError){
            hideViewForGone(mTemplateContainer);
            showView(mTemplateNoData);
        }else{
            showView(mTemplateContainer);
            hideViewForGone(mTemplateNoData);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == R.layout.no_connection) {
            mBaseActivityContainer = LayoutInflater.from(this).inflate(
                    layoutResID, null);
            super.setContentView(mBaseActivityContainer);
        } else {
            mTemplateContainer.removeAllViews();
            View inflate = this.getLayoutInflater().inflate(layoutResID, null);
            inflate.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            mTemplateContainer.addView(inflate);
        }
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, null);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (mTemplateContainer != null) {
            mTemplateContainer.removeAllViews();
            if (params != null) {
                mTemplateContainer.addView(view, params);
            } else {
                mTemplateContainer.addView(view);
            }
        } else {
            super.setContentView(view, params);
        }
    }

    /**
     * 隐藏View方法,此方法隐藏不保留View的大小
     * @param view 需要隐藏的View
     */
    public void hideViewForGone(View... view){
        if(view.length>0){
            for(View viewParams : view){
                viewParams.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 显示View方法
     * @param view 需要显示的View
     */
    public void showView(View... view){
        if(view.length>0){
            for(View viewParams : view){
                viewParams.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_MENU ){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 启动activity
     * @param action
     * @param extras
     */
    protected void startActivity(String action, Bundle extras) {
        Intent intent = new Intent();
        intent.setAction(action);
        if(null != extras) {
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    public void startActivity(Context activity,Class target,Bundle extras){
        Intent intent = new Intent();
        intent.setClass(activity, target);
        if(null!=extras){
            intent.putExtras(extras);
        }
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /** 初始化 标题  */
    private void findTitleView(){
        title_layout = (RelativeLayout) findViewById(R.id.title_layout);
        title_back=(Button)findViewById(R.id.title_back);
        title_title=(TextView)findViewById(R.id.title_title);
        title_next=(Button)findViewById(R.id.title_next);
        title_save=(Button)findViewById(R.id.title_save);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        context = null;
        tInputMethodManager = null;
        layoutInflater = null;
        super.onDestroy();
    }



    /** 设置界面布局， 第一个调用 */
    protected abstract void setContentView();

    /** 设置title 内容和处理事件, 第二个调用 */
    protected abstract void initTitle();

    /** 初始化view，最后调用 */
    protected abstract void initView();
}
