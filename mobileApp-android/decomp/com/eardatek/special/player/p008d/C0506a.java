package com.eardatek.special.player.p008d;

import com.sherwin.eardatek.labswitch.C0589a;
import java.util.ArrayList;
import java.util.List;

public class C0506a {
    public static String f356a = "192.168.1.1";
    public static int f357b = 6000;
    public static int f358c = 8000;
    public static String f359d = "TCP";
    public static List<String> f360e = new ArrayList();

    static {
        if (C0589a.m788a().m797d()) {
            f360e.add("Mobile_TV_box");
            f360e.add("MobileTV_");
            f360e.add("@Tevemo_");
            f360e.add("TzumiTV_");
            return;
        }
        switch (2) {
            case 1:
                f360e.add("@Tevemo_");
                return;
            case 2:
                f360e.add("TzumiTV_");
                return;
            default:
                return;
        }
    }
}
