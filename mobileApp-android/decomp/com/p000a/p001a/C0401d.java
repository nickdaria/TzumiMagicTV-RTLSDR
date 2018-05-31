package com.p000a.p001a;

import android.content.Context;
import android.util.Log;
import com.p000a.p001a.C0398c.C0392a;
import com.p000a.p001a.C0398c.C0395b;
import com.p000a.p001a.C0398c.C0396c;
import com.p000a.p001a.C0398c.C0397d;
import com.p000a.p001a.p002a.C0387f;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class C0401d {
    protected final Set<String> f41a;
    protected final C0395b f42b;
    protected final C0392a f43c;
    protected boolean f44d;
    protected boolean f45e;
    protected C0397d f46f;

    protected C0401d() {
        this(new C0402e(), new C0393a());
    }

    protected C0401d(C0395b c0395b, C0392a c0392a) {
        this.f41a = new HashSet();
        if (c0395b == null) {
            throw new IllegalArgumentException("Cannot pass null library loader");
        } else if (c0392a == null) {
            throw new IllegalArgumentException("Cannot pass null library installer");
        } else {
            this.f42b = c0395b;
            this.f43c = c0392a;
        }
    }

    private void m48c(Context context, String str, String str2) {
        if (!this.f41a.contains(str) || this.f44d) {
            try {
                this.f42b.mo2024a(str);
                this.f41a.add(str);
                m54a("%s (%s) was loaded normally!", str, str2);
                return;
            } catch (Throwable e) {
                m54a("Loading the library normally failed: %s", Log.getStackTraceString(e));
                m54a("%s (%s) was not loaded normally, re-linking...", str, str2);
                File a = m50a(context, str, str2);
                if (!a.exists() || this.f44d) {
                    if (this.f44d) {
                        m54a("Forcing a re-link of %s (%s)...", str, str2);
                    }
                    m55b(context, str, str2);
                    this.f43c.mo2023a(context, this.f42b.mo2025a(), this.f42b.mo2027c(str), a, this);
                }
                try {
                    if (this.f45e) {
                        for (String d : new C0387f(a).m29b()) {
                            m51a(context, this.f42b.mo2028d(d));
                        }
                    }
                } catch (IOException e2) {
                }
                this.f42b.mo2026b(a.getAbsolutePath());
                this.f41a.add(str);
                m54a("%s (%s) was re-linked!", str, str2);
                return;
            }
        }
        m54a("%s already loaded previously!", str);
    }

    protected File m49a(Context context) {
        return context.getDir("lib", 0);
    }

    protected File m50a(Context context, String str, String str2) {
        String c = this.f42b.mo2027c(str);
        return C0403f.m61a(str2) ? new File(m49a(context), c) : new File(m49a(context), c + "." + str2);
    }

    public void m51a(Context context, String str) {
        m52a(context, str, null, null);
    }

    public void m52a(Context context, String str, String str2, C0396c c0396c) {
        if (context == null) {
            throw new IllegalArgumentException("Given context is null");
        } else if (C0403f.m61a(str)) {
            throw new IllegalArgumentException("Given library is either null or empty");
        } else {
            m54a("Beginning load of %s...", str);
            if (c0396c == null) {
                m48c(context, str, str2);
                return;
            }
            final Context context2 = context;
            final String str3 = str;
            final String str4 = str2;
            final C0396c c0396c2 = c0396c;
            new Thread(new Runnable(this) {
                final /* synthetic */ C0401d f38e;

                public void run() {
                    try {
                        this.f38e.m48c(context2, str3, str4);
                        c0396c2.m42a();
                    } catch (Throwable e) {
                        c0396c2.m43a(e);
                    } catch (Throwable e2) {
                        c0396c2.m43a(e2);
                    }
                }
            }).start();
        }
    }

    public void m53a(String str) {
        if (this.f46f != null) {
            this.f46f.m44a(str);
        }
    }

    public void m54a(String str, Object... objArr) {
        m53a(String.format(Locale.US, str, objArr));
    }

    protected void m55b(Context context, String str, String str2) {
        File a = m49a(context);
        File a2 = m50a(context, str, str2);
        final String c = this.f42b.mo2027c(str);
        File[] listFiles = a.listFiles(new FilenameFilter(this) {
            final /* synthetic */ C0401d f40b;

            public boolean accept(File file, String str) {
                return str.startsWith(c);
            }
        });
        if (listFiles != null) {
            for (File file : listFiles) {
                if (this.f44d || !file.getAbsolutePath().equals(a2.getAbsolutePath())) {
                    file.delete();
                }
            }
        }
    }
}
