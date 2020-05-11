package com.intest.thailand.v2x.base;

import android.os.Bundle;

import com.library.base.base.BaseKcFragment;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.JesException;

/*

 * Description:

 * File: BaseFragment.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/12/4 13:14

 *

 * Changes (from 2017/12/4)

 * -----------------------------------------------------------------

 * 2017/12/4 : Changes BaseFragment.java (k);

 * -----------------------------------------------------------------

 */
public abstract class BaseFragment extends BaseKcFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



//    /**
//     * 跳转
//     * @param bundle
//     * @param cls
//     */
//    public void jumpFragActivity(Bundle bundle, Class<? extends Activity> cls) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), cls);
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        startActivity(intent);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }
//    public void jumpActivity(Class<? extends Activity> cls, int requestCode) {
//        jumpActivityForResult(null, cls, requestCode);
//    }
//
//    public void jumpActivityForResult(Bundle bundle, Class<? extends Activity> cls, int requestCode) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), cls);
//        if (bundle != null) {
//            intent.putExtras(bundle);
//        }
//        startActivityForResult(intent, requestCode);
//    }
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
//        getActivity().overridePendingTransition(R.anim.translate_open_in, R.anim.translate_open_out);
//        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//    }

    /**
     * 请求过程过程中返回的错误
     *
     * @param e
     */
    @Override
    public void errorJes(JesException e , boolean showMsg) {
        super.errorJes(e , showMsg);
        handleJesException(e , showMsg);
    }
    private void handleJesException(JesException exception, boolean showMsg) {
        String msg = exception.getMessage();
        switch (exception.getCode()){
            case 500:
                msg = "服务器发生错误";
                break;
            case 404:
                msg = "请求地址不存在";
                break;
            case GlobalConstant.NET_CODE_400://请求失败
                msg = exception.getMessage();
//                msg = "请求失败";
                break;
            case GlobalConstant.NET_CODE_401://请求失败
                msg = "缺少认证参数";
//                login(exception.getCode()+"");
                break;
            case GlobalConstant.NET_CODE_403://请求失败auth_code
                msg = "认证失败";
//                login(exception.getCode()+"");
                break;
            case GlobalConstant.NET_CODE_601://请求失败
                msg = "认证失败";
//                msg = "用户已禁用";
//                login(exception.getCode()+"");
                break;
            case GlobalConstant.NET_CODE_602://请求失败
                msg = "认证失败";
//                msg = "用户认证失败";
//                login(exception.getCode()+"");
                break;
            case GlobalConstant.NET_CODE_408://请求失败
                msg = "服务器连接超时";
                break;
        }
        if (showMsg){
            showDialogMessage(msg);
        }
//        showToastMessage(msg);
    }
    /**
     * 接口返回错误时跳转登录
     */
//    public void login(final String code) {
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                //清空配置信息
//                AppCache.getInstance().setObjectDevice(GlobalLocal.KEY_LOGINBEAN , null);
//                GlobalLocal.LOGIN_BEAN = null;
//                GlobalLocal.token = null;
////                AppCache.getInstance().setStr(GlobalLocal.KEY_COMPANY_ID, null);
//                GlobalLocal.company_id = null ;
//                Bundle bundle = new Bundle() ;
//                bundle.putString("CODE" , code);
//                jumpFragActivity(bundle, LoginActivity.class);
//            }
//        }, 1000);
//    }

}
