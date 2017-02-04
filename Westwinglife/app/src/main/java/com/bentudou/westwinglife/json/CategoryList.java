package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/18.
 */
public class CategoryList {
    /**
     * 二级类目数据列表
     */
    private List<ClassifyDatas> categoryList;

    public List<ClassifyDatas> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<ClassifyDatas> categoryList) {
        this.categoryList = categoryList;
    }
}
