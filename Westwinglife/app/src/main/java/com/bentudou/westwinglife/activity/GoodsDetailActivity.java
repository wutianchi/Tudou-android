package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.ISlideCallback;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.AddSub;
import com.bentudou.westwinglife.json.GoodsDetail;
import com.bentudou.westwinglife.json.GoodsDetailSession;
import com.bentudou.westwinglife.json.GoodsPictureData;
import com.bentudou.westwinglife.json.IsCollection;
import com.bentudou.westwinglife.json.OrderDetail;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.GoodsDetailImageHolderView;
import com.bentudou.westwinglife.view.SlideDetailsLayout;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.gunlei.app.ui.view.ProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/17.
 * 商品详情页面
 */
public class GoodsDetailActivity extends BaseTitleActivity implements View.OnClickListener,ISlideCallback{
    private final String TAG = getClass().getSimpleName();
    UMImage image;
    String url,shareText;
    private SlideDetailsLayout mSlideDetailsLayout;
    private WebView webView;
//    private ScrollView slv_ontouch;
    private ImageView iv_go_cart,iv_depot_icon,iv_go_collection,iv_close,iv_small_goods,brand_icon;
    private Button btn_commit_order;
    private EditText et_count_value;
    private LinearLayout llt_add_goods;
    private ConvenientBanner convenientBanner;
    private TextView tv_goods_detail_name,tv_goods_detail_shopprice,tv_sure,tv_btn_chakan,
            tv_goods_detail_cuxiao,tv_goods_detail_fahuocang,
            tv_add_to_cart,tv_cart_num,tv_count_sub,brand_name,
            tv_count_add,tv_bg_close,tv_goods_small_name,tv_goods_small_price,
            tv_pinpai,tv_huohao,tv_chandi,tv_guige;
    private String goodsId;
    private String htmls;
    private String hh ="";
    private boolean isCartorCommit=false;
    ProgressHUD progressHUD = null;
    private GoodsDetail goodsDetail;
    private UMShareAPI mShareAPI = null;
    private boolean isCollection=false;
    private List<GoodsPictureData> goodsPictureDatas;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_goods_detail);
        Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
        mShareAPI = UMShareAPI.get(this);
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
        super.setTitleText("商品详情");
        goodsId = getIntent().getStringExtra("goodsId");
        title_save.setBackgroundResource(R.drawable.shangpinxiangqing_botton_fenixang);
        title_next.setOnClickListener(this);
        mSlideDetailsLayout = (SlideDetailsLayout) findViewById(R.id.slidedetails);
//        slv_ontouch = (ScrollView) findViewById(R.id.slv_ontouch);
        webView = (WebView) findViewById(R.id.slidedetails_behind);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        llt_add_goods = (LinearLayout) findViewById(R.id.llt_add_goods);
        tv_count_sub = (TextView) findViewById(R.id.tv_count_sub);
        tv_count_add = (TextView) findViewById(R.id.tv_count_add);
        tv_bg_close = (TextView) findViewById(R.id.tv_bg_close);
        et_count_value = (EditText) findViewById(R.id.et_count_value);
        tv_cart_num = (TextView) findViewById(R.id.tv_cart_num);
        tv_pinpai = (TextView) findViewById(R.id.tv_pinpai);
        tv_huohao = (TextView) findViewById(R.id.tv_huohao);
        tv_chandi = (TextView) findViewById(R.id.tv_chandi);
        tv_guige = (TextView) findViewById(R.id.tv_guige);
        tv_sure = (TextView) findViewById(R.id.tv_sure);
        tv_goods_small_name = (TextView) findViewById(R.id.tv_goods_small_name);
        tv_goods_small_price = (TextView) findViewById(R.id.tv_goods_small_price);
        tv_btn_chakan = (TextView) findViewById(R.id.tv_btn_chakan);
        tv_goods_detail_name = (TextView) findViewById(R.id.tv_goods_detail_name);
        tv_goods_detail_shopprice = (TextView) findViewById(R.id.tv_goods_detail_shopprice);
        tv_goods_detail_cuxiao = (TextView) findViewById(R.id.tv_goods_detail_cuxiao);
        tv_goods_detail_fahuocang = (TextView) findViewById(R.id.tv_goods_detail_fahuocang);
        brand_name = (TextView) findViewById(R.id.brand_name);
        iv_go_cart = (ImageView) findViewById(R.id.iv_go_cart);
        iv_go_collection = (ImageView) findViewById(R.id.iv_go_collection);
        iv_depot_icon = (ImageView) findViewById(R.id.iv_depot_icon);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        brand_icon = (ImageView) findViewById(R.id.brand_icon);
        iv_small_goods = (ImageView) findViewById(R.id.iv_small_goods);
        tv_add_to_cart = (TextView) findViewById(R.id.tv_add_to_cart);
        btn_commit_order = (Button) findViewById(R.id.btn_commit_order);
        tv_btn_chakan.setOnClickListener(this);
        iv_go_cart.setOnClickListener(this);
        iv_go_collection.setOnClickListener(this);
        tv_add_to_cart.setOnClickListener(this);
        btn_commit_order.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_count_sub.setOnClickListener(this);
        tv_count_add.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        tv_bg_close.setOnClickListener(this);
