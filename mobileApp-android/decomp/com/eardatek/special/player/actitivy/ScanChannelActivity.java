package com.eardatek.special.player.actitivy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0466t;
import com.eardatek.special.player.p005i.C0530b;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.system.DTVApplication;
import com.eardatek.special.player.widget.CustomToolbar;
import com.eardatek.special.player.widget.ProgressWheel;
import java.util.Locale;

public class ScanChannelActivity extends SwipeBackBaseActicity {
    public static final String f305a = ScanChannelActivity.class.getSimpleName();
    private C0530b f306b;
    private TextView f307c;
    private TextView f308d;
    private ProgressWheel f309e;
    private int f310f = 0;
    private int f311g;
    private Handler f312h = new C0487a(this);
    private long f313i = 0;

    class C04861 implements OnClickListener {
        final /* synthetic */ ScanChannelActivity f304a;

        C04861(ScanChannelActivity scanChannelActivity) {
            this.f304a = scanChannelActivity;
        }

        public void onClick(View view) {
            this.f304a.f306b.m620b();
        }
    }

    private static class C0487a extends C0466t<ScanChannelActivity> {
        C0487a(ScanChannelActivity scanChannelActivity) {
            super(scanChannelActivity);
        }

        public void handleMessage(Message message) {
            ScanChannelActivity scanChannelActivity = (ScanChannelActivity) m217a();
            if (scanChannelActivity != null) {
                switch (message.what) {
                    case 10:
                        scanChannelActivity.f311g = message.arg1 / 1000;
                        String format = String.format(Locale.ENGLISH, "%dMHz", new Object[]{Integer.valueOf(message.arg1 / 1000)});
                        String format2 = String.format(Locale.ENGLISH, "%d", new Object[]{Integer.valueOf(message.arg2 % 10000)});
                        int i = (int) (((double) (message.arg2 / 10000)) * 3.6d);
                        C0539i.m643b(ScanChannelActivity.f305a, "progress:" + i);
                        scanChannelActivity.m452a(format2, format);
                        scanChannelActivity.m451a(i);
                        return;
                    case 11:
                        if (message.arg1 == 0) {
                            Toast.makeText(DTVApplication.m750a(), DTVApplication.m752b().getString(R.string.nochannletips), 0).show();
                        }
                        scanChannelActivity.m452a(String.valueOf(message.arg1), String.format(Locale.ENGLISH, "%dMHz", new Object[]{Integer.valueOf(scanChannelActivity.f311g)}));
                        scanChannelActivity.m451a(360);
                        return;
                    case 12:
                        scanChannelActivity.setResult(0);
                        if (scanChannelActivity.f312h != null) {
                            scanChannelActivity.f312h.removeCallbacksAndMessages(null);
                        }
                        C0539i.m643b(ScanChannelActivity.f305a, "scan finish...");
                        scanChannelActivity.finish();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void m449e() {
        ((CustomToolbar) findViewById(R.id.toolbar_scan)).setNavigationOnClickListener(new C04861(this));
    }

    public int m450a() {
        return this.f310f;
    }

    public void m451a(int i) {
        this.f309e.setProgress(i);
    }

    public void m452a(String str, String str2) {
        if (str != null) {
            this.f307c.setText(str);
        }
        if (str2 != null) {
            this.f308d.setText(str2);
        }
    }

    public void m453b() {
        this.f306b = new C0530b(this.f312h, this);
        this.f306b.m619a();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(128, 128);
        setContentView((int) R.layout.activity_scan);
        m449e();
        this.f307c = (TextView) findViewById(R.id.found_channels);
        this.f308d = (TextView) findViewById(R.id.scan_freq);
        this.f309e = (ProgressWheel) findViewById(R.id.scan_progreswheel);
        this.f310f = (int) getIntent().getFloatExtra("advance_search", 0.0f);
        m453b();
    }

    protected void onDestroy() {
        super.onDestroy();
        C0539i.m643b(f305a, "ScanChannelActivity destroy");
        this.f306b.m620b();
        this.f312h.removeCallbacksAndMessages(null);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        if (System.currentTimeMillis() - this.f313i > 3000) {
            Toast.makeText(getApplicationContext(), R.string.scantips, 0).show();
            this.f313i = System.currentTimeMillis();
        } else {
            this.f306b.m620b();
            this.f312h.removeCallbacksAndMessages(null);
            finish();
        }
        return true;
    }
}
