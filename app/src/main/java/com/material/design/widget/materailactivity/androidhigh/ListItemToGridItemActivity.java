package com.material.design.widget.materailactivity.androidhigh;


import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.material.design.widget.R;
import com.material.design.widget.materailactivity.adapter.ListItemToGridItemAdapter;

/**
 * Created by chawei on 2017/3/1.
 */

public class ListItemToGridItemActivity extends AppCompatActivity {

    GridLayoutManager gridLayoutManager;
    ListItemToGridItemAdapter simpleAdapter;
    private RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listitem_to_griditem_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.getItemAnimator().setChangeDuration(700);
        simpleAdapter = new ListItemToGridItemAdapter();
        mRecyclerView.setAdapter(simpleAdapter);
        gridLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_list_to_grid) {
            if (!((Animatable) item.getIcon()).isRunning()) {
                Log.d("demo", "onOptionsItemSelected: --->"+simpleAdapter.getItemStyle());
                if (!simpleAdapter.getItemStyle()) {
                    item.setIcon(AnimatedVectorDrawableCompat.create(ListItemToGridItemActivity.this, R.drawable.avd_list_to_grid));
                    simpleAdapter.setItemStyle(true);
                    gridLayoutManager.setSpanCount(2);
                    mRecyclerView.setLayoutManager(gridLayoutManager);
                } else {
                    item.setIcon(AnimatedVectorDrawableCompat.create(ListItemToGridItemActivity.this, R.drawable.avd_grid_to_list));
                    simpleAdapter.setItemStyle(false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                }
                ((Animatable) item.getIcon()).start();
                simpleAdapter.notifyDataSetChanged();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}