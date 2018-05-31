package io.realm;

import io.realm.internal.C0669m;
import io.realm.internal.Table;
import io.realm.internal.TableQuery;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class C0704j<E extends C0497q> {
    protected long f926a = -1;
    private E f927b;
    private String f928c;
    private Class<? extends C0497q> f929d;
    private boolean f930e = true;
    private C0669m f931f;
    private C0657b f932g;
    private boolean f933h;
    private List<String> f934i;
    private final List<C0713m<E>> f935j = new CopyOnWriteArrayList();
    private boolean f936k = false;

    public C0704j(E e) {
        this.f927b = e;
    }

    public C0704j(Class<? extends C0497q> cls, E e) {
        this.f929d = cls;
        this.f927b = e;
    }

    private Table m1346h() {
        return this.f928c != null ? m1347a().f785f.m979d(this.f928c) : m1347a().f785f.m974b(this.f929d);
    }

    public C0657b m1347a() {
        return this.f932g;
    }

    public void m1348a(long j) {
        if (j == 0) {
            this.f936k = true;
        } else if (!this.f936k || this.f931f == C0669m.f820b) {
            this.f936k = true;
            this.f931f = m1346h().m1209h(TableQuery.m1222b(j, this.f932g.f784e));
        }
    }

    public void m1349a(C0657b c0657b) {
        this.f932g = c0657b;
    }

    public void m1350a(C0669m c0669m) {
        this.f931f = c0669m;
    }

    public void m1351a(List<String> list) {
        this.f934i = list;
    }

    public void m1352a(boolean z) {
        this.f933h = z;
    }

    public C0669m m1353b() {
        return this.f931f;
    }

    public boolean m1354c() {
        return this.f933h;
    }

    void m1355d() {
        Object obj = 1;
        if (!this.f935j.isEmpty()) {
            Table b = this.f931f.mo2267b();
            if (b != null) {
                long k = b.mo2302k();
                if (this.f926a != k) {
                    this.f926a = k;
                } else {
                    obj = null;
                }
            }
            if (obj != null) {
                for (C0713m a : this.f935j) {
                    a.m1414a(this.f927b);
                }
            }
        }
    }

    public void m1356e() {
        if (this.f931f.mo2267b() != null) {
            this.f926a = this.f931f.mo2267b().mo2302k();
        }
    }

    public boolean m1357f() {
        return this.f930e;
    }

    public void m1358g() {
        this.f930e = false;
        this.f934i = null;
    }
}
