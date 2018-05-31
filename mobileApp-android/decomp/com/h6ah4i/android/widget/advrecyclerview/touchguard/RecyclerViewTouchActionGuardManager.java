package com.h6ah4i.android.widget.advrecyclerview.touchguard;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class RecyclerViewTouchActionGuardManager {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "ARVTouchActionGuardMgr";
    private boolean mEnabled;
    private boolean mGuarding;
    private int mInitialTouchY;
    private boolean mInterceptScrollingWhileAnimationRunning;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C05771();
    private int mLastTouchY;
    private RecyclerView mRecyclerView;
    private int mTouchSlop;

    class C05771 implements OnItemTouchListener {
        C05771() {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return RecyclerViewTouchActionGuardManager.this.onInterceptTouchEvent(recyclerView, motionEvent);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            RecyclerViewTouchActionGuardManager.this.onTouchEvent(recyclerView, motionEvent);
        }
    }

    private void handleActionDown(MotionEvent motionEvent) {
        int y = (int) (motionEvent.getY() + 0.5f);
        this.mLastTouchY = y;
        this.mInitialTouchY = y;
        this.mGuarding = false;
    }

    private boolean handleActionMove(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (!this.mGuarding) {
            this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
            int i = this.mLastTouchY - this.mInitialTouchY;
            if (this.mInterceptScrollingWhileAnimationRunning && Math.abs(i) > this.mTouchSlop && isAnimationRunning(recyclerView)) {
                this.mGuarding = true;
            }
        }
        return this.mGuarding;
    }

    private void handleActionUpOrCancel() {
        this.mGuarding = false;
        this.mInitialTouchY = 0;
        this.mLastTouchY = 0;
    }

    private static boolean isAnimationRunning(RecyclerView recyclerView) {
        ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        return itemAnimator != null && itemAnimator.isRunning();
    }

    public void attachRecyclerView(@NonNull RecyclerView recyclerView) {
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else {
            this.mRecyclerView = recyclerView;
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            this.mTouchSlop = ViewConfiguration.get(recyclerView.getContext()).getScaledTouchSlop();
        }
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public boolean isInterceptScrollingWhileAnimationRunning() {
        return this.mInterceptScrollingWhileAnimationRunning;
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (!this.mEnabled) {
            return false;
        }
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                handleActionDown(motionEvent);
                return false;
            case 1:
            case 3:
                handleActionUpOrCancel();
                return false;
            case 2:
                return handleActionMove(recyclerView, motionEvent);
            default:
                return false;
        }
    }

    void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.mEnabled) {
            switch (MotionEventCompat.getActionMasked(motionEvent)) {
                case 1:
                case 3:
                    handleActionUpOrCancel();
                    return;
                default:
                    return;
            }
        }
    }

    public void release() {
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        this.mRecyclerView = null;
    }

    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            if (!this.mEnabled) {
                handleActionUpOrCancel();
            }
        }
    }

    public void setInterceptVerticalScrollingWhileAnimationRunning(boolean z) {
        this.mInterceptScrollingWhileAnimationRunning = z;
    }
}
