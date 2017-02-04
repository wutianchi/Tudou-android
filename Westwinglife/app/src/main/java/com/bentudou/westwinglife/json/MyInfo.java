package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/22.
 */
public class MyInfo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邀请码
     */
    private String userInviteCode;
    /**
     * 手机号
     */
    private String phone;

    public String getUserInviteCode() {
        return userInviteCode;
    }

    public void setUserInviteCode(String userInviteCode) {
        this.userInviteCode = userInviteCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
