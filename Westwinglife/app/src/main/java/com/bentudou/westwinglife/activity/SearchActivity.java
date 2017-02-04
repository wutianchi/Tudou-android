package com.bentudou.westwinglife.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.Collection;
import com.bentudou.westwinglife.json.SearchKeyList;
import com.bentudou.westwinglife.json.SearchKeySession;
import com.bentudou.westwinglife.json.SearchKeyStart;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.FlowLayout;
import com.gunlei.app.ui.view.ProgressHUD;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/17.
 */
public class SearchActivity extends Activity {
    private ImageView btn_search_back;
    private EditText et_search;
    private LinearLayout llt_lishi,llt_delete;
    ProgressHUD progressHUD = null;
    private SearchKeyStart searchKeyStart;
    private List<Collection> collections;
    private FlowLayout flowLayout,lishi_flowlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initView();
        if(getWindow().getAttributes().softInputMode== WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }
    }

    private void initLishi() {
        if (null==SharePreferencesUtils.getCollection(this)){
            collections = new ArrayList<>();
        }else {
            collections=SharePreferencesUtils.getCollection(this);
        }
        if (collections.size()>0){
            llt_lishi.setVisibility(View.VISIBLE);
            lishi_flowlayout.setVisibility(View.VISIBLE);
            lishi_flowlayout.removeAllViews();
            for (int i=0;i<collections.size();i++){
                final TextView tv=new TextView(SearchActivity.this);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);

                tv.setTextColor(getResources().getColor(R.color.gray_base));
                tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_gray_border));
                tv.setText(collections.get(i).getName());
                tv.setGravity(Gravity.CENTER);
                int textPaddingV = 10;
                int textPaddingH = 30;
                tv.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
                tv.setClickable(true);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addToSearchList(tv.getText().toString());
                        startActivity(new Intent(SearchActivity.this,SearchGoodsListActivity.class).putExtra("keyWords",tv.getText().toString()));
                    }
                });
                lishi_flowlayout.addView(tv,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                        WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        }else {
            llt_lishi.setVisibility(View.GONE);
            lishi_flowlayout.setVisibility(View.GONE);
        }
    }

    private void initView() {
        btn_search_back = (ImageView) findViewById(R.id.btn_search_back);
        llt_lishi = (LinearLayout) findViewById(R.id.llt_lishi);
        llt_delete = (LinearLayout) findViewById(R.id.llt_delete);
        et_search = (EditText) findViewById(R.id.et_search);
        flowLayout = (FlowLayout) findViewById(R.id.fl);
        lishi_flowlayout = (FlowLayout) findViewById(R.id.lishi_fl);
        et_search.setHint(Constant.search_name);
        initData();
        initLishi();
        llt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDelteDialogs();
            }
        });
        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){//修改回车键功能
// 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(), 0);
                    if (et_search.getText().toString().isEmpty()){
                        addToSearchList(Constant.search_name);
                        startActivity(new Intent(SearchActivity.this,SearchGoodsListActivity.class).putExtra("keyWords",Constant.search_name));
                    }else {
                        addToSearchList(et_search.getText().toString());
                        startActivity(new Intent(SearchActivity.this,SearchGoodsListActivity.class).putExtra("keyWords",et_search.getText().toString()));
                    }
                }
                return false;
            }

        });
        btn_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initLishi();
    }

    private void addToSearchList(String s) {
        List<Collection> list = new ArrayList<>();
        Collection collect = new Collection();
        collect.setName(s);
        for (int i=0;i<collections.size();i++){
            if (s.equals(collections.get(i).getName())){
                collections.remove(i);
                break;
            }
        }
//        if (collections.size()==20){
//            collections.remove(19);
//        }
        list.add(collect);
        list.addAll(collections);
        SharePreferencesUtils.saveCollection(list,this);
    }

    private void initData() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.getSearchKeyword(new CallbackSupport<SearchKeySession>(progressHUD, this) {
            @Override
            public void success(SearchKeySession searchKeySession, Response response) {
                progressHUD.dismiss();
                if (searchKeySession.getStatus().equals("1")){
                    searchKeyStart = searchKeySession.getData();
                    List<SearchKeyList> searchKeyLists = searchKeyStart.getSearchKeywordList();
                    int n = searchKeyLists.size();
                    for (int i=0;i<n;i++){
                        final TextView tv=new TextView(SearchActivity.this);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);

                        tv.setTextColor(getResources().getColor(R.color.gray_base));
                        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_gray_border));
                        tv.setText(searchKeyLists.get(i).getKeywordName());
                        tv.setGravity(Gravity.CENTER);
                        int textPaddingV = 10;
                        int textPaddingH = 30;
                        tv.setPadding(textPaddingH, textPaddingV, textPaddingH, textPaddingV);
                        tv.setClickable(true);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addToSearchList(tv.getText().toString());
                                startActivity(new Intent(SearchActivity.this,SearchGoodsListActivity.class).putExtra("keyWords",tv.getText().toString()));
                            }
                        });
                        flowLayout.addView(tv,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.
                                WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    }


                }else {
                    ToastUtils.showToastCenter(SearchActivity.this,searchKeySession.getErrorMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                super.failure(error);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    //删除历史记录提示
    public void showDelteDialogs() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_delete_search,
                null);
        TextView tv_store = (TextView) layout.findViewById(R.id.sure_go);
        TextView noSaveInfo = (TextView) layout.findViewById(R.id.cancel_go);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        tv_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collections.clear();
                SharePreferencesUtils.saveCollection(collections,SearchActivity.this);
                initLishi();
                dialog.dismiss();
            }
        });
        noSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
