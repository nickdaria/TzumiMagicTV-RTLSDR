package io.realm.internal;

import io.realm.C0497q;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class C0677a implements Cloneable {
    private long f867a;
    private Map<Class<? extends C0497q>, C0659b> f868b;

    public C0677a(long j, Map<Class<? extends C0497q>, C0659b> map) {
        this.f867a = j;
        this.f868b = map;
    }

    private Map<Class<? extends C0497q>, C0659b> m1262c() {
        Map<Class<? extends C0497q>, C0659b> hashMap = new HashMap();
        for (Entry entry : this.f868b.entrySet()) {
            hashMap.put(entry.getKey(), ((C0659b) entry.getValue()).mo2247b());
        }
        return hashMap;
    }

    public long m1263a() {
        return this.f867a;
    }

    public C0659b m1264a(Class<? extends C0497q> cls) {
        return (C0659b) this.f868b.get(cls);
    }

    public void m1265a(C0677a c0677a, C0640l c0640l) {
        for (Entry entry : this.f868b.entrySet()) {
            C0659b a = c0677a.m1264a((Class) entry.getKey());
            if (a == null) {
                throw new IllegalStateException("Failed to copy ColumnIndices cache: " + Table.m1173d(c0640l.mo2232a((Class) entry.getKey())));
            }
            ((C0659b) entry.getValue()).mo2246a(a);
        }
        this.f867a = c0677a.f867a;
    }

    public C0677a m1266b() {
        try {
            C0677a c0677a = (C0677a) super.clone();
            c0677a.f868b = m1262c();
            return c0677a;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return m1266b();
    }
}
