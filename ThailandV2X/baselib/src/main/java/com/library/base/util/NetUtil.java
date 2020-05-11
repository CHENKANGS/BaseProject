package com.library.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*

 * Description: 判断网络

 * File: NetUtil.java

 * Author: k

 * Version: V100R001C01

 * Create: 2018/1/23 14:43

 *

 * Changes (from 2018/1/23)

 * -----------------------------------------------------------------

 * 2018/1/23 : Changes NetUtil.java (k);

 * -----------------------------------------------------------------

 */
public class NetUtil {

    /**
     * 没有连接网络
     */
    private static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    private static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;

    public static int getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }


}
