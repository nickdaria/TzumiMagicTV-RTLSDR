package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewParent;

public class RecyclerViewAdapterUtils {
    private RecyclerViewAdapterUtils() {
    }

    @Nullable
    public static RecyclerView getParentRecyclerView(@Nullable View view) {
        if (view == null) {
            return null;
        }
        ViewParent parent = view.getParent();
        return parent instanceof RecyclerView ? (RecyclerView) parent : parent instanceof View ? getParentRecyclerView((View) parent) : null;
    }

    @Nullable
    public static View getParentViewHolderItemView(@Nullable View view) {
        RecyclerView parentRecyclerView = getParentRecyclerView(view);
        return parentRecyclerView == null ? null : parentRecyclerView.findContainingItemView(view);
    }

    @Nullable
    public static ViewHolder getViewHolder(@Nullable View view) {
        RecyclerView parentRecyclerView = getParentRecyclerView(view);
        return parentRecyclerView == null ? null : parentRecyclerView.findContainingViewHolder(view);
    }
}
