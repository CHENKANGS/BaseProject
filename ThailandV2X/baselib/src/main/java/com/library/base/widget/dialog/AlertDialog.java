package com.library.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.library.base.R;

/*

 * Description: 

 * File: AlertDialog.java

 * Author: k

 * Version: V100R001C01

 * Create: 2018/1/26 17:37

 *

 * Changes (from 2018/1/26)

 * -----------------------------------------------------------------

 * 2018/1/26 : Changes AlertDialog.java (k);

 * -----------------------------------------------------------------

 */
public class AlertDialog {

    private Context context;
    private Dialog dialog;
    private FrameLayout customLayout;
    private TextView txt_title;
    private TextView txt_msg;
    private View line;
    private Button btn_add;
    private Button btn_neg;
    private Button btn_pos;
    private Display display;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showLayout = false;
    private boolean showAddBtn = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public AlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_alertdialog, null);
        // 获取自定义Dialog布局中的控件
        CardView layout = (CardView) view.findViewById(R.id.layout);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        customLayout = (FrameLayout) view.findViewById(R.id.custom_layout);
        customLayout.setVisibility(View.GONE);
        line = view.findViewById(R.id.line);
        line.setVisibility(View.GONE);
        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_add.setVisibility(View.GONE);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        layout.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public AlertDialog setView(View view) {
        showLayout = true;
        if (view == null) {
            showLayout = false;
        } else
            customLayout.addView(view,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT);
        return this;
    }

    public AlertDialog setIcon(int resId) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        txt_title.setCompoundDrawables(drawable, null, null, null);
        return this;
    }

    public AlertDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if ("".equals(msg)) {
            txt_msg.setText("内容");
        } else {
            txt_msg.setText(msg);
        }
        return this;
    }


    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AlertDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public AlertDialog setOnDismissListener(DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
        return this;
    }


    public AlertDialog setAddButton(String text, final View.OnClickListener listener) {
        showAddBtn = true;
        if ("".equals(text)) {
            btn_add.setText("添加");
        } else {
            btn_add.setText(text);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public AlertDialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }
        btn_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public AlertDialog setNegativeButton(String text,
                                         final View.OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener)
                    listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public void dismiss() {
        dialog.dismiss();
    }


    private void setLayout() {
        if (!showTitle && !showMsg) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (showLayout) {
            customLayout.setVisibility(View.VISIBLE);
        }

        if (showAddBtn){
            btn_add.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.color.white);
            btn_pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.color.white);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.color.white);
            line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.color.white);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.color.white);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }


}
