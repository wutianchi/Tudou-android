package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/8/4.
 */
public class GrowInfo {
    private int id;
    private int userId;
    private int growthTotal;
    private int growthType;
    private String addTime;
    private String growthDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGrowthTotal() {
        return growthTotal;
    }

    public void setGrowthTotal(int growthTotal) {
        this.growthTotal = growthTotal;
    }

    public int getGrowthType() {
        return growthType;
    }

    public void setGrowthType(int growthType) {
        this.growthType = growthType;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getGrowthDesc() {
        return growthDesc;
    }

    public void setGrowthDesc(String growthDesc) {
        this.growthDesc = growthDesc;
    }
}
