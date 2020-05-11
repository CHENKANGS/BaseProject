package com.intest.thailand.v2x.mvp.presenter.login;

import android.content.Context;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.mvp.contract.MainContract;
import com.intest.thailand.v2x.mvp.contract.login.LoginContract;
import com.intest.thailand.v2x.mvp.model.MainModel;
import com.intest.thailand.v2x.mvp.model.login.LoginModel;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.BasePresenter;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;

import rx.Subscription;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter<LoginContract.View> {

    private LoginModel mModel;

    public LoginPresenter() {
        mModel = LoginModel.getInstance();
    }

    /**
     * 登录
     */
    @Override
    public void login(String userName, String password) {
        Subscription mSubscribe = mModel.login(userName , password).subscribe(new JesSubscribe<TestBean>(mView, false, mView.getActivity().getResources().getString(R.string.logining_msg), false) {
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
