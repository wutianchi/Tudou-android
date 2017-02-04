package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lzz on 2016/6/28.
 */
public class OrderDetailBack implements Serializable{
    private String errorDepotName;
    private int orderId;
    private boolean goodsStatus;
    private AddressList defaultAddress;
    private List<ErrorGoodsList> errorGoodsList;
    private List<OrderListData> orderList;

    public AddressList getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(AddressList defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getErrorDepotName() {
        return errorDepotName;
    }

    public void setErrorDepotName(String errorDepotName) {
        this.errorDepotName = errorDepotName;
    }

    public boolean isGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(boolean goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public List<ErrorGoodsList> getErrorGoodsList() {
        return errorGoodsList;
    }

    public void setErrorGoodsList(List<ErrorGoodsList> errorGoodsList) {
        this.errorGoodsList = errorGoodsList;
    }

    public List<OrderListData> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListData> orderList) {
        this.orderList = orderList;
    }
}
