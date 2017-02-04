package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/18.
 */
public class SearchGoodsList {
    /**
     * 搜索每页数量
     */
    private int rows;
    /**
     * 搜索第几页
     */
    private int page;
    /**
     * 关键字
     */
    private String keyWords;
    /**
     * 热门类目数据列表
     */
    private List<HeadList> goodsList;

    public List<HeadList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HeadList> goodsList) {
        this.goodsList = goodsList;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }
}
