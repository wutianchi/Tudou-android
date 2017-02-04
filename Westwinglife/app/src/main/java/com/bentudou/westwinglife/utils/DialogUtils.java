package com.bentudou.westwinglife.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bentudou.westwinglife.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;


/**
 * Created by 天池 on 2015/8/18.
 */
public class DialogUtils {
    //仓库超两千显示对话框
    public static void showDialogs(LayoutInflater inflater, final Context context,String storeName) {
        View layout = inflater.inflate(R.layout.dialog_show,
                null);
        TextView tv_store = (TextView) layout.findViewById(R.id.tv_store);
        tv_store.setText("["+storeName+"]");
        TextView noSaveInfo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        noSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    //text显示对话框
    public static void showTextDialogs(LayoutInflater inflater, final Context context,String storeName) {
        View layout = inflater.inflate(R.layout.dialog_text_show,
                null);
        TextView tv_store = (TextView) layout.findViewById(R.id.tv_content);
        tv_store.setText(storeName);
        TextView noSaveInfo = (TextView) layout.findViewById(R.id.sure_go);
        final Dialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setContentView(layout);
        noSaveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    //text显示对话框
    public static void showToast(LayoutInflater inflater, final Context context,String storeName,String percentage) {
        View view = inflater.inflate(R.layout.book_reading_seekbar_toast, null);
        TextView chapterNameTV = (TextView) view.findViewById(R.id.chapterName);
        TextView percentageTV = (TextView) view.findViewById(R.id.percentage);
        chapterNameTV.setText(storeName);
        percentageTV.setText(percentage);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, PixelFormat.formatDipToPx(context, 70));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
 //分享展示框
    public static void showShareToast(final Activity activity, final String shareContent, final String shareTitle, final String url, final UMImage image) {
        View layout = activity.getLayoutInflater().inflate(R.layout.dialog_share_show,
                null);
        LinearLayout llt_share_dialog_qq = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_qq);
        LinearLayout llt_share_dialog_weixin = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_weixin);
        LinearLayout llt_share_dialog_pengyouquan = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_pengyouquan);
        LinearLayout llt_share_dialog_weibo = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_weibo);
        LinearLayout llt_share_dialog_cancel = (LinearLayout) layout.findViewById(R.id.llt_share_dialog_cancel);
        final Dialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();
        WindowManager.LayoutParams lp=dialog.getWindow().getAttributes();
        lp.alpha=1.0f;
        lp.dimAmount=0.8f;
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setContentView(layout);
        //用户分享回调
        final UMShareListener umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Log.d("plat","platform"+platform);
                if (platform.name().equals("QQ")){
                    ToastUtils.showToastCenter(activity, "QQ分享成功啦");
                }else if (platform.name().equals("WEIXIN")){
                    ToastUtils.showToastCenter(activity, "微信分享成功啦");
                }else if(platform.name().equals("WEIXIN_CIRCLE")){
                    ToastUtils.showToastCenter(activity, "朋友圈分享成功啦");
                }else {
                    ToastUtils.showToastCenter(activity, "微博分享成功啦");
                }

            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                if (platform.name().equals("QQ")){
                    ToastUtils.showToastCenter(activity, "QQ分享失败了");
                }else if (platform.name().equals("WEIXIN")){
                    ToastUtils.showToastCenter(activity, "微信分享失败了");
                }else if(platform.name().equals("WEIXIN_CIRCLE")){
                    ToastUtils.showToastCenter(activity, "朋友圈分享失败了");
                }else {
                    ToastUtils.showToastCenter(activity, "微博分享失败了");
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                if (platform.name().equals("QQ")){
                    ToastUtils.showToastCenter(activity, "QQ分享取消了");
                }else if (platform.name().equals("WEIXIN")){
                    ToastUtils.showToastCenter(activity, "微信分享取消了");
                }else if(platform.name().equals("WEIXIN_CIRCLE")){
                    ToastUtils.showToastCenter(activity, "朋友圈分享取消了");
                }else {
                    ToastUtils.showToastCenter(activity, "微博分享取消了");
                }
            }
        };
        llt_share_dialog_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareAction(activity).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener)
                        .withText(shareContent)
                        .withTitle(shareTitle)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                dialog.dismiss();
            }
        });
        llt_share_dialog_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withText(shareContent)
                        .withTitle(shareTitle)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                dialog.dismiss();
            }
        });
        llt_share_dialog_pengyouquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(activity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withTitle(shareTitle)
                        .withText(shareContent)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                dialog.dismiss();
            }
        });
        llt_share_dialog_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(activity).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withText(shareContent)
                        .withTitle(shareTitle)
                        .withMedia(image)
                        .withTargetUrl(url)
                        .share();
                dialog.dismiss();
            }
        });
        llt_share_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



}
