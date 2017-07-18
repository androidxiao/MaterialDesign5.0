package com.material.design.widget.materailactivity.twofragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.material.design.widget.R;

/**
 * Created by chawei on 2017/2/20.
 */

public class LeftFragment extends Fragment {

    private static final String TAG = "demo";
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_left_layout, null);
        Button btn= (Button) view.findViewById(R.id.id_btn_left);
        mTv = (TextView) view.findViewById(R.id.id_text_from_left);
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        RightFragment rightFragment = RightFragment.newFragment("我是初始化的值");
//        rightFragment.setTargetFragment(LeftFragment.this,100);
//        manager.beginTransaction().commitAllowingStateLoss();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RightFragment rightFragment = RightFragment.newFragment("我是从LeftFragment中传过来的");
                rightFragment.setTargetFragment(LeftFragment.this,100);
                manager.beginTransaction().replace(R.id.RightFragment,rightFragment).commit();
                Log.d(TAG, "onClick: Left1111111111111111");
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mTv.setText(data.getStringExtra("name"));
    }
}
