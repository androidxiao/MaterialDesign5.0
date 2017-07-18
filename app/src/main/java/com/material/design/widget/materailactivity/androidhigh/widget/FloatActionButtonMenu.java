package com.material.design.widget.materailactivity.androidhigh.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.material.design.widget.R;
import com.material.design.widget.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by chawei on 2017/2/24.
 */

public class FloatActionButtonMenu {

    private Context mContext;
    private FrameLayout mFrameLayout;
    private ImageButton mMainImageButton;
    private RotateAnimation mRotateOpenAnimation;
    private RotateAnimation mRotateCloseAnimation;
    private ArrayList<ImageButton> mPromotedActions;
    private ObjectAnimator mObjectAnimator[];
    private int px;
    private static final int ANIMATION_TIME=200;
    private boolean isMenuOpened;

    public void setUp(Context context, FrameLayout layout) {
        mContext=context;
        mPromotedActions = new ArrayList<>();
        mFrameLayout=layout;
        px = (int) context.getResources().getDimension(R.dimen.dimen_56)+10;
        openRotation();
        closeRotation();
        LogUtil.debug("setUp方法--------------》");
    }

    public ImageButton addMainItem(Drawable drawable){
        View view=LayoutInflater.from(mContext).inflate(R.layout.main_promoted_action_button, mFrameLayout, false);
        ImageButton button= (ImageButton) view.findViewById(R.id.id_ib_bottom);
        button.setImageDrawable(drawable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMenuOpened) {
                    closePromotedActions().start();
                    isMenuOpened=false;
                }else{
                    RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
                    params.width=FrameLayout.LayoutParams.MATCH_PARENT;
                    params.height=FrameLayout.LayoutParams.MATCH_PARENT;
                    mFrameLayout.setBackgroundResource(R.color.white_color);
                    mFrameLayout.setAlpha(0.5f);
                    mFrameLayout.setLayoutParams(params);
                    isMenuOpened=true;
                    openPromotedActions().start();
                }
            }

        });

        mFrameLayout.addView(button);
        mMainImageButton=button;
        return button;
    }

    public boolean getIsMenuOpen(){
        return isMenuOpened;
    }

    public void closeFloatMenu(){
        closePromotedActions().start();
        isMenuOpened=false;
    }


    public void addItem(Drawable drawable, View.OnClickListener onClickListener) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.promoted_action_button, mFrameLayout, false);
        ImageButton button= (ImageButton) view.findViewById(R.id.promoted_action_button);
        button.setImageDrawable(drawable);
        button.setOnClickListener(onClickListener);
        mPromotedActions.add(button);
        mFrameLayout.addView(button);
        return;
    }


    private void openRotation() {
        mRotateOpenAnimation = new RotateAnimation(0, 45, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateOpenAnimation.setFillAfter(true);
        mRotateOpenAnimation.setFillEnabled(true);
        mRotateOpenAnimation.setDuration(ANIMATION_TIME);
    }
    private void closeRotation() {
        mRotateCloseAnimation = new RotateAnimation(45, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateCloseAnimation.setFillAfter(true);
        mRotateCloseAnimation.setFillEnabled(true);
        mRotateCloseAnimation.setDuration(ANIMATION_TIME);
    }


    private AnimatorSet openPromotedActions() {
        if (mObjectAnimator == null) {
            objectAnimatorSetup();
        }

        AnimatorSet animation = new AnimatorSet();

        for(int i=0;i<mPromotedActions.size();i++) {
            mObjectAnimator[i] = setOpenAnimation(mPromotedActions.get(i), i);
        }

        if (mObjectAnimator.length == 0) {
            mObjectAnimator=null;
        }
        //playSequentially：串行执行动画
        //playTogether：并行执行动画
        animation.playTogether(mObjectAnimator);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mMainImageButton.startAnimation(mRotateOpenAnimation);
                mMainImageButton.setClickable(false);
                showPromotedActions();
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

        return animation;
    }

    private AnimatorSet closePromotedActions() {
        if (mObjectAnimator == null) {
            objectAnimatorSetup();
        }

        AnimatorSet animation = new AnimatorSet();

        for(int i=0;i<mPromotedActions.size();i++) {
            mObjectAnimator[i] = setCloseAnimation(mPromotedActions.get(i), i);
        }

        if (mObjectAnimator.length == 0) {
            mObjectAnimator=null;
        }

        animation.playTogether(mObjectAnimator);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mMainImageButton.startAnimation(mRotateCloseAnimation);
                mMainImageButton.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mMainImageButton.setClickable(true);
                hidePromotedActions();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                mMainImageButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        return animation;
    }

    private void objectAnimatorSetup(){
        mObjectAnimator = new ObjectAnimator[mPromotedActions.size()];
    }

    private ObjectAnimator setCloseAnimation(ImageButton promoteAction,int position) {
        ObjectAnimator objectAnimator;

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //ofFloat()方法的第一个参数表示动画操作的对象（可以是任意对象），
            // 第二个参数表示操作对象的属性名字（只要是对象有的属性都可以），
            // 第三个参数之后就是动画过渡值。当然过度值可以有一个到N个，
            // 如果是一个值的话默认这个值是动画过渡值的结束值。
            // 如果有N个值，动画就在这N个值之间过渡。
            objectAnimator = ObjectAnimator.ofFloat(promoteAction, View.TRANSLATION_Y, -px * (mPromotedActions.size() - position),0f);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (mPromotedActions.size() - position));
        }else{
            objectAnimator = ObjectAnimator.ofFloat(promoteAction, View.TRANSLATION_X, -px * (mPromotedActions.size() - position),0f);
            objectAnimator.setRepeatCount(0);
            objectAnimator.setDuration(ANIMATION_TIME * (mPromotedActions.size() - position));
        }
        return objectAnimator;
    }

    private ObjectAnimator setOpenAnimation(ImageButton promotedAction, int position) {
        ObjectAnimator objectAnimation;

        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            objectAnimation = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_Y, 0f, -px * (mPromotedActions.size() - position));
            objectAnimation.setRepeatCount(0);
            objectAnimation.setDuration(ANIMATION_TIME * (mPromotedActions.size()-position));
        }else{
            objectAnimation = ObjectAnimator.ofFloat(promotedAction, View.TRANSLATION_X, 0f, -px * (mPromotedActions.size() - position));
            objectAnimation.setRepeatCount(0);
            objectAnimation.setDuration(ANIMATION_TIME * (mPromotedActions.size() -position));
        }
        return objectAnimation;
    }

    private void hidePromotedActions(){
        for(int i=0;i<mPromotedActions.size();i++) {
            mPromotedActions.get(i).setVisibility(View.GONE);
            if (i == mPromotedActions.size()-1) {
                RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mFrameLayout.getLayoutParams();
                params.width=FrameLayout.LayoutParams.WRAP_CONTENT;
                params.height=FrameLayout.LayoutParams.WRAP_CONTENT;
                mFrameLayout.setBackground(null);
                mFrameLayout.setLayoutParams(params);
            }
        }
    }

    private void showPromotedActions(){
        for(int i=0;i<mPromotedActions.size();i++) {
            mPromotedActions.get(i).setVisibility(View.VISIBLE);
        }
    }



}
