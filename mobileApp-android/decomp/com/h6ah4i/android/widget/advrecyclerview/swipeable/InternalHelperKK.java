package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.annotation.TargetApi;
import android.view.View;

class InternalHelperKK {
    InternalHelperKK() {
    }

    @TargetApi(19)
    public static void clearViewPropertyAnimatorUpdateListener(View view) {
        view.animate().setUpdateListener(null);
    }
}
