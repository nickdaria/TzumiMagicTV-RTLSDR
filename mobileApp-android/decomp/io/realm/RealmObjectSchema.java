package io.realm;

import io.realm.internal.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class RealmObjectSchema {
    private static final Map<Class<?>, C0642b> f747b = new HashMap();
    private static final Map<Class<?>, C0642b> f748c = new HashMap();
    final Table f749a;
    private final C0657b f750d;
    private final Map<String, Long> f751e;
    private final long f752f;

    static final class C0641a implements Map<String, Long> {
        private final Table f744a;

        public C0641a(Table table) {
            this.f744a = table;
        }

        public Long m946a(Object obj) {
            long a = this.f744a.m1187a((String) obj);
            return a < 0 ? null : Long.valueOf(a);
        }

        public Long m947a(String str, Long l) {
            throw new UnsupportedOperationException();
        }

        public Long m948b(Object obj) {
            throw new UnsupportedOperationException();
        }

        public void clear() {
            throw new UnsupportedOperationException();
        }

        public boolean containsKey(Object obj) {
            throw new UnsupportedOperationException();
        }

        public boolean containsValue(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Set<Entry<String, Long>> entrySet() {
            throw new UnsupportedOperationException();
        }

        public /* synthetic */ Object get(Object obj) {
            return m946a(obj);
        }

        public boolean isEmpty() {
            throw new UnsupportedOperationException();
        }

        public Set<String> keySet() {
            throw new UnsupportedOperationException();
        }

        public /* synthetic */ Object put(Object obj, Object obj2) {
            return m947a((String) obj, (Long) obj2);
        }

        public void putAll(Map<? extends String, ? extends Long> map) {
            throw new UnsupportedOperationException();
        }

        public /* synthetic */ Object remove(Object obj) {
            return m948b(obj);
        }

        public int size() {
            throw new UnsupportedOperationException();
        }

        public Collection<Long> values() {
            throw new UnsupportedOperationException();
        }
    }

    private static class C0642b {
        public final RealmFieldType f745a;
        public final boolean f746b;

        public C0642b(RealmFieldType realmFieldType, boolean z) {
            this.f745a = realmFieldType;
            this.f746b = z;
        }
    }

    static {
        f747b.put(String.class, new C0642b(RealmFieldType.STRING, true));
        f747b.put(Short.TYPE, new C0642b(RealmFieldType.INTEGER, false));
        f747b.put(Short.class, new C0642b(RealmFieldType.INTEGER, true));
        f747b.put(Integer.TYPE, new C0642b(RealmFieldType.INTEGER, false));
        f747b.put(Integer.class, new C0642b(RealmFieldType.INTEGER, true));
        f747b.put(Long.TYPE, new C0642b(RealmFieldType.INTEGER, false));
        f747b.put(Long.class, new C0642b(RealmFieldType.INTEGER, true));
        f747b.put(Float.TYPE, new C0642b(RealmFieldType.FLOAT, false));
        f747b.put(Float.class, new C0642b(RealmFieldType.FLOAT, true));
        f747b.put(Double.TYPE, new C0642b(RealmFieldType.DOUBLE, false));
        f747b.put(Double.class, new C0642b(RealmFieldType.DOUBLE, true));
        f747b.put(Boolean.TYPE, new C0642b(RealmFieldType.BOOLEAN, false));
        f747b.put(Boolean.class, new C0642b(RealmFieldType.BOOLEAN, true));
        f747b.put(Byte.TYPE, new C0642b(RealmFieldType.INTEGER, false));
        f747b.put(Byte.class, new C0642b(RealmFieldType.INTEGER, true));
        f747b.put(byte[].class, new C0642b(RealmFieldType.BINARY, true));
        f747b.put(Date.class, new C0642b(RealmFieldType.DATE, true));
        f748c.put(C0498r.class, new C0642b(RealmFieldType.OBJECT, false));
        f748c.put(C0719o.class, new C0642b(RealmFieldType.LIST, false));
    }

    protected RealmObjectSchema(long j) {
        this.f750d = null;
        this.f749a = null;
        this.f751e = null;
        this.f752f = j;
    }

    RealmObjectSchema(C0657b c0657b, Table table, Map<String, Long> map) {
        this.f750d = c0657b;
        this.f749a = table;
        this.f751e = map;
        this.f752f = 0;
    }

    RealmObjectSchema(String str) {
        this.f750d = null;
        this.f749a = null;
        this.f751e = null;
        this.f752f = nativeCreateRealmObjectSchema(str);
    }

    private void m949a(String str, C0666h[] c0666hArr) {
        if (c0666hArr != null) {
            try {
                if (c0666hArr.length > 0) {
                    if (m951a(c0666hArr, C0666h.INDEXED)) {
                        m959a(str);
                    }
                    if (m951a(c0666hArr, C0666h.PRIMARY_KEY)) {
                        m964b(str);
                    }
                }
            } catch (Exception e) {
                long h = m957h(str);
                if (null != null) {
                    this.f749a.m1215k(h);
                }
                throw e;
            }
        }
    }

    private boolean m950a(RealmFieldType realmFieldType, RealmFieldType[] realmFieldTypeArr) {
        for (RealmFieldType realmFieldType2 : realmFieldTypeArr) {
            if (realmFieldType2 == realmFieldType) {
                return true;
            }
        }
        return false;
    }

    private boolean m951a(C0666h[] c0666hArr, C0666h c0666h) {
        if (c0666hArr == null || c0666hArr.length == 0) {
            return false;
        }
        for (C0666h c0666h2 : c0666hArr) {
            if (c0666h2 == c0666h) {
                return true;
            }
        }
        return false;
    }

    private Set<Property> m952d() {
        if (this.f750d == null) {
            long[] nativeGetProperties = nativeGetProperties(this.f752f);
            Set<Property> linkedHashSet = new LinkedHashSet(nativeGetProperties.length);
            for (long property : nativeGetProperties) {
                linkedHashSet.add(new Property(property));
            }
            return linkedHashSet;
        }
        throw new IllegalArgumentException("Not possible");
    }

    private void m953d(String str) {
        m954e(str);
        m955f(str);
    }

    private void m954e(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Field name can not be null or empty");
        } else if (str.contains(".")) {
            throw new IllegalArgumentException("Field name can not contain '.'");
        }
    }

    private void m955f(String str) {
        if (this.f749a.m1187a(str) != -1) {
            throw new IllegalArgumentException("Field already exists in '" + m966c() + "': " + str);
        }
    }

    private void m956g(String str) {
        if (this.f749a.m1187a(str) == -1) {
            throw new IllegalArgumentException("Field name doesn't exist on object '" + m966c() + "': " + str);
        }
    }

    private long m957h(String str) {
        long a = this.f749a.m1187a(str);
        if (a != -1) {
            return a;
        }
        throw new IllegalArgumentException(String.format("Field name '%s' does not exist on schema for '%s", new Object[]{str, m966c()}));
    }

    static native void nativeAddProperty(long j, long j2);

    static native void nativeClose(long j);

    static native long nativeCreateRealmObjectSchema(String str);

    static native String nativeGetClassName(long j);

    static native long[] nativeGetProperties(long j);

    protected RealmObjectSchema m958a(Property property) {
        if (this.f750d == null || this.f752f != 0) {
            nativeAddProperty(this.f752f, property.m944a());
            return this;
        }
        throw new IllegalArgumentException("Don't use this method.");
    }

    public RealmObjectSchema m959a(String str) {
        m954e(str);
        m956g(str);
        long h = m957h(str);
        if (this.f749a.m1216l(h)) {
            throw new IllegalStateException(str + " already has an index.");
        }
        this.f749a.m1213j(h);
        return this;
    }

    public RealmObjectSchema m960a(String str, Class<?> cls, C0666h... c0666hArr) {
        boolean z = false;
        C0642b c0642b = (C0642b) f747b.get(cls);
        if (c0642b != null) {
            m953d(str);
            boolean z2 = c0642b.f746b;
            if (!m951a(c0666hArr, C0666h.REQUIRED)) {
                z = z2;
            }
            long a = this.f749a.m1184a(c0642b.f745a, str, z);
            try {
                m949a(str, c0666hArr);
                return this;
            } catch (Exception e) {
                this.f749a.m1188a(a);
                throw e;
            }
        } else if (f748c.containsKey(cls)) {
            throw new IllegalArgumentException("Use addRealmObjectField() instead to add fields that link to other RealmObjects: " + str);
        } else {
            throw new IllegalArgumentException(String.format("Realm doesn't support this field type: %s(%s)", new Object[]{str, cls}));
        }
    }

    public void m961a() {
        if (this.f752f != 0) {
            for (Property b : m952d()) {
                b.m945b();
            }
            nativeClose(this.f752f);
        }
    }

    long[] m962a(String str, RealmFieldType... realmFieldTypeArr) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("Non-empty fieldname must be provided");
        } else if (str.startsWith(".") || str.endsWith(".")) {
            throw new IllegalArgumentException("Illegal field name. It cannot start or end with a '.': " + str);
        } else {
            Table table = this.f749a;
            Object obj = (realmFieldTypeArr == null || realmFieldTypeArr.length <= 0) ? null : 1;
            if (str.contains(".")) {
                long a;
                String[] split = str.split("\\.");
                long[] jArr = new long[split.length];
                int i = 0;
                while (i < split.length - 1) {
                    a = table.m1187a(split[i]);
                    if (a < 0) {
                        throw new IllegalArgumentException("Invalid query: " + split[i] + " does not refer to a class.");
                    }
                    RealmFieldType d = table.m1201d(a);
                    if (d == RealmFieldType.OBJECT || d == RealmFieldType.LIST) {
                        table = table.m1204f(a);
                        jArr[i] = a;
                        i++;
                    } else {
                        throw new IllegalArgumentException("Invalid query: " + split[i] + " does not refer to a class.");
                    }
                }
                String str2 = split[split.length - 1];
                a = table.m1187a(str2);
                jArr[split.length - 1] = a;
                if (a < 0) {
                    throw new IllegalArgumentException(str2 + " is not a field name in class " + table.m1211i());
                } else if (obj == null || m950a(table.m1201d(a), realmFieldTypeArr)) {
                    return jArr;
                } else {
                    throw new IllegalArgumentException(String.format("Field '%s': type mismatch.", new Object[]{split[split.length - 1]}));
                }
            }
            Long c = m965c(str);
            if (c == null) {
                throw new IllegalArgumentException(String.format("Field '%s' does not exist.", new Object[]{str}));
            }
            RealmFieldType d2 = table.m1201d(c.longValue());
            if (obj == null || m950a(d2, realmFieldTypeArr)) {
                return new long[]{c.longValue()};
            }
            throw new IllegalArgumentException(String.format("Field '%s': type mismatch. Was %s, expected %s.", new Object[]{str, d2, Arrays.toString(realmFieldTypeArr)}));
        }
    }

    protected long m963b() {
        return this.f752f;
    }

    public RealmObjectSchema m964b(String str) {
        m954e(str);
        m956g(str);
        if (this.f749a.m1203e()) {
            throw new IllegalStateException("A primary key is already defined");
        }
        this.f749a.m1196b(str);
        long h = m957h(str);
        if (!this.f749a.m1216l(h)) {
            this.f749a.m1213j(h);
        }
        return this;
    }

    Long m965c(String str) {
        return (Long) this.f751e.get(str);
    }

    public String m966c() {
        return this.f750d == null ? nativeGetClassName(this.f752f) : this.f749a.m1211i().substring(Table.f848a.length());
    }
}
