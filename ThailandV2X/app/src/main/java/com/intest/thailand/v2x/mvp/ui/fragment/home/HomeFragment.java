package com.intest.thailand.v2x.mvp.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.mvp.ui.MainActivity;
import com.intest.thailand.v2x.widget.PopWindowUtils;
import com.library.base.adapter.ZX_HomeItem;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.adapter.base_adapter.CommonRcvAdapter;
import com.library.base.base.BaseMVPLazyFragment;
import com.library.base.entity.bean.TestBean;
import com.library.base.widget.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseMVPLazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.statusBar_view)
    View statusBarView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.seleteCar_tv)
    TextView seleteCarTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = createView(inflater, container, R.layout.fragment_home);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void fetchData() {
        ViewGroup.LayoutParams params = statusBarView.getLayoutParams();
        params.height = MainActivity.statusBarHeight / 2;
        statusBarView.setLayoutParams(params);
    }

    @Override
    public void init() {

    }

    @Override
    public void setData() {
        recyclerList(null);
    }

    @Override
    public void addListeners() {

    }

    PopWindowUtils mPopWindowText;

    @OnClick({R.id.seleteCar_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.seleteCar_tv://选择车辆
                PopupWindow popupWindow = PopWindowUtils.getInstance(getActivity()).generatePopupWindow((PopWindowUtils.PopWindowHomeInterface) getActivity());
                PopWindowUtils.getInstance(getActivity()).showAsDropDown(popupWindow, seleteCarTv, 0, 10);
                break;
        }
    }

    public void popSelectItem(TestBean testBean) {
        seleteCarTv.setText(testBean.getName());
    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * Spinner完全自定义UI和绑定数据
     */
//    private void testSpinnerSelf() {
//        ArrayList<TestBean> cars = new ArrayList<>();
//        TestBean car = new TestBean();
//        car.setName("玛莎拉蒂");
//        cars.add(car);
//        car = new TestBean();
//        car.setName("宝马");
//        cars.add(car);
//
//        final SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), cars);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                Toast.makeText(getActivity(), ((TestBean) adapter.getItem(pos)).toString(), Toast.LENGTH_SHORT);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

    CommonRcvAdapter<TestBean> adapter;

    private void recyclerList(List<TestBean> result) {
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CommonRcvAdapter<TestBean>(result = result == null ? new ArrayList<TestBean>() : result) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new ZX_HomeItem(getActivity());
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        setDatas();
    }

    private void setDatas() {
        List<TestBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestBean testBean = new TestBean();
            testBean.setName("标题" + i);
            list.add(testBean);
        }
        adapter.setData(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
