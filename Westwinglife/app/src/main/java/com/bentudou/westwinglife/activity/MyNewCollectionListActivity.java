package com.bentudou.westwinglife.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.MyNewCollectionAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.FavoiteInfo;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.json.UserCartNumber;
import com.bentudou.westwinglife.json.UserFavoite;
import com.bentudou.westwinglife.library.RefreshSwipeMenuListView;
import com.bentudou.westwinglife.library.SwipeMenu;
import com.bentudou.westwinglife.library.SwipeMenuCreator;
import com.bentudou.westwinglife.library.SwipeMenuItem;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.view.ProgressHUD;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/12.
 * 我的收藏
 */
public class MyNewCollectionListActivity extends BaseTitleActivity implements View.OnClickListener,RefreshSwipeMenuListView.OnRefreshListener {
    public static final int CART_COUNT = 21;
    public static final int CART_SHIXIAO = 22;
    public static final int CART_CLEAR = 23;
    private RefreshSwipeMenuListView cslv_list;
    private List<FavoiteInfo> favoiteInfoList;
    private MyNewCollectionAdapter myCollectionAdapter;
    private ImageView tv_pay_order;
    private RelativeLayout rlt_have_collection;
    private TextView tv_no_collect,tv_cart_num,tv_clear_shixiao_goods;
    private ProgressHUD progressHUD = null;
    private View mFooterView2;
    private int page=1;
    private int total;
    private boolean isShixiao=false;

    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==CART_COUNT){
                tv_cart_num.setVisibility(View.VISIBLE);
                tv_cart_num.setText(String.valueOf(msg.arg1));
            }else if (msg.what==CART_SHIXIAO){
                Log.d("myHandler", "-----handleMessage: 失效");
                isShixiao=true;
            }else if (msg.what==CART_CLEAR){
                clearShixiaoGoods();
            }
        }
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_new_collection);
    }

    @Override
    protected void initView() {
        super.setTitleText("我的收藏");
        mFooterView2 = layoutInflater.inflate(R.layout.mfoot_2,null);
        tv_clear_shixiao_goods = (TextView) mFooterView2.findViewById(R.id.tv_clear_shixiao_goods);
        rlt_have_collection = (RelativeLayout) findViewById(R.id.rlt_have_collection);
        tv_no_collect = (TextView) findViewById(R.id.tv_no_collect);
        tv_cart_num = (TextView) findViewById(R.id.tv_cart_num);
        cslv_list = (RefreshSwipeMenuListView) findViewById(R.id.cslv_list);
//        srl_fresh_collection = (SwipeRefreshLayout) findViewById(R.id.srl_fresh_collection);
        tv_pay_order = (ImageView) findViewById(R.id.tv_pay_order);
        tv_pay_order.setOnClickListener(this);
        tv_clear_shixiao_goods.setOnClickListener(this);
        cslv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MyNewCollectionListActivity.this,GoodsDetailActivity.class).putExtra("goodsId",String.valueOf(favoiteInfoList.get(position-1).getGoodsId())));
            }
        });
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findCollectPage(SharePreferencesUtils.getBtdToken(this),1,100,new CallbackSupport<UserFavoite>(progressHUD, this) {
            @Override
            public void success(UserFavoite userFavoite, Response response) {
                progressHUD.dismiss();
                page=1;
                if (userFavoite.getStatus().equals("1")){
                    total=userFavoite.getData().getTotal();
                    if (0==userFavoite.getData().getTotal()){
                        rlt_have_collection.setVisibility(View.GONE);
                        tv_no_collect.setVisibility(View.VISIBLE);
                    }else {
                        tv_no_collect.setVisibility(View.GONE);
                        rlt_have_collection.setVisibility(View.VISIBLE);
                        favoiteInfoList = userFavoite.getData().getRows();
                        myCollectionAdapter = new MyNewCollectionAdapter(MyNewCollectionListActivity.this,favoiteInfoList,myHandler);
                        cslv_list.setAdapter(myCollectionAdapter);
                        cslv_list.setListViewMode(RefreshSwipeMenuListView.HEADER);
                        cslv_list.setOnRefreshListener(MyNewCollectionListActivity.this);
                        SwipeMenuCreator creator = new SwipeMenuCreator() {
                            @Override
                            public void create(SwipeMenu menu) {
                                // 创建删除选项
                                SwipeMenuItem argeeItem = new SwipeMenuItem(getApplicationContext());
                                argeeItem.setBackground(new ColorDrawable(getResources().getColor(R.color.del)));
                                argeeItem.setWidth(dp2px(80, getApplicationContext()));
                                argeeItem.setTitle("删除");
                                argeeItem.setTitleSize(16);
                                argeeItem.setTitleColor(Color.WHITE);
                                menu.addMenuItem(argeeItem);
                            }
                        };
                        cslv_list.setMenuCreator(creator);
                        cslv_list.setOnMenuItemClickListener(new RefreshSwipeMenuListView.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                                switch (index) {
                                    case 0: //第一个选项
                                        deleteMessage(favoiteInfoList.get(position).getGoodsId(),position);
//                                        del(position,cslv_list.getChildAt(position+1-cslv_list.getFirstVisiblePosition()));
                                        break;
                                }
                            }
                        });
                    }


                }else {
                    ToastUtils.showToastCenter(MyNewCollectionListActivity.this,userFavoite.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(MyNewCollectionListActivity.this,"读取收藏商品失败!");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    if (isShixiao){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cslv_list.removeFooterView(mFooterView2);
                                cslv_list.addFooterView(mFooterView2);
                                myCollectionAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onRefresh() {
        cslv_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService service = RTHttpClient.create(PotatoService.class);
                service.findCollectPage(SharePreferencesUtils.getBtdToken(MyNewCollectionListActivity.this),1,100,new CallbackSupport<UserFavoite>(MyNewCollectionListActivity.this) {
                    @Override
                    public void success(UserFavoite userFavoite, Response response) {
                        page=1;
                        total=userFavoite.getData().getTotal();
                        if (userFavoite.getStatus().equals("1")){
                            if (0==userFavoite.getData().getTotal()){
                                rlt_have_collection.setVisibility(View.GONE);
                                tv_no_collect.setVisibility(View.VISIBLE);
                            }else {
                                tv_no_collect.setVisibility(View.GONE);
                                rlt_have_collection.setVisibility(View.VISIBLE);
                                favoiteInfoList.clear();
                                favoiteInfoList.addAll(userFavoite.getData().getRows());
                                myCollectionAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        ToastUtils.showToastCenter(MyNewCollectionListActivity.this,"刷新失败!");
                    }
                });
                cslv_list.complete();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        if (total==favoiteInfoList.size()){
            cslv_list.complete();
            if (isShixiao){
                cslv_list.removeFooterView(mFooterView2);
                cslv_list.addFooterView(mFooterView2);
                myCollectionAdapter.notifyDataSetChanged();
            }
            return;
        }
        cslv_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService service = RTHttpClient.create(PotatoService.class);
                service.findCollectPage(SharePreferencesUtils.getBtdToken(MyNewCollectionListActivity.this),++page,100,new CallbackSupport<UserFavoite>(MyNewCollectionListActivity.this) {
                    @Override
                    public void success(UserFavoite userFavoite, Response response) {
                        if (userFavoite.getStatus().equals("1")){
                            if (0==userFavoite.getData().getTotal()){
                                rlt_have_collection.setVisibility(View.GONE);
                                tv_no_collect.setVisibility(View.VISIBLE);
                            }else {
                                tv_no_collect.setVisibility(View.GONE);
                                rlt_have_collection.setVisibility(View.VISIBLE);
                                favoiteInfoList.addAll(userFavoite.getData().getRows());
                                myCollectionAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        ToastUtils.showToastCenter(MyNewCollectionListActivity.this,"加载更多失败!");
                    }
                });
                cslv_list.complete();
            }
        },2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_pay_order:
                startActivity(new Intent(MyNewCollectionListActivity.this,ShoppingCartActivity.class));
                break;
            case R.id.tv_clear_shixiao_goods:
                clearShixiaoGoods();
                break;//清除失效商品
        }
    }
    //清除失效商品
    private void clearShixiaoGoods() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.cleanDisabledUserCollectGoods(SharePreferencesUtils.getBtdToken(context),new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    isShixiao=false;
                    cslv_list.removeFooterView(mFooterView2);
                    initClearData();
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"清除失效商品失败!");
            }
        });
    }

    private void initClearData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findCollectPage(SharePreferencesUtils.getBtdToken(this),1,100,new CallbackSupport<UserFavoite>(progressHUD, this) {
            @Override
            public void success(UserFavoite userFavoite, Response response) {
                progressHUD.dismiss();
                page=1;
                if (userFavoite.getStatus().equals("1")){
                    total=userFavoite.getData().getTotal();
                    if (0==userFavoite.getData().getTotal()){
                        rlt_have_collection.setVisibility(View.GONE);
                        tv_no_collect.setVisibility(View.VISIBLE);
                    }else {
                        tv_no_collect.setVisibility(View.GONE);
                        rlt_have_collection.setVisibility(View.VISIBLE);
                        favoiteInfoList = userFavoite.getData().getRows();
                        myCollectionAdapter = new MyNewCollectionAdapter(MyNewCollectionListActivity.this,favoiteInfoList,myHandler);
                        isShixiao=false;
                        cslv_list.removeFooterView(mFooterView2);
                        cslv_list.setAdapter(myCollectionAdapter);
                        cslv_list.setListViewMode(RefreshSwipeMenuListView.HEADER);
                        cslv_list.setOnRefreshListener(MyNewCollectionListActivity.this);
                        SwipeMenuCreator creator = new SwipeMenuCreator() {
                            @Override
                            public void create(SwipeMenu menu) {
                                // 创建删除选项
                                SwipeMenuItem argeeItem = new SwipeMenuItem(getApplicationContext());
                                argeeItem.setBackground(new ColorDrawable(getResources().getColor(R.color.del)));
                                argeeItem.setWidth(dp2px(80, getApplicationContext()));
                                argeeItem.setTitle("删除");
                                argeeItem.setTitleSize(16);
                                argeeItem.setTitleColor(Color.WHITE);
                                menu.addMenuItem(argeeItem);
                            }
                        };
                        cslv_list.setMenuCreator(creator);
                        cslv_list.setOnMenuItemClickListener(new RefreshSwipeMenuListView.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                                switch (index) {
                                    case 0: //第一个选项
                                        deleteMessage(favoiteInfoList.get(position).getGoodsId(),position);
//                                        del(position,cslv_list.getChildAt(position+1-cslv_list.getFirstVisiblePosition()));
                                        break;
                                }
                            }
                        });
                    }


                }else {
                    ToastUtils.showToastCenter(MyNewCollectionListActivity.this,userFavoite.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(MyNewCollectionListActivity.this,"获取消息失败!");
            }
        });
    }

    //    /**
