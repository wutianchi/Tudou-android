package com.gunlei.app.ui.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast 消息工具。
 * Created by mac on 14/12/5.
 */
public class ToastUtil {
    /**
     * @param context
     * @param msg
     */
    public static void show(Context context, String msg) {
        // FIX NPE
        if(context == null) {
            return;
        }

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    /**
     * @param context
     * @param stringID
     */
    public static void show(Context context,int stringID){
        // FIX NPE
        if(context == null) {
            return;
        }
        Toast.makeText(context, stringID, Toast.LENGTH_LONG).show();
    }
    /**
     * @param context
     * @param msg
     * @param duration only Toast.LENGTH_LONG or Toast.LENGTH_SHORT
     */
    public static void show(Context context, String msg,int duration) {
        // FIX NPE
        if(context == null) {
            return;
        }
        Toast.makeText(context, msg, duration).show();
    }
    /**
     * @param context
     * @param stringID
     * @param duration only Toast.LENGTH_LONG or Toast.LENGTH_SHORT
     */
    public static void show(Context context,int stringID,int duration){
        // FIX NPE
        if(context == null) {
            return;
        }
        Toast.makeText(context, stringID,duration).show();
    }
    /**
     * @param context
     * @param stringID
     * @param bottom
     * @param duration only Toast.LENGTH_LONG or Toast.LENGTH_SHORT
     */
    public void bottom(Context context,int stringID,int bottom,int duration){
        // FIX NPE
        if(context == null) {
            return;
        }
        Toast toast=Toast.makeText(context, stringID,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, DeviceUtil.dp2px(context, bottom));
        toast.show();
    }
}
