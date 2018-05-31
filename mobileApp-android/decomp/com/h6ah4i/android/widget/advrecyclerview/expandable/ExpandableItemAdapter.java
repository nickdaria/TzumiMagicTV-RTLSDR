package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.util.List;

public interface ExpandableItemAdapter<GVH extends ViewHolder, CVH extends ViewHolder> {
    int getChildCount(int i);

    long getChildId(int i, int i2);

    int getChildItemViewType(int i, int i2);

    int getGroupCount();

    long getGroupId(int i);

    int getGroupItemViewType(int i);

    void onBindChildViewHolder(CVH cvh, int i, int i2, int i3);

    void onBindChildViewHolder(CVH cvh, int i, int i2, int i3, List<Object> list);

    void onBindGroupViewHolder(GVH gvh, int i, int i2);

    void onBindGroupViewHolder(GVH gvh, int i, int i2, List<Object> list);

    boolean onCheckCanExpandOrCollapseGroup(GVH gvh, int i, int i2, int i3, boolean z);

    CVH onCreateChildViewHolder(ViewGroup viewGroup, int i);

    GVH onCreateGroupViewHolder(ViewGroup viewGroup, int i);

    boolean onHookGroupCollapse(int i, boolean z);

    boolean onHookGroupExpand(int i, boolean z);
}
