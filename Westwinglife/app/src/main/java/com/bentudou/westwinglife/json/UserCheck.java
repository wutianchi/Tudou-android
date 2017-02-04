package com.bentudou.westwinglife.json;

/**
 * Created by lzz on 2016/8/4.
 */
public class UserCheck {
    private boolean isUserSign;
    private int userGrade;

    public boolean isUserSign() {
        return isUserSign;
    }

    public void setUserSign(boolean userSign) {
        isUserSign = userSign;
    }

    public int getUserGrade() {
        return userGrade;
    }

    public void setUserGrade(int userGrade) {
        this.userGrade = userGrade;
    }
}
