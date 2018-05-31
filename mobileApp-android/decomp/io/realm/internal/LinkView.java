package io.realm.internal;

public class LinkView extends C0668g {
    final Table f823a;
    final long f824b;
    private final C0696c f825d;

    public LinkView(C0696c c0696c, Table table, long j, long j2) {
        this.f825d = c0696c;
        this.f823a = table;
        this.f824b = j;
        this.c = j2;
        c0696c.m1290a();
        c0696c.m1291a(0, (C0668g) this);
    }

    private void m1127e() {
        if (this.f823a.m1205f()) {
            throw new IllegalStateException("Changing Realm data can only be done from inside a write transaction.");
        }
    }

    public static native void nativeAdd(long j, long j2);

    public static native void nativeClear(long j);

    public static native void nativeClose(long j);

    private native long nativeFind(long j, long j2);

    private native long nativeGetTargetRowIndex(long j, long j2);

    private native long nativeGetTargetTable(long j);

    private native void nativeInsert(long j, long j2, long j3);

    private native boolean nativeIsAttached(long j);

    private native void nativeRemove(long j, long j2);

    private native void nativeSet(long j, long j2, long j3);

    private native long nativeSize(long j);

    public long m1128a(long j) {
        return nativeGetTargetRowIndex(this.c, j);
    }

    public void m1129a() {
        m1127e();
        nativeClear(this.c);
    }

    public void m1130a(long j, long j2) {
        m1127e();
        nativeInsert(this.c, j, j2);
    }

    public long m1131b() {
        return nativeSize(this.c);
    }

    public void m1132b(long j) {
        m1127e();
        nativeAdd(this.c, j);
    }

    public void m1133b(long j, long j2) {
        m1127e();
        nativeSet(this.c, j, j2);
    }

    public void m1134c(long j) {
        m1127e();
        nativeRemove(this.c, j);
    }

    public boolean m1135c() {
        return nativeIsAttached(this.c);
    }

    public Table m1136d() {
        this.f825d.m1290a();
        long nativeGetTargetTable = nativeGetTargetTable(this.c);
        try {
            return new Table(this.f823a, nativeGetTargetTable);
        } catch (RuntimeException e) {
            Table.nativeClose(nativeGetTargetTable);
            throw e;
        }
    }

    public boolean m1137d(long j) {
        return nativeFind(this.c, j) != -1;
    }
}
