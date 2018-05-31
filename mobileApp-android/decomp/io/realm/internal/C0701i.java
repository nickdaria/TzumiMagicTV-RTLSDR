package io.realm.internal;

import android.content.Context;
import io.realm.C0715n;
import io.realm.exceptions.RealmException;

public class C0701i {
    private static final C0701i f920a = new C0701i();
    private static C0701i f921b;

    static {
        f921b = null;
        try {
            f921b = (C0701i) Class.forName("io.realm.internal.objectserver.SyncObjectServerFacade").newInstance();
        } catch (ClassNotFoundException e) {
        } catch (Throwable e2) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e2);
        } catch (Throwable e22) {
            throw new RealmException("Failed to init SyncObjectServerFacade", e22);
        }
    }

    public static C0701i m1318a() {
        return f921b != null ? f921b : f920a;
    }

    public static C0701i m1319a(boolean z) {
        return z ? f921b : f920a;
    }

    public void m1320a(Context context) {
    }

    public void m1321a(C0715n c0715n) {
    }

    public void m1322a(C0715n c0715n, long j) {
    }

    public void m1323b(C0715n c0715n) {
    }

    public String[] m1324c(C0715n c0715n) {
        return new String[2];
    }
}
