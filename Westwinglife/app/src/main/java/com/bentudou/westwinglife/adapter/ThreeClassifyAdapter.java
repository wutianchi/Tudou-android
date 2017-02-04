package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.ClassifyThreeDatas;
import com.bentudou.westwinglife.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Grace on 2015/10/21.
 */
public class ThreeClassifyAdapter extends BaseAdapter {
    List<ClassifyThreeDatas> list;
    private ImageLoader mImageLoader;
    private LayoutInflater layoutInflater;
    public ThreeClassifyAdapter(List<ClassifyThreeDatas> list,ImageLoader mImageLoader,LayoutInflater layoutInflater) {
        this.list = list;
        this.mImageLoader = mImageLoader;
        this.layoutInflater = layoutInflater;
    }

    public void setList(List<ClassifyThreeDatas> list){
        this.list = list;
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
            convertView = layoutInflater.inflate(R.layout.three_classify_item, null);
            holder.tv_three_classify_name = (TextView) convertView.findViewById(R.id.tv_three_classify_name);
            holder.iv_three_classify_img = (ImageView) convertView.findViewById(R.id.iv_three_classify_img);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tv_three_classify_name.setText(list.get(position).getCategoryCnName());
//        ImageLoader.getInstance().displayImage(Constant.URL_BASE_TEST+list.get(position).getCategoryIcon(),holder.iv_three_classify_img);
        mImageLoader.displayImage(Constant.URL_BASE_IMG+list.get(position).getCategoryImgPc()+Constant.IMG_200,holder.iv_three_classify_img);
        return convertView;
    }

    static class Holder{
        TextView tv_three_classify_name;
        ImageView iv_three_classify_img;
    }
}
