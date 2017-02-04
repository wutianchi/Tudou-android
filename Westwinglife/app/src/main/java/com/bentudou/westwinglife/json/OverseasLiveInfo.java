package com.bentudou.westwinglife.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lzz on 2016/8/18.
 */
public class OverseasLiveInfo implements Serializable {
    private int total;
    private List<OverseasLiveData> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OverseasLiveData> getRows() {
        return rows;
    }

    public void setRows(List<OverseasLiveData> rows) {
        this.rows = rows;
    }
}
