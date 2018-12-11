package com.neuedu.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {


    /**
     * 获取缓存对象 LoadingCache
     */
    private  static LoadingCache<String,String> loadingCache=CacheBuilder.newBuilder()
            .initialCapacity(1000)//设置缓存像
            .maximumSize(10000)//设置最大缓存项
            .expireAfterAccess(12,TimeUnit.HOURS)//设置缓存时间
            .build(new CacheLoader<String, String>() {
                //当key不存在是，调用该方法
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    /**
     *
     */
    public static  void put(String key,String value){
        loadingCache.put(key,value);
    }

    /**
     * 像缓存添加键值对
     */
    public static String get(String key){
        try {
            String value=loadingCache.get(key);
            if (value.equals("null")){
                return null;
            }
            return value;
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return null;
    }
}
