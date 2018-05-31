package com.sherwin.tvbox.firmwareupdateclient.p015c;

import android.content.Context;
import com.sherwin.tvbox.firmwareupdateclient.p016b.C0619b;
import com.sherwin.tvbox.firmwareupdateclient.p016b.C0621a;
import com.sherwin.tvbox.firmwareupdateclient.p016b.C0621a.C0613b;
import com.sherwin.tvbox.firmwareupdateclient.p017a.C0617a;

public class C0625b {
    private static final String f718a = C0625b.class.getSimpleName();
    private Context f719b;
    private C0613b f720c;
    private String f721d;
    private C0617a f722e;
    private C0619b f723f;
    private int f724g = 1;

    public C0625b(Context context) {
        this.f719b = context;
    }

    public C0625b m907a(int i) {
        this.f724g = i;
        return this;
    }

    public C0625b m908a(C0617a c0617a) {
        this.f722e = c0617a;
        return this;
    }

    public C0625b m909a(C0613b c0613b) {
        this.f720c = c0613b;
        return this;
    }

    public C0625b m910a(String str) {
        this.f721d = str;
        return this;
    }

    public boolean m911a() {
        this.f723f = C0621a.m890a(this.f719b, this.f724g, this.f722e, this.f721d, this.f720c);
        if (this.f723f == null) {
            return false;
        }
        new Thread(this.f723f).start();
        return true;
    }

    public void m912b() {
        try {
            this.f723f.mo2222a();
        } catch (Exception e) {
        }
    }
}
