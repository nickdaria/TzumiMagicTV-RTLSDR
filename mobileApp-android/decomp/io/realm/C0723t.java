package io.realm;

import io.realm.internal.C0661k;
import io.realm.internal.C0674n;
import io.realm.internal.C0699f;
import io.realm.internal.TableQuery;
import io.realm.internal.TableView;
import io.realm.internal.async.BadVersionException;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

public final class C0723t<E extends C0497q> extends AbstractList<E> implements OrderedRealmCollection<E> {
    final C0657b f1000a;
    Class<E> f1001b;
    String f1002c;
    private C0674n f1003d;
    private long f1004e;
    private final TableQuery f1005f;
    private final List<C0713m<C0723t<E>>> f1006g;
    private Future<Long> f1007h;
    private boolean f1008i;
    private boolean f1009j;

    private class C0721a implements Iterator<E> {
        long f996a = 0;
        int f997b = -1;
        final /* synthetic */ C0723t f998c;

        C0721a(C0723t c0723t) {
            this.f998c = c0723t;
            this.f996a = c0723t.f1004e;
        }

        public E m1469a() {
            this.f998c.f1000a.m1010e();
            m1470b();
            this.f997b++;
            if (this.f997b < this.f998c.size()) {
                return this.f998c.m1479a(this.f997b);
            }
            throw new NoSuchElementException("Cannot access index " + this.f997b + " when size is " + this.f998c.size() + ". Remember to check hasNext() before using next().");
        }

        protected void m1470b() {
            long k = this.f998c.f1003d.mo2302k();
            if (this.f998c.f1000a.mo2252a() || this.f996a <= -1 || k == this.f996a) {
                this.f996a = k;
                return;
            }
            throw new ConcurrentModificationException("No outside changes to a Realm is allowed while iterating a RealmResults. Don't call Realm.refresh() while iterating.");
        }

        public boolean hasNext() {
            return this.f997b + 1 < this.f998c.size();
        }

        public /* synthetic */ Object next() {
            return m1469a();
        }

