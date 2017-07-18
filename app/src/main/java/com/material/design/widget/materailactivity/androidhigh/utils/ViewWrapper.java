package com.material.design.widget.materailactivity.androidhigh.utils;

import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by chawei on 2017/2/23.
 */

public class ViewWrapper {

    private View target;
    private int maxWidth;

    public  ViewWrapper(View view, int width) {
        target=view;
        maxWidth=width;
    }

    public int getWidth(){
        return target.getLayoutParams().width;
    }

    //隐示调用该方法
    public void setWidth(int widthValue){
        target.getLayoutParams().width=maxWidth*widthValue/100;
        target.requestLayout();
    }

    public void setMarginTop(int margin){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) target.getLayoutParams();
        params.setMargins(0, margin, 0, 0);
        target.setLayoutParams(params);
    }

    public int setHeight(){
        return target.getLayoutParams().height;
    }

    public void setMarginLeft(int margin){
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) target.getLayoutParams();
        params.setMargins(margin, 0, 0, 0);
        target.setLayoutParams(params);
    }
}
