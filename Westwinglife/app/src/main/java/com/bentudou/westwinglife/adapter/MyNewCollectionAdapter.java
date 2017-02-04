package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.MyNewCollectionListActivity;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.FavoiteInfo;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MyNewCollectionAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<FavoiteInfo> favoiteInfoList;
    private Context context;
    private Handler myHandler;
    public MyNewCollectionAdapter(Context context, List<FavoiteInfo> favoiteInfoList, Handler myHandler) {
        mInflater = LayoutInflater.from(context);
        this.favoiteInfoList=favoiteInfoList;
        this.context=context;
        this.myHandler=myHandler;
    }

    @Override
    public int getCount() {
        return favoiteInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoiteInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_favoite_info, null);
            holder.goods_icon = (ImageView) convertView.findViewById(R.id.goods_icon);
            holder.goods_name = (TextView) convertView.findViewById(R.id.goods_name);
            holder.goods_price = (TextView) convertView.findViewById(R.id.goods_price);
            holder.goods_shixiao = (TextView) convertView.findViewById(R.id.goods_shixiao);
            holder.tv_add_cart = (TextView) convertView.findViewById(R.id.tv_add_cart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FavoiteInfo favoiteInfo = favoiteInfoList.get(position);
        holder.goods_name.setText(favoiteInfo.getGoodsName());
        holder.goods_price.setText(VerifitionUtil.getRMBStringPrice(favoiteInfo.getShopPriceCny()));
        if (favoiteInfo.isOnSale()){
            holder.goods_shixiao.setVisibility(View.GONE);
        }else {
            holder.goods_shixiao.setVisibility(View.VISIBLE);
            Message msg = new Message();
            msg.what = MyNewCollectionListActivity.CART_SHIXIAO;
            msg.arg1 = 1;
            myHandler.sendMessage(msg);
        }
        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+favoiteInfo.getGoodsImg(),holder.goods_icon);
        holder.tv_add_cart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoiteInfo.getDiscount()==0){
                    AddCart(favoiteInfo.getGoodsId(),1);
                }else {
                    AddCart(favoiteInfo.getGoodsId(),favoiteInfo.getDiscount());
                }

            }
        });
        return convertView;
    }

    private void AddCart(int goodsId,int count) {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.addUserCartTwo(SharePreferencesUtils.getBtdToken(context),goodsId,count,new Callback<AddSub>() {
            @Override
            public void success(AddSub addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    Message msg = new Message();
                    msg.what = MyNewCollectionListActivity.CART_COUNT;
                    msg.arg1 = addSub.getData().getCartGoodsNum();
                    myHandler.sendMessage(msg);
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

    static class ViewHolder {
        public ImageView goods_icon;
        public TextView goods_name;
        public TextView goods_price;
        public TextView goods_shixiao;
        public TextView tv_add_cart;
    }
}