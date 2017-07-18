package com.material.design.widget;

import android.app.Application;

import com.material.design.widget.materailactivity.dbdemo.db.CacheManager;

/**
 * Created by chawei on 2017/2/21.
 */

public class MaterialApplication extends Application {

    private static   MaterialApplication mContext;
    private CacheManager mCacheManager;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext=this;

        mCacheManager = CacheManager.INSTANCE;
    }

    public CacheManager getCacheManager(){
        return mCacheManager;
    }


    public static MaterialApplication getAppContext(){
        return mContext;
    }
}
