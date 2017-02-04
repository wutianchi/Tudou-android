package com.bentudou.westwinglife.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.adapter.TouchImageAdapter;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.LiveGoodsImg;
import com.bentudou.westwinglife.view.ExtendedViewPager;

import java.util.List;

/**
 * Created by lzz on 2016/8/18.
 * 直播图片展示
 */
public class GoodsLiveImageActivity extends Activity {
    public static final int IMG_COUNT = 22;
    private List<LiveGoodsImg> liveGoodsImgList;
    private TouchImageAdapter touchImageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_live_image);
        liveGoodsImgList = (List<LiveGoodsImg>) getIntent().getSerializableExtra("goods_live_img");
        ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
        touchImageAdapter = new TouchImageAdapter(liveGoodsImgList,GoodsLiveImageActivity.this);
        mViewPager.setAdapter(touchImageAdapter);
        mViewPager.setCurrentItem(Constant.live_img_num);
    }
}
