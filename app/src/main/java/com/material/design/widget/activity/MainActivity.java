package com.material.design.widget.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.material.design.widget.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout ll= (LinearLayout) findViewById(R.id.id_ll_bottom);
        Button btn= (Button) findViewById(R.id.id_btn_show);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(ll);

        View view=LayoutInflater.from(this).inflate(R.layout.bottom_dialog_layout,null,false);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        //BottomSheetDialog有一个bug，如果手动将dialog隐藏，再次点击的时候不会show（如果不重新new），造成的原因是：
        //BottomSheetDialog是靠Behavior来隐藏的，此时dialog dismiss，且Behavior的行为是STATE_HIDDEN
        //所以隐藏状态的Behavior是不能显示dialog的。
        //解决方法：获取Behavior的代理重新设置state的属性值。
        View sheetView = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior<View> from = BottomSheetBehavior.from(sheetView);
        from.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dialog.dismiss();
                    from.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

//        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (behavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
//                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                } else if (behavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
//                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
                if(dialog.isShowing()){
                    dialog.dismiss();
                }else {
                    dialog.show();
                }
            }
        });



//        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
////                if(newState==)
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
    }
}
