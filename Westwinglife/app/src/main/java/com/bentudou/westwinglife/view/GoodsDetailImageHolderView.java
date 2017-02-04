package com.bentudou.westwinglife.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.GoodsPictureData;
import com.bentudou.westwinglife.json.HeadImg;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子
 */
public class GoodsDetailImageHolderView implements Holder<GoodsPictureData> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, GoodsPictureData data) {
        imageView.setImageResource(R.drawable.defult_bd_img);
        Glide.with(imageView.getContext()).load(Constant.URL_BASE_IMG+data.getGoodsImgPath()).into(imageView);
    }
}
