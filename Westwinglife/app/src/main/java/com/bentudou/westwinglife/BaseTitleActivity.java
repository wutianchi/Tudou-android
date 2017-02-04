package com.bentudou.westwinglife;

import android.view.View;

/**
 * @author jsonwu
 */
public abstract class BaseTitleActivity extends BaseActivity{

    @Override
    protected void initTitle() {
        initNextClick();
    }

    protected void initNextClick() {
        title_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });
        title_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });
    }


    /**
     * 设置标题。
     * @param text
     */
    public void setTitleText(String text) {
        if(title_title != null) {
            title_title.setText(text);
        }
    }

    /**
     * 设置点击事件
     * @param clickListener
     */
    public void setTitleNextClickListener(View.OnClickListener clickListener){
        title_next.setOnClickListener(clickListener);
    }

    /**
     * 设置标题颜色。
     * @param color
     */
    public void setTitleTextColor(int color) {
        if(title_title != null) {
            title_title.setTextColor(color);
        }
    }
    /**
     * 设置头背景色
     * @param color
     */
    public void setTitleBackground(int color){

        if (title_layout != null){
            title_layout.setBackgroundColor(color);
        }
    }

    /**
     * 设置下一步按钮标题。
     * @param text
     */
    public void setNextText(String text) {
        if(title_next != null) {
            title_next.setText(text);
        }
    }
    /**
     * 设置baocun按钮标题。
     * @param text
     */
    public void setNextSave(String text) {
        if(title_save != null) {
            title_save.setText(text);
        }
    }

    /**
     * 下一步按钮被点击, 建议子类重载。
     */
    protected void onNextClick() {}

    /**
     * 返回按钮被点击, 建议子类重载。
     */
    protected void onBackClick() {}

}
