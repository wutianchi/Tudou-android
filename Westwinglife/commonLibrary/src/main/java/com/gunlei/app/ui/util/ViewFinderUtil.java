package com.gunlei.app.ui.util;

import android.app.Activity;
import android.view.View;

/**
 * 根据ID查找出对应的UI组件类, 自动完成类型转换.
 * Created by liuchangjiong on 15/2/9.
 */
public class ViewFinderUtil {
    public static <T extends View> T findViewById(View view, int resourceId) {
        try {
            return (T) view.findViewById(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T extends View> T findViewById(Activity view, int resourceId) {
        try {
            return (T) view.findViewById(resourceId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}