package com.bentudou.westwinglife.dbcache.bean;

/**
 * @author Ben
 * @version v1.2.1
 * Create date :2015/12/17
 * Desc:时间的单位
 */
public enum  TimeType {
    day("天"),hour("小时"),minute("分钟"),second("秒"),millis("毫秒");
    private final String desc;

    TimeType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
