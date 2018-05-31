package io.realm.log;

import android.util.Log;

public final class RealmLog {
    private static String f948a = "REALM_JAVA";

    private static void m1405a(int i, Throwable th, String str, Object... objArr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (objArr != null && objArr.length > 0) {
            str = String.format(str, objArr);
        }
        if (th != null) {
            stringBuilder.append(Log.getStackTraceString(th));
        }
        if (str != null) {
            if (th != null) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(str);
        }
        nativeLog(i, f948a, th, stringBuilder.toString());
    }

    public static void m1406a(String str, Object... objArr) {
        m1408a(null, str, objArr);
    }

    public static void m1407a(Throwable th) {
        m1413d(th, null, new Object[0]);
    }

    public static void m1408a(Throwable th, String str, Object... objArr) {
        m1405a(2, th, str, objArr);
    }

    public static void m1409b(String str, Object... objArr) {
        m1410b(null, str, objArr);
    }

    public static void m1410b(Throwable th, String str, Object... objArr) {
        m1405a(3, th, str, objArr);
    }

    public static void m1411c(String str, Object... objArr) {
        m1412c(null, str, objArr);
    }

    public static void m1412c(Throwable th, String str, Object... objArr) {
        m1405a(5, th, str, objArr);
    }

    public static void m1413d(Throwable th, String str, Object... objArr) {
        m1405a(6, th, str, objArr);
    }

    private static native void nativeLog(int i, String str, Throwable th, String str2);
}
