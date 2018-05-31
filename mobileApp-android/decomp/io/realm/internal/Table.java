package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class Table implements C0674n {
    public static final String f848a = Util.m1241a();
    long f849b;
    private final C0696c f850c;
    private final SharedRealm f851d;
    private long f852e;

    public Table() {
        this.f852e = -1;
        this.f850c = new C0696c();
        this.f849b = createNative();
        if (this.f849b == 0) {
            throw new OutOfMemoryError("Out of native memory.");
        }
        this.f851d = null;
    }

    Table(SharedRealm sharedRealm, long j) {
        this.f852e = -1;
        this.f850c = sharedRealm.f842c;
        this.f851d = sharedRealm;
        this.f849b = j;
    }

    Table(Table table, long j) {
        this(table.f851d, j);
    }

    public static boolean m1169a(SharedRealm sharedRealm) {
        if (sharedRealm == null || !sharedRealm.m1153e()) {
            m1178o();
        }
        if (!sharedRealm.m1148a("pk")) {
            return false;
        }
        return nativeMigratePrimaryKeyTableIfNeeded(sharedRealm.m1155g(), sharedRealm.m1149b("pk").f849b);
    }

    public static void m1170b(Object obj) {
        throw new RealmPrimaryKeyConstraintException("Value already exists: " + obj);
    }

    public static boolean m1171b(SharedRealm sharedRealm) {
        return !sharedRealm.m1148a("pk") ? false : nativePrimaryKeyTableNeedsMigration(sharedRealm.m1149b("pk").f849b);
    }

    public static boolean m1172c(String str) {
        return str.startsWith(f848a);
    }

    public static String m1173d(String str) {
        return !str.startsWith(f848a) ? str : str.substring(f848a.length());
    }

    private void m1174e(String str) {
        if (str.length() > 63) {
            throw new IllegalArgumentException("Column names are currently limited to max 63 characters.");
        }
    }

    private Table m1175l() {
        if (this.f851d == null) {
            return null;
        }
        Table b = this.f851d.m1149b("pk");
        if (b.m1198c() != 0) {
            return b;
        }
        m1207g();
        b.m1213j(b.m1183a(RealmFieldType.STRING, "pk_table"));
        b.m1183a(RealmFieldType.STRING, "pk_property");
        return b;
    }

    private void m1176m() {
        this.f852e = -1;
    }

    private void m1177n() {
        if (!m1203e()) {
            throw new IllegalStateException(m1211i() + " has no primary key defined");
        }
    }

    private native long nativeAddColumn(long j, int i, String str, boolean z);

    public static native long nativeAddEmptyRow(long j, long j2);

    private native void nativeAddSearchIndex(long j, long j2);

    private native void nativeClear(long j);

    static native void nativeClose(long j);

    public static native long nativeFindFirstInt(long j, long j2, long j3);

    public static native long nativeFindFirstNull(long j, long j2);

    public static native long nativeFindFirstString(long j, long j2, String str);

    private native long nativeGetColumnCount(long j);

    private native long nativeGetColumnIndex(long j, String str);

    private native String nativeGetColumnName(long j, long j2);

    private native int nativeGetColumnType(long j, long j2);

    private native long nativeGetLinkTarget(long j, long j2);

    private native String nativeGetName(long j);

    private native boolean nativeHasSearchIndex(long j, long j2);

    private native boolean nativeIsColumnNullable(long j, long j2);

    private static native boolean nativeMigratePrimaryKeyTableIfNeeded(long j, long j2);

    private native void nativeMoveLastOver(long j, long j2);

    private static native boolean nativePrimaryKeyTableNeedsMigration(long j);

    private native void nativeRemoveColumn(long j, long j2);

    private native void nativeRemoveSearchIndex(long j, long j2);

    public static native void nativeSetBoolean(long j, long j2, long j3, boolean z, boolean z2);

    public static native void nativeSetLongUnique(long j, long j2, long j3, long j4);

    public static native void nativeSetNull(long j, long j2, long j3, boolean z);

    public static native void nativeSetNullUnique(long j, long j2, long j3);

    private native long nativeSetPrimaryKey(long j, long j2, String str);

    public static native void nativeSetString(long j, long j2, long j3, String str, boolean z);

    public static native void nativeSetStringUnique(long j, long j2, long j3, String str);

    private native long nativeSize(long j);

    private native long nativeVersion(long j);

    private native long nativeWhere(long j);

    private static void m1178o() {
        throw new IllegalStateException("Changing Realm data can only be done from inside a transaction.");
    }

    private boolean m1179o(long j) {
        return j == m1200d();
    }

    private boolean m1180p(long j) {
        return j >= 0 && j == m1200d();
    }

    public long mo2298a() {
        return nativeSize(this.f849b);
    }

    public long m1182a(long j, String str) {
        if (str != null) {
            return nativeFindFirstString(this.f849b, j, str);
        }
        throw new IllegalArgumentException("null is not supported");
    }

    public long m1183a(RealmFieldType realmFieldType, String str) {
        return m1184a(realmFieldType, str, false);
    }

    public long m1184a(RealmFieldType realmFieldType, String str, boolean z) {
        m1174e(str);
        return nativeAddColumn(this.f849b, realmFieldType.getNativeValue(), str, z);
    }

    public long m1185a(Object obj) {
        return m1186a(obj, true);
    }

    public long m1186a(Object obj, boolean z) {
        if (z) {
            m1207g();
            m1177n();
        }
        long d = m1200d();
        RealmFieldType d2 = m1201d(d);
        long nativeAddEmptyRow;
        if (obj == null) {
            switch (d2) {
                case STRING:
                case INTEGER:
                    if (z && m1218n(d) != -1) {
                        m1170b((Object) "null");
                    }
                    nativeAddEmptyRow = nativeAddEmptyRow(this.f849b, 1);
                    if (d2 == RealmFieldType.STRING) {
                        nativeSetStringUnique(this.f849b, d, nativeAddEmptyRow, null);
                        return nativeAddEmptyRow;
                    }
                    nativeSetNullUnique(this.f849b, d, nativeAddEmptyRow);
                    return nativeAddEmptyRow;
                default:
                    throw new RealmException("Cannot check for duplicate rows for unsupported primary key type: " + d2);
            }
        }
        switch (d2) {
            case STRING:
                if (obj instanceof String) {
                    if (z && m1182a(d, (String) obj) != -1) {
                        m1170b(obj);
                    }
                    nativeAddEmptyRow = nativeAddEmptyRow(this.f849b, 1);
                    nativeSetStringUnique(this.f849b, d, nativeAddEmptyRow, (String) obj);
                    return nativeAddEmptyRow;
                }
                throw new IllegalArgumentException("Primary key value is not a String: " + obj);
            case INTEGER:
                try {
                    long parseLong = Long.parseLong(obj.toString());
                    if (z && m1194b(d, parseLong) != -1) {
                        m1170b(Long.valueOf(parseLong));
                    }
                    nativeAddEmptyRow = nativeAddEmptyRow(this.f849b, 1);
                    nativeSetLongUnique(this.f849b, d, nativeAddEmptyRow, parseLong);
                    return nativeAddEmptyRow;
                } catch (RuntimeException e) {
                    throw new IllegalArgumentException("Primary key value is not a long: " + obj);
                }
            default:
                throw new RealmException("Cannot check for duplicate rows for unsupported primary key type: " + d2);
        }
    }

    public long m1187a(String str) {
        if (str != null) {
            return nativeGetColumnIndex(this.f849b, str);
        }
        throw new IllegalArgumentException("Column name can not be null.");
    }

    public void m1188a(long j) {
        long d = m1200d();
        nativeRemoveColumn(this.f849b, j);
        if (d < 0) {
            return;
        }
        if (d == j) {
            m1196b(null);
        } else if (d > j) {
            m1176m();
        }
    }

    void m1189a(long j, long j2) {
        if (m1179o(j)) {
            switch (m1201d(j)) {
                case STRING:
                case INTEGER:
                    long n = m1218n(j);
                    if (n != j2 && n != -1) {
                        m1170b((Object) "null");
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    void m1190a(long j, long j2, String str) {
        if (m1180p(j)) {
            long a = m1182a(j, str);
            if (a != j2 && a != -1) {
                m1170b((Object) str);
            }
        }
    }

    public void m1191a(long j, long j2, String str, boolean z) {
        m1207g();
        if (str == null) {
            m1189a(j, j2);
            nativeSetNull(this.f849b, j, j2, z);
            return;
        }
        m1190a(j, j2, str);
        nativeSetString(this.f849b, j, j2, str, z);
    }

    public void m1192a(long j, long j2, boolean z) {
        m1207g();
        m1189a(j, j2);
        nativeSetNull(this.f849b, j, j2, z);
    }

    public void m1193a(long j, long j2, boolean z, boolean z2) {
        m1207g();
        nativeSetBoolean(this.f849b, j, j2, z, z2);
    }

    public long m1194b(long j, long j2) {
        return nativeFindFirstInt(this.f849b, j, j2);
    }

    public void mo2299b() {
        m1207g();
        nativeClear(this.f849b);
    }

    public void m1196b(String str) {
        Table l = m1175l();
        if (l == null) {
            throw new RealmException("Primary keys are only supported if Table is part of a Group");
        }
        this.f852e = nativeSetPrimaryKey(l.f849b, this.f849b, str);
    }

    public boolean m1197b(long j) {
        return nativeIsColumnNullable(this.f849b, j);
    }

    public long m1198c() {
        return nativeGetColumnCount(this.f849b);
    }

    public String m1199c(long j) {
        return nativeGetColumnName(this.f849b, j);
    }

    protected native long createNative();

    public long m1200d() {
        if (this.f852e >= 0 || this.f852e == -2) {
            return this.f852e;
        }
        Table l = m1175l();
        if (l == null) {
            return -2;
        }
        long a = l.m1182a(0, m1173d(m1211i()));
        if (a != -1) {
            this.f852e = m1187a(l.m1206g(a).mo2279k(1));
        } else {
            this.f852e = -2;
        }
        return this.f852e;
    }

    public RealmFieldType m1201d(long j) {
        return RealmFieldType.fromNativeValue(nativeGetColumnType(this.f849b, j));
    }

    public void m1202e(long j) {
        m1207g();
        nativeMoveLastOver(this.f849b, j);
    }

    public boolean m1203e() {
        return m1200d() >= 0;
    }

    public Table m1204f(long j) {
        this.f850c.m1290a();
        long nativeGetLinkTarget = nativeGetLinkTarget(this.f849b, j);
        try {
            return new Table(this.f851d, nativeGetLinkTarget);
        } catch (RuntimeException e) {
            nativeClose(nativeGetLinkTarget);
            throw e;
        }
    }

    boolean m1205f() {
        return (this.f851d == null || this.f851d.m1153e()) ? false : true;
    }

    protected void finalize() throws Throwable {
        synchronized (this.f850c) {
            if (this.f849b != 0) {
                this.f850c.m1293a(this.f849b, this.f851d == null);
                this.f849b = 0;
            }
        }
        super.finalize();
    }

    public UncheckedRow m1206g(long j) {
        return UncheckedRow.m1100b(this.f850c, this, j);
    }

    void m1207g() {
        if (m1205f()) {
            m1178o();
        }
    }

    public TableQuery mo2300h() {
        this.f850c.m1290a();
        long nativeWhere = nativeWhere(this.f849b);
        try {
            return new TableQuery(this.f850c, this, nativeWhere);
        } catch (RuntimeException e) {
            TableQuery.nativeClose(nativeWhere);
            throw e;
        }
    }

    public UncheckedRow m1209h(long j) {
        return UncheckedRow.m1101c(this.f850c, this, j);
    }

    public CheckedRow m1210i(long j) {
        return CheckedRow.m1123a(this.f850c, this, j);
    }

    public String m1211i() {
        return nativeGetName(this.f849b);
    }

    public long mo2301j() {
        throw new RuntimeException("Not supported for tables");
    }

    public void m1213j(long j) {
        m1207g();
        nativeAddSearchIndex(this.f849b, j);
    }

    public long mo2302k() {
        return nativeVersion(this.f849b);
    }

    public void m1215k(long j) {
        m1207g();
        nativeRemoveSearchIndex(this.f849b, j);
    }

    public boolean m1216l(long j) {
        return nativeHasSearchIndex(this.f849b, j);
    }

    public long mo2303m(long j) {
        return j;
    }

    public long m1218n(long j) {
        return nativeFindFirstNull(this.f849b, j);
    }

    native long nativeGetRowPtr(long j, long j2);

    public String toString() {
        long c = m1198c();
        String i = m1211i();
        StringBuilder stringBuilder = new StringBuilder("The Table ");
        if (!(i == null || i.isEmpty())) {
            stringBuilder.append(m1211i());
            stringBuilder.append(" ");
        }
        if (m1203e()) {
            stringBuilder.append("has '").append(m1199c(m1200d())).append("' field as a PrimaryKey, and ");
        }
        stringBuilder.append("contains ");
        stringBuilder.append(c);
        stringBuilder.append(" columns: ");
        for (int i2 = 0; ((long) i2) < c; i2++) {
            if (i2 != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(m1199c((long) i2));
        }
        stringBuilder.append(".");
        stringBuilder.append(" And ");
        stringBuilder.append(mo2298a());
        stringBuilder.append(" rows.");
        return stringBuilder.toString();
    }
}
