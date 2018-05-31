package com.eardatek.special.player.p010g;

import android.content.Context;
import com.eardatek.special.player.p005i.C0550o;
import com.eardatek.special.player.p007b.C0500a;
import io.realm.C0497q;
import io.realm.C0708k;
import java.sql.SQLException;
import java.util.List;

public class C0521a {
    private C0708k f404a;

    public C0521a(Context context) {
        this.f404a = C0550o.m713a(context).m714a();
    }

    public C0500a m588a(String str) throws SQLException {
        return (C0500a) this.f404a.m1377a(C0500a.class).m1462a("mLocation", str).m1466b();
    }

    public List<C0500a> m589a() throws SQLException {
        return this.f404a.m1377a(C0500a.class).m1464a().m1481a("isEncrypt");
    }

    public void m590a(C0500a c0500a) throws SQLException {
        this.f404a.mo2253b();
        this.f404a.m1382b((C0497q) c0500a);
        this.f404a.mo2254c();
    }

    public void m591a(String str, String str2) throws SQLException {
        this.f404a.mo2253b();
        ((C0500a) this.f404a.m1377a(C0500a.class).m1462a("mLocation", str).m1466b()).m490a(str2);
        this.f404a.mo2254c();
    }

    public void m592b() throws SQLException {
        this.f404a.mo2253b();
        this.f404a.m1377a(C0500a.class).m1464a().m1489c();
        this.f404a.mo2254c();
    }

    public void m593b(String str) throws SQLException {
        C0500a c0500a = (C0500a) this.f404a.m1377a(C0500a.class).m1462a("mLocation", str).m1466b();
        this.f404a.mo2253b();
        c0500a.m479k();
        this.f404a.mo2254c();
    }

    public void m594c() throws SQLException {
        this.f404a.close();
    }
}
