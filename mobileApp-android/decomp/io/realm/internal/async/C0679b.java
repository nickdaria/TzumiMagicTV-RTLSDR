package io.realm.internal.async;

import android.os.Process;

public class C0679b implements Runnable {
    private final Runnable f874a;

    C0679b(Runnable runnable) {
        this.f874a = runnable;
    }

    public void run() {
        Process.setThreadPriority(10);
        this.f874a.run();
    }
}
