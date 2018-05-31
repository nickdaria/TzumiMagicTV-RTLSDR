package io.realm;

import com.eardatek.special.player.p007b.C0500a;
import io.realm.C0657b.C0655b;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.C0659b;
import io.realm.internal.C0661k;
import io.realm.internal.C0669m;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class C0662d extends C0500a implements C0499e, C0661k {
    private static final List<String> f796c;
    private C0660a f797a;
    private C0704j f798b;

    static final class C0660a extends C0659b implements Cloneable {
        public long f792a;
        public long f793b;
        public long f794c;
        public long f795d;

        C0660a(String str, Table table) {
            Map hashMap = new HashMap(4);
            this.f792a = m1018a(str, table, "ChannelInfo", "mTitle");
            hashMap.put("mTitle", Long.valueOf(this.f792a));
            this.f793b = m1018a(str, table, "ChannelInfo", "mLocation");
            hashMap.put("mLocation", Long.valueOf(this.f793b));
            this.f794c = m1018a(str, table, "ChannelInfo", "isEncrypt");
            hashMap.put("isEncrypt", Long.valueOf(this.f794c));
            this.f795d = m1018a(str, table, "ChannelInfo", "videoType");
            hashMap.put("videoType", Long.valueOf(this.f795d));
            m1020a(hashMap);
        }

        public final C0660a m1023a() {
            return (C0660a) super.mo2247b();
        }

        public final void mo2246a(C0659b c0659b) {
            C0660a c0660a = (C0660a) c0659b;
            this.f792a = c0660a.f792a;
            this.f793b = c0660a.f793b;
            this.f794c = c0660a.f794c;
            this.f795d = c0660a.f795d;
            m1020a(c0660a.m1022c());
        }

        public /* synthetic */ C0659b mo2247b() {
            return m1023a();
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            return m1023a();
        }
    }

    static {
        List arrayList = new ArrayList();
        arrayList.add("mTitle");
        arrayList.add("mLocation");
        arrayList.add("isEncrypt");
        arrayList.add("videoType");
        f796c = Collections.unmodifiableList(arrayList);
    }

    C0662d() {
        if (this.f798b == null) {
            m1034l();
        }
        this.f798b.m1358g();
    }

    static C0500a m1027a(C0708k c0708k, C0500a c0500a, C0500a c0500a2, Map<C0497q, C0661k> map) {
        c0500a.mo2111b(c0500a2.mo2113e());
        c0500a.mo2110a(c0500a2.mo2115g());
        c0500a.mo2112d(c0500a2.mo2116h());
        return c0500a;
    }

    public static C0500a m1028a(C0708k c0708k, C0500a c0500a, boolean z, Map<C0497q, C0661k> map) {
        C0500a c0500a2 = null;
        if ((c0500a instanceof C0661k) && ((C0661k) c0500a).mo2250j().m1347a() != null && ((C0661k) c0500a).mo2250j().m1347a().f782c != c0708k.c) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        } else if ((c0500a instanceof C0661k) && ((C0661k) c0500a).mo2250j().m1347a() != null && ((C0661k) c0500a).mo2250j().m1347a().mo2257f().equals(c0708k.mo2257f())) {
            return c0500a;
        } else {
            C0655b c0655b = (C0655b) C0657b.f781h.get();
            C0661k c0661k = (C0661k) map.get(c0500a);
            if (c0661k != null) {
                return (C0500a) c0661k;
            }
            C0662d c0662d;
            if (z) {
                Table b = c0708k.m1381b(C0500a.class);
                long d = b.m1200d();
                String f = c0500a.mo2114f();
                d = f == null ? b.m1218n(d) : b.m1182a(d, f);
                if (d != -1) {
                    try {
                        c0655b.m992a(c0708k, b.m1206g(d), c0708k.f.m972a(C0500a.class), false, Collections.emptyList());
                        c0500a2 = new C0662d();
                        map.put(c0500a, (C0661k) c0500a2);
                        c0662d = z;
                    } finally {
                        c0655b.m997f();
                    }
                } else {
                    c0662d = null;
                    c0500a2 = null;
                }
            } else {
                c0662d = z;
                c0500a2 = null;
            }
            return c0662d != null ? C0662d.m1027a(c0708k, c0500a2, c0500a, (Map) map) : C0662d.m1032b(c0708k, c0500a, z, map);
        }
    }

    public static RealmObjectSchema m1029a(RealmSchema realmSchema) {
        if (realmSchema.m978c("ChannelInfo")) {
            return realmSchema.m971a("ChannelInfo");
        }
        RealmObjectSchema b = realmSchema.m973b("ChannelInfo");
        b.m958a(new Property("mTitle", RealmFieldType.STRING, false, false, true));
        b.m958a(new Property("mLocation", RealmFieldType.STRING, true, true, false));
        b.m958a(new Property("isEncrypt", RealmFieldType.BOOLEAN, false, false, true));
        b.m958a(new Property("videoType", RealmFieldType.STRING, false, false, false));
        return b;
    }

    public static C0660a m1030a(SharedRealm sharedRealm, boolean z) {
        if (sharedRealm.m1148a("class_ChannelInfo")) {
            Table b = sharedRealm.m1149b("class_ChannelInfo");
            long c = b.m1198c();
            if (c != 4) {
                if (c < 4) {
                    throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Field count is less than expected - expected 4 but was " + c);
                } else if (z) {
                    RealmLog.m1409b("Field count is more than expected - expected 4 but was %1$d", Long.valueOf(c));
                } else {
                    throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Field count is more than expected - expected 4 but was " + c);
                }
            }
            Map hashMap = new HashMap();
            for (long j = 0; j < c; j++) {
                hashMap.put(b.m1199c(j), b.m1201d(j));
            }
            C0660a c0660a = new C0660a(sharedRealm.m1157i(), b);
            if (!hashMap.containsKey("mTitle")) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Missing field 'mTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            } else if (hashMap.get("mTitle") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Invalid type 'String' for field 'mTitle' in existing Realm file.");
            } else if (b.m1197b(c0660a.f792a)) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Field 'mTitle' does support null values in the existing Realm file. Remove @Required or @PrimaryKey from field 'mTitle' or migrate using RealmObjectSchema.setNullable().");
            } else if (!hashMap.containsKey("mLocation")) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Missing field 'mLocation' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            } else if (hashMap.get("mLocation") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Invalid type 'String' for field 'mLocation' in existing Realm file.");
            } else if (!b.m1197b(c0660a.f793b)) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "@PrimaryKey field 'mLocation' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            } else if (b.m1200d() != b.m1187a("mLocation")) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Primary key not defined for field 'mLocation' in existing Realm file. Add @PrimaryKey.");
            } else if (!b.m1216l(b.m1187a("mLocation"))) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Index not defined for field 'mLocation' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            } else if (!hashMap.containsKey("isEncrypt")) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Missing field 'isEncrypt' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            } else if (hashMap.get("isEncrypt") != RealmFieldType.BOOLEAN) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Invalid type 'boolean' for field 'isEncrypt' in existing Realm file.");
            } else if (b.m1197b(c0660a.f794c)) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Field 'isEncrypt' does support null values in the existing Realm file. Use corresponding boxed type for field 'isEncrypt' or migrate using RealmObjectSchema.setNullable().");
            } else if (!hashMap.containsKey("videoType")) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Missing field 'videoType' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            } else if (hashMap.get("videoType") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Invalid type 'String' for field 'videoType' in existing Realm file.");
            } else if (b.m1197b(c0660a.f795d)) {
                return c0660a;
            } else {
                throw new RealmMigrationNeededException(sharedRealm.m1157i(), "Field 'videoType' is required. Either set @Required to field 'videoType' or migrate using RealmObjectSchema.setNullable().");
            }
        }
        throw new RealmMigrationNeededException(sharedRealm.m1157i(), "The 'ChannelInfo' class is missing from the schema for this Realm.");
    }

    public static Table m1031a(SharedRealm sharedRealm) {
        if (sharedRealm.m1148a("class_ChannelInfo")) {
            return sharedRealm.m1149b("class_ChannelInfo");
        }
        Table b = sharedRealm.m1149b("class_ChannelInfo");
        b.m1184a(RealmFieldType.STRING, "mTitle", false);
        b.m1184a(RealmFieldType.STRING, "mLocation", true);
        b.m1184a(RealmFieldType.BOOLEAN, "isEncrypt", false);
        b.m1184a(RealmFieldType.STRING, "videoType", true);
        b.m1213j(b.m1187a("mLocation"));
        b.m1196b("mLocation");
        return b;
    }

    public static C0500a m1032b(C0708k c0708k, C0500a c0500a, boolean z, Map<C0497q, C0661k> map) {
        C0661k c0661k = (C0661k) map.get(c0500a);
        if (c0661k != null) {
            return (C0500a) c0661k;
        }
        C0500a c0500a2 = (C0500a) c0708k.m1376a(C0500a.class, c0500a.mo2114f(), false, Collections.emptyList());
        map.put(c0500a, (C0661k) c0500a2);
        c0500a2.mo2111b(c0500a.mo2113e());
        c0500a2.mo2110a(c0500a.mo2115g());
        c0500a2.mo2112d(c0500a.mo2116h());
        return c0500a2;
    }

    public static String m1033i() {
        return "class_ChannelInfo";
    }

    private void m1034l() {
        C0655b c0655b = (C0655b) C0657b.f781h.get();
        this.f797a = (C0660a) c0655b.m994c();
        this.f798b = new C0704j(C0500a.class, this);
        this.f798b.m1349a(c0655b.m991a());
        this.f798b.m1350a(c0655b.m993b());
        this.f798b.m1352a(c0655b.m995d());
        this.f798b.m1351a(c0655b.m996e());
    }

    public void mo2110a(boolean z) {
        if (this.f798b == null) {
            m1034l();
        }
        if (!this.f798b.m1357f()) {
            this.f798b.m1347a().m1010e();
            this.f798b.m1353b().mo2265a(this.f797a.f794c, z);
        } else if (this.f798b.m1354c()) {
            C0669m b = this.f798b.m1353b();
            b.mo2267b().m1193a(this.f797a.f794c, b.mo2269c(), z, true);
        }
    }

    public void mo2111b(String str) {
        if (this.f798b == null) {
            m1034l();
        }
        if (!this.f798b.m1357f()) {
            this.f798b.m1347a().m1010e();
            if (str == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'mTitle' to null.");
            }
            this.f798b.m1353b().mo2264a(this.f797a.f792a, str);
        } else if (this.f798b.m1354c()) {
            C0669m b = this.f798b.m1353b();
            if (str == null) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'mTitle' to null.");
            }
            b.mo2267b().m1191a(this.f797a.f792a, b.mo2269c(), str, true);
        }
    }

    public void mo2249c(String str) {
        if (this.f798b == null) {
            m1034l();
        }
        if (!this.f798b.m1357f()) {
            this.f798b.m1347a().m1010e();
            throw new RealmException("Primary key field 'mLocation' cannot be changed after object was created.");
        }
    }

    public void mo2112d(String str) {
        if (this.f798b == null) {
            m1034l();
        }
        if (!this.f798b.m1357f()) {
            this.f798b.m1347a().m1010e();
            if (str == null) {
                this.f798b.m1353b().mo2270c(this.f797a.f795d);
            } else {
                this.f798b.m1353b().mo2264a(this.f797a.f795d, str);
            }
        } else if (this.f798b.m1354c()) {
            C0669m b = this.f798b.m1353b();
            if (str == null) {
                b.mo2267b().m1192a(this.f797a.f795d, b.mo2269c(), true);
            } else {
                b.mo2267b().m1191a(this.f797a.f795d, b.mo2269c(), str, true);
            }
        }
    }

    public String mo2113e() {
        if (this.f798b == null) {
            m1034l();
        }
        this.f798b.m1347a().m1010e();
        return this.f798b.m1353b().mo2279k(this.f797a.f792a);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C0662d c0662d = (C0662d) obj;
        String f = this.f798b.m1347a().mo2257f();
        String f2 = c0662d.f798b.m1347a().mo2257f();
        if (f == null ? f2 != null : !f.equals(f2)) {
            return false;
        }
        f = this.f798b.m1353b().mo2267b().m1211i();
        f2 = c0662d.f798b.m1353b().mo2267b().m1211i();
        return (f == null ? f2 != null : !f.equals(f2)) ? false : this.f798b.m1353b().mo2269c() == c0662d.f798b.m1353b().mo2269c();
    }

    public String mo2114f() {
        if (this.f798b == null) {
            m1034l();
        }
        this.f798b.m1347a().m1010e();
        return this.f798b.m1353b().mo2279k(this.f797a.f793b);
    }

    public boolean mo2115g() {
        if (this.f798b == null) {
            m1034l();
        }
        this.f798b.m1347a().m1010e();
        return this.f798b.m1353b().mo2275g(this.f797a.f794c);
    }

    public String mo2116h() {
        if (this.f798b == null) {
            m1034l();
        }
        this.f798b.m1347a().m1010e();
        return this.f798b.m1353b().mo2279k(this.f797a.f795d);
    }

    public int hashCode() {
        int i = 0;
        String f = this.f798b.m1347a().mo2257f();
        String i2 = this.f798b.m1353b().mo2267b().m1211i();
        long c = this.f798b.m1353b().mo2269c();
        int hashCode = ((f != null ? f.hashCode() : 0) + 527) * 31;
        if (i2 != null) {
            i = i2.hashCode();
        }
        return ((i + hashCode) * 31) + ((int) ((c >>> 32) ^ c));
    }

    public C0704j mo2250j() {
        return this.f798b;
    }
}
