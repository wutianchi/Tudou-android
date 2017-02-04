package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/3/10.
 */
public class LastPayInfo {
    private String errorMessage,status,errorCode;
    private MyOrderList data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public MyOrderList getData() {
        return data;
    }

    public void setData(MyOrderList data) {
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
