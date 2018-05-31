package org.greenrobot.eventbus;

import android.util.Log;

final class C0728b implements Runnable {
    private final C0738i f1016a = new C0738i();
    private final C0732c f1017b;
    private volatile boolean f1018c;

    C0728b(C0732c c0732c) {
        this.f1017b = c0732c;
    }

    public void m1498a(C0744n c0744n, Object obj) {
        C0737h a = C0737h.m1517a(c0744n, obj);
        synchronized (this) {
            this.f1016a.m1521a(a);
            if (!this.f1018c) {
                this.f1018c = true;
                this.f1017b.m1513b().execute(this);
            }
        }
    }

    public void run() {
        while (true) {
            try {
                C0737h a = this.f1016a.m1520a(1000);
                if (a == null) {
                    synchronized (this) {
                        a = this.f1016a.m1519a();
                        if (a == null) {
                            this.f1018c = false;
                            this.f1018c = false;
                            return;
                        }
                    }
                }
                this.f1017b.m1511a(a);
            } catch (Throwable e) {
                Log.w("Event", Thread.currentThread().getName() + " was interruppted", e);
                this.f1018c = false;
                return;
            } catch (Throwable th) {
                this.f1018c = false;
            }
        }
    }
}
