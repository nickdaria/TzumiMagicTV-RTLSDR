package io.realm;

import io.realm.internal.C0661k;
import io.realm.internal.C0699f;
import io.realm.internal.LinkView;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public final class C0719o<E extends C0497q> extends AbstractList<E> implements OrderedRealmCollection<E> {
    protected Class<E> f981a;
    protected String f982b;
    protected LinkView f983c;
    protected C0657b f984d;
    private final boolean f985e = false;
    private List<E> f986f = new ArrayList();

    private class C0717a implements Iterator<E> {
        int f976a;
        int f977b;
        int f978c;
        final /* synthetic */ C0719o f979d;

        private C0717a(C0719o c0719o) {
            this.f979d = c0719o;
            this.f976a = 0;
            this.f977b = -1;
            this.f978c = this.f979d.modCount;
        }

        public E m1437a() {
            this.f979d.f984d.m1010e();
            m1438b();
            int i = this.f976a;
            try {
                E b = this.f979d.m1454b(i);
                this.f977b = i;
                this.f976a = i + 1;
                return b;
            } catch (IndexOutOfBoundsException e) {
                m1438b();
                throw new NoSuchElementException("Cannot access index " + i + " when size is " + this.f979d.size() + ". Remember to check hasNext() before using next().");
            }
        }

        final void m1438b() {
            if (this.f979d.modCount != this.f978c) {
                throw new ConcurrentModificationException();
            }
        }

        public boolean hasNext() {
            this.f979d.f984d.m1010e();
            m1438b();
            return this.f976a != this.f979d.size();
        }

        public /* synthetic */ Object next() {
            return m1437a();
        }

        public void remove() {
            this.f979d.f984d.m1010e();
            if (this.f977b < 0) {
                throw new IllegalStateException("Cannot call remove() twice. Must call next() in between.");
            }
            m1438b();
            try {
                this.f979d.m1451a(this.f977b);
                if (this.f977b < this.f976a) {
                    this.f976a--;
                }
                this.f977b = -1;
                this.f978c = this.f979d.modCount;
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class C0718b extends C0717a implements ListIterator<E> {
        final /* synthetic */ C0719o f980e;

        C0718b(C0719o c0719o, int i) {
            this.f980e = c0719o;
            super();
            if (i < 0 || i > c0719o.size()) {
                throw new IndexOutOfBoundsException("Starting location must be a valid index: [0, " + (c0719o.size() - 1) + "]. Index was " + i);
            }
            this.a = i;
        }

        public void m1439a(E e) {
            this.f980e.f984d.m1010e();
            if (this.b < 0) {
                throw new IllegalStateException();
            }
            m1438b();
            try {
                this.f980e.m1455b(this.b, e);
                this.c = this.f980e.modCount;
            } catch (IndexOutOfBoundsException e2) {
                throw new ConcurrentModificationException();
            }
        }

        public /* synthetic */ void add(Object obj) {
            m1440b((C0497q) obj);
        }

        public void m1440b(E e) {
            this.f980e.f984d.m1010e();
            m1438b();
            try {
                int i = this.a;
                this.f980e.m1452a(i, e);
                this.b = -1;
                this.a = i + 1;
                this.c = this.f980e.modCount;
            } catch (IndexOutOfBoundsException e2) {
                throw new ConcurrentModificationException();
            }
        }

        public E m1441c() {
            m1438b();
            int i = this.a - 1;
            try {
                E b = this.f980e.m1454b(i);
                this.a = i;
                this.b = i;
                return b;
            } catch (IndexOutOfBoundsException e) {
                m1438b();
                throw new NoSuchElementException("Cannot access index less than zero. This was " + i + ". Remember to check hasPrevious() before using previous().");
            }
        }

        public boolean hasPrevious() {
            return this.a != 0;
        }

        public int nextIndex() {
            return this.a;
        }

        public /* synthetic */ Object previous() {
            return m1441c();
        }

        public int previousIndex() {
            return this.a - 1;
        }

        public /* synthetic */ void set(Object obj) {
            m1439a((C0497q) obj);
        }
    }

    private boolean m1443a() {
        return this.f983c != null && this.f983c.m1135c();
    }

    private E m1445b(E e) {
        if (e instanceof C0661k) {
            C0661k c0661k = (C0661k) e;
            if (c0661k instanceof C0665g) {
                String a = RealmSchema.m967a(this.f983c.m1136d());
                String b = ((C0665g) e).m1057b();
                if (c0661k.mo2250j().m1347a() == this.f984d) {
                    if (a.equals(b)) {
                        return e;
                    }
                    throw new IllegalArgumentException(String.format("The object has a different type from list's. Type of the list is '%s', type of object is '%s'.", new Object[]{a, b}));
                } else if (this.f984d.f782c == c0661k.mo2250j().m1347a().f782c) {
                    throw new IllegalArgumentException("Cannot copy DynamicRealmObject between Realm instances.");
                } else {
                    throw new IllegalStateException("Cannot copy an object to a Realm instance created in another thread.");
                }
            } else if (c0661k.mo2250j().m1353b() != null && c0661k.mo2250j().m1347a().mo2257f().equals(this.f984d.mo2257f())) {
                if (this.f984d == c0661k.mo2250j().m1347a()) {
                    return e;
                }
                throw new IllegalArgumentException("Cannot copy an object from another Realm instance.");
            }
        }
        C0708k c0708k = (C0708k) this.f984d;
        return c0708k.m1381b(e.getClass()).m1203e() ? c0708k.m1382b((C0497q) e) : c0708k.m1375a((C0497q) e);
    }

    private void m1446b() {
        this.f984d.m1010e();
        if (this.f983c == null || !this.f983c.m1135c()) {
            throw new IllegalStateException("Realm instance has been closed or this object or its parent has been deleted.");
        }
    }

    private void m1448c(E e) {
        if (e == null) {
            throw new IllegalArgumentException("RealmList does not accept null values");
        }
    }

    public E m1451a(int i) {
        E b;
        if (this.f985e) {
            m1446b();
            b = m1454b(i);
            this.f983c.m1134c((long) i);
        } else {
            C0497q c0497q = (C0497q) this.f986f.remove(i);
        }
        this.modCount++;
        return b;
    }

    public void m1452a(int i, E e) {
        m1448c((C0497q) e);
        if (this.f985e) {
            m1446b();
            if (i < 0 || i > size()) {
                throw new IndexOutOfBoundsException("Invalid index " + i + ", size is " + size());
            }
            this.f983c.m1130a((long) i, ((C0661k) m1445b((C0497q) e)).mo2250j().m1353b().mo2269c());
        } else {
            this.f986f.add(i, e);
        }
        this.modCount++;
    }

    public boolean m1453a(E e) {
        m1448c((C0497q) e);
        if (this.f985e) {
            m1446b();
            this.f983c.m1132b(((C0661k) m1445b((C0497q) e)).mo2250j().m1353b().mo2269c());
        } else {
            this.f986f.add(e);
        }
        this.modCount++;
        return true;
    }

    public /* synthetic */ void add(int i, Object obj) {
        m1452a(i, (C0497q) obj);
    }

    public /* synthetic */ boolean add(Object obj) {
        return m1453a((C0497q) obj);
    }

    public E m1454b(int i) {
        if (!this.f985e) {
            return (C0497q) this.f986f.get(i);
        }
        m1446b();
        return this.f984d.m1002a(this.f981a, this.f982b, this.f983c.m1128a((long) i));
    }

    public E m1455b(int i, E e) {
        m1448c((C0497q) e);
        if (!this.f985e) {
            return (C0497q) this.f986f.set(i, e);
        }
        m1446b();
        C0661k c0661k = (C0661k) m1445b((C0497q) e);
        E b = m1454b(i);
        this.f983c.m1133b((long) i, c0661k.mo2250j().m1353b().mo2269c());
        return b;
    }

    public void clear() {
        if (this.f985e) {
            m1446b();
            this.f983c.m1129a();
        } else {
            this.f986f.clear();
        }
        this.modCount++;
    }

    public boolean contains(Object obj) {
        if (!this.f985e) {
            return this.f986f.contains(obj);
        }
        this.f984d.m1010e();
        if (!(obj instanceof C0661k)) {
            return false;
        }
        C0661k c0661k = (C0661k) obj;
        return (c0661k.mo2250j().m1353b() == null || !this.f984d.mo2257f().equals(c0661k.mo2250j().m1347a().mo2257f()) || c0661k.mo2250j().m1353b() == C0699f.INSTANCE) ? false : this.f983c.m1137d(c0661k.mo2250j().m1353b().mo2269c());
    }

    public /* synthetic */ Object get(int i) {
        return m1454b(i);
    }

    public Iterator<E> iterator() {
        return this.f985e ? new C0717a() : super.iterator();
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public ListIterator<E> listIterator(int i) {
        return this.f985e ? new C0718b(this, i) : super.listIterator(i);
    }

    public /* synthetic */ Object remove(int i) {
        return m1451a(i);
    }

    public boolean remove(Object obj) {
        if (!this.f985e || this.f984d.mo2252a()) {
            return super.remove(obj);
        }
        throw new IllegalStateException("Objects can only be removed from inside a write transaction");
    }

    public boolean removeAll(Collection<?> collection) {
        if (!this.f985e || this.f984d.mo2252a()) {
            return super.removeAll(collection);
        }
        throw new IllegalStateException("Objects can only be removed from inside a write transaction");
    }

    public /* synthetic */ Object set(int i, Object obj) {
        return m1455b(i, (C0497q) obj);
    }

    public int size() {
        if (!this.f985e) {
            return this.f986f.size();
        }
        m1446b();
        long b = this.f983c.m1131b();
        return b < 2147483647L ? (int) b : ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f985e ? this.f981a.getSimpleName() : getClass().getSimpleName());
        stringBuilder.append("@[");
        if (!this.f985e || m1443a()) {
            for (int i = 0; i < size(); i++) {
                if (this.f985e) {
                    stringBuilder.append(((C0661k) m1454b(i)).mo2250j().m1353b().mo2269c());
                } else {
                    stringBuilder.append(System.identityHashCode(m1454b(i)));
                }
                if (i < size() - 1) {
                    stringBuilder.append(',');
                }
            }
        } else {
            stringBuilder.append("invalid");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
