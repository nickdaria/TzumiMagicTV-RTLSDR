package io.realm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import io.realm.internal.RealmNotifier;
import io.realm.internal.async.C0692c.C0691d;
import io.realm.log.RealmLog;

class C0648a implements RealmNotifier {
    private Handler f766a;

    public C0648a(C0667i c0667i) {
        if (C0648a.m983a()) {
            this.f766a = new Handler(c0667i);
        }
    }

    private static boolean m983a() {
        return (Looper.myLooper() == null || C0648a.m984b()) ? false : true;
    }

    private static boolean m984b() {
        String name = Thread.currentThread().getName();
        return name != null && name.startsWith("IntentService[");
    }

    public void close() {
        if (this.f766a != null) {
            this.f766a.removeCallbacksAndMessages(null);
            this.f766a = null;
        }
    }

    public void completeAsyncObject(C0691d c0691d) {
        if (this.f766a.getLooper().getThread().isAlive()) {
            this.f766a.obtainMessage(63245986, c0691d).sendToTarget();
        }
    }

    public void completeAsyncResults(C0691d c0691d) {
        if (this.f766a.getLooper().getThread().isAlive()) {
            this.f766a.obtainMessage(39088169, c0691d).sendToTarget();
        }
    }

    public void completeUpdateAsyncQueries(C0691d c0691d) {
        if (this.f766a.getLooper().getThread().isAlive()) {
            this.f766a.obtainMessage(24157817, c0691d).sendToTarget();
        }
    }

    public boolean isValid() {
        return this.f766a != null;
    }

    public void notifyCommitByLocalThread() {
        if (this.f766a != null) {
            Message obtain = Message.obtain();
            obtain.what = 165580141;
            if (!this.f766a.hasMessages(165580141)) {
                this.f766a.removeMessages(14930352);
                this.f766a.sendMessageAtFrontOfQueue(obtain);
            }
        }
    }

    public void notifyCommitByOtherThread() {
        if (this.f766a != null) {
            boolean z = true;
            if (!(this.f766a.hasMessages(14930352) || this.f766a.hasMessages(165580141))) {
                z = this.f766a.sendEmptyMessage(14930352);
            }
            if (!z) {
                RealmLog.m1411c("Cannot update Looper threads when the Looper has quit. Use realm.setAutoRefresh(false) to prevent this.", new Object[0]);
            }
        }
    }

    public void post(Runnable runnable) {
        if (this.f766a.getLooper().getThread().isAlive()) {
            this.f766a.post(runnable);
        }
    }

    public void throwBackgroundException(Throwable th) {
        if (this.f766a.getLooper().getThread().isAlive()) {
            this.f766a.obtainMessage(102334155, new Error(th)).sendToTarget();
        }
    }
}
