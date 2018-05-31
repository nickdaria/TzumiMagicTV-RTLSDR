package io.realm.internal;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class C0696c {
    private List<Long> f908a = new ArrayList();
    private List<Long> f909b = new ArrayList();
    private List<Long> f910c = new ArrayList();
    private C0695a f911d = new C0695a();
    private ReferenceQueue<C0668g> f912e = new ReferenceQueue();
    private boolean f913f = false;

    private static class C0695a {
        ArrayList<C0700h> f906a;
        ArrayList<Integer> f907b;

        private C0695a() {
            this.f906a = new ArrayList();
            this.f907b = new ArrayList();
        }

        Integer m1287a() {
            int size = this.f907b.size();
            return size == 0 ? Integer.valueOf(this.f906a.size()) : (Integer) this.f907b.remove(size - 1);
        }

        void m1288a(C0700h c0700h) {
            if (this.f906a.size() <= c0700h.f919c.intValue()) {
                this.f906a.add(c0700h);
            } else {
                this.f906a.set(c0700h.f919c.intValue(), c0700h);
            }
        }
    }

    private void m1289b() {
        C0700h c0700h = (C0700h) this.f912e.poll();
        while (c0700h != null) {
            c0700h.m1317a();
            this.f911d.f907b.add(c0700h.f919c);
            c0700h = (C0700h) this.f912e.poll();
        }
    }

    public synchronized void m1290a() {
        int i = 0;
        synchronized (this) {
            int i2;
            for (i2 = 0; i2 < this.f908a.size(); i2++) {
                Table.nativeClose(((Long) this.f908a.get(i2)).longValue());
            }
            this.f908a.clear();
            for (i2 = 0; i2 < this.f909b.size(); i2++) {
                TableView.nativeClose(((Long) this.f909b.get(i2)).longValue());
            }
            this.f909b.clear();
            while (i < this.f910c.size()) {
                TableQuery.nativeClose(((Long) this.f910c.get(i)).longValue());
                i++;
            }
            this.f910c.clear();
            m1289b();
        }
    }

    public synchronized void m1291a(int i, C0668g c0668g) {
        this.f911d.m1288a(new C0700h(i, c0668g, this.f912e, this.f911d.m1287a()));
    }

    public void m1292a(long j) {
        if (this.f913f) {
            TableView.nativeClose(j);
        } else {
            this.f909b.add(Long.valueOf(j));
        }
    }

    public void m1293a(long j, boolean z) {
        if (z || this.f913f) {
            Table.nativeClose(j);
        } else {
            this.f908a.add(Long.valueOf(j));
        }
    }

    public void m1294b(long j) {
        if (this.f913f) {
            TableQuery.nativeClose(j);
        } else {
            this.f910c.add(Long.valueOf(j));
        }
    }

    protected void finalize() throws Throwable {
        synchronized (this) {
            this.f913f = true;
        }
        m1290a();
        super.finalize();
    }
}
