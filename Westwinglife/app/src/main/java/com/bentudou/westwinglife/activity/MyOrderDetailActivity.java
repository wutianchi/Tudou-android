package com.bentudou.westwinglife.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.MyOrderDetailAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/10.
 */
public class MyOrderDetailActivity extends BaseTitleActivity {
    private TextView tv_to_buy_again,tv_goods_all_price,tv_shuifei_price,
            tv_yunfei_price,tv_coupon_price,tv_pay_all_price,tv_my_order_number,
            tv_my_yundan_number,tv_user_name,tv_user_number,tv_user_address,
            tv_cancel_order,tv_pay_price;
    private ImageView iv_next_icon;
    private MyListView mlv_my_order_detail;
    private LinearLayout llt_more_goods;
    private MyOrderList myOrderList;
    private MyOrderDetailAdapter myOrderDetailAdapter;
    private List<OrderGoodsList> orderGoodsLists;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_order_detail);
    }

    @Override
    protected void initView() {
        super.setTitleText("订单详情");
        myOrderList = (MyOrderList) getIntent().getSerializableExtra("order_detail_data");
        tv_to_buy_again = (TextView) findViewById(R.id.tv_to_buy_again);
        tv_cancel_order = (TextView) findViewById(R.id.tv_cancel_order);
        tv_goods_all_price = (TextView) findViewById(R.id.tv_goods_all_price);
        tv_shuifei_price = (TextView) findViewById(R.id.tv_shuifei_price);
        tv_yunfei_price = (TextView) findViewById(R.id.tv_yunfei_price);
        tv_coupon_price = (TextView) findViewById(R.id.tv_coupon_price);
        tv_pay_all_price = (TextView) findViewById(R.id.tv_pay_all_price);
        tv_pay_price = (TextView) findViewById(R.id.tv_pay_price);
        tv_my_order_number = (TextView) findViewById(R.id.tv_my_order_number);
        tv_my_yundan_number = (TextView) findViewById(R.id.tv_my_yundan_number);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_number = (TextView) findViewById(R.id.tv_user_number);
        tv_user_address = (TextView) findViewById(R.id.tv_user_address);
        iv_next_icon = (ImageView) findViewById(R.id.iv_next_icon);
        llt_more_goods = (LinearLayout) findViewById(R.id.llt_more_goods);
        mlv_my_order_detail = (MyListView) findViewById(R.id.mlv_my_order_detail);
        initData();
        llt_more_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llt_more_goods.setVisibility(View.GONE);
                Constant.orderGoodsLists=orderGoodsLists;
                myOrderDetailAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        tv_user_name.setText("收件人: "+myOrderList.getCustomsUserName()+"   "+myOrderList.getCustomsMobile());
        tv_user_number.setText("身份证号: "+myOrderList.getCustomsIdCard());
        tv_user_address.setText("收货地址: "+myOrderList.getShippingProvinceCn()+myOrderList.getShippingCityCn()+myOrderList.getShippingAddress());
        tv_my_order_number.setText("订单号: "+myOrderList.getOrderSn());
        tv_my_order_number.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(myOrderList.getOrderSn());
                Toast.makeText(MyOrderDetailActivity.this, "订单号复制成功", Toast.LENGTH_LONG).show();
                return false;
            }
        });
        if (null!=myOrderList.getExpressSn()){
            iv_next_icon.setVisibility(View.VISIBLE);
            tv_my_yundan_number.setText("查看物流");
            tv_my_yundan_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyOrderDetailActivity.this, LogisticalMessageActivity.class)
                            .putExtra("express_sn",myOrderList.getExpressSn()));
