package com.intest.thailand.v2x.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intest.thailand.v2x.R;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.entity.bean.TestBean;


public class SpinnerAdapter implements AdapterItem<TestBean> {

    private Context ctx;
    ImageView item_car_logo_iv , item_car_down_iv;
    TextView item_car_name_tv;

    public SpinnerAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_spinner_home;
    }

    @Override
    public void bindViews(View view) {
        item_car_logo_iv = (ImageView) view.findViewById(R.id.item_car_logo_iv);
        item_car_down_iv = (ImageView) view.findViewById(R.id.item_car_down_iv);
        item_car_name_tv = (TextView) view.findViewById(R.id.item_car_name_tv);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(TestBean testBean, int position) {
        item_car_name_tv.setText(testBean.getName());
    }

}
