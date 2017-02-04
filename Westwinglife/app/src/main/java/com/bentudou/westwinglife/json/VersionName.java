package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/9/8.
 */
public class VersionName {
    private int id;
    private int versionType;
    private int versionSn;
    private String addTime;
    private String version;
    private String versionDesc;

    public int getVersionSn() {
        return versionSn;
    }

    public void setVersionSn(int versionSn) {
        this.versionSn = versionSn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionType() {
        return versionType;
    }

    public void setVersionType(int versionType) {
        this.versionType = versionType;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }
}
