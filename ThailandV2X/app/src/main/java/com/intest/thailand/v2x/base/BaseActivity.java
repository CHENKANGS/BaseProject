package com.intest.thailand.v2x.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.mvp.ui.MainActivity;
import com.intest.thailand.v2x.util.AppLanguageUtils;
import com.library.base.base.BaseKcActivity;
import com.library.base.cache.AppCache;
import com.library.base.util.StringUtils;
import com.library.base.util.permission.GuaranteeCallBack;
import com.library.base.widget.dialog.AlertDialog;
import com.library.base.widget.dialog.ShowMsgDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/*

 * Description:

 * File: BaseActivity.java

 * Author: k

 * Version: V100R001C01

 * Create: 2017/12/4 11:48

 *

 * Changes (from 2017/12/4)

 * -----------------------------------------------------------------

 * 2017/12/4 : Changes BaseActivity.java (k);

 * -----------------------------------------------------------------

 */
public abstract class BaseActivity extends BaseKcActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLanguageChange();
    }

    @Override
    protected void createView(int layoutId) {
        super.createView(layoutId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onLanguageChange();
    }

    /**
     * 切换语言
     */
    private void onLanguageChange() {
        String language = AppCache.getInstance().getStr(AppLanguageUtils.Language_key);
        AppLanguageUtils.changeAppLanguage(this, AppLanguageUtils.getSupportLanguage(language));
    }

    /**
     * 此方法先于 onCreate()方法执行
     *
     * @param newBase
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        //获取我们存储的语言环境 比如 "en","zh",等等
        String language = AppCache.getInstance().getStr(AppLanguageUtils.Language_key);
        //attach 对应语言环境下的context
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(newBase, language));
//            super.attachBaseContext(newBase);
    }

    /**
     * 纯文字提示
     *
     * @param message
     */
    private ShowMsgDialog msgDialog;

    public void showDialogMessageBase(String message) {
        Context context = getContext();
        if (null != msgDialog) {
            msgDialog.dismiss();
        }
//        if (msgDialog == null){
        msgDialog = new ShowMsgDialog.Builder(getContext())
                .setIconType(ShowMsgDialog.Builder.ICON_TYPE_NOTHING)
                .setTipWord(message)
                .create();
//        }
        msgDialog.show();
        msgDialog.dismiss();
//        new Handler().postDelayed(new Runnable(){
//            public void run() {
//                if (null != msgDialog && msgDialog.isShowing()){
//                    msgDialog.dismiss();
////                    msgDialog = null ;
//                }
//            }
//        }, 1000);
    }

    /**
     * 跳转
     *
     * @param bundle
     * @param cls
     */
    public void jumpActivity(Bundle bundle, Class<? extends Activity> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 从底部弹出
     *
     * @param bundle
     * @param cls
     */
    public void jumpBottomActivity(Bundle bundle, Class<? extends Activity> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }

    public void jumpActivityForResultBottom(Bundle bundle, Class<? extends Activity> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }

    public void jumpActivityForResult(Bundle bundle, Class<? extends Activity> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        overridePendingTransition(R.anim.translate_open_in, R.anim.translate_open_out);
        //overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w(TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    /**
     * 开启权限并跳转相册
     *
     * @param request_code
     */
    public void getCamera(final Activity context, final int request_code) {
        checkPermission(Manifest.permission.CAMERA, "请开启相机权限", new GuaranteeCallBack() {
            @Override
            public void onGuarantee() {
//                ImageSelectorActivity.start(context, 5, ImageSelectorActivity.MODE_MULTIPLE, true, true, false, request_code);
            }
        });
    }

    /**
     * 上传头像
     *
     * @param context
     * @param request_code
     */
    public void getCameraUser(final Activity context, final int request_code) {
        checkPermission(Manifest.permission.CAMERA, "请开启相机权限", new GuaranteeCallBack() {
            @Override
            public void onGuarantee() {
//                ImageSelectorActivity.start(context, 1, ImageSelectorActivity.MODE_MULTIPLE, true, true, true, request_code);
            }
        });
    }

    /**
     * 隐藏软键盘
     */
    public void hideImm() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 显示软键盘
     */
    public void showImm(EditText et) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDateNow() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 获取当前日期协议
     *
     * @return
     */
    public static String getDate() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getDateG() {
        //获取当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        return sf.format(d);
    }

    /**
     * 时间转换为时间戳
     *
     * @param time
     * @return
     */
    private static String dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return String.valueOf(ts);
    }

    /**
     * 设置TabLayout下划线长度
     *
     * @param
     * @param
     * @param
     */
//    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
//        Class<?> tabLayout = tabs.getClass();
//        Field tabStrip = null;
//        try {
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        tabStrip.setAccessible(true);
//        LinearLayout llTab = null;
//        try {
//            llTab = (LinearLayout) tabStrip.get(tabs);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
//        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
//
//        for (int i = 0; i < llTab.getChildCount(); i++) {
//            View child = llTab.getChildAt(i);
//            child.setPadding(0, 0, 0, 0);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.leftMargin = left;
//            params.rightMargin = right;
//            child.setLayoutParams(params);
//            child.invalidate();
//        }
//
//
//    }

//    public void reflex(final TabLayout tabLayout) {
//        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //拿到tabLayout的mTabStrip属性
//                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
//
//                    int dp10 = dip2px(tabLayout.getContext(), 10);
//
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//
//                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//
//                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//
//                        tabView.setPadding(0, 0, 0, 0);
//
//                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width = 0;
//                        width = mTextView.getWidth();
//                        if (width == 0) {
//                            mTextView.measure(0, 0);
//                            width = mTextView.getMeasuredWidth();
//                        }
//
//                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        params.width = width;
//                        params.leftMargin = dp10;
//                        params.rightMargin = dp10;
//                        tabView.setLayoutParams(params);
//
//                        tabView.invalidate();
//                    }
//
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }

    /*
     * bitmap中的透明色用白色替换
     *
     * @param bitmap
     * @return
     */
    public static Bitmap changeColor(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] colorArray = new int[w * h];
        int n = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int color = getMixtureWhite(bitmap.getPixel(j, i));
                colorArray[n++] = color;
            }
        }
        return Bitmap.createBitmap(colorArray, w, h, Bitmap.Config.ARGB_8888);
    }

    /**
     * 获取和白色混合颜色
     *
     * @return
     */
    private static int getMixtureWhite(int color) {
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(getSingleMixtureWhite(red, alpha), getSingleMixtureWhite

                        (green, alpha),
                getSingleMixtureWhite(blue, alpha));
    }

    /**
     * 获取单色的混合值
     *
     * @param color
     * @param alpha
     * @return
     */
    private static int getSingleMixtureWhite(int color, int alpha) {
        int newColor = color * alpha / 255 + 255 - alpha;
        return newColor > 255 ? 255 : newColor;
    }


    /**
     * EditText竖直方向是否可以滚动
     *
     * @param editText 需要判断的EditText
     * @return true：可以滚动   false：不可以滚动
     */
    public boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }


    public void showAsDropDown(PopupWindow pw, View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            // 7.1 版本处理
            if (Build.VERSION.SDK_INT == 25) {
                //【note!】Gets the screen height without the virtual key
                WindowManager wm = (WindowManager) pw.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
                int screenHeight = wm.getDefaultDisplay().getHeight();
                /*
                /*
                 * PopupWindow height for match_parent,
                 * will occupy the entire screen, it needs to do special treatment in Android 7.1
                */
                pw.setHeight(screenHeight - location[1] - anchor.getHeight() - yoff);
            }
            pw.showAtLocation(anchor, Gravity.NO_GRAVITY, xoff, location[1] + anchor.getHeight() + yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }

    /**
     * 退出时提示弹窗
     */
    public void showDaigolExit(final Activity activity) {
        new AlertDialog(activity).builder()
                .setTitle("确认退出当前页面吗？")
//                .setMsg("未保存的信息将会清空 ")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }

    /**
     * 退出时提示弹窗
     */
    public void showDaigolDetail(final Activity activity) {
        new AlertDialog(activity).builder()
                .setTitle("确认退出当前页面吗？")
                .setMsg("未保存的信息将会清空 ")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.finish();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .show();
    }

//    public void setLanguage( int language) {
//        //根据读取到存放在sp里面的数据 进行设置
//        Configuration configuration = getResources().getConfiguration();
//
//
//        switch (language) {
//            case 0:
//                configuration.setLocale(Locale.CHINESE);
//                break;
//            case 1:
//                configuration.setLocale(Locale.ENGLISH);
//                break;
//        }
//        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
//    }

    /**
     * 更改应用语言
     *
     * @param
     */
    public void changeAppLanguage(int language) {
        Locale locale = null;
        switch (language) {
            case 0:
                locale = Locale.CHINESE;
                break;
            case 1:
                locale = Locale.ENGLISH;
                break;
            case 2:
                locale = new Locale("th", "rTH");
                break;
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        //重新启动Activity
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
    }

    public void changeAppLanguage() {
//        String sta = LanguageStore.getLanguageLocal(this);
        String sta = "en";
        if (sta != null && !"".equals(sta)) {
            Locale myLocale = new Locale(sta);
            switch (sta) {
                case "default"://跟随系统
                    myLocale = Locale.getDefault();
                    break;
                case "zh_CN":
                    myLocale = Locale.SIMPLIFIED_CHINESE;
                    break;
                case "zh_TW": //自定义语言，参数1为语种代码，参数2为地区代码
                    myLocale = new Locale("zh", "TW");
                    break;
                case "en":
                    myLocale = Locale.ENGLISH;
                    break;
                case "zh_HK":
                    myLocale = new Locale("zh", "HK");
                    break;
            }
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            //更新配置
            res.updateConfiguration(conf, dm);
        }

    }

}
