package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lzz on 2016/3/2.
 */
public class HeadInfo implements Serializable{
    /**
     * 轮播图数据列表
     */
    private List<HeadImg> advertisementList;
    /**
     * 运营位数据列表
     */
    private List<YunyingData> operatingPositionList;
    /**
     * 运营位单品数据列表
     */
    private List<HeadList> appHomePageItemList;

    /**
     * 热门类目数据列表
     */
    private List<HeadGrid> categoryList;
    private List<SearchKeyList> searchKeywordList;

    public List<HeadList> getAppHomePageItemList() {
        return appHomePageItemList;
    }

    public void setAppHomePageItemList(List<HeadList> appHomePageItemList) {
        this.appHomePageItemList = appHomePageItemList;
    }

    public List<SearchKeyList> getSearchKeywordList() {
        return searchKeywordList;
    }

    public void setSearchKeywordList(List<SearchKeyList> searchKeywordList) {
        this.searchKeywordList = searchKeywordList;
    }

    public List<YunyingData> getOperatingPositionList() {
        return operatingPositionList;
    }

    public void setOperatingPositionList(List<YunyingData> operatingPositionList) {
        this.operatingPositionList = operatingPositionList;
    }

    public List<HeadImg> getAdvertisementList() {
        return advertisementList;
    }

    public void setAdvertisementList(List<HeadImg> advertisementList) {
        this.advertisementList = advertisementList;
    }

    public List<HeadGrid> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<HeadGrid> categoryList) {
        this.categoryList = categoryList;
    }

}
