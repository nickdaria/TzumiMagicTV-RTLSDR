package io.realm.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

public final class C0700h extends PhantomReference<C0668g> {
    final long f917a;
    final int f918b;
    final Integer f919c;

    C0700h(int i, C0668g c0668g, ReferenceQueue<? super C0668g> referenceQueue, Integer num) {
        super(c0668g, referenceQueue);
        this.f918b = i;
        this.f917a = c0668g.f819c;
        this.f919c = num;
    }

    void m1317a() {
        switch (this.f918b) {
            case 0:
                LinkView.nativeClose(this.f917a);
                return;
            case 1:
                UncheckedRow.nativeClose(this.f917a);
                return;
            default:
                throw new IllegalStateException("Unknown native reference type " + this.f918b + ".");
        }
    }
}
