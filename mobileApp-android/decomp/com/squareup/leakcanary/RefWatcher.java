package com.squareup.leakcanary;

import com.squareup.leakcanary.HeapDump.Listener;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public final class RefWatcher {
    public static final RefWatcher DISABLED = new RefWatcher(new C06341(), new C06352(), GcTrigger.DEFAULT, new C06363(), new C06374());
    private final DebuggerControl debuggerControl;
    private final GcTrigger gcTrigger;
    private final HeapDumper heapDumper;
    private final Listener heapdumpListener;
    private final ReferenceQueue<Object> queue = new ReferenceQueue();
    private final Set<String> retainedKeys = new CopyOnWriteArraySet();
    private final Executor watchExecutor;

    static class C06341 implements Executor {
        C06341() {
        }

        public void execute(Runnable runnable) {
        }
    }

    static class C06352 implements DebuggerControl {
        C06352() {
        }

        public boolean isDebuggerAttached() {
            return true;
        }
    }

    static class C06363 implements HeapDumper {
        C06363() {
        }

        public File dumpHeap() {
            return null;
        }
    }

    static class C06374 implements Listener {
        C06374() {
        }

        public void analyze(HeapDump heapDump) {
        }
    }

    public RefWatcher(Executor executor, DebuggerControl debuggerControl, GcTrigger gcTrigger, HeapDumper heapDumper, Listener listener) {
        this.watchExecutor = (Executor) Preconditions.checkNotNull(executor, "watchExecutor");
        this.debuggerControl = (DebuggerControl) Preconditions.checkNotNull(debuggerControl, "debuggerControl");
        this.gcTrigger = (GcTrigger) Preconditions.checkNotNull(gcTrigger, "gcTrigger");
        this.heapDumper = (HeapDumper) Preconditions.checkNotNull(heapDumper, "heapDumper");
        this.heapdumpListener = (Listener) Preconditions.checkNotNull(listener, "heapdumpListener");
    }

    private boolean gone(KeyedWeakReference keyedWeakReference) {
        return !this.retainedKeys.contains(keyedWeakReference.key);
    }

    private void removeWeaklyReachableReferences() {
        while (true) {
            KeyedWeakReference keyedWeakReference = (KeyedWeakReference) this.queue.poll();
            if (keyedWeakReference != null) {
                this.retainedKeys.remove(keyedWeakReference.key);
            } else {
                return;
            }
        }
    }

    void ensureGone(KeyedWeakReference keyedWeakReference, long j) {
        long nanoTime = System.nanoTime();
        long toMillis = TimeUnit.NANOSECONDS.toMillis(nanoTime - j);
        removeWeaklyReachableReferences();
        if (!gone(keyedWeakReference) && !this.debuggerControl.isDebuggerAttached()) {
            this.gcTrigger.runGc();
            removeWeaklyReachableReferences();
            if (!gone(keyedWeakReference)) {
                long nanoTime2 = System.nanoTime();
                long toMillis2 = TimeUnit.NANOSECONDS.toMillis(nanoTime2 - nanoTime);
                File dumpHeap = this.heapDumper.dumpHeap();
                if (dumpHeap != null) {
                    this.heapdumpListener.analyze(new HeapDump(dumpHeap, keyedWeakReference.key, keyedWeakReference.name, toMillis, toMillis2, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - nanoTime2)));
                }
            }
        }
    }

    public void watch(Object obj) {
        watch(obj, "");
    }

    public void watch(Object obj, String str) {
        Preconditions.checkNotNull(obj, "watchedReference");
        Preconditions.checkNotNull(str, "referenceName");
        if (!this.debuggerControl.isDebuggerAttached()) {
            final long nanoTime = System.nanoTime();
            String uuid = UUID.randomUUID().toString();
            this.retainedKeys.add(uuid);
            final KeyedWeakReference keyedWeakReference = new KeyedWeakReference(obj, uuid, str, this.queue);
            this.watchExecutor.execute(new Runnable() {
                public void run() {
                    RefWatcher.this.ensureGone(keyedWeakReference, nanoTime);
                }
            });
        }
    }
}
