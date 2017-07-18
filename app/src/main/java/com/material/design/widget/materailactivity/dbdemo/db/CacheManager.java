package com.material.design.widget.materailactivity.dbdemo.db;


import com.material.design.widget.utils.LogUtil;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by chawei on 2017/1/19.
 */

public enum  CacheManager {

    INSTANCE;

    private Lock mLock;
    private CacheDao<Object> mCacheDao;

    CacheManager(){
        mLock = new ReentrantLock();
        mCacheDao = new CacheDao<>();
    }

    /**
     * 获取缓存
     * @param key 缓存的key
     * @return 缓存的对象实体
     */
    public CacheEntity<Object> get(String key) {
        mLock.lock();
        try{
            return mCacheDao.get(key);
        }finally {
            mLock.unlock();
        }
    }

    /**
     * 返回带泛型的对象，注意必须确保泛型和对象对应才不会发生类型转换异常
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> CacheEntity<T> get(String key, Class<T> clazz) {
        return (CacheEntity<T>) get(key);
    }

    /**
     * 获取所有缓存
     * @return 缓存的对象实体
     */
    public List<CacheEntity<Object>> getAll(){
        mLock.lock();
        try {
            return mCacheDao.getAll();
        }finally {
            mLock.unlock();
        }
    }

    @SuppressWarnings("unchecked")
    public long update(String key, CacheEntity<Object> entity) {
        mLock.lock();
        try{
            entity.setKey(key);
            long count=mCacheDao.update(entity);
            LogUtil.debug("update--->"+count);
            return count;
        }finally {
            mLock.unlock();
        }
    }

    /**
     * 移除缓存
     * @param key 缓存的key
     * @return 是否移除成功
     */
    public boolean remove(String key) {
        if (key == null) {
            return true;
        }
        mLock.lock();
        try{
            return mCacheDao.remove(key);
        }finally {
            mLock.unlock();
        }
    }

    public int count(){
        mLock.lock();
        try{
            return mCacheDao.count();
        }finally {
            mLock.unlock();
        }
    }

    /**
     * 清除缓存
     * @return 是否清空成功
     */
    public boolean clear(){
        mLock.lock();
        try {
            return mCacheDao.deleteAll()>0;
        }finally {
            mLock.unlock();
        }
    }

}
