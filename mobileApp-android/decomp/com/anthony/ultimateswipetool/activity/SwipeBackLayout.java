package com.anthony.ultimateswipetool.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.anthony.ultimateswipetool.C0404R;
import com.anthony.ultimateswipetool.activity.ViewDragHelper.Callback;
import java.util.ArrayList;
import java.util.List;

public class SwipeBackLayout extends FrameLayout {
    private static final int DEFAULT_SCRIM_COLOR = -1728053248;
    private static final float DEFAULT_SCROLL_THRESHOLD = 0.3f;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    private static final int[] EDGE_FLAGS = new int[]{1, 2, 8, 15};
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_TOP = 4;
    private static final int FULL_ALPHA = 255;
    private static final int MIN_FLING_VELOCITY = 400;
    private static final int OVERSCROLL_DISTANCE = 10;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private Activity mActivity;
    private int mContentLeft;
    private int mContentTop;
    private View mContentView;
    private ViewDragHelper mDragHelper;
    private int mEdgeFlag;
    private boolean mEnable;
    private boolean mInLayout;
    private List<SwipeListener> mListeners;
    private int mScrimColor;
    private float mScrimOpacity;
    private float mScrollPercent;
    private float mScrollThreshold;
    private Drawable mShadowBottom;
    private Drawable mShadowLeft;
    private Drawable mShadowRight;
    private Drawable mShadowTop;
    private Rect mTmpRect;
    private int mTrackingEdge;

    public interface SwipeListener {
        void onEdgeTouch(int i);

        void onScrollOverThreshold();

        void onScrollStateChange(int i, float f);
    }

    private class ViewDragCallback extends Callback {
        private boolean mIsScrollOverValid;

        private ViewDragCallback() {
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            return (SwipeBackLayout.this.mTrackingEdge & 1) != 0 ? Math.min(view.getWidth(), Math.max(i, 0)) : (SwipeBackLayout.this.mTrackingEdge & 2) != 0 ? Math.min(0, Math.max(i, -view.getWidth())) : 0;
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return (SwipeBackLayout.this.mTrackingEdge & 4) != 0 ? Math.min(view.getHeight(), Math.max(i, 0)) : (SwipeBackLayout.this.mTrackingEdge & 8) != 0 ? Math.min(0, Math.max(i, -view.getHeight())) : 0;
        }

        public int getViewHorizontalDragRange(View view) {
            return SwipeBackLayout.this.mEdgeFlag & 3;
        }

        public int getViewVerticalDragRange(View view) {
            return SwipeBackLayout.this.mEdgeFlag & 12;
        }

