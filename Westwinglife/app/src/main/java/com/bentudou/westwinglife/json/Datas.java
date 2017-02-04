package com.bentudou.westwinglife.json;

/**
 * Created by yaoguang on 2016/6/29.
 */
public class Datas {
    private String internationalId;
    private String nameZh;
    private String nameEn;
    private String parentId;
    private String level;
    private String shortName;
    private String isEnabled;
    private String zipCode;

    public Datas(String internationalId, String nameZh, String nameEn, String parentId, String level, String shortName, String isEnabled, String zipCode) {
        this.internationalId = internationalId;
        this.nameZh = nameZh;
        this.nameEn = nameEn;
        this.parentId = parentId;
        this.level = level;
        this.shortName = shortName;
        this.isEnabled = isEnabled;
        this.zipCode = zipCode;
    }

    public String getInternationalId() {
        return internationalId;
    }

    public void setInternationalId(String internationalId) {
        this.internationalId = internationalId;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Datas{" +
                "internationalId='" + internationalId + '\'' +
                ", nameZh='" + nameZh + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", parentId='" + parentId + '\'' +
                ", level='" + level + '\'' +
                ", shortName='" + shortName + '\'' +
                ", isEnabled='" + isEnabled + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}

