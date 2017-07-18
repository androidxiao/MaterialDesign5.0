package com.material.design.widget.materailactivity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;

import com.material.design.widget.R;

/**
 * Created by chawei on 2017/2/9.
 */

public class CheckRadioButtonActivity extends AppCompatActivity {

    private static final String TAG = "demo";
    private Button mBtnShow;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_radio_button_layout);

        findView();
        initListener();
    }

    private void findView(){
        mBtnShow = (Button) findViewById(R.id.id_btn_shadow);
        mSeekBar = (SeekBar) findViewById(R.id.id_seekbar);
    }

    private void initListener(){
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onProgressChanged(SeekBar bar, int i, boolean b) {
                Log.d(TAG, "onProgressChanged: ---->"+i);
                mBtnShow.setElevation(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar bar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {

            }
        });
    }
}
