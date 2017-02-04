package com.bentudou.westwinglife.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.ToastUtils;
import com.gunlei.app.ui.base.BaseTitleActivity;
import com.umeng.analytics.social.UMSocialService;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.util.Map;

/**
 * Created by lzz on 2016/6/20.
 */
public class ShareBentudouActivity extends BaseTitleActivity implements View.OnClickListener {
    UMImage image = new UMImage(ShareBentudouActivity.this, R.drawable.share_icon);
    String url = "http://a.app.qq.com/o/simple.jsp?pkgname=com.bentudou.westwinglife";
    String shareContent = "这是一款海购神器，马上下载，一起来嗨！";
    String shareTitle = "Hi，朋友，我在用笨土豆购买全球的好东西，邀请你一起来嗨";
//    private LinearLayout llt_share,llt_share_weixin,llt_share_pengyouquan,llt_share_weibo;
//    private TextView tv_share_cancel;
    private Button btn_open_share,btn_get_user_info_weixin,btn_get_user_info_weibo,btn_get_user_info_qq;
    private UMShareAPI mShareAPI = null;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_share_bentudou);
        Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
        btn_open_share = (Button) findViewById(R.id.btn_open_share);
        mShareAPI = UMShareAPI.get(this);
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
        btn_open_share.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        super.setTitleText("分享笨土豆");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_open_share:
                /**默认分享面版**/
                DialogUtils.showShareToast(ShareBentudouActivity.this,shareContent,shareTitle,url,image);
//                llt_share.setVisibility(View.VISIBLE);
                break;
        }
    }
        //用户授权回调
        private UMAuthListener umAuthListener = new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Log.d("onComplete", "onComplete:---- "+platform.name()+data.size());
                if (data!=null){
                    DialogUtils.showTextDialogs(getLayoutInflater(),ShareBentudouActivity.this,platform.name()+data.toString());
                }
            }
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                ToastUtils.showToastCenter( getApplicationContext(), "get fail");
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                ToastUtils.showToastCenter( getApplicationContext(), "get cancel");
            }
        };
    //用户分享回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "QQ分享成功啦");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "微信分享成功啦");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "朋友圈分享成功啦");
            }else {
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "微博分享成功啦");
            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "QQ分享失败了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "微信分享失败了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "朋友圈分享失败了");
            }else {
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "微博分享失败了");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if (platform.name().equals("QQ")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "QQ分享取消了");
            }else if (platform.name().equals("WEIXIN")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "微信分享取消了");
            }else if(platform.name().equals("WEIXIN_CIRCLE")){
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "朋友圈分享取消了");
            }else {
                ToastUtils.showToastCenter(ShareBentudouActivity.this, "微博分享取消了");
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
