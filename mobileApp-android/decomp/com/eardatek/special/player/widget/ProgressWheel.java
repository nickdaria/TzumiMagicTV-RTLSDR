package com.eardatek.special.player.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import com.eardatek.special.player.C0419R;

public class ProgressWheel extends View {
    private RectF f515A = new RectF();
    private RectF f516B = new RectF();
    private RectF f517C = new RectF();
    private int f518D = 2;
    private int f519E = 2;
    private Handler f520F = new C0564a(this, Looper.getMainLooper());
    private String f521G = "";
    private String[] f522H = new String[0];
    int f523a = 0;
    boolean f524b = false;
    private int f525c = 0;
    private int f526d = 0;
    private int f527e = 100;
    private int f528f = 80;
    private int f529g = 60;
    private int f530h = 20;
    private int f531i = 20;
    private int f532j = 20;
    private float f533k = 0.0f;
    private int f534l = 5;
    private int f535m = 5;
    private int f536n = 5;
    private int f537o = 5;
    private int f538p = -1442840576;
    private int f539q = -1442840576;
    private int f540r = 0;
    private int f541s = -1428300323;
    private int f542t = ViewCompat.MEASURED_STATE_MASK;
    private Paint f543u = new Paint();
    private Paint f544v = new Paint();
    private Paint f545w = new Paint();
    private Paint f546x = new Paint();
    private Paint f547y = new Paint();
    private RectF f548z = new RectF();

    public final class C0564a extends Handler {
        final /* synthetic */ ProgressWheel f514a;

        public C0564a(ProgressWheel progressWheel, Looper looper) {
            this.f514a = progressWheel;
            super(looper);
        }

        public void handleMessage(Message message) {
            this.f514a.invalidate();
            if (this.f514a.f524b) {
                ProgressWheel progressWheel = this.f514a;
                progressWheel.f523a += this.f514a.f518D;
                if (this.f514a.f523a > 360) {
                    this.f514a.f523a = 0;
                }
                this.f514a.f520F.sendEmptyMessageDelayed(0, (long) this.f514a.f519E);
            }
        }
    }

