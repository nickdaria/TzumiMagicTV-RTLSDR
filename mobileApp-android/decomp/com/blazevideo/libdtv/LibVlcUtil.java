package com.blazevideo.libdtv;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.media.TransportMediator;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LibVlcUtil {
    private static String[] CPU_archs = new String[]{"*Pre-v4", "*v4", "*v4T", "v5T", "v5TE", "v5TEJ", "v6", "v6KZ", "v6T2", "v6K", "v7", "*v6-M", "*v6S-M", "*v7E-M", "*v8"};
    private static final int ELF_HEADER_SIZE = 52;
    private static final int EM_386 = 3;
    private static final int EM_AARCH64 = 183;
    private static final int EM_ARM = 40;
    private static final int EM_MIPS = 8;
    private static final int EM_X86_64 = 62;
    private static final int SECTION_HEADER_SIZE = 40;
    private static final int SHT_ARM_ATTRIBUTES = 1879048195;
    public static final String TAG = "VLC/LibVLC/Util";
    private static String errorMsg = null;
    private static boolean isCompatible = false;
    private static MachineSpecs machineSpecs = null;

    private static class ElfData {
        String att_arch;
        boolean att_fpu;
        int e_machine;
        int e_shnum;
        int e_shoff;
        boolean is64bits;
        ByteOrder order;
        int sh_offset;
        int sh_size;

        private ElfData() {
        }
    }

    public static class MachineSpecs {
        public float bogoMIPS;
        public float frequency;
        public boolean hasArmV6;
        public boolean hasArmV7;
        public boolean hasFpu;
        public boolean hasMips;
        public boolean hasNeon;
        public boolean hasX86;
        public boolean is64bits;
        public int processors;
    }

    public static File URItoFile(String str) {
        return str == null ? null : new File(Uri.decode(str).replace("file://", ""));
    }

    public static String URItoFileName(String str) {
        return str == null ? null : URItoFile(str).getName();
    }

    public static String getErrorMsg() {
        return errorMsg;
    }

    public static MachineSpecs getMachineSpecs() {
        return machineSpecs;
    }

    private static String getString(ByteBuffer byteBuffer) {
        StringBuilder stringBuilder = new StringBuilder(byteBuffer.limit());
        while (byteBuffer.remaining() > 0) {
            char c = (char) byteBuffer.get();
            if (c == '\u0000') {
                break;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static int getUleb128(ByteBuffer byteBuffer) {
        int i = 0;
        byte b;
        do {
            i <<= 7;
            b = byteBuffer.get();
            i |= b & TransportMediator.KEYCODE_MEDIA_PAUSE;
        } while ((b & 128) > 0);
        return i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean hasCompatibleCPU(android.content.Context r21) {
        /*
        r1 = errorMsg;
        if (r1 != 0) goto L_0x0008;
    L_0x0004:
        r1 = isCompatible;
        if (r1 == 0) goto L_0x000b;
    L_0x0008:
        r1 = isCompatible;
    L_0x000a:
        return r1;
    L_0x000b:
        r1 = searchLibrary(r21);
        if (r1 != 0) goto L_0x0013;
    L_0x0011:
        r1 = 1;
        goto L_0x000a;
    L_0x0013:
        r17 = readLib(r1);
        if (r17 != 0) goto L_0x0029;
    L_0x0019:
        r1 = "VLC/LibVLC/Util";
        r2 = "WARNING: Unable to read libvlcjni.so; cannot check device ABI!";
        android.util.Log.e(r1, r2);
        r1 = "VLC/LibVLC/Util";
        r2 = "WARNING: Cannot guarantee correct ABI for this build (may crash)!";
        android.util.Log.e(r1, r2);
        r1 = 1;
        goto L_0x000a;
    L_0x0029:
        r10 = android.os.Build.CPU_ABI;
        r2 = "none";
        r1 = android.os.Build.VERSION.SDK_INT;
        r3 = 8;
        if (r1 < r3) goto L_0x046e;
    L_0x0033:
        r1 = android.os.Build.class;
        r3 = "CPU_ABI2";
        r1 = r1.getDeclaredField(r3);	 Catch:{ Exception -> 0x01e1 }
        r3 = 0;
        r1 = r1.get(r3);	 Catch:{ Exception -> 0x01e1 }
        r1 = (java.lang.String) r1;	 Catch:{ Exception -> 0x01e1 }
    L_0x0042:
        r0 = r17;
        r2 = r0.e_machine;
        r3 = 3;
        if (r2 == r3) goto L_0x0051;
    L_0x0049:
        r0 = r17;
        r2 = r0.e_machine;
        r3 = 62;
        if (r2 != r3) goto L_0x01e5;
    L_0x0051:
        r2 = 1;
    L_0x0052:
        r0 = r17;
        r3 = r0.e_machine;
        r4 = 40;
        if (r3 == r4) goto L_0x0062;
    L_0x005a:
        r0 = r17;
        r3 = r0.e_machine;
        r4 = 183; // 0xb7 float:2.56E-43 double:9.04E-322;
        if (r3 != r4) goto L_0x01e8;
    L_0x0062:
        r3 = 1;
    L_0x0063:
        r0 = r17;
        r4 = r0.e_machine;
        r5 = 8;
        if (r4 != r5) goto L_0x01eb;
    L_0x006b:
        r4 = 1;
    L_0x006c:
        r0 = r17;
        r0 = r0.is64bits;
        r18 = r0;
        r6 = "VLC/LibVLC/Util";
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r7 = "ELF ABI = ";
        r7 = r5.append(r7);
        if (r3 == 0) goto L_0x01ee;
    L_0x0081:
        r5 = "arm";
    L_0x0083:
        r5 = r7.append(r5);
        r7 = ", ";
        r7 = r5.append(r7);
        if (r18 == 0) goto L_0x01f8;
    L_0x008f:
        r5 = "64bits";
    L_0x0091:
        r5 = r7.append(r5);
        r5 = r5.toString();
        android.util.Log.i(r6, r5);
        r5 = "VLC/LibVLC/Util";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "ELF arch = ";
        r6 = r6.append(r7);
        r0 = r17;
        r7 = r0.att_arch;
        r6 = r6.append(r7);
        r6 = r6.toString();
        android.util.Log.i(r5, r6);
        r5 = "VLC/LibVLC/Util";
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "ELF fpu = ";
        r6 = r6.append(r7);
        r0 = r17;
        r7 = r0.att_fpu;
        r6 = r6.append(r7);
        r6 = r6.toString();
        android.util.Log.i(r5, r6);
        r9 = 0;
        r15 = 0;
        r8 = 0;
        r7 = 0;
        r14 = 0;
        r6 = 0;
        r5 = 0;
        r13 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r12 = 0;
        r11 = "x86";
        r11 = r10.equals(r11);
        if (r11 != 0) goto L_0x00ee;
    L_0x00e6:
        r11 = "x86";
        r11 = r1.equals(r11);
        if (r11 == 0) goto L_0x01fc;
    L_0x00ee:
        r1 = 1;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        r20 = r1;
        r1 = r5;
        r5 = r20;
    L_0x00f7:
        r9 = 0;
        r10 = 0;
        r11 = new java.io.FileReader;	 Catch:{ IOException -> 0x0285, all -> 0x02a2 }
        r16 = "/proc/cpuinfo";
        r0 = r16;
        r11.<init>(r0);	 Catch:{ IOException -> 0x0285, all -> 0x02a2 }
        r9 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x043a, all -> 0x042e }
        r9.<init>(r11);	 Catch:{ IOException -> 0x043a, all -> 0x042e }
        r16 = r13;
        r10 = r8;
        r13 = r5;
        r8 = r15;
        r15 = r7;
        r5 = r12;
        r12 = r14;
        r14 = r6;
    L_0x0110:
        r19 = r9.readLine();	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r19 == 0) goto L_0x0269;
    L_0x0116:
        if (r14 != 0) goto L_0x0463;
    L_0x0118:
        r6 = "AArch64";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x0463;
    L_0x0122:
        r6 = 1;
        r7 = 1;
    L_0x0124:
        if (r6 != 0) goto L_0x045f;
    L_0x0126:
        r14 = "ARMv7";
        r0 = r19;
        r14 = r0.contains(r14);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r14 == 0) goto L_0x045f;
    L_0x0130:
        r6 = 1;
        r7 = 1;
        r15 = r6;
        r6 = r7;
    L_0x0134:
        if (r15 != 0) goto L_0x045c;
    L_0x0136:
        if (r6 != 0) goto L_0x045c;
    L_0x0138:
        r7 = "ARMv6";
        r0 = r19;
        r7 = r0.contains(r7);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r7 == 0) goto L_0x045c;
    L_0x0142:
        r6 = 1;
        r14 = r6;
    L_0x0144:
        r6 = "clflush size";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x0459;
    L_0x014e:
        r6 = 1;
    L_0x014f:
        r7 = "GenuineIntel";
        r0 = r19;
        r7 = r0.contains(r7);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r7 == 0) goto L_0x0456;
    L_0x0159:
        r6 = 1;
        r13 = r6;
    L_0x015b:
        r6 = "microsecond timers";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x0167;
    L_0x0165:
        r6 = 1;
        r12 = r6;
    L_0x0167:
        if (r10 != 0) goto L_0x017f;
    L_0x0169:
        r6 = "neon";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 != 0) goto L_0x017d;
    L_0x0173:
        r6 = "asimd";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x017f;
    L_0x017d:
        r6 = 1;
        r10 = r6;
    L_0x017f:
        if (r8 != 0) goto L_0x0453;
    L_0x0181:
        r6 = "vfp";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 != 0) goto L_0x019f;
    L_0x018b:
        r6 = "Features";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x0453;
    L_0x0195:
        r6 = "fp";
        r0 = r19;
        r6 = r0.contains(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x0453;
    L_0x019f:
        r6 = 1;
        r7 = r6;
    L_0x01a1:
        r6 = "processor";
        r0 = r19;
        r6 = r0.startsWith(r6);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r6 == 0) goto L_0x0450;
    L_0x01ab:
        r5 = r5 + 1;
        r6 = r5;
    L_0x01ae:
        r5 = 0;
        r5 = (r16 > r5 ? 1 : (r16 == r5 ? 0 : -1));
        if (r5 >= 0) goto L_0x0447;
    L_0x01b3:
        r5 = java.util.Locale.ENGLISH;	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        r0 = r19;
        r5 = r0.toLowerCase(r5);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        r8 = "bogomips";
        r5 = r5.contains(r8);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        if (r5 == 0) goto L_0x0447;
    L_0x01c3:
        r5 = ":";
        r0 = r19;
        r5 = r0.split(r5);	 Catch:{ IOException -> 0x043f, all -> 0x0432 }
        r8 = 1;
        r5 = r5[r8];	 Catch:{ NumberFormatException -> 0x0264 }
        r5 = r5.trim();	 Catch:{ NumberFormatException -> 0x0264 }
        r5 = java.lang.Float.parseFloat(r5);	 Catch:{ NumberFormatException -> 0x0264 }
    L_0x01d6:
        r16 = r5;
        r8 = r7;
        r5 = r6;
        r20 = r14;
        r14 = r15;
        r15 = r20;
        goto L_0x0110;
    L_0x01e1:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0042;
    L_0x01e5:
        r2 = 0;
        goto L_0x0052;
    L_0x01e8:
        r3 = 0;
        goto L_0x0063;
    L_0x01eb:
        r4 = 0;
        goto L_0x006c;
    L_0x01ee:
        if (r2 == 0) goto L_0x01f4;
    L_0x01f0:
        r5 = "x86";
        goto L_0x0083;
    L_0x01f4:
        r5 = "mips";
        goto L_0x0083;
    L_0x01f8:
        r5 = "32bits";
        goto L_0x0091;
    L_0x01fc:
        r11 = "x86_64";
        r11 = r10.equals(r11);
        if (r11 != 0) goto L_0x020c;
    L_0x0204:
        r11 = "x86_64";
        r11 = r1.equals(r11);
        if (r11 == 0) goto L_0x0213;
    L_0x020c:
        r5 = 1;
        r1 = 1;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        goto L_0x00f7;
    L_0x0213:
        r11 = "armeabi-v7a";
        r11 = r10.equals(r11);
        if (r11 != 0) goto L_0x0223;
    L_0x021b:
        r11 = "armeabi-v7a";
        r11 = r1.equals(r11);
        if (r11 == 0) goto L_0x022e;
    L_0x0223:
        r1 = 1;
        r7 = 1;
        r8 = r9;
        r20 = r6;
        r6 = r1;
        r1 = r5;
        r5 = r20;
        goto L_0x00f7;
    L_0x022e:
        r11 = "armeabi";
        r11 = r10.equals(r11);
        if (r11 != 0) goto L_0x023e;
    L_0x0236:
        r11 = "armeabi";
        r11 = r1.equals(r11);
        if (r11 == 0) goto L_0x0249;
    L_0x023e:
        r1 = 1;
        r8 = r9;
        r20 = r6;
        r6 = r7;
        r7 = r1;
        r1 = r5;
        r5 = r20;
        goto L_0x00f7;
    L_0x0249:
        r11 = "arm64-v8a";
        r10 = r10.equals(r11);
        if (r10 != 0) goto L_0x0259;
    L_0x0251:
        r10 = "arm64-v8a";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x0467;
    L_0x0259:
        r8 = 1;
        r7 = 1;
        r5 = 1;
        r1 = 1;
        r20 = r6;
        r6 = r5;
        r5 = r20;
        goto L_0x00f7;
    L_0x0264:
        r5 = move-exception;
        r5 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        goto L_0x01d6;
    L_0x0269:
        if (r9 == 0) goto L_0x026e;
    L_0x026b:
        r9.close();	 Catch:{ IOException -> 0x03f1 }
    L_0x026e:
        if (r11 == 0) goto L_0x0273;
    L_0x0270:
        r11.close();	 Catch:{ IOException -> 0x03f4 }
    L_0x0273:
        if (r5 != 0) goto L_0x0444;
    L_0x0275:
        r5 = 1;
        r7 = r5;
    L_0x0277:
        if (r2 == 0) goto L_0x02b0;
    L_0x0279:
        if (r13 != 0) goto L_0x02b0;
    L_0x027b:
        r1 = "x86 build on non-x86 device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x0285:
        r1 = move-exception;
        r2 = r10;
        r3 = r9;
    L_0x0288:
        r1.printStackTrace();	 Catch:{ all -> 0x0435 }
        r1 = "IOException whilst reading cpuinfo flags";
        errorMsg = r1;	 Catch:{ all -> 0x0435 }
        r1 = 0;
        isCompatible = r1;	 Catch:{ all -> 0x0435 }
        r1 = 0;
        if (r2 == 0) goto L_0x0298;
    L_0x0295:
        r2.close();	 Catch:{ IOException -> 0x03f7 }
    L_0x0298:
        if (r3 == 0) goto L_0x000a;
    L_0x029a:
        r3.close();	 Catch:{ IOException -> 0x029f }
        goto L_0x000a;
    L_0x029f:
        r2 = move-exception;
        goto L_0x000a;
    L_0x02a2:
        r1 = move-exception;
        r11 = r9;
        r9 = r10;
    L_0x02a5:
        if (r9 == 0) goto L_0x02aa;
    L_0x02a7:
        r9.close();	 Catch:{ IOException -> 0x03fa }
    L_0x02aa:
        if (r11 == 0) goto L_0x02af;
    L_0x02ac:
        r11.close();	 Catch:{ IOException -> 0x03fd }
    L_0x02af:
        throw r1;
    L_0x02b0:
        if (r3 == 0) goto L_0x02be;
    L_0x02b2:
        if (r13 == 0) goto L_0x02be;
    L_0x02b4:
        r1 = "ARM build on x86 device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x02be:
        if (r4 == 0) goto L_0x02cc;
    L_0x02c0:
        if (r12 != 0) goto L_0x02cc;
    L_0x02c2:
        r1 = "MIPS build on non-MIPS device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x02cc:
        if (r3 == 0) goto L_0x02da;
    L_0x02ce:
        if (r12 == 0) goto L_0x02da;
    L_0x02d0:
        r1 = "ARM build on MIPS device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x02da:
        r0 = r17;
        r2 = r0.e_machine;
        r3 = 40;
        if (r2 != r3) goto L_0x02fa;
    L_0x02e2:
        r0 = r17;
        r2 = r0.att_arch;
        r3 = "v7";
        r2 = r2.startsWith(r3);
        if (r2 == 0) goto L_0x02fa;
    L_0x02ee:
        if (r14 != 0) goto L_0x02fa;
    L_0x02f0:
        r1 = "ARMv7 build on non-ARMv7 device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x02fa:
        r0 = r17;
        r2 = r0.e_machine;
        r3 = 40;
        if (r2 != r3) goto L_0x032c;
    L_0x0302:
        r0 = r17;
        r2 = r0.att_arch;
        r3 = "v6";
        r2 = r2.startsWith(r3);
        if (r2 == 0) goto L_0x031a;
    L_0x030e:
        if (r15 != 0) goto L_0x031a;
    L_0x0310:
        r1 = "ARMv6 build on non-ARMv6 device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x031a:
        r0 = r17;
        r2 = r0.att_fpu;
        if (r2 == 0) goto L_0x032c;
    L_0x0320:
        if (r8 != 0) goto L_0x032c;
    L_0x0322:
        r1 = "FPU-enabled build on non-FPU device";
        errorMsg = r1;
        r1 = 0;
        isCompatible = r1;
        r1 = 0;
        goto L_0x000a;
    L_0x032c:
        if (r18 == 0) goto L_0x0337;
    L_0x032e:
        if (r1 != 0) goto L_0x0337;
    L_0x0330:
        r2 = "64bits build on 32bits device";
        errorMsg = r2;
        r2 = 0;
        isCompatible = r2;
    L_0x0337:
        r2 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r3 = 0;
        r4 = 0;
        r6 = "";
        r5 = new java.io.FileReader;	 Catch:{ IOException -> 0x039a, NumberFormatException -> 0x03b4, all -> 0x03e4 }
        r9 = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
        r5.<init>(r9);	 Catch:{ IOException -> 0x039a, NumberFormatException -> 0x03b4, all -> 0x03e4 }
        r3 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0425, NumberFormatException -> 0x0417 }
        r3.<init>(r5);	 Catch:{ IOException -> 0x0425, NumberFormatException -> 0x0417 }
        r4 = r3.readLine();	 Catch:{ IOException -> 0x042a, NumberFormatException -> 0x041a, all -> 0x0410 }
        if (r4 == 0) goto L_0x0356;
    L_0x034f:
        r2 = java.lang.Float.parseFloat(r4);	 Catch:{ IOException -> 0x042a, NumberFormatException -> 0x041e, all -> 0x0410 }
        r4 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r2 = r2 / r4;
    L_0x0356:
        if (r3 == 0) goto L_0x035b;
    L_0x0358:
        r3.close();	 Catch:{ IOException -> 0x0400 }
    L_0x035b:
        if (r5 == 0) goto L_0x0360;
    L_0x035d:
        r5.close();	 Catch:{ IOException -> 0x0403 }
    L_0x0360:
        r3 = 0;
        errorMsg = r3;
        r3 = 1;
        isCompatible = r3;
        r3 = new com.blazevideo.libdtv.LibVlcUtil$MachineSpecs;
        r3.<init>();
        machineSpecs = r3;
        r3 = machineSpecs;
        r3.hasArmV6 = r15;
        r3 = machineSpecs;
        r3.hasArmV7 = r14;
        r3 = machineSpecs;
        r3.hasFpu = r8;
        r3 = machineSpecs;
        r3.hasMips = r12;
        r3 = machineSpecs;
        r3.hasNeon = r10;
        r3 = machineSpecs;
        r3.hasX86 = r13;
        r3 = machineSpecs;
        r3.is64bits = r1;
        r1 = machineSpecs;
        r0 = r16;
        r1.bogoMIPS = r0;
        r1 = machineSpecs;
        r1.processors = r7;
        r1 = machineSpecs;
        r1.frequency = r2;
        r1 = 1;
        goto L_0x000a;
    L_0x039a:
        r5 = move-exception;
        r20 = r4;
        r4 = r3;
        r3 = r20;
    L_0x03a0:
        r5 = "VLC/LibVLC/Util";
        r6 = "Could not find maximum CPU frequency!";
        android.util.Log.w(r5, r6);	 Catch:{ all -> 0x0413 }
        if (r3 == 0) goto L_0x03ac;
    L_0x03a9:
        r3.close();	 Catch:{ IOException -> 0x0406 }
    L_0x03ac:
        if (r4 == 0) goto L_0x0360;
    L_0x03ae:
        r4.close();	 Catch:{ IOException -> 0x03b2 }
        goto L_0x0360;
    L_0x03b2:
        r3 = move-exception;
        goto L_0x0360;
    L_0x03b4:
        r5 = move-exception;
        r5 = r3;
        r3 = r6;
    L_0x03b7:
        r6 = "VLC/LibVLC/Util";
        r9 = "Could not parse maximum CPU frequency!";
        android.util.Log.w(r6, r9);	 Catch:{ all -> 0x040e }
        r6 = "VLC/LibVLC/Util";
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x040e }
        r9.<init>();	 Catch:{ all -> 0x040e }
        r11 = "Failed to parse: ";
        r9 = r9.append(r11);	 Catch:{ all -> 0x040e }
        r3 = r9.append(r3);	 Catch:{ all -> 0x040e }
        r3 = r3.toString();	 Catch:{ all -> 0x040e }
        android.util.Log.w(r6, r3);	 Catch:{ all -> 0x040e }
        if (r4 == 0) goto L_0x03db;
    L_0x03d8:
        r4.close();	 Catch:{ IOException -> 0x0408 }
    L_0x03db:
        if (r5 == 0) goto L_0x0360;
    L_0x03dd:
        r5.close();	 Catch:{ IOException -> 0x03e1 }
        goto L_0x0360;
    L_0x03e1:
        r3 = move-exception;
        goto L_0x0360;
    L_0x03e4:
        r1 = move-exception;
        r5 = r3;
    L_0x03e6:
        if (r4 == 0) goto L_0x03eb;
    L_0x03e8:
        r4.close();	 Catch:{ IOException -> 0x040a }
    L_0x03eb:
        if (r5 == 0) goto L_0x03f0;
    L_0x03ed:
        r5.close();	 Catch:{ IOException -> 0x040c }
    L_0x03f0:
        throw r1;
    L_0x03f1:
        r6 = move-exception;
        goto L_0x026e;
    L_0x03f4:
        r6 = move-exception;
        goto L_0x0273;
    L_0x03f7:
        r2 = move-exception;
        goto L_0x0298;
    L_0x03fa:
        r2 = move-exception;
        goto L_0x02aa;
    L_0x03fd:
        r2 = move-exception;
        goto L_0x02af;
    L_0x0400:
        r3 = move-exception;
        goto L_0x035b;
    L_0x0403:
        r3 = move-exception;
        goto L_0x0360;
    L_0x0406:
        r3 = move-exception;
        goto L_0x03ac;
    L_0x0408:
        r3 = move-exception;
        goto L_0x03db;
    L_0x040a:
        r2 = move-exception;
        goto L_0x03eb;
    L_0x040c:
        r2 = move-exception;
        goto L_0x03f0;
    L_0x040e:
        r1 = move-exception;
        goto L_0x03e6;
    L_0x0410:
        r1 = move-exception;
        r4 = r3;
        goto L_0x03e6;
    L_0x0413:
        r1 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x03e6;
    L_0x0417:
        r3 = move-exception;
        r3 = r6;
        goto L_0x03b7;
    L_0x041a:
        r4 = move-exception;
        r4 = r3;
        r3 = r6;
        goto L_0x03b7;
    L_0x041e:
        r6 = move-exception;
        r20 = r4;
        r4 = r3;
        r3 = r20;
        goto L_0x03b7;
    L_0x0425:
        r3 = move-exception;
        r3 = r4;
        r4 = r5;
        goto L_0x03a0;
    L_0x042a:
        r4 = move-exception;
        r4 = r5;
        goto L_0x03a0;
    L_0x042e:
        r1 = move-exception;
        r9 = r10;
        goto L_0x02a5;
    L_0x0432:
        r1 = move-exception;
        goto L_0x02a5;
    L_0x0435:
        r1 = move-exception;
        r9 = r2;
        r11 = r3;
        goto L_0x02a5;
    L_0x043a:
        r1 = move-exception;
        r2 = r10;
        r3 = r11;
        goto L_0x0288;
    L_0x043f:
        r1 = move-exception;
        r2 = r9;
        r3 = r11;
        goto L_0x0288;
    L_0x0444:
        r7 = r5;
        goto L_0x0277;
    L_0x0447:
        r5 = r6;
        r8 = r7;
        r20 = r14;
        r14 = r15;
        r15 = r20;
        goto L_0x0110;
    L_0x0450:
        r6 = r5;
        goto L_0x01ae;
    L_0x0453:
        r7 = r8;
        goto L_0x01a1;
    L_0x0456:
        r13 = r6;
        goto L_0x015b;
    L_0x0459:
        r6 = r13;
        goto L_0x014f;
    L_0x045c:
        r14 = r6;
        goto L_0x0144;
    L_0x045f:
        r15 = r6;
        r6 = r7;
        goto L_0x0134;
    L_0x0463:
        r6 = r14;
        r7 = r15;
        goto L_0x0124;
    L_0x0467:
        r1 = r5;
        r5 = r6;
        r6 = r7;
        r7 = r8;
        r8 = r9;
        goto L_0x00f7;
    L_0x046e:
        r1 = r2;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blazevideo.libdtv.LibVlcUtil.hasCompatibleCPU(android.content.Context):boolean");
    }

    public static boolean isFroyoOrLater() {
        return VERSION.SDK_INT >= 8;
    }

    public static boolean isGingerbreadOrLater() {
        return VERSION.SDK_INT >= 9;
    }

    public static boolean isHoneycombOrLater() {
        return VERSION.SDK_INT >= 11;
    }

    public static boolean isICSOrLater() {
        return VERSION.SDK_INT >= 14;
    }

    public static boolean isJellyBeanMR1OrLater() {
        return VERSION.SDK_INT >= 17;
    }

    public static boolean isJellyBeanMR2OrLater() {
        return VERSION.SDK_INT >= 18;
    }

    public static boolean isJellyBeanOrLater() {
        return VERSION.SDK_INT >= 16;
    }

    public static boolean isKitKatOrLater() {
        return VERSION.SDK_INT >= 19;
    }

    public static boolean isLolliPopOrLater() {
        return VERSION.SDK_INT >= 21;
    }

    private static boolean readArmAttributes(RandomAccessFile randomAccessFile, ElfData elfData) throws IOException {
        byte[] bArr = new byte[elfData.sh_size];
        randomAccessFile.seek((long) elfData.sh_offset);
        randomAccessFile.readFully(bArr);
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(elfData.order);
        if (wrap.get() != (byte) 65) {
            return false;
        }
        while (wrap.remaining() > 0) {
            int position = wrap.position();
            int i = wrap.getInt();
            if (getString(wrap).equals("aeabi")) {
                while (wrap.position() < position + i) {
                    int position2 = wrap.position();
                    byte b = wrap.get();
                    int i2 = wrap.getInt();
                    if (b != (byte) 1) {
                        wrap.position(position2 + i2);
                    } else {
                        while (wrap.position() < position2 + i2) {
                            int uleb128 = getUleb128(wrap);
                            if (uleb128 == 6) {
                                elfData.att_arch = CPU_archs[getUleb128(wrap)];
                            } else if (uleb128 == 27) {
                                getUleb128(wrap);
                                elfData.att_fpu = true;
                            } else {
                                uleb128 %= 128;
                                if (uleb128 == 4 || uleb128 == 5 || uleb128 == 32 || (uleb128 > 32 && (uleb128 & 1) != 0)) {
                                    getString(wrap);
                                } else {
                                    getUleb128(wrap);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
        return true;
    }

    private static boolean readHeader(RandomAccessFile randomAccessFile, ElfData elfData) throws IOException {
        boolean z = false;
        byte[] bArr = new byte[52];
        randomAccessFile.readFully(bArr);
        if (bArr[0] == Byte.MAX_VALUE && bArr[1] == (byte) 69 && bArr[2] == (byte) 76 && bArr[3] == (byte) 70 && (bArr[4] == (byte) 1 || bArr[4] == (byte) 2)) {
            if (bArr[4] == (byte) 2) {
                z = true;
            }
            elfData.is64bits = z;
            elfData.order = bArr[5] == (byte) 1 ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.order(elfData.order);
            elfData.e_machine = wrap.getShort(18);
            elfData.e_shoff = wrap.getInt(32);
            elfData.e_shnum = wrap.getShort(48);
            return true;
        }
        Log.e(TAG, "ELF header invalid");
        return false;
    }

    private static ElfData readLib(File file) {
        RandomAccessFile randomAccessFile;
        FileNotFoundException e;
        Throwable th;
        IOException e2;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            try {
                ElfData elfData = new ElfData();
                if (readHeader(randomAccessFile, elfData)) {
                    switch (elfData.e_machine) {
                        case 3:
                        case 8:
                        case 62:
                        case EM_AARCH64 /*183*/:
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e3) {
                                }
                            }
                            return elfData;
                        case 40:
                            randomAccessFile.close();
                            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                            try {
                                if (readSection(randomAccessFile2, elfData)) {
                                    randomAccessFile2.close();
                                    randomAccessFile = new RandomAccessFile(file, "r");
                                    if (readArmAttributes(randomAccessFile, elfData)) {
                                        if (randomAccessFile != null) {
                                            try {
                                                randomAccessFile.close();
                                            } catch (IOException e4) {
                                            }
                                        }
                                        return elfData;
                                    } else if (randomAccessFile == null) {
                                        return null;
                                    } else {
                                        try {
                                            randomAccessFile.close();
                                            return null;
                                        } catch (IOException e5) {
                                            return null;
                                        }
                                    }
                                } else if (randomAccessFile2 == null) {
                                    return null;
                                } else {
                                    try {
                                        randomAccessFile2.close();
                                        return null;
                                    } catch (IOException e6) {
                                        return null;
                                    }
                                }
                            } catch (FileNotFoundException e7) {
                                e = e7;
                                randomAccessFile = randomAccessFile2;
                                try {
                                    e.printStackTrace();
                                    if (randomAccessFile != null) {
                                        return null;
                                    }
                                    try {
                                        randomAccessFile.close();
                                        return null;
                                    } catch (IOException e8) {
                                        return null;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (randomAccessFile != null) {
                                        try {
                                            randomAccessFile.close();
                                        } catch (IOException e9) {
                                        }
                                    }
                                    throw th;
                                }
                            } catch (IOException e10) {
                                e2 = e10;
                                randomAccessFile = randomAccessFile2;
                                e2.printStackTrace();
                                if (randomAccessFile != null) {
                                    return null;
                                }
                                try {
                                    randomAccessFile.close();
                                    return null;
                                } catch (IOException e11) {
                                    return null;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                randomAccessFile = randomAccessFile2;
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }
                                throw th;
                            }
                        default:
                            if (randomAccessFile == null) {
                                return null;
                            }
                            try {
                                randomAccessFile.close();
                                return null;
                            } catch (IOException e12) {
                                return null;
                            }
                    }
                } else if (randomAccessFile == null) {
                    return null;
                } else {
                    try {
                        randomAccessFile.close();
                        return null;
                    } catch (IOException e13) {
                        return null;
                    }
                }
            } catch (FileNotFoundException e14) {
                e = e14;
                e.printStackTrace();
                if (randomAccessFile != null) {
                    return null;
                }
                randomAccessFile.close();
                return null;
            } catch (IOException e15) {
                e2 = e15;
                e2.printStackTrace();
                if (randomAccessFile != null) {
                    return null;
                }
                randomAccessFile.close();
                return null;
            }
        } catch (FileNotFoundException e16) {
            e = e16;
            randomAccessFile = null;
            e.printStackTrace();
            if (randomAccessFile != null) {
                return null;
            }
            randomAccessFile.close();
            return null;
        } catch (IOException e17) {
            e2 = e17;
            randomAccessFile = null;
            e2.printStackTrace();
            if (randomAccessFile != null) {
                return null;
            }
            randomAccessFile.close();
            return null;
        } catch (Throwable th4) {
            randomAccessFile = null;
            th = th4;
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            throw th;
        }
    }

    private static boolean readSection(RandomAccessFile randomAccessFile, ElfData elfData) throws IOException {
        byte[] bArr = new byte[40];
        randomAccessFile.seek((long) elfData.e_shoff);
        int i = 0;
        while (i < elfData.e_shnum) {
            randomAccessFile.readFully(bArr);
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            wrap.order(elfData.order);
            if (wrap.getInt(4) != SHT_ARM_ATTRIBUTES) {
                i++;
            } else {
                elfData.sh_offset = wrap.getInt(16);
                elfData.sh_size = wrap.getInt(20);
                return true;
            }
        }
        return false;
    }

    private static File searchLibrary(Context context) {
        String[] split;
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        if ((applicationInfo.flags & 1) != 0) {
            split = System.getProperty("java.library.path").split(":");
        } else {
            String[] strArr = new String[1];
            if (isGingerbreadOrLater()) {
                strArr[0] = applicationInfo.nativeLibraryDir;
                split = strArr;
            } else {
                strArr[0] = applicationInfo.dataDir + "/lib";
                split = strArr;
            }
        }
        if (split == null) {
            Log.e(TAG, "can't find library path");
            return null;
        }
        for (String file : split) {
            File file2 = new File(file, "libvlcjni.so");
            if (file2.exists() && file2.canRead()) {
                return file2;
            }
        }
        Log.e(TAG, "WARNING: Can't find shared library");
        return null;
    }
}
