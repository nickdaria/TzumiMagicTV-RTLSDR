package com.blazevideo.libdtv;

public class LibDtvException extends Exception {
    public LibDtvException(String str) {
        super(str);
    }

    public LibDtvException(String str, Throwable th) {
        super(str, th);
    }

    public LibDtvException(Throwable th) {
        super(th);
    }
}
