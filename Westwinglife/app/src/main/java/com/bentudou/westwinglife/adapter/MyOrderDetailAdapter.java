package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.CouponInfo;
import com.bentudou.westwinglife.json.GetCoupon;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *Created by lzz on 2016/3/2.
 * 订单详情商品列表适配器
 */
public class MyOrderDetailAdapter extends BaseAdapter {
    Context context;
    private String currency;
    public MyOrderDetailAdapter( Context context,String currency) {
        this.context = context;
        this.currency = currency;
    }

    @Override
    public int getCount() {
        if (Constant.orderGoodsLists==null) {
            return 0;
        } else {
            return Constant.orderGoodsLists.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (Constant.orderGoodsLists==null) {
            return null;
        } else {
            return Constant.orderGoodsLists.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail,null);
            classHolder.iv_cart_img = (ImageView) convertView.findViewById(R.id.iv_cart_img);
            classHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            classHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            classHolder.tv_number = (TextView)convertView.findViewById(R.id.tv_number);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        final OrderGoodsList orderGoodsList = Constant.orderGoodsLists.get(position);
        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+orderGoodsList.getGoodsImg(),classHolder.iv_cart_img);
        classHolder.tv_name.setText(orderGoodsList.getGoodsCnName());
        if (currency.equals("CNY")){
            classHolder.tv_price.setText(VerifitionUtil.getRMBStringPrice(orderGoodsList.getGoodsPrice()));
        }else {
            classHolder.tv_price.setText(VerifitionUtil.getStringPrice(orderGoodsList.getGoodsPrice()));
        }
        classHolder.tv_number.setText("       x "+orderGoodsList.getGoodsNumber());
        return convertView;
    }
    static class ClassHolder{
        ImageView iv_cart_img;
        TextView tv_name,tv_price,tv_number;
    }

}
