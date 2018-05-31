package com.eardatek.special.player.actitivy;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0533d;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.p005i.C0540j;
import com.eardatek.special.player.p005i.C0546l;
import com.eardatek.special.player.p005i.C0546l.C0482c;
import com.eardatek.special.player.p005i.C0548m;
import com.eardatek.special.player.p005i.C0549n;
import com.eardatek.special.player.p005i.C0559u;
import com.eardatek.special.player.p008d.C0506a;
import com.eardatek.special.player.system.DTVApplication;
import com.eardatek.special.player.widget.ProgressWheel;
import com.sherwin.eardatek.labswitch.C0589a;
import com.sherwin.tvbox.firmwareupdateclient.C0478a;
import com.sherwin.tvbox.firmwareupdateclient.C0478a.C0480a;
import java.lang.ref.WeakReference;

public class HomePageActivity extends BaseActivity {
    static final /* synthetic */ boolean f291a = (!HomePageActivity.class.desiredAssertionStatus());
    private static final String f292b = HomePageActivity.class.getSimpleName();
    private C0485a f293c;
    private ProgressWheel f294d;
    private TextView f295e;
    private Button f296f;
    private ImageView f297g;
    private String f298h;
    private String f299i;
    private C0478a f300j;
    private Button f301k = null;
    private Comparable<Boolean> f302l = new C04741(this);
    private Dialog f303m;

    class C04741 implements Comparable<Boolean> {
        final /* synthetic */ HomePageActivity f268a;

        C04741(HomePageActivity homePageActivity) {
            this.f268a = homePageActivity;
        }

        public int m361a(@NonNull Boolean bool) {
            C0540j.m647a(this.f268a.f293c, 3, bool);
            return 0;
        }

        public /* synthetic */ int compareTo(@NonNull Object obj) {
            return m361a((Boolean) obj);
        }
    }

    class C04752 implements OnClickListener {
        final /* synthetic */ HomePageActivity f269a;

        C04752(HomePageActivity homePageActivity) {
            this.f269a = homePageActivity;
        }

        public void onClick(View view) {
            BridgeSettingActivity.m138a(this.f269a);
            this.f269a.overridePendingTransition(R.anim.photo_dialog_in_anim, R.anim.photo_dialog_out_anim);
        }
    }

    class C04763 implements OnClickListener {
        final /* synthetic */ HomePageActivity f270a;

        C04763(HomePageActivity homePageActivity) {
            this.f270a = homePageActivity;
        }

        public void onClick(View view) {
            this.f270a.m437h();
        }
    }

    class C04774 implements OnClickListener {
        final /* synthetic */ HomePageActivity f271a;

