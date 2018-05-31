package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.utils.BaseWrapperAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.util.List;

class SwipeableItemWrapperAdapter<VH extends ViewHolder> extends BaseWrapperAdapter<VH> {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int STATE_FLAG_INITIAL_VALUE = -1;
    private static final String TAG = "ARVSwipeableWrapper";
    private RecyclerViewSwipeManager mSwipeManager;
    private BaseSwipeableItemAdapter mSwipeableItemAdapter;
    private long mSwipingItemId = -1;

    private interface Constants extends SwipeableItemConstants {
    }

    public SwipeableItemWrapperAdapter(RecyclerViewSwipeManager recyclerViewSwipeManager, Adapter<VH> adapter) {
        super(adapter);
        this.mSwipeableItemAdapter = getSwipeableItemAdapter(adapter);
        if (this.mSwipeableItemAdapter == null) {
            throw new IllegalArgumentException("adapter does not implement SwipeableItemAdapter");
        } else if (recyclerViewSwipeManager == null) {
            throw new IllegalArgumentException("manager cannot be null");
        } else {
            this.mSwipeManager = recyclerViewSwipeManager;
        }
    }

    private void cancelSwipe() {
        if (this.mSwipeManager != null) {
            this.mSwipeManager.cancelSwipe();
        }
    }

    private static boolean checkInRange(int i, int i2, int i3) {
        return i >= i2 && i < i2 + i3;
    }

    private static float getSwipeAmountFromAfterReaction(int i, int i2) {
        switch (i2) {
            case 1:
            case 2:
                switch (i) {
                    case 2:
                        return SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_LEFT;
                    case 3:
                        return SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_TOP;
                    case 4:
                        return SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_RIGHT;
                    case 5:
                        return SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_BOTTOM;
                    default:
                        return 0.0f;
                }
            default:
                return 0.0f;
        }
    }

    private static float getSwipeItemSlideAmount(SwipeableItemViewHolder swipeableItemViewHolder, boolean z) {
        return z ? swipeableItemViewHolder.getSwipeItemHorizontalSlideAmount() : swipeableItemViewHolder.getSwipeItemVerticalSlideAmount();
    }

    private static BaseSwipeableItemAdapter getSwipeableItemAdapter(Adapter adapter) {
        return (BaseSwipeableItemAdapter) WrapperAdapterUtils.findWrappedAdapter(adapter, BaseSwipeableItemAdapter.class);
    }

