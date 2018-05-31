package org.greenrobot.eventbus;

final class C0744n {
    final Object f1093a;
    final C0741l f1094b;
    volatile boolean f1095c = true;

    C0744n(Object obj, C0741l c0741l) {
        this.f1093a = obj;
        this.f1094b = c0741l;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C0744n)) {
            return false;
        }
        C0744n c0744n = (C0744n) obj;
        return this.f1093a == c0744n.f1093a && this.f1094b.equals(c0744n.f1094b);
    }

    public int hashCode() {
        return this.f1093a.hashCode() + this.f1094b.f1079f.hashCode();
    }
}
