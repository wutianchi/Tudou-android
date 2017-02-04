package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.HeadListAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.Goods;
import com.bentudou.westwinglife.json.GoodsList;
import com.bentudou.westwinglife.json.HeadList;
import com.bentudou.westwinglife.json.SpecialSale;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyGridView;
import com.gunlei.app.ui.view.ProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/17.
 * 商品列表页面
 */
public class GoodsClassActivity extends BaseTitleActivity {
    private MyGridView mgv_goods_list;
    private HeadListAdapter headListAdapter;
    private String categoryId;
    private String goodsClassName;
    private String bannerImg;
    private ImageView iv_banner_yunying;
    private ImageLoader mImageLoader;
    ProgressHUD progressHUD = null;
    private List<HeadList> headLists;//商品列表数据
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_goods_class);
        mImageLoader=ImageLoader.getInstance();
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
        categoryId = getIntent().getStringExtra("categoryId");
        goodsClassName = getIntent().getStringExtra("goods_class_name");
        bannerImg = getIntent().getStringExtra("banner_img");
        iv_banner_yunying = (ImageView) findViewById(R.id.iv_banner_yunying);
        mgv_goods_list = (MyGridView) findViewById(R.id.mgv_goods_list);
        mgv_goods_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(GoodsClassActivity.this, GoodsDetailActivity.class)
                        .putExtra("goodsId",headLists.get(position).getGoodsId()+""));
            }
        });
        if (goodsClassName.equals("特卖会")){
            super.setTitleText("");
            initTemai();
        }else if (goodsClassName.equals("运营商品")){
            super.setTitleText("");
            if (null==bannerImg||bannerImg.isEmpty()){
                Log.d("运营商品", "initView: 1-----"+bannerImg);
                iv_banner_yunying.setVisibility(View.GONE);
            }else {
                Log.d("运营商品", "initView: 2-----"+bannerImg);
                iv_banner_yunying.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG+bannerImg,iv_banner_yunying);
            }
            initYunying();
        }else {
            super.setTitleText(goodsClassName);
            if (null==bannerImg||bannerImg.isEmpty()){
                iv_banner_yunying.setVisibility(View.GONE);
            }else {
                iv_banner_yunying.setVisibility(View.VISIBLE);
                mImageLoader.displayImage(Constant.URL_BASE_IMG+bannerImg,iv_banner_yunying);
            }
            initData();
        }
    }
    //运营列表接口数据初始化
    private void initYunying() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getGoodsOperationsList(1,10000,categoryId,new CallbackSupport<SpecialSale>(progressHUD, this) {
            @Override
            public void success(SpecialSale specialSale, Response response) {
                progressHUD.dismiss();
                if (specialSale.getStatus().equals("1")){
                    if (specialSale.getData().getGoodsOperationsList().isEmpty()){
                        ToastUtils.showToastCenter(GoodsClassActivity.this,"暂无数据");
                    }else {
                        headLists = specialSale.getData().getGoodsOperationsList();
                        headListAdapter = new HeadListAdapter(headLists,GoodsClassActivity.this, mImageLoader);
                        mgv_goods_list.setAdapter(headListAdapter);
                    }
                }


            }
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
    //特卖列表接口数据初始化
    private void initTemai() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getIndexGoodsDiscountList(1,10000,new CallbackSupport<SpecialSale>(progressHUD, this) {
            @Override
            public void success(SpecialSale specialSale, Response response) {
                progressHUD.dismiss();
                if (specialSale.getStatus().equals("1")){
                    if (specialSale.getData().getGoodsOperationsList().isEmpty()){
                        ToastUtils.showToastCenter(GoodsClassActivity.this,"暂无数据");
                    }else {
                        headLists = specialSale.getData().getGoodsOperationsList();
                        headListAdapter = new HeadListAdapter(headLists,GoodsClassActivity.this, mImageLoader);
                        mgv_goods_list.setAdapter(headListAdapter);
                    }
                }


            }
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.readGoodsList(categoryId,new CallbackSupport<Goods>(progressHUD, this) {
            @Override
            public void success(Goods goods, Response response) {
                progressHUD.dismiss();
                if (goods.getStatus().equals("1")){
                    if (goods.getData().getGoodsList().isEmpty()){
                        ToastUtils.showToastCenter(GoodsClassActivity.this,"暂无数据");
                    }else {
                        headLists = goods.getData().getGoodsList();
                        headListAdapter = new HeadListAdapter(headLists,GoodsClassActivity.this, mImageLoader);
                        mgv_goods_list.setAdapter(headListAdapter);
                    }
                }


            }
            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
}
