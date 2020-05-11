package com.library.base.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.library.base.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by allen on 2017/4/12.
 */

public class GlideUtils {
    private static GlideUtils mInstance;

    private GlideUtils() {
    }

    public static GlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 常量
     */
    static class Constants {
        static final int BLUR_VALUE = 20; //模糊
        static final int CORNER_RADIUS = 20; //圆角
        static final float THUMB_SIZE = 0.5f; //0-1之间  10%原图的大小
    }

    //glide配置
    RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.img_load)
            .error(R.mipmap.img_load)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    /**
     * 常规加载图片
     *
     * @param context
     * @param imageView 图片容器
     * @param imgUrl    图片地址
     * @param isFade    是否需要动画
     */
    public void loadImage(Context context, ImageView imageView,
                          String imgUrl, boolean isFade) {
        if (isFade) {
//            Glide.with(context)
//                    .load(imgUrl)
//                    .error(R.mipmap.img_load)
//                    .crossFade()
//                    .priority(Priority.NORMAL) //下载的优先级
//                    //all:缓存源资源和转换后的资源 none:不作任何磁盘缓存
//                    //source:缓存源资源   result：缓存转换后的资源
//                    .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略
//                    .into(imageView);
            Glide.with(context)
                    .load(imgUrl)
                    .apply(options)
//                    .thumbnail(Glide.with(context).load(imgUrl))
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imgUrl)
                    .apply(options)
                    .into(imageView);
        }
    }

    /**
     * 加载缩略图
     *
     * @param context
     * @param imageView 图片容器
     * @param imgUrl    图片地址
     */
    public void loadThumbnailImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .thumbnail(Constants.THUMB_SIZE)
                .into(imageView);
    }

    /**
     * 加载图片并设置为指定大小
     *
     * @param context
     * @param imageView
     * @param imgUrl
     * @param withSize
     * @param heightSize
     */
    public void loadOverrideImage(Context context, ImageView imageView,
                                  String imgUrl, int withSize, int heightSize) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_load)
                .error(R.mipmap.img_load)
                .priority(Priority.HIGH)
                .override(withSize, heightSize)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载图片并对其进行模糊处理
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurImage(Context context, ImageView imageView, String imgUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_load)
                .error(R.mipmap.img_load)
                .priority(Priority.HIGH)
//                .bitmapTransform(new BlurTransformation(context, Constants.BLUR_VALUE))
                .bitmapTransform(new BlurTransformation(Constants.BLUR_VALUE, Constants.BLUR_VALUE))
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆图
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadCircleImage(Context context, ImageView imageView, String imgUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_load)
                .error(R.mipmap.img_load)
                .priority(Priority.HIGH)
                .bitmapTransform(new CropCircleTransformation())
//                .bitmapTransform(new CropCircleTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载模糊的圆角的图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurCircleImage(Context context, ImageView imageView, String imgUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_load)
                .error(R.mipmap.img_load)
                .priority(Priority.HIGH)
//                .bitmapTransform(
//                        new BlurTransformation(context, Constants.BLUR_VALUE),
//                        new CropCircleTransformation(context))
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
//                .bitmapTransform(
//                        new BlurTransformation(context, Constants.BLUR_VALUE),
//                        new CropCircleTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadCornerImage(Context context, ImageView imageView, String imgUrl) {
//        Glide.with(context)
//                .load(imgUrl)
//                .error(R.mipmap.img_load)
//                .crossFade()
//                .priority(Priority.NORMAL) //下载的优先级
//                .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略
//                .bitmapTransform(
//                        new RoundedCornersTransformation(
//                                context, Constants.CORNER_RADIUS, Constants.CORNER_RADIUS))
//                .into(imageView);
    }

    /**
     * 加载模糊的圆角图片
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadBlurCornerImage(Context context, ImageView imageView, String imgUrl) {
//        Glide.with(context)
//                .load(imgUrl)
//                .error(R.mipmap.img_load)
//                .crossFade()
//                .priority(Priority.NORMAL) //下载的优先级
//                .diskCacheStrategy(DiskCacheStrategy.ALL) //缓存策略
//                .bitmapTransform(
//                        new BlurTransformation(context, Constants.BLUR_VALUE),
//                        new RoundedCornersTransformation(
//                                context, Constants.CORNER_RADIUS, Constants.CORNER_RADIUS))
//                .into(imageView);
    }

    /**
     * 同步加载图片
     *
     * @param context
     * @param imgUrl
     * @param target
     */
    public void loadBitmapSync(Context context, String imgUrl, SimpleTarget<Bitmap> target) {
        Glide.with(context)
                .asBitmap()
                .load(imgUrl)
                .apply(options)
                .into(target);
    }

    /**
     * 加载gif
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadGifImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .asGif()
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载gif
     *
     * @param context
     * @param imageView
     * @param imgId
     */
    public void loadGifImage(Context context, ImageView imageView, int imgId) {
        Glide.with(context)
                .asGif()
                .load(imgId)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载gif的缩略图
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public void loadGifThumbnailImage(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context)
                .asGif()
                .load(imgUrl)
                .apply(options)
                .thumbnail(Constants.THUMB_SIZE)
                .into(imageView);
        /** * 注意：在Glide 3中的语法是先load()再asBitmap()的，
         *  * * 而在Glide 4中是先asBitmap()再load()的
         *  * * 如果写错了顺序就肯定会报错了
         *  */
//        Glide.with(context)
//        //.asBitmap()//强制指定加载静态图片
//         .asGif()//强制指定加载动态图片
//        // .asFile()// 强制指定文件格式的加载
////         .asDrawable()// 强制指定Drawable格式的加载
//         .load("file:///android_asset/jdfw.gif") .into(imageView);
    }

    /**
     * 恢复请求，一般在停止滚动的时候
     *
     * @param context
     */
    public void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }

    /**
     * 暂停请求 正在滚动的时候
     *
     * @param context
     */
    public void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();//清理磁盘缓存 需要在子线程中执行
            }
        }).start();
    }

    /**
     * 清除内存缓存
     *
     * @param context
     */
    public void clearMemory(Context context) {
        Glide.get(context).clearMemory();//清理内存缓存  可以在UI主线程中进行
    }
}
