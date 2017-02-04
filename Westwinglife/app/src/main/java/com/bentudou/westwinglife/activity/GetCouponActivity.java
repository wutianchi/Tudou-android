package com.bentudou.westwinglife.activity;

import android.view.View;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.GetCouponAdapter;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.CouponInfo;
import com.bentudou.westwinglife.json.GetCouponList;
import com.bentudou.westwinglife.json.UserSign;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.MyListView;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/5.
 * 领取优惠券
 */
public class GetCouponActivity extends BaseTitleActivity {
    private MyListView mlv_get_coupon;
    private TextView tv_no_get_coupon;
    private List<CouponInfo> couponInfoList;
    private GetCouponAdapter getCouponAdapter;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_get_coupon);
    }

    @Override
    protected void initView() {
        super.setTitleText("领取优惠券");
        mlv_get_coupon = (MyListView) findViewById(R.id.mlv_get_coupon);
        tv_no_get_coupon = (TextView) findViewById(R.id.tv_no_get_coupon);
        initData();
    }

    private void initData() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getCouponsListToIndex(SharePreferencesUtils.getBtdToken(this),new Callback<GetCouponList>() {
            @Override
            public void success(GetCouponList getCouponList, Response response) {
                if (getCouponList.getStatus().equals("1")){
                    if (null!=getCouponList.getData()&&!getCouponList.getData().isEmpty()){
                        mlv_get_coupon.setVisibility(View.VISIBLE);
                        tv_no_get_coupon.setVisibility(View.GONE);
                        couponInfoList = getCouponList.getData();
                        getCouponAdapter = new GetCouponAdapter(couponInfoList,GetCouponActivity.this);
                        mlv_get_coupon.setAdapter(getCouponAdapter);
                    }else {
                        mlv_get_coupon.setVisibility(View.GONE);
                        tv_no_get_coupon.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
