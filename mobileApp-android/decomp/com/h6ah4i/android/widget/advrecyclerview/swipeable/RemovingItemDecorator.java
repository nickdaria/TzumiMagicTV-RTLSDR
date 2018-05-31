package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import java.lang.ref.WeakReference;

class RemovingItemDecorator extends ItemDecoration {
    private static final long ADDITIONAL_REMOVE_DURATION = 0;
    private static final int NOTIFY_REMOVAL_EFFECT_END = 1;
    private static final int NOTIFY_REMOVAL_EFFECT_PHASE_1 = 0;
    private static final String TAG = "RemovingItemDecorator";
    private final boolean mHorizontal;
    private final long mMoveAnimationDuration;
    private Interpolator mMoveAnimationInterpolator;
    private int mPendingNotificationMask = 0;
    private RecyclerView mRecyclerView;
    private final long mRemoveAnimationDuration;
    private long mStartTime;
    private Drawable mSwipeBackgroundDrawable;
    private ViewHolder mSwipingItem;
    private final Rect mSwipingItemBounds = new Rect();
    private final long mSwipingItemId;
    private int mTranslationX;
    private int mTranslationY;

    private static class DelayedNotificationRunner implements Runnable {
        private final int mCode;
        private WeakReference<RemovingItemDecorator> mRefDecorator;

        public DelayedNotificationRunner(RemovingItemDecorator removingItemDecorator, int i) {
            this.mRefDecorator = new WeakReference(removingItemDecorator);
            this.mCode = i;
        }

        public void run() {
            RemovingItemDecorator removingItemDecorator = (RemovingItemDecorator) this.mRefDecorator.get();
            this.mRefDecorator.clear();
            this.mRefDecorator = null;
            if (removingItemDecorator != null) {
                removingItemDecorator.onDelayedNotification(this.mCode);
            }
        }
    }

    public RemovingItemDecorator(RecyclerView recyclerView, ViewHolder viewHolder, int i, long j, long j2) {
        boolean z = false;
        this.mRecyclerView = recyclerView;
        this.mSwipingItem = viewHolder;
        this.mSwipingItemId = viewHolder.getItemId();
        if (i == 2 || i == 4) {
            z = true;
        }
        this.mHorizontal = z;
        this.mRemoveAnimationDuration = 0 + j;
        this.mMoveAnimationDuration = j2;
        this.mTranslationX = (int) (ViewCompat.getTranslationX(viewHolder.itemView) + 0.5f);
        this.mTranslationY = (int) (ViewCompat.getTranslationY(viewHolder.itemView) + 0.5f);
        CustomRecyclerViewUtils.getViewBounds(this.mSwipingItem.itemView, this.mSwipingItemBounds);
    }

    private float determineBackgroundScaleSwipeCompletedSuccessfully(long j) {
        if (j < this.mRemoveAnimationDuration) {
            return 1.0f;
        }
        if (j >= this.mRemoveAnimationDuration + this.mMoveAnimationDuration || this.mMoveAnimationDuration == 0) {
            return 0.0f;
        }
        float f = 1.0f - (((float) (j - this.mRemoveAnimationDuration)) / ((float) this.mMoveAnimationDuration));
        return this.mMoveAnimationInterpolator != null ? this.mMoveAnimationInterpolator.getInterpolation(f) : f;
    }

    private void fillSwipingItemBackground(Canvas canvas, Drawable drawable, float f) {
        Rect rect = this.mSwipingItemBounds;
        int i = this.mTranslationX;
        int i2 = this.mTranslationY;
        float f2 = this.mHorizontal ? 1.0f : f;
        if (!this.mHorizontal) {
            f = 1.0f;
        }
        int width = (int) ((f2 * ((float) rect.width())) + 0.5f);
        int height = (int) ((((float) rect.height()) * f) + 0.5f);
        if (height != 0 && width != 0 && drawable != null) {
            int save = canvas.save();
            canvas.clipRect(rect.left + i, rect.top + i2, (rect.left + i) + width, (rect.top + i2) + height);
            canvas.translate((float) ((i + rect.left) - ((rect.width() - width) / 2)), (float) ((rect.top + i2) - ((rect.height() - height) / 2)));
            drawable.setBounds(0, 0, rect.width(), rect.height());
            drawable.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    private void finish() {
        this.mRecyclerView.removeItemDecoration(this);
        postInvalidateOnAnimation();
        this.mRecyclerView = null;
        this.mSwipingItem = null;
        this.mTranslationY = 0;
        this.mMoveAnimationInterpolator = null;
    }

    protected static long getElapsedTime(long j) {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis >= j ? currentTimeMillis - j : Long.MAX_VALUE;
    }

    private void notifyDelayed(int i, long j) {
        int i2 = 1 << i;
        if ((this.mPendingNotificationMask & i2) == 0) {
            this.mPendingNotificationMask = i2 | this.mPendingNotificationMask;
            ViewCompat.postOnAnimationDelayed(this.mRecyclerView, new DelayedNotificationRunner(this, i), j);
        }
    }

    private void postInvalidateOnAnimation() {
        ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
    }

    private boolean requiresContinuousAnimationAfterSwipeCompletedSuccessfully(long j) {
        return j >= this.mRemoveAnimationDuration && j < this.mRemoveAnimationDuration + this.mMoveAnimationDuration;
    }

    void onDelayedNotification(int i) {
        int i2 = 1 << i;
        long elapsedTime = getElapsedTime(this.mStartTime);
        this.mPendingNotificationMask = (i2 ^ -1) & this.mPendingNotificationMask;
        switch (i) {
            case 0:
                if (elapsedTime < this.mRemoveAnimationDuration) {
                    notifyDelayed(0, this.mRemoveAnimationDuration - elapsedTime);
                    return;
                }
                postInvalidateOnAnimation();
                notifyDelayed(1, this.mMoveAnimationDuration);
                return;
            case 1:
                finish();
                return;
            default:
                return;
        }
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        long elapsedTime = getElapsedTime(this.mStartTime);
        fillSwipingItemBackground(canvas, this.mSwipeBackgroundDrawable, determineBackgroundScaleSwipeCompletedSuccessfully(elapsedTime));
        if (this.mSwipingItemId == this.mSwipingItem.getItemId()) {
            this.mTranslationX = (int) (ViewCompat.getTranslationX(this.mSwipingItem.itemView) + 0.5f);
            this.mTranslationY = (int) (ViewCompat.getTranslationY(this.mSwipingItem.itemView) + 0.5f);
        }
        if (requiresContinuousAnimationAfterSwipeCompletedSuccessfully(elapsedTime)) {
            postInvalidateOnAnimation();
        }
    }

    public void setMoveAnimationInterpolator(Interpolator interpolator) {
        this.mMoveAnimationInterpolator = interpolator;
    }

    public void start() {
        ViewCompat.animate(((SwipeableItemViewHolder) this.mSwipingItem).getSwipeableContainerView()).cancel();
        this.mRecyclerView.addItemDecoration(this);
        this.mStartTime = System.currentTimeMillis();
        this.mTranslationY = (int) (ViewCompat.getTranslationY(this.mSwipingItem.itemView) + 0.5f);
        this.mSwipeBackgroundDrawable = this.mSwipingItem.itemView.getBackground();
        postInvalidateOnAnimation();
        notifyDelayed(0, this.mRemoveAnimationDuration);
    }
}
