package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.BtnToken;
import com.bentudou.westwinglife.json.Success;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by lzz on 2016/6/25.
 * 修改密码
 */
public class ChangePasswordActivity extends BaseTitleActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private Button btn_change_password;
    private EditText et_old_password,et_new_password;
    private CheckBox checkbox_old_password,checkbox_new_password;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_change_password);
    }

    @Override
    protected void initView() {
        super.setTitleText("修改密码");
        btn_change_password = (Button) findViewById(R.id.btn_change_password);
        et_old_password = (EditText) findViewById(R.id.et_old_password);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        checkbox_old_password = (CheckBox) findViewById(R.id.checkbox_old_password);
        checkbox_new_password = (CheckBox) findViewById(R.id.checkbox_new_password);
        checkbox_old_password.setOnCheckedChangeListener(this);
        checkbox_new_password.setOnCheckedChangeListener(this);
        btn_change_password.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.checkbox_old_password:
                if (isChecked) {
                    et_old_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    et_old_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.checkbox_new_password:
                if (isChecked) {
                    et_new_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                } else {
                    et_new_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_password:
                if (et_old_password.length()==0){
                    ToastUtils.showToastCenter(this, "旧密码不能为空!");
                }else if (et_new_password.length()<8){
                    ToastUtils.showToastCenter(this,"新密码请大于8位!");
                }else if (et_new_password.length()>20){
                    ToastUtils.showToastCenter(this,"新密码请小于20位!");
                }else {
                    change();
                }
                break;
        }
    }

    private void change() {
        final PotatoService potatoService = RTHttpClient.create(PotatoService.class);
        potatoService.changePassword(SharePreferencesUtils.getBtdToken(this),et_new_password.getText().toString(), et_old_password.getText().toString(),new Callback<Success>() {
            @Override
            public void success(Success success, Response response) {
                if (success.getStatus().equals("1")){
                    ToastUtils.showToastCenter(ChangePasswordActivity.this, "重置密码成功!请重新登录!");
                    SharePreferencesUtils.saveBtdToken(ChangePasswordActivity.this,"","","");
                    startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
                    finish();
                }else {
                    ToastUtils.showToastCenter(ChangePasswordActivity.this, success.getErrorMessage());
                }


            }
            @Override
            public void failure(RetrofitError error) {
                ToastUtils.showToastCenter(ChangePasswordActivity.this, "重置密码失败!");
            }
        });
    }
}
