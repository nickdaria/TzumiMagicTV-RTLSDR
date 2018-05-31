package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.BaseSwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemInternalUtils;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.List;

class DraggableItemWrapperAdapter<VH extends ViewHolder> extends BaseWrapperAdapter<VH> implements SwipeableItemAdapter<VH> {
    private static final boolean DEBUG_BYPASS_MOVE_OPERATION_MODE = false;
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGI = true;
    private static final boolean LOCAL_LOGV = false;
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVDraggableWrapper";
    private RecyclerViewDragDropManager mDragDropManager;
    private DraggableItemAdapter mDraggableItemAdapter;
    private ItemDraggableRange mDraggableRange;
    private int mDraggingItemCurrentPosition = -1;
    private DraggingItemInfo mDraggingItemInfo;
    private int mDraggingItemInitialPosition = -1;
    private ViewHolder mDraggingItemViewHolder;
    private int mItemMoveMode;

    private interface Constants extends DraggableItemConstants {
    }

    public DraggableItemWrapperAdapter(RecyclerViewDragDropManager recyclerViewDragDropManager, Adapter<VH> adapter) {
        super(adapter);
        this.mDraggableItemAdapter = getDraggableItemAdapter(adapter);
        if (getDraggableItemAdapter(adapter) == null) {
            throw new IllegalArgumentException("adapter does not implement DraggableItemAdapter");
        } else if (recyclerViewDragDropManager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        } else {
            this.mDragDropManager = recyclerViewDragDropManager;
        }
    }

    private void cancelDrag() {
        if (this.mDragDropManager != null) {
            this.mDragDropManager.cancelDrag();
        }
    }

    protected static int convertToOriginalPosition(int i, int i2, int i3, int i4) {
        if (i2 < 0 || i3 < 0) {
            return i;
        }
        if (i4 == 0) {
            return (i2 == i3 || ((i < i2 && i < i3) || (i > i2 && i > i3))) ? i : i3 < i2 ? i != i3 ? i - 1 : i2 : i != i3 ? i + 1 : i2;
        } else {
            if (i4 == 1) {
                return i != i3 ? i == i2 ? i3 : i : i2;
            } else {
                throw new IllegalStateException("unexpected state");
            }
        }
    }

    private static DraggableItemAdapter getDraggableItemAdapter(Adapter adapter) {
        return (DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, DraggableItemAdapter.class);
    }

    private int getOriginalPosition(int i) {
        return isDragging() ? convertToOriginalPosition(i, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode) : i;
    }

    private void onDraggingItemRecycled() {
        Log.i(TAG, "a view holder object which is bound to currently dragging item is recycled");
        this.mDraggingItemViewHolder = null;
        this.mDragDropManager.onDraggingItemViewRecycled();
    }

    private static void safeUpdateFlags(ViewHolder viewHolder, int i) {
        if (viewHolder instanceof DraggableItemViewHolder) {
            int dragStateFlags = ((DraggableItemViewHolder) viewHolder).getDragStateFlags();
            if (dragStateFlags == -1 || ((dragStateFlags ^ i) & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) != 0) {
                i |= Integer.MIN_VALUE;
            }
            ((DraggableItemViewHolder) viewHolder).setDragStateFlags(i);
        }
    }

    private boolean shouldCancelDragOnDataUpdated() {
        return isDragging();
    }

    boolean canDropItems(int i, int i2) {
        return this.mDraggableItemAdapter.onCheckCanDrop(i, i2);
    }

    boolean canStartDrag(ViewHolder viewHolder, int i, int i2, int i3) {
        return this.mDraggableItemAdapter.onCheckCanStartDrag(viewHolder, i, i2, i3);
    }

    int getDraggingItemCurrentPosition() {
        return this.mDraggingItemCurrentPosition;
    }

    int getDraggingItemInitialPosition() {
        return this.mDraggingItemInitialPosition;
    }

    ItemDraggableRange getItemDraggableRange(ViewHolder viewHolder, int i) {
        return this.mDraggableItemAdapter.onGetItemDraggableRange(viewHolder, i);
    }

    public long getItemId(int i) {
        return isDragging() ? super.getItemId(convertToOriginalPosition(i, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode)) : super.getItemId(i);
    }

    public int getItemViewType(int i) {
        return isDragging() ? super.getItemViewType(convertToOriginalPosition(i, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode)) : super.getItemViewType(i);
    }

    protected boolean isDragging() {
        return this.mDraggingItemInfo != null;
    }

    void moveItem(int i, int i2, int i3) {
        int convertToOriginalPosition = convertToOriginalPosition(i, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode);
        if (convertToOriginalPosition != this.mDraggingItemInitialPosition) {
            throw new IllegalStateException("onMoveItem() - may be a bug or has duplicate IDs  --- mDraggingItemInitialPosition = " + this.mDraggingItemInitialPosition + ", " + "mDraggingItemCurrentPosition = " + this.mDraggingItemCurrentPosition + ", " + "origFromPosition = " + convertToOriginalPosition + ", " + "fromPosition = " + i + ", " + "toPosition = " + i2);
        }
        this.mDraggingItemCurrentPosition = i2;
        if (this.mItemMoveMode == 0 && CustomRecyclerViewUtils.isLinearLayout(i3)) {
            notifyItemMoved(i, i2);
        } else {
            notifyDataSetChanged();
        }
    }

