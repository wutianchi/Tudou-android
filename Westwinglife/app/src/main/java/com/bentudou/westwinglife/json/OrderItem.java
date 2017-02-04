package com.bentudou.westwinglife.json;

import java.io.Serializable;

/**
 * Created by lzz on 2016/6/22.
 */
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 863792190632206222L;

    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 商品数量
     */
    private String goodsNumber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
