package io.realm;

import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.C0698e;
import io.realm.internal.async.C0692c;
import io.realm.internal.async.C0692c.C0689b.C0686e;
import io.realm.internal.async.C0692c.C0689b.C0687g;
import io.realm.internal.async.C0692c.C0690c;
import io.realm.internal.async.C0692c.C0691d;
import io.realm.log.RealmLog;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

final class C0667i implements Callback {
    private static final Boolean f805i = Boolean.TRUE;
    final CopyOnWriteArrayList<C0713m<? extends C0657b>> f806a = new CopyOnWriteArrayList();
    final List<WeakReference<C0713m<? extends C0657b>>> f807b = new CopyOnWriteArrayList();
    final C0657b f808c;
    final ReferenceQueue<C0497q> f809d = new ReferenceQueue();
    final Map<WeakReference<C0723t<? extends C0497q>>, C0720s<? extends C0497q>> f810e = new IdentityHashMap();
    final Map<WeakReference<C0661k>, C0720s<? extends C0497q>> f811f = new ConcurrentHashMap();
    final C0698e<WeakReference<C0723t<? extends C0497q>>> f812g = new C0698e();
    final ConcurrentHashMap<WeakReference<C0661k>, Object> f813h = new ConcurrentHashMap();
    private boolean f814j;
    private Future f815k;
    private final ReferenceQueue<C0723t<? extends C0497q>> f816l = new ReferenceQueue();
    private final ReferenceQueue<C0723t<? extends C0497q>> f817m = new ReferenceQueue();
    private final List<Runnable> f818n = new ArrayList();

    public C0667i(C0657b c0657b) {
        this.f808c = c0657b;
    }

    private void m1059a(C0691d c0691d) {
        Set keySet = c0691d.f893a.keySet();
        if (keySet.size() > 0) {
            WeakReference weakReference = (WeakReference) keySet.iterator().next();
            C0723t c0723t = (C0723t) weakReference.get();
            if (c0723t == null) {
                this.f810e.remove(weakReference);
                RealmLog.m1406a("[COMPLETED_ASYNC_REALM_RESULTS %s] realm: %s RealmResults GC'd ignore results", weakReference, this);
                return;
            }
            int a = this.f808c.f784e.m1159k().m1139a(c0691d.f895c);
            if (a == 0) {
                if (c0723t.m1491e()) {
                    RealmLog.m1406a("[COMPLETED_ASYNC_REALM_RESULTS %s] , realm: %s ignoring result the RealmResults (is already loaded)", weakReference, this);
                    return;
                }
                RealmLog.m1406a("[COMPLETED_ASYNC_REALM_RESULTS %s] , realm: %s same versions, using results (RealmResults is not loaded)", weakReference, this);
                c0723t.m1483a(((Long) c0691d.f893a.get(weakReference)).longValue());
                c0723t.m1490d();
                c0723t.m1484a(false);
            } else if (a <= 0) {
                RealmLog.m1406a("[COMPLETED_ASYNC_REALM_RESULTS %s] , %s caller thread behind worker thread, ignore results (a batch update will update everything including this query)", weakReference, this);
            } else if (c0723t.m1491e()) {
                RealmLog.m1406a("[COMPLETED_ASYNC_REALM_RESULTS %s] , %s caller is more advanced & RealmResults is loaded ignore the outdated result", weakReference, this);
            } else {
                RealmLog.m1406a("[COMPLETED_ASYNC_REALM_RESULTS %s ] , %s caller is more advanced & RealmResults is not loaded, rerunning the query against the latest version", weakReference, this);
                C0720s c0720s = (C0720s) this.f810e.get(weakReference);
                C0708k.b.m1286a(C0692c.m1280a().mo2306a(this.f808c.mo2258g()).mo2305a(weakReference, c0720s.m1468d(), c0720s.m1467c()).mo2304a(this.f808c.f784e.f840a, C0690c.COMPLETE_ASYNC_RESULTS).mo2307a());
            }
        }
    }

