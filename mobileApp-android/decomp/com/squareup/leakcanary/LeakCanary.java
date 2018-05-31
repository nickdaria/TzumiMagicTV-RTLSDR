package com.squareup.leakcanary;

import android.app.Application;

public final class LeakCanary {
    private LeakCanary() {
        throw new AssertionError();
    }

    public static RefWatcher install(Application application) {
        return RefWatcher.DISABLED;
    }
}
