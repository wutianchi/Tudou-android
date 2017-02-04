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
import com.bentudou.westwinglife.json.CouponListData;
import com.bentudou.westwinglife.utils.VerifitionUtil;

import java.util.List;

/**
 *Created by lzz on 2016/3/2.
 * 优惠券列表适配器
 */
public class CouponAdapter extends BaseAdapter {
    List<CouponListData> list;
    Context context;
    int flag;
    int mScreentWidth;
    public CouponAdapter(List<CouponListData> list, Context context,int flag) {
        this.list = list;
        this.context = context;
        this.flag = flag;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coupon,null);
            classHolder.llt_coupon_status = (LinearLayout) convertView.findViewById(R.id.llt_coupon_status);
            classHolder.llt_is_manjian = (LinearLayout) convertView.findViewById(R.id.llt_is_manjian);
            classHolder.tv_dallor = (TextView)convertView.findViewById(R.id.tv_dallor);
            classHolder.tv_coupon_num = (TextView)convertView.findViewById(R.id.tv_coupon_num);
            classHolder.tv_coupon_name = (TextView)convertView.findViewById(R.id.tv_coupon_name);
            classHolder.tv_coupon_time = (TextView)convertView.findViewById(R.id.tv_coupon_time);
            classHolder.tv_status = (TextView)convertView.findViewById(R.id.tv_status);
            convertView.setTag(classHolder);
        }else {
            classHolder = (ClassHolder) convertView.getTag();
        }
        if (flag==1){//已过期
            classHolder.llt_coupon_status.setBackgroundResource(R.drawable.youhuiquan_yiguoqi);
            classHolder.tv_coupon_num.setTextColor(context.getResources().getColor( R.color.gray_five));
            if (VerifitionUtil.getStringZhengPrice(list.get(position).getCouponPrice()).equals("0")){
                classHolder.tv_status.setText("免邮券");
                classHolder.tv_dallor.setVisibility(View.GONE);
                classHolder.tv_coupon_num.setText("免邮呦");
            }else {
                classHolder.tv_status.setText("满减券");
                classHolder.tv_dallor.setVisibility(View.VISIBLE);
                classHolder.tv_dallor.setTextColor(context.getResources().getColor( R.color.gray_five));
                classHolder.tv_coupon_num.setText(VerifitionUtil.getStringZhengPrice(list.get(position).getCouponPrice()));
            }
        }else if (flag==2){//可用
            classHolder.llt_coupon_status.setBackgroundResource(R.drawable.youhuiquan);
            classHolder.tv_coupon_num.setTextColor(context.getResources().getColor( R.color.color_youhui));
            if (list.get(position).getCouponId()==0){
                classHolder.tv_status.setText("任性");
                classHolder.tv_dallor.setVisibility(View.VISIBLE);
                classHolder.tv_dallor.setTextColor(context.getResources().getColor( R.color.color_youhui));
                classHolder.tv_coupon_num.setText("0");
            }else {
                if (VerifitionUtil.getStringZhengPrice(list.get(position).getCouponPrice()).equals("0")){
                    classHolder.tv_status.setText("免邮券");
                    classHolder.tv_dallor.setVisibility(View.GONE);
                    classHolder.tv_coupon_num.setText("免邮呦");
                }else {
                    classHolder.tv_status.setText("满减券");
                    classHolder.tv_dallor.setVisibility(View.VISIBLE);
                    classHolder.tv_dallor.setTextColor(context.getResources().getColor( R.color.color_youhui));
                    classHolder.tv_coupon_num.setText(VerifitionUtil.getStringZhengPrice(list.get(position).getCouponPrice()));
                }
            }

        }else {//已使用
            classHolder.llt_coupon_status.setBackgroundResource(R.drawable.youhuiquan_yishiyong);
            classHolder.tv_coupon_num.setTextColor(context.getResources().getColor( R.color.gray_five));
            if (VerifitionUtil.getStringZhengPrice(list.get(position).getCouponPrice()).equals("0")){
                classHolder.tv_status.setText("免邮券");
                classHolder.tv_dallor.setVisibility(View.GONE);
                classHolder.tv_coupon_num.setText("免邮呦");
            }else {
                classHolder.tv_status.setText("满减券");
                classHolder.tv_dallor.setVisibility(View.VISIBLE);
                classHolder.tv_coupon_num.setText(VerifitionUtil.getStringZhengPrice(list.get(position).getCouponPrice()));
                classHolder.tv_dallor.setTextColor(context.getResources().getColor( R.color.gray_five));
            }
        }
        if (list.get(position).getCouponId()==0){
            classHolder.tv_coupon_name.setText("我是土豪 任性不使用优惠券");
            classHolder.tv_coupon_time.setText("");
        }else {
            classHolder.tv_coupon_name.setText(list.get(position).getCouponName());
            classHolder.tv_coupon_time.setText("有效期至:"+list.get(position).getCouponDisableTime().substring(0,10));
        }
        return convertView;
    }
    static class ClassHolder{
        LinearLayout llt_coupon_status,llt_is_manjian;
        TextView tv_coupon_num,tv_coupon_name,tv_coupon_time,tv_dallor,tv_status;
    }

}
