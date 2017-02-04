package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.BtnToken;
import com.bentudou.westwinglife.json.Session;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.TimeButton;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.gunlei.app.ui.util.NetUtil;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/9.
 */
public class RegistActivity extends BaseTitleActivity implements View.OnClickListener,TextWatcher, CompoundButton.OnCheckedChangeListener {
    private EditText etPhoneNumber,etPassword,etRepassword,etIdentifyingCode;
    private TimeButton registerNumberButton;
    private Button btnRegister;
    private CheckBox checkboxIspasswordRegister;
    private Object identifyingCode;
    private String sessionId;
    private TextView tv_user_law;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initView() {
        super.setTitleText("注册");
        title_next.setText("登录");
        Drawable drawable= getResources().getDrawable(R.drawable.nav_button_shouye_selected);
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        title_back.setCompoundDrawables(drawable,null,null,null);
        etPhoneNumber = (EditText) findViewById(R.id.et_phone_number);
        etPassword = (EditText) findViewById(R.id.et_password);
        etRepassword = (EditText) findViewById(R.id.et_repassword);
        etIdentifyingCode = (EditText) findViewById(R.id.et_identifying_code);
        tv_user_law = (TextView) findViewById(R.id.tv_user_law);
        registerNumberButton = (TimeButton) findViewById(R.id.register_number_button);
        btnRegister = (Button) findViewById(R.id.btn_register);
        checkboxIspasswordRegister = (CheckBox) findViewById(R.id.checkbox_ispassword_register);
        registerNumberButton.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        checkboxIspasswordRegister.setOnCheckedChangeListener(this);
        title_back.setOnClickListener(this);
        title_next.setOnClickListener(this);
        tv_user_law.setOnClickListener(this);
        etPhoneNumber.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
        etRepassword.addTextChangedListener(this);
        etIdentifyingCode.addTextChangedListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_number_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getIdentifyingCode();
                    }
                }).start();
                break;
            case R.id.btn_register:
                if (etPhoneNumber.length()!=11){
                    ToastUtils.showToastCenter(this,"请输入正确的手机号!");
                }else if (etPassword.length()<8){
                    ToastUtils.showToastCenter(this,"密码请大于8位!");
                }else if (etPassword.length()>20){
                    ToastUtils.showToastCenter(this,"密码请小于20位!");
                }else if (etIdentifyingCode.length()==0){
                    ToastUtils.showToastCenter(this,"验证码码不能为空!");
                }else {
                    commitRegist();
                }
                break;
            case R.id.title_back:
                Constant.push_value=1;
                startActivity(new Intent(RegistActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.title_next:
                startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                finish();
                break;
            case R.id.tv_user_law:
                startActivity(new Intent(RegistActivity.this,LawActivity.class));
                break;
        }
    }

    //获取验证码
    public void getIdentifyingCode() {
        NetUtil.checkNet(this);
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.getIdentifyingcode(etPhoneNumber.getText().toString(), "regist", new Callback<Session>() {
                    @Override
                    public void success(Session session, Response response) {
                        if (session.getStatus().equals("1")){
                            registerNumberButton.setTextBefore("").setTextAfter("").setLenght(60 * 1000);
                            sessionId = session.getData().getSessionId();
                        }else {
                            ToastUtils.showToastCenter(RegistActivity.this,session.getErrorMessage());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ToastUtils.showToastCenter(RegistActivity.this,"获取验证码失败!");
                    }
                });
    }

    //提交注册
    private void commitRegist() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.commitRegist(etPhoneNumber.getText().toString(), etPassword.getText().toString(),
                etIdentifyingCode.getText().toString(), "7",
                sessionId,etRepassword.getText()+"", new Callback<BtnToken>() {
                    @Override
                    public void success(BtnToken btnToken, Response response) {
                        if (btnToken.getStatus().equals("1")){
                            if (btnToken.getData().getBtdToken().isEmpty()){
                                //------注册成功!
                                startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                            }else {
                                //------注册登录成功!
                                SharePreferencesUtils.saveBtdToken(RegistActivity.this,btnToken.getData().getBtdToken(),etPhoneNumber.getText().toString(),btnToken.getData().getUserInviteCode());
                                startActivity(new Intent(RegistActivity.this, MainActivity.class));
                                finish();
                            }
                        }else {
                            ToastUtils.showToastCenter(RegistActivity.this,btnToken.getErrorMessage());
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        ToastUtils.showToastCenter(RegistActivity.this,"请求异常,请稍后重试!");
                    }
                });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length()==11&&etPhoneNumber.isFocused()){
            registerNumberButton.setEnabled(true);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
