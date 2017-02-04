package com.bentudou.westwinglife.adapter_r;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.ClassifyDatas;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by lzz on 2016/10/11.
 */
public class NewTwoFragmentAdapter extends BaseRecyclerAdapter<ClassifyDatas>{
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_one_class,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, ClassifyDatas data) {
        if(viewHolder instanceof MyViewHolder) {
            ((MyViewHolder) viewHolder).tv_one_class_name.setText(data.getCategoryCnName());
            ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+data.getCategoryImg(),((MyViewHolder) viewHolder).iv_one_class);
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_one_class;
        TextView tv_one_class_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_one_class = (ImageView) itemView.findViewById(R.id.iv_one_class);
            tv_one_class_name = (TextView) itemView.findViewById(R.id.tv_one_class_name);
        }
    }
}
