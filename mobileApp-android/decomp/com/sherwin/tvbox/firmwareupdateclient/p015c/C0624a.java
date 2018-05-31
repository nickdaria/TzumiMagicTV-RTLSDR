package com.sherwin.tvbox.firmwareupdateclient.p015c;

import android.content.Context;
import com.sherwin.tvbox.firmwareupdateclient.C0622b;
import com.sherwin.tvbox.firmwareupdateclient.p017a.C0617a;
import java.io.File;

public class C0624a {
    private static final String f710a = C0624a.class.getSimpleName();
    private Context f711b;
    private C0611a f712c;
    private C0617a f713d;
    private boolean f714e = false;
    private int f715f = 10000;
    private int f716g = 10000;
    private Runnable f717h = new C06231(this);

    public interface C0611a {
        void mo2211a();

        void mo2212a(long j, long j2);

        void mo2213a(String str);

        void mo2214b();

        void mo2215b(String str);
    }

    class C06231 implements Runnable {
        final /* synthetic */ C0624a f709a;

        C06231(C0624a c0624a) {
            this.f709a = c0624a;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r10 = this;
            r0 = new java.lang.StringBuilder;
            r0.<init>();
            r1 = com.sherwin.tvbox.firmwareupdateclient.C0622b.f706a;
            r0 = r0.append(r1);
            r1 = "/";
            r0 = r0.append(r1);
            r1 = r10.f709a;
            r1 = r1.f713d;
            r1 = r1.m880d();
            r0 = r0.append(r1);
            r1 = r0.toString();
            r0 = new java.io.File;
            r0.<init>(r1);
            r2 = r0.exists();
            if (r2 == 0) goto L_0x0031;
        L_0x002e:
            r0.delete();
        L_0x0031:
            r0.createNewFile();	 Catch:{ IOException -> 0x007a }
            r4 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x007a }
            r4.<init>(r0);	 Catch:{ IOException -> 0x007a }
            r0 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r10.f709a;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r2.f713d;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r2.m878c();	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r0.<init>(r2);	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r0 = r0.openConnection();	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r10.f709a;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r2.f715f;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r0.setConnectTimeout(r2);	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r10.f709a;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = r2.f716g;	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r0.setReadTimeout(r2);	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r5 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x009c, IOException -> 0x00be }
            r2 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
            r6 = new byte[r2];
            r2 = r10.f709a;
            r2 = r2.f714e;
            if (r2 == 0) goto L_0x00e0;
        L_0x0070:
            r0 = r10.f709a;
            r0 = r0.f712c;
            r0.mo2214b();
        L_0x0079:
            return;
        L_0x007a:
            r0 = move-exception;
            r1 = r10.f709a;
            r1 = r1.f712c;
            r2 = r10.f709a;
            r2 = r2.f711b;
            r3 = com.sherwin.tvbox.firmwareupdateclient.C0601R.string.update_download_create_fail;
            r2 = r2.getString(r3);
            r1.mo2213a(r2);
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0624a.f710a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x0079;
        L_0x009c:
            r0 = move-exception;
            r1 = r10.f709a;
            r1 = r1.f712c;
            r2 = r10.f709a;
            r2 = r2.f711b;
            r3 = com.sherwin.tvbox.firmwareupdateclient.C0601R.string.update_download_url_error;
            r2 = r2.getString(r3);
            r1.mo2213a(r2);
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0624a.f710a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x0079;
        L_0x00be:
            r0 = move-exception;
            r1 = r10.f709a;
            r1 = r1.f712c;
            r2 = r10.f709a;
            r2 = r2.f711b;
            r3 = com.sherwin.tvbox.firmwareupdateclient.C0601R.string.update_download_connect_fail;
            r2 = r2.getString(r3);
            r1.mo2213a(r2);
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0624a.f710a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x0079;
        L_0x00e0:
            r2 = r10.f709a;
            r2 = r2.f712c;
            r2.mo2211a();
            r2 = 0;
        L_0x00eb:
            r7 = r5.read(r6);	 Catch:{ IOException -> 0x0156 }
            r8 = -1;
            if (r7 == r8) goto L_0x0109;
        L_0x00f2:
            r8 = 0;
            r4.write(r6, r8, r7);	 Catch:{ IOException -> 0x0156 }
            r8 = (long) r7;	 Catch:{ IOException -> 0x0156 }
            r2 = r2 + r8;
            r7 = r10.f709a;	 Catch:{ IOException -> 0x0156 }
            r7 = r7.f714e;	 Catch:{ IOException -> 0x0156 }
            if (r7 == 0) goto L_0x013e;
        L_0x0100:
            r6 = r10.f709a;	 Catch:{ IOException -> 0x0156 }
            r6 = r6.f712c;	 Catch:{ IOException -> 0x0156 }
            r6.mo2214b();	 Catch:{ IOException -> 0x0156 }
        L_0x0109:
            r4.flush();	 Catch:{ IOException -> 0x019e }
        L_0x010c:
            r5.close();	 Catch:{ IOException -> 0x01a1 }
        L_0x010f:
            r4.close();	 Catch:{ IOException -> 0x01a4 }
        L_0x0112:
            r0.disconnect();
            r0 = r10.f709a;
            r0 = r0.f713d;
            r0 = r0.m876b();
            r4 = r0.longValue();
            r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
            if (r0 == 0) goto L_0x0193;
        L_0x0127:
            r0 = r10.f709a;
            r0 = r0.f712c;
            r1 = r10.f709a;
            r1 = r1.f711b;
            r2 = com.sherwin.tvbox.firmwareupdateclient.C0601R.string.update_download_check_fail;
            r1 = r1.getString(r2);
            r0.mo2213a(r1);
            goto L_0x0079;
        L_0x013e:
            r7 = r10.f709a;	 Catch:{ IOException -> 0x0156 }
            r7 = r7.f712c;	 Catch:{ IOException -> 0x0156 }
            r8 = r10.f709a;	 Catch:{ IOException -> 0x0156 }
            r8 = r8.f713d;	 Catch:{ IOException -> 0x0156 }
            r8 = r8.m876b();	 Catch:{ IOException -> 0x0156 }
            r8 = r8.longValue();	 Catch:{ IOException -> 0x0156 }
            r7.mo2212a(r2, r8);	 Catch:{ IOException -> 0x0156 }
            goto L_0x00eb;
        L_0x0156:
            r1 = move-exception;
            r2 = r10.f709a;	 Catch:{ all -> 0x0185 }
            r2 = r2.f712c;	 Catch:{ all -> 0x0185 }
            r3 = r10.f709a;	 Catch:{ all -> 0x0185 }
            r3 = r3.f711b;	 Catch:{ all -> 0x0185 }
            r6 = com.sherwin.tvbox.firmwareupdateclient.C0601R.string.update_download_fail;	 Catch:{ all -> 0x0185 }
            r3 = r3.getString(r6);	 Catch:{ all -> 0x0185 }
            r2.mo2213a(r3);	 Catch:{ all -> 0x0185 }
            r2 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0624a.f710a;	 Catch:{ all -> 0x0185 }
            r1 = r1.toString();	 Catch:{ all -> 0x0185 }
            android.util.Log.e(r2, r1);	 Catch:{ all -> 0x0185 }
            r4.flush();	 Catch:{ IOException -> 0x01a7 }
        L_0x017a:
            r5.close();	 Catch:{ IOException -> 0x01a9 }
        L_0x017d:
            r4.close();	 Catch:{ IOException -> 0x01ab }
        L_0x0180:
            r0.disconnect();
            goto L_0x0079;
        L_0x0185:
            r1 = move-exception;
            r4.flush();	 Catch:{ IOException -> 0x01ad }
        L_0x0189:
            r5.close();	 Catch:{ IOException -> 0x01af }
        L_0x018c:
            r4.close();	 Catch:{ IOException -> 0x01b1 }
        L_0x018f:
            r0.disconnect();
            throw r1;
        L_0x0193:
            r0 = r10.f709a;
            r0 = r0.f712c;
            r0.mo2215b(r1);
            goto L_0x0079;
        L_0x019e:
            r6 = move-exception;
            goto L_0x010c;
        L_0x01a1:
            r5 = move-exception;
            goto L_0x010f;
        L_0x01a4:
            r4 = move-exception;
            goto L_0x0112;
        L_0x01a7:
            r1 = move-exception;
            goto L_0x017a;
        L_0x01a9:
            r1 = move-exception;
            goto L_0x017d;
        L_0x01ab:
            r1 = move-exception;
            goto L_0x0180;
        L_0x01ad:
            r2 = move-exception;
            goto L_0x0189;
        L_0x01af:
            r2 = move-exception;
            goto L_0x018c;
        L_0x01b1:
            r2 = move-exception;
            goto L_0x018f;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sherwin.tvbox.firmwareupdateclient.c.a.1.run():void");
        }
    }

    public C0624a(Context context) {
        this.f711b = context;
    }

    public C0624a m901a(int i) {
        this.f715f = i;
        return this;
    }

    public C0624a m902a(C0617a c0617a) {
        this.f713d = c0617a;
        return this;
    }

    public C0624a m903a(C0611a c0611a) {
        this.f712c = c0611a;
        return this;
    }

    public boolean m904a() {
        File file = new File(C0622b.f706a);
        if (!file.exists() && !file.isDirectory() && !file.mkdirs()) {
            return false;
        }
        this.f714e = false;
        new Thread(this.f717h).start();
        return true;
    }

    public C0624a m905b(int i) {
        this.f716g = i;
        return this;
    }

    public void m906b() {
        this.f714e = true;
    }
}
