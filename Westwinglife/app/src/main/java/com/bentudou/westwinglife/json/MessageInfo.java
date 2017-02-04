package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/8/1.
 */
public class MessageInfo {
    private int id; //消息主键
    private String createTime; //创建时间
    private String title; //消息标题
    private int status;//消息状态 （0.未读、1、已读、2、已删除）

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
