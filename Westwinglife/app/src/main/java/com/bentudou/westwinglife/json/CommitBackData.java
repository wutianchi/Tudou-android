package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/26.
 */
public class CommitBackData {
    private boolean goodsStatus;
    private List<CommitBackOrder> orderList;

    public boolean isGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(boolean goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public List<CommitBackOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<CommitBackOrder> orderList) {
        this.orderList = orderList;
    }
}
