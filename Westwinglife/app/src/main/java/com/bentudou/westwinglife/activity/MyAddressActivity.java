package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.AddressAdapter;
import com.bentudou.westwinglife.adapter.PayOrderDetailAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Address;
import com.bentudou.westwinglife.json.AddressList;
import com.bentudou.westwinglife.json.CommitBack;
import com.bentudou.westwinglife.json.Coupon;
import com.bentudou.westwinglife.json.PayInfo;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.otherview.SwipeMenu;
import com.bentudou.westwinglife.otherview.SwipeMenuCreator;
import com.bentudou.westwinglife.otherview.SwipeMenuItem;
import com.bentudou.westwinglife.otherview.SwipeMenuListView;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.gunlei.app.ui.view.ProgressHUD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/26.
 */
public class MyAddressActivity extends BaseTitleActivity implements View.OnClickListener {
    public static final int REFRESH_ADDRESS=100;
    private SwipeMenuListView smlv_address;
    private TextView tv_no_address;
    private Button btn_add_address;
    private AddressAdapter addressAdapter;
    ProgressHUD progressHUD = null;
    private List<AddressList> addressLists;
    private String flag;
    Handler mHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==REFRESH_ADDRESS){
                initData();
            }
        }
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_address);
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
        super.setTitleText("收货地址");
        flag = getIntent().getStringExtra("flag");
        smlv_address = (SwipeMenuListView) findViewById(R.id.smlv_address);
        btn_add_address = (Button) findViewById(R.id.btn_add_address);
        tv_no_address = (TextView) findViewById(R.id.tv_no_address);
        btn_add_address.setOnClickListener(this);
        if ("get".equals(flag)){
            smlv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (null==addressLists.get(position).getIdCard()){
                        ToastUtils.showToastCenter(MyAddressActivity.this,"该地址没有身份证号,请修改!");
                    }else {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order_address", addressLists.get(position));
                        intent.putExtras(bundle);
                        setResult(RESULT_OK,intent);
                        finish();
                    }

                }
            });
        }
        initData();
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.shipAddressList(SharePreferencesUtils.getBtdToken(this),new CallbackSupport<Address>(progressHUD, this) {
            @Override
            public void success(Address address, Response response) {
                progressHUD.dismiss();
                if (address.getStatus().equals("1")){
                    if (null==address.getData()){
                        smlv_address.setVisibility(View.GONE);
                        tv_no_address.setVisibility(View.VISIBLE);
                    }else {
                        smlv_address.setVisibility(View.VISIBLE);
                        tv_no_address.setVisibility(View.GONE);
                        addressLists = address.getData();
                        addressAdapter = new AddressAdapter(addressLists,MyAddressActivity.this,mHander);
                        smlv_address.setAdapter(addressAdapter);
                        SwipeMenuCreator creator = new SwipeMenuCreator() {

                            @Override
                            public void create(SwipeMenu menu) {
                                // create "delete" item
                                SwipeMenuItem deleteItem = new SwipeMenuItem(
                                        getApplicationContext());
                                // set item background
                                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xEC,
                                        0x50, 0x50)));
                                // set item width
                                deleteItem.setWidth(dp2px(90));
                                // set a icon
                                deleteItem.setIcon(R.drawable.ic_delete);
                                // add to menu
                                menu.addMenuItem(deleteItem);
                            }
                        };
                        smlv_address.setMenuCreator(creator);
                        smlv_address.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                                deleteAddress(position);

                            }
                        });
                    }
                }else {
                    ToastUtils.showToastCenter(MyAddressActivity.this,address.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void deleteAddress(final int position) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.delUserAddress(SharePreferencesUtils.getBtdToken(this),addressLists.get(position).getUserAddressId(),new CallbackSupport<Success>(progressHUD, this) {
            @Override
            public void success(Success success, Response response) {
                if (success.getStatus().equals("1")){
                    addressLists.remove(position);
                    if (addressLists.isEmpty()){
                        smlv_address.setVisibility(View.GONE);
                        tv_no_address.setVisibility(View.VISIBLE);
                    }else{
                        addressAdapter.notifyDataSetChanged();
                    }

                    ToastUtils.showToastCenter(MyAddressActivity.this,"删除成功!");
                }else {
                    ToastUtils.showToastCenter(MyAddressActivity.this,success.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_address:
                startActivity(new Intent(MyAddressActivity.this,AddtheaddressActivity.class));
                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }
}
