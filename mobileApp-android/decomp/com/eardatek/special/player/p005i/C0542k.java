package com.eardatek.special.player.p005i;

import io.realm.C0541p;
import io.realm.C0663f;
import io.realm.C0666h;
import io.realm.RealmSchema;

public class C0542k implements C0541p {
    public void mo2142a(C0663f c0663f, long j, long j2) {
        RealmSchema k = c0663f.mo2261k();
        if (j == 0) {
            C0666h[] c0666hArr = new C0666h[]{C0666h.REQUIRED};
            k.m973b("ChannelInfo").m960a("mLocation", String.class, C0666h.PRIMARY_KEY).m960a("mTitle", String.class, c0666hArr).m960a("isEncrypt", Boolean.TYPE, new C0666h[0]);
            j++;
        }
        if (j == 1) {
            k.m971a("ChannelInfo").m960a("videoType", String.class, new C0666h[0]);
            long j3 = j + 1;
        }
    }
}
