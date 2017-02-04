package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.BtnToken;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.view.ProgressHUD;
import com.umeng.analytics.MobclickAgent;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/9.
 * 登录
 */
public class LoginActivity extends BaseTitleActivity implements View.OnClickListener,TextWatcher, CompoundButton.OnCheckedChangeListener {
    private EditText etLoginPhoneNumber,etLoginPassword;
    private Button btnLogin;
    private CheckBox checkboxIspassword;
    private TextView tvForgetPassword;
    private int index=1;
    ProgressHUD progressHUD = null;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        super.setTitleText("登录");
        title_next.setText("注册");
//        Drawable drawable= getResources().getDrawable(R.drawable.nav_button_shouye_selected);
        // 这一步必须要做,否则不会显示.
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        title_back.setCompoundDrawables(drawable,null,null,null);
        index = getIntent().getIntExtra("index",1);
        etLoginPhoneNumber = (EditText) findViewById(R.id.et_login_phone_number);
        etLoginPassword = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        checkboxIspassword = (CheckBox) findViewById(R.id.checkbox_ispassword);
        tvForgetPassword = (TextView) findViewById(R.id.tv_forget_password);
        etLoginPhoneNumber.setOnClickListener(this);
        etLoginPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        title_back.setOnClickListener(this);
        title_next.setOnClickListener(this);
        checkboxIspassword.setOnCheckedChangeListener(this);
        tvForgetPassword.setOnClickListener(this);
        etLoginPhoneNumber.addTextChangedListener(this);
        etLoginPassword.addTextChangedListener(this);
        btnLogin.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_login_phone_number:
                break;
            case R.id.et_login_password:
                break;
            case R.id.btn_login:
                if (etLoginPhoneNumber.length()!=11){
                    ToastUtils.showToastCenter(this, "请输入正确的手机号!");
                }else if (etLoginPassword.length()==0){
                    ToastUtils.showToastCenter(this,"密码不能为空!");
                }else {
                    login();
                }
                break;
            case R.id.tv_forget_password:
                if (etLoginPhoneNumber.length()!=11){
                    ToastUtils.showToastCenter(this, "请输入正确的手机号!");
                }else {
                    startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class).putExtra("forget_phone",etLoginPhoneNumber.getText().toString()));
                }
                break;
            case R.id.title_next:
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                finish();
                break;
            case R.id.title_back:
                Constant.push_value=1;
                finish();
                break;
        }
    }

    private void login() {
        progressHUD = ProgressHUD.show(this, "读取中", true, null);
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.userLogin(etLoginPhoneNumber.getText().toString(), etLoginPassword.getText().toString(),new CallbackSupport<BtnToken>(progressHUD,this) {
            @Override
            public void success(BtnToken btnToken, Response response) {
                progressHUD.dismiss();
                if (btnToken.getStatus().equals("1")){
                    MobclickAgent.onProfileSignIn(etLoginPhoneNumber.getText().toString());
                    JPushInterface.setAlias(LoginActivity.this,etLoginPhoneNumber.getText().toString() , new TagAliasCallback() {
                        @Override
                        public void gotResult(int i, String s, Set<String> set) {
                            Log.d("JPush", "setAlias: "+i);
                        }
                    });
                    if (btnToken.getData().getBtdToken().isEmpty()){
                        ToastUtils.showToastCenter(LoginActivity.this, "登录成功!,但是没返BtdToken,后台处理下~~");
                    }else {
                        Constant.push_value = index;
                        SharePreferencesUtils.saveBtdToken(LoginActivity.this,btnToken.getData().getBtdToken(),etLoginPhoneNumber.getText().toString(),btnToken.getData().getUserInviteCode());
                        finish();
                    }
                }else {
                    ToastUtils.showToastCenter(LoginActivity.this, btnToken.getErrorMessage());
                }


            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            etLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        } else {
            etLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {



              if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            Constant.push_value=1;
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
