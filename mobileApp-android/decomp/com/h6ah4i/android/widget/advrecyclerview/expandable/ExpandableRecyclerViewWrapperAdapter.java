package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.OnGroupCollapseListener;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.OnGroupExpandListener;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.List;

class ExpandableRecyclerViewWrapperAdapter extends BaseWrapperAdapter<ViewHolder> implements DraggableItemAdapter<ViewHolder>, SwipeableItemAdapter<ViewHolder> {
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVExpandableWrapper";
    private static final int VIEW_TYPE_FLAG_IS_GROUP = Integer.MIN_VALUE;
    private int mDraggingItemChildRangeEnd = -1;
    private int mDraggingItemChildRangeStart = -1;
    private int mDraggingItemGroupRangeEnd = -1;
    private int mDraggingItemGroupRangeStart = -1;
    private ExpandableItemAdapter mExpandableItemAdapter;
    private RecyclerViewExpandableItemManager mExpandableListManager;
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private ExpandablePositionTranslator mPositionTranslator;

    private interface Constants extends ExpandableItemConstants {
    }

    public ExpandableRecyclerViewWrapperAdapter(RecyclerViewExpandableItemManager recyclerViewExpandableItemManager, Adapter<ViewHolder> adapter, int[] iArr) {
        super(adapter);
        this.mExpandableItemAdapter = getExpandableItemAdapter(adapter);
        if (this.mExpandableItemAdapter == null) {
            throw new IllegalArgumentException("adapter does not implement RecyclerViewExpandableListManager");
        } else if (recyclerViewExpandableItemManager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        } else {
            this.mExpandableListManager = recyclerViewExpandableItemManager;
            this.mPositionTranslator = new ExpandablePositionTranslator();
            this.mPositionTranslator.build(this.mExpandableItemAdapter, false);
            if (iArr != null) {
                this.mPositionTranslator.restoreExpandedGroupItems(iArr, null, null, null);
            }
        }
    }

    private void correctItemDragStateFlags(ViewHolder viewHolder, int i, int i2) {
        Object obj = null;
        if (viewHolder instanceof DraggableItemViewHolder) {
            DraggableItemViewHolder draggableItemViewHolder = (DraggableItemViewHolder) viewHolder;
            Object obj2 = (this.mDraggingItemGroupRangeStart == -1 || this.mDraggingItemGroupRangeEnd == -1) ? null : 1;
            Object obj3 = (this.mDraggingItemChildRangeStart == -1 || this.mDraggingItemChildRangeEnd == -1) ? null : 1;
            Object obj4 = (i < this.mDraggingItemGroupRangeStart || i > this.mDraggingItemGroupRangeEnd) ? null : 1;
            Object obj5 = (i == -1 || i2 < this.mDraggingItemChildRangeStart || i2 > this.mDraggingItemChildRangeEnd) ? null : 1;
            int dragStateFlags = draggableItemViewHolder.getDragStateFlags();
            if ((dragStateFlags & 1) != 0 && (dragStateFlags & 4) == 0 && ((obj2 == null || obj4 != null) && (obj3 == null || !(obj3 == null || obj5 == null)))) {
                obj = 1;
            }
            if (obj != null) {
                draggableItemViewHolder.setDragStateFlags((dragStateFlags | 4) | Integer.MIN_VALUE);
            }
        }
    }

    private static ExpandableItemAdapter getExpandableItemAdapter(Adapter adapter) {
        return (ExpandableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, ExpandableItemAdapter.class);
    }

    private static boolean isChildPositionRange(ItemDraggableRange itemDraggableRange) {
        return itemDraggableRange.getClass().equals(ChildPositionItemDraggableRange.class);
    }

    private static boolean isGroupPositionRange(ItemDraggableRange itemDraggableRange) {
        return itemDraggableRange.getClass().equals(GroupPositionItemDraggableRange.class) || itemDraggableRange.getClass().equals(ItemDraggableRange.class);
    }

    private void raiseOnGroupExpandedSequentially(int i, int i2, boolean z) {
        if (this.mOnGroupExpandListener != null) {
            for (int i3 = 0; i3 < i2; i3++) {
                this.mOnGroupExpandListener.onGroupExpand(i + i3, z);
            }
        }
    }

