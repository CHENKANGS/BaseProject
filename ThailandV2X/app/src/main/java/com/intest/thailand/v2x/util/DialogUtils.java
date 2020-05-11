package com.intest.thailand.v2x.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.intest.thailand.v2x.R;
import com.intest.thailand.v2x.adapter.SpinnerAdapter;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.adapter.base_adapter.CommonRcvAdapter;
import com.library.base.adapter.base_adapter.rcv.RcvOnItemClickListener;
import com.library.base.entity.bean.TestBean;

import java.util.ArrayList;
import java.util.List;

public class DialogUtils {
    public static Dialog mDialog = null;
    public static void showDialog(final Context context, final DialogHomeInterface dialogHomeInterface) {
        if (null != mDialog) {
            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = null;
        }
        mDialog = new Dialog(context, R.style.dialogStyle);//R.style.dialogToastButtomDialog
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.pop_window_home);
        RecyclerView recyclerView = mDialog.findViewById(R.id.popRecyclerView);
        List<TestBean> mList = null;
        LinearLayoutManager linearManager = new LinearLayoutManager(context);
        linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        CommonRcvAdapter<TestBean> adapter = new CommonRcvAdapter<TestBean>(mList = mList == null ? new ArrayList<TestBean>() : mList) {
            @NonNull
            @Override
            public AdapterItem createItem(Object type) {
                return new SpinnerAdapter(context);
            }
        };
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < 5; i++) {
            TestBean testBean = new TestBean();
            testBean.setName("标题" + i);
            mList.add(testBean);
        }
        adapter.setData(mList);
        final List<TestBean> finalMList = mList;
        recyclerView.addOnItemTouchListener(new RcvOnItemClickListener(context, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dialogHomeInterface != null) {
                    dialogHomeInterface.seleteItem(finalMList.get(position).getName());
                    mDialog.dismiss();
                }
            }
        }));
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.TOP);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.y = 100;
        lp.width = dm.widthPixels;
        window.setAttributes(lp);
        mDialog.show();
    }

    public interface DialogHomeInterface {
        void seleteItem(String name);
    }

}
