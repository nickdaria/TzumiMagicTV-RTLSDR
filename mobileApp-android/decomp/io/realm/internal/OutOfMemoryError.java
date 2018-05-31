package io.realm.internal;

@Keep
public class OutOfMemoryError extends Error {
    public OutOfMemoryError(String str) {
        super(str);
    }

    public OutOfMemoryError(String str, Throwable th) {
        super(str, th);
    }

    public OutOfMemoryError(Throwable th) {
        super(th);
    }
}