        C04774(HomePageActivity homePageActivity) {
            this.f271a = homePageActivity;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onClick(android.view.View r6) {
            /*
            r5 = this;
            r0 = r5.f271a;
            r0 = r0.f296f;
            r0 = r0.getTag();
            r0 = (java.lang.String) r0;
            r1 = r5.f271a;
            r1 = r1.getApplicationContext();
            r2 = com.eardatek.special.player.p005i.C0548m.m704b(r1);
            r1 = com.eardatek.special.player.actitivy.HomePageActivity.f292b;
            r3 = new java.lang.StringBuilder;
            r3.<init>();
            r4 = "next tag:";
            r3 = r3.append(r4);
            r3 = r3.append(r0);
            r3 = r3.toString();
            com.eardatek.special.player.p005i.C0539i.m643b(r1, r3);
            r1 = -1;
            r3 = r0.hashCode();
            switch(r3) {
                case 3377907: goto L_0x0056;
                case 1914106186: goto L_0x0060;
                default: goto L_0x0038;
            };
        L_0x0038:
            r0 = r1;
        L_0x0039:
            switch(r0) {
                case 0: goto L_0x006a;
                case 1: goto L_0x008f;
                default: goto L_0x003c;
            };
        L_0x003c:
            r0 = com.eardatek.special.player.p005i.C0546l.m653a();
            r1 = com.eardatek.special.player.p008d.C0506a.f356a;
            r2 = com.eardatek.special.player.p008d.C0506a.f357b;
            r3 = com.eardatek.special.player.p008d.C0506a.f359d;
            r4 = com.eardatek.special.player.p008d.C0506a.f358c;
            r0.m685a(r1, r2, r3, r4);
            r0 = r5.f271a;
            r0 = r0.f293c;
            r1 = 2;
            r0.sendEmptyMessage(r1);
        L_0x0055:
            return;
        L_0x0056:
            r3 = "next";
            r0 = r0.equals(r3);
            if (r0 == 0) goto L_0x0038;
        L_0x005e:
            r0 = 0;
            goto L_0x0039;
        L_0x0060:
            r3 = "connect_wifi";
            r0 = r0.equals(r3);
            if (r0 == 0) goto L_0x0038;
        L_0x0068:
            r0 = 1;
            goto L_0x0039;
        L_0x006a:
            r0 = com.eardatek.special.player.p005i.C0546l.m663c(r2);
            if (r0 == 0) goto L_0x0055;
        L_0x0070:
            r0 = new android.content.Intent;
            r1 = r5.f271a;
            r2 = com.eardatek.special.player.actitivy.EardatekVersion2Activity.class;
            r0.<init>(r1, r2);
            r1 = r5.f271a;
            r1.startActivity(r0);
            r0 = r5.f271a;
            r1 = 2131034135; // 0x7f050017 float:1.767878E38 double:1.052870756E-314;
            r2 = 2131034136; // 0x7f050018 float:1.7678781E38 double:1.0528707567E-314;
            r0.overridePendingTransition(r1, r2);
            r0 = r5.f271a;
            r0.finish();
            goto L_0x0055;
        L_0x008f:
            r0 = new com.eardatek.special.player.i.u;
            r1 = r5.f271a;
            r0.<init>(r1);
            r0.m734a();
            r0 = r5.f271a;
            com.eardatek.special.player.p005i.C0548m.m702a(r0);
            r0 = r5.f271a;
            r0 = r0.f293c;
            r1 = 10;
            r2 = 4000; // 0xfa0 float:5.605E-42 double:1.9763E-320;
            r0.sendEmptyMessageDelayed(r1, r2);
            goto L_0x0055;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.eardatek.special.player.actitivy.HomePageActivity.4.onClick(android.view.View):void");
        }
    }

    class C04816 implements C0480a {
        final /* synthetic */ HomePageActivity f285a;

        C04816(HomePageActivity homePageActivity) {
            this.f285a = homePageActivity;
        }

        public void mo2105a() {
            this.f285a.f293c.sendEmptyMessageDelayed(2, 1500);
        }

        public void mo2106a(String str) {
            C0539i.m644c(HomePageActivity.f292b, str);
            this.f285a.f293c.sendEmptyMessageDelayed(2, 500);
        }

        public void mo2107b() {
            this.f285a.f293c.sendEmptyMessageDelayed(2, 500);
        }
    }

    class C04837 implements C0482c {
        final /* synthetic */ HomePageActivity f286a;
        private boolean f287b = true;

        C04837(HomePageActivity homePageActivity) {
            this.f286a = homePageActivity;
        }

        public void mo2108a(boolean z) {
            if (!this.f287b) {
                return;
            }
            if (z) {
                String a = C0549n.m705a(DTVApplication.m750a(), "device_type");
                String a2 = C0549n.m705a(DTVApplication.m750a(), "software_version");
                if (this.f286a.f298h.equals(a) && this.f286a.f299i.equals(a2)) {
                    this.f286a.f300j.m412e();
                    return;
                } else {
                    this.f286a.f300j.m410c();
                    return;
                }
            }
            this.f286a.f300j.m411d();
            this.f287b = false;
        }
    }

    class C04848 implements OnCheckedChangeListener {
        final /* synthetic */ HomePageActivity f288a;

        C04848(HomePageActivity homePageActivity) {
            this.f288a = homePageActivity;
        }

        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            C0549n.m708a(DTVApplication.m750a(), "GUIDE_TRIPS_SHOW", !z);
            if (z) {
                this.f288a.f303m.dismiss();
            }
        }
    }

    private class C0485a extends Handler {
        final /* synthetic */ HomePageActivity f289a;
        private WeakReference<HomePageActivity> f290b;

