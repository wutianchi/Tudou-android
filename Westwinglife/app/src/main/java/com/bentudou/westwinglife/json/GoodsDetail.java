package com.bentudou.westwinglife.json;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lzz on 2016/6/18.
 */
public class GoodsDetail {
    /**
     * ID
     */
    private int goodsId;

    /**
     * 商品分类ID
     */
    private int categoryId;
    /**
     * 商品品牌ID
     */
    private int brandId;

    /**
     * 商品名称
     */
    private String goodsCnName;
    /**
     * 市场售价
     */
    private BigDecimal marketPrice;

    /**
     * 本店价格
     */
    private BigDecimal shopPriceCny;
    /**
     * 起订量
     */
    private int wholesaleMoq;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 商品图文详情
     */
    private String goodsDescImg;

    /**
     * 是否参加了活动（0.未参加活动/1.参加活动）
     */
    private boolean isActivity;
    /**
     * 发货仓
     */
    private String depotName;
    /**
     * 产地
     */
    private String goodsOriginName;
    /**
     * 货号
     */
    private String goodsSn;
    /**
     * 产品参数
     */
    private String goodsSpecifications;

    public BigDecimal getShopPriceCny() {
        return shopPriceCny;
    }

    public void setShopPriceCny(BigDecimal shopPriceCny) {
        this.shopPriceCny = shopPriceCny;
    }

    public String getGoodsOriginName() {
        return goodsOriginName;
    }

    public void setGoodsOriginName(String goodsOriginName) {
        this.goodsOriginName = goodsOriginName;
    }

    public String getGoodsSn() {
        return goodsSn;
    }

    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public int getWholesaleMoq() {
        return wholesaleMoq;
    }

    public void setWholesaleMoq(int wholesaleMoq) {
        this.wholesaleMoq = wholesaleMoq;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getGoodsCnName() {
        return goodsCnName;
    }

    public void setGoodsCnName(String goodsCnName) {
        this.goodsCnName = goodsCnName;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public boolean isActivity() {
        return isActivity;
    }

    public void setActivity(boolean activity) {
        isActivity = activity;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public String getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(String goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public String getGoodsDescImg() {
        return goodsDescImg;
    }

    public void setGoodsDescImg(String goodsDescImg) {
        this.goodsDescImg = goodsDescImg;
    }
}
