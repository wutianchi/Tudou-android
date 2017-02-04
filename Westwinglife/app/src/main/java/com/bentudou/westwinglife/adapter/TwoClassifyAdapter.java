package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bentudou.westwinglife.fragment.TwoFragment;
import com.bentudou.westwinglife.json.ClassifyDatas;
import com.bentudou.westwinglife.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Grace on 2015/10/21.
 */
public class TwoClassifyAdapter extends BaseAdapter {
    List<ClassifyDatas> list;
    private boolean isFirst = true;
    private Map<Integer,Integer> selected;
    private LayoutInflater layoutInflater;
    public TwoClassifyAdapter(List<ClassifyDatas> list,LayoutInflater layoutInflater) {
        this.list = list;
        this.layoutInflater = layoutInflater;
        selected = new HashMap<Integer,Integer>();
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
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = layoutInflater.inflate(R.layout.two_classify_item, null);
            holder.tv_two_classify_name = (TextView) convertView.findViewById(R.id.tv_two_classify_name);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (position== TwoFragment.last){
            holder.tv_two_classify_name.setSelected(true);
        }else {
            holder.tv_two_classify_name.setSelected(false);
        }
//        if (isFirst){
//            holder.tv_two_classify_name.setSelected(true);
//            isFirst = false;
//        }else {
//            holder.tv_two_classify_name.setSelected(false);
//        }
        holder.tv_two_classify_name.setText(list.get(position).getCategoryCnName());

        return convertView;
    }

    static class Holder{
        TextView tv_two_classify_name;
    }
}
