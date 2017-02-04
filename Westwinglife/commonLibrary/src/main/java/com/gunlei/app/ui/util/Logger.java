package com.gunlei.app.ui.util;

import android.util.Log;

//import YongcheConfig;

/**
 * 日志记录类。
 * FIXME 基础配置类？
 * @author wanghaoyue
 */
public class Logger {

	public static String getTag(Class clazz) {
		return clazz.getSimpleName();
	}

	private static final boolean IS_PRINT_LOG = true;//YongcheConfig.PRINT_LOG;
	
	private static String logFormat(Object... args) {
		String s = "";
		for (int i = 0; i < args.length; i++) {
			if (!(args[i] == null))
				s = s + "," + args[i];
		}
		return s;
	}
	
	public static void v(String tag, Object...messages){
		if(IS_PRINT_LOG){
			Log.v(tag, logFormat(messages));
		}
	}
	
	public static void d(String tag, Object...messages){
		if(IS_PRINT_LOG){
			Log.d(tag, logFormat(messages));
		}
	}
	
	public static void i(String tag, Object...messages){
		if(IS_PRINT_LOG){
			Log.i(tag, logFormat(messages));
		}
	}
	
	public static void e(String tag, Object...messages){
		if(IS_PRINT_LOG){
			Log.e(tag, logFormat(messages));
		}
	}
	
	public static void w(String tag, Object...messages){
		if(IS_PRINT_LOG){
			Log.w(tag, logFormat(messages));
		}
	}
	
	public static void log(String str) {
		if (IS_PRINT_LOG) {
			System.out.print(str + "\n");
		}
	}
}
