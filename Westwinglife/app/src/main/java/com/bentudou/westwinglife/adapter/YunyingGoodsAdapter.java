package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 运营位横向商品适配器
 */
public class YunyingGoodsAdapter extends BaseAdapter {
    List<HeadList> list;
    Context context;
    private ImageLoader mImageLoader;
    public YunyingGoodsAdapter(List<HeadList> list, Context context,ImageLoader mImageLoader) {
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
        Log.d("position", "getView:------ "+position);
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_yunying_goods,null);
            classHolder.tv_goods_content = (TextView)convertView.findViewById(R.id.tv_goods_content);
            classHolder.tv_goods_price = (TextView)convertView.findViewById(R.id.tv_goods_price);
            classHolder.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
            classHolder.img_top_left = (ImageView) convertView.findViewById(R.id.img_top_left);
            classHolder.img_top_right = (ImageView) convertView.findViewById(R.id.img_top_right);
            classHolder.img_bottom_left = (ImageView) convertView.findViewById(R.id.img_bottom_left);
            classHolder.img_bottom_right = (ImageView) convertView.findViewById(R.id.img_bottom_right);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        HeadList headList = this.list.get(position);
//        if (position<8){
            mImageLoader.displayImage(Constant.URL_BASE_TEST+headList.getGoodsImg(),classHolder.iv_goods);
            classHolder.tv_goods_content.setText(headList.getGoodsCnName());
            classHolder.tv_goods_price.setText(VerifitionUtil.getRMBStringPrice(headList.getShopPriceCny()));
            if (null!=headList.getTopLeftCornerMarkImg()&&!headList.getTopLeftCornerMarkImg().isEmpty()){
                classHolder.img_top_left.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getTopLeftCornerMarkImg(),classHolder.img_top_left);
            }else {classHolder.img_top_left.setVisibility(View.GONE);}
            if (null!=headList.getTopRightCornerMarkImg()&&!headList.getTopRightCornerMarkImg().isEmpty())
                mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getTopRightCornerMarkImg(),classHolder.img_top_right);
            if (null!=headList.getBottomLeftCornerMarkImg()&&!headList.getBottomLeftCornerMarkImg().isEmpty())
                mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getBottomLeftCornerMarkImg(),classHolder.img_bottom_left);
            if (null!=headList.getBottomRightCornerMarkImg()&&!headList.getBottomRightCornerMarkImg().isEmpty())
                mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getBottomRightCornerMarkImg(),classHolder.img_bottom_right);
//        }else if (position==8){
//            classHolder.iv_goods.setImageResource(R.drawable.gouwuche_lv);
//        }

        return convertView;

    }
    static class ClassHolder{
        TextView tv_goods_content,tv_goods_price;
        ImageView iv_goods,img_top_left,img_top_right,img_bottom_left,img_bottom_right;
    }

}
