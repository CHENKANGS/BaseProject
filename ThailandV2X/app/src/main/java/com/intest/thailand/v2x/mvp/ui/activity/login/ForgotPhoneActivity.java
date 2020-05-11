package com.intest.thailand.v2x.mvp.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.mvp.contract.login.ForgotPhoneContract;
import com.intest.thailand.v2x.mvp.presenter.login.ForgotPhonePresenter;
import com.library.base.entity.bean.TestBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 忘记密码--验证手机号
 */
public class ForgotPhoneActivity extends BaseActivity implements ForgotPhoneContract.View {

    @BindView(R.id.imgBack)
    LinearLayout imgBack;
    @BindView(R.id.forgot_phone_et)
    EditText forgotPhoneEt;
    @BindView(R.id.forgot_next_tv)
    TextView forgotNextTv;
    private ForgotPhonePresenter forgotPhonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_forgot_phone);
    }

    @Override
    public void init() {
        setStatusBarWhite();
        forgotPhonePresenter = new ForgotPhonePresenter();
        forgotPhonePresenter.attachView(this);
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

    @OnClick({R.id.imgBack, R.id.forgot_next_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack://返回
                finish();
                break;
            case R.id.forgot_next_tv://下一步
                jumpActivity(null , ForgotPsdActivity.class);
                break;
        }
    }
}
