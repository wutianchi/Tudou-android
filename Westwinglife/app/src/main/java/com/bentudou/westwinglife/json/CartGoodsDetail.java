package com.bentudou.westwinglife.json;


import java.math.BigDecimal;

/**
 * Created by Grace on 2015/7/31.
 */
public class CartGoodsDetail {
    /**
     * 商品id
     */
    private int goodsId;
    /**
     * 商品数量
     */
    private int goodsNumber;
    /**
     * 商品库存数量
     */
    private int goodsStockNumber;
    /**
     * 商品图片地址
     */
    private String goodsImg;
    /**
     * 货品中文名
     */
    private String goodsCnName;
    /**
     *起定量
     */
    private int wholesaleMoq;
    /**
     * 折扣
     */
    private int discountRate;
    /**
     * 税率
     */
    private BigDecimal customsDuties;
    /**
     * 市场价
     */
    private boolean onSale;
    /**
     * 本店售价人民币
     */
    private BigDecimal shopPriceCny;
    /**
     * 是否活动
     */
    private boolean isActivity;

    public BigDecimal getShopPriceCny() {
        return shopPriceCny;
    }

    public void setShopPriceCny(BigDecimal shopPriceCny) {
        this.shopPriceCny = shopPriceCny;
    }

    public int getGoodsStockNumber() {
        return goodsStockNumber;
    }

    public void setGoodsStockNumber(int goodsStockNumber) {
        this.goodsStockNumber = goodsStockNumber;
    }

    public int getWholesaleMoq() {
        return wholesaleMoq;
    }

    public void setWholesaleMoq(int wholesaleMoq) {
        this.wholesaleMoq = wholesaleMoq;
    }

    public CartGoodsDetail(int goodsId, String goodsImg, String goodsCnName,
                           int discountRate, BigDecimal shopPriceCny) {
        this.goodsId = goodsId;
        this.goodsImg = goodsImg;
        this.goodsCnName = goodsCnName;
        this.discountRate = discountRate;
        this.shopPriceCny = shopPriceCny;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public void setOnSale(boolean onSale) {
        this.onSale = onSale;
    }

    public CartGoodsDetail() {

    }

    public BigDecimal getCustomsDuties() {
        return customsDuties;
    }

    public void setCustomsDuties(BigDecimal customsDuties) {
        this.customsDuties = customsDuties;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setActivity(boolean activity) {
        isActivity = activity;
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

    public String getGoodsCnName() {
        return goodsCnName;
    }

    public void setGoodsCnName(String goodsCnName) {
        this.goodsCnName = goodsCnName;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

}
