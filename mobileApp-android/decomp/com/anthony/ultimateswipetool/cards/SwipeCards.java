package com.anthony.ultimateswipetool.cards;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import com.anthony.ultimateswipetool.C0404R;
import java.util.Random;

public class SwipeCards extends ViewGroup {
    public static final int DEFAULT_ANIMATION_DURATION = 300;
    public static final boolean DEFAULT_DISABLE_HW_ACCELERATION = true;
    public static final float DEFAULT_SCALE_FACTOR = 1.0f;
    public static final int DEFAULT_STACK_ROTATION = 8;
    public static final int DEFAULT_STACK_SIZE = 3;
    public static final float DEFAULT_SWIPE_OPACITY = 1.0f;
    public static final float DEFAULT_SWIPE_ROTATION = 30.0f;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_SUPER_STATE = "superState";
    public static final int SWIPE_DIRECTION_BOTH = 0;
    public static final int SWIPE_DIRECTION_ONLY_LEFT = 1;
    public static final int SWIPE_DIRECTION_ONLY_RIGHT = 2;
    private Adapter mAdapter;
    private int mAllowedSwipeDirections;
    private int mAnimationDuration;
    private int mCurrentViewIndex;
    private DataSetObserver mDataObserver;
    private boolean mDisableHwAcceleration;
    private boolean mIsFirstLayout;
    private SwipeCardsListener mListener;
    private int mNumberOfStackedViews;
    private SwipeProgressListener mProgressListener;
    private Random mRandom;
    private float mScaleFactor;
    private SwipeCardsHelper mSwipeCardsHelper;
    private float mSwipeOpacity;
    private float mSwipeRotation;
    private View mTopView;
    private int mViewRotation;
    private int mViewSpacing;

    class C04091 extends DataSetObserver {
        C04091() {
        }

        public void onChanged() {
            super.onChanged();
            SwipeCards.this.invalidate();
            SwipeCards.this.requestLayout();
        }
    }

    public interface SwipeCardsListener {
        void onCardsEmpty();

        void onViewSwipedToLeft(int i);

        void onViewSwipedToRight(int i);
    }

    public interface SwipeProgressListener {
        void onSwipeEnd(int i);

        void onSwipeProgress(int i, float f);

        void onSwipeStart(int i);
    }

    public SwipeCards(Context context) {
        this(context, null);
    }

    public SwipeCards(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SwipeCards(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsFirstLayout = true;
        readAttributes(attributeSet);
        initialize();
    }

    private void addNextView() {
        int i = 1073741824;
        if (this.mCurrentViewIndex < this.mAdapter.getCount()) {
            View view = this.mAdapter.getView(this.mCurrentViewIndex, null, this);
            view.setTag(C0404R.id.new_view, Boolean.valueOf(true));
            if (!this.mDisableHwAcceleration) {
                view.setLayerType(2, null);
            }
            if (this.mViewRotation > 0) {
                view.setRotation((float) (this.mRandom.nextInt(this.mViewRotation) - (this.mViewRotation / 2)));
            }
            int width = getWidth() - (getPaddingLeft() + getPaddingRight());
            int height = getHeight() - (getPaddingTop() + getPaddingBottom());
            LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(-2, -2);
            }
            int i2 = layoutParams.width == -1 ? 1073741824 : Integer.MIN_VALUE;
            if (layoutParams.height != -1) {
                i = Integer.MIN_VALUE;
            }
            view.measure(i2 | width, i | height);
            addViewInLayout(view, 0, layoutParams, true);
            this.mCurrentViewIndex++;
        }
    }

    private void initialize() {
        this.mRandom = new Random();
        setClipToPadding(false);
        setClipChildren(false);
        this.mSwipeCardsHelper = new SwipeCardsHelper(this);
        this.mSwipeCardsHelper.setAnimationDuration(this.mAnimationDuration);
        this.mSwipeCardsHelper.setRotation(this.mSwipeRotation);
        this.mSwipeCardsHelper.setOpacityEnd(this.mSwipeOpacity);
        this.mDataObserver = new C04091();
    }

