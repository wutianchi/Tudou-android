package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/18.
 */
public class ClassifyThreeDatas {
    /**
     * 三级类目id
     */
    private int categoryId;
    /**
     * 三级类目中文名
     */
    private String categoryCnName;
    /**
     * 三级类目是否显示
     */
    private boolean isShow;
    /**
     * 三级类目图片
     */
    private String categoryIcon;
    /**
     * 三级类目图片
     */
    private String categoryImgPc;

    public String getCategoryImgPc() {
        return categoryImgPc;
    }

    public void setCategoryImgPc(String categoryImgPc) {
        this.categoryImgPc = categoryImgPc;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCnName() {
        return categoryCnName;
    }

    public void setCategoryCnName(String categoryCnName) {
        this.categoryCnName = categoryCnName;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
