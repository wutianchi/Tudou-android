package com.bentudou.westwinglife.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bentudou.westwinglife.backend.PotatoService;
import com.bentudou.westwinglife.json.Success;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import common.retrofit.RTHttpClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by 天池 on 2015/9/25.
 */
public class VerifitionUtil {
    public static boolean mobileMumVerify(String phoneNum) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(170)|(18[0,5-9])|(14[5,7]))\\d{8}$");
        return p.matcher(phoneNum).matches();
    }
    public static boolean mobile(String phoneNum) {
        Pattern p = Pattern
                .compile("^[0-9]*$");
        return p.matcher(phoneNum).matches();
    }
    public static boolean noexpression(String phoneNum) {
        Pattern p = Pattern
                .compile("^[a-zA-Z0-9_\u4e00-\u9fa5/^\\s*$/]+$");
        return p.matcher(phoneNum).matches();
    }
    public static boolean mailAddressVerify(String mailAddress) {
        String emailExp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(emailExp);
        return p.matcher(mailAddress).matches();
    }
    //BIgDecimal转换成string
    public static String getStringPrice(BigDecimal bigDecimal){
        String stringPrice;
        DecimalFormat df = new DecimalFormat("0.00");
        stringPrice ="$"+ df.format(bigDecimal);
        return stringPrice;
    }
    //BIgDecimal转换成人民币string
    public static String getRMBStringPrice(BigDecimal bigDecimal){
        String stringPrice;
        DecimalFormat df = new DecimalFormat("0.00");
        stringPrice ="¥"+ df.format(bigDecimal);
        return stringPrice;
    }
    //double转换成string
    public static String getDoubleStringPrice(double db){
        String stringPrice;
        BigDecimal bigDecimal = new BigDecimal(db);
        DecimalFormat df = new DecimalFormat("0.00");
        stringPrice ="$"+ df.format(bigDecimal);
        return stringPrice;
    }
    //double转换成string
    public static String getDoubleRMBStringPrice(double db){
        String stringPrice;
        BigDecimal bigDecimal = new BigDecimal(db);
        DecimalFormat df = new DecimalFormat("0.00");
        stringPrice ="¥"+ df.format(bigDecimal);
        return stringPrice;
    }
    //BIgDecimal转换成string
    public static String getStringRate(BigDecimal bigDecimal){
        String stringPrice;
        DecimalFormat df = new DecimalFormat("0.000");
        stringPrice =df.format(bigDecimal);
        return stringPrice;
    }
    //BIgDecimal转换成string
    public static String getDollarStringPrice(BigDecimal bigDecimal){
        String stringPrice;
        DecimalFormat df = new DecimalFormat("0.00");
        stringPrice = df.format(bigDecimal);
        return stringPrice;
    }
    //BIgDecimal转换成string
    public static String getStringZhengPrice(BigDecimal bigDecimal){
        String stringPrice;
        DecimalFormat df = new DecimalFormat("0");
        stringPrice = df.format(bigDecimal);
        return stringPrice;
    }

    //时间戳转时间
    public static String getStyleTime(long db){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(db);
        return date;
    }
    //隐藏身份证号
    public static String getIdCard(String idcard){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(idcard.substring(0,4)).append("**********").append(idcard.substring(14));
        return stringBuffer.toString();
    }
    //BIgDecimal美元转换成string人民币
    public static String getRMBStringPrice(Context context,BigDecimal bigDecimal){
        String stringPrice;
        BigDecimal b =bigDecimal.multiply(SharePreferencesUtils.getdateRate(context));
        stringPrice =b.setScale(2,BigDecimal.ROUND_UP).toString();
        return stringPrice;
    }
    //从字符串筛选数字
    public static String copyExpress(String str1) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<str1.length();i++){
            if (str1.charAt(i) > 47 && str1.charAt(i) < 58){
                stringBuffer.append(str1.charAt(i));
            }
        }
        if (stringBuffer.toString().isEmpty()){
            return "";
        }else {
            return stringBuffer.toString();
        }
    }
    //根据特定字符截取
    public static String indexExpress(String str1) {
        int index = str1.lastIndexOf("：");
        if (index<0){
            return "";
        }else {
            return str1.substring(index+1);
        }
    }
    /**
     * 检查是否安装微信
     *
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
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
        ConnectivityManager connectivityManager = null;
        try {
            connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (NullPointerException e){
            return false;
        }catch (Exception e) {
            e.printStackTrace();
        }

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

    public static void uploadImg(String token,String path){
        File file = new File(path);
        Map<String,RequestBody> params = new HashMap<>();
        params.put("BtdToken",toRequestBody(token));
        params.put("iDImg\"; filename=\""+file.getName(),RequestBody.create(MediaType.parse("image/png"), file));
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        final PotatoService service = RTHttpClient.create(PotatoService.class);
        service.uploadIDImage(params,new Callback<Success>() {
            @Override
            public void success(Success success, Response response) {
                if (success.getStatus().equals("1")){
                    Log.d("success","------"+success.getData());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("failure","------"+error.toString());
            }
        });

    }

    private static RequestBody toRequestBody(String token) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), token);
        return requestBody;
    }
}
