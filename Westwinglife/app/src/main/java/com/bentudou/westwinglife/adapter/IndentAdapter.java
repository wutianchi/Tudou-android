package com.bentudou.westwinglife.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GoodsDetailActivity;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.fragment.ThreeFragment;
import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.CartDataList;
import com.bentudou.westwinglife.json.CartGoodsDetail;
import com.bentudou.westwinglife.json.CartOrderInfo;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by  天池 on 2015/7/30.
 * 我的订单适配器
 */
public class IndentAdapter extends BaseAdapter {

    Context context;
    private Handler handler;
    private LayoutInflater inflater;
    public IndentAdapter( Context context, Handler handler,LayoutInflater inflater){
        this.context = context;
        this.handler = handler;
        this.inflater = inflater;
    }
    @Override
    public int getCount() {
        return Constant.cartDataLists.size();
    }

    @Override
    public Object getItem(int position) {
        return Constant.cartDataLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyHolder myHolder;
        if(convertView==null){
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_list_item,null);
            myHolder.cbx_cart_cang_all_select = (CheckBox) convertView.findViewById(R.id.cbx_cart_cang_all_select);
            myHolder.tv_store_name = (TextView)convertView.findViewById(R.id.tv_store_name);
            myHolder.iv_cangku_icon = (ImageView) convertView.findViewById(R.id.iv_cangku_icon);
            myHolder.tv_self_goods_price = (TextView)convertView.findViewById(R.id.tv_self_goods_price);
            myHolder.tv_self_custom = (TextView)convertView.findViewById(R.id.tv_self_custom);
            myHolder.tv_self_all_price = (TextView)convertView.findViewById(R.id.tv_self_all_price);
            myHolder.llt_goods = (LinearLayout) convertView.findViewById(R.id.llt_goods);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        CartDataList cartDataList = Constant.cartDataLists.get(position);
        if (null==cartDataList.getDepotIcon()){
            myHolder.iv_cangku_icon.setBackgroundResource(R.drawable.gouwuche_dianpu_default);
        }else {
            ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+cartDataList.getDepotIcon(),myHolder.iv_cangku_icon);
        }
        myHolder.tv_store_name.setText(cartDataList.getDepotName());
        myHolder.llt_goods.removeAllViews();
        boolean isAllchecked=true;
        double x=0;
        double y = 0;
        for (int i=0;i<cartDataList.getGoodsList().size();i++){
            ChildHolder childHolder = new ChildHolder();
            View itemview = LayoutInflater.from(context).inflate(R.layout.item_cart_oneself,null);
            itemview.setId(i);
            myHolder.llt_goods.addView(itemview);
            final CartGoodsDetail headList =cartDataList.getGoodsList().get(i);
            childHolder.tv_name = (TextView) itemview.findViewById(R.id.tv_name);
            childHolder.tv_price = (TextView) itemview.findViewById(R.id.tv_price);
            childHolder.tv_goods_no_use = (TextView) itemview.findViewById(R.id.tv_goods_no_use);
            childHolder.ll_count_sub = (LinearLayout) itemview.findViewById(R.id.ll_count_sub);
            childHolder.ll_count_add = (LinearLayout) itemview.findViewById(R.id.ll_count_add);
            childHolder.cbx_cart_coupon = (CheckBox) itemview.findViewById(R.id.cbx_cart_coupon);
            childHolder.iv_cart_img = (ImageView) itemview.findViewById(R.id.iv_cart_img);
            childHolder.et_count_value = (EditText) itemview.findViewById(R.id.et_count_value);
            itemview.setTag(childHolder);
            if (null!=Constant.map.get(headList.getGoodsId()+"")){
                        x+=headList.getShopPriceCny().doubleValue()*headList.getGoodsNumber();
                        y+=headList.getShopPriceCny().doubleValue()*headList.getGoodsNumber()*headList.getCustomsDuties().doubleValue();
                    childHolder.cbx_cart_coupon.setChecked(true);
                }else {
                    isAllchecked=false;
                    Constant.allselect =2;
                    childHolder.cbx_cart_coupon.setChecked(false);
                }
//            if (Constant.allselect==1){
//                if (headList.isActivity()){
//                    x+=headList.getWholeSalePrice().doubleValue()*headList.getGoodsNumber();
//                    y+=headList.getWholeSalePrice().doubleValue()*headList.getGoodsNumber()*headList.getCustomsDuties().doubleValue();
//                }else{
//                    x+=headList.getShopPrice().doubleValue()*headList.getGoodsNumber();
//                    y+=headList.getShopPrice().doubleValue()*headList.getGoodsNumber()*headList.getCustomsDuties().doubleValue();
//                }
//                childHolder.cbx_cart_coupon.setChecked(true);
//            }else if (Constant.allselect==0){
//                Constant.map.clear();
//                isAllchecked=false;
//                childHolder.cbx_cart_coupon.setChecked(false);
//            }else {
//                if (null!=Constant.map.get(headList.getGoodsId()+"")){
//                    if (headList.isActivity()){
//                        x+=headList.getWholeSalePrice().doubleValue()*headList.getGoodsNumber();
//                        y+=headList.getWholeSalePrice().doubleValue()*headList.getGoodsNumber()*headList.getCustomsDuties().doubleValue();
//                    }else{
//                        x+=headList.getShopPrice().doubleValue()*headList.getGoodsNumber();
//                        y+=headList.getShopPrice().doubleValue()*headList.getGoodsNumber()*headList.getCustomsDuties().doubleValue();
//                    }
//                    childHolder.cbx_cart_coupon.setChecked(true);
//                }else {
//                    isAllchecked=false;
//                    Constant.allselect =2;
//                    childHolder.cbx_cart_coupon.setChecked(false);
//                }
//            }
            if (!headList.isOnSale()){
                if (headList.getGoodsNumber()>headList.getGoodsStockNumber()){
                    childHolder.tv_goods_no_use.setText("已失效     库存不足");
                }else {
                    childHolder.tv_goods_no_use.setText("已失效");
                }
                Message msg = new Message();
                msg.what = ThreeFragment.CART_CLEAR;
                msg.obj = "1";
                handler.sendMessage(msg);
            }else {
                if (headList.getGoodsNumber()>headList.getGoodsStockNumber()){
                    childHolder.tv_goods_no_use.setText("库存不足");
                }else {
                    childHolder.tv_goods_no_use.setText("");
                }
            }
            childHolder.tv_name.setText(headList.getGoodsCnName());
            childHolder.tv_price.setText(VerifitionUtil.getRMBStringPrice(headList.getShopPriceCny()));
            childHolder.et_count_value.setText(headList.getGoodsNumber()+"");
            ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+headList.getGoodsImg(),childHolder.iv_cart_img);
            childHolder.ll_count_add.setOnClickListener(new MyOnclickListener(position,i));
            childHolder.ll_count_sub.setOnClickListener(new MyOnclickListener(position,i));
            addChildListener(childHolder,position,i);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, GoodsDetailActivity.class).putExtra("goodsId",String.valueOf(headList.getGoodsId())));
                }
            });
        }
        if (isAllchecked){
            myHolder.cbx_cart_cang_all_select.setChecked(true);
        }else {
            myHolder.cbx_cart_cang_all_select.setChecked(false);
        }
        myHolder.tv_self_goods_price.setText(VerifitionUtil.getDoubleRMBStringPrice(x));
        if (y<=50)
            y=0;
        myHolder.tv_self_custom.setText(VerifitionUtil.getDoubleRMBStringPrice(y));
        myHolder.tv_self_all_price.setText(VerifitionUtil.getDoubleRMBStringPrice(x+y));
        Constant.allmap.put(cartDataList.getDepotName(),String.valueOf(x+y));
        if (position==Constant.cartDataLists.size()-1){
            Message msg = new Message();
            msg.what = ThreeFragment.CART_ALLMAP;
            msg.obj = "2";
            handler.sendMessage(msg);
        }
