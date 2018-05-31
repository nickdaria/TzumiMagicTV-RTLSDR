package android.support.transition;

import android.animation.Animator;
import android.view.ViewGroup;

interface VisibilityInterface extends TransitionInterface {
    boolean isVisible(TransitionValues transitionValues);

    Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2);

    Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2);
}
