package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class AddAnimationInfo extends ItemAnimationInfo {
    public ViewHolder holder;

    public AddAnimationInfo(ViewHolder viewHolder) {
        this.holder = viewHolder;
    }

    public void clear(ViewHolder viewHolder) {
        if (this.holder == null) {
            this.holder = null;
        }
    }

    public ViewHolder getAvailableViewHolder() {
        return this.holder;
    }

    public String toString() {
        return "AddAnimationInfo{holder=" + this.holder + '}';
    }
}
