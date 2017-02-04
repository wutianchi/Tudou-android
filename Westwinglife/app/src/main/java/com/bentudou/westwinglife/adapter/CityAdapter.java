package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.ShowCityActivity;
import com.bentudou.westwinglife.json.City;
import com.bentudou.westwinglife.json.Datas;


import java.util.List;

import retrofit.Callback;

/**
 * Created by yaoguang on 2016/6/29.
 */
public class CityAdapter extends BaseAdapter {
    private Context context;
    private List<Datas> citylist;
    private int mSelect = -1;   //选中项
    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }
    public CityAdapter(Context context, List<Datas> citylist) {
        this.citylist=citylist;
        this.context=context;
    }
    @Override
    public int getCount() {
        return citylist.size();
    }

    @Override
    public Object getItem(int position) {
        return citylist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if(convertView==null){
            viewholder=new ViewHolder();
            convertView=View.inflate(context, R.layout.show_city_tv,null);
            viewholder.city_tv= (TextView) convertView.findViewById(R.id.city_tv);
            convertView.setTag(viewholder);
        }else{
            viewholder= (ViewHolder) convertView.getTag();
        }
        viewholder.city_tv.setText(citylist.get(position).getNameZh());
        if(mSelect==position){
            viewholder.city_tv.setTextColor(Color.parseColor("#08ab51"));  //选中项背景
        }else{
            viewholder.city_tv.setTextColor(Color.GRAY);  //其他项背景
        }
        return convertView;
    }
    static class ViewHolder {
        TextView city_tv;
    }
}
