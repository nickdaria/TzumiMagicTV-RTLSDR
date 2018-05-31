package io.realm.internal;

import io.realm.C0724u;

public class TableView implements C0674n {
    protected long f859a;
    protected final Table f860b;
    private final TableQuery f861c;
    private long f862d;
    private final C0696c f863e;

    protected TableView(C0696c c0696c, Table table, long j) {
        this.f863e = c0696c;
        this.f860b = table;
        this.f859a = j;
        this.f861c = null;
    }

    protected TableView(C0696c c0696c, Table table, long j, TableQuery tableQuery) {
        this.f863e = c0696c;
        this.f860b = table;
        this.f859a = j;
        this.f861c = tableQuery;
    }

    private void m1229d() {
        throw new IllegalStateException("Realm data can only be changed inside a write transaction.");
    }

    private native void nativeClear(long j);

    static native void nativeClose(long j);

    private native long nativeFindBySourceNdx(long j, long j2);

    private native long nativeGetColumnCount(long j);

    private native String nativeGetColumnName(long j, long j2);

    private native long nativeGetSourceRowIndex(long j, long j2);

    private native long nativeSize(long j);

    private native void nativeSort(long j, long j2, boolean z);

    private native long nativeSyncIfNeeded(long j);

    private native long nativeWhere(long j);

    public long mo2298a() {
        return nativeSize(this.f859a);
    }

    public long m1231a(long j) {
        return nativeGetSourceRowIndex(this.f859a, j);
    }

    public void m1232a(long j, C0724u c0724u) {
        nativeSort(this.f859a, j, c0724u.m1492a());
    }

    public String m1233b(long j) {
        return nativeGetColumnName(this.f859a, j);
    }

    public void mo2299b() {
        if (this.f860b.m1205f()) {
            m1229d();
        }
        nativeClear(this.f859a);
    }

    public long m1235c() {
        return nativeGetColumnCount(this.f859a);
    }

    protected void finalize() {
        synchronized (this.f863e) {
            if (this.f859a != 0) {
                this.f863e.m1292a(this.f859a);
                this.f859a = 0;
            }
        }
    }

    public TableQuery mo2300h() {
        this.f863e.m1290a();
        long nativeWhere = nativeWhere(this.f859a);
        try {
            return new TableQuery(this.f863e, this.f860b, nativeWhere, this);
        } catch (RuntimeException e) {
            TableQuery.nativeClose(nativeWhere);
            throw e;
        }
    }

    public long mo2301j() {
        this.f862d = nativeSyncIfNeeded(this.f859a);
        return this.f862d;
    }

    public long mo2302k() {
        return this.f862d;
    }

    public long mo2303m(long j) {
        return nativeFindBySourceNdx(this.f859a, j);
    }

    public String toString() {
        long c = m1235c();
        StringBuilder stringBuilder = new StringBuilder("The TableView contains ");
        stringBuilder.append(c);
        stringBuilder.append(" columns: ");
        for (int i = 0; ((long) i) < c; i++) {
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(m1233b((long) i));
        }
        stringBuilder.append(".");
        stringBuilder.append(" And ");
        stringBuilder.append(mo2298a());
        stringBuilder.append(" rows.");
        return stringBuilder.toString();
    }
}