//                    startActivity(new Intent(MyOrderDetailActivity.this, WebDetailActivity.class)
//                            .putExtra("web_url","http://www.westwingexpress.com/tracesimpleM.html?expressSn="+myOrderList.getExpressSn()+"&expressId=0").putExtra("link_name","物流详情"));
                }
            });
        }else {
            iv_next_icon.setVisibility(View.GONE);
            tv_my_yundan_number.setText("暂无物流");
        }
        if (myOrderList.getCurrency().equals("CNY")){
            if (null!=myOrderList.getGoodsCountFee())
                tv_goods_all_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getGoodsCountFee()));
            if (null!=myOrderList.getOrderTariffsFee())
                tv_shuifei_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getOrderTariffsFee()));
            if (null!=myOrderList.getOrderInvoiceFee())
                tv_yunfei_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getOrderInvoiceFee()));
            if (null!=myOrderList.getCouponPrice())
                tv_coupon_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getCouponPrice()));
            if (null!=myOrderList.getOrderAmountFee())
                tv_pay_all_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getOrderAmountFee()));
            if (null!=myOrderList.getOrderPayFee())
                tv_pay_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getOrderPayFee()));
        }else {
            if (null!=myOrderList.getGoodsCountFee())
                tv_goods_all_price.setText(VerifitionUtil.getStringPrice(myOrderList.getGoodsCountFee()));
            if (null!=myOrderList.getOrderTariffsFee())
                tv_shuifei_price.setText(VerifitionUtil.getStringPrice(myOrderList.getOrderTariffsFee()));
            if (null!=myOrderList.getOrderInvoiceFee())
                tv_yunfei_price.setText(VerifitionUtil.getStringPrice(myOrderList.getOrderInvoiceFee()));
            if (null!=myOrderList.getCouponPrice())
                tv_coupon_price.setText(VerifitionUtil.getStringPrice(myOrderList.getCouponPrice()));
            if (null!=myOrderList.getOrderAmountFee())
                tv_pay_all_price.setText(VerifitionUtil.getStringPrice(myOrderList.getOrderAmountFee()));
            if (null!=myOrderList.getOrderPayFee())
                tv_pay_price.setText(VerifitionUtil.getStringPrice(myOrderList.getOrderPayFee()));
        }

        if (myOrderList.getOrderStatus()==1){
            if (myOrderList.getOrderPayStatus()==0){
                tv_to_buy_again.setText("去支付");
                tv_cancel_order.setVisibility(View.VISIBLE);
                tv_to_buy_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, PayStyleSelectActivity.class).putExtra("orderId",myOrderList.getOrderId()));
               finish();
                    }
                });
                tv_cancel_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消订单
                        showCancleOrder(myOrderList.getOrderId());
                    }
                });
            }else {
                tv_to_buy_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoOrder(myOrderList.getOrderGoodsList());
                    }
                });
                tv_to_buy_again.setText("再次购买");
            }
        }else {
            tv_to_buy_again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoOrder(myOrderList.getOrderGoodsList());
                }
            });
            tv_to_buy_again.setText("再次购买");
        }
        orderGoodsLists = myOrderList.getOrderGoodsList();
        if (orderGoodsLists.size()<=3){
            llt_more_goods.setVisibility(View.GONE);
            Constant.orderGoodsLists=orderGoodsLists;
        }else {
            llt_more_goods.setVisibility(View.VISIBLE);
            Constant.orderGoodsLists=threeList(orderGoodsLists);
        }
        myOrderDetailAdapter = new MyOrderDetailAdapter(MyOrderDetailActivity.this,myOrderList.getCurrency());
        mlv_my_order_detail.setAdapter(myOrderDetailAdapter);
        mlv_my_order_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MyOrderDetailActivity.this,GoodsDetailActivity.class).putExtra("goodsId",String.valueOf(orderGoodsLists.get(position).getGoodsId())));
            }
        });
    }
    //取消订单接口
    private void cancelTheOrder(int orderId) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.cannlOrder(SharePreferencesUtils.getBtdToken(this),orderId, new CallbackSupport<Success>(this) {
            @Override
            public void success(Success orderDetail, Response response) {
                if (orderDetail.getStatus().equals("1")){
                        finish();
                }else{
                    ToastUtils.showToastCenter(MyOrderDetailActivity.this,orderDetail.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                ToastUtils.showToastCenter(MyOrderDetailActivity.this,"取消订单失败~");
            }
        });
    }
    //再次购买接口
    private void gotoOrder(List<OrderGoodsList> orderGoodsLists) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.confirmOrderInfo(SharePreferencesUtils.getBtdToken(this),getStringMap(orderGoodsLists),"CNY", new CallbackSupport<OrderDetail>(this) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                if (orderDetail.getStatus().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(MyOrderDetailActivity.this, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_detail_back", orderDetail.getData());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    if (orderDetail.getErrorCode().equals("51103")){
                        ToastUtils.showToastCenter(MyOrderDetailActivity.this,"价格发生变化,请确认!");
                    }else if (orderDetail.getErrorCode().equals("51105")||orderDetail.getErrorCode().equals("51106")){
                        ToastUtils.showToastCenter(MyOrderDetailActivity.this,"有异常商品,请确认!");
                    }else {
                        ToastUtils.showToastCenter(MyOrderDetailActivity.this,orderDetail.getErrorMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                ToastUtils.showToastCenter(MyOrderDetailActivity.this,"下单失败~");
            }
        });
    }

    public String getStringMap(List<OrderGoodsList> orderGoodsLists){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<orderGoodsLists.size();i++){
            stringBuffer.append(orderGoodsLists.get(i).getGoodsId()+"~"+orderGoodsLists.get(i).getGoodsNumber()+",");
        }
        return stringBuffer.toString();
    }

    public List<OrderGoodsList> threeList(List<OrderGoodsList> orderGoodsLists){
        List<OrderGoodsList> orderGoodsListList = new ArrayList<>();
        for (int i=0;i<=2;i++){
            orderGoodsListList.add(orderGoodsLists.get(i));
        }
        return orderGoodsListList;
    }
    private void showCancleOrder(final int orderId) {
        View layout = getLayoutInflater().inflate(R.layout.dialog_cancel_order,
                null);
        TextView cancelGo = (TextView) layout.findViewById(R.id.cancel_go);
        TextView sureNoGo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        /**监听对话框里面的button点击事件*/
        sureNoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        cancelGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTheOrder(orderId);
                dialog.dismiss();
            }
        });
    }
}
