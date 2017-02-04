package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by yaoguang on 2016/6/29.
 */
public class Citys {
    private String errorMessage;
    private String status;
    private String data;
    private String errorCode;

    public Citys(String errorMessage, String status, String data, String errorCode) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "Citys{" +
                "errorMessage='" + errorMessage + '\'' +
                ", status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
