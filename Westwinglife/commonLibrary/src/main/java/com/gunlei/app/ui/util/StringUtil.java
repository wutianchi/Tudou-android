package com.gunlei.app.ui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 字符串工具。
 * Created by liuchangjiong on 14/12/15.
 */
public class StringUtil {
    private static String timeZoneID = "GMT+8";

    /**
     * 判断字符串是否未空, 如果为 null 或者长度为0, 均返回 true.
     */
    public static boolean isEmpty(String input) {
        return (input == null || input.length() == 0);
    }

    /**
     * 格式化小数保持最少和最多小数点.
     *
     * @param num
     * @param minFractionDigits
     * @param maxFractionDigits
     * @return
     */
    public static String formatFraction(double num, int minFractionDigits, int maxFractionDigits) {
        // 输出固定小数点位数
        java.text.NumberFormat nb = java.text.NumberFormat.getInstance();
        nb.setMaximumFractionDigits(maxFractionDigits);
        nb.setMinimumFractionDigits(minFractionDigits);
        nb.setGroupingUsed(false);
        String rate = nb.format(num);

        return rate;
    }

    /**
     * 解析日期.
     * @param input 输入字符串
     * @param pattern 类型
     * @return Date 对象
     */
    public static Date parseDate(String input, String pattern) {
        if(isEmpty(input)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(pattern);

        try {
            return df.parse(input);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 格式化日期为字符串.
     * @param date 日期字符串
     * @param pattern 类型
     * @return 结果字符串
     */
    public static String formatDate(Date date, String pattern) {
        if(date == null || pattern == null) { return null; }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 格式化PHP日期为字符串.
     * 用法：StringUtil.formatPHPTime(
     1419507574, "MM月dd日 HH:mm")
     * ⚠实现注意事项：如果是int，则需要*1000L，否则会数值溢出。
     * @param date PHP 时间戳
     * @param pattern 类型
     * @return 结果字符串
     */
    public static String formatPHPTime(long date, String pattern) {
        if(date == 0 || pattern == null) { return null; }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneID));
        return sdf.format(new Date(date * 1000L));
    }

    /**
     * 将字符串 source 中的 oldStr 替换为 newStr, 并以大小写敏感方式进行查找
     *
     * @param source
     *            需要替换的源字符串
     * @param oldStr
     *            需要被替换的老字符串
     * @param newStr
     *            替换为的新字符串
     */
    public static String replace(String source, String oldStr, String newStr) {
        return replace(source, oldStr, newStr, true);
    }

    /**
     * 将字符串 source 中的 oldStr 替换为 newStr, matchCase 为是否设置大小写敏感查找
     *
     * @param source
     *            需要替换的源字符串
     * @param oldStr
     *            需要被替换的老字符串
     * @param newStr
     *            替换为的新字符串
     * @param matchCase
     *            是否需要按照大小写敏感方式查找
     */
    public static String replace(String source, String oldStr, String newStr,
                                 boolean matchCase) {
        if (source == null) {
            return null;
        }
        // 首先检查旧字符串是否存在, 不存在就不进行替换
        if (source.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
            return source;
        }
        int findStartPos = 0;
        int a = 0;
        while (a > -1) {
            int b = 0;
            String str1, str2, str3, str4, strA, strB;
            str1 = source;
            str2 = str1.toLowerCase();
            str3 = oldStr;
            str4 = str3.toLowerCase();
            if (matchCase) {
                strA = str1;
                strB = str3;
            } else {
                strA = str2;
                strB = str4;
            }
            a = strA.indexOf(strB, findStartPos);
            if (a > -1) {
                b = oldStr.length();
                findStartPos = a + b;
                StringBuffer bbuf = new StringBuffer(source);
                source = bbuf.replace(a, a + b, newStr) + "";
                // 新的查找开始点位于替换后的字符串的结尾
                findStartPos = findStartPos + newStr.length() - b;
            }
        }
        return source;
    }
}
