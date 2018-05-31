package com.sherwin.libepgparser;

import com.sherwin.libepgparser.p014b.C0600a;
import java.util.ArrayList;
import java.util.List;

public class EpgParser {
    private static EpgParser f627a = new EpgParser();

    static {
        System.loadLibrary("epgparser1.1.9");
    }

    private EpgParser() {
    }

    public static synchronized String m806a(int i) {
        String str = null;
        synchronized (EpgParser.class) {
            String str2;
            switch (getLastEpgDescriptorCode()) {
                case 1:
                    str2 = "gbk";
                    break;
                case 2:
                    str2 = "ascii";
                    break;
                default:
                    str2 = "unicode";
                    break;
            }
            byte[] epgDescriptorBytes = getEpgDescriptorBytes(i);
            if (epgDescriptorBytes != null) {
                try {
                    str = new String(epgDescriptorBytes, str2);
                } catch (Exception e) {
                }
            }
        }
        return str;
    }

    public static synchronized List<C0600a> m807a(int i, int i2) {
        List<C0600a> arrayList;
        synchronized (EpgParser.class) {
            if (i2 > i) {
                i2 = i;
            }
            arrayList = new ArrayList();
            for (int i3 = i - 1; i3 >= i - i2; i3--) {
                C0600a b = m808b(i3);
                if (b != null) {
                    arrayList.add(b);
                }
            }
        }
        return arrayList;
    }

    public static synchronized C0600a m808b(int i) {
        C0600a c0600a = null;
        synchronized (EpgParser.class) {
            String epgTimesStr = getEpgTimesStr(i);
            if (epgTimesStr != null) {
                String a = m806a(i);
                if (a != null) {
                    c0600a = C0600a.m833a(epgTimesStr + "," + a);
                }
            }
        }
        return c0600a;
    }

    public static native synchronized byte[] getEpgDescriptorBytes(int i);

    public static native synchronized String getEpgTimesStr(int i);

    public static native synchronized int getLastEpgDescriptorCode();

    public static native synchronized int startParseAllList(String str, int i);

    public static native synchronized int startParseCurrentList(String str, int i);
}
