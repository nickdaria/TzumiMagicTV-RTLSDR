package com.sherwin.libepgparser;

import com.sherwin.libepgparser.p014b.C0600a;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class C0595a {
    public static Map<String, List<C0600a>> f643a = new HashMap();
    private static Map<String, String> f644b = new HashMap();
    private static C0433a f645c = null;
    private static C0591b f646d = null;

    public interface C0433a {
        void mo2049a(int i, int i2, String str);
    }

    public interface C0591b {
        void m809a(int i, int i2, List<C0600a> list);
    }

    public static String m819a(int i, int i2) {
        return String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)});
    }

    public static String m820a(String str) {
        return (String) f644b.get(str);
    }

    public static void m821a() {
        f644b.clear();
    }

    public static void m822a(int i, int i2, String str) {
        f644b.put(C0595a.m819a(i, i2), str);
        if (f645c != null) {
            f645c.mo2049a(i, i2, str);
        }
    }

    public static void m823a(int i, int i2, List<C0600a> list) {
        String a = C0595a.m819a(i, i2);
        List list2 = (List) f643a.get(a);
        if (list2 == null) {
            f643a.put(a, list);
            if (f646d != null) {
                f646d.m809a(i, i2, list);
            }
        } else if (((C0600a) list.get(list.size() - 1)).m837a().after(((C0600a) list2.get(list2.size() - 1)).m837a())) {
            f643a.put(a, list);
            if (f646d != null) {
                f646d.m809a(i, i2, list);
            }
        }
    }

    public static void m824a(C0433a c0433a) {
        f645c = c0433a;
    }

    public static List<C0600a> m825b(String str) {
        return (List) f643a.get(str);
    }

    public static void m826b() {
        f643a.clear();
    }

    public static void m827c() {
        C0595a.m821a();
        C0595a.m826b();
    }
}
