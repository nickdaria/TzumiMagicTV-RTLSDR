package com.eardatek.special.player.actitivy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0466t;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.p005i.C0546l;
import com.eardatek.special.player.p005i.C0549n;
import com.eardatek.special.player.system.DTVApplication;
import com.eardatek.special.player.widget.CustomToolbar;
import com.eardatek.special.player.widget.ProgressWheel;
import com.kyleduo.switchbutton.SwitchButton;
import com.sherwin.eardatek.labswitch.C0589a;
import java.lang.ref.WeakReference;
import java.util.Locale;

public class SettingActivity extends SwipeBackBaseActicity {
    private static final String f320a = SettingActivity.class.getSimpleName();
    private SwitchButton f321b;
    private SwitchButton f322c;
    private SwitchButton f323d;
    private TextView f324e;
    private TextView f325f;
    private ProgressWheel f326g;
    private ProgressWheel f327h;
    private TextView f328i;
    private TextView f329j;
    private Button f330k;
    private int f331l;
    private C0493a f332m;
    private Thread f333n;

    class C04881 implements OnCheckedChangeListener {
        final /* synthetic */ SettingActivity f314a;

        C04881(SettingActivity settingActivity) {
            this.f314a = settingActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (z) {
                if (this.f314a.f322c.isChecked()) {
                    this.f314a.f322c.setChecked(false);
                    this.f314a.f322c.setBackColorRes(R.color.grey700);
                    this.f314a.f322c.setClickable(true);
                }
                if (this.f314a.f323d.isChecked()) {
                    this.f314a.f323d.setChecked(false);
                    this.f314a.f323d.setBackColorRes(R.color.grey700);
                    this.f314a.f323d.setClickable(true);
                }
                this.f314a.f321b.setBackColorRes(R.color.cpb_blue_dark);
                Message obtainMessage = this.f314a.f332m.obtainMessage(2);
                obtainMessage.arg1 = 2;
                this.f314a.f332m.sendMessageDelayed(obtainMessage, 1500);
            }
        }
    }

    class C04892 implements OnCheckedChangeListener {
        final /* synthetic */ SettingActivity f315a;

        C04892(SettingActivity settingActivity) {
            this.f315a = settingActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (z) {
                if (this.f315a.f321b.isChecked()) {
                    this.f315a.f321b.setChecked(false);
                    this.f315a.f321b.setBackColorRes(R.color.grey700);
                    this.f315a.f321b.setClickable(true);
                }
                if (this.f315a.f323d.isChecked()) {
                    this.f315a.f323d.setChecked(false);
                    this.f315a.f323d.setBackColorRes(R.color.grey700);
                    this.f315a.f323d.setClickable(true);
                }
                this.f315a.f322c.setBackColorRes(R.color.cpb_blue_dark);
                Message obtainMessage = this.f315a.f332m.obtainMessage(2);
                obtainMessage.arg1 = 1;
                C0539i.m643b(SettingActivity.f320a, "change to locale:" + obtainMessage.arg1);
                this.f315a.f332m.sendMessageDelayed(obtainMessage, 1000);
            }
        }
    }

    class C04903 implements OnCheckedChangeListener {
        final /* synthetic */ SettingActivity f316a;

        C04903(SettingActivity settingActivity) {
            this.f316a = settingActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            if (z) {
                if (this.f316a.f321b.isChecked()) {
                    this.f316a.f321b.setChecked(false);
                    this.f316a.f321b.setBackColorRes(R.color.grey700);
                    this.f316a.f321b.setClickable(true);
                }
                if (this.f316a.f322c.isChecked()) {
                    this.f316a.f322c.setChecked(false);
                    this.f316a.f322c.setBackColorRes(R.color.grey700);
                    this.f316a.f322c.setClickable(true);
                }
                this.f316a.f323d.setBackColorRes(R.color.cpb_blue_dark);
                Message obtainMessage = this.f316a.f332m.obtainMessage(2);
                obtainMessage.arg1 = 3;
                C0539i.m643b(SettingActivity.f320a, "change to locale:" + obtainMessage.arg1);
                this.f316a.f332m.sendMessageDelayed(obtainMessage, 1000);
            }
        }
    }

    class C04914 implements OnClickListener {
        final /* synthetic */ SettingActivity f317a;

        C04914(SettingActivity settingActivity) {
            this.f317a = settingActivity;
        }

        public void onClick(View view) {
            this.f317a.m465e();
        }
    }

    class C04925 implements OnClickListener {
        final /* synthetic */ SettingActivity f318a;

        C04925(SettingActivity settingActivity) {
            this.f318a = settingActivity;
        }

        public void onClick(View view) {
            C0589a.m788a().m789a(this.f318a);
        }
    }

