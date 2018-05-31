package com.eardatek.special.player.p006c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.PowerManager;

public class C0505b {
    private Context f353a;
    private C0504a f354b = new C0504a();
    private C0450b f355c;

    public interface C0450b {
        void mo2089a();

        void mo2090b();

        void mo2091c();
    }

    private class C0504a extends BroadcastReceiver {
        final /* synthetic */ C0505b f351a;
        private String f352b;

        private C0504a(C0505b c0505b) {
            this.f351a = c0505b;
            this.f352b = null;
        }

        public void onReceive(Context context, Intent intent) {
            this.f352b = intent.getAction();
            if ("android.intent.action.SCREEN_OFF".equals(this.f352b)) {
                this.f351a.f355c.mo2090b();
            } else if ("android.intent.action.SCREEN_ON".equals(this.f352b)) {
                this.f351a.f355c.mo2089a();
            } else if ("android.intent.action.USER_PRESENT".equals(this.f352b)) {
                this.f351a.f355c.mo2091c();
            }
        }
    }

    public C0505b(Context context) {
        this.f353a = context;
    }

    private void m510b() {
        PowerManager powerManager = (PowerManager) this.f353a.getSystemService("power");
        if (VERSION.SDK_INT >= 20 ? powerManager.isInteractive() : powerManager.isScreenOn()) {
            if (this.f355c != null) {
                this.f355c.mo2089a();
            }
        } else if (this.f355c != null) {
            this.f355c.mo2090b();
        }
    }

    private void m511c() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        this.f353a.registerReceiver(this.f354b, intentFilter);
    }

    public void m512a() {
        this.f353a.unregisterReceiver(this.f354b);
    }

    public void m513a(C0450b c0450b) {
        this.f355c = c0450b;
        m511c();
        m510b();
    }
}
