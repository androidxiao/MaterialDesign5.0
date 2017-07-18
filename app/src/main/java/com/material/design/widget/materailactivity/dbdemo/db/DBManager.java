package com.material.design.widget.materailactivity.dbdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.material.design.widget.materailactivity.dbdemo.model.Student;
import com.material.design.widget.utils.LogUtil;

/**
 * Created by chawei on 2017/2/21.
 */

public class DBManager {

    private DbHelper mHelper;
    private static DBManager mManager;
    private final SQLiteDatabase mDb;

    private DBManager() {
        if (mHelper == null) {
            mHelper = new DbHelper();
        }
        mDb = mHelper.getWritableDatabase();
    }

    public static final DBManager getDBManager() {

        if (mManager == null) {
            mManager = new DBManager();
        }

        return mManager;
    }

    public void insertData(Student student) {
        ContentValues values = new ContentValues();
        values.put("uid", student.getUid());
        values.put("name", student.getName());
        LogUtil.debug("11111------>"+student.getName());
        values.put("sex", student.getSex());
        values.put("age", student.getAge());
        mDb.insert(DbHelper.TABLE_NAME, null, values);
    }


    public Student queryData(String queryId) {
        Cursor cursor = mDb.query(DbHelper.TABLE_NAME, null, "uid=?", new String[]{queryId}, null, null, null);
        if (cursor != null) {
            Student student = new Student();
            while (cursor.moveToNext()) {
                student.setUid(cursor.getColumnName(cursor.getColumnIndex("uid")));
                student.setName(cursor.getString(cursor.getColumnIndex("name")));
                LogUtil.debug("2222222------->"+cursor.getString(cursor.getColumnIndex("name")));
                ;
                student.setSex(cursor.getColumnName(cursor.getColumnIndex("sex")));
                student.setAge(cursor.getColumnName(cursor.getColumnIndex("age")));
            }
            return student;
        }
        return null;
    }

    public void deleteData(){
        mDb.delete(DbHelper.TABLE_NAME, null, null);
    }

}
