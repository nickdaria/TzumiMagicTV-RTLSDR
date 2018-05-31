package com.eardatek.special.player.p005i;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.widget.Toast;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.system.DTVApplication;

public class C0548m {

    public static class C0547a extends BroadcastReceiver {
        private C0453a f470a;

        public interface C0453a {
            void mo2094a();
        }

        public C0547a(C0453a c0453a) {
            this.f470a = c0453a;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.wifi.STATE_CHANGE".equals(intent.getAction()) && !DTVApplication.m753c().toString().equals(C0548m.m704b(DTVApplication.m750a())) && this.f470a != null) {
                this.f470a.mo2094a();
            }
        }
    }

    public static void m702a(Activity activity) {
        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, 1);
        } else {
            Toast.makeText(DTVApplication.m750a(), R.string.wifi_err_tips, 0).show();
        }
    }

    public static boolean m703a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        return (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null || connectivityManager.getActiveNetworkInfo().getType() != 1) ? false : true;
    }

    public static String m704b(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        return wifiManager != null ? wifiManager.getConnectionInfo().getSSID() : "";
    }
}
