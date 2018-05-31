package com.eardatek.special.player.p005i;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.blazevideo.libdtv.LibDTV;
import com.blazevideo.libdtv.LibDtvException;
import com.eardatek.special.atsc.R;
import com.eardatek.special.player.actitivy.ScanChannelActivity;
import com.eardatek.special.player.p007b.C0500a;
import com.eardatek.special.player.p010g.C0521a;
import com.eardatek.special.player.system.C0563b;
import com.eardatek.special.player.system.DTVApplication;
import java.lang.ref.WeakReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class C0530b {
    private Handler f415a = null;
    private C0546l f416b = null;
    private boolean f417c = false;
    private ScanChannelActivity f418d;
    private Thread f419e;
    private LibDTV f420f;
    private int[] f421g = new int[]{0};
    private int[] f422h = new int[]{177000, 184000, 191000, 198000, 205000, 212000, 219000, 226000, 474000, 482000, 490000, 498000, 506000, 514000, 522000, 530000, 538000, 546000, 554000, 562000, 570000, 578000, 586000, 594000, 594000, 602000, 610000, 618000, 626000, 634000, 642000, 650000, 658000, 666000, 674000, 682000, 690000, 698000, 706000, 714000, 722000, 730000, 738000, 746000, 754000, 762000, 770000, 778000, 786000, 794000, 802000, 810000, 818000, 826000, 834000, 842000, 850000, 858000};
    private int[] f423i = new int[]{474000, 482000, 490000, 498000, 506000, 514000, 522000, 530000, 538000, 546000, 554000, 562000, 570000, 578000, 586000, 594000, 594000, 602000, 610000, 618000, 626000, 634000, 642000, 650000, 658000, 666000, 674000, 682000, 690000, 698000, 706000, 714000, 722000, 730000, 738000, 746000, 754000, 762000, 770000, 778000, 786000, 794000, 802000, 810000, 818000, 826000, 834000, 842000, 850000, 858000};
    private int[] f424j = new int[]{57000, 63000, 69000, 79000, 85000, 177000, 183000, 189000, 195000, 201000, 207000, 213000, 473000, 479000, 485000, 491000, 497000, 503000, 509000, 515000, 521000, 527000, 533000, 539000, 545000, 551000, 557000, 563000, 569000, 575000, 581000, 587000, 593000, 599000, 605000, 611000, 617000, 623000, 629000, 635000, 641000, 647000, 653000, 659000, 665000, 671000, 677000, 683000, 689000, 695000, 701000, 707000, 713000, 719000, 725000, 731000, 737000, 743000, 749000, 755000, 761000, 767000, 773000, 779000, 785000, 791000, 797000, 803000, 809000, 815000, 821000, 827000, 833000, 839000, 845000, 851000, 857000, 863000, 869000, 875000, 881000, 887000};
    private int[] f425k = new int[]{93000, 99000, 105000, 111000, 117000, 123000, 129000, 135000, 141000, 147000, 153000, 159000, 167000, 173000, 179000, 185000, 191000, 195000, 201000, 207000, 213000, 219000, 225000, 231000, 237000, 243000, 249000, 255000, 261000, 267000, 273000, 279000, 285000, 291000, 297000, 303000, 309000, 315000, 321000, 327000, 333000, 339000, 345000, 351000, 357000, 363000, 369000, 375000, 381000, 387000, 393000, 399000, 405000, 411000, 417000, 423000, 429000, 435000, 441000, 447000, 453000, 459000, 465000, 473000, 479000, 485000, 491000, 497000, 503000, 509000, 515000, 521000, 527000, 533000, 539000, 545000, 551000, 557000, 563000, 569000, 575000, 581000, 587000, 593000, 599000, 605000, 611000, 617000, 623000, 629000, 635000, 641000, 647000, 653000, 659000, 665000, 671000, 677000, 683000, 689000, 695000, 701000, 707000, 713000, 719000, 725000, 731000, 737000, 743000, 749000, 755000, 761000, 767000, 773000, 779000, 785000, 791000, 797000, 803000, 809000, 815000, 821000, 827000};
    private int[] f426l = new int[]{177500, 184500, 191625, 198500, 205500, 212500, 219500, 226500, 529500, 536500, 543500, 550500, 557500, 564500, 571500, 578500, 585500, 592500, 599500, 606500, 613500, 620500, 627500, 634500, 641500, 648500, 655500, 662500, 669500, 676500, 683500, 690500, 697500, 704500, 711500, 718500, 725500, 732500, 739500, 746500, 753500, 760500, 767500, 774500, 781500, 788500, 795500, 802500, 809500, 816500};

    private static class C0528a {
        boolean f409a;
        int f410b;
        boolean f411c;

        C0528a(boolean z, int i, boolean z2) {
            this.f409a = z;
            this.f410b = i;
            this.f411c = z2;
        }
    }

    private class C0529b implements Runnable {
        final /* synthetic */ C0530b f412a;
        private List<C0500a> f413b = new ArrayList();
        private WeakReference<ScanChannelActivity> f414c;

        C0529b(C0530b c0530b, ScanChannelActivity scanChannelActivity) {
            this.f412a = c0530b;
            this.f414c = new WeakReference(scanChannelActivity);
        }

        private void m601a(List<C0500a> list) {
            if (list != null && list.size() != 0) {
                C0521a c0521a = new C0521a(DTVApplication.m750a());
                for (int i = 0; i < list.size(); i++) {
                    try {
                        c0521a.m590a((C0500a) list.get(i));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    c0521a.m594c();
                } catch (SQLException e2) {
                    e2.printStackTrace();
                }
                C0539i.m643b("receiveChannelInfo", "jump to saveChannelList");
            }
        }

        int m602a(int i, int i2, int i3) {
            int i4 = 0;
            while (i4 < 15 && !this.f412a.f417c) {
                try {
                    Thread.sleep(1000);
                    if (!this.f412a.f420f.isPlaying()) {
                        break;
                    }
                    String programList = this.f412a.f420f.getProgramList();
                    String pidList = this.f412a.f420f.getPidList();
                    if (!(programList.isEmpty() || pidList.isEmpty())) {
                        try {
                            Thread.sleep(1000);
                            String programList2 = this.f412a.f420f.getProgramList();
                            String pidList2 = this.f412a.f420f.getPidList();
                            if (programList.equals(programList2) && pidList.equals(pidList2)) {
                                Collection a = this.f412a.m607a(programList, pidList, i, i2, i3);
                                if (a.size() > 0 && i4 < 14) {
                                    programList2 = ((C0500a) a.get(0)).m489a();
                                    if (programList2.length() > 8 && programList2.substring(0, 8).equals("service-")) {
                                    }
                                }
                                this.f413b.addAll(a);
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    i4++;
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
            C0539i.m643b("ScanChannelActivity", "jump to receiveChannelInfo");
            return 0;
        }

        public void run() {
            ScanChannelActivity scanChannelActivity = (ScanChannelActivity) this.f414c.get();
            if (scanChannelActivity != null) {
                try {
                    int[] b;
                    this.f412a.f420f = C0563b.m756a();
                    String[] strArr = new String[]{":no-video", ":no-audio"};
                    String f = this.f412a.f416b.m692f();
                    if (scanChannelActivity.m450a() > 0) {
                        this.f412a.f421g[0] = scanChannelActivity.m450a();
                        b = this.f412a.f421g;
                    } else {
                        b = f.contains("DVB") ? C0549n.m709b(scanChannelActivity.getApplicationContext(), "mode") == 1 ? this.f412a.f426l : this.f412a.f422h : f.contains("ISDB") ? C0549n.m710b(DTVApplication.m750a(), "mode", 0) == 0 ? this.f412a.f425k : this.f412a.f422h : f.contains("ATSC") ? this.f412a.f424j : this.f412a.f423i;
                    }
                    int length = b.length;
                    int i = 0;
                    for (int i2 = 0; i2 < length && !this.f412a.f417c && !this.f412a.f419e.isInterrupted(); i2++) {
                        int i3 = b[i2];
                        Message obtainMessage = this.f412a.f415a.obtainMessage(10);
                        obtainMessage.what = 10;
                        obtainMessage.arg1 = i3;
                        obtainMessage.arg2 = (((i2 * 100) / length) * 10000) + i;
                        this.f412a.f415a.sendMessage(obtainMessage);
                        int i4 = 0;
                        while (!this.f412a.f417c && !this.f412a.f419e.isInterrupted() && this.f412a.f416b.m683a(i3, 8000, i4, 0)) {
                            this.f412a.f420f.playMRL(C0546l.m653a().m701o(), strArr);
                            m602a(i3, 8000, i4);
                            this.f412a.f416b.m694h();
                            this.f412a.f420f.stop();
                            if (i4 + 1 >= this.f412a.f416b.m693g()) {
                                break;
                            }
                            i4++;
                        }
                        m601a(this.f413b);
                        i += this.f413b.size();
                        this.f413b.clear();
                    }
                    this.f412a.f420f.stop();
                    Message obtainMessage2 = this.f412a.f415a.obtainMessage(11);
                    obtainMessage2.arg1 = i;
                    this.f412a.f415a.sendMessage(obtainMessage2);
                    obtainMessage2 = this.f412a.f415a.obtainMessage(12);
                    obtainMessage2.arg1 = 0;
                    this.f412a.f415a.sendMessage(obtainMessage2);
                } catch (LibDtvException e) {
                }
            }
        }
    }

    public C0530b(Handler handler, ScanChannelActivity scanChannelActivity) {
        this.f415a = handler;
        this.f418d = scanChannelActivity;
        this.f416b = C0546l.m653a();
    }

    private C0528a m604a(int i, String str) {
        String[] split = str.split(";");
        int length = split.length;
        int i2 = 0;
        while (i2 < length) {
            String[] split2 = split[i2].split(":");
            if (split2.length < 3 || Integer.parseInt(split2[0]) != i) {
                i2++;
            } else {
                String[] split3 = split2[3].split(",");
                return new C0528a(split2[1].equals("3"), Integer.parseInt(split3[0].substring(10)), split3[2].equals("hasCA=1"));
            }
        }
        return new C0528a(false, 0, false);
    }

    private List<C0500a> m607a(String str, String str2, int i, int i2, int i3) {
        List<C0500a> arrayList = new ArrayList();
        String[] split = str.split(";");
        int i4 = 0;
        int length = split.length;
        int i5 = 0;
        while (i5 < length) {
            int i6 = 0;
            String str3 = "";
            String[] split2 = split[i5].split(":");
            if (split2.length > 0) {
                i6 = Integer.parseInt(split2[0]);
            }
            C0539i.m643b("EardatekVersion2", "pidlist:" + str2);
            C0528a a = m604a(i6, str2);
            C0539i.m643b("ScanChannelActivity", "videoType:" + a.f410b);
            if (a.f409a) {
                int i7 = a.f411c ? 1 : 0;
                if (split2.length > 1) {
                    split2 = split2[1].split("\\[");
                    if (split2.length > 0) {
                        str3 = split2[0];
                    }
                }
                String format = String.format(Locale.ENGLISH, "freq%d-bw%d-plp%d-prog%d-isradio%d-isEncrypt%d-videoType%d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i6), Integer.valueOf(0), Integer.valueOf(i7), Integer.valueOf(a.f410b)});
                if (str3.isEmpty()) {
                    str3 = String.format(Locale.ENGLISH, "Program %d", new Object[]{Integer.valueOf(i6)});
                }
                arrayList.add(new C0500a(format, str3));
                i6 = i4 + 1;
            } else {
                i6 = i4;
            }
            i5++;
            i4 = i6;
        }
        C0539i.m643b("ScanChannelActivity", "jump to parseProgramList");
        return arrayList;
    }

    public static boolean m608a(String str, float f) {
        if (str.contains("DVB") && (f > 858.0f || f < 100.0f)) {
            Toast.makeText(DTVApplication.m750a(), R.string.freqtips, 0).show();
            return false;
        } else if (str.contains("ATSC") && (f > 887.0f || f < 57.0f)) {
            Toast.makeText(DTVApplication.m750a(), R.string.freqtips, 0).show();
            return false;
        } else if (str.contains("ISDB") && (f > 858.0f || f < 93.0f)) {
            Toast.makeText(DTVApplication.m750a(), R.string.freqtips, 0).show();
            return false;
        } else if (!str.contains("DTMB") || (f <= 858.0f && f >= 474.0f)) {
            return true;
        } else {
            Toast.makeText(DTVApplication.m750a(), R.string.freqtips, 0).show();
            return false;
        }
    }

    public boolean m619a() {
        this.f419e = new Thread(new C0529b(this, this.f418d));
        this.f419e.start();
        return true;
    }

    public boolean m620b() {
        this.f417c = true;
        this.f419e.interrupt();
        return true;
    }
}
