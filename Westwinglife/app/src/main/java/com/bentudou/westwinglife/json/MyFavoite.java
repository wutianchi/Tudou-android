package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/8/1.
 */
public class MyFavoite {
    private int total;//收藏总条数
    private List<FavoiteInfo> rows;//收藏商品列表

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FavoiteInfo> getRows() {
        return rows;
    }

    public void setRows(List<FavoiteInfo> rows) {
        this.rows = rows;
    }
}
