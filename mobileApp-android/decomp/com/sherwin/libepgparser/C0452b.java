package com.sherwin.libepgparser;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.sherwin.libepgparser.p014b.C0600a;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

public abstract class C0452b {
    private static final String f155a = C0452b.class.getSimpleName();
    private boolean f156b = true;
    private boolean f157c = false;
    private int f158d = 5000;
    private int f159e = 20000;
    private String f160f;
    private OutputStream f161g;
    private int f162h;
    private C0454b f163i;
    private Handler f164j = new C05961(this);
    private Runnable f165k = new C05972(this);

    public interface C0454b {
        void mo2095a();

        void mo2096b();
    }

    class C05961 extends Handler {
        final /* synthetic */ C0452b f647a;

        C05961(C0452b c0452b) {
            this.f647a = c0452b;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    if (this.f647a.mo2092a() && this.f647a.f162h == 1 && !this.f647a.f157c) {
                        this.f647a.f164j.removeMessages(1);
                        this.f647a.f164j.removeMessages(2);
                        this.f647a.f164j.sendEmptyMessage(1);
                    }
                    if (this.f647a.f156b) {
                        sendEmptyMessageDelayed(0, (long) this.f647a.f159e);
                        return;
                    }
                    return;
                case 1:
                    this.f647a.m172f();
                    if (this.f647a.f162h == 1) {
                        this.f647a.f162h = 2;
                        this.f647a.f164j.sendEmptyMessageDelayed(2, (long) this.f647a.f158d);
                        return;
                    }
                    return;
                case 2:
                    this.f647a.f162h = 3;
                    if (this.f647a.f161g != null) {
                        try {
                            this.f647a.f161g.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            this.f647a.f161g.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        this.f647a.f161g = null;
                        break;
                    }
                    break;
                case 3:
                    break;
                case 4:
                    this.f647a.f162h = 1;
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
            if (!this.f647a.mo2092a() || this.f647a.f157c) {
                sendEmptyMessage(4);
            } else {
                new Thread(this.f647a.f165k).start();
            }
        }
    }

    class C05972 implements Runnable {
        final /* synthetic */ C0452b f648a;

        C05972(C0452b c0452b) {
            this.f648a = c0452b;
        }

        public void run() {
            Exception exception;
            Object obj = null;
            String str = this.f648a.f160f + "/ts.dat";
            List<C0598a> b = this.f648a.mo2093b();
            Object obj2;
            if (b != null) {
                for (C0598a c0598a : b) {
                    if (EpgParser.startParseCurrentList(str, c0598a.m829b()) > 0) {
                        C0600a b2 = EpgParser.m808b(0);
                        if (b2 != null) {
                            C0595a.m822a(c0598a.m828a(), c0598a.m829b(), b2.m841e().m831b("%s ").m830a("HH:mm:ss-").m832c("HH:mm:ss").toString().trim());
                            obj = 1;
                        }
                    }
                    int startParseAllList = EpgParser.startParseAllList(str, c0598a.m829b());
                    List a = EpgParser.m807a(startParseAllList, startParseAllList);
                    if (b.size() > 0) {
                        try {
                            Log.e("JNIMsg", "getAllEpgInfoList:" + String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(c0598a.m828a()), Integer.valueOf(c0598a.m829b())}));
                            C0595a.m823a(c0598a.m828a(), c0598a.m829b(), a);
                            obj2 = 1;
                        } catch (Exception e) {
                            Exception exception2 = e;
                            int i = 1;
                            exception = exception2;
                        }
                    } else {
                        try {
                            Log.e("JNIMsg", "getAllEpgInfoList:" + String.format(Locale.ENGLISH, "%d-%d", new Object[]{Integer.valueOf(c0598a.m828a()), Integer.valueOf(c0598a.m829b())}) + "null");
                            obj2 = obj;
                        } catch (Exception e2) {
                            exception = e2;
                            obj2 = obj;
                        }
                    }
                    obj = obj2;
                }
                obj2 = obj;
                if (this.f648a.f163i == null) {
                }
                if (obj2 == null) {
                    this.f648a.f163i.mo2095a();
                } else {
                    this.f648a.f163i.mo2096b();
                }
            }
            return;
            exception.printStackTrace();
            if (this.f648a.f163i == null) {
                if (obj2 == null) {
                    this.f648a.f163i.mo2096b();
                } else {
                    this.f648a.f163i.mo2095a();
                }
            }
        }
    }

    public static class C0598a {
        private int f649a;
        private int f650b;
        private String f651c;

        public C0598a(int i, int i2, String str) {
            this.f649a = i;
            this.f650b = i2;
            this.f651c = str;
        }

        public int m828a() {
            return this.f649a;
        }

        public int m829b() {
            return this.f650b;
        }
    }

    private void m172f() {
        File file = new File(this.f160f + "/ts.dat");
        if (file.exists() && !file.delete()) {
            Log.w(f155a, file.getAbsolutePath() + " delete fail");
        }
        try {
            if (file.createNewFile()) {
                this.f161g = new FileOutputStream(file);
                this.f162h = 1;
                return;
            }
            throw new Exception();
        } catch (Exception e) {
            this.f162h = 0;
            e.printStackTrace();
        }
    }

    public C0452b m179a(C0454b c0454b) {
        this.f163i = c0454b;
        return this;
    }

    public C0452b m180a(String str) {
        this.f160f = str;
        File file = new File(str);
        if (file.exists() || !file.mkdirs()) {
            this.f162h = 1;
            return this;
        }
        this.f162h = 0;
        throw new Exception("Create dir: " + str + "fail");
    }

    public void m181a(byte[] bArr, int i) {
        if (this.f162h == 2) {
            try {
                if (this.f161g != null) {
                    this.f161g.write(bArr, 0, i);
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.f164j.sendEmptyMessage(2);
            }
        }
    }

    public abstract boolean mo2092a();

    public abstract List<C0598a> mo2093b();

    public void m184c() {
        if (this.f160f == null) {
            this.f162h = 0;
            throw new Exception("savePath cannot be null");
        } else if (this.f162h == 0) {
            throw new Exception("The setup have not been completed yet");
        } else {
            this.f162h = 1;
            this.f164j.sendEmptyMessageDelayed(0, (long) this.f159e);
        }
    }

    public void m185d() {
        this.f157c = true;
    }

    public void m186e() {
        this.f157c = false;
    }
}
