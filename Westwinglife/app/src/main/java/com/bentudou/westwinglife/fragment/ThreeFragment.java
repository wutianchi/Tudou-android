package com.bentudou.westwinglife.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GoodsDetailActivity;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.activity.OrderDetailActivity;
import com.bentudou.westwinglife.activity.PayOrderActivity;
import com.bentudou.westwinglife.activity.RegistActivity;
import com.bentudou.westwinglife.adapter.ElvCartStoreAdapter;
import com.bentudou.westwinglife.adapter.HeadListAdapter;
import com.bentudou.westwinglife.adapter.IndentAdapter;
import com.bentudou.westwinglife.adapter.MyFragmentPagerAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.CartData;
import com.bentudou.westwinglife.json.CartDataList;
import com.bentudou.westwinglife.json.CartOrderInfo;
import com.bentudou.westwinglife.json.CategoryList;
import com.bentudou.westwinglife.json.CommitBack;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OrderItem;
import com.bentudou.westwinglife.json.Session;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyExpandableListView;
import com.bentudou.westwinglife.view.MyGridView;
import com.bentudou.westwinglife.view.MyListView;
import com.gunlei.app.ui.view.ProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/2.
 */
public class ThreeFragment extends Fragment implements View.OnClickListener{
    public static final int CART_SELECTED = 11;
    public static final int CART_CANCEL_SELECTE = 12;
    public static final int CART_REVISE = 13;
    public static final int CART_DEL = 14;
    public static final int CART_CLEAR = 15;
    public static final int CART_ALLMAP = 16;
    public static String all_price = "0";
    private RelativeLayout rlt_cart_data;
    private LinearLayout llt_cart_nogoods;
    private ScrollView sv_cart_none;
    private CheckBox btn_all_select;
    private Button btn_commit_order;
    private TextView tv_all_price,tv_clear_no_use_goods;
    private MyGridView cart_hot_list;
    private HeadListAdapter adapter;//尖货适配器
    private ElvCartStoreAdapter elvCartStoreAdapter;//购物车列表适配器
    private IndentAdapter indentAdapter;//购物车列表适配器
    private MyExpandableListView elv_cart_store;
    private MyListView mlv_cart_store;
    List<CartOrderInfo> goods = new ArrayList<CartOrderInfo>();
//    Map<String,String> map = new HashMap<>();
    private CartOrderInfo orderItem = null;
//    private List<CartDataList> cartDataLists;
    private List<HeadList> headLists;
    private LayoutInflater layoutInflater;
    ProgressHUD progressHUD = null;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == CART_SELECTED){
                if (Constant.map.size()==getCartDataListsSize(Constant.cartDataLists)){
                    btn_all_select.setChecked(true);
                    Constant.allselect=1;
                }
                tv_all_price.setText(getPrice(Constant.map));
            }else if (msg.what == CART_CANCEL_SELECTE){
                if (Constant.map.size()==getCartDataListsSize(Constant.cartDataLists)){
                    btn_all_select.setChecked(true);
                    Constant.allselect=1;
                }else {
                    btn_all_select.setChecked(false);
//                    Constant.allselect=2;
                }
                if (Constant.map.size()>0){
                    tv_all_price.setText(getPrice(Constant.map));
                }else {
                    tv_all_price.setText("¥0");
                }
            }else if(msg.what == CART_DEL){
                Constant.map = getOrderMap(Constant.cartDataLists);
                if (Constant.cartDataLists.isEmpty()){
                    initData();
                }else {
                    if (Constant.map.size()>0){
                        if (Constant.map.size()==getCartDataListsSize(Constant.cartDataLists)){
                            btn_all_select.setChecked(true);
                            Constant.allselect=1;
                        }
                        tv_all_price.setText(getPrice(Constant.map));
                    }else {
                        tv_all_price.setText("¥0");
                    }
                }
            }else if (msg.what == CART_REVISE){
                if (Constant.map.size()>0){
                    tv_all_price.setText(getPrice(Constant.map));
                }else {
                    tv_all_price.setText("¥0");
                }

            }else if (msg.what==CART_CLEAR){
                tv_clear_no_use_goods.setVisibility(View.VISIBLE);
            }else if (msg.what==CART_ALLMAP){
                tv_all_price.setText(getPrice(Constant.map));
            }
        }
    };

    private Map<String, String> getOrderMap(List<CartDataList> cartDataLists) {
        Iterator iter = Constant.map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            boolean ishave = false;
            for (int i=0;i<cartDataLists.size();i++){
                for (int j=0;j<cartDataLists.get(i).getGoodsList().size();j++){
                    if (entry.getKey().equals(String.valueOf(cartDataLists.get(i).getGoodsList().get(j).getGoodsId()))){
                        ishave = true;
                    }

                }
            }
            if (!ishave){
                Constant.map.remove(entry.getKey());
                return Constant.map;
            }
            }

