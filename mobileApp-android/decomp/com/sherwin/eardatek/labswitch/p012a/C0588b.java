package com.sherwin.eardatek.labswitch.p012a;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class C0588b {
    public static String f607a = "Cniao_Pref_Common";
    private static final String f608b = C0588b.class.getSimpleName();

    public static boolean m786a(Context context, String str, boolean z) {
        Editor edit = context.getSharedPreferences(f607a, 0).edit();
        edit.putBoolean(str, z);
        return edit.commit();
    }

    public static boolean m787b(Context context, String str, boolean z) {
        return context.getSharedPreferences(f607a, 0).getBoolean(str, z);
    }
}
