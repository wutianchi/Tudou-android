package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lzz on 2016/6/28.
 */
public class OrderListData implements Serializable {
    private int depotId;
    private BigDecimal shipMentFee;
    private BigDecimal depotSum;
    private BigDecimal depotSumGoods;
    private BigDecimal depotCustomsDuties;
    private String depotName;
    private List<HeadList> goodsList;

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
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

    public BigDecimal getShipMentFee() {
        return shipMentFee;
    }

    public void setShipMentFee(BigDecimal shipMentFee) {
        this.shipMentFee = shipMentFee;
    }

    public List<HeadList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HeadList> goodsList) {
        this.goodsList = goodsList;
    }
}
