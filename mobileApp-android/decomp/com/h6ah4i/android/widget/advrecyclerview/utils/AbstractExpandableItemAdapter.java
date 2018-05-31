package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemAdapter;
import java.util.List;

public abstract class AbstractExpandableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> extends Adapter<ViewHolder> implements ExpandableItemAdapter<GVH, CVH> {
    public int getChildItemViewType(int i, int i2) {
        return 0;
    }

    public int getGroupItemViewType(int i) {
        return 0;
    }

    public final int getItemCount() {
        return 0;
    }

    public final long getItemId(int i) {
        return -1;
    }

    public final int getItemViewType(int i) {
        return 0;
    }

    public void onBindChildViewHolder(CVH cvh, int i, int i2, int i3, List<Object> list) {
        onBindChildViewHolder(cvh, i, i2, i3);
    }

    public void onBindGroupViewHolder(GVH gvh, int i, int i2, List<Object> list) {
        onBindGroupViewHolder(gvh, i, i2);
    }

    public final void onBindViewHolder(ViewHolder viewHolder, int i) {
    }

    public final ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    public boolean onHookGroupCollapse(int i, boolean z) {
        return true;
    }

    public boolean onHookGroupExpand(int i, boolean z) {
        return true;
    }
}
