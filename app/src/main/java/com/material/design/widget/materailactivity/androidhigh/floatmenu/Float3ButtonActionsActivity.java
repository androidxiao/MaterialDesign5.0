package com.material.design.widget.materailactivity.androidhigh.floatmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.material.design.widget.R;
import com.material.design.widget.materailactivity.androidhigh.widget.Float3ButtonActions;

/**
 * Created by chawei on 2017/2/24.
 */

public class Float3ButtonActionsActivity extends AppCompatActivity {

    private Float3ButtonActions mFloatActionButtonMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_float_3_button_actions_layout);
        TextView tvMenu= (TextView) findViewById(R.id.id_tv_menu);
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"点我",Toast.LENGTH_SHORT).show();
            }
        });
        FrameLayout frameLayout= (FrameLayout) findViewById(R.id.container);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag= (int) view.getTag();
                switch (tag){
                    case 1:
                        Toast.makeText(getApplicationContext(), "编辑", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "发送", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "复制", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };


        mFloatActionButtonMenu = new Float3ButtonActions(this, frameLayout);
        mFloatActionButtonMenu.addMenuItem(getResources().getDrawable(android.R.drawable.ic_menu_edit),onClickListener);
        mFloatActionButtonMenu.addMenuItem(getResources().getDrawable(android.R.drawable.ic_menu_send),onClickListener);
        mFloatActionButtonMenu.addMenuItem(getResources().getDrawable(android.R.drawable.ic_input_get),onClickListener);
        mFloatActionButtonMenu.addMainItem(getResources().getDrawable(R.drawable.ic_add));

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFloatActionButtonMenu.getIsMenuOpen()){
                    mFloatActionButtonMenu.closeFloatMenu();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mFloatActionButtonMenu.getIsMenuOpen()) {
            mFloatActionButtonMenu.closeFloatMenu();
        }else {
            super.onBackPressed();
        }
    }
}
