package io.realm.internal.async;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class C0693d extends ThreadPoolExecutor {
    private static final int f902a = ((Runtime.getRuntime().availableProcessors() * 2) + 1);
    private boolean f903b;
    private ReentrantLock f904c = new ReentrantLock();
    private Condition f905d = this.f904c.newCondition();

    private C0693d(int i, int i2) {
        super(i, i2, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(100));
    }

    public static C0693d m1285a() {
        return new C0693d(f902a, f902a);
    }

    public Future<?> m1286a(Runnable runnable) {
        return super.submit(new C0679b(runnable));
    }

    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        this.f904c.lock();
        while (this.f903b) {
            try {
                this.f905d.await();
            } catch (InterruptedException e) {
                thread.interrupt();
            } finally {
                this.f904c.unlock();
            }
        }
    }
}
