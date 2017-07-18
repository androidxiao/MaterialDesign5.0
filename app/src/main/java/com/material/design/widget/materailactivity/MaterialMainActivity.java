package com.material.design.widget.materailactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.material.design.widget.R;
import com.material.design.widget.materailactivity.androidhigh.ListItemToGridItemActivity;
import com.material.design.widget.materailactivity.androidhigh.floatmenu.Float3ButtonActionsActivity;
import com.material.design.widget.materailactivity.androidhigh.floatmenu.FloatActionButtonMenuActivity;
import com.material.design.widget.materailactivity.androidhigh.floatmenu.FloatMenuActivity;
import com.material.design.widget.materailactivity.androidhigh.LayerListActivity;
import com.material.design.widget.materailactivity.androidhigh.animation.PropertyAnimationActivity;
import com.material.design.widget.materailactivity.androidhigh.animation.ViewAnimatorActivity;
import com.material.design.widget.materailactivity.dbdemo.DbDemoActivity;
import com.material.design.widget.materailactivity.materiallogin.MaterialAnimationActivity;
import com.material.design.widget.materailactivity.twofragment.TwoFragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chawei on 2017/2/9.
 */

public class MaterialMainActivity extends AppCompatActivity {

    private static final String TAG = "demo";
    private RecyclerView recyclerView;
    private List<Class> mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_material_main_layout);

        findView();
        initData();
        setAdapter();

    }

    private void findView(){
        recyclerView = (RecyclerView) findViewById(R.id.id_recycleview);

    }

    private void initData(){
        mActivity = new ArrayList<>();
        mActivity.add(CheckRadioButtonActivity.class);
        mActivity.add(BottomSheetActivity.class);
        mActivity.add(MaterialAnimationActivity.class);
        mActivity.add(SettingActivity.class);
        mActivity.add(TwoFragmentActivity.class);
        mActivity.add(DbDemoActivity.class);
        mActivity.add(ViewAnimatorActivity.class);
        mActivity.add(PropertyAnimationActivity.class);
        mActivity.add(FloatActionButtonMenuActivity.class);
        mActivity.add(FloatMenuActivity.class);
        mActivity.add(Float3ButtonActionsActivity.class);
        mActivity.add(LayerListActivity.class);
        mActivity.add(ListItemToGridItemActivity.class);
    }

    private void intentTo(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void setAdapter(){
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.material_main_item_simple, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText(mActivity.get(position).getSimpleName());
                vh.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intentTo(mActivity.get(position));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mActivity.size();
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

}
