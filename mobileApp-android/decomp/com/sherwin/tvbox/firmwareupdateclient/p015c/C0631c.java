package com.sherwin.tvbox.firmwareupdateclient.p015c;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.sherwin.tvbox.firmwareupdateclient.C0601R;
import com.sherwin.tvbox.firmwareupdateclient.p017a.C0617a;
import java.util.Timer;
import java.util.TimerTask;

public class C0631c {
    private static final String f735a = C0631c.class.getSimpleName();
    private Context f736b;
    private C0602c f737c;
    private String f738d;
    private int f739e = 1000;
    private int f740f = 5000;
    private int f741g = 1;

    public interface C0602c {
        void mo2209a(C0617a c0617a);

        void mo2210a(String str);
    }

    private class C0628a implements Runnable {
        boolean f726a;
        boolean f727b;
        Timer f728c;
        TimerTask f729d;
        final /* synthetic */ C0631c f730e;

        class C06271 extends TimerTask {
            final /* synthetic */ C0628a f725a;

            C06271(C0628a c0628a) {
                this.f725a = c0628a;
            }

            public void run() {
                if (!this.f725a.f727b) {
                    Log.e(C0631c.f735a, "time out");
                    this.f725a.f726a = false;
                    this.f725a.f730e.f737c.mo2210a(this.f725a.f730e.m919d(3));
                }
            }
        }

