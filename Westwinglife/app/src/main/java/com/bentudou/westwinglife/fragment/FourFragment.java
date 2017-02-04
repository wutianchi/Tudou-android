package com.bentudou.westwinglife.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.AboutBentudouActivity;
import com.bentudou.westwinglife.activity.GiveUsMessageActivity;
import com.bentudou.westwinglife.activity.GrowLogActivity;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.activity.MyAddressActivity;
import com.bentudou.westwinglife.activity.MyCouponActivity;
import com.bentudou.westwinglife.activity.MyInviteCodeActivity;
import com.bentudou.westwinglife.activity.MyMessageListActivity;
import com.bentudou.westwinglife.activity.MyNewCollectionListActivity;
import com.bentudou.westwinglife.activity.NewOrderListActivity;
import com.bentudou.westwinglife.activity.SetActivity;
import com.bentudou.westwinglife.activity.ShareBentudouActivity;
import com.bentudou.westwinglife.activity.WebDetailActivity;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.CheckSign;
import com.bentudou.westwinglife.json.PayNumber;
import com.bentudou.westwinglife.json.UnReadMessage;
import com.bentudou.westwinglife.json.UserInfo;
import com.bentudou.westwinglife.json.UserSign;
import com.bentudou.westwinglife.utils.FileImageUpload;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.UploadUtil;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.gunlei.app.ui.view.ProgressHUD;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/2.
 */
public class FourFragment extends Fragment implements View.OnClickListener,UploadUtil.OnUploadProcessListener {
    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2; //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;
    /***
     * 这里的这个URL是我服务器的javaEE环境URL
     */
    private static String requestURL = Constant.URL_BASE_TEST+"/UploadImage/uploadIDImage.htm";
    private String picPath = "/sdcard/startimg.png";


