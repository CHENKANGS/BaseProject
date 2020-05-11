package com.library.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class BaseMainAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment ;

    public BaseMainAdapter(FragmentManager fm , List<Fragment> listFragment) {
        super(fm);
        this.listFragment = listFragment ;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

}
