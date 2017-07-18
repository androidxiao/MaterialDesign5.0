package com.material.design.widget.widget;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Scroller;

import com.material.design.widget.R;

import java.lang.ref.WeakReference;

/**
 * Created by chawei on 2017/2/8.
 */

public class HeaderScrollingBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private static final String TAG = "demo";
    private boolean isExpanded=false;
    private boolean isScrolling=false;

    /**dependentView是依赖视图的一个弱引用**/
    private WeakReference<View> dependentView;
    /**用来实现用户释放手指后的滑动动画**/
    private Scroller mScroller;
    /**用来驱动Scroller的运行**/
    private Handler mHandler;


    public HeaderScrollingBehavior(Context context, AttributeSet attrs){
        super(context, attrs);
        mScroller=new Scroller(context);
        mHandler=new Handler();
    }

    public boolean isExpanded(){
        return isExpanded;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        if(dependency!=null&&dependency.getId()== R.id.scrolling_header){
            dependentView = new WeakReference<View>(dependency);
            return true;
        }
        return false;
    }

    /**
     * 重写该方法为了使RecycleView在HeaderView的下方
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams lp= (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
//            Log.d(TAG, "onLayoutChild: "+parent.getWidth()+"--->"+parent.getHeight());
//            child.layout(0,0,parent.getWidth(),(int)(parent.getHeight()-getDependentViewCollapsedHeight()));
            child.layout(0,0,parent.getWidth(),(int)(parent.getHeight()+getDependentViewCollapsedHeight()));
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        Resources resources=getDependentView().getResources();
        final float progress = 1.f - Math.abs(dependency.getTranslationY() / (dependency.getHeight() - resources.getDimension(R.dimen.collapsed_header_height)));
        child.setTranslationY(dependency.getHeight()+dependency.getTranslationY());
        float scale = 1 + 0.4f * (1.f - progress);
        dependency.setScaleX(scale);
        dependency.setScaleY(scale);
        dependency.setAlpha(progress);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {

        return (nestedScrollAxes& ViewCompat.SCROLL_AXIS_VERTICAL)!=0;
    }

    /**
     * 只有onStartNestedScroll返回true时，才会执行该方法。
     * 可以做一些准备工作，例如停止动画。
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     */
    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        mScroller.abortAnimation();
        isScrolling=false;
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    /**
     * 子视图开始嵌套滑动之前，调用该方法
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        Log.d(TAG, "onNestedPreScroll: 111111111111111");
        if (dy < 0) {
            return;
        }

        View dependentView = getDependentView();
        float newTranslateY=dependentView.getTranslationY()-dy;
        float minHeaderTranslate = -(dependentView.getHeight() - getDependentViewCollapsedHeight());

        if (newTranslateY > minHeaderTranslate) {
            dependentView.setTranslationY(newTranslateY);
            consumed[1]=dy;
        }
    }

    /**
     * 子视图开始嵌套滚动时调用该方法
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        Log.d(TAG, "onNestedScroll: 222222222222222222");
        if (dyUnconsumed > 0) {
            return;
        }
        
        View dependentView = getDependentView();
        float newTranslateY=dependentView.getTranslationY()-dyUnconsumed;
        final float maxHeaderTranslate=0;

        if (newTranslateY < maxHeaderTranslate) {
            dependentView.setTranslationY(newTranslateY);
        }

    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY) {
        return onUserStopDragging(velocityY);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target) {
        if (!isScrolling) {
            onUserStopDragging(800);
        }
    }

    private boolean onUserStopDragging(float velocity) {
        View dependentView = getDependentView();
        float translateY=dependentView.getTranslationY();
        float minHeaderTranslate = -(dependentView.getHeight() - getDependentViewCollapsedHeight());

        if (translateY == 0 || translateY == minHeaderTranslate) {
            return false;
        }

        boolean targetState;//是否展开内容
        if (Math.abs(velocity) <= 800) {
            if (Math.abs(translateY) < Math.abs(translateY - minHeaderTranslate)) {
                targetState=false;
            }else{
                targetState=true;
            }
            velocity=800;//限制速度的最小值
        }else{
            if (velocity > 0) {
                targetState=true;
            }else{
                targetState=false;
            }
        }

        float targetTranslateY=targetState?minHeaderTranslate:0;

        mScroller.startScroll(0, (int) translateY, 0, (int) (targetTranslateY - translateY), (int) (1000000 / Math.abs(velocity)));
        mHandler.post(flingRunnable);
        isScrolling=true;
        return true;
    }

    private Runnable flingRunnable=new Runnable() {
        @Override
        public void run() {
            if (mScroller.computeScrollOffset()) {
                getDependentView().setTranslationY(mScroller.getCurrY());
                mHandler.post(this);
            }else{
                isExpanded=getDependentView().getTranslationY()!=0;
                isScrolling=false;
            }
        }
    };

    private float getDependentViewCollapsedHeight(){
        return getDependentView().getResources().getDimension(R.dimen.collapsed_header_height);

    }

    private View getDependentView(){
        return dependentView.get();
    }
}
