package com.eardatek.special.player.p006c;

import org.greenrobot.eventbus.C0732c;

public class C0502a {
    private int f349a;
    private Object f350b;

    public static void m504a(int i, Object obj) {
        C0502a c0502a = new C0502a();
        c0502a.m507a(obj);
        c0502a.m506a(i);
        C0732c.m1501a().m1515c(c0502a);
    }

    public int m505a() {
        return this.f349a;
    }

    public void m506a(int i) {
        this.f349a = i;
    }

    public void m507a(Object obj) {
        this.f350b = obj;
    }

    public Object m508b() {
        return this.f350b;
    }
}
