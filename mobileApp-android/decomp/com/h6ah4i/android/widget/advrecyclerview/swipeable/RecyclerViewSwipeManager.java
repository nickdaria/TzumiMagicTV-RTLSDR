package com.h6ah4i.android.widget.advrecyclerview.swipeable;

import android.graphics.Rect;
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
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import com.h6ah4i.android.widget.advrecyclerview.animator.SwipeDismissItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction;
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultActionDefault;
import com.h6ah4i.android.widget.advrecyclerview.utils.CustomRecyclerViewUtils;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

public class RecyclerViewSwipeManager implements SwipeableItemConstants {
    private static final boolean LOCAL_LOGD = false;
    private static final boolean LOCAL_LOGV = false;
    private static final int MIN_DISTANCE_TOUCH_SLOP_MUL = 5;
    private static final int SLIDE_ITEM_IMMEDIATELY_SET_TRANSLATION_THRESHOLD_DP = 8;
    private static final String TAG = "ARVSwipeManager";
    private SwipeableItemWrapperAdapter<ViewHolder> mAdapter;
    private long mCheckingTouchSlop = -1;
    private InternalHandler mHandler;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private OnItemTouchListener mInternalUseOnItemTouchListener = new C05761();
    private ItemSlidingAnimator mItemSlideAnimator;
    private OnItemSwipeEventListener mItemSwipeEventListener;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mLongPressTimeout = ViewConfiguration.getLongPressTimeout();
    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private long mMoveToOutsideWindowAnimationDuration = 200;
    private RecyclerView mRecyclerView;
    private long mReturnToDefaultPositionAnimationDuration = 300;
    private boolean mSwipeHorizontal;
    private int mSwipeThresholdDistance;
    private ViewHolder mSwipingItem;
    private long mSwipingItemId = -1;
    private final Rect mSwipingItemMargins = new Rect();
    private SwipingItemOperator mSwipingItemOperator;
    private int mSwipingItemPosition = -1;
    private int mSwipingItemReactionType;
    private int mTouchSlop;
    private int mTouchedItemOffsetX;
    private int mTouchedItemOffsetY;
    private VelocityTracker mVelocityTracker = VelocityTracker.obtain();

