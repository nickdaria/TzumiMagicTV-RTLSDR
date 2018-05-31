package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

public class RecyclerViewExpandableItemManager implements ExpandableItemConstants {
    public static final long NO_EXPANDABLE_POSITION = -1;
    private static final String TAG = "ARVExpandableItemMgr";
    private ExpandableRecyclerViewWrapperAdapter mAdapter;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C05741();
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private RecyclerView mRecyclerView;
    private SavedState mSavedState;
    private int mTouchSlop;
    private long mTouchedItemId = -1;

    class C05741 implements OnItemTouchListener {
        C05741() {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return RecyclerViewExpandableItemManager.this.onInterceptTouchEvent(recyclerView, motionEvent);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i, boolean z);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i, boolean z);
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new C05751();
        final int[] adapterSavedState;

        static class C05751 implements Creator<SavedState> {
            C05751() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        SavedState(Parcel parcel) {
            this.adapterSavedState = parcel.createIntArray();
        }

        public SavedState(int[] iArr) {
            this.adapterSavedState = iArr;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeIntArray(this.adapterSavedState);
        }
    }

    public RecyclerViewExpandableItemManager(@Nullable Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mSavedState = (SavedState) parcelable;
        }
    }

    public static int getChildViewType(int i) {
        return ExpandableAdapterHelper.getChildViewType(i);
    }

    public static long getCombinedChildId(long j, long j2) {
        return ExpandableAdapterHelper.getCombinedChildId(j, j2);
    }

    public static long getCombinedGroupId(long j) {
        return ExpandableAdapterHelper.getCombinedGroupId(j);
    }

    public static int getGroupViewType(int i) {
        return ExpandableAdapterHelper.getGroupViewType(i);
    }

    public static int getPackedPositionChild(long j) {
        return ExpandableAdapterHelper.getPackedPositionChild(j);
    }

    public static long getPackedPositionForChild(int i, int i2) {
        return ExpandableAdapterHelper.getPackedPositionForChild(i, i2);
    }

    public static long getPackedPositionForGroup(int i) {
        return ExpandableAdapterHelper.getPackedPositionForGroup(i);
    }

    public static int getPackedPositionGroup(long j) {
        return ExpandableAdapterHelper.getPackedPositionGroup(j);
    }

    private void handleActionDown(RecyclerView recyclerView, MotionEvent motionEvent) {
        ViewHolder findChildViewHolderUnderWithTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(recyclerView, motionEvent.getX(), motionEvent.getY());
        this.mInitialTouchX = (int) (motionEvent.getX() + 0.5f);
        this.mInitialTouchY = (int) (motionEvent.getY() + 0.5f);
        if (findChildViewHolderUnderWithTranslation instanceof ExpandableItemViewHolder) {
            this.mTouchedItemId = findChildViewHolderUnderWithTranslation.getItemId();
        } else {
            this.mTouchedItemId = -1;
        }
    }

    private boolean handleActionUpOrCancel(RecyclerView recyclerView, MotionEvent motionEvent) {
        long j = this.mTouchedItemId;
        int i = this.mInitialTouchX;
        int i2 = this.mInitialTouchY;
        this.mTouchedItemId = -1;
        this.mInitialTouchX = 0;
        this.mInitialTouchY = 0;
        if (j == -1 || MotionEventCompat.getActionMasked(motionEvent) != 1) {
            return false;
        }
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        i2 = y - i2;
        if (Math.abs(x - i) >= this.mTouchSlop || Math.abs(i2) >= this.mTouchSlop) {
            return false;
        }
        ViewHolder findChildViewHolderUnderWithTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(recyclerView, motionEvent.getX(), motionEvent.getY());
        if (findChildViewHolderUnderWithTranslation == null || findChildViewHolderUnderWithTranslation.getItemId() != j) {
            return false;
        }
        int synchronizedPosition = CustomRecyclerViewUtils.getSynchronizedPosition(findChildViewHolderUnderWithTranslation);
        if (synchronizedPosition == -1) {
            return false;
        }
        View view = findChildViewHolderUnderWithTranslation.itemView;
        return this.mAdapter.onTapItem(findChildViewHolderUnderWithTranslation, synchronizedPosition, x - (((int) (ViewCompat.getTranslationX(view) + 0.5f)) + view.getLeft()), y - (view.getTop() + ((int) (ViewCompat.getTranslationY(view) + 0.5f))));
    }

    public static boolean isGroupViewType(int i) {
        return ExpandableAdapterHelper.isGroupViewType(i);
    }

    public void attachRecyclerView(@NonNull RecyclerView recyclerView) {
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else {
            this.mRecyclerView = recyclerView;
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            this.mTouchSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
        }
    }

    public void collapseAll() {
        if (this.mAdapter != null) {
            this.mAdapter.collapseAll();
        }
    }

    public boolean collapseGroup(int i) {
        return this.mAdapter != null && this.mAdapter.collapseGroup(i, false);
    }

    public Adapter createWrappedAdapter(@NonNull Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        } else if (this.mAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        } else {
            int[] iArr = this.mSavedState != null ? this.mSavedState.adapterSavedState : null;
            this.mSavedState = null;
            this.mAdapter = new ExpandableRecyclerViewWrapperAdapter(this, adapter, iArr);
            this.mAdapter.setOnGroupExpandListener(this.mOnGroupExpandListener);
            this.mOnGroupExpandListener = null;
            this.mAdapter.setOnGroupCollapseListener(this.mOnGroupCollapseListener);
            this.mOnGroupCollapseListener = null;
            return this.mAdapter;
        }
    }

    public void expandAll() {
        if (this.mAdapter != null) {
            this.mAdapter.expandAll();
        }
    }

    public boolean expandGroup(int i) {
        return this.mAdapter != null && this.mAdapter.expandGroup(i, false);
    }

    public int getChildCount(int i) {
        return this.mAdapter.getChildCount(i);
    }

    public int getCollapsedGroupsCount() {
        return this.mAdapter.getCollapsedGroupsCount();
    }

    public long getExpandablePosition(int i) {
        return this.mAdapter == null ? -1 : this.mAdapter.getExpandablePosition(i);
    }

    public int getExpandedGroupsCount() {
        return this.mAdapter.getExpandedGroupsCount();
    }

    public int getFlatPosition(long j) {
        return this.mAdapter == null ? -1 : this.mAdapter.getFlatPosition(j);
    }

    public int getGroupCount() {
        return this.mAdapter.getGroupCount();
    }

    public Parcelable getSavedState() {
        int[] iArr = null;
        if (this.mAdapter != null) {
            iArr = this.mAdapter.getExpandedItemsSavedStateArray();
        }
        return new SavedState(iArr);
    }

    public boolean isAllGroupsCollapsed() {
        return this.mAdapter.isAllGroupsCollapsed();
    }

    public boolean isAllGroupsExpanded() {
        return this.mAdapter.isAllGroupsExpanded();
    }

    public boolean isGroupExpanded(int i) {
        return this.mAdapter != null && this.mAdapter.isGroupExpanded(i);
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    public void notifyChildItemChanged(int i, int i2) {
        this.mAdapter.notifyChildItemChanged(i, i2, null);
    }

    public void notifyChildItemChanged(int i, int i2, Object obj) {
        this.mAdapter.notifyChildItemChanged(i, i2, obj);
    }

    public void notifyChildItemInserted(int i, int i2) {
        this.mAdapter.notifyChildItemInserted(i, i2);
    }

    public void notifyChildItemMoved(int i, int i2, int i3) {
        this.mAdapter.notifyChildItemMoved(i, i2, i3);
    }

    public void notifyChildItemMoved(int i, int i2, int i3, int i4) {
        this.mAdapter.notifyChildItemMoved(i, i2, i3, i4);
    }

    public void notifyChildItemRangeChanged(int i, int i2, int i3) {
        this.mAdapter.notifyChildItemRangeChanged(i, i2, i3, null);
    }

    public void notifyChildItemRangeChanged(int i, int i2, int i3, Object obj) {
        this.mAdapter.notifyChildItemRangeChanged(i, i2, i3, obj);
    }

    public void notifyChildItemRangeInserted(int i, int i2, int i3) {
        this.mAdapter.notifyChildItemRangeInserted(i, i2, i3);
    }

    public void notifyChildItemRangeRemoved(int i, int i2, int i3) {
        this.mAdapter.notifyChildItemRangeRemoved(i, i2, i3);
    }

    public void notifyChildItemRemoved(int i, int i2) {
        this.mAdapter.notifyChildItemRemoved(i, i2);
    }

    public void notifyChildrenOfGroupItemChanged(int i) {
        this.mAdapter.notifyChildrenOfGroupItemChanged(i, null);
    }

    public void notifyChildrenOfGroupItemChanged(int i, Object obj) {
        this.mAdapter.notifyChildrenOfGroupItemChanged(i, obj);
    }

    public void notifyGroupAndChildrenItemsChanged(int i) {
        this.mAdapter.notifyGroupAndChildrenItemsChanged(i, null);
    }

    public void notifyGroupAndChildrenItemsChanged(int i, Object obj) {
        this.mAdapter.notifyGroupAndChildrenItemsChanged(i, obj);
    }

    public void notifyGroupItemChanged(int i) {
        this.mAdapter.notifyGroupItemChanged(i);
    }

    public void notifyGroupItemInserted(int i) {
        notifyGroupItemInserted(i, false);
    }

    public void notifyGroupItemInserted(int i, boolean z) {
        this.mAdapter.notifyGroupItemInserted(i, z);
    }

    public void notifyGroupItemMoved(int i, int i2) {
        this.mAdapter.notifyGroupItemMoved(i, i2);
    }

    public void notifyGroupItemRangeInserted(int i, int i2) {
        notifyGroupItemRangeInserted(i, i2, false);
    }

    public void notifyGroupItemRangeInserted(int i, int i2, boolean z) {
        this.mAdapter.notifyGroupItemRangeInserted(i, i2, z);
    }

    public void notifyGroupItemRangeRemoved(int i, int i2) {
        this.mAdapter.notifyGroupItemRangeRemoved(i, i2);
    }

    public void notifyGroupItemRemoved(int i) {
        this.mAdapter.notifyGroupItemRemoved(i);
    }

    boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.mAdapter != null) {
            switch (MotionEventCompat.getActionMasked(motionEvent)) {
                case 0:
                    handleActionDown(recyclerView, motionEvent);
                    break;
                case 1:
                case 3:
                    if (handleActionUpOrCancel(recyclerView, motionEvent)) {
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    public void release() {
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        this.mOnGroupExpandListener = null;
        this.mOnGroupCollapseListener = null;
        this.mRecyclerView = null;
        this.mSavedState = null;
    }

    public void restoreState(@Nullable Parcelable parcelable) {
        restoreState(parcelable, false, false);
    }

    public void restoreState(@Nullable Parcelable parcelable, boolean z, boolean z2) {
        if (parcelable != null) {
            if (!(parcelable instanceof SavedState)) {
                throw new IllegalArgumentException("Illegal saved state object passed");
            } else if (this.mAdapter == null || this.mRecyclerView == null) {
                throw new IllegalStateException("RecyclerView has not been attached");
            } else {
                this.mAdapter.restoreState(((SavedState) parcelable).adapterSavedState, z, z2);
            }
        }
    }

    public void scrollToGroup(int i, int i2) {
        scrollToGroup(i, i2, 0, 0);
    }

    public void scrollToGroup(int i, int i2, int i3, int i4) {
        scrollToGroupWithTotalChildrenHeight(i, getChildCount(i) * i2, i3, i4);
    }

    public void scrollToGroupWithTotalChildrenHeight(int i, int i2, int i3, int i4) {
        int flatPosition = getFlatPosition(getPackedPositionForGroup(i));
        ViewHolder findViewHolderForLayoutPosition = this.mRecyclerView.findViewHolderForLayoutPosition(flatPosition);
        if (findViewHolderForLayoutPosition != null) {
            if (!isGroupExpanded(i)) {
                i2 = 0;
            }
            int top = findViewHolderForLayoutPosition.itemView.getTop();
            int height = this.mRecyclerView.getHeight() - findViewHolderForLayoutPosition.itemView.getBottom();
            if (top <= i3) {
                ((LinearLayoutManager) this.mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(flatPosition, (i3 - this.mRecyclerView.getPaddingTop()) - ((LayoutParams) findViewHolderForLayoutPosition.itemView.getLayoutParams()).topMargin);
            } else if (height < i2 + i4) {
                this.mRecyclerView.smoothScrollBy(0, Math.min(top - i3, Math.max(0, (i2 + i4) - height)));
            }
        }
    }

    public void setOnGroupCollapseListener(@Nullable OnGroupCollapseListener onGroupCollapseListener) {
        if (this.mAdapter != null) {
            this.mAdapter.setOnGroupCollapseListener(onGroupCollapseListener);
        } else {
            this.mOnGroupCollapseListener = onGroupCollapseListener;
        }
    }

    public void setOnGroupExpandListener(@Nullable OnGroupExpandListener onGroupExpandListener) {
        if (this.mAdapter != null) {
            this.mAdapter.setOnGroupExpandListener(onGroupExpandListener);
        } else {
            this.mOnGroupExpandListener = onGroupExpandListener;
        }
    }
}