//        final boolean finalIsAllchecked = isAllchecked;
//        myHolder.cbx_cart_cang_all_select.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (finalIsAllchecked)
//                map.clear();
//            }
//        });
//        addListener(myHolder,position);
        myHolder.cbx_cart_cang_all_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( myHolder.cbx_cart_cang_all_select.isChecked()){
                    for (int i=0;i<Constant.cartDataLists.get(position).getGoodsList().size();i++){
                        Log.d("list", "-------isChecked: "+i);
                        Constant.map.put(Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsId()+"",Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsNumber()+"");
                    }
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_SELECTED;
                    msg.obj = Constant.map;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                }else {
                    Constant.allselect=2;
                    for (int i=0;i<Constant.cartDataLists.get(position).getGoodsList().size();i++){
                        Log.d("list", "-------isChecked: "+i);
                        Constant.map.remove(Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsId()+"");
                    }
                    Log.d("map", "-------map: "+Constant.map.size());
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_CANCEL_SELECTE;
                    msg.obj = Constant.map;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    //加减按钮点击
    class MyOnclickListener implements View.OnClickListener{

        private int num;
        private int position;
        private int i;
        public MyOnclickListener(int position,int i){

            this.position = position;
            this.i = i;
            this.num = Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsNumber();
        }

        @Override
        public void onClick(View v) {
            CartOrderInfo cartOrderInfo = new CartOrderInfo();
            switch (v.getId()){

                case R.id.ll_count_add:
                    if (!VerifitionUtil.isNetworkAvailable(context)){
                        ToastUtils.showToastCenter(context,"网络不给力，请检查网络~");
                        return;
                    }
                    num++;
                    cartOrderInfo.setCartId(Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsId());
                    cartOrderInfo.setCarCount(num);
                    addSubCar(cartOrderInfo,num);
                    break;

                case R.id.ll_count_sub:
                    if (!VerifitionUtil.isNetworkAvailable(context)){
                        ToastUtils.showToastCenter(context,"网络不给力，请检查网络~");
                        return;
                    }
                    if (num==Constant.cartDataLists.get(position).getGoodsList().get(i).getWholesaleMoq()||num==1||!Constant.cartDataLists.get(position).getGoodsList().get(i).isOnSale()){
                        showDialogInfo(position,i);
                        return;
                    }
                    num--;
                    cartOrderInfo.setCartId(Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsId());
                    cartOrderInfo.setCarCount(num);
                    addSubCar(cartOrderInfo,num);
                    break;
            }
        }

        private void addSubCar(final CartOrderInfo cartOrderInfo, final int num) {
            final PotatoService service = RTHttpClient.create(PotatoService.class);
            service.addUserCart(SharePreferencesUtils.getBtdToken(context),cartOrderInfo.getCartId(),cartOrderInfo.getCarCount(), new CallbackSupport<AddSub>(context) {
                @Override
                public void success(AddSub addSub, Response response) {
                    if (addSub.getStatus().equals("1")){
                        Constant.cartDataLists.get(position).getGoodsList().get(i).setGoodsNumber(num);
                        if (null!=Constant.map.get(Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsId()+""))
                            Constant.map.put(Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsId()+"",Constant.cartDataLists.get(position).getGoodsList().get(i).getGoodsNumber()+"");
                        Message msg = new Message();
                        msg.what = ThreeFragment.CART_REVISE;
                        msg.obj = Constant.map;
                        handler.sendMessage(msg);
                        notifyDataSetChanged();
                    }else {
                        ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                        notifyDataSetChanged();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    notifyDataSetChanged();
                }
            });
        }

    }

    private void addListener(MyHolder holder, final int groupPosition){

        holder.cbx_cart_cang_all_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("groupPosition", "-------: "+groupPosition);
                if (isChecked) {
                    for (int i=0;i<Constant.cartDataLists.get(groupPosition).getGoodsList().size();i++){
                        Log.d("list", "-------isChecked: "+i);
                        Constant.map.put(Constant.cartDataLists.get(groupPosition).getGoodsList().get(i).getGoodsId()+"",Constant.cartDataLists.get(groupPosition).getGoodsList().get(i).getGoodsNumber()+"");
                    }
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_SELECTED;
                    msg.obj = Constant.map;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                } else {
                    Constant.allselect=2;
                    Log.d("map", "-------map: "+Constant.map.size());
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_CANCEL_SELECTE;
                    msg.obj = Constant.map;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                }
            }
        });

    }
    private void addChildListener(ChildHolder childHolder, final int groupPosition, final int childPosition){

        childHolder.cbx_cart_coupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Constant.map.put(Constant.cartDataLists.get(groupPosition).getGoodsList().get(childPosition).getGoodsId()+"",Constant.cartDataLists.get(groupPosition).getGoodsList().get(childPosition).getGoodsNumber()+"");
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_SELECTED;
                    msg.obj = Constant.map;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                } else {
                    Constant.allselect=2;
                    Constant.map.remove(Constant.cartDataLists.get(groupPosition).getGoodsList().get(childPosition).getGoodsId()+"");
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_CANCEL_SELECTE;
                    msg.obj = Constant.map;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                }
            }
        });

    }

    private void showDialogInfo(final int groupPosition,final int childPosition) {
        View layout = inflater.inflate(R.layout.dialog_store_info,
                null);
        TextView cancelGo = (TextView) layout.findViewById(R.id.cancel_go);
        TextView sureNoGo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        /**监听对话框里面的button点击事件*/
        sureNoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        cancelGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerifitionUtil.isNetworkAvailable(context)){
                    deleteCart(groupPosition,childPosition);
                }else {
                    ToastUtils.showToastCenter(context,"网络不给力，请检查网络~");
                }
                dialog.dismiss();
            }
        });
    }
    //删除商品
    private void deleteCart(final int groupPosition, final int childPosition) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.deleteCartGoods(SharePreferencesUtils.getBtdToken(context),Constant.cartDataLists.get(groupPosition).getGoodsList().get(childPosition).getGoodsId(),new CallbackSupport<Success>(context) {
            @Override
            public void success ( Success success, Response response){
                if (Constant.cartDataLists.get(groupPosition).getGoodsList().size()==1){
                    Constant.cartDataLists.remove(groupPosition);
                }else {
                    Constant.cartDataLists.get(groupPosition).getGoodsList().remove(childPosition);
                }
                Message msg = new Message();
                msg.what = ThreeFragment.CART_DEL;
                msg.obj = Constant.cartDataLists;
                handler.sendMessage(msg);
                Constant.allmap.clear();
                notifyDataSetChanged();
            }
            @Override
            public void failure (RetrofitError error){
                super.failure(error);

            }
        });

    }
    static class MyHolder{
        CheckBox cbx_cart_cang_all_select;//本仓库全选按钮
        TextView tv_store_name;//仓库名
        ImageView iv_cangku_icon;//仓库icon
        TextView tv_self_goods_price;//本仓库商品价格
        TextView tv_self_custom;//本仓库商品税费
        TextView tv_self_all_price;//本仓库合计
        LinearLayout llt_goods;//本仓库商品列表
    }
    static class ChildHolder{
        TextView tv_name;
        TextView tv_price;
        TextView tv_goods_no_use;
        LinearLayout ll_count_sub;
        LinearLayout ll_count_add;
        CheckBox cbx_cart_coupon;
        ImageView iv_cart_img;
        EditText et_count_value;
    }

}
