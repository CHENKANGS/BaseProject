package com.library.base.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.library.base.R;
import com.library.base.adapter.base_adapter.AdapterItem;
import com.library.base.base.BaseKcActivity;
import com.library.base.entity.bean.TestBean;

/**
 * @Class
 * @Author 邮箱
 * @Description (实现的主要功能)
 * @Date Administratoron 2017/4/1 08:56
 * @Upate 修改者:;修改日期:;修改内容:.
 */
public class ZX_HomeItem implements AdapterItem<TestBean> {

    private Context context;
    private ImageView iv_url;
    private TextView zx_tv_title,zx_tv_excerpt,zx_tv_hot , zx_wd_tv;
    private String mType ;
    private RelativeLayout rl_itemLayout ;

    public ZX_HomeItem(Context context ) {
        this.context = context;
    }
    public ZX_HomeItem(Context context ,String mType) {
        this.context = context;
        this.mType = mType;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_zx_list_new;
    }

    @Override
    public void bindViews(View view) {
        zx_tv_title = (TextView) view.findViewById(R.id.zx_tv_title);
        zx_tv_excerpt = (TextView) view.findViewById(R.id.zx_tv_excerpt);
        zx_tv_hot = (TextView) view.findViewById(R.id.zx_tv_hot);
        zx_wd_tv = (TextView) view.findViewById(R.id.zx_wd_tv);
        iv_url = (ImageView) view.findViewById(R.id.iv_url);
        rl_itemLayout = (RelativeLayout) view.findViewById(R.id.rl_itemLayout);
    }

    @Override
    public void setViews() {

    }

    @Override
    public void handleData(final TestBean newsBean, int position) {
        zx_tv_title.setText(newsBean.getName());
//        zx_tv_title.setText(newsBean.getTitle());
////        zx_tv_excerpt.setText(zx_insideItemModel.getExcerpt());
//        zx_tv_hot.setText(newsBean.getCreatedAt());
//
//        if ("1".equals(mType)) {//内部资讯
//            zx_wd_tv.setVisibility(View.VISIBLE);
//            if("0".equals(newsBean.getRead())){//0未读,1已读
//                zx_wd_tv.setText("未读");
//                zx_wd_tv.setBackgroundResource(R.drawable.zx_yd_tv_bg);
//            }else {
//                zx_wd_tv.setText("已读");
//                zx_wd_tv.setBackgroundResource(R.drawable.zx_wd_tv_bg);
//            }
//        }
//
//        if (null != newsBean.getPic()){
//            Glide.with(context)
//                    .load(newsBean.getPic().getThumb())
//                    .apply(BaseKcActivity.getOptions())
////                .bitmapTransform(new RoundedCornersTransformation(context, 16, 0))//设置圆角
////                    .diskCacheStrategy(DiskCacheStrategy.ALL)  //让Glide既缓存全尺寸又缓存其他尺寸
//                    .into(iv_url);
//        }

    }

    /**
     * 延时操作二
     */
    public void delayedShow(){
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //1s后执行代码
                zx_wd_tv.setText("已读");
                zx_wd_tv.setBackgroundResource(R.drawable.zx_wd_tv_bg);
            }
        }, 1000);
    }
}
