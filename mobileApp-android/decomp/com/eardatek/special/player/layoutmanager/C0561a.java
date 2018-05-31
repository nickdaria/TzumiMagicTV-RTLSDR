package com.eardatek.special.player.layoutmanager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;

public class C0561a extends ItemDecoration {
    private static final int[] f499a = new int[]{16843284};
    private Drawable f500b;
    private int f501c;

    public C0561a(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(f499a);
        this.f500b = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        m747a(i);
    }

    public void m747a(int i) {
        if (i == 0 || i == 1) {
            this.f501c = i;
            return;
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public void m748a(Canvas canvas, RecyclerView recyclerView) {
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView recyclerView2 = new RecyclerView(recyclerView.getContext());
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int bottom = layoutParams.bottomMargin + childAt.getBottom();
            this.f500b.setBounds(paddingLeft, bottom, width, this.f500b.getIntrinsicHeight() + bottom);
            this.f500b.draw(canvas);
        }
    }

    public void m749b(Canvas canvas, RecyclerView recyclerView) {
        int paddingTop = recyclerView.getPaddingTop();
        int height = recyclerView.getHeight() - recyclerView.getPaddingBottom();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int right = layoutParams.rightMargin + childAt.getRight();
            this.f500b.setBounds(right, paddingTop, this.f500b.getIntrinsicHeight() + right, height);
            this.f500b.draw(canvas);
        }
    }

    public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
        if (this.f501c == 1) {
            rect.set(0, 0, 0, this.f500b.getIntrinsicHeight());
        } else {
            rect.set(0, 0, this.f500b.getIntrinsicWidth(), 0);
        }
    }

    public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        if (this.f501c == 1) {
            m748a(canvas, recyclerView);
        } else {
            m749b(canvas, recyclerView);
        }
    }
}
