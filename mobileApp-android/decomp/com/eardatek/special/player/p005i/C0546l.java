package com.eardatek.special.player.p005i;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import com.anthony.ultimateswipetool.cards.SwipeCards;
import com.eardatek.special.player.p008d.C0506a;
import com.eardatek.special.player.system.DTVApplication;
import com.sherwin.eardatek.labswitch.C0589a;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Locale;

public final class C0546l {
    private static final String f445a = C0546l.class.getSimpleName();
    private static Socket f446h = null;
    private static volatile boolean f447k;
    private static C0546l f448m = null;
    private static C0545b f449r;
    private String f450b;
    private int f451c;
    private String f452d;
    private int f453e;
    private boolean f454f = true;
    private boolean f455g = false;
    private String f456i = "DVB-T";
    private int f457j = 1;
    private volatile boolean f458l;
    private int f459n = 0;
    private int f460o = 0;
    private int f461p = 0;
    private double f462q = 0.0d;
    private C0553q f463s;
    private boolean f464t = false;
    private String f465u = "";
    private Handler f466v;
    private boolean f467w = false;
    private int f468x = 0;
    private C0482c f469y;

    public interface C0482c {
        void mo2108a(boolean z);
    }

    private final class C0544a extends Thread {
        final /* synthetic */ C0546l f442a;

        private C0544a(C0546l c0546l) {
            this.f442a = c0546l;
        }

