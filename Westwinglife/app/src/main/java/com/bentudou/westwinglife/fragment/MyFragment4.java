package com.bentudou.westwinglife.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.MyOrderDetailActivity;
import com.bentudou.westwinglife.adapter.OrderListAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.MyOrder;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.XListView;
import com.gunlei.app.ui.view.ProgressHUD;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/10/19.
 */
public class MyFragment4 extends Fragment implements XListView.OnXListViewListener{
    private XListView mlv_order_list;
    private TextView tv_no_order;
    private List<MyOrderList> myOrderLists;
    private OrderListAdapter orderListAdapter;
    private Handler mHandler;
    int page = 1;
    int allPage = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =inflater.inflate(R.layout.page3,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mlv_order_list = (XListView) view.findViewById(R.id.mlv_order_list);
        tv_no_order = (TextView) view.findViewById(R.id.tv_no_order);
        mlv_order_list.setXListViewListener(this);
        mlv_order_list.setPullLoadEnable(XListView.FOOTER_SHOW);
        mlv_order_list.setFooterReady(true);
        mHandler = new Handler();
        mlv_order_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("onItemClick","-----"+position);
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyOrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order_detail_data", myOrderLists.get(position-1));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        initData();
    }
    private void initData() {
        final ProgressHUD progressHUD = ProgressHUD.show(getActivity(), "加载中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findOrderList(SharePreferencesUtils.getBtdToken(getActivity()),page,"3", new CallbackSupport<MyOrder>(progressHUD,getActivity()) {
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
                        orderListAdapter = new OrderListAdapter(myOrderLists,getActivity());
                        mlv_order_list.setAdapter(orderListAdapter);
                    }else {
                        mlv_order_list.setVisibility(View.GONE);
                        tv_no_order.setVisibility(View.VISIBLE);
                    }
                }else{
                    ToastUtils.showToastCenter(getActivity(),"订单列表出现错误~");
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
                service.findOrderList(SharePreferencesUtils.getBtdToken(getActivity()),1,"3", new CallbackSupport<MyOrder>(getActivity()) {
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
                                orderListAdapter = new OrderListAdapter(myOrderLists,getActivity());
                                mlv_order_list.setAdapter(orderListAdapter);
                                onLoad();
                            }else {
                                mlv_order_list.setVisibility(View.GONE);
                                tv_no_order.setVisibility(View.VISIBLE);
                            }
                        }else{
                            ToastUtils.showToastCenter(getActivity(),"订单列表出现错误~");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });

            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
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
                service.findOrderList(SharePreferencesUtils.getBtdToken(getActivity()),page,"3", new CallbackSupport<MyOrder>(getActivity()) {
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
                            ToastUtils.showToastCenter(getActivity(),"订单列表出现错误~");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
            }
        }, 1000);
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
    public void onStart() {
        super.onStart();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        page = 1;
    }
}
