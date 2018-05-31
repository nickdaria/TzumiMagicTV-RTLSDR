package io.realm;

import io.realm.internal.C0674n;
import io.realm.internal.LinkView;
import io.realm.internal.TableQuery;
import io.realm.internal.async.C0678a;

public final class C0720s<E extends C0497q> {
    private static final Long f987h = Long.valueOf(0);
    private C0657b f988a;
    private Class<E> f989b;
    private String f990c;
    private C0674n f991d;
    private RealmObjectSchema f992e;
    private LinkView f993f;
    private TableQuery f994g;
    private C0678a f995i;

    private C0720s(C0708k c0708k, Class<E> cls) {
        this.f988a = c0708k;
        this.f989b = cls;
        this.f992e = c0708k.f.m976c((Class) cls);
        this.f991d = this.f992e.f749a;
        this.f993f = null;
        this.f994g = this.f991d.mo2300h();
    }

    private C0720s(C0723t<E> c0723t, Class<E> cls) {
        this.f988a = c0723t.f1000a;
        this.f989b = cls;
        this.f992e = this.f988a.f785f.m976c((Class) cls);
        this.f991d = c0723t.m1478a();
        this.f993f = null;
        this.f994g = this.f991d.mo2300h();
    }

    private C0720s(C0723t<C0665g> c0723t, String str) {
        this.f988a = c0723t.f1000a;
        this.f990c = str;
        this.f992e = this.f988a.f785f.m980e(str);
        this.f991d = this.f992e.f749a;
        this.f994g = c0723t.m1478a().mo2300h();
    }

    private long m1456a(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Non-empty fieldname required.");
        } else if (str.contains(".")) {
            throw new IllegalArgumentException("Sorting using child object fields is not supported: " + str);
        } else {
            Long c = this.f992e.m965c(str);
            if (c != null) {
                return c.longValue();
            }
            throw new IllegalArgumentException(String.format("Field name '%s' does not exist.", new Object[]{str}));
        }
    }

    public static <E extends C0497q> C0720s<E> m1457a(C0708k c0708k, Class<E> cls) {
        return new C0720s(c0708k, (Class) cls);
    }

    public static <E extends C0497q> C0720s<E> m1458a(C0723t<E> c0723t) {
        return c0723t.f1001b != null ? new C0720s((C0723t) c0723t, c0723t.f1001b) : new C0720s((C0723t) c0723t, c0723t.f1002c);
    }

    private boolean m1459e() {
        return this.f990c != null;
    }

    private void m1460f() {
        if (this.f995i != null) {
            throw new IllegalStateException("This RealmQuery is already used by a find* query, please create a new query");
        }
    }

    private long m1461g() {
        return this.f994g.m1224a();
    }

    public C0720s<E> m1462a(String str, String str2) {
        return m1463a(str, str2, C0658c.SENSITIVE);
    }

    public C0720s<E> m1463a(String str, String str2, C0658c c0658c) {
        this.f994g.m1226a(this.f992e.m962a(str, RealmFieldType.STRING), str2, c0658c);
        return this;
    }

    public C0723t<E> m1464a() {
        m1460f();
        return m1459e() ? C0723t.m1476a(this.f988a, this.f994g.m1228b(), this.f990c) : C0723t.m1475a(this.f988a, this.f994g.m1228b(), this.f989b);
    }

    public C0723t<E> m1465a(String str, C0724u c0724u) {
        m1460f();
        C0674n b = this.f994g.m1228b();
        b.m1232a(m1456a(str), c0724u);
        return m1459e() ? C0723t.m1476a(this.f988a, b, this.f990c) : C0723t.m1475a(this.f988a, b, this.f989b);
    }

    public E m1466b() {
        m1460f();
        long g = m1461g();
        return g >= 0 ? this.f988a.m1002a(this.f989b, this.f990c, g) : null;
    }

    public C0678a m1467c() {
        return this.f995i;
    }

    long m1468d() {
        return this.f994g.m1225a(this.f988a.f784e);
    }
}
