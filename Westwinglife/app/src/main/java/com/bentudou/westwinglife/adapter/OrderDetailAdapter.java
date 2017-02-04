package com.bentudou.westwinglife.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.fragment.ThreeFragment;
import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.CartDataList;
import com.bentudou.westwinglife.json.CartOrderInfo;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.OrderListData;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by  天池 on 2015/7/30.
 * 订单预览适配器
 */
public class OrderDetailAdapter extends BaseAdapter {

    List<OrderListData> list;
    Context context;
    private LayoutInflater inflater;
    public OrderDetailAdapter(List<OrderListData> list, Context context){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.order_detail_father,null);
            myHolder.tv_self_goods_price = (TextView)convertView.findViewById(R.id.tv_self_goods_price);
            myHolder.tv_self_custom = (TextView)convertView.findViewById(R.id.tv_self_custom);
            myHolder.tv_ship_ment_fee = (TextView)convertView.findViewById(R.id.tv_ship_ment_fee);
            myHolder.tv_self_all_price = (TextView)convertView.findViewById(R.id.tv_self_all_price);
            myHolder.llt_goods = (LinearLayout) convertView.findViewById(R.id.llt_goods);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        OrderListData orderListData = list.get(position);
        myHolder.llt_goods.removeAllViews();
        for (int i=0;i<orderListData.getGoodsList().size();i++){
            ChildHolder childHolder = new ChildHolder();
            View itemview = LayoutInflater.from(context).inflate(R.layout.item_order_detail,null);
            itemview.setId(i);
            myHolder.llt_goods.addView(itemview);
            HeadList headList =orderListData.getGoodsList().get(i);
            childHolder.tv_name = (TextView) itemview.findViewById(R.id.tv_name);
            childHolder.tv_price = (TextView) itemview.findViewById(R.id.tv_price);
            childHolder.tv_number = (TextView) itemview.findViewById(R.id.tv_number);
            childHolder.iv_cart_img = (ImageView) itemview.findViewById(R.id.iv_cart_img);
            itemview.setTag(childHolder);
            childHolder.tv_name.setText(headList.getGoodsCnName());
            childHolder.tv_number.setText("*"+headList.getGoodsNumber());
            childHolder.tv_price.setText(VerifitionUtil.getRMBStringPrice(headList.getShopPriceCny()));
            ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+headList.getGoodsImg(),childHolder.iv_cart_img);
        }
        myHolder.tv_self_goods_price.setText(VerifitionUtil.getRMBStringPrice(orderListData.getDepotSumGoods()));
        myHolder.tv_self_custom.setText(VerifitionUtil.getRMBStringPrice(orderListData.getDepotCustomsDuties()));
        myHolder.tv_ship_ment_fee.setText(VerifitionUtil.getRMBStringPrice(orderListData.getShipMentFee()));
        myHolder.tv_self_all_price.setText(VerifitionUtil.getRMBStringPrice(orderListData.getDepotSum()));
        return convertView;
    }


    static class MyHolder{
        TextView tv_self_goods_price;//本仓库商品价格
        TextView tv_self_custom;//本仓库商品税费
        TextView tv_ship_ment_fee;//本仓库商品税费
        TextView tv_self_all_price;//本仓库合计
        LinearLayout llt_goods;//本仓库商品列表
    }
    static class ChildHolder{
        TextView tv_name;
        TextView tv_price;
        TextView tv_number;
        ImageView iv_cart_img;
    }

}
