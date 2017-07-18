package com.material.design.widget.materailactivity.dbdemo.db;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

/**
 * Created by chawei on 2017/1/19.
 */

public class CacheDao<T> extends DataBaseDao<CacheEntity<T>> {

    public CacheDao() {
        super(new CacheHelper());
    }

    /**
     *根据key获取缓存
     * @param key
     * @return
     */
    public CacheEntity<T> get(String key) {
        String selection = CacheHelper.KEY + "=?";
        String[] selectionArgs = new String[]{key};
        List<CacheEntity<T>> cacheEntities = get(selection, selectionArgs);
        return cacheEntities.size() > 0 ? cacheEntities.get(0):null;
    }

    /**
     * 移除一个缓存
     * @param key
     * @return
     */
    public boolean remove(String key) {
        String whereClause = CacheHelper.KEY + "=?";
        String[] whereArgs = new String[]{key};
        int delete = delete(whereClause, whereArgs);
        return delete>0;
    }

    @Override

    protected String getTableName() {
        return CacheHelper.TABLE_NAME;
    }

    @Override
    public CacheEntity<T> parseCursorToBean(Cursor cursor) {
        return CacheEntity.parseCursorToBean(cursor);
    }

    @Override
    public ContentValues getContentValues(CacheEntity<T> cacheEntity) {
        return CacheEntity.getContentValues(cacheEntity);
    }

}
