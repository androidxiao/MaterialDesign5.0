package com.material.design.widget.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

/**
 * Created by chawei on 2017/2/22.
 */

public class NumberView extends android.support.v7.widget.AppCompatTextView {

    //动画时长 ms
    int duration = 5000;
    float number;

    public NumberView(Context context) {
        super(context);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 属性动画生效的前提：
     * <p>
     * 1.对象必须提供 setXXX 方法（XXX为属性名称），还要提供有 getXXX 方法（如果你的动画没有传递初始值的话）。
     * 2.对象的 setXXX 方法会对属性 XXX 进行某种UI改变，比如背景颜色。
     * <p>
     * 问：属性动画生效怎么调用getXXX方法的？
     * <p>
     * 答：反射
     *
     * @param number
     */
    public void showNumberWithAnimation(float number) {
        //修改number属性，会调用setNumber方法
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "number1", 0, number);
        objectAnimator.setDuration(duration);
        //加速器，从慢到快到再到慢
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    public float getNumber1() {
        return number;
    }

    /**
     * 方法名必须与propertyName(合法的变量就行)相同
     * 本例：setXXX--->setNumber1与number1
     *
     * @param number
     */
    public void setNumber1(float number) {
        this.number = number;
        setText(String.format("%.2f", number));
    }
}
