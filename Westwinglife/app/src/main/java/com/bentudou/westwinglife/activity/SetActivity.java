package com.bentudou.westwinglife.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.utils.DataCleanManager;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by lzz on 2016/6/19.
 */
public class SetActivity extends BaseTitleActivity implements View.OnClickListener {
    private RelativeLayout rl_my_key,rl_my_want,rl_my_clean,rl_about_tudou;
    private TextView tv_clean_size;
    private Button btn_out_tudou;
    private String size;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_set);
    }

    @Override
    protected void initView() {
        super.setTitleText("设置");
        rl_my_key = (RelativeLayout) findViewById(R.id.rl_my_key);
        rl_my_want = (RelativeLayout) findViewById(R.id.rl_my_want);
        rl_my_clean = (RelativeLayout) findViewById(R.id.rl_my_clean);
        rl_about_tudou = (RelativeLayout) findViewById(R.id.rl_about_tudou);
        tv_clean_size = (TextView) findViewById(R.id.tv_clean_size);
        btn_out_tudou = (Button) findViewById(R.id.btn_out_tudou);
        rl_my_key.setOnClickListener(this);
        rl_my_want.setOnClickListener(this);
        rl_my_clean.setOnClickListener(this);
        rl_about_tudou.setOnClickListener(this);
        btn_out_tudou.setOnClickListener(this);
        try {
            size = DataCleanManager.getTotalCacheSize(this);
            tv_clean_size.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_my_key:
                startActivity(new Intent(SetActivity.this,ChangePasswordActivity.class));
                break;//更改密码
            case R.id.rl_my_want:
                startActivity(new Intent(SetActivity.this,GiveUsMessageActivity.class));
                break;//去留言
            case R.id.rl_my_clean:
                showClearGoods();
                break;//清除缓存
            case R.id.rl_about_tudou:
                startActivity(new Intent(SetActivity.this,AboutBentudouActivity.class));
                break;//去关于笨土豆
            case R.id.btn_out_tudou:
                showExitDialogs();
                break;
        }
    }
    private void showClearGoods() {
        View layout =getLayoutInflater().inflate(R.layout.dialog_cart_clear,
                null);
        TextView tv_connect = (TextView) layout.findViewById(R.id.tv_connect);
        tv_connect.setText("确定清除本地缓存吗?");
        TextView cancelGo = (TextView) layout.findViewById(R.id.cancel_go);
        TextView sureNoGo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        /**监听对话框里面的button点击事件*/
        sureNoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SetActivity.this);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.showToastCenter(SetActivity.this,"共清除"+size+"缓存");
                tv_clean_size.setText("0K");
                dialog.dismiss();
            }
        });
        cancelGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //删除历史记录提示
    public void showExitDialogs() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_exit,
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
                dialog.dismiss();
            }
        });
        noSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferencesUtils.saveBtdToken(SetActivity.this,"","","");
                Constant.push_value=1;
                JPushInterface.setAlias(SetActivity.this,"", new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.d("JPush", "setAlias: "+i);
                    }
                });
//                startActivity(new Intent(SetActivity.this,MainActivity.class));
                finish();
                ToastUtils.showToastCenter(SetActivity.this,"账号已退出");
                dialog.dismiss();
            }
        });
    }
}
