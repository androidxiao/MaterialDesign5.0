package com.anxindeli.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.anxindeli.library.R;
import com.anxindeli.library.utils.ActivityUtil;
import com.anxindeli.library.utils.ToastUtil;
import com.anxindeli.library.widget.CommonLoadingView;

/**
 * Activity的基类，所有Activity都要继承此类
 * Created by Adam_Lee on 2015/9/2.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private CommonLoadingView mLoadingView;
    private final Handler mLockHandler = new Handler();
    private boolean mHasInited; // Activity是否初始化完View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewData();
        initLayoutView();
        ActivityUtil.addActivity(this);
    }

    /**
     * 在View初始化之前，需要初始化数据的，实现此方法
     */
    public abstract void beforeViewData();

    /**
     * 实现此方法，必需先加载化布局文件，再并初始化View, 并设置需要点击的控件的监听
     */
    public abstract void initLayoutView();

    /**
     * 请求网络数据实现此方法
     */
    public abstract void requestNetData();


    /**
     * 重写该方法加入进出动画
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_to_left, R.anim.out_to_right);
    }

    /**
     * 和setResult()方法一样
     * @param intent
     * @param resultCode
     */
    public void setResultForAnimation(Intent intent, int resultCode) {
        super.setResult(resultCode, intent);
        finish();
    }

    /**
     * 重写该方法加入进出动画
     */
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.exit_to_right, R.anim.exit_to_left);
    }

    /**
     *  重写该方法加入进出动画
     * @param intent
     * @param requestCode
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.in_to_left, R.anim.out_to_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * 隐藏键盘
     */
    protected void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (null != imm && imm.isActive()) {
            if (null != getCurrentFocus()) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @Override
    public void onAttachedToWindow() {
        mHasInited  = true;
        requestNetData();
    }

    /**
     * 显示加载Loading
     */
    public void showLoadingView() {
        if(mHasInited) {
            if(mLoadingView == null) {
                mLoadingView = new CommonLoadingView(this);
            }
            mLoadingView.show(getWindow().getDecorView());
        }
    }

    /**
     * 关闭加载Loading
     */
    public void dismissLoadingView() {
        if(mLoadingView != null && mLoadingView.isShowing()) {
            mLoadingView.dismiss();
            mLoadingView = null;
        }
    }

    /**
     * 短时Toast
     * @param text
     */
    public void showShortToast(String text) {
       ToastUtil.showShortToast(this, text);
    }

    /**
     * 长时Toast
     * @param text
     */
    public void showLongToast(String text) {
        ToastUtil.showLongToast(this, text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingView();
        mHasInited = false;
        ActivityUtil.removeActivity(this);
    }

}
