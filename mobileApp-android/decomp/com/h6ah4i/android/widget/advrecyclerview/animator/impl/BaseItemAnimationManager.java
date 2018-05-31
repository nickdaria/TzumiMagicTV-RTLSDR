package com.h6ah4i.android.widget.advrecyclerview.animator.impl;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.animator.BaseItemAnimator;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseItemAnimationManager<T extends ItemAnimationInfo> {
    protected final List<ViewHolder> mActive = new ArrayList();
    protected final List<List<T>> mDeferredReadySets = new ArrayList();
    protected final BaseItemAnimator mItemAnimator;
    protected final List<T> mPending = new ArrayList();

    protected static class BaseAnimatorListener implements ViewPropertyAnimatorListener {
        private ItemAnimationInfo mAnimationInfo;
        private ViewPropertyAnimatorCompat mAnimator;
        private ViewHolder mHolder;
        private BaseItemAnimationManager mManager;

        public BaseAnimatorListener(BaseItemAnimationManager baseItemAnimationManager, ItemAnimationInfo itemAnimationInfo, ViewHolder viewHolder, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
            this.mManager = baseItemAnimationManager;
            this.mAnimationInfo = itemAnimationInfo;
            this.mHolder = viewHolder;
            this.mAnimator = viewPropertyAnimatorCompat;
        }

        public void onAnimationCancel(View view) {
            this.mManager.onAnimationCancel(this.mAnimationInfo, this.mHolder);
        }

        public void onAnimationEnd(View view) {
            BaseItemAnimationManager baseItemAnimationManager = this.mManager;
            ItemAnimationInfo itemAnimationInfo = this.mAnimationInfo;
            ViewHolder viewHolder = this.mHolder;
            this.mAnimator.setListener(null);
            this.mManager = null;
            this.mAnimationInfo = null;
            this.mHolder = null;
            this.mAnimator = null;
            baseItemAnimationManager.onAnimationEndedSuccessfully(itemAnimationInfo, viewHolder);
            baseItemAnimationManager.dispatchFinished(itemAnimationInfo, viewHolder);
            itemAnimationInfo.clear(viewHolder);
            baseItemAnimationManager.mActive.remove(viewHolder);
            baseItemAnimationManager.dispatchFinishedWhenDone();
        }

        public void onAnimationStart(View view) {
            this.mManager.dispatchStarting(this.mAnimationInfo, this.mHolder);
        }
    }

    public BaseItemAnimationManager(BaseItemAnimator baseItemAnimator) {
        this.mItemAnimator = baseItemAnimator;
    }

    private void addActiveAnimationTarget(ViewHolder viewHolder) {
        if (viewHolder == null) {
            throw new IllegalStateException("item is null");
        }
        this.mActive.add(viewHolder);
    }

    public void cancelAllStartedAnimations() {
        List list = this.mActive;
        for (int size = list.size() - 1; size >= 0; size--) {
            ViewCompat.animate(((ViewHolder) list.get(size)).itemView).cancel();
        }
    }

    void createAnimation(T t) {
        onCreateAnimation(t);
    }

    protected final boolean debugLogEnabled() {
        return this.mItemAnimator.debugLogEnabled();
    }

    public abstract void dispatchFinished(T t, ViewHolder viewHolder);

    protected void dispatchFinishedWhenDone() {
        this.mItemAnimator.dispatchFinishedWhenDone();
    }

    public abstract void dispatchStarting(T t, ViewHolder viewHolder);

    public void endAllDeferredReadyAnimations() {
        endDeferredReadyAnimations(null);
    }

    public void endAllPendingAnimations() {
        endPendingAnimations(null);
    }

    protected void endAnimation(ViewHolder viewHolder) {
        this.mItemAnimator.endAnimation(viewHolder);
    }

    public void endDeferredReadyAnimations(ViewHolder viewHolder) {
        for (int size = this.mDeferredReadySets.size() - 1; size >= 0; size--) {
            List list = (List) this.mDeferredReadySets.get(size);
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                if (endNotStartedAnimation((ItemAnimationInfo) list.get(size2), viewHolder) && viewHolder != null) {
                    list.remove(size2);
                }
            }
            if (viewHolder == null) {
                list.clear();
            }
            if (list.isEmpty()) {
                this.mDeferredReadySets.remove(list);
            }
        }
    }

    protected abstract boolean endNotStartedAnimation(T t, ViewHolder viewHolder);

    public void endPendingAnimations(ViewHolder viewHolder) {
        List list = this.mPending;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (endNotStartedAnimation((ItemAnimationInfo) list.get(size), viewHolder) && viewHolder != null) {
                list.remove(size);
            }
        }
        if (viewHolder == null) {
            list.clear();
        }
    }

    protected void enqueuePendingAnimationInfo(T t) {
        if (t == null) {
            throw new IllegalStateException("info is null");
        }
        this.mPending.add(t);
    }

    public abstract long getDuration();

    public boolean hasPending() {
        return !this.mPending.isEmpty();
    }

    public boolean isRunning() {
        return (this.mPending.isEmpty() && this.mActive.isEmpty() && this.mDeferredReadySets.isEmpty()) ? false : true;
    }

    protected abstract void onAnimationCancel(T t, ViewHolder viewHolder);

    protected abstract void onAnimationEndedBeforeStarted(T t, ViewHolder viewHolder);

    protected abstract void onAnimationEndedSuccessfully(T t, ViewHolder viewHolder);

    protected abstract void onCreateAnimation(T t);

    public boolean removeFromActive(ViewHolder viewHolder) {
        return this.mActive.remove(viewHolder);
    }

    public void runPendingAnimations(boolean z, long j) {
        final List<ItemAnimationInfo> arrayList = new ArrayList();
        arrayList.addAll(this.mPending);
        this.mPending.clear();
        if (z) {
            this.mDeferredReadySets.add(arrayList);
            ViewCompat.postOnAnimationDelayed(((ItemAnimationInfo) arrayList.get(0)).getAvailableViewHolder().itemView, new Runnable() {
                public void run() {
                    for (ItemAnimationInfo createAnimation : arrayList) {
                        BaseItemAnimationManager.this.createAnimation(createAnimation);
                    }
                    arrayList.clear();
                    BaseItemAnimationManager.this.mDeferredReadySets.remove(arrayList);
                }
            }, j);
            return;
        }
        for (ItemAnimationInfo createAnimation : arrayList) {
            createAnimation(createAnimation);
        }
        arrayList.clear();
    }

    public abstract void setDuration(long j);

    protected void startActiveItemAnimation(T t, ViewHolder viewHolder, ViewPropertyAnimatorCompat viewPropertyAnimatorCompat) {
        viewPropertyAnimatorCompat.setListener(new BaseAnimatorListener(this, t, viewHolder, viewPropertyAnimatorCompat));
        addActiveAnimationTarget(viewHolder);
        viewPropertyAnimatorCompat.start();
    }
}
