package com.bentudou.westwinglife.adapter_r;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.json.ClassifyDatas;
import com.bentudou.westwinglife.json.HeadList;

import java.util.List;

/**
 * Created by lzz on 2016/10/11.
 */
public class OneClassAdapter extends RecyclerView.Adapter<OneClassAdapter.MyViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    List<ClassifyDatas> list;
    private Context context;
    public OneClassAdapter(Context context,List<ClassifyDatas> list){
        this.context = context;
        this.list = list;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_one_class,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv_one_class_name.setText(list.get(position).getCategoryCnName());
        if (mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_one_class_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_one_class_name = (TextView) itemView.findViewById(R.id.tv_one_class_name);
        }
    }
}
