package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.View;
import android.view.animation.Interpolator;

abstract class BaseDraggableItemDecorator extends ItemDecoration {
    private static final int RETURN_TO_DEFAULT_POS_ANIMATE_THRESHOLD_DP = 2;
    private static final int RETURN_TO_DEFAULT_POS_ANIMATE_THRESHOLD_MSEC = 20;
    protected ViewHolder mDraggingItemViewHolder;
    protected final RecyclerView mRecyclerView;
    private final int mReturnToDefaultPositionAnimateThreshold;
    private int mReturnToDefaultPositionDuration = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private Interpolator mReturnToDefaultPositionInterpolator;

    public BaseDraggableItemDecorator(RecyclerView recyclerView, ViewHolder viewHolder) {
        this.mRecyclerView = recyclerView;
        this.mDraggingItemViewHolder = viewHolder;
        this.mReturnToDefaultPositionAnimateThreshold = (int) ((recyclerView.getResources().getDisplayMetrics().density * 2.0f) + 0.5f);
    }

    protected static void setItemTranslation(RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2) {
        ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimation(viewHolder);
        }
        ViewCompat.setTranslationX(viewHolder.itemView, f);
        ViewCompat.setTranslationY(viewHolder.itemView, f2);
    }

    private static boolean supportsViewPropertyAnimation() {
        return VERSION.SDK_INT >= 11;
    }

    protected void moveToDefaultPosition(View view, boolean z) {
        int translationX = (int) ViewCompat.getTranslationX(view);
        int translationY = (int) ViewCompat.getTranslationY(view);
        int width = view.getWidth() / 2;
        int height = view.getHeight() / 2;
        float min = 1.0f - Math.min(width > 0 ? Math.abs(((float) translationX) / ((float) width)) : 0.0f, 1.0f);
        float min2 = 1.0f - Math.min(height > 0 ? Math.abs(((float) translationY) / ((float) height)) : 0.0f, 1.0f);
        width = Math.max((int) (((1.0f - (min * min)) * ((float) this.mReturnToDefaultPositionDuration)) + 0.5f), (int) (((1.0f - (min2 * min2)) * ((float) this.mReturnToDefaultPositionDuration)) + 0.5f));
        int max = Math.max(Math.abs(translationX), Math.abs(translationY));
        if (!supportsViewPropertyAnimation() || !z || width <= 20 || max <= this.mReturnToDefaultPositionAnimateThreshold) {
            ViewCompat.setTranslationX(view, 0.0f);
            ViewCompat.setTranslationY(view, 0.0f);
            return;
        }
        final ViewPropertyAnimatorCompat animate = ViewCompat.animate(view);
        animate.cancel();
        animate.setDuration((long) width);
        animate.setInterpolator(this.mReturnToDefaultPositionInterpolator);
        animate.translationX(0.0f);
        animate.translationY(0.0f);
        animate.setListener(new ViewPropertyAnimatorListener() {
            public void onAnimationCancel(View view) {
            }

            public void onAnimationEnd(View view) {
                animate.setListener(null);
                ViewCompat.setTranslationX(view, 0.0f);
                ViewCompat.setTranslationY(view, 0.0f);
                if (view.getParent() instanceof RecyclerView) {
                    ViewCompat.postInvalidateOnAnimation((RecyclerView) view.getParent());
                }
            }

            public void onAnimationStart(View view) {
            }
        });
        animate.start();
    }

    public void setReturnToDefaultPositionAnimationDuration(int i) {
        this.mReturnToDefaultPositionDuration = i;
    }

    public void setReturnToDefaultPositionAnimationInterpolator(Interpolator interpolator) {
        this.mReturnToDefaultPositionInterpolator = interpolator;
    }
}
