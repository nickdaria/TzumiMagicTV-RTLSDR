package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;

public class ChangeAnimationInfo extends ItemAnimationInfo {
    public int fromX;
    public int fromY;
    public ViewHolder newHolder;
    public ViewHolder oldHolder;
    public int toX;
    public int toY;

    public ChangeAnimationInfo(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        this.oldHolder = viewHolder;
        this.newHolder = viewHolder2;
        this.fromX = i;
        this.fromY = i2;
        this.toX = i3;
        this.toY = i4;
    }

    public void clear(ViewHolder viewHolder) {
        if (this.oldHolder == viewHolder) {
            this.oldHolder = null;
        }
        if (this.newHolder == viewHolder) {
            this.newHolder = null;
        }
        if (this.oldHolder == null && this.newHolder == null) {
            this.fromX = 0;
            this.fromY = 0;
            this.toX = 0;
            this.toY = 0;
        }
    }

    public ViewHolder getAvailableViewHolder() {
        return this.oldHolder != null ? this.oldHolder : this.newHolder;
    }

    public String toString() {
        return "ChangeInfo{, oldHolder=" + this.oldHolder + ", newHolder=" + this.newHolder + ", fromX=" + this.fromX + ", fromY=" + this.fromY + ", toX=" + this.toX + ", toY=" + this.toY + '}';
    }
}
