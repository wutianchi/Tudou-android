package com.bentudou.westwinglife.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * Created by yaoguang on 2016/7/5.
 */
public class ExitActivity extends Activity {
    UMShareAPI mShareAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ceshi);

        mShareAPI = UMShareAPI.get(this);
        mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);

        Button b= (Button) findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SHARE_MEDIA platform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(ExitActivity.this, platform, umAuthListener);
            }
        });

    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText( getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
//            mShareAPI.getPlatformInfo(ExitActivity.this, platform, umAuthListener);
            getUserInfo(platform);
            Log.i("asd","......................."+data.toString());
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };


    private void getUserInfo(SHARE_MEDIA platform) {
        mShareAPI.getPlatformInfo(ExitActivity.this, platform,
                new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Log.i("wsx","...................................."+map.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}