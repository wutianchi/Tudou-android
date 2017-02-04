package com.bentudou.westwinglife.json;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lzz on 2016/6/22.
 */
public class CartDataList {
    /**
     *仓库名称
     **/
    private String depotName;
    /**
     *仓库id
     **/
    private int depotId;
    /**
     *仓库id
     **/
    private String depotIcon;
    /**
     *仓库商品列表
     **/
    private List<CartGoodsDetail> goodsList;
    /**
     *本仓库总金额
     **/
    private BigDecimal depotSum;
    /**
     *本仓库所有商品总金额
     **/
    private BigDecimal depotSumGoods;
    /**
     *本仓库所有商品总税费
     **/
    private BigDecimal depotCustomsDuties;

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

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public List<CartGoodsDetail> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<CartGoodsDetail> goodsList) {
        this.goodsList = goodsList;
    }

    public BigDecimal getDepotSum() {
        return depotSum;
    }

    public void setDepotSum(BigDecimal depotSum) {
        this.depotSum = depotSum;
    }

    public BigDecimal getDepotSumGoods() {
        return depotSumGoods;
    }

    public void setDepotSumGoods(BigDecimal depotSumGoods) {
        this.depotSumGoods = depotSumGoods;
    }

    public BigDecimal getDepotCustomsDuties() {
        return depotCustomsDuties;
    }

    public void setDepotCustomsDuties(BigDecimal depotCustomsDuties) {
        this.depotCustomsDuties = depotCustomsDuties;
    }
}
