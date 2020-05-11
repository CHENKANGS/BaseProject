package com.intest.thailand.v2x.mvp.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.mvp.contract.login.RegisterContract;
import com.intest.thailand.v2x.mvp.presenter.login.RegisterPresenter;
import com.library.base.entity.bean.TestBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements RegisterContract.View {

    @BindView(R.id.register_userName_et)
    EditText registerUserNameEt;
    @BindView(R.id.register_password_et)
    EditText registerPasswordEt;
    @BindView(R.id.register_openPsd_iv)
    ImageView registerOpenPsdIv;
    @BindView(R.id.register_password1_et)
    EditText registerPassword1Et;
    @BindView(R.id.register_openPsd1_iv)
    ImageView registerOpenPsd1Iv;
    @BindView(R.id.register_radioButton)
    RadioButton registerRadioButton;
    @BindView(R.id.register_privacy_tv)
    TextView registerPrivacyTv;
    @BindView(R.id.register_tv)
    TextView registerTv;
    @BindView(R.id.imgBack)
    LinearLayout imgBack;
    private RegisterPresenter registerPresenter;
    private boolean isRadioCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_register);
    }

    @Override
    public void init() {
        setStatusBarWhite();
        registerPresenter = new RegisterPresenter();
        registerPresenter.attachView(this);
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

    @OnClick({R.id.imgBack,R.id.register_openPsd_iv, R.id.register_openPsd1_iv, R.id.register_radioButton, R.id.register_privacy_tv, R.id.register_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack://返回
                finish();
                break;
            case R.id.register_openPsd_iv://查看密码1
                break;
            case R.id.register_openPsd1_iv://查看密码2
                break;
            case R.id.register_radioButton://
                if (isRadioCheck) {
                    isRadioCheck = false;
                    registerRadioButton.setChecked(false);
                } else {
                    isRadioCheck = true;
                    registerRadioButton.setChecked(true);
                }
                break;
            case R.id.register_privacy_tv://查看隐私协议
                break;
            case R.id.register_tv://下一步
                jumpActivity(null, VerifyCodeActivity.class);
                break;
        }
    }

}
