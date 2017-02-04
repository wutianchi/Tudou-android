package com.bentudou.westwinglife.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bentudou.westwinglife.R;
import com.bentudou.westwinglife.activity.GoodsLiveImageActivity;
import com.bentudou.westwinglife.activity.MyNewCollectionListActivity;
import com.bentudou.westwinglife.config.Constant;
import com.bentudou.westwinglife.json.LiveGoodsImg;
import com.bentudou.westwinglife.view.TouchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by lzz on 2016/8/18.
 */
public class TouchImageAdapter extends PagerAdapter {
    private List<LiveGoodsImg> liveGoodsImgList;
    private Activity activity;
    public TouchImageAdapter(List<LiveGoodsImg> liveGoodsImgList, Activity activity){
        this.liveGoodsImgList=liveGoodsImgList;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return liveGoodsImgList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
       View view = LayoutInflater.from(container.getContext()).inflate(R.layout.viewpager_img,null);
        TouchImageView img = (TouchImageView) view.findViewById(R.id.view_pager_img);
        TextView textView= (TextView) view.findViewById(R.id.tv_img_number);
        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+liveGoodsImgList.get(position).getGoodsImgPath(),img);
        textView.setText(position+1+"/"+liveGoodsImgList.size());
        container.addView(view);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return view;
    }
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        TouchImageView img = new TouchImageView(container.getContext());
//        ImageLoader.getInstance().displayImage(Constant.URL_BASE_IMG+liveGoodsImgList.get(position).getGoodsImgPath(),img);
//        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                activity.finish();
//            }
//        });
//        Log.d("position","-----"+position);
//        Message msg = new Message();
//        msg.what = GoodsLiveImageActivity.IMG_COUNT;
//        msg.arg1 = position+1;
//        myHander.sendMessage(msg);
//        return img;
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
