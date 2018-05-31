package io.realm;

import android.content.Context;
import android.text.TextUtils;
import io.realm.C0708k.C0705a;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmFileException;
import io.realm.exceptions.RealmFileException.Kind;
import io.realm.internal.C0640l;
import io.realm.internal.C0702j;
import io.realm.internal.SharedRealm.C0670a;
import io.realm.internal.p019a.C0675a;
import io.realm.internal.p019a.C0676b;
import io.realm.p018a.C0646b;
import io.realm.p018a.C0647a;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class C0715n {
    private static final Object f961a = C0708k.m1373m();
    private static final C0640l f962b;
    private static Boolean f963c;
    private final File f964d;
    private final String f965e;
    private final String f966f;
    private final String f967g;
    private final byte[] f968h;
    private final long f969i;
    private final C0541p f970j;
    private final boolean f971k;
    private final C0670a f972l;
    private final C0640l f973m;
    private final C0646b f974n;
    private final C0705a f975o;

    public static class C0714a {
        private File f949a;
        private String f950b;
        private String f951c;
        private byte[] f952d;
        private long f953e;
        private C0541p f954f;
        private boolean f955g;
        private C0670a f956h;
        private HashSet<Object> f957i;
        private HashSet<Class<? extends C0497q>> f958j;
        private C0646b f959k;
        private C0705a f960l;

        public C0714a() {
            this(C0657b.f779a);
        }

        C0714a(Context context) {
            this.f957i = new HashSet();
            this.f958j = new HashSet();
            if (context == null) {
                throw new IllegalStateException("Call `Realm.init(Context)` before creating a RealmConfiguration");
            }
            C0702j.m1325a(context);
            m1415a(context);
        }

        private void m1415a(Context context) {
            this.f949a = context.getFilesDir();
            this.f950b = "default.realm";
            this.f952d = null;
            this.f953e = 0;
            this.f954f = null;
            this.f955g = false;
            this.f956h = C0670a.FULL;
            if (C0715n.f961a != null) {
                this.f957i.add(C0715n.f961a);
            }
        }

        public C0714a m1416a(long j) {
            if (j < 0) {
                throw new IllegalArgumentException("Realm schema version numbers must be 0 (zero) or higher. Yours was: " + j);
            }
            this.f953e = j;
            return this;
        }

        public C0714a m1417a(C0541p c0541p) {
            if (c0541p == null) {
                throw new IllegalArgumentException("A non-null migration must be provided");
            }
            this.f954f = c0541p;
            return this;
        }

        public C0715n m1418a() {
            if (this.f959k == null && C0715n.m1422m()) {
                this.f959k = new C0647a();
            }
            return new C0715n(this.f949a, this.f950b, C0715n.m1421a(new File(this.f949a, this.f950b)), this.f951c, this.f952d, this.f953e, this.f954f, this.f955g, this.f956h, C0715n.m1420a(this.f957i, this.f958j), this.f959k, this.f960l);
        }
    }

    static {
        if (f961a != null) {
            C0640l a = C0715n.m1419a(f961a.getClass().getCanonicalName());
            if (a.mo2234b()) {
                f962b = a;
                return;
            }
            throw new ExceptionInInitializerError("RealmTransformer doesn't seem to be applied. Please update the project configuration to use the Realm Gradle plugin. See https://realm.io/news/android-installation-change/");
        }
        f962b = null;
    }

    protected C0715n(File file, String str, String str2, String str3, byte[] bArr, long j, C0541p c0541p, boolean z, C0670a c0670a, C0640l c0640l, C0646b c0646b, C0705a c0705a) {
        this.f964d = file;
        this.f965e = str;
        this.f966f = str2;
        this.f967g = str3;
        this.f968h = bArr;
        this.f969i = j;
        this.f970j = c0541p;
        this.f971k = z;
        this.f972l = c0670a;
        this.f973m = c0640l;
        this.f974n = c0646b;
        this.f975o = c0705a;
    }

    private static C0640l m1419a(String str) {
        String[] split = str.split("\\.");
        Object obj = split[split.length - 1];
        String format = String.format("io.realm.%s%s", new Object[]{obj, "Mediator"});
        try {
            Constructor constructor = Class.forName(format).getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return (C0640l) constructor.newInstance(new Object[0]);
        } catch (Throwable e) {
            throw new RealmException("Could not find " + format, e);
        } catch (Throwable e2) {
            throw new RealmException("Could not create an instance of " + format, e2);
        } catch (Throwable e22) {
            throw new RealmException("Could not create an instance of " + format, e22);
        } catch (Throwable e222) {
            throw new RealmException("Could not create an instance of " + format, e222);
        }
    }

    protected static C0640l m1420a(Set<Object> set, Set<Class<? extends C0497q>> set2) {
        if (set2.size() > 0) {
            return new C0676b(f962b, set2);
        }
        if (set.size() == 1) {
            return C0715n.m1419a(set.iterator().next().getClass().getCanonicalName());
        }
        C0640l[] c0640lArr = new C0640l[set.size()];
        int i = 0;
        for (Object obj : set) {
            c0640lArr[i] = C0715n.m1419a(obj.getClass().getCanonicalName());
            i++;
        }
        return new C0675a(c0640lArr);
    }

    protected static String m1421a(File file) {
        try {
            return file.getCanonicalPath();
        } catch (Throwable e) {
            throw new RealmFileException(Kind.ACCESS_ERROR, "Could not resolve the canonical path to the Realm file: " + file.getAbsolutePath(), e);
        }
    }

    static synchronized boolean m1422m() {
        boolean booleanValue;
        synchronized (C0715n.class) {
            if (f963c == null) {
                try {
                    Class.forName("rx.Observable");
                    f963c = Boolean.valueOf(true);
                } catch (ClassNotFoundException e) {
                    f963c = Boolean.valueOf(false);
                }
            }
            booleanValue = f963c.booleanValue();
        }
        return booleanValue;
    }

    public File m1424a() {
        return this.f964d;
    }

    public String m1425b() {
        return this.f965e;
    }

    public byte[] m1426c() {
        return this.f968h == null ? null : Arrays.copyOf(this.f968h, this.f968h.length);
    }

    public long m1427d() {
        return this.f969i;
    }

    public C0541p m1428e() {
        return this.f970j;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C0715n c0715n = (C0715n) obj;
        if (this.f969i != c0715n.f969i || this.f971k != c0715n.f971k || !this.f964d.equals(c0715n.f964d) || !this.f965e.equals(c0715n.f965e) || !this.f966f.equals(c0715n.f966f) || !Arrays.equals(this.f968h, c0715n.f968h) || !this.f972l.equals(c0715n.f972l)) {
            return false;
        }
        if (this.f970j != null) {
            if (!this.f970j.equals(c0715n.f970j)) {
                return false;
            }
        } else if (c0715n.f970j != null) {
            return false;
        }
        if (this.f974n != null) {
            if (!this.f974n.equals(c0715n.f974n)) {
                return false;
            }
        } else if (c0715n.f974n != null) {
            return false;
        }
        if (this.f975o != null) {
            if (!this.f975o.equals(c0715n.f975o)) {
                return false;
            }
        } else if (c0715n.f975o != null) {
            return false;
        }
        return this.f973m.equals(c0715n.f973m);
    }

    public boolean m1429f() {
        return this.f971k;
    }

    public C0670a m1430g() {
        return this.f972l;
    }

    C0640l m1431h() {
        return this.f973m;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.f974n != null ? this.f974n.hashCode() : 0) + (((((((this.f971k ? 1 : 0) + (((this.f970j != null ? this.f970j.hashCode() : 0) + (((((this.f968h != null ? Arrays.hashCode(this.f968h) : 0) + (((((this.f964d.hashCode() * 31) + this.f965e.hashCode()) * 31) + this.f966f.hashCode()) * 31)) * 31) + ((int) this.f969i)) * 31)) * 31)) * 31) + this.f973m.hashCode()) * 31) + this.f972l.hashCode()) * 31)) * 31;
        if (this.f975o != null) {
            i = this.f975o.hashCode();
        }
        return hashCode + i;
    }

    C0705a m1432i() {
        return this.f975o;
    }

    boolean m1433j() {
        return !TextUtils.isEmpty(this.f967g);
    }

    InputStream m1434k() throws IOException {
        return C0657b.f779a.getAssets().open(this.f967g);
    }

    public String m1435l() {
        return this.f966f;
    }

    boolean m1436n() {
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("realmDirectory: ").append(this.f964d.toString());
        stringBuilder.append("\n");
        stringBuilder.append("realmFileName : ").append(this.f965e);
        stringBuilder.append("\n");
        stringBuilder.append("canonicalPath: ").append(this.f966f);
        stringBuilder.append("\n");
        stringBuilder.append("key: ").append("[length: ").append(this.f968h == null ? 0 : 64).append("]");
        stringBuilder.append("\n");
        stringBuilder.append("schemaVersion: ").append(Long.toString(this.f969i));
        stringBuilder.append("\n");
        stringBuilder.append("migration: ").append(this.f970j);
        stringBuilder.append("\n");
        stringBuilder.append("deleteRealmIfMigrationNeeded: ").append(this.f971k);
        stringBuilder.append("\n");
        stringBuilder.append("durability: ").append(this.f972l);
        stringBuilder.append("\n");
        stringBuilder.append("schemaMediator: ").append(this.f973m);
        return stringBuilder.toString();
    }
}
