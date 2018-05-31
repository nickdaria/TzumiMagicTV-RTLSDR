package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public abstract class ItemAnimationInfo {
    public abstract void clear(ViewHolder viewHolder);

    public abstract ViewHolder getAvailableViewHolder();
}
