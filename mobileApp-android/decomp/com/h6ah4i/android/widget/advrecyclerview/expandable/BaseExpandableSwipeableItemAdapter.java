package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface BaseExpandableSwipeableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> {
    int onGetChildItemSwipeReactionType(CVH cvh, int i, int i2, int i3, int i4);

    int onGetGroupItemSwipeReactionType(GVH gvh, int i, int i2, int i3);

    void onSetChildItemSwipeBackground(CVH cvh, int i, int i2, int i3);

    void onSetGroupItemSwipeBackground(GVH gvh, int i, int i2);
}
