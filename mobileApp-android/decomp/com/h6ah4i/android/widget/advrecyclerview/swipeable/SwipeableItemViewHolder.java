package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.view.View;

public interface SwipeableItemViewHolder {
    int getAfterSwipeReaction();

    float getMaxDownSwipeAmount();

    float getMaxLeftSwipeAmount();

    float getMaxRightSwipeAmount();

    float getMaxUpSwipeAmount();

    float getSwipeItemHorizontalSlideAmount();

    float getSwipeItemVerticalSlideAmount();

    int getSwipeResult();

    int getSwipeStateFlags();

    View getSwipeableContainerView();

    void onSlideAmountUpdated(float f, float f2, boolean z);

    void setAfterSwipeReaction(int i);

    void setMaxDownSwipeAmount(float f);

    void setMaxLeftSwipeAmount(float f);

    void setMaxRightSwipeAmount(float f);

    void setMaxUpSwipeAmount(float f);

    void setSwipeItemHorizontalSlideAmount(float f);

    void setSwipeItemVerticalSlideAmount(float f);

    void setSwipeResult(int i);

    void setSwipeStateFlags(int i);
}
