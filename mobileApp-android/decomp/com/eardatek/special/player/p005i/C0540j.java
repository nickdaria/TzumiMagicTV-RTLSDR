package com.eardatek.special.player.p005i;

import android.os.Handler;

public class C0540j {
    public static void m645a(Handler handler, int i, int i2, int i3) {
        handler.obtainMessage(i, i2, i3).sendToTarget();
    }

    public static void m646a(Handler handler, int i, int i2, int i3, Object obj) {
        handler.obtainMessage(i, i2, i3, obj).sendToTarget();
    }

    public static void m647a(Handler handler, int i, Object obj) {
        handler.obtainMessage(i, obj).sendToTarget();
    }
}
