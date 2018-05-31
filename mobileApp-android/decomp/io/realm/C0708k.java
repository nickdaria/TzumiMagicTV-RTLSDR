package io.realm;

import android.content.Context;
import io.realm.C0657b.C0654a;
import io.realm.C0715n.C0714a;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.exceptions.RealmFileException.Kind;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.C0640l;
import io.realm.internal.C0661k;
import io.realm.internal.C0677a;
import io.realm.internal.C0701i;
import io.realm.internal.C0702j;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class C0708k extends C0657b {
    private static C0715n f937i;

    public interface C0705a {
        void mo2309a(C0708k c0708k);
    }

    static class C07061 implements C0705a {
        C07061() {
        }

        public void mo2309a(C0708k c0708k) {
            c0708k.m1003a(c0708k.d.m1427d());
        }
    }

    static class C07072 implements C0654a {
        C07072() {
        }

        public void mo2310a() {
        }
    }

    C0708k(C0715n c0715n) {
        super(c0715n);
    }

    static C0708k m1362a(C0715n c0715n, C0677a[] c0677aArr) {
        try {
            return C0708k.m1367b(c0715n, c0677aArr);
        } catch (RealmMigrationNeededException e) {
            if (c0715n.m1429f()) {
                C0708k.m1371c(c0715n);
            } else {
                try {
                    C0708k.m1366a(c0715n, e);
                } catch (Throwable e2) {
                    throw new RealmFileException(Kind.NOT_FOUND, e2);
                }
            }
            return C0708k.m1367b(c0715n, c0677aArr);
        }
    }

    private <E extends C0497q> E m1363a(E e, boolean z, Map<C0497q, C0661k> map) {
        m1010e();
        return this.d.m1431h().mo2230a(this, e, z, map);
    }

    public static synchronized void m1364a(Context context) {
        synchronized (C0708k.class) {
            if (C0657b.f779a == null) {
                if (context == null) {
                    throw new IllegalArgumentException("Non-null context required.");
                }
                C0702j.m1325a(context);
                f937i = new C0714a(context).m1418a();
                C0701i.m1318a().m1320a(context);
                C0657b.f779a = context.getApplicationContext();
                SharedRealm.m1142a(new File(context.getFilesDir(), ".realm.temp"));
            }
        }
    }

    private static void m1365a(C0708k c0708k) {
        long h = c0708k.mo2259h();
        Object obj = null;
        boolean n = c0708k.d.m1436n();
        if (!n) {
            try {
                c0708k.mo2253b();
                if (h == -1) {
                    obj = 1;
                    c0708k.m1003a(c0708k.d.m1427d());
                }
            } catch (Throwable th) {
                if (!n) {
                    if (obj != null) {
                        c0708k.m1007b(false);
                    } else {
                        c0708k.mo2256d();
                    }
                }
            }
        }
        C0640l h2 = c0708k.d.m1431h();
        Set<Class> a = h2.mo2233a();
        Map hashMap = new HashMap(a.size());
        ArrayList arrayList = new ArrayList();
        RealmSchema realmSchema = new RealmSchema();
        for (Class cls : a) {
            if (h == -1 && !n) {
                h2.mo2228a(cls, c0708k.e);
            }
            if (n) {
                arrayList.add(h2.mo2227a(cls, realmSchema));
            } else {
                hashMap.put(cls, h2.mo2229a(cls, c0708k.e, false));
            }
        }
        if (n) {
            c0708k.e.m1146a(new RealmSchema(arrayList), h);
            for (Class cls2 : a) {
                hashMap.put(cls2, h2.mo2229a(cls2, c0708k.e, false));
            }
        }
        c0708k.f.f754a = new C0677a(h == -1 ? c0708k.d.m1427d() : h, hashMap);
        if (h == -1) {
            C0705a i = c0708k.mo2258g().m1432i();
            if (i != null) {
                if (n) {
                    c0708k.m1378a(i);
                    c0708k.m1378a(new C07061());
                } else {
                    i.mo2309a(c0708k);
                }
            }
        }
        if (!n) {
            if (obj != null) {
                c0708k.m1007b(false);
            } else {
                c0708k.mo2256d();
            }
        }
    }

    private static void m1366a(C0715n c0715n, RealmMigrationNeededException realmMigrationNeededException) throws FileNotFoundException {
        C0657b.m999a(c0715n, null, new C07072(), realmMigrationNeededException);
    }

    static C0708k m1367b(C0715n c0715n, C0677a[] c0677aArr) {
        C0708k c0708k = new C0708k(c0715n);
        long h = c0708k.mo2259h();
        long d = c0715n.m1427d();
        C0677a a = C0712l.m1399a(c0677aArr, d);
        if (h != -1 && h < d && a == null) {
            c0708k.m1014i();
            throw new RealmMigrationNeededException(c0715n.m1435l(), String.format("Realm on disk need to migrate from v%s to v%s", new Object[]{Long.valueOf(h), Long.valueOf(d)}));
        } else if (h == -1 || d >= h || a != null) {
            if (a == null) {
                try {
                    C0708k.m1365a(c0708k);
                } catch (RuntimeException e) {
                    c0708k.m1014i();
                    throw e;
                }
            }
            c0708k.f.f754a = a.m1266b();
            return c0708k;
        } else {
            c0708k.m1014i();
            throw new IllegalArgumentException(String.format("Realm on disk is newer than the one specified: v%s vs. v%s", new Object[]{Long.valueOf(h), Long.valueOf(d)}));
        }
    }

    public static void m1368b(C0715n c0715n) {
        if (c0715n == null) {
            throw new IllegalArgumentException("A non-null RealmConfiguration must be provided");
        }
        f937i = c0715n;
    }

    private <E extends C0497q> void m1369c(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Null objects cannot be copied into Realm.");
        }
    }

    private void m1370c(Class<? extends C0497q> cls) {
        if (!this.f.m974b((Class) cls).m1203e()) {
            throw new IllegalArgumentException("A RealmObject with no @PrimaryKey cannot be updated: " + cls.toString());
        }
    }

    public static boolean m1371c(C0715n c0715n) {
        return C0657b.m1000a(c0715n);
    }

    public static C0708k m1372l() {
        if (f937i != null) {
            return (C0708k) C0712l.m1398a(f937i, C0708k.class);
        }
        throw new IllegalStateException("Call `Realm.init(Context)` before calling this method.");
    }

    public static Object m1373m() {
        String str = "io.realm.DefaultRealmModule";
        try {
            Constructor constructor = Class.forName(str).getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return constructor.newInstance(new Object[0]);
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Throwable e2) {
            throw new RealmException("Could not create an instance of " + str, e2);
        } catch (Throwable e22) {
            throw new RealmException("Could not create an instance of " + str, e22);
        } catch (Throwable e222) {
            throw new RealmException("Could not create an instance of " + str, e222);
        }
    }

    C0677a m1374a(C0677a[] c0677aArr) {
        C0677a c0677a = null;
        long f = this.e.m1154f();
        if (f != this.f.f754a.m1263a()) {
            C0640l h = mo2258g().m1431h();
            C0677a a = C0712l.m1399a(c0677aArr, f);
            if (a == null) {
                Set<Class> a2 = h.mo2233a();
                Map hashMap = new HashMap(a2.size());
                try {
                    for (Class cls : a2) {
                        hashMap.put(cls, h.mo2229a(cls, this.e, true));
                    }
                    a = new C0677a(f, hashMap);
                    c0677a = a;
                } catch (RealmMigrationNeededException e) {
                    throw e;
                }
            }
            this.f.f754a.m1265a(a, h);
        }
        return c0677a;
    }

    public <E extends C0497q> E m1375a(E e) {
        m1369c((C0497q) e);
        return m1363a(e, false, new HashMap());
    }

    <E extends C0497q> E m1376a(Class<E> cls, Object obj, boolean z, List<String> list) {
        return m1001a((Class) cls, this.f.m974b((Class) cls).m1185a(obj), z, (List) list);
    }

    public <E extends C0497q> C0720s<E> m1377a(Class<E> cls) {
        m1010e();
        return C0720s.m1457a(this, (Class) cls);
    }

    public void m1378a(C0705a c0705a) {
        if (c0705a == null) {
            throw new IllegalArgumentException("Transaction should not be null");
        }
        mo2253b();
        try {
            c0705a.mo2309a(this);
            mo2254c();
        } catch (Throwable th) {
            if (mo2252a()) {
                mo2256d();
            } else {
                RealmLog.m1411c("Could not cancel transaction, not currently in a transaction.", new Object[0]);
            }
        }
    }

    public /* bridge */ /* synthetic */ boolean mo2252a() {
        return super.mo2252a();
    }

    Table m1381b(Class<? extends C0497q> cls) {
        return this.f.m974b((Class) cls);
    }

    public <E extends C0497q> E m1382b(E e) {
        m1369c((C0497q) e);
        m1370c(e.getClass());
        return m1363a(e, true, new HashMap());
    }

    public /* bridge */ /* synthetic */ void mo2253b() {
        super.mo2253b();
    }

    public /* bridge */ /* synthetic */ void mo2254c() {
        super.mo2254c();
    }

    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    public /* bridge */ /* synthetic */ void mo2256d() {
        super.mo2256d();
    }

    public /* bridge */ /* synthetic */ String mo2257f() {
        return super.mo2257f();
    }

    public /* bridge */ /* synthetic */ C0715n mo2258g() {
        return super.mo2258g();
    }

    public /* bridge */ /* synthetic */ long mo2259h() {
        return super.mo2259h();
    }

    public /* bridge */ /* synthetic */ boolean mo2260j() {
        return super.mo2260j();
    }

    public /* bridge */ /* synthetic */ RealmSchema mo2261k() {
        return super.mo2261k();
    }
}
