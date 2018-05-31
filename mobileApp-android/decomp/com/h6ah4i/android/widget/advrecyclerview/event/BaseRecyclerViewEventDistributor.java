package com.h6ah4i.android.widget.advrecyclerview.event;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewEventDistributor<T> {
    protected List<T> mListeners;
    protected boolean mPerformingClearMethod;
    protected RecyclerView mRecyclerView;
    protected boolean mReleased;

    public boolean add(T t) {
        return add(t, -1);
    }

    public boolean add(@NonNull T t, int i) {
        String str = "add()";
        verifyIsNotReleased("add()");
        verifyIsNotPerformingClearMethod("add()");
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        if (!this.mListeners.contains(t)) {
            if (i < 0) {
                this.mListeners.add(t);
            } else {
                this.mListeners.add(i, t);
            }
            if (t instanceof RecyclerViewEventDistributorListener) {
                ((RecyclerViewEventDistributorListener) t).onAddedToEventDistributor(this);
            }
        }
        return true;
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        String str = "attachRecyclerView()";
        if (recyclerView == null) {
            throw new IllegalArgumentException("RecyclerView cannot be null");
        }
        verifyIsNotReleased("attachRecyclerView()");
        verifyIsNotPerformingClearMethod("attachRecyclerView()");
        onRecyclerViewAttached(recyclerView);
    }

    public void clear() {
        clear(false);
    }

    protected void clear(boolean z) {
        String str = "clear()";
        if (!z) {
            verifyIsNotReleased("clear()");
        }
        verifyIsNotPerformingClearMethod("clear()");
        if (this.mListeners != null) {
            try {
                this.mPerformingClearMethod = true;
                for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                    Object remove = this.mListeners.remove(size);
                    if (remove instanceof RecyclerViewEventDistributorListener) {
                        ((RecyclerViewEventDistributorListener) remove).onRemovedFromEventDistributor(this);
                    }
                }
            } finally {
                this.mPerformingClearMethod = false;
            }
        }
    }

    public boolean contains(T t) {
        return this.mListeners != null ? this.mListeners.contains(t) : false;
    }

    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    public boolean isReleased() {
        return this.mReleased;
    }

    protected void onRecyclerViewAttached(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    protected void onRelease() {
        this.mRecyclerView = null;
        this.mListeners = null;
        this.mPerformingClearMethod = false;
    }

    public void release() {
        if (!this.mReleased) {
            this.mReleased = true;
            clear(true);
            onRelease();
        }
    }

    public boolean remove(@NonNull T t) {
        String str = "remove()";
        verifyIsNotPerformingClearMethod("remove()");
        verifyIsNotReleased("remove()");
        if (this.mListeners == null) {
            return false;
        }
        boolean remove = this.mListeners.remove(t);
        if (!remove || !(t instanceof RecyclerViewEventDistributorListener)) {
            return remove;
        }
        ((RecyclerViewEventDistributorListener) t).onRemovedFromEventDistributor(this);
        return remove;
    }

    public int size() {
        return this.mListeners != null ? this.mListeners.size() : 0;
    }

    protected void verifyIsNotPerformingClearMethod(String str) {
        if (this.mPerformingClearMethod) {
            throw new IllegalStateException(str + " can not be called while performing the clear() method");
        }
    }

    protected void verifyIsNotReleased(String str) {
        if (this.mReleased) {
            throw new IllegalStateException(str + " can not be called after release() method called");
        }
    }
}
