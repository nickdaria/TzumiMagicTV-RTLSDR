package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;

public abstract class ItemChangeAnimationManager extends BaseItemAnimationManager<ChangeAnimationInfo> {
    private static final String TAG = "ARVItemChangeAnimMgr";

    public ItemChangeAnimationManager(BaseItemAnimator baseItemAnimator) {
        super(baseItemAnimator);
    }

    public abstract boolean addPendingAnimation(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4);

    public void dispatchFinished(ChangeAnimationInfo changeAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchChangeFinished(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchChangeFinished(viewHolder, viewHolder == changeAnimationInfo.oldHolder);
    }

    public void dispatchStarting(ChangeAnimationInfo changeAnimationInfo, ViewHolder viewHolder) {
        if (debugLogEnabled()) {
            Log.d(TAG, "dispatchChangeStarting(" + viewHolder + ")");
        }
        this.mItemAnimator.dispatchChangeStarting(viewHolder, viewHolder == changeAnimationInfo.oldHolder);
    }

    protected boolean endNotStartedAnimation(ChangeAnimationInfo changeAnimationInfo, ViewHolder viewHolder) {
        if (changeAnimationInfo.oldHolder != null && (viewHolder == null || changeAnimationInfo.oldHolder == viewHolder)) {
            onAnimationEndedBeforeStarted(changeAnimationInfo, changeAnimationInfo.oldHolder);
            dispatchFinished(changeAnimationInfo, changeAnimationInfo.oldHolder);
            changeAnimationInfo.clear(changeAnimationInfo.oldHolder);
        }
        if (changeAnimationInfo.newHolder != null && (viewHolder == null || changeAnimationInfo.newHolder == viewHolder)) {
            onAnimationEndedBeforeStarted(changeAnimationInfo, changeAnimationInfo.newHolder);
            dispatchFinished(changeAnimationInfo, changeAnimationInfo.newHolder);
            changeAnimationInfo.clear(changeAnimationInfo.newHolder);
        }
        return changeAnimationInfo.oldHolder == null && changeAnimationInfo.newHolder == null;
    }

    public long getDuration() {
        return this.mItemAnimator.getChangeDuration();
    }

    protected void onCreateAnimation(ChangeAnimationInfo changeAnimationInfo) {
        if (!(changeAnimationInfo.oldHolder == null || changeAnimationInfo.oldHolder.itemView == null)) {
            onCreateChangeAnimationForOldItem(changeAnimationInfo);
        }
        if (changeAnimationInfo.newHolder != null && changeAnimationInfo.newHolder.itemView != null) {
            onCreateChangeAnimationForNewItem(changeAnimationInfo);
        }
    }

    protected abstract void onCreateChangeAnimationForNewItem(ChangeAnimationInfo changeAnimationInfo);

    protected abstract void onCreateChangeAnimationForOldItem(ChangeAnimationInfo changeAnimationInfo);

    public void setDuration(long j) {
        this.mItemAnimator.setChangeDuration(j);
    }
}
