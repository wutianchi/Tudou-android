package com.bentudou.westwinglife.json;

import java.io.Serializable;

/**
 * Created by lzz on 2016/3/10.
 */
public class OrderDetail implements Serializable {
    private String errorMessage,status,errorCode;
    private OrderDetailBack data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public OrderDetailBack getData() {
        return data;
    }

    public void setData(OrderDetailBack data) {
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
