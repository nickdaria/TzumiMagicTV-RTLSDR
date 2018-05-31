package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemAddAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemChangeAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemMoveAnimationManager;
import com.h6ah4i.android.widget.advrecyclerview.animator.impl.ItemRemoveAnimationManager;

public abstract class GeneralItemAnimator extends BaseItemAnimator {
    private static final String TAG = "ARVGeneralItemAnimator";
    private ItemAddAnimationManager mAddAnimationsManager;
    private ItemChangeAnimationManager mChangeAnimationsManager;
    private boolean mDebug;
    private ItemMoveAnimationManager mMoveAnimationsManager;
    private ItemRemoveAnimationManager mRemoveAnimationManager;

    protected GeneralItemAnimator() {
        setup();
    }

    private void setup() {
        onSetup();
        if (this.mRemoveAnimationManager == null || this.mAddAnimationsManager == null || this.mChangeAnimationsManager == null || this.mMoveAnimationsManager == null) {
            throw new IllegalStateException("setup incomplete");
        }
    }

    public boolean animateAdd(ViewHolder viewHolder) {
        if (this.mDebug) {
            Log.d(TAG, "animateAdd(id = " + viewHolder.getItemId() + ", position = " + viewHolder.getLayoutPosition() + ")");
        }
        return this.mAddAnimationsManager.addPendingAnimation(viewHolder);
    }

    public boolean animateChange(ViewHolder viewHolder, ViewHolder viewHolder2, int i, int i2, int i3, int i4) {
        if (viewHolder == viewHolder2) {
            return this.mMoveAnimationsManager.addPendingAnimation(viewHolder, i, i2, i3, i4);
        }
        if (this.mDebug) {
            String l = viewHolder != null ? Long.toString(viewHolder.getItemId()) : "-";
            String l2 = viewHolder != null ? Long.toString((long) viewHolder.getLayoutPosition()) : "-";
            Log.d(TAG, "animateChange(old.id = " + l + ", old.position = " + l2 + ", new.id = " + (viewHolder2 != null ? Long.toString(viewHolder2.getItemId()) : "-") + ", new.position = " + (viewHolder2 != null ? Long.toString((long) viewHolder2.getLayoutPosition()) : "-") + ", fromX = " + i + ", fromY = " + i2 + ", toX = " + i3 + ", toY = " + i4 + ")");
        }
        return this.mChangeAnimationsManager.addPendingAnimation(viewHolder, viewHolder2, i, i2, i3, i4);
    }

    public boolean animateMove(ViewHolder viewHolder, int i, int i2, int i3, int i4) {
        if (this.mDebug) {
            Log.d(TAG, "animateMove(id = " + viewHolder.getItemId() + ", position = " + viewHolder.getLayoutPosition() + ", fromX = " + i + ", fromY = " + i2 + ", toX = " + i3 + ", toY = " + i4 + ")");
        }
        return this.mMoveAnimationsManager.addPendingAnimation(viewHolder, i, i2, i3, i4);
    }

    public boolean animateRemove(ViewHolder viewHolder) {
        if (this.mDebug) {
            Log.d(TAG, "animateRemove(id = " + viewHolder.getItemId() + ", position = " + viewHolder.getLayoutPosition() + ")");
        }
        return this.mRemoveAnimationManager.addPendingAnimation(viewHolder);
    }

    protected void cancelAnimations(ViewHolder viewHolder) {
        ViewCompat.animate(viewHolder.itemView).cancel();
    }

    public boolean debugLogEnabled() {
        return this.mDebug;
    }

    public boolean dispatchFinishedWhenDone() {
        if (this.mDebug && !isRunning()) {
            Log.d(TAG, "dispatchFinishedWhenDone()");
        }
        return super.dispatchFinishedWhenDone();
    }

    public void endAnimation(ViewHolder viewHolder) {
        cancelAnimations(viewHolder);
        this.mMoveAnimationsManager.endPendingAnimations(viewHolder);
        this.mChangeAnimationsManager.endPendingAnimations(viewHolder);
        this.mRemoveAnimationManager.endPendingAnimations(viewHolder);
        this.mAddAnimationsManager.endPendingAnimations(viewHolder);
        this.mMoveAnimationsManager.endDeferredReadyAnimations(viewHolder);
        this.mChangeAnimationsManager.endDeferredReadyAnimations(viewHolder);
        this.mRemoveAnimationManager.endDeferredReadyAnimations(viewHolder);
        this.mAddAnimationsManager.endDeferredReadyAnimations(viewHolder);
        if (this.mRemoveAnimationManager.removeFromActive(viewHolder) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [remove]");
        } else if (this.mAddAnimationsManager.removeFromActive(viewHolder) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [add]");
        } else if (this.mChangeAnimationsManager.removeFromActive(viewHolder) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [change]");
        } else if (this.mMoveAnimationsManager.removeFromActive(viewHolder) && this.mDebug) {
            throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [move]");
        } else {
            dispatchFinishedWhenDone();
        }
    }

