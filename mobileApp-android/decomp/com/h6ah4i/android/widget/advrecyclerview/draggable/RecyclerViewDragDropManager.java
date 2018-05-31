package com.h6ah4i.android.widget.advrecyclerview.draggable;

import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class RecyclerViewDragDropManager implements DraggableItemConstants {
    public static final Interpolator DEFAULT_ITEM_SETTLE_BACK_INTO_PLACE_ANIMATION_INTERPOLATOR = new DecelerateInterpolator();
    public static final Interpolator DEFAULT_SWAP_TARGET_TRANSITION_INTERPOLATOR = new BasicSwapTargetTranslationInterpolator();
    public static final int ITEM_MOVE_MODE_DEFAULT = 0;
    public static final int ITEM_MOVE_MODE_SWAP = 1;
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final float SCROLL_AMOUNT_COEFF = 25.0f;
    private static final int SCROLL_DIR_DOWN = 2;
    private static final int SCROLL_DIR_LEFT = 4;
    private static final int SCROLL_DIR_NONE = 0;
    private static final int SCROLL_DIR_RIGHT = 8;
    private static final int SCROLL_DIR_UP = 1;
    private static final float SCROLL_THRESHOLD = 0.3f;
    private static final float SCROLL_TOUCH_SLOP_MULTIPLY = 1.5f;
    private static final String TAG = "ARVDragDropManager";
    private int mActualScrollByXAmount;
    private int mActualScrollByYAmount;
    private DraggableItemWrapperAdapter mAdapter;
    private boolean mCanDragH;
    private boolean mCanDragV;
    private boolean mCheckCanDrop;
    private final Runnable mCheckItemSwappingRunnable = new C05733();
    private int mCurrentItemMoveMode = 0;
    private float mDisplayDensity;
    private float mDragEdgeScrollSpeed = 1.0f;
    private int mDragMaxTouchX;
    private int mDragMaxTouchY;
    private int mDragMinTouchX;
    private int mDragMinTouchY;
    private int mDragScrollDistanceX;
    private int mDragScrollDistanceY;
    private int mDragStartTouchX;
    private int mDragStartTouchY;
    private ItemDraggableRange mDraggableRange;
    private DraggingItemDecorator mDraggingItemDecorator;
    private DraggingItemInfo mDraggingItemInfo;
    ViewHolder mDraggingItemViewHolder;
    private BaseEdgeEffectDecorator mEdgeEffectDecorator;
    private FindSwapTargetContext mFindSwapTargetContext = new FindSwapTargetContext();
    private InternalHandler mHandler;
    private boolean mInScrollByMethod;
    private long mInitialTouchItemId = -1;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private boolean mInitiateOnLongPress;
    private boolean mInitiateOnMove = true;
    private boolean mInitiateOnTouch;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C05711();
    private OnScrollListener mInternalUseOnScrollListener = new C05722();
    private OnItemDragEventListener mItemDragEventListener;
    private int mItemMoveMode = 0;
    private int mItemSettleBackIntoPlaceAnimationDuration = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private Interpolator mItemSettleBackIntoPlaceAnimationInterpolator = DEFAULT_ITEM_SETTLE_BACK_INTO_PLACE_ANIMATION_INTERPOLATOR;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mLongPressTimeout = ViewConfiguration.getLongPressTimeout();
    private int mOrigOverScrollMode;
    private RecyclerView mRecyclerView;
    private int mScrollDirMask = 0;
    private ScrollOnDraggingProcessRunnable mScrollOnDraggingProcess = new ScrollOnDraggingProcessRunnable(this);
    private int mScrollTouchSlop;
    private NinePatchDrawable mShadowDrawable;
    private SwapTargetItemOperator mSwapTargetItemOperator;
    private Interpolator mSwapTargetTranslationInterpolator = DEFAULT_SWAP_TARGET_TRANSITION_INTERPOLATOR;
    private SwapTarget mTempSwapTarget = new SwapTarget();
    private final Rect mTmpRect1 = new Rect();
    private int mTouchSlop;

    class C05711 implements OnItemTouchListener {
        C05711() {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return RecyclerViewDragDropManager.this.onInterceptTouchEvent(recyclerView, motionEvent);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            RecyclerViewDragDropManager.this.onRequestDisallowInterceptTouchEvent(z);
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            RecyclerViewDragDropManager.this.onTouchEvent(recyclerView, motionEvent);
        }
    }

    class C05722 extends OnScrollListener {
        C05722() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            RecyclerViewDragDropManager.this.onScrollStateChanged(recyclerView, i);
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            RecyclerViewDragDropManager.this.onScrolled(recyclerView, i, i2);
        }
    }

    class C05733 implements Runnable {
        C05733() {
        }

        public void run() {
            if (RecyclerViewDragDropManager.this.mDraggingItemViewHolder != null) {
                RecyclerViewDragDropManager.this.checkItemSwapping(RecyclerViewDragDropManager.this.getRecyclerView());
            }
        }
    }

    static class FindSwapTargetContext {
        public boolean checkCanSwap;
        public ViewHolder draggingItem;
        public DraggingItemInfo draggingItemInfo;
        public int lastTouchX;
        public int lastTouchY;
        public int layoutType;
        public int overlayItemLeft;
        public int overlayItemLeftNotClipped;
        public int overlayItemTop;
        public int overlayItemTopNotClipped;
        public ItemDraggableRange range;
        public RecyclerView rv;
        public boolean vertical;

        FindSwapTargetContext() {
        }

        public void clear() {
            this.rv = null;
            this.draggingItemInfo = null;
            this.draggingItem = null;
        }

        public void setup(RecyclerView recyclerView, ViewHolder viewHolder, DraggingItemInfo draggingItemInfo, int i, int i2, ItemDraggableRange itemDraggableRange, boolean z) {
            boolean z2 = true;
            this.rv = recyclerView;
            this.draggingItemInfo = draggingItemInfo;
            this.draggingItem = viewHolder;
            this.lastTouchX = i;
            this.lastTouchY = i2;
            this.range = itemDraggableRange;
            this.checkCanSwap = z;
            this.layoutType = CustomRecyclerViewUtils.getLayoutType(recyclerView);
            if (CustomRecyclerViewUtils.extractOrientation(this.layoutType) != 1) {
                z2 = false;
            }
            this.vertical = z2;
            int i3 = i - draggingItemInfo.grabbedPositionX;
            this.overlayItemLeftNotClipped = i3;
            this.overlayItemLeft = i3;
            i3 = i2 - draggingItemInfo.grabbedPositionY;
            this.overlayItemTopNotClipped = i3;
            this.overlayItemTop = i3;
            if (this.vertical) {
                this.overlayItemLeft = Math.max(this.overlayItemLeft, recyclerView.getPaddingLeft());
                this.overlayItemLeft = Math.min(this.overlayItemLeft, Math.max(0, (recyclerView.getWidth() - recyclerView.getPaddingRight()) - this.draggingItemInfo.width));
                return;
            }
            this.overlayItemTop = Math.max(this.overlayItemTop, recyclerView.getPaddingTop());
            this.overlayItemTop = Math.min(this.overlayItemTop, Math.max(0, (recyclerView.getHeight() - recyclerView.getPaddingBottom()) - this.draggingItemInfo.height));
        }
    }

    private static class InternalHandler extends Handler {
        private static final int MSG_CHECK_ITEM_VIEW_SIZE_UPDATE = 3;
        private static final int MSG_DEFERRED_CANCEL_DRAG = 2;
        private static final int MSG_LONGPRESS = 1;
        private MotionEvent mDownMotionEvent;
        private RecyclerViewDragDropManager mHolder;

        public InternalHandler(RecyclerViewDragDropManager recyclerViewDragDropManager) {
            this.mHolder = recyclerViewDragDropManager;
        }

        public void cancelLongPressDetection() {
            removeMessages(1);
            if (this.mDownMotionEvent != null) {
                this.mDownMotionEvent.recycle();
                this.mDownMotionEvent = null;
            }
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.mHolder.handleOnLongPress(this.mDownMotionEvent);
                    return;
                case 2:
                    this.mHolder.cancelDrag(true);
                    return;
                case 3:
                    this.mHolder.handleOnCheckItemViewSizeUpdate();
                    return;
                default:
                    return;
            }
        }

        public boolean isCancelDragRequested() {
            return hasMessages(2);
        }

        public void release() {
            removeCallbacks(null);
            this.mHolder = null;
        }

        public void removeDeferredCancelDragRequest() {
            removeMessages(2);
        }

        public void removeDraggingItemViewSizeUpdateCheckRequest() {
            removeMessages(3);
        }

        public void requestDeferredCancelDrag() {
            if (!isCancelDragRequested()) {
                sendEmptyMessage(2);
            }
        }

        public void scheduleDraggingItemViewSizeUpdateCheck() {
            sendEmptyMessage(3);
        }

        public void startLongPressDetection(MotionEvent motionEvent, int i) {
            cancelLongPressDetection();
            this.mDownMotionEvent = MotionEvent.obtain(motionEvent);
            sendEmptyMessageAtTime(1, motionEvent.getDownTime() + ((long) i));
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemMoveMode {
    }

    public interface OnItemDragEventListener {
        void onItemDragFinished(int i, int i2, boolean z);

        void onItemDragMoveDistanceUpdated(int i, int i2);

        void onItemDragPositionChanged(int i, int i2);

        void onItemDragStarted(int i);
    }

    private static class ScrollOnDraggingProcessRunnable implements Runnable {
        private final WeakReference<RecyclerViewDragDropManager> mHolderRef;
        private boolean mStarted;

        public ScrollOnDraggingProcessRunnable(RecyclerViewDragDropManager recyclerViewDragDropManager) {
            this.mHolderRef = new WeakReference(recyclerViewDragDropManager);
        }

        public void release() {
            this.mHolderRef.clear();
            this.mStarted = false;
        }

        public void run() {
            RecyclerViewDragDropManager recyclerViewDragDropManager = (RecyclerViewDragDropManager) this.mHolderRef.get();
            if (recyclerViewDragDropManager != null && this.mStarted) {
                recyclerViewDragDropManager.handleScrollOnDragging();
                View recyclerView = recyclerViewDragDropManager.getRecyclerView();
                if (recyclerView == null || !this.mStarted) {
                    this.mStarted = false;
                } else {
                    ViewCompat.postOnAnimation(recyclerView, this);
                }
            }
        }

        public void start() {
            if (!this.mStarted) {
                RecyclerViewDragDropManager recyclerViewDragDropManager = (RecyclerViewDragDropManager) this.mHolderRef.get();
                if (recyclerViewDragDropManager != null) {
                    View recyclerView = recyclerViewDragDropManager.getRecyclerView();
                    if (recyclerView != null) {
                        ViewCompat.postOnAnimation(recyclerView, this);
                        this.mStarted = true;
                    }
                }
            }
        }

        public void stop() {
            if (this.mStarted) {
                this.mStarted = false;
            }
        }
    }

    static class SwapTarget {
        public ViewHolder holder;
        public int position;
        public boolean self;

        SwapTarget() {
        }

        public void clear() {
            this.holder = null;
            this.position = -1;
            this.self = false;
        }
    }

    private boolean canStartDrag(ViewHolder viewHolder, int i, int i2) {
        int adapterPosition = viewHolder.getAdapterPosition();
        if (adapterPosition == -1) {
            return false;
        }
        View view = viewHolder.itemView;
        return this.mAdapter.canStartDrag(viewHolder, adapterPosition, i - (((int) (ViewCompat.getTranslationX(view) + 0.5f)) + view.getLeft()), i2 - (view.getTop() + ((int) (ViewCompat.getTranslationY(view) + 0.5f)))) && viewHolder.getAdapterPosition() == adapterPosition;
    }

    private boolean checkConditionAndStartDragging(RecyclerView recyclerView, MotionEvent motionEvent, boolean z) {
        if (this.mDraggingItemInfo != null) {
            return false;
        }
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        this.mLastTouchX = x;
        this.mLastTouchY = y;
        if (this.mInitialTouchItemId == -1) {
            return false;
        }
        if (z && ((!this.mCanDragH || Math.abs(x - this.mInitialTouchX) <= this.mTouchSlop) && (!this.mCanDragV || Math.abs(y - this.mInitialTouchY) <= this.mTouchSlop))) {
            return false;
        }
        ViewHolder findChildViewHolderUnderWithoutTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(recyclerView, (float) this.mInitialTouchX, (float) this.mInitialTouchY);
        if (findChildViewHolderUnderWithoutTranslation == null) {
            return false;
        }
        if (!canStartDrag(findChildViewHolderUnderWithoutTranslation, x, y)) {
            return false;
        }
        ItemDraggableRange itemDraggableRange = this.mAdapter.getItemDraggableRange(findChildViewHolderUnderWithoutTranslation, findChildViewHolderUnderWithoutTranslation.getAdapterPosition());
        if (itemDraggableRange == null) {
            itemDraggableRange = new ItemDraggableRange(0, Math.max(0, this.mAdapter.getItemCount() - 1));
        }
        verifyItemDraggableRange(itemDraggableRange, findChildViewHolderUnderWithoutTranslation);
        startDragging(recyclerView, motionEvent, findChildViewHolderUnderWithoutTranslation, itemDraggableRange);
        return true;
    }

    private boolean checkTouchedItemState(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (!(viewHolder instanceof DraggableItemViewHolder)) {
            return false;
        }
        int adapterPosition = viewHolder.getAdapterPosition();
        Adapter adapter = recyclerView.getAdapter();
        return adapterPosition >= 0 && adapterPosition < adapter.getItemCount() && viewHolder.getItemId() == adapter.getItemId(adapterPosition);
    }

    static SwapTarget findSwapTargetItem(SwapTarget swapTarget, FindSwapTargetContext findSwapTargetContext, boolean z) {
        ViewHolder findSwapTargetItemForLinearLayoutManager;
        ViewHolder viewHolder = null;
        swapTarget.clear();
        if (findSwapTargetContext.draggingItem == null || (findSwapTargetContext.draggingItem.getAdapterPosition() != -1 && findSwapTargetContext.draggingItem.getItemId() == findSwapTargetContext.draggingItemInfo.id)) {
            switch (findSwapTargetContext.layoutType) {
                case 0:
                case 1:
                    findSwapTargetItemForLinearLayoutManager = findSwapTargetItemForLinearLayoutManager(findSwapTargetContext, z);
                    break;
                case 2:
                case 3:
                    findSwapTargetItemForLinearLayoutManager = findSwapTargetItemForGridLayoutManager(findSwapTargetContext, z);
                    break;
                case 4:
                case 5:
                    findSwapTargetItemForLinearLayoutManager = findSwapTargetItemForStaggeredGridLayoutManager(findSwapTargetContext, z);
                    break;
            }
        }
        findSwapTargetItemForLinearLayoutManager = null;
        if (findSwapTargetItemForLinearLayoutManager == findSwapTargetContext.draggingItem) {
            swapTarget.self = true;
            findSwapTargetItemForLinearLayoutManager = null;
        }
        if (findSwapTargetItemForLinearLayoutManager == null || findSwapTargetContext.range == null || findSwapTargetContext.range.checkInRange(findSwapTargetItemForLinearLayoutManager.getAdapterPosition())) {
            viewHolder = findSwapTargetItemForLinearLayoutManager;
        }
        swapTarget.holder = viewHolder;
        swapTarget.position = CustomRecyclerViewUtils.safeGetAdapterPosition(viewHolder);
        return swapTarget;
    }

    private static ViewHolder findSwapTargetItemForGridLayoutManager(FindSwapTargetContext findSwapTargetContext, boolean z) {
        if (z) {
            return null;
        }
        ViewHolder findSwapTargetItemForGridLayoutManagerInternal1 = findSwapTargetItemForGridLayoutManagerInternal1(findSwapTargetContext);
        return findSwapTargetItemForGridLayoutManagerInternal1 == null ? findSwapTargetItemForGridLayoutManagerInternal2(findSwapTargetContext) : findSwapTargetItemForGridLayoutManagerInternal1;
    }

    private static ViewHolder findSwapTargetItemForGridLayoutManagerInternal1(FindSwapTargetContext findSwapTargetContext) {
        int i = (int) (findSwapTargetContext.rv.getContext().getResources().getDisplayMetrics().density * 4.0f);
        int i2 = ((int) (((float) findSwapTargetContext.draggingItemInfo.width) * 0.5f)) + findSwapTargetContext.overlayItemLeftNotClipped;
        int i3 = ((int) (((float) findSwapTargetContext.draggingItemInfo.height) * 0.5f)) + findSwapTargetContext.overlayItemTopNotClipped;
        if (findSwapTargetContext.vertical) {
            i2 = Math.min(Math.max(i2, (findSwapTargetContext.rv.getPaddingLeft() + (i * 2)) + 1), ((findSwapTargetContext.rv.getWidth() - findSwapTargetContext.rv.getPaddingRight()) - (i * 2)) - 1);
        } else {
            i3 = Math.min(Math.max(i3, (findSwapTargetContext.rv.getPaddingTop() + (i * 2)) + 1), ((findSwapTargetContext.rv.getHeight() - findSwapTargetContext.rv.getPaddingBottom()) - (i * 2)) - 1);
        }
        ViewHolder findChildViewHolderUnderWithoutTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) (i2 - i), (float) (i3 - i));
        if (findChildViewHolderUnderWithoutTranslation == null || findChildViewHolderUnderWithoutTranslation == findSwapTargetContext.draggingItem) {
            return findChildViewHolderUnderWithoutTranslation;
        }
        ViewHolder findChildViewHolderUnderWithoutTranslation2 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) (i2 + i), (float) (i3 - i));
        if (findChildViewHolderUnderWithoutTranslation2 == null || findChildViewHolderUnderWithoutTranslation2 == findSwapTargetContext.draggingItem) {
            return findChildViewHolderUnderWithoutTranslation2;
        }
        ViewHolder findChildViewHolderUnderWithoutTranslation3 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) (i2 - i), (float) (i3 + i));
        if (findChildViewHolderUnderWithoutTranslation3 == null || findChildViewHolderUnderWithoutTranslation3 == findSwapTargetContext.draggingItem) {
            return findChildViewHolderUnderWithoutTranslation3;
        }
        ViewHolder findChildViewHolderUnderWithoutTranslation4 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) (i2 + i), (float) (i3 + i));
        return (findChildViewHolderUnderWithoutTranslation4 == null || findChildViewHolderUnderWithoutTranslation4 == findSwapTargetContext.draggingItem) ? findChildViewHolderUnderWithoutTranslation4 : (findChildViewHolderUnderWithoutTranslation == findChildViewHolderUnderWithoutTranslation2 && findChildViewHolderUnderWithoutTranslation == findChildViewHolderUnderWithoutTranslation3 && findChildViewHolderUnderWithoutTranslation == findChildViewHolderUnderWithoutTranslation4) ? findChildViewHolderUnderWithoutTranslation : null;
    }

    private static ViewHolder findSwapTargetItemForGridLayoutManagerInternal2(FindSwapTargetContext findSwapTargetContext) {
        int i = 0;
        int spanCount = CustomRecyclerViewUtils.getSpanCount(findSwapTargetContext.rv);
        int height = findSwapTargetContext.rv.getHeight();
        int width = findSwapTargetContext.rv.getWidth();
        int paddingLeft = findSwapTargetContext.vertical ? findSwapTargetContext.rv.getPaddingLeft() : 0;
        int paddingTop = !findSwapTargetContext.vertical ? findSwapTargetContext.rv.getPaddingTop() : 0;
        int paddingRight = findSwapTargetContext.vertical ? findSwapTargetContext.rv.getPaddingRight() : 0;
        if (!findSwapTargetContext.vertical) {
            i = findSwapTargetContext.rv.getPaddingBottom();
        }
        int i2 = ((width - paddingLeft) - paddingRight) / spanCount;
        int i3 = ((height - paddingTop) - i) / spanCount;
        paddingRight = (findSwapTargetContext.draggingItemInfo.width / 2) + findSwapTargetContext.overlayItemLeft;
        height = (findSwapTargetContext.draggingItemInfo.height / 2) + findSwapTargetContext.overlayItemTop;
        for (width = spanCount - 1; width >= 0; width--) {
            ViewHolder findChildViewHolderUnderWithoutTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) (findSwapTargetContext.vertical ? ((i2 * width) + paddingLeft) + (i2 / 2) : paddingRight), (float) (!findSwapTargetContext.vertical ? ((i3 * width) + paddingTop) + (i3 / 2) : height));
            if (findChildViewHolderUnderWithoutTranslation != null) {
                paddingLeft = findSwapTargetContext.rv.getLayoutManager().getItemCount();
                paddingTop = findChildViewHolderUnderWithoutTranslation.getAdapterPosition();
                if (paddingTop != -1 && paddingTop == paddingLeft - 1) {
                    return findChildViewHolderUnderWithoutTranslation;
                }
                return null;
            }
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.support.v7.widget.RecyclerView.ViewHolder findSwapTargetItemForLinearLayoutManager(com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.FindSwapTargetContext r8, boolean r9) {
        /*
        r1 = 0;
        r6 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r4 = 1045220557; // 0x3e4ccccd float:0.2 double:5.164075695E-315;
        r0 = r8.draggingItem;
        if (r0 != 0) goto L_0x000b;
    L_0x000a:
        return r1;
    L_0x000b:
        r0 = r8.checkCanSwap;
        if (r0 != 0) goto L_0x005c;
    L_0x000f:
        if (r9 != 0) goto L_0x005c;
    L_0x0011:
        r0 = r8.draggingItem;
        r3 = r0.getAdapterPosition();
        r0 = r8.vertical;
        if (r0 == 0) goto L_0x0037;
    L_0x001b:
        r0 = r8.draggingItem;
        r0 = r0.itemView;
        r0 = r0.getTop();
    L_0x0023:
        r2 = r8.vertical;
        if (r2 == 0) goto L_0x0040;
    L_0x0027:
        r2 = r8.overlayItemTop;
    L_0x0029:
        if (r2 >= r0) goto L_0x0043;
    L_0x002b:
        if (r3 <= 0) goto L_0x00a9;
    L_0x002d:
        r0 = r8.rv;
        r1 = r3 + -1;
        r0 = r0.findViewHolderForAdapterPosition(r1);
    L_0x0035:
        r1 = r0;
        goto L_0x000a;
    L_0x0037:
        r0 = r8.draggingItem;
        r0 = r0.itemView;
        r0 = r0.getLeft();
        goto L_0x0023;
    L_0x0040:
        r2 = r8.overlayItemLeft;
        goto L_0x0029;
    L_0x0043:
        if (r2 <= r0) goto L_0x00a9;
    L_0x0045:
        r0 = r8.rv;
        r0 = r0.getAdapter();
        r0 = r0.getItemCount();
        r0 = r0 + -1;
        if (r3 >= r0) goto L_0x00a9;
    L_0x0053:
        r0 = r8.rv;
        r1 = r3 + 1;
        r0 = r0.findViewHolderForAdapterPosition(r1);
        goto L_0x0035;
    L_0x005c:
        r0 = r8.draggingItem;
        r0 = r0.itemView;
        r0 = r0.getResources();
        r0 = r0.getDisplayMetrics();
        r0 = r0.density;
        r2 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r0 = r0 * r2;
        r2 = r8.draggingItemInfo;
        r2 = r2.width;
        r2 = (float) r2;
        r2 = r2 * r4;
        r2 = java.lang.Math.min(r2, r0);
        r3 = r8.draggingItemInfo;
        r3 = r3.height;
        r3 = (float) r3;
        r3 = r3 * r4;
        r3 = java.lang.Math.min(r3, r0);
        r0 = r8.overlayItemLeft;
        r0 = (float) r0;
        r4 = r8.draggingItemInfo;
        r4 = r4.width;
        r4 = (float) r4;
        r4 = r4 * r6;
        r4 = r4 + r0;
        r0 = r8.overlayItemTop;
        r0 = (float) r0;
        r5 = r8.draggingItemInfo;
        r5 = r5.height;
        r5 = (float) r5;
        r5 = r5 * r6;
        r5 = r5 + r0;
        r0 = r8.rv;
        r6 = r4 - r2;
        r7 = r5 - r3;
        r0 = com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(r0, r6, r7);
        r6 = r8.rv;
        r2 = r2 + r4;
        r3 = r3 + r5;
        r2 = com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(r6, r2, r3);
        if (r0 == r2) goto L_0x0035;
    L_0x00a9:
        r0 = r1;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.findSwapTargetItemForLinearLayoutManager(com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager$FindSwapTargetContext, boolean):android.support.v7.widget.RecyclerView$ViewHolder");
    }

    private static ViewHolder findSwapTargetItemForStaggeredGridLayoutManager(FindSwapTargetContext findSwapTargetContext, boolean z) {
        if (z) {
            return null;
        }
        if (findSwapTargetContext.draggingItem == null) {
            return null;
        }
        int i;
        int i2;
        int min;
        ViewHolder findChildViewHolderUnderWithoutTranslation;
        ViewHolder findChildViewHolderUnderWithoutTranslation2;
        ViewHolder viewHolder;
        ViewHolder viewHolder2;
        int i3;
        ViewHolder viewHolder3;
        ViewHolder viewHolder4;
        ViewHolder viewHolder5 = null;
        int spanCount = CustomRecyclerViewUtils.getSpanCount(findSwapTargetContext.rv);
        int spanIndex = CustomRecyclerViewUtils.getSpanIndex(findSwapTargetContext.draggingItem);
        int i4;
        int i5;
        int paddingLeft;
        float width;
        int min2;
        ViewHolder findChildViewHolderUnderWithoutTranslation3;
        ViewHolder findChildViewHolderUnderWithoutTranslation4;
        ViewHolder findChildViewHolderUnderWithoutTranslation5;
        ViewHolder findChildViewHolderUnderWithoutTranslation6;
        int i6;
        if (findSwapTargetContext.vertical) {
            i = findSwapTargetContext.overlayItemLeft + 1;
            i4 = (findSwapTargetContext.overlayItemLeft + findSwapTargetContext.draggingItemInfo.width) - 2;
            i2 = findSwapTargetContext.overlayItemTop + 1;
            i5 = (findSwapTargetContext.overlayItemTop + (findSwapTargetContext.draggingItemInfo.height / 2)) - 1;
            int i7 = (findSwapTargetContext.overlayItemTop + findSwapTargetContext.draggingItemInfo.height) - 2;
            paddingLeft = findSwapTargetContext.rv.getPaddingLeft();
            width = ((float) ((findSwapTargetContext.rv.getWidth() - paddingLeft) - findSwapTargetContext.rv.getPaddingRight())) * (1.0f / ((float) spanCount));
            min = Math.min(Math.max((int) (((float) ((i - findSwapTargetContext.draggingItemInfo.margins.left) - paddingLeft)) / width), 0), spanCount - 1);
            min2 = Math.min(Math.max((int) (((float) ((i4 - findSwapTargetContext.draggingItemInfo.margins.right) - paddingLeft)) / width), 0), spanCount - 1);
            paddingLeft = findSwapTargetContext.overlayItemTop;
            spanCount = findSwapTargetContext.draggingItem.itemView.getTop();
            findChildViewHolderUnderWithoutTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i, (float) i2);
            findChildViewHolderUnderWithoutTranslation2 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i, (float) i5);
            findChildViewHolderUnderWithoutTranslation3 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i, (float) i7);
            findChildViewHolderUnderWithoutTranslation4 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i4, (float) i2);
            findChildViewHolderUnderWithoutTranslation5 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i4, (float) i5);
            findChildViewHolderUnderWithoutTranslation6 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i4, (float) i7);
            viewHolder = findChildViewHolderUnderWithoutTranslation3;
            viewHolder2 = findChildViewHolderUnderWithoutTranslation;
            i3 = min;
            findChildViewHolderUnderWithoutTranslation = findChildViewHolderUnderWithoutTranslation4;
            i6 = min2;
            viewHolder3 = findChildViewHolderUnderWithoutTranslation5;
            i2 = paddingLeft;
            viewHolder4 = findChildViewHolderUnderWithoutTranslation2;
            findChildViewHolderUnderWithoutTranslation2 = findChildViewHolderUnderWithoutTranslation6;
            i = spanCount;
        } else {
            i = findSwapTargetContext.overlayItemLeft + 1;
            i2 = (findSwapTargetContext.overlayItemLeft + (findSwapTargetContext.draggingItemInfo.width / 2)) - 1;
            i4 = (findSwapTargetContext.overlayItemLeft + findSwapTargetContext.draggingItemInfo.width) - 2;
            i6 = findSwapTargetContext.overlayItemTop + 1;
            i5 = (findSwapTargetContext.overlayItemTop + findSwapTargetContext.draggingItemInfo.height) - 2;
            paddingLeft = findSwapTargetContext.rv.getPaddingTop();
            width = ((float) ((findSwapTargetContext.rv.getHeight() - paddingLeft) - findSwapTargetContext.rv.getPaddingBottom())) * (1.0f / ((float) spanCount));
            min = Math.min(Math.max((int) (((float) ((i - findSwapTargetContext.draggingItemInfo.margins.top) - paddingLeft)) / width), 0), spanCount - 1);
            min2 = Math.min(Math.max((int) (((float) ((i4 - findSwapTargetContext.draggingItemInfo.margins.left) - paddingLeft)) / width), 0), spanCount - 1);
            paddingLeft = findSwapTargetContext.overlayItemLeft;
            spanCount = findSwapTargetContext.draggingItem.itemView.getLeft();
            findChildViewHolderUnderWithoutTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i, (float) i6);
            findChildViewHolderUnderWithoutTranslation2 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i2, (float) i6);
            findChildViewHolderUnderWithoutTranslation3 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i4, (float) i6);
            findChildViewHolderUnderWithoutTranslation4 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i, (float) i5);
            findChildViewHolderUnderWithoutTranslation5 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i2, (float) i5);
            findChildViewHolderUnderWithoutTranslation6 = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(findSwapTargetContext.rv, (float) i4, (float) i5);
            viewHolder = findChildViewHolderUnderWithoutTranslation3;
            viewHolder2 = findChildViewHolderUnderWithoutTranslation;
            i3 = min;
            findChildViewHolderUnderWithoutTranslation = findChildViewHolderUnderWithoutTranslation4;
            i6 = min2;
            viewHolder3 = findChildViewHolderUnderWithoutTranslation5;
            i2 = paddingLeft;
            viewHolder4 = findChildViewHolderUnderWithoutTranslation2;
            findChildViewHolderUnderWithoutTranslation2 = findChildViewHolderUnderWithoutTranslation6;
            i = spanCount;
        }
        min = 0;
        spanCount = 0;
        if (viewHolder4 != null) {
            min = 1;
            if (viewHolder4 == viewHolder2) {
                min = 3;
            }
            if (viewHolder4 == viewHolder) {
                min |= 4;
            }
        }
        if (viewHolder3 != null) {
            spanCount = 1;
            if (viewHolder3 == findChildViewHolderUnderWithoutTranslation) {
                spanCount = 3;
            }
            if (viewHolder3 == findChildViewHolderUnderWithoutTranslation2) {
                spanCount |= 4;
            }
        }
        int bitCount = Integer.bitCount(min);
        int bitCount2 = Integer.bitCount(spanCount);
        if (i3 != spanIndex && i3 == r7) {
            if (bitCount == 3) {
                viewHolder5 = viewHolder4;
            } else if (bitCount2 == 3) {
                viewHolder5 = viewHolder3;
            }
        }
        if (viewHolder5 == null) {
            if (bitCount != 2 || bitCount2 == 2) {
                if (bitCount2 == 2 && bitCount != 2) {
                    viewHolder4 = viewHolder3;
                }
            }
            if (viewHolder4 != null && spanIndex == CustomRecyclerViewUtils.getSpanIndex(viewHolder4)) {
                if (i2 > i) {
                    if (((spanCount | min) & 2) != 0) {
                        return null;
                    }
                } else if (((spanCount | min) & 4) != 0) {
                    return null;
                }
            }
            return viewHolder4;
        }
        viewHolder4 = viewHolder5;
        if (i2 > i) {
            if (((spanCount | min) & 4) != 0) {
                return null;
            }
        } else if (((spanCount | min) & 2) != 0) {
            return null;
        }
        return viewHolder4;
    }

    private void finishDragging(boolean z) {
        int i = -1;
        if (isDragging()) {
            int draggingItemInitialPosition;
            if (this.mHandler != null) {
                this.mHandler.removeDeferredCancelDragRequest();
                this.mHandler.removeDraggingItemViewSizeUpdateCheckRequest();
            }
            if (!(this.mRecyclerView == null || this.mDraggingItemViewHolder == null)) {
                ViewCompat.setOverScrollMode(this.mRecyclerView, this.mOrigOverScrollMode);
            }
            if (this.mDraggingItemDecorator != null) {
                this.mDraggingItemDecorator.setReturnToDefaultPositionAnimationDuration(this.mItemSettleBackIntoPlaceAnimationDuration);
                this.mDraggingItemDecorator.setReturnToDefaultPositionAnimationInterpolator(this.mItemSettleBackIntoPlaceAnimationInterpolator);
                this.mDraggingItemDecorator.finish(true);
            }
            if (this.mSwapTargetItemOperator != null) {
                this.mSwapTargetItemOperator.setReturnToDefaultPositionAnimationDuration(this.mItemSettleBackIntoPlaceAnimationDuration);
                this.mDraggingItemDecorator.setReturnToDefaultPositionAnimationInterpolator(this.mItemSettleBackIntoPlaceAnimationInterpolator);
                this.mSwapTargetItemOperator.finish(true);
            }
            if (this.mEdgeEffectDecorator != null) {
                this.mEdgeEffectDecorator.releaseBothGlows();
            }
            stopScrollOnDraggingProcess();
            if (!(this.mRecyclerView == null || this.mRecyclerView.getParent() == null)) {
                this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(false);
            }
            if (this.mRecyclerView != null) {
                this.mRecyclerView.invalidate();
            }
            this.mDraggableRange = null;
            this.mDraggingItemDecorator = null;
            this.mSwapTargetItemOperator = null;
            this.mDraggingItemViewHolder = null;
            this.mDraggingItemInfo = null;
            this.mLastTouchX = 0;
            this.mLastTouchY = 0;
            this.mDragStartTouchX = 0;
            this.mDragStartTouchY = 0;
            this.mDragMinTouchX = 0;
            this.mDragMinTouchY = 0;
            this.mDragMaxTouchX = 0;
            this.mDragMaxTouchY = 0;
            this.mDragScrollDistanceX = 0;
            this.mDragScrollDistanceY = 0;
            this.mCanDragH = false;
            this.mCanDragV = false;
            if (this.mAdapter != null) {
                draggingItemInitialPosition = this.mAdapter.getDraggingItemInitialPosition();
                i = this.mAdapter.getDraggingItemCurrentPosition();
                this.mAdapter.onDragItemFinished(z);
            } else {
                draggingItemInitialPosition = -1;
            }
            if (this.mItemDragEventListener != null) {
                this.mItemDragEventListener.onItemDragFinished(draggingItemInitialPosition, i, z);
            }
        }
    }

    private static DraggableItemWrapperAdapter getDraggableItemWrapperAdapter(RecyclerView recyclerView) {
        return (DraggableItemWrapperAdapter) WrapperAdapterUtils.findWrappedAdapter(recyclerView.getAdapter(), DraggableItemWrapperAdapter.class);
    }

    private static Integer getItemViewOrigin(View view, boolean z) {
        if (view == null) {
            return null;
        }
        return Integer.valueOf(z ? view.getTop() : view.getLeft());
    }

    private boolean handleActionDown(RecyclerView recyclerView, MotionEvent motionEvent) {
        boolean z = true;
        ViewHolder findChildViewHolderUnderWithoutTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithoutTranslation(recyclerView, motionEvent.getX(), motionEvent.getY());
        if (!checkTouchedItemState(recyclerView, findChildViewHolderUnderWithoutTranslation)) {
            return false;
        }
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        if (!canStartDrag(findChildViewHolderUnderWithoutTranslation, x, y)) {
            return false;
        }
        int orientation = CustomRecyclerViewUtils.getOrientation(this.mRecyclerView);
        int spanCount = CustomRecyclerViewUtils.getSpanCount(this.mRecyclerView);
        this.mLastTouchX = x;
        this.mInitialTouchX = x;
        this.mLastTouchY = y;
        this.mInitialTouchY = y;
        this.mInitialTouchItemId = findChildViewHolderUnderWithoutTranslation.getItemId();
        boolean z2 = orientation == 0 || (orientation == 1 && spanCount > 1);
        this.mCanDragH = z2;
        if (orientation != 1 && (orientation != 0 || spanCount <= 1)) {
            z = false;
        }
        this.mCanDragV = z;
        if (this.mInitiateOnTouch) {
            return checkConditionAndStartDragging(recyclerView, motionEvent, false);
        }
        if (!this.mInitiateOnLongPress) {
            return false;
        }
        this.mHandler.startLongPressDetection(motionEvent, this.mLongPressTimeout);
        return false;
    }

    private void handleActionMoveWhileDragging(RecyclerView recyclerView, MotionEvent motionEvent) {
        this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
        this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
        this.mDragMinTouchX = Math.min(this.mDragMinTouchX, this.mLastTouchX);
        this.mDragMinTouchY = Math.min(this.mDragMinTouchY, this.mLastTouchY);
        this.mDragMaxTouchX = Math.max(this.mDragMaxTouchX, this.mLastTouchX);
        this.mDragMaxTouchY = Math.max(this.mDragMaxTouchY, this.mLastTouchY);
        updateDragDirectionMask();
        if (this.mDraggingItemDecorator.update(motionEvent, false)) {
            if (this.mSwapTargetItemOperator != null) {
                this.mSwapTargetItemOperator.update(this.mDraggingItemDecorator.getDraggingItemTranslationX(), this.mDraggingItemDecorator.getDraggingItemTranslationY());
            }
            checkItemSwapping(recyclerView);
            onItemMoveDistanceUpdated();
        }
    }

    private boolean handleActionMoveWhileNotDragging(RecyclerView recyclerView, MotionEvent motionEvent) {
        return this.mInitiateOnMove ? checkConditionAndStartDragging(recyclerView, motionEvent, true) : false;
    }

    private boolean handleActionUpOrCancel(int i, boolean z) {
        boolean z2 = i == 1;
        if (this.mHandler != null) {
            this.mHandler.cancelLongPressDetection();
        }
        this.mInitialTouchX = 0;
        this.mInitialTouchY = 0;
        this.mLastTouchX = 0;
        this.mLastTouchY = 0;
        this.mDragStartTouchX = 0;
        this.mDragStartTouchY = 0;
        this.mDragMinTouchX = 0;
        this.mDragMinTouchY = 0;
        this.mDragMaxTouchX = 0;
        this.mDragMaxTouchY = 0;
        this.mDragScrollDistanceX = 0;
        this.mDragScrollDistanceY = 0;
        this.mInitialTouchItemId = -1;
        this.mCanDragH = false;
        this.mCanDragV = false;
        if (z && isDragging()) {
            finishDragging(z2);
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleScrollOnDraggingInternal(android.support.v7.widget.RecyclerView r16, boolean r17) {
        /*
        r15 = this;
        if (r17 == 0) goto L_0x000a;
    L_0x0002:
        r0 = r16.getWidth();
        r8 = r0;
    L_0x0007:
        if (r8 != 0) goto L_0x0010;
    L_0x0009:
        return;
    L_0x000a:
        r0 = r16.getHeight();
        r8 = r0;
        goto L_0x0007;
    L_0x0010:
        r0 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r1 = (float) r8;
        r9 = r0 / r1;
        if (r17 == 0) goto L_0x0122;
    L_0x0017:
        r0 = r15.mLastTouchX;
    L_0x0019:
        r0 = (float) r0;
        r0 = r0 * r9;
        r1 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r0 = r0 - r1;
        r1 = java.lang.Math.abs(r0);
        r2 = 0;
        r3 = 1050253722; // 0x3e99999a float:0.3 double:5.188942835E-315;
        r4 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r1 = r4 - r1;
        r1 = r3 - r1;
        r1 = java.lang.Math.max(r2, r1);
        r2 = 1079334229; // 0x40555555 float:3.3333333 double:5.33261963E-315;
        r1 = r1 * r2;
        r10 = r15.mScrollDirMask;
        r11 = r15.mDraggingItemDecorator;
        r0 = java.lang.Math.signum(r0);
        r0 = (int) r0;
        r2 = 1103626240; // 0x41c80000 float:25.0 double:5.45263811E-315;
        r3 = r15.mDragEdgeScrollSpeed;
        r2 = r2 * r3;
        r3 = r15.mDisplayDensity;
        r2 = r2 * r3;
        r1 = r1 * r2;
        r2 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r1 = r1 + r2;
        r1 = (int) r1;
        r3 = r0 * r1;
        r2 = 0;
        r12 = r15.mDraggableRange;
        r0 = r15.mRecyclerView;
        r13 = com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils.findFirstCompletelyVisibleItemPosition(r0);
        r0 = r15.mRecyclerView;
        r14 = com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils.findLastCompletelyVisibleItemPosition(r0);
        r5 = 0;
        r0 = 0;
        r4 = 0;
        r1 = 0;
        r6 = -1;
        if (r13 == r6) goto L_0x019e;
    L_0x0062:
        r6 = r12.getStart();
        if (r13 > r6) goto L_0x0069;
    L_0x0068:
        r0 = 1;
    L_0x0069:
        r6 = r12.getStart();
        r6 = r6 + -1;
        if (r13 > r6) goto L_0x019e;
    L_0x0071:
        r5 = 1;
        r6 = r0;
        r7 = r5;
    L_0x0074:
        r0 = -1;
        if (r14 == r0) goto L_0x019a;
    L_0x0077:
        r0 = r12.getEnd();
        if (r14 < r0) goto L_0x0197;
    L_0x007d:
        r0 = 1;
    L_0x007e:
        r1 = r12.getEnd();
        r1 = r1 + 1;
        if (r14 < r1) goto L_0x0193;
    L_0x0086:
        r1 = 1;
        r5 = r0;
    L_0x0088:
        if (r3 <= 0) goto L_0x0129;
    L_0x008a:
        if (r17 == 0) goto L_0x0126;
    L_0x008c:
        r0 = 8;
    L_0x008e:
        r0 = r0 & r10;
        if (r0 != 0) goto L_0x0190;
    L_0x0091:
        r0 = 0;
        r4 = r0;
    L_0x0093:
        if (r7 != 0) goto L_0x0097;
    L_0x0095:
        if (r4 < 0) goto L_0x009b;
    L_0x0097:
        if (r1 != 0) goto L_0x014a;
    L_0x0099:
        if (r4 <= 0) goto L_0x014a;
    L_0x009b:
        r15.safeEndAnimationsIfRequired(r16);
        if (r17 == 0) goto L_0x0137;
    L_0x00a0:
        r1 = r15.scrollByXAndGetScrolledAmount(r4);
    L_0x00a4:
        if (r4 >= 0) goto L_0x0140;
    L_0x00a6:
        if (r6 != 0) goto L_0x013d;
    L_0x00a8:
        r0 = 1;
    L_0x00a9:
        r11.setIsScrolling(r0);
    L_0x00ac:
        r0 = 1;
        r11.refresh(r0);
        r0 = r15.mSwapTargetItemOperator;
        if (r0 == 0) goto L_0x018d;
    L_0x00b4:
        r0 = r15.mSwapTargetItemOperator;
        r2 = r11.getDraggingItemTranslationX();
        r3 = r11.getDraggingItemTranslationY();
        r0.update(r2, r3);
        r3 = r1;
    L_0x00c2:
        if (r3 == 0) goto L_0x0151;
    L_0x00c4:
        r0 = 1;
    L_0x00c5:
        r1 = r15.mEdgeEffectDecorator;
        if (r1 == 0) goto L_0x010d;
    L_0x00c9:
        if (r17 == 0) goto L_0x0154;
    L_0x00cb:
        r2 = r11.getTranslatedItemPositionLeft();
    L_0x00cf:
        if (r17 == 0) goto L_0x015a;
    L_0x00d1:
        r1 = r11.getTranslatedItemPositionRight();
    L_0x00d5:
        r5 = r2 + r1;
        r5 = r5 / 2;
        if (r13 != 0) goto L_0x0160;
    L_0x00db:
        if (r14 != 0) goto L_0x0160;
    L_0x00dd:
        if (r4 >= 0) goto L_0x00e0;
    L_0x00df:
        r1 = r2;
    L_0x00e0:
        r1 = (float) r1;
        r1 = r1 * r9;
        r2 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r2 = r1 - r2;
        r5 = java.lang.Math.abs(r2);
        r1 = 0;
        r6 = 1053609165; // 0x3ecccccd float:0.4 double:5.205520926E-315;
        r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1));
        if (r5 <= 0) goto L_0x016f;
    L_0x00f2:
        if (r4 == 0) goto L_0x016f;
    L_0x00f4:
        if (r0 != 0) goto L_0x016f;
    L_0x00f6:
        r0 = 0;
        r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1));
        if (r0 >= 0) goto L_0x0171;
    L_0x00fb:
        if (r17 == 0) goto L_0x0169;
    L_0x00fd:
        r0 = r11.isReachedToLeftLimit();
        if (r0 == 0) goto L_0x016f;
    L_0x0103:
        r0 = r15.mDisplayDensity;
        r0 = -r0;
        r1 = 1000593162; // 0x3ba3d70a float:0.005 double:4.94358707E-315;
        r0 = r0 * r1;
    L_0x010a:
        r15.updateEdgeEffect(r0);
    L_0x010d:
        r0 = r15.mRecyclerView;
        r1 = r15.mCheckItemSwappingRunnable;
        android.support.v4.view.ViewCompat.postOnAnimation(r0, r1);
        if (r3 == 0) goto L_0x0009;
    L_0x0116:
        if (r17 == 0) goto L_0x0187;
    L_0x0118:
        r0 = r15.mDragScrollDistanceX;
        r0 = r0 + r3;
        r15.mDragScrollDistanceX = r0;
    L_0x011d:
        r15.onItemMoveDistanceUpdated();
        goto L_0x0009;
    L_0x0122:
        r0 = r15.mLastTouchY;
        goto L_0x0019;
    L_0x0126:
        r0 = 2;
        goto L_0x008e;
    L_0x0129:
        if (r3 >= 0) goto L_0x0190;
    L_0x012b:
        if (r17 == 0) goto L_0x0135;
    L_0x012d:
        r0 = 4;
    L_0x012e:
        r0 = r0 & r10;
        if (r0 != 0) goto L_0x0190;
    L_0x0131:
        r0 = 0;
        r4 = r0;
        goto L_0x0093;
    L_0x0135:
        r0 = 1;
        goto L_0x012e;
    L_0x0137:
        r1 = r15.scrollByYAndGetScrolledAmount(r4);
        goto L_0x00a4;
    L_0x013d:
        r0 = 0;
        goto L_0x00a9;
    L_0x0140:
        if (r5 != 0) goto L_0x0148;
    L_0x0142:
        r0 = 1;
    L_0x0143:
        r11.setIsScrolling(r0);
        goto L_0x00ac;
    L_0x0148:
        r0 = 0;
        goto L_0x0143;
    L_0x014a:
        r0 = 0;
        r11.setIsScrolling(r0);
        r3 = r2;
        goto L_0x00c2;
    L_0x0151:
        r0 = 0;
        goto L_0x00c5;
    L_0x0154:
        r2 = r11.getTranslatedItemPositionTop();
        goto L_0x00cf;
    L_0x015a:
        r1 = r11.getTranslatedItemPositionBottom();
        goto L_0x00d5;
    L_0x0160:
        r6 = r8 / 2;
        if (r5 >= r6) goto L_0x0167;
    L_0x0164:
        r1 = r2;
        goto L_0x00e0;
    L_0x0167:
        r2 = r1;
        goto L_0x0164;
    L_0x0169:
        r0 = r11.isReachedToTopLimit();
        if (r0 != 0) goto L_0x0103;
    L_0x016f:
        r0 = r1;
        goto L_0x010a;
    L_0x0171:
        if (r17 == 0) goto L_0x0180;
    L_0x0173:
        r0 = r11.isReachedToRightLimit();
        if (r0 == 0) goto L_0x016f;
    L_0x0179:
        r0 = r15.mDisplayDensity;
        r1 = 1000593162; // 0x3ba3d70a float:0.005 double:4.94358707E-315;
        r0 = r0 * r1;
        goto L_0x010a;
    L_0x0180:
        r0 = r11.isReachedToBottomLimit();
        if (r0 == 0) goto L_0x016f;
    L_0x0186:
        goto L_0x0179;
    L_0x0187:
        r0 = r15.mDragScrollDistanceY;
        r0 = r0 + r3;
        r15.mDragScrollDistanceY = r0;
        goto L_0x011d;
    L_0x018d:
        r3 = r1;
        goto L_0x00c2;
    L_0x0190:
        r4 = r3;
        goto L_0x0093;
    L_0x0193:
        r5 = r0;
        r1 = r4;
        goto L_0x0088;
    L_0x0197:
        r0 = r1;
        goto L_0x007e;
    L_0x019a:
        r5 = r1;
        r1 = r4;
        goto L_0x0088;
    L_0x019e:
        r6 = r0;
        r7 = r5;
        goto L_0x0074;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager.handleScrollOnDraggingInternal(android.support.v7.widget.RecyclerView, boolean):void");
    }

    private void onItemMoveDistanceUpdated() {
        if (this.mItemDragEventListener != null) {
            this.mItemDragEventListener.onItemDragMoveDistanceUpdated(this.mDragScrollDistanceX + this.mDraggingItemDecorator.getDraggingItemMoveOffsetX(), this.mDragScrollDistanceY + this.mDraggingItemDecorator.getDraggingItemMoveOffsetY());
        }
    }

    private void performSwapItems(RecyclerView recyclerView, @NonNull ViewHolder viewHolder, Rect rect, int i, int i2) {
        if (this.mItemDragEventListener != null) {
            this.mItemDragEventListener.onItemDragPositionChanged(i, i2);
        }
        LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
        int layoutType = CustomRecyclerViewUtils.getLayoutType(this.mRecyclerView);
        boolean z = CustomRecyclerViewUtils.extractOrientation(layoutType) == 1;
        int findFirstVisibleItemPosition = CustomRecyclerViewUtils.findFirstVisibleItemPosition(this.mRecyclerView, false);
        View findViewByPosition = CustomRecyclerViewUtils.findViewByPosition(layoutManager, i);
        View findViewByPosition2 = CustomRecyclerViewUtils.findViewByPosition(layoutManager, i2);
        View findViewByPosition3 = CustomRecyclerViewUtils.findViewByPosition(layoutManager, findFirstVisibleItemPosition);
        Integer itemViewOrigin = getItemViewOrigin(findViewByPosition, z);
        Integer itemViewOrigin2 = getItemViewOrigin(findViewByPosition2, z);
        Integer itemViewOrigin3 = getItemViewOrigin(findViewByPosition3, z);
        this.mAdapter.moveItem(i, i2, layoutType);
        if (findFirstVisibleItemPosition == i && itemViewOrigin3 != null && itemViewOrigin2 != null) {
            scrollBySpecifiedOrientation(recyclerView, -(itemViewOrigin2.intValue() - itemViewOrigin3.intValue()), z);
            safeEndAnimations(recyclerView);
        } else if (findFirstVisibleItemPosition == i2 && findViewByPosition != null && itemViewOrigin != null && !itemViewOrigin.equals(itemViewOrigin2)) {
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) findViewByPosition.getLayoutParams();
            scrollBySpecifiedOrientation(recyclerView, z ? -(marginLayoutParams.bottomMargin + (layoutManager.getDecoratedMeasuredHeight(findViewByPosition) + marginLayoutParams.topMargin)) : -(marginLayoutParams.rightMargin + (layoutManager.getDecoratedMeasuredWidth(findViewByPosition) + marginLayoutParams.leftMargin)), z);
            safeEndAnimations(recyclerView);
        }
    }

    private static void safeEndAnimation(RecyclerView recyclerView, ViewHolder viewHolder) {
        ItemAnimator itemAnimator = recyclerView != null ? recyclerView.getItemAnimator() : null;
        if (itemAnimator != null) {
            itemAnimator.endAnimation(viewHolder);
        }
    }

    private static void safeEndAnimations(RecyclerView recyclerView) {
        ItemAnimator itemAnimator = recyclerView != null ? recyclerView.getItemAnimator() : null;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
    }

    private void safeEndAnimationsIfRequired(RecyclerView recyclerView) {
        if (this.mSwapTargetItemOperator != null) {
            safeEndAnimations(recyclerView);
        }
    }

    private static void scrollBySpecifiedOrientation(RecyclerView recyclerView, int i, boolean z) {
        if (z) {
            recyclerView.scrollBy(0, i);
        } else {
            recyclerView.scrollBy(i, 0);
        }
    }

    private int scrollByXAndGetScrolledAmount(int i) {
        this.mActualScrollByXAmount = 0;
        this.mInScrollByMethod = true;
        this.mRecyclerView.scrollBy(i, 0);
        this.mInScrollByMethod = false;
        return this.mActualScrollByXAmount;
    }

    private int scrollByYAndGetScrolledAmount(int i) {
        this.mActualScrollByYAmount = 0;
        this.mInScrollByMethod = true;
        this.mRecyclerView.scrollBy(0, i);
        this.mInScrollByMethod = false;
        return this.mActualScrollByYAmount;
    }

    private void startDragging(RecyclerView recyclerView, MotionEvent motionEvent, ViewHolder viewHolder, ItemDraggableRange itemDraggableRange) {
        safeEndAnimation(recyclerView, viewHolder);
        this.mHandler.cancelLongPressDetection();
        this.mDraggingItemInfo = new DraggingItemInfo(recyclerView, viewHolder, this.mLastTouchX, this.mLastTouchY);
        this.mDraggingItemViewHolder = viewHolder;
        this.mDraggableRange = itemDraggableRange;
        this.mOrigOverScrollMode = ViewCompat.getOverScrollMode(recyclerView);
        ViewCompat.setOverScrollMode(recyclerView, 2);
        this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
        this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
        int i = this.mLastTouchY;
        this.mDragMaxTouchY = i;
        this.mDragMinTouchY = i;
        this.mDragStartTouchY = i;
        i = this.mLastTouchX;
        this.mDragMaxTouchX = i;
        this.mDragMinTouchX = i;
        this.mDragStartTouchX = i;
        this.mScrollDirMask = 0;
        this.mCurrentItemMoveMode = this.mItemMoveMode;
        this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        startScrollOnDraggingProcess();
        this.mAdapter.onDragItemStarted(this.mDraggingItemInfo, viewHolder, this.mDraggableRange, this.mCurrentItemMoveMode);
        this.mAdapter.onBindViewHolder(viewHolder, viewHolder.getLayoutPosition());
        this.mDraggingItemDecorator = new DraggingItemDecorator(this.mRecyclerView, viewHolder, this.mDraggableRange);
        this.mDraggingItemDecorator.setShadowDrawable(this.mShadowDrawable);
        this.mDraggingItemDecorator.start(motionEvent, this.mDraggingItemInfo);
        i = CustomRecyclerViewUtils.getLayoutType(this.mRecyclerView);
        if (supportsViewTranslation() && !this.mCheckCanDrop && CustomRecyclerViewUtils.isLinearLayout(i)) {
            this.mSwapTargetItemOperator = new SwapTargetItemOperator(this.mRecyclerView, viewHolder, this.mDraggableRange, this.mDraggingItemInfo);
            this.mSwapTargetItemOperator.setSwapTargetTranslationInterpolator(this.mSwapTargetTranslationInterpolator);
            this.mSwapTargetItemOperator.start();
            this.mSwapTargetItemOperator.update(this.mDraggingItemDecorator.getDraggingItemTranslationX(), this.mDraggingItemDecorator.getDraggingItemTranslationY());
        }
        if (this.mEdgeEffectDecorator != null) {
            this.mEdgeEffectDecorator.reorderToTop();
        }
        if (this.mItemDragEventListener != null) {
            this.mItemDragEventListener.onItemDragStarted(this.mAdapter.getDraggingItemInitialPosition());
            this.mItemDragEventListener.onItemDragMoveDistanceUpdated(0, 0);
        }
    }

    private void startScrollOnDraggingProcess() {
        this.mScrollOnDraggingProcess.start();
    }

    private void stopScrollOnDraggingProcess() {
        if (this.mScrollOnDraggingProcess != null) {
            this.mScrollOnDraggingProcess.stop();
        }
    }

    private static boolean supportsEdgeEffect() {
        return VERSION.SDK_INT >= 14;
    }

    private static boolean supportsViewTranslation() {
        return VERSION.SDK_INT >= 11;
    }

    private void swapItems(RecyclerView recyclerView, int i, @Nullable ViewHolder viewHolder, @NonNull ViewHolder viewHolder2) {
        Rect layoutMargins = CustomRecyclerViewUtils.getLayoutMargins(viewHolder2.itemView, this.mTmpRect1);
        int adapterPosition = viewHolder2.getAdapterPosition();
        int abs = Math.abs(i - adapterPosition);
        Object obj = null;
        if (i != -1 && adapterPosition != -1 && recyclerView.getAdapter().getItemId(i) == this.mDraggingItemInfo.id) {
            Object obj2 = (!CustomRecyclerViewUtils.isLinearLayout(CustomRecyclerViewUtils.getLayoutType(recyclerView)) || (supportsViewTranslation() && this.mCheckCanDrop)) ? null : 1;
            if (abs != 0) {
                if (abs != 1 || viewHolder == null || obj2 == null) {
                    obj = 1;
                } else {
                    int min;
                    View view = viewHolder.itemView;
                    View view2 = viewHolder2.itemView;
                    Rect rect = this.mDraggingItemInfo.margins;
                    if (this.mCanDragH) {
                        min = Math.min(view.getLeft() - rect.left, view2.getLeft() - layoutMargins.left);
                        float max = (((float) (Math.max(view.getRight() + rect.right, view2.getRight() + layoutMargins.right) - min)) * 0.5f) + ((float) min);
                        float f = ((float) (this.mLastTouchX - this.mDraggingItemInfo.grabbedPositionX)) + (((float) this.mDraggingItemInfo.width) * 0.5f);
                        if (adapterPosition < i) {
                            if (f < max) {
                                obj = 1;
                            }
                        } else if (f > max) {
                            obj = 1;
                        }
                    }
                    if (obj == null && this.mCanDragV) {
                        min = Math.min(view.getTop() - rect.top, view2.getTop() - layoutMargins.top);
                        float max2 = (((float) (Math.max(view.getBottom() + rect.bottom, view2.getBottom() + layoutMargins.bottom) - min)) * 0.5f) + ((float) min);
                        float f2 = ((float) (this.mLastTouchY - this.mDraggingItemInfo.grabbedPositionY)) + (((float) this.mDraggingItemInfo.height) * 0.5f);
                        if (adapterPosition < i) {
                            if (f2 < max2) {
                                obj = 1;
                            }
                        } else if (f2 > max2) {
                            obj = 1;
                        }
                    }
                }
            }
            if (obj != null) {
                performSwapItems(recyclerView, viewHolder2, layoutMargins, i, adapterPosition);
            }
        }
    }

    private void updateDragDirectionMask() {
        if (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView) == 1) {
            if (this.mDragStartTouchY - this.mDragMinTouchY > this.mScrollTouchSlop || this.mDragMaxTouchY - this.mLastTouchY > this.mScrollTouchSlop) {
                this.mScrollDirMask |= 1;
            }
            if (this.mDragMaxTouchY - this.mDragStartTouchY > this.mScrollTouchSlop || this.mLastTouchY - this.mDragMinTouchY > this.mScrollTouchSlop) {
                this.mScrollDirMask |= 2;
            }
        } else if (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView) == 0) {
            if (this.mDragStartTouchX - this.mDragMinTouchX > this.mScrollTouchSlop || this.mDragMaxTouchX - this.mLastTouchX > this.mScrollTouchSlop) {
                this.mScrollDirMask |= 4;
            }
            if (this.mDragMaxTouchX - this.mDragStartTouchX > this.mScrollTouchSlop || this.mLastTouchX - this.mDragMinTouchX > this.mScrollTouchSlop) {
                this.mScrollDirMask |= 8;
            }
        }
    }

    private void updateEdgeEffect(float f) {
        if (f == 0.0f) {
            this.mEdgeEffectDecorator.releaseBothGlows();
        } else if (f < 0.0f) {
            this.mEdgeEffectDecorator.pullFirstEdge(f);
        } else {
            this.mEdgeEffectDecorator.pullSecondEdge(f);
        }
    }

    private void verifyItemDraggableRange(ItemDraggableRange itemDraggableRange, ViewHolder viewHolder) {
        int max = Math.max(0, this.mAdapter.getItemCount() - 1);
        if (itemDraggableRange.getStart() > itemDraggableRange.getEnd()) {
            throw new IllegalStateException("Invalid range specified --- start > range (range = " + itemDraggableRange + ")");
        } else if (itemDraggableRange.getStart() < 0) {
            throw new IllegalStateException("Invalid range specified --- start < 0 (range = " + itemDraggableRange + ")");
        } else if (itemDraggableRange.getEnd() > max) {
            throw new IllegalStateException("Invalid range specified --- end >= count (range = " + itemDraggableRange + ")");
        } else if (!itemDraggableRange.checkInRange(viewHolder.getAdapterPosition())) {
            throw new IllegalStateException("Invalid range specified --- does not contain drag target item (range = " + itemDraggableRange + ", position = " + viewHolder.getAdapterPosition() + ")");
        }
    }

    public void attachRecyclerView(@NonNull RecyclerView recyclerView) {
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else if (this.mAdapter == null || getDraggableItemWrapperAdapter(recyclerView) != this.mAdapter) {
            throw new IllegalStateException("adapter is not set properly");
        } else {
            this.mRecyclerView = recyclerView;
            this.mRecyclerView.addOnScrollListener(this.mInternalUseOnScrollListener);
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            this.mDisplayDensity = this.mRecyclerView.getResources().getDisplayMetrics().density;
            this.mTouchSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
            this.mScrollTouchSlop = (int) ((((float) this.mTouchSlop) * SCROLL_TOUCH_SLOP_MULTIPLY) + 0.5f);
            this.mHandler = new InternalHandler(this);
            if (supportsEdgeEffect()) {
                switch (CustomRecyclerViewUtils.getOrientation(this.mRecyclerView)) {
                    case 0:
                        this.mEdgeEffectDecorator = new LeftRightEdgeEffectDecorator(this.mRecyclerView);
                        break;
                    case 1:
                        this.mEdgeEffectDecorator = new TopBottomEdgeEffectDecorator(this.mRecyclerView);
                        break;
                }
                if (this.mEdgeEffectDecorator != null) {
                    this.mEdgeEffectDecorator.start();
                }
            }
        }
    }

    public void cancelDrag() {
        cancelDrag(false);
    }

    void cancelDrag(boolean z) {
        handleActionUpOrCancel(3, false);
        if (z) {
            finishDragging(false);
        } else if (isDragging()) {
            this.mHandler.requestDeferredCancelDrag();
        }
    }

    void checkItemSwapping(RecyclerView recyclerView) {
        SwapTarget swapTarget;
        boolean z;
        ViewHolder viewHolder = this.mDraggingItemViewHolder;
        FindSwapTargetContext findSwapTargetContext = this.mFindSwapTargetContext;
        findSwapTargetContext.setup(recyclerView, this.mDraggingItemViewHolder, this.mDraggingItemInfo, this.mLastTouchX, this.mLastTouchY, this.mDraggableRange, this.mCheckCanDrop);
        int draggingItemInitialPosition = this.mAdapter.getDraggingItemInitialPosition();
        int draggingItemCurrentPosition = this.mAdapter.getDraggingItemCurrentPosition();
        SwapTarget findSwapTargetItem = findSwapTargetItem(this.mTempSwapTarget, findSwapTargetContext, false);
        if (findSwapTargetItem.position != -1) {
            boolean z2 = !this.mCheckCanDrop;
            if (!z2) {
                z2 = this.mAdapter.canDropItems(draggingItemInitialPosition, findSwapTargetItem.position);
            }
            if (!z2) {
                findSwapTargetItem = findSwapTargetItem(this.mTempSwapTarget, findSwapTargetContext, true);
                if (findSwapTargetItem.position != -1) {
                    z2 = this.mAdapter.canDropItems(draggingItemInitialPosition, findSwapTargetItem.position);
                    swapTarget = findSwapTargetItem;
                    z = z2;
                }
            }
            swapTarget = findSwapTargetItem;
            z = z2;
        } else {
            swapTarget = findSwapTargetItem;
            z = false;
        }
        if (z) {
            swapItems(recyclerView, draggingItemCurrentPosition, viewHolder, swapTarget.holder);
        }
        if (this.mSwapTargetItemOperator != null) {
            this.mSwapTargetItemOperator.setSwapTargetItem(z ? swapTarget.holder : null);
        }
        if (z) {
            this.mHandler.scheduleDraggingItemViewSizeUpdateCheck();
        }
        swapTarget.clear();
        findSwapTargetContext.clear();
    }

    public Adapter createWrappedAdapter(@NonNull Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        } else if (this.mAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        } else {
            this.mAdapter = new DraggableItemWrapperAdapter(this, adapter);
            return this.mAdapter;
        }
    }

    public float getDragEdgeScrollSpeed() {
        return this.mDragEdgeScrollSpeed;
    }

    public int getItemMoveMode() {
        return this.mItemMoveMode;
    }

    public int getItemSettleBackIntoPlaceAnimationDuration() {
        return this.mItemSettleBackIntoPlaceAnimationDuration;
    }

    @Nullable
    public Interpolator getItemSettleBackIntoPlaceAnimationInterpolator() {
        return this.mItemSettleBackIntoPlaceAnimationInterpolator;
    }

    @Nullable
    public OnItemDragEventListener getOnItemDragEventListener() {
        return this.mItemDragEventListener;
    }

    RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    void handleOnCheckItemViewSizeUpdate() {
        ViewHolder findViewHolderForItemId = this.mRecyclerView.findViewHolderForItemId(this.mDraggingItemInfo.id);
        if (findViewHolderForItemId != null) {
            int width = findViewHolderForItemId.itemView.getWidth();
            int height = findViewHolderForItemId.itemView.getHeight();
            if (width != this.mDraggingItemInfo.width || height != this.mDraggingItemInfo.height) {
                this.mDraggingItemInfo = DraggingItemInfo.createWithNewView(this.mDraggingItemInfo, findViewHolderForItemId);
                this.mDraggingItemDecorator.updateDraggingItemView(this.mDraggingItemInfo, findViewHolderForItemId);
            }
        }
    }

    void handleOnLongPress(MotionEvent motionEvent) {
        if (this.mInitiateOnLongPress) {
            checkConditionAndStartDragging(this.mRecyclerView, motionEvent, false);
        }
    }

    void handleScrollOnDragging() {
        RecyclerView recyclerView = this.mRecyclerView;
        switch (CustomRecyclerViewUtils.getOrientation(recyclerView)) {
            case 0:
                handleScrollOnDraggingInternal(recyclerView, true);
                return;
            case 1:
                handleScrollOnDraggingInternal(recyclerView, false);
                return;
            default:
                return;
        }
    }

    public boolean isCheckCanDropEnabled() {
        return this.mCheckCanDrop;
    }

    public boolean isDragging() {
        return (this.mDraggingItemInfo == null || this.mHandler.isCancelDragRequested()) ? false : true;
    }

    public boolean isInitiateOnLongPressEnabled() {
        return this.mInitiateOnLongPress;
    }

    public boolean isInitiateOnMoveEnabled() {
        return this.mInitiateOnMove;
    }

    public boolean isInitiateOnTouchEnabled() {
        return this.mInitiateOnTouch;
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    void onDraggingItemViewRecycled() {
        this.mDraggingItemViewHolder = null;
        this.mDraggingItemDecorator.invalidateDraggingItem();
    }

    boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        switch (actionMasked) {
            case 0:
                return !isDragging() ? handleActionDown(recyclerView, motionEvent) : false;
            case 1:
            case 3:
                handleActionUpOrCancel(actionMasked, true);
                return false;
            case 2:
                if (!isDragging()) {
                    return handleActionMoveWhileNotDragging(recyclerView, motionEvent);
                } else {
                    handleActionMoveWhileDragging(recyclerView, motionEvent);
                    return true;
                }
            default:
                return false;
        }
    }

    void onNewDraggingItemViewBound(ViewHolder viewHolder) {
        this.mDraggingItemViewHolder = viewHolder;
        this.mDraggingItemDecorator.setDraggingItemViewHolder(viewHolder);
    }

    void onRequestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            cancelDrag(true);
        }
    }

    void onScrollStateChanged(RecyclerView recyclerView, int i) {
        if (i == 1) {
            cancelDrag(true);
        }
    }

    void onScrolled(RecyclerView recyclerView, int i, int i2) {
        if (this.mInScrollByMethod) {
            this.mActualScrollByXAmount = i;
            this.mActualScrollByYAmount = i2;
        } else if (isDragging()) {
            ViewCompat.postOnAnimationDelayed(this.mRecyclerView, this.mCheckItemSwappingRunnable, 500);
        }
    }

    void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (isDragging()) {
            switch (actionMasked) {
                case 1:
                case 3:
                    handleActionUpOrCancel(actionMasked, true);
                    return;
                case 2:
                    handleActionMoveWhileDragging(recyclerView, motionEvent);
                    return;
                default:
                    return;
            }
        }
    }

    public void release() {
        cancelDrag(true);
        if (this.mHandler != null) {
            this.mHandler.release();
            this.mHandler = null;
        }
        if (this.mEdgeEffectDecorator != null) {
            this.mEdgeEffectDecorator.finish();
            this.mEdgeEffectDecorator = null;
        }
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        if (!(this.mRecyclerView == null || this.mInternalUseOnScrollListener == null)) {
            this.mRecyclerView.removeOnScrollListener(this.mInternalUseOnScrollListener);
        }
        this.mInternalUseOnScrollListener = null;
        if (this.mScrollOnDraggingProcess != null) {
            this.mScrollOnDraggingProcess.release();
            this.mScrollOnDraggingProcess = null;
        }
        this.mAdapter = null;
        this.mRecyclerView = null;
        this.mSwapTargetTranslationInterpolator = null;
    }

    public void setCheckCanDropEnabled(boolean z) {
        this.mCheckCanDrop = z;
    }

    public void setDragEdgeScrollSpeed(float f) {
        this.mDragEdgeScrollSpeed = Math.min(Math.max(f, 0.0f), 2.0f);
    }

    public void setDraggingItemShadowDrawable(@Nullable NinePatchDrawable ninePatchDrawable) {
        this.mShadowDrawable = ninePatchDrawable;
    }

    public void setInitiateOnLongPress(boolean z) {
        this.mInitiateOnLongPress = z;
    }

    public void setInitiateOnMove(boolean z) {
        this.mInitiateOnMove = z;
    }

    public void setInitiateOnTouch(boolean z) {
        this.mInitiateOnTouch = z;
    }

    public void setItemMoveMode(int i) {
        this.mItemMoveMode = i;
    }

    public void setItemSettleBackIntoPlaceAnimationDuration(int i) {
        this.mItemSettleBackIntoPlaceAnimationDuration = i;
    }

    public void setItemSettleBackIntoPlaceAnimationInterpolator(@Nullable Interpolator interpolator) {
        this.mItemSettleBackIntoPlaceAnimationInterpolator = interpolator;
    }

    public void setLongPressTimeout(int i) {
        this.mLongPressTimeout = i;
    }

    public void setOnItemDragEventListener(@Nullable OnItemDragEventListener onItemDragEventListener) {
        this.mItemDragEventListener = onItemDragEventListener;
    }

    public Interpolator setSwapTargetTranslationInterpolator() {
        return this.mSwapTargetTranslationInterpolator;
    }

    public void setSwapTargetTranslationInterpolator(@Nullable Interpolator interpolator) {
        this.mSwapTargetTranslationInterpolator = interpolator;
    }
}
