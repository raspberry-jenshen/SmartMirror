package com.jenshen.smartmirror.ui.holder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jenshen.smartmirror.ui.adapter.touch.ItemTouchHelperViewHolder;

public abstract class SwipeToDeleteHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    protected final Context context;

    public SwipeToDeleteHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
    }
}
