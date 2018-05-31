package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@TargetApi(14)
@RequiresApi(14)
class FadeIcs extends TransitionIcs implements VisibilityImpl {
    public FadeIcs(TransitionInterface transitionInterface) {
        init(transitionInterface, new FadePort());
    }

    public FadeIcs(TransitionInterface transitionInterface, int i) {
        init(transitionInterface, new FadePort(i));
    }

    public boolean isVisible(TransitionValues transitionValues) {
        return ((FadePort) this.mTransition).isVisible(transitionValues);
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((FadePort) this.mTransition).onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((FadePort) this.mTransition).onDisappear(viewGroup, transitionValues, i, transitionValues, i);
    }
}
