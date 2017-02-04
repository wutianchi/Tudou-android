package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.CouponAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Coupon;
import com.bentudou.westwinglife.json.CouponListData;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.view.MyListView;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.view.ProgressHUD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/26.
 */
public class SelectCouponActivity extends BaseTitleActivity {
    private MyListView mlv_coupon;
    private CouponAdapter couponAdapter;
    private List<CouponListData> couponListDatas;
    private List<CouponListData> newCouponListDatas;
    ProgressHUD progressHUD = null;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_select_coupon);
    }

    @Override
    protected void initView() {
        super.setTitleText("选择土豆条");
        couponListDatas = (List<CouponListData>) getIntent().getSerializableExtra("coupon_list");
        mlv_coupon = (MyListView) findViewById(R.id.mlv_coupon);
        mlv_coupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("order_coupon", newCouponListDatas.get(position));
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        initData(2);
    }

    private void initData(int n) {
        newCouponListDatas = new ArrayList<CouponListData>();
        CouponListData couponListData = new CouponListData();
        couponListData.setCouponId(0);
        couponListData.setCouponName("点击这里不使用优惠券");
        couponListData.setCouponPrice(BigDecimal.valueOf(0));
        couponListData.setCouponDisableTime("2019-12-30 00:00:00");
        newCouponListDatas.add(couponListData);
        newCouponListDatas.addAll(couponListDatas);
        couponAdapter = new CouponAdapter(newCouponListDatas,SelectCouponActivity.this,n);
        mlv_coupon.setAdapter(couponAdapter);
    }
}
