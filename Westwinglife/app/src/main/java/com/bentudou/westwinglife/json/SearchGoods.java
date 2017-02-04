package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/18.
 */
public class SearchGoods {
    private String errorMessage,status,errorCode;
    private SearchGoodsList data;

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

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public SearchGoodsList getData() {
        return data;
    }

    public void setData(SearchGoodsList data) {
        this.data = data;
    }
}
