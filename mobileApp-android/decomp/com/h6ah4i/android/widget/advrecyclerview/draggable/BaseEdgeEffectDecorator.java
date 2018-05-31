package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;

abstract class BaseEdgeEffectDecorator extends ItemDecoration {
    protected static final int EDGE_BOTTOM = 3;
    protected static final int EDGE_LEFT = 0;
    protected static final int EDGE_RIGHT = 2;
    protected static final int EDGE_TOP = 1;
    private EdgeEffectCompat mGlow1;
    private int mGlow1Dir;
    private EdgeEffectCompat mGlow2;
    private int mGlow2Dir;
    private RecyclerView mRecyclerView;
    private boolean mStarted;

    public BaseEdgeEffectDecorator(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    private static boolean drawGlow(Canvas canvas, RecyclerView recyclerView, int i, EdgeEffectCompat edgeEffectCompat) {
        if (edgeEffectCompat.isFinished()) {
            return false;
        }
        int save = canvas.save();
        boolean clipToPadding = getClipToPadding(recyclerView);
        switch (i) {
            case 0:
                canvas.rotate(-90.0f);
                if (!clipToPadding) {
                    canvas.translate((float) (-recyclerView.getHeight()), 0.0f);
                    break;
                }
                canvas.translate((float) ((-recyclerView.getHeight()) + recyclerView.getPaddingTop()), (float) recyclerView.getPaddingLeft());
                break;
            case 1:
                if (clipToPadding) {
                    canvas.translate((float) recyclerView.getPaddingLeft(), (float) recyclerView.getPaddingTop());
                    break;
                }
                break;
            case 2:
                canvas.rotate(90.0f);
                if (!clipToPadding) {
                    canvas.translate(0.0f, (float) (-recyclerView.getWidth()));
                    break;
                }
                canvas.translate((float) recyclerView.getPaddingTop(), (float) ((-recyclerView.getWidth()) + recyclerView.getPaddingRight()));
                break;
            case 3:
                canvas.rotate(180.0f);
                if (!clipToPadding) {
                    canvas.translate((float) (-recyclerView.getWidth()), (float) (-recyclerView.getHeight()));
                    break;
                }
                canvas.translate((float) ((-recyclerView.getWidth()) + recyclerView.getPaddingRight()), (float) ((-recyclerView.getHeight()) + recyclerView.getPaddingBottom()));
                break;
        }
        clipToPadding = edgeEffectCompat.draw(canvas);
        canvas.restoreToCount(save);
        return clipToPadding;
    }

    private void ensureGlow1(RecyclerView recyclerView) {
        if (this.mGlow1 == null) {
            this.mGlow1 = new EdgeEffectCompat(recyclerView.getContext());
        }
        updateGlowSize(recyclerView, this.mGlow1, this.mGlow1Dir);
    }

    private void ensureGlow2(RecyclerView recyclerView) {
        if (this.mGlow2 == null) {
            this.mGlow2 = new EdgeEffectCompat(recyclerView.getContext());
        }
        updateGlowSize(recyclerView, this.mGlow2, this.mGlow2Dir);
    }

    private static boolean getClipToPadding(RecyclerView recyclerView) {
        return recyclerView.getLayoutManager().getClipToPadding();
    }

    private static void updateGlowSize(RecyclerView recyclerView, EdgeEffectCompat edgeEffectCompat, int i) {
        int measuredWidth = recyclerView.getMeasuredWidth();
        int measuredHeight = recyclerView.getMeasuredHeight();
        if (getClipToPadding(recyclerView)) {
            measuredWidth -= recyclerView.getPaddingLeft() + recyclerView.getPaddingRight();
            measuredHeight -= recyclerView.getPaddingTop() + recyclerView.getPaddingBottom();
        }
        measuredWidth = Math.max(0, measuredWidth);
        measuredHeight = Math.max(0, measuredHeight);
        if (i == 0 || i == 2) {
            int i2 = measuredWidth;
            measuredWidth = measuredHeight;
            measuredHeight = i2;
        }
        edgeEffectCompat.setSize(measuredWidth, measuredHeight);
    }

    public void finish() {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        releaseBothGlows();
        this.mRecyclerView = null;
        this.mStarted = false;
    }

    protected abstract int getEdgeDirection(int i);

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        int i = 0;
        if (this.mGlow1 != null) {
            i = 0 | drawGlow(canvas, recyclerView, this.mGlow1Dir, this.mGlow1);
        }
        if (this.mGlow2 != null) {
            i |= drawGlow(canvas, recyclerView, this.mGlow2Dir, this.mGlow2);
        }
        if (i != 0) {
            ViewCompat.postInvalidateOnAnimation(recyclerView);
        }
    }

    public void pullFirstEdge(float f) {
        ensureGlow1(this.mRecyclerView);
        if (this.mGlow1.onPull(f, 0.5f)) {
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
    }

    public void pullSecondEdge(float f) {
        ensureGlow2(this.mRecyclerView);
        if (this.mGlow2.onPull(f, 0.5f)) {
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
    }

    public void releaseBothGlows() {
        int i = 0;
        if (this.mGlow1 != null) {
            i = 0 | this.mGlow1.onRelease();
        }
        if (this.mGlow2 != null) {
            i |= this.mGlow2.onRelease();
        }
        if (i != 0) {
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
    }

    public void reorderToTop() {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
            this.mRecyclerView.addItemDecoration(this);
        }
    }

    public void start() {
        if (!this.mStarted) {
            this.mGlow1Dir = getEdgeDirection(0);
            this.mGlow2Dir = getEdgeDirection(1);
            this.mRecyclerView.addItemDecoration(this);
            this.mStarted = true;
        }
    }
}
