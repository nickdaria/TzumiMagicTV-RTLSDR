package com.h6ah4i.android.widget.advrecyclerview.utils;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;

public class CustomRecyclerViewUtils {
    public static final int INVALID_SPAN_COUNT = -1;
    public static final int INVALID_SPAN_ID = -1;
    public static final int LAYOUT_TYPE_GRID_HORIZONTAL = 2;
    public static final int LAYOUT_TYPE_GRID_VERTICAL = 3;
    public static final int LAYOUT_TYPE_LINEAR_HORIZONTAL = 0;
    public static final int LAYOUT_TYPE_LINEAR_VERTICAL = 1;
    public static final int LAYOUT_TYPE_STAGGERED_GRID_HORIZONTAL = 4;
    public static final int LAYOUT_TYPE_STAGGERED_GRID_VERTICAL = 5;
    public static final int LAYOUT_TYPE_UNKNOWN = -1;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_UNKNOWN = -1;
    public static final int ORIENTATION_VERTICAL = 1;

    public static int extractOrientation(int i) {
        switch (i) {
            case -1:
                return -1;
            case 0:
            case 2:
            case 4:
                return 0;
            case 1:
            case 3:
            case 5:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown layout type (= " + i + ")");
        }
    }

    public static ViewHolder findChildViewHolderUnderWithTranslation(@NonNull RecyclerView recyclerView, float f, float f2) {
        View findChildViewUnder = recyclerView.findChildViewUnder(f, f2);
        return findChildViewUnder != null ? recyclerView.getChildViewHolder(findChildViewUnder) : null;
    }

    public static ViewHolder findChildViewHolderUnderWithoutTranslation(@NonNull RecyclerView recyclerView, float f, float f2) {
        View findChildViewUnderWithoutTranslation = findChildViewUnderWithoutTranslation(recyclerView, f, f2);
        return findChildViewUnderWithoutTranslation != null ? recyclerView.getChildViewHolder(findChildViewUnderWithoutTranslation) : null;
    }

