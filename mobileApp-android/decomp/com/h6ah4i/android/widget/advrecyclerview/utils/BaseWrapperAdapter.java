package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class BaseWrapperAdapter<VH extends ViewHolder> extends Adapter<VH> {
    protected static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();
    private static final boolean LOCAL_LOGD = false;
    private static final String TAG = "ARVBaseWrapperAdapter";
    private BridgeObserver mBridgeObserver = new BridgeObserver(this);
    private Adapter<VH> mWrappedAdapter;

    private static final class BridgeObserver<VH extends ViewHolder> extends AdapterDataObserver {
        private final WeakReference<BaseWrapperAdapter<VH>> mRefHolder;

        public BridgeObserver(BaseWrapperAdapter<VH> baseWrapperAdapter) {
            this.mRefHolder = new WeakReference(baseWrapperAdapter);
        }

        public void onChanged() {
            BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) this.mRefHolder.get();
            if (baseWrapperAdapter != null) {
                baseWrapperAdapter.onWrappedAdapterChanged();
            }
        }

        public void onItemRangeChanged(int i, int i2) {
            BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) this.mRefHolder.get();
            if (baseWrapperAdapter != null) {
                baseWrapperAdapter.onWrappedAdapterItemRangeChanged(i, i2);
            }
        }

        public void onItemRangeChanged(int i, int i2, Object obj) {
            BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) this.mRefHolder.get();
            if (baseWrapperAdapter != null) {
                baseWrapperAdapter.onWrappedAdapterItemRangeChanged(i, i2, obj);
            }
        }

        public void onItemRangeInserted(int i, int i2) {
            BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) this.mRefHolder.get();
            if (baseWrapperAdapter != null) {
                baseWrapperAdapter.onWrappedAdapterItemRangeInserted(i, i2);
            }
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
            BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) this.mRefHolder.get();
            if (baseWrapperAdapter != null) {
                baseWrapperAdapter.onWrappedAdapterRangeMoved(i, i2, i3);
            }
        }

        public void onItemRangeRemoved(int i, int i2) {
            BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) this.mRefHolder.get();
            if (baseWrapperAdapter != null) {
                baseWrapperAdapter.onWrappedAdapterItemRangeRemoved(i, i2);
            }
        }
    }

    public BaseWrapperAdapter(Adapter<VH> adapter) {
        this.mWrappedAdapter = adapter;
        this.mWrappedAdapter.registerAdapterDataObserver(this.mBridgeObserver);
        super.setHasStableIds(this.mWrappedAdapter.hasStableIds());
    }

    public int getItemCount() {
        return isWrappedAdapterAlive() ? this.mWrappedAdapter.getItemCount() : 0;
    }

    public long getItemId(int i) {
        return this.mWrappedAdapter.getItemId(i);
    }

    public int getItemViewType(int i) {
        return this.mWrappedAdapter.getItemViewType(i);
    }

    public Adapter<VH> getWrappedAdapter() {
        return this.mWrappedAdapter;
    }

    public boolean isWrappedAdapterAlive() {
        return this.mWrappedAdapter != null;
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    public void onBindViewHolder(VH vh, int i) {
        onBindViewHolder(vh, i, FULLUPDATE_PAYLOADS);
    }

    public void onBindViewHolder(VH vh, int i, List<Object> list) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onBindViewHolder(vh, i, list);
        }
    }

    public VH onCreateViewHolder(ViewGroup viewGroup, int i) {
        return this.mWrappedAdapter.onCreateViewHolder(viewGroup, i);
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    protected void onHandleWrappedAdapterChanged() {
        notifyDataSetChanged();
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int i, int i2) {
        notifyItemRangeChanged(i, i2);
    }

    protected void onHandleWrappedAdapterItemRangeChanged(int i, int i2, Object obj) {
        notifyItemRangeChanged(i, i2, obj);
    }

    protected void onHandleWrappedAdapterItemRangeInserted(int i, int i2) {
        notifyItemRangeInserted(i, i2);
    }

    protected void onHandleWrappedAdapterItemRangeRemoved(int i, int i2) {
        notifyItemRangeRemoved(i, i2);
    }

    protected void onHandleWrappedAdapterRangeMoved(int i, int i2, int i3) {
        if (i3 != 1) {
            throw new IllegalStateException("itemCount should be always 1  (actual: " + i3 + ")");
        }
        notifyItemMoved(i, i2);
    }

    protected void onRelease() {
    }

    public void onViewAttachedToWindow(VH vh) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onViewAttachedToWindow(vh);
        }
    }

    public void onViewDetachedFromWindow(VH vh) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onViewDetachedFromWindow(vh);
        }
    }

    public void onViewRecycled(VH vh) {
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.onViewRecycled(vh);
        }
    }

    final void onWrappedAdapterChanged() {
        onHandleWrappedAdapterChanged();
    }

    final void onWrappedAdapterItemRangeChanged(int i, int i2) {
        onHandleWrappedAdapterItemRangeChanged(i, i2);
    }

    final void onWrappedAdapterItemRangeChanged(int i, int i2, Object obj) {
        onHandleWrappedAdapterItemRangeChanged(i, i2, obj);
    }

    final void onWrappedAdapterItemRangeInserted(int i, int i2) {
        onHandleWrappedAdapterItemRangeInserted(i, i2);
    }

    final void onWrappedAdapterItemRangeRemoved(int i, int i2) {
        onHandleWrappedAdapterItemRangeRemoved(i, i2);
    }

    final void onWrappedAdapterRangeMoved(int i, int i2, int i3) {
        onHandleWrappedAdapterRangeMoved(i, i2, i3);
    }

    public void release() {
        onRelease();
        if (!(this.mWrappedAdapter == null || this.mBridgeObserver == null)) {
            this.mWrappedAdapter.unregisterAdapterDataObserver(this.mBridgeObserver);
        }
        this.mWrappedAdapter = null;
        this.mBridgeObserver = null;
    }

    public void setHasStableIds(boolean z) {
        super.setHasStableIds(z);
        if (isWrappedAdapterAlive()) {
            this.mWrappedAdapter.setHasStableIds(z);
        }
    }
}