        @Deprecated
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported by RealmResults iterators.");
        }
    }

    private class C0722b extends C0721a implements ListIterator<E> {
        final /* synthetic */ C0723t f999d;

        C0722b(C0723t c0723t, int i) {
            this.f999d = c0723t;
            super(c0723t);
            if (i < 0 || i > c0723t.size()) {
                throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (c0723t.size() - 1) + "]. Yours was " + i);
            }
            this.b = i - 1;
        }

        @Deprecated
        public void m1471a(E e) {
            throw new UnsupportedOperationException("Adding an element is not supported. Use Realm.createObject() instead.");
        }

        @Deprecated
        public /* synthetic */ void add(Object obj) {
            m1471a((C0497q) obj);
        }

        @Deprecated
        public void m1472b(E e) {
            throw new UnsupportedOperationException("Replacing and element is not supported.");
        }

        public E m1473c() {
            this.f999d.f1000a.m1010e();
            m1470b();
            try {
                E a = this.f999d.m1479a(this.b);
                this.b--;
                return a;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException("Cannot access index less than zero. This was " + this.b + ". Remember to check hasPrevious() before using previous().");
            }
        }

        public boolean hasPrevious() {
            return this.b >= 0;
        }

        public int nextIndex() {
            return this.b + 1;
        }

        public /* synthetic */ Object previous() {
            return m1473c();
        }

        public int previousIndex() {
            return this.b;
        }

        @Deprecated
        public /* synthetic */ void set(Object obj) {
            m1472b((C0497q) obj);
        }
    }

    private C0723t(C0657b c0657b, C0674n c0674n, Class<E> cls) {
        this.f1003d = null;
        this.f1004e = -1;
        this.f1006g = new CopyOnWriteArrayList();
        this.f1008i = false;
        this.f1009j = false;
        this.f1000a = c0657b;
        this.f1001b = cls;
        this.f1003d = c0674n;
        this.f1007h = null;
        this.f1005f = null;
        this.f1004e = c0674n.mo2301j();
    }

    private C0723t(C0657b c0657b, C0674n c0674n, String str) {
        this(c0657b, str);
        this.f1003d = c0674n;
        this.f1004e = c0674n.mo2301j();
    }

    private C0723t(C0657b c0657b, String str) {
        this.f1003d = null;
        this.f1004e = -1;
        this.f1006g = new CopyOnWriteArrayList();
        this.f1008i = false;
        this.f1009j = false;
        this.f1000a = c0657b;
        this.f1002c = str;
        this.f1007h = null;
        this.f1005f = null;
    }

    static <E extends C0497q> C0723t<E> m1475a(C0657b c0657b, C0674n c0674n, Class<E> cls) {
        C0723t c0723t = new C0723t(c0657b, c0674n, (Class) cls);
        c0657b.f786g.m1074a(c0723t);
        return c0723t;
    }

    static C0723t<C0665g> m1476a(C0657b c0657b, C0674n c0674n, String str) {
        C0723t c0723t = new C0723t(c0657b, c0674n, str);
        c0657b.f786g.m1074a(c0723t);
        return c0723t;
    }

    C0674n m1478a() {
        return this.f1003d == null ? this.f1000a.f785f.m974b(this.f1001b) : this.f1003d;
    }

    public E m1479a(int i) {
        this.f1000a.m1010e();
        C0674n a = m1478a();
        return a instanceof TableView ? this.f1000a.m1002a(this.f1001b, this.f1002c, ((TableView) a).m1231a((long) i)) : this.f1000a.m1002a(this.f1001b, this.f1002c, (long) i);
    }

    @Deprecated
    public E m1480a(int i, E e) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    public C0723t<E> m1481a(String str) {
        return m1482a(str, C0724u.ASCENDING);
    }

    public C0723t<E> m1482a(String str, C0724u c0724u) {
        return m1487b().m1465a(str, c0724u);
    }

    void m1483a(long j) {
        try {
            this.f1003d = this.f1005f.m1227a(j, this.f1000a.f784e);
            this.f1008i = true;
        } catch (BadVersionException e) {
            throw new IllegalStateException("Caller and Worker Realm should have been at the same version");
        }
    }

    void m1484a(boolean z) {
        if (!this.f1006g.isEmpty()) {
            if (this.f1007h != null && !this.f1008i) {
                return;
            }
            if (this.f1009j || z) {
                this.f1009j = false;
                for (C0713m a : this.f1006g) {
                    a.m1414a(this);
                }
            }
        }
    }

    @Deprecated
    public boolean m1485a(E e) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    @Deprecated
    public /* synthetic */ void add(int i, Object obj) {
        m1488b(i, (C0497q) obj);
    }

    @Deprecated
    public /* synthetic */ boolean add(Object obj) {
        return m1485a((C0497q) obj);
    }

    @Deprecated
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    @Deprecated
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    @Deprecated
    public E m1486b(int i) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    public C0720s<E> m1487b() {
        this.f1000a.m1010e();
        return C0720s.m1458a(this);
    }

    @Deprecated
    public void m1488b(int i, E e) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    public boolean m1489c() {
        this.f1000a.m1010e();
        if (size() <= 0) {
            return false;
        }
        m1478a().mo2299b();
        return true;
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    public boolean contains(Object obj) {
        if (!m1491e() || !(obj instanceof C0661k)) {
            return false;
        }
        C0661k c0661k = (C0661k) obj;
        return (!this.f1000a.mo2257f().equals(c0661k.mo2250j().m1347a().mo2257f()) || c0661k.mo2250j().m1353b() == C0699f.INSTANCE || this.f1003d.mo2303m(c0661k.mo2250j().m1353b().mo2269c()) == -1) ? false : true;
    }

    void m1490d() {
        long j = this.f1003d.mo2301j();
        this.f1009j = j != this.f1004e;
        this.f1004e = j;
    }

    public boolean m1491e() {
        this.f1000a.m1010e();
        return this.f1007h == null || this.f1008i;
    }

    public /* synthetic */ Object get(int i) {
        return m1479a(i);
    }

    public Iterator<E> iterator() {
        return !m1491e() ? Collections.emptyList().iterator() : new C0721a(this);
    }

    public ListIterator<E> listIterator() {
        return !m1491e() ? Collections.emptyList().listIterator() : new C0722b(this, 0);
    }

    public ListIterator<E> listIterator(int i) {
        return !m1491e() ? Collections.emptyList().listIterator(i) : new C0722b(this, i);
    }

    @Deprecated
    public /* synthetic */ Object remove(int i) {
        return m1486b(i);
    }

    @Deprecated
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    @Deprecated
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    @Deprecated
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException("This method is not supported by RealmResults.");
    }

    @Deprecated
    public /* synthetic */ Object set(int i, Object obj) {
        return m1480a(i, (C0497q) obj);
    }

    public int size() {
        if (!m1491e()) {
            return 0;
        }
        long a = m1478a().mo2298a();
        return a > 2147483647L ? ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED : (int) a;
    }
}