//        slv_ontouch.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_MOVE:
////                        llt_add_goods.setVisibility(View.GONE);
////                        llt_share.setVisibility(View.GONE);
//                        break;
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
        initData();
    }
    //页面内webview初始化
    public void initWebview(){
        final WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                public void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                webView.loadDataWithBaseURL(Constant.URL_BASE_IMG,htmls,"text/html", "utf-8", null);

            }
        });
    }
    @Override
    public void openDetails(boolean smooth) {
        Log.d(TAG, "-----openDetails: ");
        mSlideDetailsLayout.smoothOpen(smooth);
    }

    @Override
    public void closeDetails(boolean smooth) {
        Log.d(TAG, "-----closeDetails: ");
        mSlideDetailsLayout.smoothClose(smooth);
    }
    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.readGoodsDetail(SharePreferencesUtils.getBtdToken(this),goodsId,new CallbackSupport<GoodsDetailSession>(progressHUD, this) {
            @Override
            public void success(GoodsDetailSession goodsDetailSession, Response response) {
                progressHUD.dismiss();
                if (goodsDetailSession.getStatus().equals("1")){
                    if (goodsDetailSession.getData().getCartGoodsNum()==0){
                        tv_cart_num.setVisibility(View.GONE);
                    }else {
                        tv_cart_num.setVisibility(View.VISIBLE);
                        tv_cart_num.setText(String.valueOf(goodsDetailSession.getData().getCartGoodsNum()));
                    }
                    goodsPictureDatas = goodsDetailSession.getData().getGoodsImgList();
                    if (!goodsPictureDatas.isEmpty())
                        banner_image();
                    if (!goodsDetailSession.getData().getGoods().getGoodsImg().isEmpty()){
                        image = new UMImage(GoodsDetailActivity.this,  Constant.URL_BASE_IMG+goodsDetailSession.getData().getGoods().getGoodsImg()+".w200");
                    }else {
                        image = new UMImage(GoodsDetailActivity.this,  R.drawable.share_icon);
                    }
                    if (null!=goodsDetailSession.getData().getDepot().getDepotIcon()&&!goodsDetailSession.getData().getDepot().getDepotIcon().isEmpty()){
                        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+goodsDetailSession.getData().getDepot().getDepotIcon(),iv_depot_icon);
                    }
                    if (null!=goodsDetailSession.getData().getBrand()){
                        if (null!=goodsDetailSession.getData().getBrand().getBrandLogo()&&!goodsDetailSession.getData().getBrand().getBrandLogo().equals("")){
                            ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+goodsDetailSession.getData().getBrand().getBrandLogo(),brand_icon);
                        }
                        brand_name.setText(goodsDetailSession.getData().getBrand().getBrandCnName()+"("+goodsDetailSession.getData().getBrand().getBrandEnName()+")");
                    }
                    goodsDetail = goodsDetailSession.getData().getGoods();
                    if (null!=goodsDetailSession.getData().getBrand()&&null!=goodsDetailSession.getData().getBrand().getBrandEnName())
                    tv_pinpai.setText("品牌: "+goodsDetailSession.getData().getBrand().getBrandEnName());
                    if (null!=goodsDetail.getGoodsSn())
                    tv_huohao.setText("商品货号: "+goodsDetail.getGoodsSn());
                    if (null!=goodsDetail.getGoodsOriginName())
                    tv_chandi.setText("商品产地: "+goodsDetail.getGoodsOriginName());
                    if (null!=goodsDetailSession.getData().getGoods().getGoodsSpecifications())
                    tv_guige.setText("规格: "+goodsDetailSession.getData().getGoods().getGoodsSpecifications());
                    url ="http://m.bentudou.com/Goods/getGoodsInfo.htm?goodsId="+goodsDetail.getGoodsId();
                    shareText=goodsDetail.getGoodsCnName();
                    if (null!=goodsDetail.getGoodsDescImg()){
                        htmls ="<html>"
                                + "<body><link href=\"http://m.bentudou.com/themes/css/appgoodsstyle.css\" rel=\"stylesheet\" type=\"text/css\"/><br/>"
                                + goodsDetail.getGoodsDescImg()+ "</body>" + "</html>";
                    }else {
                        htmls ="<html>"
                                + "<body><link href=\"http://m.bentudou.com/themes/css/appgoodsstyle.css\" rel=\"stylesheet\" type=\"text/css\"/><P style=\"font-size:30px\">暂无图文</p><br/></body>" + "</html>";
                    }
                    initWebview();
//              webView.loadDataWithBaseURL(Constant.URL_BASE_TEST,htmls,"text/html", "utf-8", null);
//                    tv_goods_detail_marketprice.setText(VerifitionUtil.getStringPrice(goodsDetail.getMarketPrice()));
//                    tv_goods_detail_marketprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
//                    tv_goods_canshu.setText(goodsDetail.getGoodsSpecifications().replace("<br/>","\n"));
                    if (null != goodsDetail.getDepotName()){
                        tv_goods_detail_fahuocang.setText(goodsDetail.getDepotName());
                    }else {
                        tv_goods_detail_fahuocang.setText("默认仓库");
                    }
                    tv_goods_detail_shopprice.setText(VerifitionUtil.getRMBStringPrice(goodsDetail.getShopPriceCny()));
                    if (goodsDetail.isActivity()){
                        tv_goods_detail_name.setText("                "+goodsDetail.getGoodsCnName());
                        tv_goods_detail_cuxiao.setVisibility(View.VISIBLE);
                        if (null!=goodsDetailSession.getData().getPromotions().getPromotionsName()){
                            tv_goods_detail_cuxiao.setText(goodsDetailSession.getData().getPromotions().getPromotionsName());
                        }else {
                            tv_goods_detail_cuxiao.setText("单品促销");

                        }

                    }else {
                        tv_goods_detail_name.setText(goodsDetail.getGoodsCnName());

                        tv_goods_detail_cuxiao.setVisibility(View.GONE);
                    }
                    if (!SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this).equals(""))
                    initCollection();
                }else {
                    if (goodsDetailSession.getErrorCode().equals("51011")||goodsDetailSession.getErrorCode().equals("51012")||goodsDetailSession.getErrorCode().equals("51013")||goodsDetailSession.getErrorCode().equals("51015")){
                        startActivity(new Intent(GoodsDetailActivity.this,NoGoodsActivity.class));
                        finish();
                    }else {
                        ToastUtils.showToastCenter(GoodsDetailActivity.this,goodsDetailSession.getErrorMessage());
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
    //初始化收藏状态
    private void initCollection() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.IsCollectByGoodsId(SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this),goodsDetail.getGoodsId(),new Callback<IsCollection>() {
            @Override
            public void success(IsCollection addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    if (addSub.isData()){
                        isCollection=true;
                        iv_go_collection.setImageResource(R.drawable.shangpinxiangqing_yiguanzhu);
                    }else {
                        isCollection=false;
                        iv_go_collection.setImageResource(R.drawable.shangpinxiangqing_guanzhu);
                    }
                }else {
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(GoodsDetailActivity.this,"收藏失败!");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_next:
                DialogUtils.showShareToast(GoodsDetailActivity.this,"我在笨土豆发现了一个不错的商品，快来看看吧。",shareText,url,image);
                break;
            case R.id.tv_btn_chakan:
                //查看图文详情
                tv_btn_chakan.setText("图文详情");
                webView.loadDataWithBaseURL(Constant.URL_BASE_IMG,htmls,"text/html", "utf-8", null);
                break;
            case R.id.iv_go_cart:
                //去购物车
                if (SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this).isEmpty()){
                    startActivity(new Intent(GoodsDetailActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(GoodsDetailActivity.this,ShoppingCartActivity.class));
                }
                break;
            case R.id.iv_go_collection:
                //点击了收藏
                if (SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this).isEmpty()){
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,"您还没有登录哟~");
                }else {
                    if (isCollection){
                        cancelCollection();
                    }else {
                        addCollection();
                    }

                }
                break;
            case R.id.tv_count_sub:
                //减
                if (Integer.valueOf(et_count_value.getText().toString())<=goodsDetail.getWholesaleMoq()||Integer.valueOf(et_count_value.getText().toString())<=1){
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,"数量不能低于起订量!");
                }else {
                    et_count_value.setText(Integer.valueOf(et_count_value.getText().toString())-1+"");
                }
                break;
            case R.id.tv_count_add:
                //加
                et_count_value.setText(Integer.valueOf(et_count_value.getText().toString())+1+"");
                break;
            case R.id.tv_sure:
                //确认
                if (isCartorCommit){
                    commitOrder();
                }else {
                    addGoodsToCart();
                }
                llt_add_goods.setVisibility(View.GONE);
                break;
            case R.id.tv_add_to_cart:
                //添加到购物车
                isCartorCommit=false;
                if (SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this).isEmpty()){
                    startActivity(new Intent(GoodsDetailActivity.this,LoginActivity.class));
                }else {
                    llt_add_goods.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+goodsDetail.getGoodsImg()+Constant.IMG_200,iv_small_goods);
                    tv_goods_small_name.setText(goodsDetail.getGoodsCnName());
                    tv_goods_small_price.setText(VerifitionUtil.getRMBStringPrice(goodsDetail.getShopPriceCny()));
                    if (goodsDetail.getWholesaleMoq()>=1){
                        et_count_value.setText(goodsDetail.getWholesaleMoq()+"");
                    }else {
                        et_count_value.setText(1+"");
                    }

                }
                break;
            case R.id.btn_commit_order:
                //立即购买
                isCartorCommit=true;
                if (SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this).isEmpty()){
                    startActivity(new Intent(GoodsDetailActivity.this,LoginActivity.class));
                }else {
                    llt_add_goods.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+goodsDetail.getGoodsImg()+Constant.IMG_200,iv_small_goods);
                    tv_goods_small_name.setText(goodsDetail.getGoodsCnName());
                    tv_goods_small_price.setText(VerifitionUtil.getRMBStringPrice(goodsDetail.getShopPriceCny()));
                    if (goodsDetail.getWholesaleMoq()>=1){
                        et_count_value.setText(goodsDetail.getWholesaleMoq()+"");
                    }else {
                        et_count_value.setText(1+"");
                    }

                }
                break;
            case R.id.iv_close:
                llt_add_goods.setVisibility(View.GONE);
                break;
            case R.id.tv_bg_close:
                llt_add_goods.setVisibility(View.GONE);
                break;
        }
    }
    //取消收藏
    private void cancelCollection() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.delCollect(SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this),goodsDetail.getGoodsId(),new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    iv_go_collection.setImageResource(R.drawable.shangpinxiangqing_guanzhu);
                    isCollection=false;
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,"取消收藏成功!");
                }else {
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(GoodsDetailActivity.this,"取消收藏失败!");
            }
        });
    }
    //添加收藏
    private void addCollection() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.addCollect(SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this),goodsDetail.getGoodsId(),new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    iv_go_collection.setImageResource(R.drawable.shangpinxiangqing_yiguanzhu);
                    isCollection=true;
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,"收藏成功!");
                }else {
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(GoodsDetailActivity.this,"收藏失败!");
            }
        });
    }

    private void commitOrder() {
        final ProgressHUD progressHUD = ProgressHUD.show(this, "提交中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.confirmOrderInfo(SharePreferencesUtils.getBtdToken(this),getStringMap(),"CNY", new CallbackSupport<OrderDetail>(progressHUD,this) {
            @Override
            public void success(OrderDetail orderDetail, Response response) {
                progressHUD.dismiss();
                if (orderDetail.getStatus().equals("1")){
                    Intent intent = new Intent();
                    intent.setClass(GoodsDetailActivity.this, OrderDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order_detail_back", orderDetail.getData());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    if (orderDetail.getErrorCode().equals("51103")){
                        DialogUtils.showDialogs(getLayoutInflater(),GoodsDetailActivity.this,orderDetail.getData().getErrorDepotName());
                    }else if (orderDetail.getErrorCode().equals("51105")){
                        if (orderDetail.getData().getErrorGoodsList().get(0).getGoodsError().equals("51014")){
                            ToastUtils.showToastCenter(GoodsDetailActivity.this,"库存不足");
                        }else {
                            ToastUtils.showToastCenter(GoodsDetailActivity.this,"失效商品");
                        }

                    }else if (orderDetail.getErrorCode().equals("51106")){
                        ToastUtils.showToastCenter(GoodsDetailActivity.this,"失效商品");
                    }else {
                        ToastUtils.showToastCenter(GoodsDetailActivity.this,"服务器离家出走了,请稍后再试");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private String getStringMap() {
       return goodsDetail.getGoodsId()+"~"+et_count_value.getText().toString()+"";

    }

    //添加到购物车
    private void addGoodsToCart() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.addUserCartTwo(SharePreferencesUtils.getBtdToken(GoodsDetailActivity.this),goodsDetail.getGoodsId(),Integer.valueOf(et_count_value.getText().toString()),new Callback<AddSub>() {
            @Override
            public void success(AddSub addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,"加入购物车成功!");
//                    iv_depot_icon.startAnimation(AnimationUtils.loadAnimation(GoodsDetailActivity.this,R.anim.shakeanim));
                    tv_cart_num.setVisibility(View.VISIBLE);
                    tv_cart_num.setText(String.valueOf(addSub.getData().getCartGoodsNum()));
                }else {
                    ToastUtils.showToastCenter(GoodsDetailActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(GoodsDetailActivity.this,"加入购物车失败!");
            }
        });
    }

    //设置轮播图
    private void banner_image() {
        convenientBanner.setPages(new CBViewHolderCreator<GoodsDetailImageHolderView>() {
            @Override
            public GoodsDetailImageHolderView createHolder() {
                return new GoodsDetailImageHolderView();
            }
        },goodsPictureDatas)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator_focused, R.drawable.ic_page_indicator});
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            com.umeng.socialize.utils.Log.d("plat","platform"+platform);
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "QQ分享成功啦");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "微信分享成功啦");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "朋友圈分享成功啦");
            }else {
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "微博分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "QQ分享失败了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "微信分享失败了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "朋友圈分享失败了");
            }else {
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "微博分享失败了");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "QQ分享取消了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "微信分享取消了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "朋友圈分享取消了");
            }else {
                ToastUtils.showToastCenter(GoodsDetailActivity.this, "微博分享取消了");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("result","onActivityResult");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        convenientBanner.startTurning(5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }
}
