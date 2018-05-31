package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class RemoveAnimationInfo extends ItemAnimationInfo {
    public ViewHolder holder;

    public RemoveAnimationInfo(ViewHolder viewHolder) {
        this.holder = viewHolder;
    }

    public void clear(ViewHolder viewHolder) {
        if (this.holder == viewHolder) {
            this.holder = null;
        }
    }

    public ViewHolder getAvailableViewHolder() {
        return this.holder;
    }

    public String toString() {
        return "RemoveAnimationInfo{holder=" + this.holder + '}';
    }
}
