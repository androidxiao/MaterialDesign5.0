package com.material.design.widget.materailactivity.androidhigh.animation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.material.design.widget.R;
import com.material.design.widget.widget.NumberView;

import static android.animation.ValueAnimator.INFINITE;
import static android.animation.ValueAnimator.REVERSE;
import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * 视图动画
 * Created by chawei on 2017/2/22.
 */

public class ViewAnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_animator_layout);

        View view=findViewById(R.id.id_view);
        final TextView tv= (TextView) findViewById(R.id.id_tv_number);
        Button btnTranslation= (Button) findViewById(R.id.id_btn_translation);
        Button btnRotation= (Button) findViewById(R.id.id_btn_rotation);
        Button btnScale = (Button) findViewById(R.id.id_btn_scale);
        Button btnAlpha = (Button) findViewById(R.id.id_btn_alpha);
        Button btnSet = (Button) findViewById(R.id.id_btn_set);
        ValueAnimator color = ObjectAnimator.ofInt(view, "backgroundColor", new int[]{0xff000000, 0xffd71345});
        color.setDuration(3000);
        color.setRepeatCount(INFINITE);
        color.setRepeatMode(REVERSE);
        color.setEvaluator(new ArgbEvaluator());//估值器，针对颜色
//        color.start();

        AnimatorSet set=new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view,"rotationX",0,360)
                ,ObjectAnimator.ofFloat(view,"rotationY",0,180)
                ,ObjectAnimator.ofFloat(view,"translationX",0,90)
                ,ObjectAnimator.ofFloat(view,"translationY",90,190)
                ,ObjectAnimator.ofFloat(view,"scaleX",1,2.0f)
                );
        set.setDuration(5000);
//        set.start();

        ValueAnimator valueAnimator=ObjectAnimator.ofFloat(tv,"Text",0,1000.0f);
        valueAnimator.setDuration(5000);
        tv.setText("0");
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();

        ValueAnimator value= new ValueAnimator();
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                tv.setText(String.format("%.2f",animator.getAnimatedValue()));
            }
        });
        value.setFloatValues(0,1000.0f);
        value.setDuration(3000);
        value.setInterpolator(new AccelerateInterpolator());
        value.start();


        NumberView numberView= (NumberView) findViewById(R.id.id_tv);
        numberView.showNumberWithAnimation(1000);

        //视图动画只能作用于View，而且视图动画改变的只是View的绘制效果，View真正的属性并没有改变

        Animation translateAnimation = loadAnimation(this, R.anim.btn_translate);
        btnTranslation.setAnimation(translateAnimation);

        Animation rotateAnimation = loadAnimation(this, R.anim.btn_rotate);
        btnRotation.setAnimation(rotateAnimation);

        Animation scaleAnimation = loadAnimation(this, R.anim.btn_scale);
        btnScale.setAnimation(scaleAnimation);

        Animation alphaAnimation = loadAnimation(this, R.anim.btn_alpha);
        btnAlpha.setAnimation(alphaAnimation);

        Animation setAnimation=AnimationUtils.loadAnimation(this,R.anim.btn_set);
        btnSet.setAnimation(setAnimation);
    }


}
