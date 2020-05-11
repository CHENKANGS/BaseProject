package com.intest.thailand.v2x.mvp.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.mvp.contract.login.VerifyCodeContract;
import com.intest.thailand.v2x.mvp.presenter.login.VerifyCodePresenter;
import com.library.base.entity.bean.TestBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 验证手机号
 */
public class VerifyCodeActivity extends BaseActivity implements VerifyCodeContract.View {

    @BindView(R.id.imgBack)
    LinearLayout imgBack;
    @BindView(R.id.verify_code_et)
    EditText verifyCodeEt;
    @BindView(R.id.verify_getCode_tv)
    TextView verifyGetCodeTv;
    @BindView(R.id.verify_tv)
    TextView verifyTv;
    private VerifyCodePresenter verifyPhonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_verify_phone);
    }

    @Override
    public void init() {
        setStatusBarWhite();
        verifyPhonePresenter = new VerifyCodePresenter();
        verifyPhonePresenter.attachView(this);
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

    @OnClick({R.id.imgBack, R.id.verify_getCode_tv, R.id.verify_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack://返回
                finish();
                break;
            case R.id.verify_getCode_tv://获取验证码
                break;
            case R.id.verify_tv://验证
                break;
        }
    }
}
