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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class C0675a extends C0640l {
    private final Map<Class<? extends C0497q>, C0640l> f864a;

    public C0675a(C0640l... c0640lArr) {
        Map hashMap = new HashMap();
        if (c0640lArr != null) {
            for (C0640l c0640l : c0640lArr) {
                for (Class put : c0640l.mo2233a()) {
                    hashMap.put(put, c0640l);
                }
            }
        }
        this.f864a = Collections.unmodifiableMap(hashMap);
    }

    private C0640l m1244d(Class<? extends C0497q> cls) {
        C0640l c0640l = (C0640l) this.f864a.get(cls);
        if (c0640l != null) {
            return c0640l;
        }
        throw new IllegalArgumentException(cls.getSimpleName() + " is not part of the schema for this Realm");
    }

    public RealmObjectSchema mo2227a(Class<? extends C0497q> cls, RealmSchema realmSchema) {
        return m1244d(cls).mo2227a((Class) cls, realmSchema);
    }

    public Table mo2228a(Class<? extends C0497q> cls, SharedRealm sharedRealm) {
        return m1244d(cls).mo2228a((Class) cls, sharedRealm);
    }

    public C0659b mo2229a(Class<? extends C0497q> cls, SharedRealm sharedRealm, boolean z) {
        return m1244d(cls).mo2229a(cls, sharedRealm, z);
    }

    public <E extends C0497q> E mo2230a(C0708k c0708k, E e, boolean z, Map<C0497q, C0661k> map) {
        return m1244d(Util.m1240a(e.getClass())).mo2230a(c0708k, e, z, map);
    }

    public <E extends C0497q> E mo2231a(Class<E> cls, Object obj, C0669m c0669m, C0659b c0659b, boolean z, List<String> list) {
        return m1244d(cls).mo2231a(cls, obj, c0669m, c0659b, z, list);
    }

    public String mo2232a(Class<? extends C0497q> cls) {
        return m1244d(cls).mo2232a(cls);
    }

    public Set<Class<? extends C0497q>> mo2233a() {
        return this.f864a.keySet();
    }

    public boolean mo2234b() {
        for (Entry value : this.f864a.entrySet()) {
            if (!((C0640l) value.getValue()).mo2234b()) {
                return false;
            }
        }
        return true;
    }
}
