package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lzz on 2016/8/18.
 */
public class OverseasLiveData implements Serializable {
    private int goodsId;
    private int goodsNumber;
    private String goodsSlogan;
    private String goodsCnName;
    private String goodsDescImg;
    private String goodsValidityEndTime;
    private String liveStreamUrl;
    private String liveStreamImg;
    private BigDecimal shopPrice;
    private BigDecimal shopPriceCny;
    private List<LiveGoodsImg> goodsImgList;

    public BigDecimal getShopPriceCny() {
        return shopPriceCny;
    }

    public void setShopPriceCny(BigDecimal shopPriceCny) {
        this.shopPriceCny = shopPriceCny;
    }

    public String getLiveStreamUrl() {
        return liveStreamUrl;
    }

    public void setLiveStreamUrl(String liveStreamUrl) {
        this.liveStreamUrl = liveStreamUrl;
    }

    public String getLiveStreamImg() {
        return liveStreamImg;
    }

    public void setLiveStreamImg(String liveStreamImg) {
        this.liveStreamImg = liveStreamImg;
    }

    public String getGoodsDescImg() {
        return goodsDescImg;
    }

    public void setGoodsDescImg(String goodsDescImg) {
        this.goodsDescImg = goodsDescImg;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsSlogan() {
        return goodsSlogan;
    }

    public void setGoodsSlogan(String goodsSlogan) {
        this.goodsSlogan = goodsSlogan;
    }

    public String getGoodsCnName() {
        return goodsCnName;
    }

    public void setGoodsCnName(String goodsCnName) {
        this.goodsCnName = goodsCnName;
    }

    public String getGoodsValidityEndTime() {
        return goodsValidityEndTime;
    }

    public void setGoodsValidityEndTime(String goodsValidityEndTime) {
        this.goodsValidityEndTime = goodsValidityEndTime;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public List<LiveGoodsImg> getGoodsImgList() {
        return goodsImgList;
    }

    public void setGoodsImgList(List<LiveGoodsImg> goodsImgList) {
        this.goodsImgList = goodsImgList;
    }
}
