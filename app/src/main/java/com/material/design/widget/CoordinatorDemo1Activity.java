package com.material.design.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chawei on 2017/2/8.
 *
 * Behavior 基本概念
 *
 * Behavior实际就是将一些布局的过程以及Nested Scrolling的过程暴露了出来，
 * 利用代理和组合模式。可以让开发者为CoordinatorLayout添加各种效果插件。
 *
 * 依赖视图
 *
 * 一个Behavior能够将指定的视图作为一个依赖项，并且监听这个依赖项的一切布局信息，
 * 一旦依赖项发生变化，Behavior就可以做出适当的响应。很简单的例子就是FAB和SnackBar
 * 的联动，具体表现就是FAB会随SnackBar的弹出而上移，从而不会被SnackBar遮挡，
 * 这就是依赖视图的最简单的一个用法。
 *
 *
 */

public class CoordinatorDemo1Activity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coordinator_demo1_layout);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.item_simple, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText("Fake Item " + (position));
                vh.text2.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
            }

            @Override
            public int getItemCount() {
                return 20;
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView text;
                TextView text2;

                public ViewHolder(View itemView) {
                    super(itemView);

                    text = (TextView) itemView.findViewById(R.id.text);
                    text2 = (TextView) itemView.findViewById(R.id.text2);
                }

            }
        });
    }
}
