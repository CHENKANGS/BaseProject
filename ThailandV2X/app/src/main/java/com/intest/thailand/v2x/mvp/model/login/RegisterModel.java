package com.intest.thailand.v2x.mvp.model.login;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.nets.HttpResultFunc;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterModel {

    private static RegisterModel INSTANCE;

    private RegisterModel() {
    }

    public static synchronized RegisterModel getInstance() {
        if (INSTANCE == null) {
            synchronized (RegisterModel.class) {
                INSTANCE = new RegisterModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 注册
     * @return
     */
    public rx.Observable<TestBean> register(String userName , String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);

        return BaseApplication.getRetrofitClient().getChinaJesApi().login(params)
                .map(new HttpResultFunc<TestBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
