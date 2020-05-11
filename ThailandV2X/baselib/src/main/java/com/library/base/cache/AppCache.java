package com.library.base.cache;

import android.content.Context;
import android.text.TextUtils;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.UserBean;

/*

 * Description: 此项目使用缓存

 * File: AppCache.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/11/28 16:40

 *

 * Changes (from 2017/11/28)

 * -----------------------------------------------------------------

 * 2017/11/28 : Changes AppCache.java (k);

 * -----------------------------------------------------------------

 */
public class AppCache {
    private static AppCache appCache;
    private Context context ;

    private final String USER_MODEL = "USER_MODEL";

    private String Str;
    private UserBean userBean;
    SPHelper spfHelper ;

    private AppCache(Context context) {
        this.context = context ;
//        spfHelper = new SPHelper(context, SPHelper.SP_NAME);
    }

    public static synchronized AppCache getInstance() {
        if (appCache == null) {
            appCache = new AppCache(BaseApplication.getInstance());
        }
        return appCache;
    }

    /**
     * 获取String
     * @return
     */
    public String getStr(String key ) {
        if (!TextUtils.isEmpty(key)) {
            return SPHelper.getStringSF(context, key);
        }
        return "";
    }

    /**
     * 设置String
     * @param Str
     */
    public void setStr(String key ,String Str) {
        SPHelper.setStringSF(context , key , Str);
    }

    /**
     * 设置对象
     * @param key
     * @param model
     * @param <T>
     * @return
     */
    public <T> boolean setObjectDevice(String key, T model) {
        return SPHelper.setDeviceData(context , key , model);
    }

    /**
     * 获取对象
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getObjectDevice(String key) {
        return SPHelper.getDeviceData(context, key);
    }

}
