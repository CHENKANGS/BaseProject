package com.intest.thailand.v2x.mvp.model.login;

import com.intest.thailand.v2x.mvp.model.MainModel;
import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.nets.HttpResultFunc;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginModel {

    private static LoginModel INSTANCE;

    private LoginModel() {
    }

    public static synchronized LoginModel getInstance() {
        if (INSTANCE == null) {
            synchronized (LoginModel.class) {
                INSTANCE = new LoginModel();
            }
        }
        return INSTANCE;
    }


    /**
     * 登录
     * @return
     */
    public rx.Observable<TestBean> login(String userName , String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);

        return BaseApplication.getRetrofitClient().getChinaJesApi().login(params)
                .map(new HttpResultFunc<TestBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
