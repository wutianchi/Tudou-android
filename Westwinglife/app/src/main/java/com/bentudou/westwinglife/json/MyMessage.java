package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/8/1.
 */
public class MyMessage {
    private int total;//消息总条数
    private List<MessageInfo> rows;//消息列表

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MessageInfo> getRows() {
        return rows;
    }

    public void setRows(List<MessageInfo> rows) {
        this.rows = rows;
    }
}
