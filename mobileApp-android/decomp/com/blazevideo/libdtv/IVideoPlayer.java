package com.blazevideo.libdtv;

import android.view.Surface;

public interface IVideoPlayer {
    int configureSurface(Surface surface, int i, int i2, int i3);

    void eventHardwareAccelerationError();

    void setSurfaceLayout(int i, int i2, int i3, int i4, int i5, int i6);
}
