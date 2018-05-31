package com.h6ah4i.android.widget.advrecyclerview.expandable;

import android.support.v4.view.InputDeviceCompat;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.OnGroupCollapseListener;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager.OnGroupExpandListener;
import java.util.Arrays;

class ExpandablePositionTranslator {
    private static final int ALLOCATE_UNIT = 256;
    private static final long FLAG_EXPANDED = 2147483648L;
    private static final long LOWER_31BIT_MASK = 2147483647L;
    private static final long LOWER_32BIT_MASK = 4294967295L;
    private static final long UPPER_32BIT_MASK = -4294967296L;
    private ExpandableItemAdapter mAdapter;
    private int[] mCachedGroupId;
    private long[] mCachedGroupPosInfo;
    private int mEndOfCalculatedOffsetGroupPosition = -1;
    private int mExpandedChildCount;
    private int mExpandedGroupCount;
    private int mGroupCount;

    private static int binarySearchGroupPositionByFlatPosition(long[] jArr, int i, int i2) {
        int i3 = 0;
        if (i <= 0) {
            return 0;
        }
        int i4 = (int) (jArr[i] >>> 32);
        if (i2 <= ((int) (jArr[0] >>> 32))) {
            return 0;
        }
        if (i2 >= i4) {
            return i;
        }
        int i5 = i;
        int i6 = 0;
        while (i6 < i5) {
            i4 = (i6 + i5) >>> 1;
            if (((int) (jArr[i4] >>> 32)) < i2) {
                i4++;
            } else {
                i5 = i4;
                i4 = i6;
                i6 = i3;
            }
            i3 = i6;
            i6 = i4;
        }
        return i3;
    }

    private void enlargeArraysIfNeeded(int i, boolean z) {
        int i2 = (i + 511) & InputDeviceCompat.SOURCE_ANY;
        Object obj = this.mCachedGroupPosInfo;
        Object obj2 = this.mCachedGroupId;
        Object obj3 = (obj == null || obj.length < i) ? new long[i2] : obj;
        Object obj4 = (obj2 == null || obj2.length < i) ? new int[i2] : obj2;
        if (z) {
            if (!(obj == null || obj == obj3)) {
                System.arraycopy(obj, 0, obj3, 0, obj.length);
            }
            if (!(obj2 == null || obj2 == obj4)) {
                System.arraycopy(obj2, 0, obj4, 0, obj2.length);
            }
        }
        this.mCachedGroupPosInfo = obj3;
        this.mCachedGroupId = obj4;
    }

    public void build(ExpandableItemAdapter expandableItemAdapter, boolean z) {
        int groupCount = expandableItemAdapter.getGroupCount();
        enlargeArraysIfNeeded(groupCount, false);
        long[] jArr = this.mCachedGroupPosInfo;
        int[] iArr = this.mCachedGroupId;
        int i = 0;
        for (int i2 = 0; i2 < groupCount; i2++) {
            long groupId = expandableItemAdapter.getGroupId(i2);
            int childCount = expandableItemAdapter.getChildCount(i2);
            if (z) {
                jArr[i2] = ((((long) (i2 + i)) << 32) | ((long) childCount)) | FLAG_EXPANDED;
            } else {
                jArr[i2] = (((long) i2) << 32) | ((long) childCount);
            }
            iArr[i2] = (int) (groupId & LOWER_32BIT_MASK);
            i += childCount;
        }
        this.mAdapter = expandableItemAdapter;
        this.mGroupCount = groupCount;
        this.mExpandedGroupCount = z ? groupCount : 0;
        this.mExpandedChildCount = z ? i : 0;
        this.mEndOfCalculatedOffsetGroupPosition = Math.max(0, groupCount - 1);
    }

