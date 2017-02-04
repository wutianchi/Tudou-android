package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by lzz on 2016/12/28.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>{
    /**
     * ItemClick的回调接口
     * @author zhy
     *
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    private LayoutInflater mInflater;
    private ImageLoader mImageLoader;
    List<HeadList> list;
    Context context;
    public GalleryAdapter(Context context, List<HeadList> datats,ImageLoader mImageLoader)
    {   this.context = context;
        this.mImageLoader = mImageLoader;
        mInflater = LayoutInflater.from(context);
        list = datats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView iv_goods;
        TextView tv_goods_content,tv_goods_price;
        ImageView img_top_left,img_top_right,img_bottom_left,img_bottom_right;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_yunying_goods,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tv_goods_content = (TextView)view.findViewById(R.id.tv_goods_content);
        viewHolder.tv_goods_price = (TextView)view.findViewById(R.id.tv_goods_price);
        viewHolder.iv_goods = (ImageView) view.findViewById(R.id.iv_goods);
        viewHolder.img_top_left = (ImageView) view.findViewById(R.id.img_top_left);
        viewHolder.img_top_right = (ImageView) view.findViewById(R.id.img_top_right);
        viewHolder.img_bottom_left = (ImageView) view.findViewById(R.id.img_bottom_left);
        viewHolder.img_bottom_right = (ImageView) view.findViewById(R.id.img_bottom_right);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (position<8) {
            HeadList headList = list.get(position);
            Glide.with(context).load(Constant.URL_BASE_TEST + headList.getGoodsImg()).into(holder.iv_goods);
            holder.tv_goods_content.setText(headList.getGoodsCnName());
            holder.tv_goods_price.setText(VerifitionUtil.getRMBStringPrice(headList.getShopPriceCny()));
            if (null != headList.getTopLeftCornerMarkImg() && !headList.getTopLeftCornerMarkImg().isEmpty()) {
                holder.img_top_left.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG + headList.getTopLeftCornerMarkImg(), holder.img_top_left);
            } else {
                holder.img_top_left.setVisibility(View.GONE);
            }
            if (null != headList.getTopRightCornerMarkImg() && !headList.getTopRightCornerMarkImg().isEmpty()){
                holder.img_top_right.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG + headList.getTopRightCornerMarkImg(), holder.img_top_right);
            }else {
                holder.img_top_right.setVisibility(View.GONE);
            }

            if (null != headList.getBottomLeftCornerMarkImg() && !headList.getBottomLeftCornerMarkImg().isEmpty()){
                holder.img_bottom_left.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG + headList.getBottomLeftCornerMarkImg(), holder.img_bottom_left);
            }else {
                holder.img_bottom_left.setVisibility(View.GONE);
            }
            if (null != headList.getBottomRightCornerMarkImg() && !headList.getBottomRightCornerMarkImg().isEmpty()){
                holder.img_bottom_right.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG + headList.getBottomRightCornerMarkImg(), holder.img_bottom_right);
            }else {
                holder.img_bottom_right.setVisibility(View.GONE);
            }
        }else {
            holder.iv_goods.setImageResource(R.drawable.more_goods);
            holder.tv_goods_content.setText("");
            holder.tv_goods_price.setText("");
            holder.img_top_left.setVisibility(View.GONE);
            holder.img_top_right.setVisibility(View.GONE);
            holder.img_bottom_left.setVisibility(View.GONE);
            holder.img_bottom_right.setVisibility(View.GONE);
        }
    if (mOnItemClickLitener!=null){
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemClick(holder.itemView,position);
            }
        });
    }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
