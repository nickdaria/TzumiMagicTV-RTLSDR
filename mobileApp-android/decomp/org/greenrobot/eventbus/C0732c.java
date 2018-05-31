package org.greenrobot.eventbus;

import android.os.Looper;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

public class C0732c {
    public static String f1027a = "EventBus";
    static volatile C0732c f1028b;
    private static final C0733d f1029c = new C0733d();
    private static final Map<Class<?>, List<Class<?>>> f1030d = new HashMap();
    private final Map<Class<?>, CopyOnWriteArrayList<C0744n>> f1031e;
    private final Map<Object, List<Class<?>>> f1032f;
    private final Map<Class<?>, Object> f1033g;
    private final ThreadLocal<C0731a> f1034h;
    private final C0735f f1035i;
    private final C0728b f1036j;
    private final C0727a f1037k;
    private final C0743m f1038l;
    private final ExecutorService f1039m;
    private final boolean f1040n;
    private final boolean f1041o;
    private final boolean f1042p;
    private final boolean f1043q;
    private final boolean f1044r;
    private final boolean f1045s;
    private final int f1046t;

    class C07291 extends ThreadLocal<C0731a> {
        final /* synthetic */ C0732c f1019a;

        C07291(C0732c c0732c) {
            this.f1019a = c0732c;
        }

        protected C0731a m1499a() {
            return new C0731a();
        }

        protected /* synthetic */ Object initialValue() {
            return m1499a();
        }
    }

    static final class C0731a {
        final List<Object> f1021a = new ArrayList();
        boolean f1022b;
        boolean f1023c;
        C0744n f1024d;
        Object f1025e;
        boolean f1026f;

        C0731a() {
        }
    }

    public C0732c() {
        this(f1029c);
    }

    C0732c(C0733d c0733d) {
        this.f1034h = new C07291(this);
        this.f1031e = new HashMap();
        this.f1032f = new HashMap();
        this.f1033g = new ConcurrentHashMap();
        this.f1035i = new C0735f(this, Looper.getMainLooper(), 10);
        this.f1036j = new C0728b(this);
        this.f1037k = new C0727a(this);
        this.f1046t = c0733d.f1057j != null ? c0733d.f1057j.size() : 0;
        this.f1038l = new C0743m(c0733d.f1057j, c0733d.f1055h, c0733d.f1054g);
        this.f1041o = c0733d.f1048a;
        this.f1042p = c0733d.f1049b;
        this.f1043q = c0733d.f1050c;
        this.f1044r = c0733d.f1051d;
        this.f1040n = c0733d.f1052e;
        this.f1045s = c0733d.f1053f;
        this.f1039m = c0733d.f1056i;
    }

    private static List<Class<?>> m1500a(Class<?> cls) {
        List<Class<?>> list;
        synchronized (f1030d) {
            list = (List) f1030d.get(cls);
            if (list == null) {
                list = new ArrayList();
                for (Class cls2 = cls; cls2 != null; cls2 = cls2.getSuperclass()) {
                    list.add(cls2);
                    C0732c.m1505a((List) list, cls2.getInterfaces());
                }
                f1030d.put(cls, list);
            }
        }
        return list;
    }

    public static C0732c m1501a() {
        if (f1028b == null) {
            synchronized (C0732c.class) {
                if (f1028b == null) {
                    f1028b = new C0732c();
                }
            }
        }
        return f1028b;
    }

    private void m1502a(Object obj, Class<?> cls) {
        List list = (List) this.f1031e.get(cls);
        if (list != null) {
            int size = list.size();
            int i = 0;
            while (i < size) {
                int i2;
                C0744n c0744n = (C0744n) list.get(i);
                if (c0744n.f1093a == obj) {
                    c0744n.f1095c = false;
                    list.remove(i);
                    i2 = i - 1;
                    i = size - 1;
                } else {
                    i2 = i;
                    i = size;
                }
                size = i;
                i = i2 + 1;
            }
        }
    }

    private void m1503a(Object obj, C0731a c0731a) throws Error {
        boolean z;
        Class cls = obj.getClass();
        if (this.f1045s) {
            List a = C0732c.m1500a(cls);
            boolean z2 = false;
            for (int i = 0; i < a.size(); i++) {
                z2 |= m1508a(obj, c0731a, (Class) a.get(i));
            }
            z = z2;
        } else {
            z = m1508a(obj, c0731a, cls);
        }
        if (!z) {
            if (this.f1042p) {
                Log.d(f1027a, "No subscribers registered for event " + cls);
            }
            if (this.f1044r && cls != C0736g.class && cls != C0740k.class) {
                m1515c(new C0736g(this, obj));
            }
        }
    }

    private void m1504a(Object obj, C0741l c0741l) {
        CopyOnWriteArrayList copyOnWriteArrayList;
        Class cls = c0741l.f1076c;
        C0744n c0744n = new C0744n(obj, c0741l);
        CopyOnWriteArrayList copyOnWriteArrayList2 = (CopyOnWriteArrayList) this.f1031e.get(cls);
        if (copyOnWriteArrayList2 == null) {
            copyOnWriteArrayList2 = new CopyOnWriteArrayList();
            this.f1031e.put(cls, copyOnWriteArrayList2);
            copyOnWriteArrayList = copyOnWriteArrayList2;
        } else if (copyOnWriteArrayList2.contains(c0744n)) {
            throw new C0734e("Subscriber " + obj.getClass() + " already registered to event " + cls);
        } else {
            copyOnWriteArrayList = copyOnWriteArrayList2;
        }
        int size = copyOnWriteArrayList.size();
        int i = 0;
        while (i <= size) {
            if (i == size || c0741l.f1077d > ((C0744n) copyOnWriteArrayList.get(i)).f1094b.f1077d) {
                copyOnWriteArrayList.add(i, c0744n);
                break;
            }
            i++;
        }
        List list = (List) this.f1032f.get(obj);
        if (list == null) {
            list = new ArrayList();
            this.f1032f.put(obj, list);
        }
        list.add(cls);
        if (!c0741l.f1078e) {
            return;
        }
        if (this.f1045s) {
            for (Entry entry : this.f1033g.entrySet()) {
                if (cls.isAssignableFrom((Class) entry.getKey())) {
                    m1509b(c0744n, entry.getValue());
                }
            }
            return;
        }
        m1509b(c0744n, this.f1033g.get(cls));
    }

