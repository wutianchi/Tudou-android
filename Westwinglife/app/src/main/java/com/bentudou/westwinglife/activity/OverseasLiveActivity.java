package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.OverseasLiveAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.OverseasLive;
import com.bentudou.westwinglife.json.OverseasLiveData;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.XListView;
import com.bentudou.westwinglife.widget.MediaHelp;
import com.gunlei.app.ui.view.ProgressHUD;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/18.
 * 直播页面
 */
public class OverseasLiveActivity extends BaseTitleActivity implements XListView.OnXListViewListener, View.OnClickListener {
    UMImage image;
    String url ;
    private String shareTitle,shareContent;
    public static final int LIVE_SHARE = 23;
    public static boolean isPlaying;
    public static int indexPostion = -1;
    private XListView xlv_overseas_live;
    private TextView tv_no_overseas_live;
//    private LinearLayout llt_share,llt_share_weixin,llt_share_pengyouquan,llt_share_weibo;
    private List<OverseasLiveData> myOrderLists;
    private OverseasLiveAdapter orderListAdapter;
    private int page = 1;
    private int total;
    private int goodsId;
    private UMShareAPI mShareAPI = null;

    Handler myHander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==LIVE_SHARE){
                OverseasLiveData overseasLiveData = (OverseasLiveData) msg.obj;
                url = "http://m.westwinglife.cn/GoodsLive/getGoodsLiveInfo.htm?goodsId="+overseasLiveData.getGoodsId();
                image = new UMImage(OverseasLiveActivity.this, Constant.URL_BASE_IMG+overseasLiveData.getGoodsImgList().get(0).getGoodsImgPath()+".w200");
                shareTitle=overseasLiveData.getGoodsCnName();
                shareContent=overseasLiveData.getGoodsSlogan();
                DialogUtils.showShareToast(OverseasLiveActivity.this,shareContent,shareTitle,url,image);
            }
        }
    };
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_overseas_live);
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
        super.setTitleText("海外直播");
        Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
        xlv_overseas_live = (XListView) findViewById(R.id.xlv_overseas_live);
        tv_no_overseas_live = (TextView) findViewById(R.id.tv_no_overseas_live);
        xlv_overseas_live.setXListViewListener(this);
        xlv_overseas_live.setPullLoadEnable(XListView.FOOTER_SHOW);
        xlv_overseas_live.setFooterReady(true);
        mShareAPI = UMShareAPI.get(this);
        initData();
    }

    private void initData() {
        final ProgressHUD progressHUD = ProgressHUD.show(this, "加载中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getGoodsLiveList(page,10, new CallbackSupport<OverseasLive>(progressHUD,this) {
            @Override
            public void success(OverseasLive myOrder, Response response) {
                progressHUD.dismiss();
                if (myOrder.getStatus().equals("1")){
                    total=myOrder.getData().getTotal();
                    if (myOrder.getData().getTotal()==0){
                        xlv_overseas_live.setVisibility(View.GONE);
                        tv_no_overseas_live.setVisibility(View.VISIBLE);

                    }else {
                        xlv_overseas_live.setVisibility(View.VISIBLE);
                        tv_no_overseas_live.setVisibility(View.GONE);
                        myOrderLists = myOrder.getData().getRows();
                        orderListAdapter = new OverseasLiveAdapter(myOrderLists,OverseasLiveActivity.this,myHander);
                        xlv_overseas_live.setAdapter(orderListAdapter);
                        xlv_overseas_live.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int i) {

                            }

                            @Override
                            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                                if ((indexPostion < xlv_overseas_live.getFirstVisiblePosition() || indexPostion > xlv_overseas_live
                                        .getLastVisiblePosition()) && isPlaying) {
                                    indexPostion = -1;
                                    isPlaying = false;
                                    orderListAdapter.notifyDataSetChanged();
                                    MediaHelp.release();
                                }
                            }
                        });
                    }
                }else{
                    ToastUtils.showToastCenter(OverseasLiveActivity.this,myOrder.getErrorMessage());
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
        xlv_overseas_live.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService service = RTHttpClient.create(PotatoService.class);
                service.getGoodsLiveList(1,10, new CallbackSupport<OverseasLive>(OverseasLiveActivity.this) {
                    @Override
                    public void success(OverseasLive myOrder, Response response) {
                        if (myOrder.getStatus().equals("1")){
                            page =1;
                            total=myOrder.getData().getTotal();
                            if (myOrder.getData().getTotal()==0){
                                tv_no_overseas_live.setVisibility(View.VISIBLE);
                                xlv_overseas_live.setVisibility(View.GONE);
                            }else {
                                tv_no_overseas_live.setVisibility(View.GONE);
                                xlv_overseas_live.setVisibility(View.VISIBLE);
                                myOrderLists.clear();
                                myOrderLists.addAll(myOrder.getData().getRows());
                                orderListAdapter.notifyDataSetChanged();
                            }
                        }else{
                            ToastUtils.showToastCenter(OverseasLiveActivity.this,myOrder.getErrorMessage());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
                onLoad();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        if (total==myOrderLists.size()){
            xlv_overseas_live.setPullLoadEnable(XListView.FOOTER_WAIT);
            onLoad();
            return;
        }
        xlv_overseas_live.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService service = RTHttpClient.create(PotatoService.class);
                service.getGoodsLiveList(++page,10, new CallbackSupport<OverseasLive>(OverseasLiveActivity.this) {
                    @Override
                    public void success(OverseasLive myOrder, Response response) {
                        if (myOrder.getStatus().equals("1")){
                            if (myOrder.getData().getTotal()==0){
                                tv_no_overseas_live.setVisibility(View.VISIBLE);
                                xlv_overseas_live.setVisibility(View.GONE);
                            }else {
                                tv_no_overseas_live.setVisibility(View.GONE);
                                xlv_overseas_live.setVisibility(View.VISIBLE);
                                myOrderLists.addAll(myOrder.getData().getRows());
                                orderListAdapter.notifyDataSetChanged();
                            }
                        }else{
                            ToastUtils.showToastCenter(OverseasLiveActivity.this,myOrder.getErrorMessage());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        super.failure(error);
                    }
                });
                onLoad();
            }
        },2000);
    }

    private void onLoad() {
        xlv_overseas_live.stopRefresh();
        xlv_overseas_live.stopLoadMore();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str =  formatter.format(curDate);
        xlv_overseas_live.setRefreshTime(str);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.llt_share_weixin:
//                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
//                        .withText(shareContent)
//                        .withTitle(shareTitle)
//                        .withMedia(image)
//                        .withTargetUrl(url)
//                        .share();
//                break;
//            case R.id.llt_share_pengyouquan:
//                new ShareAction(this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
//                        .withTitle(shareTitle)
//                        .withText(shareContent)
//                        .withMedia(image)
//                        .withTargetUrl(url)
//                        .share();
//                break;
//            case R.id.llt_share_weibo:
//                new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
//                        .withText(shareContent)
//                        .withTitle(shareTitle)
//                        .withMedia(image)
//                        .withTargetUrl(url)
//                        .share();
//                break;
//            case R.id.tv_share_cancel:
//                llt_share.setVisibility(View.GONE);
//                break;
//        }
    }

    //用户分享回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "QQ分享成功啦");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "微信分享成功啦");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "朋友圈分享成功啦");
            }else {
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "微博分享成功啦");
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "QQ分享失败了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "微信分享失败了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "朋友圈分享失败了");
            }else {
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "微博分享失败了");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "QQ分享取消了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "微信分享取消了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "朋友圈分享取消了");
            }else {
                ToastUtils.showToastCenter(OverseasLiveActivity.this, "微博分享取消了");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        MediaHelp.getInstance().seekTo(data.getIntExtra("position", 0));
        Log.d("result","onActivityResult");
    }

    @Override
    protected void onResume() {
        MediaHelp.resume();
        super.onResume();
    }
    @Override
    protected void onPause() {
        MediaHelp.pause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        MediaHelp.release();
        super.onDestroy();

    }
}