    private void readAttributes(AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C0404R.styleable.SwipeCards);
        try {
            this.mAllowedSwipeDirections = obtainStyledAttributes.getInt(C0404R.styleable.SwipeCards_allowed_swipe_directions, 0);
            this.mAnimationDuration = obtainStyledAttributes.getInt(C0404R.styleable.SwipeCards_animation_duration, DEFAULT_ANIMATION_DURATION);
            this.mNumberOfStackedViews = obtainStyledAttributes.getInt(C0404R.styleable.SwipeCards_stack_size, 3);
            this.mViewSpacing = obtainStyledAttributes.getDimensionPixelSize(C0404R.styleable.SwipeCards_stack_spacing, getResources().getDimensionPixelSize(C0404R.dimen.default_stack_spacing));
            this.mViewRotation = obtainStyledAttributes.getInt(C0404R.styleable.SwipeCards_stack_rotation, 8);
            this.mSwipeRotation = obtainStyledAttributes.getFloat(C0404R.styleable.SwipeCards_swipe_rotation, DEFAULT_SWIPE_ROTATION);
            this.mSwipeOpacity = obtainStyledAttributes.getFloat(C0404R.styleable.SwipeCards_swipe_opacity, 1.0f);
            this.mScaleFactor = obtainStyledAttributes.getFloat(C0404R.styleable.SwipeCards_scale_factor, 1.0f);
            this.mDisableHwAcceleration = obtainStyledAttributes.getBoolean(C0404R.styleable.SwipeCards_disable_hw_acceleration, true);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    private void removeTopView() {
        if (this.mTopView != null) {
            removeView(this.mTopView);
            this.mTopView = null;
        }
        if (getChildCount() == 0 && this.mListener != null) {
            this.mListener.onCardsEmpty();
        }
    }

    private void reorderItems() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            int childCount = getChildCount() - 1;
            int width = (getWidth() - childAt.getMeasuredWidth()) / 2;
            int paddingTop = getPaddingTop() + ((this.mViewSpacing * childCount) - (this.mViewSpacing * i));
            childAt.layout(width, getPaddingTop(), childAt.getMeasuredWidth() + width, getPaddingTop() + childAt.getMeasuredHeight());
            if (VERSION.SDK_INT >= 21) {
                childAt.setTranslationZ((float) i);
            }
            boolean booleanValue = ((Boolean) childAt.getTag(C0404R.id.new_view)).booleanValue();
            float pow = (float) Math.pow((double) this.mScaleFactor, (double) (getChildCount() - i));
            if (i == childCount) {
                this.mSwipeCardsHelper.unregisterObservedView();
                this.mTopView = childAt;
                this.mSwipeCardsHelper.registerObservedView(this.mTopView, (float) width, (float) paddingTop);
            }
            if (this.mIsFirstLayout) {
                childAt.setTag(C0404R.id.new_view, Boolean.valueOf(false));
                childAt.setY((float) paddingTop);
                childAt.setScaleY(pow);
                childAt.setScaleX(pow);
            } else {
                if (booleanValue) {
                    childAt.setTag(C0404R.id.new_view, Boolean.valueOf(false));
                    childAt.setAlpha(0.0f);
                    childAt.setY((float) paddingTop);
                    childAt.setScaleY(pow);
                    childAt.setScaleX(pow);
                }
                childAt.animate().y((float) paddingTop).scaleX(pow).scaleY(pow).alpha(1.0f).setDuration((long) this.mAnimationDuration);
            }
        }
    }

    public Adapter getAdapter() {
        return this.mAdapter;
    }

    public int getAllowedSwipeDirections() {
        return this.mAllowedSwipeDirections;
    }

    public int getCurrentPosition() {
        return this.mCurrentViewIndex - getChildCount();
    }

    public View getTopView() {
        return this.mTopView;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mAdapter == null || this.mAdapter.isEmpty()) {
            this.mCurrentViewIndex = 0;
            removeAllViewsInLayout();
            return;
        }
        for (int childCount = getChildCount(); childCount < this.mNumberOfStackedViews && this.mCurrentViewIndex < this.mAdapter.getCount(); childCount++) {
            addNextView();
        }
        reorderItems();
        this.mIsFirstLayout = false;
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.getSize(i), MeasureSpec.getSize(i2));
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mCurrentViewIndex = bundle.getInt(KEY_CURRENT_INDEX);
            parcelable = bundle.getParcelable(KEY_SUPER_STATE);
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Parcelable bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_CURRENT_INDEX, this.mCurrentViewIndex - getChildCount());
        return bundle;
    }

    public void onSwipeEnd() {
        if (this.mProgressListener != null) {
            this.mProgressListener.onSwipeEnd(getCurrentPosition());
        }
    }

    public void onSwipeProgress(float f) {
        if (this.mProgressListener != null) {
            this.mProgressListener.onSwipeProgress(getCurrentPosition(), f);
        }
    }

    public void onSwipeStart() {
        if (this.mProgressListener != null) {
            this.mProgressListener.onSwipeStart(getCurrentPosition());
        }
    }

    public void onViewSwipedToLeft() {
        if (this.mListener != null) {
            this.mListener.onViewSwipedToLeft(getCurrentPosition());
        }
        removeTopView();
    }

    public void onViewSwipedToRight() {
        if (this.mListener != null) {
            this.mListener.onViewSwipedToRight(getCurrentPosition());
        }
        removeTopView();
    }

    public void resetStack() {
        this.mCurrentViewIndex = 0;
        removeAllViewsInLayout();
        requestLayout();
    }

    public void setAdapter(Adapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataObserver);
        }
        this.mAdapter = adapter;
        this.mAdapter.registerDataSetObserver(this.mDataObserver);
    }

    public void setAllowedSwipeDirections(int i) {
        this.mAllowedSwipeDirections = i;
    }

    public void setListener(@Nullable SwipeCardsListener swipeCardsListener) {
        this.mListener = swipeCardsListener;
    }

    public void setSwipeProgressListener(@Nullable SwipeProgressListener swipeProgressListener) {
        this.mProgressListener = swipeProgressListener;
    }

    public void swipeTopViewToLeft() {
        if (getChildCount() != 0) {
            this.mSwipeCardsHelper.swipeViewToLeft();
        }
    }

    public void swipeTopViewToRight() {
        if (getChildCount() != 0) {
            this.mSwipeCardsHelper.swipeViewToRight();
        }
    }
}
