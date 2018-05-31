package io.realm;

import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.C0699f;

public abstract class C0498r implements C0497q {
    public static <E extends C0497q> void m477a(E e) {
        if (e instanceof C0661k) {
            C0661k c0661k = (C0661k) e;
            if (c0661k.mo2250j().m1353b() == null) {
                throw new IllegalStateException("Object malformed: missing object in Realm. Make sure to instantiate RealmObjects with Realm.createObject()");
            } else if (c0661k.mo2250j().m1347a() == null) {
                throw new IllegalStateException("Object malformed: missing Realm. Make sure to instantiate RealmObjects with Realm.createObject()");
            } else {
                c0661k.mo2250j().m1347a().m1010e();
                C0669m b = c0661k.mo2250j().m1353b();
                b.mo2267b().m1202e(b.mo2269c());
                c0661k.mo2250j().m1350a(C0699f.INSTANCE);
                return;
            }
        }
        throw new IllegalArgumentException("Object not managed by Realm, so it cannot be removed.");
    }

    public static <E extends C0497q> boolean m478b(E e) {
        if (!(e instanceof C0661k)) {
            return true;
        }
        C0669m b = ((C0661k) e).mo2250j().m1353b();
        return b != null && b.mo2272d();
    }

    public final void m479k() {
        C0498r.m477a(this);
    }
}
