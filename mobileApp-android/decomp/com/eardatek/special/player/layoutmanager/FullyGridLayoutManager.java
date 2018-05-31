package com.eardatek.special.player.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

public class FullyGridLayoutManager extends GridLayoutManager {
    private int[] f496a;

    public FullyGridLayoutManager(Context context, int i) {
        super(context, i);
        this.f496a = new int[2];
    }

    public FullyGridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i, i2, z);
        this.f496a = new int[2];
    }

    private void m745a(Recycler recycler, int i, int i2, int i3, int[] iArr) {
        if (i < getItemCount()) {
            try {
                View viewForPosition = recycler.getViewForPosition(0);
                if (viewForPosition != null) {
                    LayoutParams layoutParams = (LayoutParams) viewForPosition.getLayoutParams();
                    viewForPosition.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingLeft() + getPaddingRight(), layoutParams.width), ViewGroup.getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom(), layoutParams.height));
                    iArr[0] = (viewForPosition.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin;
                    iArr[1] = layoutParams.topMargin + (viewForPosition.getMeasuredHeight() + layoutParams.bottomMargin);
                    recycler.recycleView(viewForPosition);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onMeasure(Recycler recycler, State state, int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        int i3 = 0;
        int i4 = 0;
        int itemCount = getItemCount();
        int spanCount = getSpanCount();
        int i5 = 0;
        while (i5 < itemCount) {
            int i6;
            int i7;
            m745a(recycler, i5, MeasureSpec.makeMeasureSpec(i5, 0), MeasureSpec.makeMeasureSpec(i5, 0), this.f496a);
            if (getOrientation() == 0) {
                i6 = i5 % spanCount == 0 ? i3 + this.f496a[0] : i3;
                i7 = i5 == 0 ? this.f496a[1] : i4;
            } else {
                i7 = i5 % spanCount == 0 ? this.f496a[1] + i4 : i4;
                i6 = i5 == 0 ? this.f496a[0] : i3;
            }
            i5++;
            i4 = i7;
            i3 = i6;
        }
        switch (mode) {
            case 1073741824:
                i3 = size;
                break;
        }
        switch (mode2) {
            case 1073741824:
                i4 = size2;
                break;
        }
        setMeasuredDimension(i3, i4);
    }
}
