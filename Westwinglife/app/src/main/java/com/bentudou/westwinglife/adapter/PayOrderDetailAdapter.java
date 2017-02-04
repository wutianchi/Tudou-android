package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.bentudou.westwinglife.json.OrderListData;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by  天池 on 2015/7/30.
 * 订单支付适配器
 */
public class PayOrderDetailAdapter extends BaseAdapter {

    List<OrderGoodsList> list;
    Context context;
    private LayoutInflater inflater;
    public PayOrderDetailAdapter(List<OrderGoodsList> list, Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail,null);
            myHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            myHolder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            myHolder.tv_number = (TextView)convertView.findViewById(R.id.tv_number);
            myHolder.iv_cart_img = (ImageView) convertView.findViewById(R.id.iv_cart_img);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        OrderGoodsList orderListData = list.get(position);
        myHolder.tv_name.setText(orderListData.getGoodsCnName());
        myHolder.tv_number.setText("*"+orderListData.getGoodsNumber());
        myHolder.tv_price.setText(VerifitionUtil.getRMBStringPrice(orderListData.getGoodsPrice()));
        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+orderListData.getGoodsImg(),myHolder.iv_cart_img);
        return convertView;
    }


    static class MyHolder{
        TextView tv_name;
        TextView tv_price;
        TextView tv_number;
        ImageView iv_cart_img;
    }

}
