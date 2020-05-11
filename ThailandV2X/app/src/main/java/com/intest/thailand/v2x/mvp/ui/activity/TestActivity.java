package com.intest.thailand.v2x.mvp.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.base.BaseApplication;
import com.intest.thailand.v2x.util.AppLanguageUtils;
import com.library.base.cache.AppCache;
import com.library.base.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends BaseActivity {

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.setLanguage)
    TextView setLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_test);
    }

    @Override
    public void init() {
        titleBar.setTitle(getResources().getString(R.string.test_title));
    }

    @Override
    public void setData() {

    }

    @Override
    public void addListeners() {

    }

    @OnClick(R.id.setLanguage)
    public void onViewClicked() {
        onChangeAppLanguage(AppLanguageUtils.CHINESE);

    }

    /**
     * 设置语言
     *
     * @param newLanguage
     */
    private void onChangeAppLanguage(String newLanguage) {
        AppCache.getInstance().setStr(AppLanguageUtils.Language_key, newLanguage);
        AppLanguageUtils.changeAppLanguage(this, newLanguage);
        AppLanguageUtils.changeAppLanguage(this, newLanguage);
        this.recreate();
    }
}
