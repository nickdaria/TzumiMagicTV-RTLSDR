package com.h6ah4i.android.widget.advrecyclerview.animator;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.SimpleItemAnimator;

public abstract class BaseItemAnimator extends SimpleItemAnimator {
    private ItemAnimatorListener mListener;

    public interface ItemAnimatorListener {
        void onAddFinished(ViewHolder viewHolder);

        void onChangeFinished(ViewHolder viewHolder);

        void onMoveFinished(ViewHolder viewHolder);

        void onRemoveFinished(ViewHolder viewHolder);
    }

    public boolean debugLogEnabled() {
        return false;
    }

    public boolean dispatchFinishedWhenDone() {
        if (isRunning()) {
            return false;
        }
        dispatchAnimationsFinished();
        return true;
    }

    public final void onAddFinished(ViewHolder viewHolder) {
        onAddFinishedImpl(viewHolder);
        if (this.mListener != null) {
            this.mListener.onAddFinished(viewHolder);
        }
    }

    protected void onAddFinishedImpl(ViewHolder viewHolder) {
    }

    public final void onAddStarting(ViewHolder viewHolder) {
        onAddStartingImpl(viewHolder);
    }

    protected void onAddStartingImpl(ViewHolder viewHolder) {
    }

    public final void onChangeFinished(ViewHolder viewHolder, boolean z) {
        onChangeFinishedImpl(viewHolder, z);
        if (this.mListener != null) {
            this.mListener.onChangeFinished(viewHolder);
        }
    }

    protected void onChangeFinishedImpl(ViewHolder viewHolder, boolean z) {
    }

    public final void onChangeStarting(ViewHolder viewHolder, boolean z) {
        onChangeStartingItem(viewHolder, z);
    }

    protected void onChangeStartingItem(ViewHolder viewHolder, boolean z) {
    }

    public final void onMoveFinished(ViewHolder viewHolder) {
        onMoveFinishedImpl(viewHolder);
        if (this.mListener != null) {
            this.mListener.onMoveFinished(viewHolder);
        }
    }

    protected void onMoveFinishedImpl(ViewHolder viewHolder) {
    }

    public final void onMoveStarting(ViewHolder viewHolder) {
        onMoveStartingImpl(viewHolder);
    }

    protected void onMoveStartingImpl(ViewHolder viewHolder) {
    }

    public final void onRemoveFinished(ViewHolder viewHolder) {
        onRemoveFinishedImpl(viewHolder);
        if (this.mListener != null) {
            this.mListener.onRemoveFinished(viewHolder);
        }
    }

    protected void onRemoveFinishedImpl(ViewHolder viewHolder) {
    }

    public final void onRemoveStarting(ViewHolder viewHolder) {
        onRemoveStartingImpl(viewHolder);
    }

    protected void onRemoveStartingImpl(ViewHolder viewHolder) {
    }

    public void setListener(ItemAnimatorListener itemAnimatorListener) {
        this.mListener = itemAnimatorListener;
    }
}
