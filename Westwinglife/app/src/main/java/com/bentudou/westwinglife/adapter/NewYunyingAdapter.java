package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GetCouponActivity;
import com.bentudou.westwinglife.activity.GoodsClassActivity;
import com.bentudou.westwinglife.activity.GoodsDetailActivity;
import com.bentudou.westwinglife.activity.OverseasLiveActivity;
import com.bentudou.westwinglife.activity.WebDetailActivity;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.YunyingData;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 七个运营位适配器
 */
public class NewYunyingAdapter extends BaseAdapter {
    List<YunyingData> list;
    Context context;
    private ImageLoader mImageLoader;
    public NewYunyingAdapter(List<YunyingData> list, Context context, ImageLoader mImageLoader) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_new_yunying,null);
            classHolder.rlt_img_rule = (RelativeLayout) convertView.findViewById(R.id.rlt_img_rule);
            ViewGroup.LayoutParams params=classHolder.rlt_img_rule.getLayoutParams();
            params.height = DensityUtils.pxWith(context)/3*2;
            classHolder.rlt_img_rule.setLayoutParams(params);
            classHolder.id_recyclerview_horizontal = (RecyclerView) convertView.findViewById(R.id.id_recyclerview_horizontal);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            classHolder.id_recyclerview_horizontal.setLayoutManager(linearLayoutManager);
            classHolder.iv_new_banner = (ImageView) convertView.findViewById(R.id.iv_new_banner);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        final YunyingData yunyingData = list.get(position);
        final List<HeadList> headLists = yunyingData.getGoodsList();
        if (null!=headLists&&headLists.size()>0){
            classHolder.rlt_img_rule.setVisibility(View.VISIBLE);
            classHolder.id_recyclerview_horizontal.setVisibility(View.VISIBLE);
            Glide.with(context).load(Constant.URL_BASE_IMG+yunyingData.getOperatingPositionImg()).into(classHolder.iv_new_banner);
            GalleryAdapter mAdapter = new GalleryAdapter(context,headLists,mImageLoader);
            mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, int position) {
                    HeadList headList = headLists.get(position);
                    if (position==8){
                        if (null!=yunyingData.getOperatingPositionInsideImg()&&
                                !yunyingData.getOperatingPositionInsideImg().isEmpty()){
                            context.startActivity(new Intent(context, GoodsClassActivity.class)
                                    .putExtra("categoryId",yunyingData.getOperatingPositionPosition())
                                    .putExtra("goods_class_name", "运营商品").putExtra("banner_img",yunyingData.getOperatingPositionInsideImg()));
                        }else {
                            context.startActivity(new Intent(context, GoodsClassActivity.class)
                                    .putExtra("categoryId",yunyingData.getOperatingPositionPosition())
                                    .putExtra("goods_class_name", "运营商品").putExtra("banner_img",""));
                        }
                    }else {
                        context.startActivity(new Intent(context, GoodsDetailActivity.class)
                                .putExtra("goodsId",String.valueOf(headList.getGoodsId())));
                    }
                }
            });
            classHolder.id_recyclerview_horizontal.setAdapter(mAdapter);
        }else {
            classHolder.rlt_img_rule.setVisibility(View.GONE);
            classHolder.id_recyclerview_horizontal.setVisibility(View.GONE);
        }
        classHolder.iv_new_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (yunyingData.getOperatingPositionLinkType()){
                    case 1:
                        if (null!=yunyingData.getOperatingPositionInsideImg()&&
                                !yunyingData.getOperatingPositionInsideImg().isEmpty()){
                            context.startActivity(new Intent(context, GoodsClassActivity.class)
                                    .putExtra("categoryId",yunyingData.getOperatingPositionPosition())
                                    .putExtra("goods_class_name", "运营商品").putExtra("banner_img",yunyingData.getOperatingPositionInsideImg()));
                        }else {
                            context.startActivity(new Intent(context, GoodsClassActivity.class)
                                    .putExtra("categoryId",yunyingData.getOperatingPositionPosition())
                                    .putExtra("goods_class_name", "运营商品").putExtra("banner_img",""));
                        }
                        break;
                    case 2:
                        context.startActivity(new Intent(context, GoodsDetailActivity.class)
                                .putExtra("goodsId",String.valueOf(yunyingData.getOperatingPositionLinkId())));

                        break;
                    case 3:
                        if (yunyingData.getOperatingPositionLink().equals("http://app.coupon.list")){
                            context.startActivity(new Intent(context, GetCouponActivity.class));
                        }else if (yunyingData.getOperatingPositionLink().equals("http://app.live.list")){
                            MobclickAgent.onEvent(context, "user_click_goods_live");
                            context.startActivity(new Intent(context, OverseasLiveActivity.class));
                        }else {
                            if (null!=yunyingData.getOperatingPositionName()){
                                context.startActivity(new Intent(context, WebDetailActivity.class)
                                        .putExtra("web_url",yunyingData.getOperatingPositionLink()).putExtra("link_name",yunyingData.getOperatingPositionName()));
                            }else {
                                context.startActivity(new Intent(context, WebDetailActivity.class)
                                        .putExtra("web_url",yunyingData.getOperatingPositionLink()).putExtra("link_name","活动详情"));

                            }
                        }
                        break;
                    case 4:
                        if (null!=yunyingData.getOperatingPositionInsideImg()&&
                                !yunyingData.getOperatingPositionInsideImg().isEmpty()){
                            context.startActivity(new Intent(context, GoodsClassActivity.class)
                                    .putExtra("categoryId",String.valueOf(yunyingData.getOperatingPositionLinkId()))
                                    .putExtra("goods_class_name", "热门推荐").putExtra("banner_img",yunyingData.getOperatingPositionInsideImg()));
                        }else {
                            context.startActivity(new Intent(context, GoodsClassActivity.class)
                                    .putExtra("categoryId",String.valueOf(yunyingData.getOperatingPositionLinkId()))
                                    .putExtra("goods_class_name", "热门推荐"));
                        }
                        break;
                }
            }
        });
        return convertView;
    }
    static class ClassHolder{
        RelativeLayout rlt_img_rule;
        ImageView iv_new_banner;
        RecyclerView id_recyclerview_horizontal;
    }


}
