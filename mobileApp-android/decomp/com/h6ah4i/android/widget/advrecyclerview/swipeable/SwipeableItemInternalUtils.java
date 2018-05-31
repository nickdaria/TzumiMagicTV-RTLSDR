package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;

public class SwipeableItemInternalUtils {
    private SwipeableItemInternalUtils() {
    }

    public static SwipeResultAction invokeOnSwipeItem(BaseSwipeableItemAdapter<?> baseSwipeableItemAdapter, ViewHolder viewHolder, int i, int i2) {
        return ((SwipeableItemAdapter) baseSwipeableItemAdapter).onSwipeItem(viewHolder, i, i2);
    }
}
