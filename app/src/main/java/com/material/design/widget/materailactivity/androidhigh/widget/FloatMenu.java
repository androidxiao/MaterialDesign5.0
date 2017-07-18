package com.material.design.widget.materailactivity.androidhigh.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.design.widget.R;
import com.material.design.widget.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by chawei on 2017/2/24.
 */

public class FloatMenu {

    private Context mContext;
    private TextView mTvMain;
    private FrameLayout mFrameLayout;
    private RotateAnimation mOpenRotateAnimation;
    private RotateAnimation mCloseRotateAnimation;
    private ArrayList<TextView> mTextViews;
    private ArrayList<RelativeLayout> mRelativeLayouts;
    private int px;
    private int ANIMATOR_TIME = 200;
    private boolean isMenuOpen;
    private View mMainView;
    private ImageButton mImageButton;
    private Drawable[] mDrawables;
    private String[] mStr;

    public FloatMenu(Context context, FrameLayout frameLayout) {
        mContext = context;
        mFrameLayout = frameLayout;
        px = (int) mContext.getResources().getDimension(R.dimen.dimen_56) + 10;
        mTextViews = new ArrayList<>();
        mRelativeLayouts = new ArrayList<>();
        initDrawableWithStr();
        initOpenMainFloatAnimator();
        initCloseMainFloatAnimator();
    }

    public void addMainItem(Drawable drawable, String str) {
        mMainView = LayoutInflater.from(mContext).inflate(R.layout.floatmenu_textview_main_item_layout, mFrameLayout, false);
        mTvMain = (TextView) mMainView.findViewById(R.id.id_tv_main_item);
        mImageButton = (ImageButton) mMainView.findViewById(R.id.id_ib);
        mTvMain.setText(str);
        mImageButton.setImageDrawable(drawable);

        mFrameLayout.addView(mMainView);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.debug("Main Item22222222222222 ");
                if (isMenuOpen) {

                    hideMenuItemAnimator();
                    isMenuOpen = false;
                } else {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
                    params.width = FrameLayout.LayoutParams.MATCH_PARENT;
                    params.height = FrameLayout.LayoutParams.MATCH_PARENT;
                    mFrameLayout.setBackgroundResource(android.R.color.holo_blue_bright);
                    mFrameLayout.setAlpha(0.7f);
                    mFrameLayout.setLayoutParams(params);


                    showMenuItemAnimator();
                    isMenuOpen = true;
                }
            }

        });
    }

    public boolean getIsMenuOpen() {
        return isMenuOpen;
    }

    public void closeFloatMenu() {
        hideMenuItemAnimator();
        isMenuOpen = false;
    }

    private void initCloseMainFloatAnimator() {
        mCloseRotateAnimation = new RotateAnimation(45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mCloseRotateAnimation.setDuration(ANIMATOR_TIME);
        mCloseRotateAnimation.setFillAfter(true);
        mCloseRotateAnimation.setFillEnabled(true);
    }

    private void initOpenMainFloatAnimator() {
        mOpenRotateAnimation = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mOpenRotateAnimation.setDuration(ANIMATOR_TIME);
        mOpenRotateAnimation.setFillAfter(true);
        mOpenRotateAnimation.setFillEnabled(true);
    }

    private void initDrawableWithStr(){
        mStr = new String[]{"编辑", "发送", "输入"};
        mDrawables = new Drawable[]{
                mContext.getResources().getDrawable(android.R.drawable.ic_menu_edit)
                , mContext.getResources().getDrawable(android.R.drawable.ic_menu_send)
                , mContext.getResources().getDrawable(android.R.drawable.ic_input_get)};
    }



    private void addMenuItem() {
        for (int i = 0; i < mStr.length; i++) {
            View view1 = LayoutInflater.from(mContext).inflate(R.layout.floatmenu_textview_item_layout, mFrameLayout, false);
            TextView tv1 = (TextView) view1.findViewById(R.id.id_tv_item_item);
            tv1.setText(mStr[i]);
            ImageButton imageButton1 = (ImageButton) view1.findViewById(R.id.id_ib);
            imageButton1.setImageDrawable(mDrawables[i]);
            RelativeLayout relativeLayout1 = (RelativeLayout) view1.findViewById(R.id.id_rl);
            FrameLayout.LayoutParams params1 = (FrameLayout.LayoutParams) relativeLayout1.getLayoutParams();
            params1.setMargins(0, 0, 0, (px * (mDrawables.length-i)));
            relativeLayout1.setLayoutParams(params1);
            mFrameLayout.addView(view1);
            mRelativeLayouts.add(relativeLayout1);
        }
    }

    private void showMenuItemAnimator() {
        mImageButton.startAnimation(mOpenRotateAnimation);
        addMenuItem();
        visibleMenuItem();
    }

    private void hideMenuItemAnimator() {
        mImageButton.startAnimation(mCloseRotateAnimation);
        goneMenuItem();
    }

    private void visibleMenuItem() {
        for (int i = 0; i < mRelativeLayouts.size(); i++) {
            //添加文字不带动画，效果会好点，如果没有文字，可以添加动画
//            RelativeLayout textView = mRelativeLayouts.get(i);
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
//            params.setMargins(0,0,0,px*(mTextViews.size()-i));
//            textView.setLayoutParams(params);
            mRelativeLayouts.get(i).setVisibility(View.VISIBLE);
        }

    }

    private void goneMenuItem() {
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(mMainView);
        mFrameLayout.setBackground(null);
    }
}
