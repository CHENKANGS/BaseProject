package com.intest.thailand.v2x.mvp.ui.fragment.car;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.widget.ClockView;
import com.library.base.base.BaseMVPLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CarConditionFragment extends BaseMVPLazyFragment {

    @BindView(R.id.clock_view)
    ClockView clockView;
    Unbinder unbinder;
    @BindView(R.id.clock_view2)
    ClockView clockView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = createView(inflater, container, R.layout.fargment_car_condition);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void fetchData() {
        clockView.setCompleteDegree(32.25f);       //设置指针最终位置，附带动画效果
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String aaa = "50.12";
                float num1 = Float.valueOf(aaa);
                clockView2.setNumAnimator(num1);
            }
        },2000);
    }

    @Override
    public void init() {
    }

    @Override
    public void setData() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
