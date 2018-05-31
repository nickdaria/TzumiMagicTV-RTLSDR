package android.support.v4.os;

import android.content.Context;

public class UserManagerCompat {
    private UserManagerCompat() {
    }

    public static boolean isUserUnlocked(Context context) {
        return BuildCompat.isAtLeastN() ? UserManagerCompatApi24.isUserUnlocked(context) : true;
    }
}
