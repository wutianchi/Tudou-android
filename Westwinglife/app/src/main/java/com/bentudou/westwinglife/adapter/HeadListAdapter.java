
package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.ImageWidthHeight;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 尖货适配器
 */
public class HeadListAdapter extends BaseAdapter{
    List<HeadList> list;
    Context context;
    int mScreentWidth=0;


    private ImageLoader mImageLoader;
    public HeadListAdapter(List<HeadList> list, Context context, ImageLoader mImageLoader) {
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        Holder h;
        if(convertView==null){
            h = new Holder();
            convertView = LayoutInflater.from(context).inflate(R.layout.headlist_item,null);
            h.tv_goods_name = (TextView)convertView.findViewById(R.id.tv_goods_name);
            h.tv_shop_price = (TextView)convertView.findViewById(R.id.tv_shop_price);
            h.tv_market_price = (TextView)convertView.findViewById(R.id.tv_market_price);
            h.image = (ImageView)convertView.findViewById(R.id.headspicallist_img);
            h.iv_flag = (ImageView)convertView.findViewById(R.id.iv_flag);
            h.img_top_left_corner_mark = (ImageView)convertView.findViewById(R.id.img_top_left_corner_mark);
            h.img_top_right_corner_mark = (ImageView)convertView.findViewById(R.id.img_top_right_corner_mark);
            h.img_bottom_left_corner_mark = (ImageView)convertView.findViewById(R.id.img_bottom_left_corner_mark);
            h.img_bottom_right_corner_mark = (ImageView)convertView.findViewById(R.id.img_bottom_right_corner_mark);
            convertView.setTag(h);
        }else{
            h = (Holder)convertView.getTag();

        }
        HeadList headList = list.get(position);
        h.tv_goods_name.setText(headList.getGoodsCnName());
        h.tv_shop_price.setText( VerifitionUtil.getRMBStringPrice(headList.getShopPriceCny()));
        h.tv_market_price.setText(headList.getDepotName()+"直邮");
//        int width = ImageWidthHeight.getItemWidth(context,mScreentWidth,0)/2;
//        double height = width/1.39;
//        h.image.setLayoutParams(new LinearLayout.LayoutParams(width, (int)height));
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getGoodsImg()+Constant.IMG_400,h.image);
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getDepotIcon(),h.iv_flag);
        if (null!=headList.getTopLeftCornerMarkImg()&&!headList.getTopLeftCornerMarkImg().isEmpty())
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getTopLeftCornerMarkImg(),h.img_top_left_corner_mark);
        if (null!=headList.getTopRightCornerMarkImg()&&!headList.getTopRightCornerMarkImg().isEmpty())
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getTopRightCornerMarkImg(),h.img_top_right_corner_mark);
        if (null!=headList.getBottomLeftCornerMarkImg()&&!headList.getBottomLeftCornerMarkImg().isEmpty())
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getBottomLeftCornerMarkImg(),h.img_bottom_left_corner_mark);
        if (null!=headList.getBottomRightCornerMarkImg()&&!headList.getBottomRightCornerMarkImg().isEmpty())
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getBottomRightCornerMarkImg(),h.img_bottom_right_corner_mark);
        return convertView;
    }
    static class Holder{
        TextView tv_goods_name,tv_shop_price,tv_market_price;
        ImageView image,iv_flag,img_top_left_corner_mark,img_top_right_corner_mark,
                img_bottom_left_corner_mark,img_bottom_right_corner_mark;
    }
}
