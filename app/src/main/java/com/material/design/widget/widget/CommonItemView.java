package com.material.design.widget.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.material.design.widget.R;
import com.material.design.widget.utils.ImageUtil;


/**
 * 财富页通用的Item
 * Created by Adam_Lee on 2015/9/21.
 */
public class CommonItemView extends RelativeLayout {

    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvHint;
    private View mCutLine;
    private ImageView mIvArrow;

    public CommonItemView(Context context) {
        this(context, null);
    }

    public CommonItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout mainView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.common_item_view, this);
        mIvIcon = (ImageView) mainView.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) mainView.findViewById(R.id.tv_title);
        mTvHint = (TextView) mainView.findViewById(R.id.tv_hint);
        mCutLine = mainView.findViewById(R.id.cut_line);
        mIvArrow = (ImageView) mainView.findViewById(R.id.iv_arrow);
    }

    public void setIconResource(int resId) {
        mIvIcon.setVisibility(View.VISIBLE);
        mIvIcon.setBackgroundResource(resId);
    }

    public void setIconDrawable(Context context, int resId, int colorId,boolean isSVG){
        Drawable drawable=null;
        if (isSVG) {
         drawable = ImageUtil.tintDrawableSVG(context, resId, colorId);

        }else{
            drawable = ImageUtil.tintDrawableNormal(context, resId, colorId);
        }
        mIvIcon.setVisibility(View.VISIBLE);
        mIvIcon.setImageDrawable(drawable);

    }

    public void setTitle(String text) {
        mTvTitle.setText(text);
    }

    public void setHint(String text) {
        mTvHint.setText(text);
    }

    public void setHintColor(int color) {
        mTvHint.setTextColor(color);
    }

    public void hideCutLine(boolean hide) {
        if(hide) {
            mCutLine.setVisibility(View.GONE);
        }else{
            mCutLine.setVisibility(View.VISIBLE);
        }
    }

    public void setArrowGone(){
        mIvArrow.setVisibility(View.GONE);
    }


}
