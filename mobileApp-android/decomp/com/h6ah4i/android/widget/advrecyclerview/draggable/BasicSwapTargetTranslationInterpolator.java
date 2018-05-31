package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.view.animation.Interpolator;

public class BasicSwapTargetTranslationInterpolator implements Interpolator {
    private final float mHalfValidRange;
    private final float mInvValidRange;
    private final float mThreshold;

    public BasicSwapTargetTranslationInterpolator() {
        this(0.3f);
    }

    public BasicSwapTargetTranslationInterpolator(float f) {
        if (f < 0.0f || f >= 0.5f) {
            throw new IllegalArgumentException("Invalid threshold range: " + f);
        }
        float f2 = 1.0f - (2.0f * f);
        this.mThreshold = f;
        this.mHalfValidRange = 0.5f * f2;
        this.mInvValidRange = 1.0f / f2;
    }

    public float getInterpolation(float f) {
        return Math.abs(f - 0.5f) < this.mHalfValidRange ? (f - this.mThreshold) * this.mInvValidRange : f < 0.5f ? 0.0f : 1.0f;
    }
}