    public ProgressWheel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m761a(context.obtainStyledAttributes(attributeSet, C0419R.styleable.ProgressWheel));
    }

    private void m761a(TypedArray typedArray) {
        this.f530h = (int) typedArray.getDimension(8, (float) this.f530h);
        this.f531i = (int) typedArray.getDimension(3, (float) this.f531i);
        this.f518D = (int) typedArray.getDimension(4, (float) this.f518D);
        this.f519E = typedArray.getInteger(12, this.f519E);
        if (this.f519E < 0) {
            this.f519E = 0;
        }
        this.f538p = typedArray.getColor(1, this.f538p);
        this.f529g = (int) typedArray.getDimension(15, (float) this.f529g);
        this.f532j = (int) typedArray.getDimension(11, (float) this.f532j);
        this.f542t = typedArray.getColor(10, this.f542t);
        if (typedArray.hasValue(9)) {
            setText(typedArray.getString(9));
        }
        this.f541s = typedArray.getColor(2, this.f541s);
        this.f540r = typedArray.getColor(13, this.f540r);
        this.f539q = typedArray.getColor(16, this.f539q);
        this.f533k = typedArray.getDimension(17, this.f533k);
        typedArray.recycle();
    }

    private void m764c() {
        this.f543u.setColor(this.f538p);
        this.f543u.setAntiAlias(true);
        this.f543u.setStyle(Style.STROKE);
        this.f543u.setStrokeWidth((float) this.f530h);
        this.f545w.setColor(this.f541s);
        this.f545w.setAntiAlias(true);
        this.f545w.setStyle(Style.STROKE);
        this.f545w.setStrokeWidth((float) this.f531i);
        this.f544v.setColor(this.f540r);
        this.f544v.setAntiAlias(true);
        this.f544v.setStyle(Style.FILL);
        this.f546x.setColor(this.f542t);
        this.f546x.setStyle(Style.FILL);
        this.f546x.setAntiAlias(true);
        this.f546x.setTextSize((float) this.f532j);
        this.f547y.setColor(this.f539q);
        this.f547y.setAntiAlias(true);
        this.f547y.setStyle(Style.STROKE);
        this.f547y.setStrokeWidth(this.f533k);
    }

    private void m765d() {
        int min = Math.min(this.f526d, this.f525c);
        int i = this.f526d - min;
        min = this.f525c - min;
        this.f534l = getPaddingTop() + (min / 2);
        this.f535m = (min / 2) + getPaddingBottom();
        this.f536n = getPaddingLeft() + (i / 2);
        this.f537o = getPaddingRight() + (i / 2);
        min = getWidth();
        i = getHeight();
        this.f548z = new RectF((float) this.f536n, (float) this.f534l, (float) (min - this.f537o), (float) (i - this.f535m));
        this.f515A = new RectF((float) (this.f536n + this.f530h), (float) (this.f534l + this.f530h), (float) ((min - this.f537o) - this.f530h), (float) ((i - this.f535m) - this.f530h));
        this.f517C = new RectF((this.f515A.left + (((float) this.f531i) / 2.0f)) + (this.f533k / 2.0f), (this.f515A.top + (((float) this.f531i) / 2.0f)) + (this.f533k / 2.0f), (this.f515A.right - (((float) this.f531i) / 2.0f)) - (this.f533k / 2.0f), (this.f515A.bottom - (((float) this.f531i) / 2.0f)) - (this.f533k / 2.0f));
        this.f516B = new RectF((this.f515A.left - (((float) this.f531i) / 2.0f)) - (this.f533k / 2.0f), (this.f515A.top - (((float) this.f531i) / 2.0f)) - (this.f533k / 2.0f), (this.f515A.right + (((float) this.f531i) / 2.0f)) + (this.f533k / 2.0f), (this.f515A.bottom + (((float) this.f531i) / 2.0f)) + (this.f533k / 2.0f));
        this.f527e = ((min - this.f537o) - this.f530h) / 2;
        this.f528f = (this.f527e - this.f530h) + 1;
    }

    public void m766a() {
        this.f524b = false;
        this.f523a = 0;
        this.f520F.removeMessages(0);
    }

    public void m767b() {
        this.f524b = true;
        this.f520F.sendEmptyMessage(0);
    }

    public int getBarColor() {
        return this.f538p;
    }

    public int getBarLength() {
        return this.f529g;
    }

    public int getBarWidth() {
        return this.f530h;
    }

    public int getCircleColor() {
        return this.f540r;
    }

    public int getCircleRadius() {
        return this.f528f;
    }

    public int getDelayMillis() {
        return this.f519E;
    }

    public int getPaddingBottom() {
        return this.f535m;
    }

    public int getPaddingLeft() {
        return this.f536n;
    }

    public int getPaddingRight() {
        return this.f537o;
    }

    public int getPaddingTop() {
        return this.f534l;
    }

    public int getRimColor() {
        return this.f541s;
    }

    public Shader getRimShader() {
        return this.f545w.getShader();
    }

    public int getRimWidth() {
        return this.f531i;
    }

    public int getSpinSpeed() {
        return this.f518D;
    }

    public int getTextColor() {
        return this.f542t;
    }

    public int getTextSize() {
        return this.f532j;
    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        super.onDraw(canvas);
        canvas.drawArc(this.f515A, 360.0f, 360.0f, false, this.f544v);
        canvas.drawArc(this.f515A, 360.0f, 360.0f, false, this.f545w);
        canvas.drawArc(this.f516B, 360.0f, 360.0f, false, this.f547y);
        canvas.drawArc(this.f517C, 360.0f, 360.0f, false, this.f547y);
        if (this.f524b) {
            canvas.drawArc(this.f515A, (float) (this.f523a - 90), (float) this.f529g, false, this.f543u);
        } else {
            canvas.drawArc(this.f515A, -90.0f, (float) this.f523a, false, this.f543u);
        }
        float descent = ((this.f546x.descent() - this.f546x.ascent()) / 2.0f) - this.f546x.descent();
        String[] strArr = this.f522H;
        int length = strArr.length;
        while (i < length) {
            String str = strArr[i];
            canvas.drawText(str, ((float) (getWidth() / 2)) - (this.f546x.measureText(str) / 2.0f), ((float) (getHeight() / 2)) + descent, this.f546x);
            i++;
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        measuredWidth = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        if (paddingLeft <= measuredWidth) {
            measuredWidth = paddingLeft;
        }
        setMeasuredDimension((getPaddingLeft() + measuredWidth) + getPaddingRight(), (measuredWidth + getPaddingTop()) + getPaddingBottom());
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.f526d = i;
        this.f525c = i2;
        m765d();
        m764c();
        invalidate();
    }

    public void setBarColor(int i) {
        this.f538p = i;
    }

    public void setBarLength(int i) {
        this.f529g = i;
    }

    public void setBarWidth(int i) {
        this.f530h = i;
    }

    public void setCircleColor(int i) {
        this.f540r = i;
    }

    public void setCircleRadius(int i) {
        this.f528f = i;
    }

    public void setDelayMillis(int i) {
        this.f519E = i;
    }

    public void setPaddingBottom(int i) {
        this.f535m = i;
    }

    public void setPaddingLeft(int i) {
        this.f536n = i;
    }

    public void setPaddingRight(int i) {
        this.f537o = i;
    }

    public void setPaddingTop(int i) {
        this.f534l = i;
    }

    public void setProgress(int i) {
        this.f524b = false;
        this.f523a = i;
        this.f520F.sendEmptyMessage(0);
    }

    public void setRimColor(int i) {
        this.f541s = i;
    }

    public void setRimShader(Shader shader) {
        this.f545w.setShader(shader);
    }

    public void setRimWidth(int i) {
        this.f531i = i;
    }

    public void setSpinSpeed(int i) {
        this.f518D = i;
    }

    public void setText(String str) {
        this.f521G = str;
        this.f522H = this.f521G.split("\n");
    }

    public void setTextColor(int i) {
        this.f542t = i;
    }

    public void setTextSize(int i) {
        this.f532j = i;
    }
}
