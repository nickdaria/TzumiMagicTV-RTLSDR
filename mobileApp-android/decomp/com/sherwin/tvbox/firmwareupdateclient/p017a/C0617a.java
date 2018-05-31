package com.sherwin.tvbox.firmwareupdateclient.p017a;

public class C0617a {
    private Double f684a;
    private Long f685b;
    private String f686c;
    private String f687d;
    private String f688e;
    private String f689f;
    private int f690g;
    private String f691h;

    public Double m871a() {
        return this.f684a;
    }

    public void m872a(int i) {
        this.f690g = i;
    }

    public void m873a(Double d) {
        this.f684a = d;
    }

    public void m874a(Long l) {
        this.f685b = l;
    }

    public void m875a(String str) {
        this.f686c = str;
    }

    public Long m876b() {
        return this.f685b;
    }

    public void m877b(String str) {
        this.f687d = str;
    }

    public String m878c() {
        return this.f686c;
    }

    public void m879c(String str) {
        this.f688e = str;
    }

    public String m880d() {
        return this.f687d;
    }

    public void m881d(String str) {
        this.f689f = str;
    }

    public String m882e() {
        return this.f688e;
    }

    public void m883e(String str) {
        this.f691h = str;
    }

    public String m884f() {
        return this.f689f;
    }

    public int m885g() {
        return this.f690g;
    }

    public String m886h() {
        return this.f691h;
    }

    public String toString() {
        return "FirmwareVersionInfo{version=" + this.f684a + ", fileSize=" + this.f685b + ", downloadUrl='" + this.f686c + '\'' + ", fileName='" + this.f687d + '\'' + ", fileSha1='" + this.f688e + '\'' + ", deviceType='" + this.f689f + '\'' + ", updateType=" + this.f690g + ", descriptor='" + this.f691h + '\'' + '}';
    }
}
