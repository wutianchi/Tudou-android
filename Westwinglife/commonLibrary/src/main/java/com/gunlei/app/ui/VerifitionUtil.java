package com.gunlei.app.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.regex.Pattern;

/**
 * Created by 天池 on 2015/9/25.
 */
public class VerifitionUtil {
    public static boolean mobileMumVerify(String phoneNum) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(170)|(18[0,5-9])|(14[5,7]))\\d{8}$");
        return p.matcher(phoneNum).matches();

    }

    public static boolean mailAddressVerify(String mailAddress) {
        String emailExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(emailExp);
        return p.matcher(mailAddress).matches();
    }
    /**
     * 检查当前网络是否可用
     *
     * @param
     * @return
     */

    public static boolean isNetworkAvailable(Context activity)
    {
//        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    //System.out.println(i + "===状态===" + networkInfo[i].getState());
                    //System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
