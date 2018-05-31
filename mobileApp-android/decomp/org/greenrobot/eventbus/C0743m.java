package org.greenrobot.eventbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.greenrobot.eventbus.p020a.C0725a;
import org.greenrobot.eventbus.p020a.C0726b;

class C0743m {
    private static final Map<Class<?>, List<C0741l>> f1088a = new ConcurrentHashMap();
    private static final C0742a[] f1089e = new C0742a[4];
    private List<C0726b> f1090b;
    private final boolean f1091c;
    private final boolean f1092d;

    static class C0742a {
        final List<C0741l> f1080a = new ArrayList();
        final Map<Class, Object> f1081b = new HashMap();
        final Map<String, Class> f1082c = new HashMap();
        final StringBuilder f1083d = new StringBuilder(128);
        Class<?> f1084e;
        Class<?> f1085f;
        boolean f1086g;
        C0725a f1087h;

        C0742a() {
        }

        private boolean m1526b(Method method, Class<?> cls) {
            this.f1083d.setLength(0);
            this.f1083d.append(method.getName());
            this.f1083d.append('>').append(cls.getName());
            String stringBuilder = this.f1083d.toString();
            Class declaringClass = method.getDeclaringClass();
            Class cls2 = (Class) this.f1082c.put(stringBuilder, declaringClass);
            if (cls2 == null || cls2.isAssignableFrom(declaringClass)) {
                return true;
            }
            this.f1082c.put(stringBuilder, cls2);
            return false;
        }

        void m1527a() {
            this.f1080a.clear();
            this.f1081b.clear();
            this.f1082c.clear();
            this.f1083d.setLength(0);
            this.f1084e = null;
            this.f1085f = null;
            this.f1086g = false;
            this.f1087h = null;
        }

        void m1528a(Class<?> cls) {
            this.f1085f = cls;
            this.f1084e = cls;
            this.f1086g = false;
            this.f1087h = null;
        }

        boolean m1529a(Method method, Class<?> cls) {
            Object put = this.f1081b.put(cls, method);
            if (put == null) {
                return true;
            }
            if (put instanceof Method) {
                if (m1526b((Method) put, cls)) {
                    this.f1081b.put(cls, this);
                } else {
                    throw new IllegalStateException();
                }
            }
            return m1526b(method, cls);
        }

        void m1530b() {
            if (this.f1086g) {
                this.f1085f = null;
                return;
            }
            this.f1085f = this.f1085f.getSuperclass();
            String name = this.f1085f.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                this.f1085f = null;
            }
        }
    }

    C0743m(List<C0726b> list, boolean z, boolean z2) {
        this.f1090b = list;
        this.f1091c = z;
        this.f1092d = z2;
    }

    private List<C0741l> m1531a(C0742a c0742a) {
        List arrayList = new ArrayList(c0742a.f1080a);
        c0742a.m1527a();
        synchronized (f1089e) {
            for (int i = 0; i < 4; i++) {
                if (f1089e[i] == null) {
                    f1089e[i] = c0742a;
                    break;
                }
            }
        }
        return arrayList;
    }

    private C0742a m1532a() {
        synchronized (f1089e) {
            for (int i = 0; i < 4; i++) {
                C0742a c0742a = f1089e[i];
                if (c0742a != null) {
                    f1089e[i] = null;
                    return c0742a;
                }
            }
            return new C0742a();
        }
    }

    private List<C0741l> m1533b(Class<?> cls) {
        C0742a a = m1532a();
        a.m1528a(cls);
        while (a.f1085f != null) {
            a.f1087h = m1534b(a);
            if (a.f1087h != null) {
                for (C0741l c0741l : a.f1087h.m1494b()) {
                    if (a.m1529a(c0741l.f1074a, c0741l.f1076c)) {
                        a.f1080a.add(c0741l);
                    }
                }
            } else {
                m1536c(a);
            }
            a.m1530b();
        }
        return m1531a(a);
    }

    private C0725a m1534b(C0742a c0742a) {
        C0725a c;
        if (!(c0742a.f1087h == null || c0742a.f1087h.m1495c() == null)) {
            c = c0742a.f1087h.m1495c();
            if (c0742a.f1085f == c.m1493a()) {
                return c;
            }
        }
        if (this.f1090b != null) {
            for (C0726b a : this.f1090b) {
                c = a.m1496a(c0742a.f1085f);
                if (c != null) {
                    return c;
                }
            }
        }
        return null;
    }

    private List<C0741l> m1535c(Class<?> cls) {
        C0742a a = m1532a();
        a.m1528a(cls);
        while (a.f1085f != null) {
            m1536c(a);
            a.m1530b();
        }
        return m1531a(a);
    }

    private void m1536c(C0742a c0742a) {
        Method[] declaredMethods;
        try {
            declaredMethods = c0742a.f1085f.getDeclaredMethods();
        } catch (Throwable th) {
            Method[] methods = c0742a.f1085f.getMethods();
            c0742a.f1086g = true;
            declaredMethods = methods;
        }
        for (Method method : r6) {
            int modifiers = method.getModifiers();
            if ((modifiers & 1) != 0 && (modifiers & 5192) == 0) {
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    C0739j c0739j = (C0739j) method.getAnnotation(C0739j.class);
                    if (c0739j != null) {
                        Class cls = parameterTypes[0];
                        if (c0742a.m1529a(method, cls)) {
                            c0742a.f1080a.add(new C0741l(method, cls, c0739j.m1522a(), c0739j.m1524c(), c0739j.m1523b()));
                        }
                    }
                } else if (this.f1091c && method.isAnnotationPresent(C0739j.class)) {
                    throw new C0734e("@Subscribe method " + (method.getDeclaringClass().getName() + "." + method.getName()) + "must have exactly 1 parameter but has " + parameterTypes.length);
                }
            } else if (this.f1091c && method.isAnnotationPresent(C0739j.class)) {
                throw new C0734e((method.getDeclaringClass().getName() + "." + method.getName()) + " is a illegal @Subscribe method: must be public, non-static, and non-abstract");
            }
        }
    }

    List<C0741l> m1537a(Class<?> cls) {
        List<C0741l> list = (List) f1088a.get(cls);
        if (list == null) {
            list = this.f1092d ? m1535c((Class) cls) : m1533b((Class) cls);
            if (list.isEmpty()) {
                throw new C0734e("Subscriber " + cls + " and its super classes have no public methods with the @Subscribe annotation");
            }
            f1088a.put(cls, list);
        }
        return list;
    }
}
