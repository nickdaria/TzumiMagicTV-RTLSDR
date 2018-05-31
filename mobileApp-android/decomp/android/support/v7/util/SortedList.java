package android.support.v7.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
    private static final int CAPACITY_GROWTH = 10;
    private static final int DELETION = 2;
    private static final int INSERTION = 1;
    public static final int INVALID_POSITION = -1;
    private static final int LOOKUP = 4;
    private static final int MIN_CAPACITY = 10;
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mMergedSize;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public static abstract class Callback<T2> implements ListUpdateCallback, Comparator<T2> {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        public abstract int compare(T2 t2, T2 t22);

        public abstract void onChanged(int i, int i2);

        public void onChanged(int i, int i2, Object obj) {
            onChanged(i, i2);
        }
    }

    public static class BatchedCallback<T2> extends Callback<T2> {
        private final BatchingListUpdateCallback mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
        final Callback<T2> mWrappedCallback;

        public BatchedCallback(Callback<T2> callback) {
            this.mWrappedCallback = callback;
        }

        public boolean areContentsTheSame(T2 t2, T2 t22) {
            return this.mWrappedCallback.areContentsTheSame(t2, t22);
        }

        public boolean areItemsTheSame(T2 t2, T2 t22) {
            return this.mWrappedCallback.areItemsTheSame(t2, t22);
        }

        public int compare(T2 t2, T2 t22) {
            return this.mWrappedCallback.compare(t2, t22);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }

        public void onChanged(int i, int i2) {
            this.mBatchingListUpdateCallback.onChanged(i, i2, null);
        }

        public void onInserted(int i, int i2) {
            this.mBatchingListUpdateCallback.onInserted(i, i2);
        }

        public void onMoved(int i, int i2) {
            this.mBatchingListUpdateCallback.onMoved(i, i2);
        }

        public void onRemoved(int i, int i2) {
            this.mBatchingListUpdateCallback.onRemoved(i, i2);
        }
    }

    public SortedList(Class<T> cls, Callback<T> callback) {
        this(cls, callback, 10);
    }

    public SortedList(Class<T> cls, Callback<T> callback, int i) {
        this.mTClass = cls;
        this.mData = (Object[]) Array.newInstance(cls, i);
        this.mCallback = callback;
        this.mSize = 0;
    }

    private int add(T t, boolean z) {
        int i = 0;
        int findIndexOf = findIndexOf(t, this.mData, 0, this.mSize, 1);
        if (findIndexOf != -1) {
            if (findIndexOf < this.mSize) {
                Object obj = this.mData[findIndexOf];
                if (this.mCallback.areItemsTheSame(obj, t)) {
                    if (this.mCallback.areContentsTheSame(obj, t)) {
                        this.mData[findIndexOf] = t;
                        return findIndexOf;
                    }
                    this.mData[findIndexOf] = t;
                    this.mCallback.onChanged(findIndexOf, 1);
                    return findIndexOf;
                }
            }
            i = findIndexOf;
        }
        addToData(i, t);
        if (z) {
            this.mCallback.onInserted(i, 1);
        }
        return i;
    }

    private void addAllInternal(T[] tArr) {
        int i = !(this.mCallback instanceof BatchedCallback) ? 1 : 0;
        if (i != 0) {
            beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        Arrays.sort(tArr, this.mCallback);
        int deduplicate = deduplicate(tArr);
        if (this.mSize == 0) {
            this.mData = tArr;
            this.mSize = deduplicate;
            this.mMergedSize = deduplicate;
            this.mCallback.onInserted(0, deduplicate);
        } else {
            merge(tArr, deduplicate);
        }
        this.mOldData = null;
        if (i != 0) {
            endBatchedUpdates();
        }
    }

    private void addToData(int i, T t) {
        if (i > this.mSize) {
            throw new IndexOutOfBoundsException("cannot add item to " + i + " because size is " + this.mSize);
        }
        if (this.mSize == this.mData.length) {
            Object[] objArr = (Object[]) Array.newInstance(this.mTClass, this.mData.length + 10);
            System.arraycopy(this.mData, 0, objArr, 0, i);
            objArr[i] = t;
            System.arraycopy(this.mData, i, objArr, i + 1, this.mSize - i);
            this.mData = objArr;
        } else {
            System.arraycopy(this.mData, i, this.mData, i + 1, this.mSize - i);
            this.mData[i] = t;
        }
        this.mSize++;
    }

    private int deduplicate(T[] tArr) {
        int i = 1;
        if (tArr.length == 0) {
            throw new IllegalArgumentException("Input array must be non-empty");
        }
        int i2 = 0;
        int i3 = 1;
        while (i < tArr.length) {
            Object obj = tArr[i];
            int compare = this.mCallback.compare(tArr[i2], obj);
            if (compare > 0) {
                throw new IllegalArgumentException("Input must be sorted in ascending order.");
            }
            if (compare == 0) {
                compare = findSameItem(obj, tArr, i2, i3);
                if (compare != -1) {
                    tArr[compare] = obj;
                } else {
                    if (i3 != i) {
                        tArr[i3] = obj;
                    }
                    i3++;
                }
            } else {
                if (i3 != i) {
                    tArr[i3] = obj;
                }
                i2 = i3;
                i3++;
            }
            i++;
        }
        return i3;
    }

    private int findIndexOf(T t, T[] tArr, int i, int i2, int i3) {
        int i4 = i2;
        int i5 = i;
        while (i5 < i4) {
            int i6 = (i5 + i4) / 2;
            Object obj = tArr[i6];
            int compare = this.mCallback.compare(obj, t);
            if (compare < 0) {
                int i7 = i4;
                i4 = i6 + 1;
                i6 = i7;
            } else if (compare != 0) {
                i4 = i5;
            } else if (this.mCallback.areItemsTheSame(obj, t)) {
                return i6;
            } else {
                i4 = linearEqualitySearch(t, i6, i5, i4);
                return i3 == 1 ? i4 != -1 ? i4 : i6 : i4;
            }
            i5 = i4;
            i4 = i6;
        }
        if (i3 != 1) {
            i5 = -1;
        }
        return i5;
    }

    private int findSameItem(T t, T[] tArr, int i, int i2) {
        for (int i3 = i; i3 < i2; i3++) {
            if (this.mCallback.areItemsTheSame(tArr[i3], t)) {
                return i3;
            }
        }
        return -1;
    }

    private int linearEqualitySearch(T t, int i, int i2, int i3) {
        int i4 = i - 1;
        while (i4 >= i2) {
            Object obj = this.mData[i4];
            if (this.mCallback.compare(obj, t) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(obj, t)) {
                return i4;
            } else {
                i4--;
            }
        }
        i4 = i + 1;
        while (i4 < i3) {
            obj = this.mData[i4];
            if (this.mCallback.compare(obj, t) != 0) {
                break;
            } else if (this.mCallback.areItemsTheSame(obj, t)) {
                return i4;
            } else {
                i4++;
            }
        }
        return -1;
    }

    private void merge(T[] tArr, int i) {
        this.mData = (Object[]) Array.newInstance(this.mTClass, (this.mSize + i) + 10);
        this.mMergedSize = 0;
        int i2 = 0;
        while (true) {
            if (this.mOldDataStart >= this.mOldDataSize && i2 >= i) {
                return;
            }
            if (this.mOldDataStart == this.mOldDataSize) {
                int i3 = i - i2;
                System.arraycopy(tArr, i2, this.mData, this.mMergedSize, i3);
                this.mMergedSize += i3;
                this.mSize += i3;
                this.mCallback.onInserted(this.mMergedSize - i3, i3);
                return;
            } else if (i2 == i) {
                i2 = this.mOldDataSize - this.mOldDataStart;
                System.arraycopy(this.mOldData, this.mOldDataStart, this.mData, this.mMergedSize, i2);
                this.mMergedSize = i2 + this.mMergedSize;
                return;
            } else {
                Object obj = this.mOldData[this.mOldDataStart];
                Object obj2 = tArr[i2];
                int compare = this.mCallback.compare(obj, obj2);
                if (compare > 0) {
                    Object[] objArr = this.mData;
                    compare = this.mMergedSize;
                    this.mMergedSize = compare + 1;
                    objArr[compare] = obj2;
                    this.mSize++;
                    i2++;
                    this.mCallback.onInserted(this.mMergedSize - 1, 1);
                } else if (compare == 0 && this.mCallback.areItemsTheSame(obj, obj2)) {
                    Object[] objArr2 = this.mData;
                    int i4 = this.mMergedSize;
                    this.mMergedSize = i4 + 1;
                    objArr2[i4] = obj2;
                    i2++;
                    this.mOldDataStart++;
                    if (!this.mCallback.areContentsTheSame(obj, obj2)) {
                        this.mCallback.onChanged(this.mMergedSize - 1, 1);
                    }
                } else {
                    Object[] objArr3 = this.mData;
                    compare = this.mMergedSize;
                    this.mMergedSize = compare + 1;
                    objArr3[compare] = obj;
                    this.mOldDataStart++;
                }
            }
        }
    }

    private boolean remove(T t, boolean z) {
        int findIndexOf = findIndexOf(t, this.mData, 0, this.mSize, 2);
        if (findIndexOf == -1) {
            return false;
        }
        removeItemAtIndex(findIndexOf, z);
        return true;
    }

    private void removeItemAtIndex(int i, boolean z) {
        System.arraycopy(this.mData, i + 1, this.mData, i, (this.mSize - i) - 1);
        this.mSize--;
        this.mData[this.mSize] = null;
        if (z) {
            this.mCallback.onRemoved(i, 1);
        }
    }

    private void throwIfMerging() {
        if (this.mOldData != null) {
            throw new IllegalStateException("Cannot call this method from within addAll");
        }
    }

    public int add(T t) {
        throwIfMerging();
        return add(t, true);
    }

    public void addAll(Collection<T> collection) {
        addAll(collection.toArray((Object[]) Array.newInstance(this.mTClass, collection.size())), true);
    }

    public void addAll(T... tArr) {
        addAll(tArr, false);
    }

    public void addAll(T[] tArr, boolean z) {
        throwIfMerging();
        if (tArr.length != 0) {
            if (z) {
                addAllInternal(tArr);
                return;
            }
            Object[] objArr = (Object[]) Array.newInstance(this.mTClass, tArr.length);
            System.arraycopy(tArr, 0, objArr, 0, tArr.length);
            addAllInternal(objArr);
        }
    }

    public void beginBatchedUpdates() {
        throwIfMerging();
        if (!(this.mCallback instanceof BatchedCallback)) {
            if (this.mBatchedCallback == null) {
                this.mBatchedCallback = new BatchedCallback(this.mCallback);
            }
            this.mCallback = this.mBatchedCallback;
        }
    }

    public void clear() {
        throwIfMerging();
        if (this.mSize != 0) {
            int i = this.mSize;
            Arrays.fill(this.mData, 0, i, null);
            this.mSize = 0;
            this.mCallback.onRemoved(0, i);
        }
    }

    public void endBatchedUpdates() {
        throwIfMerging();
        if (this.mCallback instanceof BatchedCallback) {
            ((BatchedCallback) this.mCallback).dispatchLastEvent();
        }
        if (this.mCallback == this.mBatchedCallback) {
            this.mCallback = this.mBatchedCallback.mWrappedCallback;
        }
    }

    public T get(int i) throws IndexOutOfBoundsException {
        if (i < this.mSize && i >= 0) {
            return (this.mOldData == null || i < this.mMergedSize) ? this.mData[i] : this.mOldData[(i - this.mMergedSize) + this.mOldDataStart];
        } else {
            throw new IndexOutOfBoundsException("Asked to get item at " + i + " but size is " + this.mSize);
        }
    }

    public int indexOf(T t) {
        if (this.mOldData != null) {
            int findIndexOf = findIndexOf(t, this.mData, 0, this.mMergedSize, 4);
            if (findIndexOf != -1) {
                return findIndexOf;
            }
            findIndexOf = findIndexOf(t, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
            return findIndexOf != -1 ? (findIndexOf - this.mOldDataStart) + this.mMergedSize : -1;
        } else {
            return findIndexOf(t, this.mData, 0, this.mSize, 4);
        }
    }

    public void recalculatePositionOfItemAt(int i) {
        throwIfMerging();
        Object obj = get(i);
        removeItemAtIndex(i, false);
        int add = add(obj, false);
        if (i != add) {
            this.mCallback.onMoved(i, add);
        }
    }

    public boolean remove(T t) {
        throwIfMerging();
        return remove(t, true);
    }

    public T removeItemAt(int i) {
        throwIfMerging();
        T t = get(i);
        removeItemAtIndex(i, true);
        return t;
    }

    public int size() {
        return this.mSize;
    }

    public void updateItemAt(int i, T t) {
        throwIfMerging();
        T t2 = get(i);
        boolean z = t2 == t || !this.mCallback.areContentsTheSame(t2, t);
        if (t2 == t || this.mCallback.compare(t2, t) != 0) {
            if (z) {
                this.mCallback.onChanged(i, 1);
            }
            removeItemAtIndex(i, false);
            int add = add(t, false);
            if (i != add) {
                this.mCallback.onMoved(i, add);
                return;
            }
            return;
        }
        this.mData[i] = t;
        if (z) {
            this.mCallback.onChanged(i, 1);
        }
    }
}
