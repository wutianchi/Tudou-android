package com.bentudou.westwinglife.activity;

import com.bentudou.westwinglife.BaseTitleActivity;
import com.bentudou.westwinglife.R;

/**
 * Created by lzz on 2016/8/4.
 */
public class NoGoodsActivity extends BaseTitleActivity {
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_no_goods);
    }

    @Override
    protected void initView() {
        super.setTitleText("");
    }
}
