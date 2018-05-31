package com.eardatek.special.player.p005i;

import android.os.Handler;
import java.lang.ref.WeakReference;

public abstract class C0466t<T> extends Handler {
    private WeakReference<T> f209a;

    public C0466t(T t) {
        this.f209a = new WeakReference(t);
    }

    public T m217a() {
        return this.f209a.get();
    }
}
