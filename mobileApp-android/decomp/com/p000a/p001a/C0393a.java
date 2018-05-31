package com.p000a.p001a;

import com.p000a.p001a.C0398c.C0392a;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class C0393a implements C0392a {
    private long m34a(InputStream inputStream, OutputStream outputStream) throws IOException {
        long j = 0;
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                outputStream.flush();
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }

    private void m35a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo2023a(android.content.Context r14, java.lang.String[] r15, java.lang.String r16, java.io.File r17, com.p000a.p001a.C0401d r18) {
        /*
        r13 = this;
        r3 = 0;
        r6 = r14.getApplicationInfo();	 Catch:{ all -> 0x013d }
        r2 = 0;
    L_0x0006:
        r4 = r2 + 1;
        r5 = 5;
        if (r2 >= r5) goto L_0x0157;
    L_0x000b:
        r5 = new java.util.zip.ZipFile;	 Catch:{ IOException -> 0x0027 }
        r2 = new java.io.File;	 Catch:{ IOException -> 0x0027 }
        r7 = r6.sourceDir;	 Catch:{ IOException -> 0x0027 }
        r2.<init>(r7);	 Catch:{ IOException -> 0x0027 }
        r7 = 1;
        r5.<init>(r2, r7);	 Catch:{ IOException -> 0x0027 }
    L_0x0018:
        if (r5 != 0) goto L_0x002a;
    L_0x001a:
        r2 = "FATAL! Couldn't find application APK!";
        r0 = r18;
        r0.m53a(r2);	 Catch:{ all -> 0x0082 }
        if (r5 == 0) goto L_0x0026;
    L_0x0023:
        r5.close();	 Catch:{ IOException -> 0x0137 }
    L_0x0026:
        return;
    L_0x0027:
        r2 = move-exception;
        r2 = r4;
        goto L_0x0006;
    L_0x002a:
        r2 = 0;
    L_0x002b:
        r6 = r2 + 1;
        r3 = 5;
        if (r2 >= r3) goto L_0x0126;
    L_0x0030:
        r4 = 0;
        r3 = 0;
        r7 = r15.length;	 Catch:{ all -> 0x0082 }
        r2 = 0;
        r12 = r2;
        r2 = r3;
        r3 = r4;
        r4 = r12;
    L_0x0038:
        if (r4 >= r7) goto L_0x0153;
    L_0x003a:
        r2 = r15[r4];	 Catch:{ all -> 0x0082 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0082 }
        r3.<init>();	 Catch:{ all -> 0x0082 }
        r8 = "lib";
        r3 = r3.append(r8);	 Catch:{ all -> 0x0082 }
        r8 = java.io.File.separatorChar;	 Catch:{ all -> 0x0082 }
        r3 = r3.append(r8);	 Catch:{ all -> 0x0082 }
        r2 = r3.append(r2);	 Catch:{ all -> 0x0082 }
        r3 = java.io.File.separatorChar;	 Catch:{ all -> 0x0082 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x0082 }
        r0 = r16;
        r2 = r2.append(r0);	 Catch:{ all -> 0x0082 }
        r3 = r2.toString();	 Catch:{ all -> 0x0082 }
        r2 = r5.getEntry(r3);	 Catch:{ all -> 0x0082 }
        if (r2 == 0) goto L_0x008a;
    L_0x0067:
        r4 = r2;
        r2 = r3;
    L_0x0069:
        if (r2 == 0) goto L_0x0078;
    L_0x006b:
        r3 = "Looking for %s in APK...";
        r7 = 1;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x0082 }
        r8 = 0;
        r7[r8] = r2;	 Catch:{ all -> 0x0082 }
        r0 = r18;
        r0.m54a(r3, r7);	 Catch:{ all -> 0x0082 }
    L_0x0078:
        if (r4 != 0) goto L_0x0095;
    L_0x007a:
        if (r2 == 0) goto L_0x008d;
    L_0x007c:
        r3 = new com.a.a.b;	 Catch:{ all -> 0x0082 }
        r3.<init>(r2);	 Catch:{ all -> 0x0082 }
        throw r3;	 Catch:{ all -> 0x0082 }
    L_0x0082:
        r2 = move-exception;
        r3 = r5;
    L_0x0084:
        if (r3 == 0) goto L_0x0089;
    L_0x0086:
        r3.close();	 Catch:{ IOException -> 0x013a }
    L_0x0089:
        throw r2;
    L_0x008a:
        r4 = r4 + 1;
        goto L_0x0038;
    L_0x008d:
        r2 = new com.a.a.b;	 Catch:{ all -> 0x0082 }
        r0 = r16;
        r2.<init>(r0);	 Catch:{ all -> 0x0082 }
        throw r2;	 Catch:{ all -> 0x0082 }
    L_0x0095:
        r3 = "Found %s! Extracting...";
        r7 = 1;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x0082 }
        r8 = 0;
        r7[r8] = r2;	 Catch:{ all -> 0x0082 }
        r0 = r18;
        r0.m54a(r3, r7);	 Catch:{ all -> 0x0082 }
        r2 = r17.exists();	 Catch:{ IOException -> 0x00b1 }
        if (r2 != 0) goto L_0x00b5;
    L_0x00a8:
        r2 = r17.createNewFile();	 Catch:{ IOException -> 0x00b1 }
        if (r2 != 0) goto L_0x00b5;
    L_0x00ae:
        r2 = r6;
        goto L_0x002b;
    L_0x00b1:
        r2 = move-exception;
        r2 = r6;
        goto L_0x002b;
    L_0x00b5:
        r2 = 0;
        r3 = 0;
        r4 = r5.getInputStream(r4);	 Catch:{ FileNotFoundException -> 0x0102, IOException -> 0x010f, all -> 0x011b }
        r2 = new java.io.FileOutputStream;	 Catch:{ FileNotFoundException -> 0x014c, IOException -> 0x0147, all -> 0x0140 }
        r0 = r17;
        r2.<init>(r0);	 Catch:{ FileNotFoundException -> 0x014c, IOException -> 0x0147, all -> 0x0140 }
        r8 = r13.m34a(r4, r2);	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x014a, all -> 0x0142 }
        r3 = r2.getFD();	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x014a, all -> 0x0142 }
        r3.sync();	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x014a, all -> 0x0142 }
        r10 = r17.length();	 Catch:{ FileNotFoundException -> 0x0150, IOException -> 0x014a, all -> 0x0142 }
        r3 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1));
        if (r3 == 0) goto L_0x00de;
    L_0x00d5:
        r13.m35a(r4);	 Catch:{ all -> 0x0082 }
        r13.m35a(r2);	 Catch:{ all -> 0x0082 }
        r2 = r6;
        goto L_0x002b;
    L_0x00de:
        r13.m35a(r4);	 Catch:{ all -> 0x0082 }
        r13.m35a(r2);	 Catch:{ all -> 0x0082 }
        r2 = 1;
        r3 = 0;
        r0 = r17;
        r0.setReadable(r2, r3);	 Catch:{ all -> 0x0082 }
        r2 = 1;
        r3 = 0;
        r0 = r17;
        r0.setExecutable(r2, r3);	 Catch:{ all -> 0x0082 }
        r2 = 1;
        r0 = r17;
        r0.setWritable(r2);	 Catch:{ all -> 0x0082 }
        if (r5 == 0) goto L_0x0026;
    L_0x00fa:
        r5.close();	 Catch:{ IOException -> 0x00ff }
        goto L_0x0026;
    L_0x00ff:
        r2 = move-exception;
        goto L_0x0026;
    L_0x0102:
        r4 = move-exception;
        r12 = r3;
        r3 = r2;
        r2 = r12;
    L_0x0106:
        r13.m35a(r3);	 Catch:{ all -> 0x0082 }
        r13.m35a(r2);	 Catch:{ all -> 0x0082 }
        r2 = r6;
        goto L_0x002b;
    L_0x010f:
        r4 = move-exception;
        r4 = r2;
        r2 = r3;
    L_0x0112:
        r13.m35a(r4);	 Catch:{ all -> 0x0082 }
        r13.m35a(r2);	 Catch:{ all -> 0x0082 }
        r2 = r6;
        goto L_0x002b;
    L_0x011b:
        r4 = move-exception;
        r12 = r4;
        r4 = r2;
        r2 = r12;
    L_0x011f:
        r13.m35a(r4);	 Catch:{ all -> 0x0082 }
        r13.m35a(r3);	 Catch:{ all -> 0x0082 }
        throw r2;	 Catch:{ all -> 0x0082 }
    L_0x0126:
        r2 = "FATAL! Couldn't extract the library from the APK!";
        r0 = r18;
        r0.m53a(r2);	 Catch:{ all -> 0x0082 }
        if (r5 == 0) goto L_0x0026;
    L_0x012f:
        r5.close();	 Catch:{ IOException -> 0x0134 }
        goto L_0x0026;
    L_0x0134:
        r2 = move-exception;
        goto L_0x0026;
    L_0x0137:
        r2 = move-exception;
        goto L_0x0026;
    L_0x013a:
        r3 = move-exception;
        goto L_0x0089;
    L_0x013d:
        r2 = move-exception;
        goto L_0x0084;
    L_0x0140:
        r2 = move-exception;
        goto L_0x011f;
    L_0x0142:
        r3 = move-exception;
        r12 = r3;
        r3 = r2;
        r2 = r12;
        goto L_0x011f;
    L_0x0147:
        r2 = move-exception;
        r2 = r3;
        goto L_0x0112;
    L_0x014a:
        r3 = move-exception;
        goto L_0x0112;
    L_0x014c:
        r2 = move-exception;
        r2 = r3;
        r3 = r4;
        goto L_0x0106;
    L_0x0150:
        r3 = move-exception;
        r3 = r4;
        goto L_0x0106;
    L_0x0153:
        r4 = r2;
        r2 = r3;
        goto L_0x0069;
    L_0x0157:
        r5 = r3;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.a.a.a.a(android.content.Context, java.lang.String[], java.lang.String, java.io.File, com.a.a.d):void");
    }
}
