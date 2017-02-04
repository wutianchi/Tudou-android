package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/26.
 * 接口返回优惠券
 */
public class Coupon {
    private String errorMessage,status,errorCode;
    private CouponData data;

    public CouponData getData() {
        return data;
    }

    public void setData(CouponData data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
