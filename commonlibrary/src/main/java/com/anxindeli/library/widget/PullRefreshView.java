package com.anxindeli.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.anxindeli.library.R;
import com.anxindeli.library.utils.Px2DpUtil;

/**
 * 支持ScrollView/ListView下拉刷新的控件
 * Created by Adam_Lee on 2015/12/24.
 */
public class PullRefreshView extends LinearLayout {

	private enum Status {
		NORMAL, READY, SLIDING, REFRESHING,
	}

	private Status status = Status.NORMAL;

	private final static float MIN_MOVE_DISTANCE = 8.0f;// 最小移动距离，用于判断是否在下拉
	private Scroller mScroller;
	private int mRefreshTargetTop;
	private int lastY;
	private int lastX;

	private RelativeLayout mRefreshView;
	private ImageView mRefreshArrow;
	private TextView mRefreshHint;
	private ImageView mRefreshBar;

	private Animation mLoadingAnim;
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;

	private RefreshListener mRefreshListener;// 刷新监听器
	private MovementListener mMovementListener; // 上下滑动监听

	public PullRefreshView(Context context) {
		this(context, null);
	}

	public PullRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mRefreshTargetTop = -Px2DpUtil.dp2px(context, 60);

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

		// 滑动对象，
		mScroller = new Scroller(context);

		// 刷新视图顶端的的view
		mRefreshView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.refresh_header_view, null);
		mRefreshArrow = (ImageView) mRefreshView.findViewById(R.id.iv_arrow);
		mRefreshHint = (TextView) mRefreshView.findViewById(R.id.tv_hint_txt);
		mRefreshBar = (ImageView) mRefreshView.findViewById(R.id.progress_bar);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, -mRefreshTargetTop);
		lp.topMargin = mRefreshTargetTop;
		lp.gravity = Gravity.CENTER;
		addView(mRefreshView, lp);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (status == Status.REFRESHING)
