package com.bentudou.westwinglife.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.fragment.FourFragment;
import com.bentudou.westwinglife.fragment.ThreeFragment;
import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.CartDataList;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.json.CartGoodsDetail;
import com.bentudou.westwinglife.json.CartOrderInfo;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.OrderItem;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.DecimalFormat;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by 天池 on 2015/12/22.
 */
public class ElvCartStoreAdapter extends BaseExpandableListAdapter {
    private List<CartDataList> otherStores;
    private Context context;
    private Handler handler;
    private LayoutInflater inflater;
    public ElvCartStoreAdapter(List<CartDataList> otherStores, Context context, Handler handler){
        this.otherStores = otherStores;
        this.context = context;
        this.handler = handler;
        inflater = LayoutInflater.from(this.context);
    }

    public List<CartDataList> getOtherStores() {
        return otherStores;
    }

    @Override
    public int getGroupCount() {
        return otherStores.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return otherStores.get(groupPosition).getGoodsList().size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return otherStores.get(groupPosition).getDepotName();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return otherStores.get(groupPosition).getGoodsList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = null;

        //普通分组
        if (convertView != null&&(Integer)convertView.getTag()==0)
        {
            view = convertView;
        } else {
            view = inflater.inflate(R.layout.cart_store_father, null);
            view.setTag(0);
        }
        TextView text = (TextView)view.findViewById(R.id.tv_store_name);
        if (isExpanded) {
            text.setText(""+otherStores.get(groupPosition).getDepotName());
        }
        else{
            text.setText("" + otherStores.get(groupPosition).getDepotName());
        }
        return view;

    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = inflater.inflate(R.layout.item_cart_oneself, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.ll_count_sub = (LinearLayout) convertView.findViewById(R.id.ll_count_sub);
            holder.ll_count_add = (LinearLayout) convertView.findViewById(R.id.ll_count_add);
            holder.cbx_cart_coupon = (CheckBox) convertView.findViewById(R.id.cbx_cart_coupon);
            holder.iv_cart_img = (ImageView) convertView.findViewById(R.id.iv_cart_img);
            holder.et_count_value = (EditText) convertView.findViewById(R.id.et_count_value);
            convertView.setTag(holder);
        }else{
            holder = (MyHolder)convertView.getTag();
        }
        final CartGoodsDetail cars = otherStores.get(groupPosition).getGoodsList().get(childPosition);
        holder.tv_name.setText(cars.getGoodsCnName());
        holder.tv_price.setText(VerifitionUtil.getStringPrice(cars.getShopPriceCny()));
        holder.et_count_value.setText(cars.getGoodsNumber()+"");
        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+cars.getGoodsImg(), holder.iv_cart_img);
        holder.ll_count_add.setOnClickListener(new MyOnclickListener(groupPosition, childPosition, holder));
        holder.ll_count_sub.setOnClickListener(new MyOnclickListener(groupPosition,childPosition, holder));
        addListener(holder, groupPosition, childPosition);
        return convertView;

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
    private void deleteCart(final int groupPosition, final int childPosition) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.deleteCartGoods(SharePreferencesUtils.getBtdToken(context),otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId(),new CallbackSupport<Success>(context) {
            @Override
            public void success ( Success success, Response response){
                OrderItem orderItem = new OrderItem();
                orderItem.setGoodsId(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId() + "");
                orderItem.setGoodsNumber(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsNumber() + "");
                Message msg = new Message();
                msg.what = ThreeFragment.CART_DEL;
                msg.obj = orderItem;
                handler.sendMessage(msg);
                if (otherStores.get(groupPosition).getGoodsList().size()==1){
                    otherStores.remove(groupPosition);
                }else {
                    otherStores.get(groupPosition).getGoodsList().remove(childPosition);
                }
                notifyDataSetChanged();
            }
            @Override
            public void failure (RetrofitError error){
                super.failure(error);

            }
        });

    }

    //加减按钮点击
    class MyOnclickListener implements View.OnClickListener{

        private int num;
        private int groupPosition;
        private int childPosition;
        private MyHolder holder;
        public MyOnclickListener(int groupPosition,int childPosition, MyHolder holder){

            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
            this.holder = holder;
            this.num = Integer.parseInt(holder.et_count_value.getText().toString());
        }

        @Override
        public void onClick(View v) {
            CartOrderInfo cartOrderInfo = new CartOrderInfo();
            this.num = Integer.parseInt(holder.et_count_value.getText().toString());
            switch (v.getId()){

                case R.id.ll_count_add:
                    if (!VerifitionUtil.isNetworkAvailable(context)){
                        ToastUtils.showToastCenter(context,"网络不给力，请检查网络~");
                        return;
                    }
                    num++;
                    holder.et_count_value.setText(num + "");
                    cartOrderInfo.setCartId(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId());
                    cartOrderInfo.setCarCount(num);
                    addCar(cartOrderInfo,num);
                    break;

                case R.id.ll_count_sub:
                    if (!VerifitionUtil.isNetworkAvailable(context)){
                        ToastUtils.showToastCenter(context,"网络不给力，请检查网络~");
                        return;
                    }
                    if (num==1){
                        showDialogInfo(groupPosition,childPosition);
                        return;
                    }
                    num--;
                    holder.et_count_value.setText(num + "");
                    cartOrderInfo.setCartId(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId());
                    cartOrderInfo.setCarCount(num);
                    subCar(cartOrderInfo,num);
                    break;
            }
        }

        private void addCar(CartOrderInfo cartOrderInfo, final int num) {
            final PotatoService service = RTHttpClient.create(PotatoService.class);
            service.addUserCart(SharePreferencesUtils.getBtdToken(context),cartOrderInfo.getCartId(),cartOrderInfo.getCarCount(), new CallbackSupport<AddSub>(context) {
                @Override
                public void success(AddSub addSub, Response response) {
                    otherStores.get(groupPosition).getGoodsList().get(childPosition).setGoodsNumber(num);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setGoodsId(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId() + "");
                    orderItem.setGoodsNumber(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsNumber() + "");
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_REVISE;
                    msg.obj = orderItem;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    notifyDataSetChanged();
                }
            });
        }

        private void subCar(CartOrderInfo cartOrderInfo, final int num) {
            final PotatoService service = RTHttpClient.create(PotatoService.class);
            service.addUserCart(SharePreferencesUtils.getBtdToken(context),cartOrderInfo.getCartId(),cartOrderInfo.getCarCount(), new CallbackSupport<AddSub>(context) {
                @Override
                public void success(AddSub addSub, Response response) {
                    otherStores.get(groupPosition).getGoodsList().get(childPosition).setGoodsNumber(num);
                    OrderItem orderItem = new OrderItem();
                    orderItem.setGoodsId(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId() + "");
                    orderItem.setGoodsNumber(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsNumber() + "");
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_REVISE;
                    msg.obj = orderItem;
                    handler.sendMessage(msg);
                    notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError error) {
                    super.failure(error);
                    notifyDataSetChanged();
                }
            });
        }

    }

    //选择车辆
    private void addListener(MyHolder holder, final int groupPosition, final int childPosition){

        holder.cbx_cart_coupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OrderItem orderItem = new OrderItem();
                orderItem.setGoodsId(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsId() + "");
                orderItem.setGoodsNumber(otherStores.get(groupPosition).getGoodsList().get(childPosition).getGoodsNumber() + "");
                if (isChecked) {
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_SELECTED;
                    msg.obj = orderItem;
                    handler.sendMessage(msg);
                } else {
                    Message msg = new Message();
                    msg.what = ThreeFragment.CART_CANCEL_SELECTE;
                    msg.obj = orderItem;
                    handler.sendMessage(msg);
                }
            }
        });

    }
    private String getPriceText(double price){

        return new DecimalFormat("##,###.00").format(price);
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class MyHolder{
        CheckBox cbx_cart_coupon;
        TextView tv_name,tv_price,tv_oneself_line,tv_specail,tv_saller_over;
        ImageButton ibtn_del;
        ImageView iv_cart_img;
        LinearLayout ll_count_sub,ll_count_add;
        EditText et_count_value;
    }
}
