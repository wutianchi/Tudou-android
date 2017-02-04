package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by yaoguang on 2016/6/29.
 */
public class City {
    private String errorMessage;
    private String status;
    private List<Datas> data;
    private String errorCode;

    public City(String errorMessage, String status, List<Datas> data, String errorCode) {
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

    public List<Datas> getData() {
        return data;
    }

    public void setData(List<Datas> data) {
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
        return "City{" +
                "errorMessage='" + errorMessage + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
