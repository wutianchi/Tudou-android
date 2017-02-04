package com.bentudou.westwinglife.dbcache.bean;

/**
 * @author Ben
 * @version v1.2.1
 *          Create date :2015/12/16
 *          Desc:
 */
public class Entity {

    private String url;
    private String data;
    private String lastTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
