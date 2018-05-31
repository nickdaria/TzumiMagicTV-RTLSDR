package io.realm;

import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.Table;
import java.util.Arrays;

public final class C0665g extends C0498r implements C0661k {
    private final C0704j f800a = new C0704j(this);

    C0665g(C0657b c0657b, C0669m c0669m) {
        this.f800a.m1349a(c0657b);
        this.f800a.m1350a(c0669m);
        this.f800a.m1358g();
    }

    public String[] m1056a() {
        String[] strArr = new String[((int) this.f800a.m1353b().mo2262a())];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = this.f800a.m1353b().mo2271d((long) i);
        }
        return strArr;
    }

    public String m1057b() {
        return RealmSchema.m967a(this.f800a.m1353b().mo2267b());
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C0665g c0665g = (C0665g) obj;
        String f = this.f800a.m1347a().mo2257f();
        String f2 = c0665g.f800a.m1347a().mo2257f();
        if (f != null) {
            if (!f.equals(f2)) {
                return false;
            }
        } else if (f2 != null) {
            return false;
        }
        f = this.f800a.m1353b().mo2267b().m1211i();
        f2 = c0665g.f800a.m1353b().mo2267b().m1211i();
        if (f != null) {
            if (!f.equals(f2)) {
                return false;
            }
        } else if (f2 != null) {
            return false;
        }
        if (this.f800a.m1353b().mo2269c() != c0665g.f800a.m1353b().mo2269c()) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = 0;
        String f = this.f800a.m1347a().mo2257f();
        String i2 = this.f800a.m1353b().mo2267b().m1211i();
        long c = this.f800a.m1353b().mo2269c();
        int hashCode = ((f != null ? f.hashCode() : 0) + 527) * 31;
        if (i2 != null) {
            i = i2.hashCode();
        }
        return ((i + hashCode) * 31) + ((int) ((c >>> 32) ^ c));
    }

    public C0704j mo2250j() {
        return this.f800a;
    }

    public String toString() {
        if (this.f800a.m1347a() == null || !this.f800a.m1353b().mo2272d()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder(Table.m1173d(this.f800a.m1353b().mo2267b().m1211i()) + " = [");
        for (String str : m1056a()) {
            long a = this.f800a.m1353b().mo2263a(str);
            RealmFieldType e = this.f800a.m1353b().mo2273e(a);
            stringBuilder.append("{");
            stringBuilder.append(str).append(":");
            switch (e) {
                case BOOLEAN:
                    stringBuilder.append(this.f800a.m1353b().mo2268b(a) ? "null" : Boolean.valueOf(this.f800a.m1353b().mo2275g(a)));
                    break;
                case INTEGER:
                    stringBuilder.append(this.f800a.m1353b().mo2268b(a) ? "null" : Long.valueOf(this.f800a.m1353b().mo2274f(a)));
                    break;
                case FLOAT:
                    stringBuilder.append(this.f800a.m1353b().mo2268b(a) ? "null" : Float.valueOf(this.f800a.m1353b().mo2276h(a)));
                    break;
                case DOUBLE:
                    stringBuilder.append(this.f800a.m1353b().mo2268b(a) ? "null" : Double.valueOf(this.f800a.m1353b().mo2277i(a)));
                    break;
                case STRING:
                    stringBuilder.append(this.f800a.m1353b().mo2279k(a));
                    break;
                case BINARY:
                    stringBuilder.append(Arrays.toString(this.f800a.m1353b().mo2280l(a)));
                    break;
                case DATE:
                    stringBuilder.append(this.f800a.m1353b().mo2268b(a) ? "null" : this.f800a.m1353b().mo2278j(a));
                    break;
                case OBJECT:
                    stringBuilder.append(this.f800a.m1353b().mo2266a(a) ? "null" : Table.m1173d(this.f800a.m1353b().mo2267b().m1204f(a).m1211i()));
                    break;
                case LIST:
                    stringBuilder.append(String.format("RealmList<%s>[%s]", new Object[]{Table.m1173d(this.f800a.m1353b().mo2267b().m1204f(a).m1211i()), Long.valueOf(this.f800a.m1353b().mo2281m(a).m1131b())}));
                    break;
                default:
                    stringBuilder.append("?");
                    break;
            }
            stringBuilder.append("}, ");
        }
        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
