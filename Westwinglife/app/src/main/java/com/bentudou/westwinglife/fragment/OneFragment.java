package com.bentudou.westwinglife.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GetCouponActivity;
import com.bentudou.westwinglife.activity.GoodsClassActivity;
import com.bentudou.westwinglife.activity.GoodsDetailActivity;
import com.bentudou.westwinglife.activity.LoginActivity;
import com.bentudou.westwinglife.activity.MyInviteCodeActivity;
import com.bentudou.westwinglife.activity.MyMessageListActivity;
import com.bentudou.westwinglife.activity.OverseasLiveActivity;
import com.bentudou.westwinglife.activity.SearchActivity;
import com.bentudou.westwinglife.activity.WebDetailActivity;
import com.bentudou.westwinglife.adapter.HeadListAdapter;
import com.bentudou.westwinglife.adapter.NewDanpinAdapter;
import com.bentudou.westwinglife.adapter.NewYunyingAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.dbcache.GLCacheProxy;
import com.bentudou.westwinglife.dbcache.ICache;
import com.bentudou.westwinglife.dbcache.bean.TimeType;
import com.bentudou.westwinglife.dbcache.impl.ICacheImpl;
import com.bentudou.westwinglife.json.CheckSign;
import com.bentudou.westwinglife.json.HeadImg;
import com.bentudou.westwinglife.json.HeadInfo;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.HeadSession;
import com.bentudou.westwinglife.json.IndexGoods;
import com.bentudou.westwinglife.json.UnReadMessage;
import com.bentudou.westwinglife.json.UserSign;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.MyGridView;
import com.bentudou.westwinglife.view.MyListView;
import com.bentudou.westwinglife.view.NetworkImageHolderView;
import com.bentudou.westwinglife.view.ToTopImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.gunlei.app.ui.view.ProgressHUD;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.Random;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/2.
 */
