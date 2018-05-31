package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;

public interface ExpandableDraggableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> {
    boolean onCheckChildCanDrop(int i, int i2, int i3, int i4);

    boolean onCheckChildCanStartDrag(CVH cvh, int i, int i2, int i3, int i4);

    boolean onCheckGroupCanDrop(int i, int i2);

    boolean onCheckGroupCanStartDrag(GVH gvh, int i, int i2, int i3);

    ItemDraggableRange onGetChildItemDraggableRange(CVH cvh, int i, int i2);

    ItemDraggableRange onGetGroupItemDraggableRange(GVH gvh, int i);

    void onMoveChildItem(int i, int i2, int i3, int i4);

    void onMoveGroupItem(int i, int i2);
}
