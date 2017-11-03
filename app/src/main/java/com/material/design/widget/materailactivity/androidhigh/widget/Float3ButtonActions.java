package com.material.design.widget.materailactivity.androidhigh.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.material.design.widget.R;
import com.material.design.widget.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by chawei on 2017/2/27.
 */

public class Float3ButtonActions {

    private Context mContext;
    private int ANIMATOR_TIME = 200;
    private int mBottomPx;
    private FrameLayout mFrameLayout;
    private ArrayList<ImageButton> mImageButtons;
    private ObjectAnimator[][] mObjectAnimators;
    private ImageButton mMainImageButton;
    private boolean isMenuOpen;
    private int left2Px;
    private int midTopPx;
    private int right2Px;
    private int widthHalf;
    private int viewId=1;
    public Float3ButtonActions(Context context, FrameLayout frameLayout) {
        mContext = context;
        mFrameLayout = frameLayout;
        mBottomPx = (int) mContext.getResources().getDimension(R.dimen.dimen_56);
        mImageButtons = new ArrayList<>();

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        LogUtil.debug("width------>"+width);
        LogUtil.debug("height------>"+height);
        widthHalf = width / 2;
        left2Px = widthHalf / 2;
    }

    public void addMainItem(Drawable drawable) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.float_main_3_button_actions_layout, mFrameLayout, false);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.id_ib_bottom);
        imageButton.setImageDrawable(drawable);
        mMainImageButton = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMenuOpen) {
                    hideMenuObjectAnimator();
                    isMenuOpen = false;
                } else {

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
                    params.width = FrameLayout.LayoutParams.MATCH_PARENT;
                    params.height = FrameLayout.LayoutParams.MATCH_PARENT;
                    mFrameLayout.setBackgroundResource(android.R.color.holo_blue_bright);
                    mFrameLayout.setAlpha(0.7f);
                    mFrameLayout.setLayoutParams(params);

                    showMenuObjectAnimator();
                    isMenuOpen = true;
                }
            }
        });
        mFrameLayout.addView(imageButton);
    }

    public boolean getIsMenuOpen(){
        return isMenuOpen;
    }

    public void closeFloatMenu(){
        hideMenuObjectAnimator();
        isMenuOpen=false;
    }

    public void addMenuItem(Drawable drawable, View.OnClickListener clickListener) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.float_menu_3_button_actions_layout, mFrameLayout, false);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.promoted_action_button);
        imageButton.setImageDrawable(drawable);
        imageButton.setOnClickListener(clickListener);
        mFrameLayout.addView(imageButton);
        imageButton.setTag(viewId);
        viewId++;
        mImageButtons.add(imageButton);
    }

    private void showMenuObjectAnimator() {
        if (mObjectAnimators == null) {
            initObjectAnimator();
        }

        AnimatorSet animatorSet = new AnimatorSet();

        for (int i = 0; i < mImageButtons.size(); i++) {
//            mObjectAnimators[i] = setShowMenuAnimator(mImageButtons.get(i), i);
            animatorSet.playTogether(setShowMenuAnimator(mImageButtons.get(i), i));
        }

        animatorSet.setDuration(300);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mMainImageButton.setClickable(false);
                visibleMenu();
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mMainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mMainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    private void hideMenuObjectAnimator() {
        if (mObjectAnimators == null) {
            initObjectAnimator();
        }

        AnimatorSet animatorSet = new AnimatorSet();
        for (int i = 0; i < mImageButtons.size(); i++) {
//            mObjectAnimators[i] = setHideMenuAnimator(mImageButtons.get(i), i);
            animatorSet.playTogether(setHideMenuAnimator(mImageButtons.get(i), i));
        }

        animatorSet.setDuration(300);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mMainImageButton.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mMainImageButton.setClickable(true);
                goneMenu();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mMainImageButton.setClickable(true);
                goneMenu();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    private ObjectAnimator[] setShowMenuAnimator(ImageButton imageButton, int position) {
        if (position == 0) {
            right2Px = -widthHalf / 2;
            midTopPx=-widthHalf/2;
        }

        if (position == 1) {
            midTopPx = 0;
        }

        if (position == 2) {
            midTopPx = widthHalf / 2;
        }
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageButton, View.TRANSLATION_X, 0, midTopPx);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageButton, View.TRANSLATION_Y, 0, right2Px);
        ObjectAnimator[] objectAnimators = new ObjectAnimator[2];
        objectAnimators[0] = objectAnimator1;
        objectAnimators[1] = objectAnimator2;
        return objectAnimators;
    }

    private ObjectAnimator[] setHideMenuAnimator(ImageButton imageButton, int position) {
        if (position == 0) {
            left2Px = -widthHalf / 2;
            midTopPx=-widthHalf/2;
        }

        if (position == 1) {
            midTopPx = 0;
        }

        if (position == 2) {
            midTopPx = widthHalf / 2;
        }

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(imageButton, View.TRANSLATION_X, midTopPx, 0);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(imageButton, View.TRANSLATION_Y, left2Px, 0);
        ObjectAnimator[] objectAnimators = new ObjectAnimator[2];
        objectAnimators[0] = objectAnimator1;
        objectAnimators[1] = objectAnimator2;
        return objectAnimators;
    }


    private void initObjectAnimator() {
        mObjectAnimators = new ObjectAnimator[mImageButtons.size()][];
    }

    private void visibleMenu() {
        for (int i = 0; i < mImageButtons.size(); i++) {
            //添加文字不带动画，效果会好点，如果没有文字，可以添加动画
//            ImageButton textView = mImageButtons.get(i);
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) textView.getLayoutParams();
//            params.setMargins(0,0,0,mBottomPx*(mImageButtons.size()-i));
//            textView.setLayoutParams(params);
            mImageButtons.get(i).setVisibility(View.VISIBLE);
        }
    }

    private void goneMenu() {
        for (int i = 0; i < mImageButtons.size(); i++) {
            mImageButtons.get(i).setVisibility(View.GONE);
        }
        mFrameLayout.setBackground(null);
    }


}
