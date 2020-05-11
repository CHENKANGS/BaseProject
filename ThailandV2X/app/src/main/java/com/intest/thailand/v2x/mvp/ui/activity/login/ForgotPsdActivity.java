package com.intest.thailand.v2x.mvp.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.mvp.contract.login.ForgotPsdContract;
import com.intest.thailand.v2x.mvp.presenter.login.ForgotPsdPresenter;
import com.library.base.entity.bean.TestBean;

public class ForgotPsdActivity extends BaseActivity implements ForgotPsdContract.View{

    private ForgotPsdPresenter forgotPsdPresenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_forgot_psd);
    }

    @Override
    public void init() {
        setStatusBarWhite();
        forgotPsdPresenter = new ForgotPsdPresenter();
        forgotPsdPresenter.attachView(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void success(TestBean result) {

    }

    @Override
    public void failed() {

    }

    @Override
    public void showToast() {

    }

    @Override
    public Activity getActivity() {
        return null;
    }
}