        public void onViewDragStateChanged(int i) {
            super.onViewDragStateChanged(i);
            if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty()) {
                for (SwipeListener onScrollStateChange : SwipeBackLayout.this.mListeners) {
                    onScrollStateChange.onScrollStateChange(i, SwipeBackLayout.this.mScrollPercent);
                }
            }
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            super.onViewPositionChanged(view, i, i2, i3, i4);
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(((float) i) / ((float) (SwipeBackLayout.this.mContentView.getWidth() + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth())));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(((float) i) / ((float) (SwipeBackLayout.this.mContentView.getWidth() + SwipeBackLayout.this.mShadowRight.getIntrinsicWidth())));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(((float) i2) / ((float) (SwipeBackLayout.this.mContentView.getHeight() + SwipeBackLayout.this.mShadowBottom.getIntrinsicHeight())));
            } else if ((SwipeBackLayout.this.mTrackingEdge & 4) != 0) {
                SwipeBackLayout.this.mScrollPercent = Math.abs(((float) i2) / ((float) (SwipeBackLayout.this.mContentView.getHeight() + SwipeBackLayout.this.mShadowTop.getIntrinsicHeight())));
            }
            SwipeBackLayout.this.mContentLeft = i;
            SwipeBackLayout.this.mContentTop = i2;
            SwipeBackLayout.this.invalidate();
            if (SwipeBackLayout.this.mScrollPercent < SwipeBackLayout.this.mScrollThreshold && !this.mIsScrollOverValid) {
                this.mIsScrollOverValid = true;
            }
            if (SwipeBackLayout.this.mListeners != null && !SwipeBackLayout.this.mListeners.isEmpty() && SwipeBackLayout.this.mDragHelper.getViewDragState() == 1 && SwipeBackLayout.this.mScrollPercent >= SwipeBackLayout.this.mScrollThreshold && this.mIsScrollOverValid) {
                this.mIsScrollOverValid = false;
                for (SwipeListener onScrollOverThreshold : SwipeBackLayout.this.mListeners) {
                    onScrollOverThreshold.onScrollOverThreshold();
                }
            }
            if (SwipeBackLayout.this.mScrollPercent >= 1.0f && !SwipeBackLayout.this.mActivity.isFinishing()) {
                SwipeBackLayout.this.mActivity.finish();
                SwipeBackLayout.this.mActivity.overridePendingTransition(0, 0);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            int i = 0;
            int width = view.getWidth();
            int height = view.getHeight();
            if ((SwipeBackLayout.this.mTrackingEdge & 1) != 0) {
                width = (f > 0.0f || (f == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? (width + SwipeBackLayout.this.mShadowLeft.getIntrinsicWidth()) + 10 : 0;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 2) != 0) {
                width = (f < 0.0f || (f == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? -((width + SwipeBackLayout.this.mShadowRight.getIntrinsicWidth()) + 10) : 0;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 8) != 0) {
                width = (f2 < 0.0f || (f2 == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? -((SwipeBackLayout.this.mShadowBottom.getIntrinsicHeight() + height) + 10) : 0;
                r5 = width;
                width = 0;
                i = r5;
            } else if ((SwipeBackLayout.this.mTrackingEdge & 4) != 0) {
                width = (f2 > 0.0f || (f2 == 0.0f && SwipeBackLayout.this.mScrollPercent > SwipeBackLayout.this.mScrollThreshold)) ? (SwipeBackLayout.this.mShadowTop.getIntrinsicHeight() + height) + 10 : 0;
                r5 = width;
                width = 0;
                i = r5;
            } else {
                width = 0;
            }
            SwipeBackLayout.this.mDragHelper.settleCapturedViewAt(width, i);
            SwipeBackLayout.this.invalidate();
        }

        public boolean tryCaptureView(View view, int i) {
            int i2 = 1;
            boolean isEdgeTouched = SwipeBackLayout.this.mDragHelper.isEdgeTouched(SwipeBackLayout.this.mEdgeFlag, i);
            if (isEdgeTouched) {
                if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(1, i)) {
                    SwipeBackLayout.this.mTrackingEdge = 1;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(2, i)) {
                    SwipeBackLayout.this.mTrackingEdge = 2;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(8, i)) {
                    SwipeBackLayout.this.mTrackingEdge = 8;
                } else if (SwipeBackLayout.this.mDragHelper.isEdgeTouched(4, i)) {
                    SwipeBackLayout.this.mTrackingEdge = 4;
                }
                if (!(SwipeBackLayout.this.mListeners == null || SwipeBackLayout.this.mListeners.isEmpty())) {
                    for (SwipeListener onEdgeTouch : SwipeBackLayout.this.mListeners) {
                        onEdgeTouch.onEdgeTouch(SwipeBackLayout.this.mTrackingEdge);
                    }
                }
                this.mIsScrollOverValid = true;
            }
            if (SwipeBackLayout.this.mEdgeFlag == 1 || SwipeBackLayout.this.mEdgeFlag == 2) {
                i2 = !SwipeBackLayout.this.mDragHelper.checkTouchSlop(2, i) ? 1 : 0;
            } else if (SwipeBackLayout.this.mEdgeFlag == 8 || SwipeBackLayout.this.mEdgeFlag == 4) {
                if (SwipeBackLayout.this.mDragHelper.checkTouchSlop(1, i)) {
                    i2 = 0;
                }
            } else if (SwipeBackLayout.this.mEdgeFlag != 15) {
                i2 = 0;
            }
            return isEdgeTouched & i2;
        }
    }

    public SwipeBackLayout(Context context) {
        this(context, null);
    }

    public SwipeBackLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0404R.attr.SwipeBackLayoutStyle);
    }

