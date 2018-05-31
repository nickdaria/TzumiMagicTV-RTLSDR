package com.h6ah4i.android.widget.advrecyclerview.swipeable.action;

public abstract class SwipeResultAction {
    private final int mResultAction;

    protected SwipeResultAction(int i) {
        this.mResultAction = i;
    }

    public int getResultActionType() {
        return this.mResultAction;
    }

    protected void onCleanUp() {
    }

    protected void onPerformAction() {
    }

    protected void onSlideAnimationEnd() {
    }

    public final void performAction() {
        onPerformAction();
    }

    public final void slideAnimationEnd() {
        onSlideAnimationEnd();
        onCleanUp();
    }
}
