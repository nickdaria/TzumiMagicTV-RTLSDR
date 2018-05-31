package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemAddAnimationManager extends BaseItemAnimationManager<AddAnimationInfo> {
    private static final String TAG = "ARVItemAddAnimMgr";

    public ItemAddAnimationManager(BaseItemAnimator baseItemAnimator) {
        super(baseItemAnimator);
    }

    public abstract boolean addPendingAnimation(ViewHolder viewHolder);

    public void dispatchFinished(AddAnimationInfo addAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchAddFinished(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchAddFinished(viewHolder);
    }

    public void dispatchStarting(AddAnimationInfo addAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchAddStarting(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchAddStarting(viewHolder);
    }

    protected boolean endNotStartedAnimation(AddAnimationInfo addAnimationInfo, ViewHolder viewHolder) {
        if (addAnimationInfo.holder == null || (viewHolder != null && addAnimationInfo.holder != viewHolder)) {
            return false;
        }
        onAnimationEndedBeforeStarted(addAnimationInfo, addAnimationInfo.holder);
        dispatchFinished(addAnimationInfo, addAnimationInfo.holder);
        addAnimationInfo.clear(addAnimationInfo.holder);
        return true;
    }

    public long getDuration() {
        return this.mItemAnimator.getAddDuration();
    }

    public void setDuration(long j) {
        this.mItemAnimator.setAddDuration(j);
    }
}
