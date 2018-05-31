package com.eardatek.special.player.p005i;

import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Locale;

public class C0553q {
    private static final String f477a = C0553q.class.getSimpleName();
    private ServerSocket f478b;
    private InetSocketAddress f479c;
    private InetSocketAddress f480d;
    private C0448a f481e;
    private int f482f = 1000;
    private int f483g = 5000;
    private int f484h = 5000;
    private boolean f485i = false;
    private boolean f486j = false;

    public interface C0448a {
        void mo2088a(byte[] bArr, int i);
    }

    class C0552b implements Runnable {
        final /* synthetic */ C0553q f476a;

        C0552b(C0553q c0553q) {
            this.f476a = c0553q;
        }

        public void run() {
            IOException iOException;
            this.f476a.f486j = true;
            Socket socket = null;
            Log.e(C0553q.f477a, "start");
            Log.e(C0553q.f477a, "Output:" + this.f476a.m727b());
            Log.e(C0553q.f477a, "Inout :" + this.f476a.m725a());
            while (!this.f476a.f485i) {
                try {
                    this.f476a.f478b.setSoTimeout(this.f476a.f482f);
                    Log.d(C0553q.f477a, "Wait Connect");
                    Socket accept = this.f476a.f478b.accept();
                    Log.e(C0553q.f477a, "Connect In");
                    accept.setSoTimeout(this.f476a.f484h);
                    try {
                        Socket socket2 = new Socket();
                        try {
                            socket2.connect(this.f476a.f479c);
                            if (socket2.isConnected()) {
                                socket2.setSoTimeout(this.f476a.f483g);
                                Log.e(C0553q.f477a, "Create inputSocket successful:  " + this.f476a.f479c.getAddress() + ":" + this.f476a.f479c.getPort());
                                try {
                                    OutputStream outputStream = accept.getOutputStream();
                                    InputStream inputStream = socket2.getInputStream();
                                    byte[] bArr = new byte[4096];
                                    while (!this.f476a.f485i) {
                                        try {
                                            int read = inputStream.read(bArr);
                                            if (read >= 0) {
                                                try {
                                                    outputStream.write(bArr, 0, read);
                                                    if (this.f476a.f481e != null) {
                                                        this.f476a.f481e.mo2088a(bArr, read);
                                                    }
                                                } catch (SocketTimeoutException e) {
                                                    Log.e(C0553q.f477a, "write timeout");
                                                } catch (IOException e2) {
                                                    Log.e(C0553q.f477a, "write fail:" + e2.toString());
                                                }
                                            }
                                        } catch (SocketTimeoutException e3) {
                                            Log.e(C0553q.f477a, "read timeout");
                                        } catch (IOException e22) {
                                            Log.e(C0553q.f477a, "read fail:" + e22.toString());
                                        }
                                    }
                                    try {
                                        accept.close();
                                    } catch (IOException e4) {
                                    }
                                    try {
                                        socket2.close();
                                    } catch (IOException e5) {
                                    }
                                    socket = socket2;
                                } catch (IOException e6) {
                                    try {
                                        accept.close();
                                    } catch (IOException e7) {
                                    }
                                    try {
                                        socket2.close();
                                    } catch (IOException e8) {
                                    }
                                    socket = socket2;
                                }
                            } else {
                                throw new IOException("cannot connect to" + this.f476a.f479c.getAddress() + ":" + this.f476a.f479c.getPort());
                            }
                        } catch (IOException e222) {
                            IOException iOException2 = e222;
                            socket = socket2;
                            iOException = iOException2;
                            try {
                                socket.close();
                            } catch (IOException e9) {
                            }
                            try {
                                accept.close();
                            } catch (IOException e10) {
                            }
                            Log.e(C0553q.f477a, "Create inputSocket fail:" + iOException.toString());
                        }
                    } catch (IOException e11) {
                        iOException = e11;
                        socket.close();
                        accept.close();
                        Log.e(C0553q.f477a, "Create inputSocket fail:" + iOException.toString());
                    }
                } catch (Exception e12) {
                    Log.e(C0553q.f477a, "Wait timeout");
                }
            }
            this.f476a.f486j = false;
            try {
                this.f476a.f478b.close();
            } catch (IOException e13) {
            }
        }
    }

    public C0553q(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        this.f479c = inetSocketAddress2;
        this.f480d = inetSocketAddress;
    }

    public String m725a() {
        return String.format(Locale.ENGLISH, "tcp:/%s:%d", new Object[]{this.f479c.getAddress(), Integer.valueOf(this.f479c.getPort())});
    }

    public void m726a(C0448a c0448a) {
        this.f481e = c0448a;
    }

    public String m727b() {
        return String.format(Locale.ENGLISH, "tcp://%s:%d", new Object[]{this.f480d.getHostName(), Integer.valueOf(this.f480d.getPort())});
    }

    public boolean m728c() {
        m729d();
        try {
            this.f478b = new ServerSocket();
            this.f478b.bind(this.f480d, 1);
            Log.e(f477a, "outputServer: " + this.f480d.getAddress() + ":" + this.f480d.getPort());
            Log.e(f477a, "init successful");
            this.f485i = false;
            new Thread(new C0552b(this)).start();
            return true;
        } catch (Exception e) {
            Log.e(f477a, "init fail");
            return false;
        }
    }

    public void m729d() {
        this.f485i = true;
    }

    public boolean m730e() {
        return this.f486j;
    }
}
