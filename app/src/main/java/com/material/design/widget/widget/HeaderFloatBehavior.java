package com.material.design.widget.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.material.design.widget.R;

import java.lang.ref.WeakReference;

/**
 * Created by chawei on 2017/2/8.
 */

public class HeaderFloatBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "demo";
    private WeakReference<View> dependentView;
    private ArgbEvaluator argbEvaluator;

    public HeaderFloatBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.scrolling_header) {
            dependentView = new WeakReference<>(dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Resources resources = getDependentView().getResources();
        final float progress = 1.f -
                Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.collapsed_header_height)));

//        Log.d(TAG, "onDependentViewChanged: "+dependency.getTranslationY()+"----->"+dependency.getHeight()+"--->"+(dependency.getHeight() - resources.getDimension(R.dimen.collapsed_header_height)));

        // Translation
        //推到顶之后搜索框与标题栏的Y轴偏移量（与标题栏的距离）
        final float collapsedOffset = resources.getDimension(R.dimen.collapsed_float_offset_y);
        //初始化时搜索框与标题栏的Y轴偏移量（距标题栏的距离）
        final float initOffset = resources.getDimension(R.dimen.init_float_offset_y);
        final float translateY =  collapsedOffset+ (initOffset - collapsedOffset) * progress;
        child.setTranslationY(translateY);

        // Background
        child.setBackgroundColor((int) argbEvaluator.evaluate(
                progress,
                resources.getColor(R.color.colorCollapsedFloatBackground),
                resources.getColor(R.color.colorInitFloatBackground)));

        // Margins
        //初始化时搜索框默认的margin值
        final float collapsedMargin = resources.getDimension(R.dimen.collapsed_float_margin);
        final float initMargin = resources.getDimension(R.dimen.init_float_margin);
        final int margin = (int) (collapsedMargin + (initMargin - collapsedMargin) * progress);
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        lp.setMargins(margin, 0, margin, 0);
        child.setLayoutParams(lp);

        return true;
    }

    private View getDependentView() {
        return dependentView.get();
    }

}