package com.library.base.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.library.base.BuildConfig;
import com.library.base.mvpnet.IView;
import com.library.base.mvpnet.JesException;
import com.library.base.util.NetBroadcastReceiver;
import com.library.base.util.NetUtil;
import com.library.base.widget.dialog.LoadDialog;
import com.library.base.widget.dialog.QMUITipDialog;
import com.library.base.widget.dialog.ShowMsgDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChenKang on 2017/11/21.
 */

public abstract class BaseKcFragment extends Fragment implements IView {

    private ProgressDialog mProgressDialog;
    private Toast mToast;
    public Activity mActivity;
    private Unbinder unbinder;
    private LoadDialog mLoadDialog;

    private Bundle arguments;

    private boolean isInit;
    private QMUITipDialog tipDialog ;
    private ShowMsgDialog msgDialog;

    /**
         * 初始化数据
     */
    public abstract void init();
    /**
     * 设置数据
     */
    public abstract void setData();
    /**
     * 注册监听
     */
    public abstract void addListeners();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    protected View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @LayoutRes int layoutId) {
         View fragmentView = inflater.inflate(layoutId, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        isInit = true;
        init();
        setData();
        addListeners();
        return fragmentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && isInit) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 可见。相当于activity的onResume
     * 有bug需手动处理默认页不调用的情况
     */
    public void onVisible() {

    }

    public void onInVisible() {
        hideKeyboard();
    }

    protected void showToastMessage(String message) {
        if (! TextUtils.isEmpty(message)) {
            if (mToast == null) {
                mToast = Toast.makeText(mActivity, "", Toast.LENGTH_SHORT);
            }
            mToast.setText(message);
            mToast.show();
        }
    }

    protected void showToastMessage(@StringRes int messageId) {
        if (messageId != 0) {
            String message = mActivity.getResources().getString(messageId);
            showToastMessage(message);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
//        showToastMessage(message);
        showDialogMessage(message);
    }

    @Override
    public void showMessage(@StringRes int StringRes) {
        showToastMessage(StringRes);
    }


    /**
     * 显示加载框
     */
    @Override
    public void showLoading(String msg) {
//        if (mLoadDialog == null) {
//            mLoadDialog = new LoadDialog(this);
//        }
//        mLoadDialog.show();

        if (tipDialog == null){
            tipDialog = new QMUITipDialog.Builder(getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord(msg)
                    .create();
        }
        tipDialog.show();
    }
    /**
     * 隐藏加载框
     */
    @Override
    public void hideLoading() {
//        if (mLoadDialog != null) {
//            mLoadDialog.cancel();
//        }
        if (null != tipDialog)
            tipDialog.dismiss();
        tipDialog = null;
    }


    /**
     * 请求过程过程中返回的错误
     *
     * @param e
     */
    @Override
    public void showError(JesException e) {
        if (BuildConfig.DEBUG) {
            errorJes(e);
//            showMessage(e.getMessage());
        } else {
            showMessage("出现异常");
        }
    }


    @Override
    public void showError(JesException e, boolean showMsg) {
        if (showMsg){
            if (BuildConfig.DEBUG) {
                errorJes(e, showMsg);
//                showMessage(e.getMessage());
            }else {
                showMessage("出现异常");
            }
        }
    }
    public void errorJes(JesException e, boolean showMsg) {

    }

    public void errorJes(JesException e) {

    }


    /**
     * 纯文字提示
     * @param message
     */
    public void showDialogMessage(String message) {
        if (null != msgDialog) {
            msgDialog.dismiss();
            msgDialog = null;
        }
        msgDialog = new ShowMsgDialog.Builder(getContext())
                .setIconType(ShowMsgDialog.Builder.ICON_TYPE_NOTHING)
                .setTipWord(message)
                .create();
        msgDialog.show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (null != msgDialog) {
                    msgDialog.dismiss();
                    msgDialog = null;
                }
            }
        }, 1000);
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    protected void changeTitle(String title) {
        if (getActivity() instanceof BaseKcActivity) {
            ((BaseKcActivity) getActivity()).changeTitle(title);
        }
    }

    /**
     * 绑定TabLayout
     *
     * @param viewPager
     */
    public void setTabWithViewPager(ViewPager viewPager) {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.setTabWithViewPager(viewPager);
        }
    }

    /**
     * 隐藏TabLayout
     */
    public void hideTabLayout() {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.hideTabLayout();
        }
    }

    /**
     * 搜索栏相关
     *
     * @param searchListener
     * @param addListener
     */
    public void showSearchView(String hintText, BaseKcActivity.OnSearchListener searchListener, View.OnClickListener addListener) {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.showSearchView(hintText, searchListener, addListener);
        }
    }

    /**
     * 显示搜索栏
     * @param hintText
     * @param searchListener
     */
    public void showSearchView(String hintText, BaseKcActivity.OnSearchListener searchListener) {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.showSearchView(hintText, searchListener);
        }
    }

    /**
     * 隐藏搜索栏
     */
    public void hideSearchView() {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.hideSearchView();
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarNoShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getActivity() instanceof BaseKcActivity) {
                BaseKcActivity activity = (BaseKcActivity) getActivity();
                activity.setToolBarNoShade();
            }
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarHasShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (getActivity() instanceof BaseKcActivity) {
                BaseKcActivity activity = (BaseKcActivity) getActivity();
                activity.setToolBarHasShade();
            }
        }
    }

    /**
     * 隐藏AppBar
     */
    public void hideToolBar() {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.hideToolBar();
        }
    }

    /**
     * 隐藏AppBar
     */
    public void hideKeyboard() {
        if (getActivity() instanceof BaseKcActivity) {
            BaseKcActivity activity = (BaseKcActivity) getActivity();
            activity.hideKeyboard();
        }
    }

    /**
     * 修改标题
     * @param title
     */
    protected void changeTitle(@StringRes int title) {
        if (getActivity() instanceof BaseKcActivity) {
            String string = getActivity().getString(title);
            ((BaseKcActivity) getActivity()).changeTitle(string);
        }
    }

    /**
     * 传递参数
     * @param key
     * @param value
     * @return
     */
    public BaseKcFragment putArgument(String key, String value) {
        if (arguments == null) {
            arguments = new Bundle();
        }
        arguments.putString(key, value);
        setArguments(arguments);
        return this;
    }



}

