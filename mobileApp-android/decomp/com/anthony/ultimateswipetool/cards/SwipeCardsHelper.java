package com.anthony.ultimateswipetool.cards;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.OvershootInterpolator;
import com.anthony.ultimateswipetool.SwipeHelper.AnimationEndListener;

public class SwipeCardsHelper implements OnTouchListener {
    private int mAnimationDuration = SwipeCards.DEFAULT_ANIMATION_DURATION;
    private float mDownX;
    private float mDownY;
    private float mInitialX;
    private float mInitialY;
    private boolean mListenForTouchEvents;
    private View mObservedView;
    private float mOpacityEnd = 1.0f;
    private int mPointerId;
    private float mRotateDegrees = SwipeCards.DEFAULT_SWIPE_ROTATION;
    private final SwipeCards mSwipeCards;

    class C04101 extends AnimationEndListener {
        C04101() {
        }

        public void onAnimationEnd(Animator animator) {
            SwipeCardsHelper.this.mSwipeCards.onViewSwipedToLeft();
        }
    }

    class C04112 extends AnimationEndListener {
        C04112() {
        }

        public void onAnimationEnd(Animator animator) {
            SwipeCardsHelper.this.mSwipeCards.onViewSwipedToRight();
        }
    }

    public SwipeCardsHelper(SwipeCards swipeCards) {
        this.mSwipeCards = swipeCards;
    }

    private void checkViewPosition() {
        if (this.mSwipeCards.isEnabled()) {
            float x = this.mObservedView.getX() + ((float) (this.mObservedView.getWidth() / 2));
            float width = ((float) this.mSwipeCards.getWidth()) / 3.0f;
            float f = 2.0f * width;
            if (x < width && this.mSwipeCards.getAllowedSwipeDirections() != 2) {
                swipeViewToLeft(this.mAnimationDuration / 2);
                return;
            } else if (x <= f || this.mSwipeCards.getAllowedSwipeDirections() == 1) {
                resetViewPosition();
                return;
            } else {
                swipeViewToRight(this.mAnimationDuration / 2);
                return;
            }
        }
        resetViewPosition();
    }

    private void resetViewPosition() {
        this.mObservedView.animate().x(this.mInitialX).y(this.mInitialY).rotation(0.0f).alpha(1.0f).setDuration((long) this.mAnimationDuration).setInterpolator(new OvershootInterpolator(1.4f)).setListener(null);
    }

    private void swipeViewToLeft(int i) {
        if (this.mListenForTouchEvents) {
            this.mListenForTouchEvents = false;
            this.mObservedView.animate().cancel();
            this.mObservedView.animate().x(((float) (-this.mSwipeCards.getWidth())) + this.mObservedView.getX()).rotation(-this.mRotateDegrees).alpha(0.0f).setDuration((long) i).setListener(new C04101());
        }
    }

    private void swipeViewToRight(int i) {
        if (this.mListenForTouchEvents) {
            this.mListenForTouchEvents = false;
            this.mObservedView.animate().cancel();
            this.mObservedView.animate().x(((float) this.mSwipeCards.getWidth()) + this.mObservedView.getX()).rotation(this.mRotateDegrees).alpha(0.0f).setDuration((long) i).setListener(new C04112());
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                if (!this.mListenForTouchEvents || !this.mSwipeCards.isEnabled()) {
                    return false;
                }
                view.getParent().requestDisallowInterceptTouchEvent(true);
                this.mSwipeCards.onSwipeStart();
                this.mPointerId = motionEvent.getPointerId(0);
                this.mDownX = motionEvent.getX(this.mPointerId);
                this.mDownY = motionEvent.getY(this.mPointerId);
                return true;
            case 1:
                view.getParent().requestDisallowInterceptTouchEvent(false);
                this.mSwipeCards.onSwipeEnd();
                checkViewPosition();
                return true;
            case 2:
                int findPointerIndex = motionEvent.findPointerIndex(this.mPointerId);
                if (findPointerIndex < 0) {
                    return false;
                }
                float x = motionEvent.getX(findPointerIndex) - this.mDownX;
                x += this.mObservedView.getX();
                float y = (motionEvent.getY(findPointerIndex) - this.mDownY) + this.mObservedView.getY();
                this.mObservedView.setX(x);
                this.mObservedView.setY(y);
                x = Math.min(Math.max((x - this.mInitialX) / ((float) this.mSwipeCards.getWidth()), -1.0f), 1.0f);
                this.mSwipeCards.onSwipeProgress(x);
                if (this.mRotateDegrees > 0.0f) {
                    this.mObservedView.setRotation(this.mRotateDegrees * x);
                }
                if (this.mOpacityEnd >= 1.0f) {
                    return true;
                }
                this.mObservedView.setAlpha(1.0f - Math.min(Math.abs(x * 2.0f), 1.0f));
                return true;
            default:
                return false;
        }
    }

    public void registerObservedView(View view, float f, float f2) {
        if (view != null) {
            this.mObservedView = view;
            this.mObservedView.setOnTouchListener(this);
            this.mInitialX = f;
            this.mInitialY = f2;
            this.mListenForTouchEvents = true;
        }
    }

    public void setAnimationDuration(int i) {
        this.mAnimationDuration = i;
    }

    public void setOpacityEnd(float f) {
        this.mOpacityEnd = f;
    }

    public void setRotation(float f) {
        this.mRotateDegrees = f;
    }

    public void swipeViewToLeft() {
        swipeViewToLeft(this.mAnimationDuration);
    }

    public void swipeViewToRight() {
        swipeViewToRight(this.mAnimationDuration);
    }

    public void unregisterObservedView() {
        if (this.mObservedView != null) {
            this.mObservedView.setOnTouchListener(null);
        }
        this.mObservedView = null;
        this.mListenForTouchEvents = false;
    }
}
