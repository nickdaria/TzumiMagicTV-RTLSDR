package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.view.animation.Interpolator;

class RubberBandInterpolator implements Interpolator {
    private final float mLimit;

    public RubberBandInterpolator(float f) {
        this.mLimit = f;
    }

    public float getInterpolation(float f) {
        float f2 = 1.0f - f;
        return (1.0f - (f2 * f2)) * this.mLimit;
    }
}
