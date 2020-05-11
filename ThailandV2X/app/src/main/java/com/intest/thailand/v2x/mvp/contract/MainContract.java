package com.intest.thailand.v2x.mvp.contract;

import android.app.Activity;

import com.library.base.entity.bean.TestBean;
import com.library.base.mvpnet.IPresenter;
import com.library.base.mvpnet.IView;

/*

 * Description: 

 * File: MainContract.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/12/6 10:56

 *

 * Changes (from 2017/12/6)

 * -----------------------------------------------------------------

 * 2017/12/6 : Changes MainContract.java (k);

 * -----------------------------------------------------------------

 */
public class MainContract {

    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    public interface View extends IView {
        //成功
        void success(TestBean result);

        //失败
        void failed();

        //提示
        void showToast();

        Activity getActivity();
    }

    public interface Presenter<V extends IView> extends IPresenter<V> {
        //测试样例
        void getNumber();
    }

}
