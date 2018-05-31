package io.realm.internal;

import android.content.Context;
import com.p000a.p001a.C0398c;
import java.io.File;

public class C0702j {
    private static final String f922a = File.separator;
    private static final String f923b = File.pathSeparator;
    private static final String f924c = ("lib" + f923b + ".." + f922a + "lib");
    private static volatile boolean f925d = false;

    public static synchronized void m1325a(Context context) {
        synchronized (C0702j.class) {
            if (!f925d) {
                C0398c.m45a(context, "realm-jni", "2.2.1");
                f925d = true;
            }
        }
    }
}
