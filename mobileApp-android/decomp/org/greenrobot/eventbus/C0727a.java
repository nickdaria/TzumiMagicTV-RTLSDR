package org.greenrobot.eventbus;

class C0727a implements Runnable {
    private final C0738i f1014a = new C0738i();
    private final C0732c f1015b;

    C0727a(C0732c c0732c) {
        this.f1015b = c0732c;
    }

    public void m1497a(C0744n c0744n, Object obj) {
        this.f1014a.m1521a(C0737h.m1517a(c0744n, obj));
        this.f1015b.m1513b().execute(this);
    }

    public void run() {
        C0737h a = this.f1014a.m1519a();
        if (a == null) {
            throw new IllegalStateException("No pending post available");
        }
        this.f1015b.m1511a(a);
    }
}
