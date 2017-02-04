package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.OrderListAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.MyOrder;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyListView;
import com.bentudou.westwinglife.view.XListView;
import com.gunlei.app.ui.view.ProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/28.
 */
@Deprecated
public class OrderListActivity extends BaseTitleActivity implements XListView.OnXListViewListener {
    private XListView mlv_order_list;
    private TextView tv_no_order;
    private List<MyOrderList> myOrderLists;
    private OrderListAdapter orderListAdapter;
    private Handler mHandler;
    int page = 1;
    int allPage = 0;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_order_list);
        noDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VerifitionUtil.isNetworkAvailable(context)) {
                    loadError(true);
                } else {
                    loadError(false);
                    initData();
                }
            }
        });
    }

    @Override
    protected void initView() {
        super.setTitleText("我的订单");
        mlv_order_list = (XListView) findViewById(R.id.mlv_order_list);
        tv_no_order = (TextView) findViewById(R.id.tv_no_order);
        mlv_order_list.setXListViewListener(this);
        mlv_order_list.setPullLoadEnable(XListView.FOOTER_SHOW);
        mlv_order_list.setFooterReady(true);
        mHandler = new Handler();
        mlv_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick","-----"+position);
                Intent intent = new Intent();
                intent.setClass(OrderListActivity.this, MyOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order_detail_data", myOrderLists.get(position-1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }
    private void initData() {
        final ProgressHUD progressHUD = ProgressHUD.show(this, "加载中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findOrderList(SharePreferencesUtils.getBtdToken(this),page,"0", new CallbackSupport<MyOrder>(progressHUD,this) {
            @Override
            public void success(MyOrder myOrder, Response response) {
                progressHUD.dismiss();
                if (myOrder.getStatus().equals("1")){
                    page=1;
                    if (myOrder.getData().getOrderList().getTotal()!=0){
                        if (myOrder.getData().getOrderList().getTotal()%5==0){
                            allPage = myOrder.getData().getOrderList().getTotal()/5;
                        }else {
                            allPage = myOrder.getData().getOrderList().getTotal()/5+1;
                        }
                        if (allPage==1){
                            mlv_order_list.setPullLoadEnable(XListView.FOOTER_WAIT);
                        }else {
                            mlv_order_list.setPullLoadEnable(XListView.FOOTER_SHOW);
                        }
                        mlv_order_list.setVisibility(View.VISIBLE);
                        tv_no_order.setVisibility(View.GONE);
                        myOrderLists = myOrder.getData().getOrderList().getRows();
                        orderListAdapter = new OrderListAdapter(myOrderLists,OrderListActivity.this);
                        mlv_order_list.setAdapter(orderListAdapter);
                    }else {
                        mlv_order_list.setVisibility(View.GONE);
                        tv_no_order.setVisibility(View.VISIBLE);
                    }
                }else{
                    ToastUtils.showToastCenter(OrderListActivity.this,"订单列表出现错误~");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService service = RTHttpClient.create(PotatoService.class);
                service.findOrderList(SharePreferencesUtils.getBtdToken(OrderListActivity.this),1,"0", new CallbackSupport<MyOrder>(OrderListActivity.this) {
                    @Override
                    public void success(MyOrder myOrder, Response response) {
                        if (myOrder.getStatus().equals("1")){
                            page=1;
                            if (myOrder.getData().getOrderList().getTotal()!=0){
                                if (myOrder.getData().getOrderList().getTotal()%5==0){
                                    allPage = myOrder.getData().getOrderList().getTotal()/5;
                                }else {
                                    allPage = myOrder.getData().getOrderList().getTotal()/5+1;
                                }
                                if (allPage==1){
                                    mlv_order_list.setPullLoadEnable(XListView.FOOTER_WAIT);
                                }else {
                                    mlv_order_list.setPullLoadEnable(XListView.FOOTER_SHOW);
                                }
                                mlv_order_list.setVisibility(View.VISIBLE);
                                tv_no_order.setVisibility(View.GONE);
                                myOrderLists = myOrder.getData().getOrderList().getRows();
                                orderListAdapter = new OrderListAdapter(myOrderLists,OrderListActivity.this);
                                mlv_order_list.setAdapter(orderListAdapter);
                                onLoad();
                            }else {
                                mlv_order_list.setVisibility(View.GONE);
                                tv_no_order.setVisibility(View.VISIBLE);
                            }
                        }else{
                            ToastUtils.showToastCenter(OrderListActivity.this,"订单列表出现错误~");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });

            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        Log.d("onLoadMore", "-----onLoadMore: "+page+"---"+allPage);
        if (++page>allPage){
            mlv_order_list.setPullLoadEnable(XListView.FOOTER_WAIT);
            onLoad();
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 模拟读取买好车数据接口
                final PotatoService service = RTHttpClient.create(PotatoService.class);
                service.findOrderList(SharePreferencesUtils.getBtdToken(OrderListActivity.this),page,"0", new CallbackSupport<MyOrder>(OrderListActivity.this) {
                    @Override
                    public void success(MyOrder myOrder, Response response) {
                        if (myOrder.getStatus().equals("1")){
                            if (allPage==page){
                                mlv_order_list.setPullLoadEnable(XListView.FOOTER_WAIT);
                            }
                            myOrderLists.addAll(myOrder.getData().getOrderList().getRows());
                            orderListAdapter.notifyDataSetChanged();
                            onLoad();
                        }else{
                            ToastUtils.showToastCenter(OrderListActivity.this,"订单列表出现错误~");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
            }
        }, 2000);
    }
    private void onLoad() {
        mlv_order_list.stopRefresh();
        mlv_order_list.stopLoadMore();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str =  formatter.format(curDate);
        mlv_order_list.setRefreshTime(str);
    }
    @Override
    protected void onPause() {
        super.onPause();
        page = 1;
    }
}
