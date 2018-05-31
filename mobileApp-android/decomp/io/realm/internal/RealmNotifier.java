package io.realm.internal;

import io.realm.internal.async.C0692c.C0691d;

@Keep
public interface RealmNotifier {
    void close();

    void completeAsyncObject(C0691d c0691d);

    void completeAsyncResults(C0691d c0691d);

    void completeUpdateAsyncQueries(C0691d c0691d);

    boolean isValid();

    void notifyCommitByLocalThread();

    void notifyCommitByOtherThread();

    void post(Runnable runnable);

    void throwBackgroundException(Throwable th);
}
