package com.intest.thailand.v2x.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.adapter.SpinnerAdapter;
import com.library.base.adapter.ZX_HomeItem;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.adapter.base_adapter.CommonRcvAdapter;
import com.library.base.adapter.base_adapter.rcv.RcvOnItemClickListener;
import com.library.base.entity.bean.TestBean;
import com.library.base.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class PopWindowUtils {
    private Activity activity;
    private Context context;
    private static PopWindowUtils mPopWindowUtils;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RecyclerView popRecyclerView;

    public PopWindowUtils(Context context) {
        this.context = context;
        this.activity = (Activity) context;
    }

    public static PopWindowUtils getInstance(Context context) {
        if (mPopWindowUtils == null) {
            mPopWindowUtils = new PopWindowUtils(context);
        }
        return mPopWindowUtils;
    }

    //根据view初始化popupWindow
    public PopupWindow generatePopupWindow(PopWindowHomeInterface popWindowHomeInterface) {
//       View popView = LayoutInflater.from(this).inflate(R.layout.pop, null);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.pop_window_home, null);
        final PopupWindow popupwindow = new PopupWindow(customView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupwindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupwindow.setOutsideTouchable(true);
//        popupwindow.setAnimationStyle(R.style.popup_animation);
        popupwindow.setFocusable(true);
        //弹出框消失，筛选条颜色和箭头归位
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
//                bgAlpha(1f);
            }
        });
        initPop(customView, popupwindow, popWindowHomeInterface);
        return popupwindow;
    }

    /**
     * 设置界面透明度，可以模仿pop背景透明
     *
     * @param bgAlpha
     */
    public void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 初始化pop里面的控件
     *
     * @param viewGroup
     * @param popupwindow
     */
    private void initPop(View viewGroup, final PopupWindow popupwindow, PopWindowHomeInterface popWindowHomeInterface) {
        popRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.popRecyclerView);
        View popDismissView = (View) viewGroup.findViewById(R.id.popDismissView);
        recyclerList(null, popupwindow, popWindowHomeInterface);
        popDismissView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupwindow != null) {
                    popupwindow.dismiss();
                }
            }
        });
    }

    /**
     * 绑定列表
     *
     * @param result
     */
    private void recyclerList(List<TestBean> result, final PopupWindow popupwindow, final PopWindowHomeInterface popWindowHomeInterface) {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        CommonRcvAdapter<TestBean> adapter = new CommonRcvAdapter<TestBean>(result = result == null ? new ArrayList<TestBean>() : result) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new SpinnerAdapter(context);
            }
        };
        popRecyclerView.setLayoutManager(manager);
        popRecyclerView.setAdapter(adapter);
        for (int i = 0; i < 5; i++) {
            TestBean testBean = new TestBean();
            testBean.setName("宝马" + i);
            result.add(testBean);
        }
        adapter.setData(result);
        final List<TestBean> finalResult = result;
        popRecyclerView.addOnItemTouchListener(new RcvOnItemClickListener(context, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (popWindowHomeInterface != null) {
                    popWindowHomeInterface.popSelectItem(finalResult.get(position));
                }
                if (popupwindow != null) {
                    popupwindow.dismiss();
                }
            }
        }));
    }

    public interface PopWindowHomeInterface {
        void popSelectItem(TestBean testBean);
    }

    /**
     * 显示popWindow
     *
     * @param pw
     * @param anchor
     * @param xoff
     * @param yoff
     */
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

}
