package com.bentudou.westwinglife.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GoodsClassActivity;
import com.bentudou.westwinglife.activity.SearchActivity;
import com.bentudou.westwinglife.adapter.ThreeClassifyAdapter;
import com.bentudou.westwinglife.adapter.TwoClassifyAdapter;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.CategoryList;
import com.bentudou.westwinglife.json.Classify;
import com.gunlei.app.ui.view.ProgressHUD;
import com.nostra13.universalimageloader.core.ImageLoader;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/2.
 */
@Deprecated
public class TwoFragment extends Fragment {
    private ListView lv_two_class;
    private EditText et_search;
    private GridView gv_three_class;
    private TwoClassifyAdapter twoClassifyAdapter;
    private ThreeClassifyAdapter threeClassifyAdapter;
    private Handler myHandler;
    private ImageLoader mImageLoader;
    private LayoutInflater layoutInflater;
    ProgressHUD progressHUD = null;
    private int item = 0;
    public static int last =0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_two,null);
        mImageLoader=ImageLoader.getInstance();
        layoutInflater = getActivity().getLayoutInflater();
        initView(view);
        myHandler = new Handler();
        initData();
        return view;
    }
    private void initView(View view) {
        et_search = (EditText) view.findViewById(R.id.et_search);
        lv_two_class = (ListView) view.findViewById(R.id.lv_two_class);
        gv_three_class = (GridView) view.findViewById(R.id.gv_three_class);
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        et_search.setHint(Constant.search_name);
    }
    private void initData() {
        progressHUD = ProgressHUD.show(getActivity(), "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.readClassifyDatas(new CallbackSupport<Classify>(progressHUD, getActivity()) {
            @Override
            public void success(Classify classify, Response response) {
                progressHUD.dismiss();
                CategoryList categoryList = classify.getData();
                load(categoryList);
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
                //如果失败，直接使用本地数据
            }
        });
    }


    public void load(final CategoryList categoryList) {

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                twoClassifyAdapter = new TwoClassifyAdapter(categoryList.getCategoryList(),layoutInflater);
                lv_two_class.setAdapter(twoClassifyAdapter);
                lv_two_class.setOnItemClickListener(new MyOnItemClickListener(categoryList));
//                lv_two_class.setOnItemClickListener(listviewMemuOnItemClickListener);
                threeClassifyAdapter = new ThreeClassifyAdapter(categoryList.getCategoryList().get(item).getCategoryList(),mImageLoader,layoutInflater);
                gv_three_class.setAdapter(threeClassifyAdapter);
                gv_three_class.setOnItemClickListener(new MyOnItemClickListener(categoryList));
//                gAdapter = new HeadGridAdapter(headInfo.getCategoryList(), getActivity(), mImageLoader);
//                hotClass.setAdapter(gAdapter);
//                hotClass.setOnItemClickListener(new MyOnItemClickListener(headInfo));
//                //热门商品适配
//                adapter = new HeadListAdapter(headInfo.getRecommendGoodsList(), getActivity(), mImageLoader);
//                hotGod.setAdapter(adapter);
//                hotGod.setOnItemClickListener(new MyOnItemClickListener(headInfo));
            }
        }, 0);
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        final CategoryList categoryList;
        MyOnItemClickListener(CategoryList categoryList){
            this.categoryList = categoryList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()){
                //点击二级类目
                case R.id.lv_two_class:
//                    TextView name = (TextView) view.findViewById(R.id.tv_two_classify_name);
//                    if (position != item){
//                        TextView  lastName = (TextView) lv_two_class.getChildAt(item).findViewById(R.id.tv_two_classify_name);
//                        lastName.setSelected(false);
//                    }
//                    if (!name.isSelected()) {
//                        name.setSelected(true);
//                    }
//                    if (((ListView)parent).getTag() != null){
//
//                        ((View)((ListView)parent).getTag()).setSelected(false);
//
//                    }else {
//                        TextView  lastName = (TextView) lv_two_class.getChildAt(0).findViewById(R.id.tv_two_classify_name);
//                        lastName.setSelected(false);
//                    }
//                    ((ListView)parent).setTag(view);
//                    view.setSelected(true);
                    last = position;
                    twoClassifyAdapter.notifyDataSetChanged();
                    item = position;
                    threeClassifyAdapter = new ThreeClassifyAdapter(categoryList.getCategoryList().get(item).getCategoryList(),mImageLoader,layoutInflater);
                    gv_three_class.setAdapter(threeClassifyAdapter);
                    threeClassifyAdapter.notifyDataSetChanged();
                    break;
                //点击三级分类
                case R.id.gv_three_class:
//                    if(categoryList.getCategoryList().get(item).getCategoryList().get(position).getCategoryCnName()!=null){
                        startActivity(new Intent(getActivity(), GoodsClassActivity.class).putExtra("categoryId", "" +
                                categoryList.getCategoryList().get(item).getCategoryList().get(position).getCategoryId())
                                .putExtra("goods_class_name", categoryList.getCategoryList().get(item).getCategoryList().get(position).getCategoryCnName().toString()));
//                    }else {
//                        Toast.makeText(getActivity(),"暂无该类商品",Toast.LENGTH_SHORT).show();
//                    }
                        break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Constant.push_value=2;
    }
    //    AdapterView.OnItemClickListener listviewMemuOnItemClickListener = new AdapterView.OnItemClickListener() {
//
//        @Override
//
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//
//                                long id) {
//
//            if (((ListView)parent).getTag() != null){
//
//                ((View)((ListView)parent).getTag()).setSelected(false);
//
//            }else {
//                TextView  lastName = (TextView) lv_two_class.getChildAt(0).findViewById(R.id.tv_two_classify_name);
//                lastName.setSelected(false);
//            }
//
//            ((ListView)parent).setTag(view);
//
//            view.setSelected(true);
//            item = position;
//            threeClassifyAdapter = new ThreeClassifyAdapter(categoryList.getCategoryList().get(item).getCategoryList(),getActivity());
//            gv_three_class.setAdapter(threeClassifyAdapter);
//            threeClassifyAdapter.notifyDataSetChanged();
//
//        }
//
//    };
}
