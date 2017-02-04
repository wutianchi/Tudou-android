package com.bentudou.westwinglife.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.util.Map;

/**
 * Created by lzz on 2016/6/26.
 * 邀请码
 */
public class MyInviteCodeActivity extends BaseTitleActivity implements View.OnClickListener {
    UMImage image = new UMImage(MyInviteCodeActivity.this,R.drawable.share_icon);
    String url;
    private TextView tv_invite_code,tv_share_invite_code;
    private String invite_code;
    private String shareTitle="Hi，朋友，我在用笨土豆购买全球的好东西，邀请你一起来嗨";
    private String shareContent="这是一款海购神器，马上注册，一起来嗨！注册时填写我的邀请码，有更多优惠哟~";
    private UMShareAPI mShareAPI = null;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_my_invite_code);
        mShareAPI = UMShareAPI.get(this);
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
    }

    @Override
    protected void initView() {
        super.setTitleText("我的邀请码");
        invite_code= getIntent().getStringExtra("invite_code");
        url="http://m.bentudou.com/account/retailregister.jsp?invitationCode="+invite_code;
        tv_invite_code = (TextView) findViewById(R.id.tv_invite_code);
        tv_share_invite_code = (TextView) findViewById(R.id.tv_share_invite_code);
        tv_invite_code.setText(invite_code);
        tv_share_invite_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_share_invite_code:
                DialogUtils.showShareToast(MyInviteCodeActivity.this,shareContent,shareTitle,url,image);
                break;

        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "QQ分享成功啦");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "微信分享成功啦");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "朋友圈分享成功啦");
            }else {
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "微博分享成功啦");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "QQ分享失败了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "微信分享失败了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "朋友圈分享失败了");
            }else {
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "微博分享失败了");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "QQ分享取消了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "微信分享取消了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "朋友圈分享取消了");
            }else {
                ToastUtils.showToastCenter(MyInviteCodeActivity.this, "微博分享取消了");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        Log.d("result","onActivityResult");
    }
}
