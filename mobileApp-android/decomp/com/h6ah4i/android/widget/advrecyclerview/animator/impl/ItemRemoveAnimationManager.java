package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemRemoveAnimationManager extends BaseItemAnimationManager<RemoveAnimationInfo> {
    private static final String TAG = "ARVItemRemoveAnimMgr";

    public ItemRemoveAnimationManager(BaseItemAnimator baseItemAnimator) {
        super(baseItemAnimator);
    }

    public abstract boolean addPendingAnimation(ViewHolder viewHolder);

    public void dispatchFinished(RemoveAnimationInfo removeAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchRemoveFinished(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchRemoveFinished(viewHolder);
    }

    public void dispatchStarting(RemoveAnimationInfo removeAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchRemoveStarting(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchRemoveStarting(viewHolder);
    }

    protected boolean endNotStartedAnimation(RemoveAnimationInfo removeAnimationInfo, ViewHolder viewHolder) {
        if (removeAnimationInfo.holder == null || (viewHolder != null && removeAnimationInfo.holder != viewHolder)) {
            return false;
        }
        onAnimationEndedBeforeStarted(removeAnimationInfo, removeAnimationInfo.holder);
        dispatchFinished(removeAnimationInfo, removeAnimationInfo.holder);
        removeAnimationInfo.clear(removeAnimationInfo.holder);
        return true;
    }

    public long getDuration() {
        return this.mItemAnimator.getRemoveDuration();
    }

    public void setDuration(long j) {
        this.mItemAnimator.setRemoveDuration(j);
    }
}
