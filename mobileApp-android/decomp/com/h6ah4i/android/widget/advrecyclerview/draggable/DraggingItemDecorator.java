package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;
import android.view.View;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;

class DraggingItemDecorator extends BaseDraggableItemDecorator {
    private static final String TAG = "DraggingItemDecorator";
    private Bitmap mDraggingItemImage;
    private DraggingItemInfo mDraggingItemInfo;
    private boolean mIsScrolling;
    private int mLayoutOrientation;
    private ItemDraggableRange mRange;
    private NinePatchDrawable mShadowDrawable;
    private final Rect mShadowPadding = new Rect();
    private boolean mStarted;
    private int mTouchPositionX;
    private int mTouchPositionY;
    private int mTranslationBottomLimit;
    private int mTranslationLeftLimit;
    private int mTranslationRightLimit;
    private int mTranslationTopLimit;
    private int mTranslationX;
    private int mTranslationY;

    public DraggingItemDecorator(RecyclerView recyclerView, ViewHolder viewHolder, ItemDraggableRange itemDraggableRange) {
        super(recyclerView, viewHolder);
        this.mRange = itemDraggableRange;
    }

    private static int clip(int i, int i2, int i3) {
        return Math.min(Math.max(i, i2), i3);
    }

    private Bitmap createDraggingItemImage(View view, NinePatchDrawable ninePatchDrawable) {
        int width = (view.getWidth() + this.mShadowPadding.left) + this.mShadowPadding.right;
        int height = (view.getHeight() + this.mShadowPadding.top) + this.mShadowPadding.bottom;
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (ninePatchDrawable != null) {
            ninePatchDrawable.setBounds(0, 0, width, height);
            ninePatchDrawable.draw(canvas);
        }
        int save = canvas.save(3);
        canvas.clipRect(this.mShadowPadding.left, this.mShadowPadding.top, width - this.mShadowPadding.right, height - this.mShadowPadding.bottom);
        canvas.translate((float) this.mShadowPadding.left, (float) this.mShadowPadding.top);
        view.draw(canvas);
        canvas.restoreToCount(save);
        return createBitmap;
    }

