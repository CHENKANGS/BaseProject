package com.intest.thailand.v2x.mvp.model;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.nets.HttpResultFunc;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/*

 * Description: 

 * File: MainModel.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/12/6 11:00

 *

 * Changes (from 2017/12/6)

 * -----------------------------------------------------------------

 * 2017/12/6 : Changes MainModel.java (k);

 * -----------------------------------------------------------------

 */
public class MainModel {

    private static MainModel INSTANCE;

    private MainModel() {
    }

    public static synchronized MainModel getInstance() {
        if (INSTANCE == null) {
            synchronized (MainModel.class) {
                INSTANCE = new MainModel();
            }
        }
        return INSTANCE;
    }


    /**
     * 测试样例
     * @return
     */
    public rx.Observable<TestBean> getNumber() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", "111");

        return BaseApplication.getRetrofitClient().getChinaJesApi().getTest(params)
                .map(new HttpResultFunc<TestBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }




}
