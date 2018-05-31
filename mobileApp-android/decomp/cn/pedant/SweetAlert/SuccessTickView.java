package cn.pedant.SweetAlert;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class SuccessTickView extends View {
    private final float CONST_LEFT_RECT_W = dip2px(15.0f);
    private final float CONST_RADIUS = dip2px(1.2f);
    private final float CONST_RECT_WEIGHT = dip2px(3.0f);
    private final float CONST_RIGHT_RECT_W = dip2px(25.0f);
    private final float MAX_RIGHT_RECT_W = (this.CONST_RIGHT_RECT_W + dip2px(6.7f));
    private final float MIN_LEFT_RECT_W = dip2px(3.3f);
    private float mDensity = -1.0f;
    private boolean mLeftRectGrowMode;
    private float mLeftRectWidth;
    private float mMaxLeftRectWidth;
    private Paint mPaint;
    private float mRightRectWidth;

    class C03741 extends Animation {
        C03741() {
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            if (0.54d < ((double) f) && 0.7d >= ((double) f)) {
                SuccessTickView.this.mLeftRectGrowMode = true;
                SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.mMaxLeftRectWidth * ((f - 0.54f) / 0.16f);
                if (0.65d < ((double) f)) {
                    SuccessTickView.this.mRightRectWidth = SuccessTickView.this.MAX_RIGHT_RECT_W * ((f - 0.65f) / 0.19f);
                }
                SuccessTickView.this.invalidate();
            } else if (0.7d < ((double) f) && 0.84d >= ((double) f)) {
                SuccessTickView.this.mLeftRectGrowMode = false;
                SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.mMaxLeftRectWidth * (1.0f - ((f - 0.7f) / 0.14f));
                SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.mLeftRectWidth < SuccessTickView.this.MIN_LEFT_RECT_W ? SuccessTickView.this.MIN_LEFT_RECT_W : SuccessTickView.this.mLeftRectWidth;
                SuccessTickView.this.mRightRectWidth = SuccessTickView.this.MAX_RIGHT_RECT_W * ((f - 0.65f) / 0.19f);
                SuccessTickView.this.invalidate();
            } else if (0.84d < ((double) f) && 1.0f >= f) {
                SuccessTickView.this.mLeftRectGrowMode = false;
                SuccessTickView.this.mLeftRectWidth = SuccessTickView.this.MIN_LEFT_RECT_W + ((SuccessTickView.this.CONST_LEFT_RECT_W - SuccessTickView.this.MIN_LEFT_RECT_W) * ((f - 0.84f) / 0.16f));
                SuccessTickView.this.mRightRectWidth = SuccessTickView.this.CONST_RIGHT_RECT_W + ((SuccessTickView.this.MAX_RIGHT_RECT_W - SuccessTickView.this.CONST_RIGHT_RECT_W) * (1.0f - ((f - 0.84f) / 0.16f)));
                SuccessTickView.this.invalidate();
            }
        }
    }

    public SuccessTickView(Context context) {
        super(context);
        init();
    }

    public SuccessTickView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mPaint.setColor(getResources().getColor(C0373R.color.success_stroke_color));
        this.mLeftRectWidth = this.CONST_LEFT_RECT_W;
        this.mRightRectWidth = this.CONST_RIGHT_RECT_W;
        this.mLeftRectGrowMode = false;
    }

    public float dip2px(float f) {
        if (this.mDensity == -1.0f) {
            this.mDensity = getResources().getDisplayMetrics().density;
        }
        return (this.mDensity * f) + 0.5f;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width = getWidth();
        int height = getHeight();
        canvas.rotate(45.0f, (float) (width / 2), (float) (height / 2));
        width = (int) (((double) width) / 1.2d);
        height = (int) (((double) height) / 1.4d);
        this.mMaxLeftRectWidth = (((((float) width) + this.CONST_LEFT_RECT_W) / 2.0f) + this.CONST_RECT_WEIGHT) - 1.0f;
        RectF rectF = new RectF();
        if (this.mLeftRectGrowMode) {
            rectF.left = 0.0f;
            rectF.right = rectF.left + this.mLeftRectWidth;
            rectF.top = (((float) height) + this.CONST_RIGHT_RECT_W) / 2.0f;
            rectF.bottom = rectF.top + this.CONST_RECT_WEIGHT;
        } else {
            rectF.right = (((((float) width) + this.CONST_LEFT_RECT_W) / 2.0f) + this.CONST_RECT_WEIGHT) - 1.0f;
            rectF.left = rectF.right - this.mLeftRectWidth;
            rectF.top = (((float) height) + this.CONST_RIGHT_RECT_W) / 2.0f;
            rectF.bottom = rectF.top + this.CONST_RECT_WEIGHT;
        }
        canvas.drawRoundRect(rectF, this.CONST_RADIUS, this.CONST_RADIUS, this.mPaint);
        rectF = new RectF();
        rectF.bottom = (((((float) height) + this.CONST_RIGHT_RECT_W) / 2.0f) + this.CONST_RECT_WEIGHT) - 1.0f;
        rectF.left = (((float) width) + this.CONST_LEFT_RECT_W) / 2.0f;
        rectF.right = rectF.left + this.CONST_RECT_WEIGHT;
        rectF.top = rectF.bottom - this.mRightRectWidth;
        canvas.drawRoundRect(rectF, this.CONST_RADIUS, this.CONST_RADIUS, this.mPaint);
    }

    public void startTickAnim() {
        this.mLeftRectWidth = 0.0f;
        this.mRightRectWidth = 0.0f;
        invalidate();
        Animation c03741 = new C03741();
        c03741.setDuration(750);
        c03741.setStartOffset(100);
        startAnimation(c03741);
    }
}