    private ImageView iv_my_message,iv_level;
    private SwipeRefreshLayout srl_fresh;
    private TextView tv_my_phone,tv_new_message,tv_is_sign,tv_pay_num;
    private LinearLayout llt_my_order,llt_my_address,llt_my_tudou,llt_my_yaoqing,
            llt_my_share,llt_my_set,llt_my_collection, llt_qiandao,llt_grow,llt_bg_img,
            llt_no_pay,llt_have_pay,llt_no_use_order;
    ProgressHUD progressHUD = null;
    private String  inviteCode;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_four,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        iv_my_message = (ImageView) view.findViewById(R.id.iv_my_message);
        iv_level = (ImageView) view.findViewById(R.id.iv_level);
        srl_fresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_fresh);
        tv_my_phone = (TextView) view.findViewById(R.id.tv_my_phone);
        llt_my_order = (LinearLayout) view.findViewById(R.id.llt_my_order);
        llt_no_pay = (LinearLayout) view.findViewById(R.id.llt_no_pay);
        llt_have_pay = (LinearLayout) view.findViewById(R.id.llt_have_pay);
        llt_no_use_order = (LinearLayout) view.findViewById(R.id.llt_no_use_order);
        llt_my_address = (LinearLayout) view.findViewById(R.id.llt_my_address);
        llt_my_tudou = (LinearLayout) view.findViewById(R.id.llt_my_tudou);
        llt_my_yaoqing = (LinearLayout) view.findViewById(R.id.llt_my_yaoqing);
        llt_my_share = (LinearLayout) view.findViewById(R.id.llt_my_share);
        llt_my_set = (LinearLayout) view.findViewById(R.id.llt_my_set);
        llt_my_collection = (LinearLayout) view.findViewById(R.id.llt_my_collection);
        llt_qiandao = (LinearLayout) view.findViewById(R.id.llt_qiandao);
        llt_grow = (LinearLayout) view.findViewById(R.id.llt_grow);
        llt_bg_img = (LinearLayout) view.findViewById(R.id.llt_bg_img);
        tv_new_message = (TextView) view.findViewById(R.id.tv_new_message);
        tv_is_sign = (TextView) view.findViewById(R.id.tv_is_sign);
        tv_pay_num = (TextView) view.findViewById(R.id.tv_pay_num);
        iv_my_message.setOnClickListener(this);
        llt_qiandao.setOnClickListener(this);
        llt_grow.setOnClickListener(this);
        llt_my_order.setOnClickListener(this);
        llt_no_pay.setOnClickListener(this);
        llt_have_pay.setOnClickListener(this);
        llt_no_use_order.setOnClickListener(this);
        llt_my_address.setOnClickListener(this);
        llt_my_tudou.setOnClickListener(this);
        llt_my_yaoqing.setOnClickListener(this);
        llt_my_share.setOnClickListener(this);
        llt_my_set.setOnClickListener(this);
        llt_my_collection.setOnClickListener(this);
        srl_fresh.setColorSchemeResources(R.color.color_select, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srl_fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toRefresh();
                isNewMessage();
                isCheckSign();
                initNoPayNum();
            }
        });
        isNewMessage();
        isCheckSign();
        initNoPayNum();
        initBgImg();
    }

    private void initNoPayNum() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findOrderNumberUnpaid(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<PayNumber>() {
            @Override
            public void success(PayNumber payNumber, Response response) {
                if (payNumber.getStatus().equals("1")){
                    if (payNumber.getData().getOrderNumber()>0){
                        tv_pay_num.setVisibility(View.VISIBLE);
                        tv_pay_num.setText(String.valueOf(payNumber.getData().getOrderNumber()));
                    }else {
                        tv_pay_num.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void initBgImg() {
        switch (SharePreferencesUtils.getBgImg(getActivity())){
            case 1:
                llt_bg_img.setBackgroundResource(R.drawable.bg1);
                break;
            case 2:
                llt_bg_img.setBackgroundResource(R.drawable.bg2);
                break;
            case 3:
                llt_bg_img.setBackgroundResource(R.drawable.bg3);
                break;
            case 4:
                llt_bg_img.setBackgroundResource(R.drawable.bg4);
                break;
            case 5:
                llt_bg_img.setBackgroundResource(R.drawable.bg5);
                break;
            case 6:
                llt_bg_img.setBackgroundResource(R.drawable.bg6);
                break;
            case 7:
                llt_bg_img.setBackgroundResource(R.drawable.bg7);
                break;
            case 8:
                llt_bg_img.setBackgroundResource(R.drawable.bg8);
                break;
            case 9:
                llt_bg_img.setBackgroundResource(R.drawable.bg9);
                break;
            case 10:
                llt_bg_img.setBackgroundResource(R.drawable.bg10);
                break;
            case 11:
                llt_bg_img.setBackgroundResource(R.drawable.bg11);
                break;
        }
    }

    private void isCheckSign() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.checkUserSign(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<CheckSign>() {
            @Override
            public void success(CheckSign checkSign, Response response) {
                if (checkSign.getStatus().equals("1")){
                    if (checkSign.getData().isUserSign()){
                        tv_is_sign.setText("已签到");
                    }else {
                        tv_is_sign.setText("签到");
                    }
                    switch (checkSign.getData().getUserGrade()){
                        case 0:
                            iv_level.setImageResource(R.drawable.v0);
                            break;
                        case 1:
                            iv_level.setImageResource(R.drawable.v1);
                            break;
                        case 2:
                            iv_level.setImageResource(R.drawable.v2);
                            break;
                        case 3:
                            iv_level.setImageResource(R.drawable.v3);
                            break;
                        case 4:
                            iv_level.setImageResource(R.drawable.v4);
                            break;
                        case 5:
                            iv_level.setImageResource(R.drawable.v5);
                            break;
                        case 6:
                            iv_level.setImageResource(R.drawable.v6);
                            break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    //是否有新消息
    private void isNewMessage() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getUnReadCount(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<UnReadMessage>() {
            @Override
            public void success(UnReadMessage userInfo, Response response) {
                if (userInfo.getStatus().equals("1")){
                    if (userInfo.getData().getUnreadMessageCount()>0){
                        tv_new_message.setVisibility(View.VISIBLE);
                    }else {
                        tv_new_message.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    public void toRefresh(){
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getUserInfo(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<UserInfo>() {
            @Override
            public void success(UserInfo userInfo, Response response) {
                srl_fresh.setRefreshing(false);
                if (null!=userInfo.getData()){
                    inviteCode =userInfo.getData().getUserInviteCode();
                    tv_my_phone.setText(userInfo.getData().getUserName());
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

            }

            @Override
            public void failure(RetrofitError error) {
                srl_fresh.setRefreshing(false);
            }
        });
    }
    private void initData() {
        inviteCode = SharePreferencesUtils.getInviteCode(getActivity());
        tv_my_phone.setText(SharePreferencesUtils.getMobile(getActivity()));
//        progressHUD = ProgressHUD.show(getActivity(), "读取中", true, null);
//        final PotatoService service = RTHttpClient.create(PotatoService.class);
//        service.getUserInfo(SharePreferencesUtils.getBtdToken(getActivity()),new CallbackSupport<UserInfo>(progressHUD, getActivity()) {
//            @Override
//            public void success(UserInfo userInfo, Response response) {
//                progressHUD.dismiss();
//                if (null!=userInfo.getData()){
//                    inviteCode =userInfo.getData().getUserInviteCode();
//                    tv_my_phone.setText(userInfo.getData().getUserName());
//                    tv_my_yaoqing.setText("我的邀请码   "+inviteCode);
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                super.failure(error);
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llt_my_set:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;//去设置
                case R.id.iv_my_message:
                startActivity(new Intent(getActivity(), MyMessageListActivity.class));
                break;//去消息列表
            case R.id.llt_my_order:
                startActivity(new Intent(getActivity(), NewOrderListActivity.class).putExtra("tagid",1));
//                startActivity(new Intent(getActivity(), OrderListActivity.class));
                break;//去我的订单
             case R.id.llt_no_pay:
                startActivity(new Intent(getActivity(), NewOrderListActivity.class).putExtra("tagid",2));
                break;//去待付款的订单
             case R.id.llt_have_pay:
                startActivity(new Intent(getActivity(), NewOrderListActivity.class).putExtra("tagid",3));
                break;//去已付款的订单
             case R.id.llt_no_use_order:
                startActivity(new Intent(getActivity(), NewOrderListActivity.class).putExtra("tagid",4));
                break;//去失效的订单
            case R.id.llt_my_collection:
                startActivity(new Intent(getActivity(), MyNewCollectionListActivity.class));
                break;//去我的收藏
            case R.id.llt_my_address:
                startActivity(new Intent(getActivity(), MyAddressActivity.class));
                break;//去我的地址
            case R.id.llt_my_tudou:
                startActivity(new Intent(getActivity(), MyCouponActivity.class));
                break;//去我的土豆条
            case R.id.llt_my_yaoqing:
                startActivity(new Intent(getActivity(), MyInviteCodeActivity.class).putExtra("invite_code",inviteCode));
                break;//去我的邀请码
//                handler.sendEmptyMessage(TO_UPLOAD_FILE);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String result;
//                        result= FileImageUpload.uploadFile(SharePreferencesUtils.getBtdToken(getActivity()),new File("/sdcard/startimg.png"),"iDImg",Constant.URL_BASE_TEST+"/UploadImage/uploadIDImage.htm");
//                    if (result.equals("1")){
//                        Log.d("result","-----上传成功!");
//                    }else {
//                        Log.d("result","-----上传失败!");
//                    }
//                    }
//                }).start();
//                VerifitionUtil.uploadImg(SharePreferencesUtils.getBtdToken(getActivity()),"/sdcard/startimg.png");
            case R.id.llt_my_share:
                startActivity(new Intent(getActivity(), ShareBentudouActivity.class));
                break;//去分享
            case R.id.llt_qiandao:
                if (tv_is_sign.getText().toString().equals("已签到")){
                    ToastUtils.showToastCenter(getActivity(),"今天您已经签到过了哟");
                    break;
                }
                toSign();
                break;//去签到
            case R.id.llt_grow:
                startActivity(new Intent(getActivity(), GrowLogActivity.class));
                break;//去成长记录
        }
    }
    private void toUploadFile()
    {
        String fileKey = "iDImg";
        UploadUtil uploadUtil = UploadUtil.getInstance();;
        uploadUtil.setOnUploadProcessListener(this); //设置监听器监听上传状态
        Map<String, String> params = new HashMap<String, String>();
        params.put("BtdToken", SharePreferencesUtils.getBtdToken(getActivity()));
        uploadUtil.uploadFile( picPath,fileKey, requestURL,params);
    }

    //签到接口
    private void toSign() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.createUserSign(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<UserSign>() {
            @Override
            public void success(UserSign userSign, Response response) {
                if (userSign.getStatus().equals("1")){
                    if (userSign.isData()){
                        tv_is_sign.setText("已签到");
                        ToastUtils.showToastCenter(getActivity(),"签到成功");
//                        DialogUtils.showToast(getActivity().getLayoutInflater(),getActivity(),"签到成功","获取积分15");
                    }else {
                        ToastUtils.showToastCenter(getActivity(),"服务器小憩中");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        Constant.push_value=4;
//        if (SharePreferencesUtils.getBtdToken(getActivity()).isEmpty()){
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//        }else {
        initData();
        isNewMessage();
        initNoPayNum();
        isCheckSign();
//        }
    }



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;
                case UPLOAD_INIT_PROCESS:
                    Log.d("UPLOAD_INIT_PROCESS","-----"+msg.arg1);
                    break;
                case UPLOAD_IN_PROCESS:
                    Log.d("UPLOAD_IN_PROCESS","-----"+msg.arg1);
                    break;
                case UPLOAD_FILE_DONE:
                    String result = "响应码："+msg.arg1+"\n响应信息："+msg.obj+"\n耗时："+UploadUtil.getRequestTime()+"秒";
                    Log.d("UPLOAD_FILE_DONE","-----"+result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };
    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg );
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg );
    }
}
