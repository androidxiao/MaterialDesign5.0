package com.anxindeli.library.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.anxindeli.library.R;

/**
 * 通用的加载Loading
 * Created by Adam_Lee on 2015/9/25.
 */
public class CommonLoadingView extends PopupWindow {

    private Animation mLoadingAnim;
    private ImageView mIvLoading;

    public CommonLoadingView(Context context) {
        super(context);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setOutsideTouchable(false);
        this.setBackgroundDrawable(new BitmapDrawable()); // 不响应back键时直接Null
        this.setFocusable(true);
        initView(context);
    }

    private void initView(Context context) {

        mLoadingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_anim_icon);

        RelativeLayout mainLayout = (RelativeLayout) View.inflate(context, R.layout.common_loading_view, null);
        mIvLoading = (ImageView) mainLayout.findViewById(R.id.iv_loading);
        // 添加视图
        this.setContentView(mainLayout);
    }

    /**
     * 显示
     * @param parent
     */
    public void show(View parent) {
        mIvLoading.startAnimation(mLoadingAnim);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
    }

    @Override
    public void dismiss() {
        mIvLoading.clearAnimation();
        super.dismiss();
    }

}
