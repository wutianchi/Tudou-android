package com.bentudou.westwinglife.dbcache;

import com.bentudou.westwinglife.dbcache.bean.Entity;
import com.bentudou.westwinglife.dbcache.bean.TimeType;
import com.bentudou.westwinglife.dbcache.impl.ICacheImpl;

/**
 * @author Ben
 * @version v1.2.1
 * Create date :2015/12/16
 */
public interface ICache {

    /**
     * Local data is available for inspection.
     * @param url Save data to key.
     * @return Return to the <code>true</code> table is available, otherwise not available.
     * Or if there is no network, directly will return <code>true</code>.
     * @see ICacheImpl#cacheAble(String)
     */
    public boolean cacheAble(String url);

    /**
     * To get the Java object through the transformation of the preservation of JsonString.
     * @param data The JsonString stored in local database.
     * @param clazz The type of javaObject.
     * @param <T>
     * @return javaObject
     * @see ICacheImpl#getObject(String, Class)
     */
    public <T> T getObject(String data, Class<T> clazz);

    /**
     * Save the server data to database
     * @param url the key
     * @param obj the value
     * @throws NullPointerException if {@code url} or {@code obj} is null.
     * @see ICacheImpl#saveData(String, Object)
     */
    public void saveData(String url, Object obj);

    /**
     * Return a {@link Entity} is a ORM object,the object save the cache data info.
     * @param url the key
     * @throws NullPointerException if {@code url} is null.
     * @return {@link Entity} is a ORM object
     * @see ICacheImpl#getData(String)
     */
    public Entity getData(String url);

    /**
     * Check whether the cached data is available.
     * @param cacheTime The cacheTime is used to remember the data retention time.
     * @param type The type is a time's units.
     * @throws IllegalArgumentException if {@code cacheTime} is less than 0
     * @throws NullPointerException if {@link TimeType type} is a null object
     * @see ICacheImpl#setCacheTime(long, TimeType)
     */
    public void setCacheTime(long cacheTime, TimeType type);

    /**
     * Can get a time interval of the cache data's.
     * @param lastTime The starting time saved.
     * @throws NullPointerException if {@code lastTime} is null.
     * @return args {@code lastTime} and the current time interval.
     * @see ICacheImpl#getSubTime(String)
     */
    public long getSubTime(String lastTime);

    /**
     * Ruturn a unit of time text.
     * @return {@link TimeType#desc}
     * @see ICacheImpl#getTimeTypeText()
     */
    public String getTimeTypeText();

    /**
     * Ruturn a unit of time.
     * @return {@link TimeType}
     * @see ICacheImpl#getTimeType()
     */
    public TimeType getTimeType();

    /**
     * Return text update time
     * @return
     * @see ICacheImpl#getUpdateTimeText(String)
     */
    public String getUpdateTimeText(String url);
}
