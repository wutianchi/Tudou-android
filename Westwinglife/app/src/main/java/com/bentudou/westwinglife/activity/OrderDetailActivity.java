package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.OrderDetailAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.AddressList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OrderDetailBack;
import com.bentudou.westwinglife.json.OrderListData;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyListView;
import com.gunlei.app.ui.view.ProgressHUD;

import java.util.List;
import java.util.Map;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/28.
 */
public class OrderDetailActivity extends BaseTitleActivity implements View.OnClickListener {
    private LinearLayout llt_select_address,llt_clear_note;
    private MyListView mlv_order_detail;
    private ImageView iv_address_red;
    private EditText et_notes;
    private TextView tv_select_address,tv_commit_order_detail,tv_select_name,
            tv_select_shenfen,tv_select_detail_address,tv_select_zipcode,tv_select_line,tv_focuse;
    private OrderDetailAdapter orderDetailAdapter;
    private OrderDetailBack orderDetailBack;
    AddressList addressList;
    private int livegoods;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_order_detail);
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
        super.setTitleText("订单明细");
        livegoods = getIntent().getIntExtra("livegoods",1);
        orderDetailBack = (OrderDetailBack) getIntent().getSerializableExtra("order_detail_back");
        llt_select_address = (LinearLayout) findViewById(R.id.llt_select_address);
        llt_clear_note = (LinearLayout) findViewById(R.id.llt_clear_note);
        mlv_order_detail = (MyListView) findViewById(R.id.mlv_order_detail);
        iv_address_red = (ImageView) findViewById(R.id.iv_address_red);
        tv_focuse = (TextView) findViewById(R.id.tv_focuse);
        tv_focuse.setFocusable(true);
        tv_focuse.setFocusableInTouchMode(true);
        tv_focuse.requestFocus();
        et_notes = (EditText) findViewById(R.id.et_notes);
        tv_select_address = (TextView) findViewById(R.id.tv_select_address);
        tv_commit_order_detail = (TextView) findViewById(R.id.tv_commit_order_detail);
        tv_select_name = (TextView) findViewById(R.id.tv_select_name);
        tv_select_shenfen = (TextView) findViewById(R.id.tv_select_shenfen);
        tv_select_detail_address = (TextView) findViewById(R.id.tv_select_detail_address);
        tv_select_zipcode = (TextView) findViewById(R.id.tv_select_zipcode);
        tv_select_line = (TextView) findViewById(R.id.tv_select_line);
        llt_select_address.setOnClickListener(this);
        tv_commit_order_detail.setOnClickListener(this);
        llt_clear_note.setOnClickListener(this);
        et_notes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
                    llt_clear_note.setVisibility(View.VISIBLE);
                }else {
                    llt_clear_note.setVisibility(View.INVISIBLE);
                }
            }
        });
        initData();
    }

    private void initData() {
        if (null!=orderDetailBack.getDefaultAddress()){
            addressList = orderDetailBack.getDefaultAddress();
            initAddress();
        }
        orderDetailAdapter = new OrderDetailAdapter(orderDetailBack.getOrderList(),this);
        mlv_order_detail.setAdapter(orderDetailAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llt_select_address:
                startActivityForResult(new Intent(OrderDetailActivity.this,MyAddressActivity.class).putExtra("flag","get"),1);
                break;
            case R.id.tv_commit_order_detail:
                if (livegoods==1){
                    commitOrder();
                }else {
                    liveCommitOrder();
                }
                break;
            case R.id.llt_clear_note:
                et_notes.setText("");
                break;
        }
    }

    private void commitOrder() {
        if (null==addressList){
            ToastUtils.showToastCenter(this,"请选择地址!");
            return;
        }
        //正式下单
        final ProgressHUD progressHUD = ProgressHUD.show(this, "提交中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.createOrderInfo(SharePreferencesUtils.getBtdToken(this),getStringMap(),
                addressList.getUserAddressId(),true,"CNY",et_notes.getText().toString(), new CallbackSupport<OrderDetail>(progressHUD,this) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                progressHUD.dismiss();
                if (orderDetail.getStatus().equals("1")){
                    if (null!=orderDetail.getData()&&orderDetail.getData().getOrderId()>0){
                        startActivity(new Intent(context, PayStyleSelectActivity.class).putExtra("orderId",orderDetail.getData().getOrderId()));
                    }else {
                        startActivity(new Intent(OrderDetailActivity.this,NewOrderListActivity.class));
                    }
                    finish();
                }else{
                    ToastUtils.showToastCenter(OrderDetailActivity.this,orderDetail.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
    private void liveCommitOrder() {
        if (null==addressList){
            ToastUtils.showToastCenter(this,"请选择地址!");
            return;
        }
        //正式下单
        final ProgressHUD progressHUD = ProgressHUD.show(this, "提交中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.createOrderInfo(SharePreferencesUtils.getBtdToken(this),getStringMap(),addressList.getUserAddressId(),true,
                "CNY",true,et_notes.getText().toString(), new CallbackSupport<OrderDetail>(progressHUD,this) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                progressHUD.dismiss();
                if (orderDetail.getStatus().equals("1")){
                    if (null!=orderDetail.getData()&&orderDetail.getData().getOrderId()>0){
                        startActivity(new Intent(context, PayStyleSelectActivity.class).putExtra("orderId",orderDetail.getData().getOrderId()));
                    }else {
                        startActivity(new Intent(OrderDetailActivity.this,NewOrderListActivity.class));
                    }
                    finish();
                }else{
                    ToastUtils.showToastCenter(OrderDetailActivity.this,orderDetail.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
    private String getStringMap(){
        StringBuffer stringBuffer = new StringBuffer();
        List<OrderListData> orderListData =  orderDetailBack.getOrderList();
        for (int i=0;i<orderListData.size();i++) {
            for (int j=0;j<orderListData.get(i).getGoodsList().size();j++){
                stringBuffer.append(orderListData.get(i).getGoodsList().get(j).getGoodsId()+"~"+orderListData.get(i).getGoodsList().get(j).getGoodsNumber()+",");
            }
        }
        return stringBuffer.toString();
    }
    private void initAddress(){
        tv_select_address.setVisibility(View.GONE);
        iv_address_red.setVisibility(View.VISIBLE);
        tv_select_line.setVisibility(View.VISIBLE);
        tv_select_name.setVisibility(View.VISIBLE);
        tv_select_shenfen.setVisibility(View.VISIBLE);
        tv_select_detail_address.setVisibility(View.VISIBLE);
        tv_select_zipcode.setVisibility(View.VISIBLE);
        tv_select_name.setText(addressList.getConsignee()+addressList.getTel());
        tv_select_shenfen.setText(VerifitionUtil.getIdCard(addressList.getIdCard()));
        tv_select_detail_address.setText(addressList.getProcinceCnName()+addressList.getCityCnName()+addressList.getDistrictCnName()+addressList.getAddress());
        tv_select_zipcode.setText(addressList.getZipcode());
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                addressList = (AddressList) data.getSerializableExtra("order_address");
                Log.d("addressList", "onActivityResult: -----ok"+addressList.getConsignee());
                initAddress();
                break;
            default:
                Log.d("addressList", "onActivityResult: -----no"+requestCode);
                break;
        }
    }
}
