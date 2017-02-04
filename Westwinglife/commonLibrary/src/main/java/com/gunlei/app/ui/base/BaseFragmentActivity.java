package com.gunlei.app.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gunlei.app.R;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by mac on 14/12/12.
 */
public abstract class BaseFragmentActivity extends FragmentActivity {
    protected Button title_back,title_next;
    protected TextView title_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        MobclickAgent.openActivityDurationTrack(false);
        setContentView();
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);
        findTitleView();
        initTitle();
        if(savedInstanceState==null)
            initFragmentManager();
    }
    /** 初始化 标题  */
    private void findTitleView(){
        title_back=(Button)findViewById(R.id.title_back);
        title_title=(TextView)findViewById(R.id.title_title);
        title_next=(Button)findViewById(R.id.title_next);
//        title_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
    protected abstract void setContentView();
    protected abstract void initTitle();
    protected abstract void initFragmentManager();

    //友盟统计
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

}
