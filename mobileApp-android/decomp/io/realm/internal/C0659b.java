package io.realm.internal;

import io.realm.exceptions.RealmMigrationNeededException;
import java.util.Map;

public abstract class C0659b implements Cloneable {
    private Map<String, Long> f791a;

    protected final long m1018a(String str, Table table, String str2, String str3) {
        long a = table.m1187a(str3);
        if (a != -1) {
            return a;
        }
        throw new RealmMigrationNeededException(str, "Field '" + str3 + "' not found for type " + str2);
    }

    public abstract void mo2246a(C0659b c0659b);

    protected final void m1020a(Map<String, Long> map) {
        this.f791a = map;
    }

    public C0659b mo2247b() {
        try {
            return (C0659b) super.clone();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Long> m1022c() {
        return this.f791a;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return mo2247b();
    }
}
