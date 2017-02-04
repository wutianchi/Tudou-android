package com.bentudou.westwinglife.json;


import java.math.BigDecimal;

/**
 * Created by Grace on 2015/7/31.
 */
public class SearchKeyList {
    /**
     * 关键字id
     */
    private int id;
    /**
     * 关键字名
     */
    private String keywordName;
    /**
     * 是否可用
     */
    private String isEnable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywordName() {
        return keywordName;
    }

    public void setKeywordName(String keywordName) {
        this.keywordName = keywordName;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }
}
