package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface DraggableItemAdapter<T extends ViewHolder> {
    boolean onCheckCanDrop(int i, int i2);

    boolean onCheckCanStartDrag(T t, int i, int i2, int i3);

    ItemDraggableRange onGetItemDraggableRange(T t, int i);

    void onMoveItem(int i, int i2);
}
