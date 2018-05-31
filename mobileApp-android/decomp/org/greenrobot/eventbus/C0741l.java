package org.greenrobot.eventbus;

import java.lang.reflect.Method;

public class C0741l {
    final Method f1074a;
    final ThreadMode f1075b;
    final Class<?> f1076c;
    final int f1077d;
    final boolean f1078e;
    String f1079f;

    public C0741l(Method method, Class<?> cls, ThreadMode threadMode, int i, boolean z) {
        this.f1074a = method;
        this.f1075b = threadMode;
        this.f1076c = cls;
        this.f1077d = i;
        this.f1078e = z;
    }

    private synchronized void m1525a() {
        if (this.f1079f == null) {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append(this.f1074a.getDeclaringClass().getName());
            stringBuilder.append('#').append(this.f1074a.getName());
            stringBuilder.append('(').append(this.f1076c.getName());
            this.f1079f = stringBuilder.toString();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0741l)) {
            return false;
        }
        m1525a();
        C0741l c0741l = (C0741l) obj;
        c0741l.m1525a();
        return this.f1079f.equals(c0741l.f1079f);
    }

    public int hashCode() {
        return this.f1074a.hashCode();
    }
}
