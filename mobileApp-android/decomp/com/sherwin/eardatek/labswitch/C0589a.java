package com.sherwin.eardatek.labswitch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.sherwin.eardatek.labswitch.p012a.C0587a;
import com.sherwin.eardatek.labswitch.p012a.C0588b;

public class C0589a {
    private static C0589a f609a = new C0589a();
    private Context f610b;
    private boolean f611c = false;
    private boolean f612d = false;
    private boolean f613e = false;
    private boolean f614f = false;
    private boolean f615g = true;
    private boolean f616h = false;
    private boolean f617i = false;
    private boolean f618j = false;
    private boolean f619k;
    private boolean f620l;
    private boolean f621m;
    private boolean f622n;
    private boolean f623o;
    private boolean f624p;
    private boolean f625q;
    private Dialog f626r;

    private C0589a() {
    }

    public static C0589a m788a() {
        return f609a;
    }

    public void m789a(Activity activity) {
        if (this.f626r == null || !this.f626r.isShowing()) {
            View inflate = activity.getLayoutInflater().inflate(C0585R.layout.layout_setting, null);
            this.f626r = new Dialog(activity, C0585R.style.transparentFrameWindowStyle);
            final CheckBox checkBox = (CheckBox) inflate.findViewById(C0585R.id.lab_bridge_mode_choice);
            final CheckBox checkBox2 = (CheckBox) inflate.findViewById(C0585R.id.lab_show_strength_choice);
            final CheckBox checkBox3 = (CheckBox) inflate.findViewById(C0585R.id.lab_record_mode_choice);
            final CheckBox checkBox4 = (CheckBox) inflate.findViewById(C0585R.id.lab_show_codec_choice);
            final CheckBox checkBox5 = (CheckBox) inflate.findViewById(C0585R.id.lab_show_epg_choice);
            final CheckBox checkBox6 = (CheckBox) inflate.findViewById(C0585R.id.lab_support_wifi_choice);
            final CheckBox checkBox7 = (CheckBox) inflate.findViewById(C0585R.id.lab_support_all_wifi_choice);
            ((TextView) inflate.findViewById(C0585R.id.lab_version)).setText("Release Version");
            OnCheckedChangeListener c05861 = new OnCheckedChangeListener(this) {
                final /* synthetic */ C0589a f606h;

                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    if (compoundButton == checkBox) {
                        this.f606h.m791a(z);
                    } else if (compoundButton == checkBox2) {
                        if (z) {
                            this.f606h.m798e(false);
                        }
                        this.f606h.m792b(z);
                    } else if (compoundButton == checkBox3) {
                        this.f606h.m800f(z);
                    } else if (compoundButton == checkBox4) {
                        this.f606h.m798e(z);
                    } else if (compoundButton == checkBox5) {
                        this.f606h.m802g(z);
                    } else if (compoundButton == checkBox7) {
                        this.f606h.m796d(z);
                    } else if (compoundButton == checkBox6) {
                        this.f606h.m794c(z);
                    }
                }
            };
            checkBox.setChecked(this.f619k);
            checkBox2.setChecked(this.f620l);
            checkBox3.setChecked(this.f624p);
            checkBox4.setChecked(this.f621m);
            checkBox5.setChecked(this.f625q);
            checkBox7.setChecked(this.f623o);
            checkBox6.setChecked(this.f622n);
            checkBox.setOnCheckedChangeListener(c05861);
            checkBox2.setOnCheckedChangeListener(c05861);
            checkBox3.setOnCheckedChangeListener(c05861);
            checkBox4.setOnCheckedChangeListener(c05861);
            checkBox5.setOnCheckedChangeListener(c05861);
            checkBox7.setOnCheckedChangeListener(c05861);
            checkBox6.setOnCheckedChangeListener(c05861);
            this.f626r.setContentView(inflate, new LayoutParams(-1, -2));
            Window window = this.f626r.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(C0585R.style.main_menu_animstyle);
            WindowManager.LayoutParams attributes = window.getAttributes();
            activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            attributes.x = 0;
            attributes.y = 0;
            attributes.width = C0587a.m785a(activity, 345.0f);
            attributes.height = -2;
            attributes.alpha = 0.9f;
            this.f626r.onWindowAttributesChanged(attributes);
            this.f626r.setCanceledOnTouchOutside(true);
            this.f626r.show();
        }
    }

    public void m790a(Context context) {
        this.f610b = context;
        this.f611c = C0588b.m787b(context, "LAB_ENABLE", this.f611c);
        this.f612d = C0588b.m787b(context, "BRIDGE_ENABLE", this.f612d);
        this.f619k = this.f612d;
        this.f613e = C0588b.m787b(context, "SHOW_STRENGTH_ENABLE", this.f613e);
        this.f620l = this.f613e;
        this.f614f = C0588b.m787b(context, "SHOW_CODEC_ENABLE", this.f614f);
        this.f621m = this.f614f;
        this.f618j = C0588b.m787b(context, "SHOW_EPG_ENABLE", this.f618j);
        this.f625q = this.f618j;
        this.f615g = C0588b.m787b(context, "MORE_WIFI_NAME_ENABLE", this.f615g);
        this.f622n = this.f615g;
        this.f616h = C0588b.m787b(context, "ALL_WIFI_NAME_ENABLE", this.f616h);
        this.f623o = this.f616h;
        this.f617i = C0588b.m787b(context, "RECORD_ENABLE", this.f617i);
        this.f624p = this.f617i;
    }

    public void m791a(boolean z) {
        this.f619k = z;
        C0588b.m786a(this.f610b, "BRIDGE_ENABLE", z);
    }

    public void m792b(boolean z) {
        this.f620l = z;
        C0588b.m786a(this.f610b, "SHOW_STRENGTH_ENABLE", z);
    }

    public boolean m793b() {
        return this.f612d;
    }

    public void m794c(boolean z) {
        this.f622n = z;
        C0588b.m786a(this.f610b, "MORE_WIFI_NAME_ENABLE", z);
    }

    public boolean m795c() {
        return this.f613e;
    }

    public void m796d(boolean z) {
        this.f623o = z;
        C0588b.m786a(this.f610b, "ALL_WIFI_NAME_ENABLE", z);
    }

    public boolean m797d() {
        return this.f615g;
    }

    public void m798e(boolean z) {
        this.f621m = z;
        C0588b.m786a(this.f610b, "SHOW_CODEC_ENABLE", z);
    }

    public boolean m799e() {
        return this.f616h;
    }

    public void m800f(boolean z) {
        this.f624p = z;
        C0588b.m786a(this.f610b, "RECORD_ENABLE", z);
    }

    public boolean m801f() {
        return this.f614f;
    }

    public void m802g(boolean z) {
        this.f621m = z;
        C0588b.m786a(this.f610b, "SHOW_EPG_ENABLE", z);
    }

    public boolean m803g() {
        return this.f617i;
    }

    public boolean m804h() {
        return this.f618j;
    }

    public boolean m805i() {
        return this.f611c;
    }
}
