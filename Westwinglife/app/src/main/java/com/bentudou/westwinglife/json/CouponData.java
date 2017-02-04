package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/26.
 * 优惠券总信息
 */
public class CouponData {
    private int total,page,rows;
    private List<CouponListData> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

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

    public List<CouponListData> getData() {
        return data;
    }

    public void setData(List<CouponListData> data) {
        this.data = data;
    }
}
