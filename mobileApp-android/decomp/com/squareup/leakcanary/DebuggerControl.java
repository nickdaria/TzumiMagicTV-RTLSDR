package com.squareup.leakcanary;

public interface DebuggerControl {
    public static final DebuggerControl NONE = new C06321();

    static class C06321 implements DebuggerControl {
        C06321() {
        }

        public boolean isDebuggerAttached() {
            return false;
        }
    }

    boolean isDebuggerAttached();
}
