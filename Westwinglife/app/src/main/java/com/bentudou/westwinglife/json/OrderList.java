package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/29.
 * 订单列表所有核心内容
 */
public class OrderList {
    private int total;
    private List<MyOrderList> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MyOrderList> getRows() {
        return rows;
    }

    public void setRows(List<MyOrderList> rows) {
        this.rows = rows;
    }
}
