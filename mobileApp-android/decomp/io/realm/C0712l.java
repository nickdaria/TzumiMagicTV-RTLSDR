package io.realm;

import io.realm.exceptions.RealmFileException;
import io.realm.exceptions.RealmFileException.Kind;
import io.realm.internal.C0677a;
import io.realm.internal.C0701i;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.log.RealmLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

final class C0712l {
    private static Map<String, C0712l> f944d = new HashMap();
    private final EnumMap<C0710b, C0711c> f945a;
    private final C0715n f946b;
    private final C0677a[] f947c = new C0677a[4];

    interface C0651a {
        void mo2245a(int i);
    }

    private enum C0710b {
        TYPED_REALM,
        DYNAMIC_REALM;

        static C0710b m1391a(Class<? extends C0657b> cls) {
            if (cls == C0708k.class) {
                return TYPED_REALM;
            }
            if (cls == C0663f.class) {
                return DYNAMIC_REALM;
            }
            throw new IllegalArgumentException("The type of Realm class must be Realm or DynamicRealm.");
        }
    }

    private static class C0711c {
        private final ThreadLocal<C0657b> f941a;
        private final ThreadLocal<Integer> f942b;
        private int f943c;

        private C0711c() {
            this.f941a = new ThreadLocal();
            this.f942b = new ThreadLocal();
            this.f943c = 0;
        }
    }

    private C0712l(C0715n c0715n) {
        this.f946b = c0715n;
        this.f945a = new EnumMap(C0710b.class);
        for (Enum put : C0710b.values()) {
            this.f945a.put(put, new C0711c());
        }
    }

    private static int m1397a(C0677a[] c0677aArr, C0677a c0677a) {
        long j = Long.MAX_VALUE;
        int i = -1;
        for (int length = c0677aArr.length - 1; length >= 0; length--) {
            if (c0677aArr[length] == null) {
                c0677aArr[length] = c0677a;
                return length;
            }
            C0677a c0677a2 = c0677aArr[length];
            if (c0677a2.m1263a() <= j) {
                j = c0677a2.m1263a();
                i = length;
            }
        }
        c0677aArr[i] = c0677a;
        return i;
    }

    static synchronized <E extends C0657b> E m1398a(C0715n c0715n, Class<E> cls) {
        C0657b c0657b;
        synchronized (C0712l.class) {
            C0712l c0712l;
            C0712l c0712l2 = (C0712l) f944d.get(c0715n.m1435l());
            if (c0712l2 == null) {
                c0712l2 = new C0712l(c0715n);
                C0712l.m1404b(c0715n);
                c0712l = c0712l2;
                Object obj = null;
            } else {
                c0712l2.m1402a(c0715n);
                c0712l = c0712l2;
                int i = 1;
            }
            C0711c c0711c = (C0711c) c0712l.f945a.get(C0710b.m1391a(cls));
            if (c0711c.f943c == 0) {
                SharedRealm a = SharedRealm.m1140a(c0715n);
                if (Table.m1171b(a)) {
                    a.m1150b();
                    if (Table.m1169a(a)) {
                        a.m1151c();
                    } else {
                        a.m1152d();
                    }
                }
                a.close();
            }
            if (c0711c.f941a.get() == null) {
                Object a2;
                if (cls == C0708k.class) {
                    a2 = C0708k.m1362a(c0715n, c0712l.f947c);
                } else if (cls == C0663f.class) {
                    a2 = C0663f.m1045c(c0715n);
                } else {
                    throw new IllegalArgumentException("The type of Realm class must be Realm or DynamicRealm.");
                }
                if (obj == null) {
                    f944d.put(c0715n.m1435l(), c0712l);
                }
                c0711c.f941a.set(a2);
                c0711c.f942b.set(Integer.valueOf(0));
            }
            Integer num = (Integer) c0711c.f942b.get();
            if (num.intValue() == 0) {
                if (cls == C0708k.class && c0711c.f943c == 0) {
                    C0712l.m1397a(c0712l.f947c, ((C0657b) c0711c.f941a.get()).f785f.f754a.m1266b());
                }
                c0711c.f943c = c0711c.f943c + 1;
            }
            c0711c.f942b.set(Integer.valueOf(num.intValue() + 1));
            c0657b = (C0657b) c0711c.f941a.get();
            if (c0711c.f943c == 1) {
                C0701i.m1319a(c0715n.m1436n()).m1323b(c0715n);
            }
        }
        return c0657b;
    }

    public static C0677a m1399a(C0677a[] c0677aArr, long j) {
        for (int length = c0677aArr.length - 1; length >= 0; length--) {
            C0677a c0677a = c0677aArr[length];
            if (c0677a != null && c0677a.m1263a() == j) {
                return c0677a;
            }
        }
        return null;
    }

