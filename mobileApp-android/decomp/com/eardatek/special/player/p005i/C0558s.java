package com.eardatek.special.player.p005i;

import android.support.v4.view.ViewCompat;
import android.view.View;

public class C0558s {
    public static boolean m731a(View view, int i, int i2) {
        int translationX = (int) (ViewCompat.getTranslationX(view) + 0.5f);
        int translationY = (int) (ViewCompat.getTranslationY(view) + 0.5f);
        return i >= view.getLeft() + translationX && i <= translationX + view.getRight() && i2 >= view.getTop() + translationY && i2 <= translationY + view.getBottom();
    }
}
