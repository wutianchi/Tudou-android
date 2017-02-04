package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.AddressList;
import com.bentudou.westwinglife.json.Citys;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by yaoguang on 2016/6/28.
 * 新增修改地址
 */
public class AddtheaddressActivity extends BaseTitleActivity {
    private LinearLayout select_address;
    private TextView xuan_address;
    private EditText et_zipcode;
    private Button b_keep_address;
    private EditText detailed_address,et_address_name,et_address_iphone,et_address_idcard;
    private int cityid,townid,countyid;
    private String city,town;
    private AddressList addressList;
    public static int RESULT_OK = 5;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_add_adress);
    }
    @Override
    protected void initView() {
        select_address = (LinearLayout) findViewById(R.id.select_address);
        xuan_address = (TextView) findViewById(R.id.xuan_address);
        et_zipcode= (EditText) findViewById(R.id.et_zipcode);
        et_zipcode.setInputType(InputType.TYPE_NULL);
        b_keep_address= (Button) findViewById(R.id.b_keep_address);
        detailed_address= (EditText) findViewById(R.id.detailed_address);
        et_address_name= (EditText) findViewById(R.id.et_address_name);
        et_address_iphone= (EditText) findViewById(R.id.et_address_iphone);
        et_address_idcard= (EditText) findViewById(R.id.et_address_idcard);
        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            super.setTitleText("添加收货地址");
            b_keep_address.setOnClickListener(new MyOnclickListener());
        }else{
            addressList = (AddressList) bundle.getSerializable("change_address");
            super.setTitleText("修改收货地址");
            b_keep_address.setText("修改");
            xuan_address.setText(addressList.getProcinceCnName()+addressList.getCityCnName()+addressList.getDistrictCnName()+"");
            detailed_address.setText(addressList.getAddress());
            et_zipcode.setText(addressList.getZipcode());
            et_address_name.setText(addressList.getConsignee());
            et_address_iphone.setText(addressList.getTel());
            et_address_idcard.setText(addressList.getIdCard());
            b_keep_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String detailedAddress=detailed_address.getText().toString();
                    String address=xuan_address.getText().toString();
                    String zipcode=et_zipcode.getText().toString();
                    String address_name=et_address_name.getText().toString();
                    String address_iphone=et_address_iphone.getText().toString();
                    String address_idcard=et_address_idcard.getText().toString();

                    if(!detailedAddress.equals("")&&!address.equals("")&&!zipcode.equals("")&&!address_name.equals("")&&
                      !address_idcard.equals("")&&!address_iphone.equals("")){
                        if(address_iphone.length()!=11){
                            ToastUtils.showToastCenter(AddtheaddressActivity.this,"请输入正确的手机号码");
                        }else if(address_idcard.length()!=18||address_idcard.length()!=15){
                            ToastUtils.showToastCenter(AddtheaddressActivity.this,"请输入正确的身份证号码");
                        }
//                        else if(!(VerifitionUtil.noexpression(detailedAddress)&&VerifitionUtil.noexpression(address_name))){
//                            Toast.makeText(AddtheaddressActivity.this,"含有非法字符，请输入正确的信息",Toast.LENGTH_SHORT).show();
//                        }
                        else{
                            if((addressList.getProcinceCnName()+addressList.getCityCnName()+addressList.getDistrictCnName()).equals(address)){
                                cityid=addressList.getProvince();
                                townid=addressList.getCity();
                                countyid=addressList.getDistrict();
                            }
                            final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                            potatoService.alterAddress(SharePreferencesUtils.getBtdToken(AddtheaddressActivity.this),et_address_name.getText().toString(),detailed_address.getText().toString(),336,cityid,townid,countyid,et_address_iphone.getText().toString(),et_address_idcard.getText().toString(),addressList.getUserAddressId(),new Callback<Citys>() {
                                @Override
                                public void success(Citys city, Response response) {

                                    if(city.getErrorCode().equals("60004")){
                                        ToastUtils.showToastCenter(AddtheaddressActivity.this,"身份证号码不符合标准");
                                    }else if(city.getStatus().equals("1")){
                                        ToastUtils.showToastCenter(AddtheaddressActivity.this,"修改成功！");
                                        finish();
                                    }
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    ToastUtils.showToastCenter(AddtheaddressActivity.this,"修改失败！");
                                }
                            });
                        }
                    }else{
                        ToastUtils.showToastCenter(AddtheaddressActivity.this,"请完善您的信息");
                    }
                }
            });
        }
        select_address.setOnClickListener(new MyOnclickListener());
    }
    public class MyOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.select_address :
                Intent intent=new Intent(AddtheaddressActivity.this,ShowCityActivity.class);
                startActivityForResult(intent,1);
                break;
                case R.id.b_keep_address :
                    String detailedAddress=detailed_address.getText().toString();
                    String address=xuan_address.getText().toString();
                    String zipcode=et_zipcode.getText().toString();
                    String address_name=et_address_name.getText().toString();
                    String address_iphone=et_address_iphone.getText().toString();
                    String address_idcard=et_address_idcard.getText().toString();
                    if(!detailedAddress.equals("")&&!address.equals("")&&!zipcode.equals("")&&!address_name.equals("")&&
                            !address_idcard.equals("")&&!address_iphone.equals("")){
                         if(address_iphone.length()!=11){
                             ToastUtils.showToastCenter(AddtheaddressActivity.this,"请输入正确的手机号码");
                         }else if(address_idcard.length()!=18){
                             ToastUtils.showToastCenter(AddtheaddressActivity.this,"请输入正确的身份证号码");
                         }
//                         else if(!(VerifitionUtil.noexpression(detailedAddress)&&VerifitionUtil.noexpression(address_name))){
//                             Toast.makeText(AddtheaddressActivity.this,"含有非法字符，请输入正确的信息",Toast.LENGTH_SHORT).show();
//                         }
                         else{
                             final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                             potatoService.keepAddress(SharePreferencesUtils.getBtdToken(AddtheaddressActivity.this),address_name,detailedAddress,336,cityid,townid,countyid,address_iphone,address_idcard,new Callback<Citys>() {
                                 @Override
                                 public void success(Citys city, Response response) {
                                     if(city.getErrorCode().equals("60004")){
                                         ToastUtils.showToastCenter(AddtheaddressActivity.this,"身份证号码不符合标准");
                                     }else if(city.getStatus().equals("1")){
                                         ToastUtils.showToastCenter(AddtheaddressActivity.this,"保存成功！");
                                         finish();
                                     }
                                 }
                                 @Override
                                 public void failure(RetrofitError error) {
                                     ToastUtils.showToastCenter(AddtheaddressActivity.this,"保存失败！");
                                 }
                             });
                         }
                    }else{
                        ToastUtils.showToastCenter(AddtheaddressActivity.this,"请完善您的信息");
                    }
                    break;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("zipCode") == null) {
                    return;
                } else {
                    cityid = Integer.parseInt(data.getStringExtra("cityID"));
                    city = data.getStringExtra("city");
                    town = data.getStringExtra("town");
                    townid = Integer.parseInt(data.getStringExtra("townID"));
                    String zipcode = data.getStringExtra("zipCode");
                    String county = data.getStringExtra("county");
                    countyid = Integer.parseInt(data.getStringExtra("countyID"));
                    et_zipcode.setText(zipcode.toString() + "");
                    xuan_address.setText(city.toString() + town.toString() + county.toString() + "");
                }
            }
        }


    }
}
