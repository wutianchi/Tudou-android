package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.GrowLogAdapter;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.GrowInfo;
import com.bentudou.westwinglife.json.GrowList;
import com.bentudou.westwinglife.json.UserSign;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.MyListView;

import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/2.
 * 成长之路
 */
public class GrowLogActivity extends BaseTitleActivity implements View.OnClickListener {
    private SwipeRefreshLayout srllt_grow;
    private MyListView mlv_grow;
    private List<GrowInfo> growInfoList;
    private GrowLogAdapter growLogAdapter;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_grow_log);
    }

    @Override
    protected void initView() {
        super.setTitleText("成长之路");
        title_next.setText("成长规则");
        title_next.setTextSize(12);
        title_next.setOnClickListener(this);
        srllt_grow = (SwipeRefreshLayout) findViewById(R.id.srllt_grow);
        srllt_grow.setColorSchemeResources(R.color.color_select, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        srllt_grow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toRefresh();
            }
        });
        mlv_grow = (MyListView) findViewById(R.id.mlv_grow);
        initData();
    }

    private void toRefresh() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findUserGrowthLogList(SharePreferencesUtils.getBtdToken(GrowLogActivity.this),new Callback<GrowList>() {
            @Override
            public void success(GrowList growList, Response response) {
                if (growList.getStatus().equals("1")){
                    srllt_grow.setRefreshing(false);
                    growInfoList = growList.getData();
                    growLogAdapter = new GrowLogAdapter(growInfoList,GrowLogActivity.this);
                    mlv_grow.setAdapter(growLogAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                srllt_grow.setRefreshing(false);
            }
        });
    }

    private void initData() {
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findUserGrowthLogList(SharePreferencesUtils.getBtdToken(GrowLogActivity.this),new Callback<GrowList>() {
            @Override
            public void success(GrowList growList, Response response) {
                if (growList.getStatus().equals("1")){
                    growInfoList = growList.getData();
                    growLogAdapter = new GrowLogAdapter(growInfoList,GrowLogActivity.this);
                    mlv_grow.setAdapter(growLogAdapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_next:
                startActivity(new Intent(GrowLogActivity.this,GrowLawActivity.class));
                break;
        }
    }
}