//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            boolean ishave = false;
//            for (int i=0;i<cartDataLists.size();i++){
//                for (int j=0;j<cartDataLists.get(i).getGoodsList().size();j++){
//                    if (entry.getKey().equals(String.valueOf(cartDataLists.get(i).getGoodsList().get(j).getGoodsId()))){
//                        ishave = true;
//                    }
//
//                }
//            }
//            if (!ishave){
//                map.remove(entry.getKey());
//            }
//        }
        return Constant.map;
    }

    private String getPrice(Map<String, String> map) {
        double price=0;
//        for (int i=0;i<Constant.cartDataLists.size();i++){
//           for (int j=0;j<Constant.cartDataLists.get(i).getGoodsList().size();j++){
//               if (null!=map.get(""+Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId())){
//                       price += Integer.valueOf(map.get("" + Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId())) * Constant.cartDataLists.get(i).getGoodsList().get(j).getShopPriceCny().doubleValue() +
//                               Integer.valueOf(map.get("" + Constant.cartDataLists.get(i).getGoodsList().get(j).getGoodsId())) *
//                                       Constant.cartDataLists.get(i).getGoodsList().get(j).getShopPriceCny().doubleValue() * Constant.cartDataLists.get(i).getGoodsList().get(j).getCustomsDuties().doubleValue();
//               }
//           }
//        }
        Iterator iter = Constant.allmap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry) iter.next();
            price+=Double.valueOf((String)entry.getValue());
        }
        return VerifitionUtil.getDoubleRMBStringPrice(price);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_three,null);
        layoutInflater = getActivity().getLayoutInflater();
        initView(view);
        return view;
    }

    private void initView(View view) {
        rlt_cart_data = (RelativeLayout) view.findViewById(R.id.rlt_cart_data);
        llt_cart_nogoods = (LinearLayout) view.findViewById(R.id.llt_cart_nogoods);
        sv_cart_none = (ScrollView) view.findViewById(R.id.sv_cart_none);
        cart_hot_list = (MyGridView) view.findViewById(R.id.cart_hot_list);
//        elv_cart_store = (MyExpandableListView) view.findViewById(R.id.elv_cart_store);
        mlv_cart_store = (MyListView) view.findViewById(R.id.mlv_cart_store);
        btn_all_select = (CheckBox) view.findViewById(R.id.btn_all_select);
        btn_commit_order = (Button) view.findViewById(R.id.btn_commit_order);
        tv_all_price = (TextView) view.findViewById(R.id.tv_all_price);
        tv_clear_no_use_goods = (TextView) view.findViewById(R.id.tv_clear_no_use_goods);
//        Constant.allselect=1;
        tv_clear_no_use_goods.setOnClickListener(this);
        btn_all_select.setOnClickListener(this);
        btn_commit_order.setOnClickListener(this);
        cart_hot_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                        .putExtra("goodsId",headLists.get(position).getGoodsId()+""));
            }
        });
