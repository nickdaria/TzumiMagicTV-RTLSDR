package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

abstract class TransitionImpl {
    TransitionImpl() {
    }

    public abstract TransitionImpl addListener(TransitionInterfaceListener transitionInterfaceListener);

    public abstract TransitionImpl addTarget(int i);

    public abstract TransitionImpl addTarget(View view);

    public abstract void captureEndValues(TransitionValues transitionValues);

    public abstract void captureStartValues(TransitionValues transitionValues);

    public abstract Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2);

    public abstract TransitionImpl excludeChildren(int i, boolean z);

    public abstract TransitionImpl excludeChildren(View view, boolean z);

    public abstract TransitionImpl excludeChildren(Class cls, boolean z);

    public abstract TransitionImpl excludeTarget(int i, boolean z);

    public abstract TransitionImpl excludeTarget(View view, boolean z);

    public abstract TransitionImpl excludeTarget(Class cls, boolean z);

    public abstract long getDuration();

    public abstract TimeInterpolator getInterpolator();

    public abstract String getName();

    public abstract long getStartDelay();

    public abstract List<Integer> getTargetIds();

    public abstract List<View> getTargets();

    public abstract String[] getTransitionProperties();

    public abstract TransitionValues getTransitionValues(View view, boolean z);

    public void init(TransitionInterface transitionInterface) {
        init(transitionInterface, null);
    }

    public abstract void init(TransitionInterface transitionInterface, Object obj);

    public abstract TransitionImpl removeListener(TransitionInterfaceListener transitionInterfaceListener);

    public abstract TransitionImpl removeTarget(int i);

    public abstract TransitionImpl removeTarget(View view);

    public abstract TransitionImpl setDuration(long j);

    public abstract TransitionImpl setInterpolator(TimeInterpolator timeInterpolator);

    public abstract TransitionImpl setStartDelay(long j);
}
