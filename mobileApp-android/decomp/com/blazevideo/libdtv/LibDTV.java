package com.blazevideo.libdtv;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import com.blazevideo.libdtv.HWDecoderUtil.Decoder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class LibDTV {
    public static final int AOUT_AUDIOTRACK = 1;
    public static final int AOUT_AUDIOTRACK_JAVA = 0;
    public static final int AOUT_OPENSLES = 2;
    private static final String DEFAULT_CODEC_LIST = "mediacodec,iomx,all";
    public static final int DEV_HW_DECODER_AUTOMATIC = -1;
    public static final int DEV_HW_DECODER_MEDIACODEC = 2;
    public static final int DEV_HW_DECODER_MEDIACODEC_DR = 3;
    public static final int DEV_HW_DECODER_OMX = 0;
    public static final int DEV_HW_DECODER_OMX_DR = 1;
    private static final boolean HAS_WINDOW_VOUT = true;
    public static final int HW_ACCELERATION_AUTOMATIC = -1;
    public static final int HW_ACCELERATION_DECODING = 1;
    public static final int HW_ACCELERATION_DISABLED = 0;
    public static final int HW_ACCELERATION_FULL = 2;
    public static final int INPUT_NAV_ACTIVATE = 0;
    public static final int INPUT_NAV_DOWN = 2;
    public static final int INPUT_NAV_LEFT = 3;
    public static final int INPUT_NAV_RIGHT = 4;
    public static final int INPUT_NAV_UP = 1;
    private static final String TAG = "DTV/LibDTV";
    public static final int VOUT_ANDROID_SURFACE = 0;
    public static final int VOUT_ANDROID_WINDOW = 2;
    public static final int VOUT_OPEGLES2 = 1;
    private static LibDTV sInstance;
    private int aout = 2;
    private String chroma = "";
    private String codecList = DEFAULT_CODEC_LIST;
    private int deblocking = -1;
    private String devCodecList = null;
    private int devHardwareDecoder = -1;
    private float[] equalizer = null;
    private boolean frameSkip = false;
    private int hardwareAcceleration = -1;
    private boolean httpReconnect = false;
    private String mCachePath = "";
    private StringBuffer mDebugLogBuffer;
    private int mInternalMediaPlayerIndex = 0;
    private long mInternalMediaPlayerInstance = 0;
    private boolean mIsBufferingLog = false;
    private boolean mIsInitialized = false;
    private long mLibDtvInstance = 0;
    private OnNativeCrashListener mOnNativeCrashListener;
    private int networkCaching = 0;
    private String subtitlesEncoding = "";
    private boolean timeStretching = false;
    private boolean verboseMode = true;
    private int vout = 0;

    public interface OnNativeCrashListener {
        void onNativeCrash();
    }

    static {
        try {
            System.loadLibrary("dtvjni");
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, "Can't load dtvjni library: " + e);
            System.exit(1);
        } catch (SecurityException e2) {
            Log.e(TAG, "Encountered a security issue when loading dtvjni library: " + e2);
            System.exit(1);
        }
    }

    private LibDTV() {
    }

    public static String PathToURI(String str) {
        if (str != null) {
            return nativeToURI(str);
        }
        throw new NullPointerException("Cannot convert null path!");
    }

    private void applyEqualizer() {
        setNativeEqualizer(this.mInternalMediaPlayerInstance, this.equalizer);
    }

    private native void detachEventHandler();

    public static LibDTV getExistingInstance() {
        LibDTV libDTV;
        synchronized (LibDTV.class) {
            libDTV = sInstance;
        }
        return libDTV;
    }

    public static LibDTV getInstance() throws LibDtvException {
        synchronized (LibDTV.class) {
            if (sInstance == null) {
                sInstance = new LibDTV();
                sInstance.setVout(0);
            }
        }
        return sInstance;
    }

    private native void nativeDestroy();

    private native void nativeInit() throws LibDtvException;

    public static native boolean nativeIsPathDirectory(String str);

    public static native void nativeReadDirectory(String str, ArrayList<String> arrayList);

    public static native String nativeToURI(String str);

    private void onNativeCrash() {
        if (this.mOnNativeCrashListener != null) {
            this.mOnNativeCrashListener.onNativeCrash();
        }
    }

    public static synchronized void restart(Context context) {
        synchronized (LibDTV.class) {
            if (sInstance != null) {
                try {
                    sInstance.destroy();
                    sInstance.init(context);
                } catch (LibDtvException e) {
                    Log.e(TAG, "Unable to reinit libdtv: " + e);
                }
            }
        }
    }

    public static native void sendMouseEvent(int i, int i2, int i3, int i4);

    private native void setEventHandler(EventHandler eventHandler);

    private native int setNativeEqualizer(long j, float[] fArr);

    public native int addSubtitleTrack(String str);

    public native void attachSubtitlesSurface(Surface surface);

    public native void attachSurface(Surface surface, IVideoPlayer iVideoPlayer);

    public native String changeset();

    public void clearBuffer() {
        this.mDebugLogBuffer.setLength(0);
    }

    public void destroy() {
        nativeDestroy();
        detachEventHandler();
        this.mIsInitialized = false;
    }

    public native void detachSubtitlesSurface();

    public native void detachSurface();

    public native void eventVideoPlayerActivityCreated(boolean z);

    protected void finalize() {
        if (this.mLibDtvInstance != 0) {
            Log.d(TAG, "LibDTV is was destroyed yet before finalize()");
            destroy();
        }
    }

    public boolean frameSkipEnabled() {
        return this.frameSkip;
    }

    public int getAout() {
        return this.aout;
    }

    public native int getAudioTrack();

    public native Map<Integer, String> getAudioTrackDescription();

    public native int getAudioTracksCount();

    public String getBufferContent() {
        return this.mDebugLogBuffer.toString();
    }

    public String getCachePath() {
        return this.mCachePath;
    }

    public native int getChapterCountForTitle(int i);

    public String getChroma() {
        return this.chroma;
    }

    public int getDeblocking() {
        return 4;
    }

    public int getDevHardwareDecoder() {
        return -1;
    }

    public native String getEpgList(String str);

    public float[] getEqualizer() {
        return this.equalizer;
    }

    public int getHardwareAcceleration() {
        return 1;
    }

    public boolean getHttpReconnect() {
        return this.httpReconnect;
    }

    public native String getMeta(int i);

    public int getNetworkCaching() {
        return this.networkCaching;
    }

    public native String getPidList();

    public native int getPlayerState();

    public native float getPosition();

    public native int getProgram();

    public native String getProgramList();

    public native int getSpuTrack();

    public native Map<Integer, String> getSpuTrackDescription();

    public native int getSpuTracksCount();

    public native Map<String, Object> getStats();

    public String getSubtitlesEncoding() {
        return this.subtitlesEncoding;
    }

    public native long getTime();

    public native int getTitle();

    public native int getTitleCount();

    public native int getVideoTracksCount();

    public native int getVolume();

    public int getVout() {
        return this.vout;
    }

    public native boolean hasVideoTrack(String str) throws IOException;

    public void init(Context context) throws LibDtvException {
        Log.v(TAG, "Initializing LibDTV");
        this.mDebugLogBuffer = new StringBuffer();
        if (!this.mIsInitialized) {
            File cacheDir = context.getCacheDir();
            this.mCachePath = cacheDir != null ? cacheDir.getAbsolutePath() : null;
            nativeInit();
            setEventHandler(EventHandler.getInstance());
            this.mIsInitialized = true;
        }
    }

    public boolean isDebugBuffering() {
        return this.mIsBufferingLog;
    }

    public boolean isDirectRendering() {
        return true;
    }

    public native boolean isPlaying();

    public native boolean isSeekable();

    public boolean isVerboseMode() {
        return this.verboseMode;
    }

    public native void pause();

    public native void play();

    public native void playMRL(String str, String[] strArr);

    public native void playerNavigate(int i);

    public void setAout(int i) {
        if (i < 0) {
            this.aout = 2;
        } else {
            this.aout = i;
        }
    }

    public native int setAudioTrack(int i);

    public void setDeblocking(int i) {
        this.deblocking = i;
    }

    public void setDevHardwareDecoder(int i) {
        if (i != -1) {
            this.devHardwareDecoder = i;
            if (this.devHardwareDecoder == 0 || this.devHardwareDecoder == 1) {
                this.devCodecList = "iomx";
            } else {
                this.devCodecList = "mediacodec";
            }
            Log.d(TAG, "HWDec forced: " + this.devCodecList + (isDirectRendering() ? "-dr" : ""));
            this.devCodecList += ",none";
            return;
        }
        this.devHardwareDecoder = -1;
        this.devCodecList = null;
    }

    public void setEqualizer(float[] fArr) {
        this.equalizer = fArr;
        applyEqualizer();
    }

    public void setFrameSkip(boolean z) {
        this.frameSkip = z;
    }

    public void setHardwareAcceleration(int i) {
        if (i == 0) {
            Log.d(TAG, "HWDec disabled: by user");
            this.hardwareAcceleration = 0;
            this.codecList = "all";
            return;
        }
        Decoder decoderFromDevice = HWDecoderUtil.getDecoderFromDevice();
        if (decoderFromDevice == Decoder.NONE) {
            this.hardwareAcceleration = 0;
            this.codecList = "all";
            Log.d(TAG, "HWDec disabled: device not working with mediacodec,iomx");
        } else if (decoderFromDevice != Decoder.UNKNOWN) {
            if (i < 0) {
                i = 2;
            }
            this.hardwareAcceleration = i;
            if (decoderFromDevice == Decoder.ALL) {
                this.codecList = DEFAULT_CODEC_LIST;
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                if (decoderFromDevice == Decoder.MEDIACODEC) {
                    stringBuilder.append("mediacodec,");
                } else if (decoderFromDevice == Decoder.OMX) {
                    stringBuilder.append("iomx,");
                }
                stringBuilder.append("all");
                this.codecList = stringBuilder.toString();
            }
            Log.d(TAG, "HWDec enabled: device working with: " + this.codecList);
        } else if (i < 0) {
            this.hardwareAcceleration = 0;
            this.codecList = "all";
            Log.d(TAG, "HWDec disabled: automatic and (unknown device or android version < 4.3)");
        } else {
            this.hardwareAcceleration = i;
            this.codecList = DEFAULT_CODEC_LIST;
            Log.d(TAG, "HWDec enabled: forced by user and unknown device");
        }
    }

    public void setHttpReconnect(boolean z) {
        this.httpReconnect = z;
    }

    public void setNetworkCaching(int i) {
        this.networkCaching = i;
    }

    public void setOnNativeCrashListener(OnNativeCrashListener onNativeCrashListener) {
        this.mOnNativeCrashListener = onNativeCrashListener;
    }

    public native void setPosition(float f);

    public native int setProgram(int i);

    public native int setSpuTrack(int i);

    public void setSubtitlesEncoding(String str) {
        this.subtitlesEncoding = str;
    }

    public native void setSurface(Surface surface);

    public native long setTime(long j);

    public void setTimeStretching(boolean z) {
        this.timeStretching = z;
    }

    public native void setTitle(int i);

    public void setVerboseMode(boolean z) {
        this.verboseMode = z;
    }

    public native int setVolume(int i);

    public void setVout(int i) {
        if (i < 0) {
            this.vout = 0;
        } else {
            this.vout = i;
        }
        if (this.vout == 0) {
            this.vout = 2;
        }
    }

    public native int setWindowSize(int i, int i2);

    public native void startDebugBuffer();

    public native void stop();

    public native void stopDebugBuffer();

    public boolean timeStretchingEnabled() {
        return this.timeStretching;
    }

    public boolean useCompatSurface() {
        return this.vout != 2;
    }
}
