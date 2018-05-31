package com.eardatek.special.player.p005i;

import android.util.Log;

public class C0539i {
    private static int f441a = 6;

    public static int m642a(String str, String str2) {
        return f441a > 4 ? Log.d(str, str2) : 0;
    }

    public static int m643b(String str, String str2) {
        return f441a > 3 ? Log.i(str, str2) : 0;
    }

    public static int m644c(String str, String str2) {
        return f441a > 1 ? Log.e(str, str2) : 0;
    }
}
