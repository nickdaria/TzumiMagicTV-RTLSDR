package io.realm.internal;

import io.realm.C0497q;
import io.realm.C0498r;
import io.realm.log.RealmLog;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Util {
    public static Class<? extends C0497q> m1240a(Class<? extends C0497q> cls) {
        Class<? extends C0497q> superclass = cls.getSuperclass();
        return (superclass.equals(Object.class) || superclass.equals(C0498r.class)) ? cls : superclass;
    }

    public static String m1241a() {
        return nativeGetTablePrefix();
    }

    public static boolean m1242a(String str, File file, String str2) {
        boolean z;
        String str3 = ".management";
        File file2 = new File(file, str2 + ".management");
        File[] listFiles = file2.listFiles();
        if (listFiles != null) {
            z = true;
            for (File delete : listFiles) {
                z = z && delete.delete();
            }
        } else {
            z = true;
        }
        z = z && file2.delete();
        return z && m1243b(str, file, str2);
    }

    private static boolean m1243b(String str, File file, String str2) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        for (File file2 : Arrays.asList(new File[]{new File(file, str2), new File(file, str2 + ".lock"), new File(file, str2 + ".log_a"), new File(file, str2 + ".log_b"), new File(file, str2 + ".log"), new File(str)})) {
            if (file2.exists() && !file2.delete()) {
                atomicBoolean.set(false);
                RealmLog.m1411c("Could not delete the file %s", file2);
            }
        }
        return atomicBoolean.get();
    }

    static native String nativeGetTablePrefix();
}
