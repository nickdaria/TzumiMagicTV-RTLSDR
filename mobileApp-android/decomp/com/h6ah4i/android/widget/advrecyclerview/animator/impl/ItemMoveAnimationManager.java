package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemMoveAnimationManager extends BaseItemAnimationManager<MoveAnimationInfo> {
    public static final String TAG = "ARVItemMoveAnimMgr";

    public ItemMoveAnimationManager(BaseItemAnimator baseItemAnimator) {
        super(baseItemAnimator);
    }

    public abstract boolean addPendingAnimation(ViewHolder viewHolder, int i, int i2, int i3, int i4);

    public void dispatchFinished(MoveAnimationInfo moveAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchMoveFinished(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchMoveFinished(viewHolder);
    }

    public void dispatchStarting(MoveAnimationInfo moveAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchMoveStarting(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchMoveStarting(viewHolder);
    }

    protected boolean endNotStartedAnimation(MoveAnimationInfo moveAnimationInfo, ViewHolder viewHolder) {
        if (moveAnimationInfo.holder == null || (viewHolder != null && moveAnimationInfo.holder != viewHolder)) {
            return false;
        }
        onAnimationEndedBeforeStarted(moveAnimationInfo, moveAnimationInfo.holder);
        dispatchFinished(moveAnimationInfo, moveAnimationInfo.holder);
        moveAnimationInfo.clear(moveAnimationInfo.holder);
        return true;
    }

    public long getDuration() {
        return this.mItemAnimator.getMoveDuration();
    }

    public void setDuration(long j) {
        this.mItemAnimator.setMoveDuration(j);
    }
}
