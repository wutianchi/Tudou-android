package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/18.
 */
public class ClassifyDatas {
    /**
     * 三级类目数据列表
     */
    private List<ClassifyThreeDatas> categoryList;
    /**
     * 一级类目id
     */
    private int parentId;
    /**
     * 二级类目id
     */
    private int categoryId;
    /**
     * 二级类目中文名
     */
    private String categoryCnName;
    /**
     * 二级类目是否显示
     */
    private boolean isShow;
    /**
     * 二级类目图片
     */
    private String categoryIcon;
    private String categoryImgPc;
    private String categoryImg;

    public String getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(String categoryImg) {
        this.categoryImg = categoryImg;
    }

    public String getCategoryImgPc() {
        return categoryImgPc;
    }

    public void setCategoryImgPc(String categoryImgPc) {
        this.categoryImgPc = categoryImgPc;
    }

    public List<ClassifyThreeDatas> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ClassifyThreeDatas> categoryList) {
        this.categoryList = categoryList;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
