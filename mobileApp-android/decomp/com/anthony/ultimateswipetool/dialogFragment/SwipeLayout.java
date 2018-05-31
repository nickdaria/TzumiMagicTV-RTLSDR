package com.anthony.ultimateswipetool.dialogFragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import com.anthony.ultimateswipetool.C0404R;

public class SwipeLayout extends FrameLayout implements OnTouchListener {
    private long mAnimationTime;
    private DismissCallbacks mCallbacks;
    private View mContentView;
    private Context mContext;
    private float mDownX;
    private float mDownY;
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private int mSlop;
    private boolean mSwiping;
    private int mSwipingSlop;
    private boolean mTiltEnabled;
    private Object mToken;
    private float mTranslationX;
    private VelocityTracker mVelocityTracker;
    private View mView;
    private int mViewWidth;

    public interface DismissCallbacks {
        boolean canDismiss(Object obj);

        void onDismiss(View view, boolean z, Object obj);
    }

    public SwipeLayout(Context context) {
        this(context, null);
    }

    public SwipeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C0404R.attr.SwipeBackLayoutStyle);
    }

    public SwipeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mViewWidth = 1;
        this.mTiltEnabled = true;
        this.mContext = context;
        init();
    }

    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mAnimationTime = (long) this.mContext.getResources().getInteger(17694720);
        setOnTouchListener(this);
    }

    private void performDismiss(final boolean z) {
        final LayoutParams layoutParams = this.mView.getLayoutParams();
        final int height = this.mView.getHeight();
        ValueAnimator duration = ValueAnimator.ofInt(new int[]{height, 1}).setDuration(this.mAnimationTime);
        duration.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                SwipeLayout.this.mCallbacks.onDismiss(SwipeLayout.this.mView, z, SwipeLayout.this.mToken);
                SwipeLayout.this.mView.setAlpha(1.0f);
                SwipeLayout.this.mView.setTranslationX(0.0f);
                SwipeLayout.this.mView.setRotation(0.0f);
                layoutParams.height = height;
                SwipeLayout.this.mView.setLayoutParams(layoutParams);
            }
        });
        duration.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                SwipeLayout.this.mView.setLayoutParams(layoutParams);
            }
        });
        duration.start();
    }

    public void addSwipeListener(View view, Object obj, DismissCallbacks dismissCallbacks) {
        this.mCallbacks = dismissCallbacks;
        this.mView = view;
        this.mToken = obj;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z = true;
        motionEvent.offsetLocation(this.mTranslationX, 0.0f);
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mView.getWidth();
        }
        float rawX;
        float xVelocity;
        switch (motionEvent.getActionMasked()) {
            case 0:
                this.mDownX = motionEvent.getRawX();
                this.mDownY = motionEvent.getRawY();
                if (!this.mCallbacks.canDismiss(this.mToken)) {
                    return false;
                }
                this.mVelocityTracker = VelocityTracker.obtain();
                this.mVelocityTracker.addMovement(motionEvent);
                return false;
            case 1:
                if (this.mVelocityTracker == null) {
                    return false;
                }
                boolean z2;
                rawX = motionEvent.getRawX() - this.mDownX;
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                xVelocity = this.mVelocityTracker.getXVelocity();
                float abs = Math.abs(xVelocity);
                float abs2 = Math.abs(this.mVelocityTracker.getYVelocity());
                if (Math.abs(rawX) > ((float) (this.mViewWidth / 2)) && this.mSwiping) {
                    boolean z3 = rawX > 0.0f;
                    z2 = true;
                    z = z3;
                } else if (((float) this.mMinFlingVelocity) > abs || abs > ((float) this.mMaxFlingVelocity) || abs2 >= abs || abs2 >= abs || !this.mSwiping) {
                    z = false;
                    z2 = false;
                } else {
                    z2 = ((xVelocity > 0.0f ? 1 : (xVelocity == 0.0f ? 0 : -1)) < 0) == ((rawX > 0.0f ? 1 : (rawX == 0.0f ? 0 : -1)) < 0);
                    if (this.mVelocityTracker.getXVelocity() <= 0.0f) {
                        z = false;
                    }
                }
                if (z2) {
                    ViewPropertyAnimator translationX = this.mView.animate().translationX(z ? (float) this.mViewWidth : (float) (-this.mViewWidth));
                    if (this.mTiltEnabled) {
                        rawX = (float) (z ? 45 : -45);
                    } else {
                        rawX = 0.0f;
                    }
                    translationX.rotation(rawX).alpha(0.0f).setDuration(this.mAnimationTime).setListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            SwipeLayout.this.performDismiss(z);
                        }
                    });
                } else if (this.mSwiping) {
                    this.mView.animate().translationX(0.0f).rotation(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener(null);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                this.mTranslationX = 0.0f;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mSwiping = false;
                return false;
            case 2:
                if (this.mVelocityTracker == null) {
                    return false;
                }
                this.mVelocityTracker.addMovement(motionEvent);
                xVelocity = motionEvent.getRawX() - this.mDownX;
                rawX = motionEvent.getRawY() - this.mDownY;
                if (Math.abs(xVelocity) > ((float) this.mSlop) && Math.abs(rawX) < Math.abs(xVelocity) / 2.0f) {
                    this.mSwiping = true;
                    this.mSwipingSlop = xVelocity > 0.0f ? this.mSlop : -this.mSlop;
                    this.mView.getParent().requestDisallowInterceptTouchEvent(true);
                    MotionEvent obtain = MotionEvent.obtain(motionEvent);
                    obtain.setAction((motionEvent.getActionIndex() << 8) | 3);
                    this.mView.onTouchEvent(obtain);
                    obtain.recycle();
                }
                if (!this.mSwiping) {
                    return false;
                }
                this.mTranslationX = xVelocity;
                this.mView.setTranslationX(xVelocity - ((float) this.mSwipingSlop));
                this.mView.setRotation(this.mTiltEnabled ? (45.0f * xVelocity) / ((float) this.mViewWidth) : 0.0f);
                this.mView.setAlpha(Math.max(0.0f, Math.min(1.0f, 1.0f - ((2.0f * Math.abs(xVelocity)) / ((float) this.mViewWidth)))));
                return true;
            case 3:
                if (this.mVelocityTracker == null) {
                    return false;
                }
                this.mView.animate().translationX(0.0f).rotation(0.0f).alpha(1.0f).setDuration(this.mAnimationTime).setListener(null);
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                this.mTranslationX = 0.0f;
                this.mDownX = 0.0f;
                this.mDownY = 0.0f;
                this.mSwiping = false;
                return false;
            default:
                return false;
        }
    }

    public void setTiltEnabled(boolean z) {
        this.mTiltEnabled = z;
    }
}
