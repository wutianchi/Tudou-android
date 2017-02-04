package com.bentudou.westwinglife.json;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lzz on 2016/6/26.
 */
public class CommitBackOrder {
    private int depotId;//仓库id
    private BigDecimal shipMentFee;//运费
    private BigDecimal depotSum;//订单总金额
    private BigDecimal depotSumGoods;//商品总金额
    private BigDecimal depotCustomsDuties;//商品税
    private String depotName;//仓库名
    private List<HeadList> goodsList;

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public BigDecimal getShipMentFee() {
        return shipMentFee;
    }

    public void setShipMentFee(BigDecimal shipMentFee) {
        this.shipMentFee = shipMentFee;
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

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public List<HeadList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HeadList> goodsList) {
        this.goodsList = goodsList;
    }
}
