package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/3/10.
 */
public class GetCoupon {
    private String errorMessage,status,errorCode;
    private int data;//0/1/2, //( 0 领取失败 1 领取成功 2 该用户已经存在此优惠券)

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
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
