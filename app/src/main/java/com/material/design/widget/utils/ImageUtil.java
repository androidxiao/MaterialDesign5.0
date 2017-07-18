package com.material.design.widget.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

import com.material.design.widget.R;

/**
 * 图片工具
 * Created by Adam_Lee on 2015/9/18.
 */
public class ImageUtil {

    private ImageUtil() {
    }

    /**
     * 从View里得动态得到图片
     * @param view
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static BitmapDrawable getViewImage(View view, int y, int width, int height) {
        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(), 0, y, width, height);
        view.setDrawingCacheEnabled(false);
        BitmapDrawable bd = new BitmapDrawable(bmp);
        bd.setAntiAlias(true);
        return bd;
    }


    /**
     * 为SVG图片着色（小于4.4）或EditText背景色
     * @param context
     * @param resId
     * @param colorId
     * @return
     */
    public static Drawable tintDrawableSVG(Context context, int resId, int colorId) {
        Resources resources = context.getResources();
        Resources.Theme theme = context.getTheme();
        VectorDrawableCompat compat = VectorDrawableCompat.create(resources, resId, theme);
        Drawable mutate = compat.mutate();
        ColorStateList colorStateList = context.getResources().getColorStateList(colorId);
        final Drawable wrappedDrawable = DrawableCompat.wrap(mutate);
        DrawableCompat.setTintList(wrappedDrawable, colorStateList);
        return wrappedDrawable;
    }

    /**
     * 为图片着色（大于或者小于4.4，drawable或SVG该方法都可用）或EditText背景色
     * @param context
     * @param resId
     * @param colorId
     * @return
     */
    public static Drawable tintDrawableNormal(Context context, int resId, int colorId) {
        final Drawable originalBitmapDrawable = context.getResources().getDrawable(resId).mutate();
        ColorStateList colorStateList = context.getResources().getColorStateList(colorId);
        final Drawable wrappedDrawable = DrawableCompat.wrap(originalBitmapDrawable);
        DrawableCompat.setTintList(wrappedDrawable, colorStateList);
        return wrappedDrawable;
    }
}
