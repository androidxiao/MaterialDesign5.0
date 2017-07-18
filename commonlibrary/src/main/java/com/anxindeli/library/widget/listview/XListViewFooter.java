package com.anxindeli.library.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anxindeli.library.R;


/**
 * XLisView上拉加载更多的布局
 */
public class XListViewFooter extends LinearLayout {

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int STATE_HINT = 3;

	private ImageView mFooterIcon;
	private Animation mLoadingAnim;
	private TextView mHintView;
	private RelativeLayout mRlFooterContent;

	public XListViewFooter(Context context) {
		this(context, null);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		mLoadingAnim = AnimationUtils.loadAnimation(context, R.anim.loading_anim_icon);

		LinearLayout mMoreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_footer, this);
		mRlFooterContent = (RelativeLayout) mMoreView.findViewById(R.id.rl_footer_content);
		mFooterIcon = (ImageView) mMoreView.findViewById(R.id.iv_footer_icon);
		mHintView = (TextView) mMoreView.findViewById(R.id.tv_footer_hint);
	}

	/**
	 * 设置状态和文本
	 * @param state
	 * @param textId
	 */
	public void setState(int state, int textId) {
		if (state == STATE_NORMAL) {
			mFooterIcon.clearAnimation();
			mFooterIcon.setVisibility(View.GONE);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(textId);
		} else if (state == STATE_READY) {
			mFooterIcon.setVisibility(View.GONE);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(textId);
		} else if (state == STATE_LOADING) {
			mHintView.setVisibility(View.GONE);
			mFooterIcon.setVisibility(View.VISIBLE);
			mFooterIcon.startAnimation(mLoadingAnim);
		} else if (state == STATE_HINT) {
			mFooterIcon.clearAnimation();
			mFooterIcon.setVisibility(View.GONE);
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(textId);
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0) return;
		LayoutParams lp = (LayoutParams) mRlFooterContent.getLayoutParams();
		lp.bottomMargin = height;
		mRlFooterContent.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams) mRlFooterContent.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mFooterIcon.clearAnimation();
		mHintView.setVisibility(View.GONE);
		mFooterIcon.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
		mFooterIcon.setVisibility(View.VISIBLE);
		mFooterIcon.startAnimation(mLoadingAnim);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) mRlFooterContent.getLayoutParams();
		lp.height = 0;
		mRlFooterContent.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams) mRlFooterContent.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mRlFooterContent.setLayoutParams(lp);
	}
	
}
