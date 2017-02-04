package com.bentudou.westwinglife.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter_r.MyFragmentAdapter;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.fragment.MyFragment1;
import com.bentudou.westwinglife.fragment.MyFragment2;
import com.bentudou.westwinglife.fragment.MyFragment3;
import com.bentudou.westwinglife.fragment.MyFragment4;
import com.gunlei.app.ui.base.BaseFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzz on 2016/10/19.
 */
public class NewOrderListActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private ViewPager myViewPager; // 要使用的ViewPager
    private TextView title_title,tv_tab0, tv_tab1, tv_tab2,tv_tab3; // 4个选项卡
    private int moveOne = 0; // 下划线移动一个选项卡
    private ImageView line_tab; // tab选项卡的下划线
    private Button title_back;
    private boolean isScrolling = false; // 手指是否在滑动
    private boolean isBackScrolling = false; // 手指离开后的回弹
    private long startTime = 0;
    private long currentTime = 0;
    private int tagid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_new_order_list);
        initLineImage();
        initView();
        initFragmentView();
    }
    private void initLineImage() {
        /** * 获取屏幕的宽度 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        /** * 重新设置下划线的宽度 */
//     ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
//     lp.width = screenW / 3;
//     line_tab.setLayoutParams(lp);
        moveOne = screenW / 4; // 滑动一个页面的距离
    }
    private void initView() {
        tagid = getIntent().getIntExtra("tagid",1);
        title_title = (TextView) findViewById(R.id.title_title);
        title_title.setText("我的订单");
        title_back = (Button) findViewById(R.id.title_back);
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        line_tab = (ImageView) findViewById(R.id.line_tab);
    }
    protected void initFragmentView() {
        myViewPager = (ViewPager) findViewById(R.id.myViewPager); // ViewPager中包含的页面为普通页面ViewPager中包含的页面为Fragment，用法与前面的普通适配器一模一样
        MyFragment1 myFragment1 = new MyFragment1();
        MyFragment2 myFragment2 = new MyFragment2();
        MyFragment3 myFragment3 = new MyFragment3();
        MyFragment4 myFragment4 = new MyFragment4();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        fragmentList.add(myFragment3);
        fragmentList.add(myFragment4);
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
        tv_tab0 = (TextView) findViewById(R.id.tv_tab0);
        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
        tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
        tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
        switch (tagid){
            case 1:
                myViewPager.setCurrentItem(0);
                tv_tab0.setTextColor(getResources().getColor(R.color.color_selected));
                tv_tab1.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab2.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab3.setTextColor(getResources().getColor(R.color.black_base));
                movePositionX(0);
                break;
            case 2:
                myViewPager.setCurrentItem(1);
                tv_tab0.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab1.setTextColor(getResources().getColor(R.color.color_selected));
                tv_tab2.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab3.setTextColor(getResources().getColor(R.color.black_base));
                movePositionX(1);
                break;
            case 3:
                myViewPager.setCurrentItem(2);
                tv_tab0.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab1.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab2.setTextColor(getResources().getColor(R.color.color_selected));
                tv_tab3.setTextColor(getResources().getColor(R.color.black_base));
                movePositionX(2);
                break;
            case 4:
                myViewPager.setCurrentItem(3);
                tv_tab0.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab1.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab2.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab3.setTextColor(getResources().getColor(R.color.color_selected));
                movePositionX(3);
                break;
        }
        tv_tab0.setOnClickListener(this);
        tv_tab1.setOnClickListener(this);
        tv_tab2.setOnClickListener(this);
        tv_tab3.setOnClickListener(this);
        myViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        currentTime = System.currentTimeMillis();
        if (isScrolling && (currentTime - startTime > 50)) {
            movePositionX(position, moveOne * positionOffset);
            startTime = currentTime;
        }
        if (isBackScrolling) {
            movePositionX(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                tv_tab0.setTextColor(getResources().getColor(R.color.color_selected));
                tv_tab1.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab2.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab3.setTextColor(getResources().getColor(R.color.black_base));
                movePositionX(0);
                break;
            case 1:
                tv_tab0.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab1.setTextColor(getResources().getColor(R.color.color_selected));
                tv_tab2.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab3.setTextColor(getResources().getColor(R.color.black_base));
                movePositionX(1);
                break;
            case 2:
                tv_tab0.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab1.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab2.setTextColor(getResources().getColor(R.color.color_selected));
                tv_tab3.setTextColor(getResources().getColor(R.color.black_base));
                movePositionX(2);
                break;
            case 3:
                tv_tab0.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab1.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab2.setTextColor(getResources().getColor(R.color.black_base));
                tv_tab3.setTextColor(getResources().getColor(R.color.color_selected));
                movePositionX(3);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d("on","-------"+state);
        switch (state){
            case 1:
                isScrolling = true;
                isBackScrolling = false;
                break;
            case 2:
                isScrolling = false;
//                isBackScrolling = true;
                isBackScrolling = false;
                break;
            case 3:
                isScrolling = false;
                isBackScrolling = false;
//                isBackScrolling = true;
                break;
            case 4:
                isScrolling = false;
                isBackScrolling = false;
                break;
        }
    }
/** * 下划线跟随手指的滑动而移动
 *   @param toPosition
 * @param positionOffsetPixels
 */
    private void movePositionX(int toPosition, float positionOffsetPixels) {
        // TODO Auto-generated method stub
        float curTranslationX = line_tab.getTranslationX();
        float toPositionX = moveOne * toPosition + positionOffsetPixels;
        ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab, "translationX", curTranslationX, toPositionX);
        animator.setDuration(50); animator.start();
    }
    /** * 下划线滑动到新的选项卡中
     * @param toPosition
     */
    private void movePositionX(int toPosition) {
        // TODO Auto-generated method stub
        movePositionX(toPosition, 0);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_tab0:
                myViewPager.setCurrentItem(0);
                break;
            case R.id.tv_tab1:
                myViewPager.setCurrentItem(1);
                break;
            case R.id.tv_tab2:
                myViewPager.setCurrentItem(2);
                break;
            case R.id.tv_tab3:
                myViewPager.setCurrentItem(3);
                break;
        }
    }
}
