package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.HeadListAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Goods;
import com.bentudou.westwinglife.json.GoodsList;
import com.bentudou.westwinglife.json.SearchGoods;
import com.bentudou.westwinglife.json.SearchGoodsList;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.bentudou.westwinglife.view.MyGridView;
import com.gunlei.app.ui.view.ProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/17.
 */
public class SearchGoodsListActivity extends BaseTitleActivity {
    private MyGridView mgv_goods_list;
    private ScrollView sv_data;
    private TextView tv_no_data;
    private HeadListAdapter headListAdapter;
    private String keyWords;
    private String goodsClassName;
    ProgressHUD progressHUD = null;
    private SearchGoodsList searchGoodsList;//sousuo商品列表数据
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_goods_class);
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
        keyWords = getIntent().getStringExtra("keyWords");
        super.setTitleText("搜索结果");
        sv_data = (ScrollView) findViewById(R.id.sv_data);
        mgv_goods_list = (MyGridView) findViewById(R.id.mgv_goods_list);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        mgv_goods_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(SearchGoodsListActivity.this, GoodsDetailActivity.class)
                        .putExtra("goodsId",searchGoodsList.getGoodsList().get(position).getGoodsId()+""));
            }
        });
        initData();
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getSearchGoodsList(keyWords,new CallbackSupport<SearchGoods>(progressHUD, this) {
            @Override
            public void success(SearchGoods searchGoods, Response response) {
                progressHUD.dismiss();
                if (searchGoods.getStatus().equals("1")){
                    if (searchGoods.getData().getGoodsList().isEmpty()){
                        sv_data.setVisibility(View.GONE);
                        tv_no_data.setVisibility(View.VISIBLE);
                    }else {
                        sv_data.setVisibility(View.VISIBLE);
                        tv_no_data.setVisibility(View.GONE);
                        searchGoodsList = searchGoods.getData();
                        headListAdapter = new HeadListAdapter(searchGoodsList.getGoodsList(),SearchGoodsListActivity.this, ImageLoader.getInstance());
                        mgv_goods_list.setAdapter(headListAdapter);
                    }

                }else {
                    ToastUtils.showToastCenter(SearchGoodsListActivity.this,searchGoods.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
}
