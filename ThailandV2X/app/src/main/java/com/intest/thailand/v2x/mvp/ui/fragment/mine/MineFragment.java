package com.intest.thailand.v2x.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.adapter.CardViewAdapter;
import com.intest.thailand.v2x.widget.ScaleTransformerCardView;
import com.library.base.base.BaseMVPLazyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MineFragment extends BaseMVPLazyFragment {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = createView(inflater, container, R.layout.fragment_mine);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void fetchData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.icon_login_logo);
        list.add(R.mipmap.icon_login_logo);
        list.add(R.mipmap.icon_login_logo);
        list.add(R.mipmap.icon_login_logo);
        list.add(R.mipmap.icon_login_logo);
        CardViewAdapter adapter = new CardViewAdapter(getActivity(), list);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(list.size());
        //        viewpager.setPageMargin(10);
        viewpager.setPageTransformer(false, new ScaleTransformerCardView(getActivity()));
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
