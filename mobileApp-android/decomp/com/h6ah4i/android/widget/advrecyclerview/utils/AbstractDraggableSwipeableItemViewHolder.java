package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;

public abstract class AbstractDraggableSwipeableItemViewHolder extends AbstractSwipeableItemViewHolder implements DraggableItemViewHolder {
    private int mDragStateFlags;

    public AbstractDraggableSwipeableItemViewHolder(View view) {
        super(view);
    }

    public int getDragStateFlags() {
        return this.mDragStateFlags;
    }

    public void setDragStateFlags(int i) {
        this.mDragStateFlags = i;
    }
}