    public boolean collapseGroup(int i) {
        if ((this.mCachedGroupPosInfo[i] & FLAG_EXPANDED) == 0) {
            return false;
        }
        int i2 = (int) (this.mCachedGroupPosInfo[i] & LOWER_31BIT_MASK);
        long[] jArr = this.mCachedGroupPosInfo;
        jArr[i] = jArr[i] & -2147483649L;
        this.mExpandedGroupCount--;
        this.mExpandedChildCount -= i2;
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, i);
        return true;
    }

    public boolean expandGroup(int i) {
        if ((this.mCachedGroupPosInfo[i] & FLAG_EXPANDED) != 0) {
            return false;
        }
        int i2 = (int) (this.mCachedGroupPosInfo[i] & LOWER_31BIT_MASK);
        long[] jArr = this.mCachedGroupPosInfo;
        jArr[i] = jArr[i] | FLAG_EXPANDED;
        this.mExpandedGroupCount++;
        this.mExpandedChildCount = i2 + this.mExpandedChildCount;
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, i);
        return true;
    }

    public int getChildCount(int i) {
        return (int) (this.mCachedGroupPosInfo[i] & LOWER_31BIT_MASK);
    }

    public int getCollapsedGroupsCount() {
        return this.mGroupCount - this.mExpandedGroupCount;
    }

    public long getExpandablePosition(int i) {
        long j = -1;
        if (i != -1) {
            int i2 = this.mGroupCount;
            int binarySearchGroupPositionByFlatPosition = binarySearchGroupPositionByFlatPosition(this.mCachedGroupPosInfo, this.mEndOfCalculatedOffsetGroupPosition, i);
            int i3 = binarySearchGroupPositionByFlatPosition == 0 ? 0 : (int) (this.mCachedGroupPosInfo[binarySearchGroupPositionByFlatPosition] >>> 32);
            int i4 = this.mEndOfCalculatedOffsetGroupPosition;
            int i5 = i3;
            while (binarySearchGroupPositionByFlatPosition < i2) {
                long j2 = this.mCachedGroupPosInfo[binarySearchGroupPositionByFlatPosition];
                this.mCachedGroupPosInfo[binarySearchGroupPositionByFlatPosition] = (((long) i5) << 32) | (LOWER_32BIT_MASK & j2);
                if (i5 >= i) {
                    j = ExpandableAdapterHelper.getPackedPositionForGroup(binarySearchGroupPositionByFlatPosition);
                    i4 = binarySearchGroupPositionByFlatPosition;
                    break;
                }
                i4 = i5 + 1;
                if ((FLAG_EXPANDED & j2) != 0) {
                    i5 = (int) (j2 & LOWER_31BIT_MASK);
                    if (i5 > 0 && (i4 + i5) - 1 >= i) {
                        j = ExpandableAdapterHelper.getPackedPositionForChild(binarySearchGroupPositionByFlatPosition, i - i4);
                        i4 = binarySearchGroupPositionByFlatPosition;
                        break;
                    }
                    i4 += i5;
                }
                i5 = i4;
                i4 = binarySearchGroupPositionByFlatPosition;
                binarySearchGroupPositionByFlatPosition++;
            }
            this.mEndOfCalculatedOffsetGroupPosition = Math.max(this.mEndOfCalculatedOffsetGroupPosition, i4);
        }
        return j;
    }

    public int getExpandedGroupsCount() {
        return this.mExpandedGroupCount;
    }

    public int getFlatPosition(long j) {
        if (j == -1) {
            return -1;
        }
        int packedPositionGroup = ExpandableAdapterHelper.getPackedPositionGroup(j);
        int packedPositionChild = ExpandableAdapterHelper.getPackedPositionChild(j);
        int i = this.mGroupCount;
        if (packedPositionGroup < 0 || packedPositionGroup >= i) {
            return -1;
        }
        if (packedPositionChild != -1 && !isGroupExpanded(packedPositionGroup)) {
            return -1;
        }
        int i2;
        int max = Math.max(0, Math.min(packedPositionGroup, this.mEndOfCalculatedOffsetGroupPosition));
        int i3 = this.mEndOfCalculatedOffsetGroupPosition;
        int i4 = (int) (this.mCachedGroupPosInfo[max] >>> 32);
        while (max < i) {
            long j2 = this.mCachedGroupPosInfo[max];
            this.mCachedGroupPosInfo[max] = (((long) i4) << 32) | (LOWER_32BIT_MASK & j2);
            i3 = (int) (LOWER_31BIT_MASK & j2);
            if (max == packedPositionGroup) {
                if (packedPositionChild == -1) {
                    i2 = max;
                } else if (packedPositionChild < i3) {
                    i4 = (i4 + 1) + packedPositionChild;
                    i2 = max;
                } else {
                    i4 = -1;
                    i2 = max;
                }
                this.mEndOfCalculatedOffsetGroupPosition = Math.max(this.mEndOfCalculatedOffsetGroupPosition, i2);
                return i4;
            }
            i4++;
            if ((j2 & FLAG_EXPANDED) != 0) {
                i4 += i3;
            }
            i3 = max;
            max++;
        }
        i4 = -1;
        i2 = i3;
        this.mEndOfCalculatedOffsetGroupPosition = Math.max(this.mEndOfCalculatedOffsetGroupPosition, i2);
        return i4;
    }

    public int getItemCount() {
        return this.mGroupCount + this.mExpandedChildCount;
    }

    public int[] getSavedStateArray() {
        int i = 0;
        int[] iArr = new int[this.mExpandedGroupCount];
        int i2 = 0;
        while (i < this.mGroupCount) {
            if ((this.mCachedGroupPosInfo[i] & FLAG_EXPANDED) != 0) {
                iArr[i2] = this.mCachedGroupId[i];
                i2++;
            }
            i++;
        }
        if (i2 != this.mExpandedGroupCount) {
            throw new IllegalStateException("may be a bug  (index = " + i2 + ", mExpandedGroupCount = " + this.mExpandedGroupCount + ")");
        }
        Arrays.sort(iArr);
        return iArr;
    }

    public int getVisibleChildCount(int i) {
        return isGroupExpanded(i) ? getChildCount(i) : 0;
    }

    public void insertChildItem(int i, int i2) {
        insertChildItems(i, i2, 1);
    }

    public void insertChildItems(int i, int i2, int i3) {
        long j = this.mCachedGroupPosInfo[i];
        int i4 = (int) (LOWER_31BIT_MASK & j);
        if (i2 < 0 || i2 > i4) {
            throw new IllegalStateException("Invalid child position insertChildItems(groupPosition = " + i + ", childPositionStart = " + i2 + ", count = " + i3 + ")");
        }
        if ((FLAG_EXPANDED & j) != 0) {
            this.mExpandedChildCount += i3;
        }
        this.mCachedGroupPosInfo[i] = (j & -2147483648L) | ((long) (i4 + i3));
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, i);
    }

    public int insertGroupItem(int i, boolean z) {
        return insertGroupItems(i, 1, z);
    }

    public int insertGroupItems(int i, int i2, boolean z) {
        if (i2 <= 0) {
            return 0;
        }
        enlargeArraysIfNeeded(this.mGroupCount + i2, true);
        ExpandableItemAdapter expandableItemAdapter = this.mAdapter;
        long[] jArr = this.mCachedGroupPosInfo;
        int[] iArr = this.mCachedGroupId;
        int i3 = (i - 1) + i2;
        for (int i4 = (this.mGroupCount - 1) + i2; i4 > i3; i4--) {
            jArr[i4] = jArr[i4 - i2];
            iArr[i4] = iArr[i4 - i2];
        }
        long j = z ? FLAG_EXPANDED : 0;
        int i5 = i + i2;
        int i6 = 0;
        for (int i7 = i; i7 < i5; i7++) {
            long groupId = expandableItemAdapter.getGroupId(i7);
            int childCount = expandableItemAdapter.getChildCount(i7);
            jArr[i7] = ((((long) i7) << 32) | ((long) childCount)) | j;
            iArr[i7] = (int) (groupId & LOWER_32BIT_MASK);
            i6 += childCount;
        }
        this.mGroupCount += i2;
        if (z) {
            this.mExpandedGroupCount += i2;
            this.mExpandedChildCount += i6;
        }
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, this.mGroupCount == 0 ? -1 : i - 1);
        return z ? i2 + i6 : i2;
    }

    public boolean isAllCollapsed() {
        return isEmpty() || this.mExpandedGroupCount == 0;
    }

    public boolean isAllExpanded() {
        return !isEmpty() && this.mExpandedGroupCount == this.mGroupCount;
    }

    public boolean isEmpty() {
        return this.mGroupCount == 0;
    }

    public boolean isGroupExpanded(int i) {
        return (this.mCachedGroupPosInfo[i] & FLAG_EXPANDED) != 0;
    }

    public void moveChildItem(int i, int i2, int i3, int i4) {
        if (i != i3) {
            int i5 = (int) (this.mCachedGroupPosInfo[i] & LOWER_31BIT_MASK);
            int i6 = (int) (this.mCachedGroupPosInfo[i3] & LOWER_31BIT_MASK);
            if (i5 == 0) {
                throw new IllegalStateException("moveChildItem(fromGroupPosition = " + i + ", fromChildPosition = " + i2 + ", toGroupPosition = " + i3 + ", toChildPosition = " + i4 + ")  --- may be a bug.");
            }
            this.mCachedGroupPosInfo[i] = (this.mCachedGroupPosInfo[i] & -2147483648L) | ((long) (i5 - 1));
            this.mCachedGroupPosInfo[i3] = (this.mCachedGroupPosInfo[i3] & -2147483648L) | ((long) (i6 + 1));
            if ((this.mCachedGroupPosInfo[i] & FLAG_EXPANDED) != 0) {
                this.mExpandedChildCount--;
            }
            if ((this.mCachedGroupPosInfo[i3] & FLAG_EXPANDED) != 0) {
                this.mExpandedChildCount++;
            }
            i5 = Math.min(i, i3);
            if (i5 > 0) {
                this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, i5 - 1);
            } else {
                this.mEndOfCalculatedOffsetGroupPosition = -1;
            }
        }
    }

    public void moveGroupItem(int i, int i2) {
        if (i != i2) {
            int i3;
            long j = this.mCachedGroupPosInfo[i];
            int i4 = this.mCachedGroupId[i];
            if (i2 < i) {
                for (i3 = i; i3 > i2; i3--) {
                    this.mCachedGroupPosInfo[i3] = this.mCachedGroupPosInfo[i3 - 1];
                    this.mCachedGroupId[i3] = this.mCachedGroupId[i3 - 1];
                }
            } else {
                for (i3 = i; i3 < i2; i3++) {
                    this.mCachedGroupPosInfo[i3] = this.mCachedGroupPosInfo[i3 + 1];
                    this.mCachedGroupId[i3] = this.mCachedGroupId[i3 + 1];
                }
            }
            this.mCachedGroupPosInfo[i2] = j;
            this.mCachedGroupId[i2] = i4;
            i3 = Math.min(i, i2);
            if (i3 > 0) {
                this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, i3 - 1);
            } else {
                this.mEndOfCalculatedOffsetGroupPosition = -1;
            }
        }
    }

    public void removeChildItem(int i, int i2) {
        removeChildItems(i, i2, 1);
    }

    public void removeChildItems(int i, int i2, int i3) {
        long j = this.mCachedGroupPosInfo[i];
        int i4 = (int) (LOWER_31BIT_MASK & j);
        if (i2 < 0 || i2 + i3 > i4) {
            throw new IllegalStateException("Invalid child position removeChildItems(groupPosition = " + i + ", childPosition = " + i2 + ", count = " + i3 + ")");
        }
        if ((FLAG_EXPANDED & j) != 0) {
            this.mExpandedChildCount -= i3;
        }
        this.mCachedGroupPosInfo[i] = (j & -2147483648L) | ((long) (i4 - i3));
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, i - 1);
    }

    public int removeGroupItem(int i) {
        return removeGroupItems(i, 1);
    }

    public int removeGroupItems(int i, int i2) {
        int i3 = 0;
        if (i2 <= 0) {
            return 0;
        }
        int i4;
        for (i4 = 0; i4 < i2; i4++) {
            long j = this.mCachedGroupPosInfo[i + i4];
            if ((FLAG_EXPANDED & j) != 0) {
                int i5 = (int) (j & LOWER_31BIT_MASK);
                i3 += i5;
                this.mExpandedChildCount -= i5;
                this.mExpandedGroupCount--;
            }
        }
        i4 = i3 + i2;
        this.mGroupCount -= i2;
        for (i3 = i; i3 < this.mGroupCount; i3++) {
            this.mCachedGroupPosInfo[i3] = this.mCachedGroupPosInfo[i3 + i2];
            this.mCachedGroupId[i3] = this.mCachedGroupId[i3 + i2];
        }
        this.mEndOfCalculatedOffsetGroupPosition = Math.min(this.mEndOfCalculatedOffsetGroupPosition, this.mGroupCount == 0 ? -1 : i - 1);
        return i4;
    }

    public void restoreExpandedGroupItems(int[] iArr, ExpandableItemAdapter expandableItemAdapter, OnGroupExpandListener onGroupExpandListener, OnGroupCollapseListener onGroupCollapseListener) {
        if (iArr != null && iArr.length != 0 && this.mCachedGroupPosInfo != null) {
            int i;
            long[] jArr = new long[this.mGroupCount];
            for (i = 0; i < this.mGroupCount; i++) {
                jArr[i] = (((long) this.mCachedGroupId[i]) << 32) | ((long) i);
            }
            Arrays.sort(jArr);
            int i2 = 0;
            for (int i3 : iArr) {
                int i4 = i2;
                while (i4 < jArr.length) {
                    int i5 = (int) (jArr[i4] >> 32);
                    int i6 = (int) (jArr[i4] & LOWER_31BIT_MASK);
                    if (i5 >= i3) {
                        if (i5 != i3) {
                            break;
                        }
                        i5 = i4 + 1;
                        if ((expandableItemAdapter == null || expandableItemAdapter.onHookGroupExpand(i6, false)) && expandGroup(i6) && onGroupExpandListener != null) {
                            onGroupExpandListener.onGroupExpand(i6, false);
                        }
                    } else if ((expandableItemAdapter == null || expandableItemAdapter.onHookGroupCollapse(i6, false)) && collapseGroup(i6) && onGroupCollapseListener != null) {
                        onGroupCollapseListener.onGroupCollapse(i6, false);
                        i5 = i4;
                    } else {
                        i5 = i4;
                    }
                    i4++;
                    i2 = i5;
                }
            }
            if (expandableItemAdapter != null || onGroupCollapseListener != null) {
                while (i2 < jArr.length) {
                    i = (int) (jArr[i2] & LOWER_31BIT_MASK);
                    if ((expandableItemAdapter == null || expandableItemAdapter.onHookGroupCollapse(i, false)) && collapseGroup(i) && onGroupCollapseListener != null) {
                        onGroupCollapseListener.onGroupCollapse(i, false);
                    }
                    i2++;
                }
            }
        }
    }
}
