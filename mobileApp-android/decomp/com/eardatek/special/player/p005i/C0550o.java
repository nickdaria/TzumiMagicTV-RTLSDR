package com.eardatek.special.player.p005i;

import android.content.Context;
import io.realm.C0708k;

public class C0550o {
    private static C0550o f473b;
    private Context f474a;
    private String f475c = "channelInfo.realm";

    private C0550o(Context context) {
        this.f474a = context;
    }

    public static C0550o m713a(Context context) {
        if (f473b == null) {
            synchronized (C0550o.class) {
                if (f473b == null) {
                    f473b = new C0550o(context);
                }
            }
        }
        return f473b;
    }

    public C0708k m714a() {
        return C0708k.m1372l();
    }
}