    private static View findRangeFirstItem(RecyclerView recyclerView, ItemDraggableRange itemDraggableRange, int i, int i2) {
        if (i == -1 || i2 == -1) {
            return null;
        }
        int childCount = recyclerView.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = recyclerView.getChildAt(i3);
            ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
            if (childViewHolder != null) {
                int layoutPosition = childViewHolder.getLayoutPosition();
                if (layoutPosition >= i && layoutPosition <= i2 && itemDraggableRange.checkInRange(layoutPosition)) {
                    return childAt;
                }
            }
        }
        return null;
    }

    private static View findRangeLastItem(RecyclerView recyclerView, ItemDraggableRange itemDraggableRange, int i, int i2) {
        if (i == -1 || i2 == -1) {
            return null;
        }
        for (int childCount = recyclerView.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = recyclerView.getChildAt(childCount);
            ViewHolder childViewHolder = recyclerView.getChildViewHolder(childAt);
            if (childViewHolder != null) {
                int layoutPosition = childViewHolder.getLayoutPosition();
                if (layoutPosition >= i && layoutPosition <= i2 && itemDraggableRange.checkInRange(layoutPosition)) {
                    return childAt;
                }
            }
        }
        return null;
    }

    private static int toSpanAlignedPosition(int i, int i2) {
        return i == -1 ? -1 : (i / i2) * i2;
    }

    private void updateDraggingItemPosition(float f, int i) {
        if (this.mDraggingItemViewHolder != null) {
            BaseDraggableItemDecorator.setItemTranslation(this.mRecyclerView, this.mDraggingItemViewHolder, f - ((float) this.mDraggingItemViewHolder.itemView.getLeft()), (float) (i - this.mDraggingItemViewHolder.itemView.getTop()));
        }
    }

    private void updateTranslationOffset() {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView.getChildCount() > 0) {
            this.mTranslationLeftLimit = 0;
            this.mTranslationRightLimit = recyclerView.getWidth() - this.mDraggingItemInfo.width;
            this.mTranslationTopLimit = 0;
            this.mTranslationBottomLimit = recyclerView.getHeight() - this.mDraggingItemInfo.height;
            switch (this.mLayoutOrientation) {
                case 0:
                    this.mTranslationTopLimit += recyclerView.getPaddingTop();
                    this.mTranslationBottomLimit -= recyclerView.getPaddingBottom();
                    break;
                case 1:
                    this.mTranslationLeftLimit += recyclerView.getPaddingLeft();
                    this.mTranslationRightLimit -= recyclerView.getPaddingRight();
                    break;
            }
            this.mTranslationRightLimit = Math.max(this.mTranslationLeftLimit, this.mTranslationRightLimit);
            this.mTranslationBottomLimit = Math.max(this.mTranslationTopLimit, this.mTranslationBottomLimit);
            if (!this.mIsScrolling) {
                int findFirstVisibleItemPosition = CustomRecyclerViewUtils.findFirstVisibleItemPosition(recyclerView, true);
                int findLastVisibleItemPosition = CustomRecyclerViewUtils.findLastVisibleItemPosition(recyclerView, true);
                View findRangeFirstItem = findRangeFirstItem(recyclerView, this.mRange, findFirstVisibleItemPosition, findLastVisibleItemPosition);
                View findRangeLastItem = findRangeLastItem(recyclerView, this.mRange, findFirstVisibleItemPosition, findLastVisibleItemPosition);
                switch (this.mLayoutOrientation) {
                    case 0:
                        if (findRangeFirstItem != null) {
                            this.mTranslationLeftLimit = Math.min(this.mTranslationLeftLimit, findRangeFirstItem.getLeft());
                        }
                        if (findRangeLastItem != null) {
                            this.mTranslationRightLimit = Math.min(this.mTranslationRightLimit, Math.max(0, findRangeLastItem.getRight() - this.mDraggingItemInfo.width));
                            break;
                        }
                        break;
                    case 1:
                        if (findRangeFirstItem != null) {
                            this.mTranslationTopLimit = Math.min(this.mTranslationBottomLimit, findRangeFirstItem.getTop());
                        }
                        if (findRangeLastItem != null) {
                            this.mTranslationBottomLimit = Math.min(this.mTranslationBottomLimit, Math.max(0, findRangeLastItem.getBottom() - this.mDraggingItemInfo.height));
                            break;
                        }
                        break;
                }
            }
        }
        findFirstVisibleItemPosition = recyclerView.getPaddingLeft();
        this.mTranslationLeftLimit = findFirstVisibleItemPosition;
        this.mTranslationRightLimit = findFirstVisibleItemPosition;
        int paddingTop = recyclerView.getPaddingTop();
        this.mTranslationTopLimit = paddingTop;
        this.mTranslationBottomLimit = paddingTop;
        this.mTranslationX = this.mTouchPositionX - this.mDraggingItemInfo.grabbedPositionX;
        this.mTranslationY = this.mTouchPositionY - this.mDraggingItemInfo.grabbedPositionY;
        this.mTranslationX = clip(this.mTranslationX, this.mTranslationLeftLimit, this.mTranslationRightLimit);
        this.mTranslationY = clip(this.mTranslationY, this.mTranslationTopLimit, this.mTranslationBottomLimit);
    }

    public void finish(boolean z) {
        if (this.mStarted) {
            this.mRecyclerView.removeItemDecoration(this);
        }
        ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        this.mRecyclerView.stopScroll();
        updateDraggingItemPosition((float) this.mTranslationX, this.mTranslationY);
        if (this.mDraggingItemViewHolder != null) {
            moveToDefaultPosition(this.mDraggingItemViewHolder.itemView, z);
        }
        if (this.mDraggingItemViewHolder != null) {
            this.mDraggingItemViewHolder.itemView.setVisibility(0);
        }
        this.mDraggingItemViewHolder = null;
        if (this.mDraggingItemImage != null) {
            this.mDraggingItemImage.recycle();
            this.mDraggingItemImage = null;
        }
        this.mRange = null;
        this.mTranslationX = 0;
        this.mTranslationY = 0;
        this.mTranslationLeftLimit = 0;
        this.mTranslationRightLimit = 0;
        this.mTranslationTopLimit = 0;
        this.mTranslationBottomLimit = 0;
        this.mTouchPositionX = 0;
        this.mTouchPositionY = 0;
        this.mStarted = false;
    }

    public int getDraggingItemMoveOffsetX() {
        return this.mTranslationX - this.mDraggingItemInfo.initialItemLeft;
    }

    public int getDraggingItemMoveOffsetY() {
        return this.mTranslationY - this.mDraggingItemInfo.initialItemTop;
    }

    public int getDraggingItemTranslationX() {
        return this.mTranslationX;
    }

    public int getDraggingItemTranslationY() {
        return this.mTranslationY;
    }

    public int getTranslatedItemPositionBottom() {
        return this.mTranslationY + this.mDraggingItemInfo.height;
    }

    public int getTranslatedItemPositionLeft() {
        return this.mTranslationX;
    }

    public int getTranslatedItemPositionRight() {
        return this.mTranslationX + this.mDraggingItemInfo.width;
    }

    public int getTranslatedItemPositionTop() {
        return this.mTranslationY;
    }

    public void invalidateDraggingItem() {
        if (this.mDraggingItemViewHolder != null) {
            ViewCompat.setTranslationX(this.mDraggingItemViewHolder.itemView, 0.0f);
            ViewCompat.setTranslationY(this.mDraggingItemViewHolder.itemView, 0.0f);
            this.mDraggingItemViewHolder.itemView.setVisibility(0);
        }
        this.mDraggingItemViewHolder = null;
    }

    public boolean isReachedToBottomLimit() {
        return this.mTranslationY == this.mTranslationBottomLimit;
    }

    public boolean isReachedToLeftLimit() {
        return this.mTranslationX == this.mTranslationLeftLimit;
    }

    public boolean isReachedToRightLimit() {
        return this.mTranslationX == this.mTranslationRightLimit;
    }

    public boolean isReachedToTopLimit() {
        return this.mTranslationY == this.mTranslationTopLimit;
    }

    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
        if (this.mDraggingItemImage != null) {
            canvas.drawBitmap(this.mDraggingItemImage, (float) (this.mTranslationX - this.mShadowPadding.left), (float) (this.mTranslationY - this.mShadowPadding.top), null);
        }
    }

    public boolean refresh(boolean z) {
        int i = this.mTranslationX;
        int i2 = this.mTranslationY;
        updateTranslationOffset();
        boolean z2 = (i == this.mTranslationX && i2 == this.mTranslationY) ? false : true;
        if (z2 || z) {
            updateDraggingItemPosition((float) this.mTranslationX, this.mTranslationY);
            ViewCompat.postInvalidateOnAnimation(this.mRecyclerView);
        }
        return z2;
    }

    public void setDraggingItemViewHolder(ViewHolder viewHolder) {
        if (this.mDraggingItemViewHolder != null) {
            throw new IllegalStateException("A new view holder is attempt to be assigned before invalidating the older one");
        }
        this.mDraggingItemViewHolder = viewHolder;
        viewHolder.itemView.setVisibility(4);
    }

    public void setIsScrolling(boolean z) {
        if (this.mIsScrolling != z) {
            this.mIsScrolling = z;
        }
    }

    public void setShadowDrawable(NinePatchDrawable ninePatchDrawable) {
        this.mShadowDrawable = ninePatchDrawable;
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.getPadding(this.mShadowPadding);
        }
    }

    public void start(MotionEvent motionEvent, DraggingItemInfo draggingItemInfo) {
        if (!this.mStarted) {
            View view = this.mDraggingItemViewHolder.itemView;
            this.mDraggingItemInfo = draggingItemInfo;
            this.mDraggingItemImage = createDraggingItemImage(view, this.mShadowDrawable);
            this.mTranslationLeftLimit = this.mRecyclerView.getPaddingLeft();
            this.mTranslationTopLimit = this.mRecyclerView.getPaddingTop();
            this.mLayoutOrientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
            view.setVisibility(4);
            update(motionEvent, true);
            this.mRecyclerView.addItemDecoration(this);
            this.mStarted = true;
        }
    }

    public boolean update(MotionEvent motionEvent, boolean z) {
        this.mTouchPositionX = (int) (motionEvent.getX() + 0.5f);
        this.mTouchPositionY = (int) (motionEvent.getY() + 0.5f);
        return refresh(z);
    }

    public void updateDraggingItemView(DraggingItemInfo draggingItemInfo, ViewHolder viewHolder) {
        if (this.mStarted) {
            if (this.mDraggingItemViewHolder != viewHolder) {
                invalidateDraggingItem();
                this.mDraggingItemViewHolder = viewHolder;
            }
            this.mDraggingItemImage = createDraggingItemImage(viewHolder.itemView, this.mShadowDrawable);
            this.mDraggingItemInfo = draggingItemInfo;
            refresh(true);
        }
    }
}