    private void m1060a(Iterator<WeakReference<C0723t<? extends C0497q>>> it, List<C0723t<? extends C0497q>> list) {
        while (it.hasNext()) {
            C0723t c0723t = (C0723t) ((WeakReference) it.next()).get();
            if (c0723t == null) {
                it.remove();
            } else if (c0723t.m1491e()) {
                c0723t.m1490d();
                list.add(c0723t);
            }
        }
    }

    private void m1061b(C0691d c0691d) {
        int a = this.f808c.f784e.m1159k().m1139a(c0691d.f895c);
        if (a > 0) {
            RealmLog.m1406a("COMPLETED_UPDATE_ASYNC_QUERIES %s caller is more advanced, Looper will updates queries", this);
            return;
        }
        if (a != 0) {
            RealmLog.m1406a("COMPLETED_UPDATE_ASYNC_QUERIES %s caller is behind advance_read", this);
            try {
                this.f808c.f784e.m1147a(c0691d.f895c);
            } catch (Throwable e) {
                throw new IllegalStateException("Failed to advance Caller Realm to Worker Realm version", e);
            }
        }
        List arrayList = new ArrayList(c0691d.f893a.size());
        for (Entry entry : c0691d.f893a.entrySet()) {
            WeakReference weakReference = (WeakReference) entry.getKey();
            C0723t c0723t = (C0723t) weakReference.get();
            if (c0723t == null) {
                this.f810e.remove(weakReference);
            } else {
                c0723t.m1483a(((Long) entry.getValue()).longValue());
                c0723t.m1490d();
                arrayList.add(c0723t);
                RealmLog.m1406a("COMPLETED_UPDATE_ASYNC_QUERIES updating RealmResults %s", this, weakReference);
            }
        }
        m1065c(arrayList);
        m1075a(arrayList);
        this.f815k = null;
    }

    private void m1062b(List<C0723t<? extends C0497q>> list) {
        m1060a(this.f810e.keySet().iterator(), list);
    }

    private void m1063b(boolean z) {
        String str = "%s : %s";
        Object[] objArr = new Object[2];
        objArr[0] = z ? "LOCAL_COMMIT" : "REALM_CHANGED";
        objArr[1] = this;
        RealmLog.m1409b(str, objArr);
        m1072j();
        boolean i = m1071i();
        if (z && i) {
            RealmLog.m1411c("Mixing asynchronous queries with local writes should be avoided. Realm will convert any async queries to synchronous in order to remain consistent. Use asynchronous writes instead. You can read more here: https://realm.io/docs/java/latest/#asynchronous-transactions", new Object[0]);
        }
        if (z || !i) {
            this.f808c.f784e.m1158j();
            List arrayList = new ArrayList();
            m1062b(arrayList);
            m1065c(arrayList);
            m1075a(arrayList);
            return;
        }
        m1069g();
    }

