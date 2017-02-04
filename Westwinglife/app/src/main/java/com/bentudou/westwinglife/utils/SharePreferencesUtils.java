package com.bentudou.westwinglife.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alibaba.fastjson.JSONArray;
import com.bentudou.westwinglife.json.Collection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import common.retrofit.RTHttpClient;

/**
 * Created by Ben on 2015/8/4.
 */
public class SharePreferencesUtils implements Serializable {

    private static final long serialVersionUID = 6764883196320174127L;

    public static void saveSessionId(Context context,String session){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("session_id", session).commit();

    }

    public static String getSessionId(Context context){

        String session_id = null;
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        session_id = sp.getString("session_id", "");
        return session_id;
    }
    public static void saveBtdToken(Context context,String btd_token,String mobile,String inviteCode){
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("btd_token", btd_token);
        editor.putString("phone", mobile);
        editor.putString("user_invite_code", inviteCode);
        editor.commit();

    }

    public static String getBtdToken(Context context){

        String btd_token = null;
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        btd_token = sp.getString("btd_token", "");
        return btd_token;
    }
    public static String getInviteCode(Context context){

        String btd_token = null;
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        btd_token = sp.getString("user_invite_code", "");
        return btd_token;
    }

    public static String getMobile(Context context){

        String btd_token = null;
        SharedPreferences sp = context.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        btd_token = sp.getString("phone", "");
        return btd_token;
    }
    public static void saveFirstIn(Context context,boolean isFirstIn){
        SharedPreferences sp = context.getSharedPreferences("first_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFirstIn", isFirstIn).commit();

    }

    public static boolean getFirstIn(Context context){

        boolean isFirstIn = false;
        SharedPreferences sp = context.getSharedPreferences("first_pref", Context.MODE_PRIVATE);
        isFirstIn = sp.getBoolean("isFirstIn",true);
        return isFirstIn;
    }

    public static void saveCollection(List<Collection> collections, Context context){

        String data = JSONArray.toJSONString(collections);
        SharedPreferences sp = context.getSharedPreferences("GUNLEI", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("collections");
        editor.putString("collections", data).commit();

    }

    public static List<Collection> getCollection(Context context){

        SharedPreferences sp = context.getSharedPreferences("GUNLEI", Context.MODE_PRIVATE);

        String arrayStr = sp.getString("collections", null);

        return arrayStr!=null? JSONArray.parseArray(arrayStr, Collection.class):null;
    }

//    public static void deleteCart(Context context){
//
//        SharedPreferences sp = context.getSharedPreferences("GUNLEI", context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sp.edit();
//
//        editor.remove("carts").commit();
//        Constant.CARTS = new ArrayList<Cart>();
//    }


    public static void updateRate(Context context,BigDecimal rate){
        String nowRate = VerifitionUtil.getStringRate(rate);
        SharedPreferences sp = context.getSharedPreferences("rate_update", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("rmb_rate",nowRate);
        editor.commit();
    }
    public static BigDecimal getdateRate(Context context){
        SharedPreferences sp = context.getSharedPreferences("rate_update", Context.MODE_PRIVATE);
        BigDecimal dateRate =new BigDecimal(sp.getString("rmb_rate","6.667"));
        return dateRate;
    }
    public static void updateImg_url(Context context,String rate){
        SharedPreferences sp = context.getSharedPreferences("img_update", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("img_url",rate);
        editor.commit();
    }
    public static String getImg_url(Context context){
        SharedPreferences sp = context.getSharedPreferences("img_update", Context.MODE_PRIVATE);
        String dateRate =sp.getString("img_url","http://img.westwinglife.cn");
        return dateRate;
    }
    public static int getUpdate(Context context){

        int access_token = 0;
        SharedPreferences sp = context.getSharedPreferences("first_update", Context.MODE_PRIVATE);
        access_token = Integer.valueOf(sp.getString("update", "1"));

        return access_token;
    }
    //保存筛选状态
    public static void saveUpdate(Context context){
        SharedPreferences sp = context.getSharedPreferences("first_update", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("update", RTHttpClient.VERSION_CODE).commit();

    }
    //保存白净图片
    public static void saveBgImg(Context context){
        int n= new Random().nextInt(11)+1;
        SharedPreferences sp = context.getSharedPreferences("bg_img", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("bg", n).commit();

    }
    public static int getBgImg(Context context){

        int access_token = 0;
        SharedPreferences sp = context.getSharedPreferences("bg_img", Context.MODE_PRIVATE);
        access_token = sp.getInt("bg",1);

        return access_token;
    }
    public static String getVersion(Context context)//获取版本号
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "未知";
        }
    }
 public static int getVersionCode(Context context)//获取版本号
    {
        try {
            PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }




//    //保存筛选状态
//    public static void saveSelectState(List<RequestCarCountForm> forms, Context context){
//
//        String data = JSONArray.toJSONString(forms);
//        SharedPreferences sp = context.getSharedPreferences("SELECT", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.remove("forms");
//        editor.putString("forms", data).commit();
//
//    }
//
//    public static List<RequestCarCountForm> getSelectState(Context context){
//
//        SharedPreferences sp = context.getSharedPreferences("SELECT", Context.MODE_PRIVATE);
//
//        String arrayStr = sp.getString("forms", null);
//
//        return arrayStr!=null? JSONArray.parseArray(arrayStr, RequestCarCountForm.class):null;
//    }
//
//    public static void deleteSelectState(Context context){
//
//        SharedPreferences sp = context.getSharedPreferences("SELECT", context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sp.edit();
//
//        editor.remove("forms").commit();
//    }

}
