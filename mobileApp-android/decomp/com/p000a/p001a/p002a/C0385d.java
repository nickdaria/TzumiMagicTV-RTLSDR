package com.p000a.p001a.p002a;

import com.p000a.p001a.p002a.C0384c.C0378a;
import com.p000a.p001a.p002a.C0384c.C0381b;
import com.p000a.p001a.p002a.C0384c.C0382c;
import com.p000a.p001a.p002a.C0384c.C0383d;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class C0385d extends C0381b {
    private final C0387f f30j;

    public C0385d(boolean z, C0387f c0387f) throws IOException {
        this.a = z;
        this.f30j = c0387f;
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.order(z ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.b = c0387f.m31d(allocate, 16);
        this.c = c0387f.m30c(allocate, 28);
        this.d = c0387f.m30c(allocate, 32);
        this.e = c0387f.m31d(allocate, 42);
        this.f = c0387f.m31d(allocate, 44);
        this.g = c0387f.m31d(allocate, 46);
        this.h = c0387f.m31d(allocate, 48);
        this.i = c0387f.m31d(allocate, 50);
    }

    public C0378a mo2020a(long j, int i) throws IOException {
        return new C0379a(this.f30j, this, j, i);
    }

    public C0382c mo2021a(long j) throws IOException {
        return new C0388g(this.f30j, this, j);
    }

    public C0383d mo2022a(int i) throws IOException {
        return new C0390i(this.f30j, this, i);
    }
}
