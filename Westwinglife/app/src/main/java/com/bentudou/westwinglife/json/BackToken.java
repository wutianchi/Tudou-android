package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/6/22.
 */
public class BackToken {
    private String BtdToken;
    private String userInviteCode;

    public String getUserInviteCode() {
        return userInviteCode;
    }

    public void setUserInviteCode(String userInviteCode) {
        this.userInviteCode = userInviteCode;
    }

    public String getBtdToken() {
        return BtdToken;
    }

    public void setBtdToken(String btdToken) {
        BtdToken = btdToken;
    }
}
