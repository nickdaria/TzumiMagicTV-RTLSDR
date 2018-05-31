package com.kyleduo.switchbutton;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SwitchButton extends CompoundButton {
    private static int[] CHECKED_PRESSED_STATE = new int[]{16842912, 16842910, 16842919};
    public static final int DEFAULT_ANIMATION_DURATION = 250;
    public static final float DEFAULT_BACK_MEASURE_RATIO = 1.8f;
    public static final int DEFAULT_TEXT_MARGIN_DP = 2;
    public static final int DEFAULT_THUMB_MARGIN_DP = 2;
    public static final int DEFAULT_THUMB_SIZE_DP = 20;
    public static final int DEFAULT_TINT_COLOR = 3309506;
    private static int[] UNCHECKED_PRESSED_STATE = new int[]{-16842912, 16842910, 16842919};
    private long mAnimationDuration;
    private boolean mAutoAdjustTextPosition = true;
    private ColorStateList mBackColor;
    private Drawable mBackDrawable;
    private float mBackMeasureRatio;
    private float mBackRadius;
    private RectF mBackRectF;
    private OnCheckedChangeListener mChildOnCheckedChangeListener;
    private int mClickTimeout;
    private int mCurrBackColor;
    private int mCurrThumbColor;
    private Drawable mCurrentBackDrawable;
    private boolean mDrawDebugRect = false;
    private boolean mFadeBack;
    private boolean mIsBackUseDrawable;
    private boolean mIsThumbUseDrawable;
    private float mLastX;
    private int mNextBackColor;
    private Drawable mNextBackDrawable;
    private Layout mOffLayout;
    private int mOffTextColor;
    private Layout mOnLayout;
    private int mOnTextColor;
    private Paint mPaint;
    private RectF mPresentThumbRectF;
    private float mProcess;
    private ObjectAnimator mProcessAnimator;
    private Paint mRectPaint;
    private RectF mSafeRectF;
    private float mStartX;
    private float mStartY;
    private float mTextHeight;
    private float mTextMarginH;
    private CharSequence mTextOff;
    private RectF mTextOffRectF;
    private CharSequence mTextOn;
    private RectF mTextOnRectF;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private ColorStateList mThumbColor;
    private Drawable mThumbDrawable;
    private RectF mThumbMargin;
    private float mThumbRadius;
    private RectF mThumbRectF;
    private PointF mThumbSizeF;
    private int mTintColor;
    private int mTouchSlop;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C05801();
        CharSequence offText;
        CharSequence onText;

        static class C05801 implements Creator<SavedState> {
            C05801() {
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.onText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.offText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.onText, parcel, i);
            TextUtils.writeToParcel(this.offText, parcel, i);
        }
    }

    public SwitchButton(Context context) {
        super(context);
        init(null);
    }

    public SwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    public SwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet);
    }

    private void catchView() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private int ceil(double d) {
        return (int) Math.ceil(d);
    }

    private boolean getStatusBasedOnPos() {
        return getProcess() > 0.5f;
    }

    private void init(AttributeSet attributeSet) {
        float dimension;
        float dimension2;
        float dimension3;
        float dimension4;
        float dimension5;
        float dimension6;
        Drawable drawable;
        ColorStateList colorStateList;
        float f;
        ColorStateList colorStateList2;
        int i;
        boolean z;
        int i2;
        CharSequence charSequence;
        float f2;
        Drawable drawable2;
        boolean z2;
        CharSequence charSequence2;
        float f3;
        TypedArray typedArray;
        float max;
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();
        this.mPaint = new Paint(1);
        this.mRectPaint = new Paint(1);
        this.mRectPaint.setStyle(Style.STROKE);
        this.mRectPaint.setStrokeWidth(getResources().getDisplayMetrics().density);
        this.mTextPaint = getPaint();
        this.mThumbRectF = new RectF();
        this.mBackRectF = new RectF();
        this.mSafeRectF = new RectF();
        this.mThumbSizeF = new PointF();
        this.mThumbMargin = new RectF();
        this.mTextOnRectF = new RectF();
        this.mTextOffRectF = new RectF();
        this.mProcessAnimator = ObjectAnimator.ofFloat(this, "process", new float[]{0.0f, 0.0f}).setDuration(250);
        this.mProcessAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mPresentThumbRectF = new RectF();
        float f4 = getResources().getDisplayMetrics().density;
        float f5 = f4 * 2.0f;
        float f6 = f4 * 20.0f;
        float f7 = f4 * 20.0f;
        float f8 = (20.0f * f4) / 2.0f;
        float f9 = f4 * 2.0f;
        TypedArray obtainStyledAttributes = attributeSet == null ? null : getContext().obtainStyledAttributes(attributeSet, C0578R.styleable.SwitchButton);
        if (obtainStyledAttributes != null) {
            Drawable drawable3 = obtainStyledAttributes.getDrawable(C0578R.styleable.SwitchButton_kswThumbDrawable);
            ColorStateList colorStateList3 = obtainStyledAttributes.getColorStateList(C0578R.styleable.SwitchButton_kswThumbColor);
            dimension = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbMargin, f5);
            dimension2 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbMarginLeft, dimension);
            dimension3 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbMarginRight, dimension);
            dimension4 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbMarginTop, dimension);
            dimension5 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbMarginBottom, dimension);
            dimension6 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbWidth, f6);
            f6 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbHeight, f7);
            f7 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswThumbRadius, Math.min(dimension6, f6) / 2.0f);
            f8 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswBackRadius, (2.0f * f4) + f7);
            Drawable drawable4 = obtainStyledAttributes.getDrawable(C0578R.styleable.SwitchButton_kswBackDrawable);
            ColorStateList colorStateList4 = obtainStyledAttributes.getColorStateList(C0578R.styleable.SwitchButton_kswBackColor);
            float f10 = obtainStyledAttributes.getFloat(C0578R.styleable.SwitchButton_kswBackMeasureRatio, DEFAULT_BACK_MEASURE_RATIO);
            int integer = obtainStyledAttributes.getInteger(C0578R.styleable.SwitchButton_kswAnimationDuration, 250);
            boolean z3 = obtainStyledAttributes.getBoolean(C0578R.styleable.SwitchButton_kswFadeBack, true);
            int color = obtainStyledAttributes.getColor(C0578R.styleable.SwitchButton_kswTintColor, 0);
            String string = obtainStyledAttributes.getString(C0578R.styleable.SwitchButton_kswTextOn);
            String string2 = obtainStyledAttributes.getString(C0578R.styleable.SwitchButton_kswTextOff);
            f9 = obtainStyledAttributes.getDimension(C0578R.styleable.SwitchButton_kswTextMarginH, Math.max(f9, f8 / 2.0f));
            boolean z4 = obtainStyledAttributes.getBoolean(C0578R.styleable.SwitchButton_kswAutoAdjustTextPosition, true);
            obtainStyledAttributes.recycle();
            drawable = drawable3;
            colorStateList = colorStateList3;
            f = dimension2;
            dimension2 = dimension3;
            dimension3 = dimension4;
            dimension4 = dimension5;
            dimension5 = f7;
            colorStateList2 = colorStateList4;
            i = color;
            z = z4;
            float f11 = f8;
            i2 = integer;
            charSequence = string2;
            dimension = dimension6;
            dimension6 = f11;
            boolean z5 = z3;
            f2 = f9;
            f9 = f6;
            drawable2 = drawable4;
            z2 = z5;
            float f12 = f10;
            charSequence2 = string;
            f3 = f12;
        } else {
            colorStateList = null;
            drawable = null;
            dimension2 = 0.0f;
            f = 0.0f;
            dimension4 = 0.0f;
            dimension3 = 0.0f;
            dimension6 = f8;
            dimension5 = f8;
            i2 = 250;
            charSequence = null;
            dimension = f6;
            drawable2 = null;
            z2 = true;
            f2 = f9;
            f9 = f7;
            colorStateList2 = null;
            i = 0;
            z = true;
            f3 = DEFAULT_BACK_MEASURE_RATIO;
            charSequence2 = null;
        }
        if (attributeSet == null) {
            typedArray = null;
        } else {
            int[] iArr = new int[2];
            typedArray = getContext().obtainStyledAttributes(attributeSet, new int[]{16842970, 16842981});
        }
        if (typedArray != null) {
            boolean z6 = typedArray.getBoolean(0, true);
            boolean z7 = typedArray.getBoolean(1, z6);
            setFocusable(z6);
            setClickable(z7);
            typedArray.recycle();
        }
        this.mTextOn = charSequence2;
        this.mTextOff = charSequence;
        this.mTextMarginH = f2;
        this.mAutoAdjustTextPosition = z;
        this.mThumbDrawable = drawable;
        this.mThumbColor = colorStateList;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        this.mTintColor = i;
        if (this.mTintColor == 0) {
            TypedValue typedValue = new TypedValue();
            if (getContext().getTheme().resolveAttribute(C0578R.attr.colorAccent, typedValue, true)) {
                this.mTintColor = typedValue.data;
            } else {
                this.mTintColor = DEFAULT_TINT_COLOR;
            }
        }
        if (!this.mIsThumbUseDrawable && this.mThumbColor == null) {
            this.mThumbColor = ColorUtils.generateThumbColorWithTintColor(this.mTintColor);
            this.mCurrThumbColor = this.mThumbColor.getDefaultColor();
        }
        if (this.mIsThumbUseDrawable) {
            dimension = Math.max(dimension, (float) this.mThumbDrawable.getMinimumWidth());
            max = Math.max(f9, (float) this.mThumbDrawable.getMinimumHeight());
            f9 = dimension;
        } else {
            max = f9;
            f9 = dimension;
        }
        this.mThumbSizeF.set(f9, max);
        this.mBackDrawable = drawable2;
        this.mBackColor = colorStateList2;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        if (!this.mIsBackUseDrawable && this.mBackColor == null) {
            this.mBackColor = ColorUtils.generateBackColorWithTintColor(this.mTintColor);
            this.mCurrBackColor = this.mBackColor.getDefaultColor();
            this.mNextBackColor = this.mBackColor.getColorForState(CHECKED_PRESSED_STATE, this.mCurrBackColor);
        }
        this.mThumbMargin.set(f, dimension3, dimension2, dimension4);
        this.mBackMeasureRatio = this.mThumbMargin.width() >= 0.0f ? Math.max(f3, 1.0f) : f3;
        this.mThumbRadius = dimension5;
        this.mBackRadius = dimension6;
        this.mAnimationDuration = (long) i2;
        this.mFadeBack = z2;
        this.mProcessAnimator.setDuration(this.mAnimationDuration);
        if (isChecked()) {
            setProcess(1.0f);
        }
    }

    private Layout makeLayout(CharSequence charSequence) {
        return new StaticLayout(charSequence, this.mTextPaint, (int) Math.ceil((double) Layout.getDesiredWidth(charSequence, this.mTextPaint)), Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
    }

    private int measureHeight(int i) {
        int i2;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int ceil = ceil((double) Math.max(this.mThumbSizeF.y, (this.mThumbSizeF.y + this.mThumbMargin.top) + this.mThumbMargin.right));
        float height = this.mOnLayout != null ? (float) this.mOnLayout.getHeight() : 0.0f;
        float height2 = this.mOffLayout != null ? (float) this.mOffLayout.getHeight() : 0.0f;
        if (height == 0.0f && height2 == 0.0f) {
            this.mTextHeight = 0.0f;
            i2 = ceil;
        } else {
            this.mTextHeight = Math.max(height, height2);
            i2 = ceil((double) Math.max((float) ceil, this.mTextHeight));
        }
        i2 = Math.max(i2, getSuggestedMinimumHeight());
        i2 = Math.max(i2, (getPaddingTop() + i2) + getPaddingBottom());
        return mode == 1073741824 ? Math.max(i2, size) : mode == Integer.MIN_VALUE ? Math.min(i2, size) : i2;
    }

    private int measureWidth(int i) {
        int size = MeasureSpec.getSize(i);
        int mode = MeasureSpec.getMode(i);
        int ceil = ceil((double) (this.mThumbSizeF.x * this.mBackMeasureRatio));
        if (this.mIsBackUseDrawable) {
            ceil = Math.max(ceil, this.mBackDrawable.getMinimumWidth());
        }
        float width = this.mOnLayout != null ? (float) this.mOnLayout.getWidth() : 0.0f;
        float width2 = this.mOffLayout != null ? (float) this.mOffLayout.getWidth() : 0.0f;
        if (width == 0.0f && width2 == 0.0f) {
            this.mTextWidth = 0.0f;
        } else {
            this.mTextWidth = Math.max(width, width2) + (this.mTextMarginH * 2.0f);
            width = ((float) ceil) - this.mThumbSizeF.x;
            if (width < this.mTextWidth) {
                ceil = (int) (((float) ceil) + (this.mTextWidth - width));
            }
        }
        ceil = Math.max(ceil, ceil((double) ((((float) ceil) + this.mThumbMargin.left) + this.mThumbMargin.right)));
        ceil = Math.max(Math.max(ceil, (getPaddingLeft() + ceil) + getPaddingRight()), getSuggestedMinimumWidth());
        return mode == 1073741824 ? Math.max(ceil, size) : mode == Integer.MIN_VALUE ? Math.min(ceil, size) : ceil;
    }

    private void setDrawableState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(getDrawableState());
            invalidate();
        }
    }

    private void setup() {
        float f = 0.0f;
        float paddingTop = ((float) getPaddingTop()) + Math.max(0.0f, this.mThumbMargin.top);
        float paddingLeft = ((float) getPaddingLeft()) + Math.max(0.0f, this.mThumbMargin.left);
        if (!(this.mOnLayout == null || this.mOffLayout == null || this.mThumbMargin.top + this.mThumbMargin.bottom <= 0.0f)) {
            paddingTop += (((((float) ((getMeasuredHeight() - getPaddingBottom()) - getPaddingTop())) - this.mThumbSizeF.y) - this.mThumbMargin.top) - this.mThumbMargin.bottom) / 2.0f;
        }
        if (this.mIsThumbUseDrawable) {
            this.mThumbSizeF.x = Math.max(this.mThumbSizeF.x, (float) this.mThumbDrawable.getMinimumWidth());
            this.mThumbSizeF.y = Math.max(this.mThumbSizeF.y, (float) this.mThumbDrawable.getMinimumHeight());
        }
        this.mThumbRectF.set(paddingLeft, paddingTop, this.mThumbSizeF.x + paddingLeft, this.mThumbSizeF.y + paddingTop);
        paddingTop = this.mThumbRectF.left - this.mThumbMargin.left;
        paddingLeft = Math.min(0.0f, ((Math.max(this.mThumbSizeF.x * this.mBackMeasureRatio, this.mThumbSizeF.x + this.mTextWidth) - this.mThumbRectF.width()) - this.mTextWidth) / 2.0f);
        float min = Math.min(0.0f, (((this.mThumbRectF.height() + this.mThumbMargin.top) + this.mThumbMargin.bottom) - this.mTextHeight) / 2.0f);
        this.mBackRectF.set(paddingTop + paddingLeft, (this.mThumbRectF.top - this.mThumbMargin.top) + min, (((paddingTop + this.mThumbMargin.left) + Math.max(this.mThumbSizeF.x * this.mBackMeasureRatio, this.mThumbSizeF.x + this.mTextWidth)) + this.mThumbMargin.right) - paddingLeft, (this.mThumbRectF.bottom + this.mThumbMargin.bottom) - min);
        this.mSafeRectF.set(this.mThumbRectF.left, 0.0f, (this.mBackRectF.right - this.mThumbMargin.right) - this.mThumbRectF.width(), 0.0f);
        this.mBackRadius = Math.min(Math.min(this.mBackRectF.width(), this.mBackRectF.height()) / 2.0f, this.mBackRadius);
        if (this.mBackDrawable != null) {
            this.mBackDrawable.setBounds((int) this.mBackRectF.left, (int) this.mBackRectF.top, ceil((double) this.mBackRectF.right), ceil((double) this.mBackRectF.bottom));
        }
        if (this.mOnLayout != null) {
            paddingTop = (this.mThumbMargin.left < 0.0f ? this.mThumbMargin.left * -0.5f : 0.0f) + (((((this.mBackRectF.width() - this.mThumbRectF.width()) - this.mThumbMargin.right) - ((float) this.mOnLayout.getWidth())) / 2.0f) + this.mBackRectF.left);
            if (!this.mIsBackUseDrawable && this.mAutoAdjustTextPosition) {
                paddingTop += this.mBackRadius / 4.0f;
            }
            paddingLeft = this.mBackRectF.top + ((this.mBackRectF.height() - ((float) this.mOnLayout.getHeight())) / 2.0f);
            this.mTextOnRectF.set(paddingTop, paddingLeft, ((float) this.mOnLayout.getWidth()) + paddingTop, ((float) this.mOnLayout.getHeight()) + paddingLeft);
        }
        if (this.mOffLayout != null) {
            paddingTop = (this.mBackRectF.right - ((((this.mBackRectF.width() - this.mThumbRectF.width()) - this.mThumbMargin.left) - ((float) this.mOffLayout.getWidth())) / 2.0f)) - ((float) this.mOffLayout.getWidth());
            if (this.mThumbMargin.right < 0.0f) {
                f = this.mThumbMargin.right * 0.5f;
            }
            paddingTop += f;
            if (!this.mIsBackUseDrawable && this.mAutoAdjustTextPosition) {
                paddingTop -= this.mBackRadius / 4.0f;
            }
            f = this.mBackRectF.top + ((this.mBackRectF.height() - ((float) this.mOffLayout.getHeight())) / 2.0f);
            this.mTextOffRectF.set(paddingTop, f, ((float) this.mOffLayout.getWidth()) + paddingTop, ((float) this.mOffLayout.getHeight()) + f);
        }
    }

    protected void animateToState(boolean z) {
        if (this.mProcessAnimator != null) {
            if (this.mProcessAnimator.isRunning()) {
                this.mProcessAnimator.cancel();
            }
            this.mProcessAnimator.setDuration(this.mAnimationDuration);
            if (z) {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 1.0f});
            } else {
                this.mProcessAnimator.setFloatValues(new float[]{this.mProcess, 0.0f});
            }
            this.mProcessAnimator.start();
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mIsThumbUseDrawable || this.mThumbColor == null) {
            setDrawableState(this.mThumbDrawable);
        } else {
            this.mCurrThumbColor = this.mThumbColor.getColorForState(getDrawableState(), this.mCurrThumbColor);
        }
        int[] iArr = isChecked() ? UNCHECKED_PRESSED_STATE : CHECKED_PRESSED_STATE;
        ColorStateList textColors = getTextColors();
        if (textColors != null) {
            int defaultColor = textColors.getDefaultColor();
            this.mOnTextColor = textColors.getColorForState(CHECKED_PRESSED_STATE, defaultColor);
            this.mOffTextColor = textColors.getColorForState(UNCHECKED_PRESSED_STATE, defaultColor);
        }
        if (this.mIsBackUseDrawable || this.mBackColor == null) {
            if ((this.mBackDrawable instanceof StateListDrawable) && this.mFadeBack) {
                this.mBackDrawable.setState(iArr);
                this.mNextBackDrawable = this.mBackDrawable.getCurrent().mutate();
            } else {
                this.mNextBackDrawable = null;
            }
            setDrawableState(this.mBackDrawable);
            if (this.mBackDrawable != null) {
                this.mCurrentBackDrawable = this.mBackDrawable.getCurrent().mutate();
                return;
            }
            return;
        }
        this.mCurrBackColor = this.mBackColor.getColorForState(getDrawableState(), this.mCurrBackColor);
        this.mNextBackColor = this.mBackColor.getColorForState(iArr, this.mCurrBackColor);
    }

    public long getAnimationDuration() {
        return this.mAnimationDuration;
    }

    public ColorStateList getBackColor() {
        return this.mBackColor;
    }

    public Drawable getBackDrawable() {
        return this.mBackDrawable;
    }

    public float getBackMeasureRatio() {
        return this.mBackMeasureRatio;
    }

    public float getBackRadius() {
        return this.mBackRadius;
    }

    public PointF getBackSizeF() {
        return new PointF(this.mBackRectF.width(), this.mBackRectF.height());
    }

    public final float getProcess() {
        return this.mProcess;
    }

    public ColorStateList getThumbColor() {
        return this.mThumbColor;
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    public float getThumbHeight() {
        return this.mThumbSizeF.y;
    }

    public RectF getThumbMargin() {
        return this.mThumbMargin;
    }

    public float getThumbRadius() {
        return this.mThumbRadius;
    }

    public PointF getThumbSizeF() {
        return this.mThumbSizeF;
    }

    public float getThumbWidth() {
        return this.mThumbSizeF.x;
    }

    public int getTintColor() {
        return this.mTintColor;
    }

    public boolean isDrawDebugRect() {
        return this.mDrawDebugRect;
    }

    public boolean isFadeBack() {
        return this.mFadeBack;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int process;
        if (this.mIsBackUseDrawable) {
            if (!this.mFadeBack || this.mCurrentBackDrawable == null || this.mNextBackDrawable == null) {
                this.mBackDrawable.setAlpha(255);
                this.mBackDrawable.draw(canvas);
            } else {
                process = (int) ((isChecked() ? getProcess() : 1.0f - getProcess()) * 255.0f);
                this.mCurrentBackDrawable.setAlpha(process);
                this.mCurrentBackDrawable.draw(canvas);
                this.mNextBackDrawable.setAlpha(255 - process);
                this.mNextBackDrawable.draw(canvas);
            }
        } else if (this.mFadeBack) {
            process = (int) ((isChecked() ? getProcess() : 1.0f - getProcess()) * 255.0f);
            this.mPaint.setARGB((Color.alpha(this.mCurrBackColor) * process) / 255, Color.red(this.mCurrBackColor), Color.green(this.mCurrBackColor), Color.blue(this.mCurrBackColor));
            canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
            this.mPaint.setARGB(((255 - process) * Color.alpha(this.mNextBackColor)) / 255, Color.red(this.mNextBackColor), Color.green(this.mNextBackColor), Color.blue(this.mNextBackColor));
            canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
            this.mPaint.setAlpha(255);
        } else {
            this.mPaint.setColor(this.mCurrBackColor);
            canvas.drawRoundRect(this.mBackRectF, this.mBackRadius, this.mBackRadius, this.mPaint);
        }
        Layout layout = ((double) getProcess()) > 0.5d ? this.mOnLayout : this.mOffLayout;
        RectF rectF = ((double) getProcess()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF;
        if (!(layout == null || rectF == null)) {
            float process2 = ((double) getProcess()) >= 0.75d ? (getProcess() * 4.0f) - 3.0f : ((double) getProcess()) < 0.25d ? 1.0f - (getProcess() * 4.0f) : 0.0f;
            int i = (int) (process2 * 255.0f);
            int i2 = ((double) getProcess()) > 0.5d ? this.mOnTextColor : this.mOffTextColor;
            layout.getPaint().setARGB((i * Color.alpha(i2)) / 255, Color.red(i2), Color.green(i2), Color.blue(i2));
            canvas.save();
            canvas.translate(rectF.left, rectF.top);
            layout.draw(canvas);
            canvas.restore();
        }
        this.mPresentThumbRectF.set(this.mThumbRectF);
        this.mPresentThumbRectF.offset(this.mProcess * this.mSafeRectF.width(), 0.0f);
        if (this.mIsThumbUseDrawable) {
            this.mThumbDrawable.setBounds((int) this.mPresentThumbRectF.left, (int) this.mPresentThumbRectF.top, ceil((double) this.mPresentThumbRectF.right), ceil((double) this.mPresentThumbRectF.bottom));
            this.mThumbDrawable.draw(canvas);
        } else {
            this.mPaint.setColor(this.mCurrThumbColor);
            canvas.drawRoundRect(this.mPresentThumbRectF, this.mThumbRadius, this.mThumbRadius, this.mPaint);
        }
        if (this.mDrawDebugRect) {
            this.mRectPaint.setColor(Color.parseColor("#AA0000"));
            canvas.drawRect(this.mBackRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#0000FF"));
            canvas.drawRect(this.mPresentThumbRectF, this.mRectPaint);
            this.mRectPaint.setColor(Color.parseColor("#00CC00"));
            canvas.drawRect(((double) getProcess()) > 0.5d ? this.mTextOnRectF : this.mTextOffRectF, this.mRectPaint);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.mOnLayout == null && this.mTextOn != null) {
            this.mOnLayout = makeLayout(this.mTextOn);
        }
        if (this.mOffLayout == null && this.mTextOff != null) {
            this.mOffLayout = makeLayout(this.mTextOff);
        }
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        setText(savedState.onText, savedState.offText);
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.onText = this.mTextOn;
        savedState.offText = this.mTextOff;
        return savedState;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            setup();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !isClickable() || !isFocusable()) {
            return false;
        }
        float x = motionEvent.getX() - this.mStartX;
        float y = motionEvent.getY() - this.mStartY;
        switch (motionEvent.getAction()) {
            case 0:
                catchView();
                this.mStartX = motionEvent.getX();
                this.mStartY = motionEvent.getY();
                this.mLastX = this.mStartX;
                setPressed(true);
                return true;
            case 1:
            case 3:
                setPressed(false);
                boolean statusBasedOnPos = getStatusBasedOnPos();
                float eventTime = (float) (motionEvent.getEventTime() - motionEvent.getDownTime());
                if (x < ((float) this.mTouchSlop) && y < ((float) this.mTouchSlop) && eventTime < ((float) this.mClickTimeout)) {
                    performClick();
                    return true;
                } else if (statusBasedOnPos != isChecked()) {
                    playSoundEffect(0);
                    setChecked(statusBasedOnPos);
                    return true;
                } else {
                    animateToState(statusBasedOnPos);
                    return true;
                }
            case 2:
                float x2 = motionEvent.getX();
                setProcess(getProcess() + ((x2 - this.mLastX) / this.mSafeRectF.width()));
                this.mLastX = x2;
                return true;
            default:
                return true;
        }
    }

    public boolean performClick() {
        return super.performClick();
    }

    public void setAnimationDuration(long j) {
        this.mAnimationDuration = j;
    }

    public void setBackColor(ColorStateList colorStateList) {
        this.mBackColor = colorStateList;
        if (this.mBackColor != null) {
            setBackDrawable(null);
        }
        invalidate();
    }

    public void setBackColorRes(int i) {
        setBackColor(ContextCompat.getColorStateList(getContext(), i));
    }

    public void setBackDrawable(Drawable drawable) {
        this.mBackDrawable = drawable;
        this.mIsBackUseDrawable = this.mBackDrawable != null;
        setup();
        refreshDrawableState();
        requestLayout();
        invalidate();
    }

    public void setBackDrawableRes(int i) {
        setBackDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public void setBackMeasureRatio(float f) {
        this.mBackMeasureRatio = f;
        requestLayout();
    }

    public void setBackRadius(float f) {
        this.mBackRadius = f;
        if (!this.mIsBackUseDrawable) {
            invalidate();
        }
    }

    public void setChecked(boolean z) {
        if (isChecked() != z) {
            animateToState(z);
        }
        super.setChecked(z);
    }

    public void setCheckedImmediately(boolean z) {
        super.setChecked(z);
        if (this.mProcessAnimator != null && this.mProcessAnimator.isRunning()) {
            this.mProcessAnimator.cancel();
        }
        setProcess(z ? 1.0f : 0.0f);
        invalidate();
    }

    public void setCheckedImmediatelyNoEvent(boolean z) {
        if (this.mChildOnCheckedChangeListener == null) {
            setCheckedImmediately(z);
            return;
        }
        super.setOnCheckedChangeListener(null);
        setCheckedImmediately(z);
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void setCheckedNoEvent(boolean z) {
        if (this.mChildOnCheckedChangeListener == null) {
            setChecked(z);
            return;
        }
        super.setOnCheckedChangeListener(null);
        setChecked(z);
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void setDrawDebugRect(boolean z) {
        this.mDrawDebugRect = z;
        invalidate();
    }

    public void setFadeBack(boolean z) {
        this.mFadeBack = z;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        super.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mChildOnCheckedChangeListener = onCheckedChangeListener;
    }

    public final void setProcess(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        this.mProcess = f;
        invalidate();
    }

    public void setText(CharSequence charSequence, CharSequence charSequence2) {
        this.mTextOn = charSequence;
        this.mTextOff = charSequence2;
        this.mOnLayout = null;
        this.mOffLayout = null;
        requestLayout();
        invalidate();
    }

    public void setThumbColor(ColorStateList colorStateList) {
        this.mThumbColor = colorStateList;
        if (this.mThumbColor != null) {
            setThumbDrawable(null);
        }
    }

    public void setThumbColorRes(int i) {
        setThumbColor(ContextCompat.getColorStateList(getContext(), i));
    }

    public void setThumbDrawable(Drawable drawable) {
        this.mThumbDrawable = drawable;
        this.mIsThumbUseDrawable = this.mThumbDrawable != null;
        setup();
        refreshDrawableState();
        requestLayout();
        invalidate();
    }

    public void setThumbDrawableRes(int i) {
        setThumbDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public void setThumbMargin(float f, float f2, float f3, float f4) {
        this.mThumbMargin.set(f, f2, f3, f4);
        requestLayout();
    }

    public void setThumbMargin(RectF rectF) {
        if (rectF == null) {
            setThumbMargin(0.0f, 0.0f, 0.0f, 0.0f);
        } else {
            setThumbMargin(rectF.left, rectF.top, rectF.right, rectF.bottom);
        }
    }

    public void setThumbRadius(float f) {
        this.mThumbRadius = f;
        if (!this.mIsThumbUseDrawable) {
            invalidate();
        }
    }

    public void setThumbSize(float f, float f2) {
        this.mThumbSizeF.set(f, f2);
        setup();
        requestLayout();
    }

    public void setThumbSize(PointF pointF) {
        if (pointF == null) {
            float f = getResources().getDisplayMetrics().density * 20.0f;
            setThumbSize(f, f);
            return;
        }
        setThumbSize(pointF.x, pointF.y);
    }

    public void setTintColor(int i) {
        this.mTintColor = i;
        this.mThumbColor = ColorUtils.generateThumbColorWithTintColor(this.mTintColor);
        this.mBackColor = ColorUtils.generateBackColorWithTintColor(this.mTintColor);
        this.mIsBackUseDrawable = false;
        this.mIsThumbUseDrawable = false;
        refreshDrawableState();
        invalidate();
    }

    public void toggleImmediately() {
        setCheckedImmediately(!isChecked());
    }

    public void toggleImmediatelyNoEvent() {
        if (this.mChildOnCheckedChangeListener == null) {
            toggleImmediately();
            return;
        }
        super.setOnCheckedChangeListener(null);
        toggleImmediately();
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }

    public void toggleNoEvent() {
        if (this.mChildOnCheckedChangeListener == null) {
            toggle();
            return;
        }
        super.setOnCheckedChangeListener(null);
        toggle();
        setOnCheckedChangeListener(this.mChildOnCheckedChangeListener);
    }
}
