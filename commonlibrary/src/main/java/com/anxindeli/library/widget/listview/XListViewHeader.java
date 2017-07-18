package com.anxindeli.library.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anxindeli.library.R;


/**
 * ListView下拉刷新头控件
 * Created by Adam_Lee on 2015/10/09.
 */
public class XListViewHeader extends LinearLayout {

	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private ImageView mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mLoadingAnim;
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		this(context, null);
	}

	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {

		mLoadingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_anim_icon);
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(180);
		mRotateUpAnim.setFillAfter(true);

		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(180);
		mRotateDownAnim.setFillAfter(true);

		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ImageView) findViewById(R.id.xlistview_header_progressbar);
	}

	public void setState(int state) {
		if (state == mState) {
//			mProgressBar.clearAnimation();
			return;
		}
		if (state == STATE_REFRESHING) { // 显示进度
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // 显示箭头图片
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
		}
		switch (state) {
		case STATE_NORMAL:
			mArrowImageView.startAnimation(mRotateDownAnim);
			mProgressBar.clearAnimation();
			mHintTextView.setText(R.string.listview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mProgressBar.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.listview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.listview_header_hint_loading);
			break;
		default:
			break;
		}
		startAnim();
		mState = state;
	}
	
	private void startAnim() {
		if (mProgressBar.isShown()) {
			mProgressBar.startAnimation(mLoadingAnim);
		}
	}

	public void setVisibleHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisibleHeight() {
		return mContainer.getHeight();
	}

}
