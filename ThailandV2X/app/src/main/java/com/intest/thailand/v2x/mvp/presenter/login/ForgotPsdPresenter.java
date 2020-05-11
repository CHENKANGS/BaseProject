package com.intest.thailand.v2x.mvp.presenter.login;

import com.intest.thailand.v2x.mvp.contract.login.ForgotPhoneContract;
import com.intest.thailand.v2x.mvp.contract.login.ForgotPsdContract;
import com.intest.thailand.v2x.mvp.model.login.ForgotPhoneModel;
import com.intest.thailand.v2x.mvp.model.login.ForgotPsdModel;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.BasePresenter;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;

import rx.Subscription;

public class ForgotPsdPresenter extends BasePresenter<ForgotPsdContract.View> implements ForgotPsdContract.Presenter<ForgotPsdContract.View> {
    private ForgotPsdModel mModel;

    public ForgotPsdPresenter() {
        mModel = ForgotPsdModel.getInstance();
    }

    /**
     * 验证手机号
     */
    @Override
    public void forgotPsd(String userName, String password) {
        Subscription mSubscribe = mModel.forgotPsds(userName , password).subscribe(new JesSubscribe<TestBean>(mView, false, "", false) {
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
