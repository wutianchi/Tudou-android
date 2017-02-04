package com.bentudou.westwinglife.dbcache.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ben
 * @version v1.2.1
 * Create date :2015/12/17
 * Desc:时间工具类
 */
public class DateUtil {

    /**
     * 获取当前日期
     * @return
     */
    public static String getCurrentDate(){
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dfs.format(new Date());
    }

    public static Date getDate(String date){
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date result = null;
        try {
            result = dfs.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
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
        return new SimpleDateFormat("MM-dd HH:mm").format(date);
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
    public static String firstToStringMD(long date) {
        return new SimpleDateFormat("MM-dd").format(date);
    }
}
