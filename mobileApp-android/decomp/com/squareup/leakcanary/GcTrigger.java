package com.squareup.leakcanary;

public interface GcTrigger {
    public static final GcTrigger DEFAULT = new C06331();

    static class C06331 implements GcTrigger {
        C06331() {
        }

        private void enqueueReferences() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new AssertionError();
            }
        }

        public void runGc() {
            Runtime.getRuntime().gc();
            enqueueReferences();
            System.runFinalization();
        }
    }

    void runGc();
}
