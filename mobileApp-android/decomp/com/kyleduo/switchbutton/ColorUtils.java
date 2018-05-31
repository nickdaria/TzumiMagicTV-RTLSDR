package com.kyleduo.switchbutton;

import android.content.res.ColorStateList;
import android.support.v4.view.ViewCompat;

public class ColorUtils {
    private static final int CHECKED_ATTR = 16842912;
    private static final int ENABLE_ATTR = 16842910;
    private static final int PRESSED_ATTR = 16842919;

    public static ColorStateList generateBackColorWithTintColor(int i) {
        r0 = new int[6][];
        r0[1] = new int[]{-16842910};
        r0[2] = new int[]{CHECKED_ATTR, PRESSED_ATTR};
        r0[3] = new int[]{-16842912, PRESSED_ATTR};
        r0[4] = new int[]{CHECKED_ATTR};
        r0[5] = new int[]{-16842912};
        return new ColorStateList(r0, new int[]{i - -520093696, 268435456, i - -805306368, 536870912, i - -805306368, 536870912});
    }

    public static ColorStateList generateThumbColorWithTintColor(int i) {
        r0 = new int[6][];
        r0[1] = new int[]{-16842910};
        r0[2] = new int[]{PRESSED_ATTR, -16842912};
        r0[3] = new int[]{PRESSED_ATTR, CHECKED_ATTR};
        r0[4] = new int[]{CHECKED_ATTR};
        r0[5] = new int[]{-16842912};
        return new ColorStateList(r0, new int[]{i - -1442840576, -4539718, i - -1728053248, i - -1728053248, ViewCompat.MEASURED_STATE_MASK | i, -1118482});
    }
}
