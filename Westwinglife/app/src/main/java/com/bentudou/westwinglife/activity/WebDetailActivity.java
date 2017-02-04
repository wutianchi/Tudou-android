package com.bentudou.westwinglife.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.utils.DensityUtils;
import com.bentudou.westwinglife.utils.DialogUtils;
import com.bentudou.westwinglife.utils.SharePreferencesUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.media.UMImage;

/**
 * Created by lzz on 2016/7/8.
 */
public class WebDetailActivity extends BaseTitleActivity implements View.OnClickListener {
    private WebView wv_detail;
    private UMImage share_image;
    private String share_tittle;
    private String share_content;
    private String shareUrl;
    private String url;
    private String tittle;
    private UMShareAPI mShareAPI = null;
    private ProgressBar mProgressBar;
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_web_detail);
        Config.REDIRECT_URL="http://sns.whalecloud.com/sina2/callback";
        mShareAPI = UMShareAPI.get(this);
    }

    @Override
    protected void initView() {
        title_save.setBackgroundResource(R.drawable.shangpinxiangqing_botton_fenixang);
        title_next.setOnClickListener(this);
        shareUrl = getIntent().getStringExtra("web_url");
        url =shareUrl +"?from=Android"+"&to_html5="+ SharePreferencesUtils.getBtdToken(this);
//        url = "http://192.168.20.30:8080/m.westwinglife.cn/promotions/blackday.jsp?from=Android"+"&to_html5="+ SharePreferencesUtils.getBtdToken(this);
        Log.d("url","-----"+url);
        tittle = getIntent().getStringExtra("link_name");
        super.setTitleText(tittle);
        wv_detail = (WebView) findViewById(R.id.wv_detail);
        mProgressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(this, 2)));
        wv_detail.addView(mProgressBar);
        WebSettings webSettings = wv_detail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小 //设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        wv_detail.addJavascriptInterface(new MyObject(), "myObj");
        wv_detail.loadUrl(url);
        wv_detail.setWebChromeClient(new MyClient());
        wv_detail.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title_next:
                DialogUtils.showShareToast(WebDetailActivity.this,share_content,share_tittle,shareUrl,share_image);
                break;
        }
    }

    final class MyObject{
        @JavascriptInterface
        public void toGoodsDetail(String goodsId){
            startActivity(new Intent(WebDetailActivity.this, GoodsDetailActivity.class)
                    .putExtra("goodsId",goodsId ));
        }

        @JavascriptInterface
        public void toGoodsClass(String categoryId,String name){
            startActivity(new Intent(WebDetailActivity.this, GoodsClassActivity.class)
                    .putExtra("categoryId",categoryId ).putExtra("goods_class_name",name));
        }
        @JavascriptInterface
        public void toShareActivity(String imgUrl,String shareTittle,String shareContent){
            Log.d("share_content", "------toShareActivity: "+imgUrl+shareTittle+shareContent);
            share_image = new UMImage(WebDetailActivity.this, imgUrl);
            share_tittle=shareTittle;
            share_content=shareContent;

        }

    }


    class MyClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100){

                mProgressBar.setVisibility(View.GONE);

            }else{

                if (mProgressBar.getVisibility() == View.GONE)
                    mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);

            }

            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            WebDetailActivity.super.setTitleText(title);
            tittle = title;
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("result","onActivityResult");
    }

}
