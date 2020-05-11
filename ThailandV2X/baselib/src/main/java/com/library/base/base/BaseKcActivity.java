package com.library.base.base;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jaeger.library.StatusBarUtil;
import com.library.base.BuildConfig;
import com.library.base.R;
import com.library.base.cache.AppBaseCache;
import com.library.base.global.GlobalConstant;
import com.library.base.mvpnet.IView;
import com.library.base.mvpnet.JesException;
import com.library.base.mvpnet.nets.JesSubscribe;
import com.library.base.util.NetBroadcastReceiver;
import com.library.base.util.NetUtil;
import com.library.base.util.QMUIStatusBarHelper;
import com.library.base.util.ToastUtils;
import com.library.base.util.UiUtils;
import com.library.base.util.permission.GuaranteeCallBack;
import com.library.base.util.permission.HiPermission;
import com.library.base.util.permission.SimplePermissionCallBack;
import com.library.base.view.BaseMainActivity;
import com.library.base.widget.dialog.LoadDialog;
import com.library.base.widget.dialog.QMUITipDialog;
import com.library.base.widget.dialog.ShowMsgDialog;

import butterknife.ButterKnife;

/**
 * Created by ChenKang on 2017/11/21.
 */

public abstract class BaseKcActivity extends AppCompatActivity implements IView, TextView.OnEditorActionListener, NetBroadcastReceiver.NetEvevt {
    protected Toolbar mToolBar;
    protected TabLayout mTab;
    private TextView mBarTitle;
    private AppBarLayout appBar;
    private LinearLayout ll_search;
    private ImageButton ib_search;
    private ImageButton ib_add;

    protected Context mContext;
    private QMUITipDialog tipDialog;
    private ShowMsgDialog msgDialog;

    //    private SuperSwipeRefreshLayout swipeRefreshLayout;
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    public static NetBroadcastReceiver.NetEvevt evevt;
    public static JesSubscribe.ShowErrorJes error;
    /**
     * 网络类型
     */
    private int netMobile;

    //注销广播
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AppBaseCache.getInstance().resetAppBaseCache();
            Intent loginIntent = new Intent(mContext, BaseMainActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//清空堆栈
            startActivity(loginIntent);
            finish();
        }
    };
    private LoadDialog mLoadDialog;
    private ActionBar mActionBar;
    private int menuRes = -1;
    private EditText et_search;

    /**
     * 绑定布局
     *
     * @return
     */
//    public abstract int initLayout();
    public static RequestOptions getOptions() {
        //glide配置
        //资讯
        RequestOptions options_zx = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.zx_icon)
                .error(R.mipmap.zx_icon)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        //                .bitmapTransform(new RoundedCornersTransformation(context, 16, 0))//设置圆角
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)  //让Glide既缓存全尺寸又缓存其他尺寸
        return options_zx;
    }

    public static RequestOptions getOptions2() {
        //glide配置
        //其他
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.gear_image_default)
                .error(R.mipmap.gear_image_default)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        //                .bitmapTransform(new RoundedCornersTransformation(context, 16, 0))//设置圆角
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)  //让Glide既缓存全尺寸又缓存其他尺寸
        return options;
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        AppManager.getAppManager().addActivity(this);
        registerReceiver(mLoginReceiver, new IntentFilter(GlobalConstant.LOGON_ACTION));
        evevt = this;
//        error = this;
        inspectNet();
    }

    protected void createView(@LayoutRes int layoutId) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_base);
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(mContext, R.color.main));
        QMUIStatusBarHelper.setStatusBarDarkMode(this);//设置状态栏白色字体
        FrameLayout mFlContent = (FrameLayout) findViewById(R.id.fl_content);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mTab = (TabLayout) findViewById(R.id.tab);
        mBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        ll_search = (LinearLayout) findViewById(R.id.ll_search);
        ib_search = (ImageButton) findViewById(R.id.ib_search);
        ib_add = (ImageButton) findViewById(R.id.ib_add);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.setOnEditorActionListener(this);
