package com.bentudou.westwinglife.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.SecondClassAdapter;
import com.bentudou.westwinglife.adapter_r.BaseRecyclerAdapter;
import com.bentudou.westwinglife.adapter_r.NewTwoFragmentAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.CategoryList;
import com.bentudou.westwinglife.json.Classify;
import com.bentudou.westwinglife.json.ClassifyDatas;
import com.bentudou.westwinglife.json.OneClassify;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.view.ProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/10/12.
 */
public class SecondClassActivity extends BaseTitleActivity {
    private ListView lv_second_class;
    private SecondClassAdapter secondClassAdapter;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private int Categoryid;
    ProgressHUD progressHUD = null;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_second_class);
        layoutInflater = getLayoutInflater();
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    protected void initView() {
        super.setTitleText("");
        Categoryid = getIntent().getIntExtra("Categoryid",0);
        lv_second_class = (ListView) findViewById(R.id.lv_second_class);
        initData();
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.findCategoryListByFirstCategoryId(Categoryid,new CallbackSupport<OneClassify>(progressHUD, this) {
            @Override
            public void success(OneClassify oneClassify, Response response) {
                progressHUD.dismiss();
                if (oneClassify.getStatus().equals("1")&&null!=oneClassify.getData()){
                    secondClassAdapter = new SecondClassAdapter(oneClassify.getData(),SecondClassActivity.this,layoutInflater,imageLoader);
                    lv_second_class.setAdapter(secondClassAdapter);
                }else {

                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
            }
        });
    }
}
