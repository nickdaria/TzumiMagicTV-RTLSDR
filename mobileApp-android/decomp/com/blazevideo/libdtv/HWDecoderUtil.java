package com.blazevideo.libdtv;

import java.util.HashMap;

public class HWDecoderUtil {
    private static final AudioOutputBySOC[] sAudioOutputBySOCList = new AudioOutputBySOC[]{new AudioOutputBySOC("ro.product.brand", "Amazon", AudioOutput.OPENSLES)};
    private static final DecoderBySOC[] sDecoderBySOCList = new DecoderBySOC[]{new DecoderBySOC("ro.product.brand", "SEMC", Decoder.NONE), new DecoderBySOC("ro.board.platform", "msm7627", Decoder.NONE), new DecoderBySOC("ro.board.platform", "omap3", Decoder.OMX), new DecoderBySOC("ro.board.platform", "rockchip", Decoder.OMX), new DecoderBySOC("ro.board.platform", "rk29", Decoder.OMX), new DecoderBySOC("ro.board.platform", "msm7630", Decoder.OMX), new DecoderBySOC("ro.board.platform", "s5pc", Decoder.OMX), new DecoderBySOC("ro.board.platform", "montblanc", Decoder.OMX), new DecoderBySOC("ro.board.platform", "exdroid", Decoder.OMX), new DecoderBySOC("ro.board.platform", "sun6i", Decoder.OMX), new DecoderBySOC("ro.board.platform", "exynos4", Decoder.MEDIACODEC), new DecoderBySOC("ro.board.platform", "omap4", Decoder.ALL), new DecoderBySOC("ro.board.platform", "tegra", Decoder.ALL), new DecoderBySOC("ro.board.platform", "tegra3", Decoder.ALL), new DecoderBySOC("ro.board.platform", "msm8660", Decoder.ALL), new DecoderBySOC("ro.board.platform", "exynos5", Decoder.ALL), new DecoderBySOC("ro.board.platform", "rk30", Decoder.ALL), new DecoderBySOC("ro.board.platform", "rk31", Decoder.ALL), new DecoderBySOC("ro.board.platform", "mv88de3100", Decoder.ALL), new DecoderBySOC("ro.hardware", "mt65", Decoder.ALL), new DecoderBySOC("ro.hardware", "mt83", Decoder.ALL)};
    private static final HashMap<String, String> sSystemPropertyMap = new HashMap();

    public enum AudioOutput {
        OPENSLES,
        AUDIOTRACK,
        ALL
    }

    private static class AudioOutputBySOC {
        public final AudioOutput aout;
        public final String key;
        public final String value;

        public AudioOutputBySOC(String str, String str2, AudioOutput audioOutput) {
            this.key = str;
            this.value = str2;
            this.aout = audioOutput;
        }
    }

    public enum Decoder {
        UNKNOWN,
        NONE,
        OMX,
        MEDIACODEC,
        ALL
    }

    private static class DecoderBySOC {
        public final Decoder dec;
        public final String key;
        public final String value;

        public DecoderBySOC(String str, String str2, Decoder decoder) {
            this.key = str;
            this.value = str2;
            this.dec = decoder;
        }
    }

    public static AudioOutput getAudioOutputFromDevice() {
        if (!LibVlcUtil.isGingerbreadOrLater()) {
            return AudioOutput.AUDIOTRACK;
        }
        for (AudioOutputBySOC audioOutputBySOC : sAudioOutputBySOCList) {
            String systemPropertyCached = getSystemPropertyCached(audioOutputBySOC.key);
            if (systemPropertyCached != null && systemPropertyCached.contains(audioOutputBySOC.value)) {
                return audioOutputBySOC.aout;
            }
        }
        return AudioOutput.ALL;
    }

    public static Decoder getDecoderFromDevice() {
        if (LibVlcUtil.isJellyBeanMR2OrLater()) {
            return Decoder.ALL;
        }
        if (LibVlcUtil.isHoneycombOrLater()) {
            for (DecoderBySOC decoderBySOC : sDecoderBySOCList) {
                String systemPropertyCached = getSystemPropertyCached(decoderBySOC.key);
                if (systemPropertyCached != null && systemPropertyCached.contains(decoderBySOC.value)) {
                    return decoderBySOC.dec;
                }
            }
        }
        return Decoder.UNKNOWN;
    }

    private static String getSystemProperty(String str, String str2) {
        try {
            Class loadClass = ClassLoader.getSystemClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod("get", new Class[]{String.class, String.class}).invoke(loadClass, new Object[]{str, str2});
        } catch (Exception e) {
            return str2;
        }
    }

    private static String getSystemPropertyCached(String str) {
        String str2 = (String) sSystemPropertyMap.get(str);
        if (str2 != null) {
            return str2;
        }
        str2 = getSystemProperty(str, "none");
        sSystemPropertyMap.put(str, str2);
        return str2;
    }
}
