package com.eardatek.special.player.p005i;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class C0549n {
    public static String f471a = "Cniao_Pref_Common";
    private static final String f472b = C0549n.class.getSimpleName();

    public static String m705a(Context context, String str) {
        return C0549n.m711b(context, str, null);
    }

    public static boolean m706a(Context context, String str, int i) {
        Editor edit = context.getSharedPreferences(f471a, 0).edit();
        edit.putInt(str, i);
        return edit.commit();
    }

    public static boolean m707a(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(f471a, 0).edit();
        edit.putString(str, str2);
        return edit.commit();
    }

    public static boolean m708a(Context context, String str, boolean z) {
        Editor edit = context.getSharedPreferences(f471a, 0).edit();
        edit.putBoolean(str, z);
        return edit.commit();
    }

    public static int m709b(Context context, String str) {
        return C0549n.m710b(context, str, 0);
    }

    public static int m710b(Context context, String str, int i) {
        return context.getSharedPreferences(f471a, 0).getInt(str, i);
    }

    public static String m711b(Context context, String str, String str2) {
        return context.getSharedPreferences(f471a, 0).getString(str, str2);
    }

    public static boolean m712b(Context context, String str, boolean z) {
        return context.getSharedPreferences(f471a, 0).getBoolean(str, z);
    }
}
