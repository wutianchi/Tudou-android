package com.bentudou.westwinglife.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bentudou.westwinglife.BTDApplication;
import com.bentudou.westwinglife.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Grace on 2015/7/30.
 */
public class TimeButton extends Button implements View.OnClickListener{
    private long lenght = 60 * 1000;// 倒计时长度,这里给了默认60秒
    private String textafter = "";
    private String textbefore = "";
    private String textend = "";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    Map<String, Long> map = new HashMap<String, Long>();

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
                        TimeButton.this.setText(time / 1000 + textafter+"s");
                        TimeButton.this.setTextColor(getResources().getColor(R.color.half_black));
//                        TimeButton.this.setBackgroundResource(R.drawable.bg_btn_login);
//                        TimeButton.this.setBackgroundColor(getResources().getColor(R.color.color_selected));
                      time -= 1000;
                      if (time <= 0) {
                          TimeButton.this.setEnabled(true);
                          TimeButton.this.setText("获取验证码");
                          TimeButton.this.setTextColor(getResources().getColor(R.color.black));
//                          TimeButton.this.setBackgroundResource(R.drawable.btn_cart_bg_selector);
//                          TimeButton.this.setBackgroundColor(getResources().getColor(R.color.color_selected));
//                          TimeButton.this.setBackgroundResource(R.drawable.bg_btn_login);
                          clearTimer();
                         }

                  };
          };

    private void initTimer() {
             time = lenght;
             t = new Timer();
             tt = new TimerTask() {
                 @Override
                   public void run() {
                             han.sendEmptyMessage(0x01);
                         }
                 };
           }

            private void clearTimer() {
             if (tt != null) {
                       tt.cancel();
                      tt = null;
                 }
               if (t != null)
                      t.cancel();
              t = null;
          }


    @Override
     public void setOnClickListener(OnClickListener l) {
              if (l instanceof TimeButton) {
                   super.setOnClickListener(l);
                  } else
                     this.mOnclickListener = l;
          }

               @Override
      public void onClick(View v) {
            if (mOnclickListener != null)
                      mOnclickListener.onClick(v);
              initTimer();
             this.setText(time / 1000 + textafter);
             this.setEnabled(false);
              t.schedule(tt, 0, 1000);
              // t.scheduleAtFixedRate(task, delay, period);
          }

    /**
     * * 和activity的onDestroy()方法同步
     */
               public void onDestroy() {
              if (BTDApplication.map == null)
                  BTDApplication.map = new HashMap<String, Long>();
                   BTDApplication.map.put(TIME, time);
                   BTDApplication.map.put(CTIME, System.currentTimeMillis());
               clearTimer();
               Log.e("gunlei", "onDestroy");
          }

             /**
         * 和activity的onCreate()方法同步
         */
              public void onCreate(Bundle bundle) {
               Log.e("gunlei", BTDApplication.map + "");
              if (BTDApplication.map == null)
                       return;
               if (BTDApplication.map.size() <= 0)// 这里表示没有上次未完成的计时
                       return;
              long time = System.currentTimeMillis() - BTDApplication.map.get(CTIME)
                       - BTDApplication.map.get(TIME);
                  BTDApplication.map.clear();
              if (time > 0)
                      return;
              else {
                       initTimer();
                     this.time = Math.abs(time);
                      t.schedule(tt, 0, 1000);
                      this.setText(time + textafter);
                       this.setEnabled(false);
                   }
          }

              /** * 设置计时时候显示的文本 */
             public TimeButton setTextAfter(String text1) {
              this.textafter = text1;
             return this;
         }
               /** * 设置点击之前的文本 */
               public TimeButton setTextBefore(String text0) {
                this.textbefore = text0;
              this.setText(textbefore);
               return this;
          }

                /**
        * 设置到计时长度
         *
         * @param lenght
        *            时间 默认毫秒
        * @return
         */
                public TimeButton setLenght(long lenght) {
                this.lenght = lenght;
               return this;
           }
        /*

*
*/

}
