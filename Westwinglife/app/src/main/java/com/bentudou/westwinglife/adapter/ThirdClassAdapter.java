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
import com.bentudou.westwinglife.json.ClassifyThreeDatas;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 三级分类适配器
 */
public class ThirdClassAdapter extends BaseAdapter {
    List<ClassifyThreeDatas> list;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    public ThirdClassAdapter(List<ClassifyThreeDatas> list, LayoutInflater layoutInflater,ImageLoader imageLoader) {
        this.list = list;
        this.layoutInflater = layoutInflater;
        this.imageLoader = imageLoader;
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
            convertView = layoutInflater.inflate(R.layout.item_third_class_child,null);
            classHolder.tv_third_class_name = (TextView)convertView.findViewById(R.id.tv_third_class_name);
            classHolder.iv_third_class = (ImageView) convertView.findViewById(R.id.iv_third_class);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        ClassifyThreeDatas classifyThreeDatas = this.list.get(position);
        classHolder.tv_third_class_name.setText(classifyThreeDatas.getCategoryCnName());
        imageLoader.displayImage(Constant.URL_BASE_IMG+classifyThreeDatas.getCategoryImgPc()+Constant.IMG_200,classHolder.iv_third_class);

        return convertView;

    }
    static class ClassHolder{
        TextView tv_third_class_name;
        ImageView iv_third_class;
    }

}
