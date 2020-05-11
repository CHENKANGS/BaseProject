package com.intest.thailand.v2x.mvp.model.login;

import com.library.base.base.BaseApplication;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.nets.HttpResultFunc;

import java.util.HashMap;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VerifyCodeModel {
    private static VerifyCodeModel INSTANCE;

    private VerifyCodeModel() {
    }

    public static synchronized VerifyCodeModel getInstance() {
        if (INSTANCE == null) {
            synchronized (VerifyCodeModel.class) {
                INSTANCE = new VerifyCodeModel();
            }
        }
        return INSTANCE;
    }

    /**
     * 验证验证码
     * @return
     */
    public rx.Observable<TestBean> verifyCodes(String userName , String password) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", userName);
        params.put("password", password);

        return BaseApplication.getRetrofitClient().getChinaJesApi().login(params)
                .map(new HttpResultFunc<TestBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
