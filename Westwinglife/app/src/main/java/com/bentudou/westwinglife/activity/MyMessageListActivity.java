package com.bentudou.westwinglife.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.MyMessageListAdapter;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.MessageInfo;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.json.UserMessage;
import com.bentudou.westwinglife.library.RefreshSwipeMenuListView;
import com.bentudou.westwinglife.library.SwipeMenu;
import com.bentudou.westwinglife.library.SwipeMenuCreator;
import com.bentudou.westwinglife.library.SwipeMenuItem;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/12.
 */
public class MyMessageListActivity extends BaseTitleActivity implements View.OnClickListener,RefreshSwipeMenuListView.OnRefreshListener {
    private RefreshSwipeMenuListView rsmLv;
    private MyMessageListAdapter messageListAdapter;
    private List<MessageInfo> messageInfoList = new ArrayList<>();
    private TextView tv_no_collect;
    private int page=1;
    private int total;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_message_list);
    }

    @Override
    protected void initView() {
        super.setTitleText("消息中心");
        title_next.setText("全部标为已读");
        title_next.setTextColor(getResources().getColor(R.color.gray_text8));
        title_next.setTextSize(12);
        title_next.setOnClickListener(this);
        rsmLv = (RefreshSwipeMenuListView) findViewById(R.id.swipe);
        tv_no_collect = (TextView) findViewById(R.id.tv_no_collect);
        rsmLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MyMessageListActivity.this,MessageInfoActivity.class).putExtra("id",String.valueOf(messageInfoList.get(position-1).getId())).putExtra("sysmsgId",""));
            }
        });
        initData();
    }

    private void initData() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.selectUserMessageList(SharePreferencesUtils.getBtdToken(this),1,1000,new Callback<UserMessage>() {
            @Override
            public void success(UserMessage addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    total=addSub.getData().getTotal();
                    if (addSub.getData().getTotal()==0){
                        tv_no_collect.setVisibility(View.VISIBLE);
                        rsmLv.setVisibility(View.GONE);
                    }else {
                        tv_no_collect.setVisibility(View.GONE);
                        rsmLv.setVisibility(View.VISIBLE);
                        messageInfoList =addSub.getData().getRows();
                        messageListAdapter = new MyMessageListAdapter(MyMessageListActivity.this,messageInfoList);
                        rsmLv.setAdapter(messageListAdapter);
                        rsmLv.setListViewMode(RefreshSwipeMenuListView.HEADER);
                        rsmLv.setOnRefreshListener(MyMessageListActivity.this);

                        SwipeMenuCreator creator = new SwipeMenuCreator() {
                            @Override
                            public void create(SwipeMenu menu) {
                                // 创建删除选项
                                SwipeMenuItem argeeItem = new SwipeMenuItem(getApplicationContext());
                                argeeItem.setBackground(new ColorDrawable(getResources().getColor(R.color.del)));
                                argeeItem.setWidth(dp2px(80, getApplicationContext()));
                                argeeItem.setTitle("删除");
                                argeeItem.setTitleSize(16);
                                argeeItem.setTitleColor(Color.WHITE);
                                menu.addMenuItem(argeeItem);
                            }
                        };

                        rsmLv.setMenuCreator(creator);

                        rsmLv.setOnMenuItemClickListener(new RefreshSwipeMenuListView.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                                switch (index) {
                                    case 0: //第一个选项
                                        deleteMessage(messageInfoList.get(position).getId(),position);
                                        break;

                                }
                            }
                        });
                    }
                }else {
                    ToastUtils.showToastCenter(MyMessageListActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(MyMessageListActivity.this,"获取消息失败!");
            }
        });
    }

    @Override
    public void onRefresh() {
        rsmLv.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                potatoService.selectUserMessageList(SharePreferencesUtils.getBtdToken(MyMessageListActivity.this),1,1000,new Callback<UserMessage>() {
                    @Override
                    public void success(UserMessage addSub, Response response) {
                        if (addSub.getStatus().equals("1")){
                            page =1;
                            total=addSub.getData().getTotal();
                            if (addSub.getData().getTotal()==0){
                                tv_no_collect.setVisibility(View.VISIBLE);
                                rsmLv.setVisibility(View.GONE);
                            }else {
                                tv_no_collect.setVisibility(View.GONE);
                                rsmLv.setVisibility(View.VISIBLE);
                                messageInfoList.clear();
                                messageInfoList.addAll(addSub.getData().getRows());
                                messageListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        ToastUtils.showToastCenter(MyMessageListActivity.this,"获取消息失败!");
                    }
                });
                rsmLv.complete();
            }
        },2000);
    }

    @Override
    public void onLoadMore() {
        if (total==messageInfoList.size()){
            rsmLv.complete();
            return;
        }
        rsmLv.postDelayed(new Runnable() {
            @Override
            public void run() {
                final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                potatoService.selectUserMessageList(SharePreferencesUtils.getBtdToken(MyMessageListActivity.this),++page,1000,new Callback<UserMessage>() {
                    @Override
                    public void success(UserMessage addSub, Response response) {
                        if (addSub.getStatus().equals("1")){
                            if (addSub.getData().getTotal()==0){
                                tv_no_collect.setVisibility(View.VISIBLE);
                                rsmLv.setVisibility(View.GONE);
                            }else {
                                tv_no_collect.setVisibility(View.GONE);
                                rsmLv.setVisibility(View.VISIBLE);
                                messageInfoList.addAll(addSub.getData().getRows());
                                messageListAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        ToastUtils.showToastCenter(MyMessageListActivity.this,"获取消息失败!");
                    }
                });
                rsmLv.complete();
            }
        },2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_next:
                readAllMessage();
                break;
        }
    }
    private void readAllMessage() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.markUserMessageAsRead(SharePreferencesUtils.getBtdToken(this),new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    initData();
                }else {
                    ToastUtils.showToastCenter(MyMessageListActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(MyMessageListActivity.this,"标记失败!");
            }
        });
    }

    private void deleteMessage(int goodsId, final int position) {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.delUserMessage(SharePreferencesUtils.getBtdToken(context),goodsId,new Callback<Success>() {
            @Override
            public void success(Success addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    ToastUtils.showToastCenter(context,"删除消息成功!");
                    messageInfoList.remove(position);
                    if (messageInfoList.size()>0){
                        messageListAdapter.notifyDataSetChanged();
                    }else {
                        tv_no_collect.setVisibility(View.VISIBLE);
                        rsmLv.setVisibility(View.GONE);
                    }
                }else {
                    ToastUtils.showToastCenter(context,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(context,"删除消息失败!");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    public  int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
