package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.support.v7.widget.RecyclerView.Adapter;

public class WrapperAdapterUtils {
    private WrapperAdapterUtils() {
    }

    public static <T> T findWrappedAdapter(Adapter adapter, Class<T> cls) {
        return cls.isInstance(adapter) ? cls.cast(adapter) : adapter instanceof BaseWrapperAdapter ? findWrappedAdapter(((BaseWrapperAdapter) adapter).getWrappedAdapter(), cls) : null;
    }

    public static Adapter releaseAll(Adapter adapter) {
        return releaseCyclically(adapter);
    }

    private static Adapter releaseCyclically(Adapter adapter) {
        if (!(adapter instanceof BaseWrapperAdapter)) {
            return adapter;
        }
        BaseWrapperAdapter baseWrapperAdapter = (BaseWrapperAdapter) adapter;
        Adapter wrappedAdapter = baseWrapperAdapter.getWrappedAdapter();
        baseWrapperAdapter.release();
        return releaseCyclically(wrappedAdapter);
    }
}
