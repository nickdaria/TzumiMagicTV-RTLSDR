package io.realm.internal;

import io.realm.C0497q;
import io.realm.C0708k;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;
import io.realm.exceptions.RealmException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class C0640l {
    protected static void m926b(Class<? extends C0497q> cls) {
        if (cls == null) {
            throw new NullPointerException("A class extending RealmObject must be provided");
        }
    }

    protected static RealmException m927c(Class<? extends C0497q> cls) {
        return new RealmException(cls + " is not part of the schema for this Realm.");
    }

    public abstract RealmObjectSchema mo2227a(Class<? extends C0497q> cls, RealmSchema realmSchema);

    public abstract Table mo2228a(Class<? extends C0497q> cls, SharedRealm sharedRealm);

    public abstract C0659b mo2229a(Class<? extends C0497q> cls, SharedRealm sharedRealm, boolean z);

    public abstract <E extends C0497q> E mo2230a(C0708k c0708k, E e, boolean z, Map<C0497q, C0661k> map);

    public abstract <E extends C0497q> E mo2231a(Class<E> cls, Object obj, C0669m c0669m, C0659b c0659b, boolean z, List<String> list);

    public abstract String mo2232a(Class<? extends C0497q> cls);

    public abstract Set<Class<? extends C0497q>> mo2233a();

    public boolean mo2234b() {
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0640l)) {
            return false;
        }
        return mo2233a().equals(((C0640l) obj).mo2233a());
    }

    public int hashCode() {
        return mo2233a().hashCode();
    }
}
