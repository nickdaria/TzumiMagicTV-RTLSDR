package io.realm.internal;

import io.realm.RealmFieldType;
import java.util.Date;

public class UncheckedRow extends C0668g implements C0669m {
    final C0696c f821a;
    final Table f822d;

    protected UncheckedRow(C0696c c0696c, Table table, long j) {
        this.f821a = c0696c;
        this.f822d = table;
        this.c = j;
        c0696c.m1290a();
    }

    public static UncheckedRow m1100b(C0696c c0696c, Table table, long j) {
        C0668g uncheckedRow = new UncheckedRow(c0696c, table, table.nativeGetRowPtr(table.f849b, j));
        c0696c.m1291a(1, uncheckedRow);
        return uncheckedRow;
    }

    public static UncheckedRow m1101c(C0696c c0696c, Table table, long j) {
        C0668g uncheckedRow = new UncheckedRow(c0696c, table, j);
        c0696c.m1291a(1, uncheckedRow);
        return uncheckedRow;
    }

    static native void nativeClose(long j);

    public long mo2262a() {
        return nativeGetColumnCount(this.c);
    }

    public long mo2263a(String str) {
        if (str != null) {
            return nativeGetColumnIndex(this.c, str);
        }
        throw new IllegalArgumentException("Column name can not be null.");
    }

    public void mo2264a(long j, String str) {
        this.f822d.m1207g();
        if (str == null) {
            mo2267b().m1189a(j, mo2269c());
            nativeSetNull(this.c, j);
            return;
        }
        mo2267b().m1190a(j, mo2269c(), str);
        nativeSetString(this.c, j, str);
    }

    public void mo2265a(long j, boolean z) {
        this.f822d.m1207g();
        nativeSetBoolean(this.c, j, z);
    }

    public void m1106a(long j, byte[] bArr) {
        this.f822d.m1207g();
        nativeSetByteArray(this.c, j, bArr);
    }

    public boolean mo2266a(long j) {
        return nativeIsNullLink(this.c, j);
    }

    public Table mo2267b() {
        return this.f822d;
    }

    public boolean mo2268b(long j) {
        return nativeIsNull(this.c, j);
    }

    public long mo2269c() {
        return nativeGetIndex(this.c);
    }

    public void mo2270c(long j) {
        this.f822d.m1207g();
        mo2267b().m1189a(j, mo2269c());
        nativeSetNull(this.c, j);
    }

    public String mo2271d(long j) {
        return nativeGetColumnName(this.c, j);
    }

    public boolean mo2272d() {
        return this.c != 0 && nativeIsAttached(this.c);
    }

    public RealmFieldType mo2273e(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.c, j));
    }

    public long mo2274f(long j) {
        return nativeGetLong(this.c, j);
    }

    public boolean mo2275g(long j) {
        return nativeGetBoolean(this.c, j);
    }

    public float mo2276h(long j) {
        return nativeGetFloat(this.c, j);
    }

    public double mo2277i(long j) {
        return nativeGetDouble(this.c, j);
    }

    public Date mo2278j(long j) {
        return new Date(nativeGetTimestamp(this.c, j));
    }

    public String mo2279k(long j) {
        return nativeGetString(this.c, j);
    }

    public byte[] mo2280l(long j) {
        return nativeGetByteArray(this.c, j);
    }

    public LinkView mo2281m(long j) {
        return new LinkView(this.f821a, this.f822d, j, nativeGetLinkView(this.c, j));
    }

    protected native boolean nativeGetBoolean(long j, long j2);

    protected native byte[] nativeGetByteArray(long j, long j2);

    protected native long nativeGetColumnCount(long j);

    protected native long nativeGetColumnIndex(long j, String str);

    protected native String nativeGetColumnName(long j, long j2);

    protected native int nativeGetColumnType(long j, long j2);

    protected native double nativeGetDouble(long j, long j2);

    protected native float nativeGetFloat(long j, long j2);

    protected native long nativeGetIndex(long j);

    protected native long nativeGetLinkView(long j, long j2);

    protected native long nativeGetLong(long j, long j2);

    protected native String nativeGetString(long j, long j2);

    protected native long nativeGetTimestamp(long j, long j2);

    protected native boolean nativeIsAttached(long j);

    protected native boolean nativeIsNull(long j, long j2);

    protected native boolean nativeIsNullLink(long j, long j2);

    protected native void nativeSetBoolean(long j, long j2, boolean z);

    protected native void nativeSetByteArray(long j, long j2, byte[] bArr);

    protected native void nativeSetNull(long j, long j2);

    protected native void nativeSetString(long j, long j2, String str);
}
