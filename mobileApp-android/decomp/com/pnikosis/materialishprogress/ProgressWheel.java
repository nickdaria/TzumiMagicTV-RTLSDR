package com.pnikosis.materialishprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;

public class ProgressWheel extends View {
    private static final String f576a = ProgressWheel.class.getSimpleName();
    private int f577b = 80;
    private boolean f578c = false;
    private final int f579d = 40;
    private final int f580e = 270;
    private double f581f = 0.0d;
    private double f582g = 1000.0d;
    private float f583h = 0.0f;
    private boolean f584i = true;
    private long f585j = 0;
    private final long f586k = 300;
    private int f587l = 5;
    private int f588m = 5;
    private int f589n = -1442840576;
    private int f590o = ViewCompat.MEASURED_SIZE_MASK;
    private Paint f591p = new Paint();
    private Paint f592q = new Paint();
    private RectF f593r = new RectF();
    private float f594s = 270.0f;
    private long f595t = 0;
    private float f596u = 0.0f;
    private float f597v = 0.0f;
    private boolean f598w = false;

    static class C0583a extends BaseSavedState {
        public static final Creator<C0583a> CREATOR = new C05821();
        float f567a;
        float f568b;
        boolean f569c;
        float f570d;
        int f571e;
        int f572f;
        int f573g;
        int f574h;
        int f575i;

        static class C05821 implements Creator<C0583a> {
            C05821() {
            }

            public C0583a m775a(Parcel parcel) {
                return new C0583a(parcel);
            }

            public C0583a[] m776a(int i) {
                return new C0583a[i];
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m775a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m776a(i);
            }
        }

        private C0583a(Parcel parcel) {
            super(parcel);
            this.f567a = parcel.readFloat();
            this.f568b = parcel.readFloat();
            this.f569c = parcel.readByte() != (byte) 0;
            this.f570d = parcel.readFloat();
            this.f571e = parcel.readInt();
            this.f572f = parcel.readInt();
            this.f573g = parcel.readInt();
            this.f574h = parcel.readInt();
            this.f575i = parcel.readInt();
        }

