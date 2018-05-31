package com.eardatek.special.player.widget;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class C0567b {
    private static Toast f564a;
    private static Handler f565b = new Handler();
    private static Runnable f566c = new C05661();

    static class C05661 implements Runnable {
        C05661() {
        }

        public void run() {
            C0567b.f564a.cancel();
        }
    }

    public static void m774a(Context context, String str, int i) {
        f565b.removeCallbacks(f566c);
        if (f564a != null) {
            f564a.setText(str);
        } else {
            f564a = Toast.makeText(context, str, 0);
        }
        f565b.postDelayed(f566c, (long) i);
        f564a.show();
    }
}
