package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

class SwapTargetItemOperator extends BaseDraggableItemDecorator {
    private static final String TAG = "SwapTargetItemOperator";
    private float mCurTranslationPhase;
    private final Rect mDraggingItemDecorationOffsets = new Rect();
    private DraggingItemInfo mDraggingItemInfo;
    private ItemDraggableRange mRange;
    private float mReqTranslationPhase;
    private boolean mStarted;
    private final Rect mSwapTargetDecorationOffsets = new Rect();
    private ViewHolder mSwapTargetItem;
    private boolean mSwapTargetItemChanged;
    private final Rect mSwapTargetItemMargins = new Rect();
    private Interpolator mSwapTargetTranslationInterpolator;
    private int mTranslationX;
    private int mTranslationY;

    public SwapTargetItemOperator(RecyclerView recyclerView, ViewHolder viewHolder, ItemDraggableRange itemDraggableRange, DraggingItemInfo draggingItemInfo) {
        super(recyclerView, viewHolder);
        this.mDraggingItemInfo = draggingItemInfo;
        this.mRange = itemDraggableRange;
        CustomRecyclerViewUtils.getDecorationOffsets(this.mRecyclerView.getLayoutManager(), this.mDraggingItemViewHolder.itemView, this.mDraggingItemDecorationOffsets);
    }

    private static float calculateCurrentTranslationPhase(float f, float f2) {
        float f3 = (0.7f * f) + (0.3f * f2);
        return Math.abs(f3 - f2) < 0.01f ? f2 : f3;
    }

    private float calculateTranslationPhase(ViewHolder viewHolder, ViewHolder viewHolder2) {
        View view = viewHolder2.itemView;
        int layoutPosition = viewHolder.getLayoutPosition();
        int layoutPosition2 = viewHolder2.getLayoutPosition();
        CustomRecyclerViewUtils.getDecorationOffsets(this.mRecyclerView.getLayoutManager(), view, this.mSwapTargetDecorationOffsets);
        CustomRecyclerViewUtils.getLayoutMargins(view, this.mSwapTargetItemMargins);
        Rect rect = this.mSwapTargetItemMargins;
        Rect rect2 = this.mSwapTargetDecorationOffsets;
        int height = (((view.getHeight() + rect.top) + rect.bottom) + rect2.top) + rect2.bottom;
        int width = (((view.getWidth() + rect.left) + rect.right) + rect2.left) + rect2.right;
        float left = width != 0 ? ((float) (viewHolder.itemView.getLeft() - this.mTranslationX)) / ((float) width) : 0.0f;
        float top = height != 0 ? ((float) (viewHolder.itemView.getTop() - this.mTranslationY)) / ((float) height) : 0.0f;
        int orientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
        if (orientation != 1) {
            top = orientation == 0 ? layoutPosition > layoutPosition2 ? left : 1.0f + left : 0.0f;
        } else if (layoutPosition <= layoutPosition2) {
            top += 1.0f;
        }
        return Math.min(Math.max(top, 0.0f), 1.0f);
    }

    private void updateSwapTargetTranslation(ViewHolder viewHolder, ViewHolder viewHolder2, float f) {
        View view = viewHolder2.itemView;
        int layoutPosition = viewHolder.getLayoutPosition();
        int layoutPosition2 = viewHolder2.getLayoutPosition();
        Rect rect = this.mDraggingItemInfo.margins;
        Rect rect2 = this.mDraggingItemDecorationOffsets;
        int i = (((this.mDraggingItemInfo.height + rect.top) + rect.bottom) + rect2.top) + rect2.bottom;
        int i2 = ((rect.right + (this.mDraggingItemInfo.width + rect.left)) + rect2.left) + rect2.right;
        if (this.mSwapTargetTranslationInterpolator != null) {
            f = this.mSwapTargetTranslationInterpolator.getInterpolation(f);
        }
        switch (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView)) {
            case 0:
                if (layoutPosition > layoutPosition2) {
                    ViewCompat.setTranslationX(view, ((float) i2) * f);
                    return;
                } else {
                    ViewCompat.setTranslationX(view, (f - 1.0f) * ((float) i2));
                    return;
                }
            case 1:
                if (layoutPosition > layoutPosition2) {
                    ViewCompat.setTranslationY(view, ((float) i) * f);
                    return;
                } else {
                    ViewCompat.setTranslationY(view, (f - 1.0f) * ((float) i));
                    return;
                }
            default:
                return;
        }
    }

    public void finish(boolean z) {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        this.mRecyclerView.stopScroll();
        if (this.mSwapTargetItem != null) {
            updateSwapTargetTranslation(this.mDraggingItemViewHolder, this.mSwapTargetItem, this.mCurTranslationPhase);
            moveToDefaultPosition(this.mSwapTargetItem.itemView, z);
            this.mSwapTargetItem = null;
        }
        this.mRange = null;
        this.mDraggingItemViewHolder = null;
        this.mTranslationX = 0;
        this.mTranslationY = 0;
        this.mCurTranslationPhase = 0.0f;
        this.mReqTranslationPhase = 0.0f;
        this.mStarted = false;
        this.mDraggingItemInfo = null;
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        ViewHolder viewHolder = this.mDraggingItemViewHolder;
        ViewHolder viewHolder2 = this.mSwapTargetItem;
        if (viewHolder != null && viewHolder2 != null && viewHolder.getItemId() == this.mDraggingItemInfo.id) {
            this.mReqTranslationPhase = calculateTranslationPhase(viewHolder, viewHolder2);
            if (this.mSwapTargetItemChanged) {
                this.mSwapTargetItemChanged = false;
                this.mCurTranslationPhase = this.mReqTranslationPhase;
            } else {
                this.mCurTranslationPhase = calculateCurrentTranslationPhase(this.mCurTranslationPhase, this.mReqTranslationPhase);
            }
            updateSwapTargetTranslation(viewHolder, viewHolder2, this.mCurTranslationPhase);
        }
    }

    public void setSwapTargetItem(ViewHolder viewHolder) {
        if (this.mSwapTargetItem != viewHolder) {
            if (this.mSwapTargetItem != null) {
                ViewCompat.animate(this.mSwapTargetItem.itemView).translationX(0.0f).translationY(0.0f).setDuration(10).start();
            }
            this.mSwapTargetItem = viewHolder;
            this.mSwapTargetItemChanged = true;
        }
    }

    public void setSwapTargetTranslationInterpolator(Interpolator interpolator) {
        this.mSwapTargetTranslationInterpolator = interpolator;
    }

    public void start() {
        if (!this.mStarted) {
            this.mRecyclerView.addItemDecoration(this, 0);
            this.mStarted = true;
        }
    }

    public void update(int i, int i2) {
        this.mTranslationX = i;
        this.mTranslationY = i2;
    }
}
