package com.eardatek.special.player.p005i;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.eardatek.special.player.system.DTVApplication;

public class C0535f {
    public static void m629a() {
        Resources resources = DTVApplication.m750a().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (VERSION.SDK_INT >= 17) {
            configuration.setLocale(DTVApplication.m755e());
        } else {
            configuration.locale = DTVApplication.m755e();
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }
}
