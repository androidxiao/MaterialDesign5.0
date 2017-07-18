package com.material.design.widget.materailactivity.androidhigh.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.material.design.widget.R;
import com.material.design.widget.materailactivity.androidhigh.utils.ViewWrapper;
import com.material.design.widget.utils.LogUtil;

import static android.animation.AnimatorInflater.loadAnimator;

/**
 * 属性动画
 * Created by chawei on 2017/2/23.
 */

public class PropertyAnimationActivity extends AppCompatActivity{

    private Button mBtnValueAnim;
    private Button mBtnObjectAnim;
    private Button mBtnPropertyAnimSet;
    private ImageView mIvRotate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_property_animation_layout);

        mBtnValueAnim = (Button) findViewById(R.id.id_btn_value_anim);
        mBtnObjectAnim = (Button) findViewById(R.id.id_btn_object_animation);
        mBtnPropertyAnimSet = (Button) findViewById(R.id.id_btn_property_anim_set);
        mIvRotate = (ImageView) findViewById(R.id.id_iv_arrow_rotate);

        mIvRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotateAnimation.setDuration(3000);
                rotateAnimation.setFillAfter(true);
                mIvRotate.setAnimation(rotateAnimation);
                mIvRotate.startAnimation(rotateAnimation);

                rotateAnimation.start();
                LogUtil.debug("imageView");
            }
        });

    }

    public void valueAnimatorClick(View view){
        ValueAnimator valueAnimator = (ValueAnimator) loadAnimator(this, R.animator.btn_animator);
        final int maxWidth = getWindowManager().getDefaultDisplay().getWidth();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //animator.getAnimatedValue()：的值是valueFrom->valueTo的值；
                // 当前是100，99，98，97，96，。。。。。。22，21，20。
                int width= (int) animator.getAnimatedValue();
                //最大宽度到最小宽度的渐变
                mBtnValueAnim.getLayoutParams().width= maxWidth*width/100;
                mBtnValueAnim.requestLayout();
            }
        });
        valueAnimator.start();
    }

    public void objectAnimatorClick(View view) {
        ObjectAnimator objectAnimator= (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.btn_object_animator);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        ViewWrapper viewWrapper = new ViewWrapper(mBtnObjectAnim, width);
        objectAnimator.setTarget(viewWrapper);
        objectAnimator.start();


//        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        rotateAnimation.setDuration(3000);
//        rotateAnimation.setFillAfter(true);
//        mIvRotate.setAnimation(rotateAnimation);
//        rotateAnimation.startNow();

        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillAfter(true);
        mIvRotate.setAnimation(rotateAnimation);
        rotateAnimation.startNow();
        Toast.makeText(PropertyAnimationActivity.this, "onClick", Toast.LENGTH_SHORT).show();
    }

    public void objectAnimSet(View view) {
        AnimatorSet animatorSet= (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.btn_set);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        ViewWrapper viewWrapper = new ViewWrapper(mBtnPropertyAnimSet, width);
        animatorSet.setTarget(viewWrapper);
        animatorSet.start();
    }

    public void imageViewRotate180(View view){
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(3000);
        mIvRotate.setAnimation(rotateAnimation);
        rotateAnimation.startNow();
        LogUtil.debug("imageView");

    }

}
