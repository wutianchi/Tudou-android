package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.AddtheaddressActivity;
import com.bentudou.westwinglife.activity.MyAddressActivity;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.AddressList;
import com.bentudou.westwinglife.json.HeadGrid;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.ImageWidthHeight;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *Created by lzz on 2016/3/2.
 * 分类二级适配器
 */
public class AddressAdapter extends BaseAdapter {
    List<AddressList> list;
    Context context;
    Handler mHander;
    int mScreentWidth;
    public AddressAdapter(List<AddressList> list, Context context,Handler mHander) {
        this.list = list;
        this.context = context;
        this.mHander = mHander;
    }

    @Override
    public int getCount() {
        if (list==null) {
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (list==null) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_address,null);
            classHolder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            classHolder.tv_phone = (TextView)convertView.findViewById(R.id.tv_phone);
            classHolder.tv_shenfenzheng = (TextView)convertView.findViewById(R.id.tv_shenfenzheng);
            classHolder.tv_address_detail = (TextView)convertView.findViewById(R.id.tv_address_detail);
            classHolder.tv_zipcode = (TextView)convertView.findViewById(R.id.tv_zipcode);
            classHolder.tv_change_address = (TextView)convertView.findViewById(R.id.tv_change_address);
            classHolder.tv_address_select = (TextView)convertView.findViewById(R.id.tv_address_select);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        final AddressList addressList = list.get(position);
        classHolder.tv_name.setText(addressList.getConsignee());
        classHolder.tv_phone.setText(addressList.getTel());
        if (null==addressList.getIdCard()){
            classHolder.tv_shenfenzheng.setText("身份证号");
        }else {
            classHolder.tv_shenfenzheng.setText(VerifitionUtil.getIdCard(addressList.getIdCard()));
        }
        classHolder.tv_address_detail.setText(addressList.getProcinceCnName()+addressList.getCityCnName()
                +addressList.getDistrictCnName()+addressList.getAddress());
        classHolder.tv_zipcode.setText(addressList.getZipcode());
        if (addressList.getIsDefaultAddress()!=0){
            classHolder.tv_address_select.setSelected(true);
            classHolder.tv_address_select.setText("已设为默认地址");
        }else {
            classHolder.tv_address_select.setSelected(false);
            classHolder.tv_address_select.setText("设为默认地址");
        }
        classHolder.tv_address_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSetMoren(addressList.getUserAddressId());
            }
        });
        classHolder.tv_change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, AddtheaddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("change_address", addressList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;

    }
//设置默认地址
    private void toSetMoren(int addressId) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.updateAddressIsDefault(SharePreferencesUtils.getBtdToken(context),addressId,1, new CallbackSupport<Success>(context) {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    Message msg = new Message();
                    msg.what = MyAddressActivity.REFRESH_ADDRESS;
                    mHander.sendMessage(msg);
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

    static class ClassHolder{
        TextView tv_name,tv_phone,tv_shenfenzheng,tv_address_detail,tv_zipcode,tv_change_address,tv_address_select;
    }

}
