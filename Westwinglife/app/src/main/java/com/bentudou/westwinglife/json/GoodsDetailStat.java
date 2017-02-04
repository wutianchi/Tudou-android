package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/19.
 */
public class GoodsDetailStat {
    //商品详情字段
    private GoodsDetail goods;
    private Depot depot;
    private Promotion promotions;
    private GoodBrand brand;
    private int cartGoodsNum;

    /**
     * 商品图片列表
     */

    private List<GoodsPictureData> goodsImgList;

    public GoodBrand getBrand() {
        return brand;
    }

    public void setBrand(GoodBrand brand) {
        this.brand = brand;
    }
    public Depot getDepot() {
        return depot;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Promotion getPromotions() {
        return promotions;
    }

    public void setPromotions(Promotion promotions) {
        this.promotions = promotions;
    }

    public int getCartGoodsNum() {
        return cartGoodsNum;
    }

    public void setCartGoodsNum(int cartGoodsNum) {
        this.cartGoodsNum = cartGoodsNum;
    }

    public GoodsDetail getGoods() {
        return goods;
    }

    public void setGoods(GoodsDetail goods) {
        this.goods = goods;
    }

    public List<GoodsPictureData> getGoodsImgList() {
        return goodsImgList;
    }

    public void setGoodsImgList(List<GoodsPictureData> goodsImgList) {
        this.goodsImgList = goodsImgList;
    }
}