        C0485a(HomePageActivity homePageActivity, HomePageActivity homePageActivity2) {
            this.f289a = homePageActivity;
            this.f290b = new WeakReference(homePageActivity2);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            Context context = (HomePageActivity) this.f290b.get();
            if (context != null) {
                switch (message.what) {
                    case 1:
                        context.f294d.m766a();
                        context.f294d.setVisibility(4);
                        String b = C0548m.m704b(context);
                        boolean a = C0548m.m703a(context);
                        context.f296f.setClickable(true);
                        boolean n = C0546l.m653a().m700n();
                        if (C0546l.m663c(b) && n && a) {
                            context.f295e.setText(context.getText(R.string.connected));
                            context.f296f.setVisibility(0);
                            context.f296f.setTag("next");
                            context.f296f.setText(context.getText(R.string.next));
                            context.f297g.setImageResource(R.drawable.ok);
                            context.f297g.setVisibility(0);
                            context.m433f();
                            if (C0589a.m788a().m793b()) {
                                context.f301k.setVisibility(0);
                                BridgeSettingActivity.m139a(context.f302l);
                                return;
                            }
                            return;
                        } else if (C0546l.m663c(b) && !n && a) {
                            context.f295e.setText(context.getText(R.string.disconnect));
                            context.f296f.setVisibility(0);
                            context.f296f.setTag("connect");
                            context.f296f.setText(context.getText(R.string.connect));
                            context.f297g.setVisibility(0);
                            context.f297g.setImageResource(R.drawable.cancel);
                            context.m433f();
                            if (C0589a.m788a().m793b()) {
                                context.f301k.setVisibility(4);
                                return;
                            }
                            return;
                        } else if (!C0546l.m663c(b) || !a) {
                            context.f295e.setText(context.getText(R.string.disconnect));
                            context.f296f.setVisibility(0);
                            context.f296f.setTag("connect_wifi");
                            context.f296f.setText(context.getText(R.string.connect));
                            context.f297g.setVisibility(0);
                            context.f297g.setImageResource(R.drawable.cancel);
                            context.m433f();
                            if (C0589a.m788a().m793b()) {
                                context.f301k.setVisibility(4);
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                    case 2:
                        context.f294d.setVisibility(0);
                        context.f297g.setVisibility(4);
                        context.f294d.m767b();
                        context.f296f.setVisibility(4);
                        context.f295e.setText(context.getText(R.string.connecting));
                        sendEmptyMessageDelayed(1, 500);
                        return;
                    case 3:
                        if (((Boolean) message.obj).booleanValue()) {
                            C0539i.m644c(HomePageActivity.f292b, "BridgeBtn on");
                            context.f301k.setText(context.getString(R.string.bridge_home2));
                            return;
                        }
                        C0539i.m644c(HomePageActivity.f292b, "BridgeBtn off");
                        context.f301k.setText(context.getString(R.string.bridge_home));
                        return;
                    case 4:
                        this.f289a.m424a(message.obj.toString());
                        return;
                    case 10:
                        C0559u c0559u = new C0559u(this.f289a);
                        c0559u.m738c();
                        for (WifiConfiguration wifiConfiguration : c0559u.m737b()) {
                            if (wifiConfiguration.SSID.contains("Tevemo")) {
                                Toast.makeText(this.f289a, "Please connect to a WiFi prefixed with TzumiTV or @Tevemo", 1).show();
                                return;
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void m424a(String str) {
        Toast.makeText(this, str, 1).show();
    }

    private void m429d() {
        boolean z;
        this.f296f = (Button) findViewById(R.id.next);
        this.f295e = (TextView) findViewById(R.id.connecting);
        this.f294d = (ProgressWheel) findViewById(R.id.progreswheel);
        this.f297g = (ImageView) findViewById(R.id.image_state);
        ((TextView) findViewById(R.id.version_text)).setText(DTVApplication.m754d());
        if (C0589a.m788a().m793b()) {
            this.f301k = (Button) findViewById(R.id.bridge);
            this.f301k.setOnClickListener(new C04752(this));
        }
        ((TextView) findViewById(R.id.copyright)).setVisibility(4);
        Button button = (Button) findViewById(R.id.guide_trips_btn);
        button.setVisibility(0);
        button.setOnClickListener(new C04763(this));
        if (C0549n.m712b(DTVApplication.m750a(), "GUIDE_TRIPS_SHOW", true)) {
            m437h();
            m435g();
            z = false;
        } else {
            this.f294d.m767b();
            this.f295e.setText(getString(R.string.check_update_trips));
            m435g();
            z = C0589a.m788a().m799e() ? m431e() : !C0546l.m663c(C0548m.m704b(getApplicationContext())) && m431e();
        }
        if (z) {
            this.f294d.m766a();
        } else {
            C0546l.m653a().m685a(C0506a.f356a, C0506a.f357b, C0506a.f359d, C0506a.f358c);
            this.f293c.sendEmptyMessageDelayed(1, 1500);
        }
        this.f296f.setOnClickListener(new C04774(this));
    }

    private boolean m431e() {
        this.f298h = C0549n.m705a(DTVApplication.m750a(), "device_type");
        this.f299i = C0549n.m705a(DTVApplication.m750a(), "software_version");
        if (this.f298h == null || this.f299i == null || this.f298h.isEmpty() || this.f299i.isEmpty()) {
            return false;
        }
        try {
            this.f300j = new C0478a(this, this) {
                final /* synthetic */ HomePageActivity f284a;

                public boolean mo2104a() {
                    return C0546l.m663c(C0548m.m704b(this.f284a.getApplicationContext()));
                }
            };
            this.f300j.m405a(this.f298h).m407b(1).m402a(Double.parseDouble(this.f299i)).m408b(C0506a.f356a).m403a(1).m404a(new C04816(this)).m409b();
            C0546l.m653a().m680a(new C04837(this));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void m433f() {
        ObjectAnimator.ofFloat(this.f297g, "alpha", new float[]{0.0f, 1.0f}).setDuration(1000).start();
    }

    private void m435g() {
        ObjectAnimator.ofFloat(this.f297g, "alpha", new float[]{1.0f, 0.0f}).setDuration(1000).start();
    }

    private boolean m437h() {
        if (this.f303m != null && this.f303m.isShowing()) {
            return false;
        }
        View inflate = getLayoutInflater().inflate(R.layout.guide_dialog, null);
        this.f303m = new Dialog(this, R.style.transparentFrameWindowStyle);
        CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.guide_trips_check);
        checkBox.setChecked(!C0549n.m712b(DTVApplication.m750a(), "GUIDE_TRIPS_SHOW", true));
        checkBox.setOnCheckedChangeListener(new C04848(this));
        this.f303m.setContentView(inflate, new LayoutParams(-1, -2));
        Window window = this.f303m.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams attributes = window.getAttributes();
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = C0533d.m627a(getApplicationContext(), 345.0f);
        attributes.height = -2;
        attributes.alpha = 0.9f;
        this.f303m.onWindowAttributesChanged(attributes);
        this.f303m.setCanceledOnTouchOutside(true);
        this.f303m.show();
        return true;
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        C0546l.m653a().m685a(C0506a.f356a, C0506a.f357b, C0506a.f359d, C0506a.f358c);
        String b = C0548m.m704b(getApplicationContext());
        this.f297g.setVisibility(8);
        this.f294d.setVisibility(0);
        this.f294d.m767b();
        this.f295e.setText(getText(R.string.connecting));
        this.f296f.setVisibility(8);
        if (i == 1) {
            if (C0546l.m663c(b)) {
                this.f295e.setText(R.string.connected);
            }
            this.f293c.sendEmptyMessage(2);
        } else if (i == 4) {
            this.f294d.m766a();
            this.f294d.setVisibility(4);
            if (C0546l.m663c(b)) {
                this.f295e.setText(getText(R.string.connected));
                this.f296f.setVisibility(0);
                this.f296f.setTag("next");
                this.f296f.setText(getText(R.string.next));
                this.f297g.setImageResource(R.drawable.ok);
                this.f297g.setVisibility(0);
                this.f301k.setVisibility(0);
                BridgeSettingActivity.m139a(this.f302l);
                m433f();
                return;
            }
            this.f293c.sendEmptyMessage(2);
        }
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        switch (2) {
            case 1:
                setContentView((int) R.layout.activity_homepage);
                break;
            case 2:
                setContentView((int) R.layout.activity_atsc);
                break;
            default:
                if (!(f291a || "Error Product type" == null)) {
                    throw new AssertionError();
                }
        }
        this.f293c = new C0485a(this, this);
        m429d();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        C0546l.m653a().m689c();
        finish();
        return true;
    }
}
