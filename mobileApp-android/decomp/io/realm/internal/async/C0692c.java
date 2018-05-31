package io.realm.internal.async;

import io.realm.C0497q;
import io.realm.C0715n;
import io.realm.C0723t;
import io.realm.internal.C0661k;
import io.realm.internal.RealmNotifier;
import io.realm.internal.SharedRealm;
import io.realm.internal.SharedRealm.C0672d;
import io.realm.internal.TableQuery;
import io.realm.log.RealmLog;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

public class C0692c implements Runnable {
    private final int f896a;
    private C0715n f897b;
    private List<C0684c> f898c;
    private C0684c f899d;
    private WeakReference<RealmNotifier> f900e;
    private C0690c f901f;

    private static class C0681a {
        long[] f876a;
        long[][] f877b;
        long[][] f878c;
        boolean[][] f879d;

        private C0681a() {
        }
    }

    public static class C0689b {

        public interface C0682a {
            C0692c mo2307a();
        }

        public interface C0683b {
            C0682a mo2304a(RealmNotifier realmNotifier, C0690c c0690c);
        }

        private static class C0684c {
            final WeakReference f880a;
            long f881b;
            final C0678a f882c;

            private C0684c(WeakReference weakReference, long j, C0678a c0678a) {
                this.f880a = weakReference;
                this.f881b = j;
                this.f882c = c0678a;
            }
        }

        public interface C0685d {
            C0687g mo2306a(C0715n c0715n);
        }

        public interface C0686e {
            C0682a mo2304a(RealmNotifier realmNotifier, C0690c c0690c);
        }

        public interface C0687g {
            C0686e mo2305a(WeakReference<C0723t<? extends C0497q>> weakReference, long j, C0678a c0678a);

            C0683b mo2308b(WeakReference<? extends C0497q> weakReference, long j, C0678a c0678a);
        }

        private static class C0688f implements C0682a, C0683b, C0685d, C0686e, C0687g {
            private C0715n f883a;
            private List<C0684c> f884b;
            private C0684c f885c;
            private WeakReference<RealmNotifier> f886d;
            private C0690c f887e;

            private C0688f() {
            }

            public C0682a mo2304a(RealmNotifier realmNotifier, C0690c c0690c) {
                this.f886d = new WeakReference(realmNotifier);
                this.f887e = c0690c;
                return this;
            }

            public C0686e mo2305a(WeakReference<C0723t<?>> weakReference, long j, C0678a c0678a) {
                if (this.f884b == null) {
                    this.f884b = new ArrayList(1);
                }
                this.f884b.add(new C0684c(weakReference, j, c0678a));
                return this;
            }

            public C0687g mo2306a(C0715n c0715n) {
                this.f883a = c0715n;
                return this;
            }

            public C0692c mo2307a() {
                return new C0692c(this.f884b != null ? 0 : 1, this.f883a, this.f884b, this.f885c, this.f886d, this.f887e);
            }

            public C0683b mo2308b(WeakReference<? extends C0497q> weakReference, long j, C0678a c0678a) {
                this.f885c = new C0684c(weakReference, j, c0678a);
                return this;
            }
        }
    }

    public enum C0690c {
        COMPLETE_ASYNC_RESULTS,
        COMPLETE_ASYNC_OBJECT,
        COMPLETE_UPDATE_ASYNC_QUERIES,
        THROW_BACKGROUND_EXCEPTION
    }

    public static class C0691d {
        public IdentityHashMap<WeakReference<C0723t<? extends C0497q>>, Long> f893a;
        public IdentityHashMap<WeakReference<C0661k>, Long> f894b;
        public C0672d f895c;

        public static C0691d m1278a() {
            C0691d c0691d = new C0691d();
            c0691d.f893a = new IdentityHashMap(1);
            return c0691d;
        }

        public static C0691d m1279b() {
            C0691d c0691d = new C0691d();
            c0691d.f894b = new IdentityHashMap(1);
            return c0691d;
        }
    }

    private C0692c(int i, C0715n c0715n, List<C0684c> list, C0684c c0684c, WeakReference<RealmNotifier> weakReference, C0690c c0690c) {
        this.f896a = i;
        this.f897b = c0715n;
        this.f898c = list;
        this.f899d = c0684c;
        this.f900e = weakReference;
        this.f901f = c0690c;
    }

    public static C0685d m1280a() {
        return new C0688f();
    }

    private void m1281a(C0691d c0691d, long[] jArr) {
        int i = 0;
        for (C0684c c0684c : this.f898c) {
            IdentityHashMap identityHashMap = c0691d.f893a;
            WeakReference weakReference = c0684c.f880a;
            int i2 = i + 1;
            identityHashMap.put(weakReference, Long.valueOf(jArr[i]));
            i = i2;
        }
    }

    private boolean m1282a(SharedRealm sharedRealm, C0691d c0691d) {
        if (m1284c()) {
            TableQuery.nativeCloseQueryHandover(this.f899d.f881b);
            return false;
        }
        switch (this.f899d.f882c.f869a) {
            case 3:
                c0691d.f894b.put(this.f899d.f880a, Long.valueOf(TableQuery.m1219a(sharedRealm, this.f899d.f881b)));
                return true;
            default:
                throw new IllegalArgumentException("Query mode " + this.f899d.f882c.f869a + " not supported");
        }
    }

