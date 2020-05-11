package com.intest.thailand.v2x.mvp.model.login;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.nets.HttpResultFunc;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgotPsdModel {

    private static ForgotPsdModel INSTANCE;

    private ForgotPsdModel() {
    }

    public static synchronized ForgotPsdModel getInstance() {
        if (INSTANCE == null) {
            synchronized (ForgotPsdModel.class) {
                INSTANCE = new ForgotPsdModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 验证手机号
     * @return
     */
    public rx.Observable<TestBean> forgotPsds(String userName , String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);

        return BaseApplication.getRetrofitClient().getChinaJesApi().login(params)
                .map(new HttpResultFunc<TestBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
