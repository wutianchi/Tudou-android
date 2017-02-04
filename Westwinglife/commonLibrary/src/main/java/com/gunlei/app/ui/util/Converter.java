package com.gunlei.app.ui.util;

import java.text.NumberFormat;

/**
 * 和数字时间有关的显示格式转换
 * @author liuchangjiong
 * @since Nov 17, 2013
 *
 */
public class Converter {

	public static final long K = 1024L;
	public static final long M = 0x100000L;
	public static final long G = 0x40000000L;
	public static final long T = 0x10000000000L;

    // ============= 以下为十进制的时间显示 ================
    public static final long KT = 1000L;
    public static final long MT = KT * 1000L;
    public static final long GT = MT * 1000L;
    public static final long TT = GT * 1000L;

	private static NumberFormat nf;
	private static NumberFormat nfLong;

	private Converter() {
	}
	
//	public static void main(String[] args) {
//		System.out.println(Converter.longToKMGString(19659421837015L));
//	}

	public static String longToKMGString(Number number) {
		return longToKMGString(number.longValue());
	}
	
	public static String formatLong(Number number) {
		return nfLong.format(number);
	}

    /**
     * 米转换为公里数。
     * @param l
     * @return
     */
    public static int longToTenPowerKM(long l) {
        return Math.round( l / 1000 );
    }
    /**
     * 转换为10进制的数字格式化, 千, 兆, G etc.
     * @param l
     * @return
     */
    public static String longToTenPowerKMGString(long l) {
        float f = l;
        float main = f / TT;
        if ((long) main > 0L)
            return nf.format(main) + "T";
        main = f / GT;
        if ((long) main > 0L)
            return nf.format(main) + "G";
        main = f / MT;
        if ((long) main > 0L)
            return nf.format(main) + "千公里";
        main = f / KT;
        if ((long) main > 0L)
            return nf.format(main) + "公里";
        else
            return String.valueOf(l) + "米";
//            return "小于1公里";
    }


	/**
	 * 转换为1024进制的内存格式化, KB, MB, GB etc.
	 * @param l
	 * @return
	 */
	public static String longToKMGString(long l) {
		float f = l;
		float main = f / T;
		if ((long) main > 0L)
			return nf.format(main) + "T";
		main = f / G;
		if ((long) main > 0L)
			return nf.format(main) + "G";
		main = f / M;
		if ((long) main > 0L)
			return nf.format(main) + "M";
		main = f / K;
		if ((long) main > 0L)
			return nf.format(main) + "K";
		else
			return String.valueOf(l);
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
     * 时间转化为分钟字符串。
     * @param seconds 秒数
     * @return
     */
    public static long longToMinutesString(long seconds) {
        LogUtils.e("seconds:" + seconds);
        return Math.round( seconds / 60.0 );
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
			stringbuffer.append(l2).append("天");
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
			stringbuffer.append(l2).append("分钟");
			flag = true;
			if (l2 > 0L)
				d10 = d9;
		} else {
            stringbuffer.append("1分钟");
        }
//		d7 = d10 / d;
//		l2 = (long) d7;
//		d9 = d10 - (double) l2 * d;
//		if (l2 > 0L || flag) {
//			stringbuffer.append(nf.format(d7)).append("秒 ");
//			flag = true;
//			double d11;
//			if (l2 > 0L)
//				d11 = d9;
//		}
//		if (flag)
			return stringbuffer.toString();
//		d7 = (double) time / d3;
//		if (d7 >= 1.0D)
//			return nf.format(d7) + "毫秒";//ms
//		d7 = (double) time / d2;
//		if (d7 >= 1.0D) {
//			return nf.format(d7) + "微秒";//us
//		} else {
//			double d8 = (double) time / d1;
//			return nf.format(d8) + "纳秒";//ns
//		}
	}

	static {
		nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setMinimumFractionDigits(0);
		nf.setMinimumIntegerDigits(1);
		
		nfLong = NumberFormat.getNumberInstance();		
	}
}