    private C0681a m1283b() {
        long[] jArr = new long[this.f898c.size()];
        long[][] jArr2 = (long[][]) Array.newInstance(Long.TYPE, new int[]{this.f898c.size(), 6});
        long[][] jArr3 = new long[this.f898c.size()][];
        boolean[][] zArr = new boolean[this.f898c.size()][];
        int i = 0;
        for (C0684c c0684c : this.f898c) {
            switch (c0684c.f882c.f869a) {
                case 0:
                    jArr[i] = c0684c.f881b;
                    jArr2[i][0] = 0;
                    jArr2[i][1] = 0;
                    jArr2[i][2] = -1;
                    jArr2[i][3] = -1;
                    break;
                case 1:
                    jArr[i] = c0684c.f881b;
                    jArr2[i][0] = 1;
                    jArr2[i][1] = 0;
                    jArr2[i][2] = -1;
                    jArr2[i][3] = -1;
                    jArr2[i][4] = c0684c.f882c.f870b;
                    jArr2[i][5] = c0684c.f882c.f871c.m1492a() ? 1 : 0;
                    break;
                case 2:
                    jArr[i] = c0684c.f881b;
                    jArr2[i][0] = 2;
                    jArr2[i][1] = 0;
                    jArr2[i][2] = -1;
                    jArr2[i][3] = -1;
                    jArr3[i] = c0684c.f882c.f872d;
                    zArr[i] = TableQuery.m1221a(c0684c.f882c.f873e);
                    break;
                case 4:
                    jArr[i] = c0684c.f881b;
                    jArr2[i][0] = 4;
                    jArr2[i][1] = c0684c.f882c.f870b;
                    break;
                default:
                    throw new IllegalArgumentException("Query mode " + c0684c.f882c.f869a + " not supported");
            }
            i++;
        }
        C0681a c0681a = new C0681a();
        c0681a.f876a = jArr;
        c0681a.f878c = jArr3;
        c0681a.f879d = zArr;
        c0681a.f877b = jArr2;
        return c0681a;
    }

    private boolean m1284c() {
        return Thread.currentThread().isInterrupted();
    }

    public void run() {
        SharedRealm a;
        RealmNotifier realmNotifier;
        Throwable th;
        Throwable th2;
        Throwable th3;
        SharedRealm sharedRealm;
        boolean z = true;
        SharedRealm sharedRealm2 = null;
        try {
            a = SharedRealm.m1140a(this.f897b);
            try {
                C0691d c0691d;
                if (this.f896a == 0) {
                    C0691d a2 = C0691d.m1278a();
                    C0681a b = m1283b();
                    m1281a(a2, TableQuery.m1220a(a, b.f876a, b.f877b, b.f878c, b.f879d));
                    a2.f895c = a.m1159k();
                    c0691d = a2;
                } else {
                    C0691d b2 = C0691d.m1279b();
                    boolean a3 = m1282a(a, b2);
                    b2.f895c = a.m1159k();
                    c0691d = b2;
                    z = a3;
                }
                realmNotifier = (RealmNotifier) this.f900e.get();
                if (!(!z || m1284c() || realmNotifier == null)) {
                    switch (this.f901f) {
                        case COMPLETE_ASYNC_RESULTS:
                            realmNotifier.completeAsyncResults(c0691d);
                            break;
                        case COMPLETE_ASYNC_OBJECT:
                            realmNotifier.completeAsyncObject(c0691d);
                            break;
                        case COMPLETE_UPDATE_ASYNC_QUERIES:
                            realmNotifier.completeUpdateAsyncQueries(c0691d);
                            break;
                        default:
                            throw new IllegalStateException(String.format("%s is not handled here.", new Object[]{this.f901f}));
                    }
                }
                if (a != null) {
                    a.close();
                }
            } catch (BadVersionException e) {
                sharedRealm2 = a;
                try {
                    RealmLog.m1409b("Query update task could not complete due to a BadVersionException. Retry is scheduled by a REALM_CHANGED event.", new Object[0]);
                    if (sharedRealm2 != null) {
                        sharedRealm2.close();
                    }
                } catch (Throwable th22) {
                    th = th22;
                    a = sharedRealm2;
                    th3 = th;
                    if (a != null) {
                        a.close();
                    }
                    throw th3;
                }
            } catch (Throwable th4) {
                th3 = th4;
                if (a != null) {
                    a.close();
                }
                throw th3;
            }
        } catch (BadVersionException e2) {
            RealmLog.m1409b("Query update task could not complete due to a BadVersionException. Retry is scheduled by a REALM_CHANGED event.", new Object[0]);
            if (sharedRealm2 != null) {
                sharedRealm2.close();
            }
        } catch (Throwable th222) {
            th = th222;
            a = null;
            th3 = th;
            if (a != null) {
                a.close();
            }
            throw th3;
        }
    }
}
