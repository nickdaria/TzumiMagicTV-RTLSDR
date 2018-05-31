package io.realm.p018a;

import io.realm.C0497q;
import io.realm.C0723t;
import java.util.IdentityHashMap;
import java.util.Map;

public class C0647a implements C0646b {
    ThreadLocal<C0645a<C0723t>> f764a = new C06431(this);
    ThreadLocal<C0645a<C0497q>> f765b = new C06442(this);

    class C06431 extends ThreadLocal<C0645a<C0723t>> {
        final /* synthetic */ C0647a f761a;

        C06431(C0647a c0647a) {
            this.f761a = c0647a;
        }

        protected C0645a<C0723t> m981a() {
            return new C0645a();
        }

        protected /* synthetic */ Object initialValue() {
            return m981a();
        }
    }

    class C06442 extends ThreadLocal<C0645a<C0497q>> {
        final /* synthetic */ C0647a f762a;

        C06442(C0647a c0647a) {
            this.f762a = c0647a;
        }

        protected C0645a<C0497q> m982a() {
            return new C0645a();
        }

        protected /* synthetic */ Object initialValue() {
            return m982a();
        }
    }

    private static class C0645a<K> {
        private final Map<K, Integer> f763a;

        private C0645a() {
            this.f763a = new IdentityHashMap();
        }
    }

    public boolean equals(Object obj) {
        return obj instanceof C0647a;
    }

    public int hashCode() {
        return 37;
    }
}
