package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/9/27.
 */
public class YunyingData {
    private int operatingPositionId;
    private String operatingPositionName;
    private String operatingPositionImg;
    private String operatingPositionInsideImg;//运营banner
    private String operatingPositionLink;
    private String operatingPositionPosition;
    private int operatingPositionLinkType;
    private int operatingPositionIsShow;
    private int operatingPositionSort;
    private int operatingPositionLastUpdateUser;
    private int operatingPositionLinkId;
    private List<HeadList> goodsList;

    public String getOperatingPositionInsideImg() {
        return operatingPositionInsideImg;
    }

    public void setOperatingPositionInsideImg(String operatingPositionInsideImg) {
        this.operatingPositionInsideImg = operatingPositionInsideImg;
    }

    public int getOperatingPositionLinkId() {
        return operatingPositionLinkId;
    }

    public void setOperatingPositionLinkId(int operatingPositionLinkId) {
        this.operatingPositionLinkId = operatingPositionLinkId;
    }

    public int getOperatingPositionId() {
        return operatingPositionId;
    }

    public void setOperatingPositionId(int operatingPositionId) {
        this.operatingPositionId = operatingPositionId;
    }

    public String getOperatingPositionName() {
        return operatingPositionName;
    }

    public void setOperatingPositionName(String operatingPositionName) {
        this.operatingPositionName = operatingPositionName;
    }

    public String getOperatingPositionImg() {
        return operatingPositionImg;
    }

    public void setOperatingPositionImg(String operatingPositionImg) {
        this.operatingPositionImg = operatingPositionImg;
    }

    public String getOperatingPositionLink() {
        return operatingPositionLink;
    }

    public void setOperatingPositionLink(String operatingPositionLink) {
        this.operatingPositionLink = operatingPositionLink;
    }

    public String getOperatingPositionPosition() {
        return operatingPositionPosition;
    }

    public void setOperatingPositionPosition(String operatingPositionPosition) {
        this.operatingPositionPosition = operatingPositionPosition;
    }

    public int getOperatingPositionLinkType() {
        return operatingPositionLinkType;
    }

    public void setOperatingPositionLinkType(int operatingPositionLinkType) {
        this.operatingPositionLinkType = operatingPositionLinkType;
    }

    public int getOperatingPositionIsShow() {
        return operatingPositionIsShow;
    }

    public void setOperatingPositionIsShow(int operatingPositionIsShow) {
        this.operatingPositionIsShow = operatingPositionIsShow;
    }

    public int getOperatingPositionSort() {
        return operatingPositionSort;
    }

    public void setOperatingPositionSort(int operatingPositionSort) {
        this.operatingPositionSort = operatingPositionSort;
    }

    public int getOperatingPositionLastUpdateUser() {
        return operatingPositionLastUpdateUser;
    }

    public void setOperatingPositionLastUpdateUser(int operatingPositionLastUpdateUser) {
        this.operatingPositionLastUpdateUser = operatingPositionLastUpdateUser;
    }

    public List<HeadList> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<HeadList> goodsList) {
        this.goodsList = goodsList;
    }
}
