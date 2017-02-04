package com.bentudou.westwinglife.json;

import java.math.BigDecimal;

/**
 * Created by lzz on 2016/8/1.
 */
public class FavoiteInfo {
    private int goodsId;//商品id
    private int discount;//商品起订量
    private String goodsImg; //商品图片
    private BigDecimal shopPriceCny; //商品renminb价格
    private String goodsName;//商品名称
    private boolean isOnSale;

    public boolean isOnSale() {
        return isOnSale;
    }

    public void setOnSale(boolean onSale) {
        isOnSale = onSale;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getShopPriceCny() {
        return shopPriceCny;
    }

    public void setShopPriceCny(BigDecimal shopPriceCny) {
        this.shopPriceCny = shopPriceCny;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
