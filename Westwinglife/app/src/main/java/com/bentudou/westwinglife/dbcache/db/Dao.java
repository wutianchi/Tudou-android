package com.bentudou.westwinglife.dbcache.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bentudou.westwinglife.dbcache.bean.Entity;

/**
 *
 */
public class Dao {

    private DBOpenHelper helper;

    public Dao(Context context){
        helper = new DBOpenHelper(context);
    }

    /**
     * @param url 数据库中的主键，请使用http请求的url
     * @param data 目标结果数据，使用json字符串
     * @param lastTime 记录时效的时间戳
     */
    public void insert(String url, String data, String lastTime){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO tb_data (URL,DATA,LAST_TIME) VALUES(?,?,?)",
                new String[]{ url, data, lastTime });
            db.setTransactionSuccessful();
            Log.d(Dao.class.getName(), String.format("%s data insert success", url));
        } catch (SQLException e) {
            Log.d(Dao.class.getName(), String.format("%s data insert failure", url));
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

    }

    /**
     * @param url http请求的url
     * @return 返回保存数据类型结果
     */
    public Entity queryDataByUrl(String url){

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tb_data WHERE URL=?", new String[]{url});
        if (cursor.moveToFirst()){
            Entity entity = new Entity();
            entity.setUrl(cursor.getString(cursor.getColumnIndex("URL")));
            entity.setData(cursor.getString(cursor.getColumnIndex("DATA")));
            entity.setLastTime(cursor.getString(cursor.getColumnIndex("LAST_TIME")));
            cursor.close();
            db.close();
            return  entity;
        }else {
            cursor.close();//关闭游标
            db.close();
            return null;
        }
    }

    /**
     * @param url 数据库中的主键，请使用http请求的url
     * @param data 目标结果数据，使用json字符串
     * @param lastTime 记录时效的时间戳
     */
    public void updateDate(String url, String data, String lastTime){

        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("UPDATE tb_data SET DATA=?,LAST_TIME=? WHERE URL=?",
                    new String[]{ data, lastTime, url });
            db.setTransactionSuccessful();
            Log.d(Dao.class.getName(), String.format("%s data update success", url));
        } catch (SQLException e) {
            Log.d(Dao.class.getName(), String.format("%s data update failure", url));
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
