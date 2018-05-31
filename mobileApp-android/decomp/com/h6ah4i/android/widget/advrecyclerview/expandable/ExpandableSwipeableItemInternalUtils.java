package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;

class ExpandableSwipeableItemInternalUtils {
    private ExpandableSwipeableItemInternalUtils() {
    }

    public static SwipeResultAction invokeOnSwipeItem(BaseExpandableSwipeableItemAdapter<?, ?> baseExpandableSwipeableItemAdapter, ViewHolder viewHolder, int i, int i2, int i3) {
        return i2 == -1 ? ((ExpandableSwipeableItemAdapter) baseExpandableSwipeableItemAdapter).onSwipeGroupItem(viewHolder, i, i3) : ((ExpandableSwipeableItemAdapter) baseExpandableSwipeableItemAdapter).onSwipeChildItem(viewHolder, i, i2, i3);
    }
}