public class OneFragment extends Fragment implements View.OnClickListener, OnItemClickListener {
    public static final String url = String.format("%s/Index/getIndexData.htm", Constant.URL_BASE_TEST);
    private PullToRefreshScrollView scrollView;//滚动条
    private ScrollView mScrollView;
    private ToTopImageView imageView_to_top;//返回顶部
    private ConvenientBanner convenientBanner;//活动图片
    private MyListView mlv_new_banner,mlv_new_danpin;//七个运营位
    private NewYunyingAdapter newYunyingAdapter;//七个运营位适配器
    private NewDanpinAdapter newDanpinAdapter;//运营单品适配器
    private MyGridView hotGod;//尖货列表
    private HeadListAdapter adapter;//尖货适配器
    private EditText title_search;
    private TextView tv_qiandao,tv_new_message;
    private Button title_message;
    private LinearLayout center_bottom,llt_qiandao,llt_lingqu,llt_temai,llt_youhui,center_tittle,bottom_tittle;
    private ImageLoader mImageLoader;
    private Handler mHandler;
    private ICache cache;
    private ImageView iv_two_banner_one,iv_two_banner_two;
    ProgressHUD progressHUD = null;
    private int page=1;
    private boolean lastPage = false;
    private List<HeadList> headLists;//笨土豆精选数据
    private List<HeadImg> headImgs;//banner数据
    private HeadInfo headInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("OneFragment", "-----onActivityCreated: ");
        initView();
        mHandler = new Handler();
        mImageLoader=ImageLoader.getInstance();
        hotGod.setOnScrollListener(new PauseOnScrollListener(mImageLoader, true, false));
        initString();
        initGoogsData();
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_one,null);
        cache = new GLCacheProxy(new ICacheImpl(getActivity())).getProxy();//创建缓存
        cache.setCacheTime(1, TimeType.minute);
        return view;
    }

    /**
     * 初始化组件
     */
    public void initView(){
        imageView_to_top = (ToTopImageView) getView().findViewById(R.id.imageView_to_top);
        imageView_to_top.setLimitHeight(3000);
        scrollView = (PullToRefreshScrollView) getView().findViewById(R.id.home_scollview);
        mScrollView = scrollView.getRefreshableView();
        convenientBanner = (ConvenientBanner) getView().findViewById(R.id.convenientBanner);
        convenientBanner.setFocusable(true);
        convenientBanner.setFocusableInTouchMode(true);
        convenientBanner.requestFocus();
        title_search = (EditText) getView().findViewById(R.id.title_search);
        title_message = (Button) getView().findViewById(R.id.title_message);
        iv_two_banner_one = (ImageView) getView().findViewById(R.id.iv_two_banner_one);
        iv_two_banner_two = (ImageView) getView().findViewById(R.id.iv_two_banner_two);
        mlv_new_banner = (MyListView) getView().findViewById(R.id.mlv_new_banner);
        mlv_new_danpin = (MyListView) getView().findViewById(R.id.mlv_new_danpin);
        hotGod = (MyGridView) getView().findViewById(R.id.sort_car_list);
        llt_qiandao = (LinearLayout) getView().findViewById(R.id.llt_qiandao);
        llt_lingqu = (LinearLayout) getView().findViewById(R.id.llt_lingqu);
        llt_temai = (LinearLayout) getView().findViewById(R.id.llt_temai);
        llt_youhui = (LinearLayout) getView().findViewById(R.id.llt_youhui);
        center_tittle = (LinearLayout) getView().findViewById(R.id.center_tittle);
        bottom_tittle = (LinearLayout) getView().findViewById(R.id.bottom_tittle);
        tv_qiandao = (TextView) getView().findViewById(R.id.tv_qiandao);
        tv_new_message = (TextView) getView().findViewById(R.id.tv_new_message);
        center_bottom = (LinearLayout) getView().findViewById(R.id.center_bottom);
        llt_qiandao.setOnClickListener(this);
        llt_lingqu.setOnClickListener(this);
        llt_temai.setOnClickListener(this);
        llt_youhui.setOnClickListener(this);
        iv_two_banner_one.setOnClickListener(this);
        iv_two_banner_two.setOnClickListener(this);
        title_search.setOnClickListener(this);
        title_message.setOnClickListener(this);
        scrollView.setVisibility(View.INVISIBLE);
        scrollView.getRefreshableView().fullScroll(View.FOCUS_UP);
        mScrollView.smoothScrollTo(0, 0);
        scrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state,
                                    PullToRefreshBase.Mode direction) {
                switch (state){
                    case PULL_TO_REFRESH:
                        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(cache.getUpdateTimeText(url));
                        break;
                }
            }
        });
        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doRefresh();
