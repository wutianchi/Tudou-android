package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/3/10.
 */
public class GetCouponList {
    private String errorMessage,status,errorCode;
    private List<CouponInfo> data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<CouponInfo> getData() {
        return data;
    }
    public void setData(List<CouponInfo> data) {
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
