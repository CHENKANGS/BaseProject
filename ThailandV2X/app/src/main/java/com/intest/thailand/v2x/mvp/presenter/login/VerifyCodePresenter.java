package com.intest.thailand.v2x.mvp.presenter.login;

import com.intest.thailand.v2x.mvp.contract.login.VerifyCodeContract;
import com.intest.thailand.v2x.mvp.model.login.VerifyCodeModel;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.BasePresenter;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;

import rx.Subscription;

public class VerifyCodePresenter extends BasePresenter<VerifyCodeContract.View> implements VerifyCodeContract.Presenter<VerifyCodeContract.View> {
    private VerifyCodeModel mModel;

    public VerifyCodePresenter() {
        mModel = VerifyCodeModel.getInstance();
    }

    /**
     * 验证验证码
     */
    @Override
    public void verifyCode(String userName, String password) {
        Subscription mSubscribe = mModel.verifyCodes(userName , password).subscribe(new JesSubscribe<TestBean>(mView, false, "", false) {
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
