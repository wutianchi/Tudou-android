package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/8/1.
 */
public class MyMessageDetail {
    private int type;//1 跳转到商品详情/2.跳转到分类/3.跳转活动页/4.跳转到优惠券列表/5.管理员消息/6.系统消息
    private String content;
    private String title;
    private String other;
    private String createTime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
