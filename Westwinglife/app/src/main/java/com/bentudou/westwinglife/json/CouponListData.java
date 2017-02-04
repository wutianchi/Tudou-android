package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lzz on 2016/6/26.
 * 优惠券详细信息
 */
public class CouponListData implements Serializable {
    private int couponId; //用户优惠券id
    private BigDecimal couponPrice;//用户优惠券金额
    private BigDecimal couponUseConditionPrice;//用户优惠券门槛
    private String couponType;//优惠券类型
    private String couponName;//优惠券描述
    private String couponDisableTime;//优惠券过期时间

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
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

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDisableTime() {
        return couponDisableTime;
    }

    public void setCouponDisableTime(String couponDisableTime) {
        this.couponDisableTime = couponDisableTime;
    }
}
