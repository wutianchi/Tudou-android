package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.AddressAdapter;
import com.bentudou.westwinglife.adapter.PayOrderDetailAdapter;
import com.bentudou.westwinglife.alipay.PayResult;
import com.bentudou.westwinglife.alipay.PayUtils;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Address;
import com.bentudou.westwinglife.json.CouponListData;
import com.bentudou.westwinglife.json.LastPayInfo;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.json.PayInfo;
import com.bentudou.westwinglife.json.PayInfoBack;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyListView;
import com.gunlei.app.ui.view.ProgressHUD;

import java.io.Serializable;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/29.
 */
public class PayStyleSelectActivity extends BaseTitleActivity implements View.OnClickListener {
    private LinearLayout llt_select_coupon;
    private ImageView iv_jianytou;
    private TextView tv_can_use_coupon,tv_isuse_coupon,tv_pay_order,tv_order_number,
            tv_pay_goods_price,tv_pay_custom,tv_ship_ment_fee,order_coupon_fee_pay,tv_pay_all_price;
    private MyListView mlv_pay_order_detail;
    private int orderId;
//    private RadioGroup rgp_paystyle;
//    private RadioButton rbtn_alipay;
    private PayOrderDetailAdapter payOrderDetailAdapter;
    ProgressHUD progressHUD = null;
    private PayInfoBack payInfoBack;
    private CouponListData couponListData;
    private int useCoupon=0;
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    Log.d("resultStatus", "handleMessage:----- "+resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.showToastCenter(PayStyleSelectActivity.this, "支付成功");
                    finish();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showToastCenter(PayStyleSelectActivity.this, "支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.showToastCenter(PayStyleSelectActivity.this, "支付失败");

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    ToastUtils.showToastCenter(PayStyleSelectActivity.this, "检查结果为：" + msg.obj);
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_pay_style);
        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VerifitionUtil.isNetworkAvailable(context)) {
                    loadError(true);
                } else {
                    loadError(false);
                    initData();
                }
            }
        });
    }

    @Override
    protected void initView() {
        super.setTitleText("等待支付");
        orderId = getIntent().getIntExtra("orderId",0);
        llt_select_coupon = (LinearLayout) findViewById(R.id.llt_select_coupon);
//        rgp_paystyle = (RadioGroup) findViewById(R.id.rgp_paystyle);
//        rbtn_alipay = (RadioButton) findViewById(R.id.rbtn_alipay);
        tv_can_use_coupon = (TextView) findViewById(R.id.tv_can_use_coupon);
        tv_isuse_coupon = (TextView) findViewById(R.id.tv_isuse_coupon);
        tv_pay_order = (TextView) findViewById(R.id.tv_pay_order);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        tv_pay_goods_price = (TextView) findViewById(R.id.tv_pay_goods_price);
        tv_pay_custom = (TextView) findViewById(R.id.tv_pay_custom);
        tv_ship_ment_fee = (TextView) findViewById(R.id.tv_ship_ment_fee);
        order_coupon_fee_pay = (TextView) findViewById(R.id.order_coupon_fee_pay);
        tv_pay_all_price = (TextView) findViewById(R.id.tv_pay_all_price);
        iv_jianytou = (ImageView) findViewById(R.id.iv_jianytou);
        mlv_pay_order_detail = (MyListView) findViewById(R.id.mlv_pay_order_detail);
        tv_pay_order.setOnClickListener(this);
        initData();
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.gotoPayOrder(SharePreferencesUtils.getBtdToken(this),orderId,"CNY",new CallbackSupport<PayInfo>(progressHUD, this) {
            @Override
            public void success(PayInfo address, Response response) {
                progressHUD.dismiss();
                if (address.getStatus().equals("1")){
                    payInfoBack = address.getData();
                    if (payInfoBack.isUsedCoupon()){
                        iv_jianytou.setVisibility(View.GONE);
                        tv_can_use_coupon.setText("已绑定优惠券");
                        llt_select_coupon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                        if (payInfoBack.getUsersCoupon().get(0).getCouponType().equals("00")){
                            tv_isuse_coupon.setText("-"+VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getCouponPrice()));
                        }else {
                            tv_isuse_coupon.setText("免邮");
                        }

                    }else {
                        if (null==payInfoBack.getUsersCoupon()||payInfoBack.getUsersCoupon().size()==0){
                            tv_can_use_coupon.setText("0张可用");
                            tv_isuse_coupon.setText("");
                            iv_jianytou.setVisibility(View.GONE);
                            llt_select_coupon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            });
                        }else {
                            tv_can_use_coupon.setText(payInfoBack.getUsersCoupon().size()+"张可用");
                            llt_select_coupon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent();
                                    intent.setClass(PayStyleSelectActivity.this, SelectCouponActivity.class);
                                    intent.putExtra("coupon_list",(Serializable)payInfoBack.getUsersCoupon());
                                    startActivityForResult(intent,1);
                                }
                            });
                        }
                    }

                    tv_order_number.setText("订单号: "+payInfoBack.getOrder().getOrderSn());
                    tv_pay_goods_price.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getGoodsCountFee()));
                    tv_pay_custom.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getOrderTariffsFee()));
                    tv_ship_ment_fee.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getOrderInvoiceFee()));
                    order_coupon_fee_pay.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getCouponPrice()));
                    tv_pay_all_price.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getOrderPayFee()));
                    payOrderDetailAdapter = new PayOrderDetailAdapter(address.getData().getOrder().getOrderGoodsList(),PayStyleSelectActivity.this);
                    mlv_pay_order_detail.setAdapter(payOrderDetailAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pay_order:
//                RadioButton selectedrbtn=(RadioButton)findViewById(rgp_paystyle.getCheckedRadioButtonId());
//                if (rbtn_alipay==selectedrbtn){
//                    ToastUtils.showToastCenter(this,"支付宝支付");
//                }else {
//                    ToastUtils.showToastCenter(this,"微信支付");
//                }

                preparePay();
                break;
        }
    }

    private void preparePay() {
        if (useCoupon==0){
            //不使用优惠券
            noUseCoupon();
        }else {
            //使用优惠券
            useCoupon();
        }
    }



    private void toPay() {
        PayUtils.pay(PayStyleSelectActivity.this, mHandler, "0.01");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                couponListData = (CouponListData) data.getSerializableExtra("order_coupon");
                if (couponListData.getCouponId()==0){
                    ToastUtils.showToastCenter(PayStyleSelectActivity.this,"没有使用优惠券");
                    tv_isuse_coupon.setText("未使用");
                    order_coupon_fee_pay.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getCouponPrice()));
                    tv_pay_all_price.setText(VerifitionUtil.getRMBStringPrice(payInfoBack.getOrder().getOrderPayFee()));
                }else {
                    useCoupon = couponListData.getCouponId();
                    if (VerifitionUtil.getStringZhengPrice(couponListData.getCouponPrice()).equals("0")){
                        tv_isuse_coupon.setText("免邮");
                        order_coupon_fee_pay.setText(VerifitionUtil.getDoubleRMBStringPrice(payInfoBack.getOrder().getOrderInvoiceFee().doubleValue()));
                        tv_pay_all_price.setText(VerifitionUtil.getDoubleRMBStringPrice(payInfoBack.getOrder().getOrderPayFee().doubleValue()-payInfoBack.getOrder().getOrderInvoiceFee().doubleValue()));
                    }else {
                        tv_isuse_coupon.setText("-"+VerifitionUtil.getRMBStringPrice(couponListData.getCouponPrice()));
                        order_coupon_fee_pay.setText(VerifitionUtil.getRMBStringPrice(couponListData.getCouponPrice()));
                        tv_pay_all_price.setText(VerifitionUtil.getDoubleRMBStringPrice(payInfoBack.getOrder().getOrderPayFee().doubleValue()-couponListData.getCouponPrice().doubleValue()));
                    }
                }

                break;
            default:
                Log.d("addressList", "onActivityResult: -----no"+requestCode);
                break;
        }
    }

    private void useCoupon() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.payOrder(SharePreferencesUtils.getBtdToken(this),orderId,useCoupon,new CallbackSupport<LastPayInfo>( this) {
            @Override
            public void success(LastPayInfo address, Response response) {
                if (address.getStatus().equals("1")){
                    //这里禁用选择优惠券
                    iv_jianytou.setVisibility(View.GONE);
                    llt_select_coupon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    toGetPayInfo(address.getData().getOrderId());
//                    PayUtils.pay(PayStyleSelectActivity.this, mHandler,VerifitionUtil.getDollarStringPrice(address.getData().getOrderPayFee()),address.getData().getOrderSn());
                }else {
                    ToastUtils.showToastCenter(PayStyleSelectActivity.this,address.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
    private void noUseCoupon() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.payOrder(SharePreferencesUtils.getBtdToken(this),orderId,new CallbackSupport<LastPayInfo>( this) {
            @Override
            public void success(LastPayInfo address, Response response) {
                if (address.getStatus().equals("1")){
                    //这里禁用选择优惠券
                    iv_jianytou.setVisibility(View.GONE);
                    llt_select_coupon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    toGetPayInfo(address.getData().getOrderId());
//                    PayUtils.pay(PayStyleSelectActivity.this, mHandler,VerifitionUtil.getDollarStringPrice(address.getData().getOrderPayFee()), address.getData().getOrderSn());
                }else {
                    ToastUtils.showToastCenter(PayStyleSelectActivity.this,address.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void toGetPayInfo(int orderId){
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.globalAlipay(SharePreferencesUtils.getBtdToken(this),orderId,new CallbackSupport<Success>( this) {
            @Override
            public void success(Success address, Response response) {
                if (address.getStatus().equals("1")){
                    PayUtils.pay(PayStyleSelectActivity.this, mHandler,address.getData());
                }else {
                    ToastUtils.showToastCenter(PayStyleSelectActivity.this,address.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
}
