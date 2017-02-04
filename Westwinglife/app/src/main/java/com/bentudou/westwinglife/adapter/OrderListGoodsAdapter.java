package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadGrid;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 我的订单商品适配器
 */
public class OrderListGoodsAdapter extends BaseAdapter {
    List<OrderGoodsList> list;
    Context context;
    public OrderListGoodsAdapter(List<OrderGoodsList> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list==null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list==null) {
            return null;
        } else {
            return list.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_grid_item,null);
            classHolder.tv_hot_class_name = (TextView)convertView.findViewById(R.id.tv_hot_class_name);
            classHolder.imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        OrderGoodsList orderGoodsList = this.list.get(position);
        classHolder.tv_hot_class_name.setText("X"+orderGoodsList.getGoodsNumber());
        final String imageUrl = Constant.URL_BASE_TEST+orderGoodsList.getGoodsImg();
//        final String imageUrl = "http://img.dev.costrun.cn"+orderGoodsList.getGoodsImg();
        if (classHolder.imageView!=null) {
            try {
                ImageLoader.getInstance().displayImage(imageUrl,classHolder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return convertView;

    }
    static class ClassHolder{
        TextView tv_hot_class_name;
        ImageView imageView;
    }

}
