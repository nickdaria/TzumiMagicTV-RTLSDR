package com.p000a.p001a.p002a;

import com.p000a.p001a.p002a.C0384c.C0381b;
import com.p000a.p001a.p002a.C0384c.C0383d;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class C0391j extends C0383d {
    public C0391j(C0387f c0387f, C0381b c0381b, int i) throws IOException {
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(c0381b.f16a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.a = c0387f.m30c(allocate, (c0381b.f19d + ((long) (c0381b.f22g * i))) + 44);
    }
}
