/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:yongche.com
 */
package com.gunlei.app.ui.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Title:
 * @Description:
 * @Author:rongfzh
 * @Email:rongfangzhi@gmail.com
 * @Since:2012-3-27
 * @Version:1.1.0
 */
public class NetUtil {

	/** 检测手机 wifi 或者 移动网络 是否启动 */
	public static boolean checkNet(Context context) {// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 判断网络是否可用̬
	 */
	public static boolean isAccessNetwork(Context context) {
		boolean bExistNetwork = isAccessNetwork_hard(context);
		if (!bExistNetwork) {
			bExistNetwork = isMobileConnected(context);
		}
		return bExistNetwork;
	}

	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != mConnectivityManager) {
				NetworkInfo mMobileNetworkInfo = mConnectivityManager
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				if (mMobileNetworkInfo != null
						&& mMobileNetworkInfo.isAvailable()) {
					return mMobileNetworkInfo.isConnectedOrConnecting();
				}
			}
		}
		return false;
	}

	public static boolean isAccessNetwork_hard(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isAvailable()
				&& activeInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}


}
