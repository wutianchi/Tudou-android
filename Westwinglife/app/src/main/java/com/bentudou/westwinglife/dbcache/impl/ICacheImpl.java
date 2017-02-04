package com.bentudou.westwinglife.dbcache.impl;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bentudou.westwinglife.dbcache.ICache;
import com.bentudou.westwinglife.dbcache.bean.Entity;
import com.bentudou.westwinglife.dbcache.bean.TimeType;
import com.bentudou.westwinglife.dbcache.db.Dao;
import com.bentudou.westwinglife.dbcache.util.DateUtil;
import com.bentudou.westwinglife.utils.VerifitionUtil;

import java.util.List;


/**
 * @author Ben
 * @version v1.2.1
 * Create date :2015/12/16
 * Desc: 缓存数据处理
 */
public class ICacheImpl implements ICache{

    private Dao dao;
    private Context context;
    private long cacheTime;
    private TimeType timeType;
    public ICacheImpl(Context context){
        this.context = context;
        dao = new Dao(context);
        timeType = TimeType.hour;//默认时间单位小时
        cacheTime = 2l;//默认时间为2小时
    }

    @Override
    public Entity getData(String url){
        if (url == null)
            throw new NullPointerException(String.format("Object is null: %s",url));
        return dao.queryDataByUrl(url);
    }

    @Override
    public void setCacheTime(long cacheTime, TimeType type) {

        if (cacheTime < 0)
            throw new IllegalArgumentException(String.format("Modulus < 0: %s",cacheTime));
        if (type == null)
        throw new NullPointerException(String.format("Object is null: %s", type));
        this.timeType = type;
        this.cacheTime = cacheTime;
    }

    @Override
    public void saveData(String url,Object obj){

        if (url == null)
            throw new NullPointerException(String.format("Object is null: %s",url));

        if (obj == null)
            throw new NullPointerException(String.format("Object is null: %s",obj));

        String data = JSON.toJSONString(obj);
        if (getData(url) != null)
            dao.updateDate(url, data, DateUtil.getCurrentDate());
        else
            dao.insert(url, data, DateUtil.getCurrentDate());
    }

    /*
     *  Check whether the effective time.
     */
    private boolean checkUseable(String lastTime){

        if (VerifitionUtil.isNetworkAvailable(context)){
            Log.d(this.getClass().getName(),String.format("已缓存%s%s，缓存有效时长为%s%s",
                    getSubTime(lastTime),timeType.getDesc(),cacheTime,timeType.getDesc()));
            if (getSubTime(lastTime) < cacheTime)
                return true;
            else
                return false;
        }
        return true;
    }

    @Override
    public String getTimeTypeText(){
        return timeType.getDesc();
    }

    @Override
    public TimeType getTimeType(){
        return this.timeType;
    }

    @Override
    public long getSubTime(String lastTime){

        if (lastTime == null)
            throw new NullPointerException(String.format("Object is null: %s",lastTime));
        long temp = DateUtil.getDate(DateUtil.getCurrentDate()).getTime() - DateUtil.getDate(lastTime).getTime();
        long day = temp / (24 * 60 * 60 * 1000);
        long hour = (temp / (60 * 60 * 1000) - day * 24);
        long min = ((temp / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long sec = (temp / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (temp - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - sec * 1000);

        return subTime(day, hour, min, sec, ms, timeType);
    }

    private long getSubTime(String lastTime, TimeType type){

        if (lastTime instanceof String) {
            long temp = DateUtil.getDate(DateUtil.getCurrentDate()).getTime() - DateUtil.getDate(lastTime).getTime();
            long day = temp / (24 * 60 * 60 * 1000);
            long hour = (temp / (60 * 60 * 1000) - day * 24);
            long min = ((temp / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (temp / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (temp - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - sec * 1000);
            return subTime(day, hour, min, sec, ms, type);
        }else {
            return 0;
        }
    }

    private long subTime(long day, long hour, long min, long sec, long ms, TimeType type){
        long subTime = 0l;
        switch (type){
            case day:
                subTime = day;
                break;
            case hour:
                subTime = hour;
                break;
            case second:
                subTime = sec;
                break;
            case millis:
                subTime = ms;
                break;
            case minute:
                subTime = min;
                break;
        }
        return subTime;
    }

    @Override
    public <T> T getObject(String data,Class<T> clazz){

        if (clazz.getName().equals(List.class.getName()))
            return JSON.toJavaObject(JSON.parseArray(data),clazz);
        else if (clazz.getName().equals(String.class.getName()))
            return (T) data;
        else
            return JSON.toJavaObject(JSON.parseObject(data), clazz);
    }

    @Override
    public boolean cacheAble(String url){

        final Entity entity = this.getData(url);
        if (entity == null){
            Log.d(this.getClass().getName(),String.format("本地数据是否可用：%s", false));
            return false;//本地没有缓存，则不可用
        }
        if (this.checkUseable(entity.getLastTime())){
            Log.d(this.getClass().getName(),String.format("本地数据是否可用：%s", true));
            return true;//如果缓存没有失效，则可以用
        } else{
            Log.d(this.getClass().getName(),String.format("本地数据是否可用：%s", false));
            return false;
        }
    }

    @Override
    public String getUpdateTimeText(String url){

        long day = this.getSubTime(this.getData(url).getLastTime(), TimeType.day);
        long hour = this.getSubTime(this.getData(url).getLastTime(), TimeType.hour);
        long minute = this.getSubTime(this.getData(url).getLastTime(), TimeType.minute);
        if (day > 0){
            String temp = DateUtil.firstToStringMD(DateUtil.getDate(this.getData(url).getLastTime()).getTime());
            String m = temp.split("-")[0];
            String d = temp.split("-")[1];
            m = m.charAt(0) == '0' ? m.replaceFirst("0", "") : m;
            d = d.charAt(0) == '0' ? d.replaceFirst("0", "") : d;
            return String.format("%s-%s 更新", m, d);
        }else {
            if (hour > 0){
                return String.format("%s%s前更新", hour, TimeType.hour.getDesc());
            }else {
                return minute == 0 ? "刚刚更新" : String.format("%s%s前更新", minute, TimeType.minute.getDesc());
            }
        }
    }
}
