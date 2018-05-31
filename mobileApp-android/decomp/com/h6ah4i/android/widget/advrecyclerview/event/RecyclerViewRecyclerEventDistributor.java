package com.h6ah4i.android.widget.advrecyclerview.event;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.RecyclerListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.lang.ref.WeakReference;

public class RecyclerViewRecyclerEventDistributor extends BaseRecyclerViewEventDistributor<RecyclerListener> {
    private InternalRecyclerListener mInternalRecyclerListener = new InternalRecyclerListener(this);

    private static class InternalRecyclerListener implements RecyclerListener {
        private final WeakReference<RecyclerViewRecyclerEventDistributor> mRefDistributor;

        public InternalRecyclerListener(RecyclerViewRecyclerEventDistributor recyclerViewRecyclerEventDistributor) {
            this.mRefDistributor = new WeakReference(recyclerViewRecyclerEventDistributor);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            RecyclerViewRecyclerEventDistributor recyclerViewRecyclerEventDistributor = (RecyclerViewRecyclerEventDistributor) this.mRefDistributor.get();
            if (recyclerViewRecyclerEventDistributor != null) {
                recyclerViewRecyclerEventDistributor.handleOnViewRecycled(viewHolder);
            }
        }

        public void release() {
            this.mRefDistributor.clear();
        }
    }

    void handleOnViewRecycled(ViewHolder viewHolder) {
        if (this.mListeners != null) {
            for (RecyclerListener onViewRecycled : this.mListeners) {
                onViewRecycled.onViewRecycled(viewHolder);
            }
        }
    }

    protected void onRecyclerViewAttached(RecyclerView recyclerView) {
        super.onRecyclerViewAttached(recyclerView);
        recyclerView.setRecyclerListener(this.mInternalRecyclerListener);
    }

    protected void onRelease() {
        super.onRelease();
        if (this.mInternalRecyclerListener != null) {
            this.mInternalRecyclerListener.release();
            this.mInternalRecyclerListener = null;
        }
    }
}
