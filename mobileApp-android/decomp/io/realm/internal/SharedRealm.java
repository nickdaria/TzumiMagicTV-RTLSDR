package io.realm.internal;

import io.realm.C0715n;
import io.realm.RealmSchema;
import io.realm.internal.async.BadVersionException;
import java.io.Closeable;
import java.io.File;

public final class SharedRealm implements Closeable {
    private static volatile File f839d;
    public final RealmNotifier f840a;
    public final C0701i f841b;
    final C0696c f842c = new C0696c();
    private long f843e;
    private C0715n f844f;
    private long f845g;
    private final C0649c f846h;

    public interface C0649c {
        void mo2244a(long j);
    }

    public enum C0670a {
        FULL(0),
        MEM_ONLY(1);
        
        final int f829c;

        private C0670a(int i) {
            this.f829c = i;
        }
    }

    public enum C0671b {
        SCHEMA_MODE_AUTOMATIC((byte) 0),
        SCHEMA_MODE_READONLY((byte) 1),
        SCHEMA_MODE_RESET_FILE((byte) 2),
        SCHEMA_MODE_ADDITIVE((byte) 3),
        SCHEMA_MODE_MANUAL((byte) 4);
        
        final byte f836f;

        private C0671b(byte b) {
            this.f836f = b;
        }

        public byte m1138a() {
            return this.f836f;
        }
    }

    public static class C0672d implements Comparable<C0672d> {
        public final long f837a;
        public final long f838b;

        C0672d(long j, long j2) {
            this.f837a = j;
            this.f838b = j2;
        }

        public int m1139a(C0672d c0672d) {
            if (c0672d != null) {
                return this.f837a > c0672d.f837a ? 1 : this.f837a < c0672d.f837a ? -1 : 0;
            } else {
                throw new IllegalArgumentException("Version cannot be compared to a null value.");
            }
        }

        public /* synthetic */ int compareTo(Object obj) {
            return m1139a((C0672d) obj);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            C0672d c0672d = (C0672d) obj;
            return this.f837a == c0672d.f837a && this.f838b == c0672d.f838b;
        }

        public int hashCode() {
            return (((super.hashCode() * 31) + ((int) (this.f837a ^ (this.f837a >>> 32)))) * 31) + ((int) (this.f838b ^ (this.f838b >>> 32)));
        }

        public String toString() {
            return "VersionID{version=" + this.f837a + ", index=" + this.f838b + '}';
        }
    }

    private SharedRealm(long j, C0715n c0715n, RealmNotifier realmNotifier, C0649c c0649c) {
        this.f843e = j;
        this.f844f = c0715n;
        this.f840a = realmNotifier;
        this.f846h = c0649c;
        this.f845g = c0649c == null ? -1 : m1154f();
        this.f841b = null;
    }

    public static SharedRealm m1140a(C0715n c0715n) {
        return m1141a(c0715n, null, null);
    }

    public static SharedRealm m1141a(C0715n c0715n, RealmNotifier realmNotifier, C0649c c0649c) {
        String[] c = C0701i.m1318a().m1324c(c0715n);
        String str = c[0];
        String str2 = c[1];
        String l = c0715n.m1435l();
        SharedRealm c2 = c0715n.m1426c();
        long nativeCreateConfig = nativeCreateConfig(l, c2, str != null ? C0671b.SCHEMA_MODE_ADDITIVE.m1138a() : C0671b.SCHEMA_MODE_MANUAL.m1138a(), c0715n.m1430g() == C0670a.MEM_ONLY, false, false, true, str, str2);
        try {
            c2 = new SharedRealm(nativeGetSharedRealm(nativeCreateConfig, realmNotifier), c0715n, realmNotifier, c0649c);
            return c2;
        } finally {
            nativeCloseConfig(nativeCreateConfig);
        }
    }

