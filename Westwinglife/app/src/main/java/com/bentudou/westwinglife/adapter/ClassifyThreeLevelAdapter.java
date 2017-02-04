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
import com.bentudou.westwinglife.json.HeadGrid;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bentudou.westwinglife.view.ImageWidthHeight;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 分类三级适配器
 */
public class ClassifyThreeLevelAdapter extends BaseAdapter {
    List<HeadGrid> list;
    Context context;
    int mScreentWidth;
    private ImageLoader mImageLoader;
    public ClassifyThreeLevelAdapter(List<HeadGrid> list, Context context, ImageLoader mImageLoader) {
        this.list = list;
        this.context = context;
        this.mImageLoader = mImageLoader;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.headgrid_item,null);
            classHolder.tv_hot_class_name = (TextView)convertView.findViewById(R.id.tv_hot_class_name);
            classHolder.imageView = (ImageView) convertView.findViewById(R.id.grid_item_image);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        classHolder.tv_hot_class_name.setText(list.get(position).getCategoryCnName());

        if(this.list!=null){
            HeadGrid headGrid = this.list.get(position);
            int width = ImageWidthHeight.getItemWidth(context, mScreentWidth, 0);
            double mWidth = width/3.5;
            double height = (mWidth)/1.66567164;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)mWidth,(int)height);
            if (position<=4)
                params.setMargins(0,((int)mWidth/5)- DensityUtils.dip2px(context,5),0,0);
            else
                params.setMargins(0, 0, 0, ((int)mWidth/5)-DensityUtils.dip2px(context,5));
            classHolder.imageView.setLayoutParams(params);
            final String imageUrl = Constant.URL_BASE_IMG+headGrid.getCategoryIcon();
            if (classHolder.imageView!=null) {
                try {

                    mImageLoader.displayImage(imageUrl,classHolder.imageView);
//                    classHolder.imageView = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        return convertView;

    }
    static class ClassHolder{
        TextView tv_hot_class_name;
        ImageView imageView;
    }

}
