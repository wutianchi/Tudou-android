package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.MyOrderDetailActivity;
import com.bentudou.westwinglife.activity.OrderDetailActivity;
import com.bentudou.westwinglife.activity.PayStyleSelectActivity;
import com.bentudou.westwinglife.activity.ShoppingCartActivity;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.bentudou.westwinglife.json.OrderListData;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by  天池 on 2015/7/30.
 * 订单列表适配器
 */
public class OrderListAdapter extends BaseAdapter {

    private List<MyOrderList> list;
    Context context;
    private LayoutInflater inflater;
    public OrderListAdapter(List<MyOrderList> list, Context context){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if(convertView==null){
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_father,null);
            myHolder.tv_order_number = (TextView)convertView.findViewById(R.id.tv_order_number);
            myHolder.tv_order_status = (TextView)convertView.findViewById(R.id.tv_order_status);
            myHolder.tv_order_time = (TextView)convertView.findViewById(R.id.tv_order_time);
            myHolder.tv_order_all_price = (TextView)convertView.findViewById(R.id.tv_order_all_price);
            myHolder.tv_buy_again = (TextView)convertView.findViewById(R.id.tv_buy_again);
            myHolder.mgv_goods = (MyGridView) convertView.findViewById(R.id.mgv_goods);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        final MyOrderList myOrderList = list.get(position);
        myHolder.tv_order_number.setText("订单号:"+myOrderList.getOrderSn());
        if (myOrderList.getOrderStatus()==1){
            if (myOrderList.getOrderPayStatus()==0){
                myHolder.tv_order_status.setText("待付款");
                myHolder.tv_buy_again.setText("去支付");
                myHolder.tv_buy_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, PayStyleSelectActivity.class).putExtra("orderId",myOrderList.getOrderId()));
                    }
                });
            }else {
                myHolder.tv_buy_again.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoOrder(myOrderList.getOrderGoodsList());
                    }
                });
                myHolder.tv_buy_again.setText("再次购买");
                switch (myOrderList.getOrderDeliveryStatus()){
                    case 0:
                        myHolder.tv_order_status.setText("未发货");
                        break;
                    case 1:
                        myHolder.tv_order_status.setText("已发货");
                        break;
                    case 2:
                        myHolder.tv_order_status.setText("已收货");
                        break;
                }
            }
        }else {
            myHolder.tv_buy_again.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gotoOrder(myOrderList.getOrderGoodsList());
                }
            });
            myHolder.tv_buy_again.setText("再次购买");
            switch (myOrderList.getOrderStatus()){
                case 0:
                    myHolder.tv_order_status.setText("已取消");
                    break;
                case 2:
                    myHolder.tv_order_status.setText("申请退款");
                    break;
                case 3:
                    myHolder.tv_order_status.setText("订单异常");
                    break;
                case 4:
                    myHolder.tv_order_status.setText("关闭状态");
                    break;
                case 5:
                    myHolder.tv_order_status.setText("已退货");
                    break;
            }
        }
        myHolder.tv_order_time.setText("下单时间: "+myOrderList.getOrderAddTime());
        if (myOrderList.getCurrency().equals("CNY")){
            myHolder.tv_order_all_price.setText("总价: "+VerifitionUtil.getRMBStringPrice(myOrderList.getOrderPayFee()));
        }else {
            myHolder.tv_order_all_price.setText("总价: "+VerifitionUtil.getStringPrice(myOrderList.getOrderPayFee()));
        }
        if (myHolder.mgv_goods!=null){
            OrderListGoodsAdapter orderListGoodsAdapter = new OrderListGoodsAdapter(myOrderList.getOrderGoodsList(),context);
            myHolder.mgv_goods.setAdapter(orderListGoodsAdapter);
        }
        myHolder.mgv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(context, MyOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order_detail_data", myOrderList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    public String getStringMap(List<OrderGoodsList> orderGoodsLists){
        StringBuffer stringBuffer = new StringBuffer();
       for (int i=0;i<orderGoodsLists.size();i++){
           stringBuffer.append(orderGoodsLists.get(i).getGoodsId()+"~"+orderGoodsLists.get(i).getGoodsNumber()+",");
       }
        return stringBuffer.toString();
    }

    private void gotoOrder(List<OrderGoodsList> orderGoodsLists) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.confirmOrderInfo(SharePreferencesUtils.getBtdToken(context),getStringMap(orderGoodsLists),"CNY", new CallbackSupport<OrderDetail>(context) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                if (orderDetail.getStatus().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(context, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_detail_back", orderDetail.getData());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }else{
                    if (orderDetail.getErrorCode().equals("51103")){
                        ToastUtils.showToastCenter(context,"价格发生变化,请确认!");
                    }else if (orderDetail.getErrorCode().equals("51105")||orderDetail.getErrorCode().equals("51106")){
                        ToastUtils.showToastCenter(context,"有异常商品,请确认!");
                    }else {
                        ToastUtils.showToastCenter(context,orderDetail.getErrorMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                ToastUtils.showToastCenter(context,"下单失败~");
            }
        });
    }


    static class MyHolder{
        TextView tv_order_number;//订单号
        TextView tv_order_status;//订单状态
        TextView tv_order_time;//订单创建时间
        TextView tv_order_all_price;//订单总价
        TextView tv_buy_again;//再次购买
        MyGridView mgv_goods;//本订单商品列表
    }

}
