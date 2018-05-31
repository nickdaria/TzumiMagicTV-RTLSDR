package com.eardatek.special.player.actitivy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;
import com.anthony.ultimateswipetool.cards.SwipeCards;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.p005i.C0546l;
import com.eardatek.special.player.p005i.C0559u;
import com.eardatek.special.player.system.DTVApplication;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class BridgeSettingActivity extends BaseActivity implements OnClickListener {
    private static final String f124a = BridgeSettingActivity.class.getSimpleName();
    private static final SocketAddress f125b = new InetSocketAddress("192.168.1.1", 8888);
    private Button f126c;
    private Button f127d;
    private TextView f128e;
    private EditText f129f;
    private EditText f130g;
    private CheckBox f131h;
    private EditText f132i;
    private EditText f133j;
    private String f134k;
    private String f135l;
    private String f136m;
    private SweetAlertDialog f137n;
    private Runnable f138o = new C04432(this);
    private Runnable f139p = new C04443(this);
    private Handler f140q = new C04464(this);

    class C04432 implements Runnable {
        final /* synthetic */ BridgeSettingActivity f119a;

        C04432(BridgeSettingActivity bridgeSettingActivity) {
            this.f119a = bridgeSettingActivity;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r9 = this;
            r1 = new java.net.Socket;
            r1.<init>();
            r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f125b;	 Catch:{ Exception -> 0x001a }
            r1.connect(r0);	 Catch:{ Exception -> 0x001a }
            r0 = r1.isConnected();	 Catch:{ Exception -> 0x001a }
            if (r0 != 0) goto L_0x0049;
        L_0x0012:
            r0 = new java.lang.Exception;	 Catch:{ Exception -> 0x001a }
            r2 = "unconnect";
            r0.<init>(r2);	 Catch:{ Exception -> 0x001a }
            throw r0;	 Catch:{ Exception -> 0x001a }
        L_0x001a:
            r0 = move-exception;
            r2 = r9.f119a;	 Catch:{ all -> 0x005f }
            r2 = r2.f140q;	 Catch:{ all -> 0x005f }
            r3 = 0;
            r4 = "Connect Fail!";
            com.eardatek.special.player.p005i.C0540j.m647a(r2, r3, r4);	 Catch:{ all -> 0x005f }
            r2 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ all -> 0x005f }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005f }
            r3.<init>();	 Catch:{ all -> 0x005f }
            r4 = "checkBridgeNameRunnable: ";
            r3 = r3.append(r4);	 Catch:{ all -> 0x005f }
            r0 = r0.toString();	 Catch:{ all -> 0x005f }
            r0 = r3.append(r0);	 Catch:{ all -> 0x005f }
            r0 = r0.toString();	 Catch:{ all -> 0x005f }
            android.util.Log.e(r2, r0);	 Catch:{ all -> 0x005f }
            r1.close();	 Catch:{ IOException -> 0x00ff }
        L_0x0048:
            return;
        L_0x0049:
            r0 = r1.getInputStream();	 Catch:{ Exception -> 0x001a }
            r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
            r2 = new byte[r2];	 Catch:{ Exception -> 0x001a }
            r0 = r0.read(r2);	 Catch:{ Exception -> 0x001a }
            if (r0 > 0) goto L_0x0064;
        L_0x0057:
            r0 = new java.lang.Exception;	 Catch:{ Exception -> 0x001a }
            r2 = "read error";
            r0.<init>(r2);	 Catch:{ Exception -> 0x001a }
            throw r0;	 Catch:{ Exception -> 0x001a }
        L_0x005f:
            r0 = move-exception;
            r1.close();	 Catch:{ IOException -> 0x0102 }
        L_0x0063:
            throw r0;
        L_0x0064:
            r3 = new java.lang.String;	 Catch:{ Exception -> 0x001a }
            r4 = 0;
            r3.<init>(r2, r4, r0);	 Catch:{ Exception -> 0x001a }
            r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ Exception -> 0x001a }
            r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x001a }
            r2.<init>();	 Catch:{ Exception -> 0x001a }
            r4 = "get: ";
            r2 = r2.append(r4);	 Catch:{ Exception -> 0x001a }
            r2 = r2.append(r3);	 Catch:{ Exception -> 0x001a }
            r2 = r2.toString();	 Catch:{ Exception -> 0x001a }
            android.util.Log.d(r0, r2);	 Catch:{ Exception -> 0x001a }
            r0 = "bridge_ssid";
            r2 = "\"";
            r4 = "\"";
            r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.m141b(r3, r0, r2, r4);	 Catch:{ Exception -> 0x001a }
            r2 = "bridge_passwd";
            r4 = "\"";
            r5 = "\"";
            r2 = com.eardatek.special.player.actitivy.BridgeSettingActivity.m141b(r3, r2, r4, r5);	 Catch:{ Exception -> 0x001a }
            r4 = "wifi_ssid";
            r5 = "\"";
            r6 = "\"";
            r4 = com.eardatek.special.player.actitivy.BridgeSettingActivity.m141b(r3, r4, r5, r6);	 Catch:{ Exception -> 0x001a }
            r5 = "wifi_passwd";
            r6 = "\"";
            r7 = "\"";
            r5 = com.eardatek.special.player.actitivy.BridgeSettingActivity.m141b(r3, r5, r6, r7);	 Catch:{ Exception -> 0x001a }
            r6 = "wifiEnable";
            r7 = "\"";
            r8 = "\"";
            r3 = com.eardatek.special.player.actitivy.BridgeSettingActivity.m141b(r3, r6, r7, r8);	 Catch:{ Exception -> 0x001a }
            r6 = "1";
            r3 = r3.equals(r6);	 Catch:{ Exception -> 0x001a }
            r6 = r9.f119a;	 Catch:{ Exception -> 0x001a }
            r6.f134k = r4;	 Catch:{ Exception -> 0x001a }
            r6 = r9.f119a;	 Catch:{ Exception -> 0x001a }
            r6 = r6.f140q;	 Catch:{ Exception -> 0x001a }
            r7 = 1;
            com.eardatek.special.player.p005i.C0540j.m647a(r6, r7, r0);	 Catch:{ Exception -> 0x001a }
            r0 = r9.f119a;	 Catch:{ Exception -> 0x001a }
            r0 = r0.f140q;	 Catch:{ Exception -> 0x001a }
            r6 = 2;
            com.eardatek.special.player.p005i.C0540j.m647a(r0, r6, r2);	 Catch:{ Exception -> 0x001a }
            r0 = r9.f119a;	 Catch:{ Exception -> 0x001a }
            r0 = r0.f140q;	 Catch:{ Exception -> 0x001a }
            r2 = 3;
            com.eardatek.special.player.p005i.C0540j.m647a(r0, r2, r4);	 Catch:{ Exception -> 0x001a }
            r0 = r9.f119a;	 Catch:{ Exception -> 0x001a }
            r0 = r0.f140q;	 Catch:{ Exception -> 0x001a }
            r2 = 4;
            com.eardatek.special.player.p005i.C0540j.m647a(r0, r2, r5);	 Catch:{ Exception -> 0x001a }
            r0 = r9.f119a;	 Catch:{ Exception -> 0x001a }
            r0 = r0.f140q;	 Catch:{ Exception -> 0x001a }
            r2 = 5;
            r3 = java.lang.Boolean.valueOf(r3);	 Catch:{ Exception -> 0x001a }
            com.eardatek.special.player.p005i.C0540j.m647a(r0, r2, r3);	 Catch:{ Exception -> 0x001a }
            r1.close();	 Catch:{ IOException -> 0x00fc }
            goto L_0x0048;
        L_0x00fc:
            r0 = move-exception;
            goto L_0x0048;
        L_0x00ff:
            r0 = move-exception;
            goto L_0x0048;
        L_0x0102:
            r1 = move-exception;
            goto L_0x0063;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.eardatek.special.player.actitivy.BridgeSettingActivity.2.run():void");
        }
    }

    class C04443 implements Runnable {
        final /* synthetic */ BridgeSettingActivity f120a;

        C04443(BridgeSettingActivity bridgeSettingActivity) {
            this.f120a = bridgeSettingActivity;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r11 = this;
            r1 = new java.net.Socket;
            r1.<init>();
            r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f125b;	 Catch:{ Exception -> 0x001a }
            r1.connect(r0);	 Catch:{ Exception -> 0x001a }
            r0 = r1.isConnected();	 Catch:{ Exception -> 0x001a }
            if (r0 != 0) goto L_0x0049;
        L_0x0012:
            r0 = new java.lang.Exception;	 Catch:{ Exception -> 0x001a }
            r2 = "unconnect";
            r0.<init>(r2);	 Catch:{ Exception -> 0x001a }
            throw r0;	 Catch:{ Exception -> 0x001a }
        L_0x001a:
            r0 = move-exception;
            r2 = r11.f120a;	 Catch:{ all -> 0x0132 }
            r2 = r2.f140q;	 Catch:{ all -> 0x0132 }
            r3 = 0;
            r4 = "Set Network Fail!";
            com.eardatek.special.player.p005i.C0540j.m647a(r2, r3, r4);	 Catch:{ all -> 0x0132 }
            r2 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ all -> 0x0132 }
            r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0132 }
            r3.<init>();	 Catch:{ all -> 0x0132 }
            r4 = "netCtrlRunnable:";
            r3 = r3.append(r4);	 Catch:{ all -> 0x0132 }
            r0 = r0.toString();	 Catch:{ all -> 0x0132 }
            r0 = r3.append(r0);	 Catch:{ all -> 0x0132 }
            r0 = r0.toString();	 Catch:{ all -> 0x0132 }
            android.util.Log.e(r2, r0);	 Catch:{ all -> 0x0132 }
            r1.close();	 Catch:{ IOException -> 0x016a }
        L_0x0048:
            return;
        L_0x0049:
            r0 = r1.getOutputStream();	 Catch:{ Exception -> 0x001a }
            r2 = r1.getInputStream();	 Catch:{ Exception -> 0x001a }
            r3 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
            r3 = new byte[r3];	 Catch:{ Exception -> 0x001a }
            r2.read(r3);	 Catch:{ Exception -> 0x001a }
            r2 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r2 = r2.f129f;	 Catch:{ Exception -> 0x001a }
            r2 = r2.getText();	 Catch:{ Exception -> 0x001a }
            r2 = r2.toString();	 Catch:{ Exception -> 0x001a }
            r3 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r3 = r3.f130g;	 Catch:{ Exception -> 0x001a }
            r3 = r3.getText();	 Catch:{ Exception -> 0x001a }
            r3 = r3.toString();	 Catch:{ Exception -> 0x001a }
            r4 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r4 = r4.f132i;	 Catch:{ Exception -> 0x001a }
            r4 = r4.getText();	 Catch:{ Exception -> 0x001a }
            r4 = r4.toString();	 Catch:{ Exception -> 0x001a }
            r5 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r5 = r5.f133j;	 Catch:{ Exception -> 0x001a }
            r5 = r5.getText();	 Catch:{ Exception -> 0x001a }
            r5 = r5.toString();	 Catch:{ Exception -> 0x001a }
            r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x001a }
            r6.<init>();	 Catch:{ Exception -> 0x001a }
            r7 = java.util.Locale.ENGLISH;	 Catch:{ Exception -> 0x001a }
            r8 = "<msg bridge_ssid=\"%s\" bridge_pwd=\"%s\" ";
            r9 = 2;
            r9 = new java.lang.Object[r9];	 Catch:{ Exception -> 0x001a }
            r10 = 0;
            r9[r10] = r2;	 Catch:{ Exception -> 0x001a }
            r2 = 1;
            r9[r2] = r3;	 Catch:{ Exception -> 0x001a }
            r2 = java.lang.String.format(r7, r8, r9);	 Catch:{ Exception -> 0x001a }
            r6.append(r2);	 Catch:{ Exception -> 0x001a }
            r2 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r2 = r2.f131h;	 Catch:{ Exception -> 0x001a }
            r2 = r2.isChecked();	 Catch:{ Exception -> 0x001a }
            if (r2 == 0) goto L_0x0164;
        L_0x00b5:
            r2 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r2 = r2.f134k;	 Catch:{ Exception -> 0x001a }
            if (r2 == 0) goto L_0x0137;
        L_0x00bd:
            r2 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r2 = r2.f134k;	 Catch:{ Exception -> 0x001a }
            r2 = r2.equals(r4);	 Catch:{ Exception -> 0x001a }
            if (r2 == 0) goto L_0x0137;
        L_0x00c9:
            r2 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ Exception -> 0x001a }
            r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x001a }
            r3.<init>();	 Catch:{ Exception -> 0x001a }
            r7 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r7 = r7.f134k;	 Catch:{ Exception -> 0x001a }
            r3 = r3.append(r7);	 Catch:{ Exception -> 0x001a }
            r7 = ":";
            r3 = r3.append(r7);	 Catch:{ Exception -> 0x001a }
            r3 = r3.append(r4);	 Catch:{ Exception -> 0x001a }
            r3 = r3.toString();	 Catch:{ Exception -> 0x001a }
            android.util.Log.e(r2, r3);	 Catch:{ Exception -> 0x001a }
            r2 = r5.isEmpty();	 Catch:{ Exception -> 0x001a }
            if (r2 == 0) goto L_0x0120;
        L_0x00f3:
            r2 = "wifiEnable=\"1\" />";
            r6.append(r2);	 Catch:{ Exception -> 0x001a }
        L_0x00f8:
            r2 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ Exception -> 0x001a }
            r3 = r6.toString();	 Catch:{ Exception -> 0x001a }
            android.util.Log.d(r2, r3);	 Catch:{ Exception -> 0x001a }
            r2 = r6.toString();	 Catch:{ Exception -> 0x001a }
            r2 = r2.getBytes();	 Catch:{ Exception -> 0x001a }
            r0.write(r2);	 Catch:{ Exception -> 0x001a }
            r0 = r11.f120a;	 Catch:{ Exception -> 0x001a }
            r0 = r0.f140q;	 Catch:{ Exception -> 0x001a }
            r2 = 7;
            r0.sendEmptyMessage(r2);	 Catch:{ Exception -> 0x001a }
            r1.close();	 Catch:{ IOException -> 0x011d }
            goto L_0x0048;
        L_0x011d:
            r0 = move-exception;
            goto L_0x0048;
        L_0x0120:
            r2 = java.util.Locale.ENGLISH;	 Catch:{ Exception -> 0x001a }
            r3 = "wifiEnable=\"1\" wifi_pwd=\"%s\" />";
            r4 = 1;
            r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x001a }
            r7 = 0;
            r4[r7] = r5;	 Catch:{ Exception -> 0x001a }
            r2 = java.lang.String.format(r2, r3, r4);	 Catch:{ Exception -> 0x001a }
            r6.append(r2);	 Catch:{ Exception -> 0x001a }
            goto L_0x00f8;
        L_0x0132:
            r0 = move-exception;
            r1.close();	 Catch:{ IOException -> 0x016d }
        L_0x0136:
            throw r0;
        L_0x0137:
            r2 = r5.isEmpty();	 Catch:{ Exception -> 0x001a }
            if (r2 == 0) goto L_0x014f;
        L_0x013d:
            r2 = java.util.Locale.ENGLISH;	 Catch:{ Exception -> 0x001a }
            r3 = "wifiEnable=\"1\" wifi_ssid=\"%s\" />";
            r5 = 1;
            r5 = new java.lang.Object[r5];	 Catch:{ Exception -> 0x001a }
            r7 = 0;
            r5[r7] = r4;	 Catch:{ Exception -> 0x001a }
            r2 = java.lang.String.format(r2, r3, r5);	 Catch:{ Exception -> 0x001a }
            r6.append(r2);	 Catch:{ Exception -> 0x001a }
            goto L_0x00f8;
        L_0x014f:
            r2 = java.util.Locale.ENGLISH;	 Catch:{ Exception -> 0x001a }
            r3 = "wifiEnable=\"1\" wifi_ssid=\"%s\" wifi_pwd=\"%s\" />";
            r7 = 2;
            r7 = new java.lang.Object[r7];	 Catch:{ Exception -> 0x001a }
            r8 = 0;
            r7[r8] = r4;	 Catch:{ Exception -> 0x001a }
            r4 = 1;
            r7[r4] = r5;	 Catch:{ Exception -> 0x001a }
            r2 = java.lang.String.format(r2, r3, r7);	 Catch:{ Exception -> 0x001a }
            r6.append(r2);	 Catch:{ Exception -> 0x001a }
            goto L_0x00f8;
        L_0x0164:
            r2 = "/>";
            r6.append(r2);	 Catch:{ Exception -> 0x001a }
            goto L_0x00f8;
        L_0x016a:
            r0 = move-exception;
            goto L_0x0048;
        L_0x016d:
            r1 = move-exception;
            goto L_0x0136;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.eardatek.special.player.actitivy.BridgeSettingActivity.3.run():void");
        }
    }

    class C04464 extends Handler {
        int f122a = 0;
        final /* synthetic */ BridgeSettingActivity f123b;

        class C04451 implements OnSweetClickListener {
            final /* synthetic */ C04464 f121a;

            C04451(C04464 c04464) {
                this.f121a = c04464;
            }

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                this.f121a.f123b.f140q.removeMessages(9);
            }
        }

        C04464(BridgeSettingActivity bridgeSettingActivity) {
            this.f123b = bridgeSettingActivity;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    Toast.makeText(this.f123b, message.obj.toString(), 1).show();
                    return;
                case 1:
                    this.f123b.f128e.setText(message.obj.toString());
                    return;
                case 2:
                    return;
                case 3:
                    this.f123b.f132i.setText(message.obj.toString());
                    return;
                case 4:
                    this.f123b.f133j.setText(message.obj.toString());
                    return;
                case 5:
                    this.f123b.f131h.setChecked(((Boolean) message.obj).booleanValue());
                    return;
                case 6:
                    this.f123b.f127d.setClickable(true);
                    return;
                case 7:
                    this.f123b.f137n = new SweetAlertDialog(this.f123b, 5);
                    this.f123b.f137n.setTitleText(this.f123b.getString(R.string.bridged_title));
                    this.f123b.f137n.setContentText(this.f123b.getString(R.string.bridged_trips1));
                    this.f123b.f137n.getProgressHelper().setProgress(0.0f);
                    this.f123b.f137n.getProgressHelper().stopSpinning();
                    this.f123b.f137n.setCancelable(false);
                    this.f123b.f137n.setCanceledOnTouchOutside(false);
                    this.f123b.f137n.show();
                    this.f122a = 0;
                    sendEmptyMessage(8);
                    return;
                case 8:
                    this.f123b.f137n.getProgressHelper().setProgress(((float) this.f122a) / SwipeCards.DEFAULT_SWIPE_ROTATION);
                    int i = this.f122a + 1;
                    this.f122a = i;
                    if (i < 30) {
                        sendEmptyMessageDelayed(8, 1000);
                        return;
                    }
                    this.f123b.f137n.setContentText(this.f123b.getString(R.string.bridged_trips2));
                    this.f123b.f137n.changeAlertType(2);
                    this.f123b.f137n.setConfirmClickListener(new C04451(this));
                    C0546l.m653a().m689c();
                    C0559u c0559u = new C0559u(DTVApplication.m750a());
                    if (this.f123b.f133j.getText().toString().isEmpty()) {
                        c0559u.m736a(c0559u.m733a(this.f123b.f132i.getText().toString().replace("\"", ""), "", 1, "wifi"));
                    } else {
                        c0559u.m736a(c0559u.m733a(this.f123b.f132i.getText().toString().replace("\"", ""), this.f123b.f133j.getText().toString(), 3, "wifi"));
                    }
                    sendEmptyMessageDelayed(9, 3000);
                    return;
                case 9:
                    if (this.f123b.f137n.isShowing()) {
                        this.f123b.f137n.dismissWithAnimation();
                    }
                    this.f123b.finish();
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    public static void m138a(Activity activity) {
        activity.startActivityForResult(new Intent(activity, BridgeSettingActivity.class), 4);
    }

    public static void m139a(final Comparable<Boolean> comparable) {
        new Thread(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r5 = this;
                r1 = new java.net.Socket;
                r1.<init>();
                r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f125b;	 Catch:{ Exception -> 0x001a }
                r1.connect(r0);	 Catch:{ Exception -> 0x001a }
                r0 = r1.isConnected();	 Catch:{ Exception -> 0x001a }
                if (r0 != 0) goto L_0x0047;
            L_0x0012:
                r0 = new java.lang.Exception;	 Catch:{ Exception -> 0x001a }
                r2 = "unconnect";
                r0.<init>(r2);	 Catch:{ Exception -> 0x001a }
                throw r0;	 Catch:{ Exception -> 0x001a }
            L_0x001a:
                r0 = move-exception;
                r2 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ all -> 0x005d }
                r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x005d }
                r3.<init>();	 Catch:{ all -> 0x005d }
                r4 = "checkBridgeNameRunnable: ";
                r3 = r3.append(r4);	 Catch:{ all -> 0x005d }
                r0 = r0.toString();	 Catch:{ all -> 0x005d }
                r0 = r3.append(r0);	 Catch:{ all -> 0x005d }
                r0 = r0.toString();	 Catch:{ all -> 0x005d }
                android.util.Log.e(r2, r0);	 Catch:{ all -> 0x005d }
                r0 = r2;	 Catch:{ all -> 0x005d }
                r2 = 0;
                r2 = java.lang.Boolean.valueOf(r2);	 Catch:{ all -> 0x005d }
                r0.compareTo(r2);	 Catch:{ all -> 0x005d }
                r1.close();	 Catch:{ IOException -> 0x00ad }
            L_0x0046:
                return;
            L_0x0047:
                r0 = r1.getInputStream();	 Catch:{ Exception -> 0x001a }
                r2 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
                r2 = new byte[r2];	 Catch:{ Exception -> 0x001a }
                r0 = r0.read(r2);	 Catch:{ Exception -> 0x001a }
                if (r0 > 0) goto L_0x0062;
            L_0x0055:
                r0 = new java.lang.Exception;	 Catch:{ Exception -> 0x001a }
                r2 = "read error";
                r0.<init>(r2);	 Catch:{ Exception -> 0x001a }
                throw r0;	 Catch:{ Exception -> 0x001a }
            L_0x005d:
                r0 = move-exception;
                r1.close();	 Catch:{ IOException -> 0x00af }
            L_0x0061:
                throw r0;
            L_0x0062:
                r3 = new java.lang.String;	 Catch:{ Exception -> 0x001a }
                r4 = 0;
                r3.<init>(r2, r4, r0);	 Catch:{ Exception -> 0x001a }
                r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.f124a;	 Catch:{ Exception -> 0x001a }
                r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x001a }
                r2.<init>();	 Catch:{ Exception -> 0x001a }
                r4 = "get: ";
                r2 = r2.append(r4);	 Catch:{ Exception -> 0x001a }
                r2 = r2.append(r3);	 Catch:{ Exception -> 0x001a }
                r2 = r2.toString();	 Catch:{ Exception -> 0x001a }
                android.util.Log.d(r0, r2);	 Catch:{ Exception -> 0x001a }
                r0 = "bridge_ssid";
                r2 = "\"";
                r4 = "\"";
                r0 = com.eardatek.special.player.actitivy.BridgeSettingActivity.m141b(r3, r0, r2, r4);	 Catch:{ Exception -> 0x001a }
                r0 = r0.isEmpty();	 Catch:{ Exception -> 0x001a }
                if (r0 == 0) goto L_0x00a2;
            L_0x0092:
                r0 = r2;	 Catch:{ Exception -> 0x001a }
                r2 = 0;
                r2 = java.lang.Boolean.valueOf(r2);	 Catch:{ Exception -> 0x001a }
                r0.compareTo(r2);	 Catch:{ Exception -> 0x001a }
            L_0x009c:
                r1.close();	 Catch:{ IOException -> 0x00a0 }
                goto L_0x0046;
            L_0x00a0:
                r0 = move-exception;
                goto L_0x0046;
            L_0x00a2:
                r0 = r2;	 Catch:{ Exception -> 0x001a }
                r2 = 1;
                r2 = java.lang.Boolean.valueOf(r2);	 Catch:{ Exception -> 0x001a }
                r0.compareTo(r2);	 Catch:{ Exception -> 0x001a }
                goto L_0x009c;
            L_0x00ad:
                r0 = move-exception;
                goto L_0x0046;
            L_0x00af:
                r1 = move-exception;
                goto L_0x0061;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.eardatek.special.player.actitivy.BridgeSettingActivity.1.run():void");
            }
        }).start();
    }

    private static String m141b(String str, String str2, String str3, String str4) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return "";
        }
        indexOf = str.indexOf(str3, indexOf + str2.length());
        if (indexOf < 0) {
            return "";
        }
        indexOf += str3.length();
        int indexOf2 = str.indexOf(str4, indexOf);
        return indexOf2 < 0 ? "" : str.substring(indexOf, (indexOf2 - indexOf) + indexOf);
    }

    private void m147e() {
        this.f126c = (Button) findViewById(R.id.bridge_setting_cancel_btn);
        this.f127d = (Button) findViewById(R.id.bridge_setting_confirm_btn);
        this.f128e = (TextView) findViewById(R.id.bridge_setting_bssid_text);
        this.f129f = (EditText) findViewById(R.id.bridge_setting_bssid_input);
        this.f130g = (EditText) findViewById(R.id.bridge_setting_bpasswd_input);
        this.f131h = (CheckBox) findViewById(R.id.wifi_enable_checkbox);
        this.f132i = (EditText) findViewById(R.id.wifi_setting_bssid_input);
        this.f133j = (EditText) findViewById(R.id.wifi_setting_bpasswd_input);
        this.f126c.setOnClickListener(this);
        this.f127d.setOnClickListener(this);
        this.f131h.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wifi_enable_checkbox:
                if (!this.f131h.isChecked()) {
                    Toast.makeText(this, getString(R.string.bridge_temp), 0).show();
                    this.f131h.setChecked(true);
                    return;
                } else if (this.f131h.isChecked()) {
                    this.f132i.setFocusable(true);
                    this.f133j.setFocusable(true);
                    if (this.f135l != null) {
                        this.f132i.setText(this.f135l);
                    }
                    if (this.f136m != null) {
                        this.f133j.setText(this.f136m);
                        return;
                    }
                    return;
                } else {
                    this.f132i.setFocusable(false);
                    this.f133j.setFocusable(false);
                    this.f135l = this.f132i.getText().toString();
                    this.f132i.setText("");
                    this.f136m = this.f133j.getText().toString();
                    this.f133j.setText("");
                    return;
                }
            case R.id.bridge_setting_cancel_btn:
                finish();
                return;
            case R.id.bridge_setting_confirm_btn:
                if (this.f129f.getText().toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.bridge_ssid_blank_trips), 0).show();
                    return;
                } else if (this.f132i.getText().toString().isEmpty()) {
                    Toast.makeText(this, getString(R.string.wifi_ssid_blank_trips), 0).show();
                    return;
                } else {
                    String obj = this.f133j.getText().toString();
                    if (obj.length() <= 0 || obj.length() >= 8) {
                        new Thread(this.f139p).start();
                        return;
                    } else {
                        Toast.makeText(this, getString(R.string.wifi_passwd_blank_trips), 0).show();
                        return;
                    }
                }
            default:
                return;
        }
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bridge_setting);
        m147e();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onStart() {
        super.onStart();
        new Thread(this.f138o).start();
    }
}
