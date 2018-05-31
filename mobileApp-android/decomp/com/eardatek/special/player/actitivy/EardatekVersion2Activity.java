package com.eardatek.special.player.actitivy;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.pedant.SweetAlert.SweetAlertDialog.OnSweetClickListener;
import com.anthony.ultimateswipetool.cards.SwipeCards;
import com.blazevideo.libdtv.EventHandler;
import com.blazevideo.libdtv.IVideoPlayer;
import com.blazevideo.libdtv.LibDTV;
import com.blazevideo.libdtv.LibDtvException;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.layoutmanager.C0561a;
import com.eardatek.special.player.layoutmanager.FullyLinearLayoutManager;
import com.eardatek.special.player.p003a.C0422a;
import com.eardatek.special.player.p003a.C0422a.C0420a;
import com.eardatek.special.player.p003a.C0441e;
import com.eardatek.special.player.p004f.C0520b;
import com.eardatek.special.player.p004f.C0520b.C0447a;
import com.eardatek.special.player.p005i.C0455r;
import com.eardatek.special.player.p005i.C0466t;
import com.eardatek.special.player.p005i.C0527a;
import com.eardatek.special.player.p005i.C0530b;
import com.eardatek.special.player.p005i.C0533d;
import com.eardatek.special.player.p005i.C0536g;
import com.eardatek.special.player.p005i.C0538h;
import com.eardatek.special.player.p005i.C0539i;
import com.eardatek.special.player.p005i.C0540j;
import com.eardatek.special.player.p005i.C0546l;
import com.eardatek.special.player.p005i.C0548m;
import com.eardatek.special.player.p005i.C0548m.C0547a;
import com.eardatek.special.player.p005i.C0548m.C0547a.C0453a;
import com.eardatek.special.player.p005i.C0549n;
import com.eardatek.special.player.p005i.C0551p;
import com.eardatek.special.player.p005i.C0553q;
import com.eardatek.special.player.p005i.C0553q.C0448a;
import com.eardatek.special.player.p005i.C0559u;
import com.eardatek.special.player.p005i.C0560v;
import com.eardatek.special.player.p006c.C0502a;
import com.eardatek.special.player.p006c.C0505b;
import com.eardatek.special.player.p006c.C0505b.C0450b;
import com.eardatek.special.player.p007b.C0500a;
import com.eardatek.special.player.p007b.C0501b;
import com.eardatek.special.player.p008d.C0506a;
import com.eardatek.special.player.p009e.C0508a;
import com.eardatek.special.player.p009e.C0510b;
import com.eardatek.special.player.p009e.C0510b.C0509a;
import com.eardatek.special.player.p010g.C0521a;
import com.eardatek.special.player.system.C0562a;
import com.eardatek.special.player.system.C0563b;
import com.eardatek.special.player.system.DTVApplication;
import com.eardatek.special.player.widget.C0567b;
import com.eardatek.special.player.widget.CustomEditText;
import com.eardatek.special.player.widget.ProgressWheel;
import com.sherwin.eardatek.labswitch.C0589a;
import com.sherwin.libepgparser.C0452b;
import com.sherwin.libepgparser.C0452b.C0454b;
import com.sherwin.libepgparser.C0452b.C0598a;
import com.sherwin.libepgparser.C0595a;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.C0732c;
import org.greenrobot.eventbus.C0739j;
import org.greenrobot.eventbus.ThreadMode;

