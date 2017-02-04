package com.bentudou.westwinglife.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GetCouponActivity;
import com.bentudou.westwinglife.activity.GoodsClassActivity;
import com.bentudou.westwinglife.activity.GoodsDetailActivity;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.activity.OverseasLiveActivity;
import com.bentudou.westwinglife.activity.WebDetailActivity;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.json.YunyingData;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.media.UMImage;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *Created by lzz on 2016/3/2.
 * 运营单品适配器
 */
public class NewDanpinAdapter extends BaseAdapter {
    List<HeadList> list;
    Context context;
    private ImageLoader mImageLoader;
    public NewDanpinAdapter(List<HeadList> list, Context context, ImageLoader mImageLoader) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_new_danpin,null);
            classHolder.rlt_img_rule = (RelativeLayout) convertView.findViewById(R.id.rlt_img_rule);
            ViewGroup.LayoutParams params=classHolder.rlt_img_rule.getLayoutParams();
            params.height = DensityUtils.pxWith(context);
            classHolder.rlt_img_rule.setLayoutParams(params);
            classHolder.iv_new_danpin = (ImageView) convertView.findViewById(R.id.iv_new_danpin);
            classHolder.iv_danpin_dot = (ImageView) convertView.findViewById(R.id.iv_danpin_dot);
            classHolder.iv_danpin_cart = (ImageView) convertView.findViewById(R.id.iv_danpin_cart);
            classHolder.iv_danpin_shoucang = (ImageView) convertView.findViewById(R.id.iv_danpin_shoucang);
            classHolder.iv_danpin_share = (ImageView) convertView.findViewById(R.id.iv_danpin_share);
            classHolder.tv_danpin_name = (TextView) convertView.findViewById(R.id.tv_danpin_name);
            classHolder.tv_danpin_desc = (TextView) convertView.findViewById(R.id.tv_danpin_desc);
            classHolder.tv_danpin_dot = (TextView) convertView.findViewById(R.id.tv_danpin_dot);
            classHolder.tv_danpin_price = (TextView) convertView.findViewById(R.id.tv_danpin_price);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        final HeadList headList = list.get(position);
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getGoodsImg()+Constant.IMG_400,classHolder.iv_new_danpin);
        classHolder.tv_danpin_name.setText(headList.getGoodsCnName());
//        if (null!=headList.getGoodsDescTable()&&!headList.getGoodsDescTable().isEmpty()){
//            classHolder.tv_danpin_desc.setVisibility(View.VISIBLE);
//            classHolder.tv_danpin_desc.setText(headList.getGoodsDescTable());
//        }else {
//            classHolder.tv_danpin_desc.setVisibility(View.GONE);
//        }
        mImageLoader.displayImage(Constant.URL_BASE_IMG+headList.getDepotIcon(),classHolder.iv_danpin_dot);
        classHolder.tv_danpin_dot.setText(headList.getDepotName());
        classHolder.tv_danpin_price.setText(VerifitionUtil.getRMBStringPrice(headList.getShopPriceCny()));
        classHolder.iv_danpin_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharePreferencesUtils.getBtdToken(context).equals("")){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {
                    addGoodsToCart(headList.getGoodsId(),1);
                }
            }
        });
        if (headList.isCllect()){
            classHolder.iv_danpin_shoucang.setImageResource(R.drawable.shangpinxiangqing_yiguanzhu);
        }else {
            classHolder.iv_danpin_shoucang.setImageResource(R.drawable.shangpinxiangqing_guanzhu);
        }
        classHolder.iv_danpin_shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharePreferencesUtils.getBtdToken(context).equals("")){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {
                    if (headList.isCllect()){
                        cancelCollection(headList.getGoodsId(),position);
                    }else {
                        addCollection(headList.getGoodsId(),position);
                    }
                }
            }
        });
        classHolder.iv_danpin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showShareToast((Activity) context,"我在笨土豆发现了一个不错的商品，快来看看吧。",headList.getGoodsCnName(),
                        "http://m.bentudou.com/Goods/getGoodsInfo.htm?goodsId="+headList.getGoodsId(),
                        new UMImage(context,  Constant.URL_BASE_IMG+headList.getGoodsImg()+".w200"));
            }
        });
        return convertView;
    }
    static class ClassHolder{
        RelativeLayout rlt_img_rule;
        ImageView iv_new_danpin,iv_danpin_dot,iv_danpin_cart,iv_danpin_shoucang,iv_danpin_share;
        TextView tv_danpin_name,tv_danpin_desc,tv_danpin_dot,
                tv_danpin_price;
    }
    //添加到购物车
    private void addGoodsToCart(int goodsId,int count) {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.addUserCartTwo(SharePreferencesUtils.getBtdToken(context),goodsId,count,new Callback<AddSub>() {
            @Override
            public void success(AddSub addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    ToastUtils.showToastCenter(context,"加入购物车成功!");
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"加入购物车失败!");
            }
        });
    }
    //取消收藏
    private void cancelCollection(int goodsId, final int postion) {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.delCollect(SharePreferencesUtils.getBtdToken(context),goodsId,new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    list.get(postion).setCllect(false);
                    ToastUtils.showToastCenter(context,"取消收藏成功!");
                    notifyDataSetChanged();
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"取消收藏失败!");
            }
        });
    }
    //添加收藏
    private void addCollection(int goodsId, final int postion) {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.addCollect(SharePreferencesUtils.getBtdToken(context),goodsId,new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    list.get(postion).setCllect(true);
                    ToastUtils.showToastCenter(context,"收藏成功!");
                    notifyDataSetChanged();
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"收藏失败!");
            }
        });
    }

}
