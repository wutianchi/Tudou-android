package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GoodsClassActivity;
import com.bentudou.westwinglife.activity.MyOrderDetailActivity;
import com.bentudou.westwinglife.activity.OrderDetailActivity;
import com.bentudou.westwinglife.activity.PayStyleSelectActivity;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.ClassifyDatas;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OrderGoodsList;
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
 * 二级分类列表适配器
 */
public class SecondClassAdapter extends BaseAdapter {

    private List<ClassifyDatas> list;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private Context context;
    public SecondClassAdapter(List<ClassifyDatas> list,Context context,LayoutInflater layoutInflater,ImageLoader imageLoader){
        this.list = list;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.imageLoader = imageLoader;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if(convertView==null){
            myHolder = new MyHolder();
            convertView = layoutInflater.inflate(R.layout.item_second_class_father,null);
            myHolder.tv_second_class_name = (TextView)convertView.findViewById(R.id.tv_second_class_name);
            myHolder.mgv_second_class_third = (MyGridView) convertView.findViewById(R.id.mgv_second_class_third);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        final ClassifyDatas myOrderList = list.get(position);
        myHolder.tv_second_class_name.setText("/"+myOrderList.getCategoryCnName()+"/");
        if (null!=myOrderList.getCategoryList()&&myOrderList.getCategoryList().size()>0){
            ThirdClassAdapter thirdClassAdapter = new ThirdClassAdapter(myOrderList.getCategoryList(),layoutInflater,imageLoader);
            myHolder.mgv_second_class_third.setAdapter(thirdClassAdapter);
        }
        myHolder.mgv_second_class_third.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                context.startActivity(new Intent(context, GoodsClassActivity.class)
                        .putExtra("categoryId", "" + myOrderList.getCategoryList().get(i).getCategoryId())
                        .putExtra("goods_class_name", myOrderList.getCategoryList().get(i).getCategoryCnName().toString()));
            }
        });
        return convertView;
    }
    static class MyHolder{
        TextView tv_second_class_name;//二级分类名称
        MyGridView mgv_second_class_third;//本订单商品列表
    }

}
