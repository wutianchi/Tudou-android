/**
 * FileLogger.java created at 2014年11月14日 下午2:36:13
 */
package com.gunlei.app.ui.util;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 追加式文件日志输出工具，请仅在测试时使用。
 * @author liuchangjiong
 * @since 2014年11月14日
 *  
 */
public class FileLogger {
	public static String dirName = "dache_log";
	  //用于格式化日期,作为日志文件名的一部分  
	  private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	  
	/**
	 * 纪录日志
	 * @param logFileName 日志文件名
	 * @param log 日志内容
	 */
	public static final synchronized void writeLog(Context ctx, String logFileName, String log) {
		if(FileManager.sdCardIsAvailable()) {
			String path = "/sdcard/"+ dirName + "/";  
            File dir = new File(path);  
            if (!dir.exists()) {  
                dir.mkdirs();  
            }

			FileOutputStream fos = null;
			try {
				log = formatter.format(new Date()) + "," + log + "\n";
				  fos = new FileOutputStream(path + logFileName, true);  
				  fos.write(log.getBytes("UTF-8"));  
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {
				if(fos != null) {
					  try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  
					  fos = null;
				}
			}
		}
	}
}