    private void m1064c(C0691d c0691d) {
        Set keySet = c0691d.f894b.keySet();
        if (keySet.size() > 0) {
            WeakReference weakReference = (WeakReference) keySet.iterator().next();
            C0661k c0661k = (C0661k) weakReference.get();
            if (c0661k != null) {
                int a = this.f808c.f784e.m1159k().m1139a(c0691d.f895c);
                if (a == 0) {
                    long longValue = ((Long) c0691d.f894b.get(weakReference)).longValue();
                    if (longValue != 0 && this.f811f.containsKey(weakReference)) {
                        this.f811f.remove(weakReference);
                        this.f813h.put(weakReference, f805i);
                    }
                    c0661k.mo2250j().m1348a(longValue);
                    c0661k.mo2250j().m1355d();
                } else if (a <= 0) {
                    throw new IllegalStateException("Caller thread behind the Worker thread");
                } else if (C0498r.m478b(c0661k)) {
                    RealmLog.m1406a("[COMPLETED_ASYNC_REALM_OBJECT %s], realm: %s. RealmObject is already loaded, just notify it", this.f808c, this);
                    c0661k.mo2250j().m1355d();
                } else {
                    RealmLog.m1406a("[COMPLETED_ASYNC_REALM_OBJECT %s, realm: %s. RealmObject is not loaded yet. Rerun the query.", c0661k, this);
                    Boolean bool = this.f813h.get(weakReference);
                    C0720s c0720s = (bool == null || bool == f805i) ? (C0720s) this.f811f.get(weakReference) : (C0720s) bool;
                    C0708k.b.m1286a(C0692c.m1280a().mo2306a(this.f808c.mo2258g()).mo2308b(weakReference, c0720s.m1468d(), c0720s.m1467c()).mo2304a(this.f808c.f784e.f840a, C0690c.COMPLETE_ASYNC_OBJECT).mo2307a());
                }
            }
        }
    }

    private void m1065c(List<C0723t<? extends C0497q>> list) {
        m1060a(this.f812g.keySet().iterator(), list);
    }

    private void m1066d() {
        Iterator it = this.f806a.iterator();
        while (!this.f808c.mo2260j() && it.hasNext()) {
            ((C0713m) it.next()).m1414a(this.f808c);
        }
        Iterator it2 = this.f807b.iterator();
        Collection collection = null;
        while (!this.f808c.mo2260j() && it2.hasNext()) {
            List arrayList;
            WeakReference weakReference = (WeakReference) it2.next();
            C0713m c0713m = (C0713m) weakReference.get();
            Collection collection2;
            if (c0713m == null) {
                if (collection == null) {
                    arrayList = new ArrayList(this.f807b.size());
                } else {
                    collection2 = collection;
                }
                arrayList.add(weakReference);
            } else {
                c0713m.m1414a(this.f808c);
                collection2 = collection;
            }
            Object obj = arrayList;
        }
        if (collection != null) {
            this.f807b.removeAll(collection);
        }
    }