    public SwipeBackLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mScrollThreshold = DEFAULT_SCROLL_THRESHOLD;
        this.mEnable = true;
        this.mScrimColor = DEFAULT_SCRIM_COLOR;
        this.mTmpRect = new Rect();
        this.mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0404R.styleable.SwipeBackLayout, i, C0404R.style.SwipeBackLayout);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(C0404R.styleable.SwipeBackLayout_edge_size, -1);
        if (dimensionPixelSize > 0) {
            setEdgeSize(dimensionPixelSize);
        }
        setEdgeTrackingEnabled(EDGE_FLAGS[obtainStyledAttributes.getInt(C0404R.styleable.SwipeBackLayout_edge_flag, 0)]);
        dimensionPixelSize = obtainStyledAttributes.getResourceId(C0404R.styleable.SwipeBackLayout_shadow_left, C0404R.drawable.shadow_left);
        int resourceId = obtainStyledAttributes.getResourceId(C0404R.styleable.SwipeBackLayout_shadow_right, C0404R.drawable.shadow_right);
        int resourceId2 = obtainStyledAttributes.getResourceId(C0404R.styleable.SwipeBackLayout_shadow_bottom, C0404R.drawable.shadow_bottom);
        int resourceId3 = obtainStyledAttributes.getResourceId(C0404R.styleable.SwipeBackLayout_shadow_top, C0404R.drawable.shadow_top);
        setShadow(dimensionPixelSize, 1);
        setShadow(resourceId, 2);
        setShadow(resourceId2, 8);
        setShadow(resourceId3, 4);
        obtainStyledAttributes.recycle();
        float f = getResources().getDisplayMetrics().density * 400.0f;
        this.mDragHelper.setMinVelocity(f);
        this.mDragHelper.setMaxVelocity(f * 2.0f);
    }

    private void drawScrim(Canvas canvas, View view) {
        int i = (((int) (((float) ((this.mScrimColor & ViewCompat.MEASURED_STATE_MASK) >>> 24)) * this.mScrimOpacity)) << 24) | (this.mScrimColor & ViewCompat.MEASURED_SIZE_MASK);
        if ((this.mTrackingEdge & 1) != 0) {
            canvas.clipRect(0, 0, view.getLeft(), getHeight());
        } else if ((this.mTrackingEdge & 2) != 0) {
            canvas.clipRect(view.getRight(), 0, getRight(), getHeight());
        } else if ((this.mTrackingEdge & 8) != 0) {
            canvas.clipRect(view.getLeft(), view.getBottom(), getRight(), getHeight());
        } else if ((this.mTrackingEdge & 4) != 0) {
            canvas.clipRect(view.getLeft(), getHeight(), getRight(), view.getTop());
        }
        canvas.drawColor(i);
    }

    private void drawShadow(Canvas canvas, View view) {
        Rect rect = this.mTmpRect;
        view.getHitRect(rect);
        if ((this.mEdgeFlag & 1) != 0) {
            this.mShadowLeft.setBounds(rect.left - this.mShadowLeft.getIntrinsicWidth(), rect.top, rect.left, rect.bottom);
            this.mShadowLeft.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowLeft.draw(canvas);
        }
        if ((this.mEdgeFlag & 2) != 0) {
            this.mShadowRight.setBounds(rect.right, rect.top, rect.right + this.mShadowRight.getIntrinsicWidth(), rect.bottom);
            this.mShadowRight.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowRight.draw(canvas);
        }
        if ((this.mEdgeFlag & 8) != 0) {
            this.mShadowBottom.setBounds(rect.left, rect.bottom, rect.right, rect.bottom + this.mShadowBottom.getIntrinsicHeight());
            this.mShadowBottom.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowBottom.draw(canvas);
        }
        if ((this.mEdgeFlag & 4) != 0) {
            this.mShadowTop.setBounds(rect.left, rect.top - this.mShadowTop.getIntrinsicHeight(), rect.right, rect.top);
            this.mShadowTop.setAlpha((int) (this.mScrimOpacity * 255.0f));
            this.mShadowTop.draw(canvas);
        }
    }

    private void setContentView(View view) {
        this.mContentView = view;
    }

    public void addSwipeListener(SwipeListener swipeListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(swipeListener);
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        TypedArray obtainStyledAttributes = activity.getTheme().obtainStyledAttributes(new int[]{16842836});
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        ViewGroup viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.getChildAt(0);
        viewGroup2.setBackgroundResource(resourceId);
        viewGroup.removeView(viewGroup2);
        addView(viewGroup2);
        setContentView(viewGroup2);
        viewGroup.addView(this);
    }

    public void computeScroll() {
        this.mScrimOpacity = 1.0f - this.mScrollPercent;
        if (this.mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        Object obj = view == this.mContentView ? 1 : null;
        boolean drawChild = super.drawChild(canvas, view, j);
        if (!(this.mScrimOpacity <= 0.0f || obj == null || this.mDragHelper.getViewDragState() == 0)) {
            drawShadow(canvas, view);
            drawScrim(canvas, view);
        }
        return drawChild;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (this.mEnable) {
            try {
                z = this.mDragHelper.shouldInterceptTouchEvent(motionEvent);
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        return z;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mInLayout = true;
        if (this.mContentView != null) {
            this.mContentView.layout(this.mContentLeft, this.mContentTop, this.mContentLeft + this.mContentView.getMeasuredWidth(), this.mContentTop + this.mContentView.getMeasuredHeight());
        }
        this.mInLayout = false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnable) {
            return false;
        }
        this.mDragHelper.processTouchEvent(motionEvent);
        return true;
    }

    public void removeSwipeListener(SwipeListener swipeListener) {
        if (this.mListeners != null) {
            this.mListeners.remove(swipeListener);
        }
    }

    public void requestLayout() {
        if (!this.mInLayout) {
            super.requestLayout();
        }
    }

    public void scrollToFinishActivity() {
        int i = 0;
        int width = this.mContentView.getWidth();
        int height = this.mContentView.getHeight();
        if ((this.mEdgeFlag & 1) != 0) {
            width = (width + this.mShadowLeft.getIntrinsicWidth()) + 10;
            this.mTrackingEdge = 1;
        } else if ((this.mEdgeFlag & 2) != 0) {
            width = ((-width) - this.mShadowRight.getIntrinsicWidth()) - 10;
            this.mTrackingEdge = 2;
        } else if ((this.mEdgeFlag & 8) != 0) {
            width = ((-height) - this.mShadowBottom.getIntrinsicHeight()) - 10;
            this.mTrackingEdge = 8;
            r4 = width;
            width = 0;
            i = r4;
        } else if ((this.mEdgeFlag & 4) != 0) {
            width = ((-height) + this.mShadowTop.getIntrinsicHeight()) - 10;
            this.mTrackingEdge = 4;
            r4 = width;
            width = 0;
            i = r4;
        } else {
            width = 0;
        }
        this.mDragHelper.smoothSlideViewTo(this.mContentView, width, i);
        invalidate();
    }

    public void setEdgeSize(int i) {
        this.mDragHelper.setEdgeSize(i);
    }

    public void setEdgeTrackingEnabled(int i) {
        this.mEdgeFlag = i;
        this.mDragHelper.setEdgeTrackingEnabled(this.mEdgeFlag);
    }

    public void setEnableGesture(boolean z) {
        this.mEnable = z;
    }

    public void setScrimColor(int i) {
        this.mScrimColor = i;
        invalidate();
    }

    public void setSensitivity(Context context, float f) {
        this.mDragHelper.setSensitivity(context, f);
    }

    public void setShadow(int i, int i2) {
        setShadow(getResources().getDrawable(i), i2);
    }

    public void setShadow(Drawable drawable, int i) {
        if ((i & 1) != 0) {
            this.mShadowLeft = drawable;
        } else if ((i & 2) != 0) {
            this.mShadowRight = drawable;
        } else if ((i & 8) != 0) {
            this.mShadowBottom = drawable;
        } else if ((i & 4) != 0) {
            this.mShadowTop = drawable;
        }
        invalidate();
    }

    @Deprecated
    public void setSwipeListener(SwipeListener swipeListener) {
        addSwipeListener(swipeListener);
    }
}