    private void rebuildPositionTranslator() {
        if (this.mPositionTranslator != null) {
            int[] savedStateArray = this.mPositionTranslator.getSavedStateArray();
            this.mPositionTranslator.build(this.mExpandableItemAdapter, false);
            this.mPositionTranslator.restoreExpandedGroupItems(savedStateArray, null, null, null);
        }
    }

    private static void safeUpdateExpandStateFlags(ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ExpandableItemViewHolder) {
            ExpandableItemViewHolder expandableItemViewHolder = (ExpandableItemViewHolder) viewHolder;
            int expandStateFlags = expandableItemViewHolder.getExpandStateFlags();
            int i2 = (expandStateFlags == -1 || ((expandStateFlags ^ i) & 4) == 0) ? i : i | 8;
            if (expandStateFlags == -1 || ((expandStateFlags ^ i2) & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) != 0) {
                i2 |= Integer.MIN_VALUE;
            }
            expandableItemViewHolder.setExpandStateFlags(i2);
        }
    }

    void collapseAll() {
        if (!this.mPositionTranslator.isEmpty() && !this.mPositionTranslator.isAllCollapsed()) {
            this.mPositionTranslator.build(this.mExpandableItemAdapter, false);
            notifyDataSetChanged();
        }
    }

    boolean collapseGroup(int i, boolean z) {
        if (!this.mPositionTranslator.isGroupExpanded(i) || !this.mExpandableItemAdapter.onHookGroupCollapse(i, z)) {
            return false;
        }
        if (this.mPositionTranslator.collapseGroup(i)) {
            notifyItemRangeRemoved(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i)) + 1, this.mPositionTranslator.getChildCount(i));
        }
        notifyItemChanged(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i)));
        if (this.mOnGroupCollapseListener != null) {
            this.mOnGroupCollapseListener.onGroupCollapse(i, z);
        }
        return true;
    }

    void expandAll() {
        if (!this.mPositionTranslator.isEmpty() && !this.mPositionTranslator.isAllExpanded()) {
            this.mPositionTranslator.build(this.mExpandableItemAdapter, true);
            notifyDataSetChanged();
        }
    }

    boolean expandGroup(int i, boolean z) {
        if (this.mPositionTranslator.isGroupExpanded(i) || !this.mExpandableItemAdapter.onHookGroupExpand(i, z)) {
            return false;
        }
        if (this.mPositionTranslator.expandGroup(i)) {
            notifyItemRangeInserted(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i)) + 1, this.mPositionTranslator.getChildCount(i));
        }
        notifyItemChanged(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i)));
        if (this.mOnGroupExpandListener != null) {
            this.mOnGroupExpandListener.onGroupExpand(i, z);
        }
        return true;
    }

    int getChildCount(int i) {
        return this.mExpandableItemAdapter.getChildCount(i);
    }

    int getCollapsedGroupsCount() {
        return this.mPositionTranslator.getCollapsedGroupsCount();
    }

    long getExpandablePosition(int i) {
        return this.mPositionTranslator.getExpandablePosition(i);
    }

    int getExpandedGroupsCount() {
        return this.mPositionTranslator.getExpandedGroupsCount();
    }

    int[] getExpandedItemsSavedStateArray() {
        return this.mPositionTranslator != null ? this.mPositionTranslator.getSavedStateArray() : null;
    }

    int getFlatPosition(long j) {
        return this.mPositionTranslator.getFlatPosition(j);
    }

    int getGroupCount() {
        return this.mExpandableItemAdapter.getGroupCount();
    }

    public int getItemCount() {
        return this.mPositionTranslator.getItemCount();
    }

    public long getItemId(int i) {
        if (this.mExpandableItemAdapter == null) {
            return -1;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        return packedPositionChild == -1 ? ExpandableAdapterHelper.getCombinedGroupId(this.mExpandableItemAdapter.getGroupId(packedPositionGroup)) : ExpandableAdapterHelper.getCombinedChildId(this.mExpandableItemAdapter.getGroupId(packedPositionGroup), this.mExpandableItemAdapter.getChildId(packedPositionGroup, packedPositionChild));
    }

    public int getItemViewType(int i) {
        if (this.mExpandableItemAdapter == null) {
            return 0;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        int groupItemViewType = packedPositionChild == -1 ? this.mExpandableItemAdapter.getGroupItemViewType(packedPositionGroup) : this.mExpandableItemAdapter.getChildItemViewType(packedPositionGroup, packedPositionChild);
        if ((groupItemViewType & Integer.MIN_VALUE) == 0) {
            return packedPositionChild == -1 ? groupItemViewType | Integer.MIN_VALUE : groupItemViewType;
        } else {
            throw new IllegalStateException("Illegal view type (type = " + Integer.toHexString(groupItemViewType) + ")");
        }
    }

    boolean isAllGroupsCollapsed() {
        return this.mPositionTranslator.isAllCollapsed();
    }

    boolean isAllGroupsExpanded() {
        return this.mPositionTranslator.isAllExpanded();
    }

    boolean isGroupExpanded(int i) {
        return this.mPositionTranslator.isGroupExpanded(i);
    }

    void notifyChildItemChanged(int i, int i2, Object obj) {
        notifyChildItemRangeChanged(i, i2, 1, obj);
    }

    void notifyChildItemInserted(int i, int i2) {
        this.mPositionTranslator.insertChildItem(i, i2);
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(i, i2));
        if (flatPosition != -1) {
            notifyItemInserted(flatPosition);
        }
    }

    void notifyChildItemMoved(int i, int i2, int i3) {
        notifyChildItemMoved(i, i2, i, i3);
    }

    void notifyChildItemMoved(int i, int i2, int i3, int i4) {
        long packedPositionForChild = RecyclerViewExpandableItemManager.getPackedPositionForChild(i, i2);
        long packedPositionForChild2 = RecyclerViewExpandableItemManager.getPackedPositionForChild(i3, i4);
        int flatPosition = getFlatPosition(packedPositionForChild);
        int flatPosition2 = getFlatPosition(packedPositionForChild2);
        this.mPositionTranslator.moveChildItem(i, i2, i3, i4);
        if (flatPosition != -1 && flatPosition2 != -1) {
            notifyItemMoved(flatPosition, flatPosition2);
        } else if (flatPosition != -1) {
            notifyItemRemoved(flatPosition);
        } else if (flatPosition2 != -1) {
            notifyItemInserted(flatPosition2);
        }
    }

    void notifyChildItemRangeChanged(int i, int i2, int i3, Object obj) {
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(i);
        if (visibleChildCount > 0 && i2 < visibleChildCount) {
            int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(i, 0));
            if (flatPosition != -1) {
                notifyItemRangeChanged(flatPosition + i2, Math.min(i3, visibleChildCount - i2), obj);
            }
        }
    }

    void notifyChildItemRangeInserted(int i, int i2, int i3) {
        this.mPositionTranslator.insertChildItems(i, i2, i3);
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(i, i2));
        if (flatPosition != -1) {
            notifyItemRangeInserted(flatPosition, i3);
        }
    }

    void notifyChildItemRangeRemoved(int i, int i2, int i3) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(i, i2));
        this.mPositionTranslator.removeChildItems(i, i2, i3);
        if (flatPosition != -1) {
            notifyItemRangeRemoved(flatPosition, i3);
        }
    }

    void notifyChildItemRemoved(int i, int i2) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(i, i2));
        this.mPositionTranslator.removeChildItem(i, i2);
        if (flatPosition != -1) {
            notifyItemRemoved(flatPosition);
        }
    }

    void notifyChildrenOfGroupItemChanged(int i, Object obj) {
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(i);
        if (visibleChildCount > 0) {
            int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(i, 0));
            if (flatPosition != -1) {
                notifyItemRangeChanged(flatPosition, visibleChildCount, obj);
            }
        }
    }

    void notifyGroupAndChildrenItemsChanged(int i, Object obj) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i));
        int visibleChildCount = this.mPositionTranslator.getVisibleChildCount(i);
        if (flatPosition != -1) {
            notifyItemRangeChanged(flatPosition, visibleChildCount + 1, obj);
        }
    }

    void notifyGroupItemChanged(int i) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i));
        if (flatPosition != -1) {
            notifyItemChanged(flatPosition);
        }
    }

    void notifyGroupItemInserted(int i, boolean z) {
        if (this.mPositionTranslator.insertGroupItem(i, z) > 0) {
            notifyItemInserted(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i)));
            raiseOnGroupExpandedSequentially(i, 1, false);
        }
    }

    void notifyGroupItemMoved(int i, int i2) {
        long packedPositionForGroup = RecyclerViewExpandableItemManager.getPackedPositionForGroup(i);
        long packedPositionForGroup2 = RecyclerViewExpandableItemManager.getPackedPositionForGroup(i2);
        int flatPosition = getFlatPosition(packedPositionForGroup);
        int flatPosition2 = getFlatPosition(packedPositionForGroup2);
        boolean isGroupExpanded = isGroupExpanded(i);
        boolean isGroupExpanded2 = isGroupExpanded(i2);
        this.mPositionTranslator.moveGroupItem(i, i2);
        if (isGroupExpanded || isGroupExpanded2) {
            notifyDataSetChanged();
        } else {
            notifyItemMoved(flatPosition, flatPosition2);
        }
    }

    void notifyGroupItemRangeInserted(int i, int i2, boolean z) {
        int insertGroupItems = this.mPositionTranslator.insertGroupItems(i, i2, z);
        if (insertGroupItems > 0) {
            notifyItemRangeInserted(this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i)), insertGroupItems);
            raiseOnGroupExpandedSequentially(i, i2, false);
        }
    }

    void notifyGroupItemRangeRemoved(int i, int i2) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i));
        int removeGroupItems = this.mPositionTranslator.removeGroupItems(i, i2);
        if (removeGroupItems > 0) {
            notifyItemRangeRemoved(flatPosition, removeGroupItems);
        }
    }

    void notifyGroupItemRemoved(int i) {
        int flatPosition = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(i));
        int removeGroupItem = this.mPositionTranslator.removeGroupItem(i);
        if (removeGroupItem > 0) {
            notifyItemRangeRemoved(flatPosition, removeGroupItem);
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i, List<Object> list) {
        if (this.mExpandableItemAdapter != null) {
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
            int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            int itemViewType = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & viewHolder.getItemViewType();
            int i2 = packedPositionChild == -1 ? 1 : 2;
            if (this.mPositionTranslator.isGroupExpanded(packedPositionGroup)) {
                i2 |= 4;
            }
            safeUpdateExpandStateFlags(viewHolder, i2);
            correctItemDragStateFlags(viewHolder, packedPositionGroup, packedPositionChild);
            if (packedPositionChild == -1) {
                this.mExpandableItemAdapter.onBindGroupViewHolder(viewHolder, packedPositionGroup, itemViewType);
            } else {
                this.mExpandableItemAdapter.onBindChildViewHolder(viewHolder, packedPositionGroup, packedPositionChild, itemViewType);
            }
        }
    }

    public boolean onCheckCanDrop(int i, int i2) {
        boolean z = true;
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter)) {
            return true;
        }
        if (this.mExpandableItemAdapter.getGroupCount() < 1) {
            return false;
        }
        ExpandableDraggableItemAdapter expandableDraggableItemAdapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        long expandablePosition2 = this.mPositionTranslator.getExpandablePosition(i2);
        int packedPositionGroup2 = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition2);
        int packedPositionChild2 = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition2);
        boolean z2 = packedPositionChild == -1;
        boolean z3 = packedPositionChild2 == -1;
        if (z2) {
            if (packedPositionGroup == packedPositionGroup2) {
                z = z3;
            } else if (i < i2) {
                z2 = this.mPositionTranslator.isGroupExpanded(packedPositionGroup2);
                packedPositionChild = this.mPositionTranslator.getVisibleChildCount(packedPositionGroup2);
                if (z3) {
                    if (z2) {
                        z = false;
                    }
                } else if (packedPositionChild2 != packedPositionChild - 1) {
                    z = false;
                }
            } else {
                z = z3;
            }
            return z ? expandableDraggableItemAdapter.onCheckGroupCanDrop(packedPositionGroup, packedPositionGroup2) : false;
        } else {
            boolean z4;
            int i3;
            z2 = this.mPositionTranslator.isGroupExpanded(packedPositionGroup2);
            int i4;
            if (i < i2) {
                if (!z3) {
                    i4 = packedPositionChild2;
                    packedPositionChild2 = packedPositionGroup2;
                    z4 = true;
                    i3 = i4;
                } else if (z2) {
                    packedPositionChild2 = packedPositionGroup2;
                    z4 = true;
                    i3 = 0;
                } else {
                    i4 = this.mPositionTranslator.getChildCount(packedPositionGroup2);
                    packedPositionChild2 = packedPositionGroup2;
                    z4 = true;
                    i3 = i4;
                }
            } else if (!z3) {
                i4 = packedPositionChild2;
                packedPositionChild2 = packedPositionGroup2;
                z4 = true;
                i3 = i4;
            } else if (packedPositionGroup2 > 0) {
                packedPositionGroup2--;
                i4 = this.mPositionTranslator.getChildCount(packedPositionGroup2);
                packedPositionChild2 = packedPositionGroup2;
                z4 = true;
                i3 = i4;
            } else {
                i3 = packedPositionChild2;
                packedPositionChild2 = packedPositionGroup2;
                z4 = false;
            }
            return z4 ? expandableDraggableItemAdapter.onCheckChildCanDrop(packedPositionGroup, packedPositionChild, packedPositionChild2, i3) : false;
        }
    }

    public boolean onCheckCanStartDrag(ViewHolder viewHolder, int i, int i2, int i3) {
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter)) {
            return false;
        }
        ExpandableDraggableItemAdapter expandableDraggableItemAdapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        boolean onCheckGroupCanStartDrag = packedPositionChild == -1 ? expandableDraggableItemAdapter.onCheckGroupCanStartDrag(viewHolder, packedPositionGroup, i2, i3) : expandableDraggableItemAdapter.onCheckChildCanStartDrag(viewHolder, packedPositionGroup, packedPositionChild, i2, i3);
        this.mDraggingItemGroupRangeStart = -1;
        this.mDraggingItemGroupRangeEnd = -1;
        this.mDraggingItemChildRangeStart = -1;
        this.mDraggingItemChildRangeEnd = -1;
        return onCheckGroupCanStartDrag;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (this.mExpandableItemAdapter == null) {
            return null;
        }
        int i2 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & i;
        ViewHolder onCreateGroupViewHolder = (Integer.MIN_VALUE & i) != 0 ? this.mExpandableItemAdapter.onCreateGroupViewHolder(viewGroup, i2) : this.mExpandableItemAdapter.onCreateChildViewHolder(viewGroup, i2);
        if (!(onCreateGroupViewHolder instanceof ExpandableItemViewHolder)) {
            return onCreateGroupViewHolder;
        }
        ((ExpandableItemViewHolder) onCreateGroupViewHolder).setExpandStateFlags(-1);
        return onCreateGroupViewHolder;
    }

    public ItemDraggableRange onGetItemDraggableRange(ViewHolder viewHolder, int i) {
        if (!(this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter) || this.mExpandableItemAdapter.getGroupCount() < 1) {
            return null;
        }
        ExpandableDraggableItemAdapter expandableDraggableItemAdapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        int flatPosition;
        if (packedPositionChild == -1) {
            ItemDraggableRange onGetGroupItemDraggableRange = expandableDraggableItemAdapter.onGetGroupItemDraggableRange(viewHolder, packedPositionGroup);
            if (onGetGroupItemDraggableRange == null) {
                return new ItemDraggableRange(0, Math.max(0, (this.mPositionTranslator.getItemCount() - this.mPositionTranslator.getVisibleChildCount(Math.max(0, this.mExpandableItemAdapter.getGroupCount() - 1))) - 1));
            } else if (isGroupPositionRange(onGetGroupItemDraggableRange)) {
                long packedPositionForGroup = ExpandableAdapterHelper.getPackedPositionForGroup(onGetGroupItemDraggableRange.getStart());
                long packedPositionForGroup2 = ExpandableAdapterHelper.getPackedPositionForGroup(onGetGroupItemDraggableRange.getEnd());
                int flatPosition2 = this.mPositionTranslator.getFlatPosition(packedPositionForGroup);
                flatPosition = this.mPositionTranslator.getFlatPosition(packedPositionForGroup2);
                if (onGetGroupItemDraggableRange.getEnd() > packedPositionGroup) {
                    flatPosition += this.mPositionTranslator.getVisibleChildCount(onGetGroupItemDraggableRange.getEnd());
                }
                this.mDraggingItemGroupRangeStart = onGetGroupItemDraggableRange.getStart();
                this.mDraggingItemGroupRangeEnd = onGetGroupItemDraggableRange.getEnd();
                return new ItemDraggableRange(flatPosition2, flatPosition);
            } else {
                throw new IllegalStateException("Invalid range specified: " + onGetGroupItemDraggableRange);
            }
        }
        ItemDraggableRange onGetChildItemDraggableRange = expandableDraggableItemAdapter.onGetChildItemDraggableRange(viewHolder, packedPositionGroup, packedPositionChild);
        if (onGetChildItemDraggableRange == null) {
            return new ItemDraggableRange(1, Math.max(1, this.mPositionTranslator.getItemCount() - 1));
        }
        if (isGroupPositionRange(onGetChildItemDraggableRange)) {
            expandablePosition = ExpandableAdapterHelper.getPackedPositionForGroup(onGetChildItemDraggableRange.getStart());
            packedPositionGroup = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(onGetChildItemDraggableRange.getEnd())) + this.mPositionTranslator.getVisibleChildCount(onGetChildItemDraggableRange.getEnd());
            packedPositionChild = Math.min(this.mPositionTranslator.getFlatPosition(expandablePosition) + 1, packedPositionGroup);
            this.mDraggingItemGroupRangeStart = onGetChildItemDraggableRange.getStart();
            this.mDraggingItemGroupRangeEnd = onGetChildItemDraggableRange.getEnd();
            return new ItemDraggableRange(packedPositionChild, packedPositionGroup);
        } else if (isChildPositionRange(onGetChildItemDraggableRange)) {
            packedPositionChild = Math.max(this.mPositionTranslator.getVisibleChildCount(packedPositionGroup) - 1, 0);
            flatPosition2 = Math.min(onGetChildItemDraggableRange.getStart(), packedPositionChild);
            flatPosition = Math.min(onGetChildItemDraggableRange.getEnd(), packedPositionChild);
            packedPositionForGroup = ExpandableAdapterHelper.getPackedPositionForChild(packedPositionGroup, flatPosition2);
            packedPositionForGroup2 = ExpandableAdapterHelper.getPackedPositionForChild(packedPositionGroup, flatPosition);
            packedPositionGroup = this.mPositionTranslator.getFlatPosition(packedPositionForGroup);
            packedPositionChild = this.mPositionTranslator.getFlatPosition(packedPositionForGroup2);
            this.mDraggingItemChildRangeStart = flatPosition2;
            this.mDraggingItemChildRangeEnd = flatPosition;
            return new ItemDraggableRange(packedPositionGroup, packedPositionChild);
        } else {
            throw new IllegalStateException("Invalid range specified: " + onGetChildItemDraggableRange);
        }
    }

    public int onGetSwipeReactionType(ViewHolder viewHolder, int i, int i2, int i3) {
        if (!(this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter)) {
            return 0;
        }
        BaseExpandableSwipeableItemAdapter baseExpandableSwipeableItemAdapter = (BaseExpandableSwipeableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
        return packedPositionChild == -1 ? baseExpandableSwipeableItemAdapter.onGetGroupItemSwipeReactionType(viewHolder, packedPositionGroup, i2, i3) : baseExpandableSwipeableItemAdapter.onGetChildItemSwipeReactionType(viewHolder, packedPositionGroup, packedPositionChild, i2, i3);
    }

    protected void onHandleWrappedAdapterChanged() {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int i, int i2) {
        super.onHandleWrappedAdapterItemRangeChanged(i, i2);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int i, int i2) {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterItemRangeInserted(i, i2);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int i, int i2) {
        if (i2 == 1) {
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
            int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            if (packedPositionChild == -1) {
                this.mPositionTranslator.removeGroupItem(packedPositionGroup);
            } else {
                this.mPositionTranslator.removeChildItem(packedPositionGroup, packedPositionChild);
            }
        } else {
            rebuildPositionTranslator();
        }
        super.onHandleWrappedAdapterItemRangeRemoved(i, i2);
    }

    protected void onHandleWrappedAdapterRangeMoved(int i, int i2, int i3) {
        rebuildPositionTranslator();
        super.onHandleWrappedAdapterRangeMoved(i, i2, i3);
    }

    public void onMoveItem(int i, int i2) {
        int i3 = 1;
        if (this.mExpandableItemAdapter instanceof ExpandableDraggableItemAdapter) {
            this.mDraggingItemGroupRangeStart = -1;
            this.mDraggingItemGroupRangeEnd = -1;
            this.mDraggingItemChildRangeStart = -1;
            this.mDraggingItemChildRangeEnd = -1;
            if (i != i2) {
                ExpandableDraggableItemAdapter expandableDraggableItemAdapter = (ExpandableDraggableItemAdapter) this.mExpandableItemAdapter;
                long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
                int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
                int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
                long expandablePosition2 = this.mPositionTranslator.getExpandablePosition(i2);
                int packedPositionGroup2 = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition2);
                int packedPositionChild2 = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition2);
                int i4 = packedPositionChild == -1 ? 1 : 0;
                if (packedPositionChild2 != -1) {
                    i3 = 0;
                }
                if (i4 != 0 && r4 != 0) {
                    expandableDraggableItemAdapter.onMoveGroupItem(packedPositionGroup, packedPositionGroup2);
                    this.mPositionTranslator.moveGroupItem(packedPositionGroup, packedPositionGroup2);
                } else if (i4 == 0 && r4 == 0) {
                    if (packedPositionGroup != packedPositionGroup2 && i < i2) {
                        packedPositionChild2++;
                    }
                    i2 = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForChild(packedPositionGroup, packedPositionChild2));
                    expandableDraggableItemAdapter.onMoveChildItem(packedPositionGroup, packedPositionChild, packedPositionGroup2, packedPositionChild2);
                    this.mPositionTranslator.moveChildItem(packedPositionGroup, packedPositionChild, packedPositionGroup2, packedPositionChild2);
                } else {
                    if (i4 == 0) {
                        if (i2 < i) {
                            if (packedPositionGroup2 == 0) {
                                packedPositionChild2 = 0;
                                i3 = packedPositionGroup2;
                            } else {
                                i3 = packedPositionGroup2 - 1;
                                packedPositionChild2 = this.mPositionTranslator.getChildCount(i3);
                            }
                        } else if (this.mPositionTranslator.isGroupExpanded(packedPositionGroup2)) {
                            packedPositionChild2 = 0;
                            i3 = packedPositionGroup2;
                        } else {
                            packedPositionChild2 = this.mPositionTranslator.getChildCount(packedPositionGroup2);
                            i3 = packedPositionGroup2;
                        }
                        if (packedPositionGroup == i3) {
                            packedPositionChild2 = Math.min(packedPositionChild2, Math.max(0, this.mPositionTranslator.getChildCount(i3) - 1));
                        }
                        if (!(packedPositionGroup == i3 && packedPositionChild == packedPositionChild2)) {
                            if (!this.mPositionTranslator.isGroupExpanded(packedPositionGroup2)) {
                                i2 = -1;
                            }
                            expandableDraggableItemAdapter.onMoveChildItem(packedPositionGroup, packedPositionChild, i3, packedPositionChild2);
                            this.mPositionTranslator.moveChildItem(packedPositionGroup, packedPositionChild, i3, packedPositionChild2);
                        }
                    } else if (packedPositionGroup != packedPositionGroup2) {
                        i2 = this.mPositionTranslator.getFlatPosition(ExpandableAdapterHelper.getPackedPositionForGroup(packedPositionGroup2));
                        expandableDraggableItemAdapter.onMoveGroupItem(packedPositionGroup, packedPositionGroup2);
                        this.mPositionTranslator.moveGroupItem(packedPositionGroup, packedPositionGroup2);
                    }
                    i2 = i;
                }
                if (i2 == i) {
                    return;
                }
                if (i2 != -1) {
                    notifyItemMoved(i, i2);
                } else {
                    notifyItemRemoved(i);
                }
            }
        }
    }

    protected void onRelease() {
        super.onRelease();
        this.mExpandableItemAdapter = null;
        this.mExpandableListManager = null;
        this.mOnGroupExpandListener = null;
        this.mOnGroupCollapseListener = null;
    }

    public void onSetSwipeBackground(ViewHolder viewHolder, int i, int i2) {
        if (this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter) {
            BaseExpandableSwipeableItemAdapter baseExpandableSwipeableItemAdapter = (BaseExpandableSwipeableItemAdapter) this.mExpandableItemAdapter;
            long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
            int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
            int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(expandablePosition);
            if (packedPositionChild == -1) {
                baseExpandableSwipeableItemAdapter.onSetGroupItemSwipeBackground(viewHolder, packedPositionGroup, i2);
            } else {
                baseExpandableSwipeableItemAdapter.onSetChildItemSwipeBackground(viewHolder, packedPositionGroup, packedPositionChild, i2);
            }
        }
    }

    public SwipeResultAction onSwipeItem(ViewHolder viewHolder, int i, int i2) {
        if (!(this.mExpandableItemAdapter instanceof BaseExpandableSwipeableItemAdapter) || i == -1) {
            return null;
        }
        BaseExpandableSwipeableItemAdapter baseExpandableSwipeableItemAdapter = (BaseExpandableSwipeableItemAdapter) this.mExpandableItemAdapter;
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        return ExpandableSwipeableItemInternalUtils.invokeOnSwipeItem(baseExpandableSwipeableItemAdapter, viewHolder, ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition), ExpandableAdapterHelper.getPackedPositionChild(expandablePosition), i2);
    }

    boolean onTapItem(ViewHolder viewHolder, int i, int i2, int i3) {
        if (this.mExpandableItemAdapter == null) {
            return false;
        }
        long expandablePosition = this.mPositionTranslator.getExpandablePosition(i);
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(expandablePosition);
        if (ExpandableAdapterHelper.getPackedPositionChild(expandablePosition) != -1) {
            return false;
        }
        boolean z = !this.mPositionTranslator.isGroupExpanded(packedPositionGroup);
        if (!this.mExpandableItemAdapter.onCheckCanExpandOrCollapseGroup(viewHolder, packedPositionGroup, i2, i3, z)) {
            return false;
        }
        if (z) {
            expandGroup(packedPositionGroup, true);
        } else {
            collapseGroup(packedPositionGroup, true);
        }
        return true;
    }

    public void onViewRecycled(ViewHolder viewHolder) {
        if (viewHolder instanceof ExpandableItemViewHolder) {
            ((ExpandableItemViewHolder) viewHolder).setExpandStateFlags(-1);
        }
        super.onViewRecycled(viewHolder);
    }

    void restoreState(int[] iArr, boolean z, boolean z2) {
        OnGroupCollapseListener onGroupCollapseListener = null;
        ExpandablePositionTranslator expandablePositionTranslator = this.mPositionTranslator;
        ExpandableItemAdapter expandableItemAdapter = z ? this.mExpandableItemAdapter : null;
        OnGroupExpandListener onGroupExpandListener = z2 ? this.mOnGroupExpandListener : null;
        if (z2) {
            onGroupCollapseListener = this.mOnGroupCollapseListener;
        }
        expandablePositionTranslator.restoreExpandedGroupItems(iArr, expandableItemAdapter, onGroupExpandListener, onGroupCollapseListener);
    }

    void setOnGroupCollapseListener(OnGroupCollapseListener onGroupCollapseListener) {
        this.mOnGroupCollapseListener = onGroupCollapseListener;
    }

    void setOnGroupExpandListener(OnGroupExpandListener onGroupExpandListener) {
        this.mOnGroupExpandListener = onGroupExpandListener;
    }
}