    private static View findChildViewUnderWithoutTranslation(@NonNull ViewGroup viewGroup, float f, float f2) {
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = viewGroup.getChildAt(childCount);
            if (f >= ((float) childAt.getLeft()) && f <= ((float) childAt.getRight()) && f2 >= ((float) childAt.getTop()) && f2 <= ((float) childAt.getBottom())) {
                return childAt;
            }
        }
        return null;
    }

    public static int findFirstCompletelyVisibleItemPosition(@NonNull RecyclerView recyclerView) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        return layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition() : -1;
    }

    public static int findFirstVisibleItemPosition(@NonNull RecyclerView recyclerView, boolean z) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        return layoutManager instanceof LinearLayoutManager ? z ? findFirstVisibleItemPositionIncludesPadding((LinearLayoutManager) layoutManager) : ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() : -1;
    }

    private static int findFirstVisibleItemPositionIncludesPadding(LinearLayoutManager linearLayoutManager) {
        View findOneVisibleChildIncludesPadding = findOneVisibleChildIncludesPadding(linearLayoutManager, 0, linearLayoutManager.getChildCount(), false, true);
        return findOneVisibleChildIncludesPadding == null ? -1 : linearLayoutManager.getPosition(findOneVisibleChildIncludesPadding);
    }

    public static int findLastCompletelyVisibleItemPosition(@NonNull RecyclerView recyclerView) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        return layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() : -1;
    }

    public static int findLastVisibleItemPosition(@NonNull RecyclerView recyclerView, boolean z) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        return layoutManager instanceof LinearLayoutManager ? z ? findLastVisibleItemPositionIncludesPadding((LinearLayoutManager) layoutManager) : ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() : -1;
    }

    private static int findLastVisibleItemPositionIncludesPadding(LinearLayoutManager linearLayoutManager) {
        View findOneVisibleChildIncludesPadding = findOneVisibleChildIncludesPadding(linearLayoutManager, linearLayoutManager.getChildCount() - 1, -1, false, true);
        return findOneVisibleChildIncludesPadding == null ? -1 : linearLayoutManager.getPosition(findOneVisibleChildIncludesPadding);
    }

    private static View findOneVisibleChildIncludesPadding(LinearLayoutManager linearLayoutManager, int i, int i2, boolean z, boolean z2) {
        int i3 = 1;
        int i4 = linearLayoutManager.getOrientation() == 1 ? 1 : 0;
        int height = i4 != 0 ? linearLayoutManager.getHeight() : linearLayoutManager.getWidth();
        if (i2 <= i) {
            i3 = -1;
        }
        View view = null;
        while (i != i2) {
            View childAt = linearLayoutManager.getChildAt(i);
            int top = i4 != 0 ? childAt.getTop() : childAt.getLeft();
            int bottom = i4 != 0 ? childAt.getBottom() : childAt.getRight();
            if (top < height && bottom > 0) {
                if (!z) {
                    return childAt;
                }
                if (top >= 0 && bottom <= height) {
                    return childAt;
                }
                if (z2 && view == null) {
                    i += i3;
                    view = childAt;
                }
            }
            childAt = view;
            i += i3;
            view = childAt;
        }
        return view;
    }

    public static View findViewByPosition(LayoutManager layoutManager, int i) {
        return i != -1 ? layoutManager.findViewByPosition(i) : null;
    }

    public static Rect getDecorationOffsets(@NonNull LayoutManager layoutManager, View view, Rect rect) {
        rect.left = layoutManager.getLeftDecorationWidth(view);
        rect.right = layoutManager.getRightDecorationWidth(view);
        rect.top = layoutManager.getTopDecorationHeight(view);
        rect.bottom = layoutManager.getBottomDecorationHeight(view);
        return rect;
    }

    private static View getLaidOutItemView(@Nullable ViewHolder viewHolder) {
        if (viewHolder == null) {
            return null;
        }
        View view = viewHolder.itemView;
        return ViewCompat.isLaidOut(view) ? view : null;
    }

    public static Rect getLayoutMargins(View view, Rect rect) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
            rect.left = marginLayoutParams.leftMargin;
            rect.right = marginLayoutParams.rightMargin;
            rect.top = marginLayoutParams.topMargin;
            rect.bottom = marginLayoutParams.bottomMargin;
        } else {
            rect.bottom = 0;
            rect.top = 0;
            rect.right = 0;
            rect.left = 0;
        }
        return rect;
    }

    public static int getLayoutType(@Nullable LayoutManager layoutManager) {
        return layoutManager instanceof GridLayoutManager ? ((GridLayoutManager) layoutManager).getOrientation() == 0 ? 2 : 3 : layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).getOrientation() == 0 ? 0 : 1 : layoutManager instanceof StaggeredGridLayoutManager ? ((StaggeredGridLayoutManager) layoutManager).getOrientation() == 0 ? 4 : 5 : -1;
    }

    public static int getLayoutType(@NonNull RecyclerView recyclerView) {
        return getLayoutType(recyclerView.getLayoutManager());
    }

    public static int getOrientation(@NonNull LayoutManager layoutManager) {
        return layoutManager instanceof GridLayoutManager ? ((GridLayoutManager) layoutManager).getOrientation() : layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).getOrientation() : layoutManager instanceof StaggeredGridLayoutManager ? ((StaggeredGridLayoutManager) layoutManager).getOrientation() : -1;
    }

    public static int getOrientation(@NonNull RecyclerView recyclerView) {
        return getOrientation(recyclerView.getLayoutManager());
    }

    public static int getSpanCount(@NonNull RecyclerView recyclerView) {
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        return layoutManager instanceof GridLayoutManager ? ((GridLayoutManager) layoutManager).getSpanCount() : layoutManager instanceof StaggeredGridLayoutManager ? ((StaggeredGridLayoutManager) layoutManager).getSpanCount() : 1;
    }

    public static int getSpanIndex(@Nullable ViewHolder viewHolder) {
        View laidOutItemView = getLaidOutItemView(viewHolder);
        if (laidOutItemView == null) {
            return -1;
        }
        LayoutParams layoutParams = laidOutItemView.getLayoutParams();
        return layoutParams instanceof StaggeredGridLayoutManager.LayoutParams ? ((StaggeredGridLayoutManager.LayoutParams) layoutParams).getSpanIndex() : layoutParams instanceof GridLayoutManager.LayoutParams ? ((GridLayoutManager.LayoutParams) layoutParams).getSpanIndex() : layoutParams instanceof RecyclerView.LayoutParams ? 0 : -1;
    }

    public static int getSpanSize(@Nullable ViewHolder viewHolder) {
        View laidOutItemView = getLaidOutItemView(viewHolder);
        if (laidOutItemView == null) {
            return -1;
        }
        LayoutParams layoutParams = laidOutItemView.getLayoutParams();
        return layoutParams instanceof StaggeredGridLayoutManager.LayoutParams ? ((StaggeredGridLayoutManager.LayoutParams) layoutParams).isFullSpan() ? getSpanCount((RecyclerView) laidOutItemView.getParent()) : 1 : layoutParams instanceof GridLayoutManager.LayoutParams ? ((GridLayoutManager.LayoutParams) layoutParams).getSpanSize() : layoutParams instanceof RecyclerView.LayoutParams ? 1 : -1;
    }

    public static int getSynchronizedPosition(@NonNull ViewHolder viewHolder) {
        int layoutPosition = viewHolder.getLayoutPosition();
        return layoutPosition == viewHolder.getAdapterPosition() ? layoutPosition : -1;
    }

    public static Rect getViewBounds(@NonNull View view, @NonNull Rect rect) {
        rect.left = view.getLeft();
        rect.right = view.getRight();
        rect.top = view.getTop();
        rect.bottom = view.getBottom();
        return rect;
    }

    public static boolean isFullSpan(@Nullable ViewHolder viewHolder) {
        View laidOutItemView = getLaidOutItemView(viewHolder);
        if (laidOutItemView == null) {
            return true;
        }
        LayoutParams layoutParams = laidOutItemView.getLayoutParams();
        if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return ((StaggeredGridLayoutManager.LayoutParams) layoutParams).isFullSpan();
        }
        if (!(layoutParams instanceof GridLayoutManager.LayoutParams)) {
            return layoutParams instanceof RecyclerView.LayoutParams ? true : true;
        } else {
            return getSpanCount((RecyclerView) laidOutItemView.getParent()) == ((GridLayoutManager.LayoutParams) layoutParams).getSpanSize();
        }
    }

    public static boolean isGridLayout(int i) {
        return i == 3 || i == 2;
    }

    public static boolean isLinearLayout(int i) {
        return i == 1 || i == 0;
    }

    public static boolean isStaggeredGridLayout(int i) {
        return i == 5 || i == 4;
    }

    public static int safeGetAdapterPosition(@Nullable ViewHolder viewHolder) {
        return viewHolder != null ? viewHolder.getAdapterPosition() : -1;
    }

    public static int safeGetLayoutPosition(@Nullable ViewHolder viewHolder) {
        return viewHolder != null ? viewHolder.getLayoutPosition() : -1;
    }
}
