package cn.pedant.SweetAlert;

import android.content.Context;
import com.pnikosis.materialishprogress.ProgressWheel;

public class ProgressHelper {
    private int mBarColor;
    private int mBarWidth;
    private int mCircleRadius;
    private boolean mIsInstantProgress;
    private float mProgressVal;
    private ProgressWheel mProgressWheel;
    private int mRimColor;
    private int mRimWidth;
    private float mSpinSpeed = 0.75f;
    private boolean mToSpin = true;

    public ProgressHelper(Context context) {
        this.mBarWidth = context.getResources().getDimensionPixelSize(C0373R.dimen.common_circle_width) + 1;
        this.mBarColor = context.getResources().getColor(C0373R.color.success_stroke_color);
        this.mRimWidth = 0;
        this.mRimColor = 0;
        this.mIsInstantProgress = false;
        this.mProgressVal = -1.0f;
        this.mCircleRadius = context.getResources().getDimensionPixelOffset(C0373R.dimen.progress_circle_radius);
    }

    private void updatePropsIfNeed() {
        if (this.mProgressWheel != null) {
            if (!this.mToSpin && this.mProgressWheel.m781a()) {
                this.mProgressWheel.m783c();
            } else if (this.mToSpin && !this.mProgressWheel.m781a()) {
                this.mProgressWheel.m784d();
            }
            if (this.mSpinSpeed != this.mProgressWheel.getSpinSpeed()) {
                this.mProgressWheel.setSpinSpeed(this.mSpinSpeed);
            }
            if (this.mBarWidth != this.mProgressWheel.getBarWidth()) {
                this.mProgressWheel.setBarWidth(this.mBarWidth);
            }
            if (this.mBarColor != this.mProgressWheel.getBarColor()) {
                this.mProgressWheel.setBarColor(this.mBarColor);
            }
            if (this.mRimWidth != this.mProgressWheel.getRimWidth()) {
                this.mProgressWheel.setRimWidth(this.mRimWidth);
            }
            if (this.mRimColor != this.mProgressWheel.getRimColor()) {
                this.mProgressWheel.setRimColor(this.mRimColor);
            }
            if (this.mProgressVal != this.mProgressWheel.getProgress()) {
                if (this.mIsInstantProgress) {
                    this.mProgressWheel.setInstantProgress(this.mProgressVal);
                } else {
                    this.mProgressWheel.setProgress(this.mProgressVal);
                }
            }
            if (this.mCircleRadius != this.mProgressWheel.getCircleRadius()) {
                this.mProgressWheel.setCircleRadius(this.mCircleRadius);
            }
        }
    }

    public int getBarColor() {
        return this.mBarColor;
    }

    public int getBarWidth() {
        return this.mBarWidth;
    }

    public int getCircleRadius() {
        return this.mCircleRadius;
    }

    public float getProgress() {
        return this.mProgressVal;
    }

    public ProgressWheel getProgressWheel() {
        return this.mProgressWheel;
    }

    public int getRimColor() {
        return this.mRimColor;
    }

    public int getRimWidth() {
        return this.mRimWidth;
    }

    public float getSpinSpeed() {
        return this.mSpinSpeed;
    }

    public boolean isSpinning() {
        return this.mToSpin;
    }

    public void resetCount() {
        if (this.mProgressWheel != null) {
            this.mProgressWheel.m782b();
        }
    }

    public void setBarColor(int i) {
        this.mBarColor = i;
        updatePropsIfNeed();
    }

    public void setBarWidth(int i) {
        this.mBarWidth = i;
        updatePropsIfNeed();
    }

    public void setCircleRadius(int i) {
        this.mCircleRadius = i;
        updatePropsIfNeed();
    }

    public void setInstantProgress(float f) {
        this.mProgressVal = f;
        this.mIsInstantProgress = true;
        updatePropsIfNeed();
    }

    public void setProgress(float f) {
        this.mIsInstantProgress = false;
        this.mProgressVal = f;
        updatePropsIfNeed();
    }

    public void setProgressWheel(ProgressWheel progressWheel) {
        this.mProgressWheel = progressWheel;
        updatePropsIfNeed();
    }

    public void setRimColor(int i) {
        this.mRimColor = i;
        updatePropsIfNeed();
    }

    public void setRimWidth(int i) {
        this.mRimWidth = i;
        updatePropsIfNeed();
    }

    public void setSpinSpeed(float f) {
        this.mSpinSpeed = f;
        updatePropsIfNeed();
    }

    public void spin() {
        this.mToSpin = true;
        updatePropsIfNeed();
    }

    public void stopSpinning() {
        this.mToSpin = false;
        updatePropsIfNeed();
    }
}
