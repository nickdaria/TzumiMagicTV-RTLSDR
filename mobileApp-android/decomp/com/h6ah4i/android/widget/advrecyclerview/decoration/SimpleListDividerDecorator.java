package com.h6ah4i.android.widget.advrecyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class SimpleListDividerDecorator extends ItemDecoration {
    private final int mHorizontalDividerHeight;
    private final Drawable mHorizontalDrawable;
    private final boolean mOverlap;
    private final int mVerticalDividerWidth;
    private final Drawable mVerticalDrawable;

    public SimpleListDividerDecorator(@Nullable Drawable drawable, @Nullable Drawable drawable2, boolean z) {
        int i = 0;
        this.mHorizontalDrawable = drawable;
        this.mVerticalDrawable = drawable2;
        this.mHorizontalDividerHeight = this.mHorizontalDrawable != null ? this.mHorizontalDrawable.getIntrinsicHeight() : 0;
        if (this.mVerticalDrawable != null) {
            i = this.mVerticalDrawable.getIntrinsicWidth();
        }
        this.mVerticalDividerWidth = i;
        this.mOverlap = z;
    }

    public SimpleListDividerDecorator(@Nullable Drawable drawable, boolean z) {
        this(drawable, null, z);
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        if (this.mOverlap) {
            rect.set(0, 0, 0, 0);
        } else {
            rect.set(0, 0, this.mVerticalDividerWidth, this.mHorizontalDividerHeight);
        }
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        int childCount = recyclerView.getChildCount();
        if (childCount != 0) {
            float f = this.mOverlap ? 1.0f : ((float) this.mVerticalDividerWidth) + 1.0f;
            float f2 = this.mOverlap ? 1.0f : ((float) this.mHorizontalDividerHeight) + 1.0f;
            for (int i = 0; i < childCount - 1; i++) {
                View childAt = recyclerView.getChildAt(i);
                View childAt2 = recyclerView.getChildAt(i + 1);
                if (childAt.getVisibility() == 0 && childAt2.getVisibility() == 0) {
                    float bottom = ((float) childAt.getBottom()) + ViewCompat.getTranslationY(childAt);
                    float top = ((float) childAt2.getTop()) + ViewCompat.getTranslationY(childAt2);
                    float right = ((float) childAt.getRight()) + ViewCompat.getTranslationX(childAt);
                    float left = ((float) childAt2.getLeft()) + ViewCompat.getTranslationX(childAt2);
                    if ((this.mHorizontalDividerHeight != 0 && Math.abs(top - bottom) < f2) || (this.mVerticalDividerWidth != 0 && Math.abs(left - right) < f)) {
                        if (Math.abs((ViewCompat.getTranslationZ(childAt2) + ViewCompat.getElevation(childAt2)) - (ViewCompat.getTranslationZ(childAt) + ViewCompat.getElevation(childAt))) < 1.0f) {
                            int left2;
                            int right2;
                            int bottom2;
                            bottom = ViewCompat.getAlpha(childAt);
                            top = ViewCompat.getAlpha(childAt2);
                            int translationX = (int) (ViewCompat.getTranslationX(childAt) + 0.5f);
                            int translationY = (int) (ViewCompat.getTranslationY(childAt) + 0.5f);
                            if (this.mHorizontalDividerHeight != 0) {
                                left2 = childAt.getLeft();
                                right2 = childAt.getRight();
                                bottom2 = childAt.getBottom() - (this.mOverlap ? this.mHorizontalDividerHeight : 0);
                                int i2 = this.mHorizontalDividerHeight + bottom2;
                                this.mHorizontalDrawable.setAlpha((int) ((127.5f * (bottom + top)) + 0.5f));
                                this.mHorizontalDrawable.setBounds(left2 + translationX, bottom2 + translationY, right2 + translationX, i2 + translationY);
                                this.mHorizontalDrawable.draw(canvas);
                            }
                            if (this.mVerticalDividerWidth != 0) {
                                bottom2 = childAt.getRight() - (this.mOverlap ? this.mVerticalDividerWidth : 0);
                                left2 = this.mVerticalDividerWidth + bottom2;
                                right2 = childAt.getTop();
                                int bottom3 = childAt.getBottom();
                                this.mVerticalDrawable.setAlpha((int) (((bottom + top) * 127.5f) + 0.5f));
                                this.mVerticalDrawable.setBounds(bottom2 + translationX, right2 + translationY, translationX + left2, bottom3 + translationY);
                                this.mVerticalDrawable.draw(canvas);
                            }
                        }
                    }
                }
            }
        }
    }
}
