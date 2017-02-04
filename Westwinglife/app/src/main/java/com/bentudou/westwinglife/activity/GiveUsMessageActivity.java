package com.bentudou.westwinglife.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Leaveword;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.CustomDialog;
import com.gunlei.app.ui.VerifitionUtil;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.view.ProgressHUD;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/26.
 * 把您想买的东西告诉我们
 */
public class GiveUsMessageActivity extends BaseTitleActivity {
//    private WebView webView;
    private ProgressHUD progressHUD;
    private LinearLayout ll_Whether_there_is_network,ll_leave_word;
    private Button b_reload,b_liuyan_submit;
    EditText et_leave_word_phone,et_leave_word_URLs,et_leave_word_seek_quantity,et_leave_word_say;
    public static GiveUsMessageActivity instance = null;
    private ScrollView liuyan_sv;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_give_us_message);
    }

    @Override
    protected void initView() {
        super.setTitleText("把您想买的东西告诉我们");
        instance=this;
        ll_Whether_there_is_network = (LinearLayout) findViewById(R.id.ll_Whether_there_is_network);
        liuyan_sv= (ScrollView) findViewById(R.id.liuyan_sv);
        ll_leave_word = (LinearLayout) findViewById(R.id.ll_leave_word);
        et_leave_word_phone= (EditText) findViewById(R.id.et_leave_word_phone);
        et_leave_word_URLs= (EditText) findViewById(R.id.et_leave_word_URLs);
        et_leave_word_seek_quantity= (EditText) findViewById(R.id.et_leave_word_seek_quantity);
        et_leave_word_say= (EditText) findViewById(R.id.et_leave_word_say);
        b_reload = (Button) findViewById(R.id.b_reload);
        b_liuyan_submit = (Button) findViewById(R.id.b_liuyan_submit);
        et_leave_word_phone.setText(SharePreferencesUtils.getMobile(this));
        if(VerifitionUtil.isNetworkAvailable(this)){
            liuyan_sv.setVisibility(View.VISIBLE);
            ll_Whether_there_is_network.setVisibility(View.GONE);
            b_liuyan_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String leave_word_phone=et_leave_word_phone.getText().toString();
                    String leave_word_URLs=et_leave_word_URLs.getText().toString();
                    String leave_word_seek=et_leave_word_seek_quantity.getText().toString();
                    String leave_word_say=et_leave_word_say.getText().toString();
                    if(!leave_word_phone.equals("")&&!leave_word_URLs.equals("")&&!leave_word_seek.equals("")&&!leave_word_say.equals("")){
                        if(leave_word_phone.length()!=11){
                            ToastUtils.showToastCenter(GiveUsMessageActivity.this,"请输入正确的手机号码");
                        }else if(!com.bentudou.westwinglife.utils.VerifitionUtil.mobile(leave_word_seek)){
                            ToastUtils.showToastCenter(GiveUsMessageActivity.this, "请输入正确的需求数量");
                        }else if(leave_word_seek.length()>9){
                            ToastUtils.showToastCenter(GiveUsMessageActivity.this,"数量太大~，请输入少于10位的数量");
                        }
//                        else if(!(com.bentudou.westwinglife.utils.VerifitionUtil.noexpression(leave_word_name)&& com.bentudou.westwinglife.utils.VerifitionUtil.noexpression(leave_word_URLs)&& com.bentudou.westwinglife.utils.VerifitionUtil.noexpression(leave_word_say))){
//                            Toast.makeText(GiveUsMessageActivity.this,"含有非法字符，请输入正确的信息",Toast.LENGTH_SHORT).show();
//                        }
                        else {
                            final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
                            potatoService.leaveword("lixiangren",leave_word_phone, leave_word_URLs, Integer.parseInt(leave_word_seek), leave_word_say, new Callback<Leaveword>() {
                                @Override
                                public void success(Leaveword leaveword, Response response) {
                                    if (leaveword.getStatus().equals("1")) {
                                        CustomDialog.Builder builder = new CustomDialog.Builder(GiveUsMessageActivity.this);
                                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.create().show();

                                    }
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    ToastUtils.showToastCenter(GiveUsMessageActivity.this,"留言失败！");
                                }
                            });
                        }
                    }else{
                        ToastUtils.showToastCenter(GiveUsMessageActivity.this, "请完善您的留言~");
                }
                }
            });
        }else{
          liuyan_sv.setVisibility(View.GONE);
          ll_Whether_there_is_network.setVisibility(View.VISIBLE);
          b_reload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  initView();
                }
            });
        }
    }
}
