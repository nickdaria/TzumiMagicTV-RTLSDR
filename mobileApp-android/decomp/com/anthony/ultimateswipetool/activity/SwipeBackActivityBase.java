package com.anthony.ultimateswipetool.activity;

public interface SwipeBackActivityBase {
    SwipeBackLayout getSwipeBackLayout();

    void scrollToFinishActivity();

    void setScrollDirection(int i);

    void setSwipeBackEnable(boolean z);
}
