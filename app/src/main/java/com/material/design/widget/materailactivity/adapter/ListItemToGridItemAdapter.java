package com.material.design.widget.materailactivity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.material.design.widget.R;

/**
 * Created by chawei on 2017/3/1.
 */

public class ListItemToGridItemAdapter extends RecyclerView.Adapter<ListItemToGridItemAdapter.SimpleViewHolder> {

    private boolean isGridStyle;


    @Override
    public int getItemViewType(int position) {
        if (isGridStyle) {
            return 1;
        }else{
            return 2;
        }
    }

    public void setItemStyle(boolean isGridStyle) {
        this.isGridStyle = isGridStyle;
    }

    public boolean getItemStyle(){
        return isGridStyle;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==1) {//gridview
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.griditem_to_listitem_layout, parent, false);
        }else {//listview
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_to_griditem_layout, parent, false);
        }
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
