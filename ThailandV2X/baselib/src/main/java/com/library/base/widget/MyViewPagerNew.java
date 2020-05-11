package com.library.base.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ChenKang on 2017/8/10.
 */
public class MyViewPagerNew extends ViewPager {

    private Context context;
    // 是否滑动 ;默认启动
    private boolean scrollble = true;

    public MyViewPagerNew(Context context) {
        super(context);
        this.context = context;
    }

    public MyViewPagerNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (scrollble) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (scrollble) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }

    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

