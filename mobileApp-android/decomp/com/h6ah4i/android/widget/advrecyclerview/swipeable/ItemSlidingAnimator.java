package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ItemSlidingAnimator {
    public static final int DIR_DOWN = 3;
    public static final int DIR_LEFT = 0;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_UP = 1;
    private static final String TAG = "ItemSlidingAnimator";
    private final List<ViewHolder> mActive;
    private final SwipeableItemWrapperAdapter<ViewHolder> mAdapter;
    private final List<WeakReference<ViewHolderDeferredProcess>> mDeferredProcesses;
    private int mImmediatelySetTranslationThreshold;
    private final Interpolator mSlideToDefaultPositionAnimationInterpolator = new AccelerateDecelerateInterpolator();
    private final Interpolator mSlideToOutsideOfWindowAnimationInterpolator = new AccelerateInterpolator(0.8f);
    private final int[] mTmpLocation = new int[2];
    private final Rect mTmpRect = new Rect();

    private static abstract class ViewHolderDeferredProcess implements Runnable {
        final WeakReference<ViewHolder> mRefHolder;

        public ViewHolderDeferredProcess(ViewHolder viewHolder) {
            this.mRefHolder = new WeakReference(viewHolder);
        }

        public boolean hasTargetViewHolder(ViewHolder viewHolder) {
            return ((ViewHolder) this.mRefHolder.get()) == viewHolder;
        }

        public boolean lostReference(ViewHolder viewHolder) {
            return ((ViewHolder) this.mRefHolder.get()) == null;
        }

        protected abstract void onProcess(ViewHolder viewHolder);

        public void run() {
            ViewHolder viewHolder = (ViewHolder) this.mRefHolder.get();
            if (viewHolder != null) {
                onProcess(viewHolder);
            }
        }
    }

    private static final class DeferredSlideProcess extends ViewHolderDeferredProcess {
        final boolean mHorizontal;
        final float mPosition;

        public DeferredSlideProcess(ViewHolder viewHolder, float f, boolean z) {
            super(viewHolder);
            this.mPosition = f;
            this.mHorizontal = z;
        }

        protected void onProcess(ViewHolder viewHolder) {
            View swipeableContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
            if (this.mHorizontal) {
                ItemSlidingAnimator.slideInternalCompat(viewHolder, this.mHorizontal, (int) ((((float) swipeableContainerView.getWidth()) * this.mPosition) + 0.5f), 0);
                return;
            }
            ItemSlidingAnimator.slideInternalCompat(viewHolder, this.mHorizontal, 0, (int) ((((float) swipeableContainerView.getHeight()) * this.mPosition) + 0.5f));
        }
    }

    private static class SlidingAnimatorListenerObject implements ViewPropertyAnimatorListener, ViewPropertyAnimatorUpdateListener {
        private List<ViewHolder> mActive;
        private SwipeableItemWrapperAdapter<ViewHolder> mAdapter;
        private ViewPropertyAnimatorCompat mAnimator;
        private final long mDuration;
        private final boolean mHorizontal;
        private final Interpolator mInterpolator;
        private float mInvSize;
        private final SwipeFinishInfo mSwipeFinish;
        private final int mToX;
        private final int mToY;
        private ViewHolder mViewHolder;

        SlidingAnimatorListenerObject(SwipeableItemWrapperAdapter<ViewHolder> swipeableItemWrapperAdapter, List<ViewHolder> list, ViewHolder viewHolder, int i, int i2, long j, boolean z, Interpolator interpolator, SwipeFinishInfo swipeFinishInfo) {
            this.mAdapter = swipeableItemWrapperAdapter;
            this.mActive = list;
            this.mViewHolder = viewHolder;
            this.mToX = i;
            this.mToY = i2;
            this.mHorizontal = z;
            this.mSwipeFinish = swipeFinishInfo;
            this.mDuration = j;
            this.mInterpolator = interpolator;
        }

        public void onAnimationCancel(View view) {
        }

        public void onAnimationEnd(View view) {
            this.mAnimator.setListener(null);
            if (VERSION.SDK_INT >= 19) {
                InternalHelperKK.clearViewPropertyAnimatorUpdateListener(view);
            } else {
                this.mAnimator.setUpdateListener(null);
            }
            ViewCompat.setTranslationX(view, (float) this.mToX);
            ViewCompat.setTranslationY(view, (float) this.mToY);
            this.mActive.remove(this.mViewHolder);
            ViewParent parent = this.mViewHolder.itemView.getParent();
            if (parent != null) {
                ViewCompat.postInvalidateOnAnimation((View) parent);
            }
            if (this.mSwipeFinish != null) {
                this.mSwipeFinish.resultAction.slideAnimationEnd();
            }
            this.mActive = null;
            this.mAnimator = null;
            this.mViewHolder = null;
            this.mAdapter = null;
        }

        public void onAnimationStart(View view) {
        }

        public void onAnimationUpdate(View view) {
            this.mAdapter.onUpdateSlideAmount(this.mViewHolder, this.mViewHolder.getLayoutPosition(), this.mHorizontal, (this.mHorizontal ? ViewCompat.getTranslationX(view) : ViewCompat.getTranslationY(view)) * this.mInvSize, false);
        }

        void start() {
            View swipeableContainerView = ((SwipeableItemViewHolder) this.mViewHolder).getSwipeableContainerView();
            this.mInvSize = 1.0f / Math.max(1.0f, this.mHorizontal ? (float) swipeableContainerView.getWidth() : (float) swipeableContainerView.getHeight());
            this.mAnimator = ViewCompat.animate(swipeableContainerView);
            this.mAnimator.setDuration(this.mDuration);
            this.mAnimator.translationX((float) this.mToX);
            this.mAnimator.translationY((float) this.mToY);
            if (this.mInterpolator != null) {
                this.mAnimator.setInterpolator(this.mInterpolator);
            }
            this.mAnimator.setListener(this);
            this.mAnimator.setUpdateListener(this);
            this.mActive.add(this.mViewHolder);
            this.mAnimator.start();
        }
    }

    private static class SwipeFinishInfo {
        final int itemPosition;
        SwipeResultAction resultAction;

        public SwipeFinishInfo(int i, SwipeResultAction swipeResultAction) {
            this.itemPosition = i;
            this.resultAction = swipeResultAction;
        }

        public void clear() {
            this.resultAction = null;
        }
    }

    public ItemSlidingAnimator(SwipeableItemWrapperAdapter<ViewHolder> swipeableItemWrapperAdapter) {
        this.mAdapter = swipeableItemWrapperAdapter;
        this.mActive = new ArrayList();
        this.mDeferredProcesses = new ArrayList();
    }

    private boolean animateSlideInternal(ViewHolder viewHolder, boolean z, int i, int i2, long j, Interpolator interpolator, SwipeFinishInfo swipeFinishInfo) {
        if (!(viewHolder instanceof SwipeableItemViewHolder)) {
            return false;
        }
        View swipeableContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
        int translationX = (int) (ViewCompat.getTranslationX(swipeableContainerView) + 0.5f);
        int translationY = (int) (ViewCompat.getTranslationY(swipeableContainerView) + 0.5f);
        endAnimation(viewHolder);
        int translationX2 = (int) (ViewCompat.getTranslationX(swipeableContainerView) + 0.5f);
        int translationY2 = (int) (ViewCompat.getTranslationY(swipeableContainerView) + 0.5f);
        if (j == 0 || ((translationX2 == i && translationY2 == i2) || Math.max(Math.abs(i - translationX), Math.abs(i2 - translationY)) <= this.mImmediatelySetTranslationThreshold)) {
            ViewCompat.setTranslationX(swipeableContainerView, (float) i);
            ViewCompat.setTranslationY(swipeableContainerView, (float) i2);
            return false;
        }
        ViewCompat.setTranslationX(swipeableContainerView, (float) translationX);
        ViewCompat.setTranslationY(swipeableContainerView, (float) translationY);
        new SlidingAnimatorListenerObject(this.mAdapter, this.mActive, viewHolder, i, i2, j, z, interpolator, swipeFinishInfo).start();
        return true;
    }

    private boolean animateSlideInternalCompat(ViewHolder viewHolder, boolean z, int i, int i2, long j, Interpolator interpolator, SwipeFinishInfo swipeFinishInfo) {
        return supportsViewPropertyAnimator() ? animateSlideInternal(viewHolder, z, i, i2, j, interpolator, swipeFinishInfo) : slideInternalPreHoneycomb(viewHolder, z, i, i2);
    }

    private void cancelDeferredProcess(ViewHolder viewHolder) {
        for (int size = this.mDeferredProcesses.size() - 1; size >= 0; size--) {
            ViewHolderDeferredProcess viewHolderDeferredProcess = (ViewHolderDeferredProcess) ((WeakReference) this.mDeferredProcesses.get(size)).get();
            if (viewHolderDeferredProcess != null && viewHolderDeferredProcess.hasTargetViewHolder(viewHolder)) {
                viewHolder.itemView.removeCallbacks(viewHolderDeferredProcess);
                this.mDeferredProcesses.remove(size);
            } else if (viewHolderDeferredProcess == null || viewHolderDeferredProcess.lostReference(viewHolder)) {
                this.mDeferredProcesses.remove(size);
            }
        }
    }

    private static int getTranslationXPreHoneycomb(ViewHolder viewHolder) {
        LayoutParams layoutParams = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView().getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            return ((MarginLayoutParams) layoutParams).leftMargin;
        }
        Log.w(TAG, "should use MarginLayoutParams supported view for compatibility on Android 2.3");
        return 0;
    }

    private static int getTranslationYPreHoneycomb(ViewHolder viewHolder) {
        LayoutParams layoutParams = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView().getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            return ((MarginLayoutParams) layoutParams).topMargin;
        }
        Log.w(TAG, "should use MarginLayoutParams supported view for compatibility on Android 2.3");
        return 0;
    }

    private void scheduleViewHolderDeferredSlideProcess(ViewHolder viewHolder, ViewHolderDeferredProcess viewHolderDeferredProcess) {
        this.mDeferredProcesses.add(new WeakReference(viewHolderDeferredProcess));
        viewHolder.itemView.post(viewHolderDeferredProcess);
    }

    private static void slideInternal(ViewHolder viewHolder, boolean z, int i, int i2) {
        if (viewHolder instanceof SwipeableItemViewHolder) {
            View swipeableContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
            ViewCompat.animate(swipeableContainerView).cancel();
            ViewCompat.setTranslationX(swipeableContainerView, (float) i);
            ViewCompat.setTranslationY(swipeableContainerView, (float) i2);
        }
    }

    static void slideInternalCompat(ViewHolder viewHolder, boolean z, int i, int i2) {
        if (supportsViewPropertyAnimator()) {
            slideInternal(viewHolder, z, i, i2);
        } else {
            slideInternalPreHoneycomb(viewHolder, z, i, i2);
        }
    }

    @SuppressLint({"RtlHardcoded"})
    private static boolean slideInternalPreHoneycomb(ViewHolder viewHolder, boolean z, int i, int i2) {
        if (viewHolder instanceof SwipeableItemViewHolder) {
            View swipeableContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
            LayoutParams layoutParams = swipeableContainerView.getLayoutParams();
            if (layoutParams instanceof MarginLayoutParams) {
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
                marginLayoutParams.leftMargin = i;
                marginLayoutParams.rightMargin = -i;
                marginLayoutParams.topMargin = i2;
                marginLayoutParams.bottomMargin = -i2;
                if (layoutParams instanceof FrameLayout.LayoutParams) {
                    ((FrameLayout.LayoutParams) layoutParams).gravity = 51;
                }
                swipeableContainerView.setLayoutParams(marginLayoutParams);
            } else {
                Log.w(TAG, "should use MarginLayoutParams supported view for compatibility on Android 2.3");
            }
        }
        return false;
    }

    private boolean slideToOutsideOfWindowInternal(ViewHolder viewHolder, int i, boolean z, long j, SwipeFinishInfo swipeFinishInfo) {
        if (!(viewHolder instanceof SwipeableItemViewHolder)) {
            return false;
        }
        View swipeableContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
        ViewGroup viewGroup = (ViewGroup) swipeableContainerView.getParent();
        if (viewGroup == null) {
            return false;
        }
        boolean z2;
        int left = swipeableContainerView.getLeft();
        int right = swipeableContainerView.getRight();
        int top = swipeableContainerView.getTop();
        int i2 = right - left;
        int bottom = swipeableContainerView.getBottom() - top;
        boolean isShown = viewGroup.isShown();
        viewGroup.getWindowVisibleDisplayFrame(this.mTmpRect);
        int width = this.mTmpRect.width();
        int height = this.mTmpRect.height();
        int i3;
        if (i2 != 0 && bottom != 0 && isShown) {
            viewGroup.getLocationInWindow(this.mTmpLocation);
            i3 = this.mTmpLocation[0];
            int i4 = this.mTmpLocation[1];
            switch (i) {
                case 0:
                    left = 0;
                    width = -(i3 + i2);
                    z2 = z;
                    break;
                case 1:
                    left = -(i4 + bottom);
                    width = 0;
                    z2 = z;
                    break;
                case 2:
                    left = 0;
                    width -= i3 - left;
                    z2 = z;
                    break;
                case 3:
                    left = height - (i4 - top);
                    width = 0;
                    z2 = z;
                    break;
                default:
                    left = 0;
                    width = 0;
                    z2 = z;
                    break;
            }
        }
        switch (i) {
            case 0:
                right = -width;
                i3 = 0;
                break;
            case 1:
                i3 = -height;
                right = 0;
                break;
            case 2:
                i3 = 0;
                right = width;
                break;
            case 3:
                i3 = height;
                right = 0;
                break;
            default:
                i3 = 0;
                right = 0;
                break;
        }
        left = i3;
        width = right;
        z2 = false;
        if (z2) {
            z2 = swipeableContainerView.isShown();
        }
        long j2 = z2 ? j : 0;
        boolean z3 = i == 0 || i == 2;
        return animateSlideInternalCompat(viewHolder, z3, width, left, j2, this.mSlideToOutsideOfWindowAnimationInterpolator, swipeFinishInfo);
    }

    private boolean slideToSpecifiedPositionInternal(ViewHolder viewHolder, float f, boolean z, boolean z2, long j, SwipeFinishInfo swipeFinishInfo) {
        Interpolator interpolator = this.mSlideToDefaultPositionAnimationInterpolator;
        long j2 = z2 ? j : 0;
        if (f == 0.0f) {
            return animateSlideInternalCompat(viewHolder, z, 0, 0, j2, interpolator, swipeFinishInfo);
        }
        View swipeableContainerView = ((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView();
        int width = swipeableContainerView.getWidth();
        int height = swipeableContainerView.getHeight();
        if (z && width != 0) {
            return animateSlideInternalCompat(viewHolder, z, (int) ((((float) width) * f) + 0.5f), 0, j2, interpolator, swipeFinishInfo);
        } else if (!z && height != 0) {
            return animateSlideInternalCompat(viewHolder, z, 0, (int) ((((float) height) * f) + 0.5f), j2, interpolator, swipeFinishInfo);
        } else if (swipeFinishInfo != null) {
            throw new IllegalStateException("Unexpected state in slideToSpecifiedPositionInternal (swipeFinish == null)");
        } else {
            scheduleViewHolderDeferredSlideProcess(viewHolder, new DeferredSlideProcess(viewHolder, f, z));
            return false;
        }
    }

    private static boolean supportsViewPropertyAnimator() {
        return VERSION.SDK_INT >= 11;
    }

    public void endAnimation(ViewHolder viewHolder) {
        if (viewHolder instanceof SwipeableItemViewHolder) {
            cancelDeferredProcess(viewHolder);
            ViewCompat.animate(((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView()).cancel();
            if (this.mActive.remove(viewHolder)) {
                throw new IllegalStateException("after animation is cancelled, item should not be in the active animation list [slide]");
            }
        }
    }

    public void endAnimations() {
        for (int size = this.mActive.size() - 1; size >= 0; size--) {
            endAnimation((ViewHolder) this.mActive.get(size));
        }
    }

    public boolean finishSwipeSlideToDefaultPosition(ViewHolder viewHolder, boolean z, boolean z2, long j, int i, SwipeResultAction swipeResultAction) {
        cancelDeferredProcess(viewHolder);
        return slideToSpecifiedPositionInternal(viewHolder, 0.0f, z, z2, j, new SwipeFinishInfo(i, swipeResultAction));
    }

    public boolean finishSwipeSlideToOutsideOfWindow(ViewHolder viewHolder, int i, boolean z, long j, int i2, SwipeResultAction swipeResultAction) {
        cancelDeferredProcess(viewHolder);
        return slideToOutsideOfWindowInternal(viewHolder, i, z, j, new SwipeFinishInfo(i2, swipeResultAction));
    }

    public int getImmediatelySetTranslationThreshold() {
        return this.mImmediatelySetTranslationThreshold;
    }

    public int getSwipeContainerViewTranslationX(ViewHolder viewHolder) {
        return supportsViewPropertyAnimator() ? (int) (ViewCompat.getTranslationX(((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView()) + 0.5f) : getTranslationXPreHoneycomb(viewHolder);
    }

    public int getSwipeContainerViewTranslationY(ViewHolder viewHolder) {
        return supportsViewPropertyAnimator() ? (int) (ViewCompat.getTranslationY(((SwipeableItemViewHolder) viewHolder).getSwipeableContainerView()) + 0.5f) : getTranslationYPreHoneycomb(viewHolder);
    }

    public boolean isRunning() {
        return !this.mActive.isEmpty();
    }

    public boolean isRunning(ViewHolder viewHolder) {
        return this.mActive.contains(viewHolder);
    }

    public void setImmediatelySetTranslationThreshold(int i) {
        this.mImmediatelySetTranslationThreshold = i;
    }

    public void slideToDefaultPosition(ViewHolder viewHolder, boolean z, boolean z2, long j) {
        cancelDeferredProcess(viewHolder);
        slideToSpecifiedPositionInternal(viewHolder, 0.0f, z, z2, j, null);
    }

    public void slideToOutsideOfWindow(ViewHolder viewHolder, int i, boolean z, long j) {
        cancelDeferredProcess(viewHolder);
        slideToOutsideOfWindowInternal(viewHolder, i, z, j, null);
    }

    public void slideToSpecifiedPosition(ViewHolder viewHolder, float f, boolean z) {
        cancelDeferredProcess(viewHolder);
        slideToSpecifiedPositionInternal(viewHolder, f, z, false, 0, null);
    }
}