    private static void safeUpdateFlags(ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SwipeableItemViewHolder) {
            int swipeStateFlags = ((SwipeableItemViewHolder) viewHolder).getSwipeStateFlags();
            if (swipeStateFlags == -1 || ((swipeStateFlags ^ i) & ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) != 0) {
                i |= Integer.MIN_VALUE;
            }
            ((SwipeableItemViewHolder) viewHolder).setSwipeStateFlags(i);
        }
    }

    private static void setSwipeItemSlideAmount(SwipeableItemViewHolder swipeableItemViewHolder, float f, boolean z) {
        if (z) {
            swipeableItemViewHolder.setSwipeItemHorizontalSlideAmount(f);
        } else {
            swipeableItemViewHolder.setSwipeItemVerticalSlideAmount(f);
        }
    }

    private boolean swipeHorizontal() {
        return this.mSwipeManager.swipeHorizontal();
    }

    int getSwipeReactionType(ViewHolder viewHolder, int i, int i2, int i3) {
        return this.mSwipeableItemAdapter.onGetSwipeReactionType(viewHolder, i, i2, i3);
    }

    protected boolean isSwiping() {
        return this.mSwipingItemId != -1;
    }

    public void onBindViewHolder(VH vh, int i, List<Object> list) {
        float f = 0.0f;
        if (vh instanceof SwipeableItemViewHolder) {
            f = getSwipeItemSlideAmount((SwipeableItemViewHolder) vh, swipeHorizontal());
        }
        if (isSwiping()) {
            safeUpdateFlags(vh, vh.getItemId() == this.mSwipingItemId ? 3 : 1);
            super.onBindViewHolder(vh, i, list);
        } else {
            safeUpdateFlags(vh, 0);
            super.onBindViewHolder(vh, i, list);
        }
        if (vh instanceof SwipeableItemViewHolder) {
            float swipeItemSlideAmount = getSwipeItemSlideAmount((SwipeableItemViewHolder) vh, swipeHorizontal());
            boolean isSwiping = this.mSwipeManager.isSwiping();
            boolean isAnimationRunning = this.mSwipeManager.isAnimationRunning(vh);
            if (f != swipeItemSlideAmount || (!isSwiping && !isAnimationRunning)) {
                this.mSwipeManager.applySlideItem(vh, i, f, swipeItemSlideAmount, swipeHorizontal(), true, isSwiping);
            }
        }
    }

    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        VH onCreateViewHolder = super.onCreateViewHolder(viewGroup, i);
        if (onCreateViewHolder instanceof SwipeableItemViewHolder) {
            ((SwipeableItemViewHolder) onCreateViewHolder).setSwipeStateFlags(-1);
        }
        return onCreateViewHolder;
    }

    protected void onHandleWrappedAdapterChanged() {
        if (isSwiping()) {
            cancelSwipe();
        }
        super.onHandleWrappedAdapterChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int i, int i2) {
        super.onHandleWrappedAdapterItemRangeChanged(i, i2);
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int i, int i2, Object obj) {
        super.onHandleWrappedAdapterItemRangeChanged(i, i2, obj);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int i, int i2) {
        if (isSwiping()) {
            int swipingItemPosition = this.mSwipeManager.getSwipingItemPosition();
            if (swipingItemPosition >= i) {
                this.mSwipeManager.syncSwipingItemPosition(swipingItemPosition + i2);
            }
        }
        super.onHandleWrappedAdapterItemRangeInserted(i, i2);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int i, int i2) {
        if (isSwiping()) {
            int swipingItemPosition = this.mSwipeManager.getSwipingItemPosition();
            if (checkInRange(swipingItemPosition, i, i2)) {
                cancelSwipe();
            } else if (i < swipingItemPosition) {
                this.mSwipeManager.syncSwipingItemPosition(swipingItemPosition - i2);
            }
        }
        super.onHandleWrappedAdapterItemRangeRemoved(i, i2);
    }

    protected void onHandleWrappedAdapterRangeMoved(int i, int i2, int i3) {
        if (isSwiping()) {
            this.mSwipeManager.syncSwipingItemPosition();
        }
        super.onHandleWrappedAdapterRangeMoved(i, i2, i3);
    }

    protected void onRelease() {
        super.onRelease();
        this.mSwipeableItemAdapter = null;
        this.mSwipeManager = null;
        this.mSwipingItemId = -1;
    }

    SwipeResultAction onSwipeItemFinished(ViewHolder viewHolder, int i, int i2) {
        this.mSwipingItemId = -1;
        return SwipeableItemInternalUtils.invokeOnSwipeItem(this.mSwipeableItemAdapter, viewHolder, i, i2);
    }

    void onSwipeItemFinished2(ViewHolder viewHolder, int i, int i2, int i3, SwipeResultAction swipeResultAction) {
        ((SwipeableItemViewHolder) viewHolder).setSwipeResult(i2);
        ((SwipeableItemViewHolder) viewHolder).setAfterSwipeReaction(i3);
        setSwipeItemSlideAmount((SwipeableItemViewHolder) viewHolder, getSwipeAmountFromAfterReaction(i2, i3), swipeHorizontal());
        swipeResultAction.performAction();
        notifyDataSetChanged();
    }

    void onSwipeItemStarted(RecyclerViewSwipeManager recyclerViewSwipeManager, ViewHolder viewHolder, long j) {
        this.mSwipingItemId = j;
        notifyDataSetChanged();
    }

    void onUpdateSlideAmount(ViewHolder viewHolder, int i, boolean z, float f, boolean z2) {
        SwipeableItemViewHolder swipeableItemViewHolder = (SwipeableItemViewHolder) viewHolder;
        float f2 = z ? f : 0.0f;
        if (z) {
            f = 0.0f;
        }
        swipeableItemViewHolder.onSlideAmountUpdated(f2, f, z2);
    }

    void onUpdateSlideAmount(ViewHolder viewHolder, int i, boolean z, float f, boolean z2, int i2) {
        this.mSwipeableItemAdapter.onSetSwipeBackground(viewHolder, i, i2);
        SwipeableItemViewHolder swipeableItemViewHolder = (SwipeableItemViewHolder) viewHolder;
        float f2 = z ? f : 0.0f;
        if (z) {
            f = 0.0f;
        }
        swipeableItemViewHolder.onSlideAmountUpdated(f2, f, z2);
    }

    public void onViewRecycled(VH vh) {
        super.onViewRecycled(vh);
        if (this.mSwipingItemId != -1 && this.mSwipingItemId == vh.getItemId()) {
            this.mSwipeManager.cancelSwipe();
        }
        if (vh instanceof SwipeableItemViewHolder) {
            if (this.mSwipeManager != null) {
                this.mSwipeManager.cancelPendingAnimations(vh);
            }
            SwipeableItemViewHolder swipeableItemViewHolder = (SwipeableItemViewHolder) vh;
            swipeableItemViewHolder.setSwipeResult(0);
            swipeableItemViewHolder.setAfterSwipeReaction(0);
            swipeableItemViewHolder.setSwipeItemHorizontalSlideAmount(0.0f);
            swipeableItemViewHolder.setSwipeItemVerticalSlideAmount(0.0f);
            View swipeableContainerView = swipeableItemViewHolder.getSwipeableContainerView();
            if (swipeableContainerView != null) {
                ViewCompat.animate(swipeableContainerView).cancel();
                ViewCompat.setTranslationX(swipeableContainerView, 0.0f);
                ViewCompat.setTranslationY(swipeableContainerView, 0.0f);
            }
        }
    }
}
