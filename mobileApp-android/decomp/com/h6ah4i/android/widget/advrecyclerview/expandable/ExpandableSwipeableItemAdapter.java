package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;

public interface ExpandableSwipeableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> extends BaseExpandableSwipeableItemAdapter<GVH, CVH> {
    SwipeResultAction onSwipeChildItem(CVH cvh, int i, int i2, int i3);

    SwipeResultAction onSwipeGroupItem(GVH gvh, int i, int i2);
}
