package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class FadePort extends VisibilityPort {
    private static boolean DBG = false;
    public static final int IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_SCREEN_X = "android:fade:screenX";
    private static final String PROPNAME_SCREEN_Y = "android:fade:screenY";
    private int mFadingMode;

    public FadePort() {
        this(3);
    }

    public FadePort(int i) {
        this.mFadingMode = i;
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_X, Integer.valueOf(iArr[0]));
        transitionValues.values.put(PROPNAME_SCREEN_Y, Integer.valueOf(iArr[1]));
    }

    private Animator createAnimation(View view, float f, float f2, AnimatorListenerAdapter animatorListenerAdapter) {
        Animator animator = null;
        if (f != f2) {
            animator = ObjectAnimator.ofFloat(view, "alpha", new float[]{f, f2});
            if (DBG) {
                Log.d(LOG_TAG, "Created animator " + animator);
            }
            if (animatorListenerAdapter != null) {
                animator.addListener(animatorListenerAdapter);
            }
        } else if (animatorListenerAdapter != null) {
            animatorListenerAdapter.onAnimationEnd(null);
        }
        return animator;
    }

    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.mFadingMode & 1) != 1 || transitionValues2 == null) {
            return null;
        }
        final View view = transitionValues2.view;
        if (DBG) {
            Log.d(LOG_TAG, "Fade.onAppear: startView, startVis, endView, endVis = " + (transitionValues != null ? transitionValues.view : null) + ", " + i + ", " + view + ", " + i2);
        }
        view.setAlpha(0.0f);
        addListener(new TransitionListenerAdapter() {
            boolean mCanceled = false;
            float mPausedAlpha;

            public void onTransitionCancel(TransitionPort transitionPort) {
                view.setAlpha(1.0f);
                this.mCanceled = true;
            }

            public void onTransitionEnd(TransitionPort transitionPort) {
                if (!this.mCanceled) {
                    view.setAlpha(1.0f);
                }
            }

            public void onTransitionPause(TransitionPort transitionPort) {
                this.mPausedAlpha = view.getAlpha();
                view.setAlpha(1.0f);
            }

            public void onTransitionResume(TransitionPort transitionPort) {
                view.setAlpha(this.mPausedAlpha);
            }
        });
        return createAnimation(view, 0.0f, 1.0f, null);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.mFadingMode & 2) != 2) {
            return null;
        }
        View view;
        View view2;
        int id;
        View view3 = transitionValues != null ? transitionValues.view : null;
        View view4 = transitionValues2 != null ? transitionValues2.view : null;
        if (DBG) {
            Log.d(LOG_TAG, "Fade.onDisappear: startView, startVis, endView, endVis = " + view3 + ", " + i + ", " + view4 + ", " + i2);
        }
        if (view4 == null || view4.getParent() == null) {
            if (view4 != null) {
                view = null;
                view2 = view4;
                view3 = view4;
            } else {
                if (view3 != null) {
                    if (view3.getParent() == null) {
                        view = null;
                        view2 = view3;
                    } else if ((view3.getParent() instanceof View) && view3.getParent().getParent() == null) {
                        id = ((View) view3.getParent()).getId();
                        if (id == -1 || viewGroup.findViewById(id) == null || !this.mCanRemoveViews) {
                            Object obj = null;
                            view3 = null;
                        } else {
                            view4 = view3;
                        }
                        view = null;
                        view2 = view4;
                    }
                }
                view = null;
                view2 = null;
                view3 = null;
            }
        } else if (i2 == 4) {
            view = view4;
            view2 = null;
            view3 = view4;
        } else if (view3 == view4) {
            view = view4;
            view2 = null;
            view3 = view4;
        } else {
            view = null;
            view2 = view3;
        }
        final int i3;
        final ViewGroup viewGroup2;
        if (view2 != null) {
            int intValue = ((Integer) transitionValues.values.get(PROPNAME_SCREEN_X)).intValue();
            id = ((Integer) transitionValues.values.get(PROPNAME_SCREEN_Y)).intValue();
            int[] iArr = new int[2];
            viewGroup.getLocationOnScreen(iArr);
            ViewCompat.offsetLeftAndRight(view2, (intValue - iArr[0]) - view2.getLeft());
            ViewCompat.offsetTopAndBottom(view2, (id - iArr[1]) - view2.getTop());
            ViewGroupOverlay.createFrom(viewGroup).add(view2);
            i3 = i2;
            viewGroup2 = viewGroup;
            return createAnimation(view3, 1.0f, 0.0f, new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    view3.setAlpha(1.0f);
                    if (view != null) {
                        view.setVisibility(i3);
                    }
                    if (view2 != null) {
                        ViewGroupOverlay.createFrom(viewGroup2).remove(view2);
                    }
                }
            });
        } else if (view == null) {
            return null;
        } else {
            view.setVisibility(0);
            i3 = i2;
            viewGroup2 = viewGroup;
            return createAnimation(view3, 1.0f, 0.0f, new AnimatorListenerAdapter() {
                boolean mCanceled = false;
                float mPausedAlpha = -1.0f;

                public void onAnimationCancel(Animator animator) {
                    this.mCanceled = true;
                    if (this.mPausedAlpha >= 0.0f) {
                        view3.setAlpha(this.mPausedAlpha);
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (!this.mCanceled) {
                        view3.setAlpha(1.0f);
                    }
                    if (!(view == null || this.mCanceled)) {
                        view.setVisibility(i3);
                    }
                    if (view2 != null) {
                        ViewGroupOverlay.createFrom(viewGroup2).add(view2);
                    }
                }
            });
        }
    }
}