        private void m650a() {
            boolean d = this.f442a.m676t();
            if (this.f442a.f469y != null) {
                this.f442a.f469y.mo2108a(d);
            }
            if (d) {
                this.f442a.f455g = true;
                this.f442a.f454f = false;
                return;
            }
            this.f442a.f468x = 2;
            if (C0546l.f446h != null) {
                try {
                    C0546l.f446h.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            C0546l.f446h = null;
            this.f442a.f454f = false;
            this.f442a.f455g = false;
        }

        public void run() {
            if (C0546l.f446h != null && C0546l.f446h.isConnected()) {
                m650a();
            } else if (this.f442a.m675s()) {
                m650a();
            } else {
                this.f442a.f468x = 1;
                this.f442a.f454f = false;
                this.f442a.f455g = false;
            }
        }
    }

    private final class C0545b extends Thread {
        final /* synthetic */ C0546l f443a;
        private WeakReference<Activity> f444b;

        C0545b(C0546l c0546l, Activity activity) {
            this.f443a = c0546l;
            this.f444b = new WeakReference(activity);
        }

        private void m651a(int i) {
            try {
                Thread.sleep((long) i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (true) {
                if (C0546l.f447k && C0546l.f449r.isInterrupted()) {
                    break;
                } else if (((Activity) this.f444b.get()) != null) {
                    try {
                        if (C0546l.f446h == null || !C0546l.f446h.isConnected()) {
                            if (this.f443a.f454f) {
                                this.f443a.f466v.sendEmptyMessage(2);
                            }
                            this.f443a.f466v.sendEmptyMessage(4);
                            if (!this.f443a.m675s()) {
                                if (this.f443a.f454f) {
                                    this.f443a.f466v.sendEmptyMessage(1);
                                }
                                this.f443a.f454f = false;
                                this.f443a.f455g = false;
                            } else if (this.f443a.m676t()) {
                                this.f443a.f466v.sendEmptyMessage(0);
                                this.f443a.f455g = true;
                                this.f443a.f454f = false;
                            } else {
                                if (C0546l.f446h != null) {
                                    C0546l.f446h.close();
                                }
                                C0546l.f446h = null;
                                if (this.f443a.f454f) {
                                    this.f443a.f466v.sendEmptyMessage(1);
                                }
                                this.f443a.f454f = false;
                                this.f443a.f455g = false;
                                try {
                                    m651a(SwipeCards.DEFAULT_ANIMATION_DURATION);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (Float.parseFloat(this.f443a.m677u()) > 2.3f || C0546l.m653a().m692f().equals("ISDBT")) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                        } else {
                            Socket socket = new Socket();
                            socket.connect(new InetSocketAddress(this.f443a.f450b, this.f443a.f451c), 3000);
                            socket.setSoTimeout(5000);
                            InputStream inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream();
                            byte[] bArr = new byte[1024];
                            while (!C0546l.f447k && !C0546l.f449r.isInterrupted() && socket.isConnected() && C0546l.f446h != null && C0546l.f446h.isConnected()) {
                                try {
                                    int read = inputStream.read(bArr);
                                    if (read < 0) {
                                        C0546l.f446h.close();
                                        C0546l.f446h = null;
                                        this.f443a.f466v.sendEmptyMessage(4);
                                        read = 0;
                                    }
                                    if (new String(bArr, 0, read).contains("send data fail")) {
                                        this.f443a.f466v.sendEmptyMessage(3);
                                    }
                                    outputStream.write(bArr);
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                } catch (SocketException e4) {
                                    e4.printStackTrace();
                                    if (C0546l.f446h != null) {
                                    }
                                    this.f443a.f455g = false;
                                    this.f443a.f466v.sendEmptyMessage(4);
                                    try {
                                        if (C0546l.f446h != null) {
                                            C0546l.f446h.close();
                                            C0546l.f446h = null;
                                        }
                                    } catch (IOException e5) {
                                        e4.printStackTrace();
                                    }
                                }
                            }
                            socket.close();
                            this.f443a.f455g = false;
                        }
                    } catch (SocketException e42) {
                        e42.printStackTrace();
                        if (C0546l.f446h != null) {
                        }
                        this.f443a.f455g = false;
                        this.f443a.f466v.sendEmptyMessage(4);
                        try {
                            if (C0546l.f446h != null) {
                                C0546l.f446h.close();
                                C0546l.f446h = null;
                            }
                        } catch (IOException e52) {
                            e42.printStackTrace();
                        }
                    } catch (IOException e32) {
                        e32.printStackTrace();
                        C0539i.m643b("EardatekVersion2", "lose connect to server!");
                        if (!C0546l.f446h.isConnected() || C0546l.f446h == null) {
                            this.f443a.f455g = false;
                            try {
                                if (C0546l.f446h != null) {
                                    C0546l.f446h.close();
                                    C0546l.f446h = null;
                                }
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                    }
                } else {
                    return;
                }
            }
            if (C0546l.f446h != null) {
                this.f443a.m678v();
            }
            try {
                if (C0546l.f446h != null) {
                    C0546l.f446h.close();
                }
                C0546l.f446h = null;
            } catch (IOException e3222) {
                e3222.printStackTrace();
            }
        }
    }

    public static C0546l m653a() {
        if (f448m == null) {
            f448m = new C0546l();
        }
        return f448m;
    }

    private String m654a(String str, String str2, String str3, String str4) {
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String m655a(java.lang.String r8, boolean r9) {
        /*
        r7 = this;
        r2 = 0;
        monitor-enter(r7);
        r1 = "";
        r0 = 100;
        r3 = 0;
        r7.f458l = r3;	 Catch:{ all -> 0x0095 }
        if (r9 != 0) goto L_0x00cf;
    L_0x000b:
        r0 = 30;
        r4 = r0;
    L_0x000e:
        r0 = f446h;	 Catch:{ all -> 0x0095 }
        if (r0 != 0) goto L_0x001d;
    L_0x0012:
        r0 = "HomePageActivity";
        r1 = "mSocket is null";
        com.eardatek.special.player.p005i.C0539i.m643b(r0, r1);	 Catch:{ all -> 0x0095 }
        r0 = "";
    L_0x001b:
        monitor-exit(r7);
        return r0;
    L_0x001d:
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r5 = new byte[r0];	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r0 = f446h;	 Catch:{ SocketTimeoutException -> 0x007e }
        r0 = r0.getInputStream();	 Catch:{ SocketTimeoutException -> 0x007e }
        r0.read(r5);	 Catch:{ SocketTimeoutException -> 0x007e }
    L_0x002a:
        r0 = r8.getBytes();	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = f446h;	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = r3.getOutputStream();	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3.write(r0);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r0 = "HomePageActivity";
        com.eardatek.special.player.p005i.C0539i.m643b(r0, r8);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = r2;
    L_0x003d:
        if (r3 >= r4) goto L_0x004e;
    L_0x003f:
        r0 = r7.f458l;	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        if (r0 != 0) goto L_0x004e;
    L_0x0043:
        r0 = f446h;	 Catch:{ SocketTimeoutException -> 0x0098 }
        r0 = r0.getInputStream();	 Catch:{ SocketTimeoutException -> 0x0098 }
        r0 = r0.read(r5);	 Catch:{ SocketTimeoutException -> 0x0098 }
        r2 = r0;
    L_0x004e:
        r0 = "HomePageActivity";
        r4 = new java.lang.StringBuilder;	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r4.<init>();	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r6 = "tryTimes =";
        r4 = r4.append(r6);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = r4.append(r3);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r4 = "mAborted";
        r3 = r3.append(r4);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r4 = r7.f458l;	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = r3.append(r4);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = r3.toString();	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        com.eardatek.special.player.p005i.C0539i.m643b(r0, r3);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        if (r2 > 0) goto L_0x009d;
    L_0x0074:
        r0 = "HomePageActivity";
        r2 = "No response!";
        com.eardatek.special.player.p005i.C0539i.m643b(r0, r2);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r0 = "";
        goto L_0x001b;
    L_0x007e:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        goto L_0x002a;
    L_0x0083:
        r0 = move-exception;
        r0 = r1;
    L_0x0085:
        r1 = "HomePageActivity";
        r2 = "SocketException";
        com.eardatek.special.player.p005i.C0539i.m643b(r1, r2);	 Catch:{ all -> 0x0095 }
        r1 = f446h;	 Catch:{ Exception -> 0x00ac }
        r1.close();	 Catch:{ Exception -> 0x00ac }
    L_0x0091:
        r1 = 0;
        f446h = r1;	 Catch:{ all -> 0x0095 }
        goto L_0x001b;
    L_0x0095:
        r0 = move-exception;
        monitor-exit(r7);
        throw r0;
    L_0x0098:
        r0 = move-exception;
        r0 = r3 + 1;
        r3 = r0;
        goto L_0x003d;
    L_0x009d:
        r0 = new java.lang.String;	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r3 = 0;
        r0.<init>(r5, r3, r2);	 Catch:{ SocketException -> 0x0083, IOException -> 0x00b1, Exception -> 0x00c0 }
        r1 = "HomePageActivity";
        com.eardatek.special.player.p005i.C0539i.m643b(r1, r0);	 Catch:{ SocketException -> 0x00aa, IOException -> 0x00b1, Exception -> 0x00c0 }
        goto L_0x001b;
    L_0x00aa:
        r1 = move-exception;
        goto L_0x0085;
    L_0x00ac:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0095 }
        goto L_0x0091;
    L_0x00b1:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0095 }
        r0 = "HomePageActivity";
        r1 = "IOException";
        com.eardatek.special.player.p005i.C0539i.m643b(r0, r1);	 Catch:{ all -> 0x0095 }
        r0 = "";
        goto L_0x001b;
    L_0x00c0:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0095 }
        r0 = "HomePageActivity";
        r1 = "Exception";
        com.eardatek.special.player.p005i.C0539i.m643b(r0, r1);	 Catch:{ all -> 0x0095 }
        r0 = "";
        goto L_0x001b;
    L_0x00cf:
        r4 = r0;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.eardatek.special.player.i.l.a(java.lang.String, boolean):java.lang.String");
    }

    private void m657a(int i) {
        try {
            Thread.sleep((long) i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean m663c(String str) {
        if (C0589a.m788a().m799e()) {
            return true;
        }
        if (!TextUtils.isEmpty(str)) {
            for (String contains : C0506a.f360e) {
                if (str.contains(contains)) {
                    return true;
                }
            }
        }
        return false;
    }

    private int m664d(String str) {
        String a = m654a(str, "ret", "\"", "\"");
        return a.equals("") ? -1 : Integer.parseInt(a);
    }

    private void m667e(String str) {
        byte[] bytes = str.getBytes();
        try {
            if (f446h != null) {
                f446h.getOutputStream().write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean m675s() {
        try {
            f446h = new Socket();
            SocketAddress inetSocketAddress = new InetSocketAddress(this.f450b, this.f451c);
            if (f446h == null || f446h.isConnected() || f446h.isClosed()) {
                return false;
            }
            f446h.connect(inetSocketAddress, 3000);
            if (f446h == null || !f446h.isConnected()) {
                if (f446h != null) {
                    f446h.close();
                }
                f446h = null;
                return false;
            }
            f446h.setKeepAlive(true);
            f446h.setSoTimeout(100);
            return true;
        } catch (SocketException e) {
            f446h = null;
            return false;
        } catch (IOException e2) {
            return false;
        }
    }

    private boolean m676t() {
        String a = m655a(String.format(Locale.ENGLISH, "<msg type='login_req'><params protocol='%s' port='%d'/></msg>", new Object[]{this.f452d, Integer.valueOf(this.f453e)}).replace('\'', '\"'), false);
        if (m664d(a) != 0) {
            return false;
        }
        String a2 = m654a(a, "deviceinfo", " ", "/>");
        if (a2.isEmpty()) {
            this.f456i = "Unkown";
        } else if (a2.contains("DVB")) {
            this.f456i = "DVB-T/T2";
        } else if (a2.contains("ISDBT")) {
            this.f456i = "ISDBT";
        } else if (a2.contains("DTMB")) {
            this.f456i = "DTMB";
        } else if (a2.contains("ATSC")) {
            this.f456i = "ATSC";
        }
        a2 = m654a(a, "softwareversion", "\"", "\"");
        if (!TextUtils.isEmpty(this.f456i)) {
            C0549n.m707a(DTVApplication.m750a(), "device_type", this.f456i);
        }
        if (!TextUtils.isEmpty(a2)) {
            C0549n.m707a(DTVApplication.m750a(), "software_version", a2);
        }
        return true;
    }

    private String m677u() {
        return C0549n.m705a(DTVApplication.m750a(), "software_version");
    }

    private boolean m678v() {
        m667e("<msg type='logout_req'></msg>".replace('\'', '\"'));
        return true;
    }

    private boolean m679w() {
        if (f446h != null) {
            return true;
        }
        this.f454f = true;
        return false;
    }

    public void m680a(C0482c c0482c) {
        this.f469y = c0482c;
    }

    public void m681a(C0553q c0553q) {
        this.f463s = c0553q;
        this.f464t = true;
    }

    public void m682a(boolean z) {
        this.f467w = z;
    }

    public boolean m683a(int i, int i2, int i3, int i4) {
        String replace = String.format(Locale.ENGLISH, "<msg type='tune_req'><params tv_type='%s' freq='%d' bandwidth='%d' plp='%d' programNumber='%d'/></msg>", new Object[]{this.f456i, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)}).replace('\'', '\"');
        if (this.f452d.equalsIgnoreCase("udp")) {
            this.f465u = "udp://@:8000";
        } else if (this.f464t) {
            this.f465u = this.f463s.m727b();
        } else {
            this.f465u = String.format("tcp://%s:8000", new Object[]{this.f450b});
        }
        if (m679w()) {
            replace = m655a(replace, true);
            if (m664d(replace) != 0) {
                Log.e(f445a, "lock fail\n");
                return false;
            }
            if (this.f456i.indexOf("T2") > 0) {
                String a = m654a(replace, "plpCount", "\"", "\"");
                if (a.isEmpty()) {
                    this.f457j = 1;
                } else {
                    this.f457j = Integer.parseInt(a);
                }
            }
            return true;
        }
        m657a(100);
        return false;
    }

    public boolean m684a(String str) {
        return m664d(m655a(String.format(Locale.ENGLISH, "<msg type='Switch_Mode_req'><params mode='%s'/></msg>", new Object[]{str}).replace('\'', '\"'), false)) == 0;
    }

    public boolean m685a(String str, int i, String str2, int i2) {
        this.f450b = str;
        this.f451c = i;
        this.f452d = str2;
        this.f453e = i2;
        f447k = false;
        this.f458l = false;
        new C0544a().start();
        return this.f455g;
    }

    public boolean m686a(String str, int i, String str2, int i2, Handler handler, Activity activity) {
        this.f450b = str;
        this.f451c = i;
        this.f452d = str2;
        this.f453e = i2;
        this.f466v = handler;
        f447k = false;
        this.f458l = false;
        f449r = new C0545b(this, activity);
        f449r.start();
        return true;
    }

    public String m687b() {
        return this.f450b;
    }

    public boolean m688b(String str) {
        return m664d(m655a(String.format(Locale.ENGLISH, "<msg type='Switch_Region_req'><params mode='%s'/></msg>", new Object[]{str}).replace('\'', '\"'), false)) == 0;
    }

    public void m689c() {
        try {
            if (f446h != null) {
                f446h.close();
                f446h = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void m690d() {
        m689c();
        this.f454f = true;
        this.f467w = true;
    }

    public void m691e() {
        this.f458l = true;
    }

    public String m692f() {
        return this.f456i;
    }

    int m693g() {
        return this.f456i.indexOf("T2") > 0 ? this.f457j : 1;
    }

    public boolean m694h() {
        m667e("<msg type='stop_stream_req'></msg>".replace('\'', '\"'));
        return true;
    }

    public boolean m695i() {
        String a = m655a("<msg type='signal_status_req'></msg>".replace('\'', '\"'), false);
        if (m664d(a) != 0) {
            return false;
        }
        Object a2 = m654a(a, "strength", "\"", "\"");
        if (!TextUtils.isEmpty(a2)) {
            this.f459n = Integer.parseInt(a2);
        }
        a2 = m654a(a, "quality", "\"", "\"");
        if (!TextUtils.isEmpty(a2)) {
            this.f460o = Integer.parseInt(a2);
        }
        a2 = m654a(a, "qam", "\"", "\"");
        if (!TextUtils.isEmpty(a2)) {
            this.f461p = Integer.parseInt(a2);
        }
        return true;
    }

    public int m696j() {
        return this.f459n;
    }

    public int m697k() {
        return this.f460o;
    }

    public String m698l() {
        switch (this.f461p) {
            case 0:
                return EnvironmentCompat.MEDIA_UNKNOWN;
            case 1:
                return "4QAM_NR";
            case 2:
                return "4QAM";
            case 3:
                return "16QAM";
            case 4:
                return "32QAM";
            case 5:
                return "64QAM";
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    public int m699m() {
        return m664d(m655a("<msg type='Check_Lock_req'></msg>".replace('\'', '\"'), false));
    }

    public boolean m700n() {
        if (!(this.f455g && f446h != null && f446h.isConnected())) {
            this.f454f = true;
        }
        return this.f455g;
    }

    public String m701o() {
        return this.f465u;
    }
}
