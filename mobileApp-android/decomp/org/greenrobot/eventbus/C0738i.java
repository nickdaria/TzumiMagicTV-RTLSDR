package org.greenrobot.eventbus;

final class C0738i {
    private C0737h f1068a;
    private C0737h f1069b;

    C0738i() {
    }

    synchronized C0737h m1519a() {
        C0737h c0737h;
        c0737h = this.f1068a;
        if (this.f1068a != null) {
            this.f1068a = this.f1068a.f1067c;
            if (this.f1068a == null) {
                this.f1069b = null;
            }
        }
        return c0737h;
    }

    synchronized C0737h m1520a(int i) throws InterruptedException {
        if (this.f1068a == null) {
            wait((long) i);
        }
        return m1519a();
    }

    synchronized void m1521a(C0737h c0737h) {
        if (c0737h == null) {
            throw new NullPointerException("null cannot be enqueued");
        }
        if (this.f1069b != null) {
            this.f1069b.f1067c = c0737h;
            this.f1069b = c0737h;
        } else if (this.f1068a == null) {
            this.f1069b = c0737h;
            this.f1068a = c0737h;
        } else {
            throw new IllegalStateException("Head present, but no tail");
        }
        notifyAll();
    }
}
