package com.bentudou.westwinglife.json;


import java.io.PipedReader;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Grace on 2015/7/31.
 */
public class HeadList implements Serializable {
    /**
     * 商品id
     */
    private int goodsId;
    /**
     * 商品数量
     */
    private int goodsNumber;
    /**
     * 商品图片地址
     */
    private String goodsImg;
    /**
     * 仓库图片地址
     */
    private String depotIcon;
    /**
     * 货品中文名
     */
    private String goodsCnName;
    /**
     * 商品描述
     */
    private String goodsDesc;
    /**
     * 商品描述
     */
    private String goodsDescTable;
    /**
     * 仓库名
     */
    private String depotName;
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
    private BigDecimal marketPrice;
    /**
     * 本店售价人民币
     */
    private BigDecimal shopPriceCny;
    /**
     * 是否活动
     */
    private boolean isActivity;
    /**
     * 是否被收藏
     */
    private boolean isCllect;
    /**
     * 活动信息
     */
    private ActivityDetail promotions;
    /**
     * 左上icon
     */
    private String topLeftCornerMarkImg;
    /**
     * 右上icon
     */
    private String topRightCornerMarkImg;
    /**
     * 左下icon
     */
    private String bottomLeftCornerMarkImg;
    /**
     * 右下icon
     */
    private String bottomRightCornerMarkImg;

    public BigDecimal getShopPriceCny() {
        return shopPriceCny;
    }

    public void setShopPriceCny(BigDecimal shopPriceCny) {
        this.shopPriceCny = shopPriceCny;
    }

    public String getGoodsDescTable() {
        return goodsDescTable;
    }

    public void setGoodsDescTable(String goodsDescTable) {
        this.goodsDescTable = goodsDescTable;
    }

    public boolean isCllect() {
        return isCllect;
    }

    public void setCllect(boolean cllect) {
        isCllect = cllect;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getTopLeftCornerMarkImg() {
        return topLeftCornerMarkImg;
    }

    public void setTopLeftCornerMarkImg(String topLeftCornerMarkImg) {
        this.topLeftCornerMarkImg = topLeftCornerMarkImg;
    }

    public String getBottomRightCornerMarkImg() {
        return bottomRightCornerMarkImg;
    }

    public void setBottomRightCornerMarkImg(String bottomRightCornerMarkImg) {
        this.bottomRightCornerMarkImg = bottomRightCornerMarkImg;
    }

    public String getBottomLeftCornerMarkImg() {
        return bottomLeftCornerMarkImg;
    }

    public void setBottomLeftCornerMarkImg(String bottomLeftCornerMarkImg) {
        this.bottomLeftCornerMarkImg = bottomLeftCornerMarkImg;
    }

    public String getTopRightCornerMarkImg() {
        return topRightCornerMarkImg;
    }

    public void setTopRightCornerMarkImg(String topRightCornerMarkImg) {
        this.topRightCornerMarkImg = topRightCornerMarkImg;
    }

    public String getDepotIcon() {
        return depotIcon;
    }

    public void setDepotIcon(String depotIcon) {
        this.depotIcon = depotIcon;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public ActivityDetail getPromotions() {
        return promotions;
    }

    public void setPromotions(ActivityDetail promotions) {
        this.promotions = promotions;
    }

    public HeadList(int goodsId, String goodsImg, String goodsCnName,
                    int discountRate, BigDecimal marketPrice, BigDecimal shopPriceCny) {
        this.goodsId = goodsId;
        this.goodsImg = goodsImg;
        this.goodsCnName = goodsCnName;
        this.discountRate = discountRate;
        this.marketPrice = marketPrice;
        this.shopPriceCny = shopPriceCny;
    }

    public HeadList() {

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

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

}
