package com.material.design.widget.materailactivity.dbdemo.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.material.design.widget.MaterialApplication;

/**
 * Created by chawei on 2017/2/21.
 */

public class DbHelper extends SQLiteOpenHelper{

    public static final String TABLE_NAME = "student";
    private static final  String DB_NAME = "dedemo";
    private static final  int DB_VERSION=1;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
            +"(id integer primary key autoincrement, "+"uid varchar, "+"name varchar, "+"sex varchar, "+"age integer)";

    public DbHelper() {
        super(MaterialApplication.getAppContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
