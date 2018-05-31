package io.realm.internal;

import io.realm.C0658c;
import io.realm.C0724u;
import io.realm.internal.async.BadVersionException;
import java.io.Closeable;

public class TableQuery implements Closeable {
    protected boolean f853a = false;
    protected long f854b;
    protected final Table f855c;
    private final C0674n f856d;
    private final C0696c f857e;
    private boolean f858f = true;

    public TableQuery(C0696c c0696c, Table table, long j) {
        if (this.f853a) {
            System.err.println("++++++ new TableQuery, ptr= " + j);
        }
        this.f857e = c0696c;
        this.f855c = table;
        this.f854b = j;
        this.f856d = null;
    }

    public TableQuery(C0696c c0696c, Table table, long j, C0674n c0674n) {
        if (this.f853a) {
            System.err.println("++++++ new TableQuery, ptr= " + j);
        }
        this.f857e = c0696c;
        this.f855c = table;
        this.f854b = j;
        this.f856d = c0674n;
    }

    public static long m1219a(SharedRealm sharedRealm, long j) {
        return nativeFindWithHandover(sharedRealm.m1143a(), j, 0);
    }

    public static long[] m1220a(SharedRealm sharedRealm, long[] jArr, long[][] jArr2, long[][] jArr3, boolean[][] zArr) throws BadVersionException {
        return nativeBatchUpdateQueries(sharedRealm.m1143a(), jArr, jArr2, jArr3, zArr);
    }

    public static boolean[] m1221a(C0724u[] c0724uArr) {
        boolean[] zArr = new boolean[c0724uArr.length];
        for (int i = 0; i < c0724uArr.length; i++) {
            zArr[i] = c0724uArr[i].m1492a();
        }
        return zArr;
    }

    public static long m1222b(long j, SharedRealm sharedRealm) {
        return nativeImportHandoverRowIntoSharedGroup(j, sharedRealm.m1143a());
    }

    private void m1223c() {
        if (!this.f858f) {
            String nativeValidateQuery = nativeValidateQuery(this.f854b);
            if (nativeValidateQuery.equals("")) {
                this.f858f = true;
                return;
            }
            throw new UnsupportedOperationException(nativeValidateQuery);
        }
    }

    private static native long[] nativeBatchUpdateQueries(long j, long[] jArr, long[][] jArr2, long[][] jArr3, boolean[][] zArr) throws BadVersionException;

    protected static native void nativeClose(long j);

    public static native void nativeCloseQueryHandover(long j);

    private native void nativeEqual(long j, long[] jArr, String str, boolean z);

    private native long nativeFind(long j, long j2);

    private native long nativeFindAll(long j, long j2, long j3, long j4);

    private static native long nativeFindWithHandover(long j, long j2, long j3);

    private native long nativeHandoverQuery(long j, long j2);

    private static native long nativeImportHandoverRowIntoSharedGroup(long j, long j2);

    private native long nativeImportHandoverTableViewIntoSharedGroup(long j, long j2) throws BadVersionException;

    private native String nativeValidateQuery(long j);

    public long m1224a() {
        m1223c();
        return nativeFind(this.f854b, 0);
    }

    public long m1225a(SharedRealm sharedRealm) {
        return nativeHandoverQuery(sharedRealm.m1143a(), this.f854b);
    }

    public TableQuery m1226a(long[] jArr, String str, C0658c c0658c) {
        nativeEqual(this.f854b, jArr, str, c0658c.m1017a());
        this.f858f = false;
        return this;
    }

    public TableView m1227a(long j, SharedRealm sharedRealm) throws BadVersionException {
        long nativeImportHandoverTableViewIntoSharedGroup = nativeImportHandoverTableViewIntoSharedGroup(j, sharedRealm.m1143a());
        try {
            return new TableView(this.f857e, this.f855c, nativeImportHandoverTableViewIntoSharedGroup);
        } catch (RuntimeException e) {
            if (nativeImportHandoverTableViewIntoSharedGroup != 0) {
                TableView.nativeClose(nativeImportHandoverTableViewIntoSharedGroup);
            }
            throw e;
        }
    }

    public TableView m1228b() {
        m1223c();
        this.f857e.m1290a();
        long nativeFindAll = nativeFindAll(this.f854b, 0, -1, -1);
        try {
            return new TableView(this.f857e, this.f855c, nativeFindAll, this);
        } catch (RuntimeException e) {
            TableView.nativeClose(nativeFindAll);
            throw e;
        }
    }

    public void close() {
        synchronized (this.f857e) {
            if (this.f854b != 0) {
                nativeClose(this.f854b);
                if (this.f853a) {
                    System.err.println("++++ Query CLOSE, ptr= " + this.f854b);
                }
                this.f854b = 0;
            }
        }
    }

    protected void finalize() {
        synchronized (this.f857e) {
            if (this.f854b != 0) {
                this.f857e.m1294b(this.f854b);
                this.f854b = 0;
            }
        }
    }
}
