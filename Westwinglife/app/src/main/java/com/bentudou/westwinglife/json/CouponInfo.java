package com.bentudou.westwinglife.json;

import java.math.BigDecimal;

/**
 * Created by lzz on 2016/8/9.
 */
public class CouponInfo {
    private int couponId;//优惠券ID
    private int couponShelfLife;//有效期（天）
    private String couponName;//优惠券名称
    private String couponType;//优惠券类型（00-满减券 01免邮券 02打折券）
    private String couponReceiveStartTime;//领取时间（开始）
    private String couponReceiveEndTime;//领取时间（结束）
    private BigDecimal couponPrice;//优惠劵面值/折扣率
    private BigDecimal couponUseConditionPrice;//使用门槛金额

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getCouponShelfLife() {
        return couponShelfLife;
    }

    public void setCouponShelfLife(int couponShelfLife) {
        this.couponShelfLife = couponShelfLife;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponReceiveStartTime() {
        return couponReceiveStartTime;
    }

    public void setCouponReceiveStartTime(String couponReceiveStartTime) {
        this.couponReceiveStartTime = couponReceiveStartTime;
    }

    public String getCouponReceiveEndTime() {
        return couponReceiveEndTime;
    }

    public void setCouponReceiveEndTime(String couponReceiveEndTime) {
        this.couponReceiveEndTime = couponReceiveEndTime;
    }

    public BigDecimal getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(BigDecimal couponPrice) {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getCouponUseConditionPrice() {
        return couponUseConditionPrice;
    }

    public void setCouponUseConditionPrice(BigDecimal couponUseConditionPrice) {
        this.couponUseConditionPrice = couponUseConditionPrice;
    }
}
