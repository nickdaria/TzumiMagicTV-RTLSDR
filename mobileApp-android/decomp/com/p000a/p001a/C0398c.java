package com.p000a.p001a;

import android.content.Context;
import java.io.File;

public class C0398c {

    public interface C0392a {
        void mo2023a(Context context, String[] strArr, String str, File file, C0401d c0401d);
    }

    public interface C0395b {
        void mo2024a(String str);

        String[] mo2025a();

        void mo2026b(String str);

        String mo2027c(String str);

        String mo2028d(String str);
    }

    public interface C0396c {
        void m42a();

        void m43a(Throwable th);
    }

    public interface C0397d {
        void m44a(String str);
    }

    public static void m45a(Context context, String str, String str2) {
        C0398c.m46a(context, str, str2, null);
    }

    public static void m46a(Context context, String str, String str2, C0396c c0396c) {
        new C0401d().m52a(context, str, str2, c0396c);
    }
}
