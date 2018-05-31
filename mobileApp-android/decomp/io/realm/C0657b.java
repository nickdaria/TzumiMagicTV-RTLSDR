package io.realm;

import android.content.Context;
import io.realm.C0712l.C0651a;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.C0659b;
import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.C0699f;
import io.realm.internal.C0701i;
import io.realm.internal.SharedRealm;
import io.realm.internal.SharedRealm.C0649c;
import io.realm.internal.Table;
import io.realm.internal.Util;
import io.realm.internal.async.C0693d;
import io.realm.log.RealmLog;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class C0657b implements Closeable {
    static volatile Context f779a;
    static final C0693d f780b = C0693d.m1285a();
    public static final C0656c f781h = new C0656c();
    final long f782c = Thread.currentThread().getId();
    protected C0715n f783d;
    protected SharedRealm f784e;
    RealmSchema f785f;
    C0667i f786g;

    class C06501 implements C0649c {
        final /* synthetic */ C0657b f767a;

        C06501(C0657b c0657b) {
            this.f767a = c0657b;
        }

        public void mo2244a(long j) {
            C0712l.m1401a((C0708k) this.f767a);
        }
    }

    protected interface C0654a {
        void mo2310a();
    }

    public static final class C0655b {
        private C0657b f774a;
        private C0669m f775b;
        private C0659b f776c;
        private boolean f777d;
        private List<String> f778e;

        public C0657b m991a() {
            return this.f774a;
        }

        public void m992a(C0657b c0657b, C0669m c0669m, C0659b c0659b, boolean z, List<String> list) {
            this.f774a = c0657b;
            this.f775b = c0669m;
            this.f776c = c0659b;
            this.f777d = z;
            this.f778e = list;
        }

        public C0669m m993b() {
            return this.f775b;
        }

        public C0659b m994c() {
            return this.f776c;
        }

        public boolean m995d() {
            return this.f777d;
        }

        public List<String> m996e() {
            return this.f778e;
        }

        public void m997f() {
            this.f774a = null;
            this.f775b = null;
            this.f776c = null;
            this.f777d = false;
            this.f778e = null;
        }
    }

    static final class C0656c extends ThreadLocal<C0655b> {
        C0656c() {
        }

        protected C0655b m998a() {
            return new C0655b();
        }

        protected /* synthetic */ Object initialValue() {
            return m998a();
        }
    }

    protected C0657b(C0715n c0715n) {
        this.f783d = c0715n;
        this.f786g = new C0667i(this);
        this.f784e = SharedRealm.m1141a(c0715n, new C0648a(this.f786g), !(this instanceof C0708k) ? null : new C06501(this));
        this.f785f = new RealmSchema(this);
        if (this.f786g.m1079c()) {
            mo2251a(true);
        }
    }

    protected static void m999a(final C0715n c0715n, final C0541p c0541p, final C0654a c0654a, RealmMigrationNeededException realmMigrationNeededException) throws FileNotFoundException {
        if (c0715n == null) {
            throw new IllegalArgumentException("RealmConfiguration must be provided");
        } else if (c0541p == null && c0715n.m1428e() == null) {
            throw new RealmMigrationNeededException(c0715n.m1435l(), "RealmMigration must be provided", realmMigrationNeededException);
        } else {
            final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            C0712l.m1403a(c0715n, new C0651a() {
                public void mo2245a(int i) {
                    if (i != 0) {
                        throw new IllegalStateException("Cannot migrate a Realm file that is already open: " + c0715n.m1435l());
                    } else if (new File(c0715n.m1435l()).exists()) {
                        C0541p e = c0541p == null ? c0715n.m1428e() : c0541p;
                        C0663f c0663f = null;
                        try {
                            c0663f = C0663f.m1044b(c0715n);
                            c0663f.mo2253b();
                            e.mo2142a(c0663f, c0663f.mo2259h(), c0715n.m1427d());
                            c0663f.m1003a(c0715n.m1427d());
                            c0663f.mo2254c();
                            if (c0663f != null) {
                                c0663f.close();
                                c0654a.mo2310a();
                            }
                        } catch (RuntimeException e2) {
                            if (c0663f != null) {
                                c0663f.mo2256d();
                            }
                            throw e2;
                        } catch (Throwable th) {
                            if (c0663f != null) {
                                c0663f.close();
                                c0654a.mo2310a();
                            }
                        }
                    } else {
                        atomicBoolean.set(true);
                    }
                }
            });
            if (atomicBoolean.get()) {
                throw new FileNotFoundException("Cannot migrate a Realm file which doesn't exist: " + c0715n.m1435l());
            }
        }
    }

    static boolean m1000a(final C0715n c0715n) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        C0712l.m1403a(c0715n, new C0651a() {
            public void mo2245a(int i) {
                if (i != 0) {
                    throw new IllegalStateException("It's not allowed to delete the file associated with an open Realm. Remember to close() all the instances of the Realm before deleting its file: " + c0715n.m1435l());
                }
                atomicBoolean.set(Util.m1242a(c0715n.m1435l(), c0715n.m1424a(), c0715n.m1425b()));
            }
        });
        return atomicBoolean.get();
    }

    <E extends C0497q> E m1001a(Class<E> cls, long j, boolean z, List<String> list) {
        C0669m g = this.f785f.m974b((Class) cls).m1206g(j);
        E a = this.f783d.m1431h().mo2231a(cls, this, g, this.f785f.m972a((Class) cls), z, list);
        ((C0661k) a).mo2250j().m1356e();
        return a;
    }

    <E extends C0497q> E m1002a(Class<E> cls, String str, long j) {
        E c0665g;
        boolean z = str != null;
        Table d = z ? this.f785f.m979d(str) : this.f785f.m974b((Class) cls);
        if (z) {
            c0665g = new C0665g(this, j != -1 ? d.m1210i(j) : C0699f.INSTANCE);
        } else {
            c0665g = this.f783d.m1431h().mo2231a(cls, this, j != -1 ? d.m1206g(j) : C0699f.INSTANCE, this.f785f.m972a((Class) cls), false, Collections.emptyList());
        }
        C0661k c0661k = (C0661k) c0665g;
        if (j != -1) {
            c0661k.mo2250j().m1356e();
        }
        return c0665g;
    }

    void m1003a(long j) {
        this.f784e.m1145a(j);
    }

    public void mo2251a(boolean z) {
        m1010e();
        this.f786g.m1078b();
        this.f786g.m1076a(z);
    }

    public boolean mo2252a() {
        m1010e();
        return this.f784e.m1153e();
    }

    public void mo2253b() {
        m1010e();
        this.f784e.m1150b();
    }

    void m1007b(boolean z) {
        m1010e();
        this.f784e.m1151c();
        C0701i.m1319a(this.f783d.m1436n()).m1322a(this.f783d, this.f784e.m1160l());
        if (z) {
            this.f784e.f840a.notifyCommitByLocalThread();
        }
    }

    public void mo2254c() {
        m1007b(true);
    }

    public void close() {
        if (this.f782c != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm instance can only be closed on the thread it was created.");
        }
        C0712l.m1400a(this);
    }

    public void mo2256d() {
        m1010e();
        this.f784e.m1152d();
    }

    protected void m1010e() {
        if (this.f784e == null || this.f784e.m1161m()) {
            throw new IllegalStateException("This Realm instance has already been closed, making it unusable.");
        } else if (this.f782c != Thread.currentThread().getId()) {
            throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
        }
    }

    public String mo2257f() {
        return this.f783d.m1435l();
    }

    protected void finalize() throws Throwable {
        if (!(this.f784e == null || this.f784e.m1161m())) {
            RealmLog.m1411c("Remember to call close() on all Realm instances. Realm %s is being finalized without being closed, this can lead to running out of native memory.", this.f783d.m1435l());
        }
        super.finalize();
    }

    public C0715n mo2258g() {
        return this.f783d;
    }

    public long mo2259h() {
        return this.f784e.m1154f();
    }

    void m1014i() {
        if (this.f784e != null) {
            this.f784e.close();
            this.f784e = null;
        }
        if (this.f785f != null) {
            this.f785f.m975b();
        }
    }

    public boolean mo2260j() {
        if (this.f782c == Thread.currentThread().getId()) {
            return this.f784e == null || this.f784e.m1161m();
        } else {
            throw new IllegalStateException("Realm access from incorrect thread. Realm objects can only be accessed on the thread they were created.");
        }
    }

    public RealmSchema mo2261k() {
        return this.f785f;
    }
}