    private static final class C0493a extends C0466t<SettingActivity> {
        C0493a(SettingActivity settingActivity) {
            super(settingActivity);
        }

        public void handleMessage(Message message) {
            SettingActivity settingActivity = (SettingActivity) m217a();
            if (settingActivity != null) {
                switch (message.what) {
                    case 1:
                        settingActivity.m457a(message.arg1, message.arg2, (String) message.obj);
                        return;
                    case 2:
                        settingActivity.m456a(message.arg1);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private static final class C0494b implements Runnable {
        private WeakReference<SettingActivity> f319a;

        C0494b(SettingActivity settingActivity) {
            this.f319a = new WeakReference(settingActivity);
        }

        public void run() {
            SettingActivity settingActivity = (SettingActivity) this.f319a.get();
            if (settingActivity != null) {
                while (!settingActivity.f333n.isInterrupted()) {
                    if (C0546l.m653a().m695i()) {
                        int j = C0546l.m653a().m696j();
                        Message obtainMessage = settingActivity.f332m.obtainMessage(1);
                        obtainMessage.arg1 = j;
                        obtainMessage.arg2 = C0546l.m653a().m697k();
                        obtainMessage.obj = C0546l.m653a().m698l();
                        settingActivity.f332m.sendMessage(obtainMessage);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }

    private void m456a(int i) {
        Resources resources = getApplication().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (i) {
            case 0:
                if (VERSION.SDK_INT < 17) {
                    configuration.locale = Locale.getDefault();
                    break;
                } else {
                    configuration.setLocale(Locale.getDefault());
                    break;
                }
            case 1:
                if (VERSION.SDK_INT < 17) {
                    configuration.locale = Locale.US;
                    break;
                } else {
                    configuration.setLocale(Locale.US);
                    break;
                }
            case 2:
                if (VERSION.SDK_INT < 17) {
                    configuration.locale = Locale.SIMPLIFIED_CHINESE;
                    break;
                } else {
                    configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
                    break;
                }
            case 3:
                if (VERSION.SDK_INT < 17) {
                    configuration.locale = DTVApplication.f502a;
                    break;
                } else {
                    configuration.setLocale(DTVApplication.f502a);
                    break;
                }
            default:
                if (VERSION.SDK_INT < 17) {
                    configuration.locale = Locale.getDefault();
                    break;
                } else {
                    configuration.setLocale(Locale.getDefault());
                    break;
                }
        }
        C0539i.m643b(f320a, "Locale:" + configuration.locale.toString());
        C0549n.m706a((Context) this, "language_key", i);
        resources.updateConfiguration(configuration, displayMetrics);
        Intent intent = new Intent(this, EardatekVersion2Activity.class);
        intent.setFlags(268468224);
        startActivity(intent);
        Process.killProcess(Process.myPid());
        System.exit(0);
    }

    private void m457a(int i, int i2, String str) {
        C0539i.m643b(f320a, "updateSignalStatus");
        this.f329j.setText(String.format(Locale.ENGLISH, "%d%%", new Object[]{Integer.valueOf(i2)}));
        this.f328i.setText(String.format(Locale.ENGLISH, "%ddbm", new Object[]{Integer.valueOf(i)}));
        if (!TextUtils.isEmpty(str)) {
            this.f324e.setText(String.format(Locale.ENGLISH, "%s", new Object[]{str}));
        }
        this.f327h.setProgress((int) (((double) i2) * 3.6d));
        if (i < -92) {
            i = -92;
        }
        int i3 = i < 0 ? (int) (((double) (i + 100)) * 3.6d) : i == 0 ? 324 : (int) (((double) (100 - i)) * 3.6d);
        this.f326g.setProgress(i3);
    }

    public static void m458a(Context context, int i) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.putExtra("current_freq", i);
        context.startActivity(intent);
    }

    private void m462b() {
        CustomToolbar customToolbar = (CustomToolbar) findViewById(R.id.toolbar_setting);
        this.f321b = (SwitchButton) findViewById(R.id.def_switchbtn);
        this.f322c = (SwitchButton) findViewById(R.id.english_switchbtn);
        this.f323d = (SwitchButton) findViewById(R.id.russian_switchbtn);
        TextView textView = (TextView) findViewById(R.id.english_text);
        TextView textView2 = (TextView) findViewById(R.id.china_text);
        TextView textView3 = (TextView) findViewById(R.id.russian_text);
        TextView textView4 = (TextView) findViewById(R.id.tv_type_text);
        TextView textView5 = (TextView) findViewById(R.id.current_freq);
        this.f324e = (TextView) findViewById(R.id.text_qam);
        this.f326g = (ProgressWheel) findViewById(R.id.signal_strength_progreswheel);
        this.f327h = (ProgressWheel) findViewById(R.id.freq_quality_progressWheel);
        this.f328i = (TextView) findViewById(R.id.text_signal_strength);
        this.f329j = (TextView) findViewById(R.id.text_quality);
        if (!C0549n.m705a(this, "device_type").equals("ISDBT")) {
            textView4.setText(C0549n.m705a(this, "device_type"));
        } else if (C0549n.m710b(DTVApplication.m750a(), "mode", 0) == 0) {
            textView4.setText(R.string.isdbt_mde);
        } else {
            textView4.setText(R.string.dvb_mode);
        }
        this.f321b.setAnimationDuration(500);
        this.f322c.setAnimationDuration(500);
        this.f323d.setAnimationDuration(500);
        C0539i.m643b(f320a, "current locale:" + getResources().getConfiguration().locale.toString());
        if (getResources().getConfiguration().locale.equals(Locale.US)) {
            textView.setText(getString(R.string.english));
            textView.setTextColor(getResources().getColor(R.color.cpb_blue_dark));
            textView2.setTextColor(getResources().getColor(R.color.white));
            textView3.setTextColor(getResources().getColor(R.color.white));
            this.f322c.setCheckedImmediatelyNoEvent(true);
            this.f322c.setBackColorRes(R.color.cpb_blue_dark);
            this.f322c.setClickable(false);
            this.f321b.setCheckedImmediatelyNoEvent(false);
            this.f321b.setBackColorRes(R.color.grey700);
            this.f323d.setCheckedImmediatelyNoEvent(false);
            this.f323d.setBackColorRes(R.color.grey700);
        } else if (getResources().getConfiguration().locale.equals(DTVApplication.f502a)) {
            textView3.setText(getString(R.string.russian));
            textView3.setTextColor(getResources().getColor(R.color.cpb_blue_dark));
            textView2.setTextColor(getResources().getColor(R.color.white));
            textView.setTextColor(getResources().getColor(R.color.white));
            this.f323d.setCheckedImmediatelyNoEvent(true);
            this.f323d.setBackColorRes(R.color.cpb_blue_dark);
            this.f323d.setClickable(false);
            this.f321b.setCheckedImmediatelyNoEvent(false);
            this.f321b.setBackColorRes(R.color.grey700);
            this.f322c.setCheckedImmediatelyNoEvent(false);
            this.f322c.setBackColorRes(R.color.grey700);
        } else {
            textView2.setText(getString(R.string.chinese));
            textView2.setTextColor(getResources().getColor(R.color.cpb_blue_dark));
            textView.setTextColor(getResources().getColor(R.color.white));
            textView3.setTextColor(getResources().getColor(R.color.white));
            this.f321b.setCheckedImmediatelyNoEvent(true);
            this.f321b.setBackColorRes(R.color.cpb_blue_dark);
            this.f321b.setClickable(false);
            this.f322c.setCheckedImmediatelyNoEvent(false);
            this.f322c.setBackColorRes(R.color.grey700);
            this.f323d.setCheckedImmediatelyNoEvent(false);
            this.f323d.setBackColorRes(R.color.grey700);
        }
        this.f321b.setOnCheckedChangeListener(new C04881(this));
        this.f322c.setOnCheckedChangeListener(new C04892(this));
        this.f323d.setOnCheckedChangeListener(new C04903(this));
        customToolbar.setNavigationOnClickListener(new C04914(this));
        this.f332m = new C0493a(this);
        if (this.f331l > 0) {
            textView5.setText(String.format(Locale.ENGLISH, "%dMHz", new Object[]{Integer.valueOf(this.f331l / 1000)}));
        } else {
            textView5.setText(getString(R.string.nolockfreq));
            this.f329j.setText(getString(R.string.nolock));
            this.f328i.setText(getString(R.string.nolock));
        }
        if (this.f331l > 0) {
            this.f333n = new Thread(new C0494b(this));
            this.f333n.start();
        }
        this.f325f = (TextView) findViewById(R.id.tv_firmware_version_text);
        if (C0546l.m653a().m700n()) {
            this.f325f.setText("v" + C0549n.m705a(DTVApplication.m750a(), "software_version"));
        }
        if (C0589a.m788a().m805i()) {
            this.f330k = (Button) findViewById(R.id.function_lab);
            this.f330k.setVisibility(0);
            this.f330k.setOnClickListener(new C04925(this));
        }
    }

    private void m465e() {
        if (this.f333n != null && this.f333n.isAlive()) {
            this.f333n.interrupt();
        }
        if (this.f332m != null) {
            this.f332m.removeCallbacksAndMessages(null);
        }
        finish();
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_setting);
        if (getIntent() != null) {
            this.f331l = getIntent().getIntExtra("current_freq", 0);
        }
        m462b();
    }

    protected void onDestroy() {
        super.onDestroy();
        m465e();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        m465e();
        return true;
    }
}