//			return false;
		int action = event.getAction();
		int x = (int) event.getRawX();
		int y = (int)event.getRawY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 记录下y坐标
			lastX = x;
			lastY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// y移动坐标
			int m = y - lastY;
			int n = x -lastX;
			if(Math.abs(m) > Math.abs(n)) { // 当竖直距离大于横向距离时
				doMovement(m);
			}
			// 记录下此刻y坐标
			this.lastY = y;
			this.lastX = x;
			break;
		case MotionEvent.ACTION_UP:
			fling();
			break;
		}
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		// layout截取touch事件
		int action = e.getAction();
		int x = (int) e.getRawX();
		int y = (int) e.getRawY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lastY = y;
			lastX =x;
			break;
		case MotionEvent.ACTION_MOVE:
			// y移动坐标
			int m = y - lastY;
			int n = x -lastX;
			// 记录下此刻y坐标
			this.lastY = y;
			this.lastX = x;
			boolean isXY = Math.abs(m) > Math.abs(n);
			if (isXY && m > MIN_MOVE_DISTANCE && canScroll()) {
				return true;
			} else if(isXY && m < - MIN_MOVE_DISTANCE && canScroll() && status == Status.REFRESHING ) { //此处判断正加载，可以上滑
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	public boolean isRefreshing() {
		return status == Status.REFRESHING;
	}

	/**
	 * up事件处理
	 */
	private void fling() {
		LayoutParams lp = (LayoutParams) mRefreshView.getLayoutParams();
		if ((lp.topMargin > (mRefreshTargetTop * 2 / 3)) && status == Status.READY) {// 拉到了触发可刷新事件
			refresh();
		} else {
			returnInitState();
		}
	}

	/**
	 * 还原到初始状态
	 */
	private void returnInitState() {
		LayoutParams lp = (LayoutParams) this.mRefreshView.getLayoutParams();
		int i = lp.topMargin;
		mScroller.startScroll(0, i, 0, mRefreshTargetTop - i);
		status = Status.NORMAL;
		postInvalidate();
	}

	/**
	 * 执行刷新
	 * 
	 */
	private void refresh() {
		LayoutParams lp = (LayoutParams) this.mRefreshView.getLayoutParams();
		int i = lp.topMargin;
		mRefreshArrow.clearAnimation();
		mRefreshArrow.setVisibility(GONE);
		mRefreshBar.setVisibility(View.VISIBLE);
		mRefreshBar.startAnimation(mLoadingAnim);
		mRefreshHint.setText(R.string.listview_header_hint_loading);
		mScroller.startScroll(0, i, 0, 0 - i);
		status = Status.REFRESHING;
		postInvalidateDelayed(10);
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh(this);
		}
	}
	
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {// scroll 动作还未结束
			int i = this.mScroller.getCurrY();
			LayoutParams lp = (LayoutParams) this.mRefreshView.getLayoutParams();
			int k = Math.max(i, mRefreshTargetTop);
			lp.topMargin = k;
			this.mRefreshView.setLayoutParams(lp);
			postInvalidate();
		}
	}

	/**
	 * 下拉move事件处理
	 */
	private void doMovement(int moveY) {
		status = Status.SLIDING;
		LayoutParams lp = (LayoutParams) this.mRefreshView.getLayoutParams();
		float f1 = lp.topMargin;
		float f2 = moveY * 0.3f;// 以0.3比例拖动
		int i = (int) (f1 + f2);
		// 修改上边距
		lp.topMargin = i;
		// 修改后刷新
		mRefreshView.setLayoutParams(lp);
		mRefreshView.postInvalidate();
		postInvalidateDelayed(10);
		mRefreshBar.clearAnimation();
		mRefreshBar.setVisibility(View.GONE);
		mRefreshArrow.setVisibility(View.VISIBLE);
		if (lp.topMargin > 0) {
			mRefreshArrow.clearAnimation();
			mRefreshArrow.startAnimation(mRotateDownAnim);
			mRefreshHint.setText(R.string.listview_header_hint_ready);
			status = Status.READY;
		} else {
			mRefreshHint.setText(R.string.listview_header_hint_normal);
			status = Status.NORMAL;
		}
		if(mMovementListener != null) {
			mMovementListener.onMovement(moveY);
		}
	}

	/**
	 * 设置监听
	 */
	public void setRefreshListener(RefreshListener listener) {
		this.mRefreshListener = listener;
	}

	public void setMovementListener(MovementListener movementListener) {
		this.mMovementListener = movementListener;
	}

	/**
	 * 结束刷新事件
	 */
	public void finishRefresh() {
		LayoutParams lp = (LayoutParams) this.mRefreshView.getLayoutParams();
		int i = lp.topMargin;
		mRefreshArrow.setVisibility(View.VISIBLE);
		if (status == Status.READY) {
			mRefreshArrow.startAnimation(mRotateUpAnim);
		} else if (status == Status.REFRESHING) {
			mRefreshArrow.clearAnimation();
		}
		mRefreshBar.clearAnimation();
		mRefreshBar.setVisibility(View.GONE);
		mRefreshHint.setText(R.string.listview_header_hint_normal);
		mScroller.startScroll(0, i, 0, mRefreshTargetTop - i);
		status = Status.NORMAL;
		postInvalidate();
	}

	/**
	 * 主要作用是判断子View是否滚动到了最上面，若是，则表示此次touch move事件截取然后让layout来处理，来移动下拉视图，反之则不然
	 */
	private boolean canScroll() {
		if (getChildCount() > 1) {
			View childView = this.getChildAt(1);
			if (childView instanceof ListView) {
				int top = ((ListView) childView).getChildAt(0).getTop();
				int pad = ((ListView) childView).getListPaddingTop();
				if ((Math.abs(top - pad)) < 3
						&& ((ListView) childView).getFirstVisiblePosition() == 0) {
					return true;
				} else {
					return false;
				}
			} else if (childView instanceof ScrollView) {
				if (((ScrollView) childView).getScrollY() == 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 刷新监听接口
	 */
	public interface RefreshListener {
		void onRefresh(PullRefreshView view);
	}

	/**
	 * 滑动的距离
	 */
	public interface MovementListener{
		void onMovement(int moveY);
	}

}
