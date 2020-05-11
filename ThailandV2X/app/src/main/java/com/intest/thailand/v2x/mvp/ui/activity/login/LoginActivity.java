package com.intest.thailand.v2x.mvp.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.mvp.contract.login.LoginContract;
import com.intest.thailand.v2x.mvp.presenter.login.LoginPresenter;
import com.intest.thailand.v2x.mvp.ui.MainActivity;
import com.library.base.entity.bean.TestBean;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.login_userName_et)
    EditText loginUserNameEt;
    @BindView(R.id.login_password_et)
    EditText loginPasswordEt;
    @BindView(R.id.login_openPsd_iv)
    ImageView loginOpenPsdIv;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.login_register_tv)
    TextView loginRegisterTv;
    @BindView(R.id.login_forgotPsd_tv)
    TextView loginForgotPsdTv;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_login);
    }

    @Override
    public void init() {
        setStatusBarWhite();
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
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
        return this;
    }

    @OnClick({R.id.login_openPsd_iv, R.id.login_tv,R.id.login_register_tv, R.id.login_forgotPsd_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_openPsd_iv://查看密码
                break;
            case R.id.login_tv://登录
//                loginPresenter.login("" , "");
                jumpActivity(null, MainActivity.class);
                break;
            case R.id.login_register_tv://注册
                jumpActivity(null , RegisterActivity.class);
                break;
            case R.id.login_forgotPsd_tv://忘记密码
                jumpActivity(null , ForgotPhoneActivity.class);
                break;
        }
    }

}
