package com.gunlei.app.ui.util;

/**
 * 字符串高速合并工具.
 * @author liuchangjiong
 * @since Dec 13, 2013
 *  
 */
public class StringMerger {
	/**
	 * 合并字符串的工具方法。
	 * @param strings 一个或者多个对象或字符串.
	 * @return
	 */
	public static String merge(Object... strings) {
  		int totalLength = 0;
  		for(Object s : strings) {
  			if(s instanceof String) {
  				totalLength += ((String) s).length();
  			} else {
  				totalLength += 5;
  			}
  			
  		}
  		
  		StringBuffer buff = new StringBuffer(totalLength);
  		
  		for(Object s : strings) {
  			buff.append(s);
  		}
  		
  		return buff.toString();
  	}
}
