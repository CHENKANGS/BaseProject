package com.library.base.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.library.base.R;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.nets.InitRetrofit;

/**
 * Created by ChenKang on 2017/11/14.
 */

public class BaseApplication extends Application {

    static BaseApplication instance;
    public final boolean isDebug = false;// 是否为调试模式

    private static InitRetrofit sInitRetrofit;

    public BaseApplication() {
        instance = this;
    }

    public static synchronized BaseApplication getInstance() {
        return instance;
    }
    public static Context get() {
        return instance.getApplicationContext();
    }

    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {


    }

    public synchronized static InitRetrofit getRetrofitClient() {
        if (sInitRetrofit == null) {
            sInitRetrofit = new InitRetrofit();
        }
        return sInitRetrofit;
    }

    /**
     * 重建Retrofit
     */
    public synchronized static void resetRetrofitClient() {
        sInitRetrofit = new InitRetrofit();
    }

    /**
     * 跳转登录页
     */
    public void jumpLoginActivity() {
        Intent intent = new Intent(GlobalConstant.LOGON_ACTION);
        sendBroadcast(intent);
    }

    /**
     * 加密秘钥
     * @return
     */
    public String getDESKey() {
        return getString(R.string.DES_KEY);
    }



}