    public static void m1142a(File file) {
        if (f839d == null) {
            if (file == null) {
                throw new IllegalArgumentException("'tempDirectory' must not be null.");
            }
            String absolutePath = file.getAbsolutePath();
            if (file.isDirectory() || file.mkdirs() || file.isDirectory()) {
                if (!absolutePath.endsWith("/")) {
                    absolutePath = absolutePath + "/";
                }
                nativeInit(absolutePath);
                f839d = file;
                return;
            }
            throw new C0697d("failed to create temporary directory: " + absolutePath);
        }
    }

    private static native void nativeBeginTransaction(long j);

    private static native void nativeCancelTransaction(long j);

    private static native void nativeCloseConfig(long j);

    private static native void nativeCloseSharedRealm(long j);

    private static native void nativeCommitTransaction(long j);

    private static native long nativeCreateConfig(String str, byte[] bArr, byte b, boolean z, boolean z2, boolean z3, boolean z4, String str2, String str3);

    private static native long nativeGetSharedRealm(long j, RealmNotifier realmNotifier);

    private static native long nativeGetSnapshotVersion(long j);

    private static native long nativeGetTable(long j, String str);

    private static native String nativeGetTableName(long j, int i);

    private static native long nativeGetVersion(long j);

    private static native long[] nativeGetVersionID(long j);

    private static native boolean nativeHasTable(long j, String str);

    private static native void nativeInit(String str);

    private static native boolean nativeIsClosed(long j);

    private static native boolean nativeIsInTransaction(long j);

    private static native long nativeReadGroup(long j);

    private static native void nativeRefresh(long j);

    private static native void nativeRefresh(long j, long j2, long j3);

    private static native void nativeSetVersion(long j, long j2);

    private static native long nativeSize(long j);

    private static native void nativeUpdateSchema(long j, long j2, long j3);

    long m1143a() {
        return this.f843e;
    }

    public String m1144a(int i) {
        return nativeGetTableName(this.f843e, i);
    }

    public void m1145a(long j) {
        nativeSetVersion(this.f843e, j);
    }

    public void m1146a(RealmSchema realmSchema, long j) {
        nativeUpdateSchema(this.f843e, realmSchema.m970a(), j);
    }

    public void m1147a(C0672d c0672d) throws BadVersionException {
        nativeRefresh(this.f843e, c0672d.f837a, c0672d.f838b);
        m1162n();
    }

    public boolean m1148a(String str) {
        return nativeHasTable(this.f843e, str);
    }

    public Table m1149b(String str) {
        return new Table(this, nativeGetTable(this.f843e, str));
    }

    public void m1150b() {
        nativeBeginTransaction(this.f843e);
        m1162n();
    }

    public void m1151c() {
        nativeCommitTransaction(this.f843e);
    }

    public void close() {
        if (this.f840a != null) {
            this.f840a.close();
        }
        synchronized (this.f842c) {
            if (this.f843e != 0) {
                nativeCloseSharedRealm(this.f843e);
                this.f843e = 0;
            }
        }
    }

    public void m1152d() {
        nativeCancelTransaction(this.f843e);
    }

    public boolean m1153e() {
        return nativeIsInTransaction(this.f843e);
    }

    public long m1154f() {
        return nativeGetVersion(this.f843e);
    }

    protected void finalize() throws Throwable {
        synchronized (this.f842c) {
            close();
        }
        super.finalize();
    }

    long m1155g() {
        return nativeReadGroup(this.f843e);
    }

    public long m1156h() {
        return nativeSize(this.f843e);
    }

    public String m1157i() {
        return this.f844f.m1435l();
    }

    public void m1158j() {
        nativeRefresh(this.f843e);
        m1162n();
    }

    public C0672d m1159k() {
        long[] nativeGetVersionID = nativeGetVersionID(this.f843e);
        return new C0672d(nativeGetVersionID[0], nativeGetVersionID[1]);
    }

    public long m1160l() {
        return nativeGetSnapshotVersion(this.f843e);
    }

    public boolean m1161m() {
        return this.f843e == 0 || nativeIsClosed(this.f843e);
    }

    public void m1162n() {
        if (this.f846h != null) {
            long j = this.f845g;
            long f = m1154f();
            if (f != j) {
                this.f845g = f;
                this.f846h.mo2244a(f);
            }
        }
    }
}
