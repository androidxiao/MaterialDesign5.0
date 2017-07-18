package com.material.design.widget.materailactivity.dbdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.material.design.widget.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chawei on 2017/1/17.
 */

public abstract class DataBaseDao<T> {

    private SQLiteOpenHelper mHelper;

    public DataBaseDao(SQLiteOpenHelper helper){
        mHelper=helper;
    }

    protected final SQLiteDatabase openReader(){
        return mHelper.getReadableDatabase();
    }

    protected final SQLiteDatabase openWriter(){
        return mHelper.getWritableDatabase();
    }

    protected final void closeDatabase(SQLiteDatabase database, Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    /**
     * 获取对应的表名
     */
    protected abstract String getTableName();

    /**
     * 根据数据库中有个 _id 的字段
     */
    public int count(){
        return countColumn("_id");
    }

    /**
     * 返回一列的总记录数量
     */
    public int countColumn(String columnName) {
        String sql="SELECT COUNT(?) FROM "+getTableName();
        SQLiteDatabase database=openReader();
        Cursor cursor=null;
        try {
            database.beginTransaction();
            cursor = database.rawQuery(sql, new String[]{columnName});
            int count = 0;
            if (cursor.moveToNext()) {
                count = cursor.getInt(0);
            }
            database.setTransactionSuccessful();
            return count;
        } catch (Exception e) {
            LogUtil.e(e);
        }finally {
            database.endTransaction();
            closeDatabase(database,cursor);
        }
        return 0;
    }

    /**
     * 删除所有数据
     */
    public int deleteAll(){
        return delete(null,null);
    }

    /**
     * 根据条件删除数据库中的数据
     */
    public int delete(String whereClause, String[] whereArgs) {
        SQLiteDatabase database=openWriter();
        try{
            database.beginTransaction();
            int result = database.delete(getTableName(), whereClause, whereArgs);
            database.setTransactionSuccessful();
            return result;
        }catch (Exception e){
            LogUtil.e(e);
        }finally {
            database.endTransaction();
            closeDatabase(database, null);
        }
        return 0;
    }

    /**
     * 查询并返回所有对象的集合
     */
    public List<T> getAll(){
        return get(null, null);
    }

    /**
     * 按条件查询对象并返回集合
     */
    public List<T> get(String selection, String[] selectionArgs) {
        return get(null, selection, selectionArgs, null, null,null,null);
    }

    /**
     * 按条件查询对象并返回集合
     */
    public List<T> get(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase database=openReader();
        List<T> list = new ArrayList<>();
        Cursor cursor=null;
        try {
            database.beginTransaction();
            cursor = database.query(getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            while (!cursor.isClosed() && cursor.moveToNext()) {
                list.add(parseCursorToBean(cursor));
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.e(e);
        }finally {
            database.endTransaction();
            closeDatabase(database,cursor);
        }
        return list;
    }

    /**
     * replace 语句有如下行为特点
     * 1. replace语句会删除原有的一条记录， 并且插入一条新的记录来替换原记录。
     * 2. 一般用replace语句替换一条记录的所有列， 如果在replace语句中没有指定某列， 在replace之后这列的值被置空 。
     * 3. replace语句根据主键的值确定被替换的是哪一条记录
     * 4. 如果执行replace语句时， 不存在要替换的记录， 那么就会插入一条新的记录。
     * 5. replace语句不能根据where子句来定位要被替换的记录（如果使用会报异常：Error: near "where": syntax error ）
     * 6. 如果新插入的或替换的记录中， 有字段和表中的其他记录冲突， 那么会删除那条其他记录。
     * <p>
     * replace语句和update语句的对比
     * 1、update语句使用where子句定位被更新的记录；
     * 2、update语句可以一次更新一条记录， 也可以更新多条记录， 只要这多条记录都复合where子句的要求；
     * 3、update只会在原记录上更新字段的值， 不会删除原有记录， 然后再插入新纪录；
     * 4、如果在update语句中没有指定一些字段， 那么这些字段维持原有的值， 而不会被置空；
     */
    public long update(T t) {
        SQLiteDatabase database=openWriter();
        try {
            database.beginTransaction();
            long id = database.update(getTableName(), getContentValues(t),null,null);

            database.setTransactionSuccessful();
            return id;
        } catch (Exception e) {
            LogUtil.debug("updataeeeeeeeeeeeeeeeeeeeeeeeeee");
            LogUtil.e(e);
        }finally {
            database.endTransaction();
            closeDatabase(database, null);
        }
        return 0;
    }

    /**
     * 创建一个记录
     */
    public long create(T t) {
        SQLiteDatabase database=openWriter();
        try {
            database.beginTransaction();
            long id = database.insert(getTableName(), null, getContentValues(t));
            database.setTransactionSuccessful();
            return id;
        } catch (Exception e) {
            LogUtil.e(e);
        }finally {
            database.endTransaction();
            closeDatabase(database, null);
        }
        return 0;
    }

    /**
     * 更新一条记录
     */
    public int update(T t, String whereClause, String[] whereArgs) {
        SQLiteDatabase database=openWriter();
        try {
            database.beginTransaction();
            int count = database.update(getTableName(), getContentValues(t), whereClause, whereArgs);
            return count;
        } catch (Exception e) {
            LogUtil.e(e);
        }finally {
            database.endTransaction();
            closeDatabase(database, null);
        }
        return 0;
    }

    /**
     * 将Cursor解析对应的JavaBean
     */
    public abstract T parseCursorToBean(Cursor cursor);

    /**
     * 需要替换的列
     */
    public abstract ContentValues getContentValues(T t);
}
