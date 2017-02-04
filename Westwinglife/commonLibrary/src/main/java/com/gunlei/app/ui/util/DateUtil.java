package com.gunlei.app.ui.util;

import android.content.Context;
import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/** 日期处理类 */
public class DateUtil {

	private static final String TAG = "DateUtil";
	private static long lastClickTime;
    private static String timeZoneID = "GMT+8";

	
	/**获取手机当前时间yyyy-MM-dd HH:mm  */
	public static String getCurData(){
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm");      
		Date  curDate   =   new   Date(System.currentTimeMillis());//获取当前时间      
    	return formatter.format(curDate); 
    }
	
	/**获取手机当前时间yyyy-MM-dd HH:mm:ss  */
	public static String getCurData2(){
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm:ss");      
		Date  curDate   =   new   Date(System.currentTimeMillis());//获取当前时间      
		return formatter.format(curDate); 
	}
	
	/**获取手机当前时间yyyy-MM-dd   */
	public static String getCurData3(){
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");      
		Date  curDate   =   new   Date(System.currentTimeMillis());//获取当前时间      
		return formatter.format(curDate); 
	}

	/**
	 * 将毫秒转换成日期时间类型的数据
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return yyyy-MM-dd HH:mm
	 */
	public static String secondToStringDT(long date) {
        String charDateWhole = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        charDateWhole = sdf.format(date);
        return charDateWhole;
	}

	/**
	 * 将毫秒转换成日期时间类型的数据
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String secondToStringMDHMS(long date) {

		String charDateWhole = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		charDateWhole = sdf.format(date);
		return charDateWhole;
	}
	
	/**
	 * 将秒转换成日期时间类型的数据
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return MM/dd HH:mm
	 */
	public static String secondToStringMDHM(long date) {
		String charDateWhole = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		charDateWhole = sdf.format(date).replace("-", "/");
		return charDateWhole;
	}

	/**
	 * 将日期类型转换成毫秒值
	 * @param str  年月日时分
	 * @return
	 */
	public static Long getTTimeStemp(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d.getTime();
    }
	
	
	
