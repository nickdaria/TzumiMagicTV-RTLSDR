package android.support.v7.util;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.RecyclerView.Adapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Snake> SNAKE_COMPARATOR = new C02791();

    static class C02791 implements Comparator<Snake> {
        C02791() {
        }

        public int compare(Snake snake, Snake snake2) {
            int i = snake.f9x - snake2.f9x;
            return i == 0 ? snake.f10y - snake2.f10y : i;
        }
    }

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        @Nullable
        public Object getChangePayload(int i, int i2) {
            return null;
        }

        public abstract int getNewListSize();

        public abstract int getOldListSize();
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_IGNORE = 16;
        private static final int FLAG_MASK = 31;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 5;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;
        private final List<Snake> mSnakes;

        DiffResult(Callback callback, List<Snake> list, int[] iArr, int[] iArr2, boolean z) {
            this.mSnakes = list;
            this.mOldItemStatuses = iArr;
            this.mNewItemStatuses = iArr2;
            Arrays.fill(this.mOldItemStatuses, 0);
            Arrays.fill(this.mNewItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = z;
            addRootSnake();
            findMatchingItems();
        }

        private void addRootSnake() {
            Snake snake = this.mSnakes.isEmpty() ? null : (Snake) this.mSnakes.get(0);
            if (snake == null || snake.f9x != 0 || snake.f10y != 0) {
                snake = new Snake();
                snake.f9x = 0;
                snake.f10y = 0;
                snake.removal = false;
                snake.size = 0;
                snake.reverse = false;
                this.mSnakes.add(0, snake);
            }
        }

        private void dispatchAdditions(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (this.mDetectMoves) {
                for (int i4 = i2 - 1; i4 >= 0; i4--) {
                    int i5 = this.mNewItemStatuses[i3 + i4] & 31;
                    switch (i5) {
                        case 0:
                            listUpdateCallback.onInserted(i, 1);
                            for (PostponedUpdate postponedUpdate : list) {
                                postponedUpdate.currentPos++;
                            }
                            break;
                        case 4:
                        case 8:
                            int i6 = this.mNewItemStatuses[i3 + i4] >> 5;
                            listUpdateCallback.onMoved(removePostponedUpdate(list, i6, true).currentPos, i);
                            if (i5 != 4) {
                                break;
                            }
                            listUpdateCallback.onChanged(i, 1, this.mCallback.getChangePayload(i6, i3 + i4));
                            break;
                        case 16:
                            list.add(new PostponedUpdate(i3 + i4, i, false));
                            break;
                        default:
                            throw new IllegalStateException("unknown flag for pos " + (i4 + i3) + " " + Long.toBinaryString((long) i5));
                    }
                }
                return;
            }
            listUpdateCallback.onInserted(i, i2);
        }

        private void dispatchRemovals(List<PostponedUpdate> list, ListUpdateCallback listUpdateCallback, int i, int i2, int i3) {
            if (this.mDetectMoves) {
                for (int i4 = i2 - 1; i4 >= 0; i4--) {
                    int i5 = this.mOldItemStatuses[i3 + i4] & 31;
                    switch (i5) {
                        case 0:
                            listUpdateCallback.onRemoved(i + i4, 1);
                            for (PostponedUpdate postponedUpdate : list) {
                                postponedUpdate.currentPos--;
                            }
                            break;
                        case 4:
                        case 8:
                            int i6 = this.mOldItemStatuses[i3 + i4] >> 5;
                            PostponedUpdate removePostponedUpdate = removePostponedUpdate(list, i6, false);
                            listUpdateCallback.onMoved(i + i4, removePostponedUpdate.currentPos - 1);
                            if (i5 != 4) {
                                break;
                            }
                            listUpdateCallback.onChanged(removePostponedUpdate.currentPos - 1, 1, this.mCallback.getChangePayload(i3 + i4, i6));
                            break;
                        case 16:
                            list.add(new PostponedUpdate(i3 + i4, i + i4, true));
                            break;
                        default:
                            throw new IllegalStateException("unknown flag for pos " + (i4 + i3) + " " + Long.toBinaryString((long) i5));
                    }
                }
                return;
            }
            listUpdateCallback.onRemoved(i, i2);
        }

        private void findAddition(int i, int i2, int i3) {
            if (this.mOldItemStatuses[i - 1] == 0) {
                findMatchingItem(i, i2, i3, false);
            }
        }

        private boolean findMatchingItem(int i, int i2, int i3, boolean z) {
            int i4;
            int i5;
            int i6 = 8;
            if (z) {
                i4 = i2 - 1;
                i2--;
                i5 = i4;
                i4 = i;
            } else {
                i5 = i - 1;
                i4 = i - 1;
            }
            int i7 = i4;
            while (i3 >= 0) {
                Snake snake = (Snake) this.mSnakes.get(i3);
                int i8 = snake.f9x + snake.size;
                int i9 = snake.f10y + snake.size;
                if (z) {
                    for (i7--; i7 >= i8; i7--) {
                        if (this.mCallback.areItemsTheSame(i7, i5)) {
                            i4 = this.mCallback.areContentsTheSame(i7, i5) ? 8 : 4;
                            this.mNewItemStatuses[i5] = (i7 << 5) | 16;
                            this.mOldItemStatuses[i7] = i4 | (i5 << 5);
                            return true;
                        }
                    }
                    continue;
                } else {
                    for (i7 = i2 - 1; i7 >= i9; i7--) {
                        if (this.mCallback.areItemsTheSame(i5, i7)) {
                            if (!this.mCallback.areContentsTheSame(i5, i7)) {
                                i6 = 4;
                            }
                            this.mOldItemStatuses[i - 1] = (i7 << 5) | 16;
                            this.mNewItemStatuses[i7] = ((i - 1) << 5) | i6;
                            return true;
                        }
                    }
                    continue;
                }
                i7 = snake.f9x;
                i2 = snake.f10y;
                i3--;
            }
            return false;
        }

        private void findMatchingItems() {
            int i = this.mOldListSize;
            int i2 = this.mNewListSize;
            for (int size = this.mSnakes.size() - 1; size >= 0; size--) {
                Snake snake = (Snake) this.mSnakes.get(size);
                int i3 = snake.f9x + snake.size;
                int i4 = snake.f10y + snake.size;
                if (this.mDetectMoves) {
                    while (i > i3) {
                        findAddition(i, i2, size);
                        i--;
                    }
                    while (i2 > i4) {
                        findRemoval(i, i2, size);
                        i2--;
                    }
                }
                for (i2 = 0; i2 < snake.size; i2++) {
                    i3 = snake.f9x + i2;
                    i4 = snake.f10y + i2;
                    i = this.mCallback.areContentsTheSame(i3, i4) ? 1 : 2;
                    this.mOldItemStatuses[i3] = (i4 << 5) | i;
                    this.mNewItemStatuses[i4] = i | (i3 << 5);
                }
                i = snake.f9x;
                i2 = snake.f10y;
            }
        }

        private void findRemoval(int i, int i2, int i3) {
            if (this.mNewItemStatuses[i2 - 1] == 0) {
                findMatchingItem(i, i2, i3, true);
            }
        }

        private static PostponedUpdate removePostponedUpdate(List<PostponedUpdate> list, int i, boolean z) {
            for (int size = list.size() - 1; size >= 0; size--) {
                PostponedUpdate postponedUpdate = (PostponedUpdate) list.get(size);
                if (postponedUpdate.posInOwnerList == i && postponedUpdate.removal == z) {
                    list.remove(size);
                    for (int i2 = size; i2 < list.size(); i2++) {
                        PostponedUpdate postponedUpdate2 = (PostponedUpdate) list.get(i2);
                        postponedUpdate2.currentPos = (z ? 1 : -1) + postponedUpdate2.currentPos;
                    }
                    return postponedUpdate;
                }
            }
            return null;
        }

        public void dispatchUpdatesTo(ListUpdateCallback listUpdateCallback) {
            BatchingListUpdateCallback batchingListUpdateCallback = listUpdateCallback instanceof BatchingListUpdateCallback ? (BatchingListUpdateCallback) listUpdateCallback : new BatchingListUpdateCallback(listUpdateCallback);
            List arrayList = new ArrayList();
            int i = this.mOldListSize;
            int size = this.mSnakes.size() - 1;
            int i2 = this.mNewListSize;
            while (size >= 0) {
                Snake snake = (Snake) this.mSnakes.get(size);
                int i3 = snake.size;
                int i4 = snake.f9x + i3;
                int i5 = snake.f10y + i3;
                if (i4 < i) {
                    dispatchRemovals(arrayList, batchingListUpdateCallback, i4, i - i4, i4);
                }
                if (i5 < i2) {
                    dispatchAdditions(arrayList, batchingListUpdateCallback, i4, i2 - i5, i5);
                }
                for (int i6 = i3 - 1; i6 >= 0; i6--) {
                    if ((this.mOldItemStatuses[snake.f9x + i6] & 31) == 2) {
                        batchingListUpdateCallback.onChanged(snake.f9x + i6, 1, this.mCallback.getChangePayload(snake.f9x + i6, snake.f10y + i6));
                    }
                }
                i = snake.f9x;
                size--;
                i2 = snake.f10y;
            }
            batchingListUpdateCallback.dispatchLastEvent();
        }

        public void dispatchUpdatesTo(final Adapter adapter) {
            dispatchUpdatesTo(new ListUpdateCallback() {
                public void onChanged(int i, int i2, Object obj) {
                    adapter.notifyItemRangeChanged(i, i2, obj);
                }

                public void onInserted(int i, int i2) {
                    adapter.notifyItemRangeInserted(i, i2);
                }

                public void onMoved(int i, int i2) {
                    adapter.notifyItemMoved(i, i2);
                }

                public void onRemoved(int i, int i2) {
                    adapter.notifyItemRangeRemoved(i, i2);
                }
            });
        }

        @VisibleForTesting
        List<Snake> getSnakes() {
            return this.mSnakes;
        }
    }

    private static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        public PostponedUpdate(int i, int i2, boolean z) {
            this.posInOwnerList = i;
            this.currentPos = i2;
            this.removal = z;
        }
    }

    static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range(int i, int i2, int i3, int i4) {
            this.oldListStart = i;
            this.oldListEnd = i2;
            this.newListStart = i3;
            this.newListEnd = i4;
        }
    }

    static class Snake {
        boolean removal;
        boolean reverse;
        int size;
        int f9x;
        int f10y;

        Snake() {
        }
    }

    private DiffUtil() {
    }

    public static DiffResult calculateDiff(Callback callback) {
        return calculateDiff(callback, true);
    }

    public static DiffResult calculateDiff(Callback callback, boolean z) {
        int oldListSize = callback.getOldListSize();
        int newListSize = callback.getNewListSize();
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        arrayList2.add(new Range(0, oldListSize, 0, newListSize));
        int abs = (oldListSize + newListSize) + Math.abs(oldListSize - newListSize);
        int[] iArr = new int[(abs * 2)];
        int[] iArr2 = new int[(abs * 2)];
        List arrayList3 = new ArrayList();
        while (!arrayList2.isEmpty()) {
            Range range = (Range) arrayList2.remove(arrayList2.size() - 1);
            Snake diffPartial = diffPartial(callback, range.oldListStart, range.oldListEnd, range.newListStart, range.newListEnd, iArr, iArr2, abs);
            if (diffPartial != null) {
                if (diffPartial.size > 0) {
                    arrayList.add(diffPartial);
                }
                diffPartial.f9x += range.oldListStart;
                diffPartial.f10y += range.newListStart;
                Range range2 = arrayList3.isEmpty() ? new Range() : (Range) arrayList3.remove(arrayList3.size() - 1);
                range2.oldListStart = range.oldListStart;
                range2.newListStart = range.newListStart;
                if (diffPartial.reverse) {
                    range2.oldListEnd = diffPartial.f9x;
                    range2.newListEnd = diffPartial.f10y;
                } else if (diffPartial.removal) {
                    range2.oldListEnd = diffPartial.f9x - 1;
                    range2.newListEnd = diffPartial.f10y;
                } else {
                    range2.oldListEnd = diffPartial.f9x;
                    range2.newListEnd = diffPartial.f10y - 1;
                }
                arrayList2.add(range2);
                if (!diffPartial.reverse) {
                    range.oldListStart = diffPartial.f9x + diffPartial.size;
                    range.newListStart = diffPartial.f10y + diffPartial.size;
                } else if (diffPartial.removal) {
                    range.oldListStart = (diffPartial.f9x + diffPartial.size) + 1;
                    range.newListStart = diffPartial.f10y + diffPartial.size;
                } else {
                    range.oldListStart = diffPartial.f9x + diffPartial.size;
                    range.newListStart = (diffPartial.f10y + diffPartial.size) + 1;
                }
                arrayList2.add(range);
            } else {
                arrayList3.add(range);
            }
        }
        Collections.sort(arrayList, SNAKE_COMPARATOR);
        return new DiffResult(callback, arrayList, iArr, iArr2, z);
    }

    private static Snake diffPartial(Callback callback, int i, int i2, int i3, int i4, int[] iArr, int[] iArr2, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        if (i2 - i < 1 || i4 - i3 < 1) {
            return null;
        }
        int i8 = i6 - i7;
        int i9 = ((i6 + i7) + 1) / 2;
        Arrays.fill(iArr, (i5 - i9) - 1, (i5 + i9) + 1, 0);
        Arrays.fill(iArr2, ((i5 - i9) - 1) + i8, ((i5 + i9) + 1) + i8, i6);
        Object obj = i8 % 2 != 0 ? 1 : null;
        int i10 = 0;
        while (i10 <= i9) {
            int i11;
            int i12 = -i10;
            while (i12 <= i10) {
                boolean z;
                if (i12 == (-i10) || (i12 != i10 && iArr[(i5 + i12) - 1] < iArr[(i5 + i12) + 1])) {
                    i11 = iArr[(i5 + i12) + 1];
                    z = false;
                } else {
                    i11 = iArr[(i5 + i12) - 1] + 1;
                    z = true;
                }
                int i13 = i11;
                i11 -= i12;
                while (i13 < i6 && i11 < i7 && callback.areItemsTheSame(i + i13, i3 + i11)) {
                    i13++;
                    i11++;
                }
                iArr[i5 + i12] = i13;
                if (obj == null || i12 < (i8 - i10) + 1 || i12 > (i8 + i10) - 1 || iArr[i5 + i12] < iArr2[i5 + i12]) {
                    i12 += 2;
                } else {
                    Snake snake = new Snake();
                    snake.f9x = iArr2[i5 + i12];
                    snake.f10y = snake.f9x - i12;
                    snake.size = iArr[i5 + i12] - iArr2[i5 + i12];
                    snake.removal = z;
                    snake.reverse = false;
                    return snake;
                }
            }
            i12 = -i10;
            while (i12 <= i10) {
                int i14 = i12 + i8;
                if (i14 == i10 + i8 || (i14 != (-i10) + i8 && iArr2[(i5 + i14) - 1] < iArr2[(i5 + i14) + 1])) {
                    i11 = iArr2[(i5 + i14) - 1];
                    z = false;
                } else {
                    i11 = iArr2[(i5 + i14) + 1] - 1;
                    z = true;
                }
                i13 = i11;
                i11 -= i14;
                while (i13 > 0 && i11 > 0 && callback.areItemsTheSame((i + i13) - 1, (i3 + i11) - 1)) {
                    i13--;
                    i11--;
                }
                iArr2[i5 + i14] = i13;
                if (obj != null || i12 + i8 < (-i10) || i12 + i8 > i10 || iArr[i5 + i14] < iArr2[i5 + i14]) {
                    i12 += 2;
                } else {
                    snake = new Snake();
                    snake.f9x = iArr2[i5 + i14];
                    snake.f10y = snake.f9x - i14;
                    snake.size = iArr[i5 + i14] - iArr2[i5 + i14];
                    snake.removal = z;
                    snake.reverse = true;
                    return snake;
                }
            }
            i10++;
        }
        throw new IllegalStateException("DiffUtil hit an unexpected case while trying to calculate the optimal path. Please make sure your data is not changing during the diff calculation.");
    }
}
