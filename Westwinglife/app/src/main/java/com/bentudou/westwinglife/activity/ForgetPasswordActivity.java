package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.CallbackSupport;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Session;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.view.TimeButton;
import com.gunlei.app.ui.base.BaseTitleActivity;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/3/9.
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseTitleActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private EditText etPasswordForget,etIdentifyingCodeForget;
    private TimeButton registerNumberButtonForget;
    private Button btnCommitForget;
    private CheckBox checkbox_forget_password;
    private String etPhoneNumberForget;
    private Object identifyingCode;
    private String sessionId;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_forget_password);
    }

    @Override
    protected void initView() {
        super.setTitleText("重置密码");
        etPhoneNumberForget = getIntent().getStringExtra("forget_phone");
        etPasswordForget = (EditText) findViewById(R.id.et_password_forget);
        etIdentifyingCodeForget = (EditText) findViewById(R.id.et_identifying_code_forget);
        registerNumberButtonForget = (TimeButton) findViewById(R.id.register_number_button_forget);
        btnCommitForget = (Button) findViewById(R.id.btn_commit_forget);
        checkbox_forget_password = (CheckBox) findViewById(R.id.checkbox_forget_password);
        etPasswordForget.setOnClickListener(this);
        etIdentifyingCodeForget.setOnClickListener(this);
        registerNumberButtonForget.setOnClickListener(this);
        btnCommitForget.setOnClickListener(this);
        checkbox_forget_password.setOnCheckedChangeListener(this);
        registerNumberButtonForget.performClick();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_number_button_forget:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getIdentifyingCode();
                    }
                }).start();
                break;
            case R.id.btn_commit_forget:
                if (etPasswordForget.length()<8){
                    ToastUtils.showToastCenter(this,"密码请大于8位!");
                }else if (etPasswordForget.length()>20){
                    ToastUtils.showToastCenter(this,"密码请小于20位!");
                }else if (etIdentifyingCodeForget.length()==0){
                    ToastUtils.showToastCenter(this,"验证码码不能为空!");
                }else {
                    commitForget();
                }
                break;
        }
    }

    //获取验证码
    public void getIdentifyingCode() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.getIdentifyingcode(etPhoneNumberForget, "forgetPwd", new Callback<Session>() {
                    @Override
                    public void success(Session session, Response response) {
                        if (session.getStatus().equals("1")){
                            registerNumberButtonForget.setTextBefore("").setTextAfter("").setLenght(60 * 1000);
                            sessionId = session.getData().getSessionId();
                        }else {
                            ToastUtils.showToastCenter(ForgetPasswordActivity.this,session.getErrorMessage());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
    }

    //提交重置
    private void commitForget() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.forgetPassword(etPasswordForget.getText().toString(),etPhoneNumberForget,
                etIdentifyingCodeForget.getText().toString(),
                sessionId, new CallbackSupport<Success>(ForgetPasswordActivity.this) {
                    @Override
                    public void success(Success success, Response response) {
                        if (success.getStatus().equals("1")){
                            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                        }else {
                            ToastUtils.showToastCenter(ForgetPasswordActivity.this,success.getErrorMessage());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            etPasswordForget.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        } else {
            etPasswordForget.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
