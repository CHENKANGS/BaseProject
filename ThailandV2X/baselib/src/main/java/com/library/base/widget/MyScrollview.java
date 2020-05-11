package com.library.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.library.base.widget.refresh.RefreshLayout;

/**
 * @Class
 * @Author 邮箱
 * @Description (实现的主要功能)
 * @Date Administratoron 2017/1/14 10:29
 * @Upate 修改者:;修改日期:;修改内容:.
 */
public class MyScrollview extends ScrollView {
    private int downX;
    private int downY;
    private int mTouchSlop;

    public MyScrollview(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) e.getRawY();
                if (Math.abs(moveY - downY) > mTouchSlop) {
                    return true;
                }
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onScrollChange(l, t, oldl, oldt);
        }
    }

    OnScrollChangeListener mListener;
    public interface OnScrollChangeListener {
        void onScrollChange(int l, int t, int oldl, int oldt) ;
    }
    public void setScrollChangeListener(OnScrollChangeListener listener) {
        mListener = listener;
    }


//    private float mDownPosX = 0;
//    private float mDownPosY = 0;
//
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final float x = ev.getX();
//        final float y = ev.getY();
//
//        final int action = ev.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mDownPosX = x;
//                mDownPosY = y;
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                final float deltaX = Math.abs(x - mDownPosX);
//                final float deltaY = Math.abs(y - mDownPosY);
//                // 这里是否拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//
//                if (deltaX > deltaY) {// 左右滑动不拦截
//                    return false;
//                }
//        }
//
//        return super.onInterceptTouchEvent(ev);
//    }

}
