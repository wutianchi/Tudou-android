package com.bentudou.westwinglife.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.bentudou.westwinglife.utils.VerifitionUtil;
import com.gunlei.app.ui.base.BaseTitleActivity;

/**
 * Created by lzz on 2016/6/26.
 * 关于笨土豆
 */
public class AboutBentudouActivity extends BaseTitleActivity {
    private LinearLayout llt_weixin,llt_phone;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_about_bentudou);
    }

    @Override
    protected void initView() {
        super.setTitleText("关于笨土豆");
        llt_weixin = (LinearLayout) findViewById(R.id.llt_weixin);
        llt_phone = (LinearLayout) findViewById(R.id.llt_phone);
        llt_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText("bentudou888");
                showDialogInfo();
            }
        });
        llt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:4009936911"));
                startActivity(intent);
            }
        });
    }
    //跳转到微信客户端
    private void showDialogInfo() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_go_weixin,
                null);
        TextView cancelGo = (TextView) layout.findViewById(R.id.cancel_go);
        TextView sureNoGo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        /**监听对话框里面的button点击事件*/
        sureNoGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VerifitionUtil.isWeixinAvilible(AboutBentudouActivity.this)){
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    startActivity(intent);
                }else {
                    ToastUtils.showToastCenter(AboutBentudouActivity.this,"未安装微信客户端");
                }

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
}