        C0583a(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.f567a);
            parcel.writeFloat(this.f568b);
            parcel.writeByte((byte) (this.f569c ? 1 : 0));
            parcel.writeFloat(this.f570d);
            parcel.writeInt(this.f571e);
            parcel.writeInt(this.f572f);
            parcel.writeInt(this.f573g);
            parcel.writeInt(this.f574h);
            parcel.writeInt(this.f575i);
        }
    }

    public ProgressWheel(Context context) {
        super(context);
    }

    public ProgressWheel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m779a(context.obtainStyledAttributes(attributeSet, C0584R.styleable.ProgressWheel));
    }

    private void m777a(int i, int i2) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (this.f578c) {
            this.f593r = new RectF((float) (paddingLeft + this.f587l), (float) (paddingTop + this.f587l), (float) ((i - paddingRight) - this.f587l), (float) ((i2 - paddingBottom) - this.f587l));
            return;
        }
        int min = Math.min(Math.min((i - paddingLeft) - paddingRight, (i2 - paddingBottom) - paddingTop), (this.f577b * 2) - (this.f587l * 2));
        paddingLeft += (((i - paddingLeft) - paddingRight) - min) / 2;
        paddingTop += (((i2 - paddingTop) - paddingBottom) - min) / 2;
        this.f593r = new RectF((float) (this.f587l + paddingLeft), (float) (this.f587l + paddingTop), (float) ((paddingLeft + min) - this.f587l), (float) ((paddingTop + min) - this.f587l));
    }

    private void m778a(long j) {
        if (this.f585j >= 300) {
            this.f581f += (double) j;
            if (this.f581f > this.f582g) {
                this.f581f -= this.f582g;
                this.f581f = 0.0d;
                if (!this.f584i) {
                    this.f585j = 0;
                }
                this.f584i = !this.f584i;
            }
            float cos = (((float) Math.cos(((this.f581f / this.f582g) + 1.0d) * 3.141592653589793d)) / 2.0f) + 0.5f;
            if (this.f584i) {
                this.f583h = cos * 230.0f;
                return;
            }
            cos = (1.0f - cos) * 230.0f;
            this.f596u += this.f583h - cos;
            this.f583h = cos;
            return;
        }
        this.f585j += j;
    }

    private void m779a(TypedArray typedArray) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        this.f587l = (int) TypedValue.applyDimension(1, (float) this.f587l, displayMetrics);
        this.f588m = (int) TypedValue.applyDimension(1, (float) this.f588m, displayMetrics);
        this.f577b = (int) typedArray.getDimension(C0584R.styleable.ProgressWheel_circleRadius, (float) this.f577b);
        this.f578c = typedArray.getBoolean(C0584R.styleable.ProgressWheel_fillRadius, false);
        this.f587l = (int) typedArray.getDimension(C0584R.styleable.ProgressWheel_barWidth, (float) this.f587l);
        this.f588m = (int) typedArray.getDimension(C0584R.styleable.ProgressWheel_rimWidth, (float) this.f588m);
        this.f594s = typedArray.getFloat(C0584R.styleable.ProgressWheel_spinSpeed, this.f594s / 360.0f) * 360.0f;
        this.f582g = (double) typedArray.getInt(C0584R.styleable.ProgressWheel_barSpinCycleTime, (int) this.f582g);
        this.f589n = typedArray.getColor(C0584R.styleable.ProgressWheel_barColor, this.f589n);
        this.f590o = typedArray.getColor(C0584R.styleable.ProgressWheel_rimColor, this.f590o);
        if (typedArray.getBoolean(C0584R.styleable.ProgressWheel_progressIndeterminate, false)) {
            m784d();
        }
        typedArray.recycle();
    }

    private void m780e() {
        this.f591p.setColor(this.f589n);
        this.f591p.setAntiAlias(true);
        this.f591p.setStyle(Style.STROKE);
        this.f591p.setStrokeWidth((float) this.f587l);
        this.f592q.setColor(this.f590o);
        this.f592q.setAntiAlias(true);
        this.f592q.setStyle(Style.STROKE);
        this.f592q.setStrokeWidth((float) this.f588m);
    }

    public boolean m781a() {
        return this.f598w;
    }

    public void m782b() {
        this.f596u = 0.0f;
        this.f597v = 0.0f;
        invalidate();
    }

    public void m783c() {
        this.f598w = false;
        this.f596u = 0.0f;
        this.f597v = 0.0f;
        invalidate();
    }

    public void m784d() {
        this.f595t = SystemClock.uptimeMillis();
        this.f598w = true;
        invalidate();
    }

    public int getBarColor() {
        return this.f589n;
    }

    public int getBarWidth() {
        return this.f587l;
    }

    public int getCircleRadius() {
        return this.f577b;
    }

    public float getProgress() {
        return this.f598w ? -1.0f : this.f596u / 360.0f;
    }

    public int getRimColor() {
        return this.f590o;
    }

    public int getRimWidth() {
        return this.f588m;
    }

    public float getSpinSpeed() {
        return this.f594s / 360.0f;
    }

    protected void onDraw(Canvas canvas) {
        boolean z = true;
        super.onDraw(canvas);
        canvas.drawArc(this.f593r, 360.0f, 360.0f, false, this.f592q);
        if (this.f598w) {
            long uptimeMillis = SystemClock.uptimeMillis() - this.f595t;
            float f = (((float) uptimeMillis) * this.f594s) / 1000.0f;
            m778a(uptimeMillis);
            this.f596u += f;
            if (this.f596u > 360.0f) {
                this.f596u -= 360.0f;
            }
            this.f595t = SystemClock.uptimeMillis();
            Canvas canvas2 = canvas;
            canvas2.drawArc(this.f593r, this.f596u - 90.0f, 40.0f + this.f583h, false, this.f591p);
        } else {
            if (this.f596u != this.f597v) {
                this.f596u = Math.min(((((float) (SystemClock.uptimeMillis() - this.f595t)) / 1000.0f) * this.f594s) + this.f596u, this.f597v);
                this.f595t = SystemClock.uptimeMillis();
            } else {
                z = false;
            }
            canvas.drawArc(this.f593r, -90.0f, this.f596u, false, this.f591p);
        }
        if (z) {
            invalidate();
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int paddingLeft = (this.f577b + getPaddingLeft()) + getPaddingRight();
        int paddingTop = (this.f577b + getPaddingTop()) + getPaddingBottom();
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size2 = MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            size = mode == Integer.MIN_VALUE ? Math.min(paddingLeft, size) : paddingLeft;
        }
        if (mode2 == 1073741824 || mode == 1073741824) {
            paddingTop = size2;
        } else if (mode2 == Integer.MIN_VALUE) {
            paddingTop = Math.min(paddingTop, size2);
        }
        setMeasuredDimension(size, paddingTop);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof C0583a) {
            C0583a c0583a = (C0583a) parcelable;
            super.onRestoreInstanceState(c0583a.getSuperState());
            this.f596u = c0583a.f567a;
            this.f597v = c0583a.f568b;
            this.f598w = c0583a.f569c;
            this.f594s = c0583a.f570d;
            this.f587l = c0583a.f571e;
            this.f589n = c0583a.f572f;
            this.f588m = c0583a.f573g;
            this.f590o = c0583a.f574h;
            this.f577b = c0583a.f575i;
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Parcelable c0583a = new C0583a(super.onSaveInstanceState());
        c0583a.f567a = this.f596u;
        c0583a.f568b = this.f597v;
        c0583a.f569c = this.f598w;
        c0583a.f570d = this.f594s;
        c0583a.f571e = this.f587l;
        c0583a.f572f = this.f589n;
        c0583a.f573g = this.f588m;
        c0583a.f574h = this.f590o;
        c0583a.f575i = this.f577b;
        return c0583a;
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        m777a(i, i2);
        m780e();
        invalidate();
    }

    public void setBarColor(int i) {
        this.f589n = i;
        m780e();
        if (!this.f598w) {
            invalidate();
        }
    }

    public void setBarWidth(int i) {
        this.f587l = i;
        if (!this.f598w) {
            invalidate();
        }
    }

    public void setCircleRadius(int i) {
        this.f577b = i;
        if (!this.f598w) {
            invalidate();
        }
    }

    public void setInstantProgress(float f) {
        if (this.f598w) {
            this.f596u = 0.0f;
            this.f598w = false;
        }
        if (f > 1.0f) {
            f -= 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        if (f != this.f597v) {
            this.f597v = Math.min(f * 360.0f, 360.0f);
            this.f596u = this.f597v;
            this.f595t = SystemClock.uptimeMillis();
            invalidate();
        }
    }

    public void setProgress(float f) {
        if (this.f598w) {
            this.f596u = 0.0f;
            this.f598w = false;
        }
        if (f > 1.0f) {
            f -= 1.0f;
        } else if (f < 0.0f) {
            f = 0.0f;
        }
        if (f != this.f597v) {
            if (this.f596u == this.f597v) {
                this.f595t = SystemClock.uptimeMillis();
            }
            this.f597v = Math.min(f * 360.0f, 360.0f);
            invalidate();
        }
    }

    public void setRimColor(int i) {
        this.f590o = i;
        m780e();
        if (!this.f598w) {
            invalidate();
        }
    }

    public void setRimWidth(int i) {
        this.f588m = i;
        if (!this.f598w) {
            invalidate();
        }
    }

    public void setSpinSpeed(float f) {
        this.f594s = 360.0f * f;
    }
}
