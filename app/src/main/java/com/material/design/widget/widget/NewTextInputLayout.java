package com.material.design.widget.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;

/**
 * Created by chawei on 2017/2/13.
 *
 * 为了去除输入错误时，edittet的选中效果（去除选中的背景颜色）。
 */

public class NewTextInputLayout extends TextInputLayout {
    public NewTextInputLayout(Context context) {
        super(context);
    }

    public NewTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        recoverEditTextBackGround();
    }

    @Override
    public void setError(@Nullable CharSequence error) {
        super.setError(error);
        recoverEditTextBackGround();
    }

    /**
     * 将背景颜色重置
     */
    private void recoverEditTextBackGround(){
        if (getEditText() == null) {
            return;
        }
        Drawable editTextBackground = getEditText().getBackground();
        if (editTextBackground == null) {
            return;
        }
        if (android.support.v7.widget.DrawableUtils.canSafelyMutateDrawable(editTextBackground)) {
            editTextBackground = editTextBackground.mutate();
        }
        DrawableCompat.clearColorFilter(editTextBackground);
    }
}
