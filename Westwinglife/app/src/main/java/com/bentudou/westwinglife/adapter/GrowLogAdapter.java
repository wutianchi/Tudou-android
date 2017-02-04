package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.json.CouponListData;
import com.bentudou.westwinglife.json.GrowInfo;
import com.bentudou.westwinglife.utils.VerifitionUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 成长记录适配器
 */
public class GrowLogAdapter extends BaseAdapter {
    List<GrowInfo> list;
    Context context;
    public GrowLogAdapter(List<GrowInfo> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grow,null);
            classHolder.tv_grow_content = (TextView)convertView.findViewById(R.id.tv_grow_content);
            classHolder.tv_grow_time = (TextView)convertView.findViewById(R.id.tv_grow_time);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }

        classHolder.tv_grow_content.setText(list.get(position).getGrowthDesc());
        String time = list.get(position).getAddTime().substring(0,10);
        classHolder.tv_grow_time.setText(time);
        return convertView;
    }
    static class ClassHolder{
        TextView tv_grow_time,tv_grow_content;
    }

}
