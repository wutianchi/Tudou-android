package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/22.
 */
public class ActivityDetail {
    /**
     *活动id
     **/
    private int promotionsId;
    /**
     *活动名字
     **/
    private String promotionsName;
    /**
     *活动开始时间
     **/
    private String promotionsStartTime;
    /**
     *活动结束时间
     **/
    private String promotionsEndTime;

    public int getPromotionsId() {
        return promotionsId;
    }

    public void setPromotionsId(int promotionsId) {
        this.promotionsId = promotionsId;
    }

    public String getPromotionsName() {
        return promotionsName;
    }

    public void setPromotionsName(String promotionsName) {
        this.promotionsName = promotionsName;
    }

    public String getPromotionsStartTime() {
        return promotionsStartTime;
    }

    public void setPromotionsStartTime(String promotionsStartTime) {
        this.promotionsStartTime = promotionsStartTime;
    }

    public String getPromotionsEndTime() {
        return promotionsEndTime;
    }

    public void setPromotionsEndTime(String promotionsEndTime) {
        this.promotionsEndTime = promotionsEndTime;
    }
}
