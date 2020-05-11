package com.intest.thailand.v2x.mvp.presenter;

import com.intest.thailand.v2x.mvp.contract.MainContract;
import com.intest.thailand.v2x.mvp.model.MainModel;
import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.BasePresenter;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/*

 * Description: 

 * File: MainPresenter.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/12/6 11:00

 *

 * Changes (from 2017/12/6)

 * -----------------------------------------------------------------

 * 2017/12/6 : Changes MainPresenter.java (k);

 * -----------------------------------------------------------------

 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter<MainContract.View> {

    private MainModel mModel;

    public MainPresenter() {
        mModel = MainModel.getInstance();
    }

    /**
     * 测试样例
     */
    @Override
    public void getNumber() {
        Subscription mSubscribe = mModel.getNumber().subscribe(new JesSubscribe<TestBean>(mView, false, "", false) {
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
