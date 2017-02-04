package com.bentudou.westwinglife.activity;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.CouponAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Coupon;
import com.bentudou.westwinglife.json.CouponListData;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyListView;
import com.gunlei.app.ui.view.ProgressHUD;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/26.
 */
public class MyCouponActivity extends BaseTitleActivity {
    private ScrollView sv_coupon;
    private TextView tv_no_coupon;
    private MyListView mlv_coupon;
    private RadioGroup rg_select_coupon_style;
    private RadioButton rb_can_use_coupon,rb_donnot_use_coupon,rb_had_use_coupon;
    private CouponAdapter couponAdapter;
    private List<CouponListData> couponListDatas;
    ProgressHUD progressHUD = null;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_coupon);
        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VerifitionUtil.isNetworkAvailable(context)) {
                    loadError(true);
                } else {
                    loadError(false);
                    initData(2);
                }
            }
        });
    }

    @Override
    protected void initView() {
        super.setTitleText("我的优惠券");
        sv_coupon = (ScrollView) findViewById(R.id.sv_coupon);
        tv_no_coupon = (TextView) findViewById(R.id.tv_no_coupon);
        mlv_coupon = (MyListView) findViewById(R.id.mlv_coupon);
        rg_select_coupon_style = (RadioGroup) findViewById(R.id.rg_select_coupon_style);
        rb_can_use_coupon = (RadioButton) findViewById(R.id.rb_can_use_coupon);
        rb_donnot_use_coupon = (RadioButton) findViewById(R.id.rb_donnot_use_coupon);
        rb_had_use_coupon = (RadioButton) findViewById(R.id.rb_had_use_coupon);
        rg_select_coupon_style.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               switch (group.getCheckedRadioButtonId()){
                   case R.id.rb_can_use_coupon:
                       rb_can_use_coupon.setBackgroundResource(R.drawable.border_line_tab);
                       rb_donnot_use_coupon.setBackgroundResource(R.drawable.bg_dialog_btn);
                       rb_had_use_coupon.setBackgroundResource(R.drawable.bg_dialog_btn);
                       initData(2);
                       break;
                   case R.id.rb_donnot_use_coupon:
                       rb_donnot_use_coupon.setBackgroundResource(R.drawable.border_line_tab);
                       rb_can_use_coupon.setBackgroundResource(R.drawable.bg_dialog_btn);
                       rb_had_use_coupon.setBackgroundResource(R.drawable.bg_dialog_btn);
                       initData(1);
                       break;
                   case R.id.rb_had_use_coupon:
                       rb_had_use_coupon.setBackgroundResource(R.drawable.border_line_tab);
                       rb_can_use_coupon.setBackgroundResource(R.drawable.bg_dialog_btn);
                       rb_donnot_use_coupon.setBackgroundResource(R.drawable.bg_dialog_btn);
                       initData(3);
                       break;
               }
            }
        });
        initData(2);
    }

    private void initData(final int n) {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getUserCoupon(SharePreferencesUtils.getBtdToken(this),n,"CNY",new CallbackSupport<Coupon>(progressHUD, this) {
            @Override
            public void success(Coupon coupon, Response response) {
                progressHUD.dismiss();
                if (coupon.getStatus().equals("1")){
                    if (0==coupon.getData().getTotal()){
                        if(n==1){
                            tv_no_coupon.setText("没有已过期优惠券");
                        }else if(n==3){
                            tv_no_coupon.setText("没有已使用优惠券");
                        }else if(n==2){
                            tv_no_coupon.setText("您还没有优惠券");
                        }
                        sv_coupon.setVisibility(View.GONE);
                        tv_no_coupon.setVisibility(View.VISIBLE);
                    }else {
                        sv_coupon.setVisibility(View.VISIBLE);
                        tv_no_coupon.setVisibility(View.GONE);
                        couponListDatas = coupon.getData().getData();
                        couponAdapter = new CouponAdapter(couponListDatas,MyCouponActivity.this,n);
                        mlv_coupon.setAdapter(couponAdapter);
                    }
                }else {
                    ToastUtils.showToastCenter(MyCouponActivity.this,coupon.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
}