public class EardatekVersion2Activity extends BaseActivity implements SensorEventListener, OnPageChangeListener, OnTabChangeListener, IVideoPlayer {
    private static boolean f216I = false;
    private FrameLayout f217A;
    private OnLayoutChangeListener f218B;
    private C0422a f219C;
    private Toolbar f220D;
    private String f221E;
    private String f222F;
    private int f223G;
    private boolean f224H;
    private boolean f225J = true;
    private FrameLayout f226K;
    private TextView f227L;
    private FrameLayout f228M;
    private FrameLayout f229N;
    private ImageView f230O;
    private TextView f231P;
    private TextView f232Q;
    private TextView f233R;
    private ImageView f234S;
    private ImageView f235T;
    private TextView f236U;
    private TextView f237V;
    private RelativeLayout f238W;
    private RecyclerView f239X;
    private ImageView f240Y;
    private TextView f241Z;
    public boolean f242a = false;
    private C0510b aA;
    private boolean aB;
    private float aC = -1.0f;
    private boolean aD = true;
    private int aE;
    private boolean aF;
    private boolean aG = true;
    private boolean aH;
    private boolean aI;
    private C0559u aJ;
    private String aK;
    private boolean aL;
    private boolean aM;
    private int aN = 0;
    private Button aO;
    private FrameLayout aP;
    private LinearLayout aQ;
    private Button aR;
    private C0455r aS;
    private C0553q aT;
    private LinearLayout aU;
    private float aV;
    private C0452b aW = new C0452b(this) {
        final /* synthetic */ EardatekVersion2Activity f166a;

        {
            this.f166a = r1;
        }

        public boolean mo2092a() {
            return this.f166a.av == 1 && this.f166a.f260s.isPlaying();
        }

        public List<C0598a> mo2093b() {
            return this.f166a.m312i();
        }
    };
    private Handler aX = new Handler(this) {
        final /* synthetic */ EardatekVersion2Activity f195a;

        {
            this.f195a = r1;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    C0527a.m600d(this.f195a.f239X, 1.0f, 0.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                    return;
                case 1:
                    C0527a.m598b(this.f195a.f239X, 0.0f, 1.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                    return;
                default:
                    return;
            }
        }
    };
    private final Handler aY = new C0473i(this);
    private SweetAlertDialog aZ;
    private FrameLayout aa;
    private TextView ab;
    private ImageView ac;
    private TextView ad;
    private FloatingActionButton ae;
    private CustomEditText af;
    private BroadcastReceiver ag;
    private int ah = 0;
    private int ai = 0;
    private boolean aj = true;
    private boolean ak = false;
    private int al;
    private boolean am;
    private FragmentTabHost an;
    private ViewPager ao;
    private List<C0501b> ap = new ArrayList();
    private LayoutInflater aq;
    private final Handler ar = new C0467c(this);
    private Thread as;
    private Thread at = null;
    private Thread au = null;
    private int av = 1;
    private boolean aw = true;
    private int ax = 0;
    private C0521a ay;
    private C0505b az;
    public boolean f243b = false;
    private OnMenuItemClickListener ba = new OnMenuItemClickListener(this) {
        final /* synthetic */ EardatekVersion2Activity f147a;

        {
            this.f147a = r1;
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit_list:
                    List arrayList = new ArrayList();
                    try {
                        arrayList = this.f147a.ay.m589a();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (arrayList.size() > 0) {
                        this.f147a.m221B();
                        break;
                    }
                    break;
                case R.id.setting:
                    if (this.f147a.f260s.isPlaying() && this.f147a.f252k) {
                        this.f147a.m346v();
                    }
                    SettingActivity.m458a(this.f147a, this.f147a.ai);
                    this.f147a.overridePendingTransition(R.anim.photo_dialog_in_anim, R.anim.photo_dialog_out_anim);
                    break;
                case R.id.showepg_menu:
                    if (this.f147a.f260s.isPlaying() && this.f147a.f252k) {
                        this.f147a.m346v();
                    }
                    ShowEpgActivity.m471a(this.f147a, String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(this.f147a.ai), Integer.valueOf(this.f147a.f223G)}));
                    Log.e("JNIMsg", "start" + String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(this.f147a.ai), Integer.valueOf(this.f147a.f223G)}));
                    this.f147a.overridePendingTransition(R.anim.photo_dialog_in_anim, R.anim.photo_dialog_out_anim);
                    break;
                case R.id.tv:
                    if (this.f147a.aN != 0) {
                        this.f147a.m278b((int) R.id.radio_dvb);
                        break;
                    }
                    this.f147a.m278b((int) R.id.radio_isdbt);
                    break;
            }
            return false;
        }
    };
    private final Callback bb = new Callback(this) {
        final /* synthetic */ EardatekVersion2Activity f148a;

        {
            this.f148a = r1;
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if (this.f148a.f260s != null) {
                Surface surface = surfaceHolder.getSurface();
                if (this.f148a.f265x != surface) {
                    if (this.f148a.f265x != null) {
                        synchronized (this.f148a.f265x) {
                            this.f148a.f265x.notifyAll();
                        }
                    }
                    this.f148a.f265x = surface;
                    C0539i.m643b("EardatekVersion2", "surfaceChanged: " + this.f148a.f265x);
                    this.f148a.f260s.attachSurface(this.f148a.f265x, this.f148a);
                }
            }
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            C0539i.m643b("EardatekVersion2", "surfaceCreated");
            if (this.f148a.f252k) {
                this.f148a.f253l = true;
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            C0539i.m642a("EardatekVersion2", "surfaceDestroyed");
            if (this.f148a.f260s != null) {
                synchronized (this.f148a.f265x) {
                    this.f148a.f265x.notifyAll();
                }
                this.f148a.f265x = null;
                this.f148a.f260s.detachSurface();
            }
        }
    };
    private final Callback bc = new Callback(this) {
        final /* synthetic */ EardatekVersion2Activity f149a;

        {
            this.f149a = r1;
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            C0539i.m643b("EardatekVersion2", "mSubtitlesSurfaceCallback surfaceChanged");
            if (this.f149a.f260s != null) {
                Surface surface = surfaceHolder.getSurface();
                if (this.f149a.f266y != surface) {
                    if (this.f149a.f266y != null) {
                        synchronized (this.f149a.f266y) {
                            this.f149a.f266y.notifyAll();
                        }
                    }
                    this.f149a.f266y = surface;
                    this.f149a.f260s.attachSubtitlesSurface(this.f149a.f266y);
                }
            }
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (this.f149a.f260s != null) {
                synchronized (this.f149a.f266y) {
                    this.f149a.f266y.notifyAll();
                }
                this.f149a.f266y = null;
                this.f149a.f260s.detachSubtitlesSurface();
            }
        }
    };
    private int bd = 0;
    private float be;
    private float bf = 0.0f;
    private int bg = -1;
    private int bh;
    private int bi;
    private boolean bj = false;
    private float bk;
    private float bl;
    private OrientationEventListener bm;
    private boolean bn = false;
    private boolean bo = false;
    private boolean bp = true;
    private boolean bq = true;
    private C0547a br;
    private Dialog bs;
    private Dialog bt;
    int f244c = 0;
    private int f245d = 1;
    private int f246e;
    private int f247f = -1;
    private float f248g;
    private float f249h;
    private GestureDetector f250i;
    private AudioManager f251j;
    private boolean f252k = false;
    private boolean f253l = false;
    private int f254m;
    private int f255n;
    private int f256o;
    private int f257p;
    private int f258q;
    private int f259r;
    private LibDTV f260s;
    private SurfaceView f261t;
    private SurfaceView f262u;
    private SurfaceHolder f263v;
    private SurfaceHolder f264w;
    private Surface f265x = null;
    private Surface f266y = null;
    private FrameLayout f267z;

    class C04511 implements C0450b {
        final /* synthetic */ EardatekVersion2Activity f152a;

        C04511(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f152a = eardatekVersion2Activity;
        }

        public void mo2089a() {
        }

        public void mo2090b() {
            C0539i.m643b("EardatekVersion2", "onScreenOff");
            if (this.f152a.f260s.isPlaying()) {
                this.f152a.m346v();
            }
        }

        public void mo2091c() {
            C0539i.m643b("EardatekVersion2", "onUserPresent");
            if (!this.f152a.f252k) {
                this.f152a.setRequestedOrientation(1);
                this.f152a.m334p();
                this.f152a.bn = false;
                this.f152a.bq = false;
            }
        }
    }

    class C04562 implements C0420a {
        final /* synthetic */ EardatekVersion2Activity f194a;

        C04562(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f194a = eardatekVersion2Activity;
        }

        public void mo2098a(String str, boolean z, int i) {
            if (!str.equals(this.f194a.f221E)) {
                this.f194a.f221E = str;
                if (this.f194a.f260s.isPlaying() && this.f194a.f252k) {
                    this.f194a.f260s.stop();
                }
                this.f194a.m340s();
                if (this.f194a.f252k) {
                    this.f194a.f224H = false;
                    this.f194a.f253l = true;
                    this.f194a.as.interrupt();
                    this.f194a.m218A();
                } else {
                    this.f194a.m282b(this.f194a.f221E);
                }
                C0539i.m643b("EardatekVersion2", "mChanelAdapter set replay");
                this.f194a.al = i;
                this.f194a.am = true;
                C0502a.m504a(5, Integer.valueOf(i));
                this.f194a.aX.sendEmptyMessage(0);
            }
        }
    }

    class C04573 implements OnClickListener {
        final /* synthetic */ EardatekVersion2Activity f199a;

        C04573(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f199a = eardatekVersion2Activity;
        }

        public void onClick(View view) {
            this.f199a.av = this.f199a.getResources().getConfiguration().orientation;
            this.f199a.bo = true;
            if (this.f199a.bn || !this.f199a.f252k) {
                this.f199a.setRequestedOrientation(1);
                C0539i.m643b("EardatekVersion2", "mFullScreen");
                this.f199a.m334p();
                this.f199a.bn = false;
                this.f199a.bq = false;
                return;
            }
            this.f199a.setRequestedOrientation(0);
            this.f199a.m331o();
            this.f199a.bn = true;
            this.f199a.bp = false;
        }
    }

    class C04584 implements OnClickListener {
        final /* synthetic */ EardatekVersion2Activity f200a;

        C04584(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f200a = eardatekVersion2Activity;
        }

        public void onClick(View view) {
            this.f200a.f245d = this.f200a.f245d + 1;
            this.f200a.f245d = this.f200a.f245d % 2;
            if (this.f200a.f245d == 1) {
                this.f200a.f231P.setText("16:9");
            } else {
                this.f200a.f231P.setText("4:3");
            }
            C0549n.m706a(this.f200a.getApplicationContext(), this.f200a.f221E, this.f200a.f245d);
            this.f200a.m227E();
        }
    }

    class C04595 implements OnClickListener {
        final /* synthetic */ EardatekVersion2Activity f201a;

        C04595(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f201a = eardatekVersion2Activity;
        }

        public void onClick(View view) {
            C0546l a = C0546l.m653a();
            this.f201a.m283b(this.f201a.getResources().getString(R.string.signalstrength) + String.format(Locale.ENGLISH, " %d dBm", new Object[]{Integer.valueOf(a.m696j())}), 500);
        }
    }

    class C04606 implements OnClickListener {
        final /* synthetic */ EardatekVersion2Activity f202a;

        C04606(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f202a = eardatekVersion2Activity;
        }

        public void onClick(View view) {
            if (this.f202a.f235T.getTag().toString().equals("unlock")) {
                this.f202a.aB = true;
                C0567b.m774a(DTVApplication.m750a(), this.f202a.getString(R.string.screen_lock_trips), 3000);
                this.f202a.f235T.setImageResource(R.drawable.lock);
                this.f202a.f235T.setTag("lock");
            } else if (this.f202a.f235T.getTag().toString().equals("lock")) {
                this.f202a.aB = false;
                C0567b.m774a(DTVApplication.m750a(), this.f202a.getString(R.string.screen_unlock_trips), 3000);
                this.f202a.f235T.setImageResource(R.drawable.unlock);
                this.f202a.f235T.setTag("unlock");
            }
        }
    }

    class C04617 implements OnClickListener {
        final /* synthetic */ EardatekVersion2Activity f203a;

        C04617(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f203a = eardatekVersion2Activity;
        }

        public void onClick(View view) {
            if (this.f203a.f221E != null && !this.f203a.f252k) {
                this.f203a.m282b(this.f203a.f221E);
            }
        }
    }

    class C04628 implements OnTouchListener {
        final /* synthetic */ EardatekVersion2Activity f204a;

        C04628(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f204a = eardatekVersion2Activity;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            this.f204a.m270a(motionEvent);
            return true;
        }
    }

    class C04639 implements OnClickListener {
        final /* synthetic */ EardatekVersion2Activity f205a;

        C04639(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f205a = eardatekVersion2Activity;
        }

        public void onClick(View view) {
            if (this.f205a.bs.isShowing()) {
                this.f205a.bs.dismiss();
            }
            if (this.f205a.f252k) {
                this.f205a.m346v();
                C0539i.m643b("EardatekVersion2", "mSearchAll set replay");
            }
            try {
                this.f205a.ay.m592b();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            C0536g.m632b("Channel.txt");
            Intent intent = new Intent(this.f205a, ScanChannelActivity.class);
            intent.putExtra("itemLocation", "scan channels country xxx");
            this.f205a.startActivityForResult(intent, 2);
            this.f205a.overridePendingTransition(R.anim.photo_dialog_in_anim, R.anim.photo_dialog_out_anim);
        }
    }

    private static final class C0464a implements Runnable {
        private WeakReference<EardatekVersion2Activity> f206a;

        C0464a(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f206a = new WeakReference(eardatekVersion2Activity);
        }

        public void run() {
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) this.f206a.get();
            if (eardatekVersion2Activity != null) {
                if (!C0546l.m663c(C0548m.m704b(DTVApplication.m750a()))) {
                    eardatekVersion2Activity.ar.sendEmptyMessage(50);
                }
                while (true) {
                    if (eardatekVersion2Activity.f252k || !eardatekVersion2Activity.au.isInterrupted()) {
                        int m = C0546l.m653a().m699m();
                        if (m == -1 && !EardatekVersion2Activity.f216I && !eardatekVersion2Activity.au.isInterrupted()) {
                            eardatekVersion2Activity.ar.sendEmptyMessage(8);
                        } else if (m == 0) {
                            eardatekVersion2Activity.ar.sendEmptyMessage(23);
                        } else if (m == -2) {
                            C0539i.m643b("EardatekVersion2", "checklock thread call stop playing");
                        }
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            C0539i.m643b("EardatekVersion2", "check lock thread exit!");
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }

    class C0465b implements Runnable {
        int f207a = 0;
        final /* synthetic */ EardatekVersion2Activity f208b;

        C0465b(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f208b = eardatekVersion2Activity;
        }

        public void run() {
            try {
                Thread.sleep(1000);
                while (true) {
                    try {
                        Thread.sleep(1000);
                        if (this.f208b.f243b) {
                            if (C0546l.m653a().m699m() != 0) {
                                C0540j.m645a(this.f208b.ar, 32, 0, 1);
                            } else if (this.f208b.f241Z.getVisibility() == 0 && C0546l.m653a().m695i()) {
                                C0540j.m645a(this.f208b.ar, 32, C0546l.m653a().m696j(), 0);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static class C0467c extends C0466t<EardatekVersion2Activity> {
        C0467c(EardatekVersion2Activity eardatekVersion2Activity) {
            super(eardatekVersion2Activity);
        }

        public void handleMessage(Message message) {
            Log.e("TTTTT", "LiveTvPlayerHandler:" + message.what);
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) m217a();
            Log.e("TTTTT", "isPlaying: " + eardatekVersion2Activity.f260s.isPlaying());
            Log.e("TTTTT", "isPlaying: " + eardatekVersion2Activity.f260s.getPlayerState());
            if (eardatekVersion2Activity != null) {
                switch (message.what) {
                    case 3:
                        eardatekVersion2Activity.m227E();
                        return;
                    case 4:
                        eardatekVersion2Activity.m289c(false);
                        return;
                    case 5:
                        eardatekVersion2Activity.f227L.setText(eardatekVersion2Activity.f222F);
                        if (!C0589a.m788a().m795c() && C0589a.m788a().m801f()) {
                            String str = (String) message.obj;
                            eardatekVersion2Activity.f241Z.setText(String.format("%s%s", new Object[]{DTVApplication.m752b().getText(R.string.videocode), str}));
                        }
                        if (!eardatekVersion2Activity.f228M.isShown()) {
                            eardatekVersion2Activity.ad.setVisibility(0);
                            eardatekVersion2Activity.ad.setText(eardatekVersion2Activity.f222F);
                            return;
                        }
                        return;
                    case 6:
                        eardatekVersion2Activity.m289c(false);
                        eardatekVersion2Activity.f234S.setVisibility(4);
                        eardatekVersion2Activity.f229N.setBackgroundResource(R.drawable.bg_program_name_layout);
                        eardatekVersion2Activity.ad.setTextColor(DTVApplication.m752b().getColor(R.color.black));
                        C0527a.m597a(eardatekVersion2Activity.f229N, 0.2f, 0.9f, 4000);
                        return;
                    case 7:
                        eardatekVersion2Activity.f229N.setVisibility(8);
                        eardatekVersion2Activity.ad.setVisibility(4);
                        if (eardatekVersion2Activity.f224H && eardatekVersion2Activity.f252k) {
                            eardatekVersion2Activity.m284b(false);
                            return;
                        }
                        return;
                    case 8:
                        if (!eardatekVersion2Activity.aI) {
                            removeMessages(23);
                            eardatekVersion2Activity.f229N.setVisibility(8);
                            eardatekVersion2Activity.f234S.setVisibility(4);
                            eardatekVersion2Activity.f228M.setVisibility(0);
                            eardatekVersion2Activity.f237V.setText(eardatekVersion2Activity.getString(R.string.no_signal_tips));
                            eardatekVersion2Activity.f236U.setText(eardatekVersion2Activity.getString(R.string.ant_tips));
                            removeMessages(8);
                            return;
                        }
                        return;
                    case 9:
                        eardatekVersion2Activity.m346v();
                        removeMessages(23);
                        eardatekVersion2Activity.f229N.setVisibility(8);
                        eardatekVersion2Activity.f234S.setVisibility(4);
                        eardatekVersion2Activity.f228M.setVisibility(0);
                        eardatekVersion2Activity.f237V.setText(eardatekVersion2Activity.getString(R.string.i2c_error));
                        eardatekVersion2Activity.f236U.setText("");
                        removeMessages(8);
                        return;
                    case 10:
                        if (eardatekVersion2Activity.f252k) {
                            eardatekVersion2Activity.f234S.setVisibility(4);
                            return;
                        }
                        return;
                    case 11:
                        eardatekVersion2Activity.aa.setVisibility(4);
                        return;
                    case 13:
                    case 17:
                        return;
                    case 14:
                        eardatekVersion2Activity.f229N.setVisibility(0);
                        eardatekVersion2Activity.f229N.setBackgroundResource(R.drawable.photo_choose_bg);
                        eardatekVersion2Activity.ad.setTextColor(DTVApplication.m752b().getColor(R.color.white));
                        eardatekVersion2Activity.ad.setText(eardatekVersion2Activity.f222F);
                        return;
                    case 18:
                        eardatekVersion2Activity.m265a(message.arg1, message.arg2);
                        return;
                    case 19:
                        if (eardatekVersion2Activity.f252k) {
                            eardatekVersion2Activity.f234S.setImageResource(R.drawable.play);
                            eardatekVersion2Activity.f234S.setTag("play");
                            eardatekVersion2Activity.f234S.setVisibility(0);
                            eardatekVersion2Activity.f252k = false;
                            eardatekVersion2Activity.f253l = true;
                            EardatekVersion2Activity.f216I = false;
                            C0539i.m643b("EardatekVersion2", "handler SEND_DATA_FAIL_OPTION message");
                            if (eardatekVersion2Activity.as != null) {
                                eardatekVersion2Activity.as.interrupt();
                            }
                            eardatekVersion2Activity.m218A();
                            eardatekVersion2Activity.m283b("No Signal", 1000);
                            return;
                        }
                        return;
                    case 21:
                        C0539i.m643b("EardatekVersion2", "start change program....");
                        eardatekVersion2Activity.m229F();
                        eardatekVersion2Activity.bd = 0;
                        eardatekVersion2Activity.aI = false;
                        return;
                    case 22:
                        if (eardatekVersion2Activity.f260s.isPlaying() && eardatekVersion2Activity.f252k) {
                            eardatekVersion2Activity.m283b(eardatekVersion2Activity.getString(R.string.signal_weak), 1500);
                            eardatekVersion2Activity.f229N.setVisibility(8);
                            eardatekVersion2Activity.m346v();
                            eardatekVersion2Activity.f234S.setVisibility(0);
                            return;
                        }
                        return;
                    case 23:
                        removeMessages(8);
                        eardatekVersion2Activity.f228M.setVisibility(8);
                        removeMessages(23);
                        return;
                    case 24:
                        eardatekVersion2Activity.bp = true;
                        eardatekVersion2Activity.bo = false;
                        eardatekVersion2Activity.bn = true;
                        return;
                    case 25:
                        eardatekVersion2Activity.bq = true;
                        eardatekVersion2Activity.bo = false;
                        eardatekVersion2Activity.bn = false;
                        return;
                    case 26:
                        eardatekVersion2Activity.aD = true;
                        return;
                    case 27:
                        if (eardatekVersion2Activity.bt != null) {
                            if (eardatekVersion2Activity.bt.isShowing()) {
                                eardatekVersion2Activity.bt.dismiss();
                            }
                            eardatekVersion2Activity.bt = null;
                        }
                        if (eardatekVersion2Activity.br == null) {
                            eardatekVersion2Activity.m237J();
                        }
                        boolean a = C0548m.m703a((Context) eardatekVersion2Activity);
                        String b = C0548m.m704b(eardatekVersion2Activity.getApplicationContext());
                        C0539i.m643b("EardatekVersion2", "is wifi:" + a);
                        C0539i.m643b("EardatekVersion2", "check wifi:" + b);
                        if (!a || !C0546l.m663c(b)) {
                            eardatekVersion2Activity.m239K();
                            return;
                        }
                        return;
                    case 28:
                        eardatekVersion2Activity.m325m();
                        return;
                    case 29:
                        eardatekVersion2Activity.m353z();
                        return;
                    case 30:
                        C0567b.m774a(eardatekVersion2Activity, eardatekVersion2Activity.getString(R.string.switch_success), 1000);
                        eardatekVersion2Activity.aN = message.arg1;
                        C0549n.m706a((Context) eardatekVersion2Activity, "mode", eardatekVersion2Activity.aN);
                        return;
                    case 32:
                        if (message.arg2 == 0) {
                            eardatekVersion2Activity.f241Z.setText(eardatekVersion2Activity.getString(R.string.signal_strength_show) + message.arg1 + "dbm");
                            return;
                        } else {
                            eardatekVersion2Activity.f241Z.setText(eardatekVersion2Activity.getString(R.string.signal_strength_show) + "---");
                            return;
                        }
                    case 50:
                        eardatekVersion2Activity.m346v();
                        eardatekVersion2Activity.m239K();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private class C0468d extends SimpleOnGestureListener {
        final /* synthetic */ EardatekVersion2Activity f210a;

        private C0468d(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f210a = eardatekVersion2Activity;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int rawY = (int) motionEvent2.getRawY();
            int rawX = (int) motionEvent2.getRawX();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            this.f210a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            if (this.f210a.ax == 0) {
                this.f210a.ax = Math.min(i2, i);
            }
            if (((double) x) > ((double) i) / 2.0d && Math.abs(y - ((float) rawY)) > Math.abs(x - ((float) rawX))) {
                this.f210a.m287c(f2);
            } else if (((double) x) < ((double) i) / 2.0d && Math.abs(y - ((float) rawY)) > Math.abs(x - ((float) rawX)) && Math.abs(y - ((float) rawY)) > 50.0f) {
                this.f210a.m293d(3.0f * f2);
            }
            return true;
        }
    }

    public static final class C0469e extends Handler {
        private WeakReference<EardatekVersion2Activity> f211a;

        C0469e(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f211a = new WeakReference(eardatekVersion2Activity);
        }

        public void handleMessage(Message message) {
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) this.f211a.get();
            if (eardatekVersion2Activity != null) {
                switch (message.what) {
                    case 0:
                        if (eardatekVersion2Activity.f225J) {
                            C0567b.m774a(eardatekVersion2Activity.getApplicationContext(), DTVApplication.m752b().getString(R.string.connectsuccess), 3000);
                            eardatekVersion2Activity.ak = true;
                            eardatekVersion2Activity.m310h();
                            return;
                        }
                        return;
                    case 1:
                        if (eardatekVersion2Activity.f225J) {
                            C0567b.m774a(eardatekVersion2Activity.getApplicationContext(), eardatekVersion2Activity.getString(R.string.check_down), 3000);
                            eardatekVersion2Activity.ak = false;
                            if (eardatekVersion2Activity.f260s.isPlaying()) {
                                eardatekVersion2Activity.f260s.stop();
                            }
                            eardatekVersion2Activity.aN = 0;
                            return;
                        }
                        return;
                    case 2:
                        if (eardatekVersion2Activity.f225J) {
                            C0567b.m774a(eardatekVersion2Activity.getApplicationContext(), DTVApplication.m752b().getString(R.string.connecting), 3000);
                            return;
                        }
                        return;
                    case 3:
                        eardatekVersion2Activity.m346v();
                        eardatekVersion2Activity.f234S.setVisibility(4);
                        return;
                    case 4:
                        if (eardatekVersion2Activity.f252k) {
                            C0567b.m774a(eardatekVersion2Activity.getApplicationContext(), eardatekVersion2Activity.getString(R.string.check_down), 3000);
                            eardatekVersion2Activity.m346v();
                            eardatekVersion2Activity.f234S.setVisibility(4);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private static final class C0470f implements Runnable {
        private WeakReference<EardatekVersion2Activity> f212a;

        C0470f(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f212a = new WeakReference(eardatekVersion2Activity);
        }

        public void run() {
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) this.f212a.get();
            if (eardatekVersion2Activity != null) {
                eardatekVersion2Activity.f252k = true;
                while (eardatekVersion2Activity.f252k) {
                    if (eardatekVersion2Activity.f260s.isPlaying()) {
                        C0539i.m643b("EardatekVersion2", "myRunable 1");
                        eardatekVersion2Activity.f260s.stop();
                    }
                    eardatekVersion2Activity.ar.sendEmptyMessage(6);
                    if (eardatekVersion2Activity.f228M.isShown()) {
                        eardatekVersion2Activity.ar.sendEmptyMessage(23);
                    }
                    int i = 0;
                    while (!eardatekVersion2Activity.f224H && i < 2 && eardatekVersion2Activity.f252k) {
                        eardatekVersion2Activity.f253l = false;
                        C0539i.m643b("EardatekVersion2", Thread.currentThread().getId() + "MyRunable set replay");
                        eardatekVersion2Activity.m355a(eardatekVersion2Activity.f221E);
                        i++;
                    }
                    if (eardatekVersion2Activity.f224H) {
                        eardatekVersion2Activity.ar.sendEmptyMessage(13);
                        if (EardatekVersion2Activity.f216I && eardatekVersion2Activity.av == 2) {
                            eardatekVersion2Activity.ar.sendEmptyMessage(12);
                        }
                        EardatekVersion2Activity.f216I = false;
                        eardatekVersion2Activity.ar.sendEmptyMessageDelayed(22, 10000);
                        eardatekVersion2Activity.ar.sendEmptyMessageDelayed(29, 3000);
                        while (true) {
                            if (!eardatekVersion2Activity.f252k && eardatekVersion2Activity.as.isInterrupted()) {
                                break;
                            } else if (eardatekVersion2Activity.f253l) {
                                break;
                            } else {
                                try {
                                    Thread.sleep(600);
                                } catch (InterruptedException e) {
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }
                        C0539i.m643b("EardatekVersion2", Thread.currentThread().getId() + "stop playing");
                        C0546l.m653a().m694h();
                        eardatekVersion2Activity.m218A();
                    } else {
                        EardatekVersion2Activity.f216I = false;
                        eardatekVersion2Activity.ar.sendEmptyMessage(7);
                        eardatekVersion2Activity.ar.sendEmptyMessage(8);
                        eardatekVersion2Activity.f252k = false;
                        return;
                    }
                }
                C0539i.m643b("EardatekVersion2", Thread.currentThread().getId() + "MyRunable exit!");
            }
        }
    }

    private static final class C0471g implements Runnable {
        private WeakReference<EardatekVersion2Activity> f213a;

        C0471g(EardatekVersion2Activity eardatekVersion2Activity) {
            this.f213a = new WeakReference(eardatekVersion2Activity);
        }

        public void run() {
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) this.f213a.get();
            if (eardatekVersion2Activity != null) {
                if ((eardatekVersion2Activity.f252k || !eardatekVersion2Activity.at.isInterrupted()) && C0546l.m653a().m695i()) {
                    int j = C0546l.m653a().m696j();
                    Message obtainMessage = eardatekVersion2Activity.ar.obtainMessage(18);
                    obtainMessage.arg1 = j;
                    obtainMessage.arg2 = C0546l.m653a().m697k();
                    obtainMessage.obj = C0546l.m653a().m698l();
                    eardatekVersion2Activity.ar.sendMessage(obtainMessage);
                }
            }
        }
    }

    static class C0472h implements Runnable {
        private String f214a;
        private WeakReference<EardatekVersion2Activity> f215b;

        C0472h(EardatekVersion2Activity eardatekVersion2Activity, String str) {
            this.f215b = new WeakReference(eardatekVersion2Activity);
            this.f214a = str;
        }

        public void run() {
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) this.f215b.get();
            Message obtainMessage = eardatekVersion2Activity.ar.obtainMessage(30);
            boolean b = C0546l.m653a().m692f().equals("DVB-T/T2") ? C0546l.m653a().m688b(this.f214a) : C0546l.m653a().m692f().equals("ISDBT") ? C0546l.m653a().m684a(this.f214a) : false;
            if (b) {
                if (this.f214a.equals("ISDBT") || this.f214a.equals("Europe")) {
                    obtainMessage.arg1 = 0;
                } else {
                    obtainMessage.arg1 = 1;
                }
                eardatekVersion2Activity.ar.sendMessage(obtainMessage);
            }
        }
    }

    private static class C0473i extends C0466t<EardatekVersion2Activity> {
        public C0473i(EardatekVersion2Activity eardatekVersion2Activity) {
            super(eardatekVersion2Activity);
        }

        public void handleMessage(Message message) {
            EardatekVersion2Activity eardatekVersion2Activity = (EardatekVersion2Activity) m217a();
            if (eardatekVersion2Activity != null) {
                switch (message.getData().getInt("event")) {
                    case 3:
                    case EventHandler.MediaPlayerPlaying /*260*/:
                    case EventHandler.MediaPlayerPaused /*261*/:
                    case EventHandler.MediaPlayerStopped /*262*/:
                    case EventHandler.MediaPlayerTimeChanged /*267*/:
                    case EventHandler.MediaPlayerPositionChanged /*268*/:
                    case EventHandler.MediaPlayerVout /*274*/:
                        return;
                    case EventHandler.MediaPlayerEndReached /*265*/:
                        C0539i.m643b("EardatekVersion2", "MediaPlayerEndReached");
                        return;
                    case EventHandler.MediaPlayerEncounteredError /*266*/:
                        C0539i.m643b("EardatekVersion2", "MediaPlayerEncounteredError");
                        eardatekVersion2Activity.f260s.stop();
                        eardatekVersion2Activity.f253l = true;
                        eardatekVersion2Activity.f252k = false;
                        eardatekVersion2Activity.f234S.setVisibility(0);
                        eardatekVersion2Activity.f234S.setImageResource(R.drawable.play);
                        eardatekVersion2Activity.f234S.setTag("play");
                        eardatekVersion2Activity.as.interrupt();
                        eardatekVersion2Activity.m218A();
                        eardatekVersion2Activity.m283b("MediaPlayerEncounteredError", 500);
                        return;
                    case EventHandler.HardwareAccelerationError /*12288*/:
                        C0539i.m643b("EardatekVersion2", "HardwareAccelerationError");
                        eardatekVersion2Activity.m283b(DTVApplication.m752b().getString(R.string.harddecodeerror), 500);
                        eardatekVersion2Activity.f253l = true;
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void m218A() {
        if (this.au != null && this.au.isAlive()) {
            this.au.interrupt();
        }
        if (this.at != null && this.at.isAlive()) {
            this.at.interrupt();
        }
    }

    private void m221B() {
        if (this.aZ == null || !this.aZ.isShowing()) {
            this.aZ = new SweetAlertDialog(this, 3).setTitleText(getResources().getString(R.string.deletetips)).setContentText(getResources().getString(R.string.deletecontent)).setCancelText(getResources().getString(R.string.no)).setConfirmText(getResources().getString(R.string.yes)).showCancelButton(true).setCancelClickListener(new OnSweetClickListener(this) {
                final /* synthetic */ EardatekVersion2Activity f146a;

                {
                    this.f146a = r1;
                }

                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismissWithAnimation();
                }
            }).setConfirmClickListener(new OnSweetClickListener(this) {
                final /* synthetic */ EardatekVersion2Activity f145a;

                {
                    this.f145a = r1;
                }

                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    if (this.f145a.f252k) {
                        this.f145a.m346v();
                    }
                    try {
                        this.f145a.ay.m592b();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    C0595a.m827c();
                    C0536g.m632b("Channel.txt");
                    C0502a.m504a(2, null);
                    sweetAlertDialog.setTitleText(this.f145a.getResources().getString(R.string.delete)).setContentText(this.f145a.getResources().getString(R.string.deleteconfirm)).setConfirmText(this.f145a.getResources().getString(R.string.yes)).showCancelButton(false).setCancelClickListener(null).setConfirmClickListener(null).changeAlertType(2);
                }
            });
            this.aZ.setCancelable(true);
            this.aZ.setCanceledOnTouchOutside(true);
            this.aZ.show();
        }
    }

    private float m222C() {
        if (this.f244c != 0) {
            return ((float) this.f244c) / 255.0f;
        }
        int i;
        try {
            i = System.getInt(getContentResolver(), "screen_brightness");
        } catch (SettingNotFoundException e) {
            i = 51;
        }
        this.f244c = i;
        return ((float) i) / 255.0f;
    }

    private float m224D() {
        return ((float) this.f251j.getStreamVolume(3)) / ((float) this.f246e);
    }

    private void m227E() {
        Object obj = getResources().getConfiguration().orientation == 1 ? 1 : null;
        int width = getWindow().getDecorView().getWidth();
        int height = getWindow().getDecorView().getHeight();
        if (this.aF && this.aE > 0 && obj == null) {
            width -= this.aE;
        }
        if (this.f260s != null) {
            this.f260s.setWindowSize(width, height);
        }
        double d = (double) width;
        double d2 = (double) height;
        if ((width > height && obj != null) || (width < height && obj == null)) {
            d = (double) height;
            d2 = (double) width;
        }
        if (d * d2 == 0.0d || this.f255n * this.f254m == 0) {
            C0539i.m643b("EardatekVersion2", "mVideoWidth * mVideoHeight == 0");
            if (obj != null) {
                LayoutParams layoutParams = this.f217A.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = getResources().getDimensionPixelSize(R.dimen.surface_height);
                this.f217A.setLayoutParams(layoutParams);
                this.f261t.invalidate();
                this.f262u.invalidate();
                return;
            }
            return;
        }
        double d3;
        if (this.f259r == this.f258q) {
            d3 = (double) this.f257p;
            d3 = ((double) this.f257p) / ((double) this.f256o);
        } else {
            d3 = ((((double) this.f257p) * ((double) this.f258q)) / ((double) this.f259r)) / ((double) this.f256o);
        }
        double d4 = d / d2;
        this.f245d = C0549n.m710b(getApplicationContext(), this.f221E, 1);
        if (this.f245d == 1) {
            this.f231P.setText("16:9");
        } else {
            this.f231P.setText("4:3");
        }
        double d5;
        switch (this.f245d) {
            case 0:
                if (d4 >= d3) {
                    d5 = d2;
                    d2 = d3 * d2;
                    d3 = d5;
                    break;
                }
                d3 = d / d3;
                d2 = d;
                break;
            case 1:
                if (d4 >= 1.7777777777777777d) {
                    d5 = d2;
                    d2 = 1.7777777777777777d * d2;
                    d3 = d5;
                    break;
                }
                d3 = d / 1.7777777777777777d;
                d2 = d;
                break;
            case 2:
                if (d4 >= 1.3333333333333333d) {
                    d5 = d2;
                    d2 = 1.3333333333333333d * d2;
                    d3 = d5;
                    break;
                }
                d3 = d / 1.3333333333333333d;
                d2 = d;
                break;
            default:
                d3 = d2;
                d2 = d;
                break;
        }
        SurfaceView surfaceView = this.f261t;
        SurfaceView surfaceView2 = this.f262u;
        FrameLayout frameLayout = this.f267z;
        int ceil = (int) Math.ceil((((double) this.f255n) * d2) / ((double) this.f257p));
        int ceil2 = (int) Math.ceil((((double) this.f254m) * d3) / ((double) this.f256o));
        LayoutParams layoutParams2 = surfaceView.getLayoutParams();
        layoutParams2.width = ceil;
        layoutParams2.height = ceil2;
        surfaceView.setLayoutParams(layoutParams2);
        surfaceView2.setLayoutParams(layoutParams2);
        int floor = (int) Math.floor(d2);
        height = (int) Math.floor(d3);
        LayoutParams layoutParams3 = frameLayout.getLayoutParams();
        layoutParams3.width = floor;
        layoutParams3.height = height;
        frameLayout.setLayoutParams(layoutParams3);
        if (this.aF && this.aE > 0) {
            LayoutParams layoutParams4 = this.aU.getLayoutParams();
            if (obj != null) {
                layoutParams4.width = -1;
            } else {
                layoutParams4.width = getWindow().getDecorView().getWidth() - this.aE;
            }
            this.aU.setLayoutParams(layoutParams4);
            this.aU.invalidate();
        }
        if (obj != null) {
            layoutParams = this.f217A.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            this.f217A.setLayoutParams(layoutParams);
        } else {
            layoutParams = this.f217A.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            this.f217A.setLayoutParams(layoutParams);
        }
        surfaceView.invalidate();
        surfaceView2.invalidate();
    }

    private void m229F() {
        if (this.f219C.m73c() != null) {
            this.f219C.m71a(this.f221E);
            C0539i.m643b("EardatekVersion2", "changeProgram set replay");
            if (this.f252k) {
                this.f253l = true;
            } else {
                m282b(this.f221E);
            }
            this.am = true;
            this.f239X.scrollToPosition(this.al);
            C0502a c0502a = new C0502a();
            c0502a.m506a(5);
            c0502a.m507a(Integer.valueOf(this.al));
            C0732c.m1501a().m1515c(c0502a);
        }
    }

    private void m231G() {
        this.ag = new BroadcastReceiver(this) {
            final /* synthetic */ EardatekVersion2Activity f150a;

            {
                this.f150a = r1;
            }

            public void onReceive(Context context, Intent intent) {
                int i = -1;
                StringBuilder stringBuilder = new StringBuilder();
                int intExtra = intent.getIntExtra("level", -1);
                int intExtra2 = intent.getIntExtra("scale", -1);
                int intExtra3 = intent.getIntExtra("status", -1);
                int intExtra4 = intent.getIntExtra("health", -1);
                if (intExtra >= 0 && intExtra2 > 0) {
                    i = (intExtra * 100) / intExtra2;
                }
                this.f150a.ah = i;
                stringBuilder.append("The phone");
                if (3 != intExtra4) {
                    switch (intExtra3) {
                        case 1:
                            stringBuilder.append("no battery.");
                            break;
                        case 2:
                            stringBuilder.append("'s battery");
                            if (i > 33) {
                                if (i > 84) {
                                    stringBuilder.append(" will be fully charged.");
                                    break;
                                } else {
                                    stringBuilder.append(" is charging.[").append(i).append("]");
                                    break;
                                }
                            }
                            stringBuilder.append(" is charging, battery level is low[").append(i).append("]");
                            break;
                        case 3:
                        case 4:
                            if (i != 0) {
                                if (i > 0 && i <= 33) {
                                    stringBuilder.append(" is about ready to be recharged, battery level is low[").append(i).append("]");
                                    break;
                                } else {
                                    stringBuilder.append("'s battery level is[").append(i).append("]");
                                    break;
                                }
                            }
                            stringBuilder.append(" needs charging right away.");
                            break;
                            break;
                        case 5:
                            stringBuilder.append(" is fully charged.");
                            break;
                        default:
                            stringBuilder.append("'s battery is indescribable!");
                            break;
                    }
                }
                stringBuilder.append("'s battery feels very hot!");
                stringBuilder.append(' ');
            }
        };
        registerReceiver(this.ag, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    }

    private void m233H() {
        SweetAlertDialog confirmClickListener = new SweetAlertDialog(this, 3).setTitleText(getResources().getString(R.string.quittitle)).setContentText(getResources().getString(R.string.poweroff)).setCancelText(getResources().getString(R.string.no)).setConfirmText(getResources().getString(R.string.yes)).showCancelButton(true).setCancelClickListener(new OnSweetClickListener(this) {
            final /* synthetic */ EardatekVersion2Activity f153a;

            {
                this.f153a = r1;
            }

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        }).setConfirmClickListener(new OnSweetClickListener(this) {
            final /* synthetic */ EardatekVersion2Activity f151a;

            {
                this.f151a = r1;
            }

            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                this.f151a.m327n();
            }
        });
        confirmClickListener.setCancelable(true);
        confirmClickListener.setCanceledOnTouchOutside(true);
        confirmClickListener.show();
    }

    private void m235I() {
        this.bm = new OrientationEventListener(this, this) {
            final /* synthetic */ EardatekVersion2Activity f154a;

            public void onOrientationChanged(int i) {
                if (!this.f154a.aB && !EardatekVersion2Activity.f216I) {
                    if (!(this.f154a.aI || this.f154a.f252k || C0560v.m740a(i) == 1)) {
                        this.f154a.setRequestedOrientation(1);
                        this.f154a.m334p();
                    }
                    if (!this.f154a.aD) {
                        return;
                    }
                    if ((i < 0 || i > 45) && i <= 315) {
                        if (i <= 225 || i > 315) {
                            if (i > 45 && i <= 135) {
                                if (this.f154a.bo) {
                                    if (this.f154a.bn || this.f154a.bq) {
                                        this.f154a.bp = true;
                                        this.f154a.bo = false;
                                        this.f154a.bn = true;
                                    }
                                } else if (!this.f154a.bn && this.f154a.f252k && this.f154a.getRequestedOrientation() != 8) {
                                    C0539i.m643b("EardatekVersion2", "change to reverse landscape");
                                    this.f154a.setRequestedOrientation(8);
                                    this.f154a.m331o();
                                    this.f154a.aD = false;
                                    this.f154a.ar.sendEmptyMessageDelayed(26, 1500);
                                    this.f154a.bn = true;
                                    this.f154a.bo = false;
                                }
                            }
                        } else if (this.f154a.bo) {
                            if (this.f154a.bn || this.f154a.bq) {
                                this.f154a.bp = true;
                                this.f154a.bo = false;
                                this.f154a.bn = true;
                            }
                        } else if (!this.f154a.bn && this.f154a.f252k) {
                            C0539i.m643b("EardatekVersion2", "change to  landscape");
                            this.f154a.setRequestedOrientation(0);
                            this.f154a.m331o();
                            this.f154a.aD = false;
                            this.f154a.ar.sendEmptyMessageDelayed(26, 1500);
                            this.f154a.bn = true;
                            this.f154a.bo = false;
                        }
                    } else if (this.f154a.bo) {
                        if (!this.f154a.bn || this.f154a.bp) {
                            this.f154a.bq = true;
                            this.f154a.bo = false;
                            this.f154a.bn = false;
                        }
                    } else if (this.f154a.bn) {
                        C0539i.m643b("EardatekVersion2", "change to  portrait");
                        this.f154a.setRequestedOrientation(1);
                        this.f154a.m334p();
                        this.f154a.aD = false;
                        this.f154a.ar.sendEmptyMessageDelayed(26, 1500);
                        this.f154a.bn = false;
                        this.f154a.bo = false;
                    }
                }
            }
        };
        this.bm.enable();
    }

    private void m237J() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.br = new C0547a(new C0453a(this) {
            final /* synthetic */ EardatekVersion2Activity f167a;

            {
                this.f167a = r1;
            }

            public void mo2094a() {
                if (this.f167a.f260s.isPlaying()) {
                    this.f167a.m346v();
                    C0546l.m653a().m690d();
                }
                String stringBuffer = DTVApplication.m753c().toString();
                String b = C0548m.m704b(DTVApplication.m750a());
                C0539i.m643b("EardatekVersion2", "save wifiname:" + stringBuffer);
                C0539i.m643b("EardatekVersion2", "current:" + b);
                if (!stringBuffer.equals(b) && !C0546l.m663c(b)) {
                    this.f167a.m239K();
                } else if (!stringBuffer.equals(b) && C0546l.m663c(b) && !C0546l.m653a().m700n()) {
                    C0539i.m643b("EardatekVersion2", "reconnect...");
                    C0546l.m653a().m690d();
                    DTVApplication.m751a(b);
                    if (this.f167a.bs != null && this.f167a.bs.isShowing()) {
                        this.f167a.bs.dismiss();
                        this.f167a.bs = null;
                    }
                } else if (!C0546l.m663c(b)) {
                    this.f167a.m239K();
                } else if (!C0548m.m703a(this.f167a)) {
                    this.f167a.m239K();
                } else if (this.f167a.bs != null && this.f167a.bs.isShowing()) {
                    this.f167a.bs.dismiss();
                    this.f167a.bs = null;
                }
            }
        });
        registerReceiver(this.br, intentFilter);
    }

    private void m239K() {
        if (this.bs == null || !this.bs.isShowing()) {
            View inflate = getLayoutInflater().inflate(R.layout.wifi_dialog, null);
            this.bs = new Dialog(this, R.style.transparentFrameWindowStyle);
            ((Button) inflate.findViewById(R.id.yes)).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ EardatekVersion2Activity f168a;

                {
                    this.f168a = r1;
                }

                public void onClick(View view) {
                    C0548m.m702a(this.f168a);
                    this.f168a.aG = false;
                }
            });
            this.bs.setContentView(inflate, new LayoutParams(-1, -2));
            Window window = this.bs.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams attributes = window.getAttributes();
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            attributes.x = 0;
            attributes.y = 0;
            attributes.width = C0533d.m627a(getApplicationContext(), 345.0f);
            attributes.height = -2;
            attributes.alpha = 0.9f;
            this.bs.onWindowAttributesChanged(attributes);
            this.bs.show();
        }
    }

    private void m241L() {
        if (this.bt == null) {
            View inflate = getLayoutInflater().inflate(R.layout.connecting_dialog_layout, null);
            this.bt = new Dialog(this, R.style.transparentFrameWindowStyle);
            ((ProgressWheel) inflate.findViewById(R.id.progreswheel)).m767b();
            this.bt.setContentView(inflate, new LayoutParams(-1, -2));
            Window window = this.bt.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams attributes = window.getAttributes();
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            attributes.x = 0;
            attributes.y = 0;
            attributes.width = C0533d.m627a(getApplicationContext(), 345.0f);
            attributes.height = -2;
            attributes.alpha = 0.9f;
            this.bt.onWindowAttributesChanged(attributes);
            this.bt.setCanceledOnTouchOutside(true);
            this.bt.show();
        }
    }

    private View m259a(C0501b c0501b) {
        View inflate = this.aq.inflate(R.layout.tab_indicator, null, false);
        LinearLayout linearLayout = (LinearLayout) inflate.findViewById(R.id.indicator_layout);
        ((TextView) inflate.findViewById(R.id.txt_indicator)).setText(c0501b.m502a());
        linearLayout.setBackgroundResource(R.drawable.selector_tab_indicator_background);
        return inflate;
    }

    private void m263a(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.f244c = (int) (255.0f * f);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = f;
        getWindow().setAttributes(attributes);
        this.ab.setText(String.format(Locale.ENGLISH, "%d%%", new Object[]{Integer.valueOf((int) (100.0f * f))}));
    }

    private void m264a(int i) {
        C0500a a;
        Log.e("KKKKK", "i=" + this.al);
        if (i < 0) {
            if (this.al == 0) {
                this.al = this.f219C.m73c().size() - 1;
            } else {
                this.al--;
            }
        } else if (this.al >= this.f219C.m73c().size() - 1) {
            this.al = 0;
        } else {
            this.al++;
        }
        Log.e("KKKKK", "index=" + this.al);
        this.f221E = ((C0509a) this.f219C.m73c().get(this.al)).mo2122c();
        try {
            a = this.ay.m588a(this.f221E);
        } catch (SQLException e) {
            C0539i.m643b("EardatekVersion2", "getChannelInfo SQLException ");
            e.printStackTrace();
            a = null;
        }
        if (a != null) {
            this.f222F = a.m489a();
        }
        this.ar.sendEmptyMessage(5);
        this.ar.sendEmptyMessage(14);
        this.ar.sendEmptyMessageDelayed(21, 800);
    }

    private void m265a(int i, int i2) {
        if (i > -50) {
            this.f230O.setImageResource(R.drawable.signal_4);
        } else if (i > -60) {
            this.f230O.setImageResource(R.drawable.signal_4);
        } else if (i > -70) {
            this.f230O.setImageResource(R.drawable.signal_3);
        } else if (i > -80) {
            this.f230O.setImageResource(R.drawable.signal_2);
        } else if (i > -100) {
            this.f230O.setImageResource(R.drawable.signal_1);
        } else {
            this.f230O.setImageResource(R.drawable.signal_1);
        }
    }

    private void m266a(FloatingActionButton floatingActionButton) {
        floatingActionButton.setVisibility(0);
        if (VERSION.SDK_INT >= 14) {
            ViewCompat.animate(floatingActionButton).translationY(0.0f).setInterpolator(new FastOutSlowInInterpolator()).withLayer().setListener(null).start();
        }
    }

    private boolean m270a(MotionEvent motionEvent) {
        float rawX;
        switch (motionEvent.getAction()) {
            case 0:
                this.f249h = motionEvent.getRawX();
                this.f248g = motionEvent.getRawY();
                if (this.av == 2 && this.f239X.getVisibility() != 0) {
                    if (this.bg == 1) {
                        m295d(false);
                    } else if (this.bg == -1) {
                        m299e(false);
                    }
                    this.bg = 0;
                    this.be = motionEvent.getRawY();
                    this.bf = 0.0f;
                    Display defaultDisplay = getWindowManager().getDefaultDisplay();
                    this.bh = defaultDisplay.getWidth();
                    this.bi = defaultDisplay.getHeight();
                    if (((double) motionEvent.getRawX()) <= (((double) this.bh) * 7.0d) / 10.0d) {
                        if (((double) motionEvent.getRawX()) >= (((double) this.bh) * 3.0d) / 10.0d) {
                            this.bg = 0;
                            break;
                        }
                        this.bg = -1;
                        this.bl = m222C();
                        break;
                    }
                    this.bg = 1;
                    this.bk = m224D();
                    break;
                }
            case 1:
                if (this.av == 2 && this.f239X.getVisibility() != 0) {
                    if (this.bg == 1) {
                        m295d(false);
                    } else if (this.bg == -1) {
                        m299e(false);
                    }
                    this.bg = 0;
                    this.bj = false;
                }
                if (!this.aa.isShown()) {
                    rawX = motionEvent.getRawX() - this.f249h;
                    float rawY = motionEvent.getRawY() - this.f248g;
                    if (Math.abs(rawX) <= 150.0f || Math.abs(rawX) <= Math.abs(rawY * 2.0f) || f216I || this.av != 2) {
                        if (this.f226K.getVisibility() == 4 && !this.f229N.isShown() && this.f239X.getVisibility() != 0) {
                            m289c(true);
                            this.aX.sendEmptyMessage(0);
                            break;
                        }
                        m289c(false);
                        if (this.f239X.getVisibility() == 0) {
                            this.aX.sendEmptyMessage(0);
                            break;
                        }
                    }
                    this.ar.removeMessages(21);
                    this.f228M.setVisibility(8);
                    this.aI = true;
                    if (this.f260s.isPlaying()) {
                        C0539i.m643b("EardatekVersion2", "surfaceViewTouchEvent stop");
                        this.f260s.stop();
                        m340s();
                        this.f252k = false;
                        this.f224H = false;
                        this.f253l = true;
                        this.as.interrupt();
                        m218A();
                    }
                    if (rawX < 0.0f) {
                        this.bd = 1;
                    } else {
                        this.bd = -1;
                    }
                    Log.e("KKKKK", "C:" + this.bd);
                    m264a(this.bd);
                    break;
                }
                break;
            case 2:
                if (this.av == 2 && this.f239X.getVisibility() != 0) {
                    rawX = (this.be - motionEvent.getRawY()) / ((float) this.bi);
                    if (this.bj || (rawX <= 0.1f && rawX >= -0.1f)) {
                        if (this.bj) {
                            if (this.bg != 1) {
                                if (this.bg == -1) {
                                    m263a(rawX + this.bl);
                                    break;
                                }
                            }
                            m277b(rawX + this.bk);
                            break;
                        }
                    }
                    this.bj = true;
                    this.be = motionEvent.getRawY();
                    if (this.bg != 1) {
                        if (this.bg == -1) {
                            m299e(true);
                            break;
                        }
                    }
                    m295d(true);
                    break;
                }
                break;
        }
        return true;
    }

    private void m277b(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        } else if (f > 1.0f) {
            f = 1.0f;
        }
        this.f251j.setStreamVolume(3, (int) (((float) this.f246e) * f), 0);
        this.ab.setText(String.format(Locale.ENGLISH, "%d%%", new Object[]{Integer.valueOf((int) (100.0f * f))}));
    }

    private void m278b(int i) {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_mode, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.radioGroup1);
        radioGroup.check(i);
        ((Button) inflate.findViewById(R.id.yes)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ EardatekVersion2Activity f171c;

            public void onClick(View view) {
                new Thread(new C0472h(this.f171c, radioGroup.getCheckedRadioButtonId() == R.id.radio_isdbt ? "ISDBT" : "DVB-T/T2")).start();
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate, new LayoutParams(-1, -2));
        Window window = dialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams attributes = window.getAttributes();
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = C0533d.m627a(getApplicationContext(), 345.0f);
        attributes.height = -2;
        attributes.alpha = 0.9f;
        dialog.onWindowAttributesChanged(attributes);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void m282b(String str) {
        if (this.as != null) {
            this.as.interrupt();
        }
        this.as = new Thread(new C0470f(this));
        this.as.start();
    }

    private void m283b(String str, int i) {
        Toast.makeText(getApplicationContext(), str, i).show();
    }

    private void m284b(boolean z) {
        if (z) {
            this.f234S.setImageResource(R.drawable.play);
            this.f234S.setVisibility(0);
            this.f234S.setTag("play");
            this.aj = false;
            this.ar.sendEmptyMessageDelayed(10, 3000);
        }
    }

    private void m287c(float f) {
        m289c(false);
        if (this.av == 2 || f216I) {
            int streamVolume = this.f251j.getStreamVolume(3);
            if (f >= ((float) C0533d.m627a(this, 5.0f))) {
                if (streamVolume < this.f246e) {
                    streamVolume++;
                }
            } else if (f <= ((float) (-C0533d.m627a(this, 5.0f))) && streamVolume > 0) {
                streamVolume--;
            }
            this.f234S.setVisibility(4);
            this.aa.setVisibility(0);
            this.ac.setImageResource(R.drawable.vol);
            int i = (streamVolume * 100) / this.f246e;
            this.f251j.setStreamVolume(3, streamVolume, 0);
            this.ab.setText(String.format(Locale.ENGLISH, "%d%%", new Object[]{Integer.valueOf(i)}));
            this.ar.removeMessages(11);
            this.ar.sendEmptyMessageDelayed(11, 3000);
        }
    }

    private void m288c(int i) {
        View inflate = getLayoutInflater().inflate(R.layout.dialog_dvb_region, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        final RadioGroup radioGroup = (RadioGroup) inflate.findViewById(R.id.radioGroup1);
        radioGroup.check(i);
        ((Button) inflate.findViewById(R.id.yes)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ EardatekVersion2Activity f174c;

            public void onClick(View view) {
                new Thread(new C0472h(this.f174c, radioGroup.getCheckedRadioButtonId() == R.id.radio_aus ? "Australia" : "Europe")).start();
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate, new LayoutParams(-1, -2));
        Window window = dialog.getWindow();
        window.setGravity(17);
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams attributes = window.getAttributes();
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = C0533d.m627a(getApplicationContext(), 345.0f);
        attributes.height = -2;
        attributes.alpha = 0.9f;
        dialog.onWindowAttributesChanged(attributes);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void m289c(boolean z) {
        if (C0546l.m653a().m700n()) {
            this.ar.removeMessages(4);
            if (!z) {
                C0527a.m599c(this.f235T, 1.0f, 0.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                C0527a.m599c(this.f226K, 1.0f, 0.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                if (this.f252k) {
                    this.f234S.setVisibility(4);
                }
                if (this.f239X.getScrollState() == 0) {
                    C0527a.m599c(this.f238W, 1.0f, 0.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                    return;
                }
                this.ar.removeMessages(4);
                this.ar.sendEmptyMessageDelayed(4, 5000);
            } else if (this.f252k || this.av != 1) {
                if (this.at == null || !this.at.isAlive()) {
                    this.at = new Thread(new C0471g(this));
                    this.at.start();
                }
                this.f232Q.setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date(System.currentTimeMillis())));
                this.f233R.setText(String.format(Locale.ENGLISH, "%3d%%", new Object[]{Integer.valueOf(this.ah)}));
                if (getResources().getConfiguration().orientation == 1) {
                    this.f235T.setVisibility(4);
                } else {
                    C0527a.m598b(this.f235T, 0.0f, 1.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                }
                C0527a.m598b(this.f226K, 0.0f, 1.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                C0527a.m598b(this.f238W, 0.0f, 1.0f, SwipeCards.DEFAULT_ANIMATION_DURATION);
                this.f234S.setVisibility(4);
                this.ar.sendEmptyMessageDelayed(4, 5000);
            }
        }
    }

    private void m293d(float f) {
        m289c(false);
        if (this.av == 2 || f216I) {
            if (this.aw) {
                m359e();
            }
            if (this.aC < 0.0f) {
                this.aC = getWindow().getAttributes().screenBrightness;
                if (this.aC <= 0.0f) {
                    this.aC = 0.5f;
                }
                if (this.aC < 0.01f) {
                    this.aC = 0.01f;
                }
            }
            this.f234S.setVisibility(4);
            this.aa.setVisibility(0);
            this.ac.setImageResource(R.drawable.bringhtbess);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.screenBrightness = this.aC + (f / ((float) this.ax));
            if (attributes.screenBrightness > 1.0f) {
                attributes.screenBrightness = 1.0f;
            } else if (attributes.screenBrightness < 0.01f) {
                attributes.screenBrightness = 0.01f;
            }
            getWindow().setAttributes(attributes);
            this.aC = attributes.screenBrightness;
            this.ab.setText(String.format(Locale.ENGLISH, "%d%%", new Object[]{Integer.valueOf((int) (attributes.screenBrightness * 100.0f))}));
            this.ar.removeMessages(11);
            this.ar.sendEmptyMessageDelayed(11, 1700);
        }
    }

    private void m295d(boolean z) {
        if (z) {
            m289c(false);
            if (this.av == 2 || f216I) {
                this.f234S.setVisibility(4);
                this.aa.setVisibility(0);
                this.ac.setImageResource(R.drawable.vol);
                this.ar.removeMessages(11);
                this.ar.sendEmptyMessageDelayed(11, 2000);
                return;
            }
            return;
        }
        this.ar.removeMessages(11);
        this.ar.sendEmptyMessageDelayed(11, 1000);
    }

    private void m299e(boolean z) {
        if (z) {
            m289c(false);
            if (this.av == 2 || f216I) {
                if (this.aw) {
                    m359e();
                }
                this.f234S.setVisibility(4);
                this.aa.setVisibility(0);
                this.ac.setImageResource(R.drawable.bringhtbess);
                this.ar.removeMessages(11);
                this.ar.sendEmptyMessageDelayed(11, 2000);
                return;
            }
            return;
        }
        this.ar.removeMessages(11);
        this.ar.sendEmptyMessageDelayed(11, 1000);
    }

    private void m310h() {
        new Thread(this) {
            final /* synthetic */ EardatekVersion2Activity f144a;

            class C04491 implements C0448a {
                final /* synthetic */ AnonymousClass12 f143a;

                C04491(AnonymousClass12 anonymousClass12) {
                    this.f143a = anonymousClass12;
                }

                public void mo2088a(byte[] bArr, int i) {
                    if (C0589a.m788a().m803g()) {
                        this.f143a.f144a.aS.m214a(bArr, i);
                    }
                    if (C0589a.m788a().m804h()) {
                        this.f143a.f144a.aW.m181a(bArr, i);
                    }
                }
            }

            {
                this.f144a = r1;
            }

            public void run() {
                if (this.f144a.aT == null || !this.f144a.aT.m730e()) {
                    this.f144a.aT = new C0553q(new InetSocketAddress("localhost", 8888), new InetSocketAddress(C0546l.m653a().m687b(), 8000));
                    if (C0589a.m788a().m803g() || C0589a.m788a().m804h()) {
                        this.f144a.aT.m726a(new C04491(this));
                    } else {
                        this.f144a.aT.m726a(null);
                    }
                    if (this.f144a.aT.m728c()) {
                        C0546l.m653a().m681a(this.f144a.aT);
                    }
                }
            }
        }.start();
    }

    private List<C0598a> m312i() {
        C0521a c0521a = new C0521a(DTVApplication.m750a());
        List<C0598a> arrayList = new ArrayList();
        try {
            for (C0500a c0500a : c0521a.m589a()) {
                try {
                    arrayList.add(new C0598a(C0538h.m641a(c0500a.m492b()).m633a(), C0538h.m641a(c0500a.m492b()).m636b(), c0500a.m489a()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                c0521a.m594c();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            return arrayList;
        } catch (SQLException e3) {
            try {
                c0521a.m594c();
            } catch (SQLException e22) {
                e22.printStackTrace();
            }
            return null;
        }
    }

    private void m316j() {
        try {
            this.aW.m180a(C0562a.f505a).m179a(new C0454b(this) {
                final /* synthetic */ EardatekVersion2Activity f175a;

                {
                    this.f175a = r1;
                }

                public void mo2095a() {
                    Log.e("JNIMsg", "OnParseSuccess");
                }

                public void mo2096b() {
                    Log.e("JNIMsg", "OnParseFail");
                }
            }).m184c();
        } catch (Exception e) {
            Log.e("JNIMsg", e.toString());
        }
    }

    private void m319k() {
        this.aR = (Button) findViewById(R.id.record);
        this.aQ = (LinearLayout) findViewById(R.id.record_view);
        this.aS = new C0455r(this, this.aQ, this.aR) {
            final /* synthetic */ EardatekVersion2Activity f192a;

            public String mo2097a() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
                StringBuilder stringBuilder = new StringBuilder(this.f192a.f222F);
                stringBuilder.append("_").append(simpleDateFormat.format(new java.util.Date())).append(".ts");
                return stringBuilder.toString();
            }
        };
    }

    private void m322l() {
        this.aO = (Button) findViewById(R.id.minilist);
        this.aO.setVisibility(4);
        this.aO.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ EardatekVersion2Activity f193a;

            {
                this.f193a = r1;
            }

            public void onClick(View view) {
                this.f193a.ar.sendEmptyMessage(4);
                this.f193a.aX.sendEmptyMessage(1);
            }
        });
        this.aP = (FrameLayout) findViewById(R.id.minilistLayout);
    }

    private void m325m() {
        if (this.aG && this.aH) {
            this.aJ.m736a(this.aJ.m733a(this.aK.replace("\"", ""), "", 1, "wifi"));
            m241L();
        }
        if (this.aH) {
            this.ar.sendEmptyMessageDelayed(27, 8000);
        }
        this.aG = true;
        this.aH = false;
    }

    private void m327n() {
        C0539i.m643b("EardatekVersion2", "do clean call stop playing");
        m357c();
        EventHandler.getInstance().removeHandler(this.aY);
        this.f260s.eventVideoPlayerActivityCreated(false);
        this.f225J = false;
        this.ar.removeCallbacksAndMessages(null);
        unregisterReceiver(this.ag);
        if (this.br != null) {
            unregisterReceiver(this.br);
        }
        if (this.az != null) {
            this.az.m512a();
        }
        if (this.bm != null) {
            this.bm.disable();
        }
        try {
            this.ay.m594c();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        C0546l.m653a().m689c();
        if (VERSION.SDK_INT <= 22) {
            this.aJ.m739d();
        }
        finish();
        System.exit(0);
    }

    private void m331o() {
        LayoutParams layoutParams;
        this.f240Y.setImageResource(R.drawable.quitfullscreen);
        this.f220D.setVisibility(8);
        this.an.setVisibility(8);
        this.ao.setVisibility(8);
        this.ae.setVisibility(8);
        this.f232Q.setVisibility(0);
        this.aO.setVisibility(0);
        if (!(C0589a.m788a().m795c() || C0589a.m788a().m801f())) {
            this.f241Z.setVisibility(8);
        }
        if (C0589a.m788a().m803g()) {
            this.aR.setVisibility(0);
        }
        this.f238W.setBackgroundResource(R.color.bottom_bg_hor);
        if (this.aE > 0 && this.aF) {
            int height = getWindow().getDecorView().getHeight();
            LayoutParams layoutParams2 = this.f226K.getLayoutParams();
            int i = height - this.aE;
            C0539i.m643b("EardatekVersion2", "screen height:" + height);
            C0539i.m643b("EardatekVersion2", "mTitleBar width:" + i);
            layoutParams2.width = i;
            layoutParams2.height = -2;
            this.f226K.setLayoutParams(layoutParams2);
            this.f226K.invalidate();
            layoutParams = this.f238W.getLayoutParams();
            layoutParams.width = i;
            layoutParams.height = -2;
            this.f238W.setLayoutParams(layoutParams);
            this.f238W.invalidate();
        }
        this.av = 2;
        C0560v.m742a(true, this);
        m358d();
        layoutParams = this.f217A.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        this.f217A.setLayoutParams(layoutParams);
        this.f217A.invalidate();
        setSurfaceLayout(this.f255n, this.f254m, this.f257p, this.f256o, this.f258q, this.f259r);
    }

    private void m334p() {
        this.f240Y.setImageResource(R.drawable.fullscreen);
        this.f220D.setVisibility(0);
        this.an.setVisibility(0);
        this.ao.setVisibility(0);
        this.f241Z.setVisibility(0);
        if (this.ae.getVisibility() == 8) {
            m266a(this.ae);
        }
        this.f235T.setVisibility(8);
        this.f235T.setImageResource(R.drawable.unlock);
        this.f235T.setTag("unlock");
        this.aO.setVisibility(4);
        if (C0589a.m788a().m795c() || C0589a.m788a().m801f()) {
            this.f238W.setBackgroundResource(R.color.bottom_bg_hor);
        } else {
            this.f238W.setBackgroundResource(R.color.bottom_bg_ver);
        }
        if (C0589a.m788a().m803g()) {
            this.aR.setVisibility(4);
        }
        this.f232Q.setVisibility(8);
        this.f239X.setVisibility(8);
        this.av = 1;
        if (this.am) {
            this.am = false;
        }
        if (this.aE > 0 && this.aF) {
            LayoutParams layoutParams = this.f226K.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            this.f226K.setLayoutParams(layoutParams);
            this.f226K.invalidate();
            layoutParams = this.f238W.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            this.f238W.setLayoutParams(layoutParams);
            this.f238W.invalidate();
        }
        this.aB = false;
        C0560v.m742a(false, this);
        setSurfaceLayout(this.f255n, this.f254m, this.f257p, this.f256o, this.f258q, this.f259r);
    }

    private void m336q() {
        this.ae = (FloatingActionButton) findViewById(R.id.fab);
        this.ae.setAlpha(0.6f);
        this.ae.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ EardatekVersion2Activity f197a;
            private long f198b = 0;

            {
                this.f197a = r3;
            }

            public void onClick(View view) {
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                if (timeInMillis - this.f198b > 1000) {
                    this.f198b = timeInMillis;
                    this.f197a.m347w();
                }
            }
        });
    }

    private void m338r() {
        this.f226K = (FrameLayout) findViewById(R.id.include);
        this.f227L = (TextView) findViewById(R.id.textTitle);
        this.f231P = (TextView) findViewById(R.id.aspect_ratio);
        this.f230O = (ImageView) findViewById(R.id.tv_signal_strength);
        this.f232Q = (TextView) findViewById(R.id.tv_cur_time);
        this.f233R = (TextView) findViewById(R.id.tv_battery_level);
        this.f234S = (ImageView) findViewById(R.id.play);
        this.aa = (FrameLayout) findViewById(R.id.volumeorbright_layout);
        this.ab = (TextView) findViewById(R.id.text_percent);
        this.ac = (ImageView) findViewById(R.id.operation_bg);
        this.ad = (TextView) findViewById(R.id.program_name);
        this.f235T = (ImageView) findViewById(R.id.lock_unlock);
        this.f228M = (FrameLayout) findViewById(R.id.no_signal_layout);
        this.f229N = (FrameLayout) findViewById(R.id.program_name_layout);
        this.f236U = (TextView) findViewById(R.id.tips_handle);
        this.f237V = (TextView) findViewById(R.id.no_signal_tips);
        this.f229N.setAlpha(0.0f);
        this.f226K.setVisibility(4);
        this.aa.setAlpha(0.6f);
        this.f251j = (AudioManager) getSystemService("audio");
        this.f246e = this.f251j.getStreamMaxVolume(3);
        this.f247f = this.f251j.getStreamVolume(3);
        this.ab.setText(String.format(Locale.ENGLISH, "%d%%", new Object[]{Integer.valueOf((this.f247f / this.f246e) * 100)}));
        this.f250i = new GestureDetector(this, new C0468d());
        this.f238W = (RelativeLayout) findViewById(R.id.listlayout);
        this.f238W.setAlpha(0.7f);
        this.f238W.setVisibility(4);
        if (C0589a.m788a().m795c() || C0589a.m788a().m801f()) {
            this.f238W.setBackgroundResource(R.color.bottom_bg_hor);
        } else {
            this.f238W.setBackgroundResource(R.color.bottom_bg_ver);
        }
        this.f239X = (RecyclerView) findViewById(R.id.minilistView);
        this.f240Y = (ImageView) findViewById(R.id.fullscreen);
        this.f241Z = (TextView) findViewById(R.id.video_type);
        this.f239X.setVisibility(8);
        this.f226K.setAlpha(0.8f);
        this.f219C = new C0422a(this, new C04562(this));
        LayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(this);
        fullyLinearLayoutManager.setOrientation(1);
        this.f239X.setLayoutManager(fullyLinearLayoutManager);
        this.f239X.addItemDecoration(new C0561a(this, 1));
        this.f240Y.setOnClickListener(new C04573(this));
        this.f232Q.setVisibility(8);
        this.f231P.setOnClickListener(new C04584(this));
        this.f230O.setOnClickListener(new C04595(this));
        this.f235T.setOnClickListener(new C04606(this));
        this.f234S.setClickable(true);
        this.f234S.setOnClickListener(new C04617(this));
    }

    private void m340s() {
        Canvas lockCanvas = this.f261t.getHolder().lockCanvas();
        if (lockCanvas != null) {
            lockCanvas.drawColor(0, Mode.CLEAR);
            this.f261t.getHolder().unlockCanvasAndPost(lockCanvas);
        }
    }

    private void m342t() {
        this.aU = (LinearLayout) findViewById(R.id.video_layout);
        this.f217A = (FrameLayout) findViewById(R.id.player_surface_frame_layout);
        this.f217A.setOnTouchListener(new C04628(this));
        this.f261t = (SurfaceView) findViewById(R.id.player_surface);
        this.f263v = this.f261t.getHolder();
        this.f267z = (FrameLayout) findViewById(R.id.player_surface_frame);
        this.f262u = (SurfaceView) findViewById(R.id.subtitles_surface);
        this.f264w = this.f262u.getHolder();
        this.f262u.setZOrderMediaOverlay(true);
        this.f264w.setFormat(-3);
        this.f262u.setVisibility(8);
    }

    private void m343u() {
        this.f220D = (Toolbar) findViewById(R.id.toolbar);
        this.f220D.setTitle((int) R.string.app_label);
        this.f220D.setTitleTextColor(Color.parseColor("#FFFFFF"));
        setSupportActionBar(this.f220D);
        this.f220D.setOnMenuItemClickListener(this.ba);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    private void m346v() {
        this.f234S.setImageResource(R.drawable.play);
        this.f234S.setVisibility(0);
        this.f234S.setTag("play");
        this.f224H = false;
        this.f252k = false;
        this.f253l = true;
        this.f260s.stop();
        C0539i.m643b("EardatekVersion2", "stopPlayAction");
        if (this.as != null && this.as.isAlive()) {
            this.as.interrupt();
        }
        m218A();
        m340s();
        this.aB = false;
    }

    private void m347w() {
        if (this.bs == null || !this.bs.isShowing()) {
            View inflate = getLayoutInflater().inflate(R.layout.search_layout, null);
            this.bs = new Dialog(this, R.style.transparentFrameWindowStyle);
            Button button = (Button) inflate.findViewById(R.id.search_all_btn);
            ImageButton imageButton = (ImageButton) inflate.findViewById(R.id.advance_search_btn);
            this.af = (CustomEditText) inflate.findViewById(R.id.freq_edit);
            button.setOnClickListener(new C04639(this));
            imageButton.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ EardatekVersion2Activity f141a;

                {
                    this.f141a = r1;
                }

                public void onClick(View view) {
                    CharSequence obj = this.f141a.af.getText().toString();
                    boolean a = C0551p.m715a(obj);
                    if (TextUtils.isEmpty(obj) || !a) {
                        Toast.makeText(this.f141a.getApplicationContext(), R.string.freqtips, 0).show();
                        return;
                    }
                    float parseFloat = Float.parseFloat(this.f141a.af.getText().toString());
                    if (C0530b.m608a(C0546l.m653a().m692f(), parseFloat)) {
                        if (this.f141a.bs.isShowing()) {
                            this.f141a.bs.dismiss();
                        }
                        if (this.f141a.f252k) {
                            this.f141a.m346v();
                            C0539i.m643b("EardatekVersion2", "mAdvanceSearch set replay");
                        }
                        Intent intent = new Intent(DTVApplication.m750a(), ScanChannelActivity.class);
                        intent.putExtra("advance_search", parseFloat * 1000.0f);
                        this.f141a.startActivityForResult(intent, 2);
                        this.f141a.overridePendingTransition(R.anim.photo_dialog_in_anim, R.anim.photo_dialog_out_anim);
                    }
                }
            });
            this.bs.setContentView(inflate, new LayoutParams(-1, -2));
            Window window = this.bs.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams attributes = window.getAttributes();
            getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
            attributes.x = 0;
            attributes.y = 0;
            attributes.width = C0533d.m627a(getApplicationContext(), 345.0f);
            attributes.height = -2;
            attributes.alpha = 0.9f;
            this.bs.onWindowAttributesChanged(attributes);
            this.bs.setCanceledOnTouchOutside(true);
            this.bs.show();
        }
    }

    private void m350x() {
        this.aq = LayoutInflater.from(this);
        C0501b c0501b = new C0501b(R.string.program, C0520b.class);
        C0520b.m564a(new C0447a(this) {
            final /* synthetic */ EardatekVersion2Activity f142a;

            {
                this.f142a = r1;
            }

            public void mo2086a() {
                this.f142a.ae.hide();
            }

            public void mo2087b() {
                this.f142a.ae.show();
            }
        });
        this.ap.add(c0501b);
        this.an = (FragmentTabHost) findViewById(16908306);
        this.an.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (C0501b c0501b2 : this.ap) {
            TabSpec newTabSpec = this.an.newTabSpec(getString(c0501b2.m502a()));
            newTabSpec.setIndicator(m259a(c0501b2));
            this.an.addTab(newTabSpec, c0501b2.m503b(), null);
        }
        this.an.getTabWidget().setShowDividers(0);
        this.an.setCurrentTab(0);
        this.an.setOnTabChangedListener(this);
    }

    private void m351y() {
        this.ao = (ViewPager) findViewById(R.id.pager);
        this.ao.setAdapter(new C0441e(getSupportFragmentManager(), this.ap));
        this.ao.addOnPageChangeListener(this);
    }

    private void m353z() {
        if (this.au == null || !this.au.isAlive()) {
            this.au = new Thread(new C0464a(this));
            this.au.start();
        }
    }

    public void m355a(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split("-");
            if (split.length == 7) {
                C0546l a = C0546l.m653a();
                this.ai = Integer.parseInt(split[0].substring(4));
                int parseInt = Integer.parseInt(split[1].substring(2));
                int parseInt2 = Integer.parseInt(split[2].substring(3));
                int parseInt3 = Integer.parseInt(split[3].substring(4));
                this.f223G = parseInt3;
                Integer.parseInt(split[4].substring(7));
                C0500a c0500a = null;
                C0521a c0521a = new C0521a(DTVApplication.m750a());
                try {
                    c0500a = c0521a.m588a(str);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (c0500a == null) {
                    C0539i.m643b("EardatekVersion2", "invalis media");
                    this.f224H = false;
                    return;
                }
                this.f222F = c0500a.m489a().trim();
                String d = c0500a.m496d();
                Message obtainMessage = this.ar.obtainMessage(5);
                obtainMessage.obj = d;
                this.ar.sendMessage(obtainMessage);
                try {
                    c0521a.m594c();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                f216I = true;
                this.f224H = a.m683a(this.ai, parseInt, parseInt2, parseInt3);
                C0539i.m643b("EardatekVersion2", "is lock:" + this.f224H);
                if (this.f224H && this.f252k) {
                    d = a.m701o();
                    r3 = new String[4];
                    r3[2] = String.format(Locale.ENGLISH, ":program=%d", new Object[]{Integer.valueOf(parseInt3)});
                    r3[3] = "";
                    this.f260s.playMRL(d, r3);
                    this.f260s.setProgram(parseInt3);
                    C0539i.m643b("EardatekVersion2", "program:" + this.f260s.getProgram());
                    return;
                }
                this.f224H = false;
                if (this.f260s.isPlaying()) {
                    C0539i.m643b("EardatekVersion2", "lock channel call stop");
                    this.f260s.stop();
                }
            }
        }
    }

    public void m356a(String str, int i) {
        if (!(str.equals(this.f221E) && this.f252k) && C0546l.m653a().m700n()) {
            this.f221E = str;
            this.al = i;
            this.aj = false;
            this.f243b = true;
            this.f242a = false;
            C0539i.m643b("EardatekVersion2", "mPlaying = " + this.f252k);
            if ((this.f252k || this.as != null) && this.as.isAlive()) {
                this.f260s.stop();
                m340s();
                this.f224H = false;
                this.f253l = true;
                C0539i.m643b("EardatekVersion2", "onItemClick set replay");
                this.as.interrupt();
                m218A();
                return;
            }
            m282b(this.f221E);
            if (C0589a.m788a().m804h()) {
                m316j();
            }
        }
    }

    public void m357c() {
        if (this.f252k) {
            this.f252k = false;
            this.f253l = true;
            C0539i.m643b("EardatekVersion2", "stopplaying set replay");
            if (this.as != null && this.as.isAlive()) {
                this.as.interrupt();
            }
            this.f224H = false;
            C0546l.m653a().m691e();
        }
    }

    public int configureSurface(Surface surface, int i, int i2, int i3) {
        return -1;
    }

    public void m358d() {
        this.f219C.m69a();
        this.f219C.m72b();
        this.f219C.m71a(this.f221E);
        this.f239X.setAdapter(this.f219C);
        this.f239X.scrollToPosition(this.f219C.m74d());
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!f216I) {
            return super.dispatchTouchEvent(motionEvent);
        }
        C0567b.m774a(getApplicationContext(), getResources().getString(R.string.switching), 1000);
        return true;
    }

    public void m359e() {
        float f = 0.0f;
        try {
            f = ((float) System.getInt(getContentResolver(), "screen_brightness")) / 255.0f;
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = f;
        getWindow().setAttributes(attributes);
        this.aw = false;
    }

    public void eventHardwareAccelerationError() {
        EventHandler.getInstance().callback(EventHandler.HardwareAccelerationError, new Bundle());
    }

    public C0508a m360f() {
        return this.aA;
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
        C0539i.m643b("EardatekVersion2", "onAccuracyChanged");
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2) {
            this.f219C.m69a();
            this.f219C.m72b();
            this.f239X.setAdapter(this.f219C);
            if (C0589a.m788a().m804h()) {
                C0595a.m827c();
                this.aW.m186e();
            }
            C0536g.m632b("Channel.txt");
            this.aA = new C0510b();
            for (Fragment onActivityResult : getSupportFragmentManager().getFragments()) {
                onActivityResult.onActivityResult(i, i2, intent);
            }
        }
        if (i == 1) {
            String b = C0548m.m704b(getApplicationContext());
            C0539i.m643b("EardatekVersion2", "wifi name:" + b);
            if (C0546l.m663c(b)) {
                if (!b.equals(this.aK)) {
                    C0546l.m653a().m690d();
                    this.f253l = true;
                    C0546l.m653a().m682a(false);
                    this.aK = b;
                }
                if (this.bs != null) {
                    if (this.bs.isShowing()) {
                        this.bs.dismiss();
                    }
                    this.bs = null;
                }
            }
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        m133b();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        C0539i.m643b("EardatekVersion2", "oncreate");
        getWindow().setFlags(128, 128);
        setContentView((int) R.layout.activity_eardatek_version2_main);
        this.aK = C0548m.m704b(DTVApplication.m750a());
        DTVApplication.m751a(this.aK);
        this.aE = C0560v.m741a((Context) this);
        this.aF = C0560v.m743b(this);
        this.aA = new C0510b();
        m343u();
        m342t();
        m338r();
        m350x();
        m351y();
        m336q();
        m322l();
        C0732c.m1501a().m1510a((Object) this);
        m235I();
        m231G();
        m237J();
        this.az = new C0505b(this);
        this.az.m513a(new C04511(this));
        this.f234S.setVisibility(0);
        this.ay = new C0521a(this);
        try {
            this.f260s = C0563b.m756a();
        } catch (LibDtvException e) {
            e.printStackTrace();
            C0539i.m642a("EardatekVersion2", "LibDTV initialisation failed");
        }
        EventHandler.getInstance().addHandler(this.aY);
        this.f263v.addCallback(this.bb);
        this.f264w.addCallback(this.bc);
        C0546l.m653a().m686a(C0506a.f356a, C0506a.f357b, C0506a.f359d, C0506a.f358c, new C0469e(this), this);
        if (C0546l.m653a().m700n()) {
            m310h();
        }
        this.aJ = new C0559u(this);
        this.aV = m222C();
        if (C0589a.m788a().m795c()) {
            new Thread(new C0465b(this)).start();
        }
        if (C0589a.m788a().m803g()) {
            m319k();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (C0589a.m788a().m804h()) {
            if (C0546l.m653a().m692f().equals("ISDBT")) {
                getMenuInflater().inflate(R.menu.menu_three_epg, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_eardatek_epg, menu);
            }
        } else if (C0546l.m653a().m692f().equals("ISDBT")) {
            getMenuInflater().inflate(R.menu.menu_three, menu);
        } else {
            getMenuInflater().inflate(R.menu.menu_eardatek, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    protected void onDestroy() {
        super.onDestroy();
        m263a(this.aV);
        C0732c.m1501a().m1514b(this);
        m327n();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 || keyEvent.getAction() != 0) {
            return super.onKeyDown(i, keyEvent);
        }
        if (getResources().getConfiguration().orientation == 2) {
            setRequestedOrientation(1);
            C0539i.m643b("EardatekVersion2", "changeToPortRait");
            m334p();
            this.bn = false;
            this.bo = true;
            return true;
        }
        m233H();
        return true;
    }

    @C0739j(a = ThreadMode.MAIN)
    public void onMainEventBus(C0502a c0502a) {
        switch (c0502a.m505a()) {
            case 7:
                m347w();
                return;
            case 8:
                m346v();
                return;
            case 9:
                this.al = ((Integer) c0502a.m508b()).intValue();
                return;
            default:
                return;
        }
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
    }

    public void onPageSelected(int i) {
        this.an.setCurrentTab(this.ao.getCurrentItem());
    }

    protected void onRestart() {
        super.onRestart();
        this.ar.sendEmptyMessage(28);
    }

    protected void onResume() {
        super.onResume();
        if (!this.aL && C0546l.m653a().m692f().equals("ISDBT")) {
            this.aL = true;
            m278b((int) R.id.radio_isdbt);
        }
        if (!this.aM && C0546l.m653a().m692f().equals("DVB-T/T2")) {
            this.aM = true;
            m288c((int) R.id.radio_aus);
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        C0539i.m643b("EardatekVersion2", "onSensorChanged");
    }

    @TargetApi(17)
    protected void onStart() {
        super.onStart();
        if (this.f218B == null) {
            this.f218B = new OnLayoutChangeListener(this) {
                final /* synthetic */ EardatekVersion2Activity f196a;

                {
                    this.f196a = r1;
                }

                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (i != i5 || i2 != i6 || i3 != i7 || i4 != i8) {
                        this.f196a.m227E();
                        this.f196a.setSurfaceLayout(this.f196a.f255n, this.f196a.f254m, this.f196a.f257p, this.f196a.f256o, this.f196a.f258q, this.f196a.f259r);
                    }
                }
            };
        }
        this.f267z.addOnLayoutChangeListener(this.f218B);
        setSurfaceLayout(this.f255n, this.f254m, this.f257p, this.f256o, this.f258q, this.f259r);
    }

    protected void onStop() {
        super.onStop();
        if (C0589a.m788a().m804h()) {
            this.aW.m185d();
        }
        if (this.f260s.isPlaying() && this.f252k) {
            m346v();
            setRequestedOrientation(1);
            m334p();
            this.aD = true;
            this.bn = false;
            this.bq = false;
        }
        if (C0560v.m744c(this)) {
            C0539i.m643b("EardatekVersion2", "still foreground");
            return;
        }
        this.aH = true;
        if (VERSION.SDK_INT <= 22) {
            this.aJ.m739d();
        }
        C0546l.m653a().m689c();
        if (this.br != null) {
            unregisterReceiver(this.br);
            this.br = null;
        }
        this.ar.removeMessages(27);
        if (this.bt != null) {
            if (this.bt.isShowing()) {
                this.bt.dismiss();
            }
            this.bt = null;
        }
    }

    public void onTabChanged(String str) {
        this.ao.setCurrentItem(this.an.getCurrentTab());
    }

    public void setSurfaceLayout(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i * i2 != 0) {
            this.f254m = i2;
            this.f255n = i;
            this.f256o = i4;
            this.f257p = i3;
            this.f258q = i5;
            this.f259r = i6;
            Log.e("TTTTT", String.format(Locale.ENGLISH, "width:%d, height:%d\nvisible_width:%d, visible_height:%d\nsar_num:%d, sar_den:%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)}));
            this.ar.removeMessages(22);
            this.ar.sendMessage(this.ar.obtainMessage(3));
            if (this.f224H && this.f252k && !f216I) {
                this.ar.sendEmptyMessage(7);
            }
        }
    }
}
