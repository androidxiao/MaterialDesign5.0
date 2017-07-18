package com.material.design.widget.materailactivity.twofragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.material.design.widget.R;

import static android.R.attr.name;

/**
 * Created by chawei on 2017/2/20.
 */

public class RightFragment extends Fragment {

    private static final String TAG = "demo";

    public static RightFragment newFragment(String str){
        Bundle bundle = new Bundle();
        bundle.putString("name",str);
        RightFragment rightFragment = new RightFragment();
        rightFragment.setArguments(bundle);
        Log.d(TAG, "newFragment: 2222----->"+str);
        return rightFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_right_layout, null);
        Button btn= (Button) view.findViewById(R.id.id_btn_right);
        TextView tv= (TextView) view.findViewById(R.id.id_text_from_right);
        Bundle bundle = getArguments();
        if(bundle!=null) {
            String name = bundle.getString("name");
            Log.d(TAG, "onCreateView: 2222"+name);
            tv.setText(name);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendResultData();
            }
        });
        return view;
    }

    private void sendResultData(){
        if(getTargetFragment()==null){
            return;
        }
        Log.d(TAG, "sendResultData: Right2222222222");
        Intent intent = new Intent();
        intent.putExtra("name", "我是RightFragment中传回来的值");
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}
