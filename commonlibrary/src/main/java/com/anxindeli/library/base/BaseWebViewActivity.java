package com.anxindeli.library.base;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.anxindeli.library.R;


/**
 * Activity的基类，所有Activity都要继承此类
 * Created by Adam_Lee on 2015/9/2.
 */
public abstract class BaseWebViewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvLoading;
    private Animation mLoadingAnim;
    private TextView mTitleText;
    private View mLeftClose;

    /**
     * 需要接收H5的Title,设置TextView接收Title
     * 此方法应该在setWebView（）之前调用
     */
    protected void setReceivedTitle(TextView mTitleText) {
        this.mTitleText = mTitleText;
    }

    /**
     * 需要算是H5页加载完成,显示左侧关闭按钮
     * 此方法应该在setWebView（）之前调用
     */
    protected void setLeftCloseVisibility(View mLeftClose) {
        this.mLeftClose = mLeftClose;
    }

    /**
     * 设置WebView和LoadingView
     * @param mWvContent
     * @param mIvLoading
     */
    protected void setWebView(WebView mWvContent, ImageView mIvLoading){
        mLoadingAnim = AnimationUtils.loadAnimation(this, R.anim.loading_anim_icon);
        this.mIvLoading = mIvLoading;
        mWvContent.getSettings().setJavaScriptEnabled(true);
        mWvContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWvContent.getSettings().setDomStorageEnabled(true);
        mWvContent.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 自适应屏幕
        mWvContent.getSettings().setUseWideViewPort(true);
        mWvContent.getSettings().setLoadWithOverviewMode(true);

        mWvContent.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if(mTitleText != null && !TextUtils.isEmpty(title)) {
                    mTitleText.setText(title);
                }
            }
        });

        //关闭键控制
        if(mLeftClose != null) {
            mLeftClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 重写WebViewClient的方法
     */
    protected class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            mIvLoading.setVisibility(View.VISIBLE);
            mIvLoading.startAnimation(mLoadingAnim);
        }

        public void onPageFinished(WebView view, String url) {
            if(mLeftClose != null) {
                if(view.canGoBack()) {
                    mLeftClose.setVisibility(View.VISIBLE);
                } else {
                    mLeftClose.setVisibility(View.GONE);
                }
            }

            if(mTitleText != null) {
                mTitleText.setText(view.getTitle());
            }
            mIvLoading.clearAnimation();
            mIvLoading.setVisibility(View.GONE);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //handler.cancel(); 默认的处理方式，WebView变成空白页
            handler.proceed();//接受证书
            //handleMessage(Message msg); 其他处理
        }
    }

}
