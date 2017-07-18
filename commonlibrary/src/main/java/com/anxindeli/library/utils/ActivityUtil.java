package com.anxindeli.library.utils;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Activity管理工具
 * Created by Adam_Lee on 2015/12/23.
 */
public class ActivityUtil {

    private static Stack<Activity> activityStack = new Stack<Activity>();

    private ActivityUtil() {}

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishLastActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (activity.equals(next)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public static void finishActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (next.getClass().equals(cls)) {
                next.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 移除指定的Activity
     */
    public static void removeActivity(Activity activity) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (activity.equals(next)) {
                iterator.remove();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public static void finishAllActivity() {
        for (Activity mActivity : activityStack) {
            mActivity.finish();
        }
        activityStack.clear();
    }

    /**
     * 关闭除指定Activity外的所有Activity
     */
    public static void finishExceptActivity(Class<?> cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            String acName = activity.getClass().getName();
            if (acName.equals(cls.getName())) {
                continue;
            } else {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 删除多个activity
     *
     * @param activities
     */
    public static void finishMoreActivity(Class<?>... activities) {
        for (Class<?> activity : activities)
            finishActivity(activity);
    }

}