package com.p000a.p001a;

import android.os.Build;
import android.os.Build.VERSION;
import com.p000a.p001a.C0398c.C0395b;

final class C0402e implements C0395b {
    C0402e() {
    }

    public void mo2024a(String str) {
        System.loadLibrary(str);
    }

    public String[] mo2025a() {
        if (VERSION.SDK_INT >= 21 && Build.SUPPORTED_ABIS.length > 0) {
            return Build.SUPPORTED_ABIS;
        }
        if (C0403f.m61a(Build.CPU_ABI2)) {
            return new String[]{Build.CPU_ABI};
        }
        return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
    }

    public void mo2026b(String str) {
        System.load(str);
    }

    public String mo2027c(String str) {
        return (str.startsWith("lib") && str.endsWith(".so")) ? str : System.mapLibraryName(str);
    }

    public String mo2028d(String str) {
        return str.substring(3, str.length() - 3);
    }
}
