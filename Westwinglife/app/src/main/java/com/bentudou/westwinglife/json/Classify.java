package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/3/10.
 */
public class Classify {
    private String errorMessage,status,errorCode;
    private CategoryList data;

    public CategoryList getData() {
        return data;
    }

    public void setData(CategoryList data) {
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
