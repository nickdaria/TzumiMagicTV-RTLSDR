package com.eardatek.special.player.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.FloatingActionButton.Behavior;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

public class ScrollAwareFABBehavior extends Behavior {
    private static boolean f561a = true;
    private static final Interpolator f562b = new FastOutSlowInInterpolator();
    private boolean f563c = false;

    public ScrollAwareFABBehavior(Context context, AttributeSet attributeSet) {
    }

    public void m771a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view, int i, int i2, int i3, int i4) {
        super.onNestedScroll(coordinatorLayout, floatingActionButton, view, i, i2, i3, i4);
        if (i2 <= 0 || this.f563c || floatingActionButton.getVisibility() != 0) {
            if (i2 < 0 && floatingActionButton.getVisibility() != 0 && f561a) {
                floatingActionButton.show();
            }
        } else if (f561a) {
            floatingActionButton.hide();
        }
    }

    public boolean m772a(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view, View view2, int i) {
        return i == 2 || super.onStartNestedScroll(coordinatorLayout, floatingActionButton, view, view2, i);
    }

    public /* synthetic */ void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, int i, int i2, int i3, int i4) {
        m771a(coordinatorLayout, (FloatingActionButton) view, view2, i, i2, i3, i4);
    }

    public /* synthetic */ boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i) {
        return m772a(coordinatorLayout, (FloatingActionButton) view, view2, view3, i);
    }
}