    static synchronized void m1400a(C0657b c0657b) {
        int i = 0;
        Integer num = null;
        synchronized (C0712l.class) {
            C0711c c0711c;
            String f = c0657b.mo2257f();
            C0712l c0712l = (C0712l) f944d.get(f);
            if (c0712l != null) {
                C0711c c0711c2 = (C0711c) c0712l.f945a.get(C0710b.m1391a(c0657b.getClass()));
                C0711c c0711c3 = c0711c2;
                num = (Integer) c0711c2.f942b.get();
                c0711c = c0711c3;
            } else {
                c0711c = null;
            }
            if (num == null) {
                num = Integer.valueOf(0);
            }
            if (num.intValue() <= 0) {
                RealmLog.m1411c("%s has been closed already.", f);
            } else {
                num = Integer.valueOf(num.intValue() - 1);
                if (num.intValue() == 0) {
                    c0711c.f942b.set(null);
                    c0711c.f941a.set(null);
                    c0711c.f943c = c0711c.f943c - 1;
                    if (c0711c.f943c < 0) {
                        throw new IllegalStateException("Global reference counter of Realm" + f + " got corrupted.");
                    }
                    if ((c0657b instanceof C0708k) && c0711c.f943c == 0) {
                        Arrays.fill(c0712l.f947c, null);
                    }
                    for (Object obj : C0710b.values()) {
                        i += ((C0711c) c0712l.f945a.get(obj)).f943c;
                    }
                    c0657b.m1014i();
                    if (i == 0) {
                        f944d.remove(f);
                        C0701i.m1319a(c0657b.mo2258g().m1436n()).m1321a(c0657b.mo2258g());
                    }
                } else {
                    c0711c.f942b.set(num);
                }
            }
        }
    }

    static synchronized void m1401a(C0708k c0708k) {
        synchronized (C0712l.class) {
            C0712l c0712l = (C0712l) f944d.get(c0708k.mo2257f());
            if (c0712l != null) {
                if (((C0711c) c0712l.f945a.get(C0710b.TYPED_REALM)).f941a.get() != null) {
                    C0677a[] c0677aArr = c0712l.f947c;
                    C0677a a = c0708k.m1374a(c0677aArr);
                    if (a != null) {
                        C0712l.m1397a(c0677aArr, a);
                    }
                }
            }
        }
    }

    private void m1402a(C0715n c0715n) {
        if (!this.f946b.equals(c0715n)) {
            if (Arrays.equals(this.f946b.m1426c(), c0715n.m1426c())) {
                C0541p e = c0715n.m1428e();
                C0541p e2 = this.f946b.m1428e();
                if (e2 == null || e == null || !e2.getClass().equals(e.getClass()) || e.equals(e2)) {
                    throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. \nCached configuration: \n" + this.f946b + "\n\nNew configuration: \n" + c0715n);
                }
                throw new IllegalArgumentException("Configurations cannot be different if used to open the same file. The most likely cause is that equals() and hashCode() are not overridden in the migration class: " + c0715n.m1428e().getClass().getCanonicalName());
            }
            throw new IllegalArgumentException("Wrong key used to decrypt Realm.");
        }
    }

    static synchronized void m1403a(C0715n c0715n, C0651a c0651a) {
        synchronized (C0712l.class) {
            C0712l c0712l = (C0712l) f944d.get(c0715n.m1435l());
            if (c0712l == null) {
                c0651a.mo2245a(0);
            } else {
                int i = 0;
                for (Object obj : C0710b.values()) {
                    i += ((C0711c) c0712l.f945a.get(obj)).f943c;
                }
                c0651a.mo2245a(i);
            }
        }
    }

    private static void m1404b(C0715n c0715n) {
        Throwable e;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        Throwable th;
        FileOutputStream fileOutputStream2;
        IOException iOException = null;
        if (c0715n.m1433j()) {
            File file = new File(c0715n.m1424a(), c0715n.m1425b());
            if (!file.exists()) {
                try {
                    InputStream k = c0715n.m1434k();
                    if (k == null) {
                        try {
                            throw new RealmFileException(Kind.ACCESS_ERROR, "Invalid input stream to asset file.");
                        } catch (IOException e2) {
                            e = e2;
                            inputStream = k;
                            fileOutputStream = null;
                            try {
                                throw new RealmFileException(Kind.ACCESS_ERROR, "Could not resolve the path to the Realm asset file.", e);
                            } catch (Throwable th2) {
                                e = th2;
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e3) {
                                        iOException = e3;
                                    }
                                }
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e4) {
                                        if (iOException == null) {
                                        }
                                    }
                                }
                                throw e;
                            }
                        } catch (Throwable th3) {
                            e = th3;
                            inputStream = k;
                            fileOutputStream = null;
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            throw e;
                        }
                    }
                    FileOutputStream fileOutputStream3 = new FileOutputStream(file);
                    try {
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = k.read(bArr);
                            if (read <= -1) {
                                break;
                            }
                            fileOutputStream3.write(bArr, 0, read);
                        }
                        if (k != null) {
                            try {
                                k.close();
                                th = null;
                            } catch (Throwable e5) {
                                th = e5;
                            }
                        } else {
                            th = null;
                        }
                        if (fileOutputStream3 != null) {
                            try {
                                fileOutputStream3.close();
                            } catch (IOException e6) {
                                e = e6;
                                if (th != null) {
                                    e = th;
                                }
                                th = e;
                            }
                        }
                        if (th != null) {
                            throw new RealmFileException(Kind.ACCESS_ERROR, th);
                        }
                    } catch (IOException e7) {
                        e = e7;
                        fileOutputStream2 = fileOutputStream3;
                        inputStream = k;
                        fileOutputStream = fileOutputStream2;
                        throw new RealmFileException(Kind.ACCESS_ERROR, "Could not resolve the path to the Realm asset file.", e);
                    } catch (Throwable th4) {
                        e = th4;
                        fileOutputStream2 = fileOutputStream3;
                        inputStream = k;
                        fileOutputStream = fileOutputStream2;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        throw e;
                    }
                } catch (IOException e8) {
                    e = e8;
                    fileOutputStream = null;
                    inputStream = null;
                    throw new RealmFileException(Kind.ACCESS_ERROR, "Could not resolve the path to the Realm asset file.", e);
                } catch (Throwable th5) {
                    e = th5;
                    fileOutputStream = null;
                    inputStream = null;
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw e;
                }
            }
        }
    }
}
