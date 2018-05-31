package org.greenrobot.eventbus;

import java.util.ArrayList;
import java.util.List;

final class C0737h {
    private static final List<C0737h> f1064d = new ArrayList();
    Object f1065a;
    C0744n f1066b;
    C0737h f1067c;

    private C0737h(Object obj, C0744n c0744n) {
        this.f1065a = obj;
        this.f1066b = c0744n;
    }

    static C0737h m1517a(C0744n c0744n, Object obj) {
        synchronized (f1064d) {
            int size = f1064d.size();
            if (size > 0) {
                C0737h c0737h = (C0737h) f1064d.remove(size - 1);
                c0737h.f1065a = obj;
                c0737h.f1066b = c0744n;
                c0737h.f1067c = null;
                return c0737h;
            }
            return new C0737h(obj, c0744n);
        }
    }

    static void m1518a(C0737h c0737h) {
        c0737h.f1065a = null;
        c0737h.f1066b = null;
        c0737h.f1067c = null;
        synchronized (f1064d) {
            if (f1064d.size() < 10000) {
                f1064d.add(c0737h);
            }
        }
    }
}
