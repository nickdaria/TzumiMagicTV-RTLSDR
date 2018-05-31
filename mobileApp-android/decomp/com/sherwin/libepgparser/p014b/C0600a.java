package com.sherwin.libepgparser.p014b;

import android.util.Log;
import com.sherwin.libepgparser.p013a.C0594a;
import com.sherwin.libepgparser.p013a.C0594a.C0593b;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class C0600a {
    private Date f655a;
    private Date f656b;
    private String f657c;

    public class C0599a {
        WeakReference<C0600a> f652a;
        final /* synthetic */ C0600a f653b;
        private StringBuilder f654c = new StringBuilder();

        C0599a(C0600a c0600a, C0600a c0600a2) {
            this.f653b = c0600a;
            this.f652a = new WeakReference(c0600a2);
        }

        public C0599a m830a(String str) {
            this.f654c.append(new SimpleDateFormat(str).format(((C0600a) this.f652a.get()).f655a));
            return this;
        }

        public C0599a m831b(String str) {
            this.f654c.append(String.format(str, new Object[]{((C0600a) this.f652a.get()).f657c}));
            return this;
        }

        public C0599a m832c(String str) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(((C0600a) this.f652a.get()).f655a);
            Date b = ((C0600a) this.f652a.get()).f656b;
            instance.add(10, b.getHours());
            instance.add(12, b.getMinutes());
            instance.add(13, b.getSeconds());
            b = instance.getTime();
            this.f654c.append(new SimpleDateFormat(str).format(b));
            return this;
        }

        public String toString() {
            return this.f654c.toString();
        }
    }

    private C0600a() {
    }

    public C0600a(Date date, Date date2, String str) {
        this.f655a = date;
        this.f656b = date2;
        this.f657c = str;
    }

    public static C0600a m833a(String str) {
        C0594a c0594a = new C0594a(str, "%s,%s,%s");
        try {
            String b = c0594a.m818b();
            String b2 = c0594a.m818b();
            String b3 = c0594a.m818b();
            c0594a = new C0594a(b, "%d-%d-%d-%d:%d:%d");
            try {
                Date date = new Date(c0594a.m817a() - 1900, c0594a.m817a() - 1, c0594a.m817a(), c0594a.m817a(), c0594a.m817a(), c0594a.m817a());
                C0594a c0594a2 = new C0594a(b2, "%d:%d:%d");
                try {
                    return new C0600a(date, new Date(1900, 0, 0, c0594a2.m817a(), c0594a2.m817a(), c0594a2.m817a()), b3);
                } catch (C0593b e) {
                    Log.e("JNIMsg", e.toString());
                    return null;
                }
            } catch (C0593b e2) {
                return null;
            }
        } catch (C0593b e3) {
            return null;
        }
    }

    public Date m837a() {
        return this.f655a;
    }

    public Date m838b() {
        return this.f656b;
    }

    public Date m839c() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(this.f655a);
        instance.add(10, this.f656b.getHours());
        instance.add(12, this.f656b.getMinutes());
        instance.add(13, this.f656b.getSeconds());
        return instance.getTime();
    }

    public String m840d() {
        return this.f657c;
    }

    public C0599a m841e() {
        return new C0599a(this, this);
    }

    public String toString() {
        return "EpgInfo{startTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.f655a) + ", curation=" + new SimpleDateFormat("HH:mm:ss").format(this.f656b) + ", descriptor='" + this.f657c + '\'' + '}';
    }
}
