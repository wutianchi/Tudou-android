/**
 * NumberUtil.java created at 2014-9-26 下午2:23:12
 */
package com.gunlei.app.ui.util;

import java.text.NumberFormat;

/**
 * 数字解析及格式化工具.
 * 格式化显示小数点的功能
 * @author liuchangjiong
 * @since 2014-9-26
 *  
 */
public class NumberUtil {
	private static NumberFormat nf;
	private static NumberFormat nfLong;
	static {
		nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(3);// 最大小数点个数
		nf.setMinimumFractionDigits(3);// 最小小数点个数
		nf.setMinimumIntegerDigits(1);// 最小整数个数
		
		nfLong = NumberFormat.getNumberInstance();		
	}
	
	public static String formatLong(Number number) {
		return nfLong.format(number);
	}
	
	/**
	 * 毫秒格式化为 时分秒毫秒格式.
	 * @param time
	 * @return
	 */
	public static String msTimeToString(long time) {
		return longToTimeString(time, 1000);
	}
	
	public static String longToTimeString(Number time, Number osFrequency) {
		return longToTimeString(time.longValue(), osFrequency.longValue());
	}

	/**
	 * 纳秒时间格式化为纳秒到天.
	 * @param time
	 * @param osFrequency
	 * @return
	 */
	public static String longToTimeString(long time, long osFrequency) {
		double d = osFrequency;
		double d1 = d / 1000000000D;
		double d2 = d / 1000000D;
		double d3 = d / 1000D;
		double d4 = d * 60D;
		double d5 = d4 * 60D;
		double day = d5 * 24D;
		if (time == 0L)
			return "0秒";
		StringBuffer stringbuffer = new StringBuffer();
		boolean flag = false;
		double d10 = time;
		double d7 = d10 / day;
		long l2 = (long) d7;
		double d9 = d10 - (double) l2 * day;
		if (l2 > 0L || flag) {
			stringbuffer.append(l2).append("日");
			flag = true;
			if (l2 > 0L)
				d10 = d9;
		}
		d7 = d10 / d5;
		l2 = (long) d7;
		d9 = d10 - (double) l2 * d5;
		if (l2 > 0L || flag) {
			stringbuffer.append(l2).append("小时");
			flag = true;
			if (l2 > 0L)
				d10 = d9;
		}
		d7 = d10 / d4;
		l2 = (long) d7;
		d9 = d10 - (double) l2 * d4;
		if (l2 > 0L || flag) {
			stringbuffer.append(l2).append("分");
			flag = true;
			if (l2 > 0L)
				d10 = d9;
		}
		d7 = d10 / d;
		l2 = (long) d7;
		d9 = d10 - (double) l2 * d;
		if (l2 > 0L || flag) {
			stringbuffer.append(nf.format(d7)).append("秒 ");
			flag = true;
			if (l2 > 0L) {
			}
		}
		if (flag)
			return stringbuffer.toString();
		d7 = (double) time / d3;
		if (d7 >= 1.0D)
			return nf.format(d7) + "毫秒";//ms
		d7 = (double) time / d2;
		if (d7 >= 1.0D) {
			return nf.format(d7) + "微秒";//us
		} else {
			double d8 = (double) time / d1;
			return nf.format(d8) + "纳秒";//ns
		}
	}
	
	/** 解析字符串为整数, 兼容浮点数 */
	public static int getInt(String s) {
		return getNumber(s, int.class, 0);
	}
	
	/** 解析字符串为长整数, 兼容浮点数 */
	public static long getLong(String s) {
		return getNumber(s, long.class, 0);
	}
	
	/** 解析字符串为浮点数 */
	public static float getFloat(String s) {
		return getNumber(s, float.class, 0);
	}
	
	/** 解析字符串为双精度数 */
	public static double getDouble(String s) {
		return getNumber(s, double.class, 0);
	}
	
	/**
	 * 进行字符串转数字的逻辑, 支持自动转换类型.
	 * 如: int i = NumberUtil.getNumber("10.5", int.class, 0);
	 * @param numberStr 要解析的字符串
	 * @param <T> 数字类型, 目前只支持int,long,float,double
	 * @param defaultValue 解析失败时的默认值
	 * @return 数字类型
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T getNumber(String numberStr, Class<T> clazz, Number defaultValue){
		if(numberStr != null) {
			try {
				if(clazz.equals(int.class)) {
					try {
						Integer v = Integer.valueOf(numberStr);
						return (T) v;
					} catch(NumberFormatException e) {
//						e.printStackTrace();
                        try {
                            Float d = Float.parseFloat(numberStr);
                            return (T) Integer.valueOf(Math.round(d));
                        } catch (NumberFormatException e1) {
                            return (T) new Integer(0);
                        }
                    }
				} else if(clazz.equals(long.class)) {
					try {
						Long v = Long.valueOf(numberStr);
						return (T) v;
					} catch(NumberFormatException e) {
//						e.printStackTrace();
						Double d = getNumber(numberStr, double.class, defaultValue);
						return (T) Long.valueOf(Math.round(d));
					}
				} else if(clazz.equals(float.class)) {
					try {
						Float v = Float.valueOf(numberStr);
						return (T) v;
					} catch(NumberFormatException e) {
						Double d = getNumber(numberStr, double.class, defaultValue);
						return (T)d;
					}
				}else if(clazz.equals(double.class)) {
					try {
						Double v = Double.valueOf(numberStr);
						return (T) v;
					} catch(NumberFormatException e) {
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return (T)defaultValue;
	}
	
	public static void main(String[] args) {
		int i = NumberUtil.getNumber("10.5", int.class, 0);
		System.out.println(i);
		long l = NumberUtil.getNumber("21.5", long.class, 0);
		System.out.println(l);
		float f = NumberUtil.getNumber("24.5", float.class, 0);
		System.out.println(f);
		double d = NumberUtil.getNumber("50.5", double.class, 0);
		System.out.println(d);
	}

}
