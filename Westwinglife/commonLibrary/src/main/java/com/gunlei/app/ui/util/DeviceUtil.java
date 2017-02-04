package com.gunlei.app.ui.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.DataOutputStream;
import java.io.File;
import java.util.List;

/**
 * this class can provides some device info,include DeviceVision
 */
public class DeviceUtil {
    /*
	 * 设备型号
	 */
    public static String getDeviceModel(){
        return Build.MANUFACTURER+Build.MODEL;
    }
    /*
     * 设备参数
     */
    public static String getDevice(){
        return Build.DEVICE;
    }
    /**硬件制造商*/
    public static String getFuctoryName(){
        return Build.MANUFACTURER;
    }
    /*
     * OS版本号
     */
    public static String getDeviceVersion(){
        return Build.VERSION.RELEASE;
    }
    /**
     * 获取手机mac地址<br/>
     * 错误返回12个0
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "000000000000";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress().replace(":", "");
                else
                    return macAddress;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }
    /**
     * 设备Id GSM IMEI  CDMA   MEID
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return phoneManager.getDeviceId();
    }
    /**获取本应用的版本号*/
    public static int getAppVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        int versionCode = 0;
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return versionCode;
    }
    public static String getAppVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try{
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * 设备的软件版本号：
     */
    public static String getSoftwareVersion(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return phoneManager.getDeviceSoftwareVersion();
    }
    /**
     *手机号：
     * GSM手机的 MSISDN.
     * @param context
     * @return
     */
    public static String getNumber(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return phoneManager.getLine1Number();
    }

    /**
     * 当前使用的网络类型：
     * @param context
     * @return
     * 例如： NETWORK_TYPE_UNKNOWN  网络类型未知  0
    NETWORK_TYPE_GPRS     GPRS网络  1
    NETWORK_TYPE_EDGE     EDGE网络  2
    NETWORK_TYPE_UMTS     UMTS网络  3
    NETWORK_TYPE_HSDPA    HSDPA网络  8
    NETWORK_TYPE_HSUPA    HSUPA网络  9
    NETWORK_TYPE_HSPA     HSPA网络  10
    NETWORK_TYPE_CDMA     CDMA网络,IS95A 或 IS95B.  4
    NETWORK_TYPE_EVDO_0   EVDO网络, revision 0.  5
    NETWORK_TYPE_EVDO_A   EVDO网络, revision A.  6
    NETWORK_TYPE_1xRTT    1xRTT网络  7
     */
    public static int getNetType(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return phoneManager.getNetworkType();
    }
    /**
     * 唯一的用户ID：IMSI(国际移动用户识别码) for a GSM phone.
     * @param context
     * @return
     */
    public static String getSubscriberId(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        return phoneManager.getSubscriberId();
    }

    public static String getCellID(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        CellLocation cellLocation = phoneManager.getCellLocation();
        if(cellLocation!=null&&cellLocation instanceof GsmCellLocation){
            GsmCellLocation cell= (GsmCellLocation)phoneManager.getCellLocation();
            return cell.getCid() + "";
         }else if(cellLocation!=null&&cellLocation instanceof CdmaCellLocation){
            CdmaCellLocation cell= (CdmaCellLocation)phoneManager.getCellLocation();
            return cell.getBaseStationId() + "";
         }else{
            return "";
         }

        }

    public static String getLac(Context context){
        TelephonyManager phoneManager = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        CellLocation cellLocation = phoneManager.getCellLocation();
        if(cellLocation!=null&&cellLocation instanceof GsmCellLocation){
            GsmCellLocation cell= (GsmCellLocation)phoneManager.getCellLocation();
            return cell.getLac() + "";
        }else if(cellLocation!=null&&cellLocation instanceof CdmaCellLocation){
            CdmaCellLocation cell= (CdmaCellLocation)phoneManager.getCellLocation();
            return cell.getNetworkId() + "";
        }else{
            return "";
        }
    }

    /**有root权限的 直接卸载*/
    public static void uninstallWithRoot(Context context, String packageName) {
        try {
            PackageManager manager = context.getPackageManager();
            List<PackageInfo> apps = manager.getInstalledPackages(0);
            String result = "";
            boolean packageExist = false;
            if (apps != null) {
                for (int i = 0; i < apps.size(); i++) {
                    if (apps.get(i).packageName.equals(packageName)) {
                        packageExist = true;
                        break;
                    }
                }
            }
            //如果包不存在，则返回
            if (!packageExist) {
                return;
            }

            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream dos = new DataOutputStream(process.getOutputStream());
            dos.writeBytes("pm uninstall " + packageName + "\n");
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**没有root权限的 提示卸载*/
    public static void uninstall_tip(Context context , String packageName ) {
        try {
            PackageManager manager = context.getPackageManager();
            List<PackageInfo> apps = manager.getInstalledPackages(0);
            String result = "";
            boolean packageExist = false;
            if (apps != null) {
                for (int i = 0; i < apps.size(); i++) {
                    if (apps.get(i).packageName.equals(packageName)) {
                        packageExist = true;
                        break;
                    }
                }
            }
            //如果包不存在，则返回
            if (!packageExist) {
                return;
            }
            //通过程序的包名创建URI
            Uri packageURI = Uri.parse("package:" + packageName);
            //创建Intent意图
            Intent intent = new Intent(Intent.ACTION_DELETE,packageURI);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //执行卸载程序
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**有root权限的 直接杀掉进程*/
    public static void killProcessesWithRoot( String packageName , Context context ) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        int i = list.size();
        int pid = -1;
        for (int j = 0; j < list.size(); j++) {
            if (packageName.equals(list.get(j).processName)) {
                pid = list.get(j).pid;
                break;
            }
        }
        if (pid != -1) {
//        	Logger.e("AAA", "pid:" + pid);
            try {
                Process process = Runtime.getRuntime().exec("su");
                DataOutputStream dos = new DataOutputStream(process.getOutputStream());
                dos.writeBytes("kill " + pid + "\n");
                dos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**获取手机是否有root权限*/
    public static boolean getIsSuper() {
        boolean root = false;
        try {
            if ((!new File("/system/bin/su").exists())
                    && (!new File("/system/xbin/su").exists())) {
                root = false;
            } else {
                root = true;
            }

        } catch (Exception e) {
        }

        return root;
    }

    /**
     * 获取屏幕大小
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Activity context){
        DisplayMetrics metrics=new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        DisplayMetrics metrics1= Resources.getSystem().getDisplayMetrics();
        return metrics;
    }
    public static int dp2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale +0.5f);
    }
    public static int px2dp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale +0.5f);
    }
    public static int px2sp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / scale +0.5f);
    }
    public static int sp2px(Context context, float spValue){
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale +0.5f);
    }

}
