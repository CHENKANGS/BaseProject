package com.intest.thailand.v2x.mvp.presenter.login;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.mvp.contract.login.RegisterContract;
import com.intest.thailand.v2x.mvp.model.login.LoginModel;
import com.intest.thailand.v2x.mvp.model.login.RegisterModel;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.BasePresenter;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;

import rx.Subscription;

public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter<RegisterContract.View> {
    private RegisterModel mModel;

    public RegisterPresenter() {
        mModel = RegisterModel.getInstance();
    }

    /**
     * 注册
     * @param userName
     * @param password
     */
    @Override
    public void register(String userName, String password) {
        Subscription mSubscribe = mModel.register(userName , password).subscribe(new JesSubscribe<TestBean>(mView, false, "", false) {
            @Override
            public void _onSuccess(TestBean result) {
                mView.success(result);
            }
            @Override
            public void _onError(JesException e) {
                mView.failed();
            }
        });
        mSubscriptions.add(mSubscribe);
    }

}
