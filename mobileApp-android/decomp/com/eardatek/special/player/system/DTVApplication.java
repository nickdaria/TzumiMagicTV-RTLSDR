package com.eardatek.special.player.system;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0532c;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.p005i.C0542k;
import com.eardatek.special.player.p005i.C0549n;
import com.eardatek.special.player.p011h.C0522a;
import com.sherwin.eardatek.labswitch.C0589a;
import io.realm.C0708k;
import io.realm.C0715n.C0714a;
import java.util.Locale;

public class DTVApplication extends Application {
    public static final Locale f502a = new Locale("ru", "RU");
    public static StringBuffer f503b = new StringBuffer();
    private static DTVApplication f504c;

    public static Context m750a() {
        return f504c;
    }

    public static void m751a(String str) {
        f503b = new StringBuffer(str);
        C0549n.m707a(m750a(), "LAST_DEVICE_WIFI_NAME", "");
    }

    public static Resources m752b() {
        return f504c.getResources();
    }

    public static StringBuffer m753c() {
        return f503b;
    }

    public static String m754d() {
        try {
            return f504c.getString(R.string.version_name) + f504c.getPackageManager().getPackageInfo(f504c.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return f504c.getString(R.string.version_name);
        }
    }

    public static Locale m755e() {
        switch (C0549n.m710b(m750a(), "language_key", 0)) {
            case 0:
                return Locale.getDefault().equals(Locale.SIMPLIFIED_CHINESE) ? Locale.SIMPLIFIED_CHINESE : Locale.US;
            case 1:
                return Locale.US;
            case 2:
                return Locale.SIMPLIFIED_CHINESE;
            case 3:
                return f502a;
            default:
                return Locale.US;
        }
    }

    public void onCreate() {
        super.onCreate();
        f504c = this;
        C0532c.m622a().m625a((Context) this);
        C0708k.m1364a(getApplicationContext());
        C0708k.m1368b(new C0714a().m1416a(2).m1417a(new C0542k()).m1418a());
        C0522a.m596a(this);
        C0589a.m788a().m790a((Context) this);
    }

    public void onLowMemory() {
        super.onLowMemory();
        C0539i.m643b("EardatekVersion2", "System is running low on memory");
    }
}