    public void onBindViewHolder(VH vh, int i, List<Object> list) {
        if (isDragging()) {
            long j = this.mDraggingItemInfo.id;
            long itemId = vh.getItemId();
            int convertToOriginalPosition = convertToOriginalPosition(i, this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition, this.mItemMoveMode);
            if (itemId == j && vh != this.mDraggingItemViewHolder) {
                if (this.mDraggingItemViewHolder != null) {
                    onDraggingItemRecycled();
                }
                Log.i(TAG, "a new view holder object for the currently dragging item is assigned");
                this.mDraggingItemViewHolder = vh;
                this.mDragDropManager.onNewDraggingItemViewBound(vh);
            }
            int i2 = 1;
            if (itemId == j) {
                i2 = 3;
            }
            if (this.mDraggableRange.checkInRange(i)) {
                i2 |= 4;
            }
            safeUpdateFlags(vh, i2);
            super.onBindViewHolder(vh, convertToOriginalPosition, list);
            return;
        }
        safeUpdateFlags(vh, 0);
        super.onBindViewHolder(vh, i, list);
    }

    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        VH onCreateViewHolder = super.onCreateViewHolder(viewGroup, i);
        if (onCreateViewHolder instanceof DraggableItemViewHolder) {
            ((DraggableItemViewHolder) onCreateViewHolder).setDragStateFlags(-1);
        }
        return onCreateViewHolder;
    }

    void onDragItemFinished(boolean z) {
        if (z && this.mDraggingItemCurrentPosition != this.mDraggingItemInitialPosition) {
            ((DraggableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(getWrappedAdapter(), DraggableItemAdapter.class)).onMoveItem(this.mDraggingItemInitialPosition, this.mDraggingItemCurrentPosition);
        }
        this.mDraggingItemInitialPosition = -1;
        this.mDraggingItemCurrentPosition = -1;
        this.mDraggableRange = null;
        this.mDraggingItemInfo = null;
        this.mDraggingItemViewHolder = null;
        notifyDataSetChanged();
    }

    void onDragItemStarted(DraggingItemInfo draggingItemInfo, ViewHolder viewHolder, ItemDraggableRange itemDraggableRange, int i) {
        if (viewHolder.getItemId() == -1) {
            throw new IllegalStateException("dragging target must provides valid ID");
        }
        int adapterPosition = viewHolder.getAdapterPosition();
        this.mDraggingItemCurrentPosition = adapterPosition;
        this.mDraggingItemInitialPosition = adapterPosition;
        this.mDraggingItemInfo = draggingItemInfo;
        this.mDraggingItemViewHolder = viewHolder;
        this.mDraggableRange = itemDraggableRange;
        this.mItemMoveMode = i;
        notifyDataSetChanged();
    }

    public int onGetSwipeReactionType(VH vh, int i, int i2, int i3) {
        Adapter wrappedAdapter = getWrappedAdapter();
        if (!(wrappedAdapter instanceof BaseSwipeableItemAdapter)) {
            return 0;
        }
        return ((BaseSwipeableItemAdapter) wrappedAdapter).onGetSwipeReactionType(vh, getOriginalPosition(i), i2, i3);
    }

    protected void onHandleWrappedAdapterChanged() {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterChanged();
        }
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int i, int i2) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeChanged(i, i2);
        }
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int i, int i2) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeInserted(i, i2);
        }
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int i, int i2) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterItemRangeRemoved(i, i2);
        }
    }

    protected void onHandleWrappedAdapterRangeMoved(int i, int i2, int i3) {
        if (shouldCancelDragOnDataUpdated()) {
            cancelDrag();
        } else {
            super.onHandleWrappedAdapterRangeMoved(i, i2, i3);
        }
    }

    protected void onRelease() {
        super.onRelease();
        this.mDraggingItemViewHolder = null;
        this.mDraggableItemAdapter = null;
        this.mDragDropManager = null;
    }

    public void onSetSwipeBackground(VH vh, int i, int i2) {
        Adapter wrappedAdapter = getWrappedAdapter();
        if (wrappedAdapter instanceof BaseSwipeableItemAdapter) {
            ((BaseSwipeableItemAdapter) wrappedAdapter).onSetSwipeBackground(vh, getOriginalPosition(i), i2);
        }
    }

    public SwipeResultAction onSwipeItem(VH vh, int i, int i2) {
        Adapter wrappedAdapter = getWrappedAdapter();
        if (!(wrappedAdapter instanceof BaseSwipeableItemAdapter)) {
            return new SwipeResultActionDefault();
        }
        return SwipeableItemInternalUtils.invokeOnSwipeItem((BaseSwipeableItemAdapter) wrappedAdapter, vh, getOriginalPosition(i), i2);
    }

    public void onViewRecycled(VH vh) {
        if (isDragging() && vh == this.mDraggingItemViewHolder) {
            onDraggingItemRecycled();
        }
        super.onViewRecycled(vh);
    }
}