    static void m1505a(List<Class<?>> list, Class<?>[] clsArr) {
        for (Class cls : clsArr) {
            if (!list.contains(cls)) {
                list.add(cls);
                C0732c.m1505a((List) list, cls.getInterfaces());
            }
        }
    }

    private void m1506a(C0744n c0744n, Object obj, Throwable th) {
        if (obj instanceof C0740k) {
            if (this.f1041o) {
                Log.e(f1027a, "SubscriberExceptionEvent subscriber " + c0744n.f1093a.getClass() + " threw an exception", th);
                C0740k c0740k = (C0740k) obj;
                Log.e(f1027a, "Initial event " + c0740k.f1072c + " caused exception in " + c0740k.f1073d, c0740k.f1071b);
            }
        } else if (this.f1040n) {
            throw new C0734e("Invoking subscriber failed", th);
        } else {
            if (this.f1041o) {
                Log.e(f1027a, "Could not dispatch event: " + obj.getClass() + " to subscribing class " + c0744n.f1093a.getClass(), th);
            }
            if (this.f1043q) {
                m1515c(new C0740k(this, th, obj, c0744n.f1093a));
            }
        }
    }

    private void m1507a(C0744n c0744n, Object obj, boolean z) {
        switch (c0744n.f1094b.f1075b) {
            case POSTING:
                m1512a(c0744n, obj);
                return;
            case MAIN:
                if (z) {
                    m1512a(c0744n, obj);
                    return;
                } else {
                    this.f1035i.m1516a(c0744n, obj);
                    return;
                }
            case BACKGROUND:
                if (z) {
                    this.f1036j.m1498a(c0744n, obj);
                    return;
                } else {
                    m1512a(c0744n, obj);
                    return;
                }
            case ASYNC:
                this.f1037k.m1497a(c0744n, obj);
                return;
            default:
                throw new IllegalStateException("Unknown thread mode: " + c0744n.f1094b.f1075b);
        }
    }

    private boolean m1508a(Object obj, C0731a c0731a, Class<?> cls) {
        synchronized (this) {
            CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) this.f1031e.get(cls);
        }
        if (copyOnWriteArrayList == null || copyOnWriteArrayList.isEmpty()) {
            return false;
        }
        Iterator it = copyOnWriteArrayList.iterator();
        loop0:
        while (it.hasNext()) {
            C0744n c0744n = (C0744n) it.next();
            c0731a.f1025e = obj;
            c0731a.f1024d = c0744n;
            try {
                m1507a(c0744n, obj, c0731a.f1023c);
                Object obj2 = c0731a.f1026f;
                continue;
            } finally {
                c0731a.f1025e = null;
                c0731a.f1024d = null;
                c0731a.f1026f = false;
            }
            if (obj2 != null) {
                break loop0;
            }
        }
        return true;
    }

    private void m1509b(C0744n c0744n, Object obj) {
        if (obj != null) {
            m1507a(c0744n, obj, Looper.getMainLooper() == Looper.myLooper());
        }
    }

    public void m1510a(Object obj) {
        List<C0741l> a = this.f1038l.m1537a(obj.getClass());
        synchronized (this) {
            for (C0741l a2 : a) {
                m1504a(obj, a2);
            }
        }
    }

    void m1511a(C0737h c0737h) {
        Object obj = c0737h.f1065a;
        C0744n c0744n = c0737h.f1066b;
        C0737h.m1518a(c0737h);
        if (c0744n.f1095c) {
            m1512a(c0744n, obj);
        }
    }

    void m1512a(C0744n c0744n, Object obj) {
        try {
            c0744n.f1094b.f1074a.invoke(c0744n.f1093a, new Object[]{obj});
        } catch (InvocationTargetException e) {
            m1506a(c0744n, obj, e.getCause());
        } catch (Throwable e2) {
            throw new IllegalStateException("Unexpected exception", e2);
        }
    }

    ExecutorService m1513b() {
        return this.f1039m;
    }

    public synchronized void m1514b(Object obj) {
        List<Class> list = (List) this.f1032f.get(obj);
        if (list != null) {
            for (Class a : list) {
                m1502a(obj, a);
            }
            this.f1032f.remove(obj);
        } else {
            Log.w(f1027a, "Subscriber to unregister was not registered before: " + obj.getClass());
        }
    }

    public void m1515c(Object obj) {
        C0731a c0731a = (C0731a) this.f1034h.get();
        List list = c0731a.f1021a;
        list.add(obj);
        if (!c0731a.f1022b) {
            c0731a.f1023c = Looper.getMainLooper() == Looper.myLooper();
            c0731a.f1022b = true;
            if (c0731a.f1026f) {
                throw new C0734e("Internal error. Abort state was not reset");
            }
            while (!list.isEmpty()) {
                try {
                    m1503a(list.remove(0), c0731a);
                } finally {
                    c0731a.f1022b = false;
                    c0731a.f1023c = false;
                }
            }
        }
    }

    public String toString() {
        return "EventBus[indexCount=" + this.f1046t + ", eventInheritance=" + this.f1045s + "]";
    }
}
