package com.eardatek.special.player.p005i;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.system.DTVApplication;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public abstract class C0455r {
    public static final String f176b = (Environment.getExternalStorageDirectory() + "/" + "Tevemo");
    private String f177a;
    private String f178c;
    private File f179d;
    private OutputStream f180e;
    private LinearLayout f181f;
    private ImageView f182g;
    private TextView f183h;
    private Button f184i;
    private Button f185j;
    private Button f186k;
    private Timer f187l;
    private int f188m;
    private boolean f189n;
    private boolean f190o;
    private Handler f191p = new C05574(this);

    class C05541 implements OnClickListener {
        final /* synthetic */ C0455r f487a;

        C05541(C0455r c0455r) {
            this.f487a = c0455r;
        }

        public void onClick(View view) {
            if (this.f487a.f188m != 0) {
                this.f487a.f188m = 0;
                this.f487a.m207e();
                this.f487a.m202c();
                this.f487a.f186k.setText(DTVApplication.m752b().getString(R.string.record_label));
                this.f487a.f181f.setVisibility(8);
            } else if (this.f487a.m199b()) {
                this.f487a.f188m = 1;
                this.f487a.f189n = false;
                this.f487a.f190o = true;
                this.f487a.m205d();
                this.f487a.f186k.setText(DTVApplication.m752b().getString(R.string.stop_record_label));
                this.f487a.f181f.setVisibility(0);
            }
        }
    }

    class C05552 implements OnClickListener {
        final /* synthetic */ C0455r f488a;

        C05552(C0455r c0455r) {
            this.f488a = c0455r;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.record_pause:
                    if (this.f488a.f188m == 1) {
                        this.f488a.f188m = 2;
                        this.f488a.f184i.setText("START");
                        return;
                    }
                    this.f488a.f188m = 1;
                    this.f488a.f184i.setText("PAUSE");
                    return;
                case R.id.record_stop:
                    this.f488a.f188m = 0;
                    this.f488a.m207e();
                    this.f488a.m202c();
                    this.f488a.f186k.setText(DTVApplication.m752b().getString(R.string.record_label));
                    this.f488a.f181f.setVisibility(8);
                    return;
                default:
                    return;
            }
        }
    }

    class C05563 extends TimerTask {
        final /* synthetic */ C0455r f489a;

        C05563(C0455r c0455r) {
            this.f489a = c0455r;
        }

        public void run() {
            if (this.f489a.f188m == 1) {
                this.f489a.f191p.sendEmptyMessage(1);
            }
        }
    }

    class C05574 extends Handler {
        final /* synthetic */ C0455r f490a;
        private int f491b;

        C05574(C0455r c0455r) {
            this.f490a = c0455r;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    this.f491b = 0;
                    break;
                case 1:
                    this.f491b++;
                    break;
            }
            int i = this.f491b / 60;
            int i2 = this.f491b % 60;
            this.f490a.f183h.setText(String.format(Locale.ENGLISH, "%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
        }
    }

    public C0455r(LinearLayout linearLayout, Button button) {
        this.f181f = linearLayout;
        this.f186k = button;
        this.f186k.setVisibility(4);
        this.f186k.setOnClickListener(new C05541(this));
        OnClickListener c05552 = new C05552(this);
        this.f182g = (ImageView) this.f181f.findViewById(R.id.record_image);
        this.f183h = (TextView) this.f181f.findViewById(R.id.record_time);
        this.f184i = (Button) this.f181f.findViewById(R.id.record_pause);
        this.f185j = (Button) this.f181f.findViewById(R.id.record_stop);
        this.f185j.setOnClickListener(c05552);
        this.f184i.setOnClickListener(c05552);
        this.f183h.setText("00:00");
        this.f181f.setVisibility(8);
    }

    private int m198b(byte[] bArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] == (byte) 71) {
                return i2;
            }
        }
        return 0;
    }

    private boolean m199b() {
        File file = new File(f176b);
        if (!(file.exists() || file.isDirectory())) {
            file.mkdir();
        }
        this.f178c = mo2097a();
        if (this.f178c == null || this.f178c.isEmpty()) {
            return false;
        }
        this.f177a = f176b + "/" + this.f178c;
        this.f179d = new File(this.f177a);
        if (this.f179d.exists()) {
            this.f179d.delete();
        }
        try {
            this.f179d.createNewFile();
            this.f180e = new FileOutputStream(this.f179d);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void m202c() {
        try {
            this.f180e.flush();
        } catch (IOException e) {
        }
        try {
            this.f180e.close();
            this.f179d = null;
        } catch (IOException e2) {
        }
        if (this.f189n) {
            Toast.makeText(DTVApplication.m750a(), DTVApplication.m752b().getString(R.string.record_error), 1).show();
        } else {
            Toast.makeText(DTVApplication.m750a(), DTVApplication.m752b().getString(R.string.record_finish) + this.f177a, 1).show();
        }
    }

    private void m205d() {
        this.f191p.sendEmptyMessage(0);
        this.f187l = new Timer();
        this.f187l.schedule(new C05563(this), 0, 1000);
    }

    private void m207e() {
        this.f187l.cancel();
        this.f187l = null;
        this.f191p.sendEmptyMessage(0);
    }

    public abstract String mo2097a();

    public boolean m214a(byte[] bArr, int i) {
        if (this.f188m == 1) {
            try {
                int b;
                if (this.f190o) {
                    b = m198b(bArr, i);
                    this.f190o = false;
                } else {
                    b = 0;
                }
                this.f180e.write(bArr, b, i - b);
            } catch (IOException e) {
                this.f189n = true;
                this.f186k.callOnClick();
            }
        }
        return false;
    }
}
