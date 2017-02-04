package com.bentudou.westwinglife.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.ExpressAdapter;
import com.bentudou.westwinglife.adapter.GrowLogAdapter;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.Express;
import com.bentudou.westwinglife.json.ExpressMessage;
import com.bentudou.westwinglife.json.GrowInfo;
import com.bentudou.westwinglife.json.GrowList;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.view.MyListView;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/9/9.
 * 物流追踪
 */
public class LogisticalMessageActivity extends BaseTitleActivity {
    private SwipeRefreshLayout srllt_express;
    private MyListView mlv_express;
    private TextView tv_express_number;
    private List<ExpressMessage> expressMessageList;
    private ExpressAdapter expressAdapter;
    private String expressSn;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_logistical_message);
    }

    @Override
    protected void initView() {
        super.setTitleText("物流追踪");
        expressSn = getIntent().getStringExtra("express_sn");
        tv_express_number = (TextView) findViewById(R.id.tv_express_number);
        tv_express_number.setText("运单号:  "+expressSn);
        srllt_express = (SwipeRefreshLayout) findViewById(R.id.srllt_express);
        srllt_express.setColorSchemeResources(R.color.color_select, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srllt_express.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toRefresh();
            }
        });
        mlv_express = (MyListView) findViewById(R.id.mlv_express);
        initData();
    }
    //刷新数据
    private void toRefresh() {
        RTHttpClient.init("http://www.westwingexpress.com",this);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getExpressMessageByExpressSn(SharePreferencesUtils.getBtdToken(LogisticalMessageActivity.this),expressSn,new Callback<Express>() {
            @Override
            public void success(Express express, Response response) {
                RTHttpClient.init(Constant.URL_BASE_TEST,LogisticalMessageActivity.this);
                if (express.getStatus().equals("1")){
                    srllt_express.setRefreshing(false);
                    expressMessageList = express.getData();
                    expressAdapter = new ExpressAdapter(expressMessageList,LogisticalMessageActivity.this);
                    mlv_express.setAdapter(expressAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                RTHttpClient.init(Constant.URL_BASE_TEST,LogisticalMessageActivity.this);
                srllt_express.setRefreshing(false);
            }
        });
    }

    private void initData() {
        RTHttpClient.init("http://www.westwingexpress.com",this);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getExpressMessageByExpressSn(SharePreferencesUtils.getBtdToken(LogisticalMessageActivity.this),expressSn,new Callback<Express>() {
            @Override
            public void success(Express express, Response response) {
                RTHttpClient.init(Constant.URL_BASE_TEST,LogisticalMessageActivity.this);
                if (express.getStatus().equals("1")){
                    expressMessageList = express.getData();
                    expressAdapter = new ExpressAdapter(expressMessageList,LogisticalMessageActivity.this);
                    mlv_express.setAdapter(expressAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                RTHttpClient.init(Constant.URL_BASE_TEST,LogisticalMessageActivity.this);
            }
        });
    }
}
