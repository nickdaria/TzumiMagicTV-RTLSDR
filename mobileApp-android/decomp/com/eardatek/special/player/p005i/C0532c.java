package com.eardatek.special.player.p005i;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;
import com.eardatek.special.atsc.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class C0532c implements UncaughtExceptionHandler {
    private static C0532c f428b = new C0532c();
    private UncaughtExceptionHandler f429a;
    private Context f430c;
    private Map<String, String> f431d = new HashMap();
    private DateFormat f432e = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    class C05311 extends Thread {
        final /* synthetic */ C0532c f427a;

        C05311(C0532c c0532c) {
            this.f427a = c0532c;
        }

        public void run() {
            Looper.prepare();
            Toast.makeText(this.f427a.f430c, R.string.crash_tips, 1).show();
            Looper.loop();
        }
    }

    private C0532c() {
    }

    public static C0532c m622a() {
        return f428b;
    }

    private boolean m623a(Throwable th) {
        if (th == null) {
            return false;
        }
        new C05311(this).start();
        m626b(this.f430c);
        m624b(th);
        return true;
    }

    private String m624b(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Entry entry : this.f431d.entrySet()) {
            String str = (String) entry.getKey();
            stringBuffer.append(str + "=" + ((String) entry.getValue()) + "\n");
        }
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        stringBuffer.append(stringWriter.toString());
        try {
            String str2 = "crash-" + this.f432e.format(new Date()) + "-" + System.currentTimeMillis() + ".log";
            File file;
            FileOutputStream fileOutputStream;
            if (Environment.getExternalStorageState().equals("mounted")) {
                str = Environment.getExternalStorageDirectory().getPath() + "/crash/";
                file = new File(str);
                if (!file.exists()) {
                    file.mkdirs();
                }
                fileOutputStream = new FileOutputStream(str + str2);
                fileOutputStream.write(stringBuffer.toString().getBytes());
                fileOutputStream.close();
                return str2;
            }
            str = "Crash";
            file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            fileOutputStream = new FileOutputStream(str + str2);
            fileOutputStream.write(stringBuffer.toString().getBytes());
            fileOutputStream.close();
            return str2;
        } catch (Throwable cause2) {
            Log.e("CrashHandler", "an error occured while writing file...", cause2);
            return null;
        }
    }

    public void m625a(Context context) {
        this.f430c = context;
        this.f429a = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public void m626b(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
            if (packageInfo != null) {
                Object obj = packageInfo.versionName == null ? "null" : packageInfo.versionName;
                String str = packageInfo.versionCode + "";
                this.f431d.put("versionName", obj);
                this.f431d.put("versionCode", str);
            }
        } catch (Throwable e) {
            Log.e("CrashHandler", "an error occured when collect package info", e);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                this.f431d.put(field.getName(), field.get(null).toString());
                Log.d("CrashHandler", field.getName() + " : " + field.get(null));
            } catch (Throwable e2) {
                Log.e("CrashHandler", "an error occured when collect crash info", e2);
            }
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (m623a(th) || this.f429a == null) {
            try {
                Thread.sleep(3000);
            } catch (Throwable e) {
                Log.e("CrashHandler", "error : ", e);
            }
            Process.killProcess(Process.myPid());
            System.exit(1);
            return;
        }
        this.f429a.uncaughtException(thread, th);
    }
}
