package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.CouponInfo;
import com.bentudou.westwinglife.json.CouponListData;
import com.bentudou.westwinglife.json.GetCoupon;
import com.bentudou.westwinglife.json.GetCouponList;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 *Created by lzz on 2016/3/2.
 * 领取优惠券列表适配器
 */
public class GetCouponAdapter extends BaseAdapter {
    List<CouponInfo> list;
    Context context;
    public GetCouponAdapter(List<CouponInfo> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        ClassHolder classHolder;
        if(convertView==null){
            classHolder = new ClassHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_get_coupon,null);
            classHolder.tv_coupon_num = (TextView)convertView.findViewById(R.id.tv_coupon_num);
            classHolder.tv_coupon_name = (TextView)convertView.findViewById(R.id.tv_coupon_name);
            classHolder.tv_coupon_can_use_price = (TextView)convertView.findViewById(R.id.tv_coupon_can_use_price);
            classHolder.tv_can_use_time = (TextView)convertView.findViewById(R.id.tv_can_use_time);
            classHolder.tv_to_getcoupon = (TextView)convertView.findViewById(R.id.tv_to_getcoupon);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        final CouponInfo couponInfo = list.get(position);
        if (couponInfo.getCouponType().equals("00")){
            classHolder.tv_coupon_num.setText(VerifitionUtil.getStringZhengPrice(couponInfo.getCouponPrice()));
        }else if (couponInfo.getCouponType().equals("01")){
            classHolder.tv_coupon_num.setText("免邮");
        }
        classHolder.tv_coupon_name.setText("请于"+couponInfo.getCouponReceiveEndTime()+"前领取");
        classHolder.tv_coupon_can_use_price.setText("购买满"+VerifitionUtil.getStringZhengPrice(couponInfo.getCouponUseConditionPrice())+"元可用");
        classHolder.tv_can_use_time.setText("有效期"+couponInfo.getCouponShelfLife()+"天");
        classHolder.tv_to_getcoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharePreferencesUtils.getBtdToken(context).equals("")){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {
                    toGetCoupon(String.valueOf(couponInfo.getCouponId()));
                }
            }
        });
        return convertView;
    }

    private void toGetCoupon(String couponId) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getUserCouponsListToIndex(SharePreferencesUtils.getBtdToken(context),couponId,new Callback<GetCoupon>() {
            @Override
            public void success(GetCoupon getCoupon, Response response) {
                if (getCoupon.getStatus().equals("1")){
                    if (getCoupon.getData()==0){
                        ToastUtils.showToastCenter(context,"领取失败");
                    }else if (getCoupon.getData()==1){
                        ToastUtils.showToastCenter(context,"领取成功");
                    }else {
                        ToastUtils.showToastCenter(context,"您已经领过了哟");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"您与服务器擦肩而过");
            }
        });
    }

    static class ClassHolder{
        TextView tv_coupon_num,tv_coupon_name,tv_coupon_can_use_price,tv_can_use_time,tv_to_getcoupon;
    }

}
