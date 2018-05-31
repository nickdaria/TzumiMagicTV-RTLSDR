package com.eardatek.special.player.p009e;

import android.support.annotation.NonNull;
import com.eardatek.special.player.p005i.C0536g;
import com.eardatek.special.player.p007b.C0500a;
import com.eardatek.special.player.p009e.C0508a.C0507a;
import com.eardatek.special.player.p010g.C0521a;
import com.eardatek.special.player.system.DTVApplication;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class C0510b extends C0508a {
    private List<C0509a> f370a;
    private C0509a f371b;
    private int f372c = -1;

    public static final class C0509a extends C0507a implements Serializable, Comparable<C0509a> {
        private final long f361a;
        private String f362b;
        private final int f363c;
        private boolean f364d;
        private int f365e = 0;
        private long f366f;
        private String f367g;
        private boolean f368h;
        private String f369i;

        public C0509a(long j, int i, String str, int i2, String str2, boolean z, String str3) {
            this.f361a = j;
            this.f363c = i;
            this.f362b = str;
            this.f367g = str2;
            this.f368h = z;
            this.f369i = str3;
        }

        public static int m528a(long j, long j2) {
            Calendar instance = Calendar.getInstance();
            Calendar instance2 = Calendar.getInstance();
            instance.setTimeInMillis(j);
            instance2.setTimeInMillis(j2);
            return instance.compareTo(instance2);
        }

        public int m529a(@NonNull C0509a c0509a) {
            int e = 0 - (this.f365e - c0509a.mo2124e());
            return e == 0 ? 0 - C0509a.m528a(this.f366f, c0509a.m541h()) : e;
        }

        public long mo2117a() {
            return this.f361a;
        }

        public void mo2118a(int i) {
            this.f365e = i;
        }

        public void mo2119a(long j) {
            this.f366f = j;
        }

        public void m533a(String str) {
            this.f367g = str;
        }

        public void mo2120a(boolean z) {
            this.f364d = z;
        }

        public int mo2121b() {
            return this.f363c;
        }

        public String mo2122c() {
            return this.f362b;
        }

        public /* synthetic */ int compareTo(@NonNull Object obj) {
            return m529a((C0509a) obj);
        }

        public boolean mo2123d() {
            return this.f364d;
        }

        public int mo2124e() {
            return this.f365e;
        }

        public String mo2125f() {
            return this.f367g;
        }

        public boolean mo2126g() {
            return this.f368h;
        }

        public long m541h() {
            return this.f366f;
        }

        public String toString() {
            return this.f362b;
        }
    }

    public C0510b() {
        m542d();
    }

    private void m542d() {
        this.f370a = new ArrayList();
        this.f370a = C0536g.m630a("Channel.txt");
        C0521a c0521a = new C0521a(DTVApplication.m750a());
        try {
            List a = c0521a.m589a();
            if (a.size() == 0) {
                this.f370a.clear();
                C0536g.m632b("Channel.txt");
            }
            if (this.f370a.size() == 0 && a.size() > 0) {
                for (int i = 0; i < a.size(); i++) {
                    this.f370a.add(new C0509a((long) i, 0, ((C0500a) a.get(i)).m492b(), SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_V, ((C0500a) a.get(i)).m489a(), ((C0500a) a.get(i)).m495c(), ((C0500a) a.get(i)).m496d()));
                }
            }
            try {
                c0521a.m594c();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public int mo2127a() {
        return this.f370a.size();
    }

    public C0507a mo2128a(int i) {
        if (i >= 0 && i < mo2127a()) {
            return (C0507a) this.f370a.get(i);
        }
        throw new IndexOutOfBoundsException("index = " + i);
    }

    public void mo2129a(int i, int i2) {
        if (i != i2) {
            this.f370a.add(i2, (C0509a) this.f370a.remove(i));
            this.f372c = -1;
        }
    }

    public void mo2130a(String str, String str2, int i) {
        C0509a c0509a = (C0509a) this.f370a.get(i);
        c0509a.m533a(str2);
        this.f370a.remove(i);
        this.f370a.add(i, c0509a);
        C0521a c0521a = new C0521a(DTVApplication.m750a());
        try {
            c0521a.m591a(str, str2);
            c0521a.m594c();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void m547b() {
        this.f370a.clear();
    }

    public void m548b(int i) {
        C0507a c0507a = (C0507a) this.f370a.get(i);
        C0521a c0521a = new C0521a(DTVApplication.m750a());
        try {
            c0521a.m593b(c0507a.mo2122c());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.f371b = (C0509a) this.f370a.remove(i);
        this.f372c = i;
        try {
            c0521a.m594c();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }

    public List<C0509a> m549c() {
        return this.f370a;
    }
}
