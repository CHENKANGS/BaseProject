package com.intest.thailand.v2x.mvp.presenter.login;

import com.intest.thailand.v2x.mvp.contract.login.ForgotPhoneContract;
import com.intest.thailand.v2x.mvp.contract.login.VerifyCodeContract;
import com.intest.thailand.v2x.mvp.model.login.ForgotPhoneModel;
import com.intest.thailand.v2x.mvp.model.login.VerifyCodeModel;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.BasePresenter;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;

import rx.Subscription;

public class ForgotPhonePresenter extends BasePresenter<ForgotPhoneContract.View> implements ForgotPhoneContract.Presenter<ForgotPhoneContract.View> {
    private ForgotPhoneModel mModel;

    public ForgotPhonePresenter() {
        mModel = ForgotPhoneModel.getInstance();
    }

    /**
     * 验证手机号
     */
    @Override
    public void verifyPhone(String userName, String password) {
        Subscription mSubscribe = mModel.verifyPhones(userName , password).subscribe(new JesSubscribe<TestBean>(mView, false, "", false) {
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