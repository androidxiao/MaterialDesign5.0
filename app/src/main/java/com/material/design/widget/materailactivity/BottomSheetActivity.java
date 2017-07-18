package com.material.design.widget.materailactivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.material.design.widget.R;
import com.material.design.widget.widget.CommonItemView;

import static android.support.design.widget.BottomSheetBehavior.from;

/**
 * Created by chawei on 2017/2/10.
 */

public class BottomSheetActivity extends AppCompatActivity {

    private Button mBtn;
    private BottomSheetDialog mSheetDialog;
    private RecyclerView mRecyclerfView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bottom_sheet_layout);

        findView();
        createDialog();
//        setDataAdapter();
        setBehaviorCallback();

    }

    private void findView(){
        mBtn = (Button) findViewById(R.id.id_btn_click);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSheetDialog.isShowing()) {
                    mSheetDialog.dismiss();
                }else{
                    mSheetDialog.show();
                    //设置能被"拉"到的最大值
                    mSheetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,dp2px(300));
                }
            }
        });
    }

    private void createDialog(){
        mSheetDialog = new BottomSheetDialog(this);
//        View sheetView=LayoutInflater.from(this).inflate(R.layout.bottom_sheet_contentview, null);
//        mRecyclerfView = (RecyclerView) sheetView.findViewById(R.id.id_recycleview);
        View sheetView=LayoutInflater.from(this).inflate(R.layout.include_itemview_layout, null);

        mSheetDialog.setContentView(sheetView);
        mSheetDialog.setCancelable(true);
        mSheetDialog.setCanceledOnTouchOutside(true);
        View sheetViewChange= mSheetDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior.from(sheetViewChange).setPeekHeight(dp2px(200));
        mSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        initData(sheetView);
        CommonItemView commonItemView= (CommonItemView) sheetView.findViewById(R.id.civ_touzi);
        commonItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BottomSheetActivity.this, "我的评论", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(View view) {
        CommonItemView civTouzi = (CommonItemView) view.findViewById(R.id.civ_touzi);
        civTouzi.setIconDrawable(this, R.drawable.ic_loud_speech100, R.color.edit_border_color, true);
        civTouzi.setTitle("我的评论");
        civTouzi.setArrowGone();
        CommonItemView civHuikuan = (CommonItemView)view.findViewById(R.id.civ_huikuan);
        civHuikuan.setIconDrawable(this, R.drawable.ic_my_video161, R.color.edit_border_color, true);
        civHuikuan.setTitle("我的视频");
        civHuikuan.setArrowGone();
        CommonItemView civJiaoyi = (CommonItemView) view.findViewById(R.id.civ_jiaoyi);
        civJiaoyi.setIconDrawable(this, R.drawable.ic_upload_up113, R.color.edit_border_color, true);
        civJiaoyi.setTitle("上传");
        civJiaoyi.setArrowGone();
        CommonItemView civZhuanrang = (CommonItemView) view.findViewById(R.id.civ_zhuanrang);
        civZhuanrang.setIconDrawable(this, R.drawable.ic_music214, R.color.edit_border_color, true);
        civZhuanrang.setTitle("音乐");
        civZhuanrang.setArrowGone();
        CommonItemView mCivToubiao = (CommonItemView) view.findViewById(R.id.civ_toubiao);
        mCivToubiao.setIconDrawable(this, R.drawable.ic_refresh, R.color.edit_border_color, true);
        mCivToubiao.setTitle("刷新");
        mCivToubiao.setArrowGone();
        CommonItemView mCivZiping = (CommonItemView) view.findViewById(R.id.civ_ziping);
        mCivZiping.setIconDrawable(this, R.drawable.ic_heart281, R.color.edit_border_color, true);
        mCivZiping.setTitle("点赞");
        mCivZiping.setArrowGone();
        CommonItemView civSetting = (CommonItemView) view.findViewById(R.id.civ_setting);
        civSetting.setIconDrawable(this, R.drawable.ic_business140, R.color.edit_border_color, true);
        civSetting.setTitle("设置");
        civSetting.setArrowGone();
        CommonItemView civShare = (CommonItemView) view.findViewById(R.id.civ_share);
        civShare.setIconDrawable(this, R.drawable.ic_back41, R.color.edit_border_color, true);
        civShare.setTitle("分享");
        civShare.setArrowGone();

    }

    private void setBehaviorCallback(){
        View delegateView = mSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior=BottomSheetBehavior.from(delegateView);
        behavior.setHideable(false);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mSheetDialog.dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void setDataAdapter(){
        mRecyclerfView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.material_main_item_simple, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText("Fake Item " + (position));
            }

            @Override
            public int getItemCount() {
                return 20;
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView text;

                public ViewHolder(View itemView) {
                    super(itemView);

                    text = (TextView) itemView.findViewById(R.id.text);
                }

            }
        });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private  int dp2px( float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
