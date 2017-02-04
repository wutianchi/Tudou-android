package com.bentudou.westwinglife.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.SearchActivity;
import com.bentudou.westwinglife.activity.SecondClassActivity;
import com.bentudou.westwinglife.adapter_r.BaseRecyclerAdapter;
import com.bentudou.westwinglife.adapter_r.NewTwoFragmentAdapter;
import com.bentudou.westwinglife.adapter_r.OneClassAdapter;
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
 * Created by lzz on 2016/10/11.
 */
public class NewTwoFragment extends Fragment {
    private RecyclerView rv_one_class;
    private EditText et_search;
    private ImageLoader mImageLoader;
    private LayoutInflater layoutInflater;
    ProgressHUD progressHUD = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.new_two_fragment,null);
        mImageLoader=ImageLoader.getInstance();
        layoutInflater =getActivity().getLayoutInflater();
        initView(view);
        return view;
    }

    private void initView(View view) {
        et_search = (EditText) view.findViewById(R.id.et_search);
        rv_one_class = (RecyclerView) view.findViewById(R.id.rv_one_class);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rv_one_class.setLayoutManager(gridLayoutManager);
        rv_one_class.setItemAnimator(new DefaultItemAnimator());
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        et_search.setHint(Constant.search_name);
        initData();
    }

    private void initData() {
        progressHUD = ProgressHUD.show(getActivity(), "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getFirstCategoryList(new CallbackSupport<OneClassify>(progressHUD, getActivity()) {
            @Override
            public void success(final OneClassify oneClassify, Response response) {
                progressHUD.dismiss();
//                OneClassAdapter oneClassAdapter = new OneClassAdapter(getActivity(),categoryList.getCategoryList());
//                rv_one_class.setAdapter(oneClassAdapter);
                if (oneClassify.getStatus().equals("1")){
                    NewTwoFragmentAdapter newTwoFragmentAdapter = new NewTwoFragmentAdapter();
                    rv_one_class.setAdapter(newTwoFragmentAdapter);
                    newTwoFragmentAdapter.addDatas((ArrayList<ClassifyDatas>) oneClassify.getData());
                    newTwoFragmentAdapter.setHeaderView(layoutInflater.inflate(R.layout.category_header, rv_one_class, false));
                    newTwoFragmentAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position, Object data) {
                            startActivity(new Intent(getActivity(), SecondClassActivity.class).putExtra("Categoryid",oneClassify.getData().get(position).getCategoryId()));
                        }
                    });
                }

            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        Constant.push_value=2;
    }
}
