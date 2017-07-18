package com.anxindeli.library.utils;

import android.content.Context;
import android.widget.Toast;

import com.anxindeli.library.widget.CommonToast;

/**
 * Toast显示工具类
 * Created by Adam_Lee on 2015/9/6.
 */
public class ToastUtil {

    private ToastUtil() {}

    /**
     * 显示一个短时的Toast提示
     * @param context
     * @param text
     */
    public static void showShortToast(Context context, String text) {
        CommonToast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长时的Toast提示
     * @param context
     * @param text
     */
    public static void showLongToast(Context context, String text) {
        CommonToast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示一个短时的Toast提示
     * @param context
     * @param text
     */
    public static void showShortToastNetError(Context context, String text) {
        CommonToast.makeTextNetError(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示一个长时的Toast提示
     * @param context
     * @param text
     */
    public static void showLongToastNetError(Context context, String text) {
        CommonToast.makeTextNetError(context, text, Toast.LENGTH_SHORT).show();
    }


}
