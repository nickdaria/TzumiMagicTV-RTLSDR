package com.h6ah4i.android.widget.advrecyclerview.swipeable;

class SwipeReactionUtils {
    SwipeReactionUtils() {
    }

    public static boolean canSwipeDown(int i) {
        return extractDownReaction(i) == 2;
    }

    public static boolean canSwipeLeft(int i) {
        return extractLeftReaction(i) == 2;
    }

    public static boolean canSwipeRight(int i) {
        return extractRightReaction(i) == 2;
    }

    public static boolean canSwipeUp(int i) {
        return extractUpReaction(i) == 2;
    }

    public static int extractDownReaction(int i) {
        return (i >>> 18) & 3;
    }

    public static int extractLeftReaction(int i) {
        return (i >>> 0) & 3;
    }

    public static int extractRightReaction(int i) {
        return (i >>> 12) & 3;
    }

    public static int extractUpReaction(int i) {
        return (i >>> 6) & 3;
    }
}