//     * 删除item动画
//     * @param index
//     * @param v
//     */
//    private void del(final int index, View v){
//        final Animation animation = (Animation) AnimationUtils.loadAnimation(v.getContext(), R.anim.list_anim);
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            public void onAnimationStart(Animation animation) {}
//            public void onAnimationRepeat(Animation animation) {}
//            public void onAnimationEnd(Animation animation) {
//                deleteMessage(favoiteInfoList.get(index).getGoodsId(),index);
//                animation.cancel();
//            }
//        });
//
//        v.startAnimation(animation);
//    }
    private void deleteMessage(int goodsId, final int position) {
        Log.d("deleteMessage-----","-"+goodsId+"-"+position);
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.delCollect(SharePreferencesUtils.getBtdToken(context),goodsId,new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    ToastUtils.showToastCenter(context,"删除收藏成功!");
                    favoiteInfoList.remove(position);
                    if (favoiteInfoList.size()>0){
                        myCollectionAdapter.notifyDataSetChanged();
                    }else {
                        rlt_have_collection.setVisibility(View.GONE);
                        tv_no_collect.setVisibility(View.VISIBLE);
                    }
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"删除收藏失败!");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
        initCart();
    }

    private void initCart() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.getCartCount(SharePreferencesUtils.getBtdToken(context),new Callback<UserCartNumber>() {
            @Override
            public void success(UserCartNumber addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    if (addSub.getData()>0){
                        tv_cart_num.setVisibility(View.VISIBLE);
                        tv_cart_num.setText(String.valueOf(addSub.getData()));
                    }else {
                        tv_cart_num.setVisibility(View.GONE);
                    }
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    public  int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
