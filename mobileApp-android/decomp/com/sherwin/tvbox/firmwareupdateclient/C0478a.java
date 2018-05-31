package com.sherwin.tvbox.firmwareupdateclient;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;
import com.sherwin.tvbox.firmwareupdateclient.p015c.C0624a;
import com.sherwin.tvbox.firmwareupdateclient.p015c.C0624a.C0611a;
import com.sherwin.tvbox.firmwareupdateclient.p015c.C0625b;
import com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c;
import com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.C0602c;
import com.sherwin.tvbox.firmwareupdateclient.p016b.C0621a.C0613b;
import com.sherwin.tvbox.firmwareupdateclient.p017a.C0617a;
import java.util.Locale;

public abstract class C0478a {
    private Context f272a = null;
    private String f273b = null;
    private String f274c = "192.168.1.1";
    private double f275d = -1.0d;
    private C0480a f276e;
    private C0617a f277f;
    private C0624a f278g;
    private C0625b f279h;
    private int f280i = 1;
    private int f281j = 1;
    private SweetAlertDialog f282k;
    private Handler f283l = new Handler(this, Looper.getMainLooper()) {
        C0616b f675a;
        final /* synthetic */ C0478a f676b;

        class C06081 implements OnSweetClickListener {
            final /* synthetic */ C06106 f673a;

            C06081(C06106 c06106) {
                this.f673a = c06106;
            }

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                this.f673a.f676b.f276e.mo2107b();
                sweetAlertDialog.dismissWithAnimation();
            }
        }

        class C06092 implements OnSweetClickListener {
            final /* synthetic */ C06106 f674a;

