package com.bentudou.westwinglife.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lzz on 2015/8/4.
 */
public class DensityUtils implements Serializable{

    private static final long serialVersionUID = -7718011921388699359L;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率
     */
    public static float Density(Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 获取手机的分辨率宽
     */
    public static int pxWith(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    /**
     * 获取手机的分辨率高
     */
    public static int pxHight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
    /**
     * 打电话
     * @param context
     * @param no 电话号码
     */
    public static void caller(Context context, String no){

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+no));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static boolean isNotNull(String str){
        return str != null && !"".equals(str) && !"null".equals(str) ? true : false;
    }

    public static boolean isNotNull(Integer integer){
        return integer != null ? true : false;
    }

    public static Intent getIntent(Context paramContext)
    {
        StringBuilder localStringBuilder = new StringBuilder().append("market://details?id=");
        String str = paramContext.getPackageName();
        localStringBuilder.append(str);
        Uri localUri = Uri.parse(localStringBuilder.toString());
        return new Intent("android.intent.action.VIEW", localUri);
    }

    //直接跳转不判断是否存在市场应用
    public static void start(Context paramContext, String paramString)
    {
        Uri localUri = Uri.parse(paramString);
        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        paramContext.startActivity(localIntent);
    }

    public static boolean judge(Context paramContext, Intent paramIntent)
    {
        List<ResolveInfo> localList = paramContext.getPackageManager().queryIntentActivities(paramIntent, PackageManager.GET_INTENT_FILTERS);
        if ((localList != null) && (localList.size() > 0)){
            return false;
        }else{
            return true;
        }
    }

}
