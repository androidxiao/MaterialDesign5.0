package com.material.design.widget.materailactivity.dbdemo.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.material.design.widget.utils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by chawei on 2017/1/18.
 */

public class CacheEntity<T> implements Serializable {

        public static final long CACHE_NEVER_EXPIRE = -1;//缓存永不过期

        private long id;
        private String key;
        private T data;
        private long localExpire;

        //该变量不必保存到数据库，程序运行起来会动态计算
        private boolean isExpire;


        public static long getCacheNeverExpire() {
            return CACHE_NEVER_EXPIRE;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public long getLocalExpire() {
            return localExpire;
        }

        public void setLocalExpire(long localExpire) {
            this.localExpire = localExpire;
        }

        public boolean isExpire() {
            return isExpire;
        }

        public void setExpire(boolean expire) {
            isExpire = expire;
        }

        public boolean checkExpire(CacheMode cacheMode, long cacheTime, long baseTime) {
            if (cacheTime == CACHE_NEVER_EXPIRE) {
                return false;
            }

            return getLocalExpire() + cacheTime < baseTime;
        }

        public static <T> ContentValues getContentValues(CacheEntity<T> cacheEntity) {
            ContentValues values = new ContentValues();
            values.put(CacheHelper.KEY, cacheEntity.getKey());
            values.put(CacheHelper.LOCAL_EXPIRE, cacheEntity.getLocalExpire());

            T data = cacheEntity.getData();
            LogUtil.debug("getContentVaules------>"+cacheEntity.getData());
//            LogUtil.debug("getContentVaules------>"+((BaseModel)data).getShowapi_res_code());
            ByteArrayOutputStream dataBAOS = null;
            ObjectOutputStream dataOOS = null;
            try {
                if (data != null) {
                    LogUtil.debug("data 不为空");
                    dataBAOS = new ByteArrayOutputStream();
                    dataOOS = new ObjectOutputStream(dataBAOS);
                    dataOOS.writeObject(data);
                    dataOOS.flush();
                    byte[] dataData = dataBAOS.toByteArray();
                    values.put(CacheHelper.DATA, dataData);
                }
            } catch (IOException e) {
                LogUtil.debug("getContentVaules------>");
                LogUtil.e(e);
            } finally {
                try {
                    if (dataOOS != null) {
                        dataOOS.close();
                    }

                    if (dataBAOS != null) {
                        dataBAOS.close();
                    }
                } catch (IOException e) {
                    LogUtil.e(e);
                }
            }
            return values;
        }

        public static <T> CacheEntity<T> parseCursorToBean(Cursor cursor) {
            CacheEntity<T> cacheEntity = new CacheEntity<>();
            cacheEntity.setId(cursor.getInt(cursor.getColumnIndex(CacheHelper.ID)));
            cacheEntity.setKey(cursor.getString(cursor.getColumnIndex(CacheHelper.KEY)));
            cacheEntity.setLocalExpire(cursor.getLong(cursor.getColumnIndex(CacheHelper.LOCAL_EXPIRE)));

            byte[] dataData = cursor.getBlob(cursor.getColumnIndex(CacheHelper.DATA));
            ByteArrayInputStream dataBAIS = null;
            ObjectInputStream dataOIS = null;
            try {
                if (dataData != null) {
                    dataBAIS = new ByteArrayInputStream(dataData);
                    dataOIS = new ObjectInputStream(dataBAIS);
                    T data = (T) dataOIS.readObject();
                    cacheEntity.setData(data);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            } finally {
                try {
                    if (dataOIS != null) {
                        dataOIS.close();
                    }

                    if (dataBAIS != null) {
                        dataBAIS.close();
                    }
                } catch (IOException e) {
                    LogUtil.e(e);
                }
            }
            return cacheEntity;
        }


        @Override
        public String toString() {
            return "CacheEntity{" +
                    "id=" + id +
                    ", key='" + key + '\'' +
                    ", data=" + data +
                    ", localExpire=" + localExpire +
                    ", isExpire=" + isExpire +
                    '}';
        }
}
