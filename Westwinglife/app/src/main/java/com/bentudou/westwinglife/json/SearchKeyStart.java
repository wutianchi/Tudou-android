package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/19.
 */
public class SearchKeyStart {
    /**
     * 获取搜索关键字列表
     */
    private List<SearchKeyList> searchKeywordList;

    public List<SearchKeyList> getSearchKeywordList() {
        return searchKeywordList;
    }

    public void setSearchKeywordList(List<SearchKeyList> searchKeywordList) {
        this.searchKeywordList = searchKeywordList;
    }
}
