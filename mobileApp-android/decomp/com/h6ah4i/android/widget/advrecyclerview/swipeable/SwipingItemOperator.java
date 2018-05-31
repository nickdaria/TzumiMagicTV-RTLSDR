package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.animation.Interpolator;

class SwipingItemOperator {
    private static final int MIN_GRABBING_AREA_SIZE = 48;
    private static final int REACTION_CAN_NOT_SWIPE = 0;
    private static final int REACTION_CAN_NOT_SWIPE_WITH_RUBBER_BAND_EFFECT = 1;
    private static final int REACTION_CAN_SWIPE = 2;
    private static final Interpolator RUBBER_BAND_INTERPOLATOR = new RubberBandInterpolator(RUBBER_BAND_LIMIT);
    private static final float RUBBER_BAND_LIMIT = 0.15f;
    private static final String TAG = "SwipingItemOperator";
    private int mDownSwipeReactionType;
    private int mInitialTranslateAmountX;
    private int mInitialTranslateAmountY;
    private float mInvSwipingItemHeight = calcInv(this.mSwipingItemHeight);
    private float mInvSwipingItemWidth = calcInv(this.mSwipingItemWidth);
    private int mLeftSwipeReactionType;
    private float mPrevTranslateAmount;
    private int mRightSwipeReactionType;
    private int mSwipeDistanceX;
    private int mSwipeDistanceY;
    private final boolean mSwipeHorizontal;
    private RecyclerViewSwipeManager mSwipeManager;
    private ViewHolder mSwipingItem;
    private View mSwipingItemContainerView;
    private final int mSwipingItemHeight = this.mSwipingItemContainerView.getHeight();
    private int mSwipingItemWidth = this.mSwipingItemContainerView.getWidth();
    private int mUpSwipeReactionType;

    public SwipingItemOperator(RecyclerViewSwipeManager recyclerViewSwipeManager, ViewHolder viewHolder, int i, boolean z) {
        this.mSwipeManager = recyclerViewSwipeManager;
        this.mSwipingItem = viewHolder;
        this.mLeftSwipeReactionType = SwipeReactionUtils.extractLeftReaction(i);
        this.mUpSwipeReactionType = SwipeReactionUtils.extractUpReaction(i);
        this.mRightSwipeReactionType = SwipeReactionUtils.extractRightReaction(i);
        this.mDownSwipeReactionType = SwipeReactionUtils.extractDownReaction(i);
        this.mSwipeHorizontal = z;
        this.mSwipingItemContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
    }

    private static float calcInv(int i) {
        return i != 0 ? 1.0f / ((float) i) : 0.0f;
    }

    private static int clip(int i, int i2, int i3) {
        return Math.min(Math.max(i, i2), i3);
    }

    public void finish() {
        this.mSwipeManager = null;
        this.mSwipingItem = null;
        this.mSwipeDistanceX = 0;
        this.mSwipeDistanceY = 0;
        this.mSwipingItemWidth = 0;
        this.mInvSwipingItemWidth = 0.0f;
        this.mInvSwipingItemHeight = 0.0f;
        this.mLeftSwipeReactionType = 0;
        this.mUpSwipeReactionType = 0;
        this.mRightSwipeReactionType = 0;
        this.mDownSwipeReactionType = 0;
        this.mPrevTranslateAmount = 0.0f;
        this.mInitialTranslateAmountX = 0;
        this.mInitialTranslateAmountY = 0;
        this.mSwipingItemContainerView = null;
    }

    public void start() {
        float f = this.mSwipingItem.itemView.getResources().getDisplayMetrics().density;
        int max = Math.max(0, this.mSwipingItemWidth - ((int) (f * 48.0f)));
        int max2 = Math.max(0, this.mSwipingItemHeight - ((int) (f * 48.0f)));
        this.mInitialTranslateAmountX = clip(this.mSwipeManager.getSwipeContainerViewTranslationX(this.mSwipingItem), -max, max);
        this.mInitialTranslateAmountY = clip(this.mSwipeManager.getSwipeContainerViewTranslationY(this.mSwipingItem), -max2, max2);
    }

    public void update(int i, int i2, int i3) {
        if (this.mSwipeDistanceX != i2 || this.mSwipeDistanceY != i3) {
            this.mSwipeDistanceX = i2;
            this.mSwipeDistanceY = i3;
            int i4 = this.mSwipeHorizontal ? this.mSwipeDistanceX + this.mInitialTranslateAmountX : this.mSwipeDistanceY + this.mInitialTranslateAmountY;
            int i5 = this.mSwipeHorizontal ? this.mSwipingItemWidth : this.mSwipingItemHeight;
            float f = this.mSwipeHorizontal ? this.mInvSwipingItemWidth : this.mInvSwipingItemHeight;
            int i6 = this.mSwipeHorizontal ? i4 > 0 ? this.mRightSwipeReactionType : this.mLeftSwipeReactionType : i4 > 0 ? this.mDownSwipeReactionType : this.mUpSwipeReactionType;
            float f2 = 0.0f;
            switch (i6) {
                case 1:
                    f2 = Math.signum((float) i4) * RUBBER_BAND_INTERPOLATOR.getInterpolation(((float) Math.min(Math.abs(i4), i5)) * f);
                    break;
                case 2:
                    f2 = Math.min(Math.max(((float) i4) * f, -1.0f), 1.0f);
                    break;
            }
            this.mSwipeManager.applySlideItem(this.mSwipingItem, i, this.mPrevTranslateAmount, f2, this.mSwipeHorizontal, false, true);
            this.mPrevTranslateAmount = f2;
        }
    }
}
