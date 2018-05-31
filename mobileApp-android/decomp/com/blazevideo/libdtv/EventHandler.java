package com.blazevideo.libdtv;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;

public class EventHandler {
    public static final int CustomMediaListExpanding = 8192;
    public static final int CustomMediaListExpandingEnd = 8193;
    public static final int CustomMediaListItemAdded = 8194;
    public static final int CustomMediaListItemDeleted = 8195;
    public static final int CustomMediaListItemMoved = 8196;
    public static final int HardwareAccelerationError = 12288;
    public static final int MediaMetaChanged = 0;
    public static final int MediaParsedChanged = 3;
    public static final int MediaPlayerEncounteredError = 266;
    public static final int MediaPlayerEndReached = 265;
    public static final int MediaPlayerPaused = 261;
    public static final int MediaPlayerPlaying = 260;
    public static final int MediaPlayerPositionChanged = 268;
    public static final int MediaPlayerStopped = 262;
    public static final int MediaPlayerTimeChanged = 267;
    public static final int MediaPlayerVout = 274;
    private static EventHandler mInstance;
    private ArrayList<Handler> mEventHandler = new ArrayList();

    EventHandler() {
    }

    public static EventHandler getInstance() {
        if (mInstance == null) {
            mInstance = new EventHandler();
        }
        return mInstance;
    }

    public void addHandler(Handler handler) {
        if (!this.mEventHandler.contains(handler)) {
            this.mEventHandler.add(handler);
        }
    }

    public void callback(int i, Bundle bundle) {
        bundle.putInt("event", i);
        for (int i2 = 0; i2 < this.mEventHandler.size(); i2++) {
            Message obtain = Message.obtain();
            obtain.setData(bundle);
            ((Handler) this.mEventHandler.get(i2)).sendMessage(obtain);
        }
    }

    public void removeHandler(Handler handler) {
        this.mEventHandler.remove(handler);
    }
}