//                        initGoogsData();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (lastPage){
                            scrollView.onRefreshComplete();
                        }else {
                            loadMore();
                        }
                    }
                }, 1000);
            }
        });
        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        imageView_to_top.tellMe(mScrollView);
                        break;
                }
                return false;
            }
        });
        if (SharePreferencesUtils.getBtdToken(getActivity()).equals("")){
            tv_qiandao.setText("每日签到");
        }else {
            if (!tv_qiandao.getText().toString().equals("已签到"))
                initQiandao();
            isNewMessage();
        }
    }
    private void initString(){

        if (cache.cacheAble(url)){
            //此处获取缓存数据，本地加载掉用
            headInfo = cache.getObject(cache.getData(url).getData(),HeadInfo.class);
            load();
            return;//加载本地数据，不请求网络
        }
        progressHUD = ProgressHUD.show(getActivity(), "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.readHomePageDatas(9,new CallbackSupport<HeadSession>(progressHUD, getActivity()) {
            @Override
            public void success(HeadSession headSession, Response response) {
                progressHUD.dismiss();
                headInfo = headSession.getData();
                load();
                cache.saveData(url, headInfo);//保存数据
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
                if (cache.getData(url) != null) {
                    headInfo = cache.getObject(cache.getData(url).getData(), HeadInfo.class);
                    load();
                } else {
                    //如果本地没有缓存数据，则提示没有网络
//                    linearLayoutWifi.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    //执行刷新操作
    private void doRefresh(){
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.readHomePageDatas(9,new CallbackSupport<HeadSession>(getActivity()) {
            @Override
            public void success(HeadSession headSession, Response response) {
                getCache();
                headInfo = headSession.getData();
                cache.saveData(url, headInfo);//保存数据
//                reLoad();
                scrollView.onRefreshComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
                if (cache.getData(url) != null) {
                    headInfo = cache.getObject(cache.getData(url).getData(), HeadInfo.class);
                    reLoad();
                } else {
                    //如果本地没有缓存数据，则提示没有网络
//                    linearLayoutWifi.setVisibility(View.VISIBLE);
                }
                scrollView.onRefreshComplete();
            }
        });
    }
//    //加载缓存数据
    private void getCache(){
        new Thread(){
            @Override
            public void run() {
                headInfo = cache.getObject(cache.getData(url).getData(), HeadInfo.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reLoad();
                    }
                });
            }
        }.start();
    }

    //装载缓存数据
    public void load() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.setVisibility(View.VISIBLE);
                if (!headInfo.getAdvertisementList().isEmpty()){
                    headImgs = headInfo.getAdvertisementList();
                    bannerSetImage();
                }
                int n= new Random().nextInt(headInfo.getSearchKeywordList().size());
                Constant.search_name = headInfo.getSearchKeywordList().get(n).getKeywordName();
                title_search.setHint(Constant.search_name);
                //两个类目适配
                Glide.with(getActivity()).load(Constant.URL_BASE_IMG+headInfo.getCategoryList().get(0).getCategoryImgPc()).into(iv_two_banner_one);
                Glide.with(getActivity()).load(Constant.URL_BASE_IMG+headInfo.getCategoryList().get(1).getCategoryImgPc()).into(iv_two_banner_two);
                iv_two_banner_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), GoodsClassActivity.class)
                                .putExtra("categoryId", headInfo.getCategoryList().get(0).getCategoryId().toString())
                                .putExtra("goods_class_name", headInfo.getCategoryList().get(0).getCategoryCnName()));

                    }
                });
                iv_two_banner_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), GoodsClassActivity.class)
                                .putExtra("categoryId", headInfo.getCategoryList().get(1).getCategoryId().toString())
                                .putExtra("goods_class_name", headInfo.getCategoryList().get(1).getCategoryCnName()));

                    }
                });
