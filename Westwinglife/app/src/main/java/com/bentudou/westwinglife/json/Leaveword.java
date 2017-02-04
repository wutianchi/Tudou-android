package com.bentudou.westwinglife.json;

/**
 * Created by yaoguang on 2016/7/4.
 */
public class Leaveword {
    private String data;
    private String status;

    public Leaveword(String data, String status) {
        this.data = data;
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Leaveword{" +
                "data='" + data + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
