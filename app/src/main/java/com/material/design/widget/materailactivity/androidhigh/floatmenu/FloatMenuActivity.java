package com.material.design.widget.materailactivity.androidhigh.floatmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.material.design.widget.R;
import com.material.design.widget.materailactivity.androidhigh.widget.FloatMenu;

/**
 * Created by chawei on 2017/2/24.
 */

public class FloatMenuActivity extends AppCompatActivity {

    private FloatMenu mFloatActionButtonMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_float_menu_layout);
        TextView tvMenu= (TextView) findViewById(R.id.id_tv_menu);
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"点我",Toast.LENGTH_SHORT).show();
            }
        });
        FrameLayout frameLayout= (FrameLayout) findViewById(R.id.container);
        mFloatActionButtonMenu = new FloatMenu(this, frameLayout);

        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
            }
        };

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mFloatActionButtonMenu.getIsMenuOpen()){
                    mFloatActionButtonMenu.closeFloatMenu();
                }
            }
        });

         mFloatActionButtonMenu.addMainItem(getResources().getDrawable(R.drawable.ic_add),null);
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