	/**
	 * 将毫秒转换为日期类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return yyyy-MM-dd
	 */
	public static String secondToStringD(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	/**
	 * 将秒转换为月-日类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return MM-dd
	 */
	public static String secondToDate(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	/**
	 * 将秒转换为年-月类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return yyyy-MM
	 */
	public static String secondToYD(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	/**
	 * 将秒转换为时间类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return HH:mm
	 */
	public static String secondToStringT(long date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	/**
	 * 将秒转换为小时类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return HH
	 */
	public static String secondToStringH(long date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	/**
	 * 将秒转换为日期时间类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒 若为Date类型的数据需要转换成Date.getTime()
	 * @return MM-dd hh:mm
	 */
	public static String secondToShortDate(long date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	public static String newSecondToShortDate(long date) {
		Date d = new Date();
		d.setTime(date);
		String minutes = d.getMinutes() < 10 ? ("0" + d.getMinutes()) : (d
				.getMinutes() + "");
		String time = d.getHours() < 10 ? ("0" + d.getHours() + ":" + minutes)
				: (d.getHours() + ":" + minutes);
		d = null;
		return secondToDate(date) + " " + time;
	}

//	public static String SecondToWeekDate(long date,boolean tag) {
//		Date d = new Date();
//		d.setTime(date);
//		String minutes = d.getMinutes() < 10 ? ("0" + d.getMinutes()) : (d.getMinutes() + "");
//		String time = d.getHours() < 10 ? ("0" + d.getHours() + ":" + minutes) : (d.getHours() + ":" + minutes);
//		String week = null;
//		String day = null;
//		switch (d.getDay()) {
//		case 0:
//			week = "周日";
//			break;
//		case 1:
//			week = "周一";
//			break;
//		case 2:
//			week = "周二";
//			break;
//		case 3:
//			week = "周三";
//			break;
//		case 4:
//			week = "周四";
//			break;
//		case 5:
//			week = "周五";
//			break;
//		case 6:
//			week = "周六";
//			break;
//		}
//		if (isSameDay(getSystemTime(BSApplication.getInstance()), date)) {
//			day = "今天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance())
//				+ 24 * 60 * 60 * 1000, date)) {
//			day = "明天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance())
//				- 24 * 60 * 60 * 1000, date)) {
//			day = "昨天";
//		}  else if (isSameDay(getSystemTime(BSApplication.getInstance()) + 48 * 60 * 60 * 1000, date)) {
//			day = "后天";
//		} else {
//			if(tag){
//				day = secondToDate(date).replace("-", ".");
//			}else{
//				day = secondToStringD(date).replace("-", "/");
//			}
//		}
//		d = null;
//		if(tag){
//			return day + "\n" + week + "\n" + time;
//		}else{
//			return day + "(" + week + ")";
//		}
//	}
	
//	/**
//	 * 日期格式化为可读文字, 包含日期和明后天, 星期提示.
//	 * @param date
//	 * @return
//	 */
//	public static String SecondToWeekDate_two(long date) {
//		Date d = new Date();
//		d.setTime(date);
//		String minutes = d.getMinutes() < 10 ? ("0" + d.getMinutes()) : (d.getMinutes() + "");
//		String time = d.getHours() < 10 ? ("0" + d.getHours() + ":" + minutes)
//				: (d.getHours() + ":" + minutes);
//		String week = null;
//		String day = null;
//		switch (d.getDay()) {
//		case 0:
//			week = "周日";
//				break;
//			case 1:
//			week = "周一";
//			break;
//		case 2:
//			week = "周二";
//			break;
//		case 3:
//			week = "周三";
//			break;
//		case 4:
//			week = "周四";
//			break;
//		case 5:
//			week = "周五";
//			break;
//		case 6:
//			week = "周六";
//			break;
//		}
//		if (isSameDay(getSystemTime(BSApplication.getInstance()), date)) {
//			day = "今天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance()) + 24 * 60 * 60 * 1000, date)) {
//			day = "明天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance()) + 48 * 60 * 60 * 1000, date)) {
//			day = "后天";
//		} else {
//			day = secondToDate( date ) ;
//		}
//		d = null;
//
//		return day + "(" + week + ") " + time;
//
//	}
	
//	/**
//	 * 日期格式化为可读文字, 包含日期和明后天, 时间, 无星期.
//	 * @param s
//	 * @return
//	 */
//	public static String secondToTomorrow(String s) {
//
//        String day = "";
//        String time = "";
//        try {
//            Long date = Long.parseLong(s)*1000L;
//            Date d = new Date();
//            d.setTime(date);
//            String minutes = d.getMinutes() < 10 ? ("0" + d.getMinutes()) : (d.getMinutes() + "");
//            time = d.getHours() < 10 ? ("0" + d.getHours() + ":" + minutes)
//                    : (d.getHours() + ":" + minutes);
//
//            if (isSameDay(getSystemTime(BSApplication.getInstance()), date)) {
//                day = "今天";
//            } else if (isSameDay(getSystemTime(BSApplication.getInstance()) + 24 * 60 * 60 * 1000, date)) {
//                day = "明天";
//            } else if (isSameDay(getSystemTime(BSApplication.getInstance()) + 48 * 60 * 60 * 1000, date)) {
//                day = "后天";
//            } else {
//                day = secondToDate( date ) ;
//            }
//            d = null;
//
//        }catch (Exception e){
//
//        }
//
//
//		return day + "  " + time;
//	}
	
	public static String SecondToDayAndWeek(long date) {
		Date d = new Date();
		d.setTime(date);
		String week = null;
		String day = null;
		switch (d.getDay()) {
		case 0:
			week = "周日";
			break;
		case 1:
			week = "周一";
			break;
		case 2:
			week = "周二";
			break;
		case 3:
			week = "周三";
			break;
		case 4:
			week = "周四";
			break;
		case 5:
			week = "周五";
			break;
		case 6:
			week = "周六";
			break;
		}
		day = secondToDate(date).replace("-", ".");
		return day + " " + week;
	}

//	/**
//	 * 格式化日期为 后天或08.13 10:00
//	 * @param date
//	 * @return
//	 */
//	public static String SecondToLastAndNextDate(long date) {
//		Date d = new Date();
//		d.setTime(date);
//		String minutes = d.getMinutes() < 10 ? ("0" + d.getMinutes()) : (d
//				.getMinutes() + "");
//		String time = d.getHours() < 10 ? ("0" + d.getHours() + ":" + minutes)
//				: (d.getHours() + ":" + minutes);
//		String day = null;
//		if (isSameDay(getSystemTime(BSApplication.getInstance()), date)) {
//			day = "今天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance())
//				+ 24 * 60 * 60 * 1000, date)) {
//			day = "明天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance())
//				- 24 * 60 * 60 * 1000, date)) {
//			day = "昨天";
//		} else if (isSameDay(getSystemTime(BSApplication.getInstance()) + 48 * 60 * 60 * 1000, date)) {
//			day = "后天";
//		} else {
//			day = secondToDate(date).replace("-", ".");
//		}
//		d = null;
//		return day + " " + time;
//	}

	/**
	 * 将秒转换为分钟类型字符
	 * 
	 * @param date
	 *            <p>
	 *            传毫秒
	 *            <p>
	 *            若为Date类型的数据需要转换成Date.getTime()
	 * @return mm
	 */
	public static String secondToStringM(long date) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("mm");
//		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
		return sdf.format(date);
	}

	/**
	 * 将毫秒转换为小时分钟形式字符
	 * 
	 * @param date
	 * @return XX小时XX分钟
	 */
	public static String secodToString(long date) {
		if (date / 3600000 == 0 && (date / 60000) % 60 == 0) {
			return "0分钟";
		} else {
			return (date / 3600000 == 0 ? "" : String.valueOf(date / 3600000)
					+ "小时")
					+ ((date / 60000) % 60 == 0 ? "" : String.valueOf((date / 60000) % 60) + "分钟");
		}
	}

	/**
	 * 将秒转换为小时分钟形式字符
	 * 
	 * @param date
	 * @return XX小时XX分钟
	 */
	public static String secodToStr(long date) {
		if (date / 3600 == 0 && (date / 60) % 60 == 0) {
			return "0分钟";
		} else {
			return (date / 3600 == 0 ? "" : String.valueOf(date / 3600) + "小时")
					+ ((date / 60) % 60 == 0 ? "" : String
							.valueOf((date / 60) % 60) + "分钟");
		}
	}

	/**
	 * 获取校对后的时间
	 * @param context
	 * @return
	 */
	public static long getSystemTime(Context context) {
//		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
//		boolean is24 = DateFormat.is24HourFormat(context);
		long timeoffer = System.currentTimeMillis();
//		Logger.d("DateUtil", "timeoffer :" + timeoffer);
//		Logger.d("DateUtil", "======= :" + calendar.getTimeInMillis()+ timeoffer * 1000);
		return timeoffer;
	}

	/**
	 * 获取当天23：59：59
	 * 
	 * @param time
	 *            当天时间
	 * @return
	 */
	public static long getDayMaxTime(long time) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		c.setTimeInMillis(time);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return c.getTimeInMillis();
	}

	/**
	 * 判断系统时间是否与当前日期相同
	 * @param context
	 * @param time
	 * @return
	 */
	public static boolean isEquals(long time, Context context) {
		Time time1 = new Time();
		time1.set(time);
		Time time2 = new Time();
		time2.set(getSystemTime(context));
		return time1.year == time2.year && time1.month == time2.month
				&& time1.monthDay == time2.monthDay;
	}

	/**
	 * yyyy-mm-dd HH:mm 将日期格式的字符串 转换成 date
	 * 
	 * @param time
	 * @return
	 */
	public static Date getDateTime(String time) {
		StringBuffer sb = new StringBuffer();
		sb.append(time + ":00");
		return java.sql.Timestamp.valueOf(sb.toString());
	}

	/**
	 * 判断两个日期相差几天
	 * 
	 */
	public static Long getDateDiffer(String earlyTime, String lateTime) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Long c = sf.parse(earlyTime).getTime()-sf.parse(earlyTime).getTime();
			long d = c/1000/60/60/24;//天
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


    /**
     * 判断两个日期相差几小时
     *
     */
    public static Long getMinuteDiffer(String earlyTime, String lateTime) {
        try {
            Long c = Long.valueOf(lateTime) -  Long.valueOf(earlyTime);
            long d = c/1000/60;//天
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
	 * 将日期格式的字符串 转换成当天日期对应的星期几
	 * 
	 * @param time
	 *            -MM-dd 样式 字符串
	 * @return
	 */
	public static String getDateWeek(String time) {
		// 求今天是星期几
		String dateWeek = "";
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = d.parse(time);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
			dateWeek = simpleDateFormat.format(date);// date 也可以换成 "2009-7-7"
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (dateWeek.indexOf("周") != -1) {
			return "星期" + dateWeek.subSequence(1, dateWeek.length());
		} else {
			return dateWeek;
		}
	}

	/**
	 * 两时间相比，前一个时间是否小于后面（天)
	 * 
	 * @param prevTime
	 * @param nextTime
	 * @return
	 */
	public static boolean compare(long prevTime, long nextTime, boolean flag) {
		long timez = nextTime - prevTime;
		if (timez > 0) {
			if (timez / (24 * 60 * 60 * 1000) >= 1) {
				return true;
			} else {
				Calendar c = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
				c.setTimeInMillis(prevTime);
				int year1 = c.get(Calendar.YEAR);
				c.setTimeInMillis(nextTime);
				int year2 = c.get(Calendar.YEAR);
				if (year1 < year2) {
					return true;
				} else {
					c.setTimeInMillis(prevTime);
					int day1 = c.get(Calendar.DAY_OF_YEAR);
					c.setTimeInMillis(nextTime);
					int day2 = c.get(Calendar.DAY_OF_YEAR);
					if (flag) {
						if (day1 < day2) {
							return true;
						}
					} else {
						if (day1 <= day2) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 判断两个毫秒值是不是同一天
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean isSameDay(long time1, long time2) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		c.setTimeInMillis(time1);
		int year1 = c.get(Calendar.YEAR);
		c.setTimeInMillis(time2);
		int year2 = c.get(Calendar.YEAR);
		if (year1 == year2) {
			c.setTimeInMillis(time1);
			int day1 = c.get(Calendar.DAY_OF_YEAR);
			c.setTimeInMillis(time2);
			int day2 = c.get(Calendar.DAY_OF_YEAR);
			if (day1 == day2) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 取当月的第一天毫秒值
	 */
	public static long get_firstDay_of_month() {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		long min_month = cal.getTimeInMillis();
		return min_month / 1000;
	}

	/**
	 * 取当月的最后一天毫秒值
	 * 
	 * @return
	 */
	public static long get_lastDay_of_month() {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		long max_month = cal.getTimeInMillis();
		return max_month / 1000;
	}

	/**
	 * 取上月的第一天毫秒值
	 */
	public static long get_firstDay_of_pre_month() {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) - 1;
		if (month < 0) {
			month += 12;
			year = year - 1;
		}
		Calendar start_of_month = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		start_of_month.clear();

		start_of_month.set(Calendar.YEAR, year);
		start_of_month.set(Calendar.MONTH, month);
		start_of_month.set(Calendar.DAY_OF_MONTH,start_of_month.getActualMinimum(Calendar.DAY_OF_MONTH));
		start_of_month.set(Calendar.HOUR_OF_DAY, 0);
		start_of_month.set(Calendar.MINUTE, 0);
		start_of_month.set(Calendar.SECOND, 0);
		long min_month = start_of_month.getTimeInMillis();
		return min_month / 1000;
	}

	/**
	 * 取上月的最后一天毫秒值
	 * 
	 * @return
	 */
	public static long get_lastDay_of_pre_month() {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) - 1;
		if (month < 0) {
			month += 12;
			year = year - 1;
		}

		Calendar end_of_month = Calendar.getInstance(TimeZone.getTimeZone(timeZoneID));
		end_of_month.clear();

		end_of_month.set(Calendar.YEAR, year);
		end_of_month.set(Calendar.MONTH, month);
		end_of_month.set(Calendar.DAY_OF_MONTH,end_of_month.getActualMaximum(Calendar.DAY_OF_MONTH));
		end_of_month.set(Calendar.HOUR_OF_DAY, 23);
		end_of_month.set(Calendar.MINUTE, 59);
		end_of_month.set(Calendar.SECOND, 59);

		long max_month = end_of_month.getTimeInMillis();
		return max_month / 1000;
	}

	/**
	 * 将日期类型年-月转换成毫秒值
	 * 
	 * @param str
	 * @return
	 */
	public static Long getTimeStemp(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.getTime();
	}
	
	/**
	 * 将日期类型年-月转换成毫秒值
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String forTimeStemp2Date(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		try {
			Calendar cd = Calendar.getInstance();
			cd.setTimeInMillis(timestamp);
			return sdf.format(cd.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将日期类型年-月-日转换成毫秒值
	 * 
	 * @param str
	 * @return
	 */
	public static Long getDTime(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.getTime();
	}

	/**
	 * 将时间转换成毫秒值
	 * 
	 * @param str
	 * @return
	 */
	public static Long getTime(String str) {
		String[] time = str.split(":");
		int hour = Integer.parseInt(time[0]);
		int min = Integer.parseInt(time[1]);
		long totalSec = hour * 3600 + min * 60;
		return totalSec * 1000;
	}

	/**
	 * 将时分秒转换成毫秒值
	 * 
	 */
	public static long getHMMTime(String time) {
		String[] times = time.split(":");
		int hour = Integer.parseInt(times[0]);
		int min = Integer.parseInt(times[1]);
		int sec = Integer.parseInt(times[2]);
		long totalSec = hour * 3600 + min * 60 + sec;
		return totalSec * 1000;
	}

	public static Calendar getCalendar(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.FRANCE);
		Date date;
		try {
			date = sdf.parse(str);
			Calendar calendar = Calendar.getInstance(Locale.FRANCE);
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			// TODO Auto-generated catch blockl
			e.printStackTrace();
		}
		return null;

	}

	public static Calendar SecondToCalendar(long min) {
		Calendar cal = getCalendar(secondToStringD(min));
		return cal;
	}
	
	
	public static String SecondToDayAndWeek_two(long date) {
		Date d = new Date();
		d.setTime(date);
		String week = null;
		switch (d.getDay()) {
		case 0:
			week = "日";
			break;
		case 1:
			week = "一";
			break;
		case 2:
			week = "二";
			break;
		case 3:
			week = "三";
			break;
		case 4:
			week = "四";
			break;
		case 5:
			week = "五";
			break;
		case 6:
			week = "六";
			break;
		}
		return  "星期"+week;
	}
	
	/**
	 * 获取时间差
	 * @param context
	 * @return
	 */
	public static long timeOffset(Context context) {
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//		long timeoffer = preferences.getLong(CommonFiled.TIME_OFFER, 0);
		return 00;
	}
	
	/**
	 * 防止控件被反复点击
	 */
	public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 1000) {   
            return true;   
        }   
        lastClickTime = time;   
        return false;   
    }
	
	/**
	 * 得到夜间服务费的数量
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * 
	 */
	public static int getNightServiceFeeMinutes(long startTime,long endTime){  
        if(startTime == 0 || endTime == 0) {
        	return 0;
        }
        if(endTime - startTime > 24 * 60 * 60 * 1000) {
        	return 6 * 60; 
        }
        long seconds = 0;
        int startHour = new Date(startTime).getHours();
        int endHour = new Date(endTime).getHours();
        long nightStartTime;
        long nightEndTime;
        startTime = startTime / 1000;
        endTime = endTime / 1000;
        if(startHour < 5) {
        	nightStartTime = getDayStart(startTime) - 1 * 3600;
            nightEndTime   = nightStartTime + 6 * 3600;
        } else {
        	nightStartTime = getDayStart(startTime) + 23 * 3600;
            nightEndTime   = nightStartTime + 6 * 3600;
        }

        if(startTime <= nightStartTime && endTime <= nightStartTime) {
            seconds = 0;
        } else if(startTime <= nightStartTime && endTime >= nightEndTime) {
            seconds = nightEndTime - nightStartTime;
        } else if(startTime < nightStartTime && endTime > nightStartTime && endTime < nightEndTime) {
            seconds = endTime - nightStartTime;
        } else if(startTime > nightStartTime && endTime < nightEndTime) {
            seconds = endTime - startTime;
        } else if(startTime > nightStartTime && endTime > nightEndTime) {
            if(endHour > 23) {
                seconds = (nightEndTime - startTime) + (endTime - nightStartTime + 24 * 3600);
            } else {
                seconds = nightEndTime - startTime;
            }
        } else {
            seconds = 0;
        }
        return (int)Math.floor(seconds / 60.0);
	}
	
	/**
	 * 获取一天0点的时间戳，单位秒
	 * */
	private static long getDayStart(long time){
		Date originDate = new Date(time * 1000);
		Date zeroDate = new Date();
		zeroDate.setDate(originDate.getDate());
		zeroDate.setMonth(originDate.getMonth());
		zeroDate.setYear(originDate.getYear());
		zeroDate.setHours(0);
		zeroDate.setMinutes(0);
		zeroDate.setSeconds(0);
		long t = zeroDate.getTime();//(time + 28800) % 86400;
		return t / 1000;
	}
	
	/**
	 * 根据时长换算成分钟
	 * second 单位 s 
	 */
	public static double secondToMinute(long second) {
		return Math.floor(second*1.f / 60);
	}
	
	public static String getCurrentOrderStartTime (long time) throws Exception {
		String str = "";
		if (isSameDay(time, System.currentTimeMillis())) {
			str = "今天";
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			String hourStr = sdf.format(new Date(time));
			str = str + hourStr;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			str = sdf.format(new Date(time));
		}
		return str;
	}
	
	
	/**
	 * 根据时长换算成小时分钟
	 * second 单位 s 
	 */
	public static String secondToTimeLength(long second) {
		double minute = Math.floor(second*1.f / 60);
		double hour = Math.floor(minute / 60);
		if(hour == 0) {
			return (int) minute + "分钟";
		}else if((int) minute % 60 == 0) {
			return (int) hour + "小时";
		}else {
			return (int) hour + "小时" + (int) minute % 60 + "分钟";
		}
	}

    public static long getSystemTime(){
        return System.currentTimeMillis();
    }

    public static String secondToStringDt(long date) {
        String charDateWhole = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        charDateWhole = sdf.format(date);
        return charDateWhole;
    }
}
