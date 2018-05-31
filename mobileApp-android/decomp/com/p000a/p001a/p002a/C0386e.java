package com.p000a.p001a.p002a;

import com.p000a.p001a.p002a.C0384c.C0378a;
import com.p000a.p001a.p002a.C0384c.C0381b;
import com.p000a.p001a.p002a.C0384c.C0382c;
import com.p000a.p001a.p002a.C0384c.C0383d;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class C0386e extends C0381b {
    private final C0387f f31j;

    public C0386e(boolean z, C0387f c0387f) throws IOException {
        this.a = z;
        this.f31j = c0387f;
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.b = c0387f.m31d(allocate, 16);
        this.c = c0387f.m28b(allocate, 32);
        this.d = c0387f.m28b(allocate, 40);
        this.e = c0387f.m31d(allocate, 54);
        this.f = c0387f.m31d(allocate, 56);
        this.g = c0387f.m31d(allocate, 58);
        this.h = c0387f.m31d(allocate, 60);
        this.i = c0387f.m31d(allocate, 62);
    }

    public C0378a mo2020a(long j, int i) throws IOException {
        return new C0380b(this.f31j, this, j, i);
    }

    public C0382c mo2021a(long j) throws IOException {
        return new C0389h(this.f31j, this, j);
    }

    public C0383d mo2022a(int i) throws IOException {
        return new C0391j(this.f31j, this, i);
    }
}