//                initYunying()
                //七个运营位适配
                newYunyingAdapter = new NewYunyingAdapter(headInfo.getOperatingPositionList(), getActivity(), mImageLoader);
                mlv_new_banner.setAdapter(newYunyingAdapter);
                if (null!=headInfo.getAppHomePageItemList()&&headInfo.getAppHomePageItemList().size()>0){
                    center_tittle.setVisibility(View.VISIBLE);
                    newDanpinAdapter = new NewDanpinAdapter(headInfo.getAppHomePageItemList(), getActivity(), mImageLoader);
                    mlv_new_danpin.setAdapter(newDanpinAdapter);
                    mlv_new_danpin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                                    .putExtra("goodsId",String.valueOf(headInfo.getAppHomePageItemList().get(i).getGoodsId())));
                        }
                    });
                }else {
                    center_tittle.setVisibility(View.GONE);
                }
                scrollView.getRefreshableView().fullScroll(View.FOCUS_UP);
                mScrollView.smoothScrollTo(0, 0);
                Log.d("OneFragment", "-----11111: ");
            }
        }, 0);
        Log.d("OneFragment", "-----22222: ");
    }
   //刷新数据
    public void reLoad() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.setVisibility(View.VISIBLE);
                headImgs = headInfo.getAdvertisementList();
                convenientBanner.notifyDataSetChanged();
                int n= new Random().nextInt(headInfo.getSearchKeywordList().size());
                Constant.search_name = headInfo.getSearchKeywordList().get(n).getKeywordName();
                title_search.setHint(Constant.search_name);
                //两个类目适配
                Glide.with(getActivity()).load(Constant.URL_BASE_IMG+headInfo.getCategoryList().get(0).getCategoryImgPc()).into(iv_two_banner_one);
                Glide.with(getActivity()).load(Constant.URL_BASE_IMG+headInfo.getCategoryList().get(1).getCategoryImgPc()).into(iv_two_banner_two);
                iv_two_banner_one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), GoodsClassActivity.class)
                                .putExtra("categoryId", headInfo.getCategoryList().get(0).getCategoryId().toString())
                                .putExtra("goods_class_name", headInfo.getCategoryList().get(0).getCategoryCnName()));

                    }
                });
                iv_two_banner_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), GoodsClassActivity.class)
                                .putExtra("categoryId", headInfo.getCategoryList().get(1).getCategoryId().toString())
                                .putExtra("goods_class_name", headInfo.getCategoryList().get(1).getCategoryCnName()));

                    }
                });
                //七个运营位适配
             if (null!=newYunyingAdapter)
                    newYunyingAdapter.notifyDataSetChanged();
                if (null!=headInfo.getAppHomePageItemList()&&headInfo.getAppHomePageItemList().size()>0&&null!=newDanpinAdapter){
                    center_tittle.setVisibility(View.VISIBLE);
                    newDanpinAdapter.notifyDataSetChanged();
                }else {
                    center_tittle.setVisibility(View.GONE);
                }
                scrollView.getRefreshableView().fullScroll(View.FOCUS_UP);
                mScrollView.smoothScrollTo(0, 0);
            }
        }, 0);
    }

    //适配运营位

    //设置轮播图
    private void bannerSetImage() {
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },headImgs)
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator_focused, R.drawable.ic_page_indicator})
                .setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(int position) {
        //这里是banner页跳转
        if (null!=headImgs.get(position).getAdvertisementType()){
            if (headImgs.get(position).getAdvertisementType().equals("01")){
                startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                        .putExtra("goodsId",String.valueOf(headImgs.get(position).getAdvertisementLinkId())));

            }else if (headImgs.get(position).getAdvertisementType().equals("02")){
                startActivity(new Intent(getActivity(), GoodsClassActivity.class)
                        .putExtra("categoryId",String.valueOf(headImgs.get(position).getAdvertisementLinkId()))
                        .putExtra("goods_class_name", "热门推荐"));
            }else if (headImgs.get(position).getAdvertisementType().equals("03")){
                if (headImgs.get(position).getAdvertisementLink().equals("http://app.coupon.list")){
                    if (SharePreferencesUtils.getBtdToken(getActivity()).equals("")){
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }else {
                        startActivity(new Intent(getActivity(), GetCouponActivity.class));
                    }
                }else if (headImgs.get(position).getAdvertisementLink().equals("http://app.live.list")){
                    MobclickAgent.onEvent(getActivity(), "user_click_goods_live");
                    startActivity(new Intent(getActivity(), OverseasLiveActivity.class));
                }else {
                    if (null!=headImgs.get(position).getAdvertisementName()){
                        startActivity(new Intent(getActivity(), WebDetailActivity.class)
                                .putExtra("web_url",headImgs.get(position).getAdvertisementLink()).putExtra("link_name",headImgs.get(position).getAdvertisementName()));
                    }else {
                        startActivity(new Intent(getActivity(), WebDetailActivity.class)
                                .putExtra("web_url",headImgs.get(position).getAdvertisementLink()).putExtra("link_name","活动详情"));

                    }
                }
            }else {
                if (null!=headImgs.get(position).getAdvertisementName()){
                    startActivity(new Intent(getActivity(), WebDetailActivity.class)
                            .putExtra("web_url",headImgs.get(position).getAdvertisementLink()).putExtra("link_name",headImgs.get(position).getAdvertisementName()));
                }else {
                    startActivity(new Intent(getActivity(), WebDetailActivity.class)
                            .putExtra("web_url",headImgs.get(position).getAdvertisementLink()).putExtra("link_name","活动详情"));

                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.title_message:
                if (SharePreferencesUtils.getBtdToken(getActivity()).equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), MyMessageListActivity.class));
                }
                break;
            case R.id.llt_qiandao:
                if (SharePreferencesUtils.getBtdToken(getActivity()).equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    if (tv_qiandao.getText().toString().equals("已签到")){
                        ToastUtils.showToastCenter(getActivity(),"今天您已经签到过了哟");
                    }else {
                        toSign();
                    }
                }
                break;
            case R.id.llt_lingqu:
                if (SharePreferencesUtils.getBtdToken(getActivity()).equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), MyInviteCodeActivity.class)
                            .putExtra("invite_code",SharePreferencesUtils.getInviteCode(getActivity())));
                }
                break;
            case R.id.llt_temai:
                startActivity(new Intent(getActivity(), GoodsClassActivity.class)
                        .putExtra("categoryId","1")
                        .putExtra("goods_class_name", "特卖会"));
                break;
            case R.id.llt_youhui:
                if (SharePreferencesUtils.getBtdToken(getActivity()).equals("")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), GetCouponActivity.class));
                }
                break;
        }
    }


    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        final IndexGoods indexGoods;
        MyOnItemClickListener(IndexGoods indexGoods){
            this.indexGoods = indexGoods;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()){
                //点击热门商品
                case R.id.sort_car_list:
                    startActivity(new Intent(getActivity(), GoodsDetailActivity.class)
                            .putExtra("goodsId",indexGoods.getData().getGoodsOperationsList().get(position).getGoodsId()+""));
                    break;
                default:
                    break;
            }
        }
    }
    //签到接口
    private void toSign() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.createUserSign(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<UserSign>() {
            @Override
            public void success(UserSign userSign, Response response) {
                if (userSign.getStatus().equals("1")){
                    if (userSign.isData()){
                        tv_qiandao.setText("已签到");
                        ToastUtils.showToastCenter(getActivity(),"签到成功");
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
//        Constant.push_value=1;
        Log.d("OneFragment", "-----onStart: ");
    }
    //获取是否签到接口
    private void initQiandao() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.checkUserSign(SharePreferencesUtils.getBtdToken(getActivity()),new Callback<CheckSign>() {
            @Override
            public void success(CheckSign checkSign, Response response) {
                if (checkSign.getStatus().equals("1")){
                    if (checkSign.getData().isUserSign()){
                        tv_qiandao.setText("已签到");
                    }else {
                        tv_qiandao.setText("每日签到");
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
    private void initGoogsData() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getIndexSiftGoodsList(1,10,new CallbackSupport<IndexGoods>( getActivity()) {
            @Override
            public void success(IndexGoods indexGoods, Response response) {
                page=1;
                //热门商品适配
                if (indexGoods.getData().isLastPage()){
                    lastPage=true;
                    center_bottom.setVisibility(View.VISIBLE);
                }
                headLists = indexGoods.getData().getGoodsOperationsList();
                adapter = new HeadListAdapter(headLists, getActivity(), mImageLoader);
                hotGod.setAdapter(adapter);
                hotGod.setOnItemClickListener(new MyOnItemClickListener(indexGoods));
//                scrollView.getRefreshableView().fullScroll(View.FOCUS_UP);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
//                if (cache.getData(url) != null) {
//                    load(cache.getObject(cache.getData(url).getData(), HeadInfo.class));
//                } else {
                    //如果本地没有缓存数据，则提示没有网络
//                    linearLayoutWifi.setVisibility(View.VISIBLE);
//                }
            }
        });
    }
    private void loadMore(){
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getIndexSiftGoodsList(++page,10,new CallbackSupport<IndexGoods>( getActivity()) {
            @Override
            public void success(IndexGoods indexGoods, Response response) {
                if (indexGoods.getData().isLastPage()){
                    lastPage=true;
                    center_bottom.setVisibility(View.VISIBLE);
                }
                //热门商品适配
                headLists.addAll(indexGoods.getData().getGoodsOperationsList());
                adapter.notifyDataSetChanged();
                scrollView.onRefreshComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
//                if (cache.getData(url) != null) {
//                    load(cache.getObject(cache.getData(url).getData(), HeadInfo.class));
//                } else {
                    //如果本地没有缓存数据，则提示没有网络
//                    linearLayoutWifi.setVisibility(View.VISIBLE);
//                }
                scrollView.onRefreshComplete();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //更新View
        scrollView.setVisibility(View.VISIBLE);
        convenientBanner.startTurning(5000);
//     doRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    @Override
    public void onDestroy() {
        imageView_to_top.clearCallBacks();
        super.onDestroy();
    }
}
