package io.realm.internal;

import io.realm.RealmFieldType;

public class CheckedRow extends UncheckedRow {
    private CheckedRow(C0696c c0696c, Table table, long j) {
        super(c0696c, table, j);
    }

    public static CheckedRow m1123a(C0696c c0696c, Table table, long j) {
        C0668g checkedRow = new CheckedRow(c0696c, table, table.nativeGetRowPtr(table.f849b, j));
        c0696c.m1291a(1, checkedRow);
        return checkedRow;
    }

    public boolean mo2266a(long j) {
        RealmFieldType e = mo2273e(j);
        return (e == RealmFieldType.OBJECT || e == RealmFieldType.LIST) ? super.mo2266a(j) : false;
    }

    public boolean mo2268b(long j) {
        return super.mo2268b(j);
    }

    public void mo2270c(long j) {
        if (mo2273e(j) == RealmFieldType.BINARY) {
            super.m1106a(j, null);
        } else {
            super.mo2270c(j);
        }
    }

    protected native boolean nativeGetBoolean(long j, long j2);

    protected native byte[] nativeGetByteArray(long j, long j2);

    protected native long nativeGetColumnCount(long j);

    protected native long nativeGetColumnIndex(long j, String str);

    protected native String nativeGetColumnName(long j, long j2);

    protected native int nativeGetColumnType(long j, long j2);

    protected native double nativeGetDouble(long j, long j2);

    protected native float nativeGetFloat(long j, long j2);

    protected native long nativeGetLinkView(long j, long j2);

    protected native long nativeGetLong(long j, long j2);

    protected native String nativeGetString(long j, long j2);

    protected native long nativeGetTimestamp(long j, long j2);

    protected native boolean nativeIsNullLink(long j, long j2);

    protected native void nativeSetBoolean(long j, long j2, boolean z);

    protected native void nativeSetByteArray(long j, long j2, byte[] bArr);

    protected native void nativeSetString(long j, long j2, String str);
}
