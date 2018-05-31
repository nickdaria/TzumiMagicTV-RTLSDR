package io.realm;

import io.realm.RealmObjectSchema.C0641a;
import io.realm.internal.C0659b;
import io.realm.internal.C0677a;
import io.realm.internal.Table;
import io.realm.internal.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class RealmSchema {
    private static final String f753b = Table.f848a;
    C0677a f754a;
    private final Map<String, Table> f755c;
    private final Map<Class<? extends C0497q>, Table> f756d;
    private final Map<Class<? extends C0497q>, RealmObjectSchema> f757e;
    private final Map<String, RealmObjectSchema> f758f;
    private final C0657b f759g;
    private long f760h;

    RealmSchema() {
        this.f755c = new HashMap();
        this.f756d = new HashMap();
        this.f757e = new HashMap();
        this.f758f = new HashMap();
        this.f759g = null;
        this.f760h = 0;
    }

    RealmSchema(C0657b c0657b) {
        this.f755c = new HashMap();
        this.f756d = new HashMap();
        this.f757e = new HashMap();
        this.f758f = new HashMap();
        this.f759g = c0657b;
        this.f760h = 0;
    }

    RealmSchema(ArrayList<RealmObjectSchema> arrayList) {
        this.f755c = new HashMap();
        this.f756d = new HashMap();
        this.f757e = new HashMap();
        this.f758f = new HashMap();
        long[] jArr = new long[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            jArr[i] = ((RealmObjectSchema) arrayList.get(i)).m963b();
        }
        this.f760h = nativeCreateFromList(jArr);
        this.f759g = null;
    }

    static String m967a(Table table) {
        return table.m1211i().substring(Table.f848a.length());
    }

    private void m968a(String str, String str2) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException(str2);
        }
    }

    private static boolean m969a(Class<? extends C0497q> cls, Class<? extends C0497q> cls2) {
        return cls != cls2;
    }

    static native void nativeClose(long j);

    static native long nativeCreateFromList(long[] jArr);

    static native long[] nativeGetAll(long j);

    public long m970a() {
        return this.f760h;
    }

    public RealmObjectSchema m971a(String str) {
        m968a(str, "Null or empty class names are not allowed");
        if (this.f759g == null) {
            return m978c(str) ? (RealmObjectSchema) this.f758f.get(str) : null;
        } else {
            String str2 = f753b + str;
            if (!this.f759g.f784e.m1148a(str2)) {
                return null;
            }
            Table b = this.f759g.f784e.m1149b(str2);
            return new RealmObjectSchema(this.f759g, b, new C0641a(b));
        }
    }

    C0659b m972a(Class<? extends C0497q> cls) {
        C0659b a = this.f754a.m1264a(cls);
        if (a != null) {
            return a;
        }
        throw new IllegalStateException("No validated schema information found for " + this.f759g.f783d.m1431h().mo2232a(cls));
    }

    public RealmObjectSchema m973b(String str) {
        m968a(str, "Null or empty class names are not allowed");
        if (this.f759g == null) {
            RealmObjectSchema realmObjectSchema = new RealmObjectSchema(str);
            this.f758f.put(str, realmObjectSchema);
            return realmObjectSchema;
        }
        String str2 = f753b + str;
        if (str2.length() > 56) {
            throw new IllegalArgumentException("Class name is to long. Limit is 57 characters: " + str.length());
        } else if (this.f759g.f784e.m1148a(str2)) {
            throw new IllegalArgumentException("Class already exists: " + str);
        } else {
            Table b = this.f759g.f784e.m1149b(str2);
            return new RealmObjectSchema(this.f759g, b, new C0641a(b));
        }
    }

    Table m974b(Class<? extends C0497q> cls) {
        Table table = (Table) this.f756d.get(cls);
        if (table == null) {
            Class a = Util.m1240a(cls);
            if (m969a(a, (Class) cls)) {
                table = (Table) this.f756d.get(a);
            }
            if (table == null) {
                table = this.f759g.f784e.m1149b(this.f759g.f783d.m1431h().mo2232a(a));
                this.f756d.put(a, table);
            }
            if (m969a(a, (Class) cls)) {
                this.f756d.put(cls, table);
            }
        }
        return table;
    }

    public void m975b() {
        if (this.f760h != 0) {
            for (RealmObjectSchema a : m977c()) {
                a.m961a();
            }
            nativeClose(this.f760h);
        }
    }

    RealmObjectSchema m976c(Class<? extends C0497q> cls) {
        RealmObjectSchema realmObjectSchema = (RealmObjectSchema) this.f757e.get(cls);
        if (realmObjectSchema == null) {
            Class a = Util.m1240a(cls);
            if (m969a(a, (Class) cls)) {
                realmObjectSchema = (RealmObjectSchema) this.f757e.get(a);
            }
            if (realmObjectSchema == null) {
                realmObjectSchema = new RealmObjectSchema(this.f759g, m974b((Class) cls), this.f754a.m1264a(a).m1022c());
                this.f757e.put(a, realmObjectSchema);
            }
            if (m969a(a, (Class) cls)) {
                this.f757e.put(cls, realmObjectSchema);
            }
        }
        return realmObjectSchema;
    }

    public Set<RealmObjectSchema> m977c() {
        int i = 0;
        Set<RealmObjectSchema> linkedHashSet;
        if (this.f759g == null) {
            long[] nativeGetAll = nativeGetAll(this.f760h);
            linkedHashSet = new LinkedHashSet(nativeGetAll.length);
            while (i < nativeGetAll.length) {
                linkedHashSet.add(new RealmObjectSchema(nativeGetAll[i]));
                i++;
            }
            return linkedHashSet;
        }
        int h = (int) this.f759g.f784e.m1156h();
        linkedHashSet = new LinkedHashSet(h);
        while (i < h) {
            String a = this.f759g.f784e.m1144a(i);
            if (Table.m1172c(a)) {
                Table b = this.f759g.f784e.m1149b(a);
                linkedHashSet.add(new RealmObjectSchema(this.f759g, b, new C0641a(b)));
            }
            i++;
        }
        return linkedHashSet;
    }

    public boolean m978c(String str) {
        return this.f759g == null ? this.f758f.containsKey(str) : this.f759g.f784e.m1148a(Table.f848a + str);
    }

    Table m979d(String str) {
        String str2 = Table.f848a + str;
        Table table = (Table) this.f755c.get(str2);
        if (table != null) {
            return table;
        }
        if (this.f759g.f784e.m1148a(str2)) {
            table = this.f759g.f784e.m1149b(str2);
            this.f755c.put(str2, table);
            return table;
        }
        throw new IllegalArgumentException("The class " + str2 + " doesn't exist in this Realm.");
    }

    RealmObjectSchema m980e(String str) {
        String str2 = Table.f848a + str;
        RealmObjectSchema realmObjectSchema = (RealmObjectSchema) this.f758f.get(str2);
        if (realmObjectSchema != null) {
            return realmObjectSchema;
        }
        if (this.f759g.f784e.m1148a(str2)) {
            Table b = this.f759g.f784e.m1149b(str2);
            realmObjectSchema = new RealmObjectSchema(this.f759g, b, new C0641a(b));
            this.f758f.put(str2, realmObjectSchema);
            return realmObjectSchema;
        }
        throw new IllegalArgumentException("The class " + str2 + " doesn't exist in this Realm.");
    }
}
