package com.sherwin.libepgparser.p013a;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class C0594a {
    private String f632a;
    private String f633b;
    private int f634c;
    private int f635d;
    private int f636e;
    private int f637f = 0;
    private int f638g = 0;
    private int f639h = 0;
    private boolean f640i = false;
    private List<C0592a> f641j;
    private boolean f642k = false;

    public class C0592a {
        final /* synthetic */ C0594a f628a;
        private String f629b;
        private int f630c;

        C0592a(C0594a c0594a, String str, int i) {
            this.f628a = c0594a;
            this.f629b = str;
            this.f630c = i;
        }

        public String m810a() {
            return this.f629b;
        }

        public int m811b() {
            return this.f630c;
        }
    }

    public class C0593b extends Exception {
        final /* synthetic */ C0594a f631a;

        public C0593b(C0594a c0594a, String str) {
            this.f631a = c0594a;
            super(str);
        }
    }

    public C0594a(String str, String str2) {
        this.f632a = new String(str);
        this.f633b = str2;
        this.f634c = str.length();
        this.f635d = str2.length();
        this.f641j = new ArrayList();
        this.f636e = C0594a.m813a(str2);
    }

    private static int m812a(char c, char c2) {
        char toLowerCase = Character.toLowerCase(c);
        char toLowerCase2 = Character.toLowerCase(c2);
        switch (toLowerCase) {
            case 'b':
                return 4;
            case 'c':
                return 12;
            case 'd':
            case 'i':
                return 1;
            case 'e':
            case 'f':
                return 9;
            case 'l':
                switch (toLowerCase2) {
                    case 'b':
                        return 8;
                    case 'd':
                    case 'i':
                        return 5;
                    case 'e':
                    case 'f':
                        return 10;
                    case 'o':
                        return 7;
                    case 'x':
                        return 6;
                    default:
                        return 0;
                }
            case 'o':
                return 3;
            case 's':
                return 11;
            case 'v':
                return 13;
            case 'x':
                return 2;
            default:
                return 0;
        }
    }

    private static int m813a(String str) {
        int i = 0;
        char[] toCharArray = str.toCharArray();
        int i2 = 0;
        while (i < toCharArray.length - 1) {
            if (toCharArray[i] == '%') {
                i++;
                if (toCharArray[i] != '%') {
                    i2++;
                }
            }
            i++;
        }
        return i2;
    }

    private static String m814b(String str) {
        int indexOf = str.toLowerCase().indexOf("0x");
        return indexOf >= 0 ? str.substring(indexOf + 2) : str;
    }

    private C0592a m815c() {
        C0592a c0592a = null;
        boolean z = false;
        while (!this.f640i && this.f638g < this.f635d - 1 && this.f637f < this.f634c) {
            if (!z && this.f633b.charAt(this.f638g) == '%') {
                try {
                    char charAt = this.f633b.charAt(this.f638g + 1);
                    int i = 2;
                    if (charAt == '%') {
                        this.f638g++;
                        z = true;
                    } else {
                        char charAt2;
                        if (charAt == 'l' || charAt == 'L') {
                            charAt2 = this.f633b.charAt(this.f638g + 2);
                            i = 3;
                        } else {
                            charAt2 = ' ';
                        }
                        int a = C0594a.m812a(charAt, charAt2);
                        if (a == 0) {
                            this.f640i = true;
                            throw new C0593b(this, "Unknow Format Type: %" + charAt + charAt2);
                        }
                        String substring;
                        if (this.f638g + i >= this.f635d) {
                            substring = this.f632a.substring(this.f637f);
                            this.f637f = this.f634c;
                            this.f638g = this.f635d;
                        } else {
                            int indexOf = this.f632a.substring(this.f637f).indexOf(this.f633b.charAt(this.f638g + i));
                            if (indexOf == -1) {
                                this.f640i = true;
                                throw new C0593b(this, "Prase String Error");
                            }
                            substring = this.f632a.substring(this.f637f, this.f637f + indexOf);
                            this.f637f = indexOf + this.f637f;
                            this.f638g = i + this.f638g;
                        }
                        c0592a = new C0592a(this, substring, a);
                    }
                } catch (Exception e) {
                    if (e instanceof C0593b) {
                        throw e;
                    }
                    this.f640i = true;
                    throw new C0593b(this, e.getMessage());
                }
            } else if (this.f633b.charAt(this.f638g) != this.f632a.charAt(this.f637f)) {
                this.f640i = true;
                throw new C0593b(this, "Source String and Format String unmark");
            } else {
                this.f638g++;
                this.f637f++;
                if (z) {
                    z = false;
                }
            }
            if (c0592a != null) {
                break;
            }
        }
        if (c0592a != null) {
            return c0592a;
        }
        this.f640i = true;
        throw new C0593b(this, "Parse End");
    }

    private static String m816c(String str) {
        int indexOf = str.toLowerCase().indexOf("0b");
        return indexOf >= 0 ? str.substring(indexOf + 2) : str;
    }

    public int m817a() {
        String a;
        int i;
        C0592a c = this.f642k ? (C0592a) this.f641j.remove(0) : m815c();
        switch (c.m811b()) {
            case 1:
                a = c.m810a();
                i = 10;
                break;
            case 2:
                a = C0594a.m814b(c.m810a());
                i = 16;
                break;
            case 3:
                a = c.m810a();
                i = 8;
                break;
            case 4:
                a = C0594a.m816c(c.m810a());
                i = 2;
                break;
            default:
                throw new C0593b(this, "Not Integer type");
        }
        try {
            return new BigInteger(a, i).intValue();
        } catch (NumberFormatException e) {
            throw new C0593b(this, e.toString());
        }
    }

    public String m818b() {
        C0592a c = this.f642k ? (C0592a) this.f641j.remove(0) : m815c();
        if (c.m811b() == 11) {
            return c.m810a();
        }
        throw new C0593b(this, "Not String type");
    }
}