//        elv_cart_store.setGroupIndicator(null);

    }

    private void initData() {
        progressHUD = ProgressHUD.show(getActivity(), "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.selectUserCart(SharePreferencesUtils.getBtdToken(getActivity()),"CNY", new CallbackSupport<CartData>(progressHUD,getActivity()) {
            @Override
            public void success(CartData cartData, Response response) {
                progressHUD.dismiss();
                if (cartData.getData().isCartEmpty()){
                    rlt_cart_data.setVisibility(View.GONE);
                    sv_cart_none.setVisibility(View.VISIBLE);
                    sv_cart_none.smoothScrollTo(0, 0);
                    llt_cart_nogoods.setFocusable(true);
                    llt_cart_nogoods.setFocusableInTouchMode(true);
                    llt_cart_nogoods.requestFocus();
                    if (!cartData.getData().getGoodsList().isEmpty()){
                        headLists = cartData.getData().getGoodsList();
                        adapter = new HeadListAdapter(headLists,getActivity(), ImageLoader.getInstance());
                        cart_hot_list.setAdapter(adapter);
                    }

                }else {
                    Constant.cartDataLists = cartData.getData().getCartList();
                    Constant.map = getOnsaleMap(Constant.cartDataLists);
                    rlt_cart_data.setVisibility(View.VISIBLE);
                    sv_cart_none.setVisibility(View.GONE);
                    if (Constant.map.size()==getCartDataListsSize(Constant.cartDataLists)){
                        Constant.allselect=1;
                        btn_all_select.setChecked(true);
                    }else {
                        Constant.allselect=2;
                        btn_all_select.setChecked(false);
                    }
//                    all_price =getPrice(Constant.map);
//                    tv_all_price.setText(all_price);
                    indentAdapter = new IndentAdapter(getActivity(),handler,layoutInflater);
                    mlv_cart_store.setAdapter(indentAdapter);
//                    elvCartStoreAdapter = new ElvCartStoreAdapter(cartDataLists,getActivity(),handler);
//                    elv_cart_store.setAdapter(elvCartStoreAdapter);
//                    for (int i = 0; i < elvCartStoreAdapter.getGroupCount(); i++) {
//                        elv_cart_store.expandGroup(i);
//                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private String getAllPrice(List<CartDataList> cartDataLists) {
        double x=0;
        for (int i=0;i<cartDataLists.size();i++){
            x+=cartDataLists.get(i).getDepotSum().doubleValue();
        }
        return VerifitionUtil.getDoubleRMBStringPrice(x);
    }

    //获取提交信息串
    public String getStringMap(){
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, String> entry : Constant.map.entrySet()) {
            stringBuffer.append(entry.getKey()+"~"+entry.getValue()+",");
        }
        return stringBuffer.toString();
    }
    public Map<String,String> getOnsaleMap(List<CartDataList> cartDataLists){
        Map<String,String> initMap = new HashMap<>();
        for (int i=0;i<cartDataLists.size();i++){
            for (int j=0;j<cartDataLists.get(i).getGoodsList().size();j++){
                if (cartDataLists.get(i).getGoodsList().get(j).isOnSale())
                initMap.put(cartDataLists.get(i).getGoodsList().get(j).getGoodsId()+"",cartDataLists.get(i).getGoodsList().get(j).getGoodsNumber()+"");
            }
        }
        return initMap;
    }
    public Map<String,String> getMap(List<CartDataList> cartDataLists){
        Map<String,String> initMap = new HashMap<>();
        for (int i=0;i<cartDataLists.size();i++){
            for (int j=0;j<cartDataLists.get(i).getGoodsList().size();j++){
                initMap.put(cartDataLists.get(i).getGoodsList().get(j).getGoodsId()+"",cartDataLists.get(i).getGoodsList().get(j).getGoodsNumber()+"");
            }
        }
        return initMap;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_all_select:
                //全选1,全不选0,不全选2
                if (Constant.allselect==1){
                    Constant.allselect=0;
                    tv_all_price.setText("¥0");
                    Constant.map.clear();
                    indentAdapter.notifyDataSetChanged();
                }else {
                    Constant.allselect=1;
                    Constant.map = getMap(Constant.cartDataLists);
//                    tv_all_price.setText(getAllPrice(Constant.cartDataLists));
                    indentAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_commit_order:
                //提交
                commitOrder();
                break;
            case R.id.tv_clear_no_use_goods:
                //清除失效商品
                showClearGoods();
                break;
        }
    }

    private void clearNoUseGoods() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.cleanDisabledCartGoods(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<Success>() {
            @Override
            public void success(Success success, Response response) {
                if (success.getStatus().equals("1")){
                    tv_clear_no_use_goods.setVisibility(View.GONE);
                    initData();
                }else {
                    ToastUtils.showToastCenter(getActivity(),success.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void commitOrder() {
//        Constant.FORM.setCar(cars);
//        Log.i("order_form", JSONObject.toJSONString(Constant.FORM));
        if (Constant.map.size()==0){
            ToastUtils.showToastCenter(getActivity(),"请选择下单商品~");
            return;
        }
        final ProgressHUD progressHUD = ProgressHUD.show(getActivity(), "提交中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.confirmOrderInfo(SharePreferencesUtils.getBtdToken(getActivity()),getStringMap(),"CNY", new CallbackSupport<OrderDetail>(progressHUD,getActivity()) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                    progressHUD.dismiss();
                if (orderDetail.getStatus().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_detail_back", orderDetail.getData());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    if (orderDetail.getErrorCode().equals("51103")){
                        DialogUtils.showDialogs(getActivity().getLayoutInflater(),getActivity(),orderDetail.getData().getErrorDepotName());
                    }else if (orderDetail.getErrorCode().equals("51105")){
                        if (orderDetail.getData().getErrorGoodsList().get(0).getGoodsError().equals("51014")){
                            ToastUtils.showToastCenter(getActivity(),"库存不足");
                        }else {
                            ToastUtils.showToastCenter(getActivity(),"存在失效商品");
                            initData();
                        }
                    }else {
                        ToastUtils.showToastCenter(getActivity(),orderDetail.getErrorMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    public int getCartDataListsSize(List<CartDataList> cartlist){
        int num =0;
        for (int i=0;i<cartlist.size();i++){
            for (int j=0;j<cartlist.get(i).getGoodsList().size();j++){
                ++num;
            }
        }
        return num;
    }
    @Override
    public void onStart() {
        super.onStart();
//        Constant.push_value=3;
        if (SharePreferencesUtils.getBtdToken(getActivity()).isEmpty()){
//            startActivity(new Intent(getActivity(),LoginActivity.class));
        }else {
//            btn_all_select.setChecked(true);
//            Constant.allselect=1;
            initData();
        }
    }
    private void showClearGoods() {
        View layout = getActivity().getLayoutInflater().inflate(R.layout.dialog_cart_clear,
                null);
        TextView cancelGo = (TextView) layout.findViewById(R.id.cancel_go);
        TextView sureNoGo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        /**监听对话框里面的button点击事件*/
        sureNoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearNoUseGoods();
                dialog.dismiss();
            }
        });
        cancelGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