            C06092(C06106 c06106) {
                this.f674a = c06106;
            }

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                if (intent.resolveActivity(this.f674a.f676b.f272a.getPackageManager()) != null) {
                    ((Activity) this.f674a.f676b.f272a).startActivityForResult(intent, 13);
                } else {
                    Toast.makeText(this.f674a.f676b.f272a, "Open Setting Fail!", 0).show();
                }
            }
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (this.f676b.mo2104a()) {
                        sendEmptyMessage(10);
                        return;
                    }
                    this.f676b.f282k.changeAlertType(0);
                    this.f676b.f282k.setTitleText(this.f676b.f272a.getString(C0601R.string.connect_to_device)).setContentText(this.f676b.f272a.getString(C0601R.string.connect_trips)).setConfirmText(this.f676b.f272a.getString(C0601R.string.update_yes)).setConfirmClickListener(new C06092(this)).setCancelText(this.f676b.f272a.getString(C0601R.string.update_no)).setCancelClickListener(new C06081(this)).setCanceledOnTouchOutside(false);
                    this.f676b.f282k.show();
                    return;
                case 1:
                    if (this.f676b.f282k != null && this.f676b.f282k.isShowing()) {
                        this.f676b.f282k.dismissWithAnimation();
                        return;
                    }
                    return;
                case 2:
                    this.f675a = (C0616b) message.obj;
                    this.f676b.m371a(this.f675a.m868a(), this.f675a.m869b().toString(), ((Integer) this.f675a.m870c()).intValue());
                    return;
                case 3:
                    this.f675a = (C0616b) message.obj;
                    this.f676b.m370a(this.f675a.m868a(), this.f675a.m869b().toString());
                    return;
                case 4:
                    this.f675a = (C0616b) message.obj;
                    this.f676b.m380b(this.f675a.m868a(), this.f675a.m869b().toString(), ((Integer) this.f675a.m870c()).intValue());
                    return;
                case 5:
                    this.f675a = (C0616b) message.obj;
                    this.f676b.m379b(this.f675a.m868a(), this.f675a.m869b().toString());
                    return;
                case 6:
                    this.f675a = (C0616b) message.obj;
                    this.f676b.m369a(this.f675a.m868a(), ((Long) this.f675a.m869b()).longValue(), ((Long) this.f675a.m870c()).longValue());
                    return;
                case 7:
                    this.f676b.m396h();
                    return;
                case 8:
                    this.f675a = (C0616b) message.obj;
                    this.f676b.m364a((C0617a) this.f675a.m869b(), ((Double) this.f675a.m870c()).doubleValue());
                    return;
                case 9:
                    if (this.f676b.f282k != null && message.obj != null) {
                        this.f676b.f282k.setContentText(message.obj.toString());
                        return;
                    }
                    return;
                case 10:
                    this.f676b.m395g();
                    return;
                case 11:
                    try {
                        this.f676b.m409b();
                        return;
                    } catch (Exception e) {
                        this.f676b.f276e.mo2106a(e.toString());
                        return;
                    }
                default:
                    return;
            }
        }
    };

    public interface C0480a {
        void mo2105a();

        void mo2106a(String str);

        void mo2107b();
    }

    class C06031 implements C0602c {
        final /* synthetic */ C0478a f668a;

        C06031(C0478a c0478a) {
            this.f668a = c0478a;
        }

        public void mo2209a(C0617a c0617a) {
            Log.e("VERSION", "getVersion" + c0617a.m871a());
            Log.e("VERSION", "currentVersion" + this.f668a.f275d);
            if (c0617a.m871a().doubleValue() > this.f668a.f275d) {
                this.f668a.f277f = c0617a;
                this.f668a.m373b(c0617a, this.f668a.f275d);
                return;
            }
            this.f668a.f276e.mo2107b();
        }

        public void mo2210a(String str) {
            this.f668a.f276e.mo2106a(str);
        }
    }

    class C06042 implements OnDismissListener {
        final /* synthetic */ C0478a f669a;

        C06042(C0478a c0478a) {
            this.f669a = c0478a;
        }

        public void onDismiss(DialogInterface dialogInterface) {
            this.f669a.f276e.mo2107b();
        }
    }

    class C06053 implements OnSweetClickListener {
        final /* synthetic */ C0478a f670a;

        C06053(C0478a c0478a) {
            this.f670a = c0478a;
        }

        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismissWithAnimation();
        }
    }

    class C06064 implements OnSweetClickListener {
        final /* synthetic */ C0478a f671a;

        C06064(C0478a c0478a) {
            this.f671a = c0478a;
        }

        public void onClick(SweetAlertDialog sweetAlertDialog) {
            this.f671a.m393f();
        }
    }

    class C06075 implements OnSweetClickListener {
        final /* synthetic */ C0478a f672a;

        C06075(C0478a c0478a) {
            this.f672a = c0478a;
        }

        public void onClick(SweetAlertDialog sweetAlertDialog) {
            sweetAlertDialog.dismissWithAnimation();
            this.f672a.f276e.mo2107b();
        }
    }

    class C06127 implements C0611a {
        final /* synthetic */ C0478a f677a;

        C06127(C0478a c0478a) {
            this.f677a = c0478a;
        }

        public void mo2211a() {
            this.f677a.m390d(this.f677a.f272a.getString(C0601R.string.update_download_title), this.f677a.f272a.getString(C0601R.string.update_download_ing), 1);
        }

        public void mo2212a(long j, long j2) {
            this.f677a.m378b(this.f677a.f272a.getString(C0601R.string.update_download_ing), j, j2);
        }

        public void mo2213a(String str) {
            this.f677a.m384c(this.f677a.f272a.getString(C0601R.string.update_download_title), str);
        }

        public void mo2214b() {
            this.f677a.m389d(this.f677a.f272a.getString(C0601R.string.update_download_title), this.f677a.f272a.getString(C0601R.string.update_download_break));
        }

        public void mo2215b(String str) {
            this.f677a.f283l.sendEmptyMessage(0);
        }
    }

    class C06148 implements C0613b {
        final /* synthetic */ C0478a f678a;

        C06148(C0478a c0478a) {
            this.f678a = c0478a;
        }

        public void mo2216a() {
            this.f678a.m390d(this.f678a.f272a.getString(C0601R.string.update_firmware_title), this.f678a.f272a.getString(C0601R.string.update_upload_ing), 2);
        }

        public void mo2217a(long j, long j2) {
            this.f678a.m378b(this.f678a.f272a.getString(C0601R.string.update_upload_ing), j, j2);
        }

        public void mo2218a(String str) {
            this.f678a.m384c(this.f678a.f272a.getString(C0601R.string.update_firmware_title), str);
        }

        public void mo2219b() {
            this.f678a.m399i();
        }

        public void mo2220c() {
            this.f678a.m389d(this.f678a.f272a.getString(C0601R.string.update_firmware_title), this.f678a.f272a.getString(C0601R.string.update_interrupt));
        }

        public void mo2221d() {
            this.f678a.m385c(this.f678a.f272a.getString(C0601R.string.update_firmware_title), this.f678a.f272a.getString(C0601R.string.update_success), 2);
        }
    }

    class C06159 implements OnDismissListener {
        final /* synthetic */ C0478a f679a;

        C06159(C0478a c0478a) {
            this.f679a = c0478a;
        }

        public void onDismiss(DialogInterface dialogInterface) {
            this.f679a.f283l.removeMessages(0);
            this.f679a.f283l.sendEmptyMessage(0);
        }
    }

    private class C0616b {
        final /* synthetic */ C0478a f680a;
        private String f681b;
        private Object f682c;
        private Object f683d;

        public C0616b(C0478a c0478a, String str, Object obj, Object obj2) {
            this.f680a = c0478a;
            this.f681b = str;
            this.f682c = obj;
            this.f683d = obj2;
        }

        public String m868a() {
            return this.f681b;
        }

        public Object m869b() {
            return this.f682c;
        }

        public Object m870c() {
            return this.f683d;
        }
    }

    public C0478a(Context context) {
        this.f272a = context;
        this.f282k = new SweetAlertDialog(context, 0);
    }

    private void m364a(C0617a c0617a, double d) {
        String format;
        this.f282k.setCancelable(true);
        this.f282k.setCanceledOnTouchOutside(false);
        StringBuilder stringBuilder = new StringBuilder();
        if (c0617a.m876b().longValue() > 1048576) {
            format = String.format(Locale.ENGLISH, "%.2f MB", new Object[]{Double.valueOf(((double) c0617a.m876b().longValue()) / 1048576.0d)});
        } else if (c0617a.m876b().longValue() > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            format = String.format(Locale.ENGLISH, "%.2f KB", new Object[]{Double.valueOf(((double) c0617a.m876b().longValue()) / 1024.0d)});
        } else {
            format = String.format(Locale.ENGLISH, "%d Byte", new Object[]{c0617a.m876b()});
        }
        stringBuilder.append(this.f272a.getString(C0601R.string.update_check_version_trips) + " " + this.f273b + "v" + d).append(this.f272a.getString(C0601R.string.update_check_latest_version_trips) + " " + c0617a.m884f() + "v" + c0617a.m871a()).append(this.f272a.getString(C0601R.string.update_check_packet_size) + " " + format).append(this.f272a.getString(C0601R.string.update_check_info) + C0622b.m892a(this.f272a, c0617a.m885g()) + "\n" + c0617a.m886h());
        if (c0617a.m885g() == 2) {
            stringBuilder.append(this.f272a.getString(C0601R.string.update_check_confirm2));
        } else {
            stringBuilder.append(this.f272a.getString(C0601R.string.update_check_confirm));
        }
        this.f282k.setTitleText(this.f272a.getString(C0601R.string.update_check_title)).setContentText(stringBuilder.toString()).setCancelText(this.f272a.getString(C0601R.string.update_no)).setCancelClickListener(new C06075(this)).setConfirmText(this.f272a.getString(C0601R.string.update_yes)).setConfirmClickListener(new C06064(this)).show();
    }

    private void m369a(String str, long j, long j2) {
        this.f282k.setContentText(str + "(" + j + "/" + j2 + ")").getProgressHelper().setProgress(((float) j) / ((float) j2));
    }

    private void m370a(String str, String str2) {
        this.f282k.changeAlertType(1);
        this.f282k.setCanceledOnTouchOutside(true);
        this.f282k.showCancelButton(false);
        this.f282k.setTitleText(str).setContentText(str2).setConfirmText(this.f272a.getString(C0601R.string.update_confirm)).setConfirmClickListener(new OnSweetClickListener(this) {
            final /* synthetic */ C0478a f664a;

            {
                this.f664a = r1;
            }

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        }).setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ C0478a f663a;

            {
                this.f663a = r1;
            }

            public void onDismiss(DialogInterface dialogInterface) {
                this.f663a.f276e.mo2106a("");
            }
        });
        this.f282k.show();
    }

    private void m371a(String str, String str2, int i) {
        if (i == 1) {
            this.f282k.setOnDismissListener(new C06159(this));
            this.f283l.sendEmptyMessageDelayed(0, 500);
        } else if (i == 2) {
            this.f282k.changeAlertType(2);
            this.f282k.showCancelButton(false);
            this.f282k.setCanceledOnTouchOutside(true);
            this.f282k.setTitleText(str).setContentText(str2).setConfirmText(this.f272a.getString(C0601R.string.update_confirm)).setConfirmClickListener(new OnSweetClickListener(this) {
                final /* synthetic */ C0478a f662a;

                {
                    this.f662a = r1;
                }

                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            }).setOnDismissListener(new OnDismissListener(this) {
                final /* synthetic */ C0478a f661a;

                {
                    this.f661a = r1;
                }

                public void onDismiss(DialogInterface dialogInterface) {
                    this.f661a.f276e.mo2105a();
                }
            });
            this.f282k.show();
        }
    }

    private void m373b(C0617a c0617a, double d) {
        this.f283l.obtainMessage(8, new C0616b(this, null, c0617a, Double.valueOf(d))).sendToTarget();
    }

    private void m378b(String str, long j, long j2) {
        this.f283l.obtainMessage(6, new C0616b(this, str, Long.valueOf(j), Long.valueOf(j2))).sendToTarget();
    }

    private void m379b(String str, String str2) {
        this.f282k.changeAlertType(3);
        this.f282k.setCanceledOnTouchOutside(true);
        this.f282k.showCancelButton(false);
        this.f282k.setTitleText(str).setContentText(str2).setConfirmText(this.f272a.getString(C0601R.string.update_confirm)).setConfirmClickListener(new C06053(this)).setOnDismissListener(new C06042(this));
        this.f282k.show();
        this.f283l.sendEmptyMessageDelayed(1, 2000);
    }

    private void m380b(String str, String str2, final int i) {
        this.f282k.changeAlertType(5);
        this.f282k.getProgressHelper().setProgress(0.0f);
        this.f282k.getProgressHelper().stopSpinning();
        this.f282k.showCancelButton(false);
        this.f282k.setCanceledOnTouchOutside(false);
        this.f282k.setTitleText(str).setContentText(str2).setConfirmText(this.f272a.getString(C0601R.string.update_cancel)).setConfirmClickListener(new OnSweetClickListener(this) {
            static final /* synthetic */ boolean f665a = (!C0478a.class.desiredAssertionStatus());
            final /* synthetic */ C0478a f667c;

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (i == 1) {
                    this.f667c.f278g.m906b();
                } else if (i == 2) {
                    this.f667c.f279h.m912b();
                }
                if (!f665a) {
                    throw new AssertionError();
                }
            }
        }).setOnDismissListener(null);
        this.f282k.show();
    }

    private void m384c(String str, String str2) {
        this.f283l.obtainMessage(3, new C0616b(this, str, str2, null)).sendToTarget();
    }

    private void m385c(String str, String str2, int i) {
        this.f283l.sendMessageDelayed(this.f283l.obtainMessage(2, new C0616b(this, str, str2, Integer.valueOf(i))), 3000);
    }

    private void m389d(String str, String str2) {
        this.f283l.obtainMessage(5, new C0616b(this, str, str2, null)).sendToTarget();
    }

    private void m390d(String str, String str2, int i) {
        this.f283l.obtainMessage(4, new C0616b(this, str, str2, Integer.valueOf(i))).sendToTarget();
    }

    private void m393f() {
        this.f278g = new C0624a(this.f272a);
        this.f278g.m903a(new C06127(this)).m902a(this.f277f).m901a(1000).m905b(5000).m904a();
    }

    private void m395g() {
        if (this.f274c == null || this.f280i == -1) {
            this.f276e.mo2106a("参数错误");
            return;
        }
        this.f279h = new C0625b(this.f272a);
        this.f279h.m909a(new C06148(this)).m908a(this.f277f).m910a(this.f274c).m907a(this.f280i).m911a();
    }

    private void m396h() {
        this.f282k.changeAlertType(0);
        this.f282k.setCanceledOnTouchOutside(false);
        this.f282k.setCancelable(false);
        this.f282k.setTitleText(this.f272a.getString(C0601R.string.update_firmware_title)).setContentText(this.f272a.getString(C0601R.string.update_firmware_ing)).setOnDismissListener(null);
        this.f282k.show();
    }

    private void m399i() {
        this.f283l.sendEmptyMessage(7);
    }

    public C0478a m402a(double d) {
        this.f275d = d;
        return this;
    }

    public C0478a m403a(int i) {
        this.f280i = i;
        return this;
    }

    public C0478a m404a(@NonNull C0480a c0480a) {
        this.f276e = c0480a;
        return this;
    }

    public C0478a m405a(String str) {
        this.f273b = str;
        return this;
    }

    public abstract boolean mo2104a();

    public C0478a m407b(int i) {
        this.f281j = i;
        return this;
    }

    public C0478a m408b(String str) {
        this.f274c = str;
        return this;
    }

    public void m409b() {
        if (this.f273b == null) {
            throw new Exception("HardType not found");
        } else if (this.f275d < 0.0d) {
            throw new Exception("currentVersion not found");
        } else {
            new C0631c(this.f272a).m921a(new C06031(this)).m922a(this.f273b).m920a(1000).m924b(3000).m925c(this.f281j).m923a();
        }
    }

    public void m410c() {
        this.f283l.obtainMessage(9, this.f272a.getString(C0601R.string.update_connect_warning_trips)).sendToTarget();
    }

    public void m411d() {
        this.f283l.obtainMessage(9, this.f272a.getString(C0601R.string.update_connect_unknown_trips)).sendToTarget();
    }

    public void m412e() {
        if (this.f277f != null) {
            this.f283l.sendEmptyMessage(0);
        }
    }
}
