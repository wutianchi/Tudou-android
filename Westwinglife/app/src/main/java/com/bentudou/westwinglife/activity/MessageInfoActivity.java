package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.MyMessageDetail;
import com.bentudou.westwinglife.json.UserMessageDetail;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/8/3.
 */
public class MessageInfoActivity extends BaseTitleActivity implements View.OnClickListener {
    private LinearLayout llt_message_content;
    private TextView tv_title,tv_message_content,tv_next;
    private String id,sysmsgId;
    private MyMessageDetail myMessageDetail;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_message_info);
    }

    @Override
    protected void initView() {
        super.setTitleText("消息内容");
        id= getIntent().getStringExtra("id");
        sysmsgId= getIntent().getStringExtra("sysmsgId");
        llt_message_content = (LinearLayout) findViewById(R.id.llt_message_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_message_content = (TextView) findViewById(R.id.tv_message_content);
        tv_next = (TextView) findViewById(R.id.tv_next);
        llt_message_content.setOnClickListener(this);
        if (sysmsgId.equals("")){
            initData();
        }else {
            initPushData();
        }

    }

    private void initPushData() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.getUserMessageInfo(SharePreferencesUtils.getBtdToken(this),Integer.valueOf(id),Integer.valueOf(sysmsgId),new Callback<UserMessageDetail>() {
            @Override
            public void success(UserMessageDetail addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    llt_message_content.setVisibility(View.VISIBLE);
                    myMessageDetail = addSub.getData();
                    if (myMessageDetail.getType()==5||myMessageDetail.getType()==6){
                        tv_next.setVisibility(View.INVISIBLE);
                    }else {
                        tv_next.setVisibility(View.VISIBLE);
                    }
                    tv_title.setText(myMessageDetail.getTitle());
                    tv_message_content.setText(myMessageDetail.getContent());
                }else {
                    ToastUtils.showToastCenter(MessageInfoActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(MessageInfoActivity.this,"消息错误!");
            }
        });
    }

    private void initData() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.getUserMessageInfo(SharePreferencesUtils.getBtdToken(this),Integer.valueOf(id),new Callback<UserMessageDetail>() {
            @Override
            public void success(UserMessageDetail addSub, Response response) {
                if (addSub.getStatus().equals("1")){
                    llt_message_content.setVisibility(View.VISIBLE);
                    myMessageDetail = addSub.getData();
                    if (myMessageDetail.getType()==5||myMessageDetail.getType()==6){
                        tv_next.setVisibility(View.INVISIBLE);
                    }else {
                        tv_next.setVisibility(View.VISIBLE);
                    }
                    tv_title.setText(myMessageDetail.getTitle());
                    tv_message_content.setText(myMessageDetail.getContent());
                }else {
                    ToastUtils.showToastCenter(MessageInfoActivity.this,addSub.getErrorMessage());
                }

            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(MessageInfoActivity.this,"消息错误!");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llt_message_content:
                switch (myMessageDetail.getType()){
                    case 1:
                        startActivity(new Intent(MessageInfoActivity.this,GoodsDetailActivity.class).putExtra("goodsId",myMessageDetail.getOther()));
                        break;
                    case 2:
                        startActivity(new Intent(MessageInfoActivity.this,GoodsClassActivity.class)
                                .putExtra("categoryId",myMessageDetail.getOther()).putExtra("goods_class_name","活动分类"));
                        break;
                    case 3:
                        startActivity(new Intent(MessageInfoActivity.this, WebDetailActivity.class)
                                .putExtra("web_url",myMessageDetail.getOther()).putExtra("link_name","活动详情"));
                        break;
                    case 4:
                        startActivity(new Intent(MessageInfoActivity.this,MyCouponActivity.class));
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                }
                break;
        }
    }
}
