package com.eardatek.special.player.p007b;

import android.text.TextUtils;
import io.realm.C0498r;
import io.realm.C0499e;

public class C0500a extends C0498r implements C0499e {
    private String f343a;
    private String f344b;
    private boolean f345c;
    private String f346d;

    public C0500a(String str, String str2) {
        mo2249c(str);
        mo2111b(str2);
        mo2110a(m487i());
        mo2112d(mo2250j());
    }

    private boolean m487i() {
        return !TextUtils.isEmpty(mo2114f()) && Integer.parseInt(mo2114f().split("-")[5].substring(9)) == 1;
    }

    private String mo2250j() {
        if (TextUtils.isEmpty(mo2114f())) {
            return "Unknow";
        }
        switch (Integer.parseInt(mo2114f().split("-")[6].substring(9))) {
            case 1:
                return "MPEG-1 Video";
            case 2:
                return "MPEG-2 Video";
            case 27:
                return "H264";
            case 36:
                return "H265";
            case 66:
                return "AVS";
            default:
                return "Unknow";
        }
    }

    public String m489a() {
        return mo2113e();
    }

    public void m490a(String str) {
        mo2111b(str);
    }

    public void mo2110a(boolean z) {
        this.f345c = z;
    }

    public String m492b() {
        return mo2114f();
    }

    public void mo2111b(String str) {
        this.f343a = str;
    }

    public void mo2249c(String str) {
        this.f344b = str;
    }

    public boolean m495c() {
        return mo2115g();
    }

    public String m496d() {
        return mo2250j();
    }

    public void mo2112d(String str) {
        this.f346d = str;
    }

    public String mo2113e() {
        return this.f343a;
    }

    public String mo2114f() {
        return this.f344b;
    }

    public boolean mo2115g() {
        return this.f345c;
    }

    public String mo2116h() {
        return this.f346d;
    }

    public String toString() {
        return "ChannelInfo:" + mo2113e() + "\n" + mo2114f();
    }
}
