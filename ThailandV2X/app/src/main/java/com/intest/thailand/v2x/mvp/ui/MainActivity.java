package com.intest.thailand.v2x.mvp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.base.BaseActivity;
import com.intest.thailand.v2x.mvp.contract.MainContract;
import com.intest.thailand.v2x.mvp.presenter.MainPresenter;
import com.intest.thailand.v2x.mvp.ui.activity.TestActivity;
import com.intest.thailand.v2x.mvp.ui.fragment.car.CarConditionFragment;
import com.intest.thailand.v2x.mvp.ui.fragment.home.HomeFragment;
import com.intest.thailand.v2x.mvp.ui.fragment.mine.MineFragment;
import com.intest.thailand.v2x.widget.PopWindowUtils;
import com.library.base.adapter.BaseMainAdapter;
import com.library.base.entity.bean.TestBean;
import com.library.base.widget.NoScrollViewPager;
import com.library.base.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View ,PopWindowUtils.PopWindowHomeInterface{
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.foot_home_iv)
    ImageView footHomeIv;
    @BindView(R.id.foot_home_tv)
    TextView footHomeTv;
    @BindView(R.id.foot_home_ll)
    LinearLayout footHomeLl;
    @BindView(R.id.foot_CarCondition_iv)
    ImageView footCarConditionIv;
    @BindView(R.id.foot_CarCondition_tv)
    TextView footCarConditionTv;
    @BindView(R.id.foot_CarCondition_ll)
    LinearLayout footCarConditionLl;
    @BindView(R.id.foot_mine_iv)
    ImageView footMineIv;
    @BindView(R.id.foot_mine_tv)
    TextView footMineTv;
    @BindView(R.id.foot_mine_ll)
    LinearLayout footMineLl;
    @BindView(R.id.titleBar)
    TitleBar titleBar;
    private MainPresenter mainPresenter;
    private int[] nImage = new int[]{R.mipmap.icon_home, R.mipmap.icon_vehicle, R.mipmap.icon_my};
    private int[] sImage = new int[]{R.mipmap.icon_home_1, R.mipmap.icon_vehicle_1, R.mipmap.icon_my_1};
    private ArrayList<Fragment> fragmentArray;
    public static int statusBarHeight = 20 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(R.layout.activity_main);
        statusBarHeight = getStatusBarHeight();
//        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化
     */
    @Override
    public void init() {
        setStatusBarImg();
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
//        changeAppLanguage(0);
        titleBar.setTitle(getResources().getString(R.string.test_title));
        titleBar.setRightImgRes(R.mipmap.ic_launcher, new TitleBar.OnRightClickLinstener() {
            @Override
            public void onclick() {
                jumpActivity(null , TestActivity.class);
            }
        });
    }

    /**
     * 赋值
     */
    @Override
    public void setData() {
        fragmentArray = new ArrayList<>();
        fragmentArray.add(new HomeFragment());
        fragmentArray.add(new CarConditionFragment());
        fragmentArray.add(new MineFragment());
        viewPager.setAdapter(new BaseMainAdapter(getSupportFragmentManager(), fragmentArray));
        selectTab(0);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(3);
    }

    /**
     * 设置监听
     */
    @Override
    public void addListeners() {

    }

    /**
     * 接口成功返回在这里刷新界面
     * @param result
     */
    @Override
    public void success(TestBean result) {

    }

    /**
     * 接口失败返回在这里刷新界面
     */
    @Override
    public void failed() {

    }

    /**
     * 这里展示提示语
     */
    @Override
    public void showToast() {
    }

    /**
     * 如果需要可返回当前界面
     */
    @Override
    public Activity getActivity() {
        return null;
    }

    @OnClick({R.id.foot_home_ll, R.id.foot_CarCondition_ll, R.id.foot_mine_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.foot_home_ll://首页
                selectTab(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.foot_CarCondition_ll://车况
                selectTab(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.foot_mine_ll://我的
                selectTab(2);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 切换Tab
     *
     * @param index
     */
    public void selectTab(int index) {
        notSeleteTab();
        switch (index) {
            case 0://首页
                footHomeIv.setImageResource(sImage[0]);
                footHomeTv.setTextColor(getResources().getColor(R.color.main_bg));
                break;
            case 1://车况
                footCarConditionIv.setImageResource(sImage[1]);
                footCarConditionTv.setTextColor(getResources().getColor(R.color.main_bg));
                break;
            case 2://我的
                footMineIv.setImageResource(sImage[2]);
                footMineTv.setTextColor(getResources().getColor(R.color.main_bg));
                break;
        }
    }

    /**
     * 设置未选中
     */
    public void notSeleteTab() {
        footHomeIv.setImageResource(nImage[0]);
        footCarConditionIv.setImageResource(nImage[1]);
        footMineIv.setImageResource(nImage[2]);
        footHomeTv.setTextColor(getResources().getColor(R.color.gray_666666));
        footCarConditionTv.setTextColor(getResources().getColor(R.color.gray_666666));
        footMineTv.setTextColor(getResources().getColor(R.color.gray_666666));
    }

    @Override
    public void popSelectItem(TestBean testBean) {
        HomeFragment homeFragment = (HomeFragment) fragmentArray.get(0);
        homeFragment.popSelectItem(testBean);
    }
}
