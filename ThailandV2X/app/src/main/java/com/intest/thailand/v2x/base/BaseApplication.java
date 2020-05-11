package com.intest.thailand.v2x.base;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.intest.thailand.v2x.util.AppLanguageUtils;
import com.library.base.cache.AppCache;
import com.library.base.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by ChenKang on 2017/11/14.
 */

public class BaseApplication extends com.library.base.base.BaseApplication {

    static BaseApplication instance;
    public final boolean isDebug = false;// 是否为调试模式

    public BaseApplication() {
        instance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    public static Context getApplication() {
        return instance.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        closeAndroidPDialog();
        init();
    }


    private void init() {

    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
