package com.bentudou.westwinglife.json;

/**
 * Created by Grace on 2015/9/25.
 */
public class HeadImg {
    private int advertisementId;
    private int advertisementLinkId;//详情分类页id
    private String advertisementType;//01-单品详情 02-分类列表 03-内部专题页 04-外部广告
    private String advertisementName;
    private String advertisementImg;//banner图片
    private String advertisementLink;//广告链接

    public int getAdvertisementLinkId() {
        return advertisementLinkId;
    }

    public void setAdvertisementLinkId(int advertisementLinkId) {
        this.advertisementLinkId = advertisementLinkId;
    }

    public String getAdvertisementType() {
        return advertisementType;
    }

    public void setAdvertisementType(String advertisementType) {
        this.advertisementType = advertisementType;
    }

    public int getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getAdvertisementName() {
        return advertisementName;
    }

    public void setAdvertisementName(String advertisementName) {
        this.advertisementName = advertisementName;
    }

    public String getAdvertisementImg() {
        return advertisementImg;
    }

    public void setAdvertisementImg(String advertisementImg) {
        this.advertisementImg = advertisementImg;
    }

    public String getAdvertisementLink() {
        return advertisementLink;
    }

    public void setAdvertisementLink(String advertisementLink) {
        this.advertisementLink = advertisementLink;
    }
}