//        setSupportActionBar(mToolBar);
//        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
        }
        View view = View.inflate(this, layoutId, null);
        mFlContent.addView(view);
        ButterKnife.bind(this, view);
        UiUtils.getRootView(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        hideToolBar();
        init();
        setData();
        addListeners();
    }

    /**
     * 如果需要图片侵入状态栏，可用此方法
     */
    public void setStatusBarImg() {
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);
    }

    /**
     * 设置状态栏白色背景， 黑色字体
     */
    public void setStatusBarWhite() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(mContext, R.color.white));
        QMUIStatusBarHelper.setStatusBarLightMode(this);//设置状态栏黑色字体
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight(){
        // 获得状态栏高度
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return this.getResources().getDimensionPixelSize(resourceId);
    }

    public void hideToolBar() {
        appBar.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarNoShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setElevation(0);
        }
    }

    /**
     * 设置Toolbar没阴影效果
     */
    public void setToolBarHasShade() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            appBar.setElevation(10);
        }
    }

    /**
     * 绑定TabLayout
     *
     * @param viewPager
     */
    public void setTabWithViewPager(ViewPager viewPager) {
        mTab.setVisibility(View.VISIBLE);
        mTab.setupWithViewPager(viewPager);
    }

    /**
     * 隐藏TabLayout
     */
    public void hideTabLayout() {
        mTab.setVisibility(View.GONE);
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag) {
        addFragment(containerViewId, fragment, fragmentTag, false);
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag, boolean addToBackStack) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(containerViewId, fragment, fragmentTag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    /**
     * 初始化标题栏
     *
     * @param isBack
     * @param title
     */
    public void initTitleBar(boolean isBack, String title) {
        mBarTitle.setText(title);
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(isBack);
        }
    }

    public void initTitleBar(boolean isBack, @StringRes int title) {
        initTitleBar(isBack, getString(title));
    }

    /**
     * 带菜单
     *
     * @param isBack
     * @param title
     * @param menuRes
     */
    public void initTitleBar(boolean isBack, String title, @MenuRes int menuRes, OnMenuItemClickListener onMenuItemClickListener) {
        initTitleBar(isBack, title);
        this.menuRes = menuRes;
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public void initTitleBar(boolean isBack, @StringRes int title, @MenuRes int menuRes, OnMenuItemClickListener onMenuItemClickListener) {
        initTitleBar(isBack, getString(title), menuRes, onMenuItemClickListener);
    }

    /**
     * 设置Toolbar滑动Flag
     *
     * @param flag
     */
    public void setToolBarScrollFlag(int flag) {
        AppBarLayout.LayoutParams params =
                (AppBarLayout.LayoutParams) ((View) (mToolBar.getParent())).getLayoutParams();
        params.setScrollFlags(flag);
    }

    /**
     * 搜索框
     *
     * @param hintText
     * @param searchListener
     */
    public void showSearchView(String hintText, OnSearchListener searchListener) {
        showSearchView(hintText, searchListener, null);
    }

    public void showSearchView(String hintText, final OnSearchListener searchListener, View.OnClickListener addListener) {
        if (addListener == null) {
            ib_add.setVisibility(View.GONE);
        } else {
            ib_add.setVisibility(View.VISIBLE);
        }
        onSearchListener = searchListener;
        ll_search.setVisibility(View.VISIBLE);
        et_search.setHint(hintText);
        ib_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchListener.onSearch(et_search.getText().toString());
            }
        });
        ib_add.setOnClickListener(addListener);
    }

    private OnSearchListener onSearchListener;

    public interface OnSearchListener {
        void onSearch(String str);
    }

    public void hideSearchView() {
        ll_search.setVisibility(View.GONE);
    }

    public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
        et_search.clearFocus();
        hideKeyboard();
        if (onSearchListener != null) {
            onSearchListener.onSearch(et_search.getText().toString());
        }
        return true;
    }


    /**
     * 定义菜单栏
     *
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onItemClick(item);
        }

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public interface OnMenuItemClickListener {
        void onItemClick(MenuItem item);
    }

    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    /**
     * 修改标题
     *
     * @param title
     */
    public void changeTitle(String title) {
        mBarTitle.setText(title);
    }

    public void changeTitle(@StringRes int title) {
        changeTitle(getString(title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuRes != -1) {
            getMenuInflater().inflate(menuRes, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected void startActivityNoValue(Context context, Class<?> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
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

        if (tipDialog == null) {
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
        if (null != tipDialog) {
            tipDialog.dismiss();
            tipDialog = null;
        }
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

    /**
     * 请求过程过程中返回的错误
     *
     * @param e
     */
    @Override
    public void showError(JesException e, boolean showMsg) {
        errorJes(e, showMsg);
    }

    public void errorJes(JesException e, boolean showMsg) {

    }

    public void errorJes(JesException e) {

    }


    /**
     * 获取网络状态
     *
     * @param context
     * @return
     */
    protected boolean getNetworkStatus(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }
        return false;
    }

    /**
     * 判断创建该Dialog的上下文是否有效，在有效的情况才关闭Dialog。
     */
    public void hideProgress() {
        if (msgDialog != null) {
            if (msgDialog.isShowing()) { //check if dialog is showing.

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper) msgDialog.getContext()).getBaseContext();

                //if the Context used here was an activity AND it hasn't been finished or destroyed
                //then dismiss it
                if (context instanceof Activity) {
                    if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                        msgDialog.dismiss();
                } else //if the Context used wasnt an Activity, then dismiss it too
                    msgDialog.dismiss();
            }
            msgDialog = null;
        }
    }

    /**
     * 纯文字提示
     *
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
                    if (msgDialog.isShowing()) { //check if dialog is showing.
                        Context context = ((ContextWrapper) msgDialog.getContext()).getBaseContext();
                        if (context instanceof Activity) {
                            if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed())
                                msgDialog.dismiss();
                        } else //if the Context used wasnt an Activity, then dismiss it too
                            msgDialog.dismiss();
                    }
                    msgDialog = null;
                }
            }
        }, 1000);
    }


    /**
     * 显示成功图标
     *
     * @param message
     */
    public void showDialogMessageImg(String message) {
        if (null != msgDialog) {
            msgDialog.dismiss();
            msgDialog = null;
        }
        msgDialog = new ShowMsgDialog.Builder(getContext())
                .setIconType(ShowMsgDialog.Builder.ICON_TYPE_SUCCESS)
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

    /**
     * 显示失败图标
     *
     * @param message
     */
    public void showDialogFailedMsgImg(String message) {
        Context context = getContext();
        if (null != msgDialog) {
            msgDialog.dismiss();
            msgDialog = null;
        }
        msgDialog = new ShowMsgDialog.Builder(getContext())
                .setIconType(ShowMsgDialog.Builder.ICON_TYPE_FAIL)
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

    /**
     * WebView加载时显示的弹窗
     */
    public void showWebDialog(String msg) {
        if (null != msgDialog) {
            msgDialog.dismiss();
            msgDialog = null;
        }
        msgDialog = new ShowMsgDialog.Builder(getContext())
                .setIconType(ShowMsgDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(msg)
                .create();
        msgDialog.show();
    }

    /**
     * WebView加载完成关闭弹窗
     */
    public void hideWebDialog() {
        if (null != msgDialog) {
            msgDialog.dismiss();
            msgDialog = null;
        }
    }

    /**
     * 成功操作之后延时1秒跳转
     *
     * @param activity
     */
    public void successFinish(final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                activity.finish();
            }
        }, 1000);
    }

    /**
     *  
     *  * 调用拨号界面 
     *  * @param phone 电话号码 
     *  
     */
    public void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 未开发完模块的提示
     */
    public void showModelMsg() {
        showDialogMessage("即将上线，敬请期待");
    }

    /**
     * 上传图片
     * 显示加载框
     */
    public void showLoadDialog() {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                    .setTipWord("正在上传...")
                    .create();
        }
        tipDialog.show();
    }

    public void showLoadDialogWork() {
        if (tipDialog == null) {
            tipDialog = new QMUITipDialog.Builder(getContext())
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_NOTHING)
                    .setTipWord("请稍候...")
                    .create();
        }
        tipDialog.show();
    }

    /**
     * 隐藏加载框
     */
    public void hideLoadDialog() {
        if (null != tipDialog)
            tipDialog.dismiss();
    }


    protected void showToastMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShortToast(message);
        }
    }

    protected void showToastMessage(@StringRes int messageId) {
        if (messageId != 0) {
            String message = mContext.getResources().getString(messageId);
            showToastMessage(message);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        showDialogMessage(message);
//        showToastMessage(message);
    }

    @Override
    public void showMessage(@StringRes int StringRes) {
        showToastMessage(StringRes);
    }


    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    protected void onDestroy() {
        //反注册广播
        unregisterReceiver(mLoginReceiver);
        super.onDestroy();
        if (null != msgDialog) {
            msgDialog.dismiss();
        }
        AppManager.getAppManager().removeActivity(this);
    }

    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 请求权限
     *
     * @param permission
     * @param tips
     * @param callBack
     */
    protected void checkPermission(String permission, String tips, final GuaranteeCallBack callBack) {
        HiPermission.create(this).checkSinglePermission(permission, new SimplePermissionCallBack(this, tips) {
            @Override
            public void onGuarantee(String permission, int position) {
                callBack.onGuarantee();
            }
        });
    }

    /**
     * 短信权限
     *
     * @param callBack
     */
    protected void checkSMSPermission(final GuaranteeCallBack callBack) {
        checkPermission(Manifest.permission.READ_SMS, "请开启短信权限", callBack);
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (null != tipDialog) {
                tipDialog.dismiss();
            }
            finish();
            return false;
        }
        return false;
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
//            if (null != tipDialog){
//                tipDialog.dismiss();
//            }
//            finish();
//            System.out.println("按下了back键   onKeyDown()");
//            return false;
//        }else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (keyCode == KeyEvent.KEYCODE_BACK) { //表示按返回键 时的操作
//                // 监听到返回按钮点击事件
//                if (null != tipDialog){
//                    tipDialog.dismiss();
//                }
//
//                //exit();
//                //  finish();
//
//                //  return true;    //已处理
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseKcActivity.this);
        return isNetConnect();

        // if (netMobile == 1) {
        // System.out.println("inspectNet：连接wifi");
        // } else if (netMobile == 0) {
        // System.out.println("inspectNet:连接移动数据");
        // } else if (netMobile == -1) {
        // System.out.println("inspectNet:当前没有网络");
        //
        // }
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
//        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {//无线网络
            return true;
        } else if (netMobile == 0) {//移动网络
            return true;
        } else if (netMobile == -1) {//没有连接网络
            return false;

        }
        return false;
    }


}
