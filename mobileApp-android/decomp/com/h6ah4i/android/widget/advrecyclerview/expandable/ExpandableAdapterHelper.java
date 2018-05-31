package com.h6ah4i.android.widget.advrecyclerview.expandable;

class ExpandableAdapterHelper {
    private static final long LOWER_31BIT_MASK = 2147483647L;
    private static final long LOWER_32BIT_MASK = 4294967295L;
    public static final long NO_EXPANDABLE_POSITION = -1;
    static final int VIEW_TYPE_FLAG_IS_GROUP = Integer.MIN_VALUE;

    private ExpandableAdapterHelper() {
    }

    public static int getChildViewType(int i) {
        return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & i;
    }

    public static long getCombinedChildId(long j, long j2) {
        return ((LOWER_31BIT_MASK & j) << 32) | (LOWER_32BIT_MASK & j2);
    }

    public static long getCombinedGroupId(long j) {
        return ((LOWER_31BIT_MASK & j) << 32) | LOWER_32BIT_MASK;
    }

    public static int getGroupViewType(int i) {
        return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & i;
    }

    public static int getPackedPositionChild(long j) {
        return (int) (j >>> 32);
    }

    public static long getPackedPositionForChild(int i, int i2) {
        return (((long) i2) << 32) | (((long) i) & LOWER_32BIT_MASK);
    }

    public static long getPackedPositionForGroup(int i) {
        return -4294967296L | (((long) i) & LOWER_32BIT_MASK);
    }

    public static int getPackedPositionGroup(long j) {
        return (int) (LOWER_32BIT_MASK & j);
    }

    public static boolean isGroupViewType(int i) {
        return (Integer.MIN_VALUE & i) != 0;
    }
}