    class C05761 implements OnItemTouchListener {
        C05761() {
        }

        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return RecyclerViewSwipeManager.this.onInterceptTouchEvent(recyclerView, motionEvent);
        }

        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            RecyclerViewSwipeManager.this.onRequestDisallowInterceptTouchEvent(z);
        }

        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            RecyclerViewSwipeManager.this.onTouchEvent(recyclerView, motionEvent);
        }
    }

    private static class InternalHandler extends Handler {
        private static final int MSG_DEFERRED_CANCEL_SWIPE = 2;
        private static final int MSG_LONGPRESS = 1;
        private MotionEvent mDownMotionEvent;
        private RecyclerViewSwipeManager mHolder;

        public InternalHandler(RecyclerViewSwipeManager recyclerViewSwipeManager) {
            this.mHolder = recyclerViewSwipeManager;
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
                    this.mHolder.cancelSwipe(true);
                    return;
                default:
                    return;
            }
        }

        public boolean isCancelSwipeRequested() {
            return hasMessages(2);
        }

        public void release() {
            removeCallbacks(null);
            this.mHolder = null;
        }

        public void removeDeferredCancelSwipeRequest() {
            removeMessages(2);
        }

        public void requestDeferredCancelSwipe() {
            if (!isCancelSwipeRequested()) {
                sendEmptyMessage(2);
            }
        }

        public void startLongPressDetection(MotionEvent motionEvent, int i) {
            cancelLongPressDetection();
            this.mDownMotionEvent = MotionEvent.obtain(motionEvent);
            sendEmptyMessageAtTime(1, motionEvent.getDownTime() + ((long) i));
        }
    }

    public interface OnItemSwipeEventListener {
        void onItemSwipeFinished(int i, int i2, int i3);

        void onItemSwipeStarted(int i);
    }

    private boolean checkConditionAndStartSwiping(MotionEvent motionEvent, ViewHolder viewHolder) {
        int synchronizedPosition = CustomRecyclerViewUtils.getSynchronizedPosition(viewHolder);
        if (synchronizedPosition == -1) {
            return false;
        }
        startSwiping(motionEvent, viewHolder, synchronizedPosition);
        return true;
    }

    private static int determineBackgroundType(float f, boolean z) {
        return z ? f < 0.0f ? 1 : 3 : f < 0.0f ? 2 : 4;
    }

    private void finishSwiping(int i) {
        ViewHolder viewHolder = this.mSwipingItem;
        if (viewHolder != null) {
            boolean finishSwipeSlideToDefaultPosition;
            this.mHandler.removeDeferredCancelSwipeRequest();
            this.mHandler.cancelLongPressDetection();
            if (!(this.mRecyclerView == null || this.mRecyclerView.getParent() == null)) {
                this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(false);
            }
            int swipingItemPosition = getSwipingItemPosition();
            this.mVelocityTracker.clear();
            this.mSwipingItem = null;
            this.mSwipingItemPosition = -1;
            this.mSwipingItemId = -1;
            this.mLastTouchX = 0;
            this.mLastTouchY = 0;
            this.mInitialTouchX = 0;
            this.mTouchedItemOffsetX = 0;
            this.mTouchedItemOffsetY = 0;
            this.mCheckingTouchSlop = -1;
            this.mSwipingItemReactionType = 0;
            if (this.mSwipingItemOperator != null) {
                this.mSwipingItemOperator.finish();
                this.mSwipingItemOperator = null;
            }
            int resultCodeToSlideDirection = resultCodeToSlideDirection(i);
            SwipeResultAction swipeResultAction = null;
            if (this.mAdapter != null) {
                swipeResultAction = this.mAdapter.onSwipeItemFinished(viewHolder, swipingItemPosition, i);
            }
            if (swipeResultAction == null) {
                swipeResultAction = new SwipeResultActionDefault();
            }
            int resultActionType = swipeResultAction.getResultActionType();
            verifyAfterReaction(i, resultActionType);
            switch (resultActionType) {
                case 0:
                    finishSwipeSlideToDefaultPosition = this.mItemSlideAnimator.finishSwipeSlideToDefaultPosition(viewHolder, this.mSwipeHorizontal, true, this.mReturnToDefaultPositionAnimationDuration, swipingItemPosition, swipeResultAction);
                    break;
                case 1:
                    ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
                    long removeDuration = itemAnimator != null ? itemAnimator.getRemoveDuration() : 0;
                    if (supportsViewPropertyAnimator()) {
                        RemovingItemDecorator removingItemDecorator = new RemovingItemDecorator(this.mRecyclerView, viewHolder, i, removeDuration, itemAnimator != null ? itemAnimator.getMoveDuration() : 0);
                        removingItemDecorator.setMoveAnimationInterpolator(SwipeDismissItemAnimator.MOVE_INTERPOLATOR);
                        removingItemDecorator.start();
                    }
                    finishSwipeSlideToDefaultPosition = this.mItemSlideAnimator.finishSwipeSlideToOutsideOfWindow(viewHolder, resultCodeToSlideDirection, true, removeDuration, swipingItemPosition, swipeResultAction);
                    break;
                case 2:
                    finishSwipeSlideToDefaultPosition = this.mItemSlideAnimator.finishSwipeSlideToOutsideOfWindow(viewHolder, resultCodeToSlideDirection, true, this.mMoveToOutsideWindowAnimationDuration, swipingItemPosition, swipeResultAction);
                    break;
                default:
                    throw new IllegalStateException("Unknown after reaction type: " + resultActionType);
            }
            if (this.mAdapter != null) {
                this.mAdapter.onSwipeItemFinished2(viewHolder, swipingItemPosition, i, resultActionType, swipeResultAction);
            }
            if (this.mItemSwipeEventListener != null) {
                this.mItemSwipeEventListener.onItemSwipeFinished(swipingItemPosition, i, resultActionType);
            }
            if (!finishSwipeSlideToDefaultPosition) {
                swipeResultAction.slideAnimationEnd();
            }
        }
    }

    static int getItemPosition(@Nullable Adapter adapter, long j, int i) {
        if (adapter == null) {
            return -1;
        }
        int itemCount = adapter.getItemCount();
        if (i >= 0 && i < itemCount && adapter.getItemId(i) == j) {
            return i;
        }
        for (i = 0; i < itemCount; i++) {
            if (adapter.getItemId(i) == j) {
                return i;
            }
        }
        return -1;
    }

    private static SwipeableItemWrapperAdapter getSwipeableItemWrapperAdapter(RecyclerView recyclerView) {
        return (SwipeableItemWrapperAdapter) WrapperAdapterUtils.findWrappedAdapter(recyclerView.getAdapter(), SwipeableItemWrapperAdapter.class);
    }

    private boolean handleActionDown(RecyclerView recyclerView, MotionEvent motionEvent) {
        Adapter adapter = recyclerView.getAdapter();
        ViewHolder findChildViewHolderUnderWithTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(recyclerView, motionEvent.getX(), motionEvent.getY());
        if (!(findChildViewHolderUnderWithTranslation instanceof SwipeableItemViewHolder)) {
            return false;
        }
        int synchronizedPosition = CustomRecyclerViewUtils.getSynchronizedPosition(findChildViewHolderUnderWithTranslation);
        if (synchronizedPosition < 0 || synchronizedPosition >= adapter.getItemCount() || findChildViewHolderUnderWithTranslation.getItemId() != adapter.getItemId(synchronizedPosition)) {
            return false;
        }
        int x = (int) (motionEvent.getX() + 0.5f);
        int y = (int) (motionEvent.getY() + 0.5f);
        View view = findChildViewHolderUnderWithTranslation.itemView;
        synchronizedPosition = this.mAdapter.getSwipeReactionType(findChildViewHolderUnderWithTranslation, synchronizedPosition, x - (((int) (ViewCompat.getTranslationX(view) + 0.5f)) + view.getLeft()), y - (view.getTop() + ((int) (ViewCompat.getTranslationY(view) + 0.5f))));
        if (synchronizedPosition == 0) {
            return false;
        }
        this.mInitialTouchX = x;
        this.mInitialTouchY = y;
        this.mCheckingTouchSlop = findChildViewHolderUnderWithTranslation.getItemId();
        this.mSwipingItemReactionType = synchronizedPosition;
        if ((16777216 & synchronizedPosition) != 0) {
            this.mHandler.startLongPressDetection(motionEvent, this.mLongPressTimeout);
        }
        return true;
    }

    private boolean handleActionMoveWhileNotSwiping(RecyclerView recyclerView, MotionEvent motionEvent) {
        boolean z = true;
        if (this.mCheckingTouchSlop == -1) {
            return false;
        }
        int x = ((int) (motionEvent.getX() + 0.5f)) - this.mInitialTouchX;
        int y = ((int) (motionEvent.getY() + 0.5f)) - this.mInitialTouchY;
        if (!this.mSwipeHorizontal) {
            int i = y;
            y = x;
            x = i;
        }
        if (Math.abs(y) > this.mTouchSlop) {
            this.mCheckingTouchSlop = -1;
            return false;
        } else if (Math.abs(x) <= this.mTouchSlop) {
            return false;
        } else {
            if (this.mSwipeHorizontal) {
                if (x < 0) {
                    z = (this.mSwipingItemReactionType & 8) != 0;
                } else if ((this.mSwipingItemReactionType & 32768) == 0) {
                    z = false;
                }
            } else if (x < 0) {
                if ((this.mSwipingItemReactionType & 512) == 0) {
                    z = false;
                }
            } else if ((this.mSwipingItemReactionType & 2097152) == 0) {
                z = false;
            }
            if (z) {
                this.mCheckingTouchSlop = -1;
                return false;
            }
            ViewHolder findChildViewHolderUnderWithTranslation = CustomRecyclerViewUtils.findChildViewHolderUnderWithTranslation(recyclerView, motionEvent.getX(), motionEvent.getY());
            if (findChildViewHolderUnderWithTranslation != null && findChildViewHolderUnderWithTranslation.getItemId() == this.mCheckingTouchSlop) {
                return checkConditionAndStartSwiping(motionEvent, findChildViewHolderUnderWithTranslation);
            }
            this.mCheckingTouchSlop = -1;
            return false;
        }
    }

    private void handleActionMoveWhileSwiping(MotionEvent motionEvent) {
        this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
        this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
        this.mVelocityTracker.addMovement(motionEvent);
        int i = this.mLastTouchX - this.mTouchedItemOffsetX;
        int i2 = this.mLastTouchY - this.mTouchedItemOffsetY;
        this.mSwipingItemOperator.update(getSwipingItemPosition(), i, i2);
    }

    private boolean handleActionUpOrCancel(MotionEvent motionEvent, boolean z) {
        int i = 3;
        if (motionEvent != null) {
            i = MotionEventCompat.getActionMasked(motionEvent);
            this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
            this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
        }
        if (isSwiping()) {
            if (z) {
                handleActionUpOrCancelWhileSwiping(i);
            }
            return true;
        }
        handleActionUpOrCancelWhileNotSwiping();
        return false;
    }

    private void handleActionUpOrCancelWhileNotSwiping() {
        if (this.mHandler != null) {
            this.mHandler.cancelLongPressDetection();
        }
        this.mCheckingTouchSlop = -1;
        this.mSwipingItemReactionType = 0;
    }

    private void handleActionUpOrCancelWhileSwiping(int i) {
        int i2;
        if (i == 1) {
            boolean z = this.mSwipeHorizontal;
            View view = this.mSwipingItem.itemView;
            int width = z ? view.getWidth() : view.getHeight();
            float f = z ? (float) (this.mLastTouchX - this.mInitialTouchX) : (float) (this.mLastTouchY - this.mInitialTouchY);
            float abs = Math.abs(f);
            this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaxFlingVelocity);
            float xVelocity = z ? this.mVelocityTracker.getXVelocity() : this.mVelocityTracker.getYVelocity();
            float abs2 = Math.abs(xVelocity);
            if (abs > ((float) this.mSwipeThresholdDistance) && xVelocity * f > 0.0f && abs2 <= ((float) this.mMaxFlingVelocity) && (abs > ((float) (width / 2)) || abs2 >= ((float) this.mMinFlingVelocity))) {
                if (z && f < 0.0f && SwipeReactionUtils.canSwipeLeft(this.mSwipingItemReactionType)) {
                    i2 = 2;
                    finishSwiping(i2);
                } else if (!z && f < 0.0f && SwipeReactionUtils.canSwipeUp(this.mSwipingItemReactionType)) {
                    i2 = 3;
                    finishSwiping(i2);
                } else if (z && f > 0.0f && SwipeReactionUtils.canSwipeRight(this.mSwipingItemReactionType)) {
                    i2 = 4;
                    finishSwiping(i2);
                } else if (!z && f > 0.0f && SwipeReactionUtils.canSwipeDown(this.mSwipingItemReactionType)) {
                    i2 = 5;
                    finishSwiping(i2);
                }
            }
        }
        i2 = 1;
        finishSwiping(i2);
    }

    private static int resultCodeToSlideDirection(int i) {
        switch (i) {
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
                return 3;
            default:
                return 0;
        }
    }

    private void slideItem(ViewHolder viewHolder, float f, boolean z, boolean z2) {
        if (f == SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_LEFT) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(viewHolder, 0, z2, this.mMoveToOutsideWindowAnimationDuration);
        } else if (f == SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_TOP) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(viewHolder, 1, z2, this.mMoveToOutsideWindowAnimationDuration);
        } else if (f == SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_RIGHT) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(viewHolder, 2, z2, this.mMoveToOutsideWindowAnimationDuration);
        } else if (f == SwipeableItemConstants.OUTSIDE_OF_THE_WINDOW_BOTTOM) {
            this.mItemSlideAnimator.slideToOutsideOfWindow(viewHolder, 3, z2, this.mMoveToOutsideWindowAnimationDuration);
        } else if (f == 0.0f) {
            this.mItemSlideAnimator.slideToDefaultPosition(viewHolder, z, z2, this.mReturnToDefaultPositionAnimationDuration);
        } else {
            this.mItemSlideAnimator.slideToSpecifiedPosition(viewHolder, f, z);
        }
    }

    private void startSwiping(MotionEvent motionEvent, ViewHolder viewHolder, int i) {
        this.mHandler.cancelLongPressDetection();
        this.mSwipingItem = viewHolder;
        this.mSwipingItemPosition = i;
        this.mSwipingItemId = this.mAdapter.getItemId(i);
        this.mLastTouchX = (int) (motionEvent.getX() + 0.5f);
        this.mLastTouchY = (int) (motionEvent.getY() + 0.5f);
        this.mTouchedItemOffsetX = this.mLastTouchX;
        this.mTouchedItemOffsetY = this.mLastTouchY;
        this.mCheckingTouchSlop = -1;
        CustomRecyclerViewUtils.getLayoutMargins(viewHolder.itemView, this.mSwipingItemMargins);
        this.mSwipingItemOperator = new SwipingItemOperator(this, this.mSwipingItem, this.mSwipingItemReactionType, this.mSwipeHorizontal);
        this.mSwipingItemOperator.start();
        this.mVelocityTracker.clear();
        this.mVelocityTracker.addMovement(motionEvent);
        this.mRecyclerView.getParent().requestDisallowInterceptTouchEvent(true);
        if (this.mItemSwipeEventListener != null) {
            this.mItemSwipeEventListener.onItemSwipeStarted(i);
        }
        this.mAdapter.onSwipeItemStarted(this, viewHolder, this.mSwipingItemId);
    }

    private static boolean supportsViewPropertyAnimator() {
        return VERSION.SDK_INT >= 11;
    }

    private static void verifyAfterReaction(int i, int i2) {
        if (i2 == 2 || i2 == 1) {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                    return;
                default:
                    throw new IllegalStateException("Unexpected after reaction has been requested: result = " + i + ", afterReaction = " + i2);
            }
        }
    }

    void applySlideItem(ViewHolder viewHolder, int i, float f, float f2, boolean z, boolean z2, boolean z3) {
        SwipeableItemViewHolder swipeableItemViewHolder = (SwipeableItemViewHolder) viewHolder;
        if (swipeableItemViewHolder.getSwipeableContainerView() != null) {
            int determineBackgroundType = f2 == 0.0f ? f == 0.0f ? 0 : determineBackgroundType(f, z) : determineBackgroundType(f2, z);
            if (f2 == 0.0f) {
                slideItem(viewHolder, f2, z, z2);
                this.mAdapter.onUpdateSlideAmount(viewHolder, i, z, f2, z3, determineBackgroundType);
                return;
            }
            float min = Math.min(Math.max(f2, z ? swipeableItemViewHolder.getMaxLeftSwipeAmount() : swipeableItemViewHolder.getMaxUpSwipeAmount()), z ? swipeableItemViewHolder.getMaxRightSwipeAmount() : swipeableItemViewHolder.getMaxDownSwipeAmount());
            this.mAdapter.onUpdateSlideAmount(viewHolder, i, z, f2, z3, determineBackgroundType);
            slideItem(viewHolder, min, z, z2);
        }
    }

    public void attachRecyclerView(@NonNull RecyclerView recyclerView) {
        boolean z = true;
        if (isReleased()) {
            throw new IllegalStateException("Accessing released object");
        } else if (this.mRecyclerView != null) {
            throw new IllegalStateException("RecyclerView instance has already been set");
        } else if (this.mAdapter == null || getSwipeableItemWrapperAdapter(recyclerView) != this.mAdapter) {
            throw new IllegalStateException("adapter is not set properly");
        } else {
            int orientation = CustomRecyclerViewUtils.getOrientation(recyclerView);
            if (orientation == -1) {
                throw new IllegalStateException("failed to determine layout orientation");
            }
            this.mRecyclerView = recyclerView;
            this.mRecyclerView.addOnItemTouchListener(this.mInternalUseOnItemTouchListener);
            ViewConfiguration viewConfiguration = ViewConfiguration.get(recyclerView.getContext());
            this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
            this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
            this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
            this.mSwipeThresholdDistance = this.mTouchSlop * 5;
            this.mItemSlideAnimator = new ItemSlidingAnimator(this.mAdapter);
            this.mItemSlideAnimator.setImmediatelySetTranslationThreshold((int) ((recyclerView.getResources().getDisplayMetrics().density * 8.0f) + 0.5f));
            if (orientation != 1) {
                z = false;
            }
            this.mSwipeHorizontal = z;
            this.mHandler = new InternalHandler(this);
        }
    }

    void cancelPendingAnimations(ViewHolder viewHolder) {
        if (this.mItemSlideAnimator != null) {
            this.mItemSlideAnimator.endAnimation(viewHolder);
        }
    }

    public void cancelSwipe() {
        cancelSwipe(false);
    }

    void cancelSwipe(boolean z) {
        handleActionUpOrCancel(null, false);
        if (z) {
            finishSwiping(1);
        } else if (isSwiping()) {
            this.mHandler.requestDeferredCancelSwipe();
        }
    }

    public Adapter createWrappedAdapter(@NonNull Adapter adapter) {
        if (!adapter.hasStableIds()) {
            throw new IllegalArgumentException("The passed adapter does not support stable IDs");
        } else if (this.mAdapter != null) {
            throw new IllegalStateException("already have a wrapped adapter");
        } else {
            this.mAdapter = new SwipeableItemWrapperAdapter(this, adapter);
            return this.mAdapter;
        }
    }

    public long getMoveToOutsideWindowAnimationDuration() {
        return this.mMoveToOutsideWindowAnimationDuration;
    }

    @Nullable
    public OnItemSwipeEventListener getOnItemSwipeEventListener() {
        return this.mItemSwipeEventListener;
    }

    public long getReturnToDefaultPositionAnimationDuration() {
        return this.mReturnToDefaultPositionAnimationDuration;
    }

    int getSwipeContainerViewTranslationX(ViewHolder viewHolder) {
        return this.mItemSlideAnimator.getSwipeContainerViewTranslationX(viewHolder);
    }

    int getSwipeContainerViewTranslationY(ViewHolder viewHolder) {
        return this.mItemSlideAnimator.getSwipeContainerViewTranslationY(viewHolder);
    }

    public int getSwipeThresholdDistance() {
        return this.mSwipeThresholdDistance;
    }

    int getSwipingItemPosition() {
        return this.mSwipingItemPosition;
    }

    void handleOnLongPress(MotionEvent motionEvent) {
        ViewHolder findViewHolderForItemId = this.mRecyclerView.findViewHolderForItemId(this.mCheckingTouchSlop);
        if (findViewHolderForItemId != null) {
            checkConditionAndStartSwiping(motionEvent, findViewHolderForItemId);
        }
    }

    boolean isAnimationRunning(ViewHolder viewHolder) {
        return this.mItemSlideAnimator != null && this.mItemSlideAnimator.isRunning(viewHolder);
    }

    public boolean isReleased() {
        return this.mInternalUseOnItemTouchListener == null;
    }

    public boolean isSwiping() {
        return (this.mSwipingItem == null || this.mHandler.isCancelSwipeRequested()) ? false : true;
    }

    boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                if (!isSwiping()) {
                    handleActionDown(recyclerView, motionEvent);
                    break;
                }
                break;
            case 1:
            case 3:
                if (handleActionUpOrCancel(motionEvent, true)) {
                    return true;
                }
                break;
            case 2:
                if (isSwiping()) {
                    handleActionMoveWhileSwiping(motionEvent);
                    return true;
                } else if (handleActionMoveWhileNotSwiping(recyclerView, motionEvent)) {
                    return true;
                }
                break;
        }
        return false;
    }

    void onRequestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            cancelSwipe(true);
        }
    }

    void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (isSwiping()) {
            switch (actionMasked) {
                case 1:
                case 3:
                    handleActionUpOrCancel(motionEvent, true);
                    return;
                case 2:
                    handleActionMoveWhileSwiping(motionEvent);
                    return;
                default:
                    return;
            }
        }
    }

    public void release() {
        cancelSwipe(true);
        if (this.mHandler != null) {
            this.mHandler.release();
            this.mHandler = null;
        }
        if (!(this.mRecyclerView == null || this.mInternalUseOnItemTouchListener == null)) {
            this.mRecyclerView.removeOnItemTouchListener(this.mInternalUseOnItemTouchListener);
        }
        this.mInternalUseOnItemTouchListener = null;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mItemSlideAnimator != null) {
            this.mItemSlideAnimator.endAnimations();
            this.mItemSlideAnimator = null;
        }
        this.mAdapter = null;
        this.mRecyclerView = null;
    }

    public void setLongPressTimeout(int i) {
        this.mLongPressTimeout = i;
    }

    public void setMoveToOutsideWindowAnimationDuration(long j) {
        this.mMoveToOutsideWindowAnimationDuration = j;
    }

    public void setOnItemSwipeEventListener(@Nullable OnItemSwipeEventListener onItemSwipeEventListener) {
        this.mItemSwipeEventListener = onItemSwipeEventListener;
    }

    public void setReturnToDefaultPositionAnimationDuration(long j) {
        this.mReturnToDefaultPositionAnimationDuration = j;
    }

    public void setSwipeThresholdDistance(int i) {
        this.mSwipeThresholdDistance = Math.max(i, this.mTouchSlop);
    }

    boolean swipeHorizontal() {
        return this.mSwipeHorizontal;
    }

    int syncSwipingItemPosition() {
        return syncSwipingItemPosition(this.mSwipingItemPosition);
    }

    int syncSwipingItemPosition(int i) {
        this.mSwipingItemPosition = getItemPosition(this.mAdapter, this.mSwipingItemId, i);
        return this.mSwipingItemPosition;
    }
}
