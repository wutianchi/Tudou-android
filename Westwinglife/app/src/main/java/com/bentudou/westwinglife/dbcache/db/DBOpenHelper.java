package com.bentudou.westwinglife.dbcache.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBOpenHelper extends SQLiteOpenHelper {
   private static final String DBNAME = "gunlei.db";
   private static final int VERSION = 1;
    
   public DBOpenHelper(Context context) {
       super(context, DBNAME, null, VERSION);
   }
    
   @Override
   public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE IF NOT EXISTS tb_data( " +
               "URL VARCHAR(1024) primary key, " +
               "DATA VARCHAR(8192), " +
               "LAST_TIME VARCHAR(128))");
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS tb_data");
       onCreate(db);
   }

}
