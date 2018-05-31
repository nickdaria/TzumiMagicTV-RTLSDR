package com.h6ah4i.android.widget.advrecyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class ItemShadowDecorator extends ItemDecoration {
    private final boolean mCastShadowForTransparentBackgroundItem;
    private final NinePatchDrawable mShadowDrawable;
    private final Rect mShadowPadding;

    public ItemShadowDecorator(@NonNull NinePatchDrawable ninePatchDrawable) {
        this(ninePatchDrawable, true);
    }

    public ItemShadowDecorator(@NonNull NinePatchDrawable ninePatchDrawable, boolean z) {
        this.mShadowPadding = new Rect();
        this.mShadowDrawable = ninePatchDrawable;
        this.mShadowDrawable.getPadding(this.mShadowPadding);
        this.mCastShadowForTransparentBackgroundItem = z;
    }

    private boolean shouldDrawDropShadow(View view) {
        if (view.getVisibility() != 0) {
            return false;
        }
        if (ViewCompat.getAlpha(view) != 1.0f) {
            return false;
        }
        Drawable background = view.getBackground();
        return background == null ? false : (!this.mCastShadowForTransparentBackgroundItem && (background instanceof ColorDrawable) && ((ColorDrawable) background).getAlpha() == 0) ? false : true;
    }

    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
        rect.set(0, 0, 0, 0);
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
        int childCount = recyclerView.getChildCount();
        if (childCount != 0) {
            for (int i = 0; i < childCount; i++) {
                View childAt = recyclerView.getChildAt(i);
                if (shouldDrawDropShadow(childAt)) {
                    int translationX = (int) (ViewCompat.getTranslationX(childAt) + 0.5f);
                    int translationY = (int) (ViewCompat.getTranslationY(childAt) + 0.5f);
                    int top = childAt.getTop() - this.mShadowPadding.top;
                    this.mShadowDrawable.setBounds((childAt.getLeft() - this.mShadowPadding.left) + translationX, top + translationY, translationX + (childAt.getRight() + this.mShadowPadding.right), (childAt.getBottom() + this.mShadowPadding.bottom) + translationY);
                    this.mShadowDrawable.draw(canvas);
                }
            }
        }
    }
}