    public void endAnimations() {
        this.mMoveAnimationsManager.endAllPendingAnimations();
        this.mRemoveAnimationManager.endAllPendingAnimations();
        this.mAddAnimationsManager.endAllPendingAnimations();
        this.mChangeAnimationsManager.endAllPendingAnimations();
        if (isRunning()) {
            this.mMoveAnimationsManager.endAllDeferredReadyAnimations();
            this.mAddAnimationsManager.endAllDeferredReadyAnimations();
            this.mChangeAnimationsManager.endAllDeferredReadyAnimations();
            this.mRemoveAnimationManager.cancelAllStartedAnimations();
            this.mMoveAnimationsManager.cancelAllStartedAnimations();
            this.mAddAnimationsManager.cancelAllStartedAnimations();
            this.mChangeAnimationsManager.cancelAllStartedAnimations();
            dispatchAnimationsFinished();
        }
    }

    protected ItemAddAnimationManager getItemAddAnimationsManager() {
        return this.mAddAnimationsManager;
    }

    protected ItemChangeAnimationManager getItemChangeAnimationsManager() {
        return this.mChangeAnimationsManager;
    }

    protected ItemMoveAnimationManager getItemMoveAnimationsManager() {
        return this.mMoveAnimationsManager;
    }

    protected ItemRemoveAnimationManager getRemoveAnimationManager() {
        return this.mRemoveAnimationManager;
    }

    protected boolean hasPendingAnimations() {
        return this.mRemoveAnimationManager.hasPending() || this.mMoveAnimationsManager.hasPending() || this.mChangeAnimationsManager.hasPending() || this.mAddAnimationsManager.hasPending();
    }

    public boolean isDebug() {
        return this.mDebug;
    }

    public boolean isRunning() {
        return this.mRemoveAnimationManager.isRunning() || this.mAddAnimationsManager.isRunning() || this.mChangeAnimationsManager.isRunning() || this.mMoveAnimationsManager.isRunning();
    }

    protected void onSchedulePendingAnimations() {
        schedulePendingAnimationsByDefaultRule();
    }

    protected abstract void onSetup();

    public void runPendingAnimations() {
        if (hasPendingAnimations()) {
            onSchedulePendingAnimations();
        }
    }

    protected void schedulePendingAnimationsByDefaultRule() {
        boolean z = false;
        boolean hasPending = this.mRemoveAnimationManager.hasPending();
        boolean hasPending2 = this.mMoveAnimationsManager.hasPending();
        boolean hasPending3 = this.mChangeAnimationsManager.hasPending();
        boolean hasPending4 = this.mAddAnimationsManager.hasPending();
        long removeDuration = hasPending ? getRemoveDuration() : 0;
        long moveDuration = hasPending2 ? getMoveDuration() : 0;
        long changeDuration = hasPending3 ? getChangeDuration() : 0;
        if (hasPending) {
            this.mRemoveAnimationManager.runPendingAnimations(false, 0);
        }
        if (hasPending2) {
            this.mMoveAnimationsManager.runPendingAnimations(hasPending, removeDuration);
        }
        if (hasPending3) {
            this.mChangeAnimationsManager.runPendingAnimations(hasPending, removeDuration);
        }
        if (hasPending4) {
            if (hasPending || hasPending2 || hasPending3) {
                z = true;
            }
            changeDuration = Math.max(moveDuration, changeDuration) + removeDuration;
            if (!z) {
                changeDuration = 0;
            }
            this.mAddAnimationsManager.runPendingAnimations(z, changeDuration);
        }
    }

    public void setDebug(boolean z) {
        this.mDebug = z;
    }

    protected void setItemAddAnimationsManager(ItemAddAnimationManager itemAddAnimationManager) {
        this.mAddAnimationsManager = itemAddAnimationManager;
    }

    protected void setItemChangeAnimationsManager(ItemChangeAnimationManager itemChangeAnimationManager) {
        this.mChangeAnimationsManager = itemChangeAnimationManager;
    }

    protected void setItemMoveAnimationsManager(ItemMoveAnimationManager itemMoveAnimationManager) {
        this.mMoveAnimationsManager = itemMoveAnimationManager;
    }

    protected void setItemRemoveAnimationManager(ItemRemoveAnimationManager itemRemoveAnimationManager) {
        this.mRemoveAnimationManager = itemRemoveAnimationManager;
    }
}
