package com.intest.thailand.v2x.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.intest.thailand.v2x.R;

import java.util.List;
import java.util.PriorityQueue;

public class CardViewAdapter extends PagerAdapter {
    private List<Integer> list;
    private Context context;
    private LayoutInflater inflater;
    private ImageView item_cardIv ;

    public CardViewAdapter(Context context, List<Integer> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = inflater.inflate(R.layout.item_card_view, container, false);
        item_cardIv = view.findViewById(R.id.item_cardIv);
        item_cardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context , "点了" + position , Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}