package org.greenrobot.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

final class C0735f extends Handler {
    private final C0738i f1058a = new C0738i();
    private final int f1059b;
    private final C0732c f1060c;
    private boolean f1061d;

    C0735f(C0732c c0732c, Looper looper, int i) {
        super(looper);
        this.f1060c = c0732c;
        this.f1059b = i;
    }

    void m1516a(C0744n c0744n, Object obj) {
        C0737h a = C0737h.m1517a(c0744n, obj);
        synchronized (this) {
            this.f1058a.m1521a(a);
            if (!this.f1061d) {
                this.f1061d = true;
                if (!sendMessage(obtainMessage())) {
                    throw new C0734e("Could not send handler message");
                }
            }
        }
    }

    public void handleMessage(Message message) {
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            do {
                C0737h a = this.f1058a.m1519a();
                if (a == null) {
                    synchronized (this) {
                        a = this.f1058a.m1519a();
                        if (a == null) {
                            this.f1061d = false;
                            this.f1061d = false;
                            return;
                        }
                    }
                }
                this.f1060c.m1511a(a);
            } while (SystemClock.uptimeMillis() - uptimeMillis < ((long) this.f1059b));
            if (sendMessage(obtainMessage())) {
                this.f1061d = true;
                return;
            }
            throw new C0734e("Could not send handler message");
        } catch (Throwable th) {
            this.f1061d = false;
        }
    }
}
