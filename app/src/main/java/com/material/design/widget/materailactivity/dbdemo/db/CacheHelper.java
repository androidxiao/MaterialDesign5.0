package com.material.design.widget.materailactivity.dbdemo.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.material.design.widget.MaterialApplication;
import com.material.design.widget.utils.LogUtil;


/**
 * Created by chawei on 2017/1/18.
 */

class CacheHelper extends SQLiteOpenHelper {

    public static final String DB_CACHE_NAME = "todaynews_cache.db";
    public static final int DB_CACHE_VERSION=1;
    public static final String TABLE_NAME = "cache_table";

    //表中的五个字段
    public static final String ID = "_id";
    public static final String KEY = "key";
    public static final String LOCAL_EXPIRE = "localExpire";
    public static final String DATA = "data";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY + " VARCHAR, " + LOCAL_EXPIRE + " INTEGER, " + DATA + " BLOB)";


    private static final String SQL_DELETE_TABLE="DROP TABLE "+TABLE_NAME;

    public CacheHelper() {
        super(MaterialApplication.getAppContext(), DB_CACHE_NAME, null, DB_CACHE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try{
            db.execSQL(SQL_CREATE_TABLE);
            db.setTransactionSuccessful();
        } catch (Exception e){
            LogUtil.e(e);
        }finally {
            db.endTransaction();
        }
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.beginTransaction();
            try {
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_TABLE);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                LogUtil.e(e);
            }finally {
                db.endTransaction();
            }
        }
    }

    /**
     * 数据库升级
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       onUpgrade(db, oldVersion, newVersion);
    }
}
