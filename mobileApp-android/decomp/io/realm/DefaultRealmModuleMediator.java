package io.realm;

import com.eardatek.special.player.p007b.C0500a;
import io.realm.C0657b.C0655b;
import io.realm.annotations.RealmModule;
import io.realm.internal.C0640l;
import io.realm.internal.C0659b;
import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RealmModule
class DefaultRealmModuleMediator extends C0640l {
    private static final Set<Class<? extends C0497q>> f742a;

    static {
        Set hashSet = new HashSet();
        hashSet.add(C0500a.class);
        f742a = Collections.unmodifiableSet(hashSet);
    }

    DefaultRealmModuleMediator() {
    }

    public RealmObjectSchema mo2227a(Class<? extends C0497q> cls, RealmSchema realmSchema) {
        C0640l.m926b(cls);
        if (cls.equals(C0500a.class)) {
            return C0662d.m1029a(realmSchema);
        }
        throw C0640l.m927c(cls);
    }

    public Table mo2228a(Class<? extends C0497q> cls, SharedRealm sharedRealm) {
        C0640l.m926b(cls);
        if (cls.equals(C0500a.class)) {
            return C0662d.m1031a(sharedRealm);
        }
        throw C0640l.m927c(cls);
    }

    public C0659b mo2229a(Class<? extends C0497q> cls, SharedRealm sharedRealm, boolean z) {
        C0640l.m926b(cls);
        if (cls.equals(C0500a.class)) {
            return C0662d.m1030a(sharedRealm, z);
        }
        throw C0640l.m927c(cls);
    }

    public <E extends C0497q> E mo2230a(C0708k c0708k, E e, boolean z, Map<C0497q, C0661k> map) {
        Class superclass = e instanceof C0661k ? e.getClass().getSuperclass() : e.getClass();
        if (superclass.equals(C0500a.class)) {
            return (C0497q) superclass.cast(C0662d.m1028a(c0708k, (C0500a) e, z, (Map) map));
        }
        throw C0640l.m927c(superclass);
    }

    public <E extends C0497q> E mo2231a(Class<E> cls, Object obj, C0669m c0669m, C0659b c0659b, boolean z, List<String> list) {
        C0655b c0655b = (C0655b) C0657b.f781h.get();
        try {
            c0655b.m992a((C0657b) obj, c0669m, c0659b, z, list);
            C0640l.m926b(cls);
            if (cls.equals(C0500a.class)) {
                C0497q c0497q = (C0497q) cls.cast(new C0662d());
                return c0497q;
            }
            throw C0640l.m927c(cls);
        } finally {
            c0655b.m997f();
        }
    }

    public String mo2232a(Class<? extends C0497q> cls) {
        C0640l.m926b(cls);
        if (cls.equals(C0500a.class)) {
            return C0662d.m1033i();
        }
        throw C0640l.m927c(cls);
    }

    public Set<Class<? extends C0497q>> mo2233a() {
        return f742a;
    }

    public boolean mo2234b() {
        return true;
    }
}
