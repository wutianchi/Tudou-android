package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lzz on 2016/6/29.
 */
public class OrderGoodsList implements Serializable {
    private int goodsId;
    private int recId;
    private int orderId;
    private int goodsNumber;
    private BigDecimal goodsPrice;
    private String goodsCnName;
    private String goodsImg;

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsCnName() {
        return goodsCnName;
    }

    public void setGoodsCnName(String goodsCnName) {
        this.goodsCnName = goodsCnName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
