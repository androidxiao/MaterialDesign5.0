package com.material.design.widget.materailactivity.dbdemo.db;

/**
 * Created by chawei on 2017/1/17.
 */

public enum CacheMode {

    /**
     * 不使用缓存
     */
    NO_CACHE,

    /**
     * 请求网络失败后，读取缓存
     */
    REQUEST_FAILED_READ_CACHE,

    /**
     * 如果缓存不存在才请求网络，否则使用缓存
     */
    IF_NONE_CACHE_REQUEST,

    /**
     * 先使用缓存，不管是否存在，仍然请求网络
     */
    FIRST_CACHE_THEN_REQUEST,
}
