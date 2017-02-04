package com.bentudou.westwinglife.json;

/**
 * Created by Grace on 2015/9/25.
 */
public class HeadGrid {
    /**
     * 类目id
     */
    private int categoryId;
    /**
     * 类目名称
     */
    private String categoryCnName;
    /**
     * 类目图片
     */
    private String categoryImgPc;
    private String categoryIcon;
    public HeadGrid(int categoryId,String categoryName,String categoryImgPc) {
        this.categoryId = categoryId;
        this.categoryCnName = categoryCnName;
        this.categoryImgPc = categoryImgPc;
    }
    public HeadGrid() {

    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCnName() {
        return categoryCnName;
    }

    public void setCategoryCnName(String categoryCnName) {
        this.categoryCnName = categoryCnName;
    }

    public String getCategoryImgPc() {
        return categoryImgPc;
    }

    public void setCategoryImgPc(String categoryImgPc) {
        this.categoryImgPc = categoryImgPc;
    }
}
