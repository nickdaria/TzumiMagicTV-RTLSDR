package com.p000a.p001a.p002a;

import android.support.v4.internal.view.SupportMenu;
import com.p000a.p001a.p002a.C0384c.C0378a;
import com.p000a.p001a.p002a.C0384c.C0381b;
import com.p000a.p001a.p002a.C0384c.C0382c;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class C0387f implements C0384c, Closeable {
    private final int f32a = 1179403647;
    private final FileChannel f33b;

    public C0387f(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or does not exist");
        }
        this.f33b = new FileInputStream(file).getChannel();
    }

    private long m24a(C0381b c0381b, long j, long j2) throws IOException {
        for (long j3 = 0; j3 < j; j3++) {
            C0382c a = c0381b.mo2021a(j3);
            if (a.f25a == 1 && a.f27c <= j2 && j2 <= a.f27c + a.f28d) {
                return (j2 - a.f27c) + a.f26b;
            }
        }
        throw new IllegalStateException("Could not map vma to file offset!");
    }

    public C0381b m25a() throws IOException {
        this.f33b.position(0);
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(ByteOrder.LITTLE_ENDIAN);
        if (m30c(allocate, 0) != 1179403647) {
            throw new IllegalArgumentException("Invalid ELF Magic!");
        }
        short e = m32e(allocate, 4);
        boolean z = m32e(allocate, 5) == (short) 2;
        if (e == (short) 1) {
            return new C0385d(z, this);
        }
        if (e == (short) 2) {
            return new C0386e(z, this);
        }
        throw new IllegalStateException("Invalid class type!");
    }

    protected String m26a(ByteBuffer byteBuffer, long j) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            long j2 = 1 + j;
            short e = m32e(byteBuffer, j);
            if (e == (short) 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append((char) e);
            j = j2;
        }
    }

    protected void m27a(ByteBuffer byteBuffer, long j, int i) throws IOException {
        byteBuffer.position(0);
        byteBuffer.limit(i);
        long j2 = 0;
        while (j2 < ((long) i)) {
            int read = this.f33b.read(byteBuffer, j + j2);
            if (read == -1) {
                throw new EOFException();
            }
            j2 += (long) read;
        }
        byteBuffer.position(0);
    }

    protected long m28b(ByteBuffer byteBuffer, long j) throws IOException {
        m27a(byteBuffer, j, 8);
        return byteBuffer.getLong();
    }

    public List<String> m29b() throws IOException {
        long j;
        this.f33b.position(0);
        List<String> arrayList = new ArrayList();
        C0381b a = m25a();
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.order(a.f16a ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        long j2 = (long) a.f21f;
        if (j2 == 65535) {
            j2 = a.mo2022a(0).f29a;
        }
        for (j = 0; j < j2; j++) {
            C0382c a2 = a.mo2021a(j);
            if (a2.f25a == 2) {
                j = a2.f26b;
                break;
            }
        }
        j = 0;
        if (j == 0) {
            return Collections.unmodifiableList(arrayList);
        }
        int i = 0;
        List<Long> arrayList2 = new ArrayList();
        long j3 = 0;
        C0378a a3;
        do {
            a3 = a.mo2020a(j, i);
            if (a3.f14a == 1) {
                arrayList2.add(Long.valueOf(a3.f15b));
            } else if (a3.f14a == 5) {
                j3 = a3.f15b;
            }
            i++;
        } while (a3.f14a != 0);
        if (j3 == 0) {
            throw new IllegalStateException("String table offset not found!");
        }
        j2 = m24a(a, j2, j3);
        for (Long longValue : arrayList2) {
            arrayList.add(m26a(allocate, longValue.longValue() + j2));
        }
        return arrayList;
    }

    protected long m30c(ByteBuffer byteBuffer, long j) throws IOException {
        m27a(byteBuffer, j, 4);
        return ((long) byteBuffer.getInt()) & 4294967295L;
    }

    public void close() throws IOException {
        this.f33b.close();
    }

    protected int m31d(ByteBuffer byteBuffer, long j) throws IOException {
        m27a(byteBuffer, j, 2);
        return byteBuffer.getShort() & SupportMenu.USER_MASK;
    }

    protected short m32e(ByteBuffer byteBuffer, long j) throws IOException {
        m27a(byteBuffer, j, 1);
        return (short) (byteBuffer.get() & 255);
    }
}
