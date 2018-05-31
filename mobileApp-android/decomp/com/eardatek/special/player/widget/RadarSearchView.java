package com.eardatek.special.player.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.eardatek.special.atsc.R;

public class RadarSearchView extends C0565a {
    int[] f550b = new int[]{123, 123, 123};
    int[] f551c = new int[]{185, 255, 255};
    int[] f552d = new int[]{223, 255, 255};
    int[] f553e = new int[]{236, 255, 255};
    int[] f554f = new int[]{243, 243, 250};
    private long f555g = 1500;
    private float f556h = 0.0f;
    private boolean f557i = false;
    private Bitmap f558j;
    private Bitmap f559k;
    private Bitmap f560l;

    public RadarSearchView(Context context) {
        super(context);
        m769b();
    }

    public RadarSearchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m769b();
    }

    public RadarSearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m769b();
    }

    private void m768a(MotionEvent motionEvent) {
        if (!new RectF((float) ((getWidth() / 2) - (this.f559k.getWidth() / 2)), (float) ((getHeight() / 2) - (this.f559k.getHeight() / 2)), (float) ((getWidth() / 2) + (this.f559k.getWidth() / 2)), (float) ((getHeight() / 2) + (this.f559k.getHeight() / 2))).contains(motionEvent.getX(), motionEvent.getY())) {
            return;
        }
        if (m770a()) {
            setSearching(false);
        } else {
            setSearching(true);
        }
    }

    private void m769b() {
        if (this.f558j == null) {
            this.f558j = Bitmap.createBitmap(BitmapFactory.decodeResource(this.a.getResources(), R.drawable.gplus_search_bg));
        }
        if (this.f559k == null) {
            this.f559k = Bitmap.createBitmap(BitmapFactory.decodeResource(this.a.getResources(), R.drawable.locus_round_click));
        }
        if (this.f560l == null) {
            this.f560l = Bitmap.createBitmap(BitmapFactory.decodeResource(this.a.getResources(), R.drawable.gplus_search_args));
        }
    }

    public boolean m770a() {
        return this.f557i;
    }

    @SuppressLint({"DrawAllocation"})
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(this.f558j, (float) ((getWidth() / 2) - (this.f558j.getWidth() / 2)), (float) ((getHeight() / 2) - (this.f558j.getHeight() / 2)), null);
        if (this.f557i) {
            Rect rect = new Rect((getWidth() / 2) - this.f560l.getWidth(), getHeight() / 2, getWidth() / 2, (getHeight() / 2) + this.f560l.getHeight());
            canvas.rotate(this.f556h, (float) (getWidth() / 2), (float) (getHeight() / 2));
            canvas.drawBitmap(this.f560l, null, rect, null);
            this.f556h += 3.0f;
        } else {
            canvas.drawBitmap(this.f560l, (float) ((getWidth() / 2) - this.f560l.getWidth()), (float) (getHeight() / 2), null);
        }
        canvas.drawBitmap(this.f559k, (float) ((getWidth() / 2) - (this.f559k.getWidth() / 2)), (float) ((getHeight() / 2) - (this.f559k.getHeight() / 2)), null);
        if (this.f557i) {
            invalidate();
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                m768a(motionEvent);
                return true;
            case 1:
            case 2:
                return true;
            default:
                return super.onTouchEvent(motionEvent);
        }
    }

    public void setSearching(boolean z) {
        this.f557i = z;
        this.f556h = 0.0f;
        invalidate();
    }
}
