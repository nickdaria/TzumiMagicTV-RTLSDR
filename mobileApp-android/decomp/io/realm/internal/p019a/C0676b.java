package io.realm.internal.p019a;

import io.realm.C0497q;
import io.realm.C0708k;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.internal.C0640l;
import io.realm.internal.C0659b;
import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class C0676b extends C0640l {
    private final C0640l f865a;
    private final Set<Class<? extends C0497q>> f866b;

    public C0676b(C0640l c0640l, Collection<Class<? extends C0497q>> collection) {
        this.f865a = c0640l;
        Set hashSet = new HashSet();
        if (c0640l != null) {
            Set a = c0640l.mo2233a();
            for (Class cls : collection) {
                if (a.contains(cls)) {
                    hashSet.add(cls);
                }
            }
        }
        this.f866b = Collections.unmodifiableSet(hashSet);
    }

    private void m1253d(Class<? extends C0497q> cls) {
        if (!this.f866b.contains(cls)) {
            throw new IllegalArgumentException(cls.getSimpleName() + " is not part of the schema for this Realm");
        }
    }

    public RealmObjectSchema mo2227a(Class<? extends C0497q> cls, RealmSchema realmSchema) {
        m1253d(cls);
        return this.f865a.mo2227a((Class) cls, realmSchema);
    }

    public Table mo2228a(Class<? extends C0497q> cls, SharedRealm sharedRealm) {
        m1253d(cls);
        return this.f865a.mo2228a((Class) cls, sharedRealm);
    }

    public C0659b mo2229a(Class<? extends C0497q> cls, SharedRealm sharedRealm, boolean z) {
        m1253d(cls);
        return this.f865a.mo2229a(cls, sharedRealm, z);
    }

    public <E extends C0497q> E mo2230a(C0708k c0708k, E e, boolean z, Map<C0497q, C0661k> map) {
        m1253d(Util.m1240a(e.getClass()));
        return this.f865a.mo2230a(c0708k, e, z, map);
    }

    public <E extends C0497q> E mo2231a(Class<E> cls, Object obj, C0669m c0669m, C0659b c0659b, boolean z, List<String> list) {
        m1253d(cls);
        return this.f865a.mo2231a(cls, obj, c0669m, c0659b, z, list);
    }

    public String mo2232a(Class<? extends C0497q> cls) {
        m1253d(cls);
        return this.f865a.mo2232a(cls);
    }

    public Set<Class<? extends C0497q>> mo2233a() {
        return this.f866b;
    }

    public boolean mo2234b() {
        return this.f865a == null ? true : this.f865a.mo2234b();
    }
}
