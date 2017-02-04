package com.gunlei.app.ui.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ShowToast")
public class YToast {
    
    private WindowManager wdm;
    private double time;
    private View mView;
    private WindowManager.LayoutParams params;
    private Timer timer;
    
    private YToast(Context context, String text, double time){
        wdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        timer = new Timer();
        
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mView = toast.getView();
        
        params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;  
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;  
        params.format = PixelFormat.TRANSLUCENT;  
		params.windowAnimations = Animation.INFINITE;  
        params.type = WindowManager.LayoutParams.TYPE_TOAST;  
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON  
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        params.y = 200;
        
        this.time = time;
    }
    
    public static YToast makeText(Context context, String text, double time){
        YToast yToast = new YToast(context, text, time);
        return yToast;
    }
    
    public void show(){
        wdm.addView(mView, params);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                wdm.removeView(mView);
                timer.cancel();
            }
        }, (long)(time * 1000));
    }
}