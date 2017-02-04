package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/3/10.
 */
public class AddSub {
    private String errorMessage,status,errorCode;
    private AddSubBack data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AddSubBack getData() {
        return data;
    }

    public void setData(AddSubBack data) {
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