    private void m1067e() {
        Iterator it = this.f811f.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (((WeakReference) entry.getKey()).get() != null) {
                C0708k.b.m1286a(C0692c.m1280a().mo2306a(this.f808c.mo2258g()).mo2308b((WeakReference) entry.getKey(), ((C0720s) entry.getValue()).m1468d(), ((C0720s) entry.getValue()).m1467c()).mo2304a(this.f808c.f784e.f840a, C0690c.COMPLETE_ASYNC_OBJECT).mo2307a());
            } else {
                it.remove();
            }
        }
    }

    private void m1068f() {
        List arrayList = new ArrayList();
        Iterator it = this.f813h.keySet().iterator();
        while (it.hasNext()) {
            C0661k c0661k = (C0661k) ((WeakReference) it.next()).get();
            if (c0661k == null) {
                it.remove();
            } else if (c0661k.mo2250j().m1353b().mo2272d()) {
                arrayList.add(c0661k);
            } else if (c0661k.mo2250j().m1353b() != C0669m.f820b) {
                it.remove();
            }
        }
        Iterator it2 = arrayList.iterator();
        while (!this.f808c.mo2260j() && it2.hasNext()) {
            ((C0661k) it2.next()).mo2250j().m1355d();
        }
    }

    private void m1069g() {
        if (!(this.f815k == null || this.f815k.isDone())) {
            this.f815k.cancel(true);
            C0708k.b.getQueue().remove(this.f815k);
            RealmLog.m1406a("REALM_CHANGED realm: %s cancelling pending COMPLETED_UPDATE_ASYNC_QUERIES updates", this);
        }
        RealmLog.m1406a("REALM_CHANGED realm: %s updating async queries, total: %d", this, Integer.valueOf(this.f810e.size()));
        C0687g a = C0692c.m1280a().mo2306a(this.f808c.mo2258g());
        C0686e c0686e = null;
        Iterator it = this.f810e.entrySet().iterator();
        while (it.hasNext()) {
            C0686e c0686e2;
            Entry entry = (Entry) it.next();
            WeakReference weakReference = (WeakReference) entry.getKey();
            if (((C0723t) weakReference.get()) == null) {
                it.remove();
                c0686e2 = c0686e;
            } else {
                c0686e2 = a.mo2305a(weakReference, ((C0720s) entry.getValue()).m1468d(), ((C0720s) entry.getValue()).m1467c());
            }
            c0686e = c0686e2;
        }
        if (c0686e != null) {
            this.f815k = C0708k.b.m1286a(c0686e.mo2304a(this.f808c.f784e.f840a, C0690c.COMPLETE_UPDATE_ASYNC_QUERIES).mo2307a());
        }
    }

    private void m1070h() {
        if (!this.f818n.isEmpty()) {
            for (Runnable run : this.f818n) {
                run.run();
            }
            this.f818n.clear();
        }
    }

    private boolean m1071i() {
        Iterator it = this.f810e.entrySet().iterator();
        boolean z = true;
        while (it.hasNext()) {
            boolean z2;
            if (((WeakReference) ((Entry) it.next()).getKey()).get() == null) {
                it.remove();
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        return !z;
    }

    private void m1072j() {
        while (true) {
            Reference poll = this.f816l.poll();
            if (poll == null) {
                break;
            }
            this.f810e.remove(poll);
        }
        while (true) {
            poll = this.f817m.poll();
            if (poll == null) {
                break;
            }
            this.f812g.remove(poll);
        }
        while (true) {
            poll = this.f809d.poll();
            if (poll != null) {
                this.f813h.remove(poll);
            } else {
                return;
            }
        }
    }

    private static boolean m1073k() {
        String name = Thread.currentThread().getName();
        return name != null && name.startsWith("IntentService[");
    }

    void m1074a(C0723t<? extends C0497q> c0723t) {
        this.f812g.m1295a(new WeakReference(c0723t, this.f817m));
    }

    void m1075a(List<C0723t<? extends C0497q>> list) {
        Iterator it = list.iterator();
        while (!this.f808c.mo2260j() && it.hasNext()) {
            ((C0723t) it.next()).m1484a(false);
        }
        m1068f();
        if (!this.f808c.mo2260j() && m1077a()) {
            m1067e();
        }
        m1070h();
        m1066d();
    }

    public void m1076a(boolean z) {
        m1078b();
        this.f814j = z;
    }

    boolean m1077a() {
        Iterator it = this.f811f.entrySet().iterator();
        boolean z = true;
        while (it.hasNext()) {
            boolean z2;
            if (((WeakReference) ((Entry) it.next()).getKey()).get() == null) {
                it.remove();
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        return !z;
    }

    public void m1078b() {
        if (Looper.myLooper() == null) {
            throw new IllegalStateException("Cannot set auto-refresh in a Thread without a Looper");
        } else if (C0667i.m1073k()) {
            throw new IllegalStateException("Cannot set auto-refresh in an IntentService thread.");
        }
    }

    public boolean m1079c() {
        return (Looper.myLooper() == null || C0667i.m1073k()) ? false : true;
    }

    public boolean handleMessage(Message message) {
        if (this.f808c.f784e != null) {
            switch (message.what) {
                case 14930352:
                case 165580141:
                    m1063b(message.what == 165580141);
                    break;
                case 24157817:
                    m1061b((C0691d) message.obj);
                    break;
                case 39088169:
                    m1059a((C0691d) message.obj);
                    break;
                case 63245986:
                    m1064c((C0691d) message.obj);
                    break;
                case 102334155:
                    throw ((Error) message.obj);
                default:
                    throw new IllegalArgumentException("Unknown message: " + message.what);
            }
        }
        return true;
    }
}
