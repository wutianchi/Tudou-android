package com.bentudou.westwinglife.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GoodsLiveImageActivity;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.activity.MyOrderDetailActivity;
import com.bentudou.westwinglife.activity.OrderDetailActivity;
import com.bentudou.westwinglife.activity.OverseasLiveActivity;
import com.bentudou.westwinglife.activity.PayStyleSelectActivity;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.fragment.ThreeFragment;
import com.bentudou.westwinglife.json.LiveGoodsImg;
import com.bentudou.westwinglife.json.MyOrderList;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.OrderGoodsList;
import com.bentudou.westwinglife.json.OverseasLiveData;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyGridView;
import com.bentudou.westwinglife.widget.MediaHelp;
import com.bentudou.westwinglife.widget.VideoSuperPlayer;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by  天池 on 2015/7/30.
 * 订单列表适配器
 */
public class OverseasLiveAdapter extends BaseAdapter {

    private List<OverseasLiveData> list;
    Context context;
    private LayoutInflater inflater;
    private Handler myHander;
    public OverseasLiveAdapter(List<OverseasLiveData> list, Context context,Handler myHander){
        this.context = context;
        this.list = list;
        this.myHander = myHander;
        inflater = LayoutInflater.from(this.context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if(convertView==null){
            myHolder = new MyHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_overseas_live,null);
            myHolder.tv_live_goods_title = (TextView)convertView.findViewById(R.id.tv_live_goods_title);
            myHolder.tv_live_goods_slogan = (TextView)convertView.findViewById(R.id.tv_live_goods_slogan);
            myHolder.llt_share_icon = (LinearLayout) convertView.findViewById(R.id.llt_share_icon);
            myHolder.tv_live_goods_content = (TextView)convertView.findViewById(R.id.tv_live_goods_content);
            myHolder.tv_live_goods_time = (TextView)convertView.findViewById(R.id.tv_live_goods_time);
            myHolder.tv_live_goods_number = (TextView)convertView.findViewById(R.id.tv_live_goods_number);
            myHolder.tv_live_goods_price = (TextView)convertView.findViewById(R.id.tv_live_goods_price);
            myHolder.live_detail_image = (LinearLayout) convertView.findViewById(R.id.live_detail_image);
//            myHolder.iv_goods_img = (ImageView) convertView.findViewById(R.id.iv_goods_img);
            myHolder.btn_to_buy = (Button) convertView.findViewById(R.id.btn_to_buy);
            myHolder.icon_layout = (RelativeLayout) convertView.findViewById(R.id.icon_layout);
            myHolder.mVideoViewLayout = (VideoSuperPlayer) convertView.findViewById(R.id.video);
            myHolder.mPlayBtnView = (ImageView) convertView.findViewById(R.id.play_btn);
            myHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(myHolder);
        }else{
            myHolder = (MyHolder)convertView.getTag();
        }
        final OverseasLiveData myOrderList = list.get(position);
        if (null!=myOrderList.getLiveStreamUrl()&&!myOrderList.getLiveStreamUrl().isEmpty()){
            myHolder.icon_layout.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+myOrderList.getLiveStreamImg(),myHolder.icon);
            myHolder.mPlayBtnView.setOnClickListener(new MyOnclick(myHolder.mPlayBtnView,myHolder.mVideoViewLayout,position));
            if (OverseasLiveActivity.indexPostion == position) {
                myHolder.mVideoViewLayout.setVisibility(View.VISIBLE);
            } else {
                myHolder.mVideoViewLayout.setVisibility(View.GONE);
                myHolder.mVideoViewLayout.close();
            }
        }else {
            myHolder.icon_layout.setVisibility(View.GONE);
        }
        myHolder.tv_live_goods_title.setText(myOrderList.getGoodsCnName());
        myHolder.tv_live_goods_slogan.setText(myOrderList.getGoodsSlogan());
        myHolder.tv_live_goods_content.setText("               "+myOrderList.getGoodsDescImg().replaceAll("<p>","").replaceAll("</p>","").replaceAll("<br/>",""));
        myHolder.tv_live_goods_time.setText("活动截止: "+myOrderList.getGoodsValidityEndTime());
        myHolder.tv_live_goods_number.setText("库存: "+String.valueOf(myOrderList.getGoodsNumber()));
        myHolder.tv_live_goods_price.setText(VerifitionUtil.getRMBStringPrice(myOrderList.getShopPriceCny()));
//        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+myOrderList.getGoodsImgList().get(0).getGoodsImgPath(),myHolder.iv_goods_img);
        myHolder.btn_to_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharePreferencesUtils.getBtdToken(context).equals("")){
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else {
                    gotoOrder(myOrderList.getGoodsId());
                }
            }
        });
        myHolder.live_detail_image.removeAllViews();
        final Iterator<LiveGoodsImg> iterator = myOrderList.getGoodsImgList().iterator();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
        DensityUtils.dip2px(context, 100), DensityUtils.dip2px(context, 100));
        params.setMargins(0, 0, DensityUtils.dip2px(context, 5), 0);
        while (iterator.hasNext()){
            final String url =Constant.URL_BASE_IMG+iterator.next().getGoodsImgPath();
            ImageView image = new ImageView(context);
            ImageLoader.getInstance().displayImage(url,image);
            image.setLayoutParams(params);
            image.setScaleType(ImageView.ScaleType.CENTER);
            image.setTag(url);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constant.live_img_num=knowPostion(url,myOrderList.getGoodsImgList());
                    Intent intent = new Intent();
                    intent.setClass(context, GoodsLiveImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goods_live_img", (Serializable) myOrderList.getGoodsImgList());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            myHolder.live_detail_image.addView(image);
        }

        myHolder.llt_share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = new Message();
                msg.what = OverseasLiveActivity.LIVE_SHARE;
                msg.obj =myOrderList;
                myHander.sendMessage(msg);
            }
        });
        return convertView;
    }
    private int knowPostion(String img,List<LiveGoodsImg> liveGoodsImgList){
        for(int i=0;i<liveGoodsImgList.size();i++){
            if (img.equals(Constant.URL_BASE_IMG+liveGoodsImgList.get(i).getGoodsImgPath()))
                return i;
        }
        return 0;
    }
    private void gotoOrder(int goodsId) {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.confirmOrderInfo(SharePreferencesUtils.getBtdToken(context),goodsId+"~1","CNY",true, new CallbackSupport<OrderDetail>(context) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                if (orderDetail.getStatus().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(context, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_detail_back", orderDetail.getData());
                    intent.putExtras(bundle);
                    intent.putExtra("livegoods",2);
                    context.startActivity(intent);
                }else{
                    if (orderDetail.getErrorCode().equals("51103")){
                        ToastUtils.showToastCenter(context,"价格发生变化,请确认!");
                    }else if (orderDetail.getErrorCode().equals("51105")||orderDetail.getErrorCode().equals("51106")){
                        ToastUtils.showToastCenter(context,"有异常商品,请确认!");
                    }else {
                        ToastUtils.showToastCenter(context,orderDetail.getErrorMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                ToastUtils.showToastCenter(context,"下单失败~");
            }
        });
    }
    static class MyHolder{
        private VideoSuperPlayer mVideoViewLayout;
        private ImageView mPlayBtnView;
        private ImageView icon;
        private RelativeLayout icon_layout;
        TextView tv_live_goods_title;//标题
        TextView tv_live_goods_slogan;//一句话口号
        TextView tv_live_goods_content;//描述
        TextView tv_live_goods_time;//有效期
        TextView tv_live_goods_number;//库存
        TextView tv_live_goods_price;//价格
        LinearLayout live_detail_image;
        LinearLayout llt_share_icon;
//        ImageView iv_goods_img;
        Button btn_to_buy;
    }
    class MyOnclick implements View.OnClickListener {
        VideoSuperPlayer mSuperVideoPlayer;
        ImageView mPlayBtnView;
        int position;

        public MyOnclick(ImageView mPlayBtnView,
                         VideoSuperPlayer mSuperVideoPlayer, int position) {
            this.position = position;
            this.mSuperVideoPlayer = mSuperVideoPlayer;
            this.mPlayBtnView = mPlayBtnView;
        }

        @Override
        public void onClick(View v) {
            MediaHelp.release();
            OverseasLiveActivity.indexPostion = position;
            OverseasLiveActivity.isPlaying = true;
            mSuperVideoPlayer.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.loadAndPlay(MediaHelp.getInstance(),list.get(position).getLiveStreamUrl(), 0, false);
            mSuperVideoPlayer.setVideoPlayCallback(new MyVideoPlayCallback(
                    mPlayBtnView, mSuperVideoPlayer, list.get(position)));
            notifyDataSetChanged();
        }
    }
    class MyVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
        ImageView mPlayBtnView;
        VideoSuperPlayer mSuperVideoPlayer;
        OverseasLiveData info;

        public MyVideoPlayCallback(ImageView mPlayBtnView,
                                   VideoSuperPlayer mSuperVideoPlayer, OverseasLiveData info) {
            this.mPlayBtnView = mPlayBtnView;
            this.info = info;
            this.mSuperVideoPlayer = mSuperVideoPlayer;
        }

        @Override
        public void onCloseVideo() {
            closeVideo();
        }

        @Override
        public void onSwitchPageType() {
//            if (((Activity) context).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
//                Intent intent = new Intent(new Intent(context,
//                        FullVideoActivity.class));
//                intent.putExtra("video", info);
//                intent.putExtra("position",
//                        mSuperVideoPlayer.getCurrentPosition());
//                ((Activity) context).startActivityForResult(intent, 1);
//            }
        }

        @Override
        public void onPlayFinish() {
            closeVideo();
        }

        private void closeVideo() {
            OverseasLiveActivity.isPlaying = false;
            OverseasLiveActivity.indexPostion = -1;
            mSuperVideoPlayer.close();
            MediaHelp.release();
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.GONE);
        }

    }
}
