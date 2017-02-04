package com.bentudou.westwinglife.json;

import java.io.Serializable;

/**
 * Created by lzz on 2016/6/26.
 */
public class AddressList implements Serializable{
    private int userAddressId;
    private int userId;
    private int addressType;
    private int province;
    private int city;
    private int district;
    private int isDefaultAddress;
    private String procinceCnName;
    private String cityCnName;
    private String districtCnName;
    private String consignee;
    private String tel;
    private String idCard;
    private String zipcode;
    private String address;

    public int getIsDefaultAddress() {
        return isDefaultAddress;
    }

    public void setIsDefaultAddress(int isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getDistrict() {
        return district;
    }

    public void setDistrict(int district) {
        this.district = district;
    }

    public String getProcinceCnName() {
        return procinceCnName;
    }

    public void setProcinceCnName(String procinceCnName) {
        this.procinceCnName = procinceCnName;
    }

    public String getCityCnName() {
        return cityCnName;
    }

    public void setCityCnName(String cityCnName) {
        this.cityCnName = cityCnName;
    }

    public String getDistrictCnName() {
        return districtCnName;
    }

    public void setDistrictCnName(String districtCnName) {
        this.districtCnName = districtCnName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressType() {
        return addressType;
    }

    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
