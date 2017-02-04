package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/3/10.
 */
public class StartImgInfo {
    private String errorMessage,status,errorCode;
    private StartImg data;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public StartImg getData() {
        return data;
    }

    public void setData(StartImg data) {
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
