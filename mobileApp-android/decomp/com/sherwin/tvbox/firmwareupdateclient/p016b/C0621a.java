package com.sherwin.tvbox.firmwareupdateclient.p016b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.sherwin.tvbox.firmwareupdateclient.C0601R;
import com.sherwin.tvbox.firmwareupdateclient.C0622b;
import com.sherwin.tvbox.firmwareupdateclient.p017a.C0617a;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class C0621a {
    private static final String f705a = C0621a.class.getSimpleName();

    public interface C0613b {
        void mo2216a();

        void mo2217a(long j, long j2);

        void mo2218a(String str);

        void mo2219b();

        void mo2220c();

        void mo2221d();
    }

    private static class C0620a implements C0619b {
        private Context f692a;
        private File f693b;
        private InputStream f694c;
        private Socket f695d;
        private InputStream f696e;
        private OutputStream f697f;
        private boolean f698g;
        private int f699h;
        private int f700i;
        private int f701j;
        private C0617a f702k;
        private C0613b f703l;
        private InetSocketAddress f704m;

        private C0620a(@NonNull Context context, @NonNull C0617a c0617a, @NonNull String str, @NonNull C0613b c0613b) {
            this.f699h = 10000;
            this.f700i = 2000;
            this.f701j = 60000;
            this.f692a = context;
            this.f702k = c0617a;
            this.f703l = c0613b;
            this.f704m = new InetSocketAddress(str, 6667);
        }

        private void m888b() {
            if (this.f694c != null) {
                try {
                    this.f694c.close();
                } catch (IOException e) {
                }
                this.f693b = null;
                this.f694c = null;
            }
            if (this.f696e != null) {
                try {
                    this.f696e.close();
                } catch (IOException e2) {
                }
                this.f696e = null;
            }
            if (this.f697f != null) {
                try {
                    this.f697f.close();
                } catch (IOException e3) {
                }
                this.f697f = null;
            }
            try {
                this.f695d.close();
            } catch (IOException e4) {
            }
            this.f695d = null;
        }

        public void mo2222a() {
            this.f698g = true;
        }

        public void run() {
            this.f693b = new File(C0622b.f706a + "/" + this.f702k.m880d());
            try {
                if (this.f693b.exists()) {
                    this.f694c = new FileInputStream(this.f693b);
                    this.f695d = new Socket();
                    try {
                        this.f695d.connect(this.f704m, this.f699h);
                        this.f695d.setSoTimeout(this.f700i);
                        this.f696e = this.f695d.getInputStream();
                        this.f697f = this.f695d.getOutputStream();
                        byte[] bArr = new byte[10];
                        try {
                            if (!new String(bArr, 0, this.f696e.read(bArr)).contains("SerOK")) {
                                this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_check_fail));
                                m888b();
                                return;
                            } else if (this.f698g) {
                                this.f703l.mo2220c();
                                m888b();
                                return;
                            } else {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("<INFO");
                                stringBuilder.append("<" + this.f702k.m880d());
                                stringBuilder.append("<" + this.f702k.m876b().toString());
                                stringBuilder.append("<" + this.f702k.m882e());
                                byte[] bytes = stringBuilder.toString().getBytes();
                                try {
                                    this.f697f.write(bytes, 0, bytes.length);
                                    try {
                                        if (!new String(bArr, 0, this.f696e.read(bArr)).contains("READY")) {
                                            this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_upload_check_fail));
                                            m888b();
                                            return;
                                        } else if (this.f698g) {
                                            this.f703l.mo2220c();
                                            m888b();
                                            return;
                                        } else {
                                            this.f703l.mo2216a();
                                            byte[] bArr2 = new byte[1024];
                                            int i = 0;
                                            while (true) {
                                                try {
                                                    int read = this.f694c.read(bArr2);
                                                    if (read != -1) {
                                                        this.f697f.write(bArr2, 0, read);
                                                        i += read;
                                                        if (this.f698g) {
                                                            this.f703l.mo2220c();
                                                            m888b();
                                                            return;
                                                        }
                                                        this.f703l.mo2217a((long) i, this.f702k.m876b().longValue());
                                                    } else {
                                                        try {
                                                            break;
                                                        } catch (IOException e) {
                                                        }
                                                    }
                                                } catch (Exception e2) {
                                                    this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_upload_fail));
                                                    Log.e(C0621a.f705a, e2.toString());
                                                    return;
                                                }
                                            }
                                            this.f697f.flush();
                                            try {
                                                this.f694c.close();
                                            } catch (IOException e3) {
                                            }
                                            try {
                                                this.f695d.setSoTimeout(this.f701j);
                                                try {
                                                    if (new String(bArr, 0, this.f696e.read(bArr)).contains("CHECK")) {
                                                        this.f703l.mo2219b();
                                                        try {
                                                            this.f696e.read(bArr);
                                                            String str = new String(bArr, 0, 1);
                                                            if (str.equals("1")) {
                                                                this.f703l.mo2221d();
                                                            } else {
                                                                this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_update_fail_with_code) + str);
                                                            }
                                                            m888b();
                                                            return;
                                                        } catch (SocketTimeoutException e4) {
                                                            this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_timeout));
                                                            m888b();
                                                            return;
                                                        } catch (IOException e5) {
                                                            this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_fail));
                                                            m888b();
                                                            return;
                                                        }
                                                    }
                                                    this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_firmware_check_fail));
                                                    m888b();
                                                    return;
                                                } catch (SocketTimeoutException e6) {
                                                    this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_timeout));
                                                    m888b();
                                                    return;
                                                } catch (IOException e7) {
                                                    this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_fail));
                                                    m888b();
                                                    return;
                                                }
                                            } catch (IOException e8) {
                                                this.f703l.mo2218a("Set Timeout Fail!");
                                                Log.e(C0621a.f705a, e8.toString());
                                                return;
                                            }
                                        }
                                    } catch (SocketTimeoutException e9) {
                                        this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_timeout));
                                        m888b();
                                        return;
                                    } catch (IOException e10) {
                                        this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_fail));
                                        m888b();
                                        return;
                                    }
                                } catch (IOException e11) {
                                    this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_send_info));
                                    m888b();
                                    return;
                                }
                            }
                        } catch (SocketTimeoutException e12) {
                            this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_timeout));
                            m888b();
                            return;
                        } catch (IOException e13) {
                            this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_read_fail));
                            m888b();
                            return;
                        }
                    } catch (SocketTimeoutException e14) {
                        this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_connect_timeout));
                        m888b();
                        Log.e(C0621a.f705a, e14.toString());
                        return;
                    } catch (IOException e82) {
                        this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_connect_fail));
                        Log.e(C0621a.f705a, e82.toString());
                        m888b();
                        return;
                    }
                }
                throw new FileNotFoundException();
            } catch (FileNotFoundException e15) {
                this.f703l.mo2218a(this.f692a.getString(C0601R.string.update_server_firmware_not_found));
            }
        }
    }

    public static C0619b m890a(Context context, int i, @NonNull C0617a c0617a, @NonNull String str, @NonNull C0613b c0613b) {
        switch (i) {
            case 1:
                return new C0620a(context, c0617a, str, c0613b);
            default:
                return null;
        }
    }
}
