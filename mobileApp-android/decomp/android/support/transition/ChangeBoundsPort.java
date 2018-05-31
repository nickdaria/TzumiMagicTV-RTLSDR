package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort.TransitionListenerAdapter;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class ChangeBoundsPort extends TransitionPort {
    private static final String LOG_TAG = "ChangeBounds";
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private static final String[] sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    boolean mReparent = false;
    boolean mResizeClip = false;
    int[] tempLocation = new int[2];

    class C00711 extends TransitionListenerAdapter {
        boolean mCanceled = false;

        C00711() {
        }

        public void onTransitionCancel(TransitionPort transitionPort) {
            this.mCanceled = true;
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            if (!this.mCanceled) {
            }
        }

        public void onTransitionPause(TransitionPort transitionPort) {
        }

        public void onTransitionResume(TransitionPort transitionPort) {
        }
    }

    class C00722 extends TransitionListenerAdapter {
        boolean mCanceled = false;

        C00722() {
        }

        public void onTransitionCancel(TransitionPort transitionPort) {
            this.mCanceled = true;
        }

        public void onTransitionEnd(TransitionPort transitionPort) {
            if (!this.mCanceled) {
            }
        }

        public void onTransitionPause(TransitionPort transitionPort) {
        }

        public void onTransitionResume(TransitionPort transitionPort) {
        }
    }

    class C00733 extends AnimatorListenerAdapter {
        C00733() {
        }

        public void onAnimationEnd(Animator animator) {
        }
    }

    ChangeBoundsPort() {
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        transitionValues.view.getLocationInWindow(this.tempLocation);
        transitionValues.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.tempLocation[0]));
        transitionValues.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.tempLocation[1]));
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        ViewGroup viewGroup2 = (ViewGroup) transitionValues.values.get(PROPNAME_PARENT);
        ViewGroup viewGroup3 = (ViewGroup) transitionValues2.values.get(PROPNAME_PARENT);
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        final View view = transitionValues2.view;
        Object obj = (viewGroup2 == viewGroup3 || viewGroup2.getId() == viewGroup3.getId()) ? 1 : null;
        int intValue;
        int intValue2;
        int intValue3;
        int intValue4;
        if (this.mReparent && obj == null) {
            intValue = ((Integer) transitionValues.values.get(PROPNAME_WINDOW_X)).intValue();
            intValue2 = ((Integer) transitionValues.values.get(PROPNAME_WINDOW_Y)).intValue();
            intValue3 = ((Integer) transitionValues2.values.get(PROPNAME_WINDOW_X)).intValue();
            intValue4 = ((Integer) transitionValues2.values.get(PROPNAME_WINDOW_Y)).intValue();
            if (!(intValue == intValue3 && intValue2 == intValue4)) {
                viewGroup.getLocationInWindow(this.tempLocation);
                Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.ARGB_8888);
                view.draw(new Canvas(createBitmap));
                final Drawable bitmapDrawable = new BitmapDrawable(createBitmap);
                view.setVisibility(4);
                ViewOverlay.createFrom(viewGroup).add(bitmapDrawable);
                Rect rect = new Rect(intValue - this.tempLocation[0], intValue2 - this.tempLocation[1], (intValue - this.tempLocation[0]) + view.getWidth(), (intValue2 - this.tempLocation[1]) + view.getHeight());
                Rect rect2 = new Rect(intValue3 - this.tempLocation[0], intValue4 - this.tempLocation[1], (intValue3 - this.tempLocation[0]) + view.getWidth(), (intValue4 - this.tempLocation[1]) + view.getHeight());
                Animator ofObject = ObjectAnimator.ofObject(bitmapDrawable, "bounds", sRectEvaluator, new Object[]{rect, rect2});
                final ViewGroup viewGroup4 = viewGroup;
                ofObject.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        ViewOverlay.createFrom(viewGroup4).remove(bitmapDrawable);
                        view.setVisibility(0);
                    }
                });
                return ofObject;
            }
        }
        Rect rect3 = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        rect2 = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        intValue2 = rect3.left;
        intValue3 = rect2.left;
        int i = rect3.top;
        int i2 = rect2.top;
        int i3 = rect3.right;
        int i4 = rect2.right;
        int i5 = rect3.bottom;
        int i6 = rect2.bottom;
        int i7 = i3 - intValue2;
        int i8 = i5 - i;
        int i9 = i4 - intValue3;
        int i10 = i6 - i2;
        intValue4 = 0;
        if (!(i7 == 0 || i8 == 0 || i9 == 0 || i10 == 0)) {
            if (intValue2 != intValue3) {
                intValue4 = 1;
            }
            if (i != i2) {
                intValue4++;
            }
            if (i3 != i4) {
                intValue4++;
            }
            if (i5 != i6) {
                intValue4++;
            }
        }
        if (intValue4 > 0) {
            Animator ofPropertyValuesHolder;
            if (this.mResizeClip) {
                if (i7 != i9) {
                    view.setRight(Math.max(i7, i9) + intValue3);
                }
                if (i8 != i10) {
                    view.setBottom(Math.max(i8, i10) + i2);
                }
                if (intValue2 != intValue3) {
                    view.setTranslationX((float) (intValue2 - intValue3));
                }
                if (i != i2) {
                    view.setTranslationY((float) (i - i2));
                }
                float f = (float) (intValue3 - intValue2);
                float f2 = (float) (i2 - i);
                i = i9 - i7;
                i2 = i10 - i8;
                intValue4 = 0;
                if (f != 0.0f) {
                    intValue4 = 1;
                }
                if (f2 != 0.0f) {
                    intValue4++;
                }
                if (!(i == 0 && i2 == 0)) {
                    intValue4++;
                }
                PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[intValue4];
                if (f != 0.0f) {
                    intValue4 = 1;
                    propertyValuesHolderArr[0] = PropertyValuesHolder.ofFloat("translationX", new float[]{view.getTranslationX(), 0.0f});
                } else {
                    intValue4 = 0;
                }
                if (f2 != 0.0f) {
                    intValue = intValue4 + 1;
                    propertyValuesHolderArr[intValue4] = PropertyValuesHolder.ofFloat("translationY", new float[]{view.getTranslationY(), 0.0f});
                }
                if (!(i == 0 && i2 == 0)) {
                    rect3 = new Rect(0, 0, i7, i8);
                    rect3 = new Rect(0, 0, i9, i10);
                }
                ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderArr);
                if (view.getParent() instanceof ViewGroup) {
                    viewGroup2 = (ViewGroup) view.getParent();
                    addListener(new C00722());
                }
                ofPropertyValuesHolder.addListener(new C00733());
                return ofPropertyValuesHolder;
            }
            PropertyValuesHolder[] propertyValuesHolderArr2 = new PropertyValuesHolder[intValue4];
            if (intValue2 != intValue3) {
                view.setLeft(intValue2);
            }
            if (i != i2) {
                view.setTop(i);
            }
            if (i3 != i4) {
                view.setRight(i3);
            }
            if (i5 != i6) {
                view.setBottom(i5);
            }
            if (intValue2 != intValue3) {
                intValue4 = 1;
                propertyValuesHolderArr2[0] = PropertyValuesHolder.ofInt("left", new int[]{intValue2, intValue3});
            } else {
                intValue4 = 0;
            }
            if (i != i2) {
                intValue = intValue4 + 1;
                propertyValuesHolderArr2[intValue4] = PropertyValuesHolder.ofInt("top", new int[]{i, i2});
            } else {
                intValue = intValue4;
            }
            if (i3 != i4) {
                intValue4 = intValue + 1;
                propertyValuesHolderArr2[intValue] = PropertyValuesHolder.ofInt("right", new int[]{i3, i4});
            } else {
                intValue4 = intValue;
            }
            if (i5 != i6) {
                intValue = intValue4 + 1;
                propertyValuesHolderArr2[intValue4] = PropertyValuesHolder.ofInt("bottom", new int[]{i5, i6});
            }
            ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderArr2);
            if (view.getParent() instanceof ViewGroup) {
                viewGroup2 = (ViewGroup) view.getParent();
                addListener(new C00711());
            }
            return ofPropertyValuesHolder;
        }
        return null;
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setReparent(boolean z) {
        this.mReparent = z;
    }

    public void setResizeClip(boolean z) {
        this.mResizeClip = z;
    }
}
