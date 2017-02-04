package com.bentudou.westwinglife.json;

import java.util.List;

/**
 * Created by lzz on 2016/6/29.
 * 支付页面信息
 */
public class PayInfoBack {
    private MyOrderList order;
    private boolean isUsedCoupon;
    private List<CouponListData> UsersCoupon;

    public boolean isUsedCoupon() {
        return isUsedCoupon;
    }

    public void setUsedCoupon(boolean usedCoupon) {
        isUsedCoupon = usedCoupon;
    }

    public MyOrderList getOrder() {
        return order;
    }

    public void setOrder(MyOrderList order) {
        this.order = order;
    }

    public List<CouponListData> getUsersCoupon() {
        return UsersCoupon;
    }

    public void setUsersCoupon(List<CouponListData> usersCoupon) {
        UsersCoupon = usersCoupon;
    }
}
