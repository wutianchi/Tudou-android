package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/29.
 * 订单列表分页
 */
public class OrderPage {
    private int page;
    private int rows;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
