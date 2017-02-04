package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/22.
 */
public class CartDatas {
    /**
     * 购物车是否为空
     **/
    private boolean cartEmpty;
    /**
     * 购物车是否为空
     **/
    private List<CartDataList> cartList;
    /**
     *尖货商品列表
     **/
    private List<HeadList> goodsList;

    public boolean isCartEmpty() {
        return cartEmpty;
    }

    public void setCartEmpty(boolean cartEmpty) {
        this.cartEmpty = cartEmpty;
    }

    public List<CartDataList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartDataList> cartList) {
        this.cartList = cartList;
    }

    public List<HeadList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HeadList> goodsList) {
        this.goodsList = goodsList;
    }
}
