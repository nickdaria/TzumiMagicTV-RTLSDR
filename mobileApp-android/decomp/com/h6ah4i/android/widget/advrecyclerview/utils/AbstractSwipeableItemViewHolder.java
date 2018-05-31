package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemViewHolder;

public abstract class AbstractSwipeableItemViewHolder extends ViewHolder implements SwipeableItemViewHolder {
    private int mAfterSwipeReaction = 0;
    private float mHorizontalSwipeAmount;
    private float mMaxDownSwipeAmount = SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_BOTTOM;
    private float mMaxLeftSwipeAmount = SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_LEFT;
    private float mMaxRightSwipeAmount = SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_RIGHT;
    private float mMaxUpSwipeAmount = SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_TOP;
    private int mSwipeResult = 0;
    private int mSwipeStateFlags;
    private float mVerticalSwipeAmount;

    public AbstractSwipeableItemViewHolder(View view) {
        super(view);
    }

    public int getAfterSwipeReaction() {
        return this.mAfterSwipeReaction;
    }

    public float getMaxDownSwipeAmount() {
        return this.mMaxDownSwipeAmount;
    }

    public float getMaxLeftSwipeAmount() {
        return this.mMaxLeftSwipeAmount;
    }

    public float getMaxRightSwipeAmount() {
        return this.mMaxRightSwipeAmount;
    }

    public float getMaxUpSwipeAmount() {
        return this.mMaxUpSwipeAmount;
    }

    public float getSwipeItemHorizontalSlideAmount() {
        return this.mHorizontalSwipeAmount;
    }

    public float getSwipeItemVerticalSlideAmount() {
        return this.mVerticalSwipeAmount;
    }

    public int getSwipeResult() {
        return this.mSwipeResult;
    }

    public int getSwipeStateFlags() {
        return this.mSwipeStateFlags;
    }

    public abstract View getSwipeableContainerView();

    public void onSlideAmountUpdated(float f, float f2, boolean z) {
    }

    public void setAfterSwipeReaction(int i) {
        this.mAfterSwipeReaction = i;
    }

    public void setMaxDownSwipeAmount(float f) {
        this.mMaxDownSwipeAmount = f;
    }

    public void setMaxLeftSwipeAmount(float f) {
        this.mMaxLeftSwipeAmount = f;
    }

    public void setMaxRightSwipeAmount(float f) {
        this.mMaxRightSwipeAmount = f;
    }

    public void setMaxUpSwipeAmount(float f) {
        this.mMaxUpSwipeAmount = f;
    }

    public void setSwipeItemHorizontalSlideAmount(float f) {
        this.mHorizontalSwipeAmount = f;
    }

    public void setSwipeItemVerticalSlideAmount(float f) {
        this.mVerticalSwipeAmount = f;
    }

    public void setSwipeResult(int i) {
        this.mSwipeResult = i;
    }

    public void setSwipeStateFlags(int i) {
        this.mSwipeStateFlags = i;
    }
}
