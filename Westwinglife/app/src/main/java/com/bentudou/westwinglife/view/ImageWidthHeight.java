package com.bentudou.westwinglife.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Grace on 2015/8/21.
 */
public class ImageWidthHeight {
    public static int getItemWidth(Context context,int mScreentWidth,int margin) {
        Rect r = getScreenRect(context);
        if (mScreentWidth == 0) {
            mScreentWidth = r.right;
        }
        return (int) (mScreentWidth - changeDIPtoPXdefaultly(context, margin));
    }

    /**
     * 获得设备屏幕矩形区域范围
     *
     * @param context
     * @return
     */
    public static Rect getScreenRect(Context context) {
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        return new Rect(0, 0, w,h);
    }

    /**
     * 把dip换算成像素(使用dm.density不一定是完美结果) 如果要得到完美结果需使用
     *
     * @param DIP
     * @return
     */
    public static float changeDIPtoPXdefaultly(Context context, float DIP) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (float) (DIP * scale + 0.5f);
    }
}
