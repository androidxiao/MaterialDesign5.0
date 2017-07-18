package  com.anxindeli.library.widget;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.anxindeli.library.R;
import com.anxindeli.library.utils.Px2DpUtil;

/**
 * 带清空按钮的EditText
 * Created by Adam_Lee on 2015/12/22.
 */
public class EditTextClear extends EditText implements TextWatcher, OnFocusChangeListener {

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFocus;
    private boolean showClear = true;

    private float mHintSize;
    private float mTextSize;

    private TextChangeWatcher mChangeWatcher;

    public void addTextChangeWatcher(TextChangeWatcher changeWatcher) {
        mChangeWatcher = changeWatcher;
    }

    public EditTextClear(Context context) {
        this(context, null);
    }

    public EditTextClear(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public EditTextClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.mipmap.text_clear_icon);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setCompoundDrawablePadding(Px2DpUtil.dp2px(context, 20)); // 间距20dp
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }
    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - (getTotalPaddingRight() - getCompoundDrawablePadding()))
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否显示清除按钮
     * @param showClear
     */
    public void setShowClear(boolean showClear) {
        this.showClear = showClear;
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus && showClear) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 设置提示文字和文本字体大小不同的方法
     * @param hintSize
     * @param textSize
     */
    public void setHintTextSize(float hintSize, float textSize) {
        mHintSize = hintSize;
        mTextSize = textSize;
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if(hasFocus && showClear){
            setClearIconVisible(s.length() > 0);
        }

        if(s.length() > 0) {
            if(mTextSize > 0) {
                setTextSize(mTextSize);
            }
        } else {
            if(mHintSize > 0) {
                setTextSize(mHintSize);
            }
        }

        if(mChangeWatcher != null) {
            mChangeWatcher.onTextChanged(getId(), s, start, count, after);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        if(mChangeWatcher != null) {
            mChangeWatcher.beforeTextChanged(getId(), s, start, count, after);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mChangeWatcher != null) {
            mChangeWatcher.afterTextChanged(getId(), s);
        }
    }

    /**
     * 监听文字变化
     */
    public interface TextChangeWatcher {
        void beforeTextChanged(int id, CharSequence s, int start, int count, int after);
        void onTextChanged(int id, CharSequence s, int start, int count, int after);
        void afterTextChanged(int id, Editable s);
    }

}
