package com.library.base.mvpnet.nets;



import android.util.Log;

import com.library.base.base.BaseKcActivity;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.IView;
import com.library.base.mvpnet.JesException;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * 通用Subscribe
 * 统一处理异常
 * Created by Allen on 2017/4/14.
 */

public abstract class JesSubscribe<T> extends Subscriber<T> {

    private IView mView;
    private boolean showLoading = false;//显示loading框
    private boolean isShowMsg = true;//显示msg信息
    private String Loading_msg = "" ; //显示load信息提示
    public JesSubscribe(IView view ) {
        this.mView = view;
    }

    public JesSubscribe(IView view , boolean showLoading ,String Loading_msg ,  boolean isShowMsg ) {
        this.mView = view;
        this.showLoading = showLoading;
        this.Loading_msg = Loading_msg;
        this.isShowMsg = isShowMsg;
    }
    public JesSubscribe(IView view , boolean showLoading ,String Loading_msg) {
        this.mView = view;
        this.showLoading = showLoading;
        this.Loading_msg = Loading_msg;
    }
    public JesSubscribe(IView view , boolean showLoading) {
        this.mView = view;
        this.showLoading = showLoading;
    }

    public JesSubscribe setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
        return this;
    }

    @Override
    public void onStart() {
        if (showLoading) {
            mView.showLoading(Loading_msg);
        }
    }

    @Override
    public void onCompleted() {
        if (showLoading) {
            mView.hideLoading();
        }
    }

    @Override
    public void onError(Throwable e) {
        mView.hideLoading();
        JesException exception;
        Log.e("JesSubscribe",e.getMessage());
        if (e instanceof SocketTimeoutException) {//服务器响应超时
            exception = new JesException("网络连接失败", 100020);
//            exception = new JesException("网络连接超时", 100020);
            mView.showError(exception , true);
        } else if (e instanceof ConnectException) {
            exception = new JesException("网络连接失败", 100021);
//            exception = new JesException("请检查您的网络状态", 100021);
            mView.showError(exception, true);
        }
        else if (e instanceof ConnectTimeoutException) {//服务器请求超时
            exception = new JesException("网络连接失败", 100022);
//            exception = new JesException("服务器异常，请求超时", 100022);
            mView.showError(exception, true);
        }else if (e instanceof UnknownHostException) {
            exception = new JesException("网络连接失败", 100023);
//            exception = new JesException("网络中断，请检查您的网络状态", 100023);
            mView.showError(exception, true);
        }else if (e instanceof JesException) {
            exception = (JesException) e;
//           String msg = handleJesException(exception);
//            exception = new JesException(msg, exception.getCode());
//            exception = new JesException(msg, 100024);
//            error.errorJes(exception);
            mView.showError(exception, isShowMsg);
            _onError((JesException) e);
        } else {
            if (e.getMessage().equals("Unexpected response code for CONNECT: 503")){
                exception = new JesException("网络连接失败", 100050);
            }else {
                exception = new JesException("数据异常", 100050);
            }
//            exception = new JesException(e.getMessage(), 100050);
            mView.showError(exception, isShowMsg);
        }

//        mView.showError(exception);
    }
    private String handleJesException(JesException exception) {
        String msg = exception.getMessage();
        switch (exception.getCode()){
            case 500:
                msg = "服务器发生错误";
                break;
            case 404:
                msg = "请求地址不存在";
                break;
            case GlobalConstant.NET_CODE_400://请求失败
//                msg = "请求失败";
                break;
            case GlobalConstant.NET_CODE_401://请求失败
                msg = "缺少认证参数";
                break;
            case GlobalConstant.NET_CODE_403://请求失败
                msg = "认证失败";
                break;
            case GlobalConstant.NET_CODE_601://请求失败
                msg = "用户已禁用";
                break;
            case GlobalConstant.NET_CODE_602://请求失败
                msg = "用户认证失败";
                break;
            case GlobalConstant.NET_CODE_408://请求失败
                msg = "服务器连接超时";
                break;
//                default:
//                    msg = "数据异常";
//                    break;
//            case GlobalConstant.NET_CODE_RE_LOGIN:
//                msg = "登录过期，请重新登录";
////                mView.showMessage("登录过期，请重新登录");
//
//                BaseApplication.getInstance().jumpLoginActivity();
//                break;
        }
        return msg ;
    }

//    private void handleJesException(JesException exception) {
//        switch (exception.getCode()){
//            case GlobalConstant.NET_CODE_RE_LOGIN:
//                mView.showMessage("登录过期，请重新登录");
//                BaseApplication.getInstance().jumpLoginActivity();
//                break;
//        }
//    }

    @Override
    public void onNext(T result) {
        _onSuccess(result);
    }

    public abstract void _onSuccess(T t);

    public void _onError(JesException e) {

    }


    public static ShowErrorJes error = BaseKcActivity.error;
    // 自定义接口
    public static interface ShowErrorJes {
//        public void errorJes( JesException exception);
    }

}