        private C0628a(C0631c c0631c) {
            this.f730e = c0631c;
            this.f726a = true;
            this.f727b = false;
            this.f728c = new Timer();
            this.f729d = new C06271(this);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r7 = this;
            r6 = 1;
            r0 = r7.f730e;
            r0 = r0.f738d;
            r0 = com.sherwin.tvbox.firmwareupdateclient.C0622b.m893a(r0);
            if (r0 != 0) goto L_0x001d;
        L_0x000d:
            r0 = r7.f730e;
            r0 = r0.f737c;
            r1 = r7.f730e;
            r1 = r1.m919d(r6);
            r0.mo2210a(r1);
        L_0x001c:
            return;
        L_0x001d:
            r1 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1.<init>(r0);	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r2 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            android.util.Log.e(r2, r0);	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r0 = r1.openConnection();	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1 = "GET";
            r0.setRequestMethod(r1);	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1 = r7.f730e;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1 = r1.f739e;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r0.setConnectTimeout(r1);	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0.setReadTimeout(r1);	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1 = r7.f728c;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r2 = r7.f729d;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r3 = r7.f730e;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r3 = r3.f740f;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r4 = (long) r3;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1.schedule(r2, r4);	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r2 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            r1 = r7.f726a;	 Catch:{ MalformedURLException -> 0x0079, IOException -> 0x009c }
            if (r1 == 0) goto L_0x001c;
        L_0x0058:
            r3 = new java.io.BufferedReader;
            r1 = new java.io.InputStreamReader;
            r1.<init>(r2);
            r3.<init>(r1);
            r1 = new com.sherwin.tvbox.firmwareupdateclient.a.a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r1.<init>();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r7.f726a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r5 != 0) goto L_0x00c0;
        L_0x006f:
            r2.close();	 Catch:{ IOException -> 0x0205 }
        L_0x0072:
            r3.close();	 Catch:{ IOException -> 0x0208 }
        L_0x0075:
            r0.disconnect();
            goto L_0x001c;
        L_0x0079:
            r0 = move-exception;
            r1 = r7.f726a;
            if (r1 == 0) goto L_0x008e;
        L_0x007e:
            r1 = r7.f730e;
            r1 = r1.f737c;
            r2 = r7.f730e;
            r3 = 2;
            r2 = r2.m919d(r3);
            r1.mo2210a(r2);
        L_0x008e:
            r7.f727b = r6;
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x001c;
        L_0x009c:
            r0 = move-exception;
            r1 = r7.f726a;
            if (r1 == 0) goto L_0x00b1;
        L_0x00a1:
            r1 = r7.f730e;
            r1 = r1.f737c;
            r2 = r7.f730e;
            r3 = 3;
            r2 = r2.m919d(r3);
            r1.mo2210a(r2);
        L_0x00b1:
            r7.f727b = r6;
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x001c;
        L_0x00c0:
            r4 = java.lang.Double.parseDouble(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = java.lang.Double.valueOf(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r1.m873a(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r7.f726a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r5 != 0) goto L_0x00de;
        L_0x00d3:
            r2.close();	 Catch:{ IOException -> 0x020b }
        L_0x00d6:
            r3.close();	 Catch:{ IOException -> 0x020e }
        L_0x00d9:
            r0.disconnect();
            goto L_0x001c;
        L_0x00de:
            r1.m877b(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r7.f726a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r5 != 0) goto L_0x00f4;
        L_0x00e9:
            r2.close();	 Catch:{ IOException -> 0x0211 }
        L_0x00ec:
            r3.close();	 Catch:{ IOException -> 0x0214 }
        L_0x00ef:
            r0.disconnect();
            goto L_0x001c;
        L_0x00f4:
            r4 = java.lang.Long.parseLong(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = java.lang.Long.valueOf(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r1.m874a(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r7.f726a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r5 != 0) goto L_0x0112;
        L_0x0107:
            r2.close();	 Catch:{ IOException -> 0x0217 }
        L_0x010a:
            r3.close();	 Catch:{ IOException -> 0x021a }
        L_0x010d:
            r0.disconnect();
            goto L_0x001c;
        L_0x0112:
            r1.m875a(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r4.toLowerCase();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r1.m879c(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r7.f726a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r5 != 0) goto L_0x0133;
        L_0x0128:
            r2.close();	 Catch:{ IOException -> 0x021d }
        L_0x012b:
            r3.close();	 Catch:{ IOException -> 0x0220 }
        L_0x012e:
            r0.disconnect();
            goto L_0x001c;
        L_0x0133:
            r5 = r7.f730e;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r5.f738d;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = r4.equals(r5);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r5 != 0) goto L_0x017a;
        L_0x013f:
            r1 = new java.lang.Exception;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r7.f730e;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r5 = 4;
            r4 = r4.m919d(r5);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r1.<init>(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            throw r1;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
        L_0x014c:
            r1 = move-exception;
            r4 = r7.f726a;	 Catch:{ all -> 0x01fa }
            if (r4 == 0) goto L_0x0161;
        L_0x0151:
            r4 = r7.f730e;	 Catch:{ all -> 0x01fa }
            r4 = r4.f737c;	 Catch:{ all -> 0x01fa }
            r5 = r7.f730e;	 Catch:{ all -> 0x01fa }
            r6 = 5;
            r5 = r5.m919d(r6);	 Catch:{ all -> 0x01fa }
            r4.mo2210a(r5);	 Catch:{ all -> 0x01fa }
        L_0x0161:
            r4 = 1;
            r7.f727b = r4;	 Catch:{ all -> 0x01fa }
            r4 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;	 Catch:{ all -> 0x01fa }
            r1 = r1.toString();	 Catch:{ all -> 0x01fa }
            android.util.Log.e(r4, r1);	 Catch:{ all -> 0x01fa }
            r2.close();	 Catch:{ IOException -> 0x022f }
        L_0x0172:
            r3.close();	 Catch:{ IOException -> 0x0232 }
        L_0x0175:
            r0.disconnect();
            goto L_0x001c;
        L_0x017a:
            r1.m881d(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = 0;
            r1.m872a(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = "";
            r1.m883e(r4);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r7.f726a;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            if (r4 != 0) goto L_0x0195;
        L_0x018a:
            r2.close();	 Catch:{ IOException -> 0x0223 }
        L_0x018d:
            r3.close();	 Catch:{ IOException -> 0x0226 }
        L_0x0190:
            r0.disconnect();
            goto L_0x001c;
        L_0x0195:
            r4 = 1;
            r7.f727b = r4;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r7.f730e;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4 = r4.f737c;	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r4.mo2209a(r1);	 Catch:{ IOException -> 0x014c, NumberFormatException -> 0x01ac, Exception -> 0x01da }
            r2.close();	 Catch:{ IOException -> 0x0229 }
        L_0x01a4:
            r3.close();	 Catch:{ IOException -> 0x022c }
        L_0x01a7:
            r0.disconnect();
            goto L_0x001c;
        L_0x01ac:
            r1 = move-exception;
            r4 = r7.f726a;	 Catch:{ all -> 0x01fa }
            if (r4 == 0) goto L_0x01c1;
        L_0x01b1:
            r4 = r7.f730e;	 Catch:{ all -> 0x01fa }
            r4 = r4.f737c;	 Catch:{ all -> 0x01fa }
            r5 = r7.f730e;	 Catch:{ all -> 0x01fa }
            r6 = 6;
            r5 = r5.m919d(r6);	 Catch:{ all -> 0x01fa }
            r4.mo2210a(r5);	 Catch:{ all -> 0x01fa }
        L_0x01c1:
            r4 = 1;
            r7.f727b = r4;	 Catch:{ all -> 0x01fa }
            r4 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;	 Catch:{ all -> 0x01fa }
            r1 = r1.toString();	 Catch:{ all -> 0x01fa }
            android.util.Log.e(r4, r1);	 Catch:{ all -> 0x01fa }
            r2.close();	 Catch:{ IOException -> 0x0235 }
        L_0x01d2:
            r3.close();	 Catch:{ IOException -> 0x0237 }
        L_0x01d5:
            r0.disconnect();
            goto L_0x001c;
        L_0x01da:
            r1 = move-exception;
            r4 = r7.f726a;	 Catch:{ all -> 0x01fa }
            if (r4 == 0) goto L_0x01ec;
        L_0x01df:
            r4 = r7.f730e;	 Catch:{ all -> 0x01fa }
            r4 = r4.f737c;	 Catch:{ all -> 0x01fa }
            r1 = r1.getMessage();	 Catch:{ all -> 0x01fa }
            r4.mo2210a(r1);	 Catch:{ all -> 0x01fa }
        L_0x01ec:
            r1 = 1;
            r7.f727b = r1;	 Catch:{ all -> 0x01fa }
            r2.close();	 Catch:{ IOException -> 0x0239 }
        L_0x01f2:
            r3.close();	 Catch:{ IOException -> 0x023b }
        L_0x01f5:
            r0.disconnect();
            goto L_0x001c;
        L_0x01fa:
            r1 = move-exception;
            r2.close();	 Catch:{ IOException -> 0x023d }
        L_0x01fe:
            r3.close();	 Catch:{ IOException -> 0x023f }
        L_0x0201:
            r0.disconnect();
            throw r1;
        L_0x0205:
            r1 = move-exception;
            goto L_0x0072;
        L_0x0208:
            r1 = move-exception;
            goto L_0x0075;
        L_0x020b:
            r1 = move-exception;
            goto L_0x00d6;
        L_0x020e:
            r1 = move-exception;
            goto L_0x00d9;
        L_0x0211:
            r1 = move-exception;
            goto L_0x00ec;
        L_0x0214:
            r1 = move-exception;
            goto L_0x00ef;
        L_0x0217:
            r1 = move-exception;
            goto L_0x010a;
        L_0x021a:
            r1 = move-exception;
            goto L_0x010d;
        L_0x021d:
            r1 = move-exception;
            goto L_0x012b;
        L_0x0220:
            r1 = move-exception;
            goto L_0x012e;
        L_0x0223:
            r1 = move-exception;
            goto L_0x018d;
        L_0x0226:
            r1 = move-exception;
            goto L_0x0190;
        L_0x0229:
            r1 = move-exception;
            goto L_0x01a4;
        L_0x022c:
            r1 = move-exception;
            goto L_0x01a7;
        L_0x022f:
            r1 = move-exception;
            goto L_0x0172;
        L_0x0232:
            r1 = move-exception;
            goto L_0x0175;
        L_0x0235:
            r1 = move-exception;
            goto L_0x01d2;
        L_0x0237:
            r1 = move-exception;
            goto L_0x01d5;
        L_0x0239:
            r1 = move-exception;
            goto L_0x01f2;
        L_0x023b:
            r1 = move-exception;
            goto L_0x01f5;
        L_0x023d:
            r2 = move-exception;
            goto L_0x01fe;
        L_0x023f:
            r2 = move-exception;
            goto L_0x0201;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sherwin.tvbox.firmwareupdateclient.c.c.a.run():void");
        }
    }

    class C0630b implements Runnable {
        Timer f732a = new Timer();
        boolean f733b = true;
        final /* synthetic */ C0631c f734c;

        class C06291 extends TimerTask {
            final /* synthetic */ C0630b f731a;

            C06291(C0630b c0630b) {
                this.f731a = c0630b;
            }

            public void run() {
                this.f731a.f733b = false;
                this.f731a.f734c.f737c.mo2210a(this.f731a.f734c.m919d(3));
            }
        }

        C0630b(C0631c c0631c) {
            this.f734c = c0631c;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r7 = this;
            r0 = r7.f734c;
            r0 = r0.f738d;
            r0 = com.sherwin.tvbox.firmwareupdateclient.C0622b.m893a(r0);
            if (r0 != 0) goto L_0x001d;
        L_0x000c:
            r0 = r7.f734c;
            r0 = r0.f737c;
            r1 = r7.f734c;
            r2 = 1;
            r1 = r1.m919d(r2);
            r0.mo2210a(r1);
        L_0x001c:
            return;
        L_0x001d:
            r1 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1.<init>(r0);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r2 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            android.util.Log.e(r2, r0);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r0 = r1.openConnection();	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r0 = (java.net.HttpURLConnection) r0;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1 = "GET";
            r0.setRequestMethod(r1);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1 = r7.f734c;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1 = r1.f739e;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r0.setConnectTimeout(r1);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
            r0.setReadTimeout(r1);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1 = r7.f732a;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r2 = new com.sherwin.tvbox.firmwareupdateclient.c.c$b$1;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r2.<init>(r7);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r3 = r7.f734c;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r3 = r3.f740f;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r4 = (long) r3;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1.schedule(r2, r4);	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r2 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            r1 = r7.f733b;	 Catch:{ MalformedURLException -> 0x00e5, IOException -> 0x0107 }
            if (r1 == 0) goto L_0x001c;
        L_0x005b:
            r3 = new java.io.BufferedReader;
            r1 = new java.io.InputStreamReader;
            r1.<init>(r2);
            r3.<init>(r1);
            r1 = new com.sherwin.tvbox.firmwareupdateclient.a.a;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.<init>();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = java.lang.Double.parseDouble(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = java.lang.Double.valueOf(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m873a(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m877b(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = java.lang.Long.parseLong(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = java.lang.Long.valueOf(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m874a(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m875a(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r4.toLowerCase();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m879c(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r5 = r7.f734c;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r5 = r5.f738d;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r5 = r4.equals(r5);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            if (r5 != 0) goto L_0x0129;
        L_0x00b1:
            r1 = new java.lang.Exception;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r7.f734c;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r5 = 4;
            r4 = r4.m919d(r5);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.<init>(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            throw r1;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
        L_0x00be:
            r1 = move-exception;
            r4 = r7.f734c;	 Catch:{ all -> 0x0192 }
            r4 = r4.f737c;	 Catch:{ all -> 0x0192 }
            r5 = r7.f734c;	 Catch:{ all -> 0x0192 }
            r6 = 5;
            r5 = r5.m919d(r6);	 Catch:{ all -> 0x0192 }
            r4.mo2210a(r5);	 Catch:{ all -> 0x0192 }
            r4 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;	 Catch:{ all -> 0x0192 }
            r1 = r1.toString();	 Catch:{ all -> 0x0192 }
            android.util.Log.e(r4, r1);	 Catch:{ all -> 0x0192 }
            r2.close();	 Catch:{ IOException -> 0x01a1 }
        L_0x00dd:
            r3.close();	 Catch:{ IOException -> 0x01a4 }
        L_0x00e0:
            r0.disconnect();
            goto L_0x001c;
        L_0x00e5:
            r0 = move-exception;
            r1 = r7.f733b;
            if (r1 == 0) goto L_0x00fa;
        L_0x00ea:
            r1 = r7.f734c;
            r1 = r1.f737c;
            r2 = r7.f734c;
            r3 = 2;
            r2 = r2.m919d(r3);
            r1.mo2210a(r2);
        L_0x00fa:
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x001c;
        L_0x0107:
            r0 = move-exception;
            r1 = r7.f733b;
            if (r1 == 0) goto L_0x011c;
        L_0x010c:
            r1 = r7.f734c;
            r1 = r1.f737c;
            r2 = r7.f734c;
            r3 = 3;
            r2 = r2.m919d(r3);
            r1.mo2210a(r2);
        L_0x011c:
            r1 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;
            r0 = r0.toString();
            android.util.Log.e(r1, r0);
            goto L_0x001c;
        L_0x0129:
            r1.m881d(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = java.lang.Integer.parseInt(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m872a(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r3.readLine();	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r1.m883e(r4);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r7.f734c;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4 = r4.f737c;	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r4.mo2209a(r1);	 Catch:{ IOException -> 0x00be, NumberFormatException -> 0x0152, Exception -> 0x0179 }
            r2.close();	 Catch:{ IOException -> 0x019d }
        L_0x014a:
            r3.close();	 Catch:{ IOException -> 0x019f }
        L_0x014d:
            r0.disconnect();
            goto L_0x001c;
        L_0x0152:
            r1 = move-exception;
            r4 = r7.f734c;	 Catch:{ all -> 0x0192 }
            r4 = r4.f737c;	 Catch:{ all -> 0x0192 }
            r5 = r7.f734c;	 Catch:{ all -> 0x0192 }
            r6 = 6;
            r5 = r5.m919d(r6);	 Catch:{ all -> 0x0192 }
            r4.mo2210a(r5);	 Catch:{ all -> 0x0192 }
            r4 = com.sherwin.tvbox.firmwareupdateclient.p015c.C0631c.f735a;	 Catch:{ all -> 0x0192 }
            r1 = r1.toString();	 Catch:{ all -> 0x0192 }
            android.util.Log.e(r4, r1);	 Catch:{ all -> 0x0192 }
            r2.close();	 Catch:{ IOException -> 0x01a7 }
        L_0x0171:
            r3.close();	 Catch:{ IOException -> 0x01a9 }
        L_0x0174:
            r0.disconnect();
            goto L_0x001c;
        L_0x0179:
            r1 = move-exception;
            r4 = r7.f734c;	 Catch:{ all -> 0x0192 }
            r4 = r4.f737c;	 Catch:{ all -> 0x0192 }
            r1 = r1.getMessage();	 Catch:{ all -> 0x0192 }
            r4.mo2210a(r1);	 Catch:{ all -> 0x0192 }
            r2.close();	 Catch:{ IOException -> 0x01ab }
        L_0x018a:
            r3.close();	 Catch:{ IOException -> 0x01ad }
        L_0x018d:
            r0.disconnect();
            goto L_0x001c;
        L_0x0192:
            r1 = move-exception;
            r2.close();	 Catch:{ IOException -> 0x01af }
        L_0x0196:
            r3.close();	 Catch:{ IOException -> 0x01b1 }
        L_0x0199:
            r0.disconnect();
            throw r1;
        L_0x019d:
            r1 = move-exception;
            goto L_0x014a;
        L_0x019f:
            r1 = move-exception;
            goto L_0x014d;
        L_0x01a1:
            r1 = move-exception;
            goto L_0x00dd;
        L_0x01a4:
            r1 = move-exception;
            goto L_0x00e0;
        L_0x01a7:
            r1 = move-exception;
            goto L_0x0171;
        L_0x01a9:
            r1 = move-exception;
            goto L_0x0174;
        L_0x01ab:
            r1 = move-exception;
            goto L_0x018a;
        L_0x01ad:
            r1 = move-exception;
            goto L_0x018d;
        L_0x01af:
            r2 = move-exception;
            goto L_0x0196;
        L_0x01b1:
            r2 = move-exception;
            goto L_0x0199;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.sherwin.tvbox.firmwareupdateclient.c.c.b.run():void");
        }
    }

    public C0631c(@NonNull Context context) {
        this.f736b = context;
    }

    private String m919d(int i) {
        return String.format("%s: (%x)", new Object[]{this.f736b.getString(C0601R.string.update_check_error_trips), Integer.valueOf(i)});
    }

    public C0631c m920a(int i) {
        this.f739e = i;
        return this;
    }

    public C0631c m921a(C0602c c0602c) {
        this.f737c = c0602c;
        return this;
    }

    public C0631c m922a(String str) {
        this.f738d = str;
        return this;
    }

    public boolean m923a() {
        switch (this.f741g) {
            case 1:
                new Thread(new C0628a()).start();
                break;
            case 2:
                new Thread(new C0630b(this)).start();
                break;
            default:
                return false;
        }
        return true;
    }

    public C0631c m924b(int i) {
        this.f740f = i;
        return this;
    }

    public C0631c m925c(int i) {
        this.f741g = i;
        return this;
    }
}
