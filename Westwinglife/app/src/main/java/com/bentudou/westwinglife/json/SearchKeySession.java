package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/3/10.
 */
public class SearchKeySession {
    private String errorMessage,status,errorCode;
    private SearchKeyStart data;

    public SearchKeyStart getData() {
        return data;
    }

    public void setData(SearchKeyStart data) {
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